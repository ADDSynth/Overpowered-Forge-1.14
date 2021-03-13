package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
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
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
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
        final BridgeNetwork network = tile.getBlockNetwork();
        if(network != null){
          network.check_and_update();
          NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
        }
        else{
          OverpoweredMod.log.error(new NullPointerException("Energy Suspension Bridge at "+pos.toString()+" has no BridgeNetwork!"));
        }
      }
    }
    return true;
  }

/* DELETE: Energy Suspension Bridges don't need to update when the adjacent block changes.
  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    if(world.isRemote == false){
      final BlockNetwork network = ((IBlockNetworkUser)(world.getTileEntity(pos))).getBlockNetwork();
      network.neighbor_was_changed(pos, neighbor);
    }
  }
*/

}
