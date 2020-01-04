package addsynth.overpoweredmod.blocks.dimension.tree;

import java.util.ArrayList;
import addsynth.core.util.WorldUtil;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.ModItems;
import addsynth.overpoweredmod.game.core.Portal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public final class UnknownWood extends Block {

  public UnknownWood(final String name){
    super(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f));
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public void onPlayerDestroy(final IWorld world, final BlockPos position, final BlockState state){
    final ArrayList<BlockPos> found = new ArrayList<>(40);
    found.add(position);
    tree_destroy_loop(found, world, position);
    WorldUtil.spawnItemStack(world, position, new ItemStack(ModItems.unknown_technology,1));
  }

  private final void tree_destroy_loop(final ArrayList<BlockPos> found, final IWorld world, final BlockPos position){
    BlockPos side_position;
    Block block;
    for(Direction side : Direction.values()){
      side_position = position.offset(side);
      if(found.contains(side_position) == false){
        found.add(position);
        block = world.getBlockState(side_position).getBlock();
        if(block == Portal.unknown_wood || block == Portal.unknown_leaves){
          tree_destroy_loop(found, world, side_position);
          world.removeBlock(side_position, false);
        }
      }
    }
  }

}
