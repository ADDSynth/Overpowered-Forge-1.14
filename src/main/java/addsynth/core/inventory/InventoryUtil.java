package addsynth.core.inventory;

import addsynth.core.ADDSynthCore;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public final class InventoryUtil {

  /** <p>Used to safely return the Inventory Capability. Use this if your inventory allows bi-directional
   *  transfer of items because we return the inventory regardless of which side we're checking from.
   *  Remember to ONLY USE THIS IF the Capability you're checking is
   *  {@link net.minecraftforge.items.CapabilityItemHandler#ITEM_HANDLER_CAPABILITY ITEM_HANDLER_CAPABILITY}.</p>
   *  <p>Use case:<br />
   *  <pre><code>
   *  &#64;Override
   *  public &lt;T&gt; LazyOptional&lt;T&gt; getCapability(&#64;Nonnull Capability&lt;T&gt; capability, &#64;Nullable Direction direction){
   *    if(removed == false){
   *      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
   *        return InventoryUtil.getInventoryCapability(inventory);
   *      }
   *      return super.getCapability(capability, direction);
   *    }
   *    return LazyOptional.empty();
   *  }
   *  </code></pre></p>
   * @param <T>
   * @param inventory
   */
  public static final <T> LazyOptional<T> getInventoryCapability(final CommonInventory inventory){
    try{
      return inventory != null ? (LazyOptional.of(() -> inventory)).cast() : LazyOptional.empty();
    }
    catch(Exception e){
      ADDSynthCore.log.error("Tried to use helper method InventoryUtil.getInventoryCapability() but"+
        "the the capability we're trying to get is not ITEM_HANDLER_CAPABILITY!", e);
      return LazyOptional.empty();
    }
  }

  /** <p>Used to return either the Input Inventory or Output Inventory depending on which face
   *  we're querrying from. Pass null to either inventory if your TileEntity doesn't have them.
   *  Remember to ONLY USE THIS IF the Capability you're checking is
   *  {@link net.minecraftforge.items.CapabilityItemHandler#ITEM_HANDLER_CAPABILITY ITEM_HANDLER_CAPABILITY}.</p>
   *  <p>Use case:<br />
   *  <pre><code>
   *  &#64;Override
   *  public &lt;T&gt; LazyOptional&lt;T&gt; getCapability(&#64;Nonnull Capability&lt;T&gt; capability, &#64;Nullable Direction direction){
   *    if(removed == false){
   *      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
   *        return InventoryUtil.getInventoryCapability(input_inventory, output_inventory, direction);
   *      }
   *      return super.getCapability(capability, direction);
   *    }
   *    return LazyOptional.empty();
   *  }
   *  </code></pre></p>
   * @param <T>
   * @param input_inventory
   * @param output_inventory
   * @param facing
   */
  public static final <T> LazyOptional<T> getInventoryCapability
  (InputInventory input_inventory, OutputInventory output_inventory, Direction facing){
    try{
      if(facing != null){
        if(facing == Direction.DOWN){
          return output_inventory != null ? (LazyOptional.of(() -> output_inventory)).cast() : LazyOptional.empty();
        }
        return input_inventory != null ? (LazyOptional.of(() -> input_inventory)).cast() : LazyOptional.empty();
      }
    }
    catch(Exception e){
      ADDSynthCore.log.error("Tried to use helper method InventoryUtil.getInventoryCapability() but"+
        "the the capability we're trying to get is not ITEM_HANDLER_CAPABILITY!", e);
    }
    return LazyOptional.empty();
  }
    

  public static final void drop_inventories(final BlockPos pos, final World world, final CommonInventory ... inventories){
    final double x = pos.getX();
    final double y = pos.getY();
    final double z = pos.getZ();
    for(final CommonInventory inventory : inventories){
      if(inventory != null){
        inventory.drop_in_world(world, x, y, z);
      }
    }
  }

  public static final Inventory toInventory(final ItemStackHandler handler){
    return new Inventory(getItemStacks(handler));
  }

  public static final ItemStack[] getItemStacks(final ItemStackHandler handler){
    final int max = handler.getSlots();
    final ItemStack[] stacks = new ItemStack[max];
    int i;
    for(i = 0; i < max; i++){
      stacks[i] = handler.getStackInSlot(i);
    }
    return stacks;
  }

}
