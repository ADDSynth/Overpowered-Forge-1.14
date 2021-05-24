package addsynth.overpoweredmod.machines.laser.machine;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
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

public final class LaserHousingBlock extends MachineBlock {

  public LaserHousingBlock(final String name){
    super(MaterialColor.SNOW);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Class 2 Machine"));
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, final IBlockReader world){
    return new TileLaserHousing();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isRemote == false){
      final TileLaserHousing tile = MinecraftUtility.getTileEntity(pos, world, TileLaserHousing.class);
      if(tile != null){
        final LaserNetwork network = tile.getBlockNetwork();
        if(network != null){
          network.updateClient();
          NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
        }
        else{
          OverpoweredTechnology.log.error(new NullPointerException("Laser Machine at "+pos.toString()+" has no LaserNetwork!"));
        }
      }
    }
    return true;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    BlockNetworkUtil.neighbor_changed(world, pos, neighbor);
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack){
    if(placer instanceof ServerPlayerEntity){ // ensures block was placed by plaer and on server side
      final TileLaserHousing tile = MinecraftUtility.getTileEntity(pos, world, TileLaserHousing.class);
      if(tile != null){
        tile.setPlayer((ServerPlayerEntity)placer);
      }
    }
  }

}
