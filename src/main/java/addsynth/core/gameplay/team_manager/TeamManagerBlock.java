package addsynth.core.gameplay.team_manager;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.client.GuiProvider;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.constants.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public final class TeamManagerBlock extends Block {

  public TeamManagerBlock(){
    super(Block.Properties.create(Material.ROCK, MaterialColor.IRON).sound(SoundType.STONE).hardnessAndResistance(2.0f, Constants.block_resistance));
    ADDSynthCore.registry.register_block(this, "team_manager", new Item.Properties().group(ADDSynthCore.creative_tab));
  }

  @Override
  @SuppressWarnings({ "deprecation" })
  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
    if(world.isRemote){
      if(player.hasPermissionLevel(PermissionLevel.COMMANDS)){
        GuiProvider.openTeamManagerGui(this);
      }
      else{
        player.sendMessage(new TranslationTextComponent("gui.addsynthcore.team_manager.you_do_not_have_permission", PermissionLevel.COMMANDS));
      }
    }
    return true;
  }

}
