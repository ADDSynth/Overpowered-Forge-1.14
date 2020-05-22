package addsynth.overpoweredmod.machines.inverter;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiInverter extends GuiEnergyBase<TileInverter, ContainerInverter> {

  private static final ResourceLocation inverter_gui_texture = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/inverter.png");

  private int work_percentage;
  private int energy_percentage;

  private final ProgressBar energy_progress_bar = new ProgressBar(148, 39, 17, 35, 204, 28);

  private final ProgressBar work_progress_bar = new ProgressBar(8, 84, 160, 5, 8, 194);
  
  private static final int work_percentage_text_y = 70;
  private static final int energy_percentage_text_x = 156;
  private static final int energy_percentage_text_y = 28;

  public GuiInverter(final ContainerInverter container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 187, container, player_inventory, title, inverter_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    // final float energy_float = tile.getEnergyPercentage();
    // energy_percentage = (Math.round(energy_float*100));
    // energy_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,energy_float,ProgressBar.Round.NEAREST);
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)(Math.floor(work_float*100));
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    draw_title();
    draw_energy_usage();
    draw_status(tile.getStatus());
    drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 77, 44);
    draw_text_center(work_percentage + "%", center_x, work_percentage_text_y);
    // draw_text_center(energy_percentage + "%",energy_percentage_text_x,energy_percentage_text_y);
    draw_time_left(tile, 93);
  }

}
