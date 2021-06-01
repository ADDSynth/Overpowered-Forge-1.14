package addsynth.energy.gameplay.machines.circuit_fabricator;

import javax.annotation.Nullable;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.items.ItemUtil;
import addsynth.core.util.StringUtil;
import addsynth.core.util.constants.Constants;
import addsynth.core.util.java.ArrayUtil;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipe;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileCircuitFabricator extends TileStandardWorkMachine implements INamedContainerProvider {

  private int circuit_id;
  private ItemStack[][] filter = new ItemStack[8][];

  private final InputSlot[] input_slot = {
    new InputSlot(this, 0,  76, 65),
    new InputSlot(this, 1,  94, 65),
    new InputSlot(this, 2, 112, 65),
    new InputSlot(this, 3, 130, 65),
    new InputSlot(this, 4,  76, 83),
    new InputSlot(this, 5,  94, 83),
    new InputSlot(this, 6, 112, 83),
    new InputSlot(this, 7, 130, 83)
  };

  public TileCircuitFabricator(){
    super(Tiles.CIRCUIT_FABRICATOR, 8, null, 1, Config.circuit_fabricator_data);
  }

  public final void change_circuit_craft(final int circuit_id, final boolean update){
   this.circuit_id = circuit_id;
   rebuild_filters();
   changed = update;
  }

  // Ideally, this should be rebuilt every recipe and tag reload, but this is the best I can do for now.
  public final void rebuild_filters(){
    // select ItemStack based on circuit ID.
    final ItemStack output = new ItemStack(EnergyItems.circuit[circuit_id], 1);
    // find recipe
    final CircuitFabricatorRecipe recipe = CircuitFabricatorRecipes.INSTANCE.find_recipe(output);
    // get ingredients, create filters
    filter = recipe.getItemStackIngredients();
    int i;
    for(i = 0; i < 8; i++){
      // apply filters
      if(i < filter.length){
        input_slot[i].setFilter(filter[i]);
        inventory.input_inventory.setFilter(i, ItemUtil.toItemArray(filter[i]));
      }
      else{
        input_slot[i].setFilter(new Item[0]);
        inventory.input_inventory.setFilter(i, new Item[0]);
      }
    }
    
    // update recipe in gui if on client side
    updateGui();
  }

  // go through all inventory slots and eject all items that don't match
  // this can only be called when the player clicks on a change recipe button on the gui,
  //   which then sends a network message to the server.
  public final void ejectInvalidItems(final PlayerEntity player){
    inventory.input_inventory.ejectInvalidItems(player);
  }

  public final void updateGui(){
    if(world != null){
      if(world.isRemote){
        CircuitFabricatorGui.updateRecipeDisplay(filter);
      }
    }
  }

  @Override
  protected boolean test_condition(){
    final ItemStack[] input = {
      inventory.input_inventory.getStackInSlot(0), inventory.input_inventory.getStackInSlot(1),
      inventory.input_inventory.getStackInSlot(2), inventory.input_inventory.getStackInSlot(3),
      inventory.input_inventory.getStackInSlot(4), inventory.input_inventory.getStackInSlot(5),
      inventory.input_inventory.getStackInSlot(6), inventory.input_inventory.getStackInSlot(7)
    };
    result = CircuitFabricatorRecipes.INSTANCE.getResult(input, world);
    return ItemUtil.itemStackExists(result) ? inventory.output_inventory.can_add(0, result) : false;
  }

  @Override
  protected void perform_work(){
    inventory.output_inventory.insertItem(0, result.copy(), false);
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putInt("Circuit to Craft", circuit_id);
    return nbt;
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    // this runs with a default value of 0 if the key doesn't exist. That's perfect.
    change_circuit_craft(nbt.getInt("Circuit to Craft"), false);
  }

  public final String getCircuitSelected(){
    if(onClientSide()){
      if(ArrayUtil.isInsideBounds(circuit_id, EnergyItems.circuit)){
        return StringUtil.translate(EnergyItems.circuit[circuit_id].getTranslationKey());
      }
      return Constants.null_error;
    }
    return "";
  }

  public final InputSlot[] getInputSlots(){
    return input_slot;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new CircuitFabricatorContainer(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
