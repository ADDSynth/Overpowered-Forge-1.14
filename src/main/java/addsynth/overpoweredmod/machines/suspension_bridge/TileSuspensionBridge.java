package addsynth.overpoweredmod.machines.suspension_bridge;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.util.constants.Constants;
import addsynth.energy.lib.main.Receiver;
import addsynth.energy.lib.tiles.TileBasicMachine;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileSuspensionBridge extends TileBasicMachine implements IBlockNetworkUser<BridgeNetwork>, INamedContainerProvider {

  public static final Item[] filter = Lens.index;

  private BridgeNetwork network;

  private BridgeMessage bridge_message;
  private BridgeMessage[] message = new BridgeMessage[6];

  public TileSuspensionBridge(){
    super(Tiles.ENERGY_SUSPENSION_BRIDGE, new SlotData[] {new SlotData(filter, 1)}, new Receiver());
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      try{
        if(network == null){
          BlockNetworkUtil.create_or_join(world, this, BridgeNetwork::new);
        }
        network.tick(this);
      }
      catch(Exception e){
        report_ticking_error(e);
      }
    }
  }

  @Override
  public void remove(){
    super.remove();
    BlockNetworkUtil.tileentity_was_removed(this, BridgeNetwork::new);
  }

  @Override
  public void load_block_network_data(){
    network.lens_index = Lens.get_index(inventory.getStackInSlot(0));
  }

  @Override
  public final void onInventoryChanged(){
    if(onServerSide()){
      network.update_lens(inventory.getStackInSlot(0));
    }
  }

  @Override
  public final void setBlockNetwork(BridgeNetwork network){
    this.network = network;
  }

  @Override
  @Nullable
  public final BridgeNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public final Receiver getEnergy(){
    return energy;
  }

  public final void setMessages(final BridgeMessage bridge_message, final BridgeMessage[] messages){
    this.bridge_message = bridge_message;
    this.message = messages;
  }

  public final String getBridgeMessage(){
    if(bridge_message == null){ return Constants.null_error; }
    return bridge_message.getMessage();
  }

  public final String getMessage(final int index){
    if(message        == null){ return Constants.null_error; }
    if(message[index] == null){ return Constants.null_error; }
    return message[index].getMessage();
  }

  @Override
  public final void drop_inventory(){
    if(network.getCount() == 1){
      super.drop_inventory();
    }
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerSuspensionBridge(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
