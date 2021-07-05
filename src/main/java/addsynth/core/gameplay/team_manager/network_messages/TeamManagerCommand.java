package addsynth.core.gameplay.team_manager.network_messages;

import java.util.function.Supplier;
import addsynth.core.gameplay.team_manager.TeamData;
import addsynth.core.util.game.MessageUtil;
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
import net.minecraftforge.fml.network.NetworkEvent;

public final class TeamManagerCommand {

  public static final class ClearDisplaySlot {
    private final int display_slot;
    public ClearDisplaySlot(int display_slot){
      this.display_slot = display_slot;
    }
    public static void encode(ClearDisplaySlot message, PacketBuffer data){
      data.writeInt(message.display_slot);
    }
    public static ClearDisplaySlot decode(PacketBuffer data){
      return new ClearDisplaySlot(data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(ClearDisplaySlot message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          scoreboard.setObjectiveInDisplaySlot(message.display_slot, null);
          TeamData.sync();
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class DeleteTeam {
    private final String team_name;
    public DeleteTeam(String team_name){
      this.team_name = team_name;
    }
    public static void encode(DeleteTeam message, PacketBuffer data){
      data.writeString(message.team_name);
    }
    public static DeleteTeam decode(PacketBuffer data){
      return new DeleteTeam(NetworkUtil.readString(data));
    }
    @SuppressWarnings("resource")
    public static void handle(DeleteTeam message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          scoreboard.removeTeam(scoreboard.getTeam(message.team_name));
          TeamData.sync();
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class DeleteObjective {
    private final String objective_name;
    public DeleteObjective(String objective_name){
      this.objective_name = objective_name;
    }
    public static void encode(DeleteObjective message, PacketBuffer data){
      data.writeString(message.objective_name);
    }
    public static DeleteObjective decode(PacketBuffer data){
      return new DeleteObjective(NetworkUtil.readString(data));
    }
    @SuppressWarnings("resource")
    public static void handle(DeleteObjective message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          scoreboard.removeObjective(scoreboard.getObjective(message.objective_name));
          TeamData.sync();
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class SetDisplaySlot {
    private final String objective;
    private final int display_slot;
    public SetDisplaySlot(String objective, int display_slot){
      this.objective = objective;
      this.display_slot = display_slot;
    }
    public static void encode(SetDisplaySlot message, PacketBuffer data){
      data.writeString(message.objective);
      data.writeInt(message.display_slot);
    }
    public static SetDisplaySlot decode(PacketBuffer data){
      return new SetDisplaySlot(NetworkUtil.readString(data), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(SetDisplaySlot message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final ScoreObjective objective = scoreboard.getObjective(message.objective);
          scoreboard.setObjectiveInDisplaySlot(message.display_slot, objective);
          TeamData.sync();
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class AddPlayerToTeam {
    private final String player;
    private final String team;
    public AddPlayerToTeam(String player, String team){
      this.player = player;
      this.team = team;
    }
    public static void encode(AddPlayerToTeam message, PacketBuffer data){
      data.writeString(message.player);
      data.writeString(message.team);
    }
    public static AddPlayerToTeam decode(PacketBuffer data){
      return new AddPlayerToTeam(NetworkUtil.readString(data), NetworkUtil.readString(data));
    }
    @SuppressWarnings("resource")
    public static void handle(AddPlayerToTeam message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          if(scoreboard.addPlayerToTeam(message.player, scoreboard.getTeam(message.team))){
            TeamData.sync();
          }
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class RemovePlayerFromTeam {
    private final String player;
    private final String team_name;
    public RemovePlayerFromTeam(String player, String team){
      this.player = player;
      this.team_name = team;
    }
    public static void encode(RemovePlayerFromTeam message, PacketBuffer data){
      data.writeString(message.player);
      data.writeString(message.team_name);
    }
    public static RemovePlayerFromTeam decode(PacketBuffer data){
      return new RemovePlayerFromTeam(NetworkUtil.readString(data), NetworkUtil.readString(data));
    }
    @SuppressWarnings("resource")
    public static void handle(RemovePlayerFromTeam message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          ScorePlayerTeam team = scoreboard.getPlayersTeam(message.player);
          if(team != null){ // player is on a team
            if(team.getName().equals(message.team_name)){ // players team is the one we want him out of
              scoreboard.removePlayerFromTeam(message.player, team);
              TeamData.sync();
            }
          }
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class SetScore {
    private final String objective;
    private final String player;
    private final int new_score_value;
    public SetScore(String objective, String player, int new_score_value){
      this.objective = objective;
      this.player = player;
      this.new_score_value = new_score_value;
    }
    public static void encode(SetScore message, PacketBuffer data){
      data.writeString(message.objective);
      data.writeString(message.player);
      data.writeInt(message.new_score_value);
    }
    public static SetScore decode(PacketBuffer data){
      return new SetScore(NetworkUtil.readString(data), NetworkUtil.readString(data), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(SetScore message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final ScoreObjective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreateScore(message.player, objective);
          score.setScorePoints(message.new_score_value);
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class AddScore {
    private final String objective;
    private final String player;
    private final int score_to_add;
    public AddScore(String objective, String player, int score_to_add){
      this.objective = objective;
      this.player = player;
      this.score_to_add = score_to_add;
    }
    public static void encode(AddScore message, PacketBuffer data){
      data.writeString(message.objective);
      data.writeString(message.player);
      data.writeInt(message.score_to_add);
    }
    public static AddScore decode(PacketBuffer data){
      return new AddScore(NetworkUtil.readString(data), NetworkUtil.readString(data), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(AddScore message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final ScoreObjective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreateScore(message.player, objective);
          score.increaseScore(message.score_to_add);
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class SubtractScore {
    private final String objective;
    private final String player;
    private final int score_to_subtract;
    public SubtractScore(String objective, String player, int score_to_subtract){
      this.objective = objective;
      this.player = player;
      this.score_to_subtract = score_to_subtract;
    }
    public static void encode(SubtractScore message, PacketBuffer data){
      data.writeString(message.objective);
      data.writeString(message.player);
      data.writeInt(message.score_to_subtract);
    }
    public static SubtractScore decode(PacketBuffer data){
      return new SubtractScore(NetworkUtil.readString(data), NetworkUtil.readString(data), data.readInt());
    }
    @SuppressWarnings("resource")
    public static void handle(SubtractScore message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final ScoreObjective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreateScore(message.player, objective);
          score.increaseScore(-message.score_to_subtract);
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class ResetScore {
    private final String objective;
    private final String player;
    public ResetScore(String objective, String player){
      this.objective = objective;
      this.player = player;
    }
    public static void encode(ResetScore message, PacketBuffer data){
      data.writeString(message.objective);
      data.writeString(message.player);
    }
    public static ResetScore decode(PacketBuffer data){
      return new ResetScore(NetworkUtil.readString(data), NetworkUtil.readString(data));
    }
    @SuppressWarnings("resource")
    public static void handle(ResetScore message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          final ScoreObjective objective = scoreboard.getObjective(message.objective);
          final Score score = scoreboard.getOrCreateScore(message.player, objective);
          score.reset();
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class AddObjective {
    private final String objective_id;
    private final String display_name;
    private final String criteria;
    public AddObjective(String objective_id, String display_name, String criteria){
      this.objective_id = objective_id;
      this.display_name = display_name;
      this.criteria = criteria;
    }
    public static void encode(AddObjective message, PacketBuffer data){
      data.writeString(message.objective_id);
      data.writeString(message.display_name);
      data.writeString(message.criteria);
    }
    public static AddObjective decode(PacketBuffer data){
      return new AddObjective(NetworkUtil.readString(data), NetworkUtil.readString(data), NetworkUtil.readString(data));
    }
    @SuppressWarnings("resource")
    public static void handle(AddObjective message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_objective(scoreboard, player, message.objective_id, message.display_name, message.criteria);
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class EditObjective {
    private final String objective_id;
    private final String display_name;
    private final String criteria;
    public EditObjective(String objective_id, String display_name, String criteria){
      this.objective_id = objective_id;
      this.display_name = display_name;
      this.criteria = criteria;
    }
    public static void encode(EditObjective message, PacketBuffer data){
      data.writeString(message.objective_id);
      data.writeString(message.display_name);
      data.writeString(message.criteria);
    }
    public static EditObjective decode(PacketBuffer data){
      return new EditObjective(NetworkUtil.readString(data), NetworkUtil.readString(data), NetworkUtil.readString(data));
    }
    @SuppressWarnings("resource")
    public static void handle(EditObjective message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_objective(scoreboard, player, message.objective_id, message.display_name, message.criteria);
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class AddTeam {
    private final String team_id;
    private final String display_name;
    private final boolean pvp;
    private final boolean see_invisible_allys;
    private final int team_color;
    private final int nametag_option;
    private final int death_message_option;
    private final String member_prefix;
    private final String member_suffix;
    public AddTeam(String team_id, String display_name, boolean pvp, boolean see_invisible_allys, int team_color,
                   int nametag_option, int death_message_option, String member_prefix, String member_suffix){
      this.team_id = team_id;
      this.display_name = display_name;
      this.pvp = pvp;
      this.see_invisible_allys = see_invisible_allys;
      this.team_color = team_color;
      this.nametag_option = nametag_option;
      this.death_message_option = death_message_option;
      this.member_prefix = member_prefix;
      this.member_suffix = member_suffix;
    }
    public static void encode(AddTeam message, PacketBuffer data){
      data.writeString(message.team_id);
      data.writeString(message.display_name);
      data.writeBoolean(message.pvp);
      data.writeBoolean(message.see_invisible_allys);
      data.writeInt(message.team_color);
      data.writeInt(message.nametag_option);
      data.writeInt(message.death_message_option);
      data.writeString(message.member_prefix);
      data.writeString(message.member_suffix);
    }
    public static AddTeam decode(PacketBuffer data){
      return new AddTeam(
        NetworkUtil.readString(data),
        NetworkUtil.readString(data),
        data.readBoolean(),
        data.readBoolean(),
        data.readInt(),
        data.readInt(),
        data.readInt(),
        NetworkUtil.readString(data),
        NetworkUtil.readString(data)
      );
    }
    @SuppressWarnings("resource")
    public static void handle(AddTeam message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_team(scoreboard, player, message.team_id, message.display_name, message.pvp,
                    message.see_invisible_allys, message.team_color, message.nametag_option,
                    message.death_message_option, message.member_prefix, message.member_suffix);
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  public static final class EditTeam {
    private final String team_id;
    private final String display_name;
    private final boolean pvp;
    private final boolean see_invisible_allys;
    private final int team_color;
    private final int nametag_option;
    private final int death_message_option;
    private final String member_prefix;
    private final String member_suffix;
    public EditTeam(String team_id, String display_name, boolean pvp, boolean see_invisible_allys, int team_color,
                   int nametag_option, int death_message_option, String member_prefix, String member_suffix){
      this.team_id = team_id;
      this.display_name = display_name;
      this.pvp = pvp;
      this.see_invisible_allys = see_invisible_allys;
      this.team_color = team_color;
      this.nametag_option = nametag_option;
      this.death_message_option = death_message_option;
      this.member_prefix = member_prefix;
      this.member_suffix = member_suffix;
    }
    public static void encode(EditTeam message, PacketBuffer data){
      data.writeString(message.team_id);
      data.writeString(message.display_name);
      data.writeBoolean(message.pvp);
      data.writeBoolean(message.see_invisible_allys);
      data.writeInt(message.team_color);
      data.writeInt(message.nametag_option);
      data.writeInt(message.death_message_option);
      data.writeString(message.member_prefix);
      data.writeString(message.member_suffix);
    }
    public static EditTeam decode(PacketBuffer data){
      return new EditTeam(
        NetworkUtil.readString(data),
        NetworkUtil.readString(data),
        data.readBoolean(),
        data.readBoolean(),
        data.readInt(),
        data.readInt(),
        data.readInt(),
        NetworkUtil.readString(data),
        NetworkUtil.readString(data)
      );
    }
    @SuppressWarnings("resource")
    public static void handle(EditTeam message, Supplier<NetworkEvent.Context> context){
      final ServerPlayerEntity player = context.get().getSender();
      if(player != null){
        context.get().enqueueWork(() -> {
          final MinecraftServer server = player.func_71121_q().getServer();
          final Scoreboard scoreboard = server.getScoreboard();
          edit_team(scoreboard, player, message.team_id, message.display_name, message.pvp,
                    message.see_invisible_allys, message.team_color, message.nametag_option,
                    message.death_message_option, message.member_prefix, message.member_suffix);
        });
      }
      context.get().setPacketHandled(true);
    }
  }
  
  private static final void edit_team(final Scoreboard scoreboard, final ServerPlayerEntity player, final String team_name, final String display_name,
                                      final boolean pvp, final boolean see_invisible_allys, final int team_color, final int nametag_option,
                                      final int death_message_option, final String member_prefix, final String member_suffix){
    if(team_name.isEmpty()){
      MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.create_team_failed");
      return;
    }
    ScorePlayerTeam team = scoreboard.getTeam(team_name);
    if(team == null){
      team = scoreboard.createTeam(team_name);
    }
    
    team.setDisplayName(new StringTextComponent(display_name.isEmpty() ? team_name : display_name));
    team.setAllowFriendlyFire(pvp);
    team.setSeeFriendlyInvisiblesEnabled(see_invisible_allys);
    team.setColor(TextFormatting.fromColorIndex(team_color));
    team.setNameTagVisibility(Visible.values()[nametag_option]);
    team.setDeathMessageVisibility(Visible.values()[death_message_option]);
    team.setPrefix(new StringTextComponent(member_prefix));
    team.setSuffix(new StringTextComponent(member_suffix));
    
    // MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.edit_team_success", team_name);
    TeamData.sync();
  }

  private static final void edit_objective(final Scoreboard scoreboard, final ServerPlayerEntity player, final String objective_name, final String display_name, final String criteria_name){
    if(objective_name.isEmpty()){
      MessageUtil.send_to_player(player, "gui.addsynthcore.team_manager.message.create_objective_failed");
      return;
    }
    final ScoreObjective existing_objective = scoreboard.getObjective(objective_name);
    final ScoreCriteria criteria = TeamData.getCriteria(criteria_name);
    if(existing_objective == null){
      scoreboard.addObjective(objective_name, criteria, new StringTextComponent(display_name), criteria.getRenderType());
    }
    else{
      // Objective exists
      if(criteria_name.equals(existing_objective.getCriteria().getName())){
        existing_objective.setDisplayName(new StringTextComponent(display_name));
      }
      else{
        // Can't change criteria. Must delete existing Objective and create a new one.
        scoreboard.removeObjective(existing_objective);
        scoreboard.addObjective(objective_name, criteria, new StringTextComponent(display_name), criteria.getRenderType());
      }
    }
    TeamData.sync();
  }

}
