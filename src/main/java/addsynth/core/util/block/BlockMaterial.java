package addsynth.core.util.block;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

/**
 * <p>When constructing blocks, one the block properties you must specify is the Block Material.</p>
 * <p>Vanilla has many predefined Block Materials in the {@link Material} class, so if you see
 *    one that matches the properties you want, go ahead and use it.</p>
 * <p>But, disregarding the {@link MaterialColor}, Some vanilla Materials may have the same attributes,
 *    but are still differentiated. For example, <code>Material.PACKED_ICE</code>,
 *    <code>Material.SAND</code>, and <code>Material.SPONGE</code> have the same attributes, but are
 *    different materials. Some mods can do different actions based on the material of blocks.
 *    For example, the Dynamic Surroundings mod plays different sounds depending on the type of block
 *    the player is walking on. So choose the Material that best describes your block.</p>
 * <p>If you want a Block Material that is not predefined by vanilla, then you'll have to make
 *    your own, which often is the case anyways. But if you make your own custom Material, mods
 *    will be able to check the Material attributes, but won't be able to tell your Material apart
 *    from another. In this case, Dynamic Surroundings would just play generic block sounds if
 *    your player walks over a custom Material. If you want your Material to be 'recognized' by
 *    other mods, then you'll have to use a vanilla Material.</p>
 * <p>Side Note: If the mod does treat blocks with different materials differently, then you may be
 *    able to register your Block Material with that mod, so it knows how to treat it.</p>
 * <p>This class is mainly used to debug the vanilla Materials. We DO NOT RECOMMEND using this to
 *    define your custom Block Materials, please do that manually yourself!</p>
 * 
 * @author ADDSynth
 */
public final class BlockMaterial {

  private static Material[] vanilla_materials;
  private static String[]   name;

  static {
    // First-time initialization of the vanilla materials list.
    try{
      ArrayList<Field> fields = new ArrayList<>();

      for(Field field : Material.class.getFields()){
        if(Modifier.isStatic(field.getModifiers()) && field.getType() == Material.class){
          fields.add(field);
        }
      }

      int i;
      final int max = fields.size();
      vanilla_materials = new Material[max];
      name = new String  [max];

      for(i = 0; i < max; i++){
        vanilla_materials[i] = (Material)(fields.get(i).get(null));
        name[i] = fields.get(i).getName();
      }
    }
    catch(Exception e){
      vanilla_materials = null;
      name = null;
      e.printStackTrace();
    }
  }

  // /** Piston pushes and pulls block. */
  // public static final int PISTON_PUSH_NORMAL = 0;
  /** Piston destroys block and optionally drops as an item. */
  public static final int PISTON_PUSH_DESTROYS = 1;
  /** Cannot be pushed or pulled by pistons. */
  public static final int IMMOVEABLE = 2;
  /** Special case. Not affected by Pistons. */
  public static final int IGNORE_PISTON = 4; // Pistons have no effect, only Area Effect Cloud entity uses this
  public static final int PISTON_PUSH_ONLY = 8; // Glazed Terracotta
  public static final int FLUID = 16;
  /** Is full block? */
  public static final int SOLID = 32;
  /** Solid to entities? */
  public static final int BLOCKS_MOVEMENT = 64;
  public static final int REPLACEABLE = 128;
  public static final int REQUIRES_TOOL = 256;
  public static final int TRANSPARENT = 512;
  public static final int FLAMMABLE = 1024;

  private static final boolean debug = false;
  private static final boolean use_vanilla = true;

  public static final Material get(final int flags){
    return get(MaterialColor.AIR, flags);
  }

