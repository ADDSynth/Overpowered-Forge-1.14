package addsynth.energy.lib.gui.widgets;

import addsynth.core.gui.widgets.buttons.Checkbox;
import addsynth.core.util.StringUtil;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.lib.network_messages.ToggleAutoShutoffMessage;
import addsynth.energy.lib.tiles.machines.IAutoShutoff;
import net.minecraft.tileentity.TileEntity;

public final class AutoShutoffCheckbox<T extends TileEntity & IAutoShutoff> extends Checkbox {

  private final T tile;
  
  public AutoShutoffCheckbox(int x, int y, T tile){
    super(x, y, StringUtil.translate("gui.addsynth_energy.common.auto_shutoff"));
    this.tile = tile;
  }
  
  @Override
  protected boolean get_toggle_state(){
    return tile.getAutoShutoff();
  }
  
  @Override
  public void onPress(){
    NetworkHandler.INSTANCE.sendToServer(new ToggleAutoShutoffMessage(tile.getPos()));
  }

}
