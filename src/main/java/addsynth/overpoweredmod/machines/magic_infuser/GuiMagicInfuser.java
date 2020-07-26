package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiMagicInfuser extends GuiEnergyBase<TileMagicInfuser, ContainerMagicInfuser> {

  private static final ResourceLocation magic_infuser_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/magic_infuser.png");

  private static final int work_percentage_text_y = 72;

  private int work_percentage;
  private final ProgressBar work_progress_bar = new ProgressBar(8, 84, 160, 5, 8, 194);
  
  public GuiMagicInfuser(final ContainerMagicInfuser container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 187, container, player_inventory, title, magic_infuser_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)(Math.floor(work_float*100));
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    draw_title();
    draw_energy_usage();
    draw_status(tile.getStatus());
    drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 78, 44);
    drawItemStack(tile.getWorkingInventory().getStackInSlot(1), 95, 44);
    draw_text_center(work_percentage + "%", center_x, work_percentage_text_y);
    draw_time_left(tile, 93);
  }

}
