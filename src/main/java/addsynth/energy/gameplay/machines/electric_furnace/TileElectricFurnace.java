package addsynth.energy.gameplay.machines.electric_furnace;

import javax.annotation.Nullable;
import addsynth.core.recipe.RecipeUtil;
import addsynth.energy.lib.config.MachineData;
import addsynth.energy.lib.config.MachineType;
import addsynth.energy.lib.tiles.machines.TileAlwaysOnMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileElectricFurnace extends TileAlwaysOnMachine implements INamedContainerProvider {

  private static final MachineData machine_data = new MachineData(MachineType.ALWAYS_ON, 200, 5, 0, 0);

  public TileElectricFurnace(){
    super(Tiles.ELECTRIC_FURNACE, 1, get_filter(), 1, machine_data);
    inventory.setRecipeProvider(RecipeUtil::getFurnaceResult);
  }

  public static final Item[] get_filter(){
    return RecipeUtil.getFurnaceIngredients();
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerElectricFurnace(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
