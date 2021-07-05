package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.util.StringUtil;
import addsynth.core.util.color.ColorCode;

public final class TeamManagerMessage {

  public static final String must_specify_name(){
    return ColorCode.ERROR.toString() + StringUtil.translate("gui.addsynthcore.team_manager.message.id_required");
  }
  
  public static final String cannot_contain_spaces(){
    return ColorCode.ERROR.toString() + StringUtil.translate("gui.addsynthcore.team_manager.message.no_spaces");
  }
  
  public static final String must_be_shorter(){
    return ColorCode.ERROR.toString() + StringUtil.translate("gui.addsynthcore.team_manager.message.must_be_shorter_than_16_characters");
  }
  
  public static final String name_already_exists(){
    return ColorCode.WARNING.toString() + StringUtil.translate("gui.addsynthcore.team_manager.message.name_already_exists");
  }
  
  public static final String must_specify_criteria(){
    return ColorCode.ERROR.toString() + StringUtil.translate("gui.addsynthcore.team_manager.message.criteria_required");
  }
  
  public static final String score_is_readonly(){
    return StringUtil.translate("gui.addsynthcore.team_manager.message.readonly_score");
  }

}
