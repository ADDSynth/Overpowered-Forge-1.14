package addsynth.overpoweredmod.machines.laser.cannon;

import addsynth.core.Debug;
import addsynth.core.tiles.TileBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public final class TileLaser extends TileBase implements ITickableTileEntity {

  private Laser laser;
  private Direction direction;
  private int distance;
  private int count;
  private boolean active;

  // TileEntities cannot have enums as an argument, otherwise Forge throws a InstantiationException.
  // https://stackoverflow.com/questions/41216000/why-newinstance-throws-instantiationexception-in-my-code
  // https://stackoverflow.com/questions/234600/can-i-use-class-newinstance-with-constructor-arguments
  // https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html#getDeclaredConstructor%28java.lang.Class...%29
  // https://stackoverflow.com/questions/195321/why-is-class-newinstance-evil
  // https://docs.oracle.com/javase/tutorial/reflect/member/ctorInstance.html
  // 
  // https://mcforge.readthedocs.io/en/latest/tileentities/tileentity/#creating-a-tileentity
  // "It is important that your TileEntity have a default constructor so Minecraft can load it correctly."

  public TileLaser(){
    super(Tiles.LASER);
  }

  public final void activate(int distance){
    this.distance = distance;
    count = 1;
    active = true;
  }

  @Override
  public void onLoad(){
    if(world.isRemote == false){
      final BlockState block_state = getBlockState();
      final LaserCannon block = (LaserCannon)block_state.getBlock();
      this.laser = Laser.index[Math.max(0, block.color)];
      if(block.color < 0){
        OverpoweredMod.log.fatal(
          "Standard Lasers have a color index indicating the type of laser 0-"+(Laser.index.length-1)+", but this laser has an "+
          "index of "+block.color+". Non-standard lasers currently don't need a TileEntity. If you're receiving this message, "+
          "it's probably safe to continue playing, but this indicates a serious error. Please report this to the mod author.");
        Debug.block(block, pos);
        Thread.dumpStack();
        remove();
      }
      this.direction = block_state.get(LaserCannon.FACING);
    }
  }

  @Override
  public final void tick(){
    if(world != null){
      if(world.isRemote == false){
        if(active){
          final BlockPos position = pos.offset(direction, count);
          final Block block = world.getBlockState(position).getBlock();
          if(block != Blocks.BEDROCK){
            world.destroyBlock(position, true);
            // entities won't catch on fire unless laser beam is also on server side.
            world.setBlockState(position, laser.beam.getDefaultState(),2);
            count += 1;
            if(count > distance){
              active = false;
            }
          }
          else{
            active = false;
          }
        }
      }
    }
  }

}
