package addsynth.core.util;

import java.util.ArrayList;
import addsynth.core.ADDSynthCore;
import addsynth.core.util.MinecraftUtility;
import net.minecraft.block.Block;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

@Deprecated
public final class RecipeUtil {

  // TODO: My cool RecipeUtil class had a feature that checked each ingredient to a recipe to see if an
  //       OreDictionary tag existed that I could use to allow blocks and items from other mods to be
  //       used in the recipe. I wonder if I can still do this in 1.14, check the ingredients and print
  //       a warning to the log saying there is a Tag I can use instead of the hard-coded block or item.

  // PRIORITY: Actually, I may need to just go ahead and specify Recipe.json files, because I see no way
  //           to register recipes using the old way anymore. I don't see an entry in ForgeRegistries.

  @Deprecated
  public static final boolean debug_recipes = false;

  public static final IRecipe create(final Block block, final Object ... ingredients){
    return create("", new ItemStack(block,1), ingredients);
  }
  
  public static final IRecipe create(final Item item, final Object ... ingredients){
    return create("", new ItemStack(item,1), ingredients);
  }
  
  public static final IRecipe create(final ItemStack result, final Object ... ingredients){
    return new ShapelessRecipes("", result, getIngredients(result.toString(), ingredients));
  }
  
  public static final IRecipe create(final int width, final int height, final Block block, final Object ... ingredients){
    return create("", width, height, new ItemStack(block,1), ingredients);
  }
  
  public static final IRecipe create(final int width, final int height, final Item item, final Object ... ingredients){
    return create("", width, height, new ItemStack(item,1), ingredients);
  }
  
  public static final IRecipe create(final int width, final int height, final ItemStack result, final Object ... ingredients){
    return new ShapedRecipes("", width, height, getIngredients(result.toString(), width, height, ingredients), result);
  }
  
  public static final IRecipe create(final String group, final Block block, final Object ... ingredients){
    return create(group, new ItemStack(block,1), ingredients);
  }
  
  public static final IRecipe create(final String group, final Item item, final Object ... ingredients){
    return create(group, new ItemStack(item,1), ingredients);
  }
  
  public static final IRecipe create(final String group, final ItemStack result, final Object ... ingredients){
    final NonNullList<Ingredient> recipe = getIngredients(result.toString(), ingredients);
    for(Ingredient ingredient : recipe){
      if(ingredient == Ingredient.EMPTY || ingredient.apply(ItemStack.EMPTY)){
        ADDSynthCore.log.error("There is an EMPTY ingredient in the SHAPELESS recipe for "+result.toString()+". This is stupid and may potentially cause problems.");
        break;
      }
    }
    return new ShapelessRecipes(group, result, recipe);
  }
  
  public static final IRecipe create(final String group, final int width, final int height, final Block block, final Object ... ingredients){
    return create(group, width, height, new ItemStack(block,1), ingredients);
  }
  
  public static final IRecipe create(final String group, final int width, final int height, final Item item, final Object ... ingredients){
    return create(group, width, height, new ItemStack(item,1), ingredients);
  }
  
  public static final IRecipe create(final String group, final int width, final int height, final ItemStack result, final Object ... ingredients){
    return new ShapedRecipes(group, width, height, getIngredients(result.toString(), width, height, ingredients), result);
  }
  
  public static final NonNullList<Ingredient> getIngredients(final String result, final Object ... ingredients){
    final NonNullList<Ingredient> final_ingredients = NonNullList.create();
    for(Object in : ingredients){
      final_ingredients.add(getIngredient(result, in));
    }
    return final_ingredients;
  }
  
  public static final NonNullList<Ingredient> getIngredients(final String result, final int width, final int height, final Object ... ingredients){
    final NonNullList<Ingredient> final_ingredients = getIngredients(result, ingredients);
    final int size = width * height;
    if(final_ingredients.size() != size){
      throw new IllegalArgumentException("The Shaped Recipe for "+result+" is supposed to be of shape: "+width+"x"+height+" = size "+size+", but it has "+final_ingredients.size()+" ingredients!");
    }
    return final_ingredients;
  }
  
