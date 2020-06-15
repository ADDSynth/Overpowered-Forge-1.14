package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiAdvancedOreRefinery extends GuiEnergyBase<TileAdvancedOreRefinery, ContainerOreRefinery> {

  private static final ResourceLocation advanced_ore_refinery_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/advanced_ore_refinery.png");

  private int energy_percentage;
  private int work_percentage;

  private final ProgressBar energy_progress_bar = new ProgressBar(148, 39, 17, 35, 204, 28);

  private final ProgressBar work_progress_bar = new ProgressBar(8, 83, 160, 5, 8, 194);
  
  private static final int work_percentage_text_y = 69;
  private static final int energy_percentage_text_x = 156;
  private static final int energy_percentage_text_y = 28;

  public GuiAdvancedOreRefinery(final ContainerOreRefinery container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 186, container, player_inventory, title, advanced_ore_refinery_gui_texture);
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
    RenderHelper.enableGUIStandardItemLighting();
    drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 76, 43);
    draw_text_center(work_percentage + "%", center_x, work_percentage_text_y);
    draw_time_left(tile, 92);
  }

}
