package addsynth.overpoweredmod.machines.energy_extractor;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.util.StringUtil;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.gui.widgets.EnergyProgressBar;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiCrystalEnergyExtractor extends GuiEnergyBase<TileCrystalEnergyExtractor, ContainerCrystalEnergyExtractor> {

  private static final ResourceLocation gui_texture = new ResourceLocation(OverpoweredMod.MOD_ID, "textures/gui/crystal_energy_extractor.png");

  private final String input_text   = StringUtil.translate("gui.overpowered.crystal_energy_generator.input");
  private final String extract_text = StringUtil.translate("gui.overpowered.crystal_energy_generator.max_extract");

  private final EnergyProgressBar energy_progress_bar = new EnergyProgressBar(8, 68, 168, 20, 8, 182);

  private static final int input_text_x = 52;
  private static final int input_text_y = 24;

  private static final int extract_text_x = 78;
  private static final int extract_text_line_1 = 24; // was 19 to accomodate line 2.
  // private static final int extract_text_line_2 = 31;

  private static final int energy_text_line_1 = 44;
  private static final int energy_text_line_2 = 56;

  public GuiCrystalEnergyExtractor(final ContainerCrystalEnergyExtractor container, final PlayerInventory player_inventory, final ITextComponent title){
    super(182, 176, container, player_inventory, title, gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    energy_progress_bar.drawHorizontal(this, energy);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_right(input_text+":",input_text_x,input_text_y);
    
    GuiUtil.draw_text_left(extract_text+": " + energy.getMaxExtract(),extract_text_x,extract_text_line_1);
    // draw_text_left("Energy Draw: "+energy_draw,extract_text_x,extract_text_line_2);
    
    draw_energy(6, energy_text_line_1);
    GuiUtil.draw_text_center(energy_progress_bar.getEnergyPercentage(), guiUtil.center_x, energy_text_line_2);
    draw_energy_difference(82);
  }

}
