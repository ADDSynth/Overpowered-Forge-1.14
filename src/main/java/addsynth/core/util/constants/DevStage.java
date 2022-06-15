package addsynth.core.util.constants;

public enum DevStage {

  /** Development of new project. */
  ALPHA(true, "-ALPHA"),
  /** Done adding new features (feature freeze), now we're fixing bugs. */
  BETA(true, "-BETA"),
  /** Project is currently being worked on. */
  DEVELOPMENT(true, " (DEVELOPMENT BUILD)"),
  /** Development cycle is complete. This is the version we plan to release, unless we find major bugs. */
  RELEASE_CANDIDATE(false, " Release Candidate"),
  /** Final version released to the public. */
  STABLE(false, "");

  public final boolean isDevelopment;
  public final String label;

  private DevStage(boolean isDevelopment, String label){
    this.isDevelopment = isDevelopment;
    this.label = label;
  }

}
