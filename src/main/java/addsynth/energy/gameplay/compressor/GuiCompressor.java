package addsynth.energy.gameplay.compressor;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gui.GuiEnergyBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiCompressor extends GuiEnergyBase<TileCompressor, ContainerCompressor> {

  private static final ResourceLocation compressor_gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/compressor.png");

  private static final int work_percentage_text_y = 67;
  private static final int time_left_y = 88;

  private int work_percentage;
  private final ProgressBar work_progress_bar = new ProgressBar(8, 79, 160, 5, 8, 194);
  
  public GuiCompressor(final ContainerCompressor container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 182, container, player_inventory, title, compressor_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    draw_background_texture();
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float*100);
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_energy_usage();
    draw_status(tile.getStatus());
    drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 80, 42);
    draw_text_center(work_percentage + "%", center_x, work_percentage_text_y);
    draw_time_left(time_left_y);
  }

}
