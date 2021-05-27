package addsynth.overpoweredmod.datafix;

import java.util.function.BiFunction;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.Util;

/** @see <a href="https://fabricmc.net/wiki/tutorial:datafixer">DataFix Tutorial</a>
 *  @see net.minecraft.util.datafix.DataFixesManager */
public final class OverpoweredDataFixer {

  public static final int data_version = 1;

  private static final BiFunction<Integer, Schema, Schema> SCHEMA_FACTORY = Schema::new;

  public static final DataFixer DATA_FIXER = create_datafixer();

  private static final DataFixer create_datafixer(){
    final DataFixerBuilder builder = new DataFixerBuilder(data_version);

    // first or base schema must define the types I wish to fix
    final Schema schema0 = builder.addSchema(0, Version0::new);
    final Schema schema1 = builder.addSchema(1, SCHEMA_FACTORY);
    
    /* For upgrading from version 1.3.x to 1.4.x:
     * The Overpowered Generator was renamed from generator to crystal_energy_extractor.
     * All materials have been moved from wherever they were registered to before, to ADDSynth materials.
     * all Energy tools were renamed from energy_[tool] to celestial_[tool].
     * (All previous Energy Crystals will correctly upgrade to the new Energy Crystals without having to do anything.)
     * Trophies were moved from Overpowered to ADDSynthCore.
    */
    
    return builder.build(Util.getServerExecutor());
  }


}
