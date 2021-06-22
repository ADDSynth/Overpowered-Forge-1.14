package addsynth.core.util.game;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;

public final class Game {

  public static final SoundEvent newSound(final String mod_id, final String name){
    SoundEvent sound = new SoundEvent(new ResourceLocation(mod_id,name));
    sound.setRegistryName(name);
    return sound;
  }

  /** @see Stats#registerCustom(String, IStatFormatter) */
  public static final void registerCustomStat(final ResourceLocation stat){
    Registry.register(Registry.CUSTOM_STAT, stat.getPath(), stat);
    Stats.CUSTOM.get(stat, IStatFormatter.DEFAULT);
  }

}
