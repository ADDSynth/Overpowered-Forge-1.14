package addsynth.core.game;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class Game {

  public static final SoundEvent newSound(final String mod_id, final String name){
    SoundEvent sound = new SoundEvent(new ResourceLocation(mod_id,name));
    sound.setRegistryName(name);
    return sound;
  }

}
