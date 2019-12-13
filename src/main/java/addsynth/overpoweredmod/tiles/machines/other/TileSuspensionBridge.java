package addsynth.overpoweredmod.tiles.machines.other;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyReceiver;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.tiles.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileSuspensionBridge extends TileEnergyReceiver implements IBlockNetworkUser, INamedContainerProvider {

  public TileSuspensionBridge(){
    super(Tiles.SUSPENSION_BRIDGE, 1, Lens.index, 0, new CustomEnergyStorage());
  }

  @Override
  public final void setBlockNetwork(BlockNetwork network){
  }

  @Override
  public final BlockNetwork getBlockNetwork(){
    return null;
  }

  @Override
  public final BlockNetwork getNetwork(){
    return null;
  }

  @Override
  public final void createBlockNetwork(){
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return null;
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
