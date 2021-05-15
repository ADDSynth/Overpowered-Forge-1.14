package addsynth.overpoweredmod.machines.advanced_ore_refinery;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.api.gui.GuiEnergyBase;
import addsynth.energy.api.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiAdvancedOreRefinery extends GuiEnergyBase<TileAdvancedOreRefinery, ContainerOreRefinery> {

  private static final ResourceLocation advanced_ore_refinery_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/advanced_ore_refinery.png");

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 83, 160, 5, 8, 194);
  private static final int work_percentage_text_y = 69;

  public GuiAdvancedOreRefinery(final ContainerOreRefinery container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 186, container, player_inventory, title, advanced_ore_refinery_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    RenderHelper.enableGUIStandardItemLighting();
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 76, 43);
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, work_percentage_text_y);
    draw_time_left(92);
  }

}
