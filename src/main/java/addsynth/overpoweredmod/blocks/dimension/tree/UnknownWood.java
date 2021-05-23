package addsynth.overpoweredmod.blocks.dimension.tree;

import addsynth.core.block_network.Node;
import addsynth.core.util.block.BlockUtil;
import addsynth.core.util.world.WorldUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Portal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public final class UnknownWood extends Block {

  public UnknownWood(final String name){
    super(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0f));
    OverpoweredTechnology.registry.register_block(this, name);
  }

  // TEST: I think I prefer this one because it works if the player is in Creative Mode as well.
  @Override
  public final void onPlayerDestroy(final IWorld w, final BlockPos position, final BlockState state){
    @SuppressWarnings("resource")
    final World world = w.getWorld();
    if(world.isRemote == false){
      BlockUtil.find_blocks(position, world, (Node node) -> valid(node, position)).forEach(
        (Node node) -> {
          if(node.position != position){
            world.removeBlock(node.position, false);
          }
        }
      );
      WorldUtil.spawnItemStack(world, position, new ItemStack(Init.void_crystal, 1));
    }
  }

  private static final boolean valid(final Node node, final BlockPos from){
    return node.block == Portal.unknown_wood || node.block == Portal.unknown_leaves || node.position == from;
  }

}
