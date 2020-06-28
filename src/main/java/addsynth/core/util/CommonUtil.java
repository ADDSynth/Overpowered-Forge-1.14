package addsynth.core.util;

import addsynth.core.Constants;

public final class CommonUtil {

  public static final int getOppositeDirection(final int direction){
    if(direction == Constants.DOWN ){ return Constants.UP;    }
    if(direction == Constants.UP   ){ return Constants.DOWN;  }
    if(direction == Constants.NORTH){ return Constants.SOUTH; }
    if(direction == Constants.SOUTH){ return Constants.NORTH; }
    if(direction == Constants.WEST ){ return Constants.EAST;  }
    if(direction == Constants.EAST ){ return Constants.WEST;  }
    throw new IllegalArgumentException("Input for "+CommonUtil.class.getSimpleName()+".getOppositeDirection() was not a valid Direction index! Input must be between 0 and 5!");
  }

}
