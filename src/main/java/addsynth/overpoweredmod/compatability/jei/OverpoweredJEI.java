package addsynth.overpoweredmod.compatability.jei;

import java.util.ArrayList;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Portal;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public final class OverpoweredJEI implements IModPlugin {

  static {
    Debug.log_setup_info("Overpowered JEI Plugin class was loaded.");
  }

// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/client/jei/GalacticraftJEI.java#L90

  // TODO: Handle Compressor recipes using IModRegistry.recipehandler()

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime){
    final ArrayList<ItemStack> blacklist = new ArrayList<>(1);
    blacklist.add(new ItemStack(Portal.portal_image));
    jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, blacklist);
  }

  @Override
  public ResourceLocation getPluginUid(){
    return new ResourceLocation(OverpoweredMod.MOD_ID, "overpowered_jei_plugin");
  }

}
