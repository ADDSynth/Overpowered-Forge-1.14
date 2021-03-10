package addsynth.overpoweredmod.game.core;

import addsynth.material.Material;
import addsynth.material.MaterialsUtil;
import addsynth.material.types.Gem;
import addsynth.overpoweredmod.Debug;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class Gems {

  static {
    Debug.log_setup_info("Begin loading Gems class...");
  }

  public static final Gem RUBY     = Material.RUBY;
  public static final Gem TOPAZ    = Material.TOPAZ;
  public static final Gem CITRINE  = Material.CITRINE;
  public static final Gem EMERALD  = Material.EMERALD;
  public static final Gem DIAMOND  = Material.DIAMOND;
  public static final Gem SAPPHIRE = Material.SAPPHIRE;
  public static final Gem AMETHYST = Material.AMETHYST;
  public static final Gem QUARTZ   = Material.QUARTZ;

  public static final Item ruby     = RUBY.gem;
  public static final Item topaz    = TOPAZ.gem;
  public static final Item citrine  = CITRINE.gem;
  public static final Item emerald  = EMERALD.gem;
  public static final Item diamond  = DIAMOND.gem;
  public static final Item sapphire = SAPPHIRE.gem;
  public static final Item amethyst = AMETHYST.gem;
  public static final Item quartz   = QUARTZ.gem;

  public static final Gem[] index = new Gem[] {RUBY, TOPAZ, CITRINE, EMERALD, DIAMOND, SAPPHIRE, AMETHYST, QUARTZ};
  public static final int max_index = index.length;

  public static final ItemStack getItemStack(final int gem_id){
    return new ItemStack(index[gem_id].gem);
  }

  public static final int getID(final Item gem){
    if(MaterialsUtil.match(gem, MaterialsUtil.getRubies())){    return 0; }
    if(MaterialsUtil.match(gem, MaterialsUtil.getTopaz())){     return 1; }
    if(MaterialsUtil.match(gem, MaterialsUtil.getCitrine())){   return 2; }
    if(MaterialsUtil.match(gem, MaterialsUtil.getEmeralds())){  return 3; }
    if(MaterialsUtil.match(gem, MaterialsUtil.getDiamonds())){  return 4; }
    if(MaterialsUtil.match(gem, MaterialsUtil.getSapphires())){ return 5; }
    if(MaterialsUtil.match(gem, MaterialsUtil.getAmethysts())){ return 6; }
    if(MaterialsUtil.match(gem, MaterialsUtil.getQuartz())){    return 7; }
    return -1;
  }

  static {
    Debug.log_setup_info("Finished loading Gems class.");
  }

}
