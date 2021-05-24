package addsynth.overpoweredmod.machines.fusion.converter;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class FusionEnergyConverterBlock extends MachineBlock {

  public FusionEnergyConverterBlock(final String name){
    super(MaterialColor.SNOW);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab));
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Fusion Energy"));
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, final IBlockReader world){
    return new TileFusionEnergyConverter();
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack){
    if(placer instanceof ServerPlayerEntity){
      final TileFusionEnergyConverter tile = MinecraftUtility.getTileEntity(pos, world, TileFusionEnergyConverter.class);
      if(tile != null){
        tile.setPlayer((ServerPlayerEntity)placer);
      }
    }
  }

}
