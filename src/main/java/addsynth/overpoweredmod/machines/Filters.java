package addsynth.overpoweredmod.machines;

import addsynth.core.material.MaterialsUtil;
import addsynth.core.util.JavaUtils;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import net.minecraft.item.Item;

public final class Filters {

  public static Item[] gem_converter;

  public static Item[] portal_frame;

  public static Item[] magic_infuser;

  public static final void regenerate_machine_filters(){
  
    gem_converter = MaterialsUtil.getFilter(
      MaterialsUtil.getRubies(), MaterialsUtil.getTopaz(), MaterialsUtil.getCitrine(), MaterialsUtil.getEmeralds(),
      MaterialsUtil.getDiamonds(), MaterialsUtil.getSapphires(), MaterialsUtil.getAmethysts(), MaterialsUtil.getQuartz()
    );
    
    portal_frame = MaterialsUtil.getFilter(
      MaterialsUtil.getRubyBlocks(),     MaterialsUtil.getTopazBlocks(),
      MaterialsUtil.getCitrineBlocks(),  MaterialsUtil.getEmeraldBlocks(),
      MaterialsUtil.getDiamondBlocks(),  MaterialsUtil.getSapphireBlocks(),
      MaterialsUtil.getAmethystBlocks(), MaterialsUtil.getQuartzBlocks()
    );
    
    magic_infuser = JavaUtils.combine_arrays(gem_converter, new Item[]{Init.energy_crystal, Init.void_crystal});
    
    GemConverterRecipe.generate_recipes();
  }

}
