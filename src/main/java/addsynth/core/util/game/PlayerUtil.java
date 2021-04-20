package addsynth.core.util.game;

import addsynth.core.items.ItemUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class PlayerUtil {

  public static final void add_to_player_inventory(final PlayerEntity player, final ItemStack stack){
    if(player.inventory.addItemStackToInventory(stack) == false){
      player.dropItem(stack, false);
    }
  }

  public static final boolean isPlayerHoldingItem(final PlayerEntity player, final Item item){
    final ItemStack stack = player.getHeldItemMainhand();
    if(ItemUtil.itemStackExists(stack)){
      return stack.getItem() == item;
    }
    return false;
  }

}
