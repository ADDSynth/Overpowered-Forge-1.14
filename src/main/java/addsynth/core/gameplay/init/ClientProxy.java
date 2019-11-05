package addsynth.core.gameplay.init;

import addsynth.core.game.IProxy;

public final class ClientProxy implements IProxy {

  @Override
  public void preinit(){
    Setup.setup_creative_tab();
  }

  @Override
  public void init(){
  }

}
