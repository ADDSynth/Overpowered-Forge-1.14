package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.StringUtil;
import addsynth.core.util.color.ColorCode;
import net.minecraft.util.text.TextFormatting;

public enum BridgeMessage {

  PENDING       (ColorCode.ERROR, "gui.overpowered.bridge_message.checking",      false),
  INVALID_BRIDGE(ColorCode.ERROR, "gui.overpowered.bridge_message.invalid",       false),
  INVALID_SHAPE (ColorCode.ERROR, "gui.overpowered.bridge_message.invalid_shape", false),
  OKAY          (ColorCode.GOOD,  "gui.overpowered.bridge_message.okay",          true),
  OBSTRUCTED    (ColorCode.ERROR, "gui.overpowered.bridge_message.obstructed",    false),
  NO_BRIDGE     (null,            "gui.overpowered.bridge_message.no_bridge",     false),
  NO_LENS       (ColorCode.ERROR, "gui.overpowered.bridge_message.no_lens",       false),
  OFF           (null,            "gui.overpowered.bridge_message.off",           true),
  ACTIVE        (ColorCode.GOOD,  "gui.overpowered.bridge_message.active",        true);

  private final String translation_key;
  private final String formatting_code;
  private final boolean valid;
  
  BridgeMessage(final TextFormatting code, final String translation_key, final boolean valid){
    this.translation_key = translation_key;
    this.formatting_code = code != null ? code.toString() : "";
    this.valid = valid;
  }

  public final String getMessage(){
    return formatting_code + StringUtil.translate(translation_key);
  }

  public final boolean is_valid(){
    return valid;
  }

}
