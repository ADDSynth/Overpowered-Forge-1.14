package addsynth.overpoweredmod.tiles.machines.portal;

import java.util.ArrayList;
import addsynth.core.util.MinecraftUtility;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyReceiver;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.Portal;
import addsynth.overpoweredmod.game.core.Wires;
import addsynth.overpoweredmod.network.NetworkHandler;
import addsynth.overpoweredmod.network.client_messages.SyncPortalDataMessage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class TilePortalControlPanel extends TileEnergyReceiver {

  private static final int containers = 8;
  public boolean[] portal_items = new boolean[containers];
  public boolean valid_portal = false;
  public String message = "";
  public ArrayList<BlockPos> portal_frames = new ArrayList<>(containers);
  private Direction.Axis axis;
  private BlockPos lowest_portal_frame;

  public TilePortalControlPanel() {
    super(0, null, 0, new CustomEnergyStorage(Values.portal_control_panel_required_energy));
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
      if(portal_items[0] && portal_items[1] && portal_items[2] && portal_items[3] && portal_items[4] && portal_items[5] && portal_items[6] && portal_items[7]){
        if(check_portal_construction()){
          if(energy.isFull()){
            valid_portal = true;
            message = "Portal is Ready.";
          }
          else{
            message = "Needs Energy.";
          }
        }
        else{
          message = "Portal is not constructed correctly.";
        }
      }
      else{
        message = "Incorrect items in Portal Frames.";
      }
    }
    else{
      message = "Portal requires 8 Portal Frame Blocks.";
    }
    // update_data();
    NetworkHandler.INSTANCE.sendToDimension(new SyncPortalDataMessage(pos, portal_items, message, valid_portal), world.provider.getDimension());
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
              final Item item = portal_frame.get_item();
              // currently this is more efficient.
              if(item == Gems.index[0].block_item){ has_gem_block[0] = true; }
              if(item == Gems.index[1].block_item){ has_gem_block[1] = true; }
              if(item == Gems.index[2].block_item){ has_gem_block[2] = true; }
              if(item == Gems.index[3].block_item){ has_gem_block[3] = true; }
              if(item == Gems.index[4].block_item){ has_gem_block[4] = true; }
              if(item == Gems.index[5].block_item){ has_gem_block[5] = true; }
              if(item == Gems.index[6].block_item){ has_gem_block[6] = true; }
              if(item == Gems.index[7].block_item){ has_gem_block[7] = true; }
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
    
      TilePortalFrame tile;
      for(BlockPos position : portal_frames){
        tile = MinecraftUtility.getTileEntity(position, world, TilePortalFrame.class);
        if(tile != null){
          tile.getInputInventory().setStackInSlot(0, ItemStack.EMPTY);
        }
      }
      
      energy.setEmpty();
      
      int start_x, start_y, start_z;
      int x;
      int y;
      int z;
      switch(axis){
      case X:
        start_x = lowest_portal_frame.getX()+1;
        start_y = lowest_portal_frame.getY()+1;
        start_z = lowest_portal_frame.getZ();
        z = start_z;
        for(y = start_y; y < start_y + 3; y++){
          for(x = start_x; x < start_x + 3; x++){
            world.setBlockState(new BlockPos(x,y,z),Portal.portal.getDefaultState()); // .withProperty(PortalEnergyBlock.AXIS, EnumFacing.Axis.X));
          }
        }
        break;
      case Z:
        start_x = lowest_portal_frame.getX();
        start_y = lowest_portal_frame.getY()+1;
        start_z = lowest_portal_frame.getZ()+1;
        x = start_x;
        for(y = start_y; y < start_y + 3; y++){
          for(z = start_z; z < start_z + 3; z++){
            world.setBlockState(new BlockPos(x,y,z),Portal.portal.getDefaultState()); // .withProperty(PortalEnergyBlock.AXIS, EnumFacing.Axis.Z));
          }
        }
        break;
      }

      valid_portal = false;
      
      NetworkHandler.INSTANCE.sendToDimension(new SyncPortalDataMessage(pos, portal_items, message, valid_portal),world.provider.getDimension());
      
    }
  }

}
