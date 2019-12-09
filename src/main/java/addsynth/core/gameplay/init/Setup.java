package addsynth.core.gameplay.init;

import java.io.File;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.Game;
import addsynth.core.game.Icon;
import addsynth.core.gameplay.Core;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.loading.FMLPaths;

public final class Setup {

  public static boolean config_loaded;

  /*
  public static final void setup_creative_tab(){

    final Icon[] icons = {
      new Icon(ADDSynthCore.registry.getItemBlock(Core.caution_block), Features.caution_block),
      new Icon(ADDSynthCore.registry.getItemBlock(Core.music_box), Features.music_box),
      new Icon(Core.stone_scythe, Features.scythes),
      new Icon(ADDSynthCore.registry.getItemBlock(Blocks.GRASS))
    };
    final ItemGroup tab = Game.NewCreativeTab(ADDSynthCore.MOD_ID, icons);
    
    Core.caution_block.setCreativeTab(tab); // PRIORITY: must now set Creative Tabs by using new Item.Properties().group(creative_tab).
    Core.music_box.setCreativeTab(tab);
    Core.music_sheet.setCreativeTab(tab);
    Core.wooden_scythe.setCreativeTab(tab);
    Core.stone_scythe.setCreativeTab(tab);
    Core.iron_scythe.setCreativeTab(tab);
    Core.gold_scythe.setCreativeTab(tab);
    Core.diamond_scythe.setCreativeTab(tab);
  }
  */

}
