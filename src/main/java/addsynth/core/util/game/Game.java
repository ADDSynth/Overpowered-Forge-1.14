package addsynth.core.util.game;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatisticsManager;
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

  /** I had to make my own function to help simplify this, because I couldn't call
   *  {@link StatisticsManager#getValue(net.minecraft.stats.StatType, Object)} because
   *  it's annotated with the @OnlyIn(Dist.CLIENT) annotation.
   * @param stat_manager
   * @param stat
   */
  public static final int getCustomStat(final StatisticsManager stat_manager, final ResourceLocation stat){
    return Stats.CUSTOM.contains(stat) ? stat_manager.getValue(Stats.CUSTOM.get(stat)) : 0;
  }

}
