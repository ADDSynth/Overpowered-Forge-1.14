package addsynth.core.gameplay.team_manager.gui;

import java.util.function.BiConsumer;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.team_manager.TeamData;
import addsynth.core.gameplay.team_manager.network_messages.RequestPlayerScoreMessage;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
import addsynth.core.gui.GuiBase;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.gui.widgets.scrollbar.ListEntry;
import addsynth.core.gui.widgets.scrollbar.Scrollbar;
import addsynth.core.util.StringUtil;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public final class TeamManagerGui extends GuiBase {

  // private static final String[] temp_values = {"I once again call upon", "a temporary array of", "string values", "in order to", "test the scrollbar", "to ensure it works", "as expected in", "all scenarios.", "And just as before,", "the string array must be", "large enough", "to surpass the", "scrollbar's height.", "It is still", "not enough!", "I must keep typing!", "I am also afraid", "that some of", "these strings", "are too long.", "They may", "extend past", "the scrollbar's width."};
  private static final ResourceLocation gui_texture = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/team_manager.png");

  private final String         team_header_text = StringUtil.translate("gui.addsynthcore.team_manager.main.teams");
  private final String  all_players_header_text = StringUtil.translate("gui.addsynthcore.team_manager.main.all_players");
  private final String team_players_header_text = StringUtil.translate("gui.addsynthcore.team_manager.main.players_in_team");
  private final String    objective_header_text = StringUtil.translate("gui.addsynthcore.team_manager.main.objectives");
  private final String       team_selected_text = StringUtil.translate("gui.addsynthcore.team_manager.main.team_selected");
  private final String     player_selected_text = StringUtil.translate("gui.addsynthcore.team_manager.main.player_selected");
  private final String  objective_selected_text = StringUtil.translate("gui.addsynthcore.team_manager.main.objective_selected");
  private final String       current_score_text = StringUtil.translate("gui.addsynthcore.team_manager.score.current_score");
  private final String         input_value_text = StringUtil.translate("gui.addsynthcore.team_manager.score.input");
  private final String display_slot_header_text = StringUtil.translate("gui.addsynthcore.team_manager.display_slot.header");
  private final String      display_slot_text_1 = StringUtil.translate("gui.addsynthcore.team_manager.display_slot.player_list");
  private final String      display_slot_text_2 = StringUtil.translate("gui.addsynthcore.team_manager.display_slot.sidebar");
  private final String      display_slot_text_3 = StringUtil.translate("gui.addsynthcore.team_manager.display_slot.nametag");

  private static String team_selected;
  private static String player_selected;
  private static String objective_selected;
  private static boolean team_is_selected;
  private static boolean player_is_selected;
  private static boolean player_selected_position;
  private static boolean objective_is_selected;

  public static final String getPlayerSelected(){ return player_selected; }
  public static final String getTeamSelected(){ return team_selected; }
  public static final String getObjectiveSelected(){ return objective_selected; }
  public static final void unSelectTeam(){ team_selected = ""; }
  public static final void unSelectObjective(){ objective_selected = ""; }
  // public static final boolean is_team_selected(){ return team_is_selected; }
  // public static final boolean is_player_selected(){ return player_is_selected; }
  // public static final boolean is_objective_selected(){ return objective_is_selected; }

  private static final int        team_list_size = 8;
  private static final int   objective_list_size = 8;
  private static final int      player_list_size = 11;
  // private static final int team_player_list_size = 11;

  private final ListEntry[] team_entries = new ListEntry[team_list_size];
  private Scrollbar team_list;

  private final ListEntry[] all_players = new ListEntry[player_list_size];
  private Scrollbar all_players_list;

  private final ListEntry[] team_players = new ListEntry[player_list_size];
  private Scrollbar team_players_list;

  private final ListEntry[] objectives_entries = new ListEntry[objective_list_size];
  private Scrollbar objectives_list;

  private final BiConsumer<String, Integer> onTeamSelected = (String value, Integer id) -> {
    team_selected = TextFormatting.getTextWithoutFormattingCodes(value);
    updateTeamPlayerList();
  };
  private final BiConsumer<String, Integer> onPlayerSelected = (String value, Integer id) -> {
    player_selected = TextFormatting.getTextWithoutFormattingCodes(value);
    player_selected_position = true;
    team_players_list.setSelected(-1, false);
  };
  private final BiConsumer<String, Integer> onTeamPlayerSelected = (String value, Integer id) -> {
    player_selected = TextFormatting.getTextWithoutFormattingCodes(value);
    player_selected_position = false;
    all_players_list.setSelected(-1, false);
  };
  private static final BiConsumer<String, Integer> onObjectiveSelected = (String value, Integer id) -> {
    objective_selected = TextFormatting.getTextWithoutFormattingCodes(value);
  };

  private TeamManagerGuiButtons.MovePlayerToTeamButton player_to_team_button;
  private TeamManagerGuiButtons.RemovePlayerFromTeamButton player_from_team_button;
  private TeamManagerGuiButtons.DeleteTeamButton delete_team_button;
  private TeamManagerGuiButtons.EditTeamButton edit_team_button;
  private TeamManagerGuiButtons.DeleteObjectiveButton delete_objective_button;
  private TextFieldWidget new_score;
  private TeamManagerGuiButtons.SetScoreButton set_score_button;
  private TeamManagerGuiButtons.AddScoreButton add_score_button;
  private TeamManagerGuiButtons.SubtractScoreButton subtract_score_button;
  private TeamManagerGuiButtons.SetDisplaySlotButton[] display_slot_button = new TeamManagerGuiButtons.SetDisplaySlotButton[3];

  public static int player_score;
  private static boolean player_score_can_be_changed;

  public TeamManagerGui(){
    super(442, 326, new TranslationTextComponent("block.addsynthcore.team_manager"), gui_texture);
    // team_selected = null;
    // player_selected = null;
    // objective_selected = null;
    TeamData.changed = true;
  }

  /** gap between the 3 list sections. */
  private static final int list_spacing = 10;
  private static final int list_width = 119;
  // private static final int player_list_width = 120;
  // private static final int objectives_list_width = 120;
  private static final int scrollbar_width = 12;
  private static final int entry_height = 11;

  private static final int button_y_spacing = 8;
  private static final int button_x_spacing = 4;
  private static final int button_height = 20; // max button height is 20
  private static final int widget_spacing = 2;
  private static final int text_box_width = 80;
  private static final int text_box_height = 14;

  private static final int       team_list_height = entry_height*team_list_size;
  private static final int objectives_list_height = entry_height*objective_list_size;
  private static final int     player_list_height = entry_height*player_list_size;

  private static final int text_space = 11;

  private static final int text_x1 = 12;
  private static final int text_x2 = text_x1 + list_width + scrollbar_width + list_spacing;
  private static final int text_x3 = text_x2 + list_width + scrollbar_width + list_spacing;
  private static final int line_1 = 18;

  private static final int player_buttons_y = line_1 + text_space + player_list_height + button_x_spacing;
  /** Y position all players header text should draw at. */
  private static final int players_text_line = player_buttons_y + TeamManagerGuiButtons.player_button_size + button_x_spacing;
  

  private final int selected_text_left  = text_x2 + GuiUtil.getMaxStringWidth(team_selected_text, player_selected_text, objective_selected_text);
  private final int selected_text_right = selected_text_left + 5;

  private static final int selected_text_y = line_1 + text_space + team_list_height + button_y_spacing + button_height + 8 + button_y_spacing;
  private static final int score_spacing = 6;
  /** Current Score text. */
  private static final int line_2 = selected_text_y + 30 + 7; // 30 is the 3 lines of text, 7 is spacing
  private static final int text_box_x_adjust = 74;
  private static final int text_box_y = line_2 + 8 + score_spacing;
  /** New Score text. */
  private static final int line_3 = WidgetUtil.centerYAdjacent(text_box_y, text_box_height);

  private static final int line_4 = text_box_y + text_box_height + score_spacing + button_height + button_y_spacing;
  private static final int display_slot_spacing = 2;
  private static final int display_slot_button_y1 = line_4 + text_space;
  private static final int display_slot_button_y2 = display_slot_button_y1 + TeamManagerGuiButtons.display_slot_button_height + display_slot_spacing;
  private static final int display_slot_button_y3 = display_slot_button_y2 + TeamManagerGuiButtons.display_slot_button_height + display_slot_spacing;
  private static final int display_slot_text_y1 = WidgetUtil.centerYAdjacent(display_slot_button_y1, TeamManagerGuiButtons.display_slot_button_height);
  private static final int display_slot_text_y2 = WidgetUtil.centerYAdjacent(display_slot_button_y2, TeamManagerGuiButtons.display_slot_button_height);
  private static final int display_slot_text_y3 = WidgetUtil.centerYAdjacent(display_slot_button_y3, TeamManagerGuiButtons.display_slot_button_height);
  private static final int last_x = text_x3 + list_width + scrollbar_width;
  private static final int display_slot_button_x2 = last_x - TeamManagerGuiButtons.display_slot_button_width;
  private static final int display_slot_button_x1 = display_slot_button_x2 - TeamManagerGuiButtons.display_slot_button_width - 6;
  private final int display_slot_x1 = text_x2 + GuiUtil.getMaxStringWidth(display_slot_text_1, display_slot_text_2, display_slot_text_3);
  private final int display_slot_x2 = (display_slot_x1 + 5 + display_slot_button_x1) / 2;
  // private static final int last_y = 

  @Override
  public final void init(){
    super.init();
    
    // Common Data
    int i;
    final int start_y = guiUtil.guiTop + line_1 + text_space;
    final int x_position_1 = guiUtil.guiLeft + text_x1;
    final int x_position_2 = guiUtil.guiLeft + text_x2;
    final int x_position_3 = guiUtil.guiLeft + text_x3;

    // Player Controls
    final int player_x_center = x_position_1 + (list_width / 2);
    final int player_button_x1 = player_x_center - 3 - TeamManagerGuiButtons.player_button_size;
    final int player_button_x2 = player_x_center + 3;
    final int player_list_y = guiUtil.guiTop + players_text_line + text_space;
    for(i = 0; i < team_players.length; i++){
      team_players[i] = new ListEntry(x_position_1, start_y + (entry_height * i), list_width, entry_height);
      addButton(team_players[i]);
    }
    team_players_list = new Scrollbar(x_position_1 + list_width, start_y, player_list_height, team_players);
    team_players_list.setResponder(onTeamPlayerSelected);
    addButton(team_players_list);
    
    player_to_team_button = new TeamManagerGuiButtons.MovePlayerToTeamButton(    player_button_x1, guiUtil.guiTop + player_buttons_y);
    player_from_team_button = new TeamManagerGuiButtons.RemovePlayerFromTeamButton(player_button_x2, guiUtil.guiTop + player_buttons_y);
    addButton(player_to_team_button);
    addButton(player_from_team_button);
    
    for(i = 0; i < all_players.length; i++){
      all_players[i] = new ListEntry(x_position_1, player_list_y + (entry_height * i), list_width, entry_height);
      addButton(all_players[i]);
    }
    all_players_list = new Scrollbar(x_position_1 + list_width, player_list_y, player_list_height, all_players);
    all_players_list.setResponder(onPlayerSelected);
    addButton(all_players_list);
    
    // Team controls
    final int team_buttons_y = start_y + team_list_height + button_y_spacing;
    for(i = 0; i < team_entries.length; i++){
      team_entries[i] = new ListEntry(x_position_2, start_y + (entry_height * i), list_width, entry_height);
      addButton(team_entries[i]);
    }
    team_list = new Scrollbar(x_position_2 + list_width, start_y, team_list_height, team_entries);
    team_list.setResponder(onTeamSelected);
    addButton(team_list);
      edit_team_button = new TeamManagerGuiButtons.EditTeamButton(  x_position_2 + 34, team_buttons_y, 30, button_height);
    delete_team_button = new TeamManagerGuiButtons.DeleteTeamButton(x_position_2 + 68, team_buttons_y, 50, button_height);
    addButton(new TeamManagerGuiButtons.AddTeamButton(   x_position_2,      team_buttons_y, 30, button_height));
    addButton(edit_team_button);
    addButton(delete_team_button);

    // Objectives
    final int objective_buttons_y = start_y + objectives_list_height + button_y_spacing;
    for(i = 0; i < objectives_entries.length; i++){
      objectives_entries[i] = new ListEntry(x_position_3, start_y + (entry_height * i), list_width, entry_height);
      addButton(objectives_entries[i]);
    }
    objectives_list = new Scrollbar(x_position_3 + list_width, start_y, objectives_list_height, objectives_entries);
    objectives_list.setResponder(onObjectiveSelected);
    addButton(objectives_list);
    delete_objective_button = new TeamManagerGuiButtons.DeleteObjectiveButton(x_position_3 + 68, objective_buttons_y, 50, button_height);
    addButton(new TeamManagerGuiButtons.AddObjectiveButton(   x_position_3,      objective_buttons_y, 30, button_height));
    // addButton(new TeamManagerGuiButtons.EditObjectiveButton(  x_position_3 + 34, objective_buttons_y, 30, button_height));
    addButton(delete_objective_button);
    
    // Score Controls
    new_score = new TextFieldWidget(this.font, x_position_2 + text_box_x_adjust, guiUtil.guiTop + text_box_y, text_box_width, text_box_height, "0");
    this.children.add(new_score);
    final int score_buttons_y = guiUtil.guiTop + text_box_y + text_box_height + score_spacing;
    final int[] score_buttons_width = {70, 50, 75, 60};
    final int score_button_x2 =    x_position_2 + score_buttons_width[0] + button_x_spacing;
    final int score_button_x3 = score_button_x2 + score_buttons_width[1] + button_x_spacing;
    subtract_score_button = new TeamManagerGuiButtons.SubtractScoreButton(x_position_2, score_buttons_y, score_buttons_width[0], button_height, this);
         add_score_button = new TeamManagerGuiButtons.AddScoreButton(  score_button_x2, score_buttons_y, score_buttons_width[1], button_height, this);
         set_score_button = new TeamManagerGuiButtons.SetScoreButton(  score_button_x3, score_buttons_y, score_buttons_width[2], button_height, this);
    addButton(subtract_score_button);
    addButton(add_score_button);
    addButton(set_score_button);
    
    // Display Slot widgets
    display_slot_button[0] = new TeamManagerGuiButtons.SetDisplaySlotButton(guiUtil.guiLeft + display_slot_button_x1, guiUtil.guiTop + display_slot_button_y1, 0);
    display_slot_button[1] = new TeamManagerGuiButtons.SetDisplaySlotButton(guiUtil.guiLeft + display_slot_button_x1, guiUtil.guiTop + display_slot_button_y2, 1);
    display_slot_button[2] = new TeamManagerGuiButtons.SetDisplaySlotButton(guiUtil.guiLeft + display_slot_button_x1, guiUtil.guiTop + display_slot_button_y3, 2);
    addButton(display_slot_button[0]);
    addButton(display_slot_button[1]);
    addButton(display_slot_button[2]);
    addButton(new TeamManagerGuiButtons.ClearDisplaySlotButton(guiUtil.guiLeft + display_slot_button_x2, guiUtil.guiTop + display_slot_button_y1, 0));
    addButton(new TeamManagerGuiButtons.ClearDisplaySlotButton(guiUtil.guiLeft + display_slot_button_x2, guiUtil.guiTop + display_slot_button_y2, 1));
    addButton(new TeamManagerGuiButtons.ClearDisplaySlotButton(guiUtil.guiLeft + display_slot_button_x2, guiUtil.guiTop + display_slot_button_y3, 2));
    
    TeamData.changed = true;
  }

  @Override
  public void tick(){
    super.tick();
    team_is_selected = StringUtil.StringExists(team_selected);
    player_is_selected = StringUtil.StringExists(player_selected);
    objective_is_selected = StringUtil.StringExists(objective_selected);
    player_score_can_be_changed = objective_is_selected && player_is_selected && TeamData.canObjectiveBeModified(objective_selected);
    updateButtons();
    updateLists();
    if(player_is_selected && objective_is_selected){
      NetworkHandler.INSTANCE.sendToServer(new RequestPlayerScoreMessage(player_selected, objective_selected));
    }
    new_score.tick();
  }

  private final void updateButtons(){
    // TODO: It's nice that I set the active variable, but these buttons use WidgetUtil.renderButton, which doesn't
    //       take that into account. Also, the texture doesn't have any darkened buttons to draw either.
    player_to_team_button.active = player_is_selected && player_selected_position && team_is_selected;
    player_from_team_button.active = player_is_selected && !player_selected_position && team_is_selected;
    edit_team_button.active = team_is_selected;
    delete_team_button.active = team_is_selected;
    delete_objective_button.active = objective_is_selected;
    set_score_button.active = player_score_can_be_changed;
    add_score_button.active = player_score_can_be_changed;
    subtract_score_button.active = player_score_can_be_changed;
    display_slot_button[0].active = objective_is_selected;
    display_slot_button[1].active = objective_is_selected;
    display_slot_button[2].active = objective_is_selected;
  }

  private final void updateLists(){
    if(TeamData.changed){
      team_list.updateScrollbar(TeamData.getTeams());
      updateTeamPlayerList();
      all_players_list.updateScrollbar(TeamData.getPlayers());
      objectives_list.updateScrollbar(TeamData.getObjectives());
      TeamData.changed = false;
    }
  }

  private final void updateTeamPlayerList(){
    team_players_list.updateScrollbar(TeamData.getTeamPlayers(team_selected));
  }

  public final void change_score(final int command){
    try{
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand(command, player_selected, objective_selected, Integer.parseInt(new_score.getText())));
    }
    catch(NumberFormatException e){}
  }

  @Override
  public void render(int mouse_x, int mouse_y, float partialTicks){
    super.render(mouse_x, mouse_y, partialTicks);
    new_score.render(mouse_x, mouse_y, partialTicks);
  }

  @Override
  protected final void drawGuiBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_custom_background_texture(512, 384);
  }

  @Override
  protected final void drawGuiForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
    // Main Labels
    GuiUtil.draw_text_left(team_players_header_text+":", text_x1, line_1);
    GuiUtil.draw_text_left( all_players_header_text+":", text_x1, players_text_line);
    GuiUtil.draw_text_left(        team_header_text+":", text_x2, line_1);
    GuiUtil.draw_text_left(   objective_header_text+":", text_x3, line_1);
    // Selected
    GuiUtil.draw_text_right(     team_selected_text+":", selected_text_left, selected_text_y);
    GuiUtil.draw_text_right(   player_selected_text+":", selected_text_left, selected_text_y + 10);
    GuiUtil.draw_text_right(objective_selected_text+":", selected_text_left, selected_text_y + 20);
    GuiUtil.draw_text_left(     team_selected != null ? team_selected      : "", selected_text_right, selected_text_y);
    GuiUtil.draw_text_left(   player_selected != null ? player_selected    : "", selected_text_right, selected_text_y + 10);
    GuiUtil.draw_text_left(objective_selected != null ? objective_selected : "", selected_text_right, selected_text_y + 20);
    // Score Text
    GuiUtil.draw_text_left(current_score_text+": "+player_score, text_x2, line_2);
    GuiUtil.draw_text_left(input_value_text+":", text_x2, line_3);
    // Display Slot text
    GuiUtil.draw_text_left(display_slot_header_text+":", text_x2, line_4);
    GuiUtil.draw_text_right(display_slot_text_1, display_slot_x1, display_slot_text_y1);
    GuiUtil.draw_text_right(display_slot_text_2, display_slot_x1, display_slot_text_y2);
    GuiUtil.draw_text_right(display_slot_text_3, display_slot_x1, display_slot_text_y3);
    GuiUtil.draw_text_center(TeamData.getDisplaySlotObjective(0), display_slot_x2, display_slot_text_y1);
    GuiUtil.draw_text_center(TeamData.getDisplaySlotObjective(1), display_slot_x2, display_slot_text_y2);
    GuiUtil.draw_text_center(TeamData.getDisplaySlotObjective(2), display_slot_x2, display_slot_text_y3);
  }

}
