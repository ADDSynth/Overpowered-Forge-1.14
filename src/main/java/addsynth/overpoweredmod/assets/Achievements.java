package addsynth.overpoweredmod.assets;

/*

TODO: Add MC 1.12 Advancements.
import java.util.List;
import addsynth.core.game.Game;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.*;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public final class Achievements { // FUTURE: Advancements replaced Achievements in MC 1.12.
  
  public static final Achievement FIND_RUBY          = Game.NewAchievement("find_ruby",              -3,-3,Gems.ruby);
  public static final Achievement FIND_TOPAZ         = Game.NewAchievement("find_topaz",             -2,-3,Gems.topaz);
  public static final Achievement FIND_CITRINE       = Game.NewAchievement("find_citrine",           -1,-3,Gems.citrine);
  public static final Achievement FIND_EMERALD       = Game.NewAchievement("find_emerald",            0,-3,Gems.emerald);
  public static final Achievement FIND_SAPPHIRE      = Game.NewAchievement("find_sapphire",           1,-3,Gems.sapphire);
  public static final Achievement FIND_AMETHYST      = Game.NewAchievement("find_amethyst",           2,-3,Gems.amethyst);
  public static final Achievement FIND_QUARTZ        = Game.NewAchievement("find_nether_quartz",      3,-3,Gems.quartz);
    
  public static final Achievement ENERGY_CRYSTAL     = Game.NewAchievement("make_energy_crystal",     0,-1,Init.energy_crystal, FIND_EMERALD);
  public static final Achievement LIGHT_BLOCK        = Game.NewAchievement("make_light_block",       -2,-1,Init.light_block,    ENERGY_CRYSTAL);
  public static final Achievement ENERGY_TOOLS       = Game.NewAchievement("energy_tools",            2,-1,Tools.energy_tools.sword,  ENERGY_CRYSTAL);
  
  public static final Achievement VOID_CRYSTAL       = Game.NewAchievement("make_void_crystal",       0, 1,Init.void_crystal,   ENERGY_CRYSTAL);
  public static final Achievement NULL_BLOCK         = Game.NewAchievement("make_null_block",        -2, 1,Init.null_block,     VOID_CRYSTAL);
  public static final Achievement DEATH_BLADE        = Game.NewAchievement("annihilation_blade",      2, 1,Tools.void_toolset.sword,    VOID_CRYSTAL);
  
  public static final Achievement LASER_FIRE         = Game.NewAchievement("fire_laser",             -2, 3,Laser.red_laser,     VOID_CRYSTAL);
  public static final Achievement IDENTIFY_RING      = Game.NewAchievement("identify_a_ring",         2, 3,Tools.ring[2],       VOID_CRYSTAL);
  public static final Achievement PORTAL             = Game.NewAchievement("create_portal",           0, 5,Portal.portal_image, VOID_CRYSTAL);

  public static final Achievement UNKNOWN_TECHNOLOGY = Game.NewAchievement("find_unknown_technology", 0, 7,ModItems.unknown_technology, Features.portal ? PORTAL : VOID_CRYSTAL, true);
  public static final Achievement FUSION_ENERGY      = Game.NewAchievement("fusion_energy",          -2, 7,Machines.fusion_converter,   UNKNOWN_TECHNOLOGY,true);

  public static final Achievement BLACK_HOLE         = Game.NewAchievement("make_black_hole",         0, 9,Init.black_hole,             UNKNOWN_TECHNOLOGY,true);
  public static final Achievement DIMENSIONAL_ANCHOR = Game.NewAchievement("make_dimensional_anchor", 2, 7,ModItems.dimensional_anchor, UNKNOWN_TECHNOLOGY,true);
  // public static final Achievement SURVIVE_BLACK_HOLE = Game.NewAchievement("survive_black_hole",      2, 9,Init.black_hole,             DIMENSIONAL_ANCHOR,true);
  
  // trophies
  public static final Achievement BRONZE_TROPHY      = Game.NewAchievement("get_bronze_trophy",      -5, 5,Trophy.bronze,   null, false);
  public static final Achievement SILVER_TROPHY      = Game.NewAchievement("get_silver_trophy",      -4, 5,Trophy.silver,   null, false);
  public static final Achievement GOLD_TROPHY        = Game.NewAchievement("get_gold_trophy",        -3, 5,Trophy.gold,     null, false);
  public static final Achievement PLATINUM_TROPHY    = Game.NewAchievement("get_platinum_trophy",    -2, 5,Trophy.platinum, null, true);

  public static final void registerAchievements(){
    Debug.log_setup_info("Begin Registering Achievements...");

    if(Features.achievements){
      AchievementPage page = new AchievementPage("Overpowered");
      List<Achievement> achievements = page.getAchievements();

      achievements.add(FIND_RUBY);
      achievements.add(FIND_TOPAZ);
      achievements.add(FIND_CITRINE);
      achievements.add(FIND_EMERALD);
      achievements.add(FIND_SAPPHIRE);
      achievements.add(FIND_AMETHYST);
      achievements.add(FIND_QUARTZ);
      achievements.add(ENERGY_CRYSTAL);
      if(Features.light_block){ achievements.add(LIGHT_BLOCK); }
      if(Features.energy_tools){ achievements.add(ENERGY_TOOLS); }
      achievements.add(VOID_CRYSTAL);
      if(Features.null_block){ achievements.add(NULL_BLOCK); }
      if(Features.void_tools){ achievements.add(DEATH_BLADE); }
      if(Features.lasers){ achievements.add(LASER_FIRE); }
      if(Features.identifier){ achievements.add(IDENTIFY_RING); }
      if(Features.portal){ achievements.add(PORTAL); }
      achievements.add(UNKNOWN_TECHNOLOGY);
      if(Features.fusion_container){ achievements.add(FUSION_CONTAINER); }
      if(Features.black_hole){ achievements.add(BLACK_HOLE); }
      if(Features.dimensional_anchor){ achievements.add(DIMENSIONAL_ANCHOR); }
      if(Features.trophies){
        if(Features.bronze_trophy){ achievements.add(BRONZE_TROPHY); }
        if(Features.silver_trophy){ achievements.add(SILVER_TROPHY); }
        if(Features.gold_trophy){ achievements.add(GOLD_TROPHY); }
        if(Features.platinum_trophy){ achievements.add(PLATINUM_TROPHY); }
      }
      
      Game.register_achievement_page(page);
      Debug.log_setup_info("Finished Registering Achievements.");
    }
    else{
      Debug.log_setup_info("Achievements are disabled.");
    }
    
  }

}

*/
