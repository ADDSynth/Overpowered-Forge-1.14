package addsynth.core.gameplay;

import addsynth.core.gameplay.music_box.gui.ContainerMusicBox;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.network.IContainerFactory;

public final class Containers {

  public static final ContainerType<ContainerMusicBox> MUSIC_BOX =
    new ContainerType<>((IContainerFactory<ContainerMusicBox>)ContainerMusicBox::new);

}
