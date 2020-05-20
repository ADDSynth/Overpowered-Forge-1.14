package addsynth.overpoweredmod.machines.laser.machine;

import addsynth.core.gui.objects.CheckBox;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.gui.widgets.OnOffSwitch;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.machines.laser.network_messages.SetLaserDistanceMessage;
import addsynth.overpoweredmod.machines.laser.network_messages.ToggleLaserShutoffMessage;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiLaserHousing extends GuiEnergyBase<TileLaserHousing, ContainerLaserHousing> {

  private static final ResourceLocation laser_machine_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/laser_machine.png");

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

  // private int energy_percentage;
  /*
  private static final int energy_x = 151;  DELETE, and delete some stuff below.
  private static final int energy_y = 26;
  private static final int energy_width = 17;
  private static final int energy_height = 40;
  private static final int draw_energy_x = 206;
  private static final int draw_energy_y = 28;
  private final ProgressBar energy_progress_bar = new ProgressBar(energy_x, energy_y, energy_width, energy_height, draw_energy_x, draw_energy_y);
  */

  public GuiLaserHousing(final ContainerLaserHousing container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 104, container, player_inventory, title, laser_machine_gui_texture);
  }

  private static final class ToggleAutoShutoff extends CheckBox {

    private final TileLaserHousing tile;

    public ToggleAutoShutoff(int x, int y, TileLaserHousing tile){
      super(x, y);
      this.tile = tile;
    }

    @Override
    protected boolean get_toggle_state(){
      return tile.getAutoShutoff();
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new ToggleLaserShutoffMessage(tile.getPos()));
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
      setResponder((String text)-> text_field_changed()); // TODO: can be static or instance method, don't want to bother figuring out which one is better right now.
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
          if(captured_distance > 1000){
            captured_distance = 1000;
            setText("1000");
          }
          NetworkHandler.INSTANCE.sendToServer(new SetLaserDistanceMessage(tile.getPos(), captured_distance));
        }
      }
    }

  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch(this.guiLeft + 6, this.guiTop + 17, tile));
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
  public void tick(){
    super.tick();
    if(text_box != null){
      text_box.tick();
    }
  }

  @Override
  public void render(final int mouseX, final int mouseY, final float partialTicks){
    super.render(mouseX, mouseY, partialTicks);
    if(text_box != null){
      text_box.render(mouseX, mouseY, partialTicks);
    }
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    // final float energy_float = tile.getEnergyPercentage();
    // energy_percentage = Math.round(energy_float*100);
    // energy_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,energy_float,ProgressBar.Round.NEAREST);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
    draw_text_left("Auto Shutoff", check_box_x + 12 + space, check_box_y + 2);
    draw_text_left("Lasers: "+tile.number_of_lasers,6,line_1);
    draw_text_left("Distance: ",6,line_2);
    draw_energy_requirements();
    draw_energy_difference(line_5);
  }

  private final void draw_energy_requirements(){
    final String required_energy = Integer.toString((int)(energy.getCapacity()));
    final String word_1 = "Required Energy: "+required_energy;
    final int word_1_width = font.getStringWidth(word_1);
    
    final String current_energy = Integer.toString((int)(energy.getEnergy()));
    final String word_2 = "Current Energy: "+current_energy;
    final int word_2_width = font.getStringWidth(word_2);
    
    if(Math.max(word_1_width, word_2_width) == word_1_width){
      draw_text_left(word_1, 6, line_3);
      draw_text_left("Current Energy:", 6, line_4);
      draw_text_right(current_energy, 6 + word_1_width, line_4);
    }
    else{
      draw_text_left("Required Energy:", 6, line_3);
      draw_text_right(required_energy, 6 + word_2_width, line_3);
      draw_text_left(word_2, 6, line_4);
    }
  }

}
