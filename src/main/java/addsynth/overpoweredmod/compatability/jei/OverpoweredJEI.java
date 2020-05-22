package addsynth.overpoweredmod.compatability.jei;

import java.util.ArrayList;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.ModItems;
import addsynth.overpoweredmod.game.core.Portal;
import addsynth.overpoweredmod.game.core.Tools;
import addsynth.overpoweredmod.game.core.Wires;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipes;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import addsynth.overpoweredmod.machines.inverter.InverterRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public final class OverpoweredJEI implements IModPlugin {

  static {
    Debug.log_setup_info("Overpowered JEI Plugin class was loaded.");
  }

// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/client/jei/GalacticraftJEI.java#L90

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime){
    final ArrayList<ItemStack> blacklist = new ArrayList<>(1);
    blacklist.add(new ItemStack(Portal.portal_image));
    jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, blacklist);
  }

  @Override
  public ResourceLocation getPluginUid(){
    return new ResourceLocation(OverpoweredMod.MOD_ID, "jei_plugin");
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration){
    final IJeiHelpers jei_helpers = registration.getJeiHelpers();
    final IGuiHelper gui_helper = jei_helpers.getGuiHelper();
    registration.addRecipeCategories(
      new GemConverterCategory(gui_helper),
      new AdvancedOreRefineryCategory(gui_helper),
      new InverterCategory(gui_helper)
    );
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    registration.addRecipes(GemConverterRecipe.recipes, GemConverterCategory.id);
    registration.addRecipes(OreRefineryRecipes.recipes, AdvancedOreRefineryCategory.id);
    registration.addRecipes(InverterRecipe.get_recipes(), InverterCategory.id);
    add_information(registration);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(Machines.gem_converter), GemConverterCategory.id);
    registration.addRecipeCatalyst(new ItemStack(Machines.advanced_ore_refinery), AdvancedOreRefineryCategory.id);
    registration.addRecipeCatalyst(new ItemStack(Machines.inverter), InverterCategory.id);
  }

  private static final void add_information(IRecipeRegistration registry){
    final ArrayList<ItemStack> energy_tools = new ArrayList<>(6);
    energy_tools.add(new ItemStack(Tools.energy_tools.sword));
    energy_tools.add(new ItemStack(Tools.energy_tools.shovel));
    energy_tools.add(new ItemStack(Tools.energy_tools.axe));
    energy_tools.add(new ItemStack(Tools.energy_tools.pickaxe));
    energy_tools.add(new ItemStack(Tools.energy_tools.hoe));
    registry.addIngredientInfo(energy_tools, VanillaTypes.ITEM, "Overpowered Tools have 3x the durability as Diamond tools, and are generally faster and stronger. Overpowered tools cannot be enchanted.");

    final ArrayList<ItemStack> void_tools = new ArrayList<>(5);
    void_tools.add(new ItemStack(Tools.void_toolset.sword));
    void_tools.add(new ItemStack(Tools.void_toolset.shovel));
    void_tools.add(new ItemStack(Tools.void_toolset.axe));
    void_tools.add(new ItemStack(Tools.void_toolset.pickaxe));
    void_tools.add(new ItemStack(Tools.void_toolset.hoe));
    registry.addIngredientInfo(void_tools, VanillaTypes.ITEM, "Void Tools are still being worked on. Although they currently have 5x the durability of Diamond tools, they are not faster or stronger. Void tools cannot be enchanted.");

    registry.addIngredientInfo(new ItemStack(Machines.generator), VanillaTypes.ITEM, "The Generator is the primary means of generating Energy. You can insert either an Energy Crystal, Energy Crystal Shards, or a Light Block. Each generate Energy at different rates.");
    registry.addIngredientInfo(new ItemStack(Wires.data_cable), VanillaTypes.ITEM, "Data Cable is used in the construction of the Portal and the construction of the Fusion Energy structure, as well as some crafting recipes.");
    registry.addIngredientInfo(new ItemStack(Machines.gem_converter), VanillaTypes.ITEM, "The Gem Converter, as the name implies, allows you to convert one gem type to another. This machine works with Ruby, Topaz, Citrine, Emerald, Diamond, Sapphire, Amethyst, and Quartz.");
    registry.addIngredientInfo(new ItemStack(Machines.inverter),      VanillaTypes.ITEM, "This machine will transform Energy Crystals into Void Crystals, and transforms Void Crystals into Energy Crystals.");
    registry.addIngredientInfo(new ItemStack(Machines.magic_infuser), VanillaTypes.ITEM, "Insert a Book and a type of gem, and this machine will enchant the Book. The enchantment you get depends on the gem type.");
    registry.addIngredientInfo(new ItemStack(Machines.identifier),    VanillaTypes.ITEM, "Monsters will occasionally drop an unidentified item. Insert those items into this machine and it will identify them for you.");
    registry.addIngredientInfo(new ItemStack(Machines.portal_control_panel), VanillaTypes.ITEM, "The Portal Control Panel is the machine that generates the Portal to the Unknown Dimension. It must be connected to the main Portal Frame using Data Cable.");
    registry.addIngredientInfo(new ItemStack(Machines.portal_frame),  VanillaTypes.ITEM, "The main portal frame consists of a ring of blocks arranged in a 5x5 area. 8 Portal Frames must be placed in the corners and sides while the other spaces can be filled with either Data Cable or the Iron Frame Block. Finally, a different gem Block must be inserted into each Portal Frame before you can generate the portal.");
    
    registry.addIngredientInfo(new ItemStack(Machines.laser_housing), VanillaTypes.ITEM, "This is the machine that controls how Lasers operate. Attach Lasers pointing in the direction you want to fire. Access the machine and set how far you want the laser to shoot. Then, once the laser has built up enough energy, a redstone signal will fire the laser. If multiple Laser machines are adjacent to each other, they will all fire at once.");
    final ArrayList<ItemStack> lasers = new ArrayList<>(8);
    lasers.add(new ItemStack(Laser.RED.cannon));
    lasers.add(new ItemStack(Laser.ORANGE.cannon));
    lasers.add(new ItemStack(Laser.YELLOW.cannon));
    lasers.add(new ItemStack(Laser.GREEN.cannon));
    lasers.add(new ItemStack(Laser.CYAN.cannon));
    lasers.add(new ItemStack(Laser.BLUE.cannon));
    lasers.add(new ItemStack(Laser.MAGENTA.cannon));
    lasers.add(new ItemStack(Laser.WHITE.cannon));
    registry.addIngredientInfo(lasers, VanillaTypes.ITEM, "These are the Laser Cannons that you attach to Laser Machines. Access the Laser machine to configure how the Laser operates. Lasers will mine blocks as though you mined them with the correct tool. Entities caught in the Laser Beams will also get set on fire.");
    
    registry.addIngredientInfo(new ItemStack(Machines.energy_suspension_bridge), VanillaTypes.ITEM, "The Energy Suspension Bridge, when supplied with power and activated with a redstone signal, will spawn an energy bridge across to another Energy Suspension Bridge. You must insert a Lens of the color you want. And the space between the two Energy Suspension Blocks must be clear.");
    registry.addIngredientInfo(new ItemStack(ModItems.unknown_technology), VanillaTypes.ITEM, "This device of unknown origins is used in the highest-tier machines such as the Advanced Ore Refinery and Crystal Matter Generator, and can only by found in the Unknown Dimension.");
    registry.addIngredientInfo(new ItemStack(Machines.advanced_ore_refinery), VanillaTypes.ITEM, "Insert Ores into this machine and it will produce double the output. Automatically works with Ores from other mods if they've registered their Ore with an Ore tag.");
    registry.addIngredientInfo(new ItemStack(Machines.crystal_matter_generator), VanillaTypes.ITEM, "This machine requires a lot of power, and must be running at all times. After a while, it will generate a random gem shard. You can combine 9 gem shards of the same type to make a full gem.");
    registry.addIngredientInfo(new ItemStack(ModItems.dimensional_anchor), VanillaTypes.ITEM, "This currently has no purpose.");
    registry.addIngredientInfo(new ItemStack(Init.black_hole), VanillaTypes.ITEM, "Whoa. Be careful with this! If you place this down, it will erase your world! I don't know how you got this, but you should destroy it immediately!");
    
    registry.addIngredientInfo(new ItemStack(Machines.fusion_chamber), VanillaTypes.ITEM, "Part of the Fusion Energy structure, this is the block that is in the center. It must contain a Fusion Core in order to be considered valid.");
    registry.addIngredientInfo(new ItemStack(Machines.fusion_control_unit), VanillaTypes.ITEM, "Part of the Fusion Energy structure. You need 6 of these to be placed on each side of the Fusion Chamber, 5 blocks away. Each one must be connected to each other and the Fusion Energy Converter with Data Cable.");
    registry.addIngredientInfo(new ItemStack(Machines.fusion_control_laser), VanillaTypes.ITEM, "Part of the Fusion Energy structure. You must craft 6 of these and place them on each Fusion Control Unit, pointing towards the Fusion Chamber.");
    registry.addIngredientInfo(new ItemStack(Machines.fusion_converter), VanillaTypes.ITEM, "Part of the Fusion Energy structure. You must first set up the other blocks in their correct positions, then connect the Fusion Energy Converter with the Fusion Control Units using Data Cable. The Fusion Chamber must also contain a Fusion Core. Once everything is set up correctly, activate the Fusion Energy Converter with a redstone signal to turn it on and start generating Energy.");
  }

}
