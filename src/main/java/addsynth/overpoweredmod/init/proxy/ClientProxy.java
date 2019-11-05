package addsynth.overpoweredmod.init.proxy;

import addsynth.core.game.IProxy;
// import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.assets.OverpoweredCreativeTabs;

public final class ClientProxy implements IProxy {

  @Override
  public final void preinit(){
    // Debug.log_setup_info("Begin Client Proxy PreInit...");
    OverpoweredCreativeTabs.register();
    // Debug.log_setup_info("Finished Client Proxy PreInit.");
  }

  @Override
  public final void init(){
    // Debug.log_setup_info("Begin Client Proxy Init...");
    // Debug.log_setup_info("Finished Client Proxy Init.");
  }

}
