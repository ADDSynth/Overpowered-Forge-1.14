package addsynth.overpoweredmod.blocks;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.config.MachineValues;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public final class LightBlock extends Block {

  public LightBlock(final String name){
    super(Block.Properties.create(Material.ROCK, MaterialColor.QUARTZ).lightValue(15).hardnessAndResistance(5.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2));
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
  }

  @Override
  public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent(MachineValues.light_block_energy.get().toString() + " Energy"));
  }

}
