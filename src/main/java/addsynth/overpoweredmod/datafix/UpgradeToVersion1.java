package addsynth.overpoweredmod.datafix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;

public final class UpgradeToVersion1 extends DataFix {

  public UpgradeToVersion1(Schema outputSchema, boolean changesType){
    super(outputSchema, changesType);
  }

  @Override
  protected TypeRewriteRule makeRule(){
    return null;
  }

}
