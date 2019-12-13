package addsynth.overpoweredmod.blocks.tiles.machines;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.automatic.TileCompressor;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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

public final class Compressor extends MachineBlockTileEntity {

  public Compressor(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Class 1 Machine"));
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileCompressor();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isRemote == false){
      final TileCompressor tile = MinecraftUtility.getTileEntity(pos, world, TileCompressor.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return true;
  }

}
