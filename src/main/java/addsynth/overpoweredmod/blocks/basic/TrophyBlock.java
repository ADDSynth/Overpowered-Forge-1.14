package addsynth.overpoweredmod.blocks.basic;

import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.ToolType;

public final class TrophyBlock extends Block {

  private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

  private static final AxisAlignedBB bounding_box = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.9375, 0.875);

  public TrophyBlock(String name){
    super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2));
    this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(OverpoweredMod.creative_tab));
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context){
    return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
  }

  @Override
  protected void fillStateContainer(Builder<Block, BlockState> builder){
    builder.add(FACING);
  }

}
