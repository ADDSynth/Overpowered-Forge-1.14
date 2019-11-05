package addsynth.core.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TreeSet;
import addsynth.core.ADDSynthCore;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

// What? Java doesn't have unsigned numeric types? That's the stupidest thing I've ever heard!
// https://stackoverflow.com/questions/430346/why-doesnt-java-support-unsigned-ints
// https://en.wikipedia.org/wiki/Criticism_of_Java#Unsigned_integer_types

/** <p>This utility class is used to assist in dealing with Minecraft's color codes.
 *     Minecraft's color codes are integers that use 8 bits each to store the red,
 *     green, and blue channels in that order.
 *  <p>Myself and other developers can call the {@link #dump_map_colors()} function to
 *     dump all the {@link MapColor} color codes in RGB format and which blocks use that
 *     MapColor to assist in selecting which MapColor to use when creating new blocks.
 *  <p>You should call it in your mod's Init event so it can query the Forge Registry
 *     for registered blocks.
 * @author ADDSynth
 * @since October 23, 2019
 */
public final class ColorUtil {

  public static final ImmutableMap<MapColor, String> map_color_names = new ImmutableMap.Builder<MapColor, String>()
    .put(MapColor.ADOBE,      "MapColor.ADOBE")
    .put(MapColor.AIR,        "MapColor.AIR")
    .put(MapColor.BLACK,      "MapColor.BLACK")
    .put(MapColor.BLUE,       "MapColor.BLUE")
    .put(MapColor.BROWN,      "MapColor.BROWN")
    .put(MapColor.CLAY,       "MapColor.CLAY")
    .put(MapColor.CLOTH,      "MapColor.CLOTH")
    .put(MapColor.CYAN,       "MapColor.CYAN")
    .put(MapColor.DIAMOND,    "MapColor.DIAMOND")
    .put(MapColor.DIRT,       "MapColor.DIRT")
    .put(MapColor.EMERALD,    "MapColor.EMERALD")
    .put(MapColor.FOLIAGE,    "MapColor.FOLIAGE")
    .put(MapColor.GOLD,       "MapColor.GOLD")
    .put(MapColor.GRASS,      "MapColor.GRASS")
    .put(MapColor.GRAY,       "MapColor.GRAY")
    .put(MapColor.GREEN,      "MapColor.GREEN")
    .put(MapColor.ICE,        "MapColor.ICE")
    .put(MapColor.IRON,       "MapColor.IRON")
    .put(MapColor.LAPIS,      "MapColor.LAPIS")
    .put(MapColor.LIGHT_BLUE, "MapColor.LIGHT_BLUE")
    .put(MapColor.LIME,       "MapColor.LIME")
    .put(MapColor.MAGENTA,    "MapColor.MAGENTA")
    .put(MapColor.NETHERRACK, "MapColor.NETHERRACK")
    .put(MapColor.OBSIDIAN,   "MapColor.OBSIDIAN")
    .put(MapColor.PINK,       "MapColor.PINK")
    .put(MapColor.PURPLE,     "MapColor.PURPLE")
    .put(MapColor.QUARTZ,     "MapColor.QUARTZ")
    .put(MapColor.RED,        "MapColor.RED")
    .put(MapColor.SAND,       "MapColor.SAND")
    .put(MapColor.SILVER,     "MapColor.SILVER")
    .put(MapColor.SNOW,       "MapColor.SNOW")
    .put(MapColor.STONE,      "MapColor.STONE")
    .put(MapColor.TNT,        "MapColor.TNT")
    .put(MapColor.WATER,      "MapColor.WATER")
    .put(MapColor.WOOD,       "MapColor.WOOD")
    .put(MapColor.YELLOW,     "MapColor.YELLOW")
    .put(MapColor.BLACK_STAINED_HARDENED_CLAY,      "MapColor.BLACK_STAINED_HARDENED_CLAY")
    .put(MapColor.BLUE_STAINED_HARDENED_CLAY,       "MapColor.BLUE_STAINED_HARDENED_CLAY")
    .put(MapColor.BROWN_STAINED_HARDENED_CLAY,      "MapColor.BROWN_STAINED_HARDENED_CLAY")
    .put(MapColor.CYAN_STAINED_HARDENED_CLAY,       "MapColor.CYAN_STAINED_HARDENED_CLAY")
    .put(MapColor.GRAY_STAINED_HARDENED_CLAY,       "MapColor.GRAY_STAINED_HARDENED_CLAY")
    .put(MapColor.GREEN_STAINED_HARDENED_CLAY,      "MapColor.GREEN_STAINED_HARDENED_CLAY")
    .put(MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY, "MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY")
    .put(MapColor.LIME_STAINED_HARDENED_CLAY,       "MapColor.LIME_STAINED_HARDENED_CLAY")
    .put(MapColor.MAGENTA_STAINED_HARDENED_CLAY,    "MapColor.MAGENTA_STAINED_HARDENED_CLAY")
    .put(MapColor.ORANGE_STAINED_HARDENED_CLAY,     "MapColor.ORANGE_STAINED_HARDENED_CLAY")
    .put(MapColor.PINK_STAINED_HARDENED_CLAY,       "MapColor.PINK_STAINED_HARDENED_CLAY")
    .put(MapColor.PURPLE_STAINED_HARDENED_CLAY,     "MapColor.PURPLE_STAINED_HARDENED_CLAY")
    .put(MapColor.RED_STAINED_HARDENED_CLAY,        "MapColor.RED_STAINED_HARDENED_CLAY")
    .put(MapColor.SILVER_STAINED_HARDENED_CLAY,     "MapColor.SILVER_STAINED_HARDENED_CLAY")
    .put(MapColor.WHITE_STAINED_HARDENED_CLAY,      "MapColor.WHITE_STAINED_HARDENED_CLAY")
    .put(MapColor.YELLOW_STAINED_HARDENED_CLAY,     "MapColor.YELLOW_STAINED_HARDENED_CLAY")
    .build();

