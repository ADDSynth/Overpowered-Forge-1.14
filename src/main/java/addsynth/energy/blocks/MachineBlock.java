package addsynth.energy.blocks;

import addsynth.core.blocks.BlockTile;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;

/** <p>This is your typical machine-type block with a silver appearance and metallic properties.
 *  <p>THIS DOES NOT REGISTER YOUR BLOCKS! Mods that extend from this class must register
 *     their individual blocks using that mod's {@link addsynth.core.game.RegistryUtil} instance.
 * @author ADDSynth
 */
public abstract class MachineBlock extends BlockTile {

  public MachineBlock(){
    super(Material.IRON, Material.IRON.getMaterialMapColor());
    setHardness(3.5f);
    setSoundType(SoundType.METAL);
    setResistance(10.0f);
  }

  public MachineBlock(final MapColor color){
    super(Material.IRON, color);
    setHardness(3.5f);
    setSoundType(SoundType.METAL);
    setResistance(10.0f);
  }

}
