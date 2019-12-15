package addsynth.core.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

public abstract class BaseContainer<T extends TileEntity> extends Container {

  protected final T tile;

  public BaseContainer(final ContainerType type, final int id, final PlayerInventory player_inventory){
    super(type, id);
    this.tile = null;
  }

  public BaseContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final T tile){
    super(type, id);
    this.tile = tile;
  }

  // New in 1.14, thanks again to this guy: https://github.com/Cadiboo/Example-Mod/blob/1.14.4/src/main/java/io/github/cadiboo/examplemod/container/ElectricFurnaceContainer.java
  @SuppressWarnings("unchecked")
  public BaseContainer(final ContainerType type, final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(type, id);
    this.tile = (T)(player_inventory.player.world.getTileEntity(data.readBlockPos()));
  }

  protected final void make_player_inventory(PlayerInventory player_inventory){
    make_player_inventory(player_inventory,8,84);
  }

  protected final void make_player_inventory(PlayerInventory player_inventory, int x, int y){
    int i;
    int j;
    for (j = 0; j < 3; j++) {
      for (i = 0; i < 9; i++) {
        addSlot(new Slot(player_inventory, i + 9 + (j * 9), x + (i*18), y + (j*18)));
      }
    }
    for (i = 0; i < 9; i++) {
        addSlot(new Slot(player_inventory, i, x + (i*18), y + 58));
    }  
  }

  /**
   * I've overridden the default behaviour of this method. This occurs when the user trys to
   * Shift-click an item in the inventory. Default behaviour is to return the same item that was
   * clicked, so it thinks nothing changed, so it tries again? Why try again? Why did Mojang
   * make this the default behaviour? -_-  Override this method in derived classes to specify
   * your own Shift-click behaviour, using that container's slots.
   */
   // http://www.minecraftforge.net/forum/topic/42322-1102-inventory-gui-shift-clicking/
   // https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/1571051-custom-container-how-to-properly-override-shift
  @Override
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index){
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canInteractWith(final PlayerEntity player){
    return isWithinUsableDistance(IWorldPosCallable.DUMMY, player, tile.getBlockState().getBlock());
  }

  /*
  protected final ItemStack default_transfer(final int index, final int number_of_input_slots){
    FUTURE: Added Shift-click support is just too complicated right now, so I'm moving it to the December 2019 release.

    final int player_inventory_max = 36;
    ItemStack stack = ItemStack.EMPTY;
    final Slot slot = getSlot(index);
    if(slot != null){
      if(slot.getHasStack()){
        ItemStack stack2 = slot.getStack();
        stack = stack2.copy();
        final boolean result;
        if(index < player_inventory_max){
          result = mergeItemStack(stack2, player_inventory_max, player_inventory_max + number_of_input_slots, true);
        }
        else{
          result = mergeItemStack(stack2, 0, player_inventory_max, false);
        }
        if(result == false){ return ItemStack.EMPTY; }
      }
    }
    return stack;
  }
    */

  public final T getTileEntity(){
    return tile;
  }

}
