package addsynth.overpoweredmod.machines.portal.frame;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.tiles.TileStorageMachine;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TilePortalFrame extends TileStorageMachine implements INamedContainerProvider {

  public TilePortalFrame(){
    super(Tiles.PORTAL_FRAME, new SlotData[]{new SlotData(Filters.portal_frame, 1)});
  }

  public final int check_item(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty()){ return -1; }
    final Item item = stack.getItem();
    if(MaterialsUtil.match(item, MaterialsUtil.getRubyBlocks())){     return 0; }
    if(MaterialsUtil.match(item, MaterialsUtil.getTopazBlocks())){    return 1; }
    if(MaterialsUtil.match(item, MaterialsUtil.getCitrineBlocks())){  return 2; }
    if(MaterialsUtil.match(item, MaterialsUtil.getEmeraldBlocks())){  return 3; }
    if(MaterialsUtil.match(item, MaterialsUtil.getDiamondBlocks())){  return 4; }
    if(MaterialsUtil.match(item, MaterialsUtil.getSapphireBlocks())){ return 5; }
    if(MaterialsUtil.match(item, MaterialsUtil.getAmethystBlocks())){ return 6; }
    if(MaterialsUtil.match(item, MaterialsUtil.getQuartzBlocks())){   return 7; }
    return -1;
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
