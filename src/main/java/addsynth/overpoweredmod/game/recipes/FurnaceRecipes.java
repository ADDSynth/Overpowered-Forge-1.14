package addsynth.overpoweredmod.game.recipes;

import addsynth.core.game.Compatability;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Metals;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class FurnaceRecipes {

  //  net.minecraft.item.crafting.FurnaceRecipes.class

  public static final void register(){
    Debug.log_setup_info("Begin registering Furnace Recipes...");
  
    GameRegistry.addSmelting(Gems.RUBY.ore,     new ItemStack(Gems.ruby,1),     1.0f);
    GameRegistry.addSmelting(Gems.TOPAZ.ore,    new ItemStack(Gems.topaz,1),    1.0f);
    GameRegistry.addSmelting(Gems.CITRINE.ore,  new ItemStack(Gems.citrine,1),  1.0f);
    GameRegistry.addSmelting(Gems.SAPPHIRE.ore, new ItemStack(Gems.sapphire,1), 1.0f);
    GameRegistry.addSmelting(Gems.AMETHYST.ore, new ItemStack(Gems.amethyst,1), 1.0f);

    GameRegistry.addSmelting(Metals.COPPER.ore,   new ItemStack(Metals.COPPER.ingot),   0.7f);
    GameRegistry.addSmelting(Metals.TIN.ore,      new ItemStack(Metals.TIN.ingot),      0.7f);
    GameRegistry.addSmelting(Metals.ALUMINUM.ore, new ItemStack(Metals.ALUMINUM.ingot), 0.7f);
    GameRegistry.addSmelting(Metals.SILVER.ore,   new ItemStack(Metals.SILVER.ingot),   1.0f);
    GameRegistry.addSmelting(Metals.PLATINUM.ore, new ItemStack(Metals.PLATINUM.ingot), 1.0f);
    GameRegistry.addSmelting(Metals.TITANIUM.ore, new ItemStack(Metals.TITANIUM.ingot), 1.0f);
    
    if(Compatability.RAILCRAFT.loaded == false){
      GameRegistry.addSmelting(new ItemStack(Metals.IRON.ingot), new ItemStack(Metals.STEEL.ingot), 0.3f);
    }
    
    Debug.log_setup_info("Finished registering Furnace Recipes.");
  }

}
