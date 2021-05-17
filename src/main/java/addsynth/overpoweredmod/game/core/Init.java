package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.blocks.IronFrameBlock;
import addsynth.overpoweredmod.blocks.LightBlock;
import addsynth.overpoweredmod.blocks.NullBlock;
import addsynth.overpoweredmod.items.EnergyCrystal;
import addsynth.overpoweredmod.items.EnergyCrystalShards;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.VoidCrystal;
import addsynth.overpoweredmod.machines.black_hole.BlackHole;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public final class Init {

  static {
    Debug.log_setup_info("Begin loading Init class...");
  }

  public static final Item                    celestial_gem            = new OverpoweredItem("celestial_gem");
  public static final Item                    energy_crystal_shards    = new EnergyCrystalShards("energy_crystal_shards");
  public static final Item                    energy_crystal           = new EnergyCrystal("energy_crystal");
  public static final Block                   light_block              = new LightBlock("light_block");

  public static final Item                    void_crystal             = new VoidCrystal("void_crystal");
  public static final Block                   null_block               = new NullBlock("null_block");

  public static final Block                   iron_frame_block         = new IronFrameBlock("iron_frame_block");
  public static final BlackHole               black_hole               = new BlackHole("black_hole");

  static {
    Debug.log_setup_info("Finished loading Init class.");
  }

}
