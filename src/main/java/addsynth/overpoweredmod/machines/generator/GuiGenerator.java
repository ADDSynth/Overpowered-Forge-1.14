package addsynth.overpoweredmod.machines.generator;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiGenerator extends GuiEnergyBase<TileEnergyGenerator, ContainerGenerator> {

  private int energy_percentage;

  private static final int energy_x = 8;
  private static final int energy_y = 68;
  private static final int energy_width = 160;
  private static final int energy_height = 20;
  private static final int draw_energy_x = 8;
  private static final int draw_energy_y = 182;
  private final ProgressBar energy_progress_bar = new ProgressBar(energy_x,energy_y,energy_width,energy_height,draw_energy_x,draw_energy_y);

  private static final int input_text_x = 48;
  private static final int input_text_y = 24;

  private static final int extract_text_x = 74;
  private static final int extract_text_line_1 = 24; // was 19 to accomodate line 2.
  // private static final int extract_text_line_2 = 31;

  private static final int energy_text_x = 88;
  private static final int energy_text_line_1 = 44;
  private static final int energy_text_line_2 = 56;

  public GuiGenerator(final ContainerGenerator container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/generator.png"));
    this.ySize = 176;
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
    draw_text_right("Input:",input_text_x,input_text_y);
    
    draw_text_left("Max Extract: " + energy.getMaxExtract(),extract_text_x,extract_text_line_1);
    // draw_text_left("Energy Draw: "+energy_draw,extract_text_x,extract_text_line_2);
    
    draw_energy(6, energy_text_line_1);
    draw_text_center(energy_percentage + "%",energy_text_x,energy_text_line_2);
    draw_energy_difference(82);
  }

}
