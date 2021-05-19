package addsynth.energy.lib.main;

/** Use this to have more control over how Energy is received and extracted. */
public interface ICustomEnergyUser extends IEnergyUser {

  /** Returns the energy this TileEntity requests. */
  public double getRequestedEnergy();

  /** Returns the energy this TileEntity can produce. */
  public double getAvailableEnergy();

}
