package addsynth.energy.gameplay.tiles;

import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.EnergyCompat;
import addsynth.energy.tiles.TileEnergyBattery;
import addsynth.overpoweredmod.config.Values;
import net.minecraft.nbt.CompoundNBT;

public final class TileUniversalEnergyTransfer extends TileEnergyBattery {

  public enum TRANSFER_MODE {
    BI_DIRECTIONAL(true,  true,  true,  "Bi-Directional"),
    RECEIVE(       true,  false, true,  "Receive Only"),
    EXTRACT(       false, true,  true,  "Extract Only"),
    EXTERNAL(      true,  true,  false, "External Only"),
    INTERNAL(      false, false, true,  "Internal Only"),
    NO_TRANSFER(   false, false, false, "No Transfer");

    private final boolean canReceive;
    private final boolean canExtract;
    private final boolean integrate;
    public final String text;

    private TRANSFER_MODE(final boolean receive, final boolean extract, final boolean integrate, final String text){
      this.canReceive = receive;
      this.canExtract = extract;
      this.integrate = integrate;
      this.text = text;
    }
  }

  private TRANSFER_MODE transfer_mode = TRANSFER_MODE.BI_DIRECTIONAL;

  public TileUniversalEnergyTransfer(){
    super(new CustomEnergyStorage(Values.universal_energy_interface_buffer));
  }

  public final TRANSFER_MODE get_transfer_mode(){
    return transfer_mode;
  }
  
  public final void set_next_transfer_mode(){
    final int mode = (transfer_mode.ordinal() + 1) % TRANSFER_MODE.values().length;
    transfer_mode = TRANSFER_MODE.values()[mode];
    energy.setTransferRate(transfer_mode.integrate ? Values.universal_energy_interface_buffer : 0);
    update_data();
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    transfer_mode = TRANSFER_MODE.values()[nbt.getByte("Transfer Mode")];
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putByte("Transfer Mode", (byte)transfer_mode.ordinal());
    return nbt;
  }

  @Override
  public final void update(){
    if(world.isRemote == false){
      final int energy_snapshot = energy.getEnergy();
      final EnergyCompat.CompatEnergyNode[] energy_nodes = EnergyCompat.getConnectedEnergy(pos, world);
      if(energy_nodes.length > 0){
        if(transfer_mode.canReceive){
          EnergyCompat.acceptEnergy(energy_nodes, this.energy);
        }
        if(transfer_mode.canExtract){
          EnergyCompat.transmitEnergy(energy_nodes, this.energy);
        }
      }
      if(transfer_mode.integrate){
        if(energy.hasEnergy()){
          get_machines_that_need_energy();
          if(machines.size() > 0){
            give_machines_energy();
          }
        }
      }
      if(energy.getEnergy() != energy_snapshot){
        update_data();
      }
    }
    energy.update();
  }

}
