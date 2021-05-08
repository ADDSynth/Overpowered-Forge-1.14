package addsynth.core.gameplay.team_manager;

import addsynth.core.ADDSynthCore;
import addsynth.core.Constants;
import addsynth.core.gameplay.team_manager.gui.TeamManagerGui;
import addsynth.core.util.color.ColorCode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public final class TeamManagerBlock extends Block {

  private static final int required_permission_level = 2;

  public TeamManagerBlock(){
    super(Block.Properties.create(Material.ROCK, MaterialColor.IRON).sound(SoundType.STONE).hardnessAndResistance(2.0f, Constants.block_resistance));
    ADDSynthCore.registry.register_block(this, "team_manager", new Item.Properties().group(ADDSynthCore.creative_tab));
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
    if(world.isRemote){
      if(player.hasPermissionLevel(required_permission_level)){
        Minecraft.getInstance().displayGuiScreen(new TeamManagerGui());
      }
      else{
        player.sendMessage(new StringTextComponent(ColorCode.ERROR+"You need command permission level "+ColorCode.WHITE+required_permission_level+ColorCode.ERROR+" or higher to access the Team Manager."));
      }
    }
    return true;
  }

}
