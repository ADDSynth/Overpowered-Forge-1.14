package addsynth.overpoweredmod.blocks.tiles.machines;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.automatic.TileCrystalMatterReplicator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class CrystalMatterReplicator extends MachineBlockTileEntity {

  public CrystalMatterReplicator(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Class 4 Machine");
  }

  @Override
  public final TileEntity createNewTileEntity(World worldIn, int meta){
    return new TileCrystalMatterReplicator();
  }

  @Override
  public final boolean onBlockActivated(World world, BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing side,float xHit,float yHit,float zHit){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.CRYSTAL_MATTER_GENERATOR, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

}
