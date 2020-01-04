package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.energy.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public final class EnergySuspensionBridge extends MachineBlock {

  public EnergySuspensionBridge(final String name){
    super(MaterialColor.GRAY);
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileSuspensionBridge();
  }

}
