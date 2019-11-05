package addsynth.overpoweredmod.blocks.dimension.tree;

import java.util.ArrayList;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.ModItems;
import addsynth.overpoweredmod.game.core.Portal;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class UnknownWood extends Block {

  private final ArrayList<BlockPos> found = new ArrayList<>(50);

  public UnknownWood(final String name){
    super(Material.WOOD);
    this.setHardness(2.0F);
    this.setSoundType(SoundType.WOOD);
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public void onPlayerDestroy(final World world, final BlockPos position, final IBlockState state){
    found.clear();
    found.add(position);
    tree_destroy_loop(world, position);
    spawnAsEntity(world, position, new ItemStack(ModItems.unknown_technology,1));
  }

  private final void tree_destroy_loop(final World world, final BlockPos position){
    BlockPos side_position;
    Block block;
    for(EnumFacing side : EnumFacing.values()){
      side_position = position.offset(side);
      if(found.contains(side_position) == false){
        found.add(position);
        block = world.getBlockState(side_position).getBlock();
        if(block == Portal.unknown_wood || block == Portal.unknown_leaves){
          tree_destroy_loop(world, side_position);
          world.setBlockToAir(side_position);
        }
      }
    }
  }

}
