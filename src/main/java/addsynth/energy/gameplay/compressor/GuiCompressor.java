package addsynth.energy.gameplay.compressor;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gui.GuiEnergyBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiCompressor extends GuiEnergyBase<TileCompressor, ContainerCompressor> {

  private static final ResourceLocation compressor_gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/compressor.png");

  private int energy_percentage;
  private int work_percentage;

  private static final int energy_percentage_text_x = 156;
  private static final int energy_percentage_text_y = 28;
  private static final int work_percentage_text_y = 67;
  private static final int time_left_y = 88;

  private final ProgressBar energy_progress_bar = new ProgressBar(148, 39, 17, 33, 196, 39);

  private final ProgressBar work_progress_bar = new ProgressBar(8, 79, 160, 5, 8, 194);
  
  public GuiCompressor(final ContainerCompressor container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 182, container, player_inventory, title, compressor_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    // final float energy_float = tile.getEnergyPercentage();
    // energy_percentage = Math.round(energy_float*100);
    // energy_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,energy_float,ProgressBar.Round.NEAREST);
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float*100);
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
    draw_energy_usage();
    // draw_text_center(energy_percentage + "%",energy_percentage_text_x,energy_percentage_text_y);
    draw_status(tile.getStatus());
    drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 79, 41);
    drawItemStack(tile.getWorkingInventory().getStackInSlot(1), 96, 41);
    draw_text_center(work_percentage + "%", center_x, work_percentage_text_y);
    draw_time_left(tile, time_left_y);
  }

}
