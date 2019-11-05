package addsynth.overpoweredmod.blocks.tiles.laser;

import javax.annotation.Nullable;
import addsynth.core.blocks.BlockTile;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.DamageSources;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.tiles.technical.TileLaserBeam;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class LaserBeam extends BlockTile {

  public LaserBeam(String name){
    super(Material.FIRE);
    if(Config.lasers_emit_light){
      setLightLevel((float)Config.laser_light_level / 15.0f);
    }
    translucent = true;
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public final BlockRenderLayer getRenderLayer(){
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  @Nullable
  @SuppressWarnings("deprecation")
  public final AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
    return NULL_AABB;
  }

  /**
   * Used to determine ambient occlusion and culling when rebuilding chunks for render
   */
  @Override
  @SuppressWarnings("deprecation")
  public final boolean isOpaqueCube(IBlockState state){
    return false;
  }

  @Override
  public final boolean isCollidable(){
    return false;
  }

  // You cannot set this block isBurning(true) because this will also set fire to any
  //   item Entities that fall in it, negating the purpose of having a mining laser.
  //   also, that only does 1 damage at a time.
  @Override
  public final void onEntityCollision(final World world, final BlockPos pos, final IBlockState state, final Entity entity){
    if(Config.lasers_set_entities_on_fire){
      if(entity instanceof EntityItem == false){
        if(Config.laser_damage_depends_on_world_difficulty){
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
  public final TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileLaserBeam();
  }

}
