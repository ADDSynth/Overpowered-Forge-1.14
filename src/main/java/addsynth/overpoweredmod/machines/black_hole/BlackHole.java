package addsynth.overpoweredmod.machines.black_hole;

import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

@SuppressWarnings("deprecation")
public final class BlackHole extends Block implements ITileEntityProvider {

  public BlackHole(final String name){
    super(Block.Properties.create(Material.PORTAL, MaterialColor.BLACK).doesNotBlockMovement());
    // setResistance(100.0f);
    OverpoweredMod.registry.register_block(this, name, new Item.Properties().group(CreativeTabs.creative_tab).rarity(Rarity.EPIC));
  }

  @Override
  public final TileEntity createNewTileEntity(IBlockReader worldIn){
    return new TileBlackHole();
  }

}
