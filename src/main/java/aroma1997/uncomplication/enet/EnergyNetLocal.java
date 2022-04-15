package aroma1997.uncomplication.enet;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.damagesource.IUDamageSource;
import com.denfop.item.armour.ItemArmorAdvHazmat;
import com.denfop.item.armour.ItemArmorImprovemedNano;
import com.denfop.item.armour.ItemArmorImprovemedQuantum;
import com.denfop.tiles.base.TileEntityCable;
import ic2.api.Direction;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.*;
import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.block.wiring.TileEntityTransformer;
import ic2.core.item.armor.ItemArmorHazmat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public class EnergyNetLocal {
    public static double minConductionLoss;
    private static Direction[] directions;
    private final World world;
    private final EnergyPathMap energySourceToEnergyPathMap;
    private final Map<ChunkCoordinates, IEnergyTile> registeredTiles;
    private final  List<IEnergyTile> energyTileList;
    private final Map<ChunkCoordinates, IEnergySource> sources;
    private final WaitingList waitingList;

    EnergyNetLocal(final World world) {
        this.energySourceToEnergyPathMap = new EnergyPathMap();
        this.registeredTiles = new HashMap<>();
        this.sources = new HashMap<>();
        this.waitingList = new WaitingList();
        this.world = world;
        this.energyTileList= new ArrayList<>();
    }

    public void addTile(final TileEntity par1) {
        if (par1 instanceof IMetaDelegate) {
            final List<TileEntity> tiles = ((IMetaDelegate) par1).getSubTiles();
            for (final TileEntity tile : tiles) {
                this.addTileEntity(coords(tile), par1);
            }
            if (par1 instanceof IEnergySource) {
                this.sources.put(coords(tiles.get(0)), (IEnergySource) par1);
            }
        } else {
            this.addTileEntity(coords(par1), par1);
        }
    }

    public void addTileEntity(final ChunkCoordinates coords, final TileEntity tile) {
        if (!(tile instanceof IEnergyTile) || this.registeredTiles.containsKey(coords)) {
            return;
        }
        this.registeredTiles.put(coords, (IEnergyTile) tile);
        this.update(coords.posX, coords.posY, coords.posZ);
        if (tile instanceof IEnergyAcceptor) {
            this.waitingList.onTileEntityAdded(this.getValidReceivers((IEnergyTile) tile, true), tile);
        }
        if (tile instanceof IEnergySource && !(tile instanceof IMetaDelegate)) {
            this.sources.put(coords, (IEnergySource) tile);
        }
    }

    public void removeTile(final TileEntity par1) {
        if (par1 instanceof IMetaDelegate) {
            final List<TileEntity> tiles = ((IMetaDelegate) par1).getSubTiles();
            for (final TileEntity tile : tiles) {
                this.removeTileEntity(coords(tile), par1);
            }
        } else {
            this.removeTileEntity(coords(par1), par1);
        }
    }

    public void removeTileEntity(final ChunkCoordinates coords, final TileEntity tile) {
        if (!(tile instanceof IEnergyTile) || !this.registeredTiles.containsKey(coords)) {

            return;
        }
        this.registeredTiles.remove(coords);
        this.update(coords.posX, coords.posY, coords.posZ);
        if (tile instanceof IEnergyAcceptor) {
            this.energySourceToEnergyPathMap.removeAll(this.energySourceToEnergyPathMap.getSources((IEnergyAcceptor) tile));
            this.waitingList.onTileEntityRemoved(tile);
        }
        if (tile instanceof IEnergySource) {
            this.sources.remove(coords);
            this.energySourceToEnergyPathMap.remove(tile);
        }
    }

    public double emitEnergyFrom(final ChunkCoordinates coords, final IEnergySource energySource, double amount) {
        if (!this.registeredTiles.containsKey(coords)) {
            return amount;
        }
        if (!this.energySourceToEnergyPathMap.containsKey(energySource)) {
            final EnergyPathMap energySourceToEnergyPathMap = this.energySourceToEnergyPathMap;
            final TileEntity emitter = (TileEntity) energySource;
            energySourceToEnergyPathMap.put(energySource, this.discover(emitter,  energySource.getOfferedEnergy()));
        }
        List<EnergyPath> activeEnergyPaths = new Vector<>();
        double totalInvLoss = 0.0;
        final double source = energySource.getSourceTier();
        for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.get(energySource)) {
            assert energyPath.target instanceof IEnergySink;
            final IEnergySink energySink = (IEnergySink) energyPath.target;
            if (energySink.getDemandedEnergy() <= 0.0) {
                continue;
            }
            if (energyPath.loss >= amount) {
                continue;
            }
            if (Config.enableIC2EasyMode && this.conductorToWeak(energyPath.conductors, amount)) {
                continue;
            }
            totalInvLoss += 1.0 / energyPath.loss;
            activeEnergyPaths.add(energyPath);
        }
        Collections.shuffle(activeEnergyPaths);
        for (double i = activeEnergyPaths.size() - amount; i > 0; --i) {
            final EnergyPath removedEnergyPath = activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
            totalInvLoss -= 1.0 / removedEnergyPath.loss;
        }
        final Map<EnergyPath, Double> suppliedEnergyPaths = new HashMap<>();
        while (!activeEnergyPaths.isEmpty() && amount > 0) {
            double energyConsumed = 0;
            double newTotalInvLoss = 0.0;
            final List<EnergyPath> currentActiveEnergyPaths = activeEnergyPaths;
            activeEnergyPaths = new Vector<>();
            for (final EnergyPath energyPath2 : currentActiveEnergyPaths) {
                final IEnergySink energySink2 = (IEnergySink) energyPath2.target;
                final double energyProvided = Math.floor(Math.round(amount / totalInvLoss / energyPath2.loss * 100000.0) / 100000.0);
                final double energyLoss = Math.floor(energyPath2.loss);
                if (energyProvided > energyLoss) {
                    final double providing = energyProvided - energyLoss;
                    double adding = Math.min(providing, energySink2.getDemandedEnergy());

                    if (adding <= 0.0) {
                        continue;
                    }
                    final int tier = energySink2.getSinkTier();
                    double accepting = EnergyNet.instance.getPowerFromTier(tier);
                    if (tier >= Integer.MAX_VALUE || accepting <= 0) {
                        accepting = Integer.MAX_VALUE;
                    }
                    if (providing > accepting) {
                        if (Config.enableIC2EasyMode) {
                            continue;
                        }
                        this.explodeTiles(energySink2);
                    } else {
                        double energyReturned = energySink2.injectEnergy(energyPath2.targetDirection.toForgeDirection(), adding, source);
                        if (energyReturned == 0.0) {
                            activeEnergyPaths.add(energyPath2);
                            newTotalInvLoss += 1.0 / energyPath2.loss;
                        } else if (energyReturned >= energyProvided - energyLoss) {
                            energyReturned = energyProvided - energyLoss;
                        }
                        energyConsumed += (adding - energyReturned + energyLoss);
                        final double energyInjected = (adding - energyReturned);
                        if (!suppliedEnergyPaths.containsKey(energyPath2)) {
                            suppliedEnergyPaths.put(energyPath2, energyInjected);
                        } else {
                            suppliedEnergyPaths.put(energyPath2, energyInjected + suppliedEnergyPaths.get(energyPath2));
                        }
                    }
                } else {
                    activeEnergyPaths.add(energyPath2);
                    newTotalInvLoss += 1.0 / energyPath2.loss;
                }
            }
            if (energyConsumed == 0 && !activeEnergyPaths.isEmpty()) {
                final EnergyPath removedEnergyPath2 = activeEnergyPaths.remove(activeEnergyPaths.size() - 1);
                newTotalInvLoss -= 1.0 / removedEnergyPath2.loss;
            }
            totalInvLoss = newTotalInvLoss;
            amount -= energyConsumed;
        }
        for (final Map.Entry<EnergyPath, Double> entry : suppliedEnergyPaths.entrySet()) {
            final EnergyPath energyPath3 = entry.getKey();
            final double energyInjected2 = entry.getValue();
            final EnergyPath energyPath5;
            final EnergyPath energyPath4 = energyPath5 = energyPath3;
            energyPath5.totalEnergyConducted += energyInjected2;
            energyPath4.maxSendedEnergy = (long) Math.max(energyPath4.maxSendedEnergy, energyInjected2);
            if (energyInjected2 > energyPath3.minInsulationEnergyAbsorption) {
                if (energyInjected2 >= energyPath3.minInsulationBreakdownEnergy) {
                    for (final IEnergyConductor energyConductor2 : energyPath3.conductors) {
                        if (energyInjected2 >= energyConductor2.getInsulationBreakdownEnergy()) {
                            energyConductor2.removeInsulation();
                            if (energyConductor2.getInsulationEnergyAbsorption() >= energyPath3.minInsulationEnergyAbsorption) {
                                continue;
                            }
                            energyPath3.minInsulationEnergyAbsorption = (int) energyConductor2.getInsulationEnergyAbsorption();
                        }
                    }
                }
            }
            if (energyInjected2 >= energyPath3.minConductorBreakdownEnergy) {
                for (final IEnergyConductor energyConductor3 : energyPath3.conductors) {
                    if (energyInjected2 >= energyConductor3.getConductorBreakdownEnergy() && !Config.enableIC2EasyMode) {
                        energyConductor3.removeConductor();
                    }
                }
            }
        }
        return amount;
    }

    public double getTotalEnergyEmitted(final TileEntity tileEntity) {
        double ret = 0.0;
        if (tileEntity instanceof IEnergyConductor) {
            for (final EnergyPath energyPath : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor) tileEntity)) {
                if (tileEntity instanceof IEnergyConductor && energyPath.conductors.contains(tileEntity)) {
                    ret += energyPath.totalEnergyConducted;
                }
            }
        }
        if (tileEntity instanceof IEnergySource && this.energySourceToEnergyPathMap.containsKey(tileEntity)) {
            for (final EnergyPath energyPath2 : this.energySourceToEnergyPathMap.get(tileEntity)) {
                ret += energyPath2.totalEnergyConducted;
            }
        }
        return ret;
    }

    public double getTotalEnergySunken(final TileEntity tileEntity) {
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

    private List<EnergyPath> discover(final TileEntity emitter, final double lossLimit) {
        final Map<TileEntity, EnergyBlockLink> reachedTileEntities = new HashMap<>();
        final LinkedList<TileEntity> tileEntitiesToCheck = new LinkedList<>();
        tileEntitiesToCheck.add(emitter);
        while (!tileEntitiesToCheck.isEmpty()) {
            final TileEntity currentTileEntity = tileEntitiesToCheck.remove();
            if (!currentTileEntity.isInvalid()) {
                double currentLoss = 0.0;
                if (this.registeredTiles.get(coords(currentTileEntity)) != null && this.registeredTiles.get(coords(currentTileEntity)) != emitter && reachedTileEntities.containsKey(currentTileEntity)) {
                    currentLoss = reachedTileEntities.get(currentTileEntity).loss;
                }
                final List<EnergyTarget> validReceivers = this.getValidReceivers((IEnergyTile) currentTileEntity, false);
                for (final EnergyTarget validReceiver : validReceivers) {
                    if (validReceiver.tileEntity != emitter) {
                        double additionalLoss = 0.0;
                        if (validReceiver.tileEntity instanceof IEnergyConductor) {
                            additionalLoss = ((IEnergyConductor) validReceiver.tileEntity).getConductionLoss();
                            if (additionalLoss < 1.0E-4) {
                                additionalLoss = 1.0E-4;
                            }
                            if (currentLoss + additionalLoss >= lossLimit) {
                                continue;
                            }
                        }
                        if (reachedTileEntities.containsKey(validReceiver.tileEntity) && reachedTileEntities.get(validReceiver.tileEntity).loss <= currentLoss + additionalLoss) {
                            continue;
                        }
                        reachedTileEntities.put(validReceiver.tileEntity, new EnergyBlockLink(validReceiver.direction, currentLoss + additionalLoss));
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
        for (final Map.Entry<TileEntity, EnergyBlockLink> entry : reachedTileEntities.entrySet()) {
            TileEntity tileEntity = entry.getKey();
            if ((tileEntity instanceof IEnergySink)) {
                EnergyBlockLink energyBlockLink = entry.getValue();
                final EnergyPath energyPath = new EnergyPath();
                energyPath.loss = Math.max(energyBlockLink.loss, 0.1);
                energyPath.target = tileEntity;
                energyPath.targetDirection = energyBlockLink.direction;
                if (emitter instanceof IEnergySource) {
                    while (true) {
                        tileEntity = EnergyNet.instance.getNeighbor(tileEntity, energyBlockLink.direction.toForgeDirection());
                        if (tileEntity == emitter) {
                            break;
                        }
                        if (!(tileEntity instanceof IEnergyConductor)) {
                            break;
                        }
                        final IEnergyConductor energyConductor = (IEnergyConductor) tileEntity;
                        energyPath.conductors.add(energyConductor);
                        if (energyConductor.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption) {
                            energyPath.minInsulationEnergyAbsorption = (int) energyConductor.getInsulationEnergyAbsorption();
                        }
                        if (energyConductor.getInsulationBreakdownEnergy() < energyPath.minInsulationBreakdownEnergy) {
                            energyPath.minInsulationBreakdownEnergy = (int) energyConductor.getInsulationBreakdownEnergy();
                        }
                        if (energyConductor.getConductorBreakdownEnergy() < energyPath.minConductorBreakdownEnergy) {
                            energyPath.minConductorBreakdownEnergy = (int) energyConductor.getConductorBreakdownEnergy();
                        }
                        energyBlockLink = reachedTileEntities.get(tileEntity);
                        if (energyBlockLink != null) {
                            continue;
                        }
                        IC2.platform.displayError("An energy network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile entities below)\nE: " + emitter + " (" + emitter.xCoord + "," + emitter.yCoord + "," + emitter.zCoord + ")\n" + "C: " + tileEntity + " (" + tileEntity.xCoord + "," + tileEntity.yCoord + "," + tileEntity.zCoord + ")\n" + "R: " + energyPath.target + " (" + energyPath.target.xCoord + "," + energyPath.target.yCoord + "," + energyPath.target.zCoord + ")");
                    }
                }
                energyPaths.add(energyPath);
            }
        }
        return energyPaths;
    }

    private boolean conductorToWeak(final Set<IEnergyConductor> par1, final double energyToSend) {
        boolean flag = false;
        for (final IEnergyConductor cond : par1) {
            if (cond.getConductorBreakdownEnergy() <= energyToSend) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private List<EnergyTarget> getValidReceivers(final IEnergyTile emitter, final boolean reverse) {
        final List<EnergyTarget> validReceivers = new LinkedList<>();
        for (final Direction direction : EnergyNetLocal.directions) {
            if (emitter instanceof IMetaDelegate) {
                final IMetaDelegate meta = (IMetaDelegate) emitter;
                final List<TileEntity> targets = meta.getSubTiles();
                for (final TileEntity tile : targets) {
                    final TileEntity target = EnergyNet.instance.getNeighbor(tile, direction.toForgeDirection());
                    if (target == emitter) {
                        continue;
                    }
                    if (!(target instanceof IEnergyTile) || !this.registeredTiles.containsKey(coords(target))) {
                        continue;
                    }
                    final Direction inverseDirection = direction.getInverse();
                    if (reverse) {
                        if (!(emitter instanceof IEnergyAcceptor) || !(target instanceof IEnergyEmitter)) {
                            continue;
                        }
                        final IEnergyEmitter sender = (IEnergyEmitter) target;
                        final IEnergyAcceptor receiver = (IEnergyAcceptor) emitter;
                        if (!sender.emitsEnergyTo((TileEntity) emitter, inverseDirection.toForgeDirection()) || !receiver.acceptsEnergyFrom(target, direction.toForgeDirection())) {
                            continue;
                        }
                    } else {
                        if (!(emitter instanceof IEnergyEmitter) || !(target instanceof IEnergyAcceptor)) {
                            continue;
                        }
                        final IEnergyEmitter sender = (IEnergyEmitter) emitter;
                        final IEnergyAcceptor receiver = (IEnergyAcceptor) target;
                        if (!sender.emitsEnergyTo(target, direction.toForgeDirection()) || !receiver.acceptsEnergyFrom((TileEntity) emitter, inverseDirection.toForgeDirection())) {
                            continue;
                        }
                    }
                    validReceivers.add(new EnergyTarget(target, inverseDirection));
                }
            } else {
                final TileEntity target2 = EnergyNet.instance.getNeighbor((TileEntity) emitter, direction.toForgeDirection());
                if (target2 instanceof IEnergyTile && this.registeredTiles.containsKey(coords(target2))) {
                    final Direction inverseDirection2 = direction.getInverse();
                    if (reverse) {
                        if (emitter instanceof IEnergyAcceptor && target2 instanceof IEnergyEmitter) {
                            final IEnergyEmitter sender2 = (IEnergyEmitter) target2;
                            final IEnergyAcceptor receiver2 = (IEnergyAcceptor) emitter;
                            if (sender2.emitsEnergyTo((TileEntity) emitter, inverseDirection2.toForgeDirection()) && receiver2.acceptsEnergyFrom(target2, direction.toForgeDirection())) {
                                validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                            }
                        }
                    } else if (emitter instanceof IEnergyEmitter && target2 instanceof IEnergyAcceptor) {
                        final IEnergyEmitter sender2 = (IEnergyEmitter) emitter;
                        final IEnergyAcceptor receiver2 = (IEnergyAcceptor) target2;
                        if (sender2.emitsEnergyTo(target2, direction.toForgeDirection()) && receiver2.acceptsEnergyFrom((TileEntity) emitter, inverseDirection2.toForgeDirection())) {
                            validReceivers.add(new EnergyTarget(target2, inverseDirection2));
                        }
                    }
                }
            }
        }
        return validReceivers;
    }

    public List<IEnergySource> discoverFirstPathOrSources(final TileEntity par1) {
        final Set<TileEntity> reached = new HashSet<>();
        final List<IEnergySource> result = new ArrayList<>();
        final List<TileEntity> workList = new ArrayList<>();
        workList.add(par1);
        while (workList.size() > 0) {
            final TileEntity tile = workList.remove(0);
            if (!tile.isInvalid()) {
                final List<EnergyTarget> targets = this.getValidReceivers((IEnergyTile) tile, true);
                for (EnergyTarget energyTarget : targets) {
                    final TileEntity target = energyTarget.tileEntity;
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

    public static ChunkCoordinates coords(final TileEntity par1) {
        if (par1 == null) {
            return null;
        }
        return new ChunkCoordinates(par1.xCoord, par1.yCoord, par1.zCoord);
    }

    public void onTickStart() {
        
    }

    public void onTickEnd() {
        if (this.waitingList.hasWork()) {
            final List<TileEntity> tiles = this.waitingList.getPathTiles();
            for (final TileEntity tile : tiles) {
                final List<IEnergySource> sources = this.discoverFirstPathOrSources(tile);
                if (sources.size() > 0) {
                    this.energySourceToEnergyPathMap.removeAll(sources);
                }
            }
            this.waitingList.clear();
        }
        for (Map.Entry<ChunkCoordinates, IEnergySource> entry : new HashMap<>(this.sources).entrySet()) {
            if (entry != null) {
                final IEnergySource source = entry.getValue();
                if (source != null) {
                    if (this.energySourceToEnergyPathMap.containsKey(source)) {
                        for (final EnergyPath path : this.energySourceToEnergyPathMap.get(source)) {
                            path.totalEnergyConducted = 0L;
                            path.maxSendedEnergy = 0L;
                        }
                    }
                    double offer = source.getOfferedEnergy();
                    if (offer > 0) {
                        for (double packetAmount = this.getPacketAmount(source), i = 0; i < packetAmount; ++i) {
                            offer = source.getOfferedEnergy();
                            if (offer < 1) {
                                break;
                            }
                            final double removed = offer - this.emitEnergyFrom(entry.getKey(), source, offer);
                            if (removed <= 0) {
                                break;
                            }
                            source.drawEnergy(removed);
                        }
                    }
                }
            }
        }
    }

    private double getPacketAmount(final IEnergySource source) {
        if (source instanceof IMultiEnergySource && ((IMultiEnergySource) source).sendMultibleEnergyPackets()) {
            return ((IMultiEnergySource) source).getMultibleEnergyPacketAmount();
        }
        if (source instanceof TileEntityTransformer) {
            return ((TileEntityTransformer) source).redstone ? 1 : 4;
        }
        return 1;
    }

    public void explodeTiles(final IEnergySink sink) {
        this.removeTile((TileEntity) sink);
        if (sink instanceof IMetaDelegate) {
            final IMetaDelegate meta = (IMetaDelegate) sink;
            for (final TileEntity tile : meta.getSubTiles()) {
                this.explodeMachineAt(tile.xCoord, tile.yCoord, tile.zCoord, tile);
            }
        } else {
            this.explodeMachineAt(((TileEntity) sink).xCoord, ((TileEntity) sink).yCoord, ((TileEntity) sink).zCoord, (TileEntity) sink);
        }
    }

    public TileEntity getTileEntity(final int x, final int y, final int z) {
        final ChunkCoordinates coords = new ChunkCoordinates(x, y, z);
        if (this.registeredTiles.containsKey(coords)) {
            return (TileEntity) this.registeredTiles.get(coords);
        }
        return null;
    }

    public NodeStats getNodeStats(final TileEntity tile) {
        final double emitted = this.getTotalEnergyEmitted(tile);
        final double received = this.getTotalEnergySunken(tile);
        return new NodeStats(received, emitted, EnergyNet.instance.getTierFromPower(this.getVoltage(tile)));
    }

    private double getVoltage(final TileEntity tileEntity) {
        double voltage = 0.0;
        if (tileEntity instanceof IEnergySource && this.energySourceToEnergyPathMap.containsKey(tileEntity)) {
            for (final EnergyPath energyPath2 : this.energySourceToEnergyPathMap.get(tileEntity)) {
                voltage = Math.max(voltage, (double) energyPath2.maxSendedEnergy);
            }
        }
        if (tileEntity instanceof IEnergyConductor || tileEntity instanceof IEnergySink) {
            for (final EnergyPath energyPath3 : this.energySourceToEnergyPathMap.getPaths((IEnergyAcceptor) tileEntity)) {
                if ((tileEntity instanceof IEnergySink && energyPath3.target == tileEntity) || (tileEntity instanceof IEnergyConductor && energyPath3.conductors.contains(tileEntity))) {
                    voltage = Math.max(voltage, (double) energyPath3.maxSendedEnergy);
                }
            }
        }
        return voltage;
    }

    void explodeMachineAt(final int x, final int y, final int z, final TileEntity tile) {
        if (Config.enableexlposion) {
            this.world.setBlockToAir(x, y, z);

            float power = 2.5f;
            if (tile instanceof TileEntityMatter) {
                power = 15.0f;
            }

            final ExplosionIC2 explosion = new ExplosionIC2(this.world, null, 0.5 + x, 0.5 + y, 0.5 + z, power, 0.75f);
            explosion.doExplosion();
        } else if (this.world.getBlock(x, y + 1, z) == null || this.world.getTileEntity(x, y + 1, z) == null)
            this.world.setBlock(x, y + 1, z, IUItem.errorblock);
    }

    void update(final int x, final int y, final int z) {
        for (final ForgeDirection dir : ForgeDirection.values()) {
            if (this.world.blockExists(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
                this.world.notifyBlockOfNeighborChange(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, Blocks.air);
            }
        }
    }

    public void onUnload() {
        this.energySourceToEnergyPathMap.clear();
        this.registeredTiles.clear();
        this.sources.clear();
        this.waitingList.clear();
    }

    static {
        EnergyNetLocal.minConductionLoss = 1.0E-4;
        EnergyNetLocal.directions = Direction.values();
    }

    static class EnergyTarget {
        final TileEntity tileEntity;
        final Direction direction;

        EnergyTarget(final TileEntity tileEntity, final Direction direction) {
            this.tileEntity = tileEntity;
            this.direction = direction;
        }
    }

    static class EnergyBlockLink {
        final Direction direction;
        final double loss;

        EnergyBlockLink(final Direction direction, final double loss) {
            this.direction = direction;
            this.loss = loss;
        }
    }

    static class EnergyPath {
        TileEntity target;
        Direction targetDirection;
        final Set<IEnergyConductor> conductors;
        double loss;
        double minInsulationEnergyAbsorption;
        double minInsulationBreakdownEnergy;
        double minConductorBreakdownEnergy;
        long totalEnergyConducted;
        long maxSendedEnergy;

        EnergyPath() {
            this.target = null;
            this.conductors = new HashSet<>();

            this.loss = 0.0;
            this.minInsulationEnergyAbsorption = Integer.MAX_VALUE;
            this.minInsulationBreakdownEnergy = Integer.MAX_VALUE;
            this.minConductorBreakdownEnergy = Integer.MAX_VALUE;
            this.totalEnergyConducted = 0L;
            this.maxSendedEnergy = 0L;
        }
    }

    static class EnergyPathMap {
        final Map<IEnergySource, List<EnergyPath>> senderPath;
        final Map<EnergyPath, IEnergySource> pathToSender;

        EnergyPathMap() {
            this.senderPath = new HashMap<>();
            this.pathToSender = new HashMap<>();
        }

        public void put(final IEnergySource par1, final List<EnergyPath> par2) {
            this.senderPath.put(par1, par2);
            for (EnergyPath energyPath : par2) {
                this.pathToSender.put(energyPath, par1);
            }
        }

        public boolean containsKey(final Object par1) {
            return this.senderPath.containsKey(par1);
        }

        public List<EnergyPath> get(final Object par1) {
            return this.senderPath.get(par1);
        }

        public void remove(final Object par1) {
            final List<EnergyPath> paths = this.senderPath.remove(par1);
            if (paths != null) {
                for (EnergyPath path : paths) {
                    this.pathToSender.remove(path);
                }
            }
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
            for (final Map.Entry<EnergyPath, IEnergySource> entry : this.pathToSender.entrySet()) {
                if (source.contains(entry.getValue())) {
                    continue;
                }
                final EnergyPath path = entry.getKey();
                if ((!(par1 instanceof IEnergyConductor) || !path.conductors.contains(par1)) && (!(par1 instanceof IEnergySink) || path.target != par1)) {
                    continue;
                }
                source.add(entry.getValue());
            }
            return source;
        }

        public void clear() {
            this.senderPath.clear();
            this.pathToSender.clear();
        }
    }

    class WaitingList {
        final List<PathLogic> paths;

        WaitingList() {
            this.paths = new ArrayList<>();
        }

        public void onTileEntityAdded(final List<EnergyTarget> around, final TileEntity tile) {
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
                    for (final TileEntity toMove : logic2.tiles) {
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

        public void onTileEntityRemoved(final TileEntity par1) {
            if (this.paths.isEmpty()) {
                return;
            }
            final List<TileEntity> toRecalculate = new ArrayList<>();
            for (int i = 0; i < this.paths.size(); ++i) {
                final PathLogic logic = this.paths.get(i);
                if (logic.contains(par1)) {
                    logic.remove(par1);
                    toRecalculate.addAll(logic.tiles);
                    this.paths.remove(i--);
                }
            }
            for (final TileEntity tile : toRecalculate) {
                this.onTileEntityAdded(EnergyNetLocal.this.getValidReceivers((IEnergyTile) tile, true), tile);
            }
        }

        public void createNewPath(final TileEntity par1) {
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

        public List<TileEntity> getPathTiles() {
            final List<TileEntity> tiles = new ArrayList<>();
            for (PathLogic path : this.paths) {
                final TileEntity tile = path.getRepresentingTile();
                if (tile != null) {
                    tiles.add(tile);
                }
            }
            return tiles;
        }
    }

    static class PathLogic {
        final List<TileEntity> tiles;

        PathLogic() {
            this.tiles = new ArrayList<>();
        }

        public boolean contains(final TileEntity par1) {
            return this.tiles.contains(par1);
        }

        public void add(final TileEntity par1) {
            this.tiles.add(par1);
        }

        public void remove(final TileEntity par1) {
            this.tiles.remove(par1);
        }

        public void clear() {
            this.tiles.clear();
        }

        public TileEntity getRepresentingTile() {
            if (this.tiles.isEmpty()) {
                return null;
            }
            return this.tiles.get(0);
        }
    }
}
