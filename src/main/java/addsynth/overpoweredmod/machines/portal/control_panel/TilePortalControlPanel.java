package addsynth.overpoweredmod.machines.portal.control_panel;

import java.util.ArrayList;
import javax.annotation.Nullable;
import addsynth.core.util.MinecraftUtility;
import addsynth.core.util.NetworkUtil;
import addsynth.core.util.WorldUtil;
import addsynth.energy.tiles.machines.TileWorkMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.ModItems;
import addsynth.overpoweredmod.game.core.Portal;
import addsynth.overpoweredmod.game.core.Wires;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public final class TilePortalControlPanel extends TileWorkMachine implements INamedContainerProvider {

  private static final int containers = 8;
  private boolean[] portal_items = new boolean[containers];
  private boolean valid_portal = false;
  private PortalMessage message;
  private ArrayList<BlockPos> portal_frames = new ArrayList<>(containers);
  private Direction.Axis axis;
  private BlockPos lowest_portal_frame;

  // Gui methods:
  public final String getMessage(){ return message == null ? "" : message.getMessage(); }
  public final boolean getPortalItem(final int index){  return portal_items[index]; }
  public final boolean isValid(){ return valid_portal; }

  public final void setData(final boolean[] items, final PortalMessage message, final boolean valid_portal){
    this.portal_items = items;
    this.message = message;
    this.valid_portal = valid_portal;
  }

  public TilePortalControlPanel(){
    super(Tiles.PORTAL_CONTROL_PANEL, 0, null, 0, MachineValues.portal);
    int i;
    for(i = 0; i < containers; i++){
      portal_items[i] = false;
    }
  }

  /**
   *  Call this function when the gui opens to run the portal check algorithm, which will check proper construction
   *  and check if the portal frames have the proper items in them.
   */
  public final void check_portal(){
    portal_frames.clear();
    valid_portal = false;
    portal_items[0] = false;
    portal_items[1] = false;
    portal_items[2] = false;
    portal_items[3] = false;
    portal_items[4] = false;
    portal_items[5] = false;
    portal_items[6] = false;
    portal_items[7] = false;
    axis = null;
    
    portal_search_algorithm(this.pos, this.portal_items, new ArrayList<BlockPos>(30), this.world, this.portal_frames);

    if(portal_frames.size() >= containers){
      if(check_portal_construction()){
        if(portal_items[0] && portal_items[1] && portal_items[2] && portal_items[3] && portal_items[4] && portal_items[5] && portal_items[6] && portal_items[7]){
          if(energy.isFull()){
            // TODO: add a new check to ensure the space inside the portal frame is clear before you generate the portal, for all versions.
            valid_portal = true;
            message = PortalMessage.PORTAL_READY;
          }
          else{
            message = PortalMessage.NEEDS_ENERGY;
          }
        }
        else{
          message = PortalMessage.INCORRECT_ITEMS;
        }
      }
      else{
        message = PortalMessage.PORTAL_NOT_CONSTRUCTED;
      }
    }
    else{
      message = PortalMessage.REQUIRE_PORTAL_FRAMES;
    }
    // update_data();
    final SyncPortalDataMessage message = new SyncPortalDataMessage(pos, portal_items, this.message, valid_portal);
    NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, message);
  }

  private static final void portal_search_algorithm(BlockPos from, boolean[] has_gem_block, ArrayList<BlockPos> searched, World world, ArrayList<BlockPos> portal_frames){
    BlockPos position;
    Block block;
    for(Direction side : Direction.values()){
      position = from.offset(side);
      if(searched.contains(position) == false){
        searched.add(position);
        block = world.getBlockState(position).getBlock();
        if(block == Wires.data_cable || block == Machines.portal_frame || block == Init.iron_frame_block){
          if(block == Machines.portal_frame){
            final TilePortalFrame portal_frame = (TilePortalFrame)world.getTileEntity(position); // we already know this is a portal frame block, its okay to do this.
            if(portal_frame != null){
              portal_frames.add(position);
              final int item = portal_frame.check_item();
              if(item >= 0){
                has_gem_block[item] = true;
              }
            }
          }
          if(portal_frames.size() < containers){
            portal_search_algorithm(position, has_gem_block, searched, world, portal_frames);
          }
        }
      }
    }
  }

  private final boolean check_portal_construction(){
    boolean pass = false;
    int i;
    boolean portal_frames_valid = true;
    for(i = 0; i < containers; i++){
      if(portal_frames.get(i) == null){
        portal_frames_valid = false;
        break;
      }
    }
    
    if(portal_frames_valid){
      // get lowest portal frame
      lowest_portal_frame = portal_frames.get(0);
      BlockPos position;
      for(i = 1; i < containers; i++){
        position = portal_frames.get(i);
        if(position.getX() < lowest_portal_frame.getX() || position.getY() < lowest_portal_frame.getY() || position.getZ() < lowest_portal_frame.getZ()){
          lowest_portal_frame = position;
        }
      }
      // the portal could be constructed either along the X axis or Z axis.
      final int x = lowest_portal_frame.getX();
      final int y = lowest_portal_frame.getY();
      final int z = lowest_portal_frame.getZ();
      if(check_portal_frame(x,  y,  z,world) && check_portal_frame(x+2,y,  z,world) &&
         check_portal_frame(x+4,y,  z,world) && check_portal_frame(x,  y+2,z,world) &&
         check_portal_frame(x+4,y+2,z,world) && check_portal_frame(x  ,y+4,z,world) &&
         check_portal_frame(x+2,y+4,z,world) && check_portal_frame(x+4,y+4,z,world)){
           pass = true;
           axis = Direction.Axis.X;
      }
      if(check_portal_frame(x,y,  z,  world) && check_portal_frame(x,y,  z+2,world) &&
         check_portal_frame(x,y,  z+4,world) && check_portal_frame(x,y+2,z,  world) &&
         check_portal_frame(x,y+2,z+4,world) && check_portal_frame(x,y+4,z,  world) &&
         check_portal_frame(x,y+4,z+2,world) && check_portal_frame(x,y+4,z+4,world)){
           pass = true;
           axis = Direction.Axis.Z;
      }
    }
    
    return pass;
  }

  private static final boolean check_portal_frame(int x, int y, int z, World world){
    return world.getBlockState(new BlockPos(x,y,z)).getBlock() == Machines.portal_frame;
  }

  @SuppressWarnings("incomplete-switch")
  public final void generate_portal(){
    if(valid_portal){
    
      for(BlockPos position : portal_frames){
        final TilePortalFrame tile = MinecraftUtility.getTileEntity(position, world, TilePortalFrame.class);
        if(tile != null){
          tile.getInputInventory().setStackInSlot(0, ItemStack.EMPTY);
        }
      }
      
      energy.setEmpty();
      
      int start_x, start_y, start_z;
      int x;
      int y;
      int z;
      int center;
      switch(axis){
      case X:
        start_x = lowest_portal_frame.getX()+1;
        start_y = lowest_portal_frame.getY()+1;
        start_z = lowest_portal_frame.getZ();
        center = lowest_portal_frame.getX()+2;
        z = start_z;
        for(y = start_y; y < start_y + 3; y++){
          for(x = start_x; x < start_x + 3; x++){
            world.setBlockState(new BlockPos(x,y,z),Portal.portal.getDefaultState()); // .withProperty(PortalEnergyBlock.AXIS, EnumFacing.Axis.X));
          }
        }
        // TODO: make this a config option in disable_feature.cfg in Overpowered version 1.3, to allow the Portal Construction, but disable the Unknown Dimension.
        WorldUtil.spawnItemStack(world, center, start_y, start_z, new ItemStack(ModItems.unknown_technology, 2), false);
        break;
      case Z:
        start_x = lowest_portal_frame.getX();
        start_y = lowest_portal_frame.getY()+1;
        start_z = lowest_portal_frame.getZ()+1;
        center = lowest_portal_frame.getZ()+2;
        x = start_x;
        for(y = start_y; y < start_y + 3; y++){
          for(z = start_z; z < start_z + 3; z++){
            world.setBlockState(new BlockPos(x,y,z),Portal.portal.getDefaultState()); // .withProperty(PortalEnergyBlock.AXIS, EnumFacing.Axis.Z));
          }
        }
        WorldUtil.spawnItemStack(world, start_x, start_y, center, new ItemStack(ModItems.unknown_technology, 2), false);
        break;
      }

      portal_items[0] = false;
      portal_items[1] = false;
      portal_items[2] = false;
      portal_items[3] = false;
      portal_items[4] = false;
      portal_items[5] = false;
      portal_items[6] = false;
      portal_items[7] = false;
      message = PortalMessage.INCORRECT_ITEMS;
      valid_portal = false;
      
      final SyncPortalDataMessage message = new SyncPortalDataMessage(pos, portal_items, this.message, valid_portal);
      NetworkUtil.send_to_clients_in_world(NetworkHandler.INSTANCE, world, message);
      
    }
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerPortalControlPanel(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

  @Override
  protected void test_condition(){
  }

}
