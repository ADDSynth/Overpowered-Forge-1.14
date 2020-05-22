package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.util.ColorCode;
import net.minecraft.util.text.TextFormatting;

public enum PortalMessage {

  NO_DATA_CABLE         (ColorCode.ERROR, "No Data Cable connected."),
  REQUIRE_PORTAL_FRAMES (ColorCode.ERROR, "Portal requires 8 Portal Frame Blocks."),
  TOO_MANY_PORTAL_FRAMES(ColorCode.ERROR, "Too many Portal Frame Blocks."),
  PORTAL_NOT_CONSTRUCTED(ColorCode.ERROR, "Portal is not constructed correctly."),
  OBSTRUCTED            (ColorCode.ERROR, "Obstruction detected in portal frame."),
  OFF                   (null,            "Portal Control Panel is Off."),
  INCORRECT_ITEMS       (ColorCode.ERROR, "Incorrect items in Portal Frames."),
  NEEDS_ENERGY          (ColorCode.ERROR, "Needs Energy."),
  CREATIVE_MODE         (null,            "Player is in Creative Mode."),
  PORTAL_READY          (ColorCode.GOOD,  "Portal is Ready.");

  private final String message;
  private final String formatting_code;

  private PortalMessage(final TextFormatting code, final String message){
    this.message = message;
    this.formatting_code = code != null ? code.toString() : "";
  }

  public final String getMessage(){ // TODO: pass language key and return translated string instead, in version 1.3.
    return formatting_code + message;
  }

}
