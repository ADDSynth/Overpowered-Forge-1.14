package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.LensItem;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

public enum Lens {

  WHITE  (),
  RED    ("red",     TextFormatting.DARK_RED,     MaterialColor.TNT),
  ORANGE ("orange",  TextFormatting.GOLD,         MaterialColor.ADOBE),
  YELLOW ("yellow",  TextFormatting.YELLOW,       MaterialColor.GOLD),
  GREEN  ("green",   TextFormatting.DARK_GREEN,   MaterialColor.EMERALD),
  CYAN   ("cyan",    TextFormatting.AQUA,         MaterialColor.DIAMOND),
  BLUE   ("blue",    TextFormatting.BLUE,         MaterialColor.LAPIS),
  MAGENTA("magenta", TextFormatting.LIGHT_PURPLE, MaterialColor.MAGENTA);

  static {
    Debug.log_setup_info("Begin loading Lens class...");
  }

  public final Item lens;
  public final MaterialColor color;

  private Lens(){
    lens = new OverpoweredItem("focus_lens");
    color = MaterialColor.SNOW;
  }

  private Lens(final String color, final TextFormatting format_code, final MaterialColor material){
    lens = new LensItem(color+"_lens", format_code);
    this.color = material;
  }

  public static final Item focus_lens = WHITE.lens;
  public static final Item red        = RED.lens;
  public static final Item orange     = ORANGE.lens;
  public static final Item yellow     = YELLOW.lens;
  public static final Item green      = GREEN.lens;
  public static final Item cyan       = CYAN.lens;
  public static final Item blue       = BLUE.lens;
  public static final Item magenta    = MAGENTA.lens;

  public static final Item[] index = { focus_lens, red, orange, yellow, green, cyan, blue, magenta};
  
  static {
    Debug.log_setup_info("Finished loading Lens class.");
  }

}
