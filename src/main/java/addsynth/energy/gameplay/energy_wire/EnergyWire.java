package addsynth.energy.gameplay.energy_wire;

import addsynth.core.block_network.BlockNetwork;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.blocks.Wire;
import addsynth.energy.energy_network.tiles.AbstractEnergyNetworkTile;
import addsynth.energy.main.IEnergyUser;
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

public final class EnergyWire extends Wire {

  public EnergyWire(final String name){
    super(Block.Properties.create(Material.WOOL, MaterialColor.GRAY).hardnessAndResistance(0.1f, 0.0f));
    ADDSynthEnergy.registry.register_block(this, name, new Item.Properties().group(ADDSynthEnergy.creative_tab));
  }

  @Override
  protected final boolean[] get_valid_sides(final IBlockReader world, final BlockPos pos){
    final boolean[] valid_sides = new boolean[6];
    for(Direction side : Direction.values()){
      valid_sides[side.ordinal()] = false;
      final TileEntity tile = world.getTileEntity(pos.offset(side));
      if(tile != null){
        if(tile instanceof AbstractEnergyNetworkTile || tile instanceof IEnergyUser){
          valid_sides[side.ordinal()] = true;
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
   *  For this reason we have to use {@link #neighborChanged(BlockState, World, BlockPos, Block, BlockPos, boolean)}
   *  instead of {@link #onNeighborChange(IBlockAccess, BlockPos, BlockPos)} like we do in Minecraft 1.10.
   *  As it turns out, not even Vanilla Minecraft uses the <code>onNeighborChange()</code> function a whole lot.
   */
  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    if(world.isRemote == false){
      final BlockNetwork network = BlockNetwork.getNetwork(world, pos);
      if(network != null){
        network.neighbor_was_changed(pos, neighbor);
      }
    }
  }

}
