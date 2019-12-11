package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.blocks.tiles.laser.LaserBeam;
import addsynth.overpoweredmod.blocks.tiles.laser.LaserCannon;
import net.minecraft.block.Block;

public enum Laser {

  WHITE  ("white"),
  RED    ("red"),
  ORANGE ("orange"),
  YELLOW ("yellow"),
  GREEN  ("green"),
  CYAN   ("cyan"),
  BLUE   ("blue"),
  MAGENTA("magenta");

  static {
    Debug.log_setup_info("Begin loading Lasers class...");
  }

  public final LaserCannon cannon;
  public final Block beam;

  public static final Laser[] index = Laser.values();
  public static final Block[] cannons = {WHITE.cannon, RED.cannon, ORANGE.cannon, YELLOW.cannon, GREEN.cannon, CYAN.cannon, BLUE.cannon, MAGENTA.cannon};
  public static final Block[] beams = {WHITE.beam, RED.beam, ORANGE.beam, YELLOW.beam, GREEN.beam, CYAN.beam, BLUE.beam, MAGENTA.beam};
  
  private Laser(final String color){
    this.cannon = new LaserCannon(color+"_laser", this.ordinal());
    this.beam   = new LaserBeam(color+"_laser_beam");
  }

  static {
    Debug.log_setup_info("Finished loading Lasers class.");
  }

}
