package addsynth.overpoweredmod.machines.suspension_bridge;

import addsynth.core.Constants;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.StringUtil;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.NetworkHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiEnergySuspensionBridge extends GuiEnergyBase<TileSuspensionBridge, ContainerSuspensionBridge> {

  private static final ResourceLocation energy_suspension_bridge_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID, "textures/gui/energy_suspension_bridge.png");

  // translation strings
  private final String lens_string = StringUtil.translate("gui.overpowered.energy_suspension_bridge.lens");
  private final String down  = StringUtil.translate("gui.addsynthcore.direction.down");
  private final String up    = StringUtil.translate("gui.addsynthcore.direction.up");
  private final String north = StringUtil.translate("gui.addsynthcore.direction.north");
  private final String south = StringUtil.translate("gui.addsynthcore.direction.south");
  private final String west  = StringUtil.translate("gui.addsynthcore.direction.west");
  private final String east  = StringUtil.translate("gui.addsynthcore.direction.east");

  private static final int gui_width = 206;

  private static final int lens_text_x = (6 + ContainerSuspensionBridge.lens_slot_x) / 2;
  private static final int lens_text_y = 24;
  private final int[] text_x = {
                   6,                6 + GuiUtil.getMaxStringWidth(north+": ", south+": ", west+": "),
    guiUtil.center_x, guiUtil.center_x + GuiUtil.getMaxStringWidth(up+": ", down+": ", east+": ")
  };
  private static final int[] text_y = {lens_text_y + 16, lens_text_y + 27, lens_text_y + 38};

  private static final int button_width = 50;
  private static final int button_x = gui_width - 6 - button_width;
  private static final int button_y = 17;

  private static final class RotateButton extends AdjustableButton {

    private final TileSuspensionBridge tile;

    public RotateButton(int x, int y, TileSuspensionBridge tile){
      super(x, y, button_width, 28, StringUtil.translate("gui.overpowered.energy_suspension_bridge.rotate"));
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
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_center(lens_string+":", lens_text_x, lens_text_y);
    GuiUtil.draw_text_left(north+":", text_x[0], text_y[0]);
    GuiUtil.draw_text_left(south+":", text_x[0], text_y[1]);
    GuiUtil.draw_text_left(west+":",  text_x[0], text_y[2]);
    GuiUtil.draw_text_left(tile.getMessage(Constants.NORTH), text_x[1], text_y[0]);
    GuiUtil.draw_text_left(tile.getMessage(Constants.SOUTH), text_x[1], text_y[1]);
    GuiUtil.draw_text_left(tile.getMessage(Constants.WEST),  text_x[1], text_y[2]);
    GuiUtil.draw_text_left(up+":",   text_x[2], text_y[0]);
    GuiUtil.draw_text_left(down+":", text_x[2], text_y[1]);
    GuiUtil.draw_text_left(east+":", text_x[2], text_y[2]);
    GuiUtil.draw_text_left(tile.getMessage(Constants.UP),    text_x[3], text_y[0]);
    GuiUtil.draw_text_left(tile.getMessage(Constants.DOWN),  text_x[3], text_y[1]);
    GuiUtil.draw_text_left(tile.getMessage(Constants.EAST),  text_x[3], text_y[2]);
    guiUtil.draw_text_center(tile.getBridgeMessage(), 73);
  }

}
