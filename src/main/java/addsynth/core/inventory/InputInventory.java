package addsynth.core.inventory;

import java.util.ArrayList;
import java.util.function.BiFunction;
import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;
import addsynth.core.items.ItemUtil;
import addsynth.core.recipe.shapeless.RecipeCollection;
import addsynth.core.util.player.PlayerUtil;
import addsynth.core.util.world.WorldUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

/** This inventory only allows items to be inserted. It also has an Item filter to control
 *  which items are allowed. Pass null as the item filter to allow all items.
 */
public final class InputInventory extends CommonInventory {

  @Nonnull
  private final SlotData[] slot_data;
  public boolean custom_stack_limit_is_vanilla_dependant = true;

  /** Override this to specify your own way of determining whether an item
   *  is allowed in this inventory.
   */
  @Nonnull
  public BiFunction<Integer, ItemStack, Boolean> isItemStackValid;

  private InputInventory(final IInputInventory responder, @Nonnull final SlotData[] slots){
    super(responder, slots.length);
    this.slot_data = slots;
    isItemStackValid = (Integer slot, ItemStack stack) -> {
      return slot_data[slot].is_item_valid(stack);
    };
  }

  public static final InputInventory create(final IInputInventory responder, final int number_of_slots){
    return number_of_slots > 0 ? new InputInventory(responder, SlotData.create_new_array(number_of_slots, null)) : null;
  }

  public static final InputInventory create(final IInputInventory responder, final int number_of_slots, final Item[] filter){
    return number_of_slots > 0 ? new InputInventory(responder, SlotData.create_new_array(number_of_slots, filter)) : null;
  }

  public static final InputInventory create(final IInputInventory responder, final SlotData[] slots){
    return slots != null ? (slots.length > 0 ? new InputInventory(responder, slots) : null) : null;
  }

