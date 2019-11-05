package addsynth.overpoweredmod.assets;

import net.minecraft.util.DamageSource;

public final class DamageSources {

  // https://github.com/micdoodle8/Galacticraft/blob/master/src/main/java/micdoodle8/mods/galacticraft/core/util/DamageSourceGC.java

  public static final DamageSource black_hole = new DamageSource("black_hole").setDamageBypassesArmor().setDamageIsAbsolute();
  public static final DamageSource corrupted  = new DamageSource("corrupted" ).setDamageBypassesArmor().setDamageIsAbsolute();
  // public static final DamageSource corrupted_by_player = new DamageSource("corrupted_by_player").setDamageBypassesArmor();
  public static final DamageSource laser = new DamageSource("laser").setFireDamage();

}
