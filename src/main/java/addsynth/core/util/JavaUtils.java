package addsynth.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import javax.annotation.Nonnull;
import addsynth.core.ADDSynthCore;

public final class JavaUtils {

  // https://stackoverflow.com/questions/12462079/potential-heap-pollution-via-varargs-parameter
  // https://softwareengineering.stackexchange.com/questions/155994/java-heap-pollution#
  // https://docs.oracle.com/javase/tutorial/java/generics/nonReifiableVarargsType.html
  // https://docs.oracle.com/javase/8/docs/api/java/lang/SafeVarargs.html
  @SafeVarargs
  public static final <T> T[] combine_arrays(@Nonnull final T[] first_array, final T[] ... additional_arrays){
    int final_array_length = first_array.length;
    for(T[] array : additional_arrays){
      if(array != null){
        final_array_length += array.length;
      }
    }
    final T[] final_array = Arrays.copyOf(first_array, final_array_length); // creates a new array with the total size.
    int i = first_array.length;
    for(T[] array : additional_arrays){
      if(array == null){
        ADDSynthCore.log.error(new NullPointerException("Encountered a null array in JavaUtils.combine_arrays() function."));
        continue;
      }
      for(T object : array){
        final_array[i] = object;
        i++;
      }
    }
    return final_array;
  }

  public static final int cast_to_int(final long value){
    if(value > 0){ return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)value; }
    if(value < 0){ return value < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int)value; }
    return 0;
  }

  public static final boolean classExists(final String clazz){
    try{
      Class.forName(clazz, false, JavaUtils.class.getClassLoader());
    }
    catch(ClassNotFoundException e){
      return false;
    }
    return true;
  }

  public static final boolean packageExists(final String path){
    return Package.getPackage(path) != null;
  }

  /** This converts a list of random objects to a list of class types. */
  public static final Class[] getTypes(@Nonnull final Object[] args){
    final Class[] arg_types = new Class[args.length];
    int i;
    for(i = 0; i < args.length; i++){
      arg_types[i] = args[i].getClass();
    }
    return arg_types;
  }

  /** Attempt to invoke a constructor of the supplied class, using the supplied arguments. */
  public static final <T> T InvokeConstructor(@Nonnull final Class<T> clazz, final Object ... args){
    T obj = null;
    try{
      // FEATURE: call our own GetConstructor() function which manually iterates through each constructor and tests each parameter using instanceof. Remove the RegistryUtil.InvokeCustomItemBlock function when I add this feature.
      obj = clazz.getConstructor(getTypes(args)).newInstance(args);
    }
    catch(NoSuchMethodException e){
      ADDSynthCore.log.error(e.toString());
      ADDSynthCore.log.error("JavaUtils.InvokeConstructor() failed to find a constructor in the '"+clazz.getName()+"' class that takes the arguments: "+StringUtil.print_type_array(getTypes(args)));
      // even if no constructors are specified in the class, there will always be the default constructor.
      ADDSynthCore.log.error("Available constructors for "+clazz.getSimpleName()+": "+print_constructor_list(clazz.getConstructors()));
      Thread.dumpStack();
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return obj;
  }

  public static final String print_constructor_list(final Constructor[] constructors){
    int i;
    int j;
    final String name = constructors[0].getDeclaringClass().getSimpleName();
    final StringBuilder constructor_list = new StringBuilder();
    Class[] parameters;
    for(i = 0; i < constructors.length; i++){
      constructor_list.append(Modifier.toString(constructors[i].getModifiers()));
      constructor_list.append(" ");
      constructor_list.append(name);
      constructor_list.append("(");
      parameters = constructors[i].getParameterTypes();
      for(j = 0; j < parameters.length; j++){
        constructor_list.append(parameters[j].getSimpleName());
        if(j + 1 < parameters.length){
          constructor_list.append(", ");
        }
      }
      constructor_list.append(")");
      if(i + 1 < constructors.length){
        constructor_list.append(", ");
      }
    }
    return constructor_list.toString();
  }

}
