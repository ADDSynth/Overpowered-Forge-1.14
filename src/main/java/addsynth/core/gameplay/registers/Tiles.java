package addsynth.core.gameplay.registers;

import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.music_box.TileMusicBox;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  public static final TileEntityType<TileMusicBox> MUSIC_BOX =
    TileEntityType.Builder.create(TileMusicBox::new, Core.music_box).build(null);

}
