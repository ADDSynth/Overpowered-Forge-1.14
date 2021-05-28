package addsynth.energy.gameplay.machines.circuit_fabricator;

import addsynth.core.gui.IngredientWidget;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.util.time.TimeConstants;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class CircuitFabricatorGui extends GuiEnergyBase<TileCircuitFabricator, CircuitFabricatorContainer> {

  private static final ResourceLocation gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID, "textures/gui/circuit_fabricator.png");

  private static final ItemStack[] circuit_stack = {
    new ItemStack(EnergyItems.circuit_tier_1), new ItemStack(EnergyItems.circuit_tier_2),
    new ItemStack(EnergyItems.circuit_tier_3), new ItemStack(EnergyItems.circuit_tier_4),
    new ItemStack(EnergyItems.circuit_tier_5), new ItemStack(EnergyItems.circuit_tier_6),
    new ItemStack(EnergyItems.circuit_tier_7), new ItemStack(EnergyItems.circuit_tier_7)
  };

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(153, 114, 58, 5, 8, 228);

  private static final int circuit_button_x = 10;
  private static final int circuit_button_y = 43;
  private static final int spacing = 2;

  private static final int down_arrow_x = 0;
  private static final int up_arrow_x = 28;
  private static final int arrow_y = 240;
  private static final int arrow_draw_x = 77;
  private static final int arrow_draw_y1 = 54;
  private static final int arrow_draw_y2 = 102;
  
  private static IngredientWidget[] recipe_ingredient;
  private static int tick;
  
  public CircuitFabricatorGui(final CircuitFabricatorContainer container, PlayerInventory player_inventory, ITextComponent title){
    super(221, 222, container, player_inventory, title, gui_texture);
    tile.updateGui();
  }

  private final class CircuitButton extends AbstractButton {

    private final ResourceLocation button_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID, "textures/gui/gui_textures.png");
    public static final int button_size = 18;
    private static final int button_texture_size = 36;
    // private final TileCircuitFabricator tile;
    // private final ItemStack circuit;
    private final int circuit_id;

    public CircuitButton(int x, int y, TileCircuitFabricator tile, int circuit_id){
      super(x, y, button_size, button_size, "");
      // this.circuit = new ItemStack(EnergyItems.circuit[circuit_id], 1);
      this.circuit_id = circuit_id;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      WidgetUtil.renderButton(this, button_texture, 64, 80, button_size, button_size, button_texture_size, button_texture_size);
      GuiUtil.drawItemStack(circuit_stack[circuit_id], x+1, y+1);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new ChangeCircuitCraftType(tile.getPos(), circuit_id));
    }
  }

  @Override
  protected final void init(){
    super.init();
    
    // circuit buttons
    int x;
    int y;
    int circuit_id;
    for(y = 0; y < 3; y++){
      for(x = 0; x < 3; x++){
        circuit_id = x + (y * 3);
        if(circuit_id < 7){
          addButton(new CircuitButton(
            guiUtil.guiLeft + circuit_button_x + (x * (CircuitButton.button_size + spacing)),
            guiUtil.guiTop  + circuit_button_y + (y * (CircuitButton.button_size + spacing)), this.tile, circuit_id
          ));
        }
      }
    }
    
    tile.updateGui();
  }

  public static final void updateRecipeDisplay(final ItemStack[][] recipe){
    int i;
    int length = recipe.length;
    recipe_ingredient = new IngredientWidget[length];
    for(i = 0; i < length; i++){
      recipe_ingredient[i] = new IngredientWidget(recipe[i]);
    }
  }

  @Override
  public void tick(){
    tick += 1; // TODO: another spot for a tick handler.
    if(tick >= TimeConstants.ticks_per_second){
      for(IngredientWidget w : recipe_ingredient){
        w.update();
      }
      tick = 0;
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
    // draw arrows
    if(recipe_ingredient != null){
      if(recipe_ingredient.length >= 1){ guiUtil.draw(arrow_draw_x,      arrow_draw_y1, down_arrow_x, arrow_y, 14, 8, 28, 16); }
      if(recipe_ingredient.length >= 2){ guiUtil.draw(arrow_draw_x + 18, arrow_draw_y1, down_arrow_x, arrow_y, 14, 8, 28, 16); }
      if(recipe_ingredient.length >= 3){ guiUtil.draw(arrow_draw_x + 36, arrow_draw_y1, down_arrow_x, arrow_y, 14, 8, 28, 16); }
      if(recipe_ingredient.length >= 4){ guiUtil.draw(arrow_draw_x + 54, arrow_draw_y1, down_arrow_x, arrow_y, 14, 8, 28, 16); }
      if(recipe_ingredient.length >= 5){ guiUtil.draw(arrow_draw_x,      arrow_draw_y2,   up_arrow_x, arrow_y, 14, 8, 28, 16); }
      if(recipe_ingredient.length >= 6){ guiUtil.draw(arrow_draw_x + 18, arrow_draw_y2,   up_arrow_x, arrow_y, 14, 8, 28, 16); }
      if(recipe_ingredient.length >= 7){ guiUtil.draw(arrow_draw_x + 36, arrow_draw_y2,   up_arrow_x, arrow_y, 14, 8, 28, 16); }
      if(recipe_ingredient.length >= 8){ guiUtil.draw(arrow_draw_x + 54, arrow_draw_y2,   up_arrow_x, arrow_y, 14, 8, 28, 16); }
    }
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    GuiUtil.draw_text_left("Selected: Circuit Tier "+(tile.getCircuitID()+1), 6, 28);
    // GuiUtil.drawItemStack(circuit_stack[tile.getCircuitID()], 102, 29);
    // draw ingredients
    if(recipe_ingredient != null){
      if(recipe_ingredient.length >= 1){ recipe_ingredient[0].draw(arrow_draw_x -  1, arrow_draw_y1 - 17); }
      if(recipe_ingredient.length >= 2){ recipe_ingredient[1].draw(arrow_draw_x + 17, arrow_draw_y1 - 17); }
      if(recipe_ingredient.length >= 3){ recipe_ingredient[2].draw(arrow_draw_x + 35, arrow_draw_y1 - 17); }
      if(recipe_ingredient.length >= 4){ recipe_ingredient[3].draw(arrow_draw_x + 53, arrow_draw_y1 - 17); }
      if(recipe_ingredient.length >= 5){ recipe_ingredient[4].draw(arrow_draw_x -  1, arrow_draw_y2 + 9); }
      if(recipe_ingredient.length >= 6){ recipe_ingredient[5].draw(arrow_draw_x + 17, arrow_draw_y2 + 9); }
      if(recipe_ingredient.length >= 7){ recipe_ingredient[6].draw(arrow_draw_x + 35, arrow_draw_y2 + 9); }
      if(recipe_ingredient.length >= 8){ recipe_ingredient[7].draw(arrow_draw_x + 53, arrow_draw_y2 + 9); }
    }
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), 184, 102);
    draw_time_left(129);
  }

}
