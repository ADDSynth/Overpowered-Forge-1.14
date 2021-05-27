package addsynth.core.util.constants;

public enum DevStage {

  DEVELOPMENT(true),
  ALPHA(true),
  BETA(true),
  STABLE(false);

  public final boolean isDevelopment;

  private DevStage(boolean isDevelopment){
    this.isDevelopment = isDevelopment;
  }

}
