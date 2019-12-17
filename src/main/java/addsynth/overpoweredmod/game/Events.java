package addsynth.overpoweredmod.game;

// import addsynth.core.game.Game;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
// import addsynth.overpoweredmod.assets.Achievements;
// import addsynth.overpoweredmod.game.core.Gems;
// import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.ModItems;
// import addsynth.overpoweredmod.game.core.Tools;
// import addsynth.overpoweredmod.game.core.Trophy;
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
  public static final void pick_up_item(ItemPickupEvent event){
    final Item item = event.getStack().getItem();
    final PlayerEntity player = event.getPlayer();
    /*
    if(item == Gems.ruby){
      player.addStat(Achievements.FIND_RUBY);
    }
    if(item == Gems.topaz){
      player.addStat(Achievements.FIND_TOPAZ);
    }
    if(item == Gems.citrine){
      player.addStat(Achievements.FIND_CITRINE);
    }
    if(item == Gems.emerald){
      player.addStat(Achievements.FIND_EMERALD);
    }
    if(item == Gems.sapphire){
      player.addStat(Achievements.FIND_SAPPHIRE);
    }
    if(item == Gems.amethyst){
      player.addStat(Achievements.FIND_AMETHYST);
    }
    if(item == Gems.quartz){
      player.addStat(Achievements.FIND_QUARTZ);
    }
    if(item == Trophy.BRONZE.item_block){
      player.addStat(Achievements.BRONZE_TROPHY);
    }
    if(item == Trophy.SILVER.item_block){
      player.addStat(Achievements.SILVER_TROPHY);
    }
    if(item == Trophy.GOLD.item_block){
      player.addStat(Achievements.GOLD_TROPHY);
    }
    if(item == Trophy.PLATINUM.item_block){
      player.addStat(Achievements.PLATINUM_TROPHY);
    }
    */
    if(item == ModItems.unknown_technology){
      // Game.activate_achievement(player, Achievements.UNKNOWN_TECHNOLOGY);
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
  public static final void craft_event(ItemCraftedEvent event){
    final Item item = event.getCrafting().getItem();
    final PlayerEntity player = event.getPlayer();
    /*
    if(item == Init.energy_crystal){
      Game.activate_achievement(player, Achievements.ENERGY_CRYSTAL);
    }
    if(item == Item.getItemFromBlock(Init.light_block)){
      Game.activate_achievement(player, Achievements.LIGHT_BLOCK);
    }
    if(item == Tools.energy_tools.sword || item == Tools.energy_tools.pickaxe || item == Tools.energy_tools.axe ||
       item == Tools.energy_tools.shovel || item == Tools.energy_tools.hoe){
      Game.activate_achievement(player, Achievements.ENERGY_TOOLS);
    }
    if(item == Init.void_crystal){
      Game.activate_achievement(player, Achievements.VOID_CRYSTAL);
    }
    if(item == Item.getItemFromBlock(Init.null_block)){
      Game.activate_achievement(player, Achievements.NULL_BLOCK);
    }
    if(item == Tools.void_toolset.sword){
      Game.activate_achievement(player, Achievements.DEATH_BLADE);
    }
    if(item == Tools.magic_ring[0] || item == Tools.magic_ring[1] ||
       item == Tools.magic_ring[2] || item == Tools.magic_ring[3]){
      Game.activate_achievement(player, Achievements.IDENTIFY_RING);
    }
    if(item == Item.getItemFromBlock(Machines.fusion_converter)){
      Game.activate_achievement(player, Achievements.FUSION_CONTAINER);
    }
    if(item == Init.black_hole_item){
      Game.activate_achievement(player, Achievements.BLACK_HOLE);
    }
    if(item == ModItems.dimensional_anchor){
      Game.activate_achievement(player, Achievements.DIMENSIONAL_ANCHOR);
    }
    if(item == Trophy.BRONZE.item_block){
      player.addStat(Achievements.BRONZE_TROPHY);
    }
    if(item == Trophy.SILVER.item_block){
      player.addStat(Achievements.SILVER_TROPHY);
    }
    if(item == Trophy.GOLD.item_block){
      player.addStat(Achievements.GOLD_TROPHY);
    }
    if(item == Trophy.PLATINUM.item_block){
      player.addStat(Achievements.PLATINUM_TROPHY);
    }
    */
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
