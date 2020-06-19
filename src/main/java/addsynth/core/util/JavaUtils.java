package addsynth.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public final class JavaUtils {

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
        ADDSynthCore.log.error(new NullPointerException("Encountered a null array in JavaUtils.combine_arrays() function."));
        continue;
      }
      for(T object : array){
        final_array[i] = object;
        i++;
      }
    }
    return Arrays.copyOfRange(final_array, 0, i);
  }

  public static final int cast_to_int(final long value){
    if(value > 0){ return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)value; }
    if(value < 0){ return value < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int)value; }
    return 0;
  }

  /**
   * <p>Use this to attempt to use Reflection to gain access to a private or protected method in another class.
   * <p><b>Note:</b> for performance reasons, only call this ONCE and cache the result.
   * <p><b>Remember:</b> If the obfuscated name you supply doesn't find the method, it may be under a different codename!
   * @param clazz the class that contains the method you want to access.
   * @param srg_name  the Searge obfsucated name of the method.
   * @param parameterTypes list of parameter types of the method.
   * @return the method, or null if an error occured.
   */
  @Nullable
  public static final Method getMethod(Class<?> clazz, String srg_name, Class<?> ... parameterTypes){
    Method m = null;
    try{
      m = ObfuscationReflectionHelper.findMethod(clazz, srg_name, parameterTypes);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return m;
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

  public static final String print_parameters(final Class[] parameters){
    if(parameters.length == 0){
      return "";
    }
    int i;
    final StringBuilder s = new StringBuilder();
    Class c;
    for(i = 0; i < parameters.length; i++){
      c = parameters[i];
      s.append(c.getSimpleName());
      if(i + 1 < parameters.length){
        s.append(", ");
      }
    }
    return s.toString();
  }

  public static final String print_constructor_list(final Constructor[] constructors){
    int i;
    final String name = constructors[0].getDeclaringClass().getSimpleName();
    final StringBuilder constructor_list = new StringBuilder();
    for(i = 0; i < constructors.length; i++){
      constructor_list.append(Modifier.toString(constructors[i].getModifiers()));
      constructor_list.append(" ");
      constructor_list.append(name);
      constructor_list.append("(");
      constructor_list.append(print_parameters(constructors[i].getParameterTypes()));
      constructor_list.append(")");
      if(i + 1 < constructors.length){
        constructor_list.append(", ");
      }
    }
    return constructor_list.toString();
  }

  /** Prints to the ADDSynthCore log all methods in a class, one method per line.
   *  This is useful when I had to use an SRG name of a method, but the class had multiple
   *  methods with the name, so I had to print them all out and select the one that matched
   *  the one I was looking for.
   * @param clazz
   */
  public static final void print_class_methods(final Class clazz){
    ADDSynthCore.log.info("Debug: Begin printing all methods for class "+clazz.getSimpleName()+".");
    StringBuilder method_string;
    final Method[] methods = clazz.getDeclaredMethods();
    for(final Method method : methods){
      method_string = new StringBuilder();
      method_string.append(Modifier.toString(method.getModifiers()));
      method_string.append(" ");
      method_string.append(method.getName());
      method_string.append("(");
      method_string.append(print_parameters(method.getParameterTypes()));
      method_string.append(")");
      ADDSynthCore.log.info(method_string.toString());
    }
  }

}
