package addsynth.core.gameplay.music_box.gui;

import addsynth.core.container.BaseContainer;
import addsynth.core.gameplay.Containers;
import addsynth.core.gameplay.music_box.TileMusicBox;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class ContainerMusicBox extends BaseContainer<TileMusicBox> {

  public ContainerMusicBox(final int id, final PlayerInventory player_inventory, final TileMusicBox tile){
    super(Containers.MUSIC_BOX, id, player_inventory, tile);
  }

  public ContainerMusicBox(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.MUSIC_BOX, id, player_inventory, data);
  }

}
