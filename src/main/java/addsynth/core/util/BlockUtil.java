package addsynth.core.util;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public final class BlockUtil {

  // !!! This MUST follow the same order as the Direction enum!
  public static final int DOWN  =  1;
  public static final int UP    =  2;
  public static final int NORTH =  4;
  public static final int SOUTH =  8;
  public static final int WEST  = 16;
  public static final int EAST  = 32;

  public static final VoxelShape[] create_six_sided_binary_voxel_shapes(final double thickness){
    final double half_width = thickness / 2;
    return create_six_sided_binary_voxel_shapes(0.5 - half_width, 0.5 + half_width);
  }

  public static final VoxelShape[] create_six_sided_binary_voxel_shapes(final double min_size, final double max_size){
    final int max = 64;
    final VoxelShape[] shapes = new VoxelShape[max];
    final VoxelShape center = VoxelShapes.create(min_size, min_size, min_size, max_size, max_size, max_size);
    final VoxelShape west   = VoxelShapes.create(0, min_size, min_size, max_size, max_size, max_size);
    final VoxelShape down   = VoxelShapes.create(min_size, 0, min_size, max_size, max_size, max_size);
    final VoxelShape north  = VoxelShapes.create(min_size, min_size, 0, max_size, max_size, max_size);
    final VoxelShape east   = VoxelShapes.create(min_size, min_size, min_size, 1, max_size, max_size);
    final VoxelShape up     = VoxelShapes.create(min_size, min_size, min_size, max_size, 1, max_size);
    final VoxelShape south  = VoxelShapes.create(min_size, min_size, min_size, max_size, max_size, 1);
    int i;
    for(i = 0; i < max; i++){
      
      shapes[i] = center;
      if((i & DOWN)  == DOWN ){ shapes[i] = VoxelShapes.or(shapes[i], down);  }
      if((i & UP)    == UP   ){ shapes[i] = VoxelShapes.or(shapes[i], up);    }
      if((i & NORTH) == NORTH){ shapes[i] = VoxelShapes.or(shapes[i], north); }
      if((i & SOUTH) == SOUTH){ shapes[i] = VoxelShapes.or(shapes[i], south); }
      if((i & WEST)  == WEST ){ shapes[i] = VoxelShapes.or(shapes[i], west);  }
      if((i & EAST)  == EAST ){ shapes[i] = VoxelShapes.or(shapes[i], east);  }
    }
    return shapes;
  }

  public static final int getIndex(final BlockState state){
    final int down  = state.get(BlockStateProperties.DOWN)  ? DOWN  : 0;
    final int up    = state.get(BlockStateProperties.UP)    ? UP    : 0;
    final int north = state.get(BlockStateProperties.NORTH) ? NORTH : 0;
    final int south = state.get(BlockStateProperties.SOUTH) ? SOUTH : 0;
    final int west  = state.get(BlockStateProperties.WEST)  ? WEST  : 0;
    final int east  = state.get(BlockStateProperties.EAST)  ? EAST  : 0;
    return down + up + north + south + east + west; // MAYBE computationally faster to use bitwise OR instead of adding?
  }

}
