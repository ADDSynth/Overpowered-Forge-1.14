package addsynth.core.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.inventory.CommonInventory;
import addsynth.core.inventory.InputInventory;
import addsynth.core.inventory.OutputInventory;
import addsynth.core.inventory.SlotData;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * The TileMachine is different from the TileWithStorage in that it also has an output
 * inventory, and Hoppers draw from the Output inventory instead of the Input inventory.
 * TileMachine also prevents players from inserting items into the Output inventory.
 * The Input Inventory has a filter, to restrict the type of items that can be inserted.
 * Specify 0 Output Slots to have a null Output Inventory and Hoppers will not extract
 * anything from this machine.
 * @author ADDSynth
 */
public abstract class TileMachine extends TileBase {

  protected final InputInventory input_inventory;
  protected final CommonInventory working_inventory;
  protected final OutputInventory output_inventory;

  public TileMachine(final TileEntityType type, final SlotData[] slots, final int output_slots){
    super(type);
    this.input_inventory = slots != null ? (slots.length > 0 ? new InputInventory(this, slots) : null) : null;
    this.working_inventory = slots != null ? (slots.length > 0 ? new CommonInventory(slots.length) : null) : null;
    this.output_inventory = output_slots > 0 ? new OutputInventory(this, output_slots) : null;
  }

  public TileMachine(final TileEntityType type, final int input_slots, final Item[] input_filter, final int output_slots){
    super(type);
    this.input_inventory = input_slots > 0 ? new InputInventory(this, input_slots, input_filter) : null;
    this.working_inventory = input_slots > 0 ? new CommonInventory(input_slots) : null;
    this.output_inventory = output_slots > 0 ? new OutputInventory(this, output_slots) : null;
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    if(input_inventory != null){ input_inventory.deserializeNBT(nbt.getCompound("InputInventory")); }
    if(working_inventory != null){ working_inventory.deserializeNBT(nbt.getCompound("WorkingInventory")); }
    if(output_inventory != null){ output_inventory.deserializeNBT(nbt.getCompound("OutputInventory")); }
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(input_inventory != null){ nbt.put("InputInventory", input_inventory.serializeNBT()); }
    if(working_inventory != null){ nbt.put("WorkingInventory", working_inventory.serializeNBT()); }
    if(output_inventory != null){ nbt.put("OutputInventory", output_inventory.serializeNBT()); }
    return nbt;
  }

  @Override
  public @Nonnull <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction facing){
    if(removed == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        if(facing == Direction.DOWN){
          return output_inventory != null ? (LazyOptional.of(()->output_inventory)).cast() : LazyOptional.empty();
        }
        return input_inventory != null ? (LazyOptional.of(()->input_inventory)).cast() : LazyOptional.empty();
      }
      return super.getCapability(capability, facing);
    }
    return LazyOptional.empty();
  }

  public InputInventory getInputInventory(){
    return input_inventory;
  }
  
  public CommonInventory getWorkingInventory(){
    return working_inventory;
  }
  
  public OutputInventory getOutputInventory(){
    return output_inventory;
  }

  public void onInventoryChanged(){
  }

  public void drop_inventory(){
    final double x = (double)pos.getX();
    final double y = (double)pos.getY();
    final double z = (double)pos.getZ();
    if(input_inventory != null){
      input_inventory.drop_in_world(world, x, y, z);
    }
    if(working_inventory != null){
      working_inventory.drop_in_world(world, x, y, z);
    }
    if(output_inventory != null){
      output_inventory.drop_in_world(world, x, y, z);
    }
  }

}
