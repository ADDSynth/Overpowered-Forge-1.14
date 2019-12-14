package addsynth.core.gameplay.music_box.gui;

import org.lwjgl.glfw.GLFW;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.music_box.MusicGrid;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gui.GuiBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiMusicBox extends GuiBase<ContainerMusicBox> {

  private final TileMusicBox tile;

  private static final int gui_width = 243;
  private static final int gui_height = 203;
  
  // variables
  public static byte note_selected;
  private byte ticks;
  private int bpm;

  // controls
  private static final int play_button_width = 46;
  
  private static final int tempo_button_width = 10;
  private static final int tempo_button_height = 16;
  private static final int tempo_text_width = 50;
  private static final int tempo_text_height = 18;
  private static final int tempo_text_x_center = 6 + tempo_button_width + Math.round(tempo_text_width / 2);
  private static final int tempo_text_y_center = 17 + 9;
  private static final int tempo_button_y = tempo_text_y_center - Math.round(tempo_button_height / 2);
  
  private static final int next_direction_button_width = 50;

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

    // controls list
    int x = this.guiLeft + (xSize / 2) - (play_button_width / 2);
    addButton(new MusicButtons.PlayButton(x, this.guiTop + 17, play_button_width, tile));
    addButton(new MusicButtons.TempoButton(this.guiLeft + 6, this.guiTop + tempo_button_y, tempo_button_width, tempo_button_height, true, tile));
    x = this.guiLeft + 6 + tempo_button_width + tempo_text_width; // OPTIMIZE calculation of guiTop + tempo_button_y for Tempo buttons.
    addButton(new MusicButtons.TempoButton(x, this.guiTop + tempo_button_y, tempo_button_width, tempo_button_height, false, tile));
    addButton(new MusicButtons.NextDirectionButton(this.guiRight - 6 - next_direction_button_width, this.guiTop + 17, next_direction_button_width, tile));

    // music grid buttons
    create_dynamic_buttons();
  }

  private final void create_dynamic_buttons(){
    byte i;
    byte j;
    int x;
    int y;

    // Mute Buttons
    x = this.guiLeft + mute_button_x;
    for(i = 0; i < MusicGrid.tracks; i++){
      y = this.guiTop + mute_button_y + (i * (mute_button_size + 1));
      addButton(new MusicButtons.MuteButton(x,y,i,tile));
    }

    // Instrument Buttons
    x = this.guiLeft + instrument_button_x;
    for(i = 0; i < MusicGrid.tracks; i++){
      y = this.guiTop + instrument_button_y + (i * (instrument_button_height + 1));
      addButton(new MusicButtons.InstrumentButton(x,y,i,tile));
    }

    // Note Buttons
    for(j = 0; j < MusicGrid.tracks; j++){
      for(i = 0; i < MusicGrid.frames; i++){
        x = this.guiLeft + music_grid_x + (i * note_button_width);
        y = this.guiTop + music_grid_y + (j * note_button_height);
        addButton(new MusicButtons.NoteButton(x,y,j,i,tile));
      }
    }
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
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
        this.minecraft.getTextureManager().bindTexture(playhead_texture);
        blit(this.guiLeft + playhead_x + (tile.playhead * note_button_width), this.guiTop + playhead_y,
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
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    draw_title();
    // draw tempo:
    draw_text_center("Speed:", tempo_text_x_center, 6);
    draw_text_center(ticks + " ticks",tempo_text_x_center, 17);
    draw_text_center(bpm + " bpm",tempo_text_x_center, 27);
    
    draw_text_center("Next:", this.xSize - 6 - Math.round(next_direction_button_width / 2), 6);
    
    draw_text_left("Current Note: "+MusicButtons.note[note_selected],6,info_text_y);
  }

  /**
   * Overrides the {@link ContainerScreen#keyPressed(int, int, int)} method.
   */
  @Override
  public
  final boolean keyPressed(final int keyCode, final int par2, final int par3){
    if(keyCode == GLFW.GLFW_KEY_ESCAPE){
      this.minecraft.player.closeScreen();
      return true;
    }
    // MAYBE: maybe make these keys changeable in the Controls Options screen.
    if(keyCode == GLFW.GLFW_KEY_A){ note_selected = 0; return true; }
    if(keyCode == GLFW.GLFW_KEY_Z){ note_selected = 1; return true; }
    if(keyCode == GLFW.GLFW_KEY_S){ note_selected = 2; return true; }
    if(keyCode == GLFW.GLFW_KEY_X){ note_selected = 3; return true; }
    if(keyCode == GLFW.GLFW_KEY_D){ note_selected = 4; return true; }
    if(keyCode == GLFW.GLFW_KEY_C){ note_selected = 5; return true; }
    if(keyCode == GLFW.GLFW_KEY_V){ note_selected = 6; return true; }
    if(keyCode == GLFW.GLFW_KEY_G){ note_selected = 7; return true; }
    if(keyCode == GLFW.GLFW_KEY_B){ note_selected = 8; return true; }
    if(keyCode == GLFW.GLFW_KEY_H){ note_selected = 9; return true; }
    if(keyCode == GLFW.GLFW_KEY_N){ note_selected = 10; return true; }
    if(keyCode == GLFW.GLFW_KEY_M){ note_selected = 11; return true; }
    if(keyCode == GLFW.GLFW_KEY_1){ note_selected = 12; return true; }
    if(keyCode == GLFW.GLFW_KEY_Q){ note_selected = 13; return true; }
    if(keyCode == GLFW.GLFW_KEY_2){ note_selected = 14; return true; }
    if(keyCode == GLFW.GLFW_KEY_W){ note_selected = 15; return true; }
    if(keyCode == GLFW.GLFW_KEY_3){ note_selected = 16; return true; }
    if(keyCode == GLFW.GLFW_KEY_E){ note_selected = 17; return true; }
    if(keyCode == GLFW.GLFW_KEY_R){ note_selected = 18; return true; }
    if(keyCode == GLFW.GLFW_KEY_5){ note_selected = 19; return true; }
    if(keyCode == GLFW.GLFW_KEY_T){ note_selected = 20; return true; }
    if(keyCode == GLFW.GLFW_KEY_6){ note_selected = 21; return true; }
    if(keyCode == GLFW.GLFW_KEY_Y){ note_selected = 22; return true; }
    if(keyCode == GLFW.GLFW_KEY_U){ note_selected = 23; return true; }
    if(keyCode == GLFW.GLFW_KEY_8){ note_selected = 24; return true; }
    return super.keyPressed(keyCode, par2, par3); // handle another key
  }

}
