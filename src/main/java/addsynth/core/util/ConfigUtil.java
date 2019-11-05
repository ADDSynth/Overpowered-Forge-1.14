package addsynth.core.util;

import java.util.ArrayList;
import java.util.List;
import addsynth.core.ADDSynthCore;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

/**
 * This utility class is used to build your Client Gui Config screens.
 * @author ADDSynth
 * @since October 26, 2019
 */
public final class ConfigUtil {

  /** <p>When constructing your {@link IConfigElement}s List for Gui screens, you can use this function
   *  to add a Dummy Category to use as a parent for all the Categories already in the Configuration
   *  class that you specify. I personally prefer to use this because I like to group related config
   *  properties into seperate config files.
   *  <p>Use like this: <code>list.add(ConfigUtil.addConfigFile("Name", Mod.MOD_ID, Config.instance));</code>
   *  <p>You can use a language key in the form of <code>[MOD ID].config.[NAME]</code> in language files
   *  to translate the text that appears on this Category button. You can use the same language key + 
   *  <code>.tooltip</code> to add text to display when the player mouses over the Category.
   * @param name The name of this category
   * @param modid Your Mod ID
   * @param config The Configuration class you want to add
   * @return a {@link DummyCategoryElement} containing everything in your configuration file.
   */
  public static final DummyCategoryElement addConfigFile(final String name, final String modid, final Configuration config){
    final ArrayList<IConfigElement> elements = new ArrayList<>(10);
    final String translation_key = modid+".config."+((name.toLowerCase()).replace(' ', '_'));
    addConfigElements(config, elements);
    return new DummyCategoryElement(name, translation_key, elements);
  }

  /** Use this to add the child elements of a Configuration to a List of {@link IConfigElement}s.
   *  Use this if you only have 1 config file and it is suficiently categorized.
   */
  public static final void addConfigElements(final Configuration config, final List<IConfigElement> elements){
    for(ConfigCategory category : getCategories(config)){
      elements.add(new ConfigElement(category));
    }
  }

  /** This is kind of like the opposite of {@link #addConfigFile(String, String, Configuration)} in that
   *  you use this to add specific Categories directly. Specify the category to use and this function
   *  gets the child elements from the Configuration instance and uses those elements to create a
   *  new {@link DummyCategoryElement} with the name that you specified. The name that you specify will
   *  in effect override the actual name of the category.
   * @param name The new name of the category to display in the Gui
   * @param modid Your Mod ID
   * @param config The {@link Configuration} that has the category
   * @param category The name of the category you want to add
   */
  public static final DummyCategoryElement addCategoryElements(final String name, final String modid, final Configuration config, final String category){
    if(config.hasCategory(category)){
      final String translation_key = modid+".config."+((name.toLowerCase()).replace(' ', '_'));
      List<IConfigElement> children = (new ConfigElement(config.getCategory(category))).getChildElements();
      return new DummyCategoryElement(name, translation_key, children);
    }
    ADDSynthCore.log.error(new IllegalArgumentException("From the "+modid+" mod, the category \'"+category+"\' doesn't exist in config file: "+config));
    return null;
  }

  /** Gets the categories in the Configuration class that you specify.
   * @param config
   */
  private static final ArrayList<ConfigCategory> getCategories(final Configuration config){
    final ArrayList<ConfigCategory> categories = new ArrayList<>(20);
    for(String category : config.getCategoryNames()){
      if(category.contains(".") == false){
        categories.add(config.getCategory(category));
      }
    }
    return categories;
  }

}
