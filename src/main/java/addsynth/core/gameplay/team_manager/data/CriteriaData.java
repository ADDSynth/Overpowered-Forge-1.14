package addsynth.core.gameplay.team_manager.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.StringUtil;
import addsynth.core.util.java.ArrayUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;

public final class CriteriaData {

  private static String[] statistics;
  private static String[] blocks;
  private static String[] items_with_durability;
  private static String[] items;
  private static String[] entities;

  public static final void calculate(){
    try{
      calculateBlocks();
      calculateItems();
      calculateStatistics();
      calculateEntities();
    }
    catch(Exception e){
      ADDSynthCore.log.error("Encountered an error while calculating Criteria Data for the Team Manager.", e);
    }
  }
  
  private static final void calculateBlocks(){
    final Set<ResourceLocation> blocks = CommonUtil.getAllBlockIDs();
    final int size = blocks.size();
    final ArrayList<String> block_list = new ArrayList<String>(size);
    for(ResourceLocation id : blocks){
      block_list.add(id.toString());
    }
    Collections.sort(block_list);
    CriteriaData.blocks = block_list.toArray(new String[size]);
  }
  
  @SuppressWarnings("null")
  private static final void calculateItems(){
    final Collection<Item> items = CommonUtil.getAllItems();
    final int size = items.size();
    final ArrayList<String> item_list = new ArrayList<String>(size);
    final ArrayList<String> item_with_durability_list = new ArrayList<String>();
    for(Item item : items){
      item_list.add(item.getRegistryName().toString());
      if(item.isDamageable()){
        item_with_durability_list.add(item.getRegistryName().toString());
      }
    }
    Collections.sort(item_list);
    Collections.sort(item_with_durability_list);
    CriteriaData.items = item_list.toArray(new String[size]);
    CriteriaData.items_with_durability = item_with_durability_list.toArray(new String[item_with_durability_list.size()]);
  }
  
  private static final void calculateStatistics(){
    // since I can only get the Display Name from the id, not the other way around,
    // I have to sort the ids alphebetically, then construc the display names.
    final Set<ResourceLocation> stats = Registry.CUSTOM_STAT.keySet();
    final int size = stats.size();
    final ArrayList<String> stat_list = new ArrayList<String>(size);
    for(ResourceLocation stat_id : stats){
      stat_list.add(stat_id.toString());
    }
    Collections.sort(stat_list);
    CriteriaData.statistics = stat_list.toArray(new String[size]);
  }
  
  private static final void calculateEntities(){
    final Set<ResourceLocation> entities = CommonUtil.getAllEntityIDs();
    final int size = entities.size();
    final ArrayList<String> entity_list = new ArrayList<String>(size);
    for(ResourceLocation entity : entities){
      entity_list.add(entity.toString());
    }
    Collections.sort(entity_list);
    CriteriaData.entities = entity_list.toArray(new String[size]);
  }
  
  
  
  
  public static final String[] getStandardCriteria(){
    return new String[] {
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.dummy"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.trigger"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.death_count"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.player_kills"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.total_kills"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.health"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.xp"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.xp_level"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.air"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.food"),
      StringUtil.translate("gui.addsynthcore.team_manager.criteria.armor")
    };
  }
  
  // Statistics have a client-translated Display Name, so they must be calculated each time,
  // in case the player changes the language in-game.
  public static final String[] getStatistics(){
    if(statistics == null){
      calculateStatistics();
    }
    final String[] statistic_names = new String[statistics.length];
    Optional<ResourceLocation> statistic;
    int i;
    for(i = 0; i < statistics.length; i++){
      statistic = Registry.CUSTOM_STAT.getValue(new ResourceLocation(CriteriaData.statistics[i]));
      if(statistic.isPresent()){
        statistic_names[i] = StringUtil.translate("stat."+(statistic.get().toString().replace(':', '.')));
      }
      else{
        statistic_names[i] = "stat."+(CriteriaData.statistics[i].replace(':', '.'));
      }
    }
    return statistic_names;
  }
  
  @SuppressWarnings("deprecation")
  public static final String getStatisticID(int id){
    // if(statistics == null){
    //   calculateStatistics();
    // }
    return ArrayUtil.getArrayValue(statistics, id);
  }
  
  public static final int getStatisticIndex(String statistic_id){
    int i;
    for(i = 0; i < statistics.length; i++){
      if(statistics[i].equals(statistic_id)){
        break;
      }
    }
    return i == statistics.length ? -1 : i;
  }
  
  public static final String[] getAllBlocks(){
    return blocks != null ? blocks : new String[0];
  }
  
  public static final String[] getAllItems(){
    return items != null ? items : new String[0];
  }
  
  public static final String[] getItemsWithDurability(){
    return items_with_durability != null ? items_with_durability : new String[0];
  }

  @SuppressWarnings("null")
  public static final String[] getColors(){
    // the Minecraft colors are translated, however the teams use the TextFormatting colors.
    return new String[] {
      TextFormatting.fromColorIndex(0).getFriendlyName(),
      TextFormatting.fromColorIndex(1).getFriendlyName(),
      TextFormatting.fromColorIndex(2).getFriendlyName(),
      TextFormatting.fromColorIndex(3).getFriendlyName(),
      TextFormatting.fromColorIndex(4).getFriendlyName(),
      TextFormatting.fromColorIndex(5).getFriendlyName(),
      TextFormatting.fromColorIndex(6).getFriendlyName(),
      TextFormatting.fromColorIndex(7).getFriendlyName(),
      TextFormatting.fromColorIndex(8).getFriendlyName(),
      TextFormatting.fromColorIndex(9).getFriendlyName(),
      TextFormatting.fromColorIndex(10).getFriendlyName(),
      TextFormatting.fromColorIndex(11).getFriendlyName(),
      TextFormatting.fromColorIndex(12).getFriendlyName(),
      TextFormatting.fromColorIndex(13).getFriendlyName(),
      TextFormatting.fromColorIndex(14).getFriendlyName(),
      TextFormatting.fromColorIndex(15).getFriendlyName()
    };
  }

  public static final String[] getEntities(){
    return entities != null ? entities : new String[0];
  }

}
