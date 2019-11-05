package addsynth.overpoweredmod.blocks.tiles.portal;

import java.util.Random;
import javax.annotation.Nonnull;
import addsynth.overpoweredmod.dimension.WeirdDimension;
import addsynth.overpoweredmod.tiles.technical.TilePortal;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.dimension.CustomTeleporter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class PortalEnergyBlock extends BlockContainer {

  // NOTE: well, after seeing a YouTube video, I was going to make this extend from the Vanilla PortalBlock class,
  //       but I want this to have a TileEntity, then just implement ITileProvider?

  public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});

  public PortalEnergyBlock(final String name){
    super(Material.PORTAL);
    OverpoweredMod.registry.register_block(this, name);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
    return NULL_AABB;
  }

  @Override
  public final @Nonnull ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player){
    return ItemStack.EMPTY;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final EnumBlockRenderType getRenderType(IBlockState state){
    return EnumBlockRenderType.MODEL;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public final BlockRenderLayer getRenderLayer(){
    return BlockRenderLayer.TRANSLUCENT;
  }

  @Override
  public final void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity){
    if(world.isRemote == false){
      if(entity.isNonBoss()){
        if(entity instanceof EntityPlayerMP){
          final MinecraftServer server = entity.getServer();
          if(server != null){
            server.getPlayerList().transferPlayerToDimension((EntityPlayerMP)entity, WeirdDimension.id, new CustomTeleporter(server.getWorld(WeirdDimension.id)));
          }
        }
        else{
          entity.changeDimension(WeirdDimension.id);
        }
      }
    }
  }

  @Override
  public final TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TilePortal();
  }

  @Override
  public int quantityDropped(Random random){
    return 0;
  }

  @Override
  @SuppressWarnings("deprecation")
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
    return ItemStack.EMPTY;
  }

}
