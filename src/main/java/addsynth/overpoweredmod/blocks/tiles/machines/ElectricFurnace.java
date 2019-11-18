package addsynth.overpoweredmod.blocks.tiles.machines;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.automatic.TileElectricFurnace;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class ElectricFurnace extends MachineBlockTileEntity {

  public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

  public ElectricFurnace(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
    this.setDefaultState(this.stateContainer.getBaseState().withProperty(FACING, Direction.NORTH));
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Class 1 Machine"));
  }

  @Override
  protected final BlockStateContainer createBlockState(){
    return new BlockStateContainer(this, FACING);
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileElectricFurnace();
  }

  @Override
  public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final PlayerEntity player, final Hand hand, final Direction facing, final float hitX, final float hitY, final float hitZ){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.ELECTRIC_FURNACE, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

  @Override
  public final IBlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, Hand hand){
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }

}
