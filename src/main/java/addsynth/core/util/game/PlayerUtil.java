package addsynth.core.util.game;

import addsynth.core.items.ItemUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

  /** Gets the {@link ServerPlayerEntity} using the player's name.
   *  Get the Player's name by calling PlayerEntity.getGameProfile().getName().
   *  This must be called on the server side, otherwise it will return null.
   *  Also returns null if the player isn't on the server at the moment.
   * @param world
   * @param player_name
   */
  @SuppressWarnings({ "resource", "null", "deprecation" })
  public static final ServerPlayerEntity getPlayer(World world, String player_name){
    if(world == null){
      return ServerUtils.getServer().getPlayerList().getPlayerByUsername(player_name);
    }
    if(world.isRemote == false){
      return world.getServer().getPlayerList().getPlayerByUsername(player_name);
    }
    return null;
  }

}
