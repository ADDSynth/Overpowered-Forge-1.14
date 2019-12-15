package addsynth.core.material.blocks;

import java.util.Random;
import addsynth.core.material.MiningStrength;
import addsynth.core.util.MathUtility;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public final class ItemOreBlock extends OreBlock {

  private Item itemdrop;
  private static final int number_of_drops = 1;

	public ItemOreBlock(final String name, final Item itemdrop, final MiningStrength strength){
      super(name, itemdrop, strength);
      this.itemdrop = itemdrop;
	}

    @Override
    public final Item getItemDropped(final IBlockState state, final Random rand, final int fortune){
      if(itemdrop == null){
        return Item.getItemFromBlock(this);
      }
      return itemdrop;
    }

    @Override
    public final int quantityDropped(Random random){
        return number_of_drops;
    }

    @Override
    public final int quantityDroppedWithBonus(int fortune, Random random){
      if (fortune > 0 && this.getItemDropped(this.getDefaultState(), random, fortune) != Item.getItemFromBlock(this)) {
        // links:
        // https://minecraft.gamepedia.com/Enchanting#Fortune
        // https://www.suppergerrie2.com/minecraft-1-12-modding-with-forge-5-custom-ore-block/
        // https://github.com/suppergerrie2/ForgeTutorial/blob/master/src/main/java/com/suppergerrie2/tutorial/blocks/BlockOre.java
        int drop_number = number_of_drops;
        switch(fortune){
        case 1:
          if(random.nextInt(3) == 0){            // 1 67%, 2 33%
            drop_number = 2;
          }
          break;
        case 2:
          int random_number = random.nextInt(5); // 1 40%, 2 60%
          if(random_number != 0 && random_number != 1){
            drop_number = 2;
          }
          break;
        case 3:
          switch(random.nextInt(8)){             // 1 25%, 2 65%, 3 10%
          case 0: case 1: break;
          default: drop_number = 2; break;
          case 7: drop_number = 3; break;
          }
          break;
        }
        return drop_number;
      }
      return this.quantityDropped(random);
    }

  @Override
  public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune){
    if(getItemDropped(state, null, fortune) != Item.getItemFromBlock(this)){
      return MathUtility.RandomRange(3, 7);
    }
    return 0;
  }

}
