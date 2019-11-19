package addsynth.energy.blocks;

import addsynth.core.blocks.BlockTile;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

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

}
