package addsynth.core;

public final class Constants {

public static final int world_height = 256;
public static final int sea_level = 63;

public static final int ticks_per_second = 20;
public static final int ticks_per_minute = ticks_per_second * 60;
public static final int ticks_per_hour = ticks_per_minute * 60;
public static final int ticks_per_day = ticks_per_hour * 24;

@SuppressWarnings("deprecation")
public static final float block_resistance = net.minecraft.block.Blocks.STONE.getExplosionResistance(); //6.0f; // same as Stone

public static final int   sword_damage   = 3;
public static final float shovel_damage  = 1.5f;
public static final int   pickaxe_damage = 1;
public static final float axe_damage     = 6.0f;

// for hoes, the speed depends on the Material, so check the speeds set in the Items class and make your best judgement.
public static final float sword_speed   = -2.4f;
public static final float pickaxe_speed = -2.8f;
public static final float shovel_speed  = -3.0f;
public static final float axe_speed     = -3.2f;

}
