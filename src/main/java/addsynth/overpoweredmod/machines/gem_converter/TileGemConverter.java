package addsynth.overpoweredmod.machines.gem_converter;

import javax.annotation.Nullable;
import addsynth.core.util.data.AdvancementUtil;
import addsynth.core.util.player.PlayerUtil;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.assets.CustomStats;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileGemConverter extends TileStandardWorkMachine implements INamedContainerProvider {

  private ServerPlayerEntity player;
  private byte selection;
  private ItemStack gem_selected = new ItemStack(Gems.ruby, 1);
  private byte converting_to;
  
  public TileGemConverter(){
    super(Tiles.GEM_CONVERTER, 1, Filters.gem_converter, 1, MachineValues.gem_converter);
  }

  public final void cycle(final boolean direction){
    if(onServerSide()){
      if(direction){
        selection += 1;
        if(selection == 8){
          selection = 0;
        }
      }
      else{
        selection -= 1;
        if(selection < 0){
          selection = 7;
        }
      }
      gem_selected = Gems.getItemStack(selection); // updates on server-side
      changed = true;
    }
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putByte("Gem Selected", selection);
    nbt.putByte("Converting To", converting_to);
    if(player != null){
      nbt.putString("Player", player.getGameProfile().getName());
    }
    return nbt;
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    selection = nbt.getByte("Gem Selected");
    gem_selected = Gems.getItemStack(selection); // updates on client-side and server-side
    converting_to = nbt.getByte("Converting To");
    player = PlayerUtil.getPlayer(world, nbt.getString("Player"));
  }

  @Override
  protected final boolean can_work(){
    if(quick_transfer()){
      return false;
    }
    return inventory.getInputInventory().getStackInSlot(0).isEmpty() ? false : inventory.getOutputInventory().can_add(0, gem_selected);
  }

  /** Checks if the Input gem matches the gem we're converting to, and if that is the case,
   *  then this just quickly transfers 1 input gem to the output slot.<br />
   *  <b>Note:</b> If the input gem DOES match the output gem, but happens to be from another mod,
   *  we'll convert the gem to OUR gem.
   */
  private final boolean quick_transfer(){
    final ItemStack input_stack = inventory.getInputInventory().getStackInSlot(0);
    if(input_stack.isEmpty() == false){
      if(match(input_stack.getItem(), selection) && inventory.getOutputInventory().can_add(0, gem_selected)){
        final ItemStack insert = inventory.getInputInventory().extractItem(0, 1, false);
        inventory.getOutputInventory().insertItem(0, insert, false);
        return true;
      }
    }
    return false;
  }

  /** Returns whether the input ItemStack matches the specified Gem Index. */
  private static final boolean match(final Item item, final int id){
    return Gems.getID(item) == id;
  }

  @Override
  protected final void begin_work(){
    inventory.begin_work();
    converting_to = selection;
  }

  @Override
  protected final void perform_work(){
    inventory.getOutputInventory().insertItem(0, Gems.getItemStack(converting_to), false);
    
    if(player != null){
      final Stat gems_converted_stat = Stats.CUSTOM.get(CustomStats.GEMS_CONVERTED);
      player.addStat(gems_converted_stat);
      if(player.getStats().getValue(gems_converted_stat) >= 1000){
        AdvancementUtil.grantAdvancement(player, CustomAdvancements.CONVERT_A_THOUSAND_GEMS);
      }
    }
  }

  public final int get_gem_selected(){
    return selection;
  }

  public final int getConvertingStack(){
    return converting_to;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    if(player instanceof ServerPlayerEntity){
      this.player = (ServerPlayerEntity)player;
    }
    return new ContainerGemConverter(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
