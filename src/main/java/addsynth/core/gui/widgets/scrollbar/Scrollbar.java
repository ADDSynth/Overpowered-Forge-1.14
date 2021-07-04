package addsynth.core.gui.widgets.scrollbar;

import java.util.function.BiConsumer;
import addsynth.core.ADDSynthCore;
import addsynth.core.gui.widgets.Dimensions;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.core.util.math.CommonMath;
import addsynth.core.util.math.MathUtility;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;

/** <p>A Scrollbar is a widget that goes beside a list of values which the player can
 *     move up or down to scroll a list of values. It automatically adjusts its position
 *     and size based on how many list items there are.
 *  <p>First define an array of {@link ListEntry}s and place them on your gui, and add them as buttons.
 *     Then create the Scrollbar. Place it in the gui where you want, give it your full array of string
 *     values, and your array of {@link ListEntry}s.
 *  <p>When any {@link ListEntry} is clicked, that value will be selected in the list. Only one value
 *     can be selected at any time.
 *  <p>You can assign a {@link BiConsumer} as a responder, which will be called whenever a list item
 *     is selected. The first argument is the string value. The second is the index in the array of values.
 *  <p>You can manually get the selected value by called {@link #getSelected}, or the index by calling
 *     {@link #getSelectedIndex}.
 *  <p>To manually set which entry is selected call {@link #setSelected(int)}. If you don't want the
 *     Scrollbar to call the responder you assigned, call {@link #setSelected(int, boolean)}.
 *     Absolutely be careful you don't have Scrollbar responders call each other otherwise that will
 *     create an infinite loop!
 *  <p>Call {@link #setSelected(int)} with any negative value to unselect.
 * @author ADDSynth
 */
public final class Scrollbar extends Widget {

  private static final ResourceLocation texture = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/scrollbar.png");

  // texture coordinates
  /** Main scrollbar X texture coordinate. */
  private static final int scrollbar_texture_x = 0;
  /** Main & Background scrollbar Y texture coordinate. */
  private static final int scrollbar_texture_y = 24;
  /** Scrollbar background X texture coordinate. */
  private static final int scrollbar_background_x = 24;
  /** Scrollbar center X texture coordinate. */
  private static final int scrollbar_center_x = 48;
  /** Main scrollbar maximum Y texture coordinate. */
  private static final int scrollbar_texture_max_y = 488;

  // texture dimensions
  /** Scrollbar texture width. */
  private static final int scrollbar_texture_width = 24;
  /** Height of main scrollbar in texture. */
  private static final int max_scrollbar_height = 464; // 512 - 48
  /** Height of scrollbar center in texture. */
  private static final int scrollbar_center_height = 4;

  // gui dimensions
  /** Scrollbar width on the gui. */
  private static final int scrollbar_gui_width = 12;
  private static final int center_gui_height = 2;
  /** Scrollbar texture width scale value. */
  private static final int texture_scale_width = 72;
  /** Scrollbar texture height scale value. */
  private static final int texture_scale_height = 512;

  // other constants
  /** For every scrollbar height that is a multiple of this, we add a center section. */
  private static final int center_ratio = 16;

  // scrollbar size values
  /** Main scrollbar height that shows on the gui. Resizes based on number of list values. */
  private int scrollbar_height;
  /** Calculation value. Half height of Scrollbar. Used to calculate the center_y value. */
  private int scrollbar_half_height;
  /** Main scrollbar center Y position. Used to in conjunction with the Mouse Y to move the scrollbar. */
  private int center_y;
  /** Calculation value. Starting Y Gui coordinate of the center section. */
  private int scrollbar_center_y;
  /** Calculation value. Starting bottom half Y Gui coordinate. */
  private int scrollbar_bottom_y;
  
  // calculations
  private int number_of_center_sections;
  private int[] scrollbar_gui_height_calc;
  
  /** Current position of main scrollbar in gui coordinate space. All drawing starts at this position. */
  private int position_y;
  /** Max Position scrollbar will be at if at last index position. */
  private int max_position_y;
  /** Temp variable used to determine if the index position changed. */
  private int temp_index;
  /** Current scrollbar index. */
  private int index_position;
  /** Maximum scrollbar positions. */
  private int max_positions;
  private int[] index_positions;

  /** The List Entries connected to this Scrollbar. */
  private ListEntry[] list_items;
  /** Number of visible List Entries. */
  private int visible_elements;
  /** Full list of values. */
  private String[] values;
  /** Number of string values in the full list. */
  private int list_length;
  /** The selected string value in the full list. */
  private int selected = -1;

  /** This is passed the new Selection Index every time it is changed,
   *  even if it is changed to an invalid value. */
  private BiConsumer<String, Integer> onSelected;

  public Scrollbar(int x, int y, int height, ListEntry[] list_items){
    this(x, y, height, list_items, null);
  }

  public Scrollbar(int x, int y, int height, ListEntry[] list_items, String[] values){
    super(x, y, scrollbar_gui_width, height, "");
    if(height > max_scrollbar_height - 8){
      ADDSynthCore.log.error("Requested Scrollbar height is bigger than Max Scrollbar height!");
    }
    for(ListEntry entry : list_items){
      entry.setScrollbar(this);
    }
    this.list_items = list_items;
    visible_elements = list_items.length;
    updateScrollbar(values);
  }
  
