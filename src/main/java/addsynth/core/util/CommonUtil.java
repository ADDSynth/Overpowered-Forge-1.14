package addsynth.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import addsynth.core.util.constants.DevStage;

public final class CommonUtil {

  public static final String get_mod_info(String mod_name, String author, String version, DevStage dev_stage, String date){
    if(dev_stage.isDevelopment){
      return StringUtil.build(mod_name, " by ", author, ", version ", version, dev_stage.label, ", built on ", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), ".");
    }
    return StringUtil.build(mod_name, " by ", author, ", version ", version, ", built on ", date, ".");
  }

}
