package addsynth.overpoweredmod.game.recipes;

import addsynth.core.gameplay.items.ScytheTool;
import addsynth.core.material.types.Gem;
import addsynth.core.material.types.Metal;
import addsynth.core.util.RecipeUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.*;
import addsynth.overpoweredmod.init.Setup;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public final class Recipes {

/*
  @SubscribeEvent
  public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
    ResourceLocation VARIABLE NAME HERE = new ResourceLocation("MODID:ITEMNAME");
    IForgeRegistryModifiable modRegistry = (IForgeRegistryModifiable) event.getRegistry();
    modRegistry.remove(VARIABLE NAME HERE);
  }
*/

  public static final void register(){
    Debug.log_setup_info("Begin registering Crafting Recipes...");
    
    // Ingredients used in recipes
    final Ingredient glass_pane           = Ingredient.fromStacks(new ItemStack(Blocks.GLASS_PANE,1)); // MAYBE: can this be an OreDictionary (and should it?)
    final Ingredient energy_crystal       = Ingredient.fromStacks(new ItemStack(Init.energy_crystal,1));
    final Ingredient energy_crystal_shard = Ingredient.fromStacks(new ItemStack(Init.energy_crystal_shards,1));
    final Ingredient void_crystal         = Ingredient.fromStacks(new ItemStack(Init.void_crystal,1));
    final Ingredient power_core           = Ingredient.fromStacks(new ItemStack(ModItems.power_core,1));
    final Ingredient advanced_power_core  = Ingredient.fromStacks(new ItemStack(ModItems.advanced_power_core,1));
    final Ingredient energized_power_core = Ingredient.fromStacks(new ItemStack(ModItems.energized_power_core,1));
    final Ingredient nullified_power_core = Ingredient.fromStacks(new ItemStack(ModItems.nullified_power_core,1));
    final Ingredient beam_emitter         = Ingredient.fromStacks(new ItemStack(ModItems.beam_emitter,1));
    final Ingredient energy_grid          = Ingredient.fromStacks(new ItemStack(ModItems.energy_grid,1));
    final Ingredient wire                 = Ingredient.fromStacks(new ItemStack(Wires.wire,1));
    final Ingredient data_cable           = Ingredient.fromStacks(new ItemStack(Wires.data_cable,1));
    final Ingredient focus_lens           = Ingredient.fromStacks(new ItemStack(Lens.focus_lens,1));
    final Ingredient unknown_technology   = Ingredient.fromStacks(new ItemStack(ModItems.unknown_technology,1));
    final Ingredient trophy_base          = Ingredient.fromStacks(new ItemStack(Trophy.trophy_base,1));

    final String iron_plate     = Features.compressor ? "plateIron"     : "ingotIron";
    final String gold_plate     = Features.compressor ? "plateGold"     : "ingotGold";
    final String tin_plate      = Features.compressor ? "plateTin"      : "ingotTin";
    final String copper_plate   = Features.compressor ? "plateCopper"   : "ingotCopper";
    final String aluminum_plate = Features.compressor ? "plateAluminum" : "ingotAluminum";
    final String steel_plate    = Features.compressor ? "plateSteel"    : "ingotSteel";
    final String bronze_plate   = Features.compressor ? "plateBronze"   : "ingotBronze";
    final String silver_plate   = Features.compressor ? "plateSilver"   : "ingotSilver";
    final String platinum_plate = Features.compressor ? "platePlatinum" : "ingotPlatinum";
    final String titanium_plate = Features.compressor ? "plateTitanium" : "ingotTitanium";
  
    final Object iron_frame_block = Features.iron_frame_block ? new ItemStack(Init.iron_frame_block,1) : "blockIron";
    // for use for the vacuum container recipe, I thought of using a Nullified Power Core, but nah.
    final Object null_block       = Features.null_block       ? new ItemStack(Init.null_block,1)       : advanced_power_core;
  
    // Gems
    for(Gem gem : Gems.index){
      RecipeUtil.register(gem.shards_to_gem_recipe, new ResourceLocation(OverpoweredMod.MOD_ID, gem.id_name+"_from_shards"));
      if(gem.custom){
        RecipeUtil.register(gem.gems_to_block_recipe, new ResourceLocation(OverpoweredMod.MOD_ID, gem.id_name+"_block"));
        RecipeUtil.register(gem.block_to_gems_recipe, new ResourceLocation(OverpoweredMod.MOD_ID, gem.id_name+"_from_block"));
      }
    }

    // Metals
    for(Metal metal : Metals.values){
      if(metal.custom){
        RecipeUtil.register(metal.ingots_to_block_recipe, new ResourceLocation(OverpoweredMod.MOD_ID, metal.id_name+"_block"));
        RecipeUtil.register(metal.block_to_ingots_recipe, new ResourceLocation(OverpoweredMod.MOD_ID, metal.id_name+"_from_block"));
      }
    }

    // Crystals
    RecipeUtil.register(new ItemStack(Init.energy_crystal,3), Init.energy_crystal.getRegistryName(),
        new Object[] {"gemRuby","gemTopaz","gemCitrine","gemEmerald","gemDiamond","gemSapphire","gemAmethyst","gemQuartz"}) ;
    RecipeUtil.register(new ItemStack(Init.energy_crystal_shards,9),Init.energy_crystal_shards.getRegistryName(),energy_crystal);
    if(Features.light_block){
      RecipeUtil.register(new ItemStack(Init.light_block,1), Init.light_block.getRegistryName(), energy_crystal, energy_crystal,
        energy_crystal, energy_crystal, energy_crystal, energy_crystal, energy_crystal, energy_crystal, energy_crystal);
      RecipeUtil.register(new ItemStack(Init.energy_crystal,9), "energy_crystals_from_block", new Object[] {new ItemStack(Init.light_block,1)});
    }
    if(Features.null_block){
      RecipeUtil.register(2,2,new ItemStack(Init.null_block,1), Init.null_block.getRegistryName(), void_crystal, void_crystal, void_crystal, void_crystal);
      RecipeUtil.register(new ItemStack(Init.void_crystal,4), "void_crystals_from_block", new Object[] {new ItemStack(Init.null_block,1)});
    }
    
    // Tools
    if(Features.energy_tools){
      RecipeUtil.registerSword(  "Energy Tools", Tools.energy_tools.sword,   energy_crystal);
      RecipeUtil.registerPickaxe("Energy Tools", Tools.energy_tools.pickaxe, energy_crystal);
      RecipeUtil.registerAxe(    "Energy Tools", Tools.energy_tools.axe,     energy_crystal);
      RecipeUtil.registerShovel( "Energy Tools", Tools.energy_tools.shovel,  energy_crystal);
      RecipeUtil.registerHoe(    "Energy Tools", Tools.energy_tools.hoe,     energy_crystal);
      if(addsynth.core.config.Features.scythes){
        ScytheTool.registerRecipe(Tools.energy_scythe, energy_crystal);
      }
    }
    if(Features.void_tools){
      RecipeUtil.registerSword(  "Void Tools", Tools.void_toolset.sword,   void_crystal);
      RecipeUtil.registerPickaxe("Void Tools", Tools.void_toolset.pickaxe, void_crystal);
      RecipeUtil.registerAxe(    "Void Tools", Tools.void_toolset.axe,     void_crystal);
      RecipeUtil.registerShovel( "Void Tools", Tools.void_toolset.shovel,  void_crystal);
      RecipeUtil.registerHoe(    "Void Tools", Tools.void_toolset.hoe,     void_crystal);
    }

    // Items
    RecipeUtil.register(2, 2, new ItemStack(ModItems.power_core,1), new Object[] {"dustRedstone", "dustRedstone", "dustRedstone", "dustRedstone"});
    RecipeUtil.register(3, 3, new ItemStack(ModItems.advanced_power_core,1), new Object[] {
      Ingredient.EMPTY, "dyeBlue",   Ingredient.EMPTY,
      "dyeBlue",         power_core, "dyeBlue",
      Ingredient.EMPTY, "dyeBlue",   Ingredient.EMPTY});
    RecipeUtil.register(new ItemStack(ModItems.energized_power_core,1), new Object[] {advanced_power_core, energy_crystal, energy_crystal});
    RecipeUtil.register(new ItemStack(ModItems.nullified_power_core,1), new Object[] {advanced_power_core, void_crystal, void_crystal});
    RecipeUtil.register(new ItemStack(ModItems.beam_emitter,1), new Object[] {"paneGlassColorless", power_core, iron_plate}); // MAYBE: have this use the glass_pane local variable above, once I convert it to an OreDictionary Name.
    RecipeUtil.register(new ItemStack(ModItems.energy_grid,1), new Object[] {steel_plate, beam_emitter});
    RecipeUtil.register(3, 3, new ItemStack(ModItems.vacuum_container,1), new Object[] {
      glass_pane, glass_pane, glass_pane,
      glass_pane, null_block, glass_pane,
      glass_pane, glass_pane, glass_pane});
      
    RecipeUtil.register(3, 3, new ItemStack(ModItems.fusion_core,1), new Object[]{
      Ingredient.EMPTY, Items.FIRE_CHARGE, Ingredient.EMPTY,
      Items.FIRE_CHARGE, unknown_technology, Items.FIRE_CHARGE,
      Ingredient.EMPTY, Items.FIRE_CHARGE, Ingredient.EMPTY});
      
    if(Features.dimensional_anchor){
      RecipeUtil.register(3, 3, new ItemStack(ModItems.dimensional_anchor,1), new Object[]{
        energy_crystal, "enderpearl", void_crystal,
        "enderpearl", unknown_technology, "enderpearl",
        void_crystal, "enderpearl", energy_crystal});
    }

    // Lenses
    for(Lens lens : Lens.values()){
      RecipeUtil.register(lens.recipe, lens.lens.getRegistryName());
    }

    // Machines
    RecipeUtil.register(3, 3, new ItemStack(Wires.wire,8), new Object[]{
      Blocks.WHITE_WOOL, Blocks.WHITE_WOOL, Blocks.WHITE_WOOL,
      "ingotCopper", "ingotCopper", "ingotCopper",
      Blocks.WHITE_WOOL, Blocks.WHITE_WOOL, Blocks.WHITE_WOOL});
      
    RecipeUtil.register(3,3, new ItemStack(Wires.data_cable,6), new Object[]{
      Blocks.WHITE_WOOL, Blocks.WHITE_WOOL, Blocks.WHITE_WOOL,
      energy_crystal_shard, energy_crystal_shard, energy_crystal_shard,
      Blocks.WHITE_WOOL, Blocks.WHITE_WOOL, Blocks.WHITE_WOOL});
      
    RecipeUtil.register(3, 3, new ItemStack(Machines.generator,1), new Object[]{
      "ingotIron", power_core, "ingotIron",
      wire,        Blocks.FURNACE, wire,
      "ingotIron", power_core, "ingotIron"});
      
    RecipeUtil.register(3, 3, new ItemStack(Machines.energy_storage,1), new Object[]{
      "blockGlass", wire, "blockGlass",
      wire,         energy_grid, wire,
      "blockGlass", wire, "blockGlass"});

    if(Features.universal_energy_interface){
      RecipeUtil.registerMachine(Machines.universal_energy_machine, new Object[]{
        iron_plate, data_cable,          iron_plate,
        wire,       energy_grid,         wire,
        iron_plate, advanced_power_core, iron_plate});
    }

    if(Features.compressor){
      RecipeUtil.register(3, 3, new ItemStack(Machines.compressor,1), new Object[]{
        "ingotSteel", Blocks.PISTON, "ingotSteel",
        power_core,   Ingredient.EMPTY, power_core,
        "ingotSteel", Blocks.ANVIL,  "ingotSteel"});
    }
    else{ // Need to have a way to get Bronze Ingots if the Compressor is disabled.
      RecipeUtil.register(new ItemStack(Metals.BRONZE.ingot,2), new Object[]{"ingotTin", "ingotCopper"});
    }

    if(Features.electric_furnace){
      RecipeUtil.registerMachine(new ItemStack(Machines.electric_furnace), new Object[]{
        iron_plate, iron_plate, iron_plate,
        iron_plate, Blocks.FURNACE, iron_plate,
        power_core, power_core, power_core});
    }

    if(Features.gem_converter){
      RecipeUtil.register(3, 3, new ItemStack(Machines.gem_converter,1), new Object[]{
        "gemRuby",    "gemQuartz",      "gemAmethyst",
        "gemTopaz",   iron_frame_block, "gemSapphire",
        "gemCitrine", "gemEmerald",     "gemDiamond"});
    }

    RecipeUtil.register(3, 3, new ItemStack(Machines.inverter,1), new Object[]{
      silver_plate, energy_crystal, silver_plate,
      advanced_power_core, energy_grid, advanced_power_core,
      silver_plate, energy_crystal, silver_plate});

    if(Features.magic_infuser){
      RecipeUtil.registerMachine(new ItemStack(Machines.magic_infuser,1), new Object[]{
        aluminum_plate, void_crystal, aluminum_plate,
        void_crystal, Blocks.BOOKSHELF, void_crystal,
        aluminum_plate, void_crystal, aluminum_plate});
    }

    if(Features.identifier){
      RecipeUtil.registerMachine(new ItemStack(Machines.identifier,1), new Object[]{
        aluminum_plate, focus_lens,          aluminum_plate,
        data_cable,     advanced_power_core, data_cable,
        aluminum_plate, void_crystal,        aluminum_plate});
    }

    if(Features.lasers){
      RecipeUtil.register(3,3, new ItemStack(Machines.laser_housing,3), new Object[]{
        tin_plate, tin_plate, tin_plate,
        wire, energized_power_core, wire,
        tin_plate, tin_plate, tin_plate});
          
      if(Features.white_laser){   RecipeUtil.register(Laser.WHITE.recipe,   Laser.WHITE.cannon.getRegistryName());   }
      if(Features.red_laser){     RecipeUtil.register(Laser.RED.recipe,     Laser.RED.cannon.getRegistryName());     }
      if(Features.orange_laser){  RecipeUtil.register(Laser.ORANGE.recipe,  Laser.ORANGE.cannon.getRegistryName());  }
      if(Features.yellow_laser){  RecipeUtil.register(Laser.YELLOW.recipe,  Laser.YELLOW.cannon.getRegistryName());  }
      if(Features.green_laser){   RecipeUtil.register(Laser.GREEN.recipe,   Laser.GREEN.cannon.getRegistryName());   }
      if(Features.cyan_laser){    RecipeUtil.register(Laser.CYAN.recipe,    Laser.CYAN.cannon.getRegistryName());    }
      if(Features.blue_laser){    RecipeUtil.register(Laser.BLUE.recipe,    Laser.BLUE.cannon.getRegistryName());    }
      if(Features.magenta_laser){ RecipeUtil.register(Laser.MAGENTA.recipe, Laser.MAGENTA.cannon.getRegistryName()); }
    }

    if(Features.portal){
      RecipeUtil.register(3,3,new ItemStack(Machines.portal_control_panel,1), new Object[]{
        aluminum_plate, "enderpearl",         aluminum_plate,
        data_cable,     nullified_power_core, data_cable,
        aluminum_plate, "enderpearl",         aluminum_plate});

      RecipeUtil.register(3,3,new ItemStack(Machines.portal_frame,1), new Object[]{
        "ingotIron", "obsidian", "ingotIron",
        "obsidian", void_crystal, "obsidian",
        "ingotIron", "obsidian", "ingotIron"});
    }
    else{
      // alternate recipe for Unknown Technology
      RecipeUtil.register(new ItemStack(ModItems.unknown_technology,1), new Object[]{
        energized_power_core, advanced_power_core, nullified_power_core});
    }

    if(Features.crystal_matter_generator){
      RecipeUtil.register(3,3,new ItemStack(Machines.crystal_matter_generator,1), new Object[]{
        platinum_plate,       beam_emitter,       platinum_plate,
        energized_power_core, focus_lens,         energized_power_core,
        platinum_plate,       unknown_technology, platinum_plate});
    }

    if(Features.advanced_ore_refinery){
      RecipeUtil.registerMachine(new ItemStack(Machines.advanced_ore_refinery,1), new Object[]{
        steel_plate, unknown_technology, steel_plate,
        advanced_power_core, Blocks.FURNACE, advanced_power_core,
        steel_plate, energy_grid, steel_plate});
    }

    if(Features.fusion_container){
      RecipeUtil.registerMachine(new ItemStack(Machines.fusion_converter,1), new Object[]{
        titanium_plate, advanced_power_core, titanium_plate,
        nullified_power_core, unknown_technology, energized_power_core,
        titanium_plate, advanced_power_core, titanium_plate});

      RecipeUtil.registerMachine(new ItemStack(Machines.singularity_container,1), new Object[]{
        titanium_plate, glass_pane, titanium_plate,
        glass_pane, ModItems.vacuum_container, glass_pane,
        titanium_plate, glass_pane, titanium_plate});

      RecipeUtil.registerMachine(new ItemStack(Machines.laser_scanning_unit,1), new Object[]{
        titanium_plate, data_cable, titanium_plate,
        data_cable, energized_power_core, data_cable,
        titanium_plate, data_cable, titanium_plate});
      RecipeUtil.registerMachine(new ItemStack(Machines.fusion_laser, 1), new Object[]{
        Ingredient.EMPTY, "dyeGray",   steel_plate,
        focus_lens,       energy_grid, data_cable,
        Ingredient.EMPTY, "dyeGray",   steel_plate});
    }

    // Blocks
    if(Features.iron_frame_block){
      RecipeUtil.registerMachine(new ItemStack(Init.iron_frame_block,1), new Object[]{
        "ingotIron", "obsidian", "ingotIron",
        "obsidian",  "blockGlassColorless", "obsidian",
        "ingotIron", "obsidian", "ingotIron"});
    }
    if(Features.black_hole){
      RecipeUtil.register(3, 3, new ItemStack(Init.black_hole,1), new Object[]{ // the ItemBlock for the Black Hole should already be registered by now.
        void_crystal, void_crystal, void_crystal,
        void_crystal, ModItems.fusion_core, void_crystal,
        void_crystal, void_crystal, void_crystal});
    }

    // Trophies
    if(Features.trophies){
      RecipeUtil.register(new ItemStack(Trophy.trophy_base, 1), new Object[] {"ingotIron","slabWood"});

      if(Features.bronze_trophy){
        RecipeUtil.register(3, 3, new ItemStack(Trophy.bronze,1), new Object[]{
          bronze_plate, bronze_plate, bronze_plate,
          bronze_plate, bronze_plate, bronze_plate,
          Ingredient.EMPTY, trophy_base, Ingredient.EMPTY});
      }
      if(Features.silver_trophy){
        RecipeUtil.register(3, 3, new ItemStack(Trophy.silver,1), new Object[]{
          silver_plate, silver_plate, silver_plate,
          silver_plate, silver_plate, silver_plate,
          Ingredient.EMPTY, trophy_base, Ingredient.EMPTY});
      }
      if(Features.gold_trophy){
        RecipeUtil.register(3, 3, new ItemStack(Trophy.gold,1), new Object[]{
          gold_plate, gold_plate, gold_plate,
          gold_plate, gold_plate, gold_plate,
          Ingredient.EMPTY, trophy_base, Ingredient.EMPTY});
      }
      if(Features.platinum_trophy){
        RecipeUtil.register(3, 3, new ItemStack(Trophy.platinum,1), new Object[]{
          platinum_plate, platinum_plate, platinum_plate,
          platinum_plate, platinum_plate, platinum_plate,
          Ingredient.EMPTY, trophy_base, Ingredient.EMPTY});
      }
    }

    Debug.log_setup_info("Finished registering Crafting Recipes.");
  }
}
