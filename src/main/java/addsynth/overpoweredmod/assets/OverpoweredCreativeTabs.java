package addsynth.overpoweredmod.assets;

import addsynth.core.game.Compatability;
import addsynth.core.game.Game;
import addsynth.core.game.Icon;
import addsynth.core.util.JavaUtils;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.*;
import addsynth.overpoweredmod.init.Setup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;

// Let this be a lesson in the future: variables initialized in the Init class can be initialized
// in any order as necessary, but the order in which they appear in the Creative Tab and JEI depends
// on the order you register items!
// also, always assume your mod will be run with other mods so just have a single creative tab.
public final class OverpoweredCreativeTabs {

  private static final String main_tab_name = "overpowered";

  private static final Object[] gem_items = {
    // shards
    Gems.RUBY.shard,
    Gems.TOPAZ.shard,
    Gems.CITRINE.shard,
    Gems.EMERALD.shard,
    Gems.DIAMOND.shard,
    Gems.SAPPHIRE.shard,
    Gems.AMETHYST.shard,
    Gems.QUARTZ.shard,
    // gems
    Gems.RUBY.gem,
    Gems.TOPAZ.gem,
    Gems.CITRINE.gem,
    Gems.SAPPHIRE.gem,
    Gems.AMETHYST.gem,
    // gem blocks
    Gems.RUBY.block,
    Gems.TOPAZ.block,
    Gems.CITRINE.block,
    Gems.SAPPHIRE.block,
    Gems.AMETHYST.block,
    // gem ore
    Gems.RUBY.ore,
    Gems.TOPAZ.ore,
    Gems.CITRINE.ore,
    Gems.SAPPHIRE.ore,
    Gems.AMETHYST.ore,
  };

  private static final Object[] main_items = {
    // energy crystal & void crystal
    Init.energy_crystal_shards,
    Init.energy_crystal,
    Init.light_block,
    Init.void_crystal,
    Init.null_block,
    // power cores
    ModItems.power_core,
    ModItems.advanced_power_core,
    ModItems.energized_power_core,
    ModItems.nullified_power_core,
    // items
    ModItems.energy_grid,
    ModItems.vacuum_container,
    ModItems.beam_emitter,
    ModItems.unknown_technology,
    ModItems.fusion_core,
    ModItems.dimensional_anchor,
    // lenses
    Lens.focus_lens,
    Lens.red,
    Lens.orange,
    Lens.yellow,
    Lens.green,
    Lens.cyan,
    Lens.blue,
    Lens.magenta,
    // blocks
    Init.iron_frame_block,
    Init.black_hole,
    // trophies
    Trophy.trophy_base,
    Trophy.bronze,
    Trophy.silver,
    Trophy.gold,
    Trophy.platinum,
  };

  private static final Object[] machine_items = {
    Wires.wire,
    Wires.data_cable,
    Machines.generator,
    Machines.energy_storage,
    Machines.universal_energy_machine,
    Machines.compressor,
    Machines.electric_furnace,
    Machines.gem_converter,
    Machines.inverter,
    Machines.magic_infuser,
    Machines.identifier,
    Machines.portal_control_panel,
    Machines.portal_frame,
    Machines.crystal_matter_generator,
    Machines.advanced_ore_refinery,
    Machines.fusion_converter,
    Machines.laser_scanning_unit,
    Machines.singularity_container,
    Machines.fusion_laser,
    Machines.laser_housing,
    Laser.WHITE.cannon,
    Laser.RED.cannon,
    Laser.ORANGE.cannon,
    Laser.YELLOW.cannon,
    Laser.GREEN.cannon,
    Laser.CYAN.cannon,
    Laser.BLUE.cannon,
    Laser.MAGENTA.cannon,
  };

  private static final Object[] tool_items = {
    // light tools
    Tools.energy_tools.sword,
    Tools.energy_tools.pickaxe,
    Tools.energy_tools.axe,
    Tools.energy_tools.shovel,
    Tools.energy_tools.hoe,
    Tools.energy_scythe,
    // void tools
    Tools.void_toolset.sword,
    Tools.void_toolset.pickaxe,
    Tools.void_toolset.axe,
    Tools.void_toolset.shovel,
    Tools.void_toolset.hoe,
    // unidentified armor
    Tools.unidentified_armor[0][0],
    Tools.unidentified_armor[0][1],
    Tools.unidentified_armor[0][2],
    Tools.unidentified_armor[0][3],
    Tools.unidentified_armor[1][0],
    Tools.unidentified_armor[1][1],
    Tools.unidentified_armor[1][2],
    Tools.unidentified_armor[1][3],
    Tools.unidentified_armor[2][0],
    Tools.unidentified_armor[2][1],
    Tools.unidentified_armor[2][2],
    Tools.unidentified_armor[2][3],
    Tools.unidentified_armor[3][0],
    Tools.unidentified_armor[3][1],
    Tools.unidentified_armor[3][2],
    Tools.unidentified_armor[3][3],
    Tools.unidentified_armor[4][0],
    Tools.unidentified_armor[4][1],
    Tools.unidentified_armor[4][2],
    Tools.unidentified_armor[4][3],
  };

