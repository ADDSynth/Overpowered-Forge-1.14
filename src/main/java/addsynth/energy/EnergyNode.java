package addsynth.energy;

import addsynth.core.block_network.Node;
import addsynth.energy.tiles.TileEnergyWithStorage;
import net.minecraft.util.math.BlockPos;

public final class EnergyNode extends Node {

  public final CustomEnergyStorage energy;

  /** I jused saved all important data in the EnergyNetwork.receivers List, because the various
   *  machines work with different data.
   *  For instance, the Energy Network checks for TileEntities that can hold Energy.
   *  The TileEnergyTransmitters check what kind of Receiver is receiving the energy, because the
   *    type of receiver determines how much energy they get.
   *  Laser Housings all function as a single Laser Network. They are different TileEntities but
   *    they refer to the same CustomEnergyStorage object.
   * @param position
   * @param tileEntity
   */
  public EnergyNode(final BlockPos position, final TileEnergyWithStorage tileEntity){
    super(position, tileEntity);
    this.energy = tileEntity.getEnergy();
  }

  public EnergyNode(final BlockPos position, final TileEnergyWithStorage tileEntity, final CustomEnergyStorage energy){
    super(position, tileEntity);
    this.energy = energy;
  }

  @Override
  public boolean isInvalid(){
    return (tile == null || position == null || energy == null) ? true : (tile.isInvalid() || !tile.getPos().equals(position));
  }

  @Override
  public final TileEnergyWithStorage getTile(){
    return (TileEnergyWithStorage)tile;
  }

  @Override
  public String toString(){
    return "Node{TileEntity: "+(tile == null ? "null" : tile.getClass().getSimpleName())+", "+
                "Position: "+(position == null ? "null" : position.toString())+", "+
                (energy == null ? "Energy: null" : energy.toString())+"}";
  }

}
