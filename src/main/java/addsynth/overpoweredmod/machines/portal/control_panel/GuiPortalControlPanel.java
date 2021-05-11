package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.gui.widgets.buttons.Checkbox;
import addsynth.core.util.StringUtil;
import addsynth.energy.gui.GuiEnergyBase;
import addsynth.energy.gui.widgets.EnergyProgressBar;
import addsynth.energy.gui.widgets.OnOffSwitch;
import addsynth.energy.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredMod;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.ToggleAutoShutoffMessage;
import addsynth.overpoweredmod.game.core.Gems;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public final class GuiPortalControlPanel extends GuiEnergyBase<TilePortalControlPanel, ContainerPortalControlPanel> {

  private static final ResourceLocation portal_control_panel_gui_texture =
    new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/portal_control_panel.png");

  // It's efficient to create each ItemStack only ONCE when the gui is constructed then pass it as a reference when drawing the ItemStack.
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

  private static final int checkbox_x = 70;
  private static final int checkbox_y = 19;

  private static final int energy_percentage_y = 45;
  private static final int energy_change_y = 56;

  private final EnergyProgressBar energy_bar = new EnergyProgressBar(177, 56, 17, 63, 211, 24);

  private static final ResourceLocation gui_icons = new ResourceLocation(OverpoweredMod.MOD_ID,"textures/gui/gui_textures.png");
  private static final int image_x = 14;
  private static final int image_y = 67;
  private static final int space_x = 40;
  private static final int space_y = 18;

  private static final int button_x = 22;
  private static final int button_y = 104;
  private static final int button_width = 136;
  private static final int button_height = 16;
  
  private static final int status_message_y = button_y + button_height + 6;

  // TODO: record which player has this gui open so you can award them with the achievement when they
  //          activate the portal.

  public GuiPortalControlPanel(final ContainerPortalControlPanel container, final PlayerInventory player_inventory, final ITextComponent title){
    super(202, 144, container, player_inventory, title, portal_control_panel_gui_texture);
  }

  private static final class GeneratePortalButton extends AdjustableButton {

    private final TilePortalControlPanel tile;

    public GeneratePortalButton(final int x, final int y, final TilePortalControlPanel tile){
      super(x, y, button_width, button_height, StringUtil.translate("gui.overpowered.portal_control_panel.generate_portal"));
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

  private static final class ToggleAutoShutoff extends Checkbox {
  
    private final TilePortalControlPanel tile;
    
    public ToggleAutoShutoff(int x, int y, TilePortalControlPanel tile){
      super(x, y, StringUtil.translate("gui.addsynth_energy.common.auto_shutoff"));
      this.tile = tile;
    }
    
    @Override
    protected boolean get_toggle_state(){
      return tile.getAutoShutoff();
    }
    
    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new ToggleAutoShutoffMessage(tile.getPos()));
    }
  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch<>(this.guiLeft + 6, this.guiTop + 19, tile));
    addButton(new ToggleAutoShutoff(this.guiLeft + checkbox_x, this.guiTop + checkbox_y, tile));
    addButton(new GeneratePortalButton(this.guiLeft + button_x, this.guiTop + button_y, tile));
  }

  @Override
  protected final void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture();
    energy_bar.drawVertical(this, energy);
    draw_portal_items();
  }

  @Override
  protected final void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY){
    guiUtil.draw_title(this.title);
    draw_energy(44, 34);
    draw_status(tile.getStatus(), energy_percentage_y);
    guiUtil.draw_text_right(WorkProgressBar.getWorkTimeProgress(tile), energy_percentage_y);
    draw_energy_difference(energy_change_y);
    guiUtil.draw_text_center(tile.getMessage(), status_message_y);
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
        GuiUtil.drawItemStack(gem_block[index], x, y);
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
