package addsynth.overpoweredmod.machines.laser.machine;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyReceiver;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.machines.laser.network_messages.LaserClientSyncMessage;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileLaserHousing extends TileEnergyReceiver implements ITickableTileEntity, IBlockNetworkUser, INamedContainerProvider {

  private LaserNetwork network;
  private boolean first_tick = true;

  private int laser_distance = Config.default_laser_distance.get();
  /** Set by {@link LaserNetwork#updateLaserNetwork()} method and used by
   *  {@link addsynth.overpoweredmod.machines.laser.machine.GuiLaserHousing}.
   */
  public int number_of_lasers;
  private boolean auto_shutoff = true;

  public TileLaserHousing(){
    super(Tiles.LASER_MACHINE, 0, null, 0, new CustomEnergyStorage());
  }

  @Override
  public final void onLoad(){
  }

  @Override
  public final void tick(){
    if(first_tick){
      if(world.isRemote == false){
        if(network == null){
          createBlockNetwork();
          first_tick = false;
        }
      }
    }
    if(world != null){
      if(world.isRemote == false){
        if(network != null){
          // It's okay to call the update method so long as it's only on the server side.
          network.update(this); // MAYBE: It may be possible to move the redstone detection to the Block class, check out how the TileNoteBlock does it.
        }
      }
    }
    energy.update();
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    energy.readFromNBT(nbt);
    laser_distance = nbt.getInt("Laser Distance");
    auto_shutoff = nbt.getBoolean("Auto Shutoff");
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    energy.writeToNBT(nbt);
    nbt.putInt("Laser Distance", laser_distance);
    nbt.putBoolean("Auto Shutoff", auto_shutoff);
    return nbt;
  }

  @Override
  public final CustomEnergyStorage getEnergy(){
    if(world.isRemote){
      return energy; // only guis should use this.
    }
    return getNetwork().energy;
  }

  // Only the gui calls these
  public final int getLaserDistance(){ return laser_distance; }
  public final boolean getAutoShutoff(){ return auto_shutoff; }

  public final void setDataDirectlyFromNetwork(final CustomEnergyStorage energy, final int laser_distance, final boolean running, final boolean shutoff){
    this.energy.set(energy);
    this.laser_distance = laser_distance;
    this.running = running;
    this.auto_shutoff = shutoff;
    super.update_data();
  }

  @Override
  public final void setBlockNetwork(final BlockNetwork network){
    this.network = (LaserNetwork)network;
  }

  /**
   * Keep this private to the TileLaserHousing. External code will have to call the getEnergy() and
   * getLaserDistance() functions to get those variables.<br>
   * This is public because {@link LaserClientSyncMessage} needs to get the {@link LaserNetwork} on the client side to
   *   set the laser count. and the nbt data sets the laser distance and energy on client side too.
   * @return LaserNetwork
   */
  @Override
  public final LaserNetwork getNetwork(){
    if(network == null){
      createBlockNetwork();
    }
    return network;
  }

  @Override
  public final BlockNetwork getBlockNetwork(){
    return this.network;
  }

  @Override
  public final void createBlockNetwork(){ // TODO, PRIORITY: change the way networks operate, must create them when adding a new TileEntity, but first check for nearby networks and join them. Remove code in the neighbor_changed function. On block break: run the update network again, for THIS network, then check if any adjacent blocks are NOT part of the network, then they become new networks!
    if(world == null){
      throw new NullPointerException("World not loaded yet when creating LaserNetwork in TileLaserHousing.");
    }
    network = new LaserNetwork(world, this);
    if(world.isRemote == false){
      network.updateNetwork(pos);
      network.running = running;
      network.auto_shutoff = auto_shutoff;
    }
  }

  /**
   * This is overridden so whatever code calls this function is actually updates the whole network.
   */
  @Override
  public final void update_data(){
    network.updateLaserNetwork();
  }

  @Override
  public final void toggleRun(){
    network.running = !network.running;
    network.updateLaserNetwork();
  }

  public final void toggle_auto_shutoff(){
    network.auto_shutoff = !network.auto_shutoff;
    network.updateLaserNetwork();
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerLaserHousing(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
