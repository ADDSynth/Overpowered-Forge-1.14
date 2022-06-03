package addsynth.core.game.items.enchantment;

import net.minecraft.enchantment.Enchantment;

public final class EnchantPair {

  public final Enchantment enchantment;
  public final int level;

  public EnchantPair(final Enchantment enchantment, final int level){
    this.enchantment = enchantment;
    this.level = level;
  }

}
