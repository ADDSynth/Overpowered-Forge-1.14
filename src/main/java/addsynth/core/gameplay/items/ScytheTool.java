package addsynth.core.gameplay.items;

import java.util.HashSet;
import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;

public class ScytheTool extends ToolItem {

  public ScytheTool(final String name, final IItemTier material){
    super(1.5f, -3.0f, material, new HashSet<Block>(), new Item.Properties());
    ADDSynthCore.registry.register_item(this, name);
  }

}
