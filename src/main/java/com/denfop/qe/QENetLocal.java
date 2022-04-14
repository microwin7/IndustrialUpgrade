package com.denfop.qe;

import com.denfop.Config;
import com.denfop.api.qe.IQEAcceptor;
import com.denfop.api.qe.IQEConductor;
import com.denfop.api.qe.IQEEmitter;
import com.denfop.api.qe.IQESink;
import com.denfop.api.qe.IQESource;
import com.denfop.api.qe.IQETile;
import com.denfop.api.qe.NodeQEStats;
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
import java.util.Vector;

public class QENetLocal {
    private static final EnumFacing[] directions;
    private final World world;
    private final EnergyPathMap energySourceToEnergyPathMap;

    private final Map<IQETile, BlockPos> chunkCoordinatesMap;
    private final Map<IQETile, TileEntity> energyTileTileEntityMap;
    private final Map<BlockPos, IQETile> chunkCoordinatesIQETileMap;
    private final List<IQESource> sources;
    private final List<IQETile> energyTileList;
    private final WaitingList waitingList;
    private final List<BlockPos> listFromCoord;
    QENetLocal(final World world) {
        this.energySourceToEnergyPathMap = new EnergyPathMap();
        this.sources = new ArrayList<>();
        this.waitingList = new WaitingList();
        this.world = world;
        this.chunkCoordinatesIQETileMap = new HashMap<>();
        this.chunkCoordinatesMap= new HashMap<>();
        this.energyTileList= new ArrayList<>();
        this.energyTileTileEntityMap = new HashMap<>();
        this.listFromCoord = new ArrayList<>();
    }

    public void addTile(IQETile tile1) {
        this.addTileEntity(getTileFromIQE(tile1).getPos(), tile1);
        this.energyTileList.add(tile1);

    }

    public void addTileEntity(final BlockPos coords, final IQETile tile) {
        if (this.listFromCoord.contains(coords))
            return;

        TileEntity te = getTileFromIQE(tile);
        this.listFromCoord.add(coords);
        this.energyTileTileEntityMap.put(tile,te);
        this.chunkCoordinatesMap.put(tile, coords);
        this.chunkCoordinatesIQETileMap.put(coords, tile);
        this.update(coords.getX(), coords.getY(), coords.getZ());
        if (tile instanceof IQEAcceptor) {
            this.waitingList.onTileEntityAdded(this.getValidReceivers(tile, true), tile);
        }
        if (tile instanceof IQESource) {
            this.sources.add((IQESource) tile);
        }
    }
    public void removeTile(IQETile tile1) {
        this.removeTileEntity(tile1);
        this.energyTileList.remove(tile1);

    }
    public void removeTileEntity(IQETile tile) {
        if (!this.energyTileList.contains(tile) ) {
            return;
        }
        final BlockPos coord = this.chunkCoordinatesMap.get(tile);
        this.listFromCoord.remove(coord);
        this.chunkCoordinatesMap.remove(tile);
        this.energyTileTileEntityMap.remove(tile,this.energyTileTileEntityMap.get(tile));
        this.chunkCoordinatesIQETileMap.remove(coord,tile);
        this.update(coord.getX(), coord.getY(), coord.getZ());
        if (tile instanceof IQEAcceptor) {
            this.energySourceToEnergyPathMap.removeAll(this.energySourceToEnergyPathMap.getSources((IQEAcceptor) tile));
            this.waitingList.onTileEntityRemoved(tile);
        }
        if (tile instanceof IQESource) {
            this.sources.remove((IQESource)tile);
            this.energySourceToEnergyPathMap.remove((IQESource)tile);
        }
    }

    public double emitEnergyFrom(final IQESource energySource, double amount) {
        if (!this.energySourceToEnergyPathMap.containsKey(energySource)) {
            this.energySourceToEnergyPathMap.put(energySource, this.discover(energySource));
        }
        List<EnergyPath> activeEnergyPaths = new Vector<>();
        for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.get(energySource)) {
            assert energyPath.target instanceof IQESink;
            final IQESink energySink = (IQESink) energyPath.target;
            if (energySink.getDemandedQE() <= 0.0) {
                continue;
            }


            activeEnergyPaths.add(energyPath);
        }


