package addsynth.core.gameplay.music_box.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public final class MusicGrid {

  public static final SoundEvent[] instruments = new SoundEvent[] {
    SoundEvents.BLOCK_NOTE_BLOCK_HARP,
    SoundEvents.BLOCK_NOTE_BLOCK_BASS,
    SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM,
    SoundEvents.BLOCK_NOTE_BLOCK_SNARE,
    SoundEvents.BLOCK_NOTE_BLOCK_HAT,
    SoundEvents.BLOCK_NOTE_BLOCK_BELL,
    SoundEvents.BLOCK_NOTE_BLOCK_CHIME,
    SoundEvents.BLOCK_NOTE_BLOCK_FLUTE,
    SoundEvents.BLOCK_NOTE_BLOCK_GUITAR,
    SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE,
    SoundEvents.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,
    SoundEvents.BLOCK_NOTE_BLOCK_COW_BELL,
    SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO,
    SoundEvents.BLOCK_NOTE_BLOCK_BIT,
    SoundEvents.BLOCK_NOTE_BLOCK_BANJO,
    SoundEvents.BLOCK_NOTE_BLOCK_PLING
  };
  
  public static final byte tracks = 8;
  public static final byte frames = 8;
  private static final byte default_tempo = 5;
  private static final byte lowest_tempo = 20;

  private byte tempo = default_tempo;
  private final Track[] track = new Track[tracks];

  public MusicGrid(){
    byte j;
    for(j = 0; j < tracks; j++){
      track[j] = new Track();
    }
  }

  public final void save_to_nbt(final CompoundNBT nbt){
    final CompoundNBT music_tag = new CompoundNBT();
    final ListNBT track_list = new ListNBT();
    music_tag.putByte("Tempo", tempo);
    byte j;
    for(j = 0; j < tracks; j++){
      track_list.add(track[j].getNBT());
    }
    music_tag.put("Tracks", track_list);
    nbt.put("MusicGrid", music_tag);
  }

  public final void load_from_nbt(final CompoundNBT nbt){
    final CompoundNBT music_tag = nbt.getCompound("MusicGrid");
    final ListNBT track_list = music_tag.getList("Tracks", Constants.NBT.TAG_COMPOUND);
    tempo = music_tag.getByte("Tempo");
    byte j;
    for(j = 0; j < tracks; j++){
      track[j].load_from_nbt(track_list.getCompound(j));
    }
  }

  private final Note getNote(final byte track, final byte frame){
    Note note = null;
    try{
      note = this.track[track].note[frame];
    }
    catch(ArrayIndexOutOfBoundsException e){
      throw new IllegalArgumentException("getNote(track = "+track+", frame = "+frame+") failed. track must be in the range 0-"+(tracks-1)+", and frame must be in the range 0-"+(frames-1)+".");
    }
    return note;
  }

  public final boolean note_exists(final byte track, final byte frame){
    return getNote(track, frame).on;
  }

  public final void set_note(final byte track, final byte frame, byte pitch){
    this.track[track].note[frame].set(pitch);
  }

  public final void disable_note(final byte track, final byte frame){
    getNote(track, frame).on = false;
  }

  public final byte get_pitch(final byte track, final byte frame){
    return getNote(track, frame).pitch;
  }

  public final void change_tack_instrument(final byte track, final byte instrument){
    this.track[track].instrument = instrument;
  }

  public final byte get_track_instrument(final byte track){
    return this.track[track].instrument;
  }

  /**
   * @param direction
   * @return true if tempo variable actually changed.
   */
  public final boolean setTempo(final boolean direction){
    boolean pass = false;
    if(direction){
      if(tempo > 1){
        tempo -=1;
        pass = true;
      }
    }
    else{
      if(tempo < lowest_tempo){
        tempo += 1;
        pass = true;
      }
    }
    return pass;
  }

  public final byte getTempo(){
    return tempo;
  }

  public final boolean get_mute(final byte track){
    return this.track[track].mute;
  }
  
  public final void toggle_mute(final byte track){
    this.track[track].mute = !(this.track[track].mute);
  }

  /**
   * 
   * @param world
   * @param position
   * @param frame
   * @see net.minecraft.block.NoteBlock#eventReceived
   */
  public final void play_frame(final World world, final BlockPos position, final byte frame){
    if(world != null && frame >= 0){
      Track track;
      Note note;
      final boolean spawn_particles = false;
      SoundEvent instrument;
      float pitch;
      byte i;
      for(i = 0; i < tracks; i++){
        track = this.track[i];
        if(track != null){
          if(track.mute == false){
            instrument = instruments[track.instrument];
            note = track.note[frame];
            if(note != null){
              if(note.on){
                // world.addBlockEvent(pos, Blocks.NOTEBLOCK, i, music[page + playhead][i]);
                pitch = (float)Math.pow(2.0, (float)(note.pitch - 12) / 12); // they literally use OpenAL to change the pitch, they're not seperate sound files.
                world.playSound(null, position, instrument, SoundCategory.RECORDS, 3.0f, pitch);
                if(spawn_particles){
                  world.addParticle(ParticleTypes.NOTE, position.getX()+0.5, position.getY()+0.5, position.getZ()+0.5, note.pitch / 24, 0.0, 0.0);
                }
              }
            }
          }
        }
      }
    }
  }

  public final void swap_track(final int from, final int to){
    final Track temp_track = new Track();
    temp_track.setFrom(track[to]);
    track[to].setFrom(track[from]);
    track[from].setFrom(temp_track);
  }

}
