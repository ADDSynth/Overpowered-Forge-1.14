package addsynth.energy.gameplay.universal_energy_interface;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.util.JavaUtils;
import addsynth.energy.Energy;
import addsynth.energy.compat.energy.EnergyCompat;
import addsynth.energy.compat.energy.forge.ForgeEnergyIntermediary;
import addsynth.energy.gameplay.Config;
import addsynth.energy.registers.Tiles;
import addsynth.energy.tiles.TileEnergyWithStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public final class TileUniversalEnergyTransfer extends TileEnergyWithStorage implements ITickableTileEntity, INamedContainerProvider {

  private final ForgeEnergyIntermediary forge_energy;

  private TRANSFER_MODE transfer_mode = TRANSFER_MODE.BI_DIRECTIONAL;

  public TileUniversalEnergyTransfer(){
    super(Tiles.UNIVERSAL_ENERGY_INTERFACE, new Energy(Config.universal_energy_interface_buffer.get()));
    forge_energy = new ForgeEnergyIntermediary(energy){
      @Override
      public boolean canExtract(){
        return super.canExtract() && transfer_mode.canExtract;
      }
      @Override
      public boolean canReceive(){
        return super.canReceive() && transfer_mode.canReceive;
      }
    };
  }

  public final TRANSFER_MODE get_transfer_mode(){
    return transfer_mode;
  }
  
  public final void set_next_transfer_mode(){
    final int mode = (transfer_mode.ordinal() + 1) % TRANSFER_MODE.values().length;
    transfer_mode = TRANSFER_MODE.values()[mode];
    // energy.setTransferRate(transfer_mode.integrate ? Config.universal_energy_interface_buffer.get() : 0);
    update_data();
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    transfer_mode = JavaUtils.getArrayValue(TRANSFER_MODE.values(), nbt.getByte("Transfer Mode"));
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putByte("Transfer Mode", (byte)transfer_mode.ordinal());
    return nbt;
  }

  @Override
  public @Nonnull <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction facing){
    if(capability == CapabilityEnergy.ENERGY){
      return (LazyOptional.of(()->forge_energy)).cast();
    }
    return super.getCapability(capability, facing);
  }
  
  @Override
  public final void tick(){
    if(world.isRemote == false){
      final EnergyCompat.CompatEnergyNode[] energy_nodes = EnergyCompat.getConnectedEnergy(pos, world);
      if(energy_nodes.length > 0){
        if(transfer_mode.canReceive){
          EnergyCompat.acceptEnergy(energy_nodes, this.energy);
        }
        if(transfer_mode.canExtract){
          EnergyCompat.transmitEnergy(energy_nodes, this.energy);
        }
      }
      energy.update(world);
    }
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
