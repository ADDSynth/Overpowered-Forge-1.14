package addsynth.core.gameplay.team_manager.data;

import java.util.ArrayList;
import addsynth.core.util.network.NetworkUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public final class TeamDataUnit {

  public String name;
  public ITextComponent display_name;
  public int color;
  public ITextComponent prefix;
  public ITextComponent suffix;
  public boolean pvp;
  public boolean see_invisible_allys;
  public int nametag_option;
  public int death_message_option;
  public ArrayList<ITextComponent> players = new ArrayList<ITextComponent>();
  
  public final boolean matches(final Team team){
    return name.equals(team.getName());
  }
  
  public final void encode(final PacketBuffer data){
    data.writeString(name);
    data.writeString(display_name.getFormattedText());
    data.writeByte(color);
    data.writeBoolean(pvp);
    data.writeBoolean(see_invisible_allys);
    data.writeByte(nametag_option);
    data.writeByte(death_message_option);
    data.writeString(prefix.getFormattedText());
    data.writeString(suffix.getFormattedText());
    int i;
    final int length = players.size();
    final StringTextComponent[] player_names = new StringTextComponent[length];
    for(i = 0; i < length; i++){
      player_names[i] = (StringTextComponent)players.get(i);
    }
    NetworkUtil.writeTextComponentArray(data, player_names);
  }
  
  public static final TeamDataUnit decode(final PacketBuffer data){
    final TeamDataUnit team = new TeamDataUnit();
    team.name = NetworkUtil.readString(data);
    team.display_name = new StringTextComponent(NetworkUtil.readString(data));
    team.color = data.readByte();
    team.pvp = data.readBoolean();
    team.see_invisible_allys = data.readBoolean();
    team.nametag_option = data.readByte();
    team.death_message_option = data.readByte();
    team.prefix = new StringTextComponent(NetworkUtil.readString(data));
    team.suffix = new StringTextComponent(NetworkUtil.readString(data));
    team.players = new ArrayList<ITextComponent>();
    for(final ITextComponent t : NetworkUtil.readTextComponentArray(data)){
      team.players.add(t);
    }
    return team;
  }

}
