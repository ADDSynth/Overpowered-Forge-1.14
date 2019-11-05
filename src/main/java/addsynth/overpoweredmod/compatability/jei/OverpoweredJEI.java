package addsynth.overpoweredmod.compatability.jei;

import addsynth.core.game.Compatability;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.core.Portal;
import addsynth.overpoweredmod.game.core.Tools;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;

@JEIPlugin
public final class OverpoweredJEI implements IModPlugin {

  static {
    Debug.log_setup_info("Overpowered JEI Plugin class was loaded.");
  }

// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/client/jei/GalacticraftJEI.java#L90

  @Override
  public final void register(final IModRegistry registry){
  
    // Hide items from JEI:    (Items that are registered but don't want to show up in JEI)
    IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
    if(blacklist != null){
      blacklist.addIngredientToBlacklist(new ItemStack(Portal.portal_image));
    }
    
    // TODO: Handle Compressor recipes using IModRegistry.recipehandler()
  }

}
