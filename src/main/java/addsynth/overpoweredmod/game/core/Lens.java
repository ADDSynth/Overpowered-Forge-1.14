package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.LensItem;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

public enum Lens {

  WHITE  (),
  RED    ("red",     TextFormatting.DARK_RED),
  ORANGE ("orange",  TextFormatting.GOLD),
  YELLOW ("yellow",  TextFormatting.YELLOW),
  GREEN  ("green",   TextFormatting.DARK_GREEN),
  CYAN   ("cyan",    TextFormatting.AQUA),
  BLUE   ("blue",    TextFormatting.BLUE),
  MAGENTA("magenta", TextFormatting.LIGHT_PURPLE);

  static {
    Debug.log_setup_info("Begin loading Lens class...");
  }

  public final Item lens;

  private Lens(){
    lens = new OverpoweredItem("focus_lens");
  }

  private Lens(final String color, final TextFormatting format_code){
    lens = new LensItem(color+"_lens", format_code);
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
