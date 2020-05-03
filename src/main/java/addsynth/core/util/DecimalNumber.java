package addsynth.core.util;

public final class DecimalNumber {

  private static final int ACCURACY = 3;
  public static final int DECIMAL_ACCURACY = 1_000; // (int)Math.pow(10, ACCURACY);

  public static final double align_to_accuracy(final double input){
    return Math.floor(input * DECIMAL_ACCURACY) / DECIMAL_ACCURACY;
  }

  private int number;
  private int sub_number;
  
  public final void set(final double number){
    this.number = (int)number;
    this.sub_number = (int)Math.floor((number - this.number) * DECIMAL_ACCURACY);
  }

  public final void set(final int number){
    this.number = number;
    this.sub_number = 0;
  }

  // DELETE
  // public final void setDecimal(final int decimal){
  //   this.sub_number = Math.min(decimal, DECIMAL_ACCURACY - 1);
  // }

  public final double get(){
    return number + getDecimal();
  }

  public final int getNumber(){
    return number;
  }
  
  public final double getDecimal(){
    return (double)sub_number / DECIMAL_ACCURACY;
  }

  public final int getDecimalNumber(){
    return sub_number;
  }

  // No unsigned primitive values, no structs, and now,
  // 3rd problem with Java: No operator overload! >:(

  public final void add(final double amount){
    set(get() + amount);
  }

  public final void subtract(final double amount){
    set(get() - amount);
  }

  public final void multiply(final double amount){
    set(get() * amount);
  }

  public final void divide(final double amount){
    set(get() / amount);
  }

  @Override
  public final String toString(){
    return Double.toString(get());
  }

}
