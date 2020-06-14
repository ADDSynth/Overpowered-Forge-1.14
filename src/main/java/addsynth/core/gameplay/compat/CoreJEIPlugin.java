package addsynth.core.gameplay.compat;

import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.Compatability;
import addsynth.core.gameplay.Core;
import addsynth.core.util.StringUtil;
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
    registry.addIngredientInfo(list, VanillaTypes.ITEM, StringUtil.translate("gui.addsynthcore.jei_description.scythes"));
    registry.addIngredientInfo(new ItemStack(Core.music_box), VanillaTypes.ITEM, StringUtil.translate("gui.addsynthcore.jei_description.music_box"));
    registry.addIngredientInfo(new ItemStack(Core.music_sheet), VanillaTypes.ITEM, StringUtil.translate("gui.addsynthcore.jei_description.music_sheet"));
  }

}
