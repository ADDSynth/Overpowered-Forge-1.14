package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.game.core.Init;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum OverpoweredTiers implements IItemTier {
  ENERGY        (4, ItemTier.DIAMOND.getMaxUses()*3, 12.0f, 4.0f, 0, Ingredient.fromItems(Init.energy_crystal)),
  ENERGY_PICKAXE(4, ItemTier.DIAMOND.getMaxUses()*3, 16.0f, 4.0f, 0, Ingredient.fromItems(Init.energy_crystal)),
  ENERGY_SWORD  (4, 1000,                            12.0f, 4.0f, 0, Ingredient.fromItems(Init.energy_crystal)),
  VOID          (4, ItemTier.DIAMOND.getMaxUses()*5, 12.0f, 5.0f, 0, Ingredient.fromItems(Init.void_crystal));
  // MAYBE: Add Unimatter Tools, THESE will have Integer.MAX_VALUE durability.

  private final int harvestLevel;
  private final int maxUses;
  private final float efficiency;
  private final float attackDamage;
  private final int enchantability;
  private final Ingredient repairMaterial;

  private OverpoweredTiers(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, Ingredient repair_item){
    this.harvestLevel = harvestLevel;
    this.maxUses = maxUses;
    this.efficiency = efficiency;
    this.attackDamage = damage;
    this.enchantability = enchantability;
    this.repairMaterial = repair_item;
  }

  @Override
  public int getMaxUses(){ return maxUses; }

  @Override
  public float getEfficiency(){ return efficiency; }

  @Override
  public float getAttackDamage(){ return attackDamage; }

  @Override
  public int getHarvestLevel(){ return harvestLevel; }

  @Override
  public int getEnchantability(){ return enchantability; }

  @Override
  public Ingredient getRepairMaterial(){ return repairMaterial; }
}
