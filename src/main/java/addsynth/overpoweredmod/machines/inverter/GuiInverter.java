package addsynth.overpoweredmod.machines.inverter;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Config;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiInverter extends GuiEnergyBase<TileInverter, ContainerInverter> {

  private static final ResourceLocation inverter_gui_texture = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/inverter.png");

  private float work_float;
  private int work_percentage;
  private final ProgressBar work_progress_bar = new ProgressBar(8, 84, 160, 5, 8, 194);
  
  private static final int work_percentage_text_y = 70;

  public GuiInverter(final ContainerInverter container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 187, container, player_inventory, title, inverter_gui_texture);
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    
    work_float = tile.getWorkTimePercentage();
    work_percentage = (int)(Math.floor(work_float*100));
    work_progress_bar.draw(this,guiLeft,guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    draw_title();
    draw_energy_usage();
    draw_status(tile.getStatus());
    
    final ItemStack s1 = tile.getWorkingInventory().getStackInSlot(0);
    if(Config.blend_working_item.get()){
      final ItemStack s2 = TileInverter.getInverted(s1);
      blendItemStacks(s1, s2, 77, 44, work_float);
    }
    else{
      drawItemStack(s1, 77, 44);
    }
    
    draw_text_center(work_percentage + "%", center_x, work_percentage_text_y);
    draw_time_left(93);
  }

}
