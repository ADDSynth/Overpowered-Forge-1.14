package addsynth.core.util.game;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
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

public final class MinecraftUtility {

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
    ADDSynthCore.log.error(new NullPointerException("The item '"+StringUtil.getName(item)+"' doesn't have its registry name set!"));
    return false;
  }

  public static final boolean isVanilla(final Block block){
    final ResourceLocation registry_name = block.getRegistryName();
    if(registry_name != null){
      return registry_name.getNamespace().equals("minecraft");
    }
    ADDSynthCore.log.error(new NullPointerException("The block '"+StringUtil.getName(block)+"' doesn't have its registry name set!"));
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

  /** DO NOT INTENTIONALLY CRASH THE GAME!
   *  Whenever possible, always use a <code>try/catch</code> block to check for Exceptions and handle them!
   *  Only use this in a development environment for debug purposes. */
  public static final void crash(final String context, @Nonnull final Throwable exception){
    final CrashReport crash_report = new CrashReport(context, exception);
    Minecraft.getInstance().crashed(crash_report);
    Minecraft.getInstance().displayCrashReport(crash_report);
    // FMLCommonHandler.exitJava();
  }

}
