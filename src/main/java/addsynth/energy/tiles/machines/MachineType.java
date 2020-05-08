package addsynth.energy.tiles.machines;

public enum MachineType {

  /** Standard machines can be turned off, have an Idle state, and a Working state. */
  STANDARD(true, false, true),

  /** Always On machines cannot be turned off by the player, but they have Idle and Working states. */
  ALWAYS_ON(false, false, true),

  /** Passive machines do work when they are on. There is no Idle state. */
  PASSIVE(true, true, true),

  /** This machine can be switched off, but only accepts energy when ON. But does nothing
   *  when it reaches full energy. You must call the do_work() function and drain energy yourself. */
  MANUAL_ACTIVATION(true, true, false);

  public final boolean can_be_off;
  public final boolean passive_work;
  public final boolean auto_switch;
  
  private MachineType(boolean can_be_off, boolean passive_work, boolean auto_switch){
    this.can_be_off = can_be_off;
    this.passive_work = passive_work;
    this.auto_switch = auto_switch;
  }

}
