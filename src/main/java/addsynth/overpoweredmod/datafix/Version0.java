package addsynth.overpoweredmod.datafix;

import java.util.Map;
import java.util.function.Supplier;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.minecraft.util.datafix.TypeReferences;

public final class Version0 extends Schema {

  public Version0(int versionKey, Schema parent){
    super(versionKey, parent);
  }

  @Override
  public void registerTypes(Schema schema, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes){
    schema.registerType(true, TypeReferences.BLOCK_ENTITY, () -> {
      return DSL.taggedChoiceLazy("id", DSL.string(), blockEntityTypes);
    });
    // schema.registerType(true, TypeReferences.ITEM_STACK, () -> {
    //   return DSL.hook(DSL.optionalFields("id", DSL.or(DSL.constType(DSL.intType()), 
    schema.registerType(false, TypeReferences.ITEM_NAME, () -> {
      return DSL.constType(DSL.namespacedString());
    });
  }

}
