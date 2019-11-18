package addsynth.overpoweredmod.tiles.machines.automatic;

import java.util.Random;
import addsynth.energy.CustomEnergyStorage;
import addsynth.energy.tiles.machines.WorkMachine;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.tiles.Tiles;
import net.minecraft.item.ItemStack;

public final class TileCrystalMatterReplicator extends WorkMachine {

  public TileCrystalMatterReplicator(){
    super(Tiles.CRYSTAL_MATTER_REPLICATOR,0,null,8,new CustomEnergyStorage(Values.crystal_matter_generator_required_energy),Values.crystal_matter_generator_work_time);
  }

  @Override
  protected final void performWork(){
    final Random random = new Random();
    final int slot = random.nextInt(8);
    final ItemStack stack = new ItemStack(Gems.index[slot].shard,1);
    output_inventory.insertItem(slot, stack, false);
  }

}
