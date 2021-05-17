package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.blocks.dimension.*;
import addsynth.overpoweredmod.blocks.dimension.tree.*;
import addsynth.overpoweredmod.machines.portal.rift.PortalEnergyBlock;
import net.minecraft.item.Item;

public final class Portal {

  static {
    Debug.log_setup_info("Begin loading Portal class...");
  }

  public static final PortalEnergyBlock   portal             = new PortalEnergyBlock("portal");
  /** Item form of Portal, used only for Achievement icon. Does not show up in jei or creative tab.
   *  But players can still get it by using the /give command.
   */
  public static final Item                portal_image       = OverpoweredTechnology.registry.getItemBlock(portal);
  
  
  public static final BlockGrassNoDestroy custom_grass_block = new BlockGrassNoDestroy("grass_block");
  public static final BlockAirNoDestroy   custom_air_block   = new BlockAirNoDestroy("air");


  public static final UnknownWood         unknown_wood       = new UnknownWood("unknown_wood");
  public static final UnknownLeaves       unknown_leaves     = new UnknownLeaves("unknown_leaves");


  static {
    Debug.log_setup_info("Finished loading Portal class.");
  }

}
