package addsynth.core.util.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public final class CommandUtil {

  // private static final SimpleCommandExceptionType ARGUMENT_OUT_OF_RANGE =
  //   new SimpleCommandExceptionType(new StringTextComponent("Too many items specified. Only a maximum of "+MAX_ITEMS+" are allowed."));

  public static final boolean isPlayer(final CommandSource command_source) throws CommandSyntaxException {
    if(command_source.getEntity() instanceof ServerPlayerEntity){
      return true;
    }
    // translation key copied from CommandSource.REQUIRES_PLAYER_EXCEPTION_TYPE
    // command_source.sendFeedback(new TranslationTextComponent("permissions.requires.player"), false);
    throw CommandSource.REQUIRES_PLAYER_EXCEPTION_TYPE.create();
    // return false;
  }

  public static final void check_argument(final String argument, final double value, final double min, final double max) throws CommandSyntaxException {
    if(value > max || value < min){
      throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.addsynthcore.argument_fail", argument, min, max)).create();
    }
  }

}
