package addsynth.overpoweredmod.client.gui.tiles;

import java.io.IOException;
import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.gui.widgets.OnOffSwitch;
import addsynth.energy.network.server_messages.SwitchMachineMessage;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.containers.ContainerCrystalGenerator;
import addsynth.overpoweredmod.network.NetworkHandler;
import addsynth.overpoweredmod.tiles.machines.automatic.TileCrystalMatterReplicator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public final class GuiCrystalMatterGenerator extends GuiEnergyBase {

  private final TileCrystalMatterReplicator tile;

  private int energy_percentage;
  private int work_percentage;

  private static final int energy_bar_x = 155;
  private static final int energy_bar_y = 45;
  private static final int energy_bar_width = 13;
  private static final int energy_bar_height = 38;
  private static final int draw_energy_x = 206;
  private static final int draw_energy_y = 24;
  private final ProgressBar energy_progress_bar = new ProgressBar(energy_bar_x, energy_bar_y, energy_bar_width, energy_bar_height, draw_energy_x, draw_energy_y);

  private static final int work_bar_x = 8;
  private static final int work_bar_y = 89;
  private static final int work_bar_width = 160;
  private static final int work_bar_height = 5;
  private static final int draw_work_bar_x = 11;
  private static final int draw_work_bar_y = 194;
  private final ProgressBar work_progress_bar = new ProgressBar(work_bar_x, work_bar_y, work_bar_width, work_bar_height, draw_work_bar_x, draw_work_bar_y);
  
  private static final int energy_percentage_text_x = 158;
  private static final int energy_percentage_text_y = 33;
  private static final int work_percentage_text_y = 77;

  public GuiCrystalMatterGenerator(IInventory player_inventory, TileCrystalMatterReplicator tile) {
    super(new ContainerCrystalGenerator(player_inventory, tile),tile,new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/crystal_matter_generator.png"));
    this.tile = tile;
    this.ySize = 194;
  }

  @Override
  public final void initGui() {
    super.initGui();
    buttonList.add(new OnOffSwitch(0, this.guiLeft + 6, this.guiTop + 17, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    draw_background_texture();
    final float energy_float = tile.getEnergyPercentage();
    energy_percentage = Math.round(energy_float * 100);
    energy_progress_bar.draw(this, guiLeft, guiTop, ProgressBar.Direction.BOTTOM_TO_TOP, energy_float, ProgressBar.Round.NEAREST);
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float * 100);
    work_progress_bar.draw(this, guiLeft, guiTop, ProgressBar.Direction.LEFT_TO_RIGHT, work_float, ProgressBar.Round.FLOOR);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    draw_title();
    draw_energy_after_switch(tile.getEnergy());
    draw_text_center(energy_percentage + "%",energy_percentage_text_x,energy_percentage_text_y);
    draw_status(tile.getStatus(), 36); // this is the only call to the other draw_status() function that specifies the y level. Likely to be removed once we rewrite the energy system, because all machines will have a On/Off switch and will need to draw at THIS y level.
    draw_text_center(work_percentage + "%", work_percentage_text_y);
    draw_time_left(tile.getTimeLeft(), 98);
  }

  @Override
  protected final void actionPerformed(GuiButton button) throws IOException {
    if(button.id == 0){
      NetworkHandler.INSTANCE.sendToServer(new SwitchMachineMessage(tile.getPos()));
    }
  }

}
