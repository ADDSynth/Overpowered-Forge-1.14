package addsynth.overpoweredmod.machines;

import addsynth.core.material.MaterialsUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import net.minecraft.item.Item;

public final class Filters {

  // In Minecraft versions 1.14+, We now use the Tag system, which is also part of the Data Pack system.
  // Machine filters need to be regenerated whenever Tags are reloaded, and possibly when Recipes are
  // reloaded as well. These events occur when joining worlds, and when players do the Reload Resource
  // Packs command. But what about on a dedicated server? Both server and client sides use these filters.
  // We need to ensure these filters are generated on both the client and server side, otherwise there
  // will be a desync issue, where one side accepts items into the machine while the other does not.

  // TODO: When we do the WorkSystem upgrade, have TileEntities construct their own item filters.
  //       Keep the variable private, and have the get() method first check if it is null and if it is,
  //       then construct it first. Always keep the constructed filter as a cached variable.
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
    
    magic_infuser = ArrayUtil.combine_arrays(gem_converter, new Item[]{Init.energy_crystal, Init.void_crystal});
    
    GemConverterRecipe.generate_recipes();
  }

}
