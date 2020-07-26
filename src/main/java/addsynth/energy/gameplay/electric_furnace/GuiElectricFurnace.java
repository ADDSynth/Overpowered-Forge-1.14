package addsynth.energy.gameplay.electric_furnace;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gui.GuiEnergyBase;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiElectricFurnace extends GuiEnergyBase<TileElectricFurnace, ContainerElectricFurnace> {

  private static final ResourceLocation electric_furnace_gui_texture =
    new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/electric_furnace.png");

  private int work_percentage;
  private final ProgressBar work_progress_bar = new ProgressBar(80, 60, 14, 14, 200, 2);
  
  private static final int work_percentage_text_y = 65;
  private static final int time_left_y = 78;

  public GuiElectricFurnace(final ContainerElectricFurnace container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 172, container, player_inventory, title, electric_furnace_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float*100);
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
    draw_energy_usage();
    draw_status(tile.getStatus());
    RenderHelper.enableGUIStandardItemLighting();
    drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 80, 40);
    draw_text_center(work_percentage + "%", center_x + 21, work_percentage_text_y);
    draw_time_left(tile, time_left_y);
  }

}
