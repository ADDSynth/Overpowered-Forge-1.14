package addsynth.overpoweredmod.game;

import addsynth.core.util.game.MessageUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;

/** Note: functions with the <code>@SubscribeEvent</code> annotation MUST be public! */
@Mod.EventBusSubscriber(modid = OverpoweredMod.MOD_ID)
public final class Events {

  static {
    Debug.log_setup_info("Events class was loaded.");
  }

  @SubscribeEvent
  public static final void pick_up_item(final ItemPickupEvent event){
    final Item item = event.getStack().getItem();
    final PlayerEntity player = event.getPlayer();
    if(item == Init.void_crystal){
      /*
      if(player.dimension.getId() == WeirdDimension.id){
        final MinecraftServer server = player.getServer();
        if(server != null){
          server.getPlayerList().transferPlayerToDimension((ServerPlayerEntity)player, 0, new CustomTeleporter(server.getWorld(0)));
        }
      }
      */
    }
  }

  /* DELETE when we implement the Unknown Dimension for 1.14+ delete this warning message and all translation keys.
  @SubscribeEvent
  public static final void craft_event(final ItemCraftedEvent event){
    final Item item = event.getCrafting().getItem();
    final PlayerEntity player = event.getPlayer();
    if(item == Item.BLOCK_TO_ITEM.get(Machines.portal_control_panel)){
      if(EffectiveSide.get() == LogicalSide.CLIENT){
        MessageUtil.send_to_player(player, "gui.overpowered.portal_warning_message");
      }
    }
  }
  */

  /** @see net.minecraftforge.common.ForgeHooks#onLivingAttack */
  @SubscribeEvent
  public static final void onLivingEntityAttacked(final LivingAttackEvent event){
  }

  // DELETE these if they don't get used.

  @SubscribeEvent
  public static final void onAttackLivingEntity(final AttackEntityEvent event){
  }

}
