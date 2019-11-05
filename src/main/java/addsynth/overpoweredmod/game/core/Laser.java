package addsynth.overpoweredmod.game.core;

import addsynth.core.util.RecipeUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.blocks.tiles.laser.LaserBeam;
import addsynth.overpoweredmod.blocks.tiles.laser.LaserCannon;
import addsynth.overpoweredmod.game.core.Lens;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;

public enum Laser {

  WHITE  ("white"),
  RED    ("red"),
  ORANGE ("orange"),
  YELLOW ("yellow"),
  GREEN  ("green"),
  CYAN   ("cyan"),
  BLUE   ("blue"),
  MAGENTA("magenta");

  static {
    Debug.log_setup_info("Begin loading Lasers class...");
  }

  public final LaserCannon cannon;
  public final Block beam;
  public final IRecipe recipe;

  public static final Laser[] index = Laser.values();
  
  private Laser(final String color){
    this.cannon = new LaserCannon(color+"_laser", this.ordinal());
    this.beam   = new LaserBeam(color+"_laser_beam");
    this.recipe = RecipeUtil.create("Lasers",3,3, new ItemStack(OverpoweredMod.registry.getItemBlock(this.cannon),1),
      Ingredient.EMPTY, "stone", "stone",
      Lens.index[this.ordinal()], ModItems.beam_emitter, new ItemStack(OverpoweredMod.registry.getItemBlock(Wires.wire),1),
      Ingredient.EMPTY, "stone", "stone");
  }

  static {
    Debug.log_setup_info("Finished loading Lasers class.");
  }

}
