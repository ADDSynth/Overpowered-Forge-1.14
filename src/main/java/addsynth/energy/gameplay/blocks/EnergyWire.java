package addsynth.energy.gameplay.blocks;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.energy.blocks.Wire;
import addsynth.energy.gameplay.tiles.TileEnergyWire;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class EnergyWire extends Wire {

  public EnergyWire(final String name){
    super(Block.Properties.create(Material.WOOL, MaterialColor.GRAY).hardnessAndResistance(0.1f, 0.0f));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(OverpoweredMod.machines_creative_tab));
  }

  @Override
  protected final ArrayList<Direction> get_valid_sides(final IBlockReader world, final BlockPos pos){
    ArrayList<Direction> valid_sides = new ArrayList<>(6);
    boolean can_use_energy;
    for(Direction side : Direction.values()){
      can_use_energy = false;
      TileEntity tile = world.getTileEntity(pos.offset(side));
      if(tile != null){
        IEnergyStorage energy = tile.getCapability(CapabilityEnergy.ENERGY, side.getOpposite()).orElse(null);
        if(energy != null){
          can_use_energy = true;
        }
        if(tile instanceof TileEnergyWire || can_use_energy){
          valid_sides.add(side);
        }
      }
    }
    return valid_sides;
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader world){
    return new TileEnergyWire();
  }

  /** Starting in Minecraft 1.11, {@link World#addTileEntity(TileEntity)} no longer calls
   *  {@link World#updateComparatorOutputLevel(BlockPos, Block)} at the end of the function.
   *  For this reason we have to use {@link #neighborChanged(IBlockState, World, BlockPos, Block, BlockPos)}
   *  instead of {@link #onNeighborChange(IBlockAccess, BlockPos, BlockPos)} like we do in Minecraft 1.10.
   *  As it turns out, not even Vanilla Minecraft uses the <code>onNeighborChange()</code> function a whole lot.
   */
  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    if(world.isRemote == false){
      final BlockNetwork network = BlockNetwork.getNetwork(world, pos);
      if(network != null){
        network.neighbor_was_changed(pos, neighbor);
      }
    }
  }

  @Override
  public final void onReplaced(BlockState state, final World world, final BlockPos pos, final BlockState newstate, boolean isMoving){ // called on server side only
    final BlockNetwork network = BlockNetwork.getNetwork(world, pos); // get network while block still exists
    super.onReplaced(state, world, pos, newstate, isMoving);          // finish breaking the block
    BlockNetwork.block_was_broken(network, world, pos, this);         // update network
  }

}
