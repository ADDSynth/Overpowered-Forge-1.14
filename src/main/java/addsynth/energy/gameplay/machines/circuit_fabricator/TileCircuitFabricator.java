package addsynth.energy.gameplay.machines.circuit_fabricator;

import javax.annotation.Nullable;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.game.items.ItemUtil;
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

  private int circuit_id = -1;
  private ItemStack[][] filter = new ItemStack[8][];

  private final InputSlot[] input_slot = {
    new InputSlot(this, 0,  76, 76),
    new InputSlot(this, 1,  94, 76),
    new InputSlot(this, 2, 112, 76),
    new InputSlot(this, 3, 130, 76),
    new InputSlot(this, 4,  76, 94),
    new InputSlot(this, 5,  94, 94),
    new InputSlot(this, 6, 112, 94),
    new InputSlot(this, 7, 130, 94)
  };

  public TileCircuitFabricator(){
    super(Tiles.CIRCUIT_FABRICATOR, 8, null, 1, Config.circuit_fabricator_data);
    inventory.setRecipeProvider(CircuitFabricatorRecipes.INSTANCE);
  }

  public final void change_circuit_craft(final int circuit_id){
    if(this.circuit_id != circuit_id){
     this.circuit_id = circuit_id;
     rebuild_filters();
     changed = true;
    }
  }

  // TODO: Ideally, this should be rebuilt every recipe and tag reload, but this is the best I can do for now.
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
        inventory.getInputInventory().setFilter(i, ItemUtil.toItemArray(filter[i]));
      }
      else{
        input_slot[i].setFilter(new Item[0]);
        inventory.getInputInventory().setFilter(i, new Item[0]);
      }
    }
    
    // update recipe in gui if on client side
    updateGui();
  }

  /** Go through all inventory slots and eject all items that don't match.
   *  This can only be called when the player clicks on a change recipe button on the gui,
   *  which then sends a network message to the server.
   */
  public final void ejectInvalidItems(final PlayerEntity player){
    inventory.getInputInventory().ejectInvalidItems(player);
  }

  @SuppressWarnings("null")
  public final void updateGui(){
    if(world != null){
      if(world.isRemote){
        CircuitFabricatorGui.updateRecipeDisplay(filter);
      }
    }
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putInt("Circuit to Craft", circuit_id >= 0 ? circuit_id : 0);
    return nbt;
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    // this runs with a default value of 0 if the key doesn't exist. That's perfect.
    change_circuit_craft(nbt.getInt("Circuit to Craft"));
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