  @Override
  public @Nonnull ItemStack insertItem(final int slot, @Nonnull final ItemStack stack, final boolean simulate){
    if(stack.isEmpty()){       return ItemStack.EMPTY; }
    if(stack.getCount() == 0){ return ItemStack.EMPTY; }
    if(is_valid_slot(slot) == false){ return stack; }
    try{
      if(isItemStackValid.apply(slot, stack) == false){
        return stack;
      }
      final ItemStack existing_stack = this.stacks.get(slot);
      int limit = getStackLimit(slot, stack);
      if(existing_stack.isEmpty() == false){
        if(ItemHandlerHelper.canItemStacksStack(stack, existing_stack) == false){
          return stack;
        }
        limit -= existing_stack.getCount();
      }
      if(limit <= 0){
        return stack;
      }
      final boolean reached_limit = stack.getCount() > limit;
      if(simulate == false){
        if(existing_stack.isEmpty()){
          if(reached_limit){ this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(stack, limit)); }
          else{              this.stacks.set(slot, stack); }
        }
        else{
          if(reached_limit){ existing_stack.grow(limit); }
          else{              existing_stack.grow(stack.getCount()); }
        }
        onContentsChanged(slot);
      }
      if(reached_limit){
        return ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit);
      }
      return ItemStack.EMPTY; // none left over, all of stack was added.
    }
    catch(Exception e){
      e.printStackTrace();
      return ItemStack.EMPTY;
    }
  }

  @Override
  public @Nonnull ItemStack extractItem(int slot, int amount, boolean simulate){
    if(amount == 0){ return ItemStack.EMPTY; }
    try{
      validateSlotIndex(slot);
      final ItemStack existing_stack = this.stacks.get(slot);
      if(existing_stack.isEmpty()){ return ItemStack.EMPTY; }
      final int extract_amount = Math.min(amount, existing_stack.getMaxStackSize());
      if(existing_stack.getCount() <= extract_amount){
        if(simulate == false){
          this.stacks.set(slot, ItemStack.EMPTY);
          onContentsChanged(slot);
        }
        return existing_stack;
      }
      if(simulate == false){
        this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing_stack, existing_stack.getCount() - extract_amount));
        onContentsChanged(slot);
      }
      return ItemHandlerHelper.copyStackWithSize(existing_stack, extract_amount);
    }
    catch(Exception e){
      e.printStackTrace();
      return ItemStack.EMPTY;
    }
  }

  public final void add(final int slot, final ItemStack stack){
    insertItem(slot, stack, false);
  }

  public final void decrease(final int slot){
    decrease(slot, 1);
  }

  public final void decrease(final int slot, final int amount){
    try{
      if(stacks.get(slot) != null){
        if(stacks.get(slot).isEmpty() == false){
          stacks.get(slot).shrink(amount);
        }
      }
    }
    catch(IndexOutOfBoundsException e){
      ADDSynthCore.log.error("InputInventory -> decrease() function: slot argument is out of bounds. Must be within 0 and "+(stacks.size() - 1)+".");
      e.printStackTrace();
    }
    catch(NullPointerException e){
      e.printStackTrace();
    }
  }

  public final void decrease_input(){
    decrease_input(1);
  }

  public final void decrease_input(final int amount){
    int i;
    for(i = 0; i < stacks.size(); i++){
      decrease(i,amount);
    }
  }

  @Override
  protected final int getStackLimit(final int slot, @Nonnull final ItemStack stack){
    if(this.slot_data[slot].stack_limit < 0){
      return stack.getMaxStackSize();
    }
    if(custom_stack_limit_is_vanilla_dependant){
      return Math.min(this.slot_data[slot].stack_limit, stack.getMaxStackSize());
    }
    return this.slot_data[slot].stack_limit;
  }

  @Override
  public final void save(final CompoundNBT nbt){
    nbt.put("InputInventory", serializeNBT());
  }

  @Override
  public final void load(final CompoundNBT nbt){
    deserializeNBT(nbt.getCompound("InputInventory"));
  }

  /** Normally, slot item filters are based on the recipe input items, and never change
   *  through the life of the TileEntity machine. However, you can use this function to
   *  change the slot item filters on-the-fly. Still recommend you don't use this though.
   * @param slot
   * @param new_filter
   */
  public final void setFilter(final int slot, final Item[] new_filter){
    if(is_valid_slot(slot)){
      slot_data[slot].setFilter(new_filter);
    }
  }

  /** Changes the item slot filters of this inventory to match a specific recipe.
   *  If you want to filter items based on all recipes (which is the standard behaviour),
   *  then use {@link RecipeCollection#build_filter()}. If the inventory has more
   *  slots than required by the recipe, then the extra slots are filtered to
   *  accept no items.
   * @param recipe
   */
  public final void setFilter(final IRecipe<?> recipe){
    final NonNullList<Ingredient> ingredients = recipe.getIngredients();
    final int ingredients_length = ingredients.size();
    final int inventory_size = stacks.size();
    if(ingredients_length > inventory_size){
      ADDSynthCore.log.error(
        "Cannot set the InputInventory slot filters to match '"+recipe.getClass().getSimpleName()+
        "' because the recipe has too many ingredients."
      );
      Thread.dumpStack();
    }
    int i;
    for(i = 0; i < inventory_size; i++){
      if(i < ingredients_length){
        slot_data[i].setFilter(ItemUtil.toItemArray(ingredients.get(i).getMatchingStacks()));
      }
      else{
        slot_data[i].setFilterAll();
      }
    }
  }

  /** Checks all slots in this inventory and returns invalid ItemStacks to the player. */
  public final void ejectInvalidItems(final PlayerEntity player){
    final ItemStack[] ejected_itemStacks = getInvalidItemStacks();
    for(ItemStack stack : ejected_itemStacks){
      PlayerUtil.add_to_player_inventory(player, stack);
    }
  }

  /** Checks all slots in this inventory, removes invalid ItemStacks that don't
   *  match the filter, and spawns them in the world. */
  public final void ejectInvalidItems(final World world, final BlockPos pos){
    final ItemStack[] ejected_itemStacks = getInvalidItemStacks();
    for(ItemStack stack : ejected_itemStacks){
      WorldUtil.spawnItemStack(world, pos, stack, false);
    }
  }

  private final ItemStack[] getInvalidItemStacks(){
    int i;
    final ArrayList<ItemStack> ejected_itemStacks = new ArrayList<>(stacks.size());
    for(i = 0; i < stacks.size(); i++){
      if(slot_data[i].is_item_valid(getStackInSlot(i)) == false){
        ejected_itemStacks.add(extractItemStack(i));
      }
    }
    return ejected_itemStacks.toArray(new ItemStack[ejected_itemStacks.size()]);
  }

}
