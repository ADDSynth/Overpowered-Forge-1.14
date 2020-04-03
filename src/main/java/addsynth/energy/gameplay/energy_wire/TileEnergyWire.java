package addsynth.energy.gameplay.energy_wire;

import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.EnergyNetwork;
import addsynth.energy.registers.Tiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * This is the TileEntity that Energy Wires should use to form an Energy Network.
 * Have your Wire blocks create a new TileEntity instance of this class type.
 * This class and Energy Network will automatically detect the Block type and
 * connect to other blocks of the same type.
 * @author ADDSynth
 */
public final class TileEnergyWire extends TileEntity implements IBlockNetworkUser, ITickableTileEntity {

  private EnergyNetwork network;
  private boolean first_tick = true;

  public TileEnergyWire(){
    super(Tiles.ENERGY_WIRE);
  }

  /**
   * When TileEntity loads, search for surrounding wires and join their networks. onBlockBreak() method is
   * implemented in the EnergyWire block and it seperates networks.
   */
  @Override
  public final void onLoad(){
  }

  @Override
  public void tick(){
    if(first_tick){
      if(world.isRemote == false){
        if(network == null){
          createBlockNetwork();
        }
      }
      first_tick = false;
    }
  }

  @Override
  public void setBlockNetwork(BlockNetwork network){
    this.network = (EnergyNetwork)network;
  }

  @Override
  public BlockNetwork getNetwork(){
    return network;
  }

  @Override
  public BlockNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public void createBlockNetwork(){
    if(world.isRemote == false){
      if(network == null){
        network = new EnergyNetwork(world, this);
      }
      network.updateNetwork(pos);
    }
  }

}
