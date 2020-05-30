package addsynth.core.util.color;

import net.minecraft.block.material.MaterialColor;

public enum MinecraftColor {

  // Some of these colors values were obtained from looking them up on Wikipedia.
  WHITE(    "White",     0xFFFFFF, MaterialColor.SNOW, MaterialColor.QUARTZ),
  SILVER(   "Silver",    0xC0C0C0, MaterialColor.WOOL, MaterialColor.IRON, MaterialColor.CLAY),
  GRAY(     "Gray",      0x808080, MaterialColor.STONE, MaterialColor.LIGHT_GRAY),
  DARK_GRAY("Dark Gray", 0x404040, MaterialColor.GRAY),
  BLACK(    "Black",     0x000000, MaterialColor.AIR, MaterialColor.BLACK),
  RED(      "Red",       0xFF0000, MaterialColor.TNT, MaterialColor.RED, MaterialColor.NETHERRACK),
  ORANGE(   "Orange",    0xFF8000, MaterialColor.ADOBE),
  YELLOW(   "Yellow",    0xFFFF00, MaterialColor.YELLOW, MaterialColor.GOLD),
  GREEN(    "Green",     0X00FF00, MaterialColor.GRASS, MaterialColor.LIME, MaterialColor.FOLIAGE, MaterialColor.GREEN, MaterialColor.EMERALD),
  CYAN(     "Cyan",      0x00FFFF, MaterialColor.CYAN, MaterialColor.DIAMOND),
  BLUE(     "Blue",      0x0000FF, MaterialColor.BLUE, MaterialColor.LIGHT_BLUE, MaterialColor.WATER, MaterialColor.ICE, MaterialColor.LAPIS),
  MAGENTA(  "Magenta",   0xFF00FF, MaterialColor.MAGENTA),
  PURPLE(   "Purple",    0x800080, MaterialColor.PURPLE),
  PINK(     "Pink",      0xFFC0CB, MaterialColor.PINK),
  PEACH(    "Peach",     0xFFE5B4, MaterialColor.SAND),
  BROWN(    "Brown",     0x964B00, MaterialColor.BROWN, MaterialColor.DIRT, MaterialColor.WOOD, MaterialColor.OBSIDIAN);

  public final String name;
  public final int value;
  public final MaterialColor[] colors;
  
  private MinecraftColor(final String name, final int value, final MaterialColor ... overrides){
    this.name = name;
    this.value = value;
    this.colors = overrides;
  }

  @Override
  public final String toString(){
    return name+": "+value;
  }

}
