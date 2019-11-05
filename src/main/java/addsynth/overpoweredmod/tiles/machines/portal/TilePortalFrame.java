package addsynth.overpoweredmod.tiles.machines.portal;

import addsynth.core.inventory.SlotData;
import addsynth.core.tiles.TileMachine;
import addsynth.overpoweredmod.game.core.Gems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class TilePortalFrame extends TileMachine {

  public static final Item[] input_filter = Gems.gem_block_items;

  public TilePortalFrame(){
    super(new SlotData[]{new SlotData(input_filter, 1)}, 0);
  }

  public final Item get_item(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    return stack.isEmpty() ? null : stack.getItem();
  }

}
