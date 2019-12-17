package addsynth.core.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public final class WorldUtil {

  public static final void spawnItemStack(IWorld world, BlockPos pos, ItemStack stack){
    spawnItemStack((World)world, pos.getX(), pos.getY(), pos.getZ(), stack);
  }

  public static final void spawnItemStack(IWorld world, BlockPos pos, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack((World)world, pos.getX(), pos.getY(), pos.getZ(), stack, vanilla_jump_randomly);
  }

  public static final void spawnItemStack(IWorld world, double x, double y, double z, ItemStack stack){
    spawnItemStack((World)world, x, y, z, stack);
  }

  public static final void spawnItemStack(IWorld world, double x, double y, double z, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack((World)world, x, y, z, stack, vanilla_jump_randomly);
  }

  public static final void spawnItemStack(World world, BlockPos pos, ItemStack stack){
    spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
  }

  public static final void spawnItemStack(World world, BlockPos pos, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack, vanilla_jump_randomly);
  }

  public static final void spawnItemStack(World world, double x, double y, double z, ItemStack stack){
    if(stack.isEmpty() == false){
      // PRIORITY: wait to spawn Items myself until I can run the mod without crashing.
      // final ItemEntity entity = new ItemEntity(world, x, y, z, stack);
      // world.addEntity(entity);
      InventoryHelper.spawnItemStack(world, x, y, z, stack);
    }
  }

  public static final void spawnItemStack(World world, double x, double y, double z, ItemStack stack, boolean vanilla_jump_randomly){
    if(vanilla_jump_randomly){
      InventoryHelper.spawnItemStack(world, x, y, z, stack);
    }
    else{
      spawnItemStack(world, x, y, z, stack);
    }
  }

}
