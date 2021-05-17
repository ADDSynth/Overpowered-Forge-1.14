package addsynth.overpoweredmod.machines.portal.control_panel;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.api.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
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

public final class PortalControlPanel extends MachineBlock {

  public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

  public PortalControlPanel(final String name){
    super(MaterialColor.SNOW);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Class 3 Machine"));
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TilePortalControlPanel();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isRemote == false){
      final TilePortalControlPanel tile = MinecraftUtility.getTileEntity(pos, world, TilePortalControlPanel.class);
      if(tile != null){
        tile.check_portal(player.isCreative());
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return true;
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context){
    return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
  }

  @Override
  protected void fillStateContainer(Builder<Block, BlockState> builder){
    builder.add(FACING);
  }

}
