package addsynth.overpoweredmod.machines.portal.frame;

import javax.annotation.Nullable;
import addsynth.core.inventory.SlotData;
import addsynth.core.material.MaterialsUtil;
import addsynth.core.tiles.TileMachine;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TilePortalFrame extends TileMachine implements INamedContainerProvider {

  public static final Item[] getFilter(){
    return MaterialsUtil.getFilter(
      MaterialsUtil.getRubyBlocks(),     MaterialsUtil.getTopazBlocks(),
      MaterialsUtil.getCitrineBlocks(),  MaterialsUtil.getEmeraldBlocks(),
      MaterialsUtil.getDiamondBlocks(),  MaterialsUtil.getSapphireBlocks(),
      MaterialsUtil.getAmethystBlocks(), MaterialsUtil.getQuartzBlocks()
    );
  }

  public TilePortalFrame(){
    super(Tiles.PORTAL_FRAME, new SlotData[]{new SlotData(getFilter(), 1)}, 0);
  }

  public final Item get_item(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    return stack.isEmpty() ? null : stack.getItem();
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerPortalFrame(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
