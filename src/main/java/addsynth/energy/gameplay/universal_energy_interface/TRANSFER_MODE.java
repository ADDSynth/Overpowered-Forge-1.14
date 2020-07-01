package addsynth.energy.gameplay.universal_energy_interface;

import addsynth.core.util.StringUtil;

public enum TRANSFER_MODE {

  BI_DIRECTIONAL(true,  true,  "gui.addsynth_energy.transfer_mode.bi_directional"),
  RECEIVE(       true,  false, "gui.addsynth_energy.transfer_mode.receive"),
  EXTRACT(       false, true,  "gui.addsynth_energy.transfer_mode.extract"),
  // EXTERNAL(      true,  true,  false, "gui.addsynth_energy.transfer_mode.external"),
  // INTERNAL(      false, false, true,  "gui.addsynth_energy.transfer_mode.internal"),
  NO_TRANSFER(   false, false, "gui.addsynth_energy.transfer_mode.no_transfer");
  // FUTURE: wait until v1.5 when we've added Work Systems to reimplement this.
  
  public final boolean canReceive;
  public final boolean canExtract;
  private final String translation_key;

  private TRANSFER_MODE(final boolean receive, final boolean extract, final String translation_key){
    this.canReceive = receive;
    this.canExtract = extract;
    this.translation_key = translation_key;
  }
  
  @Override
  public final String toString(){
    return StringUtil.translate(translation_key);
  }

}
