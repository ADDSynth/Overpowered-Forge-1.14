package addsynth.overpoweredmod.machines.fusion.chamber;

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
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class FusionChamber extends MachineBlock {

  public FusionChamber(final String name){
    super(MaterialColor.SNOW);
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.machines_creative_tab));
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
    if(world.isRemote == false){
      final TileFusionChamber tile = MinecraftUtility.getTileEntity(pos, world, TileFusionChamber.class);
      if(tile == null){ return false; }
      if(tile.is_on()){ return false; }
      NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
    }
    return true;
  }

  @Override
  public final void onBlockHarvested(final World worldIn, final BlockPos pos, final BlockState state, final PlayerEntity player){
    super.onBlockHarvested(worldIn, pos, state, player);
    check_container(worldIn, pos);
  }

  @Override
  public final void onExplosionDestroy(final World world, final BlockPos pos, final Explosion explosion){
    check_container(world, pos);
  }

  private static final void check_container(final World world, final BlockPos position){
    if(world.isRemote == false){
      final TileFusionChamber tile = MinecraftUtility.getTileEntity(position, world, TileFusionChamber.class);
      if(tile != null){
        if(tile.is_on()){
          tile.explode();
        }
      }
    }
  }

}
