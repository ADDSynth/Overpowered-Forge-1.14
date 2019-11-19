package addsynth.core.util;

import java.util.Arrays;
import java.util.Collection;
import addsynth.core.ADDSynthCore;
import addsynth.core.Constants;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class StringUtil {

  /**
   * Generally I would only use this if the class already included this Utility class for something else,
   *  otherwise just put the code in-place of where you need it.
   * @param input_string
   */
  public static final boolean StringExists(final String input_string){
    if(input_string != null){
      if(input_string.trim().length() > 0){
        return true;
      }
    }
    return false;
  }

  // https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#toLowerCase-java.util.Locale-
  public static final String Capitalize(final String input_string){
    return Character.toUpperCase(input_string.charAt(0)) + input_string.substring(1);
  }

  public static final String print_time(final int ticks){
    final int seconds = (int)Math.ceil(((double)ticks) / Constants.ticks_per_second);
    final int minutes = (int)Math.floor(((double)seconds) / 60);
    final int hours = (int)Math.floor(((double)minutes) / 60);
    return hours+"h "+(minutes % 60)+"m "+(seconds % 60)+"s";
  }

  /** <p>Prints the Types of your array, such as <code>Class, Object, Integer, Short, Long,
   *  Float, Double, Boolean, String, </code>or <code> Character</code>.
   *  <p>Note: If you need to print a standard array, use {@link Arrays#deepToString(Object[])}.
   * @param array
   */
  public static final String print_type_array(final Object[] array){
    if(array == null){ return "null"; }
    final StringBuilder output = new StringBuilder("[");
    int i;
    for(i = 0; i < array.length; i++){
      if(array[i] == null){
        output.append("null");
      }
      else{
        if(array[i] instanceof Class){
          output.append(((Class)array[i]).getSimpleName());
        }
        else{
          output.append(array[i].getClass().getSimpleName());
        }
      }
      if(i + 1 < array.length){
        output.append(", ");
      }
    }
    output.append("]");
    return output.toString();
  }

  public static final String print_minecraft_array(final Collection array){
    if(array == null){ return "null"; }
    return print_minecraft_array(array.toArray());
  }

  /** <p>This is used to better represent an array of Minecraft types such as Item, Block, and ItemStacks.
   *  <p>Note: If you need to print a standard array, use {@link Arrays#deepToString(Object[])}.
   * @param array
   */
  public static final String print_minecraft_array(final Object[] array){
    if(array == null){ return "null"; }
    final StringBuilder output = new StringBuilder("[");
    int i;
    for(i = 0; i < array.length; i++){
      if(array[i] == null){
        ADDSynthCore.log.error(new NullPointerException("Found null object in array argument for StringUtil.print_minecraft_array()."));
        output.append("null");
      }
      else{
        if(array[i] instanceof Item){
          output.append(getName((Item)array[i]));
        }
        else{
          if(array[i] instanceof Block){
            output.append(getName((Block)array[i]));
          }
          else{
            if(array[i] instanceof ItemStack){
              output.append(getName(((ItemStack)array[i]).getItem()));
            }
            else{
              ADDSynthCore.log.error(new IllegalArgumentException("An object in the array is of the wrong type. StringUtil.print_minecraft_array() only recognizes arrays of Item, Block, or ItemStack objects."));
              output.append(array[i].toString());
            }
          }
        }
      }
      if(i + 1 < array.length){
        output.append(", ");
      }
    }
    output.append("]");
    return output.toString();
  }

  public static final String getName(final Item item){
    if(item != null){
      final ResourceLocation registry_name = item.getRegistryName();
      if(registry_name == null){
        final String unlocalized_name = item.getTranslationKey();
        if(unlocalized_name.equals("item.null")){
          return item.getClass().getSimpleName();
        }
        return unlocalized_name;
      }
      return registry_name.toString();
    }
    return "null";
  }

  public static final String getName(final Block block){
    if(block != null){
      final ResourceLocation registry_name = block.getRegistryName();
      if(registry_name == null){
        final String unlocalized_name = block.getTranslationKey();
        if(unlocalized_name.equals("tile.null")){
          return block.getClass().getSimpleName();
        }
        return unlocalized_name;
      }
      return registry_name.toString();
    }
    return "null";
  }

}
