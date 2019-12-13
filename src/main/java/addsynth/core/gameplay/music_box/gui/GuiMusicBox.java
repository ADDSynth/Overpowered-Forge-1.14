package addsynth.core.gameplay.music_box.gui;

import java.io.IOException;
import org.lwjgl.glfw.GLFW;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.music_box.MusicGrid;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.music_box.network_messages.MusicBoxMessage;
import addsynth.core.gameplay.music_box.network_messages.NoteMessage;
import addsynth.core.gui.GuiBase;
import addsynth.core.gui.objects.AdjustableButton;
import addsynth.core.inventory.container.BaseContainer;
import addsynth.overpoweredmod.containers.ContainerCompressor;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiMusicBox extends GuiBase<ContainerMusicBox> {

  private final TileMusicBox tile;

  private static final int gui_width = 243;
  private static final int gui_height = 203;
  
  // variables
  private static byte note_selected;
  private static final String[] note = new String[] {                                "F#3","G3","G#3","A3","A#3","B3",
                                                     "C4","C#4","D4","D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4",
                                                     "C5","C#5","D5","D#5","E5","F5","F#5"};
  private byte ticks;
  private int bpm;
  private static final String[] face = new String[] {"Down", "Up", "North", "South", "West", "East"};

  // controls
  private static final int play_button_width = 46;
  private static AdjustableButton play_button;
  
  private static final int tempo_button_width = 10;
  private static final int tempo_button_height = 16;
  private static final int tempo_text_width = 50;
  private static final int tempo_text_height = 18;
  private static final int tempo_text_x_center = 6 + tempo_button_width + Math.round(tempo_text_width / 2);
  private static final int tempo_text_y_center = 17 + 9;
  private static final int tempo_button_y = tempo_text_y_center - Math.round(tempo_button_height / 2);
  private static AdjustableButton tempo_lower_button;
  private static AdjustableButton tempo_higher_button;
  
  private static final int next_direction_button_width = 50;
  private static AdjustableButton next_direction_button;

  private static final int info_text_y = 17 + tempo_text_height + 3;

  private static final int music_grid_x = 34;
  private static final int music_grid_y = 56;
  public static final int note_button_width = 25;
  public static final int note_button_height = 13;
  
  public static final int mute_button_x = music_grid_x - 27;
  public static final int mute_button_y = music_grid_y + 1;
  public static final int mute_button_size = 12;

  public static final int instrument_button_x = music_grid_x - 13;
  public static final int instrument_button_y = music_grid_y + 1;
  public static final int instrument_button_width = 12;
  public static final int instrument_button_height = 12;

  private static final ResourceLocation playhead_texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");
  private static final int playhead_x = music_grid_x + Math.round((float)note_button_width / 2) - 8;
  private static final int playhead_y = music_grid_y - 8;
  private static final int playhead_texture_x = 64;
  private static final int playhead_texture_y = 24;

  public GuiMusicBox(final ContainerMusicBox container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/music_box.png"));
    this.tile = container.getTileEntity();
    this.xSize = gui_width;
    this.ySize = gui_height;
  }

  @Override
  public final void init(){
    super.init();

    // button setup
    play_button           = new AdjustableButton(this.guiLeft + (xSize / 2) - (play_button_width / 2), this.guiTop + 17, play_button_width, 14, "Play");
    tempo_lower_button    = new AdjustableButton(this.guiLeft + 6,                                         this.guiTop + tempo_button_y,
                                                    tempo_button_width, tempo_button_height, "<");
    tempo_higher_button   = new AdjustableButton(this.guiLeft + 6 + tempo_button_width + tempo_text_width, this.guiTop + tempo_button_y,
                                                    tempo_button_width, tempo_button_height,">");
    next_direction_button = new AdjustableButton(this.guiRight - 6 - next_direction_button_width, this.guiTop + 17, next_direction_button_width, 14, "North");

    // controls list
    buttons.add(play_button);
    buttons.add(tempo_lower_button);
    buttons.add(tempo_higher_button);
    buttons.add(next_direction_button);
    // add loop checkbox
    create_dynamic_buttons();
  }

  private final void create_dynamic_buttons(){
    byte i;
    byte j;
    int button_base_id = 10;
    int id = 0;
    int x;
    int y;

    // Mute Buttons
    x = this.guiLeft + mute_button_x;
    for(i = 0; i < MusicGrid.tracks; i++){
      id = button_base_id + i;
      y = this.guiTop + mute_button_y + (i * (mute_button_size + 1));
      buttons.add(new MuteButton(id,x,y,i,tile.get_mute(i)));
    }

    // Instrument Buttons
    button_base_id += MusicGrid.tracks;
    x = this.guiLeft + instrument_button_x;
    for(i = 0; i < MusicGrid.tracks; i++){
      id = button_base_id + i;
      y = this.guiTop + instrument_button_y + (i * (instrument_button_height + 1));
      buttons.add(new InstrumentButton(id,x,y,i,tile.get_track_instrument(i)));
    }

    // Note Buttons
    button_base_id += MusicGrid.tracks;
    NoteButton button;
    for(j = 0; j < MusicGrid.tracks; j++){
      for(i = 0; i < MusicGrid.frames; i++){
        id = button_base_id + (j * MusicGrid.frames) + i;
        x = this.guiLeft + music_grid_x + (i * note_button_width);
        y = this.guiTop + music_grid_y + (j * note_button_height);
        button = new NoteButton(id,x,y,j,i,note[tile.get_note(i, j)]);
        button.visible = tile.note_exists(j, i);
        buttons.add(button);
      }
    }
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    draw_custom_background_texture(384,256);
    get_variables_from_music_box();
    draw_playhead();
    draw_muted_tracks();
  }

  private final void get_variables_from_music_box(){
    ticks = tile.getTempo();
    bpm = Math.round((20.0f / ticks) * 30);
  }

  private final void draw_playhead(){
    if(tile != null){
      if(tile.is_playing()){
        this.mc.getTextureManager().bindTexture(playhead_texture);
        drawTexturedModalRect(this.guiLeft + playhead_x + (tile.playhead * note_button_width), this.guiTop + playhead_y,
                              playhead_texture_x, playhead_texture_y, 16, 8);
      }
    }
  }

  private final void draw_muted_tracks(){
    byte i;
    for(i = 0; i < MusicGrid.tracks; i++){
      if(tile.get_mute(i)){
      }
    }
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    draw_title();
    // draw tempo:
    draw_text_center("Speed:", tempo_text_x_center, 6);
    draw_text_center(ticks + " ticks",tempo_text_x_center, 17);
    draw_text_center(bpm + " bpm",tempo_text_x_center, 27);
    
    draw_text_center("Next:", this.xSize - 6 - Math.round(next_direction_button_width / 2), 6);
    next_direction_button.setMessage(face[tile.get_next_direction()]);
    
    draw_text_left("Current Note: "+note[note_selected],6,info_text_y);
    
  }

  /**
   * Overrides the {@link ContainerScreen#keyPressed(int, int, int)} method.
   */
  @Override
  public
  final boolean keyPressed(final int par1, final int par2, final int par3){
    if(keyCode == GLFW.GLFW_KEY_ESCAPE){
      this.mc.player.closeScreen();
    }
    else{
      switch(keyCode){ // MAYBE: maybe make these keys changeable in the Controls Options screen.
      case GLFW.GLFW_KEY_A: note_selected = 0; break;
      case GLFW.GLFW_KEY_Z: note_selected = 1; break;
      case GLFW.GLFW_KEY_S: note_selected = 2; break;
      case GLFW.GLFW_KEY_X: note_selected = 3; break;
      case GLFW.GLFW_KEY_D: note_selected = 4; break;
      case GLFW.GLFW_KEY_C: note_selected = 5; break;
      case GLFW.GLFW_KEY_V: note_selected = 6; break;
      case GLFW.GLFW_KEY_G: note_selected = 7; break;
      case GLFW.GLFW_KEY_B: note_selected = 8; break;
      case GLFW.GLFW_KEY_H: note_selected = 9; break;
      case GLFW.GLFW_KEY_N: note_selected = 10; break;
      case GLFW.GLFW_KEY_M: note_selected = 11; break;
      case GLFW.GLFW_KEY_1: note_selected = 12; break;
      case GLFW.GLFW_KEY_Q: note_selected = 13; break;
      case GLFW.GLFW_KEY_2: note_selected = 14; break;
      case GLFW.GLFW_KEY_W: note_selected = 15; break;
      case GLFW.GLFW_KEY_3: note_selected = 16; break;
      case GLFW.GLFW_KEY_E: note_selected = 17; break;
      case GLFW.GLFW_KEY_R: note_selected = 18; break;
      case GLFW.GLFW_KEY_5: note_selected = 19; break;
      case GLFW.GLFW_KEY_T: note_selected = 20; break;
      case GLFW.GLFW_KEY_6: note_selected = 21; break;
      case GLFW.GLFW_KEY_Y: note_selected = 22; break;
      case GLFW.GLFW_KEY_U: note_selected = 23; break;
      case GLFW.GLFW_KEY_8: note_selected = 24; break;
      }
    }

  }

  @Override
  protected final void mouseClicked(final int mouseX, final int mouseY, final int mouse_button) throws IOException {
    int i;
    for(i = 0; i < buttons.size(); i++){
      Widget button = buttons.get(i); // OPTIMIZE set this to final in all versions
      if(button.mousePressed(this.mc, mouseX, mouseY)){
        switch(mouse_button){
        case 0:
          if(button.id < 10){
            switch(button.id){
            case 0: NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.PLAY)); break;
            case 1: NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CHANGE_TEMPO,0)); break;
            case 2: NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CHANGE_TEMPO,1)); break;
            case 3: NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CYCLE_NEXT_DIRECTION)); break;
            }
            if(button.id != 0){
               button.playPressSound(this.mc.getSoundHandler());
            }
          }
          else{
            if(button instanceof NoteButton){
              final NoteButton note_button = (NoteButton)button;
              note_button.visible = true;
              note_button.setMessage(note[note_selected]);
              NetworkHandler.INSTANCE.sendToServer(new NoteMessage(tile.getPos(),note_button.frame,note_button.track,(byte)0,note_selected,1.0f));
            }
            if(button instanceof InstrumentButton){
              final InstrumentButton instrument_button = (InstrumentButton)button;
              instrument_button.increment_instrument();
              NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CHANGE_TRACK_INSTRUMENT,instrument_button.track));
            }
            if(button instanceof MuteButton){
              final MuteButton mute_button = (MuteButton)button;
              mute_button.toggle_mute();
              NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.TOGGLE_MUTE,mute_button.track));
            }
          }
          break;
        case 1:
          if(button instanceof NoteButton){
            final NoteButton note_button = (NoteButton)button;
            note_button.visible = false;
            NetworkHandler.INSTANCE.sendToServer(new NoteMessage(tile.getPos(),note_button.frame, note_button.track));
          }
          break;
        }
      }
    }
  }

}
