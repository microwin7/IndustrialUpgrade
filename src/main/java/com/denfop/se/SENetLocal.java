package com.denfop.se;

import com.denfop.api.se.ISEAcceptor;
import com.denfop.api.se.ISEConductor;
import com.denfop.api.se.ISEEmitter;
import com.denfop.api.se.ISESink;
import com.denfop.api.se.ISESource;
import com.denfop.api.se.ISETile;
import com.denfop.api.se.NodeSEStats;
import ic2.api.energy.NodeStats;
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

public class SENetLocal {
    private static final EnumFacing[] directions;
    private final World world;
    private final EnergyPathMap energySourceToEnergyPathMap;

    private final Map<ISETile, BlockPos> chunkCoordinatesMap;
    private final Map<ISETile, TileEntity> energyTileTileEntityMap;
    private final Map<BlockPos, ISETile> chunkCoordinatesISETileMap;
    private final List<ISESource> sources;
    private final List<ISETile> energyTileList;
    private final WaitingList waitingList;
    private final List<BlockPos> listFromCoord;
    SENetLocal(final World world) {
        this.energySourceToEnergyPathMap = new EnergyPathMap();
        this.sources = new ArrayList<>();
        this.waitingList = new WaitingList();
        this.world = world;
        this.chunkCoordinatesISETileMap = new HashMap<>();
        this.chunkCoordinatesMap= new HashMap<>();
        this.energyTileList= new ArrayList<>();
        this.energyTileTileEntityMap = new HashMap<>();
        this.listFromCoord = new ArrayList<>();
    }

    public void addTile(ISETile tile1) {
            this.addTileEntity(getTileFromISE(tile1).getPos(), tile1);
            this.energyTileList.add(tile1);

    }

    public void addTileEntity(final BlockPos coords, final ISETile tile) {
        if (this.listFromCoord.contains(coords))
            return;

        TileEntity te = getTileFromISE(tile);
        this.listFromCoord.add(coords);
        this.energyTileTileEntityMap.put(tile,te);
        this.chunkCoordinatesMap.put(tile, coords);
        this.chunkCoordinatesISETileMap.put(coords, tile);
        this.update(coords.getX(), coords.getY(), coords.getZ());
        if (tile instanceof ISEAcceptor) {
            this.waitingList.onTileEntityAdded(this.getValidReceivers(tile, true), tile);
        }
        if (tile instanceof ISESource) {
            this.sources.add((ISESource) tile);
        }
    }
    public void removeTile(ISETile tile1) {
            this.removeTileEntity(tile1);
            this.energyTileList.remove(tile1);

    }
    public void removeTileEntity(ISETile tile) {
        if (!this.energyTileList.contains(tile) ) {
            return;
        }
        final BlockPos coord = this.chunkCoordinatesMap.get(tile);
        this.listFromCoord.remove(coord);
        this.chunkCoordinatesMap.remove(tile);
        this.energyTileTileEntityMap.remove(tile,this.energyTileTileEntityMap.get(tile));
        this.chunkCoordinatesISETileMap.remove(coord,tile);
        this.update(coord.getX(), coord.getY(), coord.getZ());
        if (tile instanceof ISEAcceptor) {
            this.energySourceToEnergyPathMap.removeAll(this.energySourceToEnergyPathMap.getSources((ISEAcceptor) tile));
            this.waitingList.onTileEntityRemoved(tile);
        }
        if (tile instanceof ISESource) {
            this.sources.remove((ISESource)tile);
            this.energySourceToEnergyPathMap.remove((ISESource)tile);
        }
    }

    public double emitEnergyFrom(final ISESource energySource, double amount) {
        if (!this.energySourceToEnergyPathMap.containsKey(energySource)) {
            this.energySourceToEnergyPathMap.put(energySource, this.discover(energySource));
        }
        List<EnergyPath> activeEnergyPaths = new Vector<>();
        for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.get(energySource)) {
            assert energyPath.target instanceof ISESink;
            final ISESink energySink = (ISESink) energyPath.target;
            if (energySink.getDemandedSE() <= 0.0) {
                continue;
            }


            activeEnergyPaths.add(energyPath);
        }


