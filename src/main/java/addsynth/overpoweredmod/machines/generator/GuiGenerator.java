package addsynth.overpoweredmod.machines.generator;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.core.util.StringUtil;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiGenerator extends GuiEnergyBase<TileCrystalEnergyGenerator, ContainerGenerator> {

  private static final ResourceLocation generator_gui_texture = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/generator.png");

  private final String input_text   = StringUtil.translate("gui.overpowered.crystal_energy_generator.input");
  private final String extract_text = StringUtil.translate("gui.overpowered.crystal_energy_generator.max_extract");

  private int energy_percentage;

  private final ProgressBar energy_progress_bar = new ProgressBar(8, 68, 168, 20, 8, 182);

  private static final int input_text_x = 52;
  private static final int input_text_y = 24;

  private static final int extract_text_x = 78;
  private static final int extract_text_line_1 = 24; // was 19 to accomodate line 2.
  // private static final int extract_text_line_2 = 31;

  private static final int energy_text_line_1 = 44;
  private static final int energy_text_line_2 = 56;

  public GuiGenerator(final ContainerGenerator container, final PlayerInventory player_inventory, final ITextComponent title){
    super(182, 176, container, player_inventory, title, generator_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    
    final float energy_float = energy.getEnergyPercentage();
    energy_percentage = Math.round(energy_float*100);
    energy_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,energy_float,ProgressBar.Round.NEAREST);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_text_right(input_text+":",input_text_x,input_text_y);
    
    draw_text_left(extract_text+": " + energy.getMaxExtract(),extract_text_x,extract_text_line_1);
    // draw_text_left("Energy Draw: "+energy_draw,extract_text_x,extract_text_line_2);
    
    draw_energy(6, energy_text_line_1);
    draw_text_center(energy_percentage + "%", center_x, energy_text_line_2);
    draw_energy_difference(82);
  }

}
