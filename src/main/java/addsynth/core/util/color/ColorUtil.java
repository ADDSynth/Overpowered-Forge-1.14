package addsynth.core.util.color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TreeSet;
import addsynth.core.ADDSynthCore;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

// What? Java doesn't have unsigned numeric types? That's the stupidest thing I've ever heard!
// https://stackoverflow.com/questions/430346/why-doesnt-java-support-unsigned-ints
// https://en.wikipedia.org/wiki/Criticism_of_Java#Unsigned_integer_types

/** <p>This utility class is used to assist in dealing with Minecraft's color codes.
 *     Minecraft's color codes are integers that use 8 bits each to store the red,
 *     green, and blue channels in that order.
 *  <p>ADDSynthCore will automatically call {@link #dump_map_colors()} if it is enabled
 *     in the config.
 * @author ADDSynth
 * @see Color
 * @see MinecraftColor
 * @since October 23, 2019
 */
public final class ColorUtil {

  public static final ImmutableMap<MaterialColor, String> map_color_names = new ImmutableMap.Builder<MaterialColor, String>()
    .put(MaterialColor.ADOBE,      "MaterialColor.ADOBE")
    .put(MaterialColor.AIR,        "MaterialColor.AIR")
    .put(MaterialColor.BLACK,      "MaterialColor.BLACK")
    .put(MaterialColor.BLUE,       "MaterialColor.BLUE")
    .put(MaterialColor.BROWN,      "MaterialColor.BROWN")
    .put(MaterialColor.CLAY,       "MaterialColor.CLAY")
    .put(MaterialColor.WOOL,       "MaterialColor.WOOL")
    .put(MaterialColor.CYAN,       "MaterialColor.CYAN")
    .put(MaterialColor.DIAMOND,    "MaterialColor.DIAMOND")
    .put(MaterialColor.DIRT,       "MaterialColor.DIRT")
    .put(MaterialColor.EMERALD,    "MaterialColor.EMERALD")
    .put(MaterialColor.FOLIAGE,    "MaterialColor.FOLIAGE")
    .put(MaterialColor.GOLD,       "MaterialColor.GOLD")
    .put(MaterialColor.GRASS,      "MaterialColor.GRASS")
    .put(MaterialColor.GRAY,       "MaterialColor.GRAY")
    .put(MaterialColor.GREEN,      "MaterialColor.GREEN")
    .put(MaterialColor.ICE,        "MaterialColor.ICE")
    .put(MaterialColor.IRON,       "MaterialColor.IRON")
    .put(MaterialColor.LAPIS,      "MaterialColor.LAPIS")
    .put(MaterialColor.LIGHT_BLUE, "MaterialColor.LIGHT_BLUE")
    .put(MaterialColor.LIME,       "MaterialColor.LIME")
    .put(MaterialColor.MAGENTA,    "MaterialColor.MAGENTA")
    .put(MaterialColor.NETHERRACK, "MaterialColor.NETHERRACK")
    .put(MaterialColor.OBSIDIAN,   "MaterialColor.OBSIDIAN")
    .put(MaterialColor.PINK,       "MaterialColor.PINK")
    .put(MaterialColor.PURPLE,     "MaterialColor.PURPLE")
    .put(MaterialColor.QUARTZ,     "MaterialColor.QUARTZ")
    .put(MaterialColor.RED,        "MaterialColor.RED")
    .put(MaterialColor.SAND,       "MaterialColor.SAND")
    .put(MaterialColor.LIGHT_GRAY, "MaterialColor.LIGHT_GRAY")
    .put(MaterialColor.SNOW,       "MaterialColor.SNOW")
    .put(MaterialColor.STONE,      "MaterialColor.STONE")
    .put(MaterialColor.TNT,        "MaterialColor.TNT")
    .put(MaterialColor.WATER,      "MaterialColor.WATER")
    .put(MaterialColor.WOOD,       "MaterialColor.WOOD")
    .put(MaterialColor.YELLOW,     "MaterialColor.YELLOW")
    .put(MaterialColor.BLACK_TERRACOTTA,      "MaterialColor.BLACK_TERRACOTTA")
    .put(MaterialColor.BLUE_TERRACOTTA,       "MaterialColor.BLUE_TERRACOTTA")
    .put(MaterialColor.BROWN_TERRACOTTA,      "MaterialColor.BROWN_TERRACOTTA")
    .put(MaterialColor.CYAN_TERRACOTTA,       "MaterialColor.CYAN_TERRACOTTA")
    .put(MaterialColor.GRAY_TERRACOTTA,       "MaterialColor.GRAY_TERRACOTTA")
    .put(MaterialColor.GREEN_TERRACOTTA,      "MaterialColor.GREEN_TERRACOTTA")
    .put(MaterialColor.LIGHT_BLUE_TERRACOTTA, "MaterialColor.LIGHT_BLUE_TERRACOTTA")
    .put(MaterialColor.LIME_TERRACOTTA,       "MaterialColor.LIME_TERRACOTTA")
    .put(MaterialColor.MAGENTA_TERRACOTTA,    "MaterialColor.MAGENTA_TERRACOTTA")
    .put(MaterialColor.ORANGE_TERRACOTTA,     "MaterialColor.ORANGE_TERRACOTTA")
    .put(MaterialColor.PINK_TERRACOTTA,       "MaterialColor.PINK_TERRACOTTA")
    .put(MaterialColor.PURPLE_TERRACOTTA,     "MaterialColor.PURPLE_TERRACOTTA")
    .put(MaterialColor.RED_TERRACOTTA,        "MaterialColor.RED_TERRACOTTA")
    .put(MaterialColor.LIGHT_GRAY_TERRACOTTA, "MaterialColor.SILVER_TERRACOTTA")
    .put(MaterialColor.WHITE_TERRACOTTA,      "MaterialColor.WHITE_TERRACOTTA")
    .put(MaterialColor.YELLOW_TERRACOTTA,     "MaterialColor.YELLOW_TERRACOTTA")
    .build();

