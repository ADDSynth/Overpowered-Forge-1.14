package addsynth.core.gameplay.commands;

import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.command.CommandUtil;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.entity.MobUtil;
import addsynth.core.util.math.CommonMath;
import addsynth.core.util.player.PlayerUtil;
import addsynth.core.util.time.TimeConstants;
import addsynth.core.util.time.WorldTime;
import addsynth.core.util.world.WorldUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public final class ZombieRaidCommand {

  private static final int MIN_ZOMBIES = 6;
  private static final int MAX_ZOMBIES = 250;
  private static final int MIN_ZOMBIE_RADIUS = 8;
  private static final int MAX_ZOMBIE_RADIUS = 50;
  private static final int MIN_DURATION = 60;
  private static final int MAX_DURATION = (int)WorldTime.night_length_in_seconds;

  private static final int DEFAULT_ZOMBIES = 100;
  private static final int DEFAULT_ZOMBIE_RADIUS = 32;
  private static final int DEFAULT_DURATION = MAX_DURATION; // in seconds, e.i. the whole night

  private static ServerWorld world;
  private static boolean do_zombie_raid;
  private static int zombie_tick_count;
  private static int zombie_raid_time;
  private static long previous_world_time;

  private static final ArrayList<ZombieEntity> zombies = new ArrayList<>();
  private static final ArrayList<ServerPlayerEntity> players = new ArrayList<>();

  public static final void register(CommandDispatcher<CommandSource> dispatcher){
    dispatcher.register(
      Commands.literal(ADDSynthCore.MOD_ID).requires(
        (command_source) -> { return command_source.hasPermissionLevel(PermissionLevel.COMMANDS); }
      ).then(
        Commands.literal("zombie_raid").executes(
          (command_context) -> { return zombie_raid(command_context.getSource(), DEFAULT_ZOMBIES, DEFAULT_ZOMBIE_RADIUS, DEFAULT_DURATION); }
        ).then(
          Commands.literal("start").then(
            Commands.argument("zombies", IntegerArgumentType.integer(MIN_ZOMBIES, MAX_ZOMBIES)
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
          )
        ).then(
          Commands.literal("stop").executes(
            (command_context) -> {return stop_zombie_raid(command_context.getSource()); }
          )
        ).then(
          Commands.literal("query").executes(
            (command_context) -> {return query_zombie_raid(command_context.getSource()); }
          )
        )
      )
    );
  }

  public static final void tick(){
    if(do_zombie_raid){
      // check win
      zombies.removeIf((ZombieEntity z) -> {return !z.isAlive(); });
      if(zombies.size() == 0){
        do_zombie_raid = false;
        message_all_players("commands.addsynthcore.zombie_raid.win");
        return_world_time();
      }
      
      // check time elapsed
      if(do_zombie_raid){
        zombie_tick_count += 1;
        if(zombie_tick_count >= zombie_raid_time){
          // zombie raid stopped normally
          // if all the timing calculations were correct, it should be day now,
          // the zombies should be dying and the night vision effect is worn out.
          if(world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE) == false){
            WorldUtil.set_time(world, previous_world_time);
          }
          message_all_players("commands.addsynthcore.zombie_raid.end");
          do_zombie_raid = false;
        }
      }
    }
  }

  @SuppressWarnings("resource")
  private static final int zombie_raid(final CommandSource command_source, final int number_of_zombies, final int radius, final int duration) throws CommandSyntaxException {
    CommandUtil.check_argument("zombies", number_of_zombies, MIN_ZOMBIES, MAX_ZOMBIES);
    CommandUtil.check_argument("radius", radius, MIN_ZOMBIE_RADIUS, MAX_ZOMBIE_RADIUS);
    CommandUtil.check_argument("duration", duration, MIN_DURATION, MAX_DURATION);
    
    // get world and position
    final BlockPos position = new BlockPos(command_source.getPos());
    world = command_source.func_197023_e(); // PRIORITY: run zombie raids on a per-world basis. Use a Zombie Raid struct that contains all the data. Possibly do the same for the lightning storm command.
    zombies.clear();
    players.clear();
    
    // get data
    final int[] y_level_adjust = {0, 1, -1, 2, -2, 3, -3, 4, -4, 5, -5, 6, -6};//, 7, -7, 8, -8};
    int i;
    int r = radius;
    int circumference = Math.min((int)Math.round(radius * 2 * Math.PI), number_of_zombies);
    int count = 0;
    int index;
    
    // begin loop
    double spawn_x;
    double spawn_z;
    BlockPos block_position = position;
    int y_check;
    int round_x;
    int round_z;
    int ground_level;
    for(i = 0; i < number_of_zombies; i++){
      
      // update circumference
      index = i - count;
      if(index > circumference){
        r += 1;
        count = i;
        circumference = Math.min((int)Math.round(r * 2 * Math.PI), number_of_zombies - i);
        index = 0;
      }
      
      // get spawn position
      spawn_x = position.getX() + (Math.cos(((double)index / circumference) * 2 * Math.PI) * r) + 0.5;
      spawn_z = position.getZ() + (Math.sin(((double)index / circumference) * 2 * Math.PI) * r) + 0.5;
      round_x = (int)Math.floor(spawn_x);
      round_z = (int)Math.floor(spawn_z);
      for(y_check = 0; y_check < y_level_adjust.length; y_check++){
        ground_level = WorldUtil.getTopMostFreeSpace(world, round_x, round_z);
        block_position = new BlockPos(round_x, Math.min(Math.max(position.getY() + y_level_adjust[y_check], 0), ground_level), round_z);
        if(world.isAirBlock(block_position) && world.isAirBlock(block_position.add(0, 1, 0))){
          break;
        }
      }
      
      // spawn zombie
      if(y_check < y_level_adjust.length){
        final ZombieEntity entity = EntityType.ZOMBIE.create(world);
        if(entity != null){
          final float angle = ((((float)index / circumference) + 0.25f) * 360 ) % 360.0f;
          MobUtil.setPosition(entity, spawn_x, block_position.getY(), spawn_z);
          MobUtil.setEntityFacingDirection(entity, angle);
          world.summonEntity(entity);
          // if(randomizeProperties){
          //   entity.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.COMMAND, null, null);
          // }
          zombies.add(entity);
        }
      }
    }
    
    zombie_raid_time = duration * TimeConstants.ticks_per_second;

    // set time to day - duration
    final MinecraftServer server = command_source.getServer();
    previous_world_time = world.getDayTime();
    WorldUtil.set_time(server,
      CommonMath.CeilingNearest(world.getDayTime(), WorldTime.minecraft_day_in_ticks) // round to start of next day (0)
      - 540 // subtract the part of sunrise where zombies can still burn
      - zombie_raid_time
    );
    // get all players inside radius
    PlayerUtil.allPlayersWithinHorizontalDistance(server, world, position, radius, (ServerPlayerEntity player) -> {
      if(player.isAlive()){
        players.add(player);
      }
    });
    
    // Start zombie raid
    // TODO: Look into using a boss bar / raid bar to track the player's progress.
    zombie_tick_count = 0;
    do_zombie_raid = true;
    for(ServerPlayerEntity player : players){
      // give all players inside radius night vision
      player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, duration * TimeConstants.ticks_per_second, 0, false, false));
      player.sendMessage(new TranslationTextComponent("commands.addsynthcore.zombie_raid.start", number_of_zombies, duration, radius));
    }

    return number_of_zombies;
  }
  
  private static final int stop_zombie_raid(final CommandSource source){
    if(do_zombie_raid){
      do_zombie_raid = false;
      return_world_time();
      for(ZombieEntity zombie : zombies){
        if(zombie.isAlive()){
          // zombie.setHealth(0.0f);
          zombie.remove();
        }
      }
      zombies.clear();
      // remove night vision from all players? NO
      message_all_players("commands.addsynthcore.zombie_raid.stop");
      return 1;
    }
    source.sendFeedback(new TranslationTextComponent("commands.addsynthcore.zombie_raid.not_occurring"), false);
    return 0;
  }
  
  private static final int query_zombie_raid(final CommandSource source){
    if(do_zombie_raid){
      final int seconds_remaining = (int)Math.ceil((double)(zombie_raid_time - zombie_tick_count) / TimeConstants.ticks_per_second);
      source.sendFeedback(new TranslationTextComponent("commands.addsynthcore.zombie_raid.occurring", seconds_remaining), false);
      return seconds_remaining;
    }
    source.sendFeedback(new TranslationTextComponent("commands.addsynthcore.zombie_raid.not_occurring"), false);
    return 0;
  }
  
  @SuppressWarnings("resource")
  private static final void return_world_time(){
    if(world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)){
      WorldUtil.set_time(world.getServer(), WorldTime.getNextDay(world));
    }
    else{
      WorldUtil.set_time(world.getServer(), previous_world_time);
    }
  }
  
  private static final void message_all_players(final String translation_key){
    players.removeIf((ServerPlayerEntity player) -> {return !player.isAlive(); });
    for(ServerPlayerEntity player : players){
      player.sendMessage(new TranslationTextComponent(translation_key));
    }
  }
  
}
