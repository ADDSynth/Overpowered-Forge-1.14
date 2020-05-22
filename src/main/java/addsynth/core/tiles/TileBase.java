package addsynth.core.tiles;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants;

/** YES! ALL of ADDSynth's TileEntities should override THIS class, because this
 *  simplifies updating the TileEntity!
 */
public abstract class TileBase extends TileEntity {

  public TileBase(final TileEntityType type){
    super(type);
  }

  // http://mcforge.readthedocs.io/en/latest/tileentities/tileentity/#synchronizing-the-data-to-the-client
  // https://github.com/TheGreyGhost/MinecraftByExample/blob/master/src/main/java/minecraftbyexample/mbe20_tileentity_data/TileEntityData.java
  // https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/rv6-1.12/src/main/java/appeng/tile/AEBaseTile.java
  // https://github.com/Railcraft/Railcraft/blob/mc-1.12.2/src/main/java/mods/railcraft/common/blocks/RailcraftTileEntity.java

  // When the world loads from disk, the server needs to send the TileEntity information to the client
  //  it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this:
  //  getUpdatePacket() and onDataPacket() are used for one-at-a-time TileEntity updates
  //  getUpdateTag() and handleUpdateTag() are used by vanilla to collate together into a single chunk update packet.

  // NBTexplorer is a very useful tool to examine the structure of your NBT saved data and make sure it's correct:
  //   http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-tools/1262665-nbtexplorer-nbt-editor-for-windows-and-mac

  @Override
  public final SUpdateTileEntityPacket getUpdatePacket(){
      CompoundNBT nbtTag = new CompoundNBT();
      write(nbtTag);
      return new SUpdateTileEntityPacket(this.pos, -1, nbtTag);
  }

  @Override
  public final void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt){
      read(pkt.getNbtCompound());
  }

  @Override
  public final CompoundNBT getUpdateTag(){
    CompoundNBT nbtTagCompound = new CompoundNBT();
    write(nbtTagCompound);
    return nbtTagCompound;
  }

  @Override
  public final void handleUpdateTag(final CompoundNBT tag){
    read(tag);
  }

  public void update_data(){
    if(world != null){
      markDirty();
      final BlockState blockstate = world.getBlockState(pos);
      world.notifyBlockUpdate(pos, blockstate, blockstate, Constants.BlockFlags.DEFAULT);
    }
  }

  /**
   * Marks chunk as dirty because a block with data was changed. This chunk will be saved. I Override
   * this method so I can remove some unecessary code involving block meta and Comparator Levels.
   * Most of my Tile Entities aren't going to use those.
   */
  @Override
  public final void markDirty(){
    // https://minecraft.gamepedia.com/1.13/Flattening
    world.markChunkDirty(pos,this);
  }

}
