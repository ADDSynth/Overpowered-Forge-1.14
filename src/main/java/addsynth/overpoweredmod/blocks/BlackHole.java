package addsynth.overpoweredmod.blocks;

import addsynth.core.util.MathUtility;
import addsynth.core.util.ServerUtils;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public final class BlackHole extends Block {

  public static final byte MIN_RADIUS = 2;
  public static final byte MAX_RADIUS = 100;

  public BlackHole(final String name){
    super(Block.Properties.create(Material.PORTAL, MaterialColor.BLACK).doesNotBlockMovement());
    // setResistance(100.0f);
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab).rarity(Rarity.EPIC));
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving){
    if(world.isRemote == false){
      erase_the_world(world, pos);
    }
  }

  public static final void erase_the_world(final World world, final BlockPos center){
    if(is_black_hole_allowed(world.getDimension().getType().getId())){

      if(Config.alert_players_of_black_hole.get()){
        ServerUtils.send_message_to_all_players_in_world(new StringTextComponent(
          TextFormatting.DARK_PURPLE + "Singularity Event Detected at Coordinates: "+center.getX()+" , "+center.getY()+" , "+center.getZ()
        ), world);
      }
    
      // MAYBE: play sound?

      final int radius = get_black_hole_radius(world);

      final double center_x = center.getX() + 0.5;
      final double center_y = center.getY() + 0.5;
      final double center_z = center.getZ() + 0.5;
      final AxisAlignedBB area = new AxisAlignedBB(center_x, center_y, center_z, center_x, center_y, center_z);
      area.grow(radius);
      destroyBlocks(world, center, radius);
      destroyEntities(world, area);
      destroyItems();
    }
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
        radius = difficulty_radius[difficulty.ordinal()];
      }
    }
    else{
      if(Config.randomize_black_hole_radius.get()){
        radius = MathUtility.RandomRange(Config.minimum_black_hole_radius.get(), Config.maximum_black_hole_radius.get());
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
              if(Config.black_holes_erase_bedrock.get()){
                world.removeBlock(position, false);
              }
              else{
                if(world.getBlockState(position).getBlock() != Blocks.BEDROCK){
                  world.removeBlock(position, false);
                }
              }
            }
          }
        }
      }
    }
  }
  
  private static final void destroyEntities(final World world, final AxisAlignedBB area){
    for(Entity entity : world.getEntitiesWithinAABB(Entity.class, area, null)){
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
        ((LivingEntity)entity).setHealth(0.0f);
        continue;
      }
      entity.remove();
    }
  }
  
  private static final void destroyItems(){
  }

}