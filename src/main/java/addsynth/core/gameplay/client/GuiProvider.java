package addsynth.core.gameplay.client;

import addsynth.core.gameplay.music_box.MusicBox;
import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.music_box.gui.GuiMusicBox;
import addsynth.core.gameplay.team_manager.TeamManagerBlock;
import addsynth.core.gameplay.team_manager.gui.TeamManagerGui;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

public final class GuiProvider {

  @SuppressWarnings("resource")
  public static final void openMusicBoxGui(final TileMusicBox tile, final MusicBox block){
    Minecraft.getInstance().displayGuiScreen(new GuiMusicBox(tile, new TranslationTextComponent(block.getTranslationKey())));
  }

  @SuppressWarnings("resource")
  public static final void openTeamManagerGui(final TeamManagerBlock block){
    Minecraft.getInstance().displayGuiScreen(new TeamManagerGui());
  }

}
