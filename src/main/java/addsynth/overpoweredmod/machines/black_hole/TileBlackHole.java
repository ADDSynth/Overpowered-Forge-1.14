package addsynth.overpoweredmod.machines.black_hole;

import addsynth.core.Constants;
import addsynth.core.util.MathUtility;
import addsynth.core.util.ServerUtils;
import addsynth.core.util.TimeUtil;
import addsynth.core.util.WorldUtil;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public final class TileBlackHole extends TileEntity implements ITickableTileEntity {

  private boolean first_tick = true;
  private boolean erase_world;
  private boolean reached_the_end;

  private long begin_tick_time;

  /** Max time the black hole algorithm is alloted per tick. Can be changed in the config. */
  private final long max_time = (long)Math.round(Constants.tick_time_in_nanoseconds * Config.black_hole_max_tick_time.get());

  public static final int MIN_RADIUS = 2;
  public static final int MAX_RADIUS = 500;

  private int radius;
  private AxisAlignedBB entity_area;

  private int x;
  private int y;
  private int z;
  private int min_x;
  private int min_z;
  private int max_x;
  private int max_y;
  private int max_z;

  private MinecraftServer server;

  public TileBlackHole(){
    super(Tiles.BLACK_HOLE);
  }

  @Override
  public final CompoundNBT write(final CompoundNBT compound){
    return super.write(compound);
  }

  @Override
  public final void read(final CompoundNBT compound){
    super.read(compound);
  }

  @Override
  public void tick(){
    if(world.isRemote == false){
      begin_tick_time = TimeUtil.get_start_time();
      if(first_tick){
        first_tick();
        if(server == null){
          OverpoweredMod.log.error("Black Hole at coordinates "+pos+" could not get a reference to the Minecraft Server! And thus, cannot check server tick processing time!");
          remove();
          erase_world = false;
        }
      }
      if(erase_world){
        delete_entities();
        delete_blocks();
        if(reached_the_end){
          world.destroyBlock(pos, false);
        }
      }
    }
  }

  private final void first_tick(){
    if(is_black_hole_allowed(world.getDimension().getType().getId())){
      erase_world = true;
      radius = get_black_hole_radius(world);
      min_x = pos.getX() - radius;
      min_z = pos.getZ() - radius;
      x = min_x;
      y = pos.getY() + radius;
      z = min_z;
      max_x = pos.getX() + radius;
      max_y = pos.getY() - radius;
      max_z = pos.getZ() + radius;
      final double center_x = pos.getX() + 0.5;
      final double center_y = pos.getY() + 0.5;
      final double center_z = pos.getZ() + 0.5;
      entity_area = new AxisAlignedBB(center_x, center_y, center_z, center_x, center_y, center_z);
      entity_area.grow(radius);
      // MAYBE: play sound?
      if(Config.alert_players_of_black_hole.get()){
        ServerUtils.send_message_to_all_players_in_world(new StringTextComponent(
          TextFormatting.DARK_PURPLE + "Singularity Event Detected at Coordinates: "+pos.getX()+" , "+pos.getY()+" , "+pos.getZ()
        ), world);
      }
      server = ServerUtils.getServer();
    }
    first_tick = false;
  }

  private static final boolean is_black_hole_allowed(final int dimension_id){
    boolean pass = true;
    for(int id_check : Config.black_hole_dimension_blacklist.get()){
      if(dimension_id == id_check){
        pass = false;
        break;
      }
    }
    return pass;
  }

  private static final int get_black_hole_radius(final World world){
    int radius = Config.black_hole_radius.get();
    if(Config.black_hole_radius_depends_on_world_difficulty.get()){
      final Difficulty difficulty = world.getDifficulty();
      final int[] difficulty_radius = new int[] {
        Config.BLACK_HOLE_PEACEFUL_DIFFICULTY_RADIUS, Config.BLACK_HOLE_EASY_DIFFICULTY_RADIUS,
        Config.BLACK_HOLE_NORMAL_DIFFICULTY_RADIUS,   Config.BLACK_HOLE_HARD_DIFFICULTY_RADIUS};
      if(Config.randomize_black_hole_radius.get()){
        final int deviation = 20;
        final int min_value = difficulty_radius[difficulty.ordinal()] - deviation;
        final int max_value = difficulty_radius[difficulty.ordinal()] + deviation;
        radius = MathHelper.clamp(MathUtility.RandomRange(min_value, max_value), MIN_RADIUS, MAX_RADIUS);
      }
      else{
        radius = difficulty_radius[difficulty.getId()];
      }
    }
    else{
      if(Config.randomize_black_hole_radius.get()){
        radius = MathUtility.RandomRange(Config.minimum_black_hole_radius.get(), Config.maximum_black_hole_radius.get());
      }
    }
    return radius;
  }

  private final void delete_entities(){ // FIX: This is not deleting entities for some reason!
    for(Entity entity : world.getEntitiesWithinAABB(Entity.class, entity_area, null)){
      if(entity instanceof PlayerEntity){
        final PlayerEntity player = (PlayerEntity)entity;
        if(player.isCreative() == false && player.isSpectator() == false){
          player.setHealth(0.0f);
          continue;
        }
      }
      if(entity instanceof LivingEntity){
        // entity.attackEntityFrom(source, amount);
        // entity.setDead(); // possibly use .remove()
        entity.remove();
        continue;
      }
      entity.remove();
    }
  }

  private final void delete_blocks(){
    BlockPos position;
    boolean check_1;
    boolean check_2;
    do{
      // delete current position
      position = new BlockPos(x,y,z);
      if((x == pos.getX() && y == pos.getY() && z == pos.getZ()) == false){
        if(world.getBlockState(position).getBlock() != Blocks.AIR){
          if(MathUtility.is_inside_sphere(pos, radius, position)){
            if(Config.black_holes_erase_bedrock.get()){
              WorldUtil.delete_block(world, position);
            }
            else{
              if(world.getBlockState(position).getBlock() != Blocks.BEDROCK){
                WorldUtil.delete_block(world, position);
              }
            }
          }
        }
      }
      // increment position
      x += 1;
      if(x > max_x){
        x = min_x;
        z += 1;
        if(z > max_z){
          z = min_z;
          y -= 1;
          if(y < max_y){
            reached_the_end = true;
          }
        }
      }
      // record time
      check_1 = TimeUtil.time_exceeded(begin_tick_time, max_time);
      check_2 = TimeUtil.exceeded_server_tick_time(server, begin_tick_time);
    }
    while((check_1 || check_2) == false);
  }

}
