package addsynth.overpoweredmod.machines.identifier;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiIdentifier extends GuiEnergyBase<ContainerIdentifier> {

  private final TileIdentifier tile;

  private int work_percentage;
  private int energy_percentage;

  private static final int work_percentage_y = 61;
  private static final int energy_percentage_text_x = 156;
  private static final int energy_percentage_text_y = 28;

  private static final int work_bar_x = 8;
  private static final int work_bar_y = 73;
  private static final int work_bar_width = 160;
  private static final int work_bar_height = 5;
  private static final int draw_work_bar_x = 11;
  private static final int draw_work_bar_y = 184;
  private final ProgressBar work_progress_bar = new ProgressBar(work_bar_x, work_bar_y, work_bar_width, work_bar_height, draw_work_bar_x, draw_work_bar_y);

  private static final int energy_bar_x = 151;
  private static final int energy_bar_y = 39;
  private static final int energy_bar_width = 17;
  private static final int energy_bar_height = 28;
  private static final int draw_energy_x = 206;
  private static final int draw_energy_y = 24;
  private final ProgressBar energy_progress_bar = new ProgressBar(energy_bar_x,energy_bar_y,energy_bar_width,energy_bar_height,draw_energy_x,draw_energy_y);

  public GuiIdentifier(final ContainerIdentifier container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/identifier.png"));
    this.tile = container.getTileEntity();
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
    super.draw_energy(tile.getEnergy());
    draw_status(tile.getStatus());
    draw_text_center(work_percentage + "%",this.xSize / 2,work_percentage_y);
    draw_text_center(energy_percentage + "%",energy_percentage_text_x,energy_percentage_text_y);
  }

}
