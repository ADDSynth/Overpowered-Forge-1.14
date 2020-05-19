package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.gui.objects.AdjustableButton;
import addsynth.core.gui.objects.ProgressBar;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.gui.widgets.OnOffSwitch;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.core.Gems;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiPortalControlPanel extends GuiEnergyBase<TilePortalControlPanel, ContainerPortalControlPanel> {

  /**
   * It's efficient to create each ItemStack only ONCE when the gui is constructed then pass it as a reference when drawing the ItemStack.
   */
  private static final ItemStack[] gem_block = new ItemStack[] {
    new ItemStack(Gems.RUBY.block_item,1),
    new ItemStack(Gems.TOPAZ.block_item,1),
    new ItemStack(Gems.CITRINE.block_item,1),
    new ItemStack(Gems.EMERALD.block_item,1),
    new ItemStack(Gems.DIAMOND.block_item,1),
    new ItemStack(Gems.SAPPHIRE.block_item,1),
    new ItemStack(Gems.AMETHYST.block_item,1),
    new ItemStack(Gems.QUARTZ.block_item,1)
  };

  private static final int energy_bar_x = 167;
  private static final int energy_bar_y = 34;
  private static final int energy_bar_width = 17;
  private static final int energy_bar_height = 46;
  private static final int energy_bar_draw_x = 206;
  private static final int energy_bar_draw_y = 24;
  private final ProgressBar energy_bar = new ProgressBar(energy_bar_x,energy_bar_y,energy_bar_width,energy_bar_height,energy_bar_draw_x,energy_bar_draw_y);

  private static final ResourceLocation gui_icons = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/gui_textures.png");
  private static final int image_x = 15;
  private static final int image_y = 47;
  private static final int space_x = 36;
  private static final int space_y = 18;

  private static final int button_x = 17;
  private static final int button_y = 84;
  private static final int button_width = 136;
  private static final int button_height = 16;
  
  private static final int status_message_y = button_y + button_height + 6;

  // TODO: record which player has this gui open so you can award them with the achievement when they
  //          activate the portal.

  public GuiPortalControlPanel(final ContainerPortalControlPanel container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/portal_control_panel.png"));
    this.xSize = 192;
    this.ySize = 124;
  }

  private static final class GeneratePortalButton extends AdjustableButton {

    private final TilePortalControlPanel tile;

    public GeneratePortalButton(final int x, final int y, final TilePortalControlPanel tile){
      super(x, y, button_width, button_height, "Generate Portal");
      this.tile = tile;
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      this.active = tile.isValid();
      super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new GeneratePortalMessage(tile.getPos()));
    }

  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch(this.guiLeft + 6, this.guiTop + 17, tile));
    addButton(new GeneratePortalButton(this.guiLeft + button_x, this.guiTop + button_y, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    draw_background_texture();
    // energy_bar.draw(this,this.guiLeft,this.guiTop,ProgressBar.Direction.BOTTOM_TO_TOP,tile.getEnergyPercentage(),ProgressBar.Round.NEAREST);
    draw_portal_items();
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    super.draw_title();
    super.draw_energy_after_switch();
    draw_energy_difference(36);
    draw_text_center(tile.getMessage(),status_message_y);
  }
  
  /**
   * This is used to show non-interactable slots on the Gui showing each of the 8 gem blocks, and shows a
   * check mark or X by each of them indicating if the portal frame has it or not.
   */
  private final void draw_portal_items(){
    // GuiContainer class -> drawScreen() method -> Line 100
    // GuiContainer class -> drawSlot() method -> Line 298
    int i;
    int j;
    int index;
    int x;
    int y;
    RenderHelper.enableGUIStandardItemLighting();
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.guiLeft + image_x + (i * space_x);
        y = this.guiTop + image_y + (j * space_y);
        drawItemStack(gem_block[index], x, y);
      }
    }
    minecraft.getTextureManager().bindTexture(gui_icons);
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.guiLeft + image_x + (i * space_x) + 16;
        y = this.guiTop + image_y + (j * space_y);
        if(tile.getPortalItem(index)){
          blit(x, y, 64, 0, 16, 16);
        }
        else{
          blit(x, y, 80, 0, 16, 16);
        }
      }
    }
  }

}
