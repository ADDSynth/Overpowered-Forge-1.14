package addsynth.overpoweredmod.client.gui.tiles;

import addsynth.core.gui.GuiBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.containers.ContainerFusionChamber;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionChamber;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public final class GuiSingularityContainer extends GuiBase {

  public GuiSingularityContainer(IInventory player_inventory, TileFusionChamber tile) {
    super(new ContainerFusionChamber(player_inventory, tile),tile,new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/portal_frame.png"));
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    super.draw_title();
  }

}
