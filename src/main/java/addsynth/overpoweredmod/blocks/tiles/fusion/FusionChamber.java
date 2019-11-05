package addsynth.overpoweredmod.blocks.tiles.fusion;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionChamber;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public final class FusionChamber extends MachineBlock {

  /** A standard TNT explosion is size of 4. */
  private static final float FUSION_CHAMBER_EXPLOSION_SIZE = 8.0f;

  public FusionChamber(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Fusion Energy");
  }

  @Override
  public final TileEntity createNewTileEntity(final World worldIn, final int meta){
    return new TileFusionChamber();
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
    // FIX, getting variable on the client side which is never updated and will always be false.
    // if(world.isRemote == false){
      final TileEntity tile = world.getTileEntity(pos);
      if(tile != null){
        if(((TileFusionChamber)tile).is_on() == false){
          player.openGui(OverpoweredMod.instance,GuiHandler.FUSION_CONTAINER, world,pos.getX(),pos.getY(),pos.getZ());
          return true;
        }
      }
      return false;
    // }
    // return true;
  }

  @Override
  public final void onBlockHarvested(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer player){
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
          world.setBlockToAir(position);
          world.newExplosion(null, position.getX()+0.5, position.getY()+0.5, position.getZ()+0.5, FUSION_CHAMBER_EXPLOSION_SIZE, true, true);
          return true;
        }
      }
    }
    return false;
  }

}
