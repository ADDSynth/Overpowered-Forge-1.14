package addsynth.core.game;

import java.util.Map;
import java.util.TreeMap;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.JavaUtils;
import net.minecraftforge.fml.ModList;

/**
 * <p>This class asks the Forge Mod Loader if certain mods are loaded. Since a specific string value is
 *    used, they are all listed here in case the mod developer changes their mod ID, then I can easily
 *    change it here.</p>
 * <p>The Forge Mod Loader automatically designates a mod to be loaded as soon as it finds its jar file.</p>
 * <p>Whatever code first requests a value from here will then initialize the whole class.</p>
 * @author ADDSynth
 * @since October 28, 2019
 */
public final class Compatability {

  // Java doesn't have structs either?! That's another stupid thing about Java!

  // NOTE: The original purpose of doing it like this, individual classes, all static references,
  //       was because in the 1.12 and earlier versions used Forges @Optional annotation to denote
  //       certain classes and methods as Optional, but available ONLY if a certain mod was loaded.
  //       The annotation required a constant expression for the Mod ID.
  // There's no reason to change this back to extending from a base MOD class.
  // I CAN, but it's better to leave it as static constant values in case I need them again.

  public static final boolean isAPI(final String modid){
    return modid.equals(BAUBLES.modid) || modid.equals(FORGE_MULTIPART.modid) || modid.equals(REDSTONE_FLUX.modid)    ||
           modid.equals(TESLA.modid)   || modid.equals(ADDSynthCore.MOD_ID)   || modid.equals(CODE_CHICKEN_LIB.modid) ||
           modid.equals(MANTLE.modid)  || modid.equals(ADDSYNTH_ENERGY.modid) || modid.equals(SHADOWFACTS_FORGELIN.modid);
  }

  public static final class ACTUALLY_ADDITIONS {
    public static final String name = "Actually Additions";
    public static final String modid = "actuallyadditions";
    public static final boolean loaded = ModList.get().isLoaded(modid);
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

  public static final class BAUBLES { // FUTURE: Baubles no longer exists. Change this to detect the Curios mod when we Re-add Rings in Overpowered v1.5.
    public static final String name  = "Baubles";
    public static final String modid = "baubles";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class BIOMES_O_PLENTY {
    public static final String name  = "Biomes O' Plenty";
    public static final String modid = "biomesoplenty";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class BLOOD_MAGIC {
    public static final String name = "Blood Magic";
    public static final String modid = "bloodmagic";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class BOTANIA {
    public static final String name = "Botania";
    public static final String modid = "botania";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class BUILDCRAFT {
    public static final String name  = "Buildcraft";
    public static final String modid = "buildcraftcore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class CHISELS_AND_BITS {
    public static final String name = "Chisels & Bits";
    public static final String modid = "chiselsandbits";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class CODE_CHICKEN_LIB {
    public static final String name = "CodeChicken Lib";
    public static final String modid = "codechickenlib";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class DYNAMIC_SURROUNDINGS {
    public static final String name = "Dynamic Surroundings";
    public static final String modid = "dsurround";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class ENDER_IO {
    public static final String name  = "Ender IO";
    public static final String modid = "enderio";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class EXTRA_UTILITIES {
    public static final String name = "Extra Utilities 2";
    public static final String modid = "extrautils2";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class EXTREME_REACTORS {
    public static final String name  = "Extreme Reactors";
    public static final String modid = "bigreactors";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class FORGE_MULTIPART {
    public static final String name = "Forge Multipart CBE";
    public static final String modid = "forgemultipartcbe";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class GALACTICRAFT {
    public static final String name  = "Galacticraft";
    public static final String modid = "galacticraftcore";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class INDUSTRIAL_CRAFT {
    public static final String name = "Industrial Craft";
    public static final String modid = "ic2";
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
  
  public static final class JOURNEY_MAP {
    public static final String name = "Journey Map";
    public static final String modid = "journeymap";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class MANTLE {
    public static final String name = "Mantle";
    public static final String modid = "mantle";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class MEKANISM {
    public static final String name = "Mekanism";
    public static final String modid = "mekanism";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }

  public static final class MYSTICAL_AGRICULTURE {
    public static final String name = "Mystical Agriculture";
    public static final String modid = "mysticalagriculture";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class NATURA {
    public static final String name = "Natura";
    public static final String modid = "natura";
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
  
  public static final class PAMS_HARVESTCRAFT {
    public static final String name = "Pam's Harvestcraft";
    public static final String modid = "harvestcraft";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class PROJECT_E {
    public static final String name  = "Project E";
    public static final String modid = "projecte";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class PROJECT_RED {
    public static final String name = "Project Red";
    public static final String modid = "projectred-core";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class QUARK {
    public static final String name  = "Quark";
    public static final String modid = "quark";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class RAILCRAFT { // FUTURE: Railcraft potentially not coming to Minecraft 1.14+ (this message written Apr 2020)
    public static final String name  = "Railcraft";
    public static final String modid = "railcraft";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }

  public static final class REDSTONE_FLUX {
    public static final String name  = "Redstone Flux API";
    public static final String modid = "redstoneflux";
    public static final boolean loaded = ModList.get().isLoaded(modid) || JavaUtils.packageExists("cofh.redstoneflux.api");
  }

  public static final class SHADOWFACTS_FORGELIN {
    public static final String name = "Shadowfacts' Forgelin";
    public static final String modid = "forgelin";
    public static final boolean loaded = ModList.get().isLoaded(modid);
  }
  
  public static final class TESLA {
    public static final String name  = "Tesla Energy";
    public static final String modid = "tesla";
    public static final boolean loaded = ModList.get().isLoaded(modid) || JavaUtils.packageExists("net.darkhax.tesla.api");
  }

  public static final class TINKERS_CONSTRUCT {
    public static final String name = "Tinkers' Construct";
    public static final String modid = "tconstruct";
    public static final boolean loaded = ModList.get().isLoaded(modid);
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