  public static final Material get(final MaterialColor block_color, int flags){
    // if(vanilla_materials == null){
    //   init();
    // }
  
    // Check we're not using more than 1 Piston flag
    int piston_flags = flags & 15;
    if(piston_flags != PISTON_PUSH_DESTROYS && piston_flags != IMMOVEABLE &&
       piston_flags != IGNORE_PISTON        && piston_flags != PISTON_PUSH_ONLY){
      ADDSynthCore.log.error(new IllegalArgumentException("Cannot combine multiple Piston PushReaction flags. Only 1 piston flag can be used at a time."));
      flags >>= 4;
      flags <<= 4;
      piston_flags = flags & 15;
    }
    
    // Check vanilla Materials and return a match
    final Material[] materials = getMatchingVanillaMaterials(flags);
    if(materials != null){
      if(materials.length > 0){
        if(materials.length > 1){
          if(debug){
            print_matching_materials(flags, materials);
          }
        }
        if(use_vanilla){
          return materials[0];
        }
      }
    }
    
    // Build custom material
    if(debug){
      ADDSynthCore.log.info("Built custom Block Material.");
    }
    final boolean fluid          = (flags & FLUID) > 0;
    final boolean solid          = (flags & SOLID) > 0;
    final boolean block_movement = (flags & BLOCKS_MOVEMENT) > 0;
    final boolean opaque         = (flags & TRANSPARENT) == 0;
    final boolean requires_tool  = (flags & REQUIRES_TOOL) > 0;
    final boolean flammable      = (flags & FLAMMABLE) > 0;
    final boolean replaceable    = (flags & REPLACEABLE) > 0;
    PushReaction push_reaction = PushReaction.NORMAL;
      if((piston_flags & PISTON_PUSH_DESTROYS) > 0){ push_reaction = PushReaction.DESTROY; }
      if((piston_flags & IMMOVEABLE) > 0){           push_reaction = PushReaction.BLOCK; }
      if((piston_flags & IGNORE_PISTON) > 0){        push_reaction = PushReaction.IGNORE; }
      if((piston_flags & PISTON_PUSH_ONLY) > 0){     push_reaction = PushReaction.PUSH_ONLY; }
    return new Material(block_color, fluid, solid, block_movement, opaque, !requires_tool, flammable, replaceable, push_reaction);
  }



  private static final Material[] getMatchingVanillaMaterials(final int flags){
    if(vanilla_materials == null){
      ADDSynthCore.log.error(new NullPointerException("Cannot do any Block Material utility functions at this time due to an error in static initialization."));
      return null;
    }
    // Match all materials against flags
    ArrayList<Material> final_list = new ArrayList<>();
    for(Material material : vanilla_materials){
      if(((flags & PISTON_PUSH_DESTROYS) > 0) ^ (material.getPushReaction() == PushReaction.DESTROY)){
        continue;
      }
      if(((flags & IMMOVEABLE) > 0) ^ (material.getPushReaction() == PushReaction.BLOCK)){
        continue;
      }
      if(((flags & IGNORE_PISTON) > 0) ^ (material.getPushReaction() == PushReaction.IGNORE)){
        continue;
      }
      if(((flags & PISTON_PUSH_ONLY) > 0) ^ (material.getPushReaction() == PushReaction.PUSH_ONLY)){
        continue;
      }
      if(((flags & FLUID) > 0) ^ material.isLiquid()){
        continue;
      }
      if(((flags & SOLID) > 0) ^ material.isSolid()){
        continue;
      }
      if(((flags & BLOCKS_MOVEMENT) > 0) ^ material.blocksMovement()){
        continue;
      }
      if(((flags & REPLACEABLE) > 0) ^ material.isReplaceable()){
        continue;
      }
      if(((flags & REQUIRES_TOOL) > 0) ^ (material.isToolNotRequired() == false)){
        continue;
      }
      if(((flags & TRANSPARENT) > 0) ^ (material.isOpaque() == false)){
        continue;
      }
      if(((flags & FLAMMABLE) > 0) ^ material.isFlammable()){
        continue;
      }
      final_list.add(material); // compile a list of all matching materials.
    }
    return final_list.toArray(new Material[final_list.size()]);
  }

  private static final void print_matching_materials(final int flags, final Material[] matched){
    int i;
    int j;
    final StringBuilder s = new StringBuilder("Found multiple vanilla Materials that match Block Material ID code "+flags+": ");
    for(i = 0; i < matched.length; i++){
      for(j = 0; j < vanilla_materials.length; j++){
        if(matched[i] == vanilla_materials[j]){
          s.append(name[j]);
          break;
        }
      }
      if(i + 1 < matched.length){
        s.append(", ");
      }
    }
    ADDSynthCore.log.info(s.toString());
  }

}
