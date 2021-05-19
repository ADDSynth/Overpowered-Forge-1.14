package addsynth.overpoweredmod.machines.laser.machine;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.tiles.TileBase;
import addsynth.core.util.game.PlayerUtil;
import addsynth.energy.api.main.Energy;
import addsynth.energy.api.main.IEnergyConsumer;
import addsynth.energy.api.main.Receiver;
import addsynth.energy.api.tiles.machines.IAutoShutoff;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileLaserHousing extends TileBase implements IBlockNetworkUser<LaserNetwork>,
  ITickableTileEntity, IEnergyConsumer, IAutoShutoff, INamedContainerProvider {

  private final Receiver energy = new Receiver(0, MachineValues.laser_max_receive.get());
  private boolean power_switch = true;

  private LaserNetwork network;
  private ServerPlayerEntity player;
  private int laser_distance = Config.default_laser_distance.get();

  /** Set by {@link LaserNetwork#updateLaserNetwork()} method and used by
   *  {@link addsynth.overpoweredmod.machines.laser.machine.GuiLaserHousing}.
   */
  public int number_of_lasers;
  private boolean auto_shutoff = true;

  public TileLaserHousing(){
    super(Tiles.LASER_MACHINE);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      try{
        if(network == null){
          BlockNetworkUtil.create_or_join(world, this, LaserNetwork::new);
        }
        network.tick(this);
      }
      catch(Exception e){
        report_ticking_error(e);
      }
    }
  }

  @Override
  public final void remove(){
    super.remove();
    BlockNetworkUtil.tileentity_was_removed(this, LaserNetwork::new);
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    energy.loadFromNBT(nbt);
    power_switch = nbt.getBoolean("Power Switch");
    laser_distance = nbt.getInt("Laser Distance");
    auto_shutoff = nbt.getBoolean("Auto Shutoff");
    player = PlayerUtil.getPlayer(world, nbt.getString("Player"));
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    energy.saveToNBT(nbt); // save everything just in case we need to save more than just Energy, and maintain backward compatability.
    nbt.putBoolean("Power Switch", power_switch);
    nbt.putInt("Laser Distance", laser_distance);
    nbt.putBoolean("Auto Shutoff", auto_shutoff);
    if(player != null){
      nbt.putString("Player", player.getGameProfile().getName());
    }
    return nbt;
  }

  @Override
  @SuppressWarnings("null")
  public final Energy getEnergy(){
    // OPTIMIZE: Standardize getting energy from multi-block structures.
    if(world.isRemote){
      return energy; // only guis should use this.
    }
    if(network == null){
      BlockNetworkUtil.createBlockNetwork(world, this, LaserNetwork::new);
    }
    return network.energy;
  }

  // Only the gui calls these
  public final int getLaserDistance(){     return laser_distance; }
  @Override
  public final boolean getAutoShutoff(){   return auto_shutoff; }
  @Override
  public final boolean get_switch_state(){ return power_switch; }

  public final void setDataDirectlyFromNetwork(final Energy energy, final int laser_distance, final boolean running, final boolean shutoff){
    this.energy.set(energy);
    this.laser_distance = laser_distance;
    this.power_switch = running;
    this.auto_shutoff = shutoff;
    super.update_data();
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
    network.load_data(energy, power_switch, laser_distance, auto_shutoff);
  }

  /**
   * This is overridden so whatever code calls this function actually updates the whole network.
   */
  @Override
  public final void update_data(){
  }

  public final void setPlayer(final ServerPlayerEntity player){
    this.player = player;
    super.update_data();
  }

  public final ServerPlayerEntity getPlayer(){
    return player;
  }

  @Override
  public final void togglePowerSwitch(){
    network.running = !network.running;
    network.changed = true;
  }

  @Override
  public final void toggle_auto_shutoff(){
    network.auto_shutoff = !network.auto_shutoff;
    network.changed = true;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    if(player instanceof ServerPlayerEntity){
      this.player = (ServerPlayerEntity)player;
    }
    return new ContainerLaserHousing(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
