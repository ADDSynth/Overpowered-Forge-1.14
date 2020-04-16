package addsynth.core.gameplay.music_box;

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

  private static final class Note {
    public boolean on;
    public byte pitch;
    public float volume = 1.0f;
  }

  private static final class Track {
    public boolean mute;
    public byte instrument;
    public final Note[] note = new Note[frames];
  }

  private byte tempo = default_tempo;
  private final Track[] track = new Track[tracks];

  public MusicGrid() {
    byte i;
    byte j;
    for(j = 0; j < tracks; j++){
      track[j] = new Track();
      for(i = 0; i < frames; i++){
        track[j].note[i] = new Note();
      }
    }
  }

  public final void save_to_nbt(final CompoundNBT nbt){
    final CompoundNBT music_tag = new CompoundNBT();
    ListNBT track_list;
    CompoundNBT track_tag;
    ListNBT note_list;
    CompoundNBT note_tag;
    Note note;
    byte i;
    byte j;
    
    music_tag.putByte("Tempo", tempo);
    track_list = new ListNBT();
    for(j = 0; j < tracks; j++){
      track_tag = new CompoundNBT();
      track_tag.putBoolean("Mute", track[j].mute);
      track_tag.putByte("Instrument", track[j].instrument);
      note_list = new ListNBT();
      for(i = 0; i < frames; i++){
        note_tag = new CompoundNBT();
        note = track[j].note[i];
        note_tag.putBoolean("On", note.on);
        note_tag.putByte("Pitch", note.pitch);
        note_tag.putFloat("Volume", note.volume);
        note_list.add(note_tag);
      }
      track_tag.put("Notes", note_list);
      track_list.add(track_tag);
    }
    music_tag.put("Tracks", track_list);
    
    nbt.put("MusicGrid", music_tag);
  }

  public final void load_from_nbt(final CompoundNBT nbt){
    final CompoundNBT music_tag = nbt.getCompound("MusicGrid");
    ListNBT track_list;
    CompoundNBT track_tag;
    ListNBT note_list;
    CompoundNBT note_tag;
    Note note;
    byte i;
    byte j;
    
    tempo = music_tag.getByte("Tempo");
    for(j = 0; j < tracks; j++){
      track_list = music_tag.getList("Tracks", Constants.NBT.TAG_COMPOUND);
      track_tag = track_list.getCompound(j);
      track[j].mute = track_tag.getBoolean("Mute");
      track[j].instrument = track_tag.getByte("Instrument");
      note_list = track_tag.getList("Notes", Constants.NBT.TAG_COMPOUND);
      for (i = 0; i < frames; i++){
        note_tag = note_list.getCompound(i);
        note = track[j].note[i];
        note.on = note_tag.getBoolean("On");
        note.pitch = note_tag.getByte("Pitch");
        note.volume = note_tag.getFloat("Volume");
      }
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

  public final void set_note(final byte track, final byte frame, byte pitch, byte instrument){
    this.track[track].note[frame].on = true;
    this.track[track].note[frame].pitch = pitch;
    this.track[track].note[frame].volume = 1.0f;
    this.track[track].instrument = instrument;
  }

  public final void disable_note(final byte track, final byte frame){
    getNote(track, frame).on = false;
  }

  public final byte get_pitch(final byte track, final byte frame){
    return getNote(track, frame).pitch;
  }

  public final void change_tack_instrument(final byte track){
    this.track[track].instrument = (byte)((this.track[track].instrument + 1) % instruments.length);
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

}
