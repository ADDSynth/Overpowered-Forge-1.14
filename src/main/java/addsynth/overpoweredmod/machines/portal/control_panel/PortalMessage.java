package addsynth.overpoweredmod.machines.portal.control_panel;

public enum PortalMessage {

  REQUIRE_PORTAL_FRAMES ("Portal requires 8 Portal Frame Blocks."),
  PORTAL_NOT_CONSTRUCTED("Portal is not constructed correctly."),
  INCORRECT_ITEMS       ("Incorrect items in Portal Frames."),
  NEEDS_ENERGY          ("Needs Energy."),
  PORTAL_READY          ("Portal is Ready.");

  private final String message;

  private PortalMessage(final String message){
    this.message = message;
  }

  public final String getMessage(){ // TODO: pass language key and return translated string instead, in version 1.3.
    return message;
  }

}
