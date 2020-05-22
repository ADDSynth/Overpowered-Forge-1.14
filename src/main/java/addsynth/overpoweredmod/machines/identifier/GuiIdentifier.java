package addsynth.overpoweredmod.machines.identifier;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiIdentifier extends GuiEnergyBase<TileIdentifier, ContainerIdentifier> {

  private static final ResourceLocation identifier_gui_texture = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/identifier.png");

  private int work_percentage;
  private int energy_percentage;

  private static final int work_percentage_y = 63;
  private static final int energy_percentage_text_x = 156;
  private static final int energy_percentage_text_y = 28;

  private final ProgressBar work_progress_bar = new ProgressBar(8, 75, 160, 5, 11, 184);

  private final ProgressBar energy_progress_bar = new ProgressBar(151, 39, 17, 28, 206, 24);

  public GuiIdentifier(final ContainerIdentifier container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 169, container, player_inventory, title, identifier_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    draw_background_texture();
    // final float energy_float = tile.getEnergyPercentage();
    // energy_percentage = Math.round(energy_float*100);
    // energy_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,energy_float,ProgressBar.Round.NEAREST);
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float*100);
    work_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_energy_usage();
    draw_status(tile.getStatus());
    drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 76, 41);
    draw_text_center(work_percentage + "%", center_x, work_percentage_y);
    // draw_text_center(energy_percentage + "%",energy_percentage_text_x,energy_percentage_text_y);
  }

}
