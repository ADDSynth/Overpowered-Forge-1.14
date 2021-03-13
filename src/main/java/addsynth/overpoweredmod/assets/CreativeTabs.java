package addsynth.overpoweredmod.assets;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.Tools;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class CreativeTabs {

  public static final ItemGroup creative_tab = new ItemGroup("overpowered")
  {
    @Override
    public final ItemStack createIcon(){
      return new ItemStack(Init.energy_crystal, 1);
    }
  };

  public static final ItemGroup machines_creative_tab =
    Config.creative_tab_machines.get() ? new ItemGroup("overpowered_machines")
    {
      @Override
      public final ItemStack createIcon(){
        return new ItemStack(OverpoweredMod.registry.getItemBlock(Machines.generator), 1);
      }
    }
  : creative_tab;

  public static final ItemGroup tools_creative_tab =
    Config.creative_tab_tools.get() ? new ItemGroup("overpowered_tools")
    {
      @Override
      public final ItemStack createIcon(){
        return Features.energy_tools.get() ? new ItemStack(Tools.energy_tools.pickaxe, 1) :
               Features.identifier.get()   ? new ItemStack(Tools.unidentified_armor[2][0], 1) :
               Features.void_tools.get()   ? new ItemStack(Tools.void_toolset.sword, 1) :
               new ItemStack(Items.STONE_SHOVEL, 1);
      }
    }
  : creative_tab;

}
