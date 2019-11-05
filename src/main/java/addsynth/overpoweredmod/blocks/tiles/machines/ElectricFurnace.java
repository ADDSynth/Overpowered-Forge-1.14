package addsynth.overpoweredmod.blocks.tiles.machines;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.automatic.TileElectricFurnace;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ElectricFurnace extends MachineBlockTileEntity {

  public static final PropertyDirection FACING = BlockHorizontal.FACING;

  public ElectricFurnace(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
    this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Class 1 Machine");
  }

  @Override
  protected final BlockStateContainer createBlockState(){
    return new BlockStateContainer(this, FACING);
  }

  @Override
  public final TileEntity createNewTileEntity(final World worldIn, final int meta){
    return new TileElectricFurnace();
  }

  @Override
  public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.ELECTRIC_FURNACE, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

  @Override
  public final IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  @SuppressWarnings("deprecation")
  public final IBlockState getStateFromMeta(final int meta){
    return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
  }

  @Override
  public final int getMetaFromState(final IBlockState state){
    return state.getValue(FACING).getIndex();
  }

}
