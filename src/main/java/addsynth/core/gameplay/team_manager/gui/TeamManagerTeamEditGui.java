package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.team_manager.TeamData;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
import addsynth.core.gui.GuiBase;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.ClientCheckbox;
import addsynth.core.gui.widgets.buttons.RadialButtonGroup;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public final class TeamManagerTeamEditGui extends GuiBase {

  private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/team_manager_team_edit.png");

  private static final String friendly_fire_text = "Friendly Fire";
  private static final String see_invisible_allys_text = "See Invisible Allys";

  private boolean new_team;
  private TextFieldWidget team_id_name;
  private TextFieldWidget team_display_name;
  private ClientCheckbox friendly_fire;
  private ClientCheckbox see_invisible_allys;
  private ColorButtons color_buttons;
  private RadialButtonGroup nametag_controls;
  private RadialButtonGroup death_message_controls;
  private TextFieldWidget member_prefix;
  private TextFieldWidget member_suffix;

  public TeamManagerTeamEditGui(final boolean new_team){
    super(274, 233, new StringTextComponent("Team Edit"), texture);
    this.new_team = new_team;
  }

  private static final int center_space = 3;
  private static final int widget_spacing = 2;
  private static final int line_space = 8;
  private static final int text_box_height = 14;
  private static final int button_width = 80;
  private static final int button_height = 20;
  private static final String[] nametag_options = {"Always", "Never", "This Team Only", "Other Teams Only"};
  private static final String[] death_message_options = {"Always", "Never", "This Team Only", "Other Teams Only"};

  // Text
  private static final int left_text_x = 6;
  private int right_text_x;
  private static final int line_1 = 18;
  private static final int line_2 = line_1 + 8 + widget_spacing + text_box_height + line_space;
  private static final int line_3 = line_2 + 8 + widget_spacing + (ColorButtons.gui_height) + line_space;
  private static final int line_4 = line_3 + 8 + widget_spacing + (RadialButtonGroup.radial_gui_size*4)+(RadialButtonGroup.line_spacing*3) + line_space;


  @Override
  public final void init(){
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
    final int widget_line_4 = guiUtil.guiTop + line_4 + 8 + widget_spacing;
    final int button_y = widget_line_4 + text_box_height + line_space;
    final int left_text_box_width  =  left_edge -  left_x;
    final int right_text_box_width = right_edge - right_x; // even though technically these should be the same
    final int button_x1 = (( left_x +  left_edge) / 2) - (button_width/2);
    final int button_x2 = ((right_x + right_edge) / 2) - (button_width/2);
    
    team_id_name           = new TextFieldWidget(this.font,  left_x, widget_line_1, left_text_box_width, text_box_height, "");
    team_display_name      = new TextFieldWidget(this.font, right_x, widget_line_1, right_text_box_width, text_box_height, "");
    friendly_fire          = new ClientCheckbox(       left_x, widget_line_2 + 2, friendly_fire_text);
    see_invisible_allys    = new ClientCheckbox(  left_x, widget_line_2 + ColorButtons.button_gui_size + 2, see_invisible_allys_text);
    color_buttons          = new ColorButtons(              right_x, widget_line_2, null);
    nametag_controls       = new RadialButtonGroup(          left_x, widget_line_3, nametag_options);
    death_message_controls = new RadialButtonGroup(         right_x, widget_line_3, death_message_options);
    member_prefix          = new TextFieldWidget(this.font,  left_x, widget_line_4, left_text_box_width, text_box_height, "");
    member_suffix          = new TextFieldWidget(this.font, right_x, widget_line_4, right_text_box_width, text_box_height, "");
    
    addButton(friendly_fire);
    addButton(see_invisible_allys);
    addButton(color_buttons);
    addButton(nametag_controls);
    addButton(death_message_controls);
    this.children.add(team_id_name);
    this.children.add(team_display_name);
    this.children.add(member_prefix);
    this.children.add(member_suffix);
    addButton(new TeamManagerGuiButtons.FinishButton(button_x1, button_y, button_width, button_height, this));
    addButton(new TeamManagerGuiButtons.CancelButton(button_x2, button_y, button_width, button_height));

    if(new_team == false){
      // editing pre-existing team, load all data
      final TeamData.TeamDataUnit team = TeamData.getTeamData(TeamManagerGui.getTeamSelected());
      team_id_name.setText(team.name);
      team_display_name.setText(team.display_name.getFormattedText());
      color_buttons.setColor(team.color);
      friendly_fire.checked = team.pvp;
      see_invisible_allys.checked = team.see_invisible_allys;
      nametag_controls.option_selected = team.nametag_option;
      death_message_controls.option_selected = team.death_message_option;
      member_prefix.setText(team.prefix.getFormattedText());
      member_suffix.setText(team.suffix.getFormattedText());
    }
  }

  public final void create_team(){
    NetworkHandler.INSTANCE.sendToServer(
      new TeamManagerCommand(
        new_team ? TeamManagerCommand.ADD_TEAM : TeamManagerCommand.EDIT_TEAM,
        team_id_name.getText().replace(' ', '_'),
        team_display_name.getText(),
        friendly_fire.checked,
        see_invisible_allys.checked,
        color_buttons.getColor(),
        nametag_controls.getSelectedOption(),
        death_message_controls.getSelectedOption(),
        member_prefix.getText(),
        member_suffix.getText()
      )
    );
  }

  @Override
  public final void tick(){
    super.tick();
    team_id_name.tick();
    team_display_name.tick();
    member_prefix.tick();
    member_suffix.tick();
  }

  @Override
  public final void render(final int mouse_x, final int mouse_y, final float partialTicks){
    super.render(mouse_x, mouse_y, partialTicks);
         team_id_name.render(mouse_x, mouse_y, partialTicks);
    team_display_name.render(mouse_x, mouse_y, partialTicks);
        member_prefix.render(mouse_x, mouse_y, partialTicks);
        member_suffix.render(mouse_x, mouse_y, partialTicks);
  }

  @Override
  protected void drawGuiBackgroundLayer(float partialTicks, int mouse_x, int mouse_y){
    guiUtil.draw_custom_background_texture(384, 256);
  }

  @Override
  protected void drawGuiForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_left("Team ID Name:",         left_text_x, line_1);
    GuiUtil.draw_text_left("Team Display Name:",   right_text_x, line_1);
    GuiUtil.draw_text_left("Team Color:",          right_text_x, line_2);
    GuiUtil.draw_text_left("Show Nametags:",        left_text_x, line_3);
    GuiUtil.draw_text_left("Show Death Messages:", right_text_x, line_3);
    GuiUtil.draw_text_left("Member Prefix:",        left_text_x, line_4);
    GuiUtil.draw_text_left("Member Suffix:",       right_text_x, line_4);
  }

}