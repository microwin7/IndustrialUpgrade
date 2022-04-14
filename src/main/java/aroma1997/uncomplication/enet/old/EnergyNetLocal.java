package aroma1997.uncomplication.enet.old;

import com.denfop.Config;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMetaDelegate;
import ic2.api.info.ILocatable;
import ic2.core.IC2;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EnergyNetLocal {
    private static EnumFacing[] directions;
    private final World world;
    private final EnergyPathMap energySourceToEnergyPathMap;

    private final Map<IEnergyTile, BlockPos> chunkCoordinatesMap;
    private final Map<IEnergyTile, TileEntity> energyTileTileEntityMap;
    private final Map<BlockPos, IEnergyTile> chunkCoordinatesIEnergyTileMap;
    private final List<IEnergySource> sources;
    private final List<IEnergyTile> energyTileList;
    private final WaitingList waitingList;
    private final List<BlockPos> listFromCoord;
    EnergyNetLocal(final World world) {
        this.energySourceToEnergyPathMap = new EnergyPathMap();
        this.sources = new ArrayList<>();
        this.waitingList = new WaitingList();
        this.world = world;
        this.chunkCoordinatesIEnergyTileMap = new HashMap<>();
        this.chunkCoordinatesMap= new HashMap<>();
        this.energyTileList= new ArrayList<>();
        this.energyTileTileEntityMap = new HashMap<>();
        this.listFromCoord = new ArrayList<>();
    }

    public void addTile(IEnergyTile tile1) {
        if (tile1 instanceof IMetaDelegate) {
            final List<IEnergyTile> tiles = ((IMetaDelegate) tile1).getSubTiles();
            for (final IEnergyTile tile : tiles) {
                this.addTileEntity(getTileFromIEnergy(tile).getPos(), tile1,tile);
            }
            if (tile1 instanceof IEnergySource ) {
                this.sources.add((IEnergySource) tile1);
            }

            this.energyTileList.addAll(tiles);
        }else {
            this.addTileEntity(getTileFromIEnergy(tile1).getPos(), tile1);
            this.energyTileList.add(tile1);
        }
    }

    public void addTileEntity(final BlockPos coords, final IEnergyTile tile) {
        if (this.listFromCoord.contains(coords))
           return;

            TileEntity te = getTileFromIEnergy(tile);
        this.listFromCoord.add(coords);
        this.energyTileTileEntityMap.put(tile,te);
        this.chunkCoordinatesMap.put(tile, coords);
            this.chunkCoordinatesIEnergyTileMap.put(coords, tile);
        this.update(coords.getX(), coords.getY(), coords.getZ());
        if (tile instanceof IEnergyAcceptor) {
            this.waitingList.onTileEntityAdded(this.getValidReceivers(tile, true), tile);
        }
        if (tile instanceof IEnergySource && !(tile instanceof IMetaDelegate)) {
            this.sources.add((IEnergySource) tile);
        }
    }
    public void addTileEntity(final BlockPos coords, final IEnergyTile tile,final IEnergyTile tile1) {
        if (this.listFromCoord.contains(coords))
            return;

        TileEntity te = getTileFromIEnergy(tile);
        TileEntity te1 = getTileFromIEnergy(tile1);
        this.listFromCoord.add(coords);
        this.energyTileTileEntityMap.put(tile,te);
        this.energyTileTileEntityMap.put(tile1,te1);
        this.chunkCoordinatesMap.put(tile, coords);
        this.chunkCoordinatesIEnergyTileMap.put(coords, tile);

        this.update(coords.getX(), coords.getY(), coords.getZ());
        if (tile instanceof IEnergyAcceptor) {
            this.waitingList.onTileEntityAdded(this.getValidReceivers(tile, true), tile);
        }
        if (tile instanceof IEnergySource && !(tile instanceof IMetaDelegate)) {
            this.sources.add((IEnergySource) tile);

        }


    }
    public void removeTile(IEnergyTile tile1) {
        if (tile1 instanceof IMetaDelegate) {
            final List<IEnergyTile> tiles = ((IMetaDelegate) tile1).getSubTiles();
            for (final IEnergyTile tile : tiles) {
                BlockPos coord1;
                if(this.energyTileTileEntityMap.containsKey(tile))
                    coord1 = this.energyTileTileEntityMap.get(tile).getPos();
                else
                    coord1 = getTileFromIEnergy(tile).getPos();
                this.removeTileEntity(coord1, tile1,tile);

            }
            this.energyTileList.removeAll(tiles);
        } else {
            this.removeTileEntity(tile1);
            this.energyTileList.remove(tile1);
        }
    }
    public void removeTileEntity(BlockPos coord, IEnergyTile tile,IEnergyTile tile1) {
        if (!this.listFromCoord.contains(coord) ) {
            return;
        }
        this.listFromCoord.remove(coord);
        this.chunkCoordinatesMap.remove(tile,coord);
        this.chunkCoordinatesIEnergyTileMap.remove(coord);
        this.energyTileTileEntityMap.remove(tile1,this.energyTileTileEntityMap.get(tile1));
        this.energyTileTileEntityMap.remove(tile,this.energyTileTileEntityMap.get(tile));
        this.update(coord.getX(), coord.getY(), coord.getZ());
        if (tile instanceof IEnergyAcceptor) {
            this.energySourceToEnergyPathMap.removeAll(this.energySourceToEnergyPathMap.getSources((IEnergyAcceptor) tile));
            this.waitingList.onTileEntityRemoved(tile);
        }
        if (tile instanceof IEnergySource) {
            this.sources.remove((IEnergySource)tile);
            this.energySourceToEnergyPathMap.remove((IEnergySource)tile);
        }
    }

    public void removeTileEntity(IEnergyTile tile) {
        if (!this.energyTileList.contains(tile) ) {
            return;
        }
        final BlockPos coord = this.chunkCoordinatesMap.get(tile);
        this.listFromCoord.remove(coord);
        this.chunkCoordinatesMap.remove(tile);
        this.energyTileTileEntityMap.remove(tile,this.energyTileTileEntityMap.get(tile));
        this.chunkCoordinatesIEnergyTileMap.remove(coord,tile);
        this.update(coord.getX(), coord.getY(), coord.getZ());
        if (tile instanceof IEnergyAcceptor) {
            this.energySourceToEnergyPathMap.removeAll(this.energySourceToEnergyPathMap.getSources((IEnergyAcceptor) tile));
            this.waitingList.onTileEntityRemoved(tile);
        }
        if (tile instanceof IEnergySource) {
            this.sources.remove((IEnergySource)tile);
            this.energySourceToEnergyPathMap.remove((IEnergySource)tile);
        }
    }

    public double emitEnergyFrom(final IEnergySource energySource, double amount) {
        if (!this.energySourceToEnergyPathMap.containsKey(energySource)) {
            this.energySourceToEnergyPathMap.put(energySource, this.discover(energySource));
        }
        List<EnergyPath> activeEnergyPaths = new ArrayList<>();
        final List<EnergyPath> energyPaths = this.energySourceToEnergyPathMap.get(energySource);
        for (final EnergyPath energyPath : energyPaths) {
            assert energyPath.target instanceof IEnergySink;
            final IEnergySink energySink = (IEnergySink) energyPath.target;
            if (energySink.getDemandedEnergy() <= 0.0) {
                continue;
            }


            activeEnergyPaths.add(energyPath);
        }


        final Map<EnergyPath, Long> suppliedEnergyPaths = new HashMap<>();
        while (!activeEnergyPaths.isEmpty() && amount > 0) {
            double energyConsumed = 0;

            final List<EnergyPath> currentActiveEnergyPaths = activeEnergyPaths;
            activeEnergyPaths = new ArrayList<>();
            for (final EnergyPath energyPath2 : currentActiveEnergyPaths) {
                final IEnergySink energySink2 = (IEnergySink) energyPath2.target;


                double adding = Math.min(amount, energySink2.getDemandedEnergy());

                    if (adding <= 0.0) {
                        continue;
                    }


                        double energyReturned = energySink2.injectEnergy(energyPath2.targetDirection, adding, 0);
                        if (energyReturned == 0.0) {
                            activeEnergyPaths.add(energyPath2);
                        } else if (energyReturned >= amount) {
                            energyReturned = amount;
                        }
                        energyConsumed += (adding - energyReturned );
                        final double energyInjected = (adding - energyReturned);
                        if (!suppliedEnergyPaths.containsKey(energyPath2)) {
                            suppliedEnergyPaths.put(energyPath2, (long) energyInjected);
                            energyPath2.totalEnergyConducted = (long) energyInjected;
                        } else {
                            suppliedEnergyPaths.replace(energyPath2,
                                    (long) (energyInjected + suppliedEnergyPaths.get(energyPath2))
                            );
                            energyPath2.totalEnergyConducted =  suppliedEnergyPaths.get(energyPath2);
                        }


            }
            if (energyConsumed == 0 && !activeEnergyPaths.isEmpty()) {
                activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
            }

            amount -= energyConsumed;
        }
        return amount;
    }

    public double getTotalEnergyEmitted(final IEnergyTile tileEntity) {
        double ret = 0.0;
        if (tileEntity instanceof IEnergyConductor) {
            for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor) tileEntity)) {
                if (energyPath.conductors.contains(tileEntity)) {
                    ret += energyPath.totalEnergyConducted;
                }
            }
        }
        if (tileEntity instanceof IEnergySource && this.energySourceToEnergyPathMap.containsKey((IEnergySource)tileEntity)) {
            for (final EnergyPath energyPath2 : this.energySourceToEnergyPathMap.get((IEnergySource)tileEntity)) {
                ret += energyPath2.totalEnergyConducted;
            }
        }
        return ret;
    }

    public double getTotalEnergySunken(final IEnergyTile tileEntity) {
        double ret = 0.0;
        if (tileEntity instanceof IEnergyConductor || tileEntity instanceof IEnergySink) {
            for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor) tileEntity)) {
                if ((tileEntity instanceof IEnergySink && energyPath.target == tileEntity) || (tileEntity instanceof IEnergyConductor && energyPath.conductors.contains(tileEntity))) {
                    ret += energyPath.totalEnergyConducted;
                }
            }
        }
        return ret;
    }
    public TileEntity  getTileFromIEnergy(IEnergyTile tile){
        if(tile instanceof TileEntity)
            return (TileEntity) tile;
        if(tile instanceof ILocatable){
            return this.world.getTileEntity (((ILocatable)tile).getPosition());
        }

        return null;
    }
    private List<EnergyPath> discover(final IEnergySource emitter) {
        final Map<IEnergyTile, EnergyBlockLink> reachedTileEntities = new HashMap<>();
        final LinkedList<IEnergyTile> tileEntitiesToCheck = new LinkedList<>();
        if(!(emitter instanceof IMetaDelegate))
       tileEntitiesToCheck.add(emitter);
        else
            tileEntitiesToCheck.addAll(((IMetaDelegate)emitter).getSubTiles());

        while (!tileEntitiesToCheck.isEmpty()) {
            final IEnergyTile currentTileEntity = tileEntitiesToCheck.remove();

            TileEntity tile =this.energyTileTileEntityMap.get(currentTileEntity);

            if (!tile.isInvalid()) {
                final List<EnergyTarget> validReceivers = this.getValidReceivers(currentTileEntity, false);
                for (final EnergyTarget validReceiver : validReceivers) {
                    if (validReceiver.tileEntity != emitter) {

                        if (reachedTileEntities.containsKey(validReceiver.tileEntity)) {
                            continue;
                        }
                        reachedTileEntities.put(validReceiver.tileEntity, new EnergyBlockLink(validReceiver.direction));
                        if (!(validReceiver.tileEntity instanceof IEnergyConductor)) {
                            continue;
                        }
                        tileEntitiesToCheck.remove(validReceiver.tileEntity);
                        tileEntitiesToCheck.add(validReceiver.tileEntity);
                    }
                }
            }

            }
        final List<EnergyPath> energyPaths = new LinkedList<>();
        for (final Map.Entry<IEnergyTile, EnergyBlockLink> entry : reachedTileEntities.entrySet()) {
            IEnergyTile tileEntity = entry.getKey();
            if ((tileEntity instanceof IEnergySink)) {
                EnergyBlockLink energyBlockLink = entry.getValue();
                final EnergyPath energyPath = new EnergyPath();
                energyPath.target = tileEntity;
                energyPath.targetDirection = energyBlockLink.direction;
                if (emitter != null) {
                    while (true) {
                        TileEntity te =this.energyTileTileEntityMap.get(tileEntity);
                        if (energyBlockLink != null) {
                            tileEntity = this.getTileEntity(te.getPos().offset(energyBlockLink.direction));
                        }
                        if (tileEntity == emitter) {
                            break;
                        }
                        if (!(tileEntity instanceof IEnergyConductor)) {
                            break;
                        }
                        final IEnergyConductor energyConductor = (IEnergyConductor) tileEntity;
                        energyPath.conductors.add(energyConductor);
                        energyBlockLink = reachedTileEntities.get(tileEntity);
                        if (energyBlockLink != null) {
                            continue;
                        }
                        IC2.platform.displayError("An energy network pathfinding entry is corrupted.\nThis could happen due to " +
                                "incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile " +
                                "entities below)\nE: " + emitter + " (" + te.getPos().getX() + "," + te.getPos().getY()  + "," + te.getPos().getZ()  + ")\n" + "C: " + tileEntity + " (" + te.getPos().getX() + "," + te.getPos().getY() + "," + te.getPos().getZ() + ")\n" + "R: " + energyPath.target + " (" +this.energyTileTileEntityMap.get(energyPath.target).getPos().getX() + "," + getTileFromIEnergy(energyPath.target).getPos().getY() + "," +getTileFromIEnergy( energyPath.target).getPos().getZ() + ")");
                    }
                }
                energyPaths.add(energyPath);
            }
        }
        return energyPaths;
    }


    public IEnergyTile getNeighbor(final IEnergyTile tile, final EnumFacing dir) {
        if (tile == null) {
            return null;
        }
        return  this.getTileEntity(this.energyTileTileEntityMap.get(tile).getPos().offset(dir));
    }
    public IEnergyTile getNeighbor(final IEnergyTile tile, final EnumFacing dir,List<IEnergyTile> tiles) {
        if (tile == null) {
            return null;
        }
        if(!this.energyTileTileEntityMap.containsKey(tile) )
            return null;
        IEnergyTile tile1 = this.getTileEntity(this.energyTileTileEntityMap.get(tile).getPos().offset(dir));
        if(tiles.contains(tile1))
            return null;
        return tile1 ;
    }
    private List<EnergyTarget> getValidReceivers(final IEnergyTile emitter, final boolean reverse) {
        final List<EnergyTarget> validReceivers = new LinkedList<>();
        if (emitter instanceof IMetaDelegate) {
            final IMetaDelegate meta = (IMetaDelegate) emitter;
            final List<IEnergyTile> targets = meta.getSubTiles();
            for (final IEnergyTile tile : targets) {
                for (final EnumFacing direction : EnergyNetLocal.directions) {
                    final IEnergyTile target = getNeighbor(tile, direction,targets);
                    if (target == emitter) {
                        continue;
                    }
                    if (target == null) {
                        continue;
                    }
                    final EnumFacing inverseDirection = direction.getOpposite();
                    if (reverse) {
                        if (!(emitter instanceof IEnergyAcceptor) || !(target instanceof IEnergyEmitter)) {
                            continue;
                        }
                        final IEnergyEmitter sender = (IEnergyEmitter) target;
                        final IEnergyAcceptor receiver = (IEnergyAcceptor) emitter;
                        if (!sender.emitsEnergyTo(receiver, inverseDirection) || !receiver.acceptsEnergyFrom(sender,
                                direction)) {
                            continue;
                        }
                    } else {
                        if (!(emitter instanceof IEnergyEmitter) || !(target instanceof IEnergyAcceptor)) {
                            continue;
                        }
                        final IEnergyEmitter sender = (IEnergyEmitter) emitter;
                        final IEnergyAcceptor receiver = (IEnergyAcceptor) target;
                        if (!sender.emitsEnergyTo(receiver, direction) || !receiver.acceptsEnergyFrom(sender,
                                inverseDirection)) {
                            continue;
                        }
                    }
                    validReceivers.add(new EnergyTarget(target, inverseDirection));

                }
            }
        }else{
            for (final EnumFacing direction : EnergyNetLocal.directions) {
                final IEnergyTile target2 = getNeighbor(emitter, direction);
                if (target2 != null) {
                    final EnumFacing inverseDirection2 = direction.getOpposite();
                    if (reverse) {
                        if (emitter instanceof IEnergyAcceptor && target2 instanceof IEnergyEmitter) {
                            final IEnergyEmitter sender2 = (IEnergyEmitter) target2;
                            final IEnergyAcceptor receiver2 = (IEnergyAcceptor) emitter;
                            if (sender2.emitsEnergyTo(receiver2, inverseDirection2) && receiver2.acceptsEnergyFrom(sender2,
                                    direction)) {
                                validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                            }
                        }
                    } else if (emitter instanceof IEnergyEmitter && target2 instanceof IEnergyAcceptor) {
                        final IEnergyEmitter sender2 = (IEnergyEmitter) emitter;
                        final IEnergyAcceptor receiver2 = (IEnergyAcceptor) target2;
                        if (sender2.emitsEnergyTo(receiver2, direction) && receiver2.acceptsEnergyFrom(sender2, inverseDirection2)) {
                            validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                        }
                    }
                }
            }
        }
        //

        return validReceivers;
    }

    public List<IEnergySource> discoverFirstPathOrSources(final IEnergyTile par1) {
        final Set<IEnergyTile> reached = new HashSet<>();
        final List<IEnergySource> result = new ArrayList<>();
        final List<IEnergyTile> workList = new ArrayList<>();
        workList.add(par1);
        while (workList.size() > 0) {
            final IEnergyTile tile = workList.remove(0);
            final TileEntity te = this.energyTileTileEntityMap.get(tile);
            if (!te.isInvalid()) {
                final List<EnergyTarget> targets = this.getValidReceivers(tile, true);
                for (EnergyTarget energyTarget : targets) {
                    final IEnergyTile target = energyTarget.tileEntity;
                    if (target != par1) {
                        if (!reached.contains(target)) {
                            reached.add(target);
                            if (target instanceof IEnergySource) {
                                result.add((IEnergySource) target);
                            } else if (target instanceof IEnergyConductor) {
                                workList.add(target);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }



    public void onTickStart() {



    }

    public void onTickEnd() {
        if(this.world.provider.getWorldTime() % Config.tickupdateenergysystem == 0)
        if (this.waitingList.hasWork()) {
            final List<IEnergyTile> tiles = this.waitingList.getPathTiles();
            for (final IEnergyTile tile : tiles) {
                final List<IEnergySource> sources = this.discoverFirstPathOrSources(tile);
                if (sources.size() > 0) {
                    this.energySourceToEnergyPathMap.removeAll(sources);
                }
            }
            this.waitingList.clear();
        }
        if(this.world.provider.getWorldTime() % Config.ticktransferenergy == 0)
        for (IEnergySource entry : this.sources) {
            if (entry != null) {


                double offer =Math.min(
                        entry.getOfferedEnergy(),
                        EnergyNet.instance.getPowerFromTier( entry.getSourceTier()));
                if (offer > 0) {
                    for (double packetAmount = 1, i = 0; i < packetAmount; ++i) {
                        offer =Math.min(
                                entry.getOfferedEnergy(),
                                EnergyNet.instance.getPowerFromTier( entry.getSourceTier()));
                        if (offer < 1) {
                            break;
                        }
                        final double removed = offer - this.emitEnergyFrom(entry, offer);
                        if (removed <= 0) {
                            break;
                        }

                        entry.drawEnergy(removed);
                    }
                }

            }
        }
    }










    public IEnergyTile getTileEntity(BlockPos pos) {

        return this.chunkCoordinatesIEnergyTileMap.get(pos);
    }
    public NodeStats getNodeStats(final IEnergyTile tile) {
        final double emitted = this.getTotalEnergyEmitted(tile);
        final double received = this.getTotalEnergySunken(tile);
        return new NodeStats(received, emitted, 0);
    }




    void update(final int x, final int y, final int z) {
        for (final EnumFacing dir : EnumFacing.values()) {
            if (this.world.isChunkGeneratedAt(x + dir.getFrontOffsetX() >> 4, z + dir.getFrontOffsetZ()  >> 4)) {
                BlockPos pos = new BlockPos(x,y ,
                        z).offset(dir);
                this.world.neighborChanged(pos , Blocks.AIR, pos);

            }
        }
    }

    public void onUnload() {
        this.energySourceToEnergyPathMap.clear();
        this.sources.clear();
        this.waitingList.clear();
        this.chunkCoordinatesIEnergyTileMap.clear();
        this.chunkCoordinatesMap.clear();
        this.energyTileList.clear();
        this.energyTileTileEntityMap.clear();
        this.listFromCoord.clear();
    }

    static {
        EnergyNetLocal.directions = EnumFacing.values();
    }



    static class EnergyTarget {
        final IEnergyTile tileEntity;
        final EnumFacing direction;

        EnergyTarget(final IEnergyTile tileEntity, final EnumFacing direction) {
            this.tileEntity = tileEntity;
            this.direction = direction;
        }
    }

    static class EnergyBlockLink {
        final EnumFacing direction;

        EnergyBlockLink(final EnumFacing direction) {
            this.direction = direction;
        }
    }

    static class EnergyPath {
        IEnergyTile target;
        EnumFacing targetDirection;
        final Set<IEnergyConductor> conductors;
        long totalEnergyConducted;

        EnergyPath() {
            this.target = null;
            this.conductors = new HashSet<>();
            this.totalEnergyConducted = 0L;
        }
    }

    static class EnergyPathMap {
        final Map<IEnergySource, List<EnergyPath>> senderPath;

        EnergyPathMap() {
            this.senderPath = new HashMap<>();

        }

        public void put(final IEnergySource par1, final List<EnergyPath> par2) {
            this.senderPath.put(par1, par2);


        }

        public boolean containsKey(final IEnergySource par1) {
            return this.senderPath.containsKey(par1);
        }

        public List<EnergyPath> get(final IEnergySource par1) {
            return this.senderPath.get(par1);
        }

        public void remove(final IEnergySource par1) {
           this.senderPath.remove(par1);
        }

        public void removeAll(final List<IEnergySource> par1) {
            for (IEnergySource iEnergySource : par1) {
                this.remove(iEnergySource);
            }
        }

        public List<EnergyPath> getPaths(final IEnergyAcceptor par1) {
            final List<EnergyPath> paths = new ArrayList<>();
            for (final IEnergySource source : this.getSources(par1)) {
                if (this.containsKey(source)) {
                    paths.addAll(this.get(source));
                }
            }
            return paths;
        }

        public List<IEnergySource> getSources(final IEnergyAcceptor par1) {
            final List<IEnergySource> source = new ArrayList<>();
            for (final Map.Entry<IEnergySource,List<EnergyPath>> entry : this.senderPath.entrySet()) {
                if (source.contains(entry.getKey())) {
                    continue;
                }
                for(EnergyPath path : entry.getValue()) {
                    if ((!(par1 instanceof IEnergyConductor) || !path.conductors.contains(par1)) && (!(par1 instanceof IEnergySink) || path.target != par1)) {
                        continue;
                    }
                    source.add(entry.getKey());
                }
            }
            return source;
        }

        public void clear() {
            this.senderPath.clear();
        }
    }

    class WaitingList {
        final List<PathLogic> paths;

        WaitingList() {
            this.paths = new ArrayList<>();
        }

        public void onTileEntityAdded(final List<EnergyTarget> around, final IEnergyTile tile) {
            if (around.isEmpty() || this.paths.isEmpty()) {
                this.createNewPath(tile);
                return;
            }
            boolean found = false;
            final List<PathLogic> logics = new ArrayList<>();
            for (final PathLogic logic : this.paths) {
                if (logic.contains(tile)) {
                    found = true;
                    if (tile instanceof IEnergyConductor) {
                        logics.add(logic);
                    }
                } else {
                    for (final EnergyTarget target : around) {
                        if (logic.contains(target.tileEntity)) {
                            found = true;
                            logic.add(tile);
                            if (target.tileEntity instanceof IEnergyConductor) {
                                logics.add(logic);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            if (logics.size() > 1 && tile instanceof IEnergyConductor) {
                final PathLogic newLogic = new PathLogic();
                for (final PathLogic logic2 : logics) {
                    this.paths.remove(logic2);
                    for (final IEnergyTile toMove : logic2.tiles) {
                        if (!newLogic.contains(toMove)) {
                            newLogic.add(toMove);
                        }
                    }
                    logic2.clear();
                }
                this.paths.add(newLogic);
            }
            if (!found) {
                this.createNewPath(tile);
            }
        }

        public void onTileEntityRemoved(final IEnergyTile par1) {
            if (this.paths.isEmpty()) {
                return;
            }
            final List<IEnergyTile> toRecalculate = new ArrayList<>();
            for (int i = 0; i < this.paths.size(); ++i) {
                final PathLogic logic = this.paths.get(i);
                if (logic.contains(par1)) {
                    logic.remove(par1);
                    toRecalculate.addAll(logic.tiles);
                    this.paths.remove(i--);
                }
            }
            for (final IEnergyTile tile : toRecalculate) {
                this.onTileEntityAdded(EnergyNetLocal.this.getValidReceivers(tile, true), tile);
            }
        }

        public void createNewPath(final IEnergyTile par1) {
            final PathLogic logic = new PathLogic();
            logic.add(par1);
            this.paths.add(logic);
        }

        public void clear() {
            if (this.paths.isEmpty()) {
                return;
            }
            for (PathLogic path : this.paths) {
                path.clear();
            }
            this.paths.clear();
        }

        public boolean hasWork() {
            return this.paths.size() > 0;
        }

        public List<IEnergyTile> getPathTiles() {
            final List<IEnergyTile> tiles = new ArrayList<>();
            for (PathLogic path : this.paths) {
                final IEnergyTile tile = path.getRepresentingTile();
                if (tile != null) {
                    tiles.add(tile);
                }
            }
            return tiles;
        }
    }

    static class PathLogic {
        final List<IEnergyTile> tiles;

        PathLogic() {
            this.tiles = new ArrayList<>();
        }

        public boolean contains(final IEnergyTile par1) {
            return this.tiles.contains(par1);
        }

        public void add(final IEnergyTile par1) {
            this.tiles.add(par1);
        }

        public void remove(final IEnergyTile par1) {
            this.tiles.remove(par1);
        }

        public void clear() {
            this.tiles.clear();
        }

        public IEnergyTile getRepresentingTile() {
            if (this.tiles.isEmpty()) {
                return null;
            }
            return this.tiles.get(0);
        }
    }
}
