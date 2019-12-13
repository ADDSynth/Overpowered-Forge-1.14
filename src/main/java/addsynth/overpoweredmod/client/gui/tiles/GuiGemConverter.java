package addsynth.overpoweredmod.client.gui.tiles;

import addsynth.core.gui.objects.AdjustableButton;
import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.containers.ContainerGemConverter;
import addsynth.overpoweredmod.containers.ContainerGenerator;
import addsynth.overpoweredmod.network.NetworkHandler;
import addsynth.overpoweredmod.network.server_messages.CycleGemConverterMessage;
import addsynth.overpoweredmod.tiles.machines.automatic.TileGemConverter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiGemConverter extends GuiEnergyBase<ContainerGemConverter> {

  private final TileGemConverter tile;

  private int work_percentage;
  private int energy_percentage;

  private static final int work_percentage_x = 25;
  private static final int work_percentage_y = 88;
  private static final int energy_percentage_text_x = 156;
  private static final int energy_percentage_text_y = 28;
  private static final int time_left_y = 99;

  private static final int left_button_x = 64;
  private static final int cycle_button_y = 64;
  private static final int cycle_button_width = 10;
  private static final int cycle_button_buffer = 2;
  private static final int right_button_x = left_button_x + cycle_button_width + 16 + (cycle_button_buffer*2);
  private static final int cycle_button_height = 16;
  
  private static final int energy_bar_x = 148;
  private static final int energy_bar_y = 39;
  private static final int energy_bar_width = 17;
  private static final int energy_bar_height = 42;
  private static final int draw_energy_x = 204;
  private static final int draw_energy_y = 21;
  private final ProgressBar energy_progress_bar = new ProgressBar(energy_bar_x, energy_bar_y, energy_bar_width, energy_bar_height, draw_energy_x, draw_energy_y);

  private static final int work_bar_x = 43;
  private static final int work_bar_y = 89;
  private static final int work_bar_width = 122;
  private static final int work_bar_height = 5;
  private static final int draw_work_bar_x = 40;
  private static final int draw_work_bar_y = 199;
  private final ProgressBar work_progress_bar = new ProgressBar(work_bar_x, work_bar_y, work_bar_width, work_bar_height, draw_work_bar_x, draw_work_bar_y);
  
  public GuiGemConverter(final ContainerGemConverter container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/gem_converter.png"));
    this.tile = container.getTileEntity();
    this.ySize = 194;
  }

  @Override
  public final void init(){
    super.init();
    buttons.add(new AdjustableButton(0,this.guiLeft + left_button_x, this.guiTop + cycle_button_y,cycle_button_width,cycle_button_height,"<"));
    buttons.add(new AdjustableButton(1,this.guiLeft + right_button_x, this.guiTop + cycle_button_y,cycle_button_width,cycle_button_height,">"));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    draw_background_texture();
    final float energy_float = tile.getEnergyPercentage();
    energy_percentage = Math.round(energy_float*100);
    energy_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,energy_float,ProgressBar.Round.NEAREST);
    
    final float work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float*100);
    work_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
    
    this.itemRender.renderItemIntoGUI(tile.get_gem_selected(), this.guiLeft + left_button_x + 12, this.guiTop + cycle_button_y);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_text_center(energy_percentage + "%",energy_percentage_text_x,energy_percentage_text_y);
    draw_energy(tile.getEnergy());
    draw_status(tile.getStatus());
    draw_text_center(work_percentage + "%",work_percentage_x,work_percentage_y);
    draw_time_left(tile.getTotalTimeLeft(), time_left_y);
  }

  @Override
  protected final void actionPerformed(final GuiButton button){
    switch(button.id){
    case 0: NetworkHandler.INSTANCE.sendToServer(new CycleGemConverterMessage(tile.getPos(),false)); break;
    case 1: NetworkHandler.INSTANCE.sendToServer(new CycleGemConverterMessage(tile.getPos(),true)); break;
    }
  }
    
}