        final Map<EnergyPath, Long> suppliedEnergyPaths = new HashMap<>();
        while (!activeEnergyPaths.isEmpty() && amount > 0) {
            double energyConsumed = 0;

            final List<EnergyPath> currentActiveEnergyPaths = activeEnergyPaths;
            activeEnergyPaths = new Vector<>();
            for (final EnergyPath energyPath2 : currentActiveEnergyPaths) {
                final IQESink energySink2 = (IQESink) energyPath2.target;


                double adding = Math.min(amount, energySink2.getDemandedQE());

                if (adding <= 0.0) {
                    continue;
                }


                double energyReturned = energySink2.injectQE(energyPath2.targetDirection, adding, 0);
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

    public double getTotalEnergyEmitted(final IQETile tileEntity) {
        double ret = 0.0;
        if (tileEntity instanceof IQEConductor) {
            for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IQEAcceptor) tileEntity)) {
                if (energyPath.conductors.contains(tileEntity)) {
                    ret += energyPath.totalEnergyConducted;
                }
            }
        }
        if (tileEntity instanceof IQESource && this.energySourceToEnergyPathMap.containsKey((IQESource)tileEntity)) {
            for (final EnergyPath energyPath2 : this.energySourceToEnergyPathMap.get((IQESource)tileEntity)) {
                ret += energyPath2.totalEnergyConducted;
            }
        }
        return ret;
    }

    public double getTotalEnergySunken(final IQETile tileEntity) {
        double ret = 0.0;
        if (tileEntity instanceof IQEConductor || tileEntity instanceof IQESink) {
            for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IQEAcceptor) tileEntity)) {
                if ((tileEntity instanceof IQESink && energyPath.target == tileEntity) || (tileEntity instanceof IQEConductor && energyPath.conductors.contains(tileEntity))) {
                    ret += energyPath.totalEnergyConducted;
                }
            }
        }
        return ret;
    }
    public TileEntity  getTileFromIQE(IQETile tile){
        if(tile instanceof TileEntity)
            return (TileEntity) tile;
        if(tile instanceof ILocatable){
            return this.world.getTileEntity (((ILocatable)tile).getPosition());
        }

        return null;
    }
    private List<EnergyPath> discover(final IQESource emitter) {
        final Map<IQETile, EnergyBlockLink> reachedTileEntities = new HashMap<>();
        final LinkedList<IQETile> tileEntitiesToCheck = new LinkedList<>();
        tileEntitiesToCheck.add(emitter);


        while (!tileEntitiesToCheck.isEmpty()) {
            final IQETile currentTileEntity = tileEntitiesToCheck.remove();

            TileEntity tile =this.energyTileTileEntityMap.get(currentTileEntity);

            if (!tile.isInvalid()) {
                final List<EnergyTarget> validReceivers = this.getValidReceivers(currentTileEntity, false);
                for (final EnergyTarget validReceiver : validReceivers) {
                    if (validReceiver.tileEntity != emitter) {

                        if (reachedTileEntities.containsKey(validReceiver.tileEntity)) {
                            continue;
                        }
                        reachedTileEntities.put(validReceiver.tileEntity, new EnergyBlockLink(validReceiver.direction));
                        if (!(validReceiver.tileEntity instanceof IQEConductor)) {
                            continue;
                        }
                        tileEntitiesToCheck.remove(validReceiver.tileEntity);
                        tileEntitiesToCheck.add(validReceiver.tileEntity);
                    }
                }
            }

        }
        final List<EnergyPath> energyPaths = new LinkedList<>();
        for (final Map.Entry<IQETile, EnergyBlockLink> entry : reachedTileEntities.entrySet()) {
            IQETile tileEntity = entry.getKey();
            if ((tileEntity instanceof IQESink)) {
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
                        if (!(tileEntity instanceof IQEConductor)) {
                            break;
                        }
                        final IQEConductor energyConductor = (IQEConductor) tileEntity;
                        energyPath.conductors.add(energyConductor);
                        energyBlockLink = reachedTileEntities.get(tileEntity);
                        if (energyBlockLink != null) {
                            continue;
                        }
                        IC2.platform.displayError("An energy network pathfinding entry is corrupted.\nThis could happen due to " +
                                "incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile " +
                                "entities below)\nE: " + emitter + " (" + te.getPos().getX() + "," + te.getPos().getY()  + "," + te.getPos().getZ()  + ")\n" + "C: " + tileEntity + " (" + te.getPos().getX() + "," + te.getPos().getY() + "," + te.getPos().getZ() + ")\n" + "R: " + energyPath.target + " (" +this.energyTileTileEntityMap.get(energyPath.target).getPos().getX() + "," + getTileFromIQE(energyPath.target).getPos().getY() + "," +getTileFromIQE( energyPath.target).getPos().getZ() + ")");
                    }
                }
                energyPaths.add(energyPath);
            }
        }
        return energyPaths;
    }


    public IQETile getNeighbor(final IQETile tile, final EnumFacing dir) {
        if (tile == null) {
            return null;
        }
        return  this.getTileEntity(this.energyTileTileEntityMap.get(tile).getPos().offset(dir));
    }
    private List<EnergyTarget> getValidReceivers(final IQETile emitter, final boolean reverse) {
        final List<EnergyTarget> validReceivers = new LinkedList<>();

        for (final EnumFacing direction : directions) {
            final IQETile target2 = getNeighbor(emitter, direction);
            if (target2 != null) {
                final EnumFacing inverseDirection2 = direction.getOpposite();
                if (reverse) {
                    if (emitter instanceof IQEAcceptor && target2 instanceof IQEEmitter) {
                        final IQEEmitter sender2 = (IQEEmitter) target2;
                        final IQEAcceptor receiver2 = (IQEAcceptor) emitter;
                        if (sender2.emitsQETo(receiver2, inverseDirection2) && receiver2.acceptsQEFrom(sender2,
                                direction)) {
                            validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                        }
                    }
                } else if (emitter instanceof IQEEmitter && target2 instanceof IQEAcceptor) {
                    final IQEEmitter sender2 = (IQEEmitter) emitter;
                    final IQEAcceptor receiver2 = (IQEAcceptor) target2;
                    if (sender2.emitsQETo(receiver2, direction) && receiver2.acceptsQEFrom(sender2, inverseDirection2)) {
                        validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                    }
                }
            }
        }

        //

        return validReceivers;
    }

    public List<IQESource> discoverFirstPathOrSources(final IQETile par1) {
        final Set<IQETile> reached = new HashSet<>();
        final List<IQESource> result = new ArrayList<>();
        final List<IQETile> workList = new ArrayList<>();
        workList.add(par1);
        while (workList.size() > 0) {
            final IQETile tile = workList.remove(0);
            final TileEntity te = this.energyTileTileEntityMap.get(tile);
            if (!te.isInvalid()) {
                final List<EnergyTarget> targets = this.getValidReceivers(tile, true);
                for (EnergyTarget energyTarget : targets) {
                    final IQETile target = energyTarget.tileEntity;
                    if (target != par1) {
                        if (!reached.contains(target)) {
                            reached.add(target);
                            if (target instanceof IQESource) {
                                result.add((IQESource) target);
                            } else if (target instanceof IQEConductor) {
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
        if(this.world.provider.getWorldTime() % 20 == 0)
        if (this.waitingList.hasWork()) {
            final List<IQETile> tiles = this.waitingList.getPathTiles();
            for (final IQETile tile : tiles) {
                final List<IQESource> sources = this.discoverFirstPathOrSources(tile);
                if (sources.size() > 0) {
                    this.energySourceToEnergyPathMap.removeAll(sources);
                }
            }
            this.waitingList.clear();
        }
        for (IQESource entry : this.sources) {
            if (entry != null) {


                double offer =entry.getOfferedQE();
                if (offer > 0) {
                    for (double packetAmount = 1, i = 0; i < packetAmount; ++i) {
                        offer =entry.getOfferedQE();
                        if (offer < 1) {
                            break;
                        }
                        final double removed = offer - this.emitEnergyFrom(entry, offer);
                        if (removed <= 0) {
                            break;
                        }

                        entry.drawQE(removed);
                    }
                }

            }
        }
    }










    public IQETile getTileEntity(BlockPos pos) {

        return this.chunkCoordinatesIQETileMap.get(pos);
    }
    public NodeQEStats getNodeStats(final IQETile tile) {
        final double emitted = this.getTotalEnergyEmitted(tile);
        final double received = this.getTotalEnergySunken(tile);
        return new NodeQEStats(received, emitted);
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
        this.chunkCoordinatesIQETileMap.clear();
        this.chunkCoordinatesMap.clear();
        this.energyTileList.clear();
        this.energyTileTileEntityMap.clear();
        this.listFromCoord.clear();
    }

    static {
        directions = EnumFacing.values();
    }



    static class EnergyTarget {
        final IQETile tileEntity;
        final EnumFacing direction;

        EnergyTarget(final IQETile tileEntity, final EnumFacing direction) {
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
        IQETile target;
        EnumFacing targetDirection;
        final Set<IQEConductor> conductors;
        long totalEnergyConducted;

        EnergyPath() {
            this.target = null;
            this.conductors = new HashSet<>();
            this.totalEnergyConducted = 0L;
        }
    }

    static class EnergyPathMap {
        final Map<IQESource, List<EnergyPath>> senderPath;

        EnergyPathMap() {
            this.senderPath = new HashMap<>();
        }

        public void put(final IQESource par1, final List<EnergyPath> par2) {
            this.senderPath.put(par1, par2);


        }

        public boolean containsKey(final IQESource par1) {
            return this.senderPath.containsKey(par1);
        }

        public List<EnergyPath> get(final IQESource par1) {
            return this.senderPath.get(par1);
        }

        public void remove(final IQESource par1) {
            this.senderPath.remove(par1);
        }

        public void removeAll(final List<IQESource> par1) {
            for (IQESource IQESource : par1) {
                this.remove(IQESource);
            }
        }

        public List<EnergyPath> getPaths(final IQEAcceptor par1) {
            final List<EnergyPath> paths = new ArrayList<>();
            for (final IQESource source : this.getSources(par1)) {
                if (this.containsKey(source)) {
                    paths.addAll(this.get(source));
                }
            }
            return paths;
        }

        public List<IQESource> getSources(final IQEAcceptor par1) {
            final List<IQESource> source = new ArrayList<>();
            for (final Map.Entry<IQESource,List<EnergyPath>> entry : this.senderPath.entrySet()) {
                if (source.contains(entry.getKey())) {
                    continue;
                }
                for(EnergyPath path : entry.getValue()) {
                    if ((!(par1 instanceof IQEConductor) || !path.conductors.contains(par1)) && (!(par1 instanceof IQESink) || path.target != par1)) {
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

        public void onTileEntityAdded(final List<EnergyTarget> around, final IQETile tile) {
            if (around.isEmpty() || this.paths.isEmpty()) {
                this.createNewPath(tile);
                return;
            }
            boolean found = false;
            final List<PathLogic> logics = new ArrayList<>();
            for (final PathLogic logic : this.paths) {
                if (logic.contains(tile)) {
                    found = true;
                    if (tile instanceof IQEConductor) {
                        logics.add(logic);
                    }
                } else {
                    for (final EnergyTarget target : around) {
                        if (logic.contains(target.tileEntity)) {
                            found = true;
                            logic.add(tile);
                            if (target.tileEntity instanceof IQEConductor) {
                                logics.add(logic);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            if (logics.size() > 1 && tile instanceof IQEConductor) {
                final PathLogic newLogic = new PathLogic();
                for (final PathLogic logic2 : logics) {
                    this.paths.remove(logic2);
                    for (final IQETile toMove : logic2.tiles) {
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

        public void onTileEntityRemoved(final IQETile par1) {
            if (this.paths.isEmpty()) {
                return;
            }
            final List<IQETile> toRecalculate = new ArrayList<>();
            for (int i = 0; i < this.paths.size(); ++i) {
                final PathLogic logic = this.paths.get(i);
                if (logic.contains(par1)) {
                    logic.remove(par1);
                    toRecalculate.addAll(logic.tiles);
                    this.paths.remove(i--);
                }
            }
            for (final IQETile tile : toRecalculate) {
                this.onTileEntityAdded(QENetLocal.this.getValidReceivers(tile, true), tile);
            }
        }

        public void createNewPath(final IQETile par1) {
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

        public List<IQETile> getPathTiles() {
            final List<IQETile> tiles = new ArrayList<>();
            for (PathLogic path : this.paths) {
                final IQETile tile = path.getRepresentingTile();
                if (tile != null) {
                    tiles.add(tile);
                }
            }
            return tiles;
        }
    }

    static class PathLogic {
        final List<IQETile> tiles;

        PathLogic() {
            this.tiles = new ArrayList<>();
        }

        public boolean contains(final IQETile par1) {
            return this.tiles.contains(par1);
        }

        public void add(final IQETile par1) {
            this.tiles.add(par1);
        }

        public void remove(final IQETile par1) {
            this.tiles.remove(par1);
        }

        public void clear() {
            this.tiles.clear();
        }

        public IQETile getRepresentingTile() {
            if (this.tiles.isEmpty()) {
                return null;
            }
            return this.tiles.get(0);
        }
    }
}
