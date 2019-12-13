package addsynth.overpoweredmod.blocks.tiles.fusion;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionChamber;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class FusionChamber extends MachineBlock {

  /** A standard TNT explosion is size of 4. */
  private static final float FUSION_CHAMBER_EXPLOSION_SIZE = 8.0f;

  public FusionChamber(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Fusion Energy"));
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileFusionChamber();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    // FIX, getting variable on the client side which is never updated and will always be false.
    // if(world.isRemote == false){
      final TileFusionChamber tile = MinecraftUtility.getTileEntity(pos, world, TileFusionChamber.class);
      if(tile != null){
        if((tile).is_on() == false){
          NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
          return true;
        }
      }
      return false;
    // }
    // return true;
  }

  @Override
  public final void onBlockHarvested(final World worldIn, final BlockPos pos, final BlockState state, final PlayerEntity player){
    check_container(worldIn, pos);
  }

  @Override
  public final void onBlockExploded(final World world, final BlockPos pos, final Explosion explosion){
    check_container(world, pos);
  }

  private static final boolean check_container(final World world, final BlockPos position){
    if(world.isRemote == false){
      final TileEntity tile = world.getTileEntity(position);
      if(tile != null){
        if(((TileFusionChamber)tile).has_fusion_core()){
          world.removeBlock(position, false);
          world.createExplosion(null, position.getX()+0.5, position.getY()+0.5, position.getZ()+0.5, FUSION_CHAMBER_EXPLOSION_SIZE, true, Explosion.Mode.DESTROY);
          return true;
        }
      }
    }
    return false;
  }

}
