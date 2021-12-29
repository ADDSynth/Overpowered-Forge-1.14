package addsynth.overpoweredmod.machines.black_hole;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class BlackHoleBlock extends Block {

  public BlackHoleBlock(final String name){
    super(Block.Properties.create(Material.AIR, MaterialColor.BLACK).doesNotBlockMovement());
    // setResistance(100.0f);
    OverpoweredTechnology.registry.register_block(this, name);
    OverpoweredTechnology.registry.register_ItemBlock(new BlackHoleItem(this));
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
     return VoxelShapes.empty();
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, final IBlockReader world){
    return new TileBlackHole();
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack){
    // if(placer instanceof ServerPlayerEntity){
    //   ((ServerPlayerEntity)placer).addStat(CustomStats.BLACK_HOLE_EVENTS);
    // }
  }

}
