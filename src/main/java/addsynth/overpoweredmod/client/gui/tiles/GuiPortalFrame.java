package addsynth.overpoweredmod.client.gui.tiles;

import addsynth.core.gui.GuiBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.containers.ContainerPortalFrame;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public final class GuiPortalFrame extends GuiBase {

  public GuiPortalFrame(IInventory player_inventory, TilePortalFrame tile) {
    super(new ContainerPortalFrame(player_inventory, tile),tile,new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/portal_frame.png"));
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.draw_title();
  }

}
