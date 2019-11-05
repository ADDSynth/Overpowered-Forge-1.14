package addsynth.energy.gameplay.blocks;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.energy.gameplay.tiles.TileUniversalEnergyTransfer;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class UniversalEnergyBlock extends MachineBlockTileEntity {

  public UniversalEnergyBlock(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Energy Machine");
  }

  @Override
  public final TileEntity createNewTileEntity(World worldIn, int meta){
    return new TileUniversalEnergyTransfer();
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance, GuiHandler.UNIVERSAL_ENERGY_INTERFACE, world, pos.getX(), pos.getY(), pos.getZ());
    }
    return true;
  }

}
