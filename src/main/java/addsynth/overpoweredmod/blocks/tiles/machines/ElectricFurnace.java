package addsynth.overpoweredmod.blocks.tiles.machines;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.automatic.TileElectricFurnace;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class ElectricFurnace extends MachineBlockTileEntity {

  public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

  public ElectricFurnace(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(OverpoweredMod.machines_creative_tab));
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Class 1 Machine"));
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileElectricFurnace();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isRemote == false){
      final TileElectricFurnace tile = MinecraftUtility.getTileEntity(pos, world, TileElectricFurnace.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return true;
  }

  @Override
  public final IBlockState getStateForPlacement(World world, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, Hand hand){
    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
  }

}
