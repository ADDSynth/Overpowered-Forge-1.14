package addsynth.energy;

import java.util.ArrayList;
import addsynth.core.game.Compatability;
import addsynth.core.util.JavaUtils;
import addsynth.core.util.MathUtility;
/*
import cofh.redstoneflux.api.IEnergyHandler;
import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.darkhax.tesla.api.*;
import net.darkhax.tesla.capability.TeslaCapabilities;
*/
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
    
    public final boolean available;
    
    public final void setError(final Exception e){
    }
    
    private EnergyType(final boolean loaded){
      this.available = loaded;
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
        final IEnergyStorage energy = tile.getCapability(CapabilityEnergy.ENERGY, capability_side).orElseGet(null);
        if(energy != null){
          nodes.add(new CompatEnergyNode(EnergyType.FORGE, energy, capability_side));
          continue;
        }
        
        try{
        
          // RF Energy
          if(EnergyType.RF.available){
            /*
            if(JavaUtils.classExists("cofh.redstoneflux.api.IEnergyHandler")){
              if(tile instanceof IEnergyHandler){
                nodes.add(new CompatEnergyNode(EnergyType.RF, tile, capability_side));
                continue;
              }
            }
            if(JavaUtils.classExists("cofh.redstoneflux.api.IEnergyReceiver")){
              if(tile instanceof IEnergyReceiver){
                nodes.add(new CompatEnergyNode(EnergyType.RF, tile, capability_side));
                continue;
              }
            }
            if(JavaUtils.classExists("cofh.redstoneflux.api.IEnergyProvider")){
              if(tile instanceof IEnergyProvider){
                nodes.add(new CompatEnergyNode(EnergyType.RF, tile, capability_side));
                continue;
              }
            }
            */
          }
          
          // Tesla Energy
          if(EnergyType.TESLA.available){
            // MAYBE: And then I rediscovered the existance of Forge's CapabilityInjector annotation.
            /*
            if(JavaUtils.classExists("net.darkhax.tesla.capability.TeslaCapabilities")){
              if(tile.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, capability_side)){
                energy = tile.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, capability_side);
                if(energy != null){
                  nodes.add(new CompatEnergyNode(EnergyType.TESLA, energy, capability_side));
                  continue;
                }
              }
              if(tile.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, capability_side)){
                energy = tile.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, capability_side);
                if(energy != null){
                  nodes.add(new CompatEnergyNode(EnergyType.TESLA, energy, capability_side));
                  continue;
                }
              }
              if(tile.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, capability_side)){
                energy = tile.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, capability_side);
                if(energy != null){
                  nodes.add(new CompatEnergyNode(EnergyType.TESLA, energy, capability_side));
                  continue;
                }
              }
            }
            */
          }
        }
        catch(Exception e){
        }
      }
    }
    return nodes.toArray(new CompatEnergyNode[nodes.size()]);
  }

  public static final void acceptEnergy(final CompatEnergyNode[] nodes, final CustomEnergyStorage our_energy){
    final int energy_needed = our_energy.getEnergyNeeded();
    final int[] available_energy = new int[nodes.length];
    int i;
    // get available energy
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: available_energy[i] = GetForgeEnergy(       nodes[i].energy, energy_needed, true);                break;
        //case RF:    available_energy[i] = GetRedstoneFluxEnergy(nodes[i].energy, energy_needed, true, nodes[i].side); break;
        //case TESLA: available_energy[i] = GetTeslaEnergy(       nodes[i].energy, energy_needed, true);                break;
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
        case FORGE: GetForgeEnergy(       nodes[i].energy, energy_to_extract[i], false);                break;
        //case RF:    GetRedstoneFluxEnergy(nodes[i].energy, energy_to_extract[i], false, nodes[i].side); break;
        //case TESLA: GetTeslaEnergy(       nodes[i].energy, energy_to_extract[i], false);                break;
        }
        our_energy.receiveEnergy(energy_to_extract[i]);
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }
  }

  private static final int GetForgeEnergy(final Object input, final int energy_requested, final boolean simulate){
    final IEnergyStorage energy = (IEnergyStorage)input;
    if(energy.canExtract()){
      return energy.extractEnergy(energy_requested, simulate);
    }
    return 0;
  }

/*
  private static final int GetRedstoneFluxEnergy(final Object input, final int energy_requested, final boolean simulate, final Direction side){
    if(input instanceof IEnergyProvider){
      final IEnergyProvider energy = (IEnergyProvider)input;
      return energy.extractEnergy(side, energy_requested, simulate);
    }
    return 0;
  }

  private static final int GetTeslaEnergy(final Object input, final int energy_requested, final boolean simulate){
    if(input instanceof ITeslaProducer){
      final ITeslaProducer energy = (ITeslaProducer)input;
      return JavaUtils.cast_to_int(energy.takePower(energy_requested, simulate));
    }
    return 0;
  }
*/

  public static final void transmitEnergy(final CompatEnergyNode[] nodes, final CustomEnergyStorage our_energy){
    final int[] energy_available = MathUtility.divide_evenly(our_energy.getEnergy(), nodes.length);
    int actual_energy_extracted = 0;
    int i;
    for(i = 0; i < nodes.length; i++){
      try{
        switch(nodes[i].type){
        case FORGE: actual_energy_extracted = SendForgeEnergy(       nodes[i].energy, energy_available[i]);                break;
        //case RF:    actual_energy_extracted = SendRedstoneFluxEnergy(nodes[i].energy, energy_available[i], nodes[i].side); break;
        //case TESLA: actual_energy_extracted = SendTeslaEnergy(       nodes[i].energy, energy_available[i]);                break;
        }
        our_energy.extractEnergy(actual_energy_extracted, false);
      }
      catch(Exception e){
        nodes[i].type.setError(e);
      }
    }
  }
  
  private static final int SendForgeEnergy(final Object input, final int transmitted_energy){
    final IEnergyStorage energy = (IEnergyStorage)input;
    if(energy.canReceive()){
      return energy.receiveEnergy(transmitted_energy, false);
    }
    return 0;
  }
  
/*
  private static final int SendRedstoneFluxEnergy(final Object input, final int transmitted_energy, final Direction side){
    if(input instanceof IEnergyReceiver){
      final IEnergyReceiver energy = (IEnergyReceiver)input;
      return energy.receiveEnergy(side, transmitted_energy, false);
    }
    return 0;
  }
  
  private static final int SendTeslaEnergy(final Object input, final int transmitted_energy){
    if(input instanceof ITeslaConsumer){
      final ITeslaConsumer energy = (ITeslaConsumer)input;
      return JavaUtils.cast_to_int(energy.givePower(transmitted_energy, false));
    }
    return 0;
  }
*/

}
