package addsynth.core.gameplay;

import addsynth.core.gameplay.music_box.TileMusicBox;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  public static final TileEntityType<TileMusicBox> MUSIC_BOX =
    TileEntityType.Builder.create(TileMusicBox::new, Core.music_box).build(null);

}
