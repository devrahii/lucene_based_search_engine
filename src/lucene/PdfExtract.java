package lucene;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;  
  
public class PdfExtract {  
      
    public String readPdf(String FilePath)throws IOException {  
          
        //Loading an existing document  
          File file = new File(FilePath);  
          PDDocument doc = PDDocument.load(file);  
      
    //Instantiate PDFTextStripper class  
          PDFTextStripper pdfStripper = new PDFTextStripper();  
  
    //Retrieving text from PDF document  
         return pdfStripper.getText(doc);  
            
    }  
}  