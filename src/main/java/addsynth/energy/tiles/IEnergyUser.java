package addsynth.energy.tiles;

import addsynth.energy.Energy;

public interface IEnergyUser {

  public Energy getEnergy();

  /** This is called by your {@link Energy} object whenever any internal energy variable is changed.
   *  For TileEntities, the standard code to execute is:<br />
   *  <code><pre>
   *  markDirty();              // to mark chunk dirty so it saves on world quit.
   *  world.notifyBlockUpdate() // to send an update tag to clients so the client's Energy object updates.
   *  </pre></code>
   */
  public void onEnergyChanged();

}
