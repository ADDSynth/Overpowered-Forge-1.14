package addsynth.core.inventory.container.slots;

import javax.annotation.Nonnull;
import addsynth.core.tiles.TileMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.SlotItemHandler;

public final class OutputSlot extends SlotItemHandler {

  public OutputSlot(TileMachine tile, int index, int xPosition, int yPosition) {
    super(tile.getOutputInventory(), index, xPosition, yPosition);
  }

  @Override
  public final boolean isItemValid(@Nonnull final ItemStack stack){
    return false;
  }

  // NOTE: warning. it's possible this is run on client and server!
  @Override
  public ItemStack onTake(EntityPlayer player, ItemStack stack){
    FMLCommonHandler.instance().firePlayerCraftingEvent(player, stack, null);
    onSlotChanged();
    return stack;
  }

}
