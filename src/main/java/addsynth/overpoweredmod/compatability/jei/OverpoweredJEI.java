package addsynth.overpoweredmod.compatability.jei;

import java.util.ArrayList;
import addsynth.core.util.StringUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredTechnology;
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
    return new ResourceLocation(OverpoweredTechnology.MOD_ID, "jei_plugin");
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
    registry.addIngredientInfo(new ItemStack(Init.celestial_gem),         VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.celestial_gem"));
    registry.addIngredientInfo(new ItemStack(Init.energy_crystal_shards), VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.energy_crystal_shards"));
    registry.addIngredientInfo(new ItemStack(Init.energy_crystal),        VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.energy_crystal"));
    registry.addIngredientInfo(new ItemStack(Init.light_block),           VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.light_block"));
    registry.addIngredientInfo(new ItemStack(Init.void_crystal),          VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.void_crystal"));
    registry.addIngredientInfo(new ItemStack(Init.null_block),            VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.null_block"));
    
    final ArrayList<ItemStack> celestial_tools = new ArrayList<>(6);
    celestial_tools.add(new ItemStack(Tools.overpowered_tools.sword));
    celestial_tools.add(new ItemStack(Tools.overpowered_tools.shovel));
    celestial_tools.add(new ItemStack(Tools.overpowered_tools.axe));
    celestial_tools.add(new ItemStack(Tools.overpowered_tools.pickaxe));
    celestial_tools.add(new ItemStack(Tools.overpowered_tools.hoe));
    registry.addIngredientInfo(celestial_tools, VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.celestial_tools"));

    final ArrayList<ItemStack> void_tools = new ArrayList<>(5);
    void_tools.add(new ItemStack(Tools.void_toolset.sword));
    void_tools.add(new ItemStack(Tools.void_toolset.shovel));
    void_tools.add(new ItemStack(Tools.void_toolset.axe));
    void_tools.add(new ItemStack(Tools.void_toolset.pickaxe));
    void_tools.add(new ItemStack(Tools.void_toolset.hoe));
    registry.addIngredientInfo(void_tools, VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.void_tools"));

    registry.addIngredientInfo(new ItemStack(Machines.crystal_energy_extractor), VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.crystal_energy_extractor"));
    registry.addIngredientInfo(new ItemStack(Wires.data_cable),              VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.data_cable"));
    registry.addIngredientInfo(new ItemStack(Machines.gem_converter),        VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.gem_converter"));
    registry.addIngredientInfo(new ItemStack(Machines.inverter),             VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.inverter"));
    registry.addIngredientInfo(new ItemStack(Machines.magic_infuser),        VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.magic_infuser"));
    registry.addIngredientInfo(new ItemStack(Machines.identifier),           VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.identifier"));
    registry.addIngredientInfo(new ItemStack(Machines.portal_control_panel), VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.portal_control_panel"));
    registry.addIngredientInfo(new ItemStack(Machines.portal_frame),         VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.portal_frame"));
    
    registry.addIngredientInfo(new ItemStack(Machines.laser_housing), VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.laser_housing"));
    final ArrayList<ItemStack> lasers = new ArrayList<>(8);
    lasers.add(new ItemStack(Laser.RED.cannon));
    lasers.add(new ItemStack(Laser.ORANGE.cannon));
    lasers.add(new ItemStack(Laser.YELLOW.cannon));
    lasers.add(new ItemStack(Laser.GREEN.cannon));
    lasers.add(new ItemStack(Laser.CYAN.cannon));
    lasers.add(new ItemStack(Laser.BLUE.cannon));
    lasers.add(new ItemStack(Laser.MAGENTA.cannon));
    lasers.add(new ItemStack(Laser.WHITE.cannon));
    registry.addIngredientInfo(lasers, VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.laser"));
    
    registry.addIngredientInfo(new ItemStack(Machines.energy_suspension_bridge), VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.energy_suspension_bridge"));
    registry.addIngredientInfo(new ItemStack(Machines.advanced_ore_refinery),    VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.advanced_ore_refinery"));
    registry.addIngredientInfo(new ItemStack(Machines.crystal_matter_generator), VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.crystal_matter_generator"));
    registry.addIngredientInfo(new ItemStack(ModItems.dimensional_anchor),       VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.dimensional_anchor"));
    registry.addIngredientInfo(new ItemStack(Init.black_hole),                   VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.black_hole"));
    
    registry.addIngredientInfo(new ItemStack(Machines.fusion_chamber),       VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.fusion_chamber"));
    registry.addIngredientInfo(new ItemStack(Machines.fusion_control_unit),  VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.fusion_control_unit"));
    registry.addIngredientInfo(new ItemStack(Machines.fusion_control_laser), VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.fusion_control_laser"));
    registry.addIngredientInfo(new ItemStack(Machines.fusion_converter),     VanillaTypes.ITEM, StringUtil.translate("gui.overpowered.jei_description.fusion_converter"));
  }

}
