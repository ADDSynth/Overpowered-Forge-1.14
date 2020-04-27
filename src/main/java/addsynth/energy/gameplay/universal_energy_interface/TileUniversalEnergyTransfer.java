package addsynth.energy.gameplay.universal_energy_interface;

import javax.annotation.Nullable;
import addsynth.energy.Energy;
import addsynth.energy.compat.energy.EnergyCompat;
import addsynth.energy.energy_network.tiles.TileEnergyBattery;
import addsynth.energy.gameplay.Config;
import addsynth.energy.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileUniversalEnergyTransfer extends TileEnergyBattery implements ITickableTileEntity, INamedContainerProvider {

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
    super(Tiles.UNIVERSAL_ENERGY_INTERFACE, new Energy(Config.universal_energy_interface_buffer.get()));
  }

  public final TRANSFER_MODE get_transfer_mode(){
    return transfer_mode;
  }
  
  public final void set_next_transfer_mode(){
    final int mode = (transfer_mode.ordinal() + 1) % TRANSFER_MODE.values().length;
    transfer_mode = TRANSFER_MODE.values()[mode];
    energy.setTransferRate(transfer_mode.integrate ? Config.universal_energy_interface_buffer.get() : 0);
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
  public final void tick(){
    if(world.isRemote == false){
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
