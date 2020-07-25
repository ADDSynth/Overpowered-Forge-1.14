package addsynth.energy.blocks;

import addsynth.core.blocks.BlockTile;
import addsynth.core.tiles.TileMachine;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** <p>This is your typical machine-type block with a silver appearance and metallic properties.
 *  <p>THIS DOES NOT REGISTER YOUR BLOCKS! Mods that extend from this class must register
 *     their individual blocks using that mod's {@link addsynth.core.game.RegistryUtil} instance.
 * @author ADDSynth
 */
public abstract class MachineBlock extends BlockTile {

  public MachineBlock(){
    super(Block.Properties.create(Material.IRON, MaterialColor.IRON).sound(SoundType.METAL).hardnessAndResistance(3.5f, 6.0f));
  }

  public MachineBlock(final MaterialColor color){
    super(Block.Properties.create(Material.IRON, color).sound(SoundType.METAL).hardnessAndResistance(3.5f, 6.0f));
  }

  public MachineBlock(final SoundType sound){
    super(Block.Properties.create(Material.IRON, MaterialColor.IRON).sound(sound).hardnessAndResistance(3.5f, 6.0f));
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
