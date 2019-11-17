package addsynth.energy.gameplay.gui;

import java.io.IOException;
import addsynth.core.gui.objects.AdjustableButton;
import addsynth.core.gui.objects.ProgressBar;
import addsynth.core.inventory.container.BaseContainer;
import addsynth.energy.gameplay.tiles.TileUniversalEnergyTransfer;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.network.server_messages.CycleTransferModeMessage;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.network.NetworkHandler;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public final class GuiUniversalEnergyInterface extends GuiEnergyBase {

  private final TileUniversalEnergyTransfer tile;

  private AdjustableButton mode_button;
  private static final int button_width = 90;
  
  private final ProgressBar energy_bar = new ProgressBar(156,18,12,34,206, 28);
  
  private static final int line_1 = 21;
  private static final int line_2 = 41;

  public GuiUniversalEnergyInterface(final IInventory player_inventory, TileUniversalEnergyTransfer tile){
    super(new BaseContainer<>(tile),tile,new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/universal_energy_interface.png"));
    this.tile = tile;
    this.ySize = 60;
  }

  @Override
  public void initGui(){
    super.initGui();
    final int button_x = guiLeft + (this.xSize / 2) - (button_width / 2) + 4;
    mode_button = new AdjustableButton(button_x, guiTop + 17, button_width, 16, tile.get_transfer_mode().text);
    buttons.add(mode_button);
  }

  @Override
  public void updateScreen(){
    super.updateScreen();
    if(tile != null && mode_button != null){
      mode_button.setMessage(tile.get_transfer_mode().text);
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    energy_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,tile.getEnergyPercentage(),ProgressBar.Round.NEAREST);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    draw_title();
    draw_text_left("Mode:", 6, line_1);
    draw_text_left("Energy:", 6, line_2);
    draw_text_right(tile.getEnergy().getEnergy() + " / "+tile.getEnergy().getCapacity(), 130, line_2);
  }

  @Override
  protected void actionPerformed(AbstractButton button) throws IOException{
    switch(button.id){
    case 0: NetworkHandler.INSTANCE.sendToServer(new CycleTransferModeMessage(tile.getPos())); break;
    }
  }

}
