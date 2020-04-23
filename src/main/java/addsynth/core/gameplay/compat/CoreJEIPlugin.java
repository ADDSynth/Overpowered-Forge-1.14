package addsynth.core.gameplay.compat;

import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.Compatability;
import addsynth.core.gameplay.Core;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public final class CoreJEIPlugin  implements IModPlugin {

  public static final ResourceLocation id = new ResourceLocation(ADDSynthCore.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid(){
    return id;
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    add_information(registration);
  }

  private static final void add_information(IRecipeRegistration registry){
    final ArrayList<ItemStack> list = new ArrayList<>(6);
    list.add(new ItemStack(Core.wooden_scythe));
    list.add(new ItemStack(Core.stone_scythe));
    list.add(new ItemStack(Core.iron_scythe));
    list.add(new ItemStack(Core.gold_scythe));
    list.add(new ItemStack(Core.diamond_scythe));
    if(Compatability.OVERPOWERED.loaded){
      list.add(new ItemStack(addsynth.overpoweredmod.game.core.Tools.energy_scythe));
    }
    registry.addIngredientInfo(list, VanillaTypes.ITEM, "Scythes are used to cut leaves off of trees very quickly. They are mined as though you mined them by hand.");
    registry.addIngredientInfo(new ItemStack(Core.music_box), VanillaTypes.ITEM, "Press keys on your keyboard to change notes. Arrange notes on the grid, and press PLAY to hear the current Music Box. Activate a Music Box with a redstone signal. When one Music Box is done playing, it will search in the NEXT direction for another Music Box and play that one.");
    registry.addIngredientInfo(new ItemStack(Core.music_sheet), VanillaTypes.ITEM, "The Music Sheet can be used to copy Music Box data to other Music Boxes. Right-click a Music Box to copy data. When the Music Sheet has data, right-click Music Boxes to paste. Right-clicking without looking at a Music Box will clear the data.");
  }

}
