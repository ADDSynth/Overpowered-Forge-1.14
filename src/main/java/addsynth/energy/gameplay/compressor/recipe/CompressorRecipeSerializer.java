package addsynth.energy.gameplay.compressor.recipe;

import javax.annotation.Nullable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

/** Code here is copied from {@link net.minecraft.item.crafting.ShapelessRecipe.Serializer ShapelessRecipe.Serializer} */
public class CompressorRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CompressorRecipe> {

  private static final NonNullList<Ingredient> readIngredients(JsonArray json_array){
    NonNullList<Ingredient> nonnulllist = NonNullList.create();
    for(int i = 0; i < json_array.size(); ++i) {
      Ingredient ingredient = Ingredient.deserialize(json_array.get(i));
      if(!ingredient.hasNoMatchingItems()){
        nonnulllist.add(ingredient);
      }
    }
    return nonnulllist;
  }

  // Start Here
  @Override
  public CompressorRecipe read(ResourceLocation recipeId, JsonObject json){
    String s = JSONUtils.getString(json, "group", "");
    NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
    if(nonnulllist.isEmpty()){
      throw new JsonParseException("No ingredients for compressor recipe");
    }
    if(nonnulllist.size() > CompressorRecipe.MAX_SIZE){
      throw new JsonParseException("Too many ingredients for compressor recipe. There can only be a max of "+CompressorRecipe.MAX_SIZE+" ingredients.");
    }
    ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
    return new CompressorRecipe(recipeId, s, itemstack, nonnulllist);
  }

  @Override
  @Nullable
  public CompressorRecipe read(ResourceLocation recipeId, PacketBuffer buffer){
    String s = buffer.readString(32767);
    int i = buffer.readVarInt();
    NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

    for(int j = 0; j < nonnulllist.size(); ++j) {
       nonnulllist.set(j, Ingredient.read(buffer));
    }

    ItemStack itemstack = buffer.readItemStack();
    return new CompressorRecipe(recipeId, s, itemstack, nonnulllist);
  }

  @Override
  public void write(PacketBuffer buffer, CompressorRecipe recipe){
    buffer.writeString(recipe.getGroup());
    buffer.writeVarInt(recipe.getIngredients().size());

    for(Ingredient ingredient : recipe.getIngredients()){
      ingredient.write(buffer);
    }

    buffer.writeItemStack(recipe.getRecipeOutput());
  }

}
