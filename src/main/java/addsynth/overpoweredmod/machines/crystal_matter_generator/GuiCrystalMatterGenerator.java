package addsynth.overpoweredmod.machines.crystal_matter_generator;

import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.gui.widgets.OnOffSwitch;
import addsynth.energy.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiCrystalMatterGenerator extends GuiEnergyBase<TileCrystalMatterGenerator, ContainerCrystalGenerator> {

  private static final ResourceLocation crystal_matter_generator_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/crystal_matter_generator.png");

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 89, 160, 5, 11, 194);
  
  private static final int work_percentage_text_y = 77;

  public GuiCrystalMatterGenerator(final ContainerCrystalGenerator container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 192, container, player_inventory, title, crystal_matter_generator_gui_texture);
  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch<>(this.guiLeft + 6, this.guiTop + 17, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage_after_switch();
    draw_status_below_switch(tile.getStatus());
    guiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), work_percentage_text_y);
    draw_time_left(98);
  }

}
