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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EnergyStorageBlock extends MachineBlockTileEntity {

  public EnergyStorageBlock(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
    setSoundType(SoundType.GLASS);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Energy Machine");
  }

  @Override
  public final TileEntity createNewTileEntity(World worldIn, int meta){
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
    @SideOnly(Side.CLIENT)
    public final BlockRenderLayer getRenderLayer(){
      return BlockRenderLayer.TRANSLUCENT;
    }

  @Override
  public final boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.ENERGY_STORAGE, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

}
