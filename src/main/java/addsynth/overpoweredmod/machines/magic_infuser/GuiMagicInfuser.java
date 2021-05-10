package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.ProgressBar;
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
    super(176, 187, container, player_inventory, title, magic_infuser_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)(Math.floor(work_float*100));
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 78, 44);
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(1), 95, 44);
    GuiUtil.draw_text_center(work_percentage + "%", guiUtil.center_x, work_percentage_text_y);
    draw_time_left(93);
  }

}
