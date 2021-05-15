package addsynth.energy.gameplay.machines.universal_energy_interface;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.api.gui.GuiEnergyBase;
import addsynth.energy.api.gui.widgets.EnergyProgressBar;
import addsynth.energy.gameplay.NetworkHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiUniversalEnergyInterface extends GuiEnergyBase<TileUniversalEnergyTransfer, ContainerUniversalInterface> {

  private static final ResourceLocation universal_interface_gui_texture =
    new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/universal_energy_interface.png");

  private final String mode_text   = StringUtil.translate("gui.addsynth_energy.common.mode");
  private final String energy_text = StringUtil.translate("gui.addsynth_energy.common.energy");

  private static final int button_width = 90;
  private final EnergyProgressBar energy_bar = new EnergyProgressBar(156, 18, 12, 34, 206, 28);
  
  private static final int line_1 = 21;
  private static final int line_2 = 41;

  public GuiUniversalEnergyInterface(final ContainerUniversalInterface container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 60, container, player_inventory, title, universal_interface_gui_texture);
  }

  private static final class CycleTransferModeButton extends AdjustableButton {

    private final TileUniversalEnergyTransfer tile;

    public CycleTransferModeButton(int xIn, int yIn, TileUniversalEnergyTransfer tile){
      super(xIn, yIn, button_width, 16);
      this.tile = tile;
    }

    @Override
    public final void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      setMessage(tile.get_transfer_mode().toString());
      super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new CycleTransferModeMessage(tile.getPos()));
    }

  }

  @Override
  public final void init(){
    super.init();
    final int button_x = guiLeft + guiUtil.center_x - (button_width / 2) + 4;
    addButton(new CycleTransferModeButton(button_x, guiTop + 17, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture();
    energy_bar.drawVertical(this, energy);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
    GuiUtil.draw_text_left(mode_text+":", 6, line_1);
    GuiUtil.draw_text_left(energy_text+":", 6, line_2);
    GuiUtil.draw_text_right(energy.getEnergy() + " / "+energy.getCapacity(), 130, line_2);
  }

}