  /**
   * Automatically detects if the passed object has an OreDictionary Name assigned in the OreDictionary and uses that instead.
   * Logs a warning if the developer should've used an OreDictionary Name.
   * @return
   */
  public static final Ingredient getIngredient(final String result, final Object ingredient){
    if(ingredient instanceof Ingredient){ return (Ingredient)ingredient; }
    if(ingredient instanceof String){ throw new UnsupportedOperationException("Overpowered for 1.14 is still WIP."); }
    
    ItemStack stack = null;
    if(ingredient instanceof Block){ stack = new ItemStack((Block)ingredient,1); }
    if(ingredient instanceof Item){  stack = new ItemStack((Item)ingredient,1); }
    if(ingredient instanceof ItemStack){ stack = (ItemStack)ingredient; }
    if(stack == null){ // FEATURE: I could go a step further here to specify the index of the ingredient in the list of ingredients.
      throw new IllegalArgumentException("Invalid ingredient for recipe "+result+". Input can only be a Block, Item, ItemStack, Ingredient, or OreDictionary Name!");
    }

      // TODO: For each Item and Block, search through the Tags to see if it is registered with
      // any of them, then suggest to use the Tag names in recipes instead of the Block or Item.
      ADDSynthCore.log.warn("The ingredient "+stack.getTranslationKey()+" for recipe "+result+" has OreDictionary Names "+names.toString()+". It is recommended that the developer use one of these instead.");

    return Ingredient.fromStacks(stack);
  }
  
  public static final void register(final IRecipe recipe, final ResourceLocation registry_name){
    recipe.setRegistryName(registry_name);
    ForgeRegistries.RECIPES.register(recipe);
  }

  public static final void register(final IRecipe recipe, final String registry_name){
    recipe.setRegistryName(new ResourceLocation(MinecraftUtility.getModID(),registry_name));
    ForgeRegistries.RECIPES.register(recipe);
  }

  public static final void register(final IRecipe recipe){
    recipe.setRegistryName(recipe.getRecipeOutput().getItem().getRegistryName());
    ForgeRegistries.RECIPES.register(recipe);
  }

