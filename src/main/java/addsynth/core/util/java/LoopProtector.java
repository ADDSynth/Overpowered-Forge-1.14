package addsynth.core.util.java;

public final class LoopProtector {

  private int iteration = 0;
  private final int max_value;
  private boolean safe = true;

  public LoopProtector(){
    this.max_value = Integer.MAX_VALUE;
  }

  public LoopProtector(final int max_interations){
    this.max_value = max_interations;
  }

  public final void increment(){
    iteration += 1;
    if(safe && iteration >= max_value){
      System.err.println(new Exception("Looped Forever!"));
      safe = false;
    }
  }

  public final boolean isSafe(){
    return safe;
  }

  public final int index(){
    return iteration;
  }

}
