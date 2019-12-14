package addsynth.core.gameplay.music_box.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.music_box.network_messages.MusicBoxMessage;
import addsynth.core.gameplay.music_box.network_messages.NoteMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public final class MusicButtons {

  public static final class PlayButton extends AbstractButton {

    private final TileMusicBox tile;

    public PlayButton(int x, int y, int width, TileMusicBox tile){
      super(x, y, width, 14, "Play");
      this.tile = tile;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.PLAY));
    }
  }

  public static final class TempoButton extends AbstractButton {

    private final boolean direction;
    private final TileMusicBox tile;

    public TempoButton(int xIn, int yIn, int widthIn, int heightIn, boolean direction, TileMusicBox tile){
      super(xIn, yIn, widthIn, heightIn, direction ? "<" : ">");
      this.direction = direction;
      this.tile = tile;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CHANGE_TEMPO,direction ? 0 : 1));
    }
  }

  public static final class NextDirectionButton extends AbstractButton {

    private final TileMusicBox tile;
    private static final String[] face = new String[] {"Down", "Up", "North", "South", "West", "East"};

    public NextDirectionButton(int xIn, int yIn, int widthIn, TileMusicBox tile){
      super(xIn, yIn, widthIn, 14, "");
      this.tile = tile;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      setMessage(face[tile.get_next_direction()]);
      super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CYCLE_NEXT_DIRECTION));
    }
  }

  public static final class MuteButton extends AbstractButton {
  
    private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");
    private static final int texture_width = 24;
    private static final int texture_height = 24;
    private static final int texture_x = 64;
    private static final int texture_y = 32;
  
    private final TileMusicBox tile;
    private boolean mute;
    private final byte track;

    public MuteButton(final int x, final int y, final byte track, final TileMusicBox tile){
      super(x, y, GuiMusicBox.mute_button_size, GuiMusicBox.mute_button_size, null);
      this.tile = tile;
      this.track = track;
    }
  
    @Override
    public final void renderButton(final int mouseX, final int mouseY, final float partial_ticks){
      mute = tile.get_mute(track);

      Minecraft.getInstance().getTextureManager().bindTexture(texture);
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
  
      GlStateManager.enableBlend();
      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
  
      blit(x, y, mute ? texture_x + 24 : texture_x, texture_y,
        texture_width, texture_height, GuiMusicBox.mute_button_size, GuiMusicBox.mute_button_size, 256, 256);
    }
  
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.TOGGLE_MUTE,track));
    }
  
    @Override
    public void playDownSound(SoundHandler p_playDownSound_1_){
    }
  
  }

  public static final class InstrumentButton extends AbstractButton {
  
    private static final ResourceLocation instruments_texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/instruments.png");
    private static final int texture_width = 64;
    private static final int texture_height = 64;
  
    private final TileMusicBox tile;
    public final byte track;

    public InstrumentButton(final int x, final int y, final byte track, final TileMusicBox tile){
      super(x, y, GuiMusicBox.instrument_button_width, GuiMusicBox.instrument_button_height, null);
      this.tile = tile;
      this.track = track;
    }
  
    @Override
    public final void renderButton(final int mouseX, final int mouseY, final float partial_ticks){
      // change texture coordinates based on instrument
      final byte instrument = tile.get_track_instrument(track);
      final int texture_x = texture_width * (instrument % 4);
      final int texture_y = texture_height * (instrument / 4);

      Minecraft.getInstance().getTextureManager().bindTexture(instruments_texture);
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

      GlStateManager.enableBlend();
      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

      // drawTexturedModalRect((float)xPosition, (float)yPosition, texture_x, texture_y, texture_x + texture_width, texture_y + texture_height);
      blit(x, y, texture_x, texture_y, texture_width, texture_height, GuiMusicBox.instrument_button_width, GuiMusicBox.instrument_button_height, 256, 256);
    }
  
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CHANGE_TRACK_INSTRUMENT,track));
    }

    @Override
    public void playDownSound(SoundHandler p_playDownSound_1_){
    }
  
  }

  public static final String[] note = new String[] {                                "F#3","G3","G#3","A3","A#3","B3",
                                                    "C4","C#4","D4","D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4",
                                                    "C5","C#5","D5","D#5","E5","F5","F#5"};

  public static final class NoteButton extends Widget {
  
    private final TileMusicBox tile;
    private final byte track;
    private final byte frame;
    private static final int center_x = Math.round((float)GuiMusicBox.note_button_width / 2);
    private static final int text_draw_y = Math.round((float)GuiMusicBox.note_button_height / 2) - 4;
    private static final int text_color = 4210752; //Color.make_color(0,0,0); // 4210752;
  
    public NoteButton(int x_position, int y_position, byte track, byte frame, TileMusicBox tile){
      super(x_position, y_position , GuiMusicBox.note_button_width, GuiMusicBox.note_button_height, note[tile.get_note(frame, track)]);
      this.tile = tile;
      this.track = (byte)track;
      this.frame = (byte)frame;
    }
  
    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_){
      visible = tile.note_exists(track, frame);
      if(visible){
        setMessage(note[tile.get_note(frame, track)]);
      }
      super.render(p_render_1_, p_render_2_, p_render_3_);
    }
  
    @Override
    public final void renderButton(final int mouseX, final int mouseY, final float partial){
      final String note = getMessage();
      if(note != null){
        final Minecraft mc = Minecraft.getInstance();
        mc.fontRenderer.drawString(note, x + center_x - (mc.fontRenderer.getStringWidth(note) / 2), y + text_draw_y, text_color);
      }
    }
  
    @Override
    public boolean mouseClicked(double mouse_x, double mouse_y, int button){
      if(active){
        if(clicked(mouse_x, mouse_y)){
          boolean flag = false;
          switch(button){
          case 0: // left mouse button
            set_note();
            flag = true;
            break;
          case 2: // right mouse button
            delete_note();
            flag = true;
            break;
          }
          return flag;
        }
      }
      return false;
    }

    @Override
    protected boolean clicked(double p_clicked_1_, double p_clicked_3_){
      return this.active &&
        p_clicked_1_ >= (double)this.x               && p_clicked_3_ >= (double)this.y &&
        p_clicked_1_ < (double)(this.x + this.width) && p_clicked_3_ < (double)(this.y + this.height);
    }

    private final void set_note(){
      NetworkHandler.INSTANCE.sendToServer(new NoteMessage(tile.getPos(),frame,track,(byte)0,GuiMusicBox.note_selected,1.0f));
    }

    private final void delete_note(){
      if(visible == true){
        NetworkHandler.INSTANCE.sendToServer(new NoteMessage(tile.getPos(),frame, track));
      }
    }

  }

}
