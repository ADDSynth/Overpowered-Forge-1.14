package addsynth.overpoweredmod.datafix;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.Util;

public final class OverpoweredDataFixer {

  public static final int data_version = 1;

  public static final DataFixer DATA_FIXER = create_datafixer();

  private static final DataFixer create_datafixer(){
    final DataFixerBuilder builder = new DataFixerBuilder(data_version);

    // first or base schema must define the types I wish to fix
    final Schema schema_v0 = new Schema(0, null);
    final Schema schema_v1 = new Schema(1, schema_v0);
    
    return builder.build(Util.getServerExecutor());
  }


}
