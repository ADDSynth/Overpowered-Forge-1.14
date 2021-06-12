package addsynth.core.gameplay.team_manager.network_messages;

import java.util.Arrays;
import java.util.function.Supplier;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.team_manager.TeamData;
import addsynth.core.util.game.MessageUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.core.util.network.NetworkUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team.Visible;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class TeamManagerCommand {

  public static final int ADD_TEAM           =  0;
  public static final int EDIT_TEAM          =  1;
  public static final int DELETE_TEAM        =  2;
  public static final int ADD_PLAYER         =  3;
  public static final int REMOVE_PLAYER      =  4;
  public static final int ADD_OBJECTIVE      =  5;
  public static final int EDIT_OBJECTIVE     =  6;
  public static final int DELETE_OBJECTIVE   =  7;
  public static final int SET_SCORE          =  8;
  public static final int ADD_SCORE          =  9;
  public static final int SUBTRACT_SCORE     = 10;
  public static final int RESET_SCORE        = 11;
  public static final int SET_DISPLAY_SLOT   = 12;
  public static final int CLEAR_DISPLAY_SLOT = 13;

  private int command;
  private Object[] parameters;

  public TeamManagerCommand(){
  }

  /** For resetting display slots. */
  public TeamManagerCommand(int command, int display_slot){
    checkCommands(command, CLEAR_DISPLAY_SLOT);
    this.parameters = new Object[]{display_slot};
  }

  /** For deleting teams or objectives. */
  public TeamManagerCommand(int command, String name){
    checkCommands(command, DELETE_TEAM, DELETE_OBJECTIVE);
    this.parameters = new Object[]{name};
  }

  /** For assigning objective to a display slot. */
  public TeamManagerCommand(int command, String objective, int display_slot){
    checkCommands(command, SET_DISPLAY_SLOT);
    this.parameters = new Object[]{objective, display_slot};
  }

  /** For adding or removing players from a team. */
  public TeamManagerCommand(int command, String player, String team){
    checkCommands(command, ADD_PLAYER, REMOVE_PLAYER);
    this.parameters = new Object[]{player, team};
  }

  /** For adding or editing objectives, or for changing a player's score. */
  public TeamManagerCommand(int command, String objective_id, String display_name, int criteria){
    checkCommands(command, ADD_OBJECTIVE, EDIT_OBJECTIVE, SET_SCORE, ADD_SCORE, SUBTRACT_SCORE);
    this.parameters = new Object[]{objective_id, display_name, criteria};
  }

  /** For adding or editing teams. */
  public TeamManagerCommand(int command, String team_id, String display_name, boolean pvp, boolean see_invisible_allys, int color,
                            int nametag_option, int death_message_option, String prefix, String suffix){
    checkCommands(command, ADD_TEAM, EDIT_TEAM);
    this.parameters = new Object[]{team_id, display_name, pvp, see_invisible_allys, color, nametag_option, death_message_option, prefix, suffix};
  }

  private final void checkCommands(Integer command, Integer ... valid_commands){
    if(ArrayUtil.valueExists(valid_commands, command)){
      this.command = command;
    }
    else{
      ADDSynthCore.log.error("Programmer used wrong command type for TeamMessageCommand! Expected commands are "+
        Arrays.deepToString(valid_commands)+" but found "+command+"! Please report to the developer.");
    }
  }

  public static final void encode(final TeamManagerCommand message, final PacketBuffer data){
    data.writeByte(message.command);
    try{
      switch(message.command){
      case ADD_TEAM: case EDIT_TEAM:
        data.writeString((String)message.parameters[0]);
        data.writeString((String)message.parameters[1]);
        data.writeBoolean((boolean)message.parameters[2]);
        data.writeBoolean((boolean)message.parameters[3]);
        data.writeInt((int)message.parameters[4]);
        data.writeInt((int)message.parameters[5]);
        data.writeInt((int)message.parameters[6]);
        data.writeString((String)message.parameters[7]);
        data.writeString((String)message.parameters[8]);
        break;
      case DELETE_TEAM: case DELETE_OBJECTIVE:
        data.writeString((String)message.parameters[0]);
        break;
      case ADD_PLAYER: case REMOVE_PLAYER:
        data.writeString((String)message.parameters[0]);
        data.writeString((String)message.parameters[1]);
        break;
      case ADD_OBJECTIVE: case EDIT_OBJECTIVE: case SET_SCORE: case ADD_SCORE: case SUBTRACT_SCORE:
        data.writeString((String)message.parameters[0]);
        data.writeString((String)message.parameters[1]);
        data.writeInt((int)message.parameters[2]);
        break;
      case SET_DISPLAY_SLOT:
        data.writeString((String)message.parameters[0]);
        data.writeInt((int)message.parameters[1]);
        break;
      case CLEAR_DISPLAY_SLOT:
        data.writeInt((int)message.parameters[0]);
        break;
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
  
  public static final TeamManagerCommand decode(final PacketBuffer data){
    final TeamManagerCommand message = new TeamManagerCommand();
    message.command = data.readByte();
    try{
      switch(message.command){
      case ADD_TEAM: case EDIT_TEAM:
        message.parameters = new Object[]{
          NetworkUtil.readString(data),
          NetworkUtil.readString(data),
          data.readBoolean(),
          data.readBoolean(),
          data.readInt(),
          data.readInt(),
          data.readInt(),
          NetworkUtil.readString(data),
          NetworkUtil.readString(data)
        };
        break;
      case DELETE_TEAM: case DELETE_OBJECTIVE:
        message.parameters = new Object[]{
          NetworkUtil.readString(data)
        };
        break;
      case ADD_PLAYER: case REMOVE_PLAYER:
        message.parameters = new Object[]{
          NetworkUtil.readString(data),
          NetworkUtil.readString(data)
        };
        break;
      case ADD_OBJECTIVE: case EDIT_OBJECTIVE: case SET_SCORE: case ADD_SCORE: case SUBTRACT_SCORE:
        message.parameters = new Object[]{
          NetworkUtil.readString(data),
          NetworkUtil.readString(data),
          data.readInt()
        };
        break;
      case SET_DISPLAY_SLOT:
        message.parameters = new Object[]{
          NetworkUtil.readString(data),
          data.readInt()
        };
        break;
      case CLEAR_DISPLAY_SLOT:
        message.parameters = new Object[]{
          data.readInt()
        };
        break;
      }
    }
    catch(Exception e){
      e.printStackTrace();
      return new TeamManagerCommand();
    }
    return message;
  }
  
  @SuppressWarnings("resource")
  public static final void handle(final TeamManagerCommand message, final Supplier<NetworkEvent.Context> context){
    final ServerPlayerEntity player = context.get().getSender();
    if(player != null){
      final ServerWorld world = player.func_71121_q();
      context.get().enqueueWork(() -> {
        try{
          final MinecraftServer server = world.getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          switch(message.command){
          case ADD_TEAM: case EDIT_TEAM:
            edit_team(scoreboard, player, message.parameters);
            break;
          case DELETE_TEAM:
            delete_team(scoreboard, player, (String)message.parameters[0]);
            break;
          case ADD_PLAYER:
            add_player_to_team(scoreboard, player, (String)message.parameters[0], (String)message.parameters[1]);
            break;
          case REMOVE_PLAYER:
            remove_player_from_team(scoreboard, player, (String)message.parameters[0], (String)message.parameters[1]);
            break;
          case ADD_OBJECTIVE: case EDIT_OBJECTIVE:
            edit_objective(scoreboard, player, (String)message.parameters[0], (String)message.parameters[1], (int)message.parameters[2]);
            break;
          case DELETE_OBJECTIVE:
            delete_objective(scoreboard, player, (String)message.parameters[0]);
            break;
          case SET_SCORE: case ADD_SCORE: case SUBTRACT_SCORE:
            change_score(message.command, scoreboard, player, (String)message.parameters[0], (String)message.parameters[1], (int)message.parameters[2]);
            break;
          case SET_DISPLAY_SLOT:
            final ScoreObjective objective = scoreboard.getObjective((String)message.parameters[0]);
            scoreboard.setObjectiveInDisplaySlot((int)message.parameters[1], objective);
            TeamData.sync();
            break;
          case CLEAR_DISPLAY_SLOT:
            scoreboard.setObjectiveInDisplaySlot((int)message.parameters[0], (ScoreObjective)null);
            TeamData.sync();
            break;
          }
        }
        catch(Exception e){
          e.printStackTrace();
        }
      });
      context.get().setPacketHandled(true);
    }
  }

  private static final void edit_team(final Scoreboard scoreboard, final ServerPlayerEntity player, final Object[] parameters){
    final String team_name = (String)parameters[0];
    if(team_name.isEmpty()){
      MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.create_team_failed");
      return;
    }
    // if(team_name.length() > max_name_length){ // was 16, but then I saw that vanilla does it anyway
    //   MessageUtil.send_to_player(player, "Failed to create team. Team Name must not be greater than "+max_name_length+" characters.");
    //   return;
    // }
    String display_name = (String)parameters[1];
    if(display_name.isEmpty()){
      display_name = team_name;
    }
    final boolean pvp = (boolean)parameters[2];
    final boolean see_invisible_allys = (boolean)parameters[3];
    final int color = (int)parameters[4];
    final int nametag_option = (int)parameters[5];
    final int death_message_option = (int)parameters[6];
    final String prefix = (String)parameters[7];
    final String suffix = (String)parameters[8];

    ScorePlayerTeam team = scoreboard.getTeam(team_name) ;
    if(team == null){
      team = scoreboard.createTeam(team_name);
    }
    
    team.setDisplayName(new StringTextComponent(display_name));
    team.setAllowFriendlyFire(pvp);
    team.setSeeFriendlyInvisiblesEnabled(see_invisible_allys);
    team.setColor(TextFormatting.fromColorIndex(color));
    team.setNameTagVisibility(Visible.values()[nametag_option]);
    team.setDeathMessageVisibility(Visible.values()[death_message_option]);
    team.setPrefix(new StringTextComponent(prefix));
    team.setSuffix(new StringTextComponent(suffix));
    
    // MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.edit_team_success", team_name);
    TeamData.sync();
  }

  private static final void delete_team(final Scoreboard scoreboard, final ServerPlayerEntity player, final String team_name){
    scoreboard.removeTeam(scoreboard.getTeam(team_name));
    TeamData.sync();
  }

  private static final void add_player_to_team(final Scoreboard scoreboard, final ServerPlayerEntity command_source, final String player, final String team){
    if(scoreboard.addPlayerToTeam(player, scoreboard.getTeam(team))){
      TeamData.sync();
    }
  }

  private static final void remove_player_from_team(final Scoreboard scoreboard, final ServerPlayerEntity command_source, final String player, final String team_name){
    ScorePlayerTeam team = scoreboard.getPlayersTeam(player);
    if(team != null){ // player is on a team
      if(team.getName().equals(team_name)){ // players team is the one we want him out of
        scoreboard.removePlayerFromTeam(player, scoreboard.getTeam(team_name));
        TeamData.sync();
      }
    }
  }

  private static final void edit_objective(final Scoreboard scoreboard, final ServerPlayerEntity player, final String objective, final String display_name, final int criteria_id){
    if(objective.isEmpty()){
      MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.create_objective_failed");
      return;
    }
    // final ScoreObjective existing_objective = scoreboard.getObjective(objective);
    // if(existing_objective == null){
      final ScoreCriteria criteria = TeamData.getCriteria(criteria_id);
      scoreboard.addObjective(objective, criteria, new StringTextComponent(display_name), criteria.getRenderType());
      TeamData.sync();
    // }
  }

  private static final void delete_objective(final Scoreboard scoreboard, final ServerPlayerEntity player, final String objective){
    scoreboard.removeObjective(scoreboard.getObjective(objective));
    TeamData.sync();
  }

  private static final void change_score(final int command, final Scoreboard scoreboard, final ServerPlayerEntity command_source, final String player, final String objective_name, final int value){
    final ScoreObjective objective = scoreboard.getObjective(objective_name);
    final Score score = scoreboard.getOrCreateScore(player, objective);
    switch(command){
    case SET_SCORE:      score.setScorePoints(value); break;
    case ADD_SCORE:      score.increaseScore(value); break;
    case SUBTRACT_SCORE: score.increaseScore(-value); break;
    case RESET_SCORE:    score.reset(); break;
    }
  }

}
