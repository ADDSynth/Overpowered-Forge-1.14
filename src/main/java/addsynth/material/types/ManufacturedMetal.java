package addsynth.material.types;

import addsynth.material.MiningStrength;
import net.minecraft.block.material.MaterialColor;

public final class ManufacturedMetal extends Metal {

  /** Placeholder Metal */
  public ManufacturedMetal(final String name){
    super(name);
  }

  /** MiningStrength here might be used in the future if I want the metal blocks to
   *  require a certain pickaxe strength to mine them.
   * @param unlocalized_name
   * @param color
   * @param strength
   */
  public ManufacturedMetal(final String unlocalized_name, final MaterialColor color, MiningStrength strength){
    super(unlocalized_name, color);
  }

}
