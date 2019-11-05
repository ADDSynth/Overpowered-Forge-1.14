package addsynth.overpoweredmod.client.gui.tiles;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.containers.ContainerElectricFurnace;
import addsynth.overpoweredmod.tiles.machines.automatic.TileElectricFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public final class GuiElectricFurnace extends GuiEnergyBase {

  private final TileElectricFurnace tile;

  private int energy_percentage;
  private int work_percentage;

  private static final int energy_bar_x = 148;
  private static final int energy_bar_y = 39;
  private static final int energy_bar_width = 17;
  private static final int energy_bar_height = 45;
  private static final int draw_energy_x = 204;
  private static final int draw_energy_y = 28;
  private final ProgressBar energy_progress_bar = new ProgressBar(energy_bar_x, energy_bar_y, energy_bar_width, energy_bar_height, draw_energy_x, draw_energy_y);

  private static final int work_bar_x = 40;
  private static final int work_bar_y = 60;
  private static final int work_bar_width = 14;
  private static final int work_bar_height = 14;
  private static final int draw_work_bar_x = 200;
  private static final int draw_work_bar_y = 2;
  private final ProgressBar work_progress_bar = new ProgressBar(work_bar_x, work_bar_y, work_bar_width, work_bar_height, draw_work_bar_x, draw_work_bar_y);
  
  private static final int energy_percentage_text_x = 156;
  private static final int energy_percentage_text_y = 28;
  private static final int work_percentage_text_y = 65;
  private static final int time_left_y = 78;

  public GuiElectricFurnace(IInventory player_inventory, TileElectricFurnace tile){
    super(new ContainerElectricFurnace(player_inventory, tile),tile,
          new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/electric_furnace.png"));
    this.tile = tile;
    this.ySize = 172;
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    final float energy_float = tile.getEnergyPercentage();
    energy_percentage = Math.round(energy_float*100);
    energy_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,energy_float,ProgressBar.Round.NEAREST);
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float*100);
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
    super.draw_energy(tile.getEnergy());
    draw_text_center(energy_percentage + "%",energy_percentage_text_x,energy_percentage_text_y);
    draw_status(tile.getStatus());
    draw_text_center(work_percentage + "%",(this.xSize / 2)-10,work_percentage_text_y);
    draw_time_left(tile.getTotalTimeLeft(), time_left_y);
  }

}
