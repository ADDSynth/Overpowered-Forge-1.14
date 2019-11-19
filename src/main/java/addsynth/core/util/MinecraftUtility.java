package addsynth.core.util;

import java.util.Locale;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.ModLoadingContext;

public final class MinecraftUtility {

  /**
   * Taken from {@link net.minecraftforge.registries.GameData#checkPrefix(String)}.
   * @return String modID
   */
  public static final String getModID(){
    final String id = ModLoadingContext.get().getActiveNamespace();
    if(id.equals("minecraft")){
      ADDSynthCore.log.warn("function addsynth.core.util.MinecraftUtil.getModID() returned 'minecraft'. This might not be what you expected. Did you call getModID() at a wrong time?");
    }
    return id;
  }

  public static final boolean isVanilla(final ItemStack stack){
    return isVanilla(stack.getItem());
  }

  public static final boolean isVanilla(final BlockItem itemblock){
    return isVanilla(itemblock.getBlock());
  }

  public static final boolean isVanilla(final Item item){
    if(item instanceof BlockItem){
      return isVanilla(((BlockItem)item).getBlock());
    }
    final ResourceLocation registry_name = item.getRegistryName();
    if(registry_name != null){
      return registry_name.getNamespace().equals("minecraft");
    }
    ADDSynthCore.log.error(new NullPointerException("The item '"+item+"' doesn't have its registry name set!"));
    return false;
  }

  public static final boolean isVanilla(final Block block){
    final ResourceLocation registry_name = block.getRegistryName();
    if(registry_name != null){
      return registry_name.getNamespace().equals("minecraft");
    }
    ADDSynthCore.log.error(new NullPointerException("The block '"+block+"' doesn't have its registry name set!"));
    return false;
  }

  public static final Block.Properties setUnbreakable(final Block.Properties properties){
    return properties.hardnessAndResistance(-1.0F, 3600000.0F).noDrops(); // Bedrock, Barrier
  }

  /** <p>This is a helper method to get a specific type of TileEntity.
   *  <p>Returns a TileEntity cast to the type you specified if the TileEntity we
   *     found is an instance of the class you specified. Returns null otherwise.
   */
  public static final @Nullable <T extends TileEntity> T getTileEntity(final BlockPos position, final World world, final Class<T> specific_tile_entity_class){
    final TileEntity tile = world.getTileEntity(position);
    if(tile != null){
      if(specific_tile_entity_class.isInstance(tile)){
        return specific_tile_entity_class.cast(tile);
      }
    }
    return null;
  }

  public static final void crash(final String context, final Throwable exception){
    final CrashReport crash_report = new CrashReport(context, exception);
    Minecraft.getMinecraft().crashed(crash_report);
    Minecraft.getMinecraft().displayCrashReport(crash_report);
    // FMLCommonHandler.exitJava();
  }

  /**
   * <p>
   * This is for biomes. The {@link net.minecraftforge.common.DimensionManager} already has a method
   * for getting the next available Dimension id.
   * </p>
   * <p>
   * SO FAR, THIS IS NOT USED!
   * </p>
   */
  public static final int getNextBiomeID(){
    int i = 0;
    while(Biome.getBiomeForId(i) != null){
      i++;
      if(i == Integer.MIN_VALUE){
        /*
        i = -1;
        while(Biome.getBiomeForId(i) != null){
          i--;
          if(i == Integer.MAX_VALUE){
          }
        }
        */
        throw new RuntimeException("Could not get a free Biome ID, all values are used.");
      }
    }
    return i;
  }

}
