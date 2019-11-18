package addsynth.overpoweredmod.tiles.machines.other;

import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyReceiver;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.tiles.Tiles;

public final class TileSuspensionBridge extends TileEnergyReceiver implements IBlockNetworkUser {

  public TileSuspensionBridge(){
    super(Tiles.SUSPENSION_BRIDGE, 1, Lens.index, 0, new CustomEnergyStorage());
  }

  @Override
  public final void setBlockNetwork(BlockNetwork network){
  }

  @Override
  public final BlockNetwork getBlockNetwork(){
    return null;
  }

  @Override
  public final BlockNetwork getNetwork(){
    return null;
  }

  @Override
  public final void createBlockNetwork(){
  }

}
