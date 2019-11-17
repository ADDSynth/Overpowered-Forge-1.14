package addsynth.core.material.types;

import addsynth.core.ADDSynthCore;
import addsynth.core.material.MiningStrength;
import addsynth.core.util.StringUtil;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.oredict.OreDictionary;

public final class ManufacturedMetal extends Metal {

  /** Placeholder Metal */
  public ManufacturedMetal(final String name){
    super(name);
  }

  /** MiningStrength here might be used in the future if I want the metal blocks to
   *  require a certain pickaxe strength to mine them.
   * @param unlocalized_name
   * @param color
   * @param strength
   */
  public ManufacturedMetal(final String unlocalized_name, final MaterialColor color, MiningStrength strength){
    super(unlocalized_name, color);
  }

  @Override
  public void debug(){
    ADDSynthCore.log.info("Material: "+name+", Type: Manufactured Metal");
    ADDSynthCore.log.info("ingot"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("ingot"+name, false)));
    ADDSynthCore.log.info("nugget"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("nugget"+name, false)));
    ADDSynthCore.log.info("block"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("block"+name, false)));
    ADDSynthCore.log.info("plate"+name+": "+StringUtil.print_minecraft_array(OreDictionary.getOres("plate"+name, false)));
  }

}
