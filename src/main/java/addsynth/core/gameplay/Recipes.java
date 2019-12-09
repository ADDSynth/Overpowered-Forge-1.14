package addsynth.core.gameplay;

import addsynth.core.config.Features;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.util.RecipeUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;

@Deprecated
public final class Recipes {

  public static final void register(){
    if(Features.caution_block.get()){
      RecipeUtil.register(3, 3, new ItemStack(Core.caution_block,8), new Object[]{
        "dyeYellow", "dyeBlack", "dyeYellow",
        "dyeBlack",  "stone",    "dyeBlack",
        "dyeYellow", "dyeBlack", "dyeYellow"});
    }
    if(Features.music_box.get()){
      RecipeUtil.registerMachine(new ItemStack(Core.music_box,1), new Object[]{
        Blocks.NOTE_BLOCK, Blocks.NOTE_BLOCK, Blocks.NOTE_BLOCK,
        Blocks.NOTE_BLOCK, Blocks.NOTE_BLOCK, Blocks.NOTE_BLOCK,
        Blocks.NOTE_BLOCK, Blocks.NOTE_BLOCK, Blocks.NOTE_BLOCK});
      if(Features.music_sheet.get()){
        RecipeUtil.register(new ItemStack(Core.music_sheet,1), new Object[]{ "paper", Blocks.NOTE_BLOCK});
      }
    }
    if(Features.scythes.get()){
      ScytheTool.registerRecipe(Core.wooden_scythe, "plankWood");
      ScytheTool.registerRecipe(Core.stone_scythe, "cobblestone");
      ScytheTool.registerRecipe(Core.iron_scythe, "ingotIron");
      ScytheTool.registerRecipe(Core.gold_scythe, "ingotGold");
      ScytheTool.registerRecipe(Core.diamond_scythe, "gemDiamond");
    }
  }

}
