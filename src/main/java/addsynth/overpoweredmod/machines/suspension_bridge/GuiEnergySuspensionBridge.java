package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.Constants;
import addsynth.core.gui.objects.AdjustableButton;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.NetworkHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiEnergySuspensionBridge extends GuiEnergyBase<TileSuspensionBridge, ContainerSuspensionBridge> {

  private static final ResourceLocation energy_suspension_bridge_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID, "textures/gui/energy_suspension_bridge.png");

  private static final int gui_width = 206;

  private static final int lens_text_x = (6 + ContainerSuspensionBridge.lens_slot_x) / 2;
  private static final int lens_text_y = 24;
  private static final int[] text_x = {6, 38, gui_width / 2, 132};
  private static final int[] text_y = {lens_text_y + 16, lens_text_y + 27, lens_text_y + 38};

  private static final int button_width = 50;
  private static final int button_x = gui_width - 6 - button_width;
  private static final int button_y = 17;

  private static final class RotateButton extends AdjustableButton {

    private final TileSuspensionBridge tile;

    public RotateButton(int x, int y, TileSuspensionBridge tile){
      super(x, y, button_width, 28, "Rotate");
      this.tile = tile;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new RotateBridgeMessage(tile.getPos()));
    }
  }

  public GuiEnergySuspensionBridge(final ContainerSuspensionBridge container, final PlayerInventory inventory, final ITextComponent title){
    super(gui_width, 167, container, inventory, title, energy_suspension_bridge_gui_texture);
  }

  @Override
  public final void init(){
    super.init();
    addButton(new RotateButton(this.guiLeft + button_x, this.guiTop + button_y, tile));
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
    draw_text_center("Lens:", lens_text_x, lens_text_y);
    draw_text_left("North:", text_x[0], text_y[0]);
    draw_text_left("South:", text_x[0], text_y[1]);
    draw_text_left("West: ", text_x[0], text_y[2]);
    draw_text_left(tile.getMessage(Constants.NORTH), text_x[1], text_y[0]);
    draw_text_left(tile.getMessage(Constants.SOUTH), text_x[1], text_y[1]);
    draw_text_left(tile.getMessage(Constants.WEST),  text_x[1], text_y[2]);
    draw_text_left("Up:   ", text_x[2], text_y[0]);
    draw_text_left("Down: ", text_x[2], text_y[1]);
    draw_text_left("East: ", text_x[2], text_y[2]);
    draw_text_left(tile.getMessage(Constants.UP),    text_x[3], text_y[0]);
    draw_text_left(tile.getMessage(Constants.DOWN),  text_x[3], text_y[1]);
    draw_text_left(tile.getMessage(Constants.EAST),  text_x[3], text_y[2]);
    draw_text_center(tile.getBridgeMessage(), 73);
  }

}
