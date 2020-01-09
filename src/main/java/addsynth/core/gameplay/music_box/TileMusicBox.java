package addsynth.core.gameplay.music_box;

import javax.annotation.Nullable;
import addsynth.core.gameplay.Tiles;
import addsynth.core.gameplay.music_box.gui.ContainerMusicBox;
import addsynth.core.tiles.TileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileMusicBox extends TileBase implements ITickableTileEntity, INamedContainerProvider {

  public enum Command {
    PLAY, CHANGE_TEMPO, CYCLE_NEXT_DIRECTION, CHANGE_TRACK_INSTRUMENT, TOGGLE_MUTE;
    public static final Command[] value = Command.values();
  }

  private int next_direction = Direction.NORTH.getIndex();
  private final MusicGrid music_grid = new MusicGrid();

  private boolean playing;
  private boolean activated;
  public byte playhead;
  private int count;
  public boolean keep_playing;

  public TileMusicBox(){
    super(Tiles.MUSIC_BOX);
  }

  public final void change_tempo(final boolean direction){
    if(world.isRemote == false){
      if(music_grid.setTempo(direction)){
        // update_tempo_of_song();
        update_data();
      }
    }
  }

  public final void increment_next_direction(){
    next_direction = (next_direction + 1) % 6;
    update_data();
  }

  public final void change_track_instrument(byte track){
    if(music_grid != null){
      music_grid.change_tack_instrument(track);
      update_data();
    }
  }

  public final void disable_note(final byte track, final byte frame){
    if(music_grid != null){
      music_grid.disable_note(track, frame);
      update_data();
    }
  }

  public final void set_note(final byte track, final byte frame, final byte pitch){
    if(music_grid != null){
      music_grid.set_note(track, frame, pitch);
      update_data();
    }
  }

  public final void toggle_mute(final byte track){
    if(music_grid != null){
      music_grid.toggle_mute(track);
    }
    update_data();
  }

  @Override
  public final void tick(){
    if(world.isRemote == false){
      if(world.isBlockPowered(pos)){ // FIX: music boxes will play if block is powered when the world loads up.
        if(activated == false){
          play(true);
        }
        activated = true;
      }
      else{
        activated = false;
      }
      if(playing){ // OPTIMIZE: this should be Client-side.
        if(count == 0){
          music_grid.play_frame(world, pos, playhead);
        }
        count += 1;
        if(count >= music_grid.getTempo()){
          count = 0;
          playhead += 1;
          if(playhead >= MusicGrid.frames){
            if(keep_playing){
              play_next();
            }
            playing = false;
          }
          update_data();
        }
      }
    }
  }

  @Override
  public final void read(CompoundNBT nbt){
    next_direction = nbt.getInt("Next Direction");
    playing = nbt.getBoolean("Playing");
    playhead = nbt.getByte("Playhead Position");
    music_grid.load_from_nbt(nbt);
    super.read(nbt);
  }

  @Override
  public final CompoundNBT write(CompoundNBT nbt){
    nbt.putInt("Next Direction", next_direction);
    nbt.putBoolean("Playing", playing);
    nbt.putByte("Playhead Position", playhead);
    music_grid.save_to_nbt(nbt);
    return super.write(nbt);
  }

  public final void play(final boolean play_all){
    count = 0;
    playing = true;
    playhead = 0;
    keep_playing = play_all;
    update_data();
  }

  public final boolean is_playing(){
    return playing;
  }

  private final void play_next(){
    final TileEntity tile = world.getTileEntity(pos.offset(Direction.byIndex(next_direction)));
    if(tile instanceof TileMusicBox){
      ((TileMusicBox)tile).play(true);
    }
  }

  /**
   * This function recursively finds other Music Boxes next to this one and sets all their Tempo's,
   * but I decided to have each Music Box's tempo's independant of each other so they can have different tempos.
   * So your song can change the tempo mid-song.
   */
  private final void update_tempo_of_song(){ // DELETE, OPTIMIZE all versions!
  }

  /**
   * This is only used by the {@link addsynth.core.gameplay.music_box.MusicSheet}.
   */
  public final MusicGrid getMusicGrid(){
    return music_grid;
  }

  public final byte getTempo(){
    return music_grid.getTempo();
  }

  public final int get_next_direction(){
    return next_direction;
  }

  public final byte get_note(byte frame, byte track){
    if(music_grid != null){
      return music_grid.get_pitch((byte)track, (byte)frame);
    }
    return 0;
  }

  public final byte get_track_instrument(byte track){
    if(music_grid != null){
      return music_grid.get_track_instrument(track);
    }
    return 0;
  }

  public final boolean note_exists(final byte track, final byte frame){
    if(music_grid != null){
      return music_grid.note_exists(track, frame);
    }
    return false;
  }

  public final boolean get_mute(final byte track){
    if(music_grid != null){
      return music_grid.get_mute(track);
    }
    return false;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerMusicBox(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getTranslationKey());
  }

}
