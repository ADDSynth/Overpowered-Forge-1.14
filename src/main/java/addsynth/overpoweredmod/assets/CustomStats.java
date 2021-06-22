package addsynth.overpoweredmod.assets;

import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.util.ResourceLocation;

public final class CustomStats {

  public static final ResourceLocation GEMS_CONVERTED =
    new ResourceLocation(OverpoweredTechnology.MOD_ID, "gems_converted");
  
  public static final ResourceLocation LASERS_FIRED =
    new ResourceLocation(OverpoweredTechnology.MOD_ID, "lasers_fired");
  
  public static final ResourceLocation ITEMS_IDENTIFIED =
    new ResourceLocation(OverpoweredTechnology.MOD_ID, "items_identified");
  
  // REMOVE Black Hole Stat. Minecraft already keeps track of how many Black Hole blocks you place down?
  // public static final ResourceLocation BLACK_HOLE_EVENTS =
  //   new ResourceLocation(OverpoweredTechnology.MOD_ID, "black_hole_events");

}
