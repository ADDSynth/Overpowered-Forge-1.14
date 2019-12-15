package addsynth.overpoweredmod.blocks.tiles.fusion;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionEnergyConverter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;

public final class FusionEnergyConverter extends MachineBlockTileEntity {

  public FusionEnergyConverter(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(OverpoweredMod.machines_creative_tab));
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Fusion Energy"));
  }

  @Override
  public TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TileFusionEnergyConverter();
  }

}
