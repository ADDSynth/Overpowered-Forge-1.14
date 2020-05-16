package addsynth.energy.gameplay.energy_storage;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gui.GuiEnergyBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiEnergyStorageContainer extends GuiEnergyBase<TileEnergyStorage, ContainerEnergyStorage> {

  private float energy_float;
  private static final int draw_energy_text_y = 25;
  private static final int draw_energy_percentage_y = 36;
  private final ProgressBar energy_bar = new ProgressBar(9,48,174,17,9,95);

  public GuiEnergyStorageContainer(final ContainerEnergyStorage container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/energy_storage.png"));
    this.xSize = 190;
    this.ySize = 83;
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    
    if(energy != null){
      energy_float = energy.getEnergyPercentage();
      energy_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,energy_float,ProgressBar.Round.NEAREST);
    }
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_text_center("Energy Stored: "+energy.getEnergy()+" / "+energy.getCapacity(),this.xSize / 2, draw_energy_text_y);
    draw_text_center(Math.round(energy_float*100) + "%",this.xSize / 2, draw_energy_percentage_y);
    draw_energy_difference(69);
  }

}
