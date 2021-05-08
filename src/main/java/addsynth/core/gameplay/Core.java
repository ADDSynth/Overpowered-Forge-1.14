package addsynth.core.gameplay;

import addsynth.core.blocks.TestBlock;
import addsynth.core.gameplay.blocks.CautionBlock;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.gameplay.music_box.MusicBox;
import addsynth.core.gameplay.music_box.MusicSheet;
import addsynth.core.gameplay.team_manager.TeamManagerBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemTier;

public final class Core {

  public static final TestBlock        test_block              = new TestBlock();

  public static final Block            caution_block           = new CautionBlock("caution_block");
  public static final MusicBox         music_box               = new MusicBox("music_box");
  public static final MusicSheet       music_sheet             = new MusicSheet("music_sheet");
  public static final TeamManagerBlock team_manager            = new TeamManagerBlock();

  public static final ScytheTool       wooden_scythe           = new ScytheTool("wooden_scythe", ItemTier.WOOD);
  public static final ScytheTool       stone_scythe            = new ScytheTool("stone_scythe", ItemTier.STONE);
  public static final ScytheTool       iron_scythe             = new ScytheTool("iron_scythe",  ItemTier.IRON);
  public static final ScytheTool       gold_scythe             = new ScytheTool("gold_scythe",  ItemTier.GOLD);
  public static final ScytheTool       diamond_scythe          = new ScytheTool("diamond_scythe", ItemTier.DIAMOND);

}
