package addsynth.overpoweredmod.game.core;

import addsynth.material.Material;
import addsynth.material.types.Metal;
import addsynth.overpoweredmod.Debug;

public final class Metals {

  static {
    Debug.log_setup_info("Begin loading Metals class...");
  }

  public static final Metal IRON     = Material.IRON;
  public static final Metal GOLD     = Material.GOLD;
  public static final Metal TIN      = Material.TIN;
  public static final Metal COPPER   = Material.COPPER;
  public static final Metal ALUMINUM = Material.ALUMINUM;
  public static final Metal STEEL    = Material.STEEL;
  public static final Metal BRONZE   = Material.BRONZE;
  public static final Metal SILVER   = Material.SILVER;
  public static final Metal PLATINUM = Material.PLATINUM;
  public static final Metal TITANIUM = Material.TITANIUM;

  public static final Metal[] values = new Metal[] {
    IRON, GOLD, TIN, COPPER, ALUMINUM, STEEL, BRONZE, SILVER, PLATINUM, TITANIUM
  };

  static {
    Debug.log_setup_info("Finished loading Metals class.");
  }

}
