package addsynth.core.tiles;

import addsynth.core.inventory.IInputInventory;
import addsynth.core.inventory.IOutputInventory;
import net.minecraft.tileentity.TileEntityType;

/** This is for TileEntities that have an Input Inventory and an Output Inventory,
 *  and possibly a Working Inventory as well. This is a machine that works on items
 *  without using any Energy.
 * @author ADDSynth
 */
public abstract class TileMachine extends TileBase implements IInputInventory, IOutputInventory {

  public TileMachine(TileEntityType type){
    super(type);
  }

}
