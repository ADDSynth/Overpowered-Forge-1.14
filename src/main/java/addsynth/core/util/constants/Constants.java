package addsynth.core.util.constants;

import addsynth.core.util.color.ColorCode;
import net.minecraft.block.Blocks;

/** @see addsynth.core.items.HarvestLevel
 *  @see addsynth.core.items.ToolConstants
 *  @see addsynth.core.util.command.PermissionLevel
 *  @see addsynth.core.util.world.WorldConstants
 *  @see addsynth.core.util.time.TimeConstants
 *  @see addsynth.core.util.time.WorldTime
 */
public final class Constants {

/** Default error string to show, if attempting to get a status message from TileEntities. */
public static final String null_error = ColorCode.ERROR.toString() + "[Null Error]";

@SuppressWarnings("deprecation")
public static final float block_resistance = Blocks.STONE.getExplosionResistance(); //6.0f; // same as Stone
@SuppressWarnings("deprecation")
public static final float infinite_resistance = Blocks.BEDROCK.getExplosionResistance();

public static final float[] light_level = new float[] { // UNUSED Constants.light_level[]
  0.0f, 1.0f / 15, 2.0f / 15, 3.0f / 15, 4.0f / 15, 5.0f / 15, 6.0f / 15, 7.0f / 15,
  8.0f / 15, 9.0f / 15, 10.0f / 15, 11.0f / 15, 12.0f / 15, 13.0f / 15, 14.0f / 15, 1.0f
};

/** Hostile mobs spawn at light level 7 and lower. */
public static final int MONSTER_SPAWN_LIGHT_LEVEL = 7;
/** Undead mobs burn at light level 12 and above.
 *  @see <a href="https://minecraft.fandom.com/wiki/Light#Effects_of_internal_light">Effects of Light Level</a> */
public static final int UNDEAD_MOB_BURN_LIGHT_LEVEL = 12;
public static final int HOSTILE_MOB_ATTACK_RANGE = 16;
/** Zombies can see players from farther away than other hostile mobs.
 * @see net.minecraft.entity.monster.ZombieEntity#registerAttributes() */
public static final int ZOMBIE_ATTACK_RANGE = 35;

}
