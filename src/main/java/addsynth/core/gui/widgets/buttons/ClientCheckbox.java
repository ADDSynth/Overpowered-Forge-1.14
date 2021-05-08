package addsynth.core.gui.widgets.buttons;

public class ClientCheckbox extends Checkbox {

  public boolean checked;

  public ClientCheckbox(int x, int y, String text){
    super(x, y, text);
  }

  @Override
  protected boolean get_toggle_state(){
    return checked;
  }

  @Override
  public void onPress(){
    checked = !checked;
  }

}
