package addsynth.overpoweredmod.blocks.tiles.portal;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.blocks.MachineBlockTileEntity;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.client.gui.GuiHandler;
import addsynth.overpoweredmod.tiles.machines.portal.TilePortalControlPanel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class PortalControlPanel extends MachineBlockTileEntity {

  public PortalControlPanel(final String name){
    super();
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  public final void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
    tooltip.add("Class 3 Machine");
  }

  @Override
  public final TileEntity createNewTileEntity(final IBlockReader worldIn){
    return new TilePortalControlPanel();
  }

  @Override
  public final boolean onBlockActivated(World world, BlockPos pos,IBlockState state,PlayerEntity player,Hand hand,Direction side,float xHit,float yHit,float zHit){
    if(world.isRemote == false){
      player.openGui(OverpoweredMod.instance,GuiHandler.PORTAL_CONTROL_PANEL, world,pos.getX(),pos.getY(),pos.getZ());
    }
    return true;
  }

}
