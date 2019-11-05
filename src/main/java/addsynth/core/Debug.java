package addsynth.core;

import addsynth.core.ADDSynthCore;
import addsynth.core.config.Config;
import addsynth.core.game.Compatability;
import addsynth.core.game.RegistryUtil;
import addsynth.core.material.Material;
import addsynth.core.material.types.AbstractMaterial;
import addsynth.core.util.ColorUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public final class Debug {

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

  public static final void init(){
    RegistryUtil.safety_check();
    if(Config.debug_mod_detection){
      Compatability.debug();
    }
    if(Config.dump_map_colors){
      ColorUtil.dump_map_colors();
    }
  }

  public static final void postInit(){
    if(Config.debug_materials_detection){
      ADDSynthCore.log.info("Begin printing all detected materials via the OreDictionary:");
      for(AbstractMaterial material : Material.list){
        material.debug();
      }
      ADDSynthCore.log.info("Done detecting OreDictionary entries.");
    }
  }

}