  // maybe remove all these functions that use a ResourceLocation for the ModID, I mean we already get the Mod's ID and automatically append it.
  //  - NO, let's leave them there because the MOD author can just specify the Mod ID, which is a lot faster than letting it be automatically assigned.
  public static final void register(final String group, final Block block, final ResourceLocation registry_name, final Object ... ingredients){
    register(create(group, new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void register(final String group, final Item item, final ResourceLocation registry_name, final Object ... ingredients){
    register(create(group, new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final String group, final ItemStack result, final ResourceLocation registry_name, final Object ... ingredients){
    register(create(group, result, ingredients), registry_name);
  }

  public static final void register(final String group, final int width, final int height, final Block block, final ResourceLocation registry_name, final Object ... ingredients){
    register(create(group, width, height, new ItemStack(block,1), ingredients), registry_name);
  }
  
  public static final void register(final String group, final int width, final int height, final Item item, final ResourceLocation registry_name, final Object ... ingredients){
    register(create(group, width, height, new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final String group, final int width, final int height, final ItemStack result, final ResourceLocation registry_name, final Object ... ingredients){
    register(create(group, width, height, result, ingredients), registry_name);
  }

  // Forge OreDictionary ShapelessOreRecipe just uses a group string and makes a new ResourceLocation, which just appends the minecraft: domain in front.

  public static final void register(final String group, final Block block, final String registry_name, final Object[] ingredients){
    register(create(group, new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void register(final String group, final Item item, final String registry_name, final Object[] ingredients){
    register(create(group, new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final String group, final ItemStack result, final String registry_name, final Object[] ingredients){
    register(create(group, result, ingredients), registry_name);
  }

  public static final void register(final String group, final int width, final int height, final Block block, final String registry_name, final Object[] ingredients){
    register(create(group, width, height, new ItemStack(block,1), ingredients), registry_name);
  }
  
  public static final void register(final String group, final int width, final int height, final Item item, final String registry_name, final Object[] ingredients){
    register(create(group, width, height, new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final String group, final int width, final int height, final ItemStack result, final String registry_name, final Object[] ingredients){
    register(create(group, width, height, result, ingredients), registry_name);
  }

  public static final void register(final String group, final Block block, final Object[] ingredients){
    register(create(group, new ItemStack(block,1), ingredients), block.getRegistryName());
  }

  public static final void register(final String group, final Item item, final Object[] ingredients){
    register(create(group, new ItemStack(item,1), ingredients), item.getRegistryName());
  }

  public static final void register(final String group, final ItemStack result, final Object[] ingredients){
    register(create(group, result, ingredients), result.getItem().getRegistryName());
  }

  public static final void register(final String group, final int width, final int height, final Block block, final Object[] ingredients){
    register(create(group, width, height, new ItemStack(block,1), ingredients), block.getRegistryName());
  }
  
  public static final void register(final String group, final int width, final int height, final Item item, final Object[] ingredients){
    register(create(group, width, height, new ItemStack(item,1), ingredients), item.getRegistryName());
  }

  public static final void register(final String group, final int width, final int height, final ItemStack result, final Object[] ingredients){
    register(create(group, width, height, result, ingredients), result.getItem().getRegistryName());
  }

  public static final void register(final Block block, final ResourceLocation registry_name, final Object ... ingredients){
    register(create("", new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void register(final Item item, final ResourceLocation registry_name, final Object ... ingredients){
    register(create("", new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final ItemStack result, final ResourceLocation registry_name, final Object ... ingredients){
    register(create("", result, ingredients), registry_name);
  }

  public static final void register(final int width, final int height, final Block block, final ResourceLocation registry_name, final Object ... ingredients){
    register(create("", width, height, new ItemStack(block,1), ingredients), registry_name);
  }
  
  public static final void register(final int width, final int height, final Item item, final ResourceLocation registry_name, final Object ... ingredients){
    register(create("", width, height, new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final int width, final int height, final ItemStack result, final ResourceLocation registry_name, final Object ... ingredients){
    register(create("", width, height, result, ingredients), registry_name);
  }

  public static final void register(final Block block, final String registry_name, final Object[] ingredients){
    register(create("", new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void register(final Item item, final String registry_name, final Object[] ingredients){
    register(create("", new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final ItemStack result, final String registry_name, final Object[] ingredients){
    register(create("", result, ingredients), registry_name);
  }

  public static final void register(final int width, final int height, final Block block, final String registry_name, final Object[] ingredients){
    register(create("", width, height, new ItemStack(block,1), ingredients), registry_name);
  }
  
  public static final void register(final int width, final int height, final Item item, final String registry_name, final Object[] ingredients){
    register(create("", width, height, new ItemStack(item,1), ingredients), registry_name);
  }

  public static final void register(final int width, final int height, final ItemStack result, final String registry_name, final Object[] ingredients){
    register(create("", width, height, result, ingredients), registry_name);
  }

  public static final void register(final Block block, final Object[] ingredients){
    register(create("", new ItemStack(block,1), ingredients), block.getRegistryName());
  }

  public static final void register(final Item item, final Object[] ingredients){
    register(create("", new ItemStack(item,1), ingredients), item.getRegistryName());
  }

  public static final void register(final ItemStack result, final Object[] ingredients){
    register(create("", result, ingredients), result.getItem().getRegistryName());
  }

  public static final void register(final int width, final int height, final Block block, final Object[] ingredients){
    register(create("", width, height, new ItemStack(block,1), ingredients), block.getRegistryName());
  }
  
  public static final void register(final int width, final int height, final Item item, final Object[] ingredients){
    register(create("", width, height, new ItemStack(item,1), ingredients), item.getRegistryName());
  }

  public static final void register(final int width, final int height, final ItemStack result, final Object[] ingredients){
    register(create("", width, height, result, ingredients), result.getItem().getRegistryName());
  }

  public static final void registerSword(final Item item, final Object ingredient){
    registerSword("", new ItemStack(item,1), ingredient);
  }

  public static final void registerSword(final ItemStack result, final Object ingredient){
    registerSword("", result, ingredient);
  }

  public static final void registerSword(final String group, final Item item, final Object ingredient){
    register(create(group, 1, 3, new ItemStack(item,1), ingredient, ingredient, "stickWood"), item.getRegistryName());
  }

  public static final void registerSword(final String group, final ItemStack result, final Object ingredient){
    register(create(group, 1, 3, result, ingredient, ingredient, "stickWood"), result.getItem().getRegistryName());
  }

  public static final void registerPickaxe(final Item item, final Object ingredient){
    registerPickaxe("", new ItemStack(item,1), ingredient);
  }

  public static final void registerPickaxe(final ItemStack result, final Object ingredient){
    registerPickaxe("", result, ingredient);
  }

  public static final void registerPickaxe(final String group, final Item item, final Object ingredient){
    register(create(group, 3, 3, new ItemStack(item,1), ingredient, ingredient, ingredient, Ingredient.EMPTY, "stickWood", Ingredient.EMPTY, Ingredient.EMPTY, "stickWood", Ingredient.EMPTY), item.getRegistryName());
  }

  public static final void registerPickaxe(final String group, final ItemStack result, final Object ingredient){
    register(create(group, 3, 3, result, ingredient, ingredient, ingredient, Ingredient.EMPTY, "stickWood", Ingredient.EMPTY, Ingredient.EMPTY, "stickWood", Ingredient.EMPTY), result.getItem().getRegistryName());
  }

  public static final void registerShovel(final Item item, final Object ingredient){
    registerShovel("", new ItemStack(item,1), ingredient);
  }

  public static final void registerShovel(final ItemStack result, final Object ingredient){
    registerShovel("", result, ingredient);
  }

  public static final void registerShovel(final String group, final Item item, final Object ingredient){
    register(create(group, 1, 3, new ItemStack(item,1), ingredient, "stickWood", "stickWood"), item.getRegistryName());
  }

  public static final void registerShovel(final String group, final ItemStack result, final Object ingredient){
    register(create(group, 1, 3, result, ingredient, "stickWood", "stickWood"), result.getItem().getRegistryName());
  }

  public static final void registerHoe(final Item item, final Object ingredient){
    registerHoe("", new ItemStack(item,1), ingredient);
  }

  public static final void registerHoe(final ItemStack result, final Object ingredient){
    registerHoe("", result, ingredient);
  }

  public static final void registerHoe(final String group, final Item item, final Object ingredient){
    register(create(group, 2, 3, new ItemStack(item,1), ingredient, ingredient, Ingredient.EMPTY, "stickWood", Ingredient.EMPTY, "stickWood"), item.getRegistryName());
  }

  public static final void registerHoe(final String group, final ItemStack result, final Object ingredient){
    register(create(group, 2, 3, result, ingredient, ingredient, Ingredient.EMPTY, "stickWood", Ingredient.EMPTY, "stickWood"), result.getItem().getRegistryName());
  }

  public static final void registerAxe(final Item item, final Object ingredient){
    registerAxe("", new ItemStack(item,1), ingredient);
  }

  public static final void registerAxe(final ItemStack result, final Object ingredient){
    registerAxe("", result, ingredient);
  }

  public static final void registerAxe(final String group, final Item item, final Object ingredient){
    register(create(group, 2, 3, new ItemStack(item,1), ingredient, ingredient, ingredient, "stickWood", Ingredient.EMPTY, "stickWood"), item.getRegistryName());
  }

  public static final void registerAxe(final String group, final ItemStack result, final Object ingredient){
    register(create(group, 2, 3, result, ingredient, ingredient, ingredient, "stickWood", Ingredient.EMPTY, "stickWood"), result.getItem().getRegistryName());
  }

  public static final void registerMachine(final String group, final Block block, final Object[] ingredients){
    register(create(group, 3, 3, new ItemStack(block,1), ingredients), block.getRegistryName());
  }

  public static final void registerMachine(final String group, final Item item, final Object[] ingredients){
    register(create(group, 3, 3, new ItemStack(item,1), ingredients), item.getRegistryName());
  }
  
  public static final void registerMachine(final String group, final ItemStack result, final Object[] ingredients){
    register(create(group, 3, 3, result, ingredients), result.getItem().getRegistryName());
  }
  
  public static final void registerMachine(final String group, final Block block, final String registry_name, final Object[] ingredients){
    register(create(group, 3, 3, new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void registerMachine(final String group, final Item item, final String registry_name, final Object[] ingredients){
    register(create(group, 3, 3, new ItemStack(item,1), ingredients), registry_name);
  }
  
  public static final void registerMachine(final String group, final ItemStack result, final String registry_name, final Object[] ingredients){
    register(create(group, 3, 3, result, ingredients), registry_name);
  }
  
  public static final void registerMachine(final String group, final Block block, final ResourceLocation registry_name, final Object[] ingredients){
    register(create(group, 3, 3, new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void registerMachine(final String group, final Item item, final ResourceLocation registry_name, final Object[] ingredients){
    register(create(group, 3, 3, new ItemStack(item,1), ingredients), registry_name);
  }
  
  public static final void registerMachine(final String group, final ItemStack result, final ResourceLocation registry_name, final Object[] ingredients){
    register(create(group, 3, 3, result, ingredients), registry_name);
  }
  
  public static final void registerMachine(final Block block, final Object[] ingredients){
    register(create("", 3, 3, new ItemStack(block,1), ingredients), block.getRegistryName());
  }

  public static final void registerMachine(final Item item, final Object[] ingredients){
    register(create("", 3, 3, new ItemStack(item,1), ingredients), item.getRegistryName());
  }
  
  public static final void registerMachine(final ItemStack result, final Object[] ingredients){
    register(create("", 3, 3, result, ingredients), result.getItem().getRegistryName());
  }
  
  public static final void registerMachine(final Block block, final String registry_name, final Object[] ingredients){
    register(create("", 3, 3, new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void registerMachine(final Item item, final String registry_name, final Object[] ingredients){
    register(create("", 3, 3, new ItemStack(item,1), ingredients), registry_name);
  }
  
  public static final void registerMachine(final ItemStack result, final String registry_name, final Object[] ingredients){
    register(create("", 3, 3, result, ingredients), registry_name);
  }
  
  public static final void registerMachine(final Block block, final ResourceLocation registry_name, final Object[] ingredients){
    register(create("", 3, 3, new ItemStack(block,1), ingredients), registry_name);
  }

  public static final void registerMachine(final Item item, final ResourceLocation registry_name, final Object[] ingredients){
    register(create("", 3, 3, new ItemStack(item,1), ingredients), registry_name);
  }
  
  public static final void registerMachine(final ItemStack result, final ResourceLocation registry_name, final Object[] ingredients){
    register(create("", 3, 3, result, ingredients), registry_name);
  }

  @Deprecated // OPTIMIZE delete? Does this exist in earlier versions of RecipeUtil?
  public static final ShapedOreRecipe NewShapedOreRecipe(final ResourceLocation group, final Item item, final Object ... recipe_arguments){
    if(item == null){
      ADDSynthCore.log.error("Output Item for new ShapedOreRecipe is null.");
      return null;
    }
    return NewShapedOreRecipe(group, new ItemStack(item,1), recipe_arguments);
  }

  @Deprecated
  public static final ShapedOreRecipe NewShapedOreRecipe(final ResourceLocation group, final Block block, final Object ... recipe_arguments){
    if(block == null){
      ADDSynthCore.log.error("Output Block for new ShapedOreRecipe is null.");
      return null;
    }
    final Item item = Item.getItemFromBlock(block);
    return NewShapedOreRecipe(group, new ItemStack(item,1), recipe_arguments);
  }

  @Deprecated
  public static final ShapedOreRecipe NewShapedOreRecipe(final ResourceLocation group, final ItemStack stack, final Object ... recipe_arguments){
    if(stack == null){
      ADDSynthCore.log.error("Output ItemStack for new ShapedOreRecipe is null.");
      return null;
    }
    if(debug_recipes){
      ADDSynthCore.log.info("Created new ShapedOreRecipe:");
      
      String shape = "";
      int index = 0;
      
      if(recipe_arguments[index] instanceof Boolean){
        index += 1;
      }
      
      if(recipe_arguments[index] instanceof String[]){
        String[] parts = (String[])recipe_arguments[index];
        for(String string : parts){
          shape += string;
        }
        index += 1;
      }
      else{
        String s;
        while(recipe_arguments[index] instanceof String){
          s = (String)recipe_arguments[index];
          shape += s;
          index += 1;
        }
      }
      
      ADDSynthCore.log.info("Input: "+shape+"  Output: "+stack.toString());

      try{
        Character character;
        Object in;
        while(index < recipe_arguments.length){
          character = (Character)recipe_arguments[index];
          in = recipe_arguments[index+1];
          if(in instanceof ItemStack || in instanceof Item || in instanceof Block || in instanceof String){
            ADDSynthCore.log.info(character+" = "+in.toString());
          }
          else{
            throw new RuntimeException("Invalid input, must be an ItemStack, Item, Block, or String");
          }
          index += 2;
        }
      }
      catch(Throwable e){
        ADDSynthCore.log.error("Invalid shaped ore recipe.");
        e.printStackTrace();
        return null;
      }
      
    }
    return new ShapedOreRecipe(group, stack, recipe_arguments);
  }














  @Deprecated
  public static final ShapelessOreRecipe NewShapelessOreRecipe(final ResourceLocation group, final Item item, final Object ... recipe_arguments){
    if(item == null){
      ADDSynthCore.log.error("Output Item for new ShapelessOreRecipe is null.");
      return null;
    }
    return NewShapelessOreRecipe(group, new ItemStack(item,1), recipe_arguments);
  }

  @Deprecated
  public static final ShapelessOreRecipe NewShapelessOreRecipe(final ResourceLocation group, final Block block, final Object ... recipe_arguments){
    if(block == null){
      ADDSynthCore.log.error("Output Block for new ShapelessOreRecipe is null.");
      return null;
    }
    final Item item = Item.getItemFromBlock(block);
    return NewShapelessOreRecipe(group, new ItemStack(item,1), recipe_arguments);
  }

  @Deprecated
  public static final ShapelessOreRecipe NewShapelessOreRecipe(final ResourceLocation group, final ItemStack itemStack, final Object ... recipe_arguments){
    if(itemStack == null){
      ADDSynthCore.log.error("Output ItemStack for new ShapelessOreRecipe is null.");
      return null;
    }
    boolean error = false;
    if(debug_recipes){
      String input = "";
      ItemStack temp_stack = null;
      for(Object in : recipe_arguments){
        if(in instanceof ItemStack || in instanceof Item || in instanceof Block || in instanceof String){
          if(in instanceof ItemStack){ temp_stack = (ItemStack)in; }
          if(in instanceof Item){ temp_stack = new ItemStack((Item)in,1); }
          if(in instanceof Block){
            Item item = Item.getItemFromBlock((Block)in);
            temp_stack = new ItemStack(item,1);
          }
          if(in instanceof String){
            input += (String)in + ", ";
          }
          else{
            if(temp_stack != null){
              input += temp_stack.toString() + ", ";
            }
            else{
              ADDSynthCore.log.error("Input is invalid for Recipe: "+itemStack.toString()+".");
            }
          }
        }
        else{
          ADDSynthCore.log.error("Input is invalid for Recipe: "+itemStack.toString()+".");
          error = true;
          break;
        }
      }

      if(error == false){
        ADDSynthCore.log.info("Created new ShapelessOreRecipe:");
        ADDSynthCore.log.info("Output: "+itemStack.toString()+", Input: "+input);
      }
    }
    return error ? null : new ShapelessOreRecipe(group, itemStack, recipe_arguments);
  }

















  /** THIS JUST PROVES THAT I CAN MAKE MY OWN COPY OF RECIPE COMPARISONS WITHOUT USING A CRAFTING INVENTORY
   *  This code is an EXACT copy from {@link ShapelessOreRecipe#matches(InventoryCrafting, World)}.
   * @param input
   * @param recipe
   * @since August 28, 2018, modified for Minecraft 1.12 on March 13, 2019.
   */
  // @SuppressWarnings({ "unchecked", "javadoc" })
  public static final boolean match(final ItemStack[] input, final IRecipe recipe){ // FEATURE: change the 2nd argument to IRecipe for all versions. (Must add the ability to match Shaped recipes!)
    if(recipe instanceof ShapedRecipes){
      ADDSynthCore.log.error("Missing feature: RecipeUtil.match(ItemStack[] input, IRecipe recipe) cannot check Shaped Recipes! I never put it in because I never use match() to check Shaped Recipes!");
      return false;
    }
    if(recipe.isDynamic()){
      ADDSynthCore.log.error("Missing feature: RecipeUtil.match(ItemStack[] input, IRecipe recipe) cannot check Dynamic recipes, i.e. recipes whose output depends on the STATE of the input ingredients!");
      return false;
    }
    int ingredient_count = 0;
    final RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
    for(ItemStack stack : input){
      if(stack.isEmpty() == false){
        ingredient_count += 1;
        recipeItemHelper.accountStack(stack, 1);
      }
    }
    if(recipe instanceof ShapelessRecipes && ingredient_count != recipe.getIngredients().size()){
      return false;
    }
    return recipeItemHelper.canCraft(recipe, null);
  }

}
