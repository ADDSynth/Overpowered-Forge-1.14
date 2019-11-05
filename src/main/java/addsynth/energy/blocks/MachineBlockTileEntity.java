package addsynth.energy.blocks;

import addsynth.core.tiles.TileMachine;
import addsynth.core.util.MinecraftUtility;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
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

  public MachineBlockTileEntity(final MapColor color){
    super(color);
  }

  @Override
  public void breakBlock(final World world, final BlockPos pos, final IBlockState state){
    final TileMachine tile = MinecraftUtility.getTileEntity(pos, world, TileMachine.class);
    if(tile != null){
      tile.drop_inventory();
    }
    super.breakBlock(world, pos, state);
  }
  
}
