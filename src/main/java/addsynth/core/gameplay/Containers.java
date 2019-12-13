package addsynth.core.gameplay;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.music_box.gui.ContainerMusicBox;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public final class Containers {

  public static final ContainerType<ContainerMusicBox> MUSIC_BOX = register("music_box", ContainerMusicBox::new);

  private static final <T extends Container> ContainerType<T> register(final String name, final ContainerType.IFactory<T> factory){
    final ContainerType<T> container = new ContainerType<>(factory);
    container.setRegistryName(ADDSynthCore.MOD_ID, name);
    return container;
  }

}
