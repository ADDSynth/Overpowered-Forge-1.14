package addsynth.core.gameplay.commands;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.command.CommandUtil;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.math.BlockMath;
import addsynth.core.util.world.WorldUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public final class BlackoutCommand {

  private static final int MAX_BATS = 2_000;
  private static final int DEFAULT_BATS = 200;

  private static final int up_in_the_sky = 10;
  private static final int horizontal_radius = 19;
  private static final int vertical_radius = 1;

  public static final void register(CommandDispatcher<CommandSource> dispatcher){
    dispatcher.register(
      Commands.literal(ADDSynthCore.MOD_ID).requires(
        (command_source) -> { return command_source.hasPermissionLevel(PermissionLevel.COMMANDS); }
      ).then(
        Commands.literal("blackout").executes(
          (command_context) -> { return blackout(command_context.getSource(), DEFAULT_BATS); }
        ).then(
          Commands.argument("size", IntegerArgumentType.integer(1, MAX_BATS)).executes(
            (command_context) -> { return blackout(command_context.getSource(), IntegerArgumentType.getInteger(command_context, "size")); }
          )
        )
      )
    );
  }

  @SuppressWarnings("resource")
  private static final int blackout(final CommandSource command_source, final int number_of_bats) throws CommandSyntaxException {
    CommandUtil.check_argument("size", number_of_bats, 1, MAX_BATS);
    // get data
    final Vec3d position = command_source.getPos();
    final ServerWorld world = command_source.func_197023_e();
    final int x = (int)Math.floor(position.x);
    final int z = (int)Math.floor(position.z);
    // get top-most block over player
    final BlockPos origin = new BlockPos(x, WorldUtil.getTopMostFreeSpace(world, x, z) + 12, z);
    // WorldUtil.getTopMostFreeSpace(world, x, z) + up_in_the_sky + (vertical_radius / 2),
    // spawn bats
    int count = 0;
    for(final BlockPos pos : BlockMath.getBlockPositionsAroundPillar(origin, horizontal_radius, vertical_radius)){
      if(world.isAirBlock(pos)){
        final BatEntity bat = new BatEntity(EntityType.BAT, world);
        bat.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        world.addEntity(bat);
        count += 1;
        if(count >= number_of_bats){
          break;
        }
      }
    }
    // feeback
    command_source.sendFeedback(new StringTextComponent(
      command_source.getDisplayName().getFormattedText()+" has just summoned "+count+" Bats.\n"+
      "Use command §a/kill @e[type=minecraft:bat]§r to get rid of them."
    ), true);
    return number_of_bats;
  }

}
