package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class EnergySuspensionBridge extends MachineBlock {

  public EnergySuspensionBridge(final String name){
    super(MaterialColor.GRAY);
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.machines_creative_tab));
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileSuspensionBridge();
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Class 2 Machine"));
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isRemote == false){
      final TileSuspensionBridge tile = MinecraftUtility.getTileEntity(pos, world, TileSuspensionBridge.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return true;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    if(world.isRemote == false){
      final BlockNetwork network = ((IBlockNetworkUser)(world.getTileEntity(pos))).getNetwork();
      network.neighbor_was_changed(pos, neighbor);
    }
  }

  

  @Override
  public final void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving){
    final BlockNetwork network = BlockNetwork.getNetwork(world, pos);
    super.onReplaced(state, world, pos, newState, isMoving);
    BlockNetwork.block_was_broken(network, world, pos, state.getBlock());
  }

}
