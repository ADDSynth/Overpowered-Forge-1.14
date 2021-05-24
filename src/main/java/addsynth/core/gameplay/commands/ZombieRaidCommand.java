package addsynth.core.gameplay.commands;

import addsynth.core.ADDSynthCore;
import addsynth.core.Constants;
import addsynth.core.util.command.CommandUtil;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.player.PlayerUtil;
import addsynth.core.util.world.WorldUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public final class ZombieRaidCommand {

  private static final int MIN_ZOMBIES = 6;
  private static final int MAX_ZOMBIES = 500;
  private static final int MIN_ZOMBIE_RADIUS = 10;
  private static final int MAX_ZOMBIE_RADIUS = 100;
  private static final int MIN_DURATION = 60;
  private static final int MAX_DURATION = Constants.minecraft_half_day_in_seconds;

  private static final int DEFAULT_ZOMBIES = 100;
  private static final int DEFAULT_ZOMBIE_RADIUS = 32;
  private static final int DEFAULT_DURATION = MAX_DURATION; // in seconds, e.i. the whole night

  private static boolean do_zombie_raid;
  private static int zombie_tick_count;
  private static int zombie_raid_time;

  public static final void register(CommandDispatcher<CommandSource> dispatcher){
    dispatcher.register(
      Commands.literal(ADDSynthCore.MOD_ID).requires(
        (command_source) -> { return command_source.hasPermissionLevel(PermissionLevel.COMMANDS); }
      ).then(
        Commands.literal("zombie_raid").executes(
          (command_context) -> { return zombie_raid(command_context.getSource(), DEFAULT_ZOMBIES, DEFAULT_ZOMBIE_RADIUS, DEFAULT_DURATION); }
        ).then(
          Commands.argument("zombies", IntegerArgumentType.integer(MIN_ZOMBIES, MAX_ZOMBIES)).executes(
            (command_context) -> { return zombie_raid(command_context.getSource(), IntegerArgumentType.getInteger(command_context, "zombies"), DEFAULT_ZOMBIE_RADIUS, DEFAULT_DURATION); }
          ).then(
            Commands.argument("radius", IntegerArgumentType.integer(MIN_ZOMBIE_RADIUS, MAX_ZOMBIE_RADIUS)).executes(
              (command_context) -> {
                return zombie_raid(command_context.getSource(), IntegerArgumentType.getInteger(command_context, "zombies"),
                                                                IntegerArgumentType.getInteger(command_context, "radius"),
                                                                DEFAULT_DURATION);
              }
            ).then(
              Commands.argument("duration", IntegerArgumentType.integer(MIN_DURATION, MAX_DURATION)).executes(
                (command_context) -> {
                  return zombie_raid(command_context.getSource(), IntegerArgumentType.getInteger(command_context, "zombies"),
                                                                  IntegerArgumentType.getInteger(command_context, "radius"),
                                                                  IntegerArgumentType.getInteger(command_context, "duration"));
                }
              )
            )
          )
        ).then(
          Commands.literal("stop").executes(
            (command_context) -> {return stop_zombie_raid(command_context.getSource()); }
          )
        )
      )
    );
  }

  public static final void tick(){
    if(do_zombie_raid){
      zombie_tick_count += 1;
      if(zombie_tick_count >= zombie_raid_time){
        // zombie raid stopped normally
        // if all the timing calculations were correct, it should be day now,
        // the zombies should be dying and the night vision effect is worn out.
        do_zombie_raid = false;
      }
    }
  }

  @SuppressWarnings("resource")
  private static final int zombie_raid(final CommandSource command_source, final int zombies, final int radius, final int duration) throws CommandSyntaxException {
    CommandUtil.check_argument("zombies", zombies, MIN_ZOMBIES, MAX_ZOMBIES);
    CommandUtil.check_argument("radius", radius, MIN_ZOMBIE_RADIUS, MAX_ZOMBIE_RADIUS);
    CommandUtil.check_argument("duration", duration, MIN_DURATION, MAX_DURATION);
    
    // get world and position
    final BlockPos position = new BlockPos(command_source.getPos());
    final ServerWorld world = command_source.func_197023_e();
    
    // get data
    final int[] y_level_adjust = {0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6};//, 7, -7, 8, -8};
    int i;
    int r = radius;
    int circumference = Math.min((int)Math.round(radius * 2 * Math.PI), zombies);
    int count = 0;
    int index;
    
    // begin loop
    double spawn_x;
    double spawn_z;
    BlockPos block_position = position;
    int y_check;
    for(i = 0; i < zombies; i++){
      
      // update circumference
      index = i - count;
      if(index > circumference){
        r += 1;
        count = i;
        circumference = Math.min((int)Math.round(r * 2 * Math.PI), zombies - i);
        index = 0;
      }
      
      // get spawn position
      spawn_x = position.getX() + (Math.cos(((double)index / circumference) * 2 * Math.PI) * r) + 0.5;
      spawn_z = position.getZ() + (Math.sin(((double)index / circumference) * 2 * Math.PI) * r) + 0.5;
      for(y_check = 0; y_check < y_level_adjust.length; y_check++){
        block_position = new BlockPos((int)Math.floor(spawn_x), position.getY() + y_level_adjust[y_check], (int)Math.floor(spawn_z));
        if(world.isAirBlock(block_position) && world.isAirBlock(block_position.add(0, 1, 0))){
          break;
        }
      }
      
      // spawn zombie
      if(y_check < y_level_adjust.length){
        final ZombieEntity entity = new ZombieEntity(world);
        entity.setLocationAndAngles(spawn_x, block_position.getY(), spawn_z, ((float)index / circumference) + 0.5f, 0.0f);
        world.summonEntity(entity);
      }
    }
    
    // set time to day - duration
    final MinecraftServer server = command_source.getServer();
    WorldUtil.set_time(server, Constants.world_time_day - (duration * Constants.ticks_per_second));
    // give all players inside radius night vision
    PlayerUtil.allPlayersWithinHorizontalDistance(server, world, position, radius, (ServerPlayerEntity player) -> {
      player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, duration * Constants.ticks_per_second));
    });
    
    // Start zombie raid
    zombie_tick_count = 0;
    zombie_raid_time = duration * Constants.ticks_per_second;
    command_source.sendFeedback(new StringTextComponent("Zombie Raid started. Zombies: "+zombies+", Time: "+duration+", Distance: "+radius+"."), true);

    return zombies;
  }
  
  @SuppressWarnings("resource")
  private static final int stop_zombie_raid(final CommandSource source){
    do_zombie_raid = false;
    WorldUtil.set_time(source.getServer(), Constants.world_time_day);
    // kill all zombies? NO
    // remove night vision from all players? NO
    return 0;
  }
  
}
