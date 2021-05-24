package addsynth.core.gameplay.commands;

import java.util.ArrayList;
import java.util.Random;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.command.CommandUtil;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.math.Movement;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public final class ItemExplosionCommand {

  // private static final int MIN_ITEMS = 1;
  private static final int MAX_ITEMS = 1_000;
  // private static final int MIN_ITEM_RADIUS = 1;
  private static final int MAX_ITEM_RADIUS = 300;

  private static final int DEFAULT_ITEMS = 200;
  private static final int DEFAULT_ITEM_RADIUS = 16;

  public static final void register(CommandDispatcher<CommandSource> dispatcher){
    dispatcher.register(
      Commands.literal(ADDSynthCore.MOD_ID).requires(
        (command_source) -> { return command_source.hasPermissionLevel(PermissionLevel.COMMANDS); }
      ).then(
        Commands.literal("item_explosion").executes(
          (command_context) -> { return item_explosion(command_context.getSource(), DEFAULT_ITEMS, DEFAULT_ITEM_RADIUS); }
        ).then(
          Commands.argument("items", IntegerArgumentType.integer(1, MAX_ITEMS)).executes(
            (command_context) -> { return item_explosion(command_context.getSource(), IntegerArgumentType.getInteger(command_context, "items"), DEFAULT_ITEM_RADIUS); }
          ).then(
            Commands.argument("radius", IntegerArgumentType.integer(1, MAX_ITEM_RADIUS)).executes(
              (command_context) -> {
                return item_explosion(command_context.getSource(), IntegerArgumentType.getInteger(command_context, "items"),
                                                                  IntegerArgumentType.getInteger(command_context, "radius"));
              }
            )
          )
        )
      )
    );
  }

  private static final int item_explosion(final CommandSource command_source, final int item_count, final int radius) throws CommandSyntaxException {
    CommandUtil.check_argument("items", item_count, 1, MAX_ITEMS);
    CommandUtil.check_argument("radius", radius, 1, MAX_ITEM_RADIUS);

    // get world and position
    final Vec3d position    = command_source.getPos();
    @SuppressWarnings("resource")
    final ServerWorld world = command_source.func_197023_e();

    // get items
    final ItemStack[] items = new ItemStack[item_count];
    int i;
    final Random random = new Random();
    final ArrayList<Item> all_items = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
    final int size = all_items.size();
    for(i = 0; i < item_count; i++){
      items[i] = new ItemStack(all_items.get(random.nextInt(size)), 1);
    }

    // get data
    final int fly_time = 40; // 2 seconds
    final int rings = (int)Math.ceil(Math.sqrt(((double)item_count) / Math.PI));
    final double[] scaled_distance = new double[rings];
    double area;
    final int[] total_area = new int[rings];
    final int[] ring_area = new int[rings];
    for(i = 0; i < rings; i++){
      // get scaled radius
      scaled_distance[i] = ((double)(i+1) / (rings))*Movement.getHorizontalVelocity(fly_time, radius);
      // get number of items per ring
      area = (i+1)*(i+1)*Math.PI;
      total_area[i] = (int)Math.round(area);
      if(i == 0){
        ring_area[0] = 3;
      }
      else{
        if(i == rings - 1){
          ring_area[i] = (int)Math.round( item_count - (i*i*Math.PI) );
        }
        else{
          ring_area[i] = (int)Math.round( area - (i*i*Math.PI) );
        }
      }
    }
    final double phase = (double)random.nextInt(360) / 360;
    
    // spawn items
    int ring;
    int index;
    for(i = 0; i < item_count; i++){
      ring = (int)Math.floor(Math.sqrt(i / Math.PI));
      index = ring == 0 ? i : i - total_area[ring-1];
      final ItemEntity item_entity = new ItemEntity(world, position.x, position.y, position.z, items[i]);
      item_entity.setMotion(
        Math.cos(phase + (2*Math.PI*((double)index / ring_area[ring])))*scaled_distance[ring],
        Movement.getUpwardsVelocity(fly_time),
        Math.sin(phase + (2*Math.PI*((double)index / ring_area[ring])))*scaled_distance[ring]
      );
      item_entity.setPickupDelay(20);
      world.addEntity(item_entity);
    }
    command_source.sendFeedback(new StringTextComponent("Spawned "+item_count+" items."), true);
    // \nUse command §b/kill @e[type=item]§r to remove them.
    return item_count;
  }
  
}
