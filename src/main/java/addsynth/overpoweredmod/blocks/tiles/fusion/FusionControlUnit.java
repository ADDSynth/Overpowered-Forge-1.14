package addsynth.overpoweredmod.blocks.tiles.fusion;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class FusionControlUnit extends MachineBlock {

  public FusionControlUnit(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Fusion Energy");
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta){
    return null;
  }

}
