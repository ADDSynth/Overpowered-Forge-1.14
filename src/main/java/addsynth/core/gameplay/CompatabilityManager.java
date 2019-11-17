package addsynth.core.gameplay;

import java.util.HashSet;
import java.util.Set;
import addsynth.core.config.Features;
import addsynth.core.game.Compatability;
import addsynth.core.gameplay.items.ScytheTool;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

public final class CompatabilityManager {

  public static final void init(){
    if(Features.scythes){
      set_scythe_harvest_blocks();
    }
  }

  /** Automatically gets all blocks that are registered in the OreDictionary as "treeLeaves"
   *  and uses reflection to set the effectiveBlocks list of all Scythe tools.
   */
  private static final void set_scythe_harvest_blocks(){
    final NonNullList<ItemStack> list = OreDictionary.getOres("treeLeaves");
    final Set<Block> leaves = new HashSet<>(list.size());
    for(ItemStack stack : list){
      if(stack.getItem() instanceof BlockItem){
        leaves.add(((BlockItem)stack.getItem()).getBlock());
      }
    }
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
