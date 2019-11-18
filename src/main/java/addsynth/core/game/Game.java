package addsynth.core.game;

import addsynth.core.ADDSynthCore;
import addsynth.core.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class Game {

/*

  public static final ArrayList<Achievement> achievement_list = new ArrayList<>(30);
  
  public static final Achievement NewAchievement(String unlocalized_name, int x, int y, Item item){
    return NewAchievement(unlocalized_name, x, y, new ItemStack(item), null, false);
  }

  public static final Achievement NewAchievement(String unlocalized_name, int x, int y, Block block){
    return NewAchievement(unlocalized_name, x, y, new ItemStack(block), null, false);
  }

  public static final Achievement NewAchievement(String unlocalized_name, int x, int y, Item item, Achievement parent) {
    return NewAchievement(unlocalized_name, x, y, new ItemStack(item), parent, false);
  }

  public static final Achievement NewAchievement(String unlocalized_name, int x, int y, Block block, Achievement parent) {
    return NewAchievement(unlocalized_name, x, y, new ItemStack(block), parent, false);
  }

  public static final Achievement NewAchievement(String unlocalized_name, int x, int y, Item item, Achievement parent, boolean important) {
    return NewAchievement(unlocalized_name, x, y, new ItemStack(item), parent, important);
  }

  public static final Achievement NewAchievement(String unlocalized_name, int x, int y, Block block, Achievement parent, boolean important) {
    return NewAchievement(unlocalized_name, x, y, new ItemStack(block), parent, important);
  }

  public static final Achievement NewAchievement(String unlocalized_name, int x, int y, ItemStack stack, Achievement parent, boolean important) {
    if(y < -7){ throw new IllegalArgumentException("y value cannot be -8 or lower otherwise Forge will crash.");}
    if(x < -7){ throw new IllegalArgumentException("x value cannot be lower than -7 or else graphical glitches will occur.");}
    for(Achievement index : achievement_list){
      if(index.displayColumn == x && index.displayRow == y){
        throw new RuntimeException("Cannot create an Achievement with x "+x+" and y "+y+" because an Achievement already exists at that location.");
      }
    }
    Achievement achievement = new Achievement("achievement."+unlocalized_name, unlocalized_name, x, y, stack, parent);
    if(important){
      achievement.setSpecial();
    }
    achievement_list.add(achievement);
    return achievement;
  }

  /**
   * Call this to register your Achievement page. This will go through the final list to ensure that all achievements
   * also have their parent achievement registered. If it finds an unregistered achievement, it won't crash Minecraft,
   * but it does mean you forgot to register an achievement somewhere.
   * @param page
  public static final void register_achievement_page(final AchievementPage page){
    List<Achievement> achievements = page.getAchievements();
    Achievement parent;
    String parent_string;
    for(Achievement index : achievements){
      parent = index.parentAchievement;
      if(parent != null){
        if(achievements.contains(parent) == false){
          parent_string = parent.toString();
          ADDSynthCore.log.error("The '"+parent_string+"' achievement is not registered! "+parent_string+" is the parent achievement for the "+index.toString()+" achievement.");
        }
      }
    }
    AchievementPage.registerAchievementPage(page);
  }

  /**
   * Calling this function will also grant the player all of the achievements required to get this one.
   * @param player
   * @param achievement
  public static final void activate_achievement(final EntityPlayer player, final Achievement achievement){
    if(Config.recursive_loop_achievements){
      if(achievement.parentAchievement != null){
        activate_achievement(player, achievement.parentAchievement);
      }
    }
    player.addStat(achievement);
  }

*/

  public static final ItemGroup NewCreativeTab(final String name, final Icon[] icons){
    final ItemStack icon = Icon.get(icons);
    return new ItemGroup(name){
      @Override
      public final ItemStack createIcon(){
        return icon;
      }
    };
  }

  public static final SoundEvent newSound(final String mod_id, final String name){
    SoundEvent sound = new SoundEvent(new ResourceLocation(mod_id,name));
    sound.setRegistryName(name);
    return sound;
  }

}
