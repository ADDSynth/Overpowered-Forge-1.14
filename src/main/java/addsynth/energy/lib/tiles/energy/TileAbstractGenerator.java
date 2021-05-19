package addsynth.energy.lib.tiles.energy;

import addsynth.core.tiles.TileBase;
import addsynth.energy.lib.main.Generator;
import addsynth.energy.lib.main.IEnergyGenerator;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

/** This TileEntity is for machines that generate Energy all on their own. */
public abstract class TileAbstractGenerator extends TileBase implements IEnergyGenerator, ITickableTileEntity {

  protected boolean changed;
  protected final Generator energy;

  public TileAbstractGenerator(final TileEntityType type){
    super(type);
    this.energy = new Generator();
  }

  @Override
  public void tick(){
    if(onServerSide()){
      try{
        if(energy.isEmpty()){
          setGeneratorData();
          changed = true;
        }
        if(energy.tick()){
          changed = true;
        }
        if(changed){
          update_data();
          changed = false;
        }
      }
      catch(Exception e){
        report_ticking_error(e);
      }
    }
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    energy.loadFromNBT(nbt);
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    energy.saveToNBT(nbt);
    return nbt;
  }
  
  protected abstract void setGeneratorData();

  @Override
  public Generator getEnergy(){
    return energy;
  }

}
