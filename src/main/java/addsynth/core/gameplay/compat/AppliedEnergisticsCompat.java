package addsynth.core.gameplay.compat;

import addsynth.core.gameplay.music_box.TileMusicBox;
import appeng.api.AEAddon;
import appeng.api.IAEAddon;
import appeng.api.IAppEngApi;
import appeng.api.movable.IMovableRegistry;

@AEAddon
public final class AppliedEnergisticsCompat implements IAEAddon {

  @Override
  public void onAPIAvailable(IAppEngApi api){
    final IMovableRegistry moveable_registry = api.registries().movable();
    moveable_registry.whiteListTileEntity(TileMusicBox.class);
  }

}
