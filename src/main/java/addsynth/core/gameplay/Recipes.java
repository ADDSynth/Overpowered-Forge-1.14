package addsynth.core.gameplay;

import addsynth.core.config.Features;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.util.RecipeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public final class Recipes {

  public static final void register(){
    if(Features.caution_block){
      RecipeUtil.register(3, 3, new ItemStack(Core.caution_block,8), new Object[]{
        "dyeYellow", "dyeBlack", "dyeYellow",
        "dyeBlack",  "stone",    "dyeBlack",
        "dyeYellow", "dyeBlack", "dyeYellow"});
    }
    if(Features.music_box){
      RecipeUtil.registerMachine(new ItemStack(Core.music_box,1), new Object[]{
        Blocks.NOTEBLOCK, Blocks.NOTEBLOCK, Blocks.NOTEBLOCK,
        Blocks.NOTEBLOCK, Blocks.NOTEBLOCK, Blocks.NOTEBLOCK,
        Blocks.NOTEBLOCK, Blocks.NOTEBLOCK, Blocks.NOTEBLOCK});
      if(Features.music_sheet){
        RecipeUtil.register(new ItemStack(Core.music_sheet,1), new Object[]{ "paper", Blocks.NOTEBLOCK});
      }
    }
    if(Features.scythes){
      ScytheTool.registerRecipe(Core.wooden_scythe, "plankWood");
      ScytheTool.registerRecipe(Core.stone_scythe, "cobblestone");
      ScytheTool.registerRecipe(Core.iron_scythe, "ingotIron");
      ScytheTool.registerRecipe(Core.gold_scythe, "ingotGold");
      ScytheTool.registerRecipe(Core.diamond_scythe, "gemDiamond");
    }
  }

}
