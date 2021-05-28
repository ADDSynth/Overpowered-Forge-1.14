package addsynth.core.util.constants;

public enum DevStage {

  DEVELOPMENT(true, " (DEVELOPMENT BUILD)"),
  ALPHA(true, "-ALPHA"),
  BETA(true, "-BETA"),
  STABLE(false, "");

  public final boolean isDevelopment;
  public final String label;

  private DevStage(boolean isDevelopment, String label){
    this.isDevelopment = isDevelopment;
    this.label = label;
  }

}
