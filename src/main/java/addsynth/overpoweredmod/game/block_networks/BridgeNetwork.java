package addsynth.overpoweredmod.game.block_networks;

import addsynth.core.block_network.BlockNetwork;
import addsynth.energy.CustomEnergyStorage;
import addsynth.overpoweredmod.tiles.machines.other.TileSuspensionBridge;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class BridgeNetwork extends BlockNetwork<TileSuspensionBridge> {

  public final CustomEnergyStorage energy = new CustomEnergyStorage(0,1000);

  public BridgeNetwork(final World world, final TileSuspensionBridge first_tile){
    super(world, first_tile.getBlockType(), first_tile);
    energy.set_receive_only();
  }

  @Override
  protected final void clear_custom_data(){
  }

  @Override
  protected final void customSearch(final Block block, final BlockPos position){
  }

  @Override
  public final void neighbor_was_changed(final BlockPos current_position, final BlockPos position_of_neighbor){
  }

}
