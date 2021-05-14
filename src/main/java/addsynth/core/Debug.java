package addsynth.core;

import addsynth.core.config.Config;
import addsynth.core.game.Compatability;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.color.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;

public final class Debug {

  public static final boolean debug_recipes = false;
  
  public static final void log_recipe(IRecipe recipe){
    if(debug_recipes){
      ADDSynthCore.log.info("Added "+recipe.getClass().getSimpleName()+" "+recipe.getId()+".");
    }
  }

  public static final void block(final Block block){
    block(block, null);
  }

  public static final void block(final Block block, final BlockPos position){
    ADDSynthCore.log.warn(
      "Debug Block: Type: "+block.getClass().getName()+
      ", Registry Name: "+block.getRegistryName()+
      ", Translation Key: "+block.getTranslationKey()+
      (position != null ? ", Position: "+position : ""));
  }

  public static final void item(final Item item){
    ADDSynthCore.log.warn(
      "Debug Item: Type: "+item.getClass().getName()+
      ", Registry Name: "+item.getRegistryName()+
      ", Translation Key: "+item.getTranslationKey());
  }

  public static final void debug(){
    RegistryUtil.safety_check();
    if(Config.debug_mod_detection.get()){
      Compatability.debug();
    }
    if(Config.dump_map_colors.get()){
      ColorUtil.dump_map_colors();
    }
  }

}
