package addsynth.energy.energy_network;

import addsynth.core.block_network.Node;
import addsynth.energy.main.Energy;
import addsynth.energy.main.IEnergyUser;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public final class EnergyNode extends Node {

  public final Energy energy;

  /**
   * @param position
   * @param tileEntity
   */
  // MAYBE: move type variable to class header, but this causes a type safety warning in Energy Network.
  public <E extends TileEntity & IEnergyUser> EnergyNode(final BlockPos position, final E tileEntity){
    super(position, tileEntity);
    this.energy = tileEntity.getEnergy();
  }

  public EnergyNode(final BlockPos position, final TileEntity tileEntity, final Energy energy){
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
