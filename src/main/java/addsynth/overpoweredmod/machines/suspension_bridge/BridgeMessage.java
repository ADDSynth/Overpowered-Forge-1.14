package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.util.StringUtil;

public enum BridgeMessage {

  PENDING(       "gui.overpowered.bridge_message.checking",   false),
  OKAY(          "gui.overpowered.bridge_message.okay",       true),
  OBSTRUCTED(    "gui.overpowered.bridge_message.obstructed", false),
  NO_BRIDGE(     "gui.overpowered.bridge_message.no_bridge",  false),
  INVALID_BRIDGE("gui.overpowered.bridge_message.invalid",    false),
  NO_LENS(       "gui.overpowered.bridge_message.no_lens",    false),
  ACTIVE(        "gui.overpowered.bridge_message.active",     true);

  private final String translation_key;
  private final boolean valid;
  
  BridgeMessage(final String translation_key, final boolean valid){
    this.translation_key = translation_key;
    this.valid = valid;
  }

  public final String getMessage(){
    return StringUtil.translate(translation_key);
  }

  public final boolean is_valid(){
    return valid;
  }

}
