package addsynth.energy.lib.energy_network;

import javax.annotation.Nonnull;
import addsynth.core.block_network.Node;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.IEnergyUser;
import net.minecraft.tileentity.TileEntity;

public final class EnergyNode extends Node {

  public final Energy energy;

  public <E extends TileEntity & IEnergyUser> EnergyNode(@Nonnull final E tileEntity){
    super(tileEntity.getPos(), tileEntity.getBlockState().getBlock(), tileEntity);
    this.energy = tileEntity.getEnergy();
  }

  public EnergyNode(@Nonnull final TileEntity tileEntity, @Nonnull final Energy energy){
    super(tileEntity.getPos(), tileEntity.getBlockState().getBlock(), tileEntity);
    this.energy = energy;
  }

  @Override
  public boolean isInvalid(){
    if(block == null || position == null || tile == null || energy == null){
      return true;
    }
    return tile.isRemoved() || !tile.getPos().equals(position);
  }

  @Override
  public String toString(){
    return "Node{TileEntity: "+(tile == null ? "null" : tile.getClass().getSimpleName())+", "+
                "Position: "+(position == null ? "null" : position.toString())+", "+
                (energy == null ? "Energy: null" : energy.toString())+"}";
  }

}
