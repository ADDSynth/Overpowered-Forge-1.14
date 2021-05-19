package addsynth.energy.compat.energy;

import java.util.ArrayList;
import addsynth.core.game.Compatability;
import addsynth.core.util.math.MathUtility;
import addsynth.energy.compat.energy.forge.ForgeEnergy;
import addsynth.energy.compat.energy.redstoneflux.RedstoneFluxEnergy;
import addsynth.energy.compat.energy.tesla.TeslaEnergy;
import addsynth.energy.lib.main.Energy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class EnergyCompat {

  public enum EnergyType {
    FORGE(true),
    RF(Compatability.REDSTONE_FLUX.loaded),
    TESLA(Compatability.TESLA.loaded);
    
    public final boolean exists;
    
    public final void setError(final Exception e){
    }
    
    private EnergyType(final boolean loaded){
      this.exists = loaded;
    }
  }
  
  public static final class CompatEnergyNode {
    public final EnergyType type;
    public final Object energy;
    public final Direction side;
    
    public CompatEnergyNode(final EnergyType type, final Object energy, final Direction side){
      this.type = type;
      this.energy = energy;
      this.side = side;
    }
  }
  
  public static final CompatEnergyNode[] getConnectedEnergy(final BlockPos position, final World world){
    final ArrayList<CompatEnergyNode> nodes = new ArrayList<>(6);
    TileEntity tile;
    Direction capability_side;
    // Object energy;

    for(Direction side : Direction.values()){
      tile = world.getTileEntity(position.offset(side));
      if(tile != null){
        capability_side = side.getOpposite();

        // Forge Energy
        final IEnergyStorage energy = tile.getCapability(CapabilityEnergy.ENERGY, capability_side).orElse(null);
        if(energy != null){
          nodes.add(new CompatEnergyNode(EnergyType.FORGE, energy, capability_side));
          continue;
        }
        
        try{
        
          // RF Energy
          if(EnergyType.RF.exists){
            if(RedstoneFluxEnergy.check(tile)){
              nodes.add(new CompatEnergyNode(EnergyType.RF, tile, capability_side));
              continue;
            }
          }
          
          // Tesla Energy
          if(EnergyType.TESLA.exists){
            if(TeslaEnergy.check(tile)){
              continue;
            }
          }
        }
        catch(Exception e){
        }
      }
    }
    return nodes.toArray(new CompatEnergyNode[nodes.size()]);
  }

  public static final void acceptEnergy(final CompatEnergyNode[] nodes, final Energy our_energy){
    // get needed energy
    final int energy_needed = (int)our_energy.getEnergyNeeded();
    final int[] available_energy = new int[nodes.length];
    int i;

    // get available energy
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: available_energy[i] = ForgeEnergy       .get(nodes[i].energy, energy_needed, true);                break;
        case RF:    available_energy[i] = RedstoneFluxEnergy.get(nodes[i].energy, energy_needed, true, nodes[i].side); break;
        case TESLA: available_energy[i] = TeslaEnergy       .get(nodes[i].energy, energy_needed, true);                break;
        }
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }

    // set requested amount for each energy source
    final int[] energy_to_extract = MathUtility.divide_evenly(energy_needed, available_energy);

    // extract energy
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: ForgeEnergy       .get(nodes[i].energy, energy_to_extract[i], false);                break;
        case RF:    RedstoneFluxEnergy.get(nodes[i].energy, energy_to_extract[i], false, nodes[i].side); break;
        case TESLA: TeslaEnergy       .get(nodes[i].energy, energy_to_extract[i], false);                break;
        }
        our_energy.receiveEnergy(energy_to_extract[i]);
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }
  }

  public static final void transmitEnergy(final CompatEnergyNode[] nodes, final Energy our_energy){
    // get available energy, divide evenly amongst the number of external machines.
    final int[] energy_available = MathUtility.divide_evenly((int)our_energy.getEnergy(), nodes.length);
    int actual_energy_extracted = 0;
    int i;
    // attempt to insert energy into external machines, record energy that was really transferred.
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: actual_energy_extracted = ForgeEnergy.send(       nodes[i].energy, energy_available[i]);                break;
        case RF:    actual_energy_extracted = RedstoneFluxEnergy.send(nodes[i].energy, energy_available[i], nodes[i].side); break;
        case TESLA: actual_energy_extracted = TeslaEnergy.send(       nodes[i].energy, energy_available[i]);                break;
        }
        // decrease internal energy by the amount that was actually transferred.
        our_energy.extractEnergy(actual_energy_extracted);
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }
  }
  
}
