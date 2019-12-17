package addsynth.overpoweredmod.tiles.machines.fusion;

import javax.annotation.Nullable;
import addsynth.core.inventory.SlotData;
import addsynth.core.tiles.TileMachine;
import addsynth.overpoweredmod.containers.ContainerFusionChamber;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.ModItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileFusionChamber extends TileMachine implements INamedContainerProvider {

  public static final Item[] input_filter = new Item[]{ModItems.fusion_core};

  public static final byte container_radius = 5;
  private boolean on;

  public TileFusionChamber(){
    super(Tiles.FUSION_CHAMBER, new SlotData[]{new SlotData(input_filter,1)}, 0);
  }

  public final boolean has_fusion_core(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty()){
      return false;
    }
    return stack.getCount() > 0;
  }

  public final boolean is_on(){
    return on;
  }

  public final void keep_updated(final boolean valid){
    if(valid){
      if(on == false){
        turn_on();
      }
    }
    else{
      if(on){
        turn_off();
      }
    }
  }

  public final void turn_on(){
    int i;
    BlockPos position;
    for(Direction side : Direction.values()){
      for(i = 1; i < container_radius - 1; i++){
        position = pos.offset(side, i);
        world.setBlockState(position, Machines.fusion_control_laser_beam.getDefaultState(), 3);
      }
    }
    on = true;
  }

  public final void turn_off(){
    int i;
    BlockPos position;
    for(Direction side : Direction.values()){
      for(i = 1; i < container_radius - 1; i++){
        position = pos.offset(side, i);
        world.removeBlock(position, false);
      }
    }
    on = false;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerFusionChamber(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
