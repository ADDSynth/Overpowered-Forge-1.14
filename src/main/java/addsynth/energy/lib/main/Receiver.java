package addsynth.energy.lib.main;

public class Receiver extends Energy {

  public Receiver(){
    super(0, 0, 0, 0);
  }
  
  public Receiver(final double capacity){
    super(capacity, capacity, 0, 0);
  }

  public Receiver(final double capacity, final double maxReceive){
    super(capacity, maxReceive, 0, 0);
  }

  @Override
  public final double getAvailableEnergy(){
    return 0;
  }

  @Override
  public final boolean canExtract(){
    return false;
  }

  @Override
  public final void setMaxExtract(final double maxExtract){
  }

  @Override
  public final void setTransferRate(final int transferRate){
    this.maxReceive.set(transferRate);
    changed = true;
  }

  @Override
  public final double getMaxExtract(){
    return 0.0;
  }

}
