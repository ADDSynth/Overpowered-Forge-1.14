package addsynth.overpoweredmod.machines.suspension_bridge;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.inventory.SlotData;
import addsynth.energy.Energy;
import addsynth.energy.tiles.TileEnergyReceiver;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileSuspensionBridge extends TileEnergyReceiver implements IBlockNetworkUser<BridgeNetwork>, ITickableTileEntity, INamedContainerProvider {

  public static final Item[] filter = Lens.index;

  private boolean first_tick = true;

  private BridgeNetwork network;

  private BridgeMessage[] message = new BridgeMessage[6];

  public TileSuspensionBridge(){
    super(Tiles.ENERGY_SUSPENSION_BRIDGE, new SlotData[] {new SlotData(filter, 1)}, 0, new Energy());
  }

  @Override
  public final void tick(){
    if(world.isRemote == false){
      if(first_tick){
        BlockNetworkUtil.create_or_join(world, this, BridgeNetwork::new);
        first_tick = false;
      }
      if(network != null){
        network.tick(this);
      }
    }
    super.tick();
  }

  @Override
  public void remove(){
    super.remove();
    BlockNetworkUtil.tileentity_was_removed(this, BridgeNetwork::new);
  }

  @Override
  public void load_block_network_data(){
    network.lens_index = Lens.get_index(input_inventory.getStackInSlot(0));
  }

  @Override
  public final void onInventoryChanged(){
    if(world.isRemote == false){
      network.update_lens(input_inventory.getStackInSlot(0));
    }
  }

  @Override
  public final void setBlockNetwork(BridgeNetwork network){
    this.network = network;
  }

  @Override
  public final BridgeNetwork getBlockNetwork(){
    return network;
  }

  public final void setMessages(final BridgeMessage[] messages){
    this.message = messages;
  }

  public final String getMessage(final int index){
    if(message        == null){ return "Null Error"; }
    if(message[index] == null){ return "Null Error"; }
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
