package addsynth.overpoweredmod.machines.portal.frame;

import addsynth.core.gui.GuiBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiPortalFrame extends GuiBase<ContainerPortalFrame> {

  private static final ResourceLocation portal_frame_gui = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/portal_frame.png");

  public GuiPortalFrame(final ContainerPortalFrame container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, portal_frame_gui);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
  }

}
