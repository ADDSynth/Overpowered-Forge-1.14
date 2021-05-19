package addsynth.energy.lib.main;

public final class Generator extends Energy {

  public Generator(){
    super(0, 0, 0, 0);
  }
  
  public Generator(final double capacity){
    super(capacity, 0, capacity, 0);
  }
  
  public Generator(final double capacity, final double maxExtract){
    super(capacity, 0, maxExtract, 0);
  }

  @Override
  public final double getRequestedEnergy(){
    return 0;
  }

  @Override
  public final boolean canReceive(){
    return false;
  }

  @Override
  public final void setMaxReceive(final int maxReceive){
  }

  @Override
  public final void setTransferRate(final int transferRate){
    this.maxExtract.set(transferRate);
    changed = true;
  }

  @Override
  public final double getMaxReceive(){
    return 0.0;
  }

}
