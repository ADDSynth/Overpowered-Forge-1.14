package addsynth.overpoweredmod.blocks.tiles.fusion;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.tiles.machines.fusion.TileFusionEnergyConverter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class FusionEnergyConverter extends MachineBlockTileEntity {

  public FusionEnergyConverter(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Fusion Energy");
  }

  @Override
  public TileEntity createNewTileEntity(final World worldIn, final int meta){
    return new TileFusionEnergyConverter();
  }

}
