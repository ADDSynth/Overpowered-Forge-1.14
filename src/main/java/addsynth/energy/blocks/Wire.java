package addsynth.energy.blocks;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.blocks.BlockTile;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class Wire extends BlockTile {

  // http://mcforge.readthedocs.io/en/latest/blocks/states/

  public static final BooleanProperty NORTH = BooleanProperty.create("north");
  public static final BooleanProperty SOUTH = BooleanProperty.create("south");
  public static final BooleanProperty WEST  = BooleanProperty.create("west");
  public static final BooleanProperty EAST  = BooleanProperty.create("east");
  public static final BooleanProperty UP    = BooleanProperty.create("up");
  public static final BooleanProperty DOWN  = BooleanProperty.create("down");

  private static final double default_min_wire_size =  5.0 / 16;
  private static final double default_max_wire_size = 11.0 / 16;

  /**
   * This is the default bounding boxes for ALL wires. If your wire is of a different size, then you need to
   * "hide" this field by declaring your own field in subclasses with THE SAME NAME, and also declare them
   * as static, because all wires of that type use the same bounding boxes.
   */
  protected static final AxisAlignedBB bounding_box[] = new AxisAlignedBB[7];

  static {
    // Can initialize the bounding_box[] variables here.
  }

  public Wire(final Material material, final MaterialColor color){
    this( material, color, default_min_wire_size, default_max_wire_size);
  }

  public Wire(final Material material, final MaterialColor color, final double min_wire_size, final double max_wire_size){
    super(material, color);
    if(bounding_box[0] == null){
      set_bounding_boxes(min_wire_size, max_wire_size);
    }
    this.setDefaultState(this.blockState.getBaseState()
       .withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false)
       .withProperty(EAST, false).withProperty(UP, false).withProperty(DOWN, false));
  }

  private static final void set_bounding_boxes(double min_wire_size, double max_wire_size){
    bounding_box[0] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[1] = new AxisAlignedBB(0, min_wire_size, min_wire_size, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[2] = new AxisAlignedBB(min_wire_size, 0, min_wire_size, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[3] = new AxisAlignedBB(min_wire_size, min_wire_size, 0, max_wire_size, max_wire_size, max_wire_size);
    bounding_box[4] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, 1, max_wire_size, max_wire_size);
    bounding_box[5] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, max_wire_size, 1, max_wire_size);
    bounding_box[6] = new AxisAlignedBB(min_wire_size, min_wire_size, min_wire_size, max_wire_size, max_wire_size, 1);
  }

  @Override
  protected BlockStateContainer createBlockState(){
    return new BlockStateContainer(this, NORTH, SOUTH, WEST, EAST, UP, DOWN);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos){
    final ArrayList<Direction> valid_sides = get_valid_sides(world, pos);
    
    final boolean north = valid_sides.contains(Direction.NORTH);
    final boolean south = valid_sides.contains(Direction.SOUTH);
    final boolean west = valid_sides.contains(Direction.WEST);
    final boolean east = valid_sides.contains(Direction.EAST);
    final boolean up = valid_sides.contains(Direction.UP);
    final boolean down = valid_sides.contains(Direction.DOWN);
    
    return state.withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(WEST, west)
                .withProperty(EAST, east).withProperty(UP, up).withProperty(DOWN, down);
  }

  protected abstract ArrayList<Direction> get_valid_sides(IBlockAccess world, BlockPos pos);

  @Override
  @SuppressWarnings("deprecation")
  public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean unused){
    state = getActualState(state, world, pos);
    addCollisionBoxToList(pos, entityBox, collidingBoxes, bounding_box[0]);
    if(state.getValue(WEST)){  addCollisionBoxToList(pos, entityBox, collidingBoxes, bounding_box[1]);}
    if(state.getValue(DOWN)){  addCollisionBoxToList(pos, entityBox, collidingBoxes, bounding_box[2]);}
    if(state.getValue(NORTH)){ addCollisionBoxToList(pos, entityBox, collidingBoxes, bounding_box[3]);}
    if(state.getValue(EAST)){  addCollisionBoxToList(pos, entityBox, collidingBoxes, bounding_box[4]);}
    if(state.getValue(UP)){    addCollisionBoxToList(pos, entityBox, collidingBoxes, bounding_box[5]);}
    if(state.getValue(SOUTH)){ addCollisionBoxToList(pos, entityBox, collidingBoxes, bounding_box[6]);}
  }

  /**
   * Will eventually get replaced with IBlockState.getBoundingBox(). The implementation in BlockStateContainer
   * currently calls the getBoundingBox() method in the Block class.
   */
  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
    state = getActualState(state, source, pos);
    AxisAlignedBB box = bounding_box[0];
    if(state.getValue(WEST)){  box = box.union(bounding_box[1]);}
    if(state.getValue(DOWN)){  box = box.union(bounding_box[2]);}
    if(state.getValue(NORTH)){ box = box.union(bounding_box[3]);}
    if(state.getValue(EAST)){  box = box.union(bounding_box[4]);}
    if(state.getValue(UP)){    box = box.union(bounding_box[5]);}
    if(state.getValue(SOUTH)){ box = box.union(bounding_box[6]);}
    return box;
  }

  /**
   * This is here because the Wire class is derived from the BlockTile class, which is derived from the BlockContainer class,
   * which implements the ITileProvider interface, and must implement the createNewTileEntity() method. However more specific
   * Wire classes that are derived from this one may not need to produce a Tile Entity, so we return null. Override this method
   * and specify a Tile Entity to spawn if you need to.
   */
  @Override
  public TileEntity createNewTileEntity(IBlockReader worldIn){
    return null;
  }

  /** In case your block has transparency or not */
  @Override
  @SuppressWarnings("deprecation")
  public boolean isOpaqueCube(IBlockState state){
    return false;
  }

  /** If your block is a full block or a custom model */
  @Override
  @SuppressWarnings("deprecation")
  public boolean isFullCube(IBlockState state){
    return false;
  }

}
