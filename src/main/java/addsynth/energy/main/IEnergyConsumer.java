package addsynth.energy.main;

public interface IEnergyConsumer extends IEnergyUser {

  public double getNeededEnergy();

  @Deprecated
  public void receiveEnergy(double add_energy);

}
