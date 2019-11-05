package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.compatability.baubles.DimensionAnchor;
import addsynth.overpoweredmod.items.FusionCore;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.UnknownTechnology;
import net.minecraft.item.Item;

public final class ModItems {

  static {
    Debug.log_setup_info("Begin loading Items class...");
  }

  public static final Item            power_core               = new OverpoweredItem("power_core");
  public static final Item            advanced_power_core      = new OverpoweredItem("advanced_power_core");
  public static final Item            energized_power_core     = new OverpoweredItem("energized_power_core");
  public static final Item            nullified_power_core     = new OverpoweredItem("nullified_power_core");
  public static final Item            energy_grid              = new OverpoweredItem("energy_grid");
  public static final Item            vacuum_container         = new OverpoweredItem("vacuum_container");
  public static final Item            beam_emitter             = new OverpoweredItem("beam_emitter");
  public static final Item            unknown_technology       = new UnknownTechnology("unknown_technology");
  public static final Item            fusion_core              = new FusionCore("fusion_core");
  public static final Item            dimensional_anchor       = new DimensionAnchor("dimensional_anchor");
  
  static {
    Debug.log_setup_info("Finished loading Items class.");
  }

}
