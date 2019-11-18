package addsynth.energy.gameplay.blocks;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.energy.gameplay.tiles.TileEnergyStorage;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class EnergyStorageBlock extends MachineBlockTileEntity {

  public EnergyStorageBlock(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
    setSoundType(SoundType.GLASS);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new StringTextComponent("Energy Machine"));
  }

  @Override
  public final TileEntity createNewTileEntity(IBlockReader worldIn){
    return new TileEnergyStorage();
  }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    @SuppressWarnings("deprecation")
    public final boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public final BlockRenderLayer getRenderLayer(){
      return BlockRenderLayer.TRANSLUCENT;
    }

  @Override
  public final boolean onBlockActivated(World world, BlockPos pos, IBlockState state, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.ENERGY_STORAGE, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

}
