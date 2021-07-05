package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.team_manager.CriteriaData;
import addsynth.core.gameplay.team_manager.CriteriaType;
import addsynth.core.gameplay.team_manager.TeamData;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
import addsynth.core.gui.GuiBase;
import addsynth.core.gui.util.GuiSection;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.gui.widgets.buttons.RadialButtonGroup;
import addsynth.core.gui.widgets.scrollbar.ListEntry;
import addsynth.core.gui.widgets.scrollbar.Scrollbar;
import addsynth.core.util.StringUtil;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public final class TeamManagerObjectiveGui extends GuiBase {

  private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/team_manager_objective_edit.png");

  private final boolean new_objective;

  public TeamManagerObjectiveGui(boolean new_objective){
    super(473, 314, new TranslationTextComponent("gui.addsynthcore.team_manager.objective_edit.gui_title"), GUI_TEXTURE);
    this.new_objective = new_objective;
  }

  private final String      objective_id_name_text = StringUtil.translate("gui.addsynthcore.team_manager.objective_edit.id_name");
  private final String objective_display_name_text = StringUtil.translate("gui.addsynthcore.team_manager.objective_edit.display_name");
  private final String          criteria_type_text = StringUtil.translate("gui.addsynthcore.team_manager.objective_edit.type");
  private final String        criteria_header_text = StringUtil.translate("gui.addsynthcore.team_manager.objective_edit.criteria_header");
  private final String[] criteria_options = {
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.standard"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.team_kill"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.killed_by_team"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.statistic"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.block_mined"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.item_broken"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.item_crafted"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.item_used"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.item_picked_up"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.item_dropped"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.killed"),
    StringUtil.translate("gui.addsynthcore.team_manager.criteria_type.killed_by")
  };

  private TextFieldWidget objective_id_name;
  private TextFieldWidget objective_display_name;
  private RadialButtonGroup criteria_types;
  private Scrollbar criteria_list;
  private TeamManagerGuiButtons.FinishButton finish_button;

  private static final int widget_spacing = 2;
  private static final int line_space = 8;
  private static final int text_box_height = 14;
  private static final int entry_height = 11;
  private static final int scrollbar_width = 12;
  private static final int button_width = 80;
  private static final int button_height = 20;

  // Text
  private static final int line_1 = 18;
  private static final int line_2 = line_1 + 8 + widget_spacing + text_box_height + line_space;

  // Widgets
  private static final int left_section_width = 120;
  private static final int middle_section_width = 100;
  private final int widget_line_1 = guiUtil.guiTop + line_1 + 8 + widget_spacing;
  private final int widget_line_2 = guiUtil.guiTop + line_2 + 8 + widget_spacing;
  private final GuiSection   left_section = GuiSection.box(     guiUtil.guiLeft + 6, widget_line_1, guiUtil.guiLeft    + 6 + left_section_width,   guiUtil.guiBottom - 6);
  private final GuiSection middle_section = GuiSection.box(  left_section.right + 6, widget_line_1, left_section.right + 6 + middle_section_width, guiUtil.guiBottom - 6);
  private final GuiSection  right_section = GuiSection.box(middle_section.right + 6, widget_line_1, guiUtil.guiRight   - 6 - scrollbar_width,      guiUtil.guiBottom - 6);

  // Criteria List
  private final int criteria_list_length = right_section.height / entry_height;
  private final int list_height = entry_height*criteria_list_length;
  private final ListEntry[] objective_entries = new ListEntry[criteria_list_length];

  @Override
  protected void init(){
    super.init();
    
    // Name TextBoxes
    objective_id_name = new TextFieldWidget(this.font, left_section.x, left_section.y, left_section_width, text_box_height, "");
    this.children.add(objective_id_name);
    objective_display_name = new TextFieldWidget(this.font, left_section.x, widget_line_2, left_section_width, text_box_height, "");
    this.children.add(objective_display_name);

    // Criteria Type Selection
    criteria_types = new RadialButtonGroup(middle_section.x, left_section.y, criteria_options, this::setCriteriaList);
    addButton(criteria_types);

    // Criteria List
    // final int scrollbar_x = right_section.right; right edge already has scrollbar width factored in
    int i;
    for(i = 0; i < criteria_list_length; i++){
      objective_entries[i] = new ListEntry(right_section.x, right_section.y + (entry_height*i), right_section.width, entry_height);
      addButton(objective_entries[i]);
    }
    criteria_list = new Scrollbar(right_section.right, widget_line_1, list_height, objective_entries);
    addButton(criteria_list);

    // Done and Cancel Buttons
    final int button_area = middle_section.right - left_section.left;
    final int[] button_x = WidgetUtil.evenAlignment(button_area, button_width, 2);
    final int button_y = guiUtil.guiBottom - 6 - button_height;
    finish_button = new TeamManagerGuiButtons.FinishButton(guiUtil.guiLeft + button_x[0], button_y, button_width, button_height, this::create_objective);
    addButton(finish_button);
    addButton(new TeamManagerGuiButtons.CancelButton(guiUtil.guiLeft + button_x[1], button_y, button_width, button_height));
    
    if(new_objective){
      // Initialize Criteria List
      setCriteriaList(CriteriaType.STANDARD);
    }
    else{
      // editting existing Objective, load all data
      final TeamData.ObjectiveDataUnit objective_data = TeamData.getObjectiveData(TeamManagerGui.getObjectiveSelected());
      objective_id_name.setText(objective_data.name);
      objective_display_name.setText(objective_data.display_name.getFormattedText());
      criteria_types.option_selected = objective_data.criteria_type;
      setCriteria(objective_data.criteria_type, objective_data.criteria_name);
    }
  }

  /** Initializes the criteria list whenever the player changes the criteria type. */
  private final void setCriteriaList(int type){
    criteria_list.setSelected(-1, false, false);
    switch(type){
    case CriteriaType.STANDARD:
      criteria_list.updateScrollbar(CriteriaData.getStandardCriteria());
      break;
    case CriteriaType.TEAM_KILL:
      criteria_list.updateScrollbar(CriteriaData.getColors());
      break;
    case CriteriaType.KILLED_BY_TEAM:
      criteria_list.updateScrollbar(CriteriaData.getColors());
      break;
    case CriteriaType.STATISTICS:
      criteria_list.updateScrollbar(CriteriaData.getStatistics());
      break;
    case CriteriaType.BLOCK_MINED:
      criteria_list.updateScrollbar(CriteriaData.getAllBlocks());
      break;
    case CriteriaType.ITEM_BROKEN:
      criteria_list.updateScrollbar(CriteriaData.getItemsWithDurability());
      break;
    case CriteriaType.ITEM_CRAFTED:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.ITEM_USED:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.ITEM_PICKED_UP:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.ITEM_DROPPED:
      criteria_list.updateScrollbar(CriteriaData.getAllItems());
      break;
    case CriteriaType.KILLED:
      criteria_list.updateScrollbar(CriteriaData.getEntities());
      break;
    case CriteriaType.KILLED_BY:
      criteria_list.updateScrollbar(CriteriaData.getEntities());
      break;
    }
  }

  public final void create_objective(){
    final String objective_id = objective_id_name.getText().replace(' ', '_');
    final String display_name = objective_display_name.getText();
    final String criteria     = getCriteriaID();
    if(new_objective){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.AddObjective(objective_id, display_name, criteria));
    }
    else{
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.EditObjective(objective_id, display_name, criteria));
    }
  }

  /** Gets the Criteria ID given the type selected and the selected List Entry in the Criteria List. */
  private final String getCriteriaID(){
    String criteria = null;
    switch(criteria_types.option_selected){
    case CriteriaType.STANDARD:
      criteria = TeamData.getStandardCriteria(criteria_list.getSelectedIndex());
      break;
    case CriteriaType.TEAM_KILL:
      criteria = CriteriaType.TEAM_KILL_PREFIX + criteria_list.getSelected();
      break;
    case CriteriaType.KILLED_BY_TEAM:
      criteria = CriteriaType.KILLED_BY_TEAM_PREFIX + criteria_list.getSelected();
      break;
    case CriteriaType.STATISTICS:
      criteria = CriteriaType.STATISTICS_PREFIX + CriteriaData.getStatisticID(criteria_list.getSelectedIndex()).replace(':', '.');
      break;
    case CriteriaType.BLOCK_MINED:
      criteria = CriteriaType.BLOCK_MINED_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    case CriteriaType.ITEM_BROKEN:
      criteria = CriteriaType.ITEM_BROKEN_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    case CriteriaType.ITEM_CRAFTED:
      criteria = CriteriaType.ITEM_CRAFTED_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    case CriteriaType.ITEM_DROPPED:
      criteria = CriteriaType.ITEM_DROPPED_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    case CriteriaType.ITEM_PICKED_UP:
      criteria = CriteriaType.ITEM_PICKED_UP_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    case CriteriaType.ITEM_USED:
      criteria = CriteriaType.ITEM_USED_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    case CriteriaType.KILLED:
      criteria = CriteriaType.KILLED_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    case CriteriaType.KILLED_BY:
      criteria = CriteriaType.KILLED_BY_PREFIX + criteria_list.getSelected().replace(':', '.');
      break;
    }
    return criteria;
  }

  /** Adjusts the Criteria List to select the criteria. */
  private final void setCriteria(int type, String criteria){
    setCriteriaList(type);
    try{
      switch(type){
      case CriteriaType.STANDARD:
        criteria_list.setSelected(TeamData.getStandardCriteriaIndex(criteria));
        break;
      case CriteriaType.TEAM_KILL: case CriteriaType.KILLED_BY_TEAM:
        criteria_list.setSelected(criteria.substring(criteria.indexOf('.')+1));
        break;
      case CriteriaType.STATISTICS:
        criteria_list.setSelected(CriteriaData.getStatisticIndex(criteria.substring(criteria.indexOf(':')+1).replace('.', ':')));
        break;
      default:
        criteria_list.setSelected(criteria.substring(criteria.indexOf(':')+1).replace('.', ':'));
        break;
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void tick(){
    super.tick();
    objective_id_name.tick();
    objective_display_name.tick();
    finish_button.active = criteria_list.hasValidSelection();
  }

  @Override
  public void render(int mouse_x, int mouse_y, float partialTicks){
    super.render(mouse_x, mouse_y, partialTicks);
    objective_id_name.render(mouse_x, mouse_y, partialTicks);
    objective_display_name.render(mouse_x, mouse_y, partialTicks);
  }

  @Override
  protected void drawGuiBackgroundLayer(float partialTicks, int mouse_x, int mouse_y){
    guiUtil.draw_custom_background_texture(512, 384);
  }

  @Override
  protected void drawGuiForegroundLayer(int mouse_x, int mouse_y){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_left(     objective_id_name_text+":", 6, line_1);
    GuiUtil.draw_text_left(objective_display_name_text+":", 6, line_2);
    GuiUtil.draw_text_left(         criteria_type_text+":", middle_section.left - guiUtil.guiLeft, line_1);
    GuiUtil.draw_text_left(       criteria_header_text+":",  right_section.left - guiUtil.guiLeft, line_1);
  }

}
