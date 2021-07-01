package addsynth.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.Set;
import addsynth.core.util.constants.DevStage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public final class CommonUtil {

  /** Using {@link ForgeRegistries#BLOCKS} directly in an advanced for statement
   *  will automatically iterate over all Blocks. */
  public static final Collection<Block> getAllBlocks(){
    return ForgeRegistries.BLOCKS.getValues();
  }
  
  public static final Set<ResourceLocation> getAllBlockIDs(){
    return ForgeRegistries.BLOCKS.getKeys();
  }
  
  /** Using {@link ForgeRegistries#ITEMS} directly in an advanced for statement
   *  will automatically iterate over all Items. */
  public static final Collection<Item> getAllItems(){
    return ForgeRegistries.ITEMS.getValues();
  }
  
  public static final Set<ResourceLocation> getAllItemIDs(){
    return ForgeRegistries.ITEMS.getKeys();
  }

  public static final String get_mod_info(String mod_name, String author, String version, DevStage dev_stage, String date){
    if(dev_stage.isDevelopment){
      return StringUtil.build(mod_name, " by ", author, ", version ", version, dev_stage.label, ", built on ", LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)), ".");
    }
    return StringUtil.build(mod_name, " by ", author, ", version ", version, ", built on ", date, ".");
  }

}
