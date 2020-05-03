package addsynth.energy.energy_network;

import addsynth.core.block_network.Node;
import addsynth.energy.Energy;
import addsynth.energy.tiles.IEnergyUser;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public final class EnergyNode extends Node {

  public final Energy energy;

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
  public <E extends TileEntity & IEnergyUser> EnergyNode(final BlockPos position, final E tileEntity){
    super(position, tileEntity);
    this.energy = tileEntity.getEnergy();
  }

  public <E extends TileEntity & IEnergyUser> EnergyNode(final BlockPos position, final E tileEntity, final Energy energy){
    super(position, tileEntity);
    this.energy = energy;
  }

  @Override
  public boolean isInvalid(){
    return (tile == null || position == null || energy == null) ? true : (tile.isRemoved() || !tile.getPos().equals(position));
  }

  @Override
  public String toString(){
    return "Node{TileEntity: "+(tile == null ? "null" : tile.getClass().getSimpleName())+", "+
                "Position: "+(position == null ? "null" : position.toString())+", "+
                (energy == null ? "Energy: null" : energy.toString())+"}";
  }

}
