package addsynth.overpoweredmod.compatability.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.Constants;
import addsynth.overpoweredmod.compatability.baubles.RingEffects;
import addsynth.overpoweredmod.items.OverpoweredItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public final class Ring extends OverpoweredItem implements IBauble {

  private final int type;

  public Ring(final int ring, final String name){
    super(name);
    this.type = ring;
    setMaxStackSize(1);
  }

  @Override
  public EnumRarity getForgeRarity(ItemStack stack){
    return EnumRarity.values()[type];
  }

  @Override
  public boolean hasEffect(ItemStack stack){
    return type == 3;
  }

  @Override
  public BaubleType getBaubleType(ItemStack arg0){
    return BaubleType.RING;
  }

  @Override
  public void onEquipped(ItemStack itemstack, EntityLivingBase player){
    // OPTIMIZE: equip and unequip functions are currently the most efficient way to do this, set duration
    // to Integer.MAX and it will go for 3.4 years at 20 ticks per second. Remove the effect on Unequip, example below.
  }

  /**
   * This is exactly how Baubles adds the Haste Effect to its example Ring. Even though Status Effects with a
   * duration time greater than 20 minutes will show as **:** the internal 'time' variable is counting down each
   * step. Therefore, no matter how high you set the duration, all effects will eventually go away. To counter
   * this, check the total time the entity has existed modulo an amount of time and set the Mob Effect.
   * @since June 11, 2019 - Not anymore, I've changed it to update as quick as possible.
   */
  @Override
  public void onWornTick(ItemStack itemstack, EntityLivingBase player){
    final int level = RingEffects.get_ring_effect_level(itemstack);
    switch(RingEffects.get_ring_effect(itemstack)){
    case RingEffects.STRENGTH:        player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,       2,level - 1, false, false)); break;
    case RingEffects.SPEED:           player.addPotionEffect(new PotionEffect(MobEffects.SPEED,          2,level - 1, false, false)); break;
    case RingEffects.LUCK:            player.addPotionEffect(new PotionEffect(MobEffects.LUCK,           2,level - 1, false, false)); break;
    case RingEffects.EXTRA_HEALTH:    add_health(player,level); break;
    case RingEffects.JUMP:            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST,     2,level - 1, false, false)); break;
    case RingEffects.HASTE:           player.addPotionEffect(new PotionEffect(MobEffects.HASTE,          2,level - 1, false, false)); break;
    case RingEffects.FIRE_IMMUNE:     player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,2,0, false, false)); break;
    case RingEffects.WATER_BREATHING: player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING,2,0, false, false)); break;
    case RingEffects.NIGHT_VISION:    player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,   2,0, false, false)); break;
    case RingEffects.INVISIBILITY:    player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY,   2,0, false, false)); break;
    }
  }

  private static final void add_health(final EntityLivingBase player, final int level){
    final PotionEffect effect = player.getActivePotionEffect(MobEffects.HEALTH_BOOST);
    if(effect != null){
      if(effect.getDuration() > Constants.ticks_per_second * 10){
        return;
      }
    }
    player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST,Integer.MAX_VALUE,level - 1, false, false));
  }

  @Override    // Yes, this does run every tick the Tooltip is showing.
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    final byte effect = RingEffects.get_ring_effect(stack);
    String string = null;
    switch(effect){
    case RingEffects.STRENGTH:     string = "Strength"; break;
    case RingEffects.SPEED:        string = "Speed"; break;
    case RingEffects.HASTE:        string = "Haste"; break;
    case RingEffects.LUCK:         string = "Luck"; break;
    case RingEffects.EXTRA_HEALTH: string = "Extra Health"; break;
    case RingEffects.JUMP:         string = "Jump Skill"; break;
    case RingEffects.FIRE_IMMUNE:  string = "Immune to Fire"; break;
    case RingEffects.WATER_BREATHING: string = "Water Breathing"; break;
    case RingEffects.NIGHT_VISION: string = "Night Vision"; break;
    case RingEffects.INVISIBILITY: string = "Invisibility"; break;
    }
    if(effect != RingEffects.FIRE_IMMUNE && effect != RingEffects.WATER_BREATHING &&
       effect != RingEffects.NIGHT_VISION && effect != RingEffects.INVISIBILITY){
      string += " Lv "+RingEffects.get_ring_effect_level(stack);
    }
    if(string != null){
      tooltip.add(TextFormatting.GRAY + string);
    }
  }

  @Override
  public void onUnequipped(final ItemStack stack, final EntityLivingBase player){
    final byte effect = RingEffects.get_ring_effect(stack);
    if(effect == RingEffects.EXTRA_HEALTH){
      player.removePotionEffect(MobEffects.HEALTH_BOOST);
    }
  }

}