        while (!activeEnergyPaths.isEmpty() && amount > 0) {
            double energyConsumed = 0;

            final List<EnergyPath> currentActiveEnergyPaths = activeEnergyPaths;
            activeEnergyPaths = new Vector<>();
            for (final EnergyPath energyPath2 : currentActiveEnergyPaths) {
                final ISESink energySink2 = (ISESink) energyPath2.target;


                double adding = Math.min(amount, energySink2.getDemandedSE());

                if (adding <= 0.0) {
                    continue;
                }


                double energyReturned = energySink2.injectSE(energyPath2.targetDirection, adding, 0);
                if (energyReturned == 0.0) {
                    activeEnergyPaths.add(energyPath2);
                } else if (energyReturned >= amount) {
                    energyReturned = amount;
                }
                energyConsumed += (adding - energyReturned );

            }
            if (energyConsumed == 0 && !activeEnergyPaths.isEmpty()) {
                activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
            }

            amount -= energyConsumed;
        }
        return amount;
    }

    public double getTotalEnergyEmitted(final ISETile tileEntity) {
        double ret = 0.0;
        if (tileEntity instanceof ISEConductor) {
            for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((ISEAcceptor) tileEntity)) {
                if (energyPath.conductors.contains(tileEntity)) {
                    ret += energyPath.totalEnergyConducted;
                }
            }
        }
        if (tileEntity instanceof ISESource && this.energySourceToEnergyPathMap.containsKey((ISESource)tileEntity)) {
            for (final EnergyPath energyPath2 : this.energySourceToEnergyPathMap.get((ISESource)tileEntity)) {
                ret += energyPath2.totalEnergyConducted;
            }
        }
        return ret;
    }

    public double getTotalEnergySunken(final ISETile tileEntity) {
        double ret = 0.0;
        if (tileEntity instanceof ISEConductor || tileEntity instanceof ISESink) {
            for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((ISEAcceptor) tileEntity)) {
                if ((tileEntity instanceof ISESink && energyPath.target == tileEntity) || (tileEntity instanceof ISEConductor && energyPath.conductors.contains(tileEntity))) {
                    ret += energyPath.totalEnergyConducted;
                }
            }
        }
        return ret;
    }
    public TileEntity  getTileFromISE(ISETile tile){
        if(tile instanceof TileEntity)
            return (TileEntity) tile;
        if(tile instanceof ILocatable){
            return this.world.getTileEntity (((ILocatable)tile).getPosition());
        }

        return null;
    }
    private List<EnergyPath> discover(final ISESource emitter) {
        final Map<ISETile, EnergyBlockLink> reachedTileEntities = new HashMap<>();
        final LinkedList<ISETile> tileEntitiesToCheck = new LinkedList<>();
            tileEntitiesToCheck.add(emitter);


        while (!tileEntitiesToCheck.isEmpty()) {
            final ISETile currentTileEntity = tileEntitiesToCheck.remove();

            TileEntity tile =this.energyTileTileEntityMap.get(currentTileEntity);

            if (!tile.isInvalid()) {
                final List<EnergyTarget> validReceivers = this.getValidReceivers(currentTileEntity, false);
                for (final EnergyTarget validReceiver : validReceivers) {
                    if (validReceiver.tileEntity != emitter) {

                        if (reachedTileEntities.containsKey(validReceiver.tileEntity)) {
                            continue;
                        }
                        reachedTileEntities.put(validReceiver.tileEntity, new EnergyBlockLink(validReceiver.direction));
                        if (!(validReceiver.tileEntity instanceof ISEConductor)) {
                            continue;
                        }
                        tileEntitiesToCheck.remove(validReceiver.tileEntity);
                        tileEntitiesToCheck.add(validReceiver.tileEntity);
                    }
                }
            }

        }
        final List<EnergyPath> energyPaths = new LinkedList<>();
        for (final Map.Entry<ISETile, EnergyBlockLink> entry : reachedTileEntities.entrySet()) {
            ISETile tileEntity = entry.getKey();
            if ((tileEntity instanceof ISESink)) {
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
                        if (!(tileEntity instanceof ISEConductor)) {
                            break;
                        }
                        final ISEConductor energyConductor = (ISEConductor) tileEntity;
                        energyPath.conductors.add(energyConductor);
                        energyBlockLink = reachedTileEntities.get(tileEntity);
                        if (energyBlockLink != null) {
                            continue;
                        }
                        IC2.platform.displayError("An energy network pathfinding entry is corrupted.\nThis could happen due to " +
                                "incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile " +
                                "entities below)\nE: " + emitter + " (" + te.getPos().getX() + "," + te.getPos().getY()  + "," + te.getPos().getZ()  + ")\n" + "C: " + tileEntity + " (" + te.getPos().getX() + "," + te.getPos().getY() + "," + te.getPos().getZ() + ")\n" + "R: " + energyPath.target + " (" +this.energyTileTileEntityMap.get(energyPath.target).getPos().getX() + "," + getTileFromISE(energyPath.target).getPos().getY() + "," +getTileFromISE( energyPath.target).getPos().getZ() + ")");
                    }
                }
                energyPaths.add(energyPath);
            }
        }
        return energyPaths;
    }


    public ISETile getNeighbor(final ISETile tile, final EnumFacing dir) {
        if (tile == null) {
            return null;
        }
        return  this.getTileEntity(this.energyTileTileEntityMap.get(tile).getPos().offset(dir));
    }
    private List<EnergyTarget> getValidReceivers(final ISETile emitter, final boolean reverse) {
        final List<EnergyTarget> validReceivers = new LinkedList<>();

            for (final EnumFacing direction : directions) {
                final ISETile target2 = getNeighbor(emitter, direction);
                if (target2 != null) {
                    final EnumFacing inverseDirection2 = direction.getOpposite();
                    if (reverse) {
                        if (emitter instanceof ISEAcceptor && target2 instanceof ISEEmitter) {
                            final ISEEmitter sender2 = (ISEEmitter) target2;
                            final ISEAcceptor receiver2 = (ISEAcceptor) emitter;
                            if (sender2.emitsSETo(receiver2, inverseDirection2) && receiver2.acceptsSEFrom(sender2,
                                    direction)) {
                                validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                            }
                        }
                    } else if (emitter instanceof ISEEmitter && target2 instanceof ISEAcceptor) {
                        final ISEEmitter sender2 = (ISEEmitter) emitter;
                        final ISEAcceptor receiver2 = (ISEAcceptor) target2;
                        if (sender2.emitsSETo(receiver2, direction) && receiver2.acceptsSEFrom(sender2, inverseDirection2)) {
                            validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                        }
                    }
                }
            }

        //

        return validReceivers;
    }

    public List<ISESource> discoverFirstPathOrSources(final ISETile par1) {
        final Set<ISETile> reached = new HashSet<>();
        final List<ISESource> result = new ArrayList<>();
        final List<ISETile> workList = new ArrayList<>();
        workList.add(par1);
        while (workList.size() > 0) {
            final ISETile tile = workList.remove(0);
            final TileEntity te = this.energyTileTileEntityMap.get(tile);
            if (!te.isInvalid()) {
                final List<EnergyTarget> targets = this.getValidReceivers(tile, true);
                for (EnergyTarget energyTarget : targets) {
                    final ISETile target = energyTarget.tileEntity;
                    if (target != par1) {
                        if (!reached.contains(target)) {
                            reached.add(target);
                            if (target instanceof ISESource) {
                                result.add((ISESource) target);
                            } else if (target instanceof ISEConductor) {
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
            final List<ISETile> tiles = this.waitingList.getPathTiles();
            for (final ISETile tile : tiles) {
                final List<ISESource> sources = this.discoverFirstPathOrSources(tile);
                if (sources.size() > 0) {
                    this.energySourceToEnergyPathMap.removeAll(sources);
                }
            }
            this.waitingList.clear();
        }
        for (ISESource entry : this.sources) {
            if (entry != null) {


                double offer =entry.getOfferedSE();
                if (offer > 0) {
                    for (double packetAmount = 1, i = 0; i < packetAmount; ++i) {
                        offer =entry.getOfferedSE();
                        if (offer < 1) {
                            break;
                        }
                        final double removed = offer - this.emitEnergyFrom(entry, offer);
                        if (removed <= 0) {
                            break;
                        }

                        entry.drawSE(removed);
                    }
                }

            }
        }
    }










    public ISETile getTileEntity(BlockPos pos) {

        return this.chunkCoordinatesISETileMap.get(pos);
    }
    public NodeSEStats getNodeStats(final ISETile tile) {
        final double emitted = this.getTotalEnergyEmitted(tile);
        final double received = this.getTotalEnergySunken(tile);
        return new NodeSEStats(received, emitted);
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
        this.chunkCoordinatesISETileMap.clear();
        this.chunkCoordinatesMap.clear();
        this.energyTileList.clear();
        this.energyTileTileEntityMap.clear();
        this.listFromCoord.clear();
    }

    static {
        directions = EnumFacing.values();
    }



    static class EnergyTarget {
        final ISETile tileEntity;
        final EnumFacing direction;

        EnergyTarget(final ISETile tileEntity, final EnumFacing direction) {
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
        ISETile target;
        EnumFacing targetDirection;
        final Set<ISEConductor> conductors;
        long totalEnergyConducted;

        EnergyPath() {
            this.target = null;
            this.conductors = new HashSet<>();
            this.totalEnergyConducted = 0L;
        }
    }

    static class EnergyPathMap {
        final Map<ISESource, List<EnergyPath>> senderPath;

        EnergyPathMap() {
            this.senderPath = new HashMap<>();
        }

        public void put(final ISESource par1, final List<EnergyPath> par2) {
            this.senderPath.put(par1, par2);


        }

        public boolean containsKey(final ISESource par1) {
            return this.senderPath.containsKey(par1);
        }

        public List<EnergyPath> get(final ISESource par1) {
            return this.senderPath.get(par1);
        }

        public void remove(final ISESource par1) {
            this.senderPath.remove(par1);
        }

        public void removeAll(final List<ISESource> par1) {
            for (ISESource ISESource : par1) {
                this.remove(ISESource);
            }
        }

        public List<EnergyPath> getPaths(final ISEAcceptor par1) {
            final List<EnergyPath> paths = new ArrayList<>();
            for (final ISESource source : this.getSources(par1)) {
                if (this.containsKey(source)) {
                    paths.addAll(this.get(source));
                }
            }
            return paths;
        }

        public List<ISESource> getSources(final ISEAcceptor par1) {
            final List<ISESource> source = new ArrayList<>();
            for (final Map.Entry<ISESource,List<EnergyPath>> entry : this.senderPath.entrySet()) {
                if (source.contains(entry.getKey())) {
                    continue;
                }
                for(EnergyPath path : entry.getValue()) {
                    if ((!(par1 instanceof ISEConductor) || !path.conductors.contains(par1)) && (!(par1 instanceof ISESink) || path.target != par1)) {
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

        public void onTileEntityAdded(final List<EnergyTarget> around, final ISETile tile) {
            if (around.isEmpty() || this.paths.isEmpty()) {
                this.createNewPath(tile);
                return;
            }
            boolean found = false;
            final List<PathLogic> logics = new ArrayList<>();
            for (final PathLogic logic : this.paths) {
                if (logic.contains(tile)) {
                    found = true;
                    if (tile instanceof ISEConductor) {
                        logics.add(logic);
                    }
                } else {
                    for (final EnergyTarget target : around) {
                        if (logic.contains(target.tileEntity)) {
                            found = true;
                            logic.add(tile);
                            if (target.tileEntity instanceof ISEConductor) {
                                logics.add(logic);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            if (logics.size() > 1 && tile instanceof ISEConductor) {
                final PathLogic newLogic = new PathLogic();
                for (final PathLogic logic2 : logics) {
                    this.paths.remove(logic2);
                    for (final ISETile toMove : logic2.tiles) {
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

        public void onTileEntityRemoved(final ISETile par1) {
            if (this.paths.isEmpty()) {
                return;
            }
            final List<ISETile> toRecalculate = new ArrayList<>();
            for (int i = 0; i < this.paths.size(); ++i) {
                final PathLogic logic = this.paths.get(i);
                if (logic.contains(par1)) {
                    logic.remove(par1);
                    toRecalculate.addAll(logic.tiles);
                    this.paths.remove(i--);
                }
            }
            for (final ISETile tile : toRecalculate) {
                this.onTileEntityAdded(SENetLocal.this.getValidReceivers(tile, true), tile);
            }
        }

        public void createNewPath(final ISETile par1) {
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

        public List<ISETile> getPathTiles() {
            final List<ISETile> tiles = new ArrayList<>();
            for (PathLogic path : this.paths) {
                final ISETile tile = path.getRepresentingTile();
                if (tile != null) {
                    tiles.add(tile);
                }
            }
            return tiles;
        }
    }

    static class PathLogic {
        final List<ISETile> tiles;

        PathLogic() {
            this.tiles = new ArrayList<>();
        }

        public boolean contains(final ISETile par1) {
            return this.tiles.contains(par1);
        }

        public void add(final ISETile par1) {
            this.tiles.add(par1);
        }

        public void remove(final ISETile par1) {
            this.tiles.remove(par1);
        }

        public void clear() {
            this.tiles.clear();
        }

        public ISETile getRepresentingTile() {
            if (this.tiles.isEmpty()) {
                return null;
            }
            return this.tiles.get(0);
        }
    }
}
