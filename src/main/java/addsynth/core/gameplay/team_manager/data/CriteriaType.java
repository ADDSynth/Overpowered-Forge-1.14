package addsynth.core.gameplay.team_manager.data;

public final class CriteriaType {

  public static final int STANDARD       =  0;
  public static final int TEAM_KILL      =  1;
  public static final int KILLED_BY_TEAM =  2;
  public static final int STATISTICS     =  3;
  public static final int BLOCK_MINED    =  4;
  public static final int ITEM_BROKEN    =  5;
  public static final int ITEM_CRAFTED   =  6;
  public static final int ITEM_USED      =  7;
  public static final int ITEM_PICKED_UP =  8;
  public static final int ITEM_DROPPED   =  9;
  public static final int KILLED         = 10;
  public static final int KILLED_BY      = 11;
  
  public static final String TEAM_KILL_PREFIX      = "teamkill.";
  public static final String KILLED_BY_TEAM_PREFIX = "killedByTeam.";
  public static final String ITEM_BROKEN_PREFIX    = "minecraft.broken:";
  public static final String ITEM_CRAFTED_PREFIX   = "minecraft.crafted:";
  public static final String STATISTICS_PREFIX     = "minecraft.custom:";
  public static final String ITEM_DROPPED_PREFIX   = "minecraft.dropped:";
  public static final String BLOCK_MINED_PREFIX    = "minecraft.mined:";
  public static final String ITEM_PICKED_UP_PREFIX = "minecraft.picked_up:";
  public static final String ITEM_USED_PREFIX      = "minecraft.used:";
  public static final String KILLED_PREFIX         = "minecraft.killed:";
  public static final String KILLED_BY_PREFIX      = "minecraft.killed_by:";
  
}
