package addsynth.energy.gameplay.gui;

import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.tiles.TileEnergyBattery;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.containers.ContainerEnergyStorage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiEnergyStorageContainer extends GuiEnergyBase<ContainerEnergyStorage> {

  private final TileEnergyBattery tile;
  private CustomEnergyStorage tile_energy;

  private float energy_float;
  private static final int draw_energy_text_y = 25;
  private static final int draw_energy_percentage_y = 36;
  private final ProgressBar energy_bar = new ProgressBar(9,48,174,17,9,95);

  public GuiEnergyStorageContainer(final ContainerEnergyStorage container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/energy_storage.png"));
    this.tile = container.getTileEntity();
    this.xSize = 190;
    this.ySize = 83;
  }

  @Override
  public final void init(){
    super.init();
    tile_energy = tile.getEnergy(); // FUTURE OPTIMIZE
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    
    if(tile_energy != null){
      energy_float = tile.getEnergyPercentage();
      energy_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,energy_float,ProgressBar.Round.NEAREST);
    }
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_text_center("Energy Stored: "+tile_energy.getEnergy()+" / "+tile_energy.getCapacity(),this.xSize / 2, draw_energy_text_y);
    draw_text_center(Math.round(energy_float*100) + "%",this.xSize / 2, draw_energy_percentage_y);
    draw_energy_difference(tile_energy.getEnergyDifference(), tile_energy, 69);
  }

}
