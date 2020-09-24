package addsynth.core.util;

import java.util.Arrays;
import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;

public final class ArrayUtil {

  public static final boolean isInsideBounds(final int i, final int max_length){
    return i >= 0 && i < max_length;
  }

  /** This will attempt to return the value at index in the array.
   *  Throws an error if array is null or of length 0.
   *  Returns value at index 0 if index is out of bounds.
   * @param array
   * @param index
   */
  public static final <T> T getArrayValue(final T[] array, final int index){
    return getArrayValue(array, index, false);
  }

  /** This will attempt to return the value at index in the array.
   *  Throws an error if array is null or of length 0.
   *  Returns value at index 0 if index is out of bounds.
   *  Logs a warning that index was out of bounds if warn is set to true.
   * @param array
   * @param index
   * @param warn
   */
  public static final <T> T getArrayValue(final T[] array, final int index, final boolean warn){
    if(array == null){
      throw new NullPointerException("Input array for "+ArrayUtil.class.getName()+".getArrayValue() is null!");
    }
    final int length = array.length;
    if(length == 0){
      throw new IllegalArgumentException("Input array for "+ArrayUtil.class.getName()+".getArrayValue() doesn't have any values!");
    }
    if(isInsideBounds(index, length) == false){
      final T default_value = array[0];
      if(warn){
        print_array_index_out_of_bounds_error(array, index);
        ADDSynthCore.log.warn("Returning the assumed default value in index 0 instead!");
      }
      return default_value;
    }
    return array[index];
  }

  /**
   * Returns the value at index in the array. Returns the default value if
   * any error occured.
   * @param array
   * @param index
   * @param default_value
   */
  public static final <T> T getArrayValue(final T[] array, final int index, final T default_value){
    return getArrayValue(array, index, default_value, false);
  }

  /**
   * Returns the value at index in the array. Returns the default value if
   * any error occured.
   * Logs a warning that index was out of bounds if warn is true.
   * @param array
   * @param index
   * @param default_value
   * @param warn
   */
  public static final <T> T getArrayValue(final T[] array, final int index, final T default_value, final boolean warn){
    if(array == null){
      if(warn){
        ADDSynthCore.log.error(new NullPointerException("Input array for "+ArrayUtil.class.getName()+".getArrayValue() is null!"));
      }
      return default_value;
    }
    final int length = array.length;
    if(length == 0){
      if(warn){
        ADDSynthCore.log.error(new IllegalArgumentException("Input array for "+ArrayUtil.class.getName()+".getArrayValue() doesn't have any values!"));
      }
      return default_value;
    }
    if(isInsideBounds(index, length) == false){
      if(warn){
        print_array_index_out_of_bounds_error(array, index);
      }
      return default_value;
    }
    return array[index];
  }

  public static final boolean is_valid_array(final Object[] array){
    if(array == null){
      ADDSynthCore.log.error("Input array is null.");
      return false;
    }
    if(array.length == 0){
      ADDSynthCore.log.error("Input array is empty.");
      return false;
    }
    return true;
  }

  // MAYBE can I just pass an object array?
  public static final <T> boolean is_valid_array(final T[] array, final String call_location){
    if(array == null){
      ADDSynthCore.log.error(new NullPointerException("Input for "+call_location+" was null!"));
      return false;
    }
    if(array.length == 0){
      ADDSynthCore.log.error(new IllegalArgumentException("Input array for "+call_location+" requires at least 1 "+array.getClass().getComponentType().getSimpleName()+" element."));
      return false;
    }
    return true;
  }
          
  public static final <T> boolean check_array_index(final T[] array, final int index){
    if(is_valid_array(array)){
      if(index >= 0 && index < array.length){
        return true;
      }
      print_array_index_out_of_bounds_error(array, index);
    }
    return false;
  }

  /** Prints extremely detailed information to the log, and also prints the stacktrace. */
  public static final <T> void print_array_index_out_of_bounds_error(@Nonnull final T[] array, final int index){
    // if(array == null){
    //   ADDSynthCore.log.error(new NullPointerException("null array."));
    // }
    final int length = array.length;
    final String valid_indexes = length == 0 ? "The array is empty." : length == 1 ? "Only index 0 is valid." : "Only indexes 0-"+(length-1)+" are valid.";
    ADDSynthCore.log.error(new ArrayIndexOutOfBoundsException("Invalid index "+index+" for array "+array.getClass().getComponentType().getSimpleName()+"["+length+"]. "+valid_indexes));
    Thread.dumpStack(); // FEATURE: easiest solution for now because I'm in a time crunch, but would be nice to find a way to print the thread stack EXCEPT the first 2 lines that shows dumpStack() and print_array_error() respectively.
  }

  public static final int get_length_of_arrays(final Object[] ... arrays){
    int total_length = 0;
    for(Object[] array : arrays){
      if(array != null){
        total_length += array.length;
      }
    }
    return total_length;
  }

  // https://stackoverflow.com/questions/12462079/potential-heap-pollution-via-varargs-parameter
  // https://softwareengineering.stackexchange.com/questions/155994/java-heap-pollution#
  // https://docs.oracle.com/javase/tutorial/java/generics/nonReifiableVarargsType.html
  // https://docs.oracle.com/javase/8/docs/api/java/lang/SafeVarargs.html
  // THIS WORKS PERFECTLY!!! NEVER CHANGE!!!
  @SafeVarargs
  public static final <T> T[] combine_arrays(@Nonnull final T[] first_array, final T[] ... additional_arrays){
    int i = first_array.length;
    final T[] final_array = Arrays.copyOf(first_array, i + get_length_of_arrays(additional_arrays)); // creates a new array with the total size.
    for(T[] array : additional_arrays){
      if(array == null){
        ADDSynthCore.log.error(new NullPointerException("Encountered a null array in "+ArrayUtil.class.getSimpleName()+".combine_arrays() function."));
        continue;
      }
      for(T object : array){
        final_array[i] = object;
        i++;
      }
    }
    return Arrays.copyOfRange(final_array, 0, i);
  }

}
