package addsynth.core.util.command;

public final class PermissionLevel {

  /** Only allowed to use basic commands such as
   *  \help, \list, \me, \seed, \tell, and \trigger.
   */
  public static final int BASE = 0;

  public static final int BYPASS_SPAWN_PROTECTION = 1;

  /** Allowed to use most commands such as \advancement, \clear, \data, \difficulty,
   *  \effect, \enchant, \experience, \fill, \gamemode, \gamerule, \give, \kill,
   *  \locate, \particle, \playsound, \recipe, \say, \scoreboard, \setblock,
   *  \summon, \tag, \teleport, \team, \time, \title, \weather, and \worldborder.
   */
  public static final int COMMANDS = 2;

  /** Can use multiplayer commands such as \op, \deop, \ban, \ban-ip, \banlist,
   *  \kick, \pardon, \pardon-ip, \debug, \whitelist, and \setidletimeout.
   */
  public static final int MULTIPLAYER = 3;

  /** Able to use server commands such as \save-all, \save-off, \save-on,
   *  \publish, and \stop.
   */
  public static final int SERVER_OWNER = 4;

}
