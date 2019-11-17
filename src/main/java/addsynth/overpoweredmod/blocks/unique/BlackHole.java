package addsynth.overpoweredmod.blocks.unique;

import addsynth.core.util.MathUtility;
import addsynth.core.util.ServerUtils;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public final class BlackHole extends Block {

  public static final byte MIN_RADIUS = 2;
  public static final byte MAX_RADIUS = 100;

  public BlackHole(final String name){
    super(Material.GROUND, MaterialColor.BLACK);
    // setResistance(100.0f);
    OverpoweredMod.registry.register_block(this, name); // we register a custom BlackHoleItem in Init class.
  }

  @Override
  public final void onBlockAdded(final World world, final BlockPos pos, final IBlockState state){
    if(world.isRemote == false){
      erase_the_world(world, pos);
    }
  }

  public static final void erase_the_world(final World world, final BlockPos center){
    if(is_black_hole_allowed(world.provider.getDimension())){

      if(Config.alert_players_of_black_hole){
        ServerUtils.send_message_to_all_players_in_world(new TextComponentString(
          TextFormatting.DARK_PURPLE + "Singularity Event Detected at Coordinates: "+center.getX()+" , "+center.getY()+" , "+center.getZ()
        ), world);
      }
    
      // MAYBE: play sound?

      final int radius = get_black_hole_radius(world);

      final double center_x = center.getX() + 0.5;
      final double center_y = center.getY() + 0.5;
      final double center_z = center.getZ() + 0.5;
      destroyBlocks(world, center, radius);
      destroyEntities(world, center_x, center_y, center_z, radius);
      destroyPlayers(world, center_x, center_y, center_z, radius);
      destroyItems();
    }
  }

  private static final boolean is_black_hole_allowed(final int dimension_id){
    boolean pass = true;
    for(int id_check : Config.black_hole_dimension_blacklist){
      if(dimension_id == id_check){
        pass = false;
        break;
      }
    }
    return pass;
  }

  private static final int get_black_hole_radius(final World world){
    int radius = Config.black_hole_radius;
    if(Config.black_hole_radius_depends_on_world_difficulty){
      final EnumDifficulty difficulty = world.getDifficulty();
      final int[] difficulty_radius = new int[] {
        Config.BLACK_HOLE_PEACEFUL_DIFFICULTY_RADIUS, Config.BLACK_HOLE_EASY_DIFFICULTY_RADIUS,
        Config.BLACK_HOLE_NORMAL_DIFFICULTY_RADIUS,   Config.BLACK_HOLE_HARD_DIFFICULTY_RADIUS};
      if(Config.randomize_black_hole_radius){
        final int deviation = 20;
        final int min_value = difficulty_radius[difficulty.ordinal()] - deviation;
        final int max_value = difficulty_radius[difficulty.ordinal()] + deviation;
        radius = MathHelper.clamp(MathUtility.RandomRange(min_value, max_value), MIN_RADIUS, MAX_RADIUS);
      }
      else{
        radius = difficulty_radius[difficulty.ordinal()];
      }
    }
    else{
      if(Config.randomize_black_hole_radius){
        radius = MathUtility.RandomRange(Config.minimum_black_hole_radius, Config.maximum_black_hole_radius);
      }
    }
    return radius;
  }

  private static final void destroyBlocks(final World world, final BlockPos center, final int radius){
    BlockPos position;
    int x;
    int y;
    int z;
    final int max_x = center.getX() + radius;
    final int max_y = center.getY() + radius;
    final int max_z = center.getZ() + radius;
    for(y = center.getY() - radius; y <= max_y; y++){
      for(z = center.getZ() - radius; z <= max_z; z++){
        for(x = center.getX() - radius; x <= max_x; x++){
          position = new BlockPos(x,y,z); // this also gets the center (Black Hole) block.
          if(world.getBlockState(position).getBlock() != Blocks.AIR){
            if(MathUtility.is_inside_sphere(center,radius,position)){
              if(Config.black_holes_erase_bedrock){
                world.setBlockToAir(position);
              }
              else{
                if(world.getBlockState(position).getBlock() != Blocks.BEDROCK){
                  world.setBlockToAir(position);
                }
              }
            }
          }
        }
      }
    }
  }
  
  private static final void destroyEntities(World world, double x, double y, double z, final int radius){
    for(Entity entity : world.loadedEntityList){
      if(MathUtility.get_distance(x, y, z, entity.posX, entity.posY, entity.posZ) <= radius){
        // entity.attackEntityFrom(source, amount);
        entity.setDead();
      }
    }
  }
  
  private static final void destroyPlayers(World world, double x, double y, double z, final int radius){
    for(PlayerEntity player : world.playerEntities){
      if(player.isCreative() == false && player.isSpectator() == false){
        if(MathUtility.get_distance(x, y, z, player.posX, player.posY, player.posZ) <= radius){
          player.setDead();
        }
      }
    }
  }
  
  private static final void destroyItems(){
  }

}
