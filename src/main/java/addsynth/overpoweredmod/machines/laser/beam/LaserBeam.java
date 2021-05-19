package addsynth.overpoweredmod.machines.laser.beam;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.DamageSources;
import addsynth.overpoweredmod.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class LaserBeam extends Block {

  public LaserBeam(String name){
    super(Block.Properties.create(Material.FIRE).variableOpacity().doesNotBlockMovement().lightValue(Config.laser_light_level.get()));
    OverpoweredTechnology.registry.register_block(this, name);
  }

  @Override
  public final BlockRenderLayer getRenderLayer(){
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return VoxelShapes.empty();
  }

  // You cannot set this block isBurning(true) because this will also set fire to any
  //   item Entities that fall in it, negating the purpose of having a mining laser.
  //   also, that only does 1 damage at a time.
  @Override
  @SuppressWarnings("deprecation")
  public final void onEntityCollision(final BlockState state, final World world, final BlockPos pos, final Entity entity){
    if(Config.lasers_set_entities_on_fire.get()){
      if(entity instanceof ItemEntity == false){
        if(Config.laser_damage_depends_on_world_difficulty.get()){
          final int[] damage = new int[] {
            Config.LASER_DAMAGE_PEACEFUL_DIFFICULTY, Config.LASER_DAMAGE_EASY_DIFFICULTY,
            Config.LASER_DAMAGE_NORMAL_DIFFICULTY, Config.LASER_DAMAGE_HARD_DIFFICULTY};
          entity.attackEntityFrom(DamageSources.laser, damage[world.getDifficulty().ordinal()]);
        }
        else{
          entity.attackEntityFrom(DamageSources.laser, Config.LASER_DAMAGE_NORMAL_DIFFICULTY);
        }
        entity.setFire(8); // 8 seconds is the same time Vanilla sets players on fire when in contact with fire.
      }
    }
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, IBlockReader world){
    return new TileLaserBeam();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() instanceof LaserBeam ? true : super.isSideInvisible(state, adjacentBlockState, side);
  }

}
