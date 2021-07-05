package addsynth.core.gameplay.team_manager.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.team_manager.gui.TeamManagerGui;
import addsynth.core.gameplay.team_manager.gui.TeamManagerObjectiveGui;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerSyncMessage;
import addsynth.core.util.StringUtil;
import addsynth.core.util.network.NetworkUtil;
import addsynth.core.util.server.ServerUtils;
import addsynth.core.util.time.TimeConstants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public final class TeamData {

  private static Collection<ScorePlayerTeam> team_list;
  private static int number_of_teams;
  private static ScorePlayerTeam[] team_array;

  private static List<? extends PlayerEntity> all_players;
  private static Team team;

  private static Collection<ScoreObjective> objectives_list;
  private static int number_of_objectives;
  private static ScoreObjective[] objective_array;

  private static int tick_time;

  private static ArrayList<ITextComponent> non_team_players = new ArrayList<ITextComponent>();
  private static TeamDataUnit[] teams;
  private static ObjectiveDataUnit[] objectives;
  /** This is set to true whenever the client is synced with the server data. Used to update guis that are open. */
  public static boolean changed;

  private static String[] display_slot_objective = {"", "", ""};

  /** This runs every server tick (20 times a second). Assigned to the Forge Event bus by {@link ADDSynthCore}. */
  public static final void serverTick(final ServerTickEvent event){
    if(event.phase == Phase.END){
      tick_time += 1;
      if(tick_time >= TimeConstants.ticks_per_second){
        sync();
        tick_time = 0;
      }
    }
  }

  /** Gets data from server. */
  public static final void sync(){
    @SuppressWarnings({ "deprecation", "resource" })
    final MinecraftServer server = ServerUtils.getServer();
    if(server != null){
      final Scoreboard scoreboard = server.getScoreboard();
      
      // Teams
      team_list = scoreboard.getTeams();
      number_of_teams = team_list.size();
      team_array = team_list.toArray(new ScorePlayerTeam[number_of_teams]);
      teams = new TeamDataUnit[number_of_teams];
      int i;
      for(i = 0; i < number_of_teams; i++){
        teams[i] = new TeamDataUnit();
        teams[i].name = team_array[i].getName();
        teams[i].display_name = team_array[i].getDisplayName();
        teams[i].color = team_array[i].getColor().getColorIndex();
        teams[i].prefix = team_array[i].getPrefix();
        teams[i].suffix = team_array[i].getSuffix();
        teams[i].pvp = team_array[i].getAllowFriendlyFire();
        teams[i].see_invisible_allys = team_array[i].getSeeFriendlyInvisiblesEnabled();
        teams[i].nametag_option = team_array[i].getNameTagVisibility().id;
        teams[i].death_message_option = team_array[i].getDeathMessageVisibility().id;
      }
      
      // Players
      all_players = server.getPlayerList().getPlayers();
      non_team_players.clear();
      for(final PlayerEntity player : all_players){
        team = player.getTeam();
        if(team == null){
          non_team_players.add(player.getName());
        }
        else{
          for(final TeamDataUnit team_data : teams){
            if(team_data.matches(team)){
              team_data.players.add(player.getName());
            }
          }
        }
      }
      
      // Objectives
      objectives_list = scoreboard.getScoreObjectives();
      number_of_objectives = objectives_list.size();
      objective_array = objectives_list.toArray(new ScoreObjective[number_of_objectives]);
      objectives = new ObjectiveDataUnit[number_of_objectives];
      for(i = 0; i < number_of_objectives; i++){
        objectives[i] = new ObjectiveDataUnit();
        objectives[i].name = objective_array[i].getName();
        objectives[i].display_name = objective_array[i].getDisplayName();
        objectives[i].criteria = objective_array[i].getCriteria();
        objectives[i].criteria_name = objectives[i].criteria.getName();
        objectives[i].modify = !objectives[i].criteria.isReadOnly();
      }
      
      // DisplaySlots
      ScoreObjective o = scoreboard.getObjectiveInDisplaySlot(0);
      display_slot_objective[0] = o != null ? o.getName() : "";
      o = scoreboard.getObjectiveInDisplaySlot(1);
      display_slot_objective[1] = o != null ? o.getName() : "";
      o = scoreboard.getObjectiveInDisplaySlot(2);
      display_slot_objective[2] = o != null ? o.getName() : "";
  
      NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new TeamManagerSyncMessage());
    }
  }

  /** Send data to Clients. */
  public static final void encode(final PacketBuffer data){
    NetworkUtil.writeTextComponentArray(data, non_team_players.toArray(new ITextComponent[non_team_players.size()]));
    data.writeInt(teams.length);
    for(final TeamDataUnit t : teams){
      t.encode(data);
    }
    data.writeInt(objectives.length);
    for(final ObjectiveDataUnit o : objectives){
      o.encode(data);
    }
    data.writeString(display_slot_objective[0]);
    data.writeString(display_slot_objective[1]);
    data.writeString(display_slot_objective[2]);
  }

  /** Receiving data on client side. */
  public static final void decode(final PacketBuffer data){
    non_team_players = new ArrayList<ITextComponent>();
    for(final ITextComponent t : NetworkUtil.readTextComponentArray(data)){
      non_team_players.add(t);
    }
    int length = data.readInt();
    teams = new TeamDataUnit[length];
    int i;
    for(i = 0; i < length; i++){
      teams[i] = TeamDataUnit.decode(data);
    }
    length = data.readInt();
    objectives = new ObjectiveDataUnit[length];
    for(i = 0; i < length; i++){
      objectives[i] = ObjectiveDataUnit.decode(data);
    }
    display_slot_objective[0] = NetworkUtil.readString(data);
    display_slot_objective[1] = NetworkUtil.readString(data);
    display_slot_objective[2] = NetworkUtil.readString(data);
    
    changed = true;
  }

  public static final int getStandardCriteriaIndex(final String criteria){
    if(criteria.equals(ScoreCriteria.DUMMY.getName())){             return  0; }
    if(criteria.equals(ScoreCriteria.TRIGGER.getName())){           return  1; }
    if(criteria.equals(ScoreCriteria.DEATH_COUNT.getName())){       return  2; }
    if(criteria.equals(ScoreCriteria.PLAYER_KILL_COUNT.getName())){ return  3; }
    if(criteria.equals(ScoreCriteria.TOTAL_KILL_COUNT.getName())){  return  4; }
    if(criteria.equals(ScoreCriteria.HEALTH.getName())){            return  5; }
    if(criteria.equals(ScoreCriteria.XP.getName())){                return  6; }
    if(criteria.equals(ScoreCriteria.LEVEL.getName())){             return  7; }
    if(criteria.equals(ScoreCriteria.FOOD.getName())){              return  8; }
    if(criteria.equals(ScoreCriteria.AIR.getName())){               return  9; }
    if(criteria.equals(ScoreCriteria.ARMOR.getName())){             return 10; }
    return -1;
  }

  public static final int getStandardCriteriaIndex(final ScoreCriteria input_criteria){
    if(input_criteria == ScoreCriteria.DUMMY){             return  0; }
    if(input_criteria == ScoreCriteria.TRIGGER){           return  1; }
    if(input_criteria == ScoreCriteria.DEATH_COUNT){       return  2; }
    if(input_criteria == ScoreCriteria.PLAYER_KILL_COUNT){ return  3; }
    if(input_criteria == ScoreCriteria.TOTAL_KILL_COUNT){  return  4; }
    if(input_criteria == ScoreCriteria.HEALTH){            return  5; }
    if(input_criteria == ScoreCriteria.XP){                return  6; }
    if(input_criteria == ScoreCriteria.LEVEL){             return  7; }
    if(input_criteria == ScoreCriteria.FOOD){              return  8; }
    if(input_criteria == ScoreCriteria.AIR){               return  9; }
    if(input_criteria == ScoreCriteria.ARMOR){             return 10; }
    ADDSynthCore.log.error(new IllegalArgumentException("Invalid ScoreCriteria."));
    return -1;
  }

  /** Currently only used in the Objective Gui to get the Standard Criteria name based on the selected List Entry. */
  public static final String getStandardCriteria(final int index){
    ScoreCriteria criteria = null;
    switch(index){
    case 0: criteria = ScoreCriteria.DUMMY; break;
    case 1: criteria = ScoreCriteria.TRIGGER; break;
    case 2: criteria = ScoreCriteria.DEATH_COUNT; break;
    case 3: criteria = ScoreCriteria.PLAYER_KILL_COUNT; break;
    case 4: criteria = ScoreCriteria.TOTAL_KILL_COUNT; break;
    case 5: criteria = ScoreCriteria.HEALTH; break;
    case 6: criteria = ScoreCriteria.XP; break;
    case 7: criteria = ScoreCriteria.LEVEL; break;
    case 8: criteria = ScoreCriteria.FOOD; break;
    case 9: criteria = ScoreCriteria.AIR; break;
    case 10: criteria = ScoreCriteria.ARMOR; break;
    default: ADDSynthCore.log.error(new IllegalArgumentException("Invalid index.")); break;
    }
    return criteria != null ? criteria.getName() : "[Null Error]";
  }

  /** Returns the {@link ScoreCriteria} given the supplied ID. */
  public static final ScoreCriteria getCriteria(final String id){
    final Optional<ScoreCriteria> criteria = ScoreCriteria.func_216390_a(id);
    if(criteria.isPresent()){
      return criteria.get();
    }
    ADDSynthCore.log.error(new NoSuchElementException("Unable to determine Criteria. Invalid criteria ID '"+id+"'."));
    return null;
  }

  /** Returns the criteria type given the name. Used for the Radial Button Group
   *  in the Objective Edit Screen.
   * @param criteria
   */
  public static final int getCriteriaType(final String criteria){
    if(criteria.startsWith("teamkill.")){            return CriteriaType.TEAM_KILL; }
    if(criteria.startsWith("killedByTeam.")){        return CriteriaType.KILLED_BY_TEAM; }
    if(criteria.startsWith("minecraft.broken:")){    return CriteriaType.ITEM_BROKEN; }
    if(criteria.startsWith("minecraft.crafted:")){   return CriteriaType.ITEM_CRAFTED; }
    if(criteria.startsWith("minecraft.custom:")){    return CriteriaType.STATISTICS; }
    if(criteria.startsWith("minecraft.dropped:")){   return CriteriaType.ITEM_DROPPED; }
    if(criteria.startsWith("minecraft.killed:")){    return CriteriaType.KILLED; }
    if(criteria.startsWith("minecraft.killed_by:")){ return CriteriaType.KILLED_BY; }
    if(criteria.startsWith("minecraft.mined:")){     return CriteriaType.BLOCK_MINED; }
    if(criteria.startsWith("minecraft.picked_up:")){ return CriteriaType.ITEM_PICKED_UP; }
    if(criteria.startsWith("minecraft.used:")){      return CriteriaType.ITEM_USED; }
    return CriteriaType.STANDARD;
  }

  public static final String[] getTeams(){
    if(teams != null){
      int i;
      final String[] t = new String[teams.length];
      for(i = 0; i < teams.length; i++){
        t[i] = teams[i].name;
      }
      return t;
    }
    return new String[0];
  }

  public static final String[] getPlayers(){
    if(non_team_players != null){
      int i;
      final int length = non_team_players.size();
      final String[] p = new String[length];
      for(i = 0; i < length; i++){
        p[i] = non_team_players.get(i).getFormattedText();
      }
      return p;
    }
    return new String[0];
  }

  /** Used to build the Objectives List on the Main Screen. */
  public static final String[] getObjectives(){
    if(objectives != null){
      int i;
      final String[] o = new String[objectives.length];
      for(i = 0; i < objectives.length; i++){
        o[i] = objectives[i].name;
      }
      return o;
    }
    return new String[0];
  }

  public static final String[] getTeamPlayers(final String team_selected){
    if(StringUtil.StringExists(team_selected)){
      int i;
      String[] tp = null;
      for(final TeamDataUnit t : teams){
        if(t.name.equals(team_selected)){
          final int length = t.players.size();
          tp = new String[length];
          for(i = 0; i < length; i++){
            tp[i] = t.players.get(i).getFormattedText();
          }
          break;
        }
      }
      return tp;
    }
    return new String[0];
  }

  public static final TeamDataUnit getTeamData(final String team_name){
    TeamDataUnit team = null;
    for(final TeamDataUnit t : teams){
      if(t.name.equals(team_name)){
        team = t;
        break;
      }
    }
    return team;
  }

  /** Used by {@link TeamManagerObjectiveGui} to get existing objective data
   *  if the player clicked the Edit Objective button. */
  public static final ObjectiveDataUnit getObjectiveData(final String objective_name){
    ObjectiveDataUnit objective = null;
    for(final ObjectiveDataUnit o : objectives){
      if(o.name.equals(objective_name)){
        objective = o;
        break;
      }
    }
    return objective;
  }

  public static final String getDisplaySlotObjective(final int display_slot){
    return display_slot_objective[display_slot];
  }

  /** Used by {@link TeamManagerGui#tick} to determine whether the selected objective can be modified. */
  public static final boolean canObjectiveBeModified(final String objective){
    boolean can_modify = false;
    for(final ObjectiveDataUnit o : objectives){
      if(o.name.equals(objective)){
        can_modify = o.modify;
        break;
      }
    }
    return can_modify;
  }

}
