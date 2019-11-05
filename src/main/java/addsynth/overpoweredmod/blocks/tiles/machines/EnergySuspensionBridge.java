package addsynth.overpoweredmod.blocks.tiles.machines;

import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.other.TileSuspensionBridge;
import net.minecraft.block.material.MapColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class EnergySuspensionBridge extends MachineBlockTileEntity {

  public EnergySuspensionBridge(final String name){
    super(MapColor.GRAY);
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final TileEntity createNewTileEntity(final World worldIn, final int meta){
    return new TileSuspensionBridge();
  }

}
