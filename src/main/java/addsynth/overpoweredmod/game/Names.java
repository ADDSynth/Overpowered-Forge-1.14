package addsynth.overpoweredmod.game;

import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.util.ResourceLocation;

/** Starting in Minecraft 1.14, there are now many new object types that need to be registered
 *  to the game, such as TileEntity Types and Containers. As such, they need to be registered
 *  with their own RegistryName string IDs just like Blocks and Items. So now I want to use
 *  classes such as this that hold all the ResourceLocation names used to register EVERYTHING.
 *  Say for instance you have a TileEntity, that also has a Block, a Container, and a Gui.
 *  You can register all of those by referring to its global name here.
 */
public final class Names {

  public static final ResourceLocation CELESTIAL_GEM         = new ResourceLocation(OverpoweredTechnology.MOD_ID, "celestial_gem");
  public static final ResourceLocation ENERGY_CRYSTAL_SHARDS = new ResourceLocation(OverpoweredTechnology.MOD_ID, "energy_crystal_shards");
  public static final ResourceLocation ENERGY_CRYSTAL        = new ResourceLocation(OverpoweredTechnology.MOD_ID, "energy_crystal");
  public static final ResourceLocation LIGHT_BLOCK           = new ResourceLocation(OverpoweredTechnology.MOD_ID, "light_block");
  public static final ResourceLocation VOID_CRYSTAL          = new ResourceLocation(OverpoweredTechnology.MOD_ID, "void_crystal");
  public static final ResourceLocation NULL_BLOCK            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "null_block");
  public static final ResourceLocation IRON_FRAME_BLOCK      = new ResourceLocation(OverpoweredTechnology.MOD_ID, "iron_frame_block");
  public static final ResourceLocation BLACK_HOLE            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "black_hole");

  public static final ResourceLocation POWER_CORE            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "power_core");
  public static final ResourceLocation ADVANCED_POWER_CORE   = new ResourceLocation(OverpoweredTechnology.MOD_ID, "advanced_power_core");
  public static final ResourceLocation ENERGIZED_POWER_CORE  = new ResourceLocation(OverpoweredTechnology.MOD_ID, "energized_power_core");
  public static final ResourceLocation NULLIFIED_POWER_CORE  = new ResourceLocation(OverpoweredTechnology.MOD_ID, "nullified_power_core");
  public static final ResourceLocation ENERGY_GRID           = new ResourceLocation(OverpoweredTechnology.MOD_ID, "energy_grid");
  public static final ResourceLocation SEALED_CONTAINER      = new ResourceLocation(OverpoweredTechnology.MOD_ID, "sealed_container");
  public static final ResourceLocation BEAM_EMITTER          = new ResourceLocation(OverpoweredTechnology.MOD_ID, "beam_emitter");
  public static final ResourceLocation FUSION_CORE           = new ResourceLocation(OverpoweredTechnology.MOD_ID, "fusion_fore");
  public static final ResourceLocation UNKNOWN_TECHNOLOGY    = new ResourceLocation(OverpoweredTechnology.MOD_ID, "unknown_technology");
  public static final ResourceLocation DIMENSIONAL_ANCHOR    = new ResourceLocation(OverpoweredTechnology.MOD_ID, "dimensional_anchor");

  public static final ResourceLocation FOCUS_LENS            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "focus_lens");
  public static final ResourceLocation RED_LENS              = new ResourceLocation(OverpoweredTechnology.MOD_ID, "red_lens");
  public static final ResourceLocation ORANGE_LENS           = new ResourceLocation(OverpoweredTechnology.MOD_ID, "orange_lens");
  public static final ResourceLocation YELLOW_LENS           = new ResourceLocation(OverpoweredTechnology.MOD_ID, "yellow_lens");
  public static final ResourceLocation GREEN_LENS            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "green_lens");
  public static final ResourceLocation CYAN_LENS             = new ResourceLocation(OverpoweredTechnology.MOD_ID, "cyan_lens");
  public static final ResourceLocation BLUE_LENS             = new ResourceLocation(OverpoweredTechnology.MOD_ID, "blue_lens");
  public static final ResourceLocation MAGENTA_LENS          = new ResourceLocation(OverpoweredTechnology.MOD_ID, "magenta_lens");

  public static final ResourceLocation DATA_CABLE               = new ResourceLocation(OverpoweredTechnology.MOD_ID, "data_cable");
  public static final ResourceLocation CRYSTAL_ENERGY_EXTRACTOR = new ResourceLocation(OverpoweredTechnology.MOD_ID, "crystal_energy_extractor");
  public static final ResourceLocation GEM_CONVERTER            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "gem_converter");
  public static final ResourceLocation MAGIC_INFUSER            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "magic_infuser");
  public static final ResourceLocation IDENTIFIER               = new ResourceLocation(OverpoweredTechnology.MOD_ID, "identifier");
  public static final ResourceLocation INVERTER                 = new ResourceLocation(OverpoweredTechnology.MOD_ID, "inverter");
  public static final ResourceLocation ADVANCED_ORE_REFINERY    = new ResourceLocation(OverpoweredTechnology.MOD_ID, "advanced_ore_refinery");
  public static final ResourceLocation CRYSTAL_MATTER_GENERATOR = new ResourceLocation(OverpoweredTechnology.MOD_ID, "crystal_matter_generator");
  public static final ResourceLocation ENERGY_SUSPENSION_BRIDGE = new ResourceLocation(OverpoweredTechnology.MOD_ID, "energy_suspension_bridge");

  public static final ResourceLocation PORTAL_CONTROL_PANEL     = new ResourceLocation(OverpoweredTechnology.MOD_ID, "portal_control_panel");
  public static final ResourceLocation PORTAL_FRAME             = new ResourceLocation(OverpoweredTechnology.MOD_ID, "portal_frame");
  public static final ResourceLocation PORTAL_RIFT              = new ResourceLocation(OverpoweredTechnology.MOD_ID, "portal_block");

  public static final ResourceLocation LASER_HOUSING            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "laser_housing");
  public static final ResourceLocation LASER_CANNON             = new ResourceLocation(OverpoweredTechnology.MOD_ID, "laser_cannon");
  public static final ResourceLocation LASER_BEAM               = new ResourceLocation(OverpoweredTechnology.MOD_ID, "laser_beam");
  // Must register the different laser cannons as different blocks so they have different names and produce
  // different color beams, but all the beams can be merged into a single block and use a color property.

