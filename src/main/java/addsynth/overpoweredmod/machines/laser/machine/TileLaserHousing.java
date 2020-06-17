package addsynth.overpoweredmod.machines.laser.machine;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.Energy;
import addsynth.energy.tiles.machines.MachineData;
import addsynth.energy.tiles.machines.MachineType;
import addsynth.energy.tiles.machines.TileWorkMachine;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileLaserHousing extends TileWorkMachine implements ITickableTileEntity, IBlockNetworkUser<LaserNetwork>, INamedContainerProvider {

  private LaserNetwork network;
  private boolean first_tick = true;

  private int laser_distance = Config.default_laser_distance.get();
  /** Set by {@link LaserNetwork#updateLaserNetwork()} method and used by
   *  {@link addsynth.overpoweredmod.machines.laser.machine.GuiLaserHousing}.
   */
  public int number_of_lasers;
  private boolean auto_shutoff = true;

  public TileLaserHousing(){
    super(Tiles.LASER_MACHINE, 0, null, 0, new MachineData(MachineType.MANUAL_ACTIVATION, 0, 0, 0, 0));
  }

  @Override
  public final void onLoad(){
  }

  @Override
  public final void tick(){
    if(world.isRemote == false){
      if(first_tick){
        BlockNetworkUtil.create_or_join(world, this, LaserNetwork::new);
        first_tick = false;
      }
      network.tick(this);
    }
  }

  @Override
  public void remove(){
    super.remove();
    BlockNetworkUtil.tileentity_was_removed(this, LaserNetwork::new);
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    laser_distance = nbt.getInt("Laser Distance");
    auto_shutoff = nbt.getBoolean("Auto Shutoff");
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putInt("Laser Distance", laser_distance);
    nbt.putBoolean("Auto Shutoff", auto_shutoff);
    return nbt;
  }

  @Override
  @SuppressWarnings("null")
  public final Energy getEnergy(){
    if(world.isRemote){
      return energy; // only guis should use this.
    }
    if(network == null){
      BlockNetworkUtil.createBlockNetwork(world, this, LaserNetwork::new);
    }
    return getBlockNetwork().energy;
  }

  @Override
  public final double getNeededEnergy(){
    return power_switch ? energy.getRequestedEnergy() : 0;
  }

  @Override
  public final void receiveEnergy(double add_energy){
    if(network == null){
      BlockNetworkUtil.createBlockNetwork(world, this, LaserNetwork::new);
    }
    network.energy.receiveEnergy(add_energy);
  }

  // Only the gui calls these
  public final int getLaserDistance(){ return laser_distance; }
  public final boolean getAutoShutoff(){ return auto_shutoff; }

  public final void setDataDirectlyFromNetwork(final Energy energy, final int laser_distance, final boolean running, final boolean shutoff){
    this.energy.set(energy);
    this.laser_distance = laser_distance;
    this.power_switch = running;
    this.auto_shutoff = shutoff;
    super.update_data(); // explicitly calls super method, because THIS class overrides it!
  }

  @Override
  public void onEnergyChanged(){ // do nothing, because we already track changes whenever the network's Energy changes!
  }

  @Override
  public final void setBlockNetwork(final LaserNetwork network){
    this.network = network;
  }

  @Override
  @Nullable
  public final LaserNetwork getBlockNetwork(){
    return this.network;
  }

  @Override
  public final void load_block_network_data(){
    network.auto_shutoff = auto_shutoff;
    network.setLaserDistance(laser_distance);
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

  @Override
  protected final boolean test_condition(){
    return true;
  }

}
