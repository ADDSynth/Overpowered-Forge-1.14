package addsynth.energy.lib.main;

/** This is the main interface used to interact with objects that store
 *  Energy. Only use this if your TileEntity is a Battery. Other machines,
 *  should use either {@link IEnergyGenerator} or {@link IEnergyConsumer}. */
public interface IEnergyUser {

  public Energy getEnergy();

}
