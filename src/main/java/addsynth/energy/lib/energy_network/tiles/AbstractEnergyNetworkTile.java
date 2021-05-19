package addsynth.energy.lib.energy_network.tiles;

import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.tiles.TileBase;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class AbstractEnergyNetworkTile extends TileBase
  implements IBlockNetworkUser<EnergyNetwork>, ITickableTileEntity {

  protected EnergyNetwork network;

  public AbstractEnergyNetworkTile(final TileEntityType type){
    super(type);
  }

}
