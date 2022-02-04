package lucene;
import java.io.FileInputStream;
import java.io.FileReader;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordExtractor {

   public static String readDoc(String FilePath)throws Exception {

      XWPFDocument docx = new XWPFDocument(new FileInputStream(FilePath));
      
      //using XWPFWordExtractor Class
      XWPFWordExtractor we = new XWPFWordExtractor(docx);
      
     return we.getText();
   }
}