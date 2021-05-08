package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
import addsynth.core.gui.GuiBase;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.scrollbar.ListEntry;
import addsynth.core.gui.widgets.scrollbar.Scrollbar;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public final class TeamManagerObjectiveGui extends GuiBase {

  private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/team_manager_objective_edit.png");

  private final boolean new_objective;

  public TeamManagerObjectiveGui(boolean new_objective){
    super(274, 233, new StringTextComponent("Edit Objective"), GUI_TEXTURE);
    this.new_objective = new_objective;
  }

  private TextFieldWidget objective_id_name;
  private TextFieldWidget objective_display_name;
  private final ListEntry[] objective_entries = new ListEntry[objectives.length];
  private Scrollbar objective_list;
  private static final String[] objectives = {
    "Dummy", "Trigger", "Death Count", "Player Kills", "Total Kills", "Health", "Total XP", "XP Level", "Air", "Food", "Armor"
  };

  private static final int center_space = 3;
  private static final int widget_spacing = 2;
  private static final int line_space = 8;
  private static final int text_box_height = 14;
  private static final int entry_height = 11;
  private static final int scrollbar_width = 12;
  private static final int button_width = 80;
  private static final int button_height = 20;

  // Text
  private static final int left_text_x = 6;
  private int right_text_x;
  private static final int line_1 = 18;
  private static final int line_2 = line_1 + 8 + widget_spacing + text_box_height + line_space;
  private static final int line_3 = line_2 + 8 + widget_spacing; // + (RadialButtonGroup.radial_gui_size*4)+(RadialButtonGroup.line_spacing*3) + line_space;

  @Override
  protected void init(){
    super.init();
    
    right_text_x = guiUtil.center_x + center_space;
    
    // Widgets
    final int left_x     = guiUtil.guiLeft + 6;
    final int left_edge  = guiUtil.guiCenter - center_space;
    final int right_x    = guiUtil.guiCenter + center_space;
    final int right_edge = guiUtil.guiRight - 6;
    final int widget_line_1 = guiUtil.guiTop + line_1 + 8 + widget_spacing;
    final int widget_line_2 = guiUtil.guiTop + line_2 + 8 + widget_spacing;
    final int widget_line_3 = guiUtil.guiTop + line_3 + 8 + widget_spacing;
    final int list_height = entry_height*objective_entries.length;
    final int button_y = Math.max(widget_line_3 + ColorButtons.gui_height + line_space, widget_line_2 + list_height + line_space);
    final int scrollbar_x = left_edge - scrollbar_width;
    final int left_text_box_width  =  left_edge -  left_x;
    final int right_text_box_width = right_edge - right_x;
    final int button_x1 = (( left_x +  left_edge) / 2) - (button_width/2);
    final int button_x2 = ((right_x + right_edge) / 2) - (button_width/2);
    int i;
    
    objective_id_name = new TextFieldWidget(this.font, left_x, widget_line_1, left_text_box_width, text_box_height, "");
    this.children.add(objective_id_name);
    objective_display_name = new TextFieldWidget(this.font, right_x, widget_line_1, right_text_box_width, text_box_height, "");
    this.children.add(objective_display_name);
    for(i = 0; i < objective_entries.length; i++){
      objective_entries[i] = new ListEntry(left_x, widget_line_2 + (entry_height*i), scrollbar_x - left_x, entry_height);
      addButton(objective_entries[i]);
    }
    objective_list = new Scrollbar(scrollbar_x, widget_line_2, list_height, objective_entries, objectives);
    addButton(objective_list);

    addButton(new TeamManagerGuiButtons.FinishButton(button_x1, button_y, button_width, button_height, this));
    addButton(new TeamManagerGuiButtons.CancelButton(button_x2, button_y, button_width, button_height));
  }

  public final void setData(Object something){
  }

  public void create_objective(){
    NetworkHandler.INSTANCE.sendToServer(
      new TeamManagerCommand(
        new_objective ? TeamManagerCommand.ADD_OBJECTIVE : TeamManagerCommand.EDIT_OBJECTIVE,
        objective_id_name.getText().replace(' ', '_'),
        objective_display_name.getText(),
        objective_list.getSelectedIndex()
      )
    );
  }

  @Override
  public void tick(){
    super.tick();
    objective_id_name.tick();
    objective_display_name.tick();
  }

  @Override
  public void render(int mouse_x, int mouse_y, float partialTicks){
    super.render(mouse_x, mouse_y, partialTicks);
    objective_id_name.render(mouse_x, mouse_y, partialTicks);
    objective_display_name.render(mouse_x, mouse_y, partialTicks);
  }

  @Override
  protected void drawGuiBackgroundLayer(float partialTicks, int mouse_x, int mouse_y){
    guiUtil.draw_custom_background_texture(384, 256);
  }

  @Override
  protected void drawGuiForegroundLayer(int mouse_x, int mouse_y){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_left("Objective ID Name:",       left_text_x, line_1);
    GuiUtil.draw_text_left("Objective Display Name:", right_text_x, line_1);
    GuiUtil.draw_text_left("Objective Criteria:",      left_text_x, line_2);
    GuiUtil.draw_text_left("Display Slot:",           right_text_x, line_2);
  }

}
