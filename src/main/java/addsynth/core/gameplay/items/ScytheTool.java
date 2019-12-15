package addsynth.core.gameplay.items;

import java.util.HashSet;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;

public class ScytheTool extends ToolItem {

  public ScytheTool(final String name, final IItemTier material){
    super(1.5f, -3.0f, material, new HashSet<Block>(), new Item.Properties().group(ADDSynthCore.creative_tab));
    ADDSynthCore.registry.register_item(this, name);
  }

  public ScytheTool(final String name, final IItemTier material, final RegistryUtil registry, final Item.Properties properties){
    super(1.5f, -3.0f, material, new HashSet<Block>(), properties);
    registry.register_item(this, name);
  }

}
