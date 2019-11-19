package addsynth.energy.blocks;

import addsynth.core.tiles.TileMachine;
import addsynth.core.util.MinecraftUtility;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** If you don't want your block to drop its inventory or update Energy
 *  Networks, then please use {@link MachineBlock}.
 * @author ADDSynth
 * @since June 14, 2019
 */
public abstract class MachineBlockTileEntity extends MachineBlock {

  public MachineBlockTileEntity(){
    super();
  }

  public MachineBlockTileEntity(final MaterialColor color){
    super(color);
  }

  public MachineBlockTileEntity(final SoundType sound){
    super(sound);
  }

  @Override
  @SuppressWarnings("deprecation")
   public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving){
    final TileMachine tile = MinecraftUtility.getTileEntity(pos, world, TileMachine.class);
    if(tile != null){
      tile.drop_inventory();
    }
    super.onReplaced(state, world, pos, newState, isMoving);
  }
  
}
