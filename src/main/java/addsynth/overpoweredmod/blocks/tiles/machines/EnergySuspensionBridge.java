package addsynth.overpoweredmod.blocks.tiles.machines;

import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.other.TileSuspensionBridge;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public final class EnergySuspensionBridge extends MachineBlockTileEntity {

  public EnergySuspensionBridge(final String name){
    super(MaterialColor.GRAY);
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileSuspensionBridge();
  }

}
