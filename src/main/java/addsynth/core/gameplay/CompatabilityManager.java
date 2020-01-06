package addsynth.core.gameplay;

import java.util.Set;
import addsynth.core.game.Compatability;
import addsynth.core.gameplay.items.ScytheTool;
import net.minecraft.block.Block;
import net.minecraft.item.ToolItem;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public final class CompatabilityManager {

  /** Fires once after all mods have been loaded. */
  public static final void init(){
  }

  /** Must be executed every time the data packs are loaded or reloaded. */
  public static final void run_data_compatability(){
    set_scythe_harvest_blocks();
  }

  /** Automatically gets all blocks that are tagged with the "leaves" tag
   *  and uses reflection to set the effectiveBlocks list of all Scythe tools.
   */
  private static final void set_scythe_harvest_blocks(){
    // FUTURE: Works because it currently fires after Servers (dedicated and integrated) loads the worlds
    //         which also loads DataPacks, but should really be fired every time data packs are reloaded,
    //         because they can also be loaded with the /reload command.
    // NOTE: It would be really helpful to developers if Minecraft Forge fired a DataPacks Loaded event AFTER datapacks
    //       are loaded/reloaded, So developers can reconfigure their in-game behaviour based on what data is loaded.
    //  See: MinecraftServer -> loadDataPacks()
    //  See: minecraftforge -> resource -> ISelectiveResourceReloadListener
    final Set<Block> leaves = (Set<Block>)BlockTags.LEAVES.getAllElements();
    override_scythe_field(Core.wooden_scythe, leaves);
    override_scythe_field(Core.stone_scythe, leaves);
    override_scythe_field(Core.iron_scythe, leaves);
    override_scythe_field(Core.gold_scythe, leaves);
    override_scythe_field(Core.diamond_scythe, leaves);
    if(Compatability.OVERPOWERED.loaded){ // TEMP
      override_scythe_field(addsynth.overpoweredmod.game.core.Tools.energy_scythe, leaves);
    }
  }

  private static final void override_scythe_field(final ScytheTool tool, final Set<Block> leaves){
    try{
      ObfuscationReflectionHelper.setPrivateValue(ToolItem.class, tool, leaves, "field_150914_c"); // "effectiveBlocks"
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

}
