package addsynth.overpoweredmod.compatability.baubles;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class RingEffects {

  public static final byte SPEED           = 1;
  public static final byte STRENGTH        = 2;
  public static final byte HASTE           = 3;
  public static final byte LUCK            = 4;
  public static final byte EXTRA_HEALTH    = 5;
  public static final byte JUMP            = 6;
  public static final byte FIRE_IMMUNE     = 7;
  public static final byte WATER_BREATHING = 8;
  public static final byte NIGHT_VISION    = 9;
  public static final byte INVISIBILITY    = 10;


  public static final void set_ring_effects(final int ring_type, final ItemStack stack){
    switch(ring_type){
    case 0: common_ring_effects(stack); break;
    case 1: good_ring_effects(stack); break;
    case 2: rare_ring_effects(stack); break;
    case 3: unique_ring_effects(stack); break;
    }
  }

  public static final void common_ring_effects(final ItemStack stack){
    Random random = new Random();
    switch(random.nextInt(4)){
    case 0: write_nbt(stack,SPEED,1); break;
    case 1: write_nbt(stack,HASTE,1); break;
    case 2: write_nbt(stack,LUCK,1); break;
    case 3: write_nbt(stack,EXTRA_HEALTH,1); break;
    }
  }

  public static final void good_ring_effects(final ItemStack stack){
    Random random = new Random();
    switch(random.nextInt(6)){
    case 0: write_nbt(stack,SPEED,2); break;
    case 1: write_nbt(stack,HASTE,2); break;
    case 2: write_nbt(stack,LUCK,2); break;
    case 3: write_nbt(stack,EXTRA_HEALTH,2); break;
    case 4: write_nbt(stack,STRENGTH,1); break;
    case 5: write_nbt(stack,JUMP,1); break;
    }
  }
  
  public static final void rare_ring_effects(final ItemStack stack){
    Random random = new Random();
    switch(random.nextInt(6)){
    case 0: write_nbt(stack,SPEED,3); break;
    case 1: write_nbt(stack,HASTE,3); break;
    case 2: write_nbt(stack,LUCK,3); break;
    case 3: write_nbt(stack,EXTRA_HEALTH,3); break;
    case 4: write_nbt(stack,STRENGTH,2); break;
    case 5: write_nbt(stack,JUMP,2); break;
    }
  }
  
  public static final void unique_ring_effects(final ItemStack stack){
    Random random = new Random();
    switch(random.nextInt(8)){
    case 0: write_nbt(stack,SPEED,4); break;
    case 1: write_nbt(stack,EXTRA_HEALTH,4); break;
    case 2: write_nbt(stack,STRENGTH,3); break;
    case 3: write_nbt(stack,JUMP,3); break;
    case 4: write_nbt(stack,FIRE_IMMUNE,1); break;
    case 5: write_nbt(stack,WATER_BREATHING,1); break;
    case 6: write_nbt(stack,NIGHT_VISION,1); break;
    case 7: write_nbt(stack,INVISIBILITY,1); break;
    }
  }

  /**
   * This method is very similar to the addEnchantment() method in the ItemStack class.
   */
  public static final void write_nbt(final ItemStack stack, final byte effect, final int level){
  
    NBTTagCompound nbt = stack.getTagCompound(); // get current compound
    if(nbt == null){
      nbt = new NBTTagCompound(); // if null, create new compound
    }
    
    nbt.setByte("RingEffect",effect); // add to compound
    nbt.setInteger("RingEffectLevel",level);
    stack.setTagCompound(nbt); // return compound to item stack
  }

  public static final byte get_ring_effect(final ItemStack stack){
    NBTTagCompound tag = stack.getTagCompound();
    if(tag != null){
      if(tag.hasKey("RingEffect")){
        return tag.getByte("RingEffect");
      }
    }
    return 0;
  }
  
  public static final int get_ring_effect_level(final ItemStack stack){
    NBTTagCompound tag = stack.getTagCompound();
    if(tag != null){
      if(tag.hasKey("RingEffectLevel")){
        return tag.getInteger("RingEffectLevel");
      }
    }
    return 0;
  }

}
