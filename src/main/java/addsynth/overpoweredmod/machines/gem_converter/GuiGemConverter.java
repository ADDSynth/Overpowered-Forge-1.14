package addsynth.overpoweredmod.machines.gem_converter;

import addsynth.core.gui.objects.AdjustableButton;
import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.core.Gems;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiGemConverter extends GuiEnergyBase<TileGemConverter, ContainerGemConverter> {

  private static final ResourceLocation gem_converter_gui_texture = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/gem_converter.png");

  private static final ItemStack[] gem = new ItemStack[] {
    new ItemStack(Gems.ruby, 1),
    new ItemStack(Gems.topaz, 1),
    new ItemStack(Gems.citrine, 1),
    new ItemStack(Gems.emerald, 1),
    new ItemStack(Gems.diamond, 1),
    new ItemStack(Gems.sapphire, 1),
    new ItemStack(Gems.amethyst, 1),
    new ItemStack(Gems.quartz, 1)
  };

  private float work_float;
  private int work_percentage;

  private static final int work_percentage_x = 25;
  private static final int work_percentage_y = 88;
  private static final int time_left_y = 99;

  private static final int left_button_x = 64;
  private static final int cycle_button_y = 64;
  private static final int cycle_button_width = 10;
  private static final int cycle_button_buffer = 2;
  private static final int right_button_x = left_button_x + cycle_button_width + 16 + (cycle_button_buffer*2);
  private static final int cycle_button_height = 16;
  
  private final ProgressBar work_progress_bar = new ProgressBar(43, 89, 122, 5, 40, 199);
  
  public GuiGemConverter(final ContainerGemConverter container, final PlayerInventory player_inventory, final ITextComponent title){
    super(-1, 194, container, player_inventory, title, gem_converter_gui_texture);
  }

  private static final class CycleGemButton extends AdjustableButton {

    private final TileGemConverter tile;
    private final boolean direction;

    public CycleGemButton(int xIn, int yIn, boolean direction, TileGemConverter tile){
      super(xIn, yIn, cycle_button_width, cycle_button_height, direction ? ">" : "<"); // true goes right
      this.tile = tile;
      this.direction = direction;
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new CycleGemConverterMessage(tile.getPos(),direction));
    }

  }

  @Override
  public final void init(){
    super.init();
    addButton(new CycleGemButton(this.guiLeft + left_button_x, this.guiTop + cycle_button_y,false, tile));
    addButton(new CycleGemButton(this.guiLeft + right_button_x, this.guiTop + cycle_button_y,true, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY){
    draw_background_texture();
    
    work_float = tile.getWorkTimePercentage();
    work_percentage = (int)Math.floor(work_float*100);
    work_progress_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.LEFT_TO_RIGHT,work_float,ProgressBar.Round.FLOOR);
    
    drawItemStack(gem[tile.get_gem_selected()], this.guiLeft + left_button_x + 12, this.guiTop + cycle_button_y);
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    super.draw_title();
    draw_energy_usage();
    draw_status(tile.getStatus());
    
    final ItemStack s1 = tile.getWorkingInventory().getStackInSlot(0);
    if(Config.blend_working_item.get()){
      final ItemStack s2 = gem[tile.getConvertingStack()];
      blendItemStacks(s1, s2, 76, 45, work_float);
    }
    else{
      drawItemStack(s1, 76, 45);
    }
    
    draw_text_center(work_percentage + "%", work_percentage_x, work_percentage_y);
    draw_time_left(tile, time_left_y);
  }

}
