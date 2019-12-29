package addsynth.overpoweredmod.machines.fusion.chamber;

import addsynth.core.gui.GuiBase;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiFusionChamber extends GuiBase<ContainerFusionChamber> {

  public GuiFusionChamber(final ContainerFusionChamber container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/portal_frame.png"));
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
  }

}
