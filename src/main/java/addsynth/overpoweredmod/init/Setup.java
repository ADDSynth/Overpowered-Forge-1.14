package addsynth.overpoweredmod.init;

import addsynth.core.material.Material;
import addsynth.core.worldgen.OreGenerator;
import addsynth.overpoweredmod.Debug;

public final class Setup {

  public static boolean items_registered;
  
  /** <p>This should ONLY be called from OverpoweredMod class.</p>
   *  <p>This doesn't actually set up the WorldGenerators, instead it sets a boolean value in ADDSynthCore.
   *  It's ADDSynthCore that actually sets up the WorldGenerators when its Initialization Event is fired.
   *  So you have to ensure that it gets fired AFTER this function is called.</p>
   */
  public static final void register_world_generators(){
    Debug.log_setup_info("Begin registering World Generators...");

    OreGenerator.request_to_generate(Material.RUBY);
    OreGenerator.request_to_generate(Material.TOPAZ);
    OreGenerator.request_to_generate(Material.CITRINE);
    OreGenerator.request_to_generate(Material.EMERALD);
    OreGenerator.request_to_generate(Material.SAPPHIRE);
    OreGenerator.request_to_generate(Material.AMETHYST);
    OreGenerator.request_to_generate(Material.TIN);
    OreGenerator.request_to_generate(Material.ALUMINUM);
    OreGenerator.request_to_generate(Material.COPPER);
    OreGenerator.request_to_generate(Material.SILVER);
    OreGenerator.request_to_generate(Material.PLATINUM);
    OreGenerator.request_to_generate(Material.TITANIUM);
    
    Debug.log_setup_info("Finished registering World Generators.");
  }

}
