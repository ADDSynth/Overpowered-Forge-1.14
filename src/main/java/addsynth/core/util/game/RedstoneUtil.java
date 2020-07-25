package addsynth.core.util.game;

public final class RedstoneUtil {

  public static final boolean rising_edge_trigger(final boolean powered, final boolean previous_state){
    return powered && powered != previous_state;
  }

  public static final boolean turned_off(final boolean powered, final boolean previous_state){
    return ( !powered ) && (powered != previous_state);
  }

}
