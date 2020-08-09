package addsynth.core.gameplay.music_box.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.NetworkHandler;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.music_box.network_messages.ChangeInstrumentMessage;
import addsynth.core.gameplay.music_box.network_messages.MusicBoxMessage;
import addsynth.core.gameplay.music_box.network_messages.NoteMessage;
import addsynth.core.gui.objects.AdjustableButton;
import addsynth.core.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;

public final class MusicButtons {

  private static final ResourceLocation instruments_texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/instruments.png");
  private static final int instrument_texture_size = 64;

  public static final class PlayButton extends AdjustableButton {

    private final TileMusicBox tile;

    public PlayButton(int x, int y, int width, TileMusicBox tile){
      super(x, y, width, 14, StringUtil.translate("gui.addsynthcore.music_box.play"));
      this.tile = tile;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.PLAY));
    }
    
    @Override
    public final void playDownSound(final SoundHandler p_playDownSound_1_){
    }
  
  }

  public static final class TempoButton extends AdjustableButton {

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

  public static final class NextDirectionButton extends AdjustableButton {

    private final TileMusicBox tile;
    private final String[] face = new String[] {
      StringUtil.translate("gui.addsynthcore.direction.down"),
      StringUtil.translate("gui.addsynthcore.direction.up"),
      StringUtil.translate("gui.addsynthcore.direction.north"),
      StringUtil.translate("gui.addsynthcore.direction.south"),
      StringUtil.translate("gui.addsynthcore.direction.west"),
      StringUtil.translate("gui.addsynthcore.direction.east")
    };
  
    public NextDirectionButton(int xIn, int yIn, int widthIn, TileMusicBox tile){
      super(xIn, yIn, widthIn, 14);
      this.tile = tile;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      setMessage(face[tile.get_next_direction()]); // stays up-to-date
      super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.CYCLE_NEXT_DIRECTION));
    }
  }

  public static final class MuteButton extends AbstractButton {
  
    private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID,"textures/gui/gui_textures.png");
    private static final int button_size = 12;
    private static final int texture_size = 24;
    private static final int texture_x = 64;
    private static final int texture_y = 32;
  
    private final TileMusicBox tile;
    private boolean mute;
    private final byte track;

    public MuteButton(final int x, final int y, final byte track, final TileMusicBox tile){
      super(x, y, button_size, button_size, "");
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
  
      blit(x, y, button_size, button_size, mute ? texture_x + 24 : texture_x, texture_y, texture_size, texture_size, 256, 256);
    }
  
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new MusicBoxMessage(tile.getPos(),TileMusicBox.Command.TOGGLE_MUTE,track));
    }
  
    @Override
    public final void playDownSound(final SoundHandler p_playDownSound_1_){
    }
  
  }

  public static final class TrackInstrumentButton extends AbstractButton {

    private static final int button_size = 12;

    private final TileMusicBox tile;
    private final byte track;

    public TrackInstrumentButton(final int x, final int y, final byte track, final TileMusicBox tile){
      super(x, y, button_size, button_size, "");
      this.track = track;
      this.tile = tile;
    }

    @Override
    public final void renderButton(final int mouse_x, final int mouse_y, final float partial_ticks){
      final int instrument = tile.get_track_instrument(track);
      final int texture_x = instrument_texture_size * (instrument % 4);
      final int texture_y = instrument_texture_size * (instrument / 4);
    
      Minecraft.getInstance().getTextureManager().bindTexture(instruments_texture);
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

      GlStateManager.enableBlend();
      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

      blit(x, y, button_size, button_size, texture_x, texture_y, instrument_texture_size, instrument_texture_size, 256, 256);
    }
  
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new ChangeInstrumentMessage(tile.getPos(), track, GuiMusicBox.instrument_selected));
    }

    @Override
    public final void playDownSound(final SoundHandler p_playDownSound_1_){
    }
  }

  public static final class SelectInstrumentButton extends AbstractButton {
  
    private static final int button_size = 16;

    private final int instrument;
    private final int texture_x;
    private final int texture_y;

    public SelectInstrumentButton(final int x, final int y, final int instrument){
      super(x, y, button_size, button_size, "");
      this.instrument = instrument;
      texture_x = instrument_texture_size * (instrument % 4);
      texture_y = instrument_texture_size * (instrument / 4);
    }
  
    @Override
    public final void renderButton(final int mouse_x, final int mouse_y, final float partial_ticks){
      Minecraft.getInstance().getTextureManager().bindTexture(instruments_texture);
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

      GlStateManager.enableBlend();
      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

      blit(x, y, button_size, button_size, texture_x, texture_y, instrument_texture_size, instrument_texture_size, 256, 256);
    }
  
    @Override
    public void onPress(){
      GuiMusicBox.instrument_selected = (byte)instrument;
    }

    @Override
    public final void playDownSound(final SoundHandler p_playDownSound_1_){
    }
  }

  public static final String[] note = new String[] {                                "F#3","G3","G#3","A3","A#3","B3",
                                                    "C4","C#4","D4","D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4",
                                                    "C5","C#5","D5","D#5","E5","F5","F#5"};

  public static final class NoteButton extends Widget {
  
    private static final int center_x = Math.round((float)GuiMusicBox.note_button_width / 2);
    private static final int text_draw_y = Math.round((float)GuiMusicBox.note_button_height / 2) - 4;
    private static final int text_color = 4210752; //Color.make_color(0,0,0); // 4210752;
  
    private final TileMusicBox tile;
    private final byte track;
    private final byte frame;

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
          case 1: // right mouse button
            delete_note(); // TODO: Dur, I completely forgot why I did this! In the future, add configs to swap the add and delete note mouse buttons, for left-handed people.
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
      NetworkHandler.INSTANCE.sendToServer(new NoteMessage(tile.getPos(), frame, track, GuiMusicBox.note_selected, 1.0f));
    }

    private final void delete_note(){
      if(visible == true){
        NetworkHandler.INSTANCE.sendToServer(new NoteMessage(tile.getPos(), frame, track));
      }
    }

  }

}
