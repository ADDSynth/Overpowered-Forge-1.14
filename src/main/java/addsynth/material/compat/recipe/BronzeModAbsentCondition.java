package addsynth.material.compat.recipe;

import addsynth.material.ADDSynthMaterials;
import addsynth.material.compat.MaterialsCompat;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public final class BronzeModAbsentCondition implements ICondition {

  public static final BronzeModAbsentCondition INSTANCE = new BronzeModAbsentCondition();
  public static final ResourceLocation id = new ResourceLocation(ADDSynthMaterials.MOD_ID, "bronze_mod_absent");

  private BronzeModAbsentCondition(){ }

  @Override
  public ResourceLocation getID(){
    return id;
  }

  @Override
  public boolean test(){
    return MaterialsCompat.BronzeModAbsent();
  }

  public static final class Serializer implements IConditionSerializer<BronzeModAbsentCondition> {

    public static final Serializer INSTANCE = new Serializer();

    @Override
    public void write(JsonObject json, BronzeModAbsentCondition value){
    }

    @Override
    public BronzeModAbsentCondition read(JsonObject json){
      return BronzeModAbsentCondition.INSTANCE;
    }

    @Override
    public ResourceLocation getID(){
      return BronzeModAbsentCondition.id;
    }
  
  }

}
