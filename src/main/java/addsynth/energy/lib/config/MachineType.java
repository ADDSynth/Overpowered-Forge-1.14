package addsynth.energy.lib.config;

public enum MachineType {

  /** Standard machines can be turned off, have an Idle state, and a Working state. */
  STANDARD,

  /** Always On machines cannot be turned off by the player, but they have Idle and Working states. */
  ALWAYS_ON,

  /** Passive machines do work when they are on. There is no Idle state. */
  PASSIVE,

  /** This machine can be switched off, but only accepts energy when ON. But does nothing
   *  when it reaches full energy. You must call the do_work() function and drain energy yourself. */
  MANUAL_ACTIVATION;

}
