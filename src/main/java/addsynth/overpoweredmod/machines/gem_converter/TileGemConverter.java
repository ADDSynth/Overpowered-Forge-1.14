package addsynth.overpoweredmod.machines.gem_converter;

import javax.annotation.Nullable;
import addsynth.core.material.MaterialsUtil;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.PassiveMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileGemConverter extends PassiveMachine implements INamedContainerProvider {
	
  private byte selection;
  private ItemStack gem_selected;
  
  public static final Item[] getFilter(){
    return MaterialsUtil.getFilter(
      MaterialsUtil.getRubies(), MaterialsUtil.getTopaz(), MaterialsUtil.getCitrine(), MaterialsUtil.getEmeralds(),
      MaterialsUtil.getDiamonds(), MaterialsUtil.getSapphires(), MaterialsUtil.getAmethysts(), MaterialsUtil.getQuartz()
    );
  }
  
  public TileGemConverter(){
    super(Tiles.GEM_CONVERTER,1,getFilter(),1,new CustomEnergyStorage(Values.gem_converter_required_energy.get()),Values.gem_converter_work_time.get());
  }

  public final void cycle(final boolean direction){
    if(world.isRemote == false){
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
      gem_selected = new ItemStack(Gems.index[selection].gem,1);
      update_data(); // TEST: this may call readFromNBT and set the gem_item stack anyway.
    }
  }

  @Override
  public final CompoundNBT write(final CompoundNBT nbt){
    super.write(nbt);
    nbt.putByte("Gem Selected", selection);
    return nbt;
  }

  @Override
  public final void read(final CompoundNBT nbt){
    super.read(nbt);
    selection = nbt.getByte("Gem Selected");
    gem_selected = new ItemStack(Gems.index[selection].gem,1);
  }

  @Override
  protected final void test_condition(){
    can_run = input_inventory.getStackInSlot(0).isEmpty() ? false : output_inventory.can_add(0, gem_selected);
  }

  /** This override for the Gem Converter adds an optimization that quickly transfers over
   *  the gem to the output slot if it is the same gem type you're trying to convert to.
   */
  @Override
  protected final void machine_tick(){
    boolean transfer = false;
    final ItemStack input_stack = input_inventory.getStackInSlot(0);
    if(input_stack.isEmpty() == false){
      transfer = match(input_stack.getItem(), selection);
    }
    if(transfer){
      final ItemStack insert = input_inventory.extractItem(0, 1, false);
      output_inventory.insertItem(0, insert, false);
      update_data();
    }
    else{
      super.machine_tick();
    }
  }

  private static final boolean match(final Item item, final byte id){
    if(id == 0){ return MaterialsUtil.match(item, MaterialsUtil.getRubies()); }
    if(id == 1){ return MaterialsUtil.match(item, MaterialsUtil.getTopaz()); }
    if(id == 2){ return MaterialsUtil.match(item, MaterialsUtil.getCitrine()); }
    if(id == 3){ return MaterialsUtil.match(item, MaterialsUtil.getEmeralds()); }
    if(id == 4){ return MaterialsUtil.match(item, MaterialsUtil.getDiamonds()); }
    if(id == 5){ return MaterialsUtil.match(item, MaterialsUtil.getSapphires()); }
    if(id == 6){ return MaterialsUtil.match(item, MaterialsUtil.getAmethysts()); }
    if(id == 7){ return MaterialsUtil.match(item, MaterialsUtil.getQuartz()); }
    return false;
  }

  @Override
  protected final void performWork(){
    input_inventory.decrease(0);
    // always remember to pass A COPY of the stack your trying to insert! Do not reference
    // a stack you're keeping. Otherwise it will assign a direct reference!
    output_inventory.insertItem(0, gem_selected.copy(), false);
  }

  public final ItemStack get_gem_selected(){
    return gem_selected;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerGemConverter(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}