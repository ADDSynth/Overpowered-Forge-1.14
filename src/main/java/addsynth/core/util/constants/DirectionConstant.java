package addsynth.core.util.constants;

import javax.annotation.Nonnegative;
import addsynth.core.ADDSynthCore;
import net.minecraft.util.Direction;

public final class DirectionConstant {

  public static final int DOWN  = Direction.DOWN.getIndex();
  public static final int UP    = Direction.UP.getIndex();
  public static final int NORTH = Direction.NORTH.getIndex();
  public static final int SOUTH = Direction.SOUTH.getIndex();
  public static final int WEST  = Direction.WEST.getIndex();
  public static final int EAST  = Direction.EAST.getIndex();

  public static final int getOppositeDirection(@Nonnegative final int direction){
    if(direction == DOWN ){ return UP;    }
    if(direction == UP   ){ return DOWN;  }
    if(direction == NORTH){ return SOUTH; }
    if(direction == SOUTH){ return NORTH; }
    if(direction == WEST ){ return EAST;  }
    if(direction == EAST ){ return WEST;  }
    ADDSynthCore.log.error(new IllegalArgumentException("Invalid input direction for "+DirectionConstant.class.getSimpleName()+".getOppositeDirection()! Only values 0 to 5 are acceptable!"));
    if((direction & 1) == 0){
      return (Math.abs(direction) + 1) % 6;
    }
    return (Math.abs(direction) - 1) % 6;
  }

}