/*  
  public static final ResourceLocation WHITE_LASER           = new ResourceLocation(OverpoweredMod.MOD_ID, "white_laser");
  public static final ResourceLocation RED_LASER             = new ResourceLocation(OverpoweredMod.MOD_ID, "red_laser");
  public static final ResourceLocation ORANGE_LASER          = new ResourceLocation(OverpoweredMod.MOD_ID, "orange_laser");
  public static final ResourceLocation YELLOW_LASER          = new ResourceLocation(OverpoweredMod.MOD_ID, "yellow_laser");
  public static final ResourceLocation GREEN_LASER           = new ResourceLocation(OverpoweredMod.MOD_ID, "green_laser");
  public static final ResourceLocation CYAN_LASER            = new ResourceLocation(OverpoweredMod.MOD_ID, "cyan_laser");
  public static final ResourceLocation BLUE_LASER            = new ResourceLocation(OverpoweredMod.MOD_ID, "blue_laser");
  public static final ResourceLocation MAGENTA_LASER         = new ResourceLocation(OverpoweredMod.MOD_ID, "magenta_laser");

  public static final ResourceLocation WHITE_LASER_BEAM      = new ResourceLocation(OverpoweredMod.MOD_ID, "white_laser_beam");
  public static final ResourceLocation RED_LASER_BEAM        = new ResourceLocation(OverpoweredMod.MOD_ID, "red_laser_beam");
  public static final ResourceLocation ORANGE_LASER_BEAM     = new ResourceLocation(OverpoweredMod.MOD_ID, "orange_laser_beam");
  public static final ResourceLocation YELLOW_LASER_BEAM     = new ResourceLocation(OverpoweredMod.MOD_ID, "yellow_laser_beam");
  public static final ResourceLocation GREEN_LASER_BEAM      = new ResourceLocation(OverpoweredMod.MOD_ID, "green_laser_beam");
  public static final ResourceLocation CYAN_LASER_BEAM       = new ResourceLocation(OverpoweredMod.MOD_ID, "cyan_laser_beam");
  public static final ResourceLocation BLUE_LASER_BEAM       = new ResourceLocation(OverpoweredMod.MOD_ID, "blue_laser_beam");
  public static final ResourceLocation MAGENTA_LASER_BEAM    = new ResourceLocation(OverpoweredMod.MOD_ID, "magenta_laser_beam");
*/

  public static final ResourceLocation FUSION_CONVERTER         = new ResourceLocation(OverpoweredTechnology.MOD_ID, "fusion_energy_converter");
  public static final ResourceLocation FUSION_CHAMBER           = new ResourceLocation(OverpoweredTechnology.MOD_ID, "fusion_chamber");
  public static final ResourceLocation FUSION_CONTROL_UNIT      = new ResourceLocation(OverpoweredTechnology.MOD_ID, "fusion_control_unit");
  public static final ResourceLocation FUSION_CONTROL_LASER     = new ResourceLocation(OverpoweredTechnology.MOD_ID, "fusion_control_laser");
  public static final ResourceLocation FUSION_CONTROL_LASER_BEAM = new ResourceLocation(OverpoweredTechnology.MOD_ID, "fusion_control_laser_beam");

  public static final ResourceLocation TROPHY_BASE              = new ResourceLocation(OverpoweredTechnology.MOD_ID, "trophy_base");
  public static final ResourceLocation BRONZE_TROPHY            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "bronze_trophy");
  public static final ResourceLocation SILVER_TROPHY            = new ResourceLocation(OverpoweredTechnology.MOD_ID, "silver_trophy");
  public static final ResourceLocation GOLD_TROPHY              = new ResourceLocation(OverpoweredTechnology.MOD_ID, "gold_trophy");
  public static final ResourceLocation PLATINUM_TROPHY          = new ResourceLocation(OverpoweredTechnology.MOD_ID, "platinum_trophy");

}
