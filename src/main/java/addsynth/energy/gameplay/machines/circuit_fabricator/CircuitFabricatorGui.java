package addsynth.energy.gameplay.machines.circuit_fabricator;

import addsynth.core.gui.IngredientWidget;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.util.StringUtil;
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

  private final String selected_text = StringUtil.translate("gui.addsynth_energy.common.selected");

  private static final ItemStack[] circuit_stack = {
    new ItemStack(EnergyItems.circuit_tier_1), new ItemStack(EnergyItems.circuit_tier_2),
    new ItemStack(EnergyItems.circuit_tier_3), new ItemStack(EnergyItems.circuit_tier_4),
    new ItemStack(EnergyItems.circuit_tier_5), new ItemStack(EnergyItems.circuit_tier_6),
    new ItemStack(EnergyItems.circuit_tier_7), new ItemStack(EnergyItems.circuit_tier_8)
  };

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(153, 125, 58, 5, 8, 239);

  private static final int circuit_button_x = 10;
  private static final int circuit_button_y = 54;
  private static final int spacing = 2;

  private static final int down_arrow_texture_x = 228;
  private static final int down_arrow_texture_y = 201;
  private static final int up_arrow_texture_x = 228;
  private static final int up_arrow_texture_y = 217;
  private static final int[] arrow_draw_x = {77, 95, 113, 131};
  private static final int[] arrow_draw_y = {65, 113};
  private static final int[] ingredient_draw_x = {76, 94, 112, 130};
  private static final int[] ingredient_draw_y = {48, 122};
  
  private static IngredientWidget[] recipe_ingredient;
  private static int tick;
  
  public CircuitFabricatorGui(final CircuitFabricatorContainer container, PlayerInventory player_inventory, ITextComponent title){
    super(221, 233, container, player_inventory, title, gui_texture);
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
        if(circuit_id < 8){
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
  public final void tick(){
    tick += 1; // TODO: another spot for a tick handler.
    if(tick >= TimeConstants.ticks_per_second){
      for(IngredientWidget w : recipe_ingredient){
        w.update();
      }
      tick = 0;
    }
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    work_progress_bar.draw(this, tile);
    // draw arrows and ingredients
    if(recipe_ingredient != null){
      if(recipe_ingredient.length >= 1){
        guiUtil.draw(arrow_draw_x[0], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[0].draw(guiLeft + ingredient_draw_x[0], guiTop + ingredient_draw_y[0]);
      }
      if(recipe_ingredient.length >= 2){
        guiUtil.draw(arrow_draw_x[1], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[1].draw(guiLeft + ingredient_draw_x[1], guiTop + ingredient_draw_y[0]);
      }
      if(recipe_ingredient.length >= 3){
        guiUtil.draw(arrow_draw_x[2], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[2].draw(guiLeft + ingredient_draw_x[2], guiTop + ingredient_draw_y[0]);
      }
      if(recipe_ingredient.length >= 4){
        guiUtil.draw(arrow_draw_x[3], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[3].draw(guiLeft + ingredient_draw_x[3], guiTop + ingredient_draw_y[0]);
      }
      if(recipe_ingredient.length >= 5){
        guiUtil.draw(arrow_draw_x[0], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[4].draw(guiLeft + ingredient_draw_x[0], guiTop + ingredient_draw_y[1]);
      }
      if(recipe_ingredient.length >= 6){
        guiUtil.draw(arrow_draw_x[1], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[5].draw(guiLeft + ingredient_draw_x[1], guiTop + ingredient_draw_y[1]);
      }
      if(recipe_ingredient.length >= 7){
        guiUtil.draw(arrow_draw_x[2], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[6].draw(guiLeft + ingredient_draw_x[2], guiTop + ingredient_draw_y[1]);
      }
      if(recipe_ingredient.length >= 8){
        guiUtil.draw(arrow_draw_x[3], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
        recipe_ingredient[7].draw(guiLeft + ingredient_draw_x[3], guiTop + ingredient_draw_y[1]);
      }
    }
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy_usage();
    draw_status(tile.getStatus());
    GuiUtil.draw_text_left(selected_text+": "+tile.getCircuitSelected(), 6, 39);
    // GuiUtil.drawItemStack(circuit_stack[tile.getCircuitID()], 102, 29);
    GuiUtil.draw_text_center(work_progress_bar.getWorkTimeProgress(), 184, 113);
    draw_time_left(140);
  }

  @Override
  protected final void renderHoveredToolTip(int mouseX, int mouseY){
    super.renderHoveredToolTip(mouseX, mouseY);
    // draw ingredient tooltips
    if(recipe_ingredient != null){
      if(recipe_ingredient.length >= 1){
        recipe_ingredient[0].drawTooltip(this, guiLeft + ingredient_draw_x[0], guiTop + ingredient_draw_y[0], mouseX, mouseY);
      }
      if(recipe_ingredient.length >= 2){
        recipe_ingredient[1].drawTooltip(this, guiLeft + ingredient_draw_x[1], guiTop + ingredient_draw_y[0], mouseX, mouseY);
      }
      if(recipe_ingredient.length >= 3){
        recipe_ingredient[2].drawTooltip(this, guiLeft + ingredient_draw_x[2], guiTop + ingredient_draw_y[0], mouseX, mouseY);
      }
      if(recipe_ingredient.length >= 4){
        recipe_ingredient[3].drawTooltip(this, guiLeft + ingredient_draw_x[3], guiTop + ingredient_draw_y[0], mouseX, mouseY);
      }
      if(recipe_ingredient.length >= 5){
        recipe_ingredient[4].drawTooltip(this, guiLeft + ingredient_draw_x[0], guiTop + ingredient_draw_y[1], mouseX, mouseY);
      }
      if(recipe_ingredient.length >= 6){
        recipe_ingredient[5].drawTooltip(this, guiLeft + ingredient_draw_x[1], guiTop + ingredient_draw_y[1], mouseX, mouseY);
      }
      if(recipe_ingredient.length >= 7){
        recipe_ingredient[6].drawTooltip(this, guiLeft + ingredient_draw_x[2], guiTop + ingredient_draw_y[1], mouseX, mouseY);
      }
      if(recipe_ingredient.length >= 8){
        recipe_ingredient[7].drawTooltip(this, guiLeft + ingredient_draw_x[3], guiTop + ingredient_draw_y[1], mouseX, mouseY);
      }
    }
  }

}
