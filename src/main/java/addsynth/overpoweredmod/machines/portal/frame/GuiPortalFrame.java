package addsynth.overpoweredmod.machines.portal.frame;

import addsynth.core.gui.GuiContainerBase;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiPortalFrame extends GuiContainerBase<ContainerPortalFrame> {

  private static final ResourceLocation portal_frame_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/portal_frame.png");

  public GuiPortalFrame(final ContainerPortalFrame container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, portal_frame_gui_texture);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
  }

}
