package addsynth.core.gameplay.music_box.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public final class NoteButton extends GuiButton {

  public final byte track;
  public final byte frame;
  private static final int center_x = Math.round((float)GuiMusicBox.note_button_width / 2);
  private static final int text_draw_y = Math.round((float)GuiMusicBox.note_button_height / 2) - 4;
  private static final int text_color = 4210752; //Color.make_color(0,0,0); // 4210752;

  public NoteButton(int buttonId, int x_position, int y_position, int track, int frame, String note){
    super(buttonId, x_position, y_position , GuiMusicBox.note_button_width, GuiMusicBox.note_button_height, note);
    this.track = (byte)track;
    this.frame = (byte)frame;
  }

  @Override
  public final void drawButton(final Minecraft mc, final int mouseX, final int mouseY, final float partial){
    if(visible){
      if(displayString != null){
        mc.fontRenderer.drawString(displayString, x + center_x - (mc.fontRenderer.getStringWidth(displayString) / 2), y + text_draw_y, text_color);
      }
    }
  }

  @Override
  public final boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY){
    return enabled && mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
  }

}
