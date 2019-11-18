package addsynth.energy.gameplay.blocks;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.energy.gameplay.tiles.TileUniversalEnergyTransfer;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class UniversalEnergyBlock extends MachineBlockTileEntity {

  public UniversalEnergyBlock(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Energy Machine"));
  }

  @Override
  public final TileEntity createNewTileEntity(IBlockReader worldIn){
    return new TileUniversalEnergyTransfer();
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, PlayerEntity player, Hand hand, Direction facing, float hitX, float hitY, float hitZ){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance, GuiHandler.UNIVERSAL_ENERGY_INTERFACE, world, pos.getX(), pos.getY(), pos.getZ());
    }
    return true;
  }

}
