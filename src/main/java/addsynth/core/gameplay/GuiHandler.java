package addsynth.core.gameplay;

import addsynth.core.gameplay.music_box.TileMusicBox;
import addsynth.core.gameplay.music_box.gui.GuiMusicBox;
import addsynth.core.inventory.container.BaseContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public final class GuiHandler implements IGuiHandler {

  public static final byte MUSIC_BOX = 0;

  @Override
  public final Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
    Object object = null;
    final TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
    switch(id){
    case MUSIC_BOX:                object = new BaseContainer<>(          (TileMusicBox)tile); break;
    }
    return object;
  }

  @Override
  public final Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
    Object object = null;
    final TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
    switch(id){
    case MUSIC_BOX:                object = new GuiMusicBox(              player.inventory,(TileMusicBox)tile); break;
    }
    return object;
  }

}