  private static final Object[] metal_items = {
    // metal ingots
    Metals.TIN.ingot,
    Metals.COPPER.ingot,
    Metals.ALUMINUM.ingot,
    Metals.STEEL.ingot,
    Metals.BRONZE.ingot,
    Metals.SILVER.ingot,
    Metals.PLATINUM.ingot,
    Metals.TITANIUM.ingot,
    // metal blocks
    Metals.TIN.block,
    Metals.COPPER.block,
    Metals.ALUMINUM.block,
    Metals.STEEL.block,
    Metals.BRONZE.block,
    Metals.SILVER.block,
    Metals.PLATINUM.block,
    Metals.TITANIUM.block,
    // metal ores
    Metals.TIN.ore,
    Metals.COPPER.ore,
    Metals.ALUMINUM.ore,
    Metals.SILVER.ore,
    Metals.PLATINUM.ore,
    Metals.TITANIUM.ore,
    // metal plates
    Metals.IRON.plating,
    Metals.GOLD.plating,
    Metals.TIN.plating,
    Metals.COPPER.plating,
    Metals.ALUMINUM.plating,
    Metals.STEEL.plating,
    Metals.BRONZE.plating,
    Metals.SILVER.plating,
    Metals.PLATINUM.plating,
    Metals.TITANIUM.plating,
  };

  private static final ItemGroup[] tab = new ItemGroup[5];
  private static final int GEMS     = 0;
  private static final int MAIN     = 1;
  private static final int MACHINES = 2;
  private static final int TOOLS    = 3;
  private static final int METALS   = 4;

  private static final Icon[][] icons = {
    {
      new Icon(Gems.ruby, true)
    },
    {
      new Icon(Init.energy_crystal, true)
    },
    {
      new Icon(OverpoweredMod.registry.getItemBlock(Machines.generator), true)
    },
    {
      new Icon(Tools.energy_tools.pickaxe,     Features.energy_tools),
      new Icon(Tools.unidentified_armor[2][0], Features.identifier),
      new Icon(Tools.void_toolset.sword,       Features.void_tools),
      new Icon(Items.STONE_SHOVEL)
    },
    {
      new Icon(Metals.TIN.ingot, true)
    }
  };

  /** This is the main function that registers the Overpowered Creative Tabs.
   *  Called from {@link addsynth.overpoweredmod.init.proxy.ClientProxy}
   */
  public static final void register(){
    Debug.log_setup_info("Begin Registering Creative Tabs...");
  
    add_items(gem_items,     GEMS,     Features.creative_tab_gems,     "overpowered_gems",     icons[0]);
    set_creative_tab_of_items(main_items, MAIN, main_tab_name, icons[1]);
    add_items(machine_items, MACHINES, Features.creative_tab_machines, "overpowered_machines", icons[2]);
    add_items(tool_items,    TOOLS,    Features.creative_tab_tools,    "overpowered_tools",    icons[3]);
    add_items(metal_items,   METALS,   Features.creative_tab_metals,   "overpowered_metals",   icons[4]);
    
    Setup.creative_tabs_registered = true;
    Debug.log_setup_info("Finished Registering Creative Tabs.");
  }

  private static final void add_items(final Object[] item_list, final int tab, final boolean enabled, final String name, final Icon[] icons_in){
    if(enabled){
      set_creative_tab_of_items(item_list, tab, name, icons_in);
    }
    else{
      set_creative_tab_of_items(item_list, MAIN, main_tab_name, icons[MAIN]);
    }
  }

  private static final void set_creative_tab_of_items(final Object[] item_list, final int tab_index, final String name, final Icon[] icons){
    if(tab[tab_index] == null){
      tab[tab_index] = Game.NewCreativeTab(name, icons);
    }
    final ItemGroup creative_tab = tab[tab_index];
    for(Object obj : item_list){
      if(obj == null){
        OverpoweredMod.log.warn(new NullPointerException("Found a null reference in Creative Tab list: "+tab_index+" - "+name+"."));
        continue;
      }
      if(obj instanceof Item){
        ((Item)obj).setCreativeTab(creative_tab);
        continue;
      }
      if(obj instanceof Block){
        ((Block)obj).setCreativeTab(creative_tab);
        continue;
      }
      OverpoweredMod.log.error(new IllegalArgumentException("The Creative Tab lists should ONLY contain Items and Blocks! What is this? : "+obj.toString()));
      Thread.dumpStack();
    }
  }

}
