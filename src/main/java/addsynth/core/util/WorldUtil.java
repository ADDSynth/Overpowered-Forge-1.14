package addsynth.core.util;

import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public final class WorldUtil {

  public static final void delete_block(final World world, final BlockPos position){
    world.setBlockState(position, Blocks.AIR.getDefaultState(), 3);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(IWorld world, BlockPos pos, ItemStack stack){
    InventoryHelper.spawnItemStack((World)world, pos.getX(), pos.getY(), pos.getZ(), stack);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(World world, BlockPos pos, ItemStack stack){
    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(IWorld world, double x, double y, double z, ItemStack stack){
    InventoryHelper.spawnItemStack((World)world, x, y, z, stack);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(World world, double x, double y, double z, ItemStack stack){
    InventoryHelper.spawnItemStack(world, x, y, z, stack);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(IWorld world, BlockPos pos, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack((World)world, pos.getX(), pos.getY(), pos.getZ(), stack, vanilla_jump_randomly);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(World world, BlockPos pos, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack, vanilla_jump_randomly);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(IWorld world, double x, double y, double z, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack((World)world, x, y, z, stack, vanilla_jump_randomly);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(World world, double x, double y, double z, ItemStack stack, boolean vanilla_jump_randomly){
    if(vanilla_jump_randomly){
      InventoryHelper.spawnItemStack(world, x, y, z, stack);
    }
    else{
      if(stack.isEmpty() == false){
        final ItemEntity entity = new ItemEntity(world, x + 0.5, y, z + 0.5, stack);
        entity.setMotion(0.0, 0.0, 0.0);
        world.addEntity(entity);
      }
    }
  }

}