  /** Call this to assign the full list of values. The scrollbar will automatically update its displayed list and size. */
  @SuppressWarnings("deprecation")
  public void updateScrollbar(final String[] values){
    try{
      this.values = values != null ? values : new String[0];
      list_length = this.values.length;
  
      // recalculate everything for now. it doesn't hurt.
  
      // scrollbar
      if(list_length > visible_elements){
        final double ratio = (double)visible_elements / list_length;
        scrollbar_height = Math.max((int)Math.round(height * ratio), 6);
        max_positions = list_length - visible_elements + 1;
        index_position = CommonMath.clamp(index_position, 0, max_positions - 1);
      }
      else{
        scrollbar_height = this.height;
        max_positions = 1;
        index_position = 0;
      }
      // update position_y based on new index value
      max_position_y = y + height - scrollbar_height;
      index_positions = MathUtility.getPositions(y, max_position_y, max_positions);
      position_y = index_positions[index_position];

      // setup
      scrollbar_half_height = scrollbar_height / 2;
      number_of_center_sections = (int)Math.round((double)scrollbar_height / center_ratio);
      if(number_of_center_sections == 1){
        number_of_center_sections = 0;
      }
      scrollbar_gui_height_calc = WidgetUtil.get_half_lengths(scrollbar_height - (number_of_center_sections * center_gui_height));
  
      // set list entries
      updateList();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  @Override
  public void render(int p_render_1_, int p_render_2_, float p_render_3_){
    WidgetUtil.common_button_render_setup(texture);

    // Background
    WidgetUtil.verticalSplitRender(
      new Dimensions(x, y, scrollbar_gui_width, height, scrollbar_gui_width, max_scrollbar_height),
      new Dimensions(scrollbar_background_x, scrollbar_texture_y, scrollbar_texture_width, height*2, scrollbar_texture_width, max_scrollbar_height),
      texture_scale_width, texture_scale_height
    );

    // scrollbar values
    scrollbar_center_y = position_y + scrollbar_gui_height_calc[0];
    scrollbar_bottom_y = position_y + scrollbar_height - scrollbar_gui_height_calc[1];
    
    // Scrollbar
    blit(x, position_y, scrollbar_gui_width, scrollbar_gui_height_calc[0], scrollbar_texture_x, scrollbar_texture_y, scrollbar_texture_width, scrollbar_gui_height_calc[0]*2, texture_scale_width, texture_scale_height);
    blit(x, scrollbar_center_y, scrollbar_gui_width, center_gui_height * number_of_center_sections, scrollbar_center_x, 0, scrollbar_texture_width, scrollbar_center_height * number_of_center_sections, texture_scale_width, texture_scale_height);
    blit(x, scrollbar_bottom_y, scrollbar_gui_width, scrollbar_gui_height_calc[1], scrollbar_texture_x, scrollbar_texture_max_y - scrollbar_gui_height_calc[1]*2, scrollbar_texture_width, scrollbar_gui_height_calc[1]*2, texture_scale_width, texture_scale_height);
  }

  @Override
  protected void onDrag(double gui_x, double gui_y, double screen_x, double screen_y){
    move_scrollbar((int)Math.round(gui_y));
  }

  @SuppressWarnings("deprecation")
  private final void move_scrollbar(final int new_scrollbar_y_position){
    center_y = new_scrollbar_y_position;
    position_y = CommonMath.clamp(center_y - scrollbar_half_height, this.y, max_position_y);
    
    temp_index = MathUtility.getPositionIndex(position_y, index_positions);
    if(temp_index != index_position){
      index_position = temp_index;
      updateList();
    }
  }

  private void updateList(){
    int i;
    int id;
    for(i = 0; i < visible_elements; i++){
      id = index_position + i;
      if(id < list_length){
        list_items[i].set(id, values[id]);
        list_items[i].setSelected(selected);
      }
      else{
        list_items[i].set(-1, "");
        list_items[i].setSelected(-1);
      }
    }
  }

  @Override
  public void onRelease(double p_onRelease_1_, double p_onRelease_3_){
    position_y = index_positions[index_position];
  }

  public void setResponder(final BiConsumer<String, Integer> responder){
    this.onSelected = responder;
  }

  public void unSelect(){
    setSelected(-1, true, false);
  }

  public void setSelected(int list_entry){
    setSelected(list_entry, true, true);
  }
  
  public void setSelected(int list_entry, boolean respond, boolean adjust_scrollbar){
    // MAYBE: responding to selection changes now happens all the time by default, consider removing
    //        the parameter. But I may want to control this in the future, so it's left as-is for now.
    //        Remove this in 2027 if it's no longer necessary.
    selected = list_entry;
    for(final ListEntry e : list_items){
      e.setSelected(selected);
    }
    
    if(respond){
      if(onSelected != null){
        onSelected.accept(getSelected(), selected);
      }
    }
    
    if(adjust_scrollbar){
      scroll_to_value();
    }
  }

  /** Attempt to set this scrollbar's selected index to one of the values in the list. */
  public void setSelected(final String value){
    int i;
    for(i = 0; i < values.length; i++){
      if(values[i].equals(value)){
        setSelected(i);
        return;
      }
    }
    unSelect();
  }

  private final void scroll_to_value(){ // seperate as a function in case we need to call it externally?
    if(hasValidSelection()){
      final double index = (double)selected / values.length;
      move_scrollbar(y + (int)Math.round(index * height));
    }
    else{
      move_scrollbar(y);
    }
  }

  public String getSelected(){
    return hasValidSelection() ? values[selected] : null;
  }

  public int getSelectedIndex(){
    return selected;
  }

  public boolean hasValidSelection(){
    return ArrayUtil.isInsideBounds(selected, values.length);
  }

  @Override
  public void playDownSound(SoundHandler p_playDownSound_1_){
  }

}
