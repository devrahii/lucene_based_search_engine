package lucene;

import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

   @Override
   public boolean accept(File pathname) {
      return true;//pathname.getName().toLowerCase().endsWith(".txt");
   }
}