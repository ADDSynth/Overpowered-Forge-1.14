package addsynth.core.util.java;

import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;

public final class ArrayUtil {

  public static final boolean isInsideBounds(final int index, final int max_length){
    return index >= 0 && index < max_length;
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

  // TEST can I just pass an object array?
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

  /** Used to check if the array contains the value. */
  public static final <T> boolean valueExists(final T[] array, final T value){
    boolean exists = false;
    for(final T i : array){
      if(i.equals(value)){
        exists = true;
        break;
      }
    }
    return exists;
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

  /** This checks the two arrays and returns true if they are different.
   *  Only replaces the cached_array if the size changed. */
  public static final <T> boolean arrayChanged(final T[] cached_array, final T[] new_array){
    if(new_array.length != cached_array.length){
      return true;
    }
    boolean changed = false;
    int i;
    final int length = new_array.length;
    for(i = 0; i < length; i++){
      if(cached_array[i].equals(new_array[i]) == false){
        changed = true;
        break;
      }
    }
    return changed;
  }

  /** This checks the array against the supplied {@link ArrayList} to see if the array needs to be updated. */
  public static final <T> boolean arrayChanged(final T[] cached_array, final ArrayList<T> list){
    if(list.size() != cached_array.length){
      return true;
    }
    boolean changed = false;
    int i;
    final int length = cached_array.length;
    for(i = 0; i < length; i++){
      if(list.get(i).equals(cached_array[i]) == false){
        changed = true;
        break;
      }
    }
    return changed;
  }

  /** This checks the data in the new_array against the data in the cached array and updates the
   *  cached_array accordingly. Returns true if an update occured. */
  public static final <T> boolean updateArray(T[] cached_array, final T[] new_array){
    boolean changed = false;
    final int length = new_array.length;
    if(cached_array.length == length){
      int i;
      for(i = 0; i < length; i++){
        if(cached_array[i].equals(new_array[i]) == false){
          changed = true;
          cached_array[i] = new_array[i];
        }
      }
    }
    else{
      cached_array = new_array;
      changed = true;
    }
    return changed;
  }

  /** This checks the cached_array against the data stored in the supplied list, and updates
   *  it if there were any changes. Returns true if an update occured. */
  public static final <T> boolean updateArray(T[] cached_array, final ArrayList<T> list){
    boolean changed = false;
    final int length = list.size();
    if(cached_array.length == length){
      int i;
      T e;
      for(i = 0; i < length; i++){
        e = list.get(i);
        if(e.equals(cached_array[i]) == false){
          changed = true;
          cached_array[i] = e;
        }
      }
    }
    else{
      cached_array = (T[])list.toArray(); // list.toArray(new T[length]);
      return true;
    }
    return changed;
  }

  /** Syncs the data in the cached_list with that in the supplied list. Only copies data.
   *  Does not replace the cached_list object. Returns true if an update occured. */
  public static final <T> boolean syncList(final ArrayList<T> cached_list, final ArrayList<T> new_list){
    boolean changed = false;
    final int length = new_list.size();
    while(cached_list.size() > length){
      cached_list.remove(length);
      changed = true;
    }
    final int old_length = cached_list.size();
    int i;
    T a;
    for(i = 0; i < length; i++){
      a = new_list.get(i);
      if(i < old_length){
        if(cached_list.get(i).equals(a) == false){
          cached_list.set(i, a);
          changed = true;
        }
        continue;
      }
      cached_list.add(a);
      changed = true;
    }
    return changed;
  }

}
