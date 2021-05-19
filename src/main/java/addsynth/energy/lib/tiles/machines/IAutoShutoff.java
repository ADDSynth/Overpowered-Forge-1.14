package addsynth.energy.lib.tiles.machines;

public interface IAutoShutoff extends ISwitchableMachine {

  public void toggle_auto_shutoff();

  public boolean getAutoShutoff();

}
