package addsynth.core.container.slots;

import javax.annotation.Nonnull;
import addsynth.core.game.inventory.IInputInventory;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraftforge.items.SlotItemHandler;

/** REPLICA: Although it isn't likely to change very often, this is a replica of {@link FurnaceFuelSlot}
 */
public final class FuelSlot extends SlotItemHandler {

  public FuelSlot(IInputInventory tile, int index, int xPosition, int yPosition){
    super(tile.getInputInventory(), index, xPosition, yPosition);
  }

  @Override
  public boolean isItemValid(@Nonnull ItemStack stack){
    // Let's hold off on Lava Buckets for now.
    return AbstractFurnaceTileEntity.isFuel(stack) && stack.getItem() != Items.LAVA_BUCKET;
  }

}
