package addsynth.material.compat.recipe;

import addsynth.material.ADDSynthMaterials;
import addsynth.material.compat.MaterialsCompat;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public final class SteelModAbsentCondition implements ICondition {

  public static final SteelModAbsentCondition INSTANCE = new SteelModAbsentCondition();
  public static final ResourceLocation id = new ResourceLocation(ADDSynthMaterials.MOD_ID, "steel_mod_absent");

  private SteelModAbsentCondition(){ }

  @Override
  public ResourceLocation getID(){
    return id;
  }

  @Override
  public boolean test(){
    return MaterialsCompat.SteelModAbsent();
  }

  public static final class Serializer implements IConditionSerializer<SteelModAbsentCondition> {

    public static final Serializer INSTANCE = new Serializer();

    @Override
    public void write(JsonObject json, SteelModAbsentCondition value){
    }

    @Override
    public SteelModAbsentCondition read(JsonObject json){
      return SteelModAbsentCondition.INSTANCE;
    }

    @Override
    public ResourceLocation getID(){
      return SteelModAbsentCondition.id;
    }
  
  }

}
