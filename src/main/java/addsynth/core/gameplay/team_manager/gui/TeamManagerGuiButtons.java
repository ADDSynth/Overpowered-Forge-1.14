package addsynth.core.gameplay.team_manager.gui;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.team_manager.network_messages.TeamManagerCommand;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public final class TeamManagerGuiButtons {

  private static final ResourceLocation gui_widgets = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/gui_textures.png");
  private static final Minecraft minecraft = Minecraft.getInstance();
  public final static int player_button_size = 20;

  public final static int display_slot_button_width = 50;
  public final static int display_slot_button_height = 14;

  public static final class AddTeamButton extends AdjustableButton {
    public AddTeamButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.add"));
    }
    @Override
    public void onPress(){
      minecraft.displayGuiScreen(new TeamManagerTeamEditGui(true));
    }
  }

  public static final class EditTeamButton extends AdjustableButton {
    public EditTeamButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.edit"));
    }
    @Override
    public void onPress(){
      minecraft.displayGuiScreen(new TeamManagerTeamEditGui(false));
    }
  }

  public static final class DeleteTeamButton extends AdjustableButton {
    public DeleteTeamButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.delete"));
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.DeleteTeam(TeamManagerGui.getTeamSelected()));
      TeamManagerGui.unSelectTeam();
    }
  }

  public static final class MovePlayerToTeamButton extends AbstractButton {

    private final int texture_x = 20;
    private final int texture_y = 184;

    public MovePlayerToTeamButton(int xIn, int yIn){
      super(xIn, yIn, player_button_size, player_button_size, "");
    }

    @Override
    public final void renderButton(int mouse_x, int mouse_y, float partial_ticks){
      WidgetUtil.renderButton(this, gui_widgets, texture_x, isHovered ? texture_y + player_button_size : texture_y, player_button_size, player_button_size);
    }

    @Override
    public final void onPress(){
      // ensure team is selected
      // ensure player is not part of a team
      // if(){
        NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.AddPlayerToTeam(TeamManagerGui.getPlayerSelected(), TeamManagerGui.getTeamSelected()));
      // }
    }
  }
  
  public static final class RemovePlayerFromTeamButton extends AbstractButton {

    private final int texture_x = 0;
    private final int texture_y = 184;

    public RemovePlayerFromTeamButton(int xIn, int yIn){
      super(xIn, yIn, player_button_size, player_button_size, "");
    }

    @Override
    public final void renderButton(int mouse_x, int mouse_y, float partial_ticks){
      WidgetUtil.renderButton(this, gui_widgets, texture_x, isHovered ? texture_y + player_button_size : texture_y, player_button_size, player_button_size);
    }

    @Override
    public final void onPress(){
      // if(StringUtil.StringExists(TeamManagerGui.team_selected) && StringUtil.StringExists(TeamManagerGui.player_selected)){
        NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.RemovePlayerFromTeam(TeamManagerGui.getPlayerSelected(), TeamManagerGui.getTeamSelected()));
      // }
    }
  }

  public static final class AddObjectiveButton extends AdjustableButton {
    public AddObjectiveButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.add"));
    }
    @Override
    public void onPress(){
      minecraft.displayGuiScreen(new TeamManagerObjectiveGui(true));
    }
  }

  public static final class EditObjectiveButton extends AdjustableButton {
    public EditObjectiveButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.edit"));
    }
    @Override
    public void onPress(){
      minecraft.displayGuiScreen(new TeamManagerObjectiveGui(false));
    }
  }

  public static final class DeleteObjectiveButton extends AdjustableButton {
    public DeleteObjectiveButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.delete"));
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.DeleteObjective(TeamManagerGui.getObjectiveSelected()));
      TeamManagerGui.unSelectObjective();
    }
  }

  public static final class FinishButton extends AdjustableButton {

    private final Runnable onClick;

    public FinishButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.done"));
      this.onClick = onClick;
    }

    @Override
    public void onPress(){
      onClick.run();
      minecraft.displayGuiScreen(new TeamManagerGui());
    }
  }

  public static final class CancelButton extends AdjustableButton {

    public CancelButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.common.cancel"));
    }

    @Override
    public void onPress(){
      minecraft.displayGuiScreen(new TeamManagerGui());
    }
  }

  public static final class SubtractScoreButton extends AdjustableButton {
  
    private final Runnable onClick;
    
    public SubtractScoreButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.score.subtract"));
      this.onClick = onClick;
    }
    @Override
    public void onPress(){
      onClick.run();
    }
  }

  public static final class AddScoreButton extends AdjustableButton {
  
    private final Runnable onClick;
    
    public AddScoreButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.score.add"));
      this.onClick = onClick;
    }
    @Override
    public void onPress(){
      onClick.run();
    }
  }

  public static final class SetScoreButton extends AdjustableButton {
  
    private final Runnable onClick;
    
    public SetScoreButton(int x, int y, int width, int height, Runnable onClick){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.score.set"));
      this.onClick = onClick;
    }
    @Override
    public void onPress(){
      onClick.run();
    }
  }

  public static final class ResetScoreButton extends AdjustableButton {
  
    public ResetScoreButton(int x, int y, int width, int height){
      super(x, y, width, height, StringUtil.translate("gui.addsynthcore.team_manager.score.reset"));
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(
        new TeamManagerCommand.ResetScore(
          TeamManagerGui.getObjectiveSelected(),
          TeamManagerGui.getPlayerSelected()
        )
      );
    }
  }
  
  public static final class SetDisplaySlotButton extends AdjustableButton {
  
    private final int display_slot;
  
    public SetDisplaySlotButton(int x, int y, int display_slot){
      super(x, y, display_slot_button_width, display_slot_button_height, StringUtil.translate("gui.addsynthcore.team_manager.display_slot.assign"));
      this.display_slot = display_slot;
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.SetDisplaySlot(TeamManagerGui.getObjectiveSelected(), display_slot));
    }
  }
  
  public static final class ClearDisplaySlotButton extends AdjustableButton {
  
    private final int display_slot;
  
    public ClearDisplaySlotButton(int x, int y, int display_slot){
      super(x, y, display_slot_button_width, display_slot_button_height, StringUtil.translate("gui.addsynthcore.team_manager.display_slot.clear"));
      this.display_slot = display_slot;
    }
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new TeamManagerCommand.ClearDisplaySlot(display_slot));
    }
  }

}
