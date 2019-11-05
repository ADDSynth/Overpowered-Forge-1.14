package addsynth.core.gameplay;

import addsynth.core.gameplay.blocks.CautionBlock;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.gameplay.music_box.MusicBox;
import addsynth.core.gameplay.music_box.MusicSheet;
import net.minecraft.block.Block;
import net.minecraft.item.Item.ToolMaterial;

public final class Core {

  public static final Block           caution_block            = new CautionBlock("caution_block");
  public static final MusicBox        music_box                = new MusicBox("music_box");
  public static final MusicSheet      music_sheet              = new MusicSheet("music_sheet");

  public static final ScytheTool      wooden_scythe            = new ScytheTool("wooden_scythe", ToolMaterial.WOOD);
  public static final ScytheTool      stone_scythe             = new ScytheTool("stone_scythe", ToolMaterial.STONE);
  public static final ScytheTool      iron_scythe              = new ScytheTool("iron_scythe",  ToolMaterial.IRON);
  public static final ScytheTool      gold_scythe              = new ScytheTool("gold_scythe",  ToolMaterial.GOLD);
  public static final ScytheTool      diamond_scythe           = new ScytheTool("diamond_scythe", ToolMaterial.DIAMOND);

}
