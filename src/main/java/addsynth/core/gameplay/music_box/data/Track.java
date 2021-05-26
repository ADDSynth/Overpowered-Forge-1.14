package addsynth.core.gameplay.music_box.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

public final class Track {

  public boolean mute;
  public byte instrument;
  public final Note[] note = new Note[MusicGrid.frames];

  public Track(){
    byte i;
    for(i = 0; i < MusicGrid.frames; i++){
      note[i] = new Note();
    }
  }
  
  public final CompoundNBT getNBT(){
    final CompoundNBT track_tag = new CompoundNBT();
    track_tag.putBoolean("Mute", mute);
    track_tag.putByte("Instrument", instrument);
    final ListNBT note_list = new ListNBT();
    byte i;
    for(i = 0; i < MusicGrid.frames; i++){
      note_list.add(note[i].getNBT());
    }
    track_tag.put("Notes", note_list);
    return track_tag;
  }

  public final void load_from_nbt(final CompoundNBT track_tag){
    mute       = track_tag.getBoolean("Mute");
    instrument = track_tag.getByte("Instrument");
    final ListNBT note_list = track_tag.getList("Notes", Constants.NBT.TAG_COMPOUND);
    byte i;
    for(i = 0; i < MusicGrid.frames; i++){
      note[i].load_from_nbt(note_list.getCompound(i));
    }
  }

  public final void setFrom(final Track track){
    mute = track.mute;
    instrument = track.instrument;
    byte i;
    for(i = 0; i < MusicGrid.frames; i++){
      note[i].setFrom(track.note[i]); // I don't want to think about assigning reference variables right now.
    }
  }

}
