package addsynth.overpoweredmod.game;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
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
    if(item == ModItems.unknown_technology){
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

  @SubscribeEvent
  public static final void craft_event(final ItemCraftedEvent event){
    final Item item = event.getCrafting().getItem();
    final PlayerEntity player = event.getPlayer();
    if(item == Item.BLOCK_TO_ITEM.get(Machines.portal_control_panel)){
      if(EffectiveSide.get() == LogicalSide.CLIENT){
        player.sendMessage(new StringTextComponent(
          TextFormatting.RED+"Beware"+TextFormatting.RESET+": Traveling to the Unknown Dimension "+
          "is still buggy. There's a chance you may spawn in the air and fall to your death! "+
          "(I believe this occurs when you enter for the first time because the dimension hasn't "+
          "loaded yet.) We recommend entering the portal in Creative Mode, or disabling the Portal "+
          "in the config. (Boots with Feather Falling IV won't work. I've tried.) If you're quick "+
          "to enter the portal a second time before the portal goes away you might spawn on the "+
          "ground. Enter at your own risk! This will get fixed in a later update."));
      }
    }
  }

  /** @see net.minecraftforge.common.ForgeHooks#onLivingAttack */
  @SubscribeEvent
  public static final void onLivingEntityAttacked(final LivingAttackEvent event){
  }

  // DELETE these if they don't get used.

  @SubscribeEvent
  public static final void onAttackLivingEntity(final AttackEntityEvent event){
  }

}