  // Some of these colors values were obtained from looking them up on Wikipedia.
  public static final Color WHITE     = new Color("White",     0xFFFFFF, MapColor.SNOW, MapColor.QUARTZ);
  public static final Color SILVER    = new Color("Silver",    0xC0C0C0, MapColor.CLOTH, MapColor.IRON, MapColor.CLAY);
  public static final Color GRAY      = new Color("Gray",      0x808080, MapColor.STONE, MapColor.SILVER);
  public static final Color DARK_GRAY = new Color("Dark Gray", 0x404040, MapColor.GRAY);
  public static final Color BLACK     = new Color("Black",     0x000000, MapColor.AIR, MapColor.BLACK);
  public static final Color RED       = new Color("Red",       0xFF0000, MapColor.TNT, MapColor.RED, MapColor.NETHERRACK);
  public static final Color ORANGE    = new Color("Orange",    0xFF8000, MapColor.ADOBE);
  public static final Color YELLOW    = new Color("Yellow",    0xFFFF00, MapColor.YELLOW, MapColor.GOLD);
  public static final Color GREEN     = new Color("Green",     0X00FF00, MapColor.GRASS, MapColor.LIME, MapColor.FOLIAGE, MapColor.GREEN, MapColor.EMERALD);
  public static final Color CYAN      = new Color("Cyan",      0x00FFFF, MapColor.CYAN, MapColor.DIAMOND);
  public static final Color BLUE      = new Color("Blue",      0x0000FF, MapColor.BLUE, MapColor.LIGHT_BLUE, MapColor.WATER, MapColor.ICE, MapColor.LAPIS);
  public static final Color MAGENTA   = new Color("Magenta",   0xFF00FF, MapColor.MAGENTA);
  public static final Color PURPLE    = new Color("Purple",    0x800080, MapColor.PURPLE);
  public static final Color PINK      = new Color("Pink",      0xFFC0CB, MapColor.PINK);
  public static final Color PEACH     = new Color("Peach",     0xFFE5B4, MapColor.SAND);
  public static final Color BROWN     = new Color("Brown",     0x964B00, MapColor.BROWN, MapColor.DIRT, MapColor.WOOD, MapColor.OBSIDIAN);

  public static final class Color {
    public String name;
    public int value;
    public MapColor[] colors;
    
    public Color(final String name, final int value, final MapColor ... overrides){
      this.name = name;
      this.value = value;
      this.colors = overrides;
    }

    @Override
    public final String toString(){
      return name+": "+value;
    }
  }

  public static final Color[] color_list = {
    WHITE, SILVER, GRAY, DARK_GRAY, BLACK, RED, ORANGE, YELLOW,
    GREEN, CYAN, BLUE, MAGENTA, PURPLE, PINK, PEACH, BROWN
  };

  private static final class ColorSet implements Comparable<ColorSet> {
    public MapColor mapcolor;
    public int difference;
    public Block[] blocks;
    
    public ColorSet(final MapColor mapcolor, final int difference, final Block[] blocks){
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
        final ColorSet[][] set = build_color_list();
        for(i = 0; i < color_list.length; i++){
          writer.write(color_list[i].name+" colors: "+printColor(color_list[i].value)+"\n");
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
    // Part 1: verify MapColor array is in order
    int number_of_colors = 0;
    for(MapColor c : MapColor.COLORS){
      if(c != null){
        number_of_colors += 1;
      }
    }
    final MapColor[] map_colors = new MapColor[number_of_colors];
    int i = 0;
    for(MapColor c : MapColor.COLORS){
      if(c != null){
        map_colors[i] = c;
        i += 1;
      }
    }

    // Part 2: create difference list. get the difference for each MapColor aggainst each color we're testing.
    final int length = color_list.length;
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
        color     = color_list[k].value;
        red   = Math.abs(getRed(map_color) - getRed(color));
        green = Math.abs(getGreen(map_color) - getGreen(color));
        blue  = Math.abs(getBlue(map_color) - getBlue(color));
        difference[j][k] = red + green + blue;
      }
    }

    // Part 3: For each color in color_list, get associated MapColors via override or closest match.
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
          for(MapColor mp : color_list[k].colors){
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

  public static final int getRed(final int color){ return color >> 16; }
  public static final int getGreen(final int color){ return (color >> 8) % 256; }
  public static final int getBlue(final int color){ return color % 256; }

  public static final int make_color(final int red, final int green, final int blue){
    return (red & 255) << 16 | (green & 255) << 8 | (blue & 255);
  }

  public static final Block[] get_blocks_that_match_color(final MapColor test_color){
    final ArrayList<Block> blocks = new ArrayList<>(200);
    final IForgeRegistry<Block> registry = ForgeRegistries.BLOCKS;
    try{
      final Field field = ObfuscationReflectionHelper.findField(Block.class, "field_181083_K");
      for(Block block : registry){
        if(test_color == (MapColor)field.get(block)){
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
    return "( "+getRed(color)+" , "+getGreen(color)+" , "+getBlue(color)+" )";
  }

}
