package addsynth.core.items;

import net.minecraft.item.Item;

/** <p>The only thing this does is override the toString() method to provide a more readable
 *     string representation for Items.
 *  <p>THIS DOES NOT REGISTER YOUR ITEM! Create your own mod-specific Base Item class that extends
 *     this class. Register your items there using that mod's {@link addsynth.core.game.RegistryUtil} class.
 * @author ADDSynth
 */
public abstract class BaseItem extends Item {

  @Override
  public String toString(){
    return getTranslationKey();
  }

}