  private static final class ColorSet implements Comparable<ColorSet> {
    public MaterialColor mapcolor;
    public int difference;
    public Block[] blocks;
    
    public ColorSet(final MaterialColor mapcolor, final int difference, final Block[] blocks){
      this.mapcolor = mapcolor;
      this.difference = difference;
      this.blocks = blocks;
    }

    @Override
    public final int compareTo(final ColorSet obj){
      return (int)Math.signum(this.difference - obj.difference);
    }

    @Override
    public final String toString(){
      return map_color_names.get(mapcolor);
    }
  }

  public static final void dump_map_colors(){
    ADDSynthCore.log.info("Begin dumping Minecraft Map Colors...");

    final String debug_file = "debug_map_colors.txt";
    boolean can_write = true;
    final File file = new File(debug_file);

    if(file.exists()){
      can_write = file.delete();
    }
    
    if(can_write){
      try(final FileWriter writer = new FileWriter(file)){
        writer.write("ADDSynthCore: debug Minecraft Map Colors:\n\n\n");
  
        int i;
        final MinecraftColor[] color_values = MinecraftColor.values();
        final int length = color_values.length;
        final ColorSet[][] set = build_color_list();

        for(i = 0; i < length; i++){
          writer.write(color_values[i].name+" colors: "+printColor(color_values[i].value)+"\n");
          for(ColorSet color : set[i]){
            writer.write("   "+map_color_names.get(color.mapcolor)+" "+printColor(color.mapcolor.colorValue)+"\n");
            for(Block block : color.blocks){
              writer.write("      "+block.getRegistryName()+"\n");
            }
          }
          writer.write("\n\n");
        }
      }
      catch(IOException e){
        e.printStackTrace();
      }
    }
    else{
      ADDSynthCore.log.error(new IOException("Was unable to delete the existing "+debug_file+" file for some reason."));
    }
    
    ADDSynthCore.log.info("Done dumping Minecraft Map Colors.");
  }

  private static final ColorSet[][] build_color_list(){
    // Part 1: verify MaterialColor array is in order
    int number_of_colors = 0;
    for(MaterialColor c : MaterialColor.COLORS){
      if(c != null){
        number_of_colors += 1;
      }
    }
    final MaterialColor[] map_colors = new MaterialColor[number_of_colors];
    int i = 0;
    for(MaterialColor c : MaterialColor.COLORS){
      if(c != null){
        map_colors[i] = c;
        i += 1;
      }
    }

    // Part 2: create difference list. get the difference for each MaterialColor aggainst each color we're testing.
    final MinecraftColor[] color_values = MinecraftColor.values();
    final int length = color_values.length;
    final int[][] difference = new int[number_of_colors][length];
    int j;
    int k;
    int map_color;
    int color;
    int red;
    int green;
    int blue;

    for(j = 0; j < number_of_colors; j++){
      for(k = 0; k < length; k++){
        map_color = map_colors[j].colorValue;
        color     = color_values[k].value;
        red   = Math.abs(Color.getRed(map_color) - Color.getRed(color));
        green = Math.abs(Color.getGreen(map_color) - Color.getGreen(color));
        blue  = Math.abs(Color.getBlue(map_color) - Color.getBlue(color));
        difference[j][k] = red + green + blue;
      }
    }

    // Part 3: For each color in color_list, get associated MaterialColors via override or closest match.
    final ColorSet[][] list = new ColorSet[length][];
    int lowest_value;
    int lowest_index;
    TreeSet<ColorSet> values;
    boolean found_override;

    for(i = 0; i < length; i++){
      values = new TreeSet<ColorSet>();
      for(j = 0; j < number_of_colors; j++){
      
        found_override = false;

        // Part 3a: look for override
        for(k = 0; k < length && !found_override; k++){
          for(MaterialColor mp : color_values[k].colors){
            if(map_colors[j] == mp){
              found_override = true;
              if(k == i){
                values.add(new ColorSet(mp, difference[j][i], get_blocks_that_match_color(mp)));
              }
              break;
            }
          }
        }
        // Part 3b: no override found, match color with the lowest difference
        if(found_override == false){
          // get lowest for this row
          lowest_index = 0;
          lowest_value = difference[j][0];
          for(k = 1; k < length; k++){
            if(difference[j][k] < lowest_value){
              lowest_value = difference[j][k];
              lowest_index = k;
            }
          }
          if(lowest_index == i){
            values.add(new ColorSet(map_colors[j], lowest_value, get_blocks_that_match_color(map_colors[j])));
          }
        }
      }

      // Part 3c: Set value list to array for Color i
      list[i] = values.toArray(new ColorSet[values.size()]);
    }

    return list;
  }

  public static final Block[] get_blocks_that_match_color(final MaterialColor test_color){
    final ArrayList<Block> blocks = new ArrayList<>(200);
    try{
      final Field field = ObfuscationReflectionHelper.findField(Block.class, "field_181083_K");
      for(Block block : ForgeRegistries.BLOCKS){
        if(test_color == (MaterialColor)field.get(block)){
          blocks.add(block);
        }
      }
    }
    catch(Exception e){
      System.out.println(e.toString());
    }
    return blocks.toArray(new Block[blocks.size()]);
  }

  public static final String printColor(final int color){
    return "( "+Color.getRed(color)+" , "+Color.getGreen(color)+" , "+Color.getBlue(color)+" )";
  }

}
