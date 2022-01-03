package addsynth.overpoweredmod.machines.identifier;

import javax.annotation.Nullable;
import addsynth.core.game.Compatability;
import addsynth.core.items.ItemUtil;
import addsynth.core.util.data.AdvancementUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.core.util.player.PlayerUtil;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.assets.CustomStats;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Tools;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileIdentifier extends TileStandardWorkMachine implements INamedContainerProvider {

  private ServerPlayerEntity player;

  public static final Item[] input_filter = ArrayUtil.combine_arrays(
    Tools.unidentified_armor[0],
    Tools.unidentified_armor[1],
    Tools.unidentified_armor[2],
    Tools.unidentified_armor[3],
    Tools.unidentified_armor[4]
  );

  public TileIdentifier(){
    super(Tiles.IDENTIFIER, 1, input_filter, 1, MachineValues.identifier);
  }

  @Override
  protected final boolean can_work(){
    final ItemStack input = inventory.getInputInventory().getStackInSlot(0);
    final ItemStack output = inventory.getOutputInventory().getStackInSlot(0);
    return input.isEmpty() == false && output.isEmpty();
  }

  @Override
  protected final void perform_work(){
    final ItemStack input = inventory.getWorkingInventory().getStackInSlot(0);
    if(input.isEmpty() == false){ // safety feature? couldn't hurt I guess. But getItem() returns AIR for Empty Itemstacks.
      if(input.getItem() instanceof UnidentifiedItem){
        final UnidentifiedItem item = (UnidentifiedItem)(input.getItem());
        final ItemStack stack = new ItemStack(ItemUtil.get_armor(item.armor_material, item.equipment_type), 1);
        ArmorEffects.enchant(stack);
        inventory.getOutputInventory().setStackInSlot(0, stack);
        if(player != null){
          AdvancementUtil.grantAdvancement(player, CustomAdvancements.IDENTIFY_SOMETHING);
          player.addStat(CustomStats.ITEMS_IDENTIFIED);
        }
      }
      inventory.getWorkingInventory().setEmpty();
    }
  }

  @Override
  public void read(final CompoundNBT nbt){
    super.read(nbt);
    player = PlayerUtil.getPlayer(world, nbt.getString("Player"));
  }

  @Override
  public CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    if(player != null){
      nbt.putString("Player", player.getGameProfile().getName());
    }
    return nbt;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    if(player instanceof ServerPlayerEntity){
      this.player = (ServerPlayerEntity)player;
      changed = true;
    }
    return new ContainerIdentifier(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
