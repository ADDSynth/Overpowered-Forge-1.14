package addsynth.energy.gameplay.blocks;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.energy.blocks.Wire;
import addsynth.energy.gameplay.tiles.TileEnergyWire;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class EnergyWire extends Wire {

  public EnergyWire(final String name){
    super(Material.CLOTH, MapColor.GRAY);
    OverpoweredMod.registry.register_block(this, name);
    setHardness(0.1f);
  }

  @Override
  protected final ArrayList<EnumFacing> get_valid_sides(final IBlockAccess world, final BlockPos pos){
    ArrayList<EnumFacing> valid_sides = new ArrayList<>(6);
    boolean can_use_energy;
    for(EnumFacing side : EnumFacing.values()){
      can_use_energy = false;
      TileEntity tile = world.getTileEntity(pos.offset(side));
      if(tile != null){
        if(tile.hasCapability(CapabilityEnergy.ENERGY, side.getOpposite())){
          IEnergyStorage energy = tile.getCapability(CapabilityEnergy.ENERGY, side.getOpposite());
          if(energy != null){
            can_use_energy = true;
          }
        }
        if(tile instanceof TileEnergyWire || can_use_energy){
          valid_sides.add(side);
        }
      }
    }
    return valid_sides;
  }

  @Override
  public final TileEntity createNewTileEntity(final World world, final int meta){
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
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor){
    if(world.isRemote == false){
      final BlockNetwork network = BlockNetwork.getNetwork(world, pos);
      if(network != null){
        network.neighbor_was_changed(pos, neighbor);
      }
    }
  }

  @Override
  public final void breakBlock(final World world, final BlockPos pos, final IBlockState state){ // called on server side only
    final BlockNetwork network = BlockNetwork.getNetwork(world, pos); // get network while block still exists
    super.breakBlock(world, pos, state);                              // finish breaking the block
    BlockNetwork.block_was_broken(network, world, pos, this);         // update network
  }

}
