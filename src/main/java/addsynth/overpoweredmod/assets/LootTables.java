package addsynth.overpoweredmod.assets;

import addsynth.core.game.Compatability;
import addsynth.core.items.ArmorMaterial;
import addsynth.core.util.JavaUtils;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OverpoweredMod.MOD_ID)
public final class LootTables {

// https://mcforge.readthedocs.io/en/latest/items/loot_tables/
// https://minecraft.gamepedia.com/Loot_table
// https://minecraft.gamepedia.com/Status_effect#Luck
// https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/common/core/loot/LootHandler.java

  static {
    Debug.log_setup_info("LootTables class was loaded.");
  }
/*
  private static final float default_spawn_chance = 1.0f / 40.0f; // before, it was just with rings and it was 1 / 15 chance.
  private static final float spawn_chance = default_spawn_chance;

  private static final int master_armor_weight = 4;
  private static final int unidentified_armor_weight = 5;
  private static final int leather_weight   = master_armor_weight * 10;
  private static final int gold_weight      = master_armor_weight * 6;
  private static final int chainmail_weight = master_armor_weight * 6;
  private static final int iron_weight      = master_armor_weight * 3;
  private static final int diamond_weight   = master_armor_weight * 1;
  
  private static final int ring_weight = 1;
  private static final int common_ring_weight = 10;
  private static final int good_ring_weight = 6;
  private static final int rare_ring_weight = 3;
  private static final int unique_ring_weight = 1;

  private static final LootEntry[] unidentified_armor = {
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][0],leather_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][1],leather_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][2],leather_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][3],leather_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.GOLD.ordinal()][0],gold_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.GOLD.ordinal()][1],gold_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.GOLD.ordinal()][2],gold_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.GOLD.ordinal()][3],gold_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][0],chainmail_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][1],chainmail_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][2],chainmail_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][3],chainmail_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.IRON.ordinal()][0],iron_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.IRON.ordinal()][1],iron_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.IRON.ordinal()][2],iron_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.IRON.ordinal()][3],iron_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][0],diamond_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][1],diamond_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][2],diamond_weight),
    newLootEntry(Tools.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][3],diamond_weight),
  };
  
  private static final LootPool custom_loot_pool = new LootPool(
    unidentified_armor,
    new LootCondition[] {
      new KilledByPlayer(false),
      new RandomChance(spawn_chance)
    },
    new RandomValueRange(1.0f),
    new RandomValueRange(0.0f),
    "overpowered_custom_loot_table"
  );

  @SuppressWarnings("null")
  private static final LootEntryItem newLootEntry(final Item item, final int weight){
    return new LootEntryItem(item, weight, 0, new LootFunction[0], new LootCondition[0],item.getRegistryName().toString());
  }
*/

  @SubscribeEvent
  public static void inject_loot(LootTableLoadEvent event){
    if(Features.identifier.get()){
      final String prefix = "minecraft:entities/";
      final String name = event.getName().toString();
      if(Debug.debug_loot_tables){
        OverpoweredMod.log.info("Loading Loot Table: "+name);
      }
      if(name.startsWith(prefix)){
        final String mob = name.substring(prefix.length());
        boolean add_rings = false;
        if(mob.equals("zombie")            && Config.drop_for_zombie.get()){            add_rings = true; }
        if(mob.equals("zombie_villager")   && Config.drop_for_zombie_villager.get()){   add_rings = true; }
        if(mob.equals("husk")              && Config.drop_for_husk.get()){              add_rings = true; }
        if(mob.equals("spider")            && Config.drop_for_spider.get()){            add_rings = true; }
        if(mob.equals("cave_spider")       && Config.drop_for_cave_spider.get()){       add_rings = true; }
        if(mob.equals("creeper")           && Config.drop_for_creeper.get()){           add_rings = true; }
        if(mob.equals("skeleton")          && Config.drop_for_skeleton.get()){          add_rings = true; }
        if(mob.equals("zombie_pigman")     && Config.drop_for_zombie_pigman.get()){     add_rings = true; }
        if(mob.equals("blaze")             && Config.drop_for_blaze.get()){             add_rings = true; }
        if(mob.equals("witch")             && Config.drop_for_witch.get()){             add_rings = true; }
        if(mob.equals("ghast")             && Config.drop_for_ghast.get()){             add_rings = true; }
        if(mob.equals("enderman")          && Config.drop_for_enderman.get()){          add_rings = true; }
        if(mob.equals("stray")             && Config.drop_for_stray.get()){             add_rings = true; }
        if(mob.equals("guardian")          && Config.drop_for_guardian.get()){          add_rings = true; }
        if(mob.equals("elder_guardian")    && Config.drop_for_elder_guardian.get()){    add_rings = true; }
        if(mob.equals("wither_skeleton")   && Config.drop_for_wither_skeleton.get()){   add_rings = true; }
        if(mob.equals("magma_cube")        && Config.drop_for_magma_cube.get()){        add_rings = true; }
        if(mob.equals("shulker")           && Config.drop_for_shulker.get()){           add_rings = true; }
        if(mob.equals("vex")               && Config.drop_for_vex.get()){               add_rings = true; }
        if(mob.equals("evocation_illager") && Config.drop_for_evocation_illager.get()){ add_rings = true; }
        if(mob.equals("vindication_illager") && Config.drop_for_vindication_illager.get()){ add_rings = true; }
        // FUTURE: New Illusioner mob in 1.12, but they don't have a Loot Table or Loot Table Load Event until 1.14, and they don't naturally spawn anywhere.

        if(add_rings){
          // event.getTable().addPool(custom_loot_pool);
          if(Debug.debug_loot_tables){
            OverpoweredMod.log.info("Successfully injected custom loot pool into Loot Table for: "+mob);
          }
        }
      }
    }
  }

}
