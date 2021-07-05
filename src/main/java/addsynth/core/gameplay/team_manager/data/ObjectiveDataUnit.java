package addsynth.core.gameplay.team_manager.data;

import addsynth.core.util.network.NetworkUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public final class ObjectiveDataUnit {

  public String name;
  public ITextComponent display_name;
  public int criteria_type;
  public String criteria_name;
  public ScoreCriteria criteria;
  /** If this objective can be modified (NOT readOnly) */
  public boolean modify;
  
  public final void encode(final PacketBuffer data){
    data.writeString(name);
    data.writeString(display_name.getFormattedText());
    data.writeString(criteria.getName());
  }
  
  public static final ObjectiveDataUnit decode(final PacketBuffer data){
    final ObjectiveDataUnit objective = new ObjectiveDataUnit();
    objective.name = NetworkUtil.readString(data);
    objective.display_name = new StringTextComponent(NetworkUtil.readString(data));
    objective.criteria_name = NetworkUtil.readString(data);
    objective.criteria = TeamData.getCriteria(objective.criteria_name);
    objective.criteria_type = TeamData.getCriteriaType(objective.criteria_name);
    objective.modify = !objective.criteria.isReadOnly();
    return objective;
  }

}
