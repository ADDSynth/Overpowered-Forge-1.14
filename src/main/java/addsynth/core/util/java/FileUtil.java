package addsynth.core.util.java;

import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

public final class FileUtil {

  @Nullable
  public static final File getNewFile(final String filename){
    final File file = new File(filename);
    if(file.exists()){
      if(file.delete()){
        return file;
      }
      System.err.println(new IOException("Unable to delete "+file.toString()+" for some reason."));
      return null;
    }
    return file;
  }

}
