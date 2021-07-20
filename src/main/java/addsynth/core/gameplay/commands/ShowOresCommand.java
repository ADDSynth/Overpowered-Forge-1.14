package addsynth.core.gameplay.commands;

import java.util.Collection;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.math.CommonMath;
import addsynth.core.util.world.WorldConstants;
import addsynth.core.util.world.WorldUtil;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public final class ShowOresCommand {

  public static final void register(CommandDispatcher<CommandSource> dispatcher){
    dispatcher.register(
      Commands.literal(ADDSynthCore.MOD_ID).requires(
        (command_source) -> { return command_source.hasPermissionLevel(PermissionLevel.COMMANDS); }
      ).then(
        Commands.literal("show_ores").executes(
          (command_context) -> { return show_ores(command_context.getSource()); }
        )
      )
    );
    dispatcher.register(
      Commands.literal(ADDSynthCore.MOD_ID).requires(
        (command_source) -> { return command_source.hasPermissionLevel(PermissionLevel.COMMANDS); }
      ).then(
        Commands.literal("sample_ores").executes(
          (command_context) -> { return print_ore_sample(command_context.getSource()); }
        )
      )
    );
  }

  private static final int show_ores(final CommandSource source){
    int ores = 0;
    final Entity entity = source.getEntity();
    if(entity != null){
      final World world = source.func_197023_e();
      final Collection<Block> ore_blocks = Tags.Blocks.ORES.getAllElements();
      final BlockPos position = entity.getPosition();
      final int x_coord = CommonMath.FloorNearest(position.getX(), WorldConstants.chunk_size);
      final int z_coord = CommonMath.FloorNearest(position.getZ(), WorldConstants.chunk_size);
      int x;
      int y;
      int z;
      BlockPos new_position;
      BlockState blockstate;
      Block block;
      IFluidState fluid_state;
      for(y = WorldConstants.world_height - 1; y >= 5; y--){
        for(x = x_coord - 1; x < x_coord + 17; x++){
          for(z = z_coord - 1; z < z_coord + 17; z++){
            new_position = new BlockPos(x, y, z);
            blockstate = world.getBlockState(new_position);
            if(x == x_coord - 1 || x == x_coord + 16 || z == z_coord - 1 || z == z_coord + 16){
              fluid_state = blockstate.getFluidState();
              if(fluid_state.isSource()){
                world.setBlockState(new_position, Blocks.STONE.getDefaultState());
              }
            }
            else{
              block = blockstate.getBlock();
              if(ore_blocks.contains(block)){
                ores += 1;
              }
              else{
                WorldUtil.delete_block(world, new_position);
              }
            }
          }
        }
      }
    }
    return ores;
  }

  private static final int print_ore_sample(final CommandSource source){
    // TODO: Print Ore Sample Command.
    // specify radius argument, default of 2.
    // get current chunk of entity, get all chunks within radius.
    // check each block, Use a Map or Dictionary to map each ore block to a value, increment
    // value each time we count that ore.
    // print basic counts first, like total Aluminum, or Total Topaz Ore.
    // print to a text file, first line says "took ore sample of 25 chunks".
    return 0;
  }

}
