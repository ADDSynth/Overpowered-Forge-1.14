package addsynth.overpoweredmod.machines.black_hole;

import addsynth.core.Constants;
import addsynth.core.util.TimeUtil;
import addsynth.core.util.block.BlockMath;
import addsynth.core.util.game.MessageUtil;
import addsynth.core.util.game.ServerUtils;
import addsynth.core.util.game.WorldUtil;
import addsynth.core.util.math.MathUtility;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

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

  private double center_x;
  private double center_y;
  private double center_z;
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
  @SuppressWarnings("null")
  public void tick(){
    if(world.isRemote == false){
      begin_tick_time = TimeUtil.get_start_time();
      if(first_tick){
        first_tick();
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
    if(is_black_hole_allowed(world)){
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
      center_x = pos.getX() + 0.5;
      center_y = pos.getY() + 0.5;
      center_z = pos.getZ() + 0.5;
      entity_area = new AxisAlignedBB(center_x - radius, center_y - radius, center_z - radius,
                                      center_x + radius, center_y + radius, center_z + radius);
      // MAYBE: play sound?
      if(Config.alert_players_of_black_hole.get()){
        MessageUtil.send_to_all_players_in_world(world, "gui.overpowered.black_hole.notify_players", pos.getX(), pos.getY(), pos.getZ());
      }
      server = ServerUtils.getServer(world);
    }
    first_tick = false;
  }

  public static final boolean is_black_hole_allowed(final World world){
    if(world == null){
      OverpoweredMod.log.error(new NullPointerException("World not loaded yet."));
      return false;
    }
    if(world.getWorldType() == WorldType.DEBUG_ALL_BLOCK_STATES){
      // NOTE: If you create a Debug World, by holding Shift as you cycle through the World types, this spawns all
      //       blocks in Minecraft, in all possible block states. Since this also spawns a Black Hole, this starts
      //       the black hole algorithm of erasing the world. However, in a Debug World blocks cannot be destroyed.
      //       So the black hole algorithm fails to destroy any blocks, and when it finishes it will continuously
      //       try to destroy itself, spawning hundreds of particle effects.
      return false;
    }
    final int dimension_id = world.getDimension().getType().getId();
    for(int id_check : Config.black_hole_dimension_blacklist.get()){
      if(dimension_id == id_check){
        return false;
      }
    }
    return true;
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

  @SuppressWarnings("null")
  private final void delete_entities(){
    for(final Entity entity : world.getEntitiesWithinAABB(Entity.class, entity_area, null)){
      if(MathUtility.get_distance(center_x, center_y, center_z, entity.posX, entity.posY, entity.posZ) <= radius){
        if(entity instanceof PlayerEntity){ // server players
          final PlayerEntity player = (PlayerEntity)entity;
          if(player.isCreative() == false && player.isSpectator() == false){
            player.setHealth(0.0f); // DO NOT REMOVE PLAYERS! You must DAMAGE them!
          }
        }
        else{
          entity.remove();
        }
      }
    }
  }

  @SuppressWarnings("null")
  private final void delete_blocks(){
    BlockPos position;
    boolean check_1;
    boolean check_2;
    do{
      // delete current position
      position = new BlockPos(x,y,z);
      if((x == pos.getX() && y == pos.getY() && z == pos.getZ()) == false){
        if(world.getBlockState(position).getBlock() != Blocks.AIR){
          if(BlockMath.is_inside_sphere(pos, radius, position)){
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
