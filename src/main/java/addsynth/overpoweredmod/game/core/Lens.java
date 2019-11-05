package addsynth.overpoweredmod.game.core;

import addsynth.core.util.RecipeUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.LensItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.TextFormatting;

public enum Lens {

  WHITE  (),
  RED    (WHITE.lens, "red",     "Red",     TextFormatting.DARK_RED),
  ORANGE (WHITE.lens, "orange",  "Orange",  TextFormatting.GOLD),
  YELLOW (WHITE.lens, "yellow",  "Yellow",  TextFormatting.YELLOW),
  GREEN  (WHITE.lens, "green",   "Green",   TextFormatting.DARK_GREEN),
  CYAN   (WHITE.lens, "cyan",    "Cyan",    TextFormatting.AQUA),
  BLUE   (WHITE.lens, "blue",    "Blue",    TextFormatting.BLUE),
  MAGENTA(WHITE.lens, "magenta", "Magenta", TextFormatting.LIGHT_PURPLE);

  static {
    Debug.log_setup_info("Begin loading Lens class...");
  }

  public final Item lens;
  public final IRecipe recipe;

  private Lens(){
    lens = new OverpoweredItem("focus_lens");
    recipe = RecipeUtil.create("Lenses",lens,"gemQuartz");
  }

  private Lens(final Item focus_lens, final String color, final String dye_color, final TextFormatting format_code){
    lens = new LensItem(color+"_lens", format_code);
    recipe = RecipeUtil.create("Lenses",lens,focus_lens,"dye"+dye_color);
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
