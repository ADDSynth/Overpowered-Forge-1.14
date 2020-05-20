package addsynth.energy.gui;

import addsynth.core.container.BaseContainer;
import addsynth.core.gui.GuiBase;
import addsynth.core.util.StringUtil;
import addsynth.energy.Energy;
import addsynth.energy.tiles.IEnergyUser;
import addsynth.energy.tiles.machines.TileWorkMachine;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiEnergyBase<T extends TileEntity & IEnergyUser, C extends BaseContainer> extends GuiBase<C> {

  protected final T tile;
  protected final Energy energy;

  @SuppressWarnings("unchecked")
  public GuiEnergyBase(final C container, final PlayerInventory player_inventory, final ITextComponent title, final ResourceLocation gui_texture_location){
    super(container, player_inventory, title, gui_texture_location);
    this.tile = (T)container.getTileEntity();
    // this.energy = tile instanceof IEnergyUser ? ((IEnergyUser)tile).getEnergy() : null;
    this.energy = tile.getEnergy();
  }

  /** Draws Energy: Level / Capacity in the standard location, just below the title, at y = 17 pixels.
   * 
   * @param energy
   */
  protected final void draw_energy(){
    this.draw_energy(6, 17);
  }

  protected final void draw_energy_after_switch(){
    this.draw_energy(44, 21);
  }

  protected final void draw_energy(final int draw_x, final int draw_y){
    if(energy != null){
      draw_text_left("Energy:",draw_x,draw_y);
      draw_text_right(String.format("%.2f",energy.getEnergy()) + " / " + energy.getCapacity(),this.xSize - 6, draw_y);
    }
    else{
      draw_text_center("[Error: null EnergyStorage reference]",(draw_x + this.xSize - 6) / 2, draw_y);
    }
  }

  protected final void draw_energy_usage(){
    this.draw_energy_usage(6, 17);
  }
  
  protected final void draw_energy_usage_after_switch(){
    this.draw_energy_usage(44, 21);
  }
  
  protected final void draw_energy_usage(final int draw_x, final int draw_y){
    if(energy != null){
      draw_text_left("Energy Usage:", draw_x, draw_y);
      draw_text_right(String.format("%.2f", energy.get_energy_in()) + " /tick", this.xSize - 6, draw_y);
    }
    else{
      draw_text_left("[Error: null Energy reference]", draw_x, draw_y);
    }
  }

  /** Draws the status at the default location, below the energy capacity line. */
  protected final void draw_status(final String status){
    draw_text_left("Status: "+status, 6, 28);
  }

  protected final void draw_status_below_switch(final String status){
    draw_text_left("Status: "+status, 6, 37);
  }

  protected final void draw_time_left(final TileWorkMachine tile, final int draw_y){
    draw_text_left("Time Left: "+tile.getTotalTimeLeft(), 6, draw_y);
  }

  protected final void draw_energy_difference(final int draw_y){
    if(energy == null){
      draw_text_left("[Error: null Energy object]", 6, draw_y);
      return;
    }
    final double difference = energy.getDifference();
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
