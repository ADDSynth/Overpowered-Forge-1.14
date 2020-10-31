package addsynth.energy.main;

public final class Receiver extends Energy {

  public Receiver(){
    super(0, 0, 0, 0);
  }
  
  public Receiver(final double capacity){
    super(0, capacity, capacity, 0);
  }

  public Receiver(final double capacity, final double maxReceive){
    super(0, capacity, maxReceive, 0);
  }

  @Override
  public final boolean canExtract(){
    return false;
  }

  @Override
  public final void setMaxExtract(final int maxExtract){
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
