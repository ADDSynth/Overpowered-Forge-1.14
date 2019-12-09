package addsynth.core.gameplay;

import java.util.HashSet;
import java.util.Set;
import addsynth.core.config.Features;
import addsynth.core.game.Compatability;
import addsynth.core.gameplay.items.ScytheTool;
import net.minecraft.block.Block;
import net.minecraft.item.ToolItem;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public final class CompatabilityManager {

  public static final void init(){
    if(Features.scythes.get()){
      set_scythe_harvest_blocks();
    }
  }

  /** Automatically gets all blocks that are tagged with the "leaves" tag
   *  and uses reflection to set the effectiveBlocks list of all Scythe tools.
   */
  private static final void set_scythe_harvest_blocks(){
    final Set<Block> leaves = (Set<Block>)BlockTags.LEAVES.getAllElements(); // TEST, if this doesn't work use HashSet
    override_scythe_field(Core.wooden_scythe, leaves);
    override_scythe_field(Core.stone_scythe, leaves);
    override_scythe_field(Core.iron_scythe, leaves);
    override_scythe_field(Core.gold_scythe, leaves);
    override_scythe_field(Core.diamond_scythe, leaves);
    if(Compatability.OVERPOWERED.loaded){ // TEMP
      override_scythe_field(addsynth.overpoweredmod.game.core.Tools.energy_scythe, leaves);
    }
  }

  private static final void override_scythe_field(final ScytheTool tool, final Set<Block> leaves){
    try{
      ObfuscationReflectionHelper.setPrivateValue(ToolItem.class, tool, leaves, "field_150914_c"); // "effectiveBlocks"
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

}
