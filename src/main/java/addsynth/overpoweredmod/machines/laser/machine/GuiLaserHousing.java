package addsynth.overpoweredmod.machines.laser.machine;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.Checkbox;
import addsynth.core.util.StringUtil;
import addsynth.energy.api.gui.GuiEnergyBase;
import addsynth.energy.api.gui.widgets.OnOffSwitch;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.ToggleAutoShutoffMessage;
import addsynth.overpoweredmod.machines.laser.network_messages.SetLaserDistanceMessage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiLaserHousing extends GuiEnergyBase<TileLaserHousing, ContainerLaserHousing> {

  // TODO: The Laser machine also doesn't have any item slots in its inventory. It can derive from a Non-container
  //       GuiEnergyBase, but that means that I need to extract all the common helper functions from GuiEnergyBase
  //       into a GuiEnergyUtil. I can have that extend GuiUtil, then pass that as a reference through the Gui constructors.

  private static final ResourceLocation laser_machine_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/laser_machine.png");

  private final String required_energy_text = StringUtil.translate("gui.overpowered.laser_housing.required_energy");
  private final String current_energy_text  = StringUtil.translate("gui.overpowered.laser_housing.current_energy");
  private final String lasers_text          = StringUtil.translate("gui.overpowered.laser_housing.lasers");
  private final String distance_text        = StringUtil.translate("gui.overpowered.laser_housing.distance");

  private TextFieldWidget text_box;

  private static final int space = 4;

  private static final int line_1 = 36;
  private static final int text_box_width = 50;
  private static final int text_box_height = 14;
  private static final int text_box_x = 60; // 6 + fontRendererObj.getStringWidth("Distance") + space
  private static final int text_box_y = line_1 + 8 + space;
  private static final int line_2 = text_box_y + 3;

  private static final int line_3 = text_box_y + text_box_height + space;
  private static final int line_4 = line_3 + 8 + space;
  private static final int line_5 = line_4 + 8 + space;

  private static final int check_box_x = 70;
  private static final int check_box_y = 19;

  public GuiLaserHousing(final ContainerLaserHousing container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 104, container, player_inventory, title, laser_machine_gui_texture);
  }

  private static final class ToggleAutoShutoff extends Checkbox {

    private final TileLaserHousing tile;

    public ToggleAutoShutoff(int x, int y, TileLaserHousing tile){
      super(x, y, StringUtil.translate("gui.addsynth_energy.common.auto_shutoff"));
      this.tile = tile;
    }

    @Override
    protected boolean get_toggle_state(){
      return tile.getAutoShutoff();
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new ToggleAutoShutoffMessage(tile.getPos()));
    }
  }

  private static final class LaserDistanceTextField extends TextFieldWidget {

    private final TileLaserHousing tile;

    public LaserDistanceTextField(FontRenderer fontIn, int x, int y, int width, int height, TileLaserHousing tile){
      super(fontIn, x, y, width, height, "");
      this.tile = tile;
      final String initial_distance = Integer.toString(tile.getLaserDistance());
      setText(initial_distance);
      setMaxStringLength(4); // FEATURE: add a numbers-only textbox to ADDSynthCore.
      setEnableBackgroundDrawing(true);
      setVisible(true);
      setTextColor(16777215);
      setResponder((String text) -> text_field_changed()); // TODO: can be static or instance method, don't want to bother figuring out which one is better right now.
    }

    private final void text_field_changed(){
      int captured_distance = 0;
      try{
        captured_distance = Integer.parseUnsignedInt(getText());
      }
      catch(NumberFormatException e){
        captured_distance = -1;
      }
      if(captured_distance >= 0){
        if(captured_distance != tile.getLaserDistance()){
          if(captured_distance > LaserNetwork.max_laser_distance){
            captured_distance = LaserNetwork.max_laser_distance;
            setText(Integer.toString(LaserNetwork.max_laser_distance));
          }
          NetworkHandler.INSTANCE.sendToServer(new SetLaserDistanceMessage(tile.getPos(), captured_distance));
        }
      }
    }

  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch<>(this.guiLeft + 6, this.guiTop + 17, tile)); // OPTIMIZE: On/Off switch position should be standardized.
    addButton(new ToggleAutoShutoff(this.guiLeft + check_box_x, this.guiTop + check_box_y, tile));
    
    this.text_box = new LaserDistanceTextField(this.font,this.guiLeft + text_box_x,this.guiTop + text_box_y,text_box_width,text_box_height, tile);
    this.children.add(text_box);
  }

  // NOTE: The only thing that doesn't sync with the client is when 2 people have the gui open
  //   and one of them changes the Laser Distance. The energy requirements sucessfully updates,
  //   but not the Laser Distance text field of the other player. Here is my solution, but it looked
  //   too weird and I didn't feel it was absolutely necessary to keep things THAT much in-sync.
  // final int captured_distance = get_laser_distance();
  // if(captured_distance >= 0){
  //   if(captured_distance != tile.getLaserDistance()){
  //     distance_text_field.setText(Integer.toString(tile.getLaserDistance()));
  //   }
  // }

  @Override
  public final void tick(){
    super.tick();
    if(text_box != null){
      text_box.tick();
    }
  }

  @Override
  public final void render(final int mouseX, final int mouseY, final float partialTicks){
    super.render(mouseX, mouseY, partialTicks);
    if(text_box != null){
      text_box.render(mouseX, mouseY, partialTicks);
    }
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_left(lasers_text+": "+tile.number_of_lasers, 6, line_1);
    GuiUtil.draw_text_left(distance_text+": ", 6, line_2);
    draw_energy_requirements();
    draw_energy_difference(line_5);
  }

  private final void draw_energy_requirements(){
    final String required_energy = Integer.toString((int)(energy.getCapacity()));
    final String word_1 = required_energy_text+": "+required_energy;
    final int word_1_width = font.getStringWidth(word_1);
    
    final String current_energy = Integer.toString((int)(energy.getEnergy()));
    final String word_2 = current_energy_text+": "+current_energy;
    final int word_2_width = font.getStringWidth(word_2);
    
    if(Math.max(word_1_width, word_2_width) == word_1_width){
      GuiUtil.draw_text_left(word_1, 6, line_3);
      GuiUtil.draw_text_left(current_energy_text+":", 6, line_4);
      GuiUtil.draw_text_right(current_energy, 6 + word_1_width, line_4);
    }
    else{
      GuiUtil.draw_text_left(required_energy_text+":", 6, line_3);
      GuiUtil.draw_text_right(required_energy, 6 + word_2_width, line_3);
      GuiUtil.draw_text_left(word_2, 6, line_4);
    }
  }

}
