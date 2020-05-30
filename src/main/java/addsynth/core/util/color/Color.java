package addsynth.core.util.color;

import net.minecraft.util.math.MathHelper;

public final class Color {

  public static final int text_color = 4210752;
  
  public static final Color BLACK   = new Color(  0,   0,   0);
  public static final Color GRAY    = new Color(128, 128, 128);
  public static final Color WHITE   = new Color(255, 255, 255);
  public static final Color RED     = new Color(255,   0,   0);
  public static final Color ORANGE  = new Color(255, 128,   0);
  public static final Color YELLOW  = new Color(255, 255,   0);
  public static final Color GREEN   = new Color(  0, 255,   0);
  public static final Color CYAN    = new Color(  0, 255, 255);
  public static final Color BLUE    = new Color(  0,   0, 255);
  public static final Color MAGENTA = new Color(255,  0,  255);

// =======================================================================================

  private int red;
  private int green;
  private int blue;
  private int alpha;
  private float opacity;
  private int full_color;

  public Color(){}
  
  public Color(final int value){
    set(value);
  }
  
  public Color(final int red, final int green, final int blue){
    this(red, green, blue, 255);
  }
  
  public Color(final int red, final int green, final int blue, final int alpha){
    this.red   = red;
    this.green = green;
    this.blue  = blue;
    this.alpha = alpha;
    this.opacity = convert(alpha);
    this.full_color = make(red, green, blue, alpha);
  }

// =======================================================================================

  public final void set(final Color other){
    this.set(other.get());
  }

  public final void setRed(final int red){
    final int r = red & 255;
    this.red = r;
    this.full_color &= 0xFF00FFFF;
    this.full_color |= r << 16;
  }

  public final void setRed(final float red){
    setRed(convert(red));
  }

  public final void setGreen(final int green){
    final int g = green & 255;
    this.green = g;
    this.full_color &= 0xFFFF00FF;
    this.full_color |= g << 8;
  }

  public final void setGreen(final float green){
    setGreen(convert(green));
  }

  public final void setBlue(final int blue){
    final int b = blue & 255;
    this.blue = b;
    this.full_color &= 0xFFFFFF00;
    this.full_color |= b;
  }

  public final void setBlue(final float blue){
    setBlue(convert(blue));
  }

  public final void setAlpha(final int alpha){
    final int a = alpha & 255;
    this.alpha = a;
    this.opacity = convert(a);
    this.full_color &= 0x00FFFFFF;
    this.full_color |= a << 24;
  }

  public final void setOpacity(final float opacity){
    this.opacity = MathHelper.clamp(opacity, 0.0f, 1.0f);
    this.alpha = convert(opacity);
    this.full_color &= 0x00FFFFFF;
    this.full_color |= this.alpha << 24;
  }

  public final void set(final int color){
    this.full_color = color;
    this.red   = getRed(  color);
    this.green = getGreen(color);
    this.blue  = getBlue( color);
    this.alpha = getAlpha(color);
    this.opacity = convert(this.alpha);
  }

// =======================================================================================

  public final int getRed(){   return red; }
  public final int getGreen(){ return green; }
  public final int getBlue(){  return blue; }
  public final int getAlpha(){ return alpha; }
  public final int get(){      return full_color; }
  public final float getOpacity(){ return opacity; }
  public final int getOpacityPercentage(){ return Math.round(opacity * 100); }

// =======================================================================================

  public static final int make(final float red, final float green, final float blue){
    return make(convert(red), convert(green), convert(blue), 255);
  }

  public static final int make(final int red, final int green, final int blue){
    return make(red, green, blue, 255);
  }

  public static final int make(final int red, final int green, final int blue, final int alpha){
    return (red & 255 ) << 16 | (green & 255) << 8 | (blue & 255) | (alpha & 255) << 24;
  }
  
  public static final int make(final int red, final int green, final int blue, final float alpha){
    return make(red, green, blue, convert(alpha));
  }
  
  public static final int make(final float red, final float green, final float blue, final float opacity){
    return make(convert(red), convert(green), convert(blue), convert(opacity));
  }

  public static final int getRed(  final int color){ return (color >>> 16) & 255; }
  public static final int getGreen(final int color){ return (color >>>  8) & 255; }
  public static final int getBlue( final int color){ return color & 255; }
  public static final int getAlpha(final int color){ return (color >>> 24) & 255; }
  
// =======================================================================================
  
  public static final int convert(final float percentage){
    return Math.round(MathHelper.clamp(percentage, 0.0f, 1.0f) * 255);
  }

  public static final float convert(final int value){
    return (float)MathHelper.clamp(value, 0, 255) / 255;
  }

  public final boolean isOpaque(){
    return alpha == 255;
  }

  public final boolean isTransparent(){
    return alpha != 255;
  }

  public final boolean isInvisible(){
    return alpha == 0;
  }

  @Override
  public final boolean equals(final Object obj){
    if(obj instanceof Color){
      final Color other = (Color)obj;
      return this.red  == other.getRed()  && this.green == other.getGreen() &&
             this.blue == other.getBlue() && this.alpha == other.getAlpha();
    }
    return false;
  }

  @Override
  public final String toString(){
    if(alpha == 255){
      return "Color{Red: "+red+", Green: "+green+", Blue: "+blue+", Opaque}";
    }
    if(alpha == 0){
      return "Color{Red: "+red+", Green: "+green+", Blue: "+blue+", Invisible}";
    }
    return "Color{Red: "+red+", Green: "+green+", Blue: "+blue+", Opacity: "+getOpacityPercentage()+"%}";
  }

  // FEATURE: add a compare function, that returns a percentage of how much difference there is between 2 colors.

}
