package addsynth.core.game;

import java.util.Map;
import java.util.TreeMap;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.JavaUtils;
import net.minecraftforge.fml.ModList;

/**<p>
 * This class asks the Forge Mod Loader if certain mods are loaded. Since a specific string value is
 * used, they are all listed here in case the mod developer changes their mod_id, then I can easily
 * change it here.
 * </p>
 * <p>
 * The Forge Mod Loader automatically designates a mod to be loaded as soon as it finds its jar file.
 * </p>
 * <p>
 * Whatever code first requests a value from here will then initialize the whole class.
 * And values cannot be changed.
 * </p>
 * @author ADDSynth
 * @since October 28, 2019
 */
public final class Compatability {

  // Java doesn't have structs either?! That's another stupid thing about Java!

  public static final boolean isAPI(final String modid){
    return modid.equals(BAUBLES.modid)       || modid.equals(TESLA.modid) || modid.equals(REDSTONE_FLUX.modid) ||
           modid.equals(ADDSynthCore.MOD_ID);
  }

  public static final class ADDSYNTH_ENERGY {
    public static final String name = "ADDSynth Energy";
    public static final String modid = "addsynth_energy";
    public static final boolean loded = ModList.get().isLoaded(modid);
  }

  public static final class APPLIED_ENERGISTICS {
    public static final String name  = "Applied Energistics";
    public static final String modid = "appliedenergistics2";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }

  public static final class BAUBLES { // OPTIMIZE: Baubles no longer exists in Minecraft versions 1.13+. Also remove translation keys for Rings when this is permanently removed.
    public static final String name  = "Baubles";
    public static final String modid = "baubles";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class BIOMES_O_PLENTY {
    public static final String name  = "Biomes O' Plenty";
    public static final String modid = "biomesoplenty";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class BUILDCRAFT {
    public static final String name  = "Buildcraft";
    public static final String modid = "buildcraftcore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class ENDER_IO {
    public static final String name  = "Ender IO";
    public static final String modid = "enderio";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class EXTREME_REACTORS {
    public static final String name  = "Extreme Reactors";
    public static final String modid = "bigreactors";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class GALACTICRAFT {
    public static final String name  = "Galacticraft";
    public static final String modid = "galacticraftcore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class INDUSTRIAL_FOREGOING {
    public static final String name  = "Industrial Foregoing";
    public static final String modid = "industrialforegoing";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class JEI {
    public static final String name  = "JEI";
    public static final String modid = "jei";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class OPENCOMPUTERS {
    public static final String name  = "OpenComputers";
    public static final String modid = "opencomputers";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class OVERPOWERED {
    public static final String name  = "Overpowered";
    public static final String modid = "overpowered";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class PROJECT_E {
    public static final String name  = "Project E";
    public static final String modid = "projecte";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class QUARK {
    public static final String name  = "Quark";
    public static final String modid = "quark";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class RAILCRAFT {
    public static final String name  = "Railcraft";
    public static final String modid = "railcraft";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }

  public static final class REDSTONE_FLUX {
    public static final String name  = "Redstone Flux API";
    public static final String modid = "redstoneflux";
    public static final boolean loaded = ModList.get().isLoaded(modid) || JavaUtils.packageExists("cofh.redstoneflux.api");
  }

  public static final class TESLA {
    public static final String name  = "Tesla Energy";
    public static final String modid = "tesla";
    public static final boolean loaded = ModList.get().isLoaded(modid) || JavaUtils.packageExists("net.darkhax.tesla.api");
  }

  public static final void debug(){
    ADDSynthCore.log.info("Begin printing ADDSynthCore mod detection results:");
    final TreeMap<String, Boolean> modlist = new TreeMap<>();
    try{
      for(Class clazz : Compatability.class.getClasses()){
        modlist.put((String)(clazz.getField("name").get(null)), (boolean)(clazz.getField("loaded").get(null)));
      }
    }
    catch(NoSuchFieldException e){
      ADDSynthCore.log.error("Error while iterating through the mods in the "+Compatability.class.getName()+" class file. The class fields are not correctly named 'name', 'modid', and 'loaded'! A spelling error is the #1 mistake and the bane of all programmers!");
    }
    catch(Exception e){
      e.printStackTrace();
    }
    for(Map.Entry<String,Boolean> mod : modlist.entrySet()){
      ADDSynthCore.log.info(mod.getKey() + ": "+(mod.getValue() ? "loaded" : "not detected"));
    }
    ADDSynthCore.log.info("Done checking. ADDSynthCore does not check for any mods that are not listed here.");
  }

}
