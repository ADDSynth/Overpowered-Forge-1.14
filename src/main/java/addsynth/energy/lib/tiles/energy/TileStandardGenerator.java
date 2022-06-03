package addsynth.energy.lib.tiles.energy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.IInputInventory;
import addsynth.core.game.inventory.InputInventory;
import addsynth.core.game.inventory.InventoryUtil;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** Standard Generators are generators that have an Input inventory and
 *  accept fuel items to be consumed to produce Energy. 
 * @author ADDSynth
 */
public abstract class TileStandardGenerator extends TileAbstractGenerator implements IInputInventory {

  protected final InputInventory input_inventory;

  public TileStandardGenerator(final TileEntityType type, final Item[] filter){
    super(type);
    this.input_inventory = InputInventory.create(this, 1, filter);
  }

  @Override
  public void tick(){
    if(onServerSide()){
      try{
        if(energy.isEmpty()){
          if(input_inventory.isEmpty() == false){
            setGeneratorData();
            changed = true;
          }
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
    if(input_inventory != null){ input_inventory.load(nbt);}
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(input_inventory != null){ input_inventory.save(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
    if(removed == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(input_inventory, null, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    changed = true;
  }

  @Override
  public final void drop_inventory(){
    InventoryUtil.drop_inventories(pos, world, input_inventory);
  }

  @Override
  public final InputInventory getInputInventory(){
    return input_inventory;
  }

}
