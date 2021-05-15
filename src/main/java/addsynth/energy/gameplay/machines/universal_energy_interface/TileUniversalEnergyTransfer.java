package addsynth.energy.gameplay.machines.universal_energy_interface;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.util.java.ArrayUtil;
import addsynth.energy.api.energy_network.tiles.BasicEnergyNetworkTile;
import addsynth.energy.api.main.Energy;
import addsynth.energy.api.main.ICustomEnergyUser;
import addsynth.energy.compat.energy.EnergyCompat;
import addsynth.energy.compat.energy.forge.ForgeEnergyIntermediary;
import addsynth.energy.gameplay.Config;
import addsynth.energy.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public final class TileUniversalEnergyTransfer extends BasicEnergyNetworkTile
  // The Universal Energy Interface should REMAIN as an ICustomEnergyUser
  // and SHOULD NOT be treated as a Battery. Confirmed.
  implements ICustomEnergyUser, INamedContainerProvider {

  private final Energy energy = new Energy(Config.universal_energy_interface_buffer.get());

  private final ForgeEnergyIntermediary forge_energy = new ForgeEnergyIntermediary(energy){
    @Override
    public boolean canExtract(){
      return super.canExtract() && transfer_mode.canExtract;
    }
    @Override
    public boolean canReceive(){
      return super.canReceive() && transfer_mode.canReceive;
    }
  };

  private boolean changed;

  private TRANSFER_MODE transfer_mode = TRANSFER_MODE.BI_DIRECTIONAL;

  public TileUniversalEnergyTransfer(){
    super(Tiles.UNIVERSAL_ENERGY_INTERFACE);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      try{
        super.tick(); // handles EnergyNetwork stuff
        final EnergyCompat.CompatEnergyNode[] energy_nodes = EnergyCompat.getConnectedEnergy(pos, world);
        if(energy_nodes.length > 0){
          if(transfer_mode.canReceive){
            EnergyCompat.acceptEnergy(energy_nodes, this.energy);
          }
          if(transfer_mode.canExtract){
            EnergyCompat.transmitEnergy(energy_nodes, this.energy);
          }
        }
        if(energy.tick()){
          changed = true;
        }
        if(changed){
          update_data();
          changed = false;
        }
      }
      catch(Exception e){
        report_ticking_error(e);
      }
    }
  }

  public final TRANSFER_MODE get_transfer_mode(){
    return transfer_mode;
  }
  
  public final void set_next_transfer_mode(){
    final int mode = (transfer_mode.ordinal() + 1) % TRANSFER_MODE.values().length;
    transfer_mode = TRANSFER_MODE.values()[mode];
    changed = true;
  }

  @Override
  public final double getRequestedEnergy(){
    if(transfer_mode.canExtract){
      return energy.getRequestedEnergy();
    }
    return 0;
  }

  @Override
  public final double getAvailableEnergy(){
    if(transfer_mode.canReceive){
      return energy.getAvailableEnergy();
    }
    return 0;
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    energy.loadFromNBT(nbt);
    transfer_mode = ArrayUtil.getArrayValue(TRANSFER_MODE.values(), nbt.getByte("Transfer Mode"));
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    energy.saveToNBT(nbt);
    nbt.putByte("Transfer Mode", (byte)transfer_mode.ordinal());
    return nbt;
  }

  @Override
  public @Nonnull <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction facing){
    if(removed == false){
      if(capability == CapabilityEnergy.ENERGY){
        return forge_energy != null ? (LazyOptional.of(()->forge_energy)).cast() : LazyOptional.empty();
      }
      return super.getCapability(capability, facing);
    }
    return LazyOptional.empty();
  }
  
  @Override
  public final Energy getEnergy(){
    return energy;
  }
  
  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerUniversalInterface(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
