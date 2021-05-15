package addsynth.energy.api.gui.widgets;

import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.api.network_messages.SwitchMachineMessage;
import addsynth.energy.api.tiles.machines.ISwitchableMachine;
import addsynth.energy.gameplay.NetworkHandler;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Draws a custom button which displays an on/off switch depending on the Machine's running state.
 * Currently, we only use this to toggle the running state of an EnergyReceiver machine.
 */
public final class OnOffSwitch<T extends TileEntity & ISwitchableMachine> extends AbstractButton {

  private final T tile;
  private static final ResourceLocation gui_switch = new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/gui_textures.png");

  private final String on_text  = StringUtil.translate("gui.addsynth_energy.switch.on");
  private final String off_text = StringUtil.translate("gui.addsynth_energy.switch.off");

  /**
   * Call with guiLeft + standard x = 6 and guiTop + standard y = 17.
   * @param x
   * @param y
   * @param tile
   */
  public OnOffSwitch(final int x, final int y, final T tile){
    super(x, y, 34, 16, "");
    this.tile = tile;
  }

  /**
   * Draws the button depending on the running boolean of the TileEnergyReceiver, otherwise it contains the same code
   * as Vanilla draws a GuiButton.
   */
  @Override
  public final void renderButton(final int mouseX, final int mouseY, final float partial_ticks){
    @SuppressWarnings("resource")
    final Minecraft minecraft = Minecraft.getInstance();
    int texture_y = 0;

    if(tile != null){
      if(tile.get_switch_state() == false){
        texture_y = 16;
      }
    }

    minecraft.getTextureManager().bindTexture(gui_switch);
    GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

    // this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    // final int hover_state = this.getHoverState(this.hovered);
    
    GlStateManager.enableBlend();
    GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

    this.blit(x, y, 0, texture_y, width, height);

    final FontRenderer fontrenderer = minecraft.fontRenderer;
    final int text_color = 14737632;
    if(tile != null){
      if(tile.get_switch_state()){
        setMessage(on_text);
        this.drawCenteredString(fontrenderer, on_text, x + 20, y + 4, text_color);
        // TODO: detect state changes and call setMessage() to change what the narrator says when players mouse over this button.
      }
      else{
        setMessage(off_text);
        this.drawCenteredString(fontrenderer, off_text, x + 14, y + 4, text_color);
      }
    }
    else{
      this.drawCenteredString(fontrenderer, "[null]", x + (this.width / 2), y + 4, text_color);
    }
  }

  @Override
  public final void onPress(){
    if(tile != null){
      NetworkHandler.INSTANCE.sendToServer(new SwitchMachineMessage(tile.getPos()));
    }
  }

}
