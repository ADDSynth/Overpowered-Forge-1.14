package addsynth.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.annotation.Nonnegative;
import addsynth.core.ADDSynthCore;
import addsynth.core.Constants;
import addsynth.core.util.constants.DevStage;

public final class CommonUtil {

  public static final String get_mod_info(String mod_name, String author, String version, DevStage dev_stage, String date){
    if(dev_stage.isDevelopment){
      return StringUtil.build(mod_name, " by ", author, ", version ", version, "-", dev_stage.toString(), ", built on ", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), ".");
    }
    return StringUtil.build(mod_name, " by ", author, ", version ", version, ", built on ", date, ".");
  }

  public static final int getOppositeDirection(@Nonnegative final int direction){
    if(direction == Constants.DOWN ){ return Constants.UP;    }
    if(direction == Constants.UP   ){ return Constants.DOWN;  }
    if(direction == Constants.NORTH){ return Constants.SOUTH; }
    if(direction == Constants.SOUTH){ return Constants.NORTH; }
    if(direction == Constants.WEST ){ return Constants.EAST;  }
    if(direction == Constants.EAST ){ return Constants.WEST;  }
    ADDSynthCore.log.warn(new IllegalArgumentException("Invalid input direction for "+CommonUtil.class.getSimpleName()+".getOppositeDirection()! Only values 0 to 5 are acceptable!"));
    if((direction & 1) == 0){
      return (Math.abs(direction) + 1) % 6;
    }
    return (Math.abs(direction) - 1) % 6;
  }

}
