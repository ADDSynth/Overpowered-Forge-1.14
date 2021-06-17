package addsynth.core.gameplay.commands;

import java.util.Random;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.math.MathUtility;
import addsynth.core.util.world.WorldUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public final class LightningStormCommand {

  private static final float MAX_CHANCE = 10.0f;

  private static final int DEFAULT_COUNT = 30;
  private static final int DEFAULT_RADIUS = 100;
  private static final int DEFAULT_DELAY = 10;
  private static final float DEFAULT_CHANCE = 0.1f;

  private static boolean do_lightning;
  private static BlockPos position;
  private static ServerWorld world;
  private static int lightning_count;
  private static int lightning_strikes;
  private static int lightning_radius;
  private static int tick_time;
  private static int lightning_delay;
  private static Random random = new Random();
  private static float lightning_chance;

  public static final void register(CommandDispatcher<CommandSource> dispatcher){
    dispatcher.register(
      Commands.literal(ADDSynthCore.MOD_ID).requires(
        (command_source) -> { return command_source.hasPermissionLevel(PermissionLevel.COMMANDS); }
      ).then(
        Commands.literal("lightning").executes(
          (command_context) -> { return lightning(command_context.getSource(), DEFAULT_COUNT, DEFAULT_RADIUS, DEFAULT_DELAY, DEFAULT_CHANCE); }
        ).then(
          Commands.literal("start").then(
            Commands.argument("count", IntegerArgumentType.integer(1)
            ).then(
              Commands.argument("radius", IntegerArgumentType.integer(0)
              ).then(
                Commands.argument("tick_delay", IntegerArgumentType.integer(1)
                ).then(
                  Commands.argument("chance", FloatArgumentType.floatArg(0.000001f, MAX_CHANCE)).executes(
                    (command_context) -> {
                      return lightning(command_context.getSource(), IntegerArgumentType.getInteger(command_context, "count"),
                                                                    IntegerArgumentType.getInteger(command_context, "radius"),
                                                                    IntegerArgumentType.getInteger(command_context, "tick_delay"),
                                                                    FloatArgumentType.getFloat(command_context, "chance"));
                    }
                  )
                )
              )
            )
          )
        ).then(
          Commands.literal("stop").executes(
            (command_context) -> { return stop_lightning(command_context.getSource()); }
          )
        ).then(
          Commands.literal("query").executes(
            (command_context) -> { return query_lightning(command_context.getSource()); }
          )
        )
      )
    );
  }

  public static final void tick(){
    if(do_lightning){
      try{
        // summon lightning
        tick_time += 1;
        if(tick_time >= lightning_delay){
          if(spawn_lightning()){
            lightning_count += 1;
            if(lightning_count >= lightning_strikes){
              do_lightning = false;
            }
          }
          tick_time = 0;
        }
      }
      catch(Exception e){
        e.printStackTrace();
        do_lightning = false;
      }
    }
  }

  private static final int lightning(final CommandSource command_source, final int strikes, final int radius, final int delay, final float chance) throws CommandSyntaxException {
    position = new BlockPos(command_source.getPos());
    world    = command_source.func_197023_e();
    lightning_strikes = strikes;
    lightning_radius  = radius;
    tick_time         = 0;
    lightning_delay   = delay;
    lightning_chance  = chance;
    lightning_count   = 0;
    do_lightning      = true;
    command_source.sendFeedback(new TranslationTextComponent("commands.addsynthcore.lightning_storm.success", strikes, radius, delay, chance), true);
    return strikes;
  }

  private static final boolean spawn_lightning(){
    int times = (int)lightning_chance;
    float chance = lightning_chance - times;
    if(chance > 0){
      if(random.nextFloat() < chance){
        times += 1;
      }
    }
    int i;
    int location_x;
    int location_z;
    for(i = 0; i < times; i++){
      do{
        location_x = position.getX();
        location_z = position.getZ();
        if(lightning_radius > 0){
          location_x += -lightning_radius + random.nextInt(lightning_radius * 2);
          location_z += -lightning_radius + random.nextInt(lightning_radius * 2);
        }
      }
      while(MathUtility.isWithin(position.getX(), position.getZ(), location_x, location_z, lightning_radius) == false);
      LightningBoltEntity lightning = new LightningBoltEntity(
        world,
        (double)location_x + 0.5,
        WorldUtil.getTopMostFreeSpace(world, location_x, location_z),
        (double)location_z + 0.5,
        false
      );
      world.addLightningBolt(lightning);
    }
    return times > 0;
  }

  private static final int stop_lightning(final CommandSource source){
    do_lightning = false;
    source.sendFeedback(new TranslationTextComponent("commands.addsynthcore.lightning_storm.stop"), false);
    return 0;
  }

  private static final int query_lightning(final CommandSource source){
    if(do_lightning){
      source.sendFeedback(
        new TranslationTextComponent("commands.addsynthcore.lightning_storm.query.true",
          lightning_count, lightning_strikes, StringUtil.toPercentageString(lightning_count, lightning_strikes)
        ), false
      );
      return lightning_strikes - lightning_count;
    }
    source.sendFeedback(new TranslationTextComponent("commands.addsynthcore.lightning_storm.query.false"), false);
    return 0;
  }

}
