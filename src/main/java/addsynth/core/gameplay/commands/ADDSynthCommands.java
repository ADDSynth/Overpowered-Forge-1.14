package addsynth.core.gameplay.commands;

import addsynth.core.ADDSynthCore;
import addsynth.core.config.Features;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;

/** @see net.minecraft.network.play.ServerPlayNetHandler#handleSlashCommand
 *  @see net.minecraft.server.dedicated.DedicatedServer#handleConsoleInput
 *  @see net.minecraft.command.Commands#handleCommand
 */
public final class ADDSynthCommands {

  public static final void register(CommandDispatcher<CommandSource> dispatcher){
    if(Features.item_explosion_command.get()){
      ItemExplosionCommand.register(dispatcher);
    }
    if(Features.zombie_raid_command.get()){
      ZombieRaidCommand.register(dispatcher);
    }
    if(Features.blackout_command.get()){
      BlackoutCommand.register(dispatcher);
    }
    if(Features.lightning_storm_command.get()){
      LightningStormCommand.register(dispatcher);
    }
  }

  /** This runs every server tick (20 times a second). Assigned to the Forge Event bus by {@link ADDSynthCore}. */
  public static final void tick(final ServerTickEvent tick_event){
    if(tick_event.phase == Phase.START){
      ZombieRaidCommand.tick();
      LightningStormCommand.tick();
    }
  }

}
