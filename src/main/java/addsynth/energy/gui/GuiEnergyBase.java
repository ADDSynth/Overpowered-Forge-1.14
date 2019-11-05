package addsynth.energy.gui;

import addsynth.core.gui.GuiBase;
import addsynth.core.util.StringUtil;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.TileEnergyWithStorage;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiEnergyBase extends GuiBase {

  public GuiEnergyBase(final Container container, final TileEnergyWithStorage tile, final ResourceLocation gui_texture_location){
    super(container, tile, gui_texture_location);
  }

  /** Draws Energy: Level / Capacity in the standard location, just below the title, at y = 17 pixels.
   * 
   * @param energy
   */
  protected final void draw_energy(final CustomEnergyStorage energy){
    this.draw_energy(energy, 6, 17);
  }

  protected final void draw_energy_after_switch(final CustomEnergyStorage energy){
    this.draw_energy(energy, 44, 21);
  }

  protected final void draw_energy(final CustomEnergyStorage energy, final int draw_x, final int draw_y){
    if(energy != null){
      draw_text_left("Energy:",draw_x,draw_y);
      draw_text_right(energy.getEnergy() + " / " + energy.getCapacity(),this.xSize - 6, draw_y);
    }
    else{
      draw_text_center("[Error: null EnergyStorage reference]",(draw_x + this.xSize - 6) / 2, draw_y);
    }
  }

  /** Draws the status at the default location, below the energy capacity line. */
  protected final void draw_status(final String status){
    draw_text_left("Status: "+status, 6, 28);
  }

  protected final void draw_status(final String status, final int draw_y){
    draw_text_left("Status: "+status, 6, draw_y);
  }

  protected final void draw_time_left(final int time_left, final int draw_y){
    draw_text_left("Time Left: "+StringUtil.print_time(time_left), 6, draw_y);
  }

  protected final void draw_energy_difference(final int difference, final CustomEnergyStorage energy, final int draw_y){
    switch((int)Math.signum(difference)){
    case 1:
      draw_text_left("Time to Full Charge: "+StringUtil.print_time((int)Math.ceil((double)energy.getEnergyNeeded() / difference)), 6, draw_y);
      break;
    case -1:
      draw_text_left("Charge Time Remaining: "+StringUtil.print_time((int)Math.ceil((double)energy.getEnergy() / (-difference))), 6, draw_y);
      break;
    case 0:
      draw_text_left("No Energy Change.", 6, draw_y);
      break;
    }
  }

}
