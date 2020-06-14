package addsynth.energy.gameplay.universal_energy_interface;

import addsynth.core.util.StringUtil;

public enum TRANSFER_MODE {

  BI_DIRECTIONAL(true,  true,  true,  "gui.addsynth_energy.transfer_mode.bi_directional"),
  RECEIVE(       true,  false, true,  "gui.addsynth_energy.transfer_mode.receive"),
  EXTRACT(       false, true,  true,  "gui.addsynth_energy.transfer_mode.extract"),
  EXTERNAL(      true,  true,  false, "gui.addsynth_energy.transfer_mode.external"),
  INTERNAL(      false, false, true,  "gui.addsynth_energy.transfer_mode.internal"),
  NO_TRANSFER(   false, false, false, "gui.addsynth_energy.transfer_mode.no_transfer");
  
  public final boolean canReceive;
  public final boolean canExtract;
  public final boolean integrate;
  private final String translation_key;

  private TRANSFER_MODE(final boolean receive, final boolean extract, final boolean integrate, final String translation_key){
    this.canReceive = receive;
    this.canExtract = extract;
    this.integrate = integrate;
    this.translation_key = translation_key;
  }
  
  @Override
  public final String toString(){
    return StringUtil.translate(translation_key);
  }

}
