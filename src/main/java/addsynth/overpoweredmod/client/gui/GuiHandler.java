package addsynth.overpoweredmod.client.gui;

import addsynth.core.inventory.container.BaseContainer;
import addsynth.energy.gameplay.gui.*;
import addsynth.energy.gameplay.tiles.TileUniversalEnergyTransfer;
import addsynth.energy.tiles.TileEnergyBattery;
import addsynth.overpoweredmod.client.gui.tiles.*;
import addsynth.overpoweredmod.containers.*;
import addsynth.overpoweredmod.tiles.machines.automatic.*;
import addsynth.overpoweredmod.tiles.machines.energy.TileEnergyGenerator;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionChamber;
import addsynth.overpoweredmod.tiles.machines.laser.TileLaserHousing;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalControlPanel;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class GuiHandler implements IGuiHandler {

  // See? It would be really cool to convert from enum to integer, but nooooo.
  public static final byte GENERATOR                = 0;
  public static final byte ENERGY_STORAGE           = 1;
  public static final byte GEM_CONVERTER            = 2;
  public static final byte COMPRESSOR               = 3;
  public static final byte INVERTER                 = 4;
  public static final byte MAGIC_UNLOCKER           = 5;
  public static final byte IDENTIFIER               = 6;
  public static final byte PORTAL_FRAME             = 7;
  public static final byte PORTAL_CONTROL_PANEL     = 8;
  public static final byte LASER_HOUSING            = 9;
  public static final byte CRYSTAL_MATTER_GENERATOR = 10;
  public static final byte ADVANCED_ORE_REFINERY    = 11;
  public static final byte FUSION_CONTAINER         = 13;
  public static final byte ELECTRIC_FURNACE         = 14;
  public static final byte UNIVERSAL_ENERGY_INTERFACE = 15;
  // PRIORITY: convert to an enum. all versions.

  @Override
  public final Object getServerGuiElement(int id, PlayerEntity player, World world, int x, int y, int z){
    Object object = null;
    final TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
    switch(id){
    case GENERATOR:                object = new ContainerGenerator(       player.inventory,(TileEnergyGenerator)tile); break;
    case ENERGY_STORAGE:           object = new BaseContainer<>(          (TileEnergyBattery)tile); break;
    case GEM_CONVERTER:            object = new ContainerGemConverter(    player.inventory,(TileGemConverter)tile); break;
    case COMPRESSOR:               object = new ContainerCompressor(      player.inventory,(TileCompressor)tile); break;
    case INVERTER:                 object = new ContainerInverter(        player.inventory,(TileInverter)tile); break;
    case MAGIC_UNLOCKER:           object = new ContainerMagicUnlocker(   player.inventory,(TileMagicUnlocker)tile); break;
    case IDENTIFIER:               object = new ContainerIdentifier(      player.inventory,(TileIdentifier)tile); break;
    case PORTAL_FRAME:             object = new ContainerPortalFrame(     player.inventory,(TilePortalFrame)tile); break;
    case PORTAL_CONTROL_PANEL:     object = new BaseContainer<>(          (TilePortalControlPanel)tile); break;
    case LASER_HOUSING:            object = new BaseContainer<>(          (TileLaserHousing)tile); break;
    case CRYSTAL_MATTER_GENERATOR: object = new ContainerCrystalGenerator(player.inventory,(TileCrystalMatterReplicator)tile); break;
    case ADVANCED_ORE_REFINERY:    object = new ContainerOreRefinery(     player.inventory,(TileAdvancedOreRefinery)tile); break;
    case FUSION_CONTAINER:         object = new ContainerFusionChamber(   player.inventory,(TileFusionChamber)tile); break;
    case ELECTRIC_FURNACE:         object = new ContainerElectricFurnace( player.inventory,(TileElectricFurnace)tile); break;
    case UNIVERSAL_ENERGY_INTERFACE: object = new BaseContainer<>(        (TileUniversalEnergyTransfer)tile); break;
    }
    return object;
  }

  @Override
  @SideOnly(Side.CLIENT) // here so I can debug this in a development environment, ran perfectly fine without it from gradlew runServer.
  public final Object getClientGuiElement(int id, PlayerEntity player, World world, int x, int y, int z){
    Object object = null;
    final TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
    switch(id){
    case GENERATOR:                object = new GuiGenerator(             player.inventory,(TileEnergyGenerator)tile); break;
    case ENERGY_STORAGE:           object = new GuiEnergyStorageContainer(player.inventory,(TileEnergyBattery)tile); break;
    case GEM_CONVERTER:            object = new GuiGemConverter(          player.inventory,(TileGemConverter)tile); break;
    case COMPRESSOR:               object = new GuiCompressor(            player.inventory,(TileCompressor)tile); break;
    case INVERTER:                 object = new GuiInverter(              player.inventory,(TileInverter)tile); break;
    case MAGIC_UNLOCKER:           object = new GuiMagicUnlocker(         player.inventory,(TileMagicUnlocker)tile); break;
    case IDENTIFIER:               object = new GuiIdentifier(            player.inventory,(TileIdentifier)tile); break;
    case PORTAL_FRAME:             object = new GuiPortalFrame(           player.inventory,(TilePortalFrame)tile); break;
    case PORTAL_CONTROL_PANEL:     object = new GuiPortalControlPanel(    player.inventory,(TilePortalControlPanel)tile); break;
    case LASER_HOUSING:            object = new GuiLaserHousing(          player.inventory,(TileLaserHousing)tile); break;
    case CRYSTAL_MATTER_GENERATOR: object = new GuiCrystalMatterGenerator(player.inventory,(TileCrystalMatterReplicator)tile); break;
    case ADVANCED_ORE_REFINERY:    object = new GuiAdvancedOreRefinery(   player.inventory,(TileAdvancedOreRefinery)tile); break;
    case FUSION_CONTAINER:         object = new GuiSingularityContainer(  player.inventory,(TileFusionChamber)tile); break;
    case ELECTRIC_FURNACE:         object = new GuiElectricFurnace(       player.inventory,(TileElectricFurnace)tile); break;
    case UNIVERSAL_ENERGY_INTERFACE: object = new GuiUniversalEnergyInterface(player.inventory,(TileUniversalEnergyTransfer)tile); break;
    }
    return object;
  }

}
