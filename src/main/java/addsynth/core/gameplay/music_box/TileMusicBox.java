package addsynth.core.gameplay.music_box;

import addsynth.core.gameplay.music_box.data.MusicGrid;
import addsynth.core.gameplay.registers.Tiles;
import addsynth.core.tiles.TileBase;
import addsynth.core.util.game.MinecraftUtility;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;

public final class TileMusicBox extends TileBase implements ITickableTileEntity {

  public enum Command {
    PLAY, CHANGE_TEMPO, CYCLE_NEXT_DIRECTION, TOGGLE_MUTE, SWAP_TRACK;
    public static final Command[] value = Command.values();
  }

  private int next_direction = Direction.NORTH.getIndex();
  private final MusicGrid music_grid = new MusicGrid();
  public boolean changed;
  private boolean playing;
  private boolean activated;
  public byte playhead;
  private int count;
  public boolean keep_playing;

  public TileMusicBox(){
    super(Tiles.MUSIC_BOX);
  }

// ===================================== TICK ====================================

  @Override
  public final void tick(){
    if(onServerSide()){
      if(world.isBlockPowered(pos)){ // FIX: music boxes will play if block is powered when the world loads up.
        if(activated == false){
          play(true);
        }
        activated = true;
      }
      else{
        activated = false;
      }
      if(playing){
        if(count == 0){
          music_grid.play_frame(world, pos, playhead);
        }
        count += 1; // TODO: Also use a Tick Handler here.
        if(count >= music_grid.getTempo()){
          count = 0;
          playhead += 1;
          if(playhead >= MusicGrid.frames){
            if(keep_playing){
              play_next();
            }
            playing = false;
          }
          changed = true;
        }
      }
      if(changed){
        update_data();
        changed = false;
      }
    }
  }

// ================================ MAIN FUNCTIONS ===================================

  @Override
  public final void read(CompoundNBT nbt){
    super.read(nbt);
    next_direction = nbt.getInt("Next Direction");
    playing = nbt.getBoolean("Playing");
    playhead = nbt.getByte("Playhead Position");
    music_grid.load_from_nbt(nbt);
  }

  @Override
  public final CompoundNBT write(CompoundNBT nbt){
    super.write(nbt);
    nbt.putInt("Next Direction", next_direction);
    nbt.putBoolean("Playing", playing);
    nbt.putByte("Playhead Position", playhead);
    music_grid.save_to_nbt(nbt);
    return nbt;
  }

  public final void play(final boolean play_all){
    count = 0;
    playing = true;
    playhead = 0;
    keep_playing = play_all;
    changed = true;
  }

  public final boolean is_playing(){
    return playing;
  }

  private final void play_next(){
    final TileMusicBox tile = MinecraftUtility.getTileEntity(pos.offset(Direction.byIndex(next_direction)), world, TileMusicBox.class);
    if(tile != null){
      tile.play(true);
    }
  }

// ================================= CHANGE DATA ==================================

  /**
   * This function recursively finds other Music Boxes next to this one and sets all their Tempo's,
   * but I decided to have each Music Box's tempo's independant of each other so they can have different tempos.
   * So your song can change the tempo mid-song.
   */
  private final void update_tempo_of_song(){
  }

  public final void change_tempo(final boolean direction){
    if(onServerSide()){
      if(music_grid.setTempo(direction)){
        // update_tempo_of_song();
        changed = true;
      }
    }
  }

  public final void increment_next_direction(){
    next_direction = (next_direction + 1) % 6;
    changed = true;
  }

  public final void change_track_instrument(final byte track, final byte instrument){
    if(music_grid != null){
      music_grid.change_tack_instrument(track, instrument);
      changed = true;
    }
  }

  public final void disable_note(final byte track, final byte frame){
    if(music_grid != null){
      music_grid.disable_note(track, frame);
      changed = true;
    }
  }

  public final void set_note(final byte track, final byte frame, final byte pitch){
    if(music_grid != null){
      music_grid.set_note(track, frame, pitch);
      changed = true;
    }
  }

  public final void toggle_mute(final byte track){
    if(music_grid != null){
      music_grid.toggle_mute(track);
      changed = true;
    }
  }

  public final void swap_track(final int track_from, final int track_to){
    if(music_grid != null){
      music_grid.swap_track(track_from, track_to);
      changed = true;
    }
  }

// =================================== GET DATA ====================================

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

}
