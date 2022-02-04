package lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import lucene.WordExtractor;
import lucene.PdfExtract;
import lucene.GenericFileReader;

public class Indexer {

   private IndexWriter writer;
  

   public Indexer(String indexDirectoryPath) throws IOException {
      //this directory will contain the indexes
      Directory indexDirectory = 
         FSDirectory.open(new File(indexDirectoryPath));

      //create the indexer
      writer = new IndexWriter(indexDirectory, 
         new StandardAnalyzer(Version.LUCENE_36),true, 
         IndexWriter.MaxFieldLength.UNLIMITED);
   }

   public void close() throws CorruptIndexException, IOException {
      writer.close();
   }

   private Document getDocument(File file) throws Exception {
      Document document = new Document();
      String content = null;
   
  
      if(file.getName().toLowerCase().endsWith(".docx")) {
    	  WordExtractor wordExtractor = new WordExtractor();
			 content = wordExtractor.readDoc(file.getCanonicalPath());
      }
   else if(file.getName().toLowerCase().endsWith(".pdf")) {
    	 	PdfExtract pdf = new PdfExtract();
			content = pdf.readPdf(file.getCanonicalPath());
      }
   else {
	   GenericFileReader gfr = new GenericFileReader();
	   content = gfr.readFile(file.getAbsolutePath());
	   
   }
   
      //index file contents
      Field contentField = new Field(LuceneConstants.CONTENTS, new StringReader(content)); // /*content);*/ new FileReader(file));
      //index file name
      Field fileNameField = new Field(LuceneConstants.FILE_NAME,
         file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED);
      //index file path
      Field filePathField = new Field(LuceneConstants.FILE_PATH,
         file.getCanonicalPath(),Field.Store.YES,Field.Index.NOT_ANALYZED);

      document.add(contentField);
      document.add(fileNameField);
      document.add(filePathField);
      

      return document;
   }   
   
   private Boolean isFileIndexable(File file) {
	   
	   Boolean is_indexable = false;
	   if(file.getName().toLowerCase().endsWith(".docx")) {
		   is_indexable = true;
	   } 
	   else if(file.getName().toLowerCase().endsWith(".pdf"))  {
		   is_indexable = true;
	   }
	   else {
		   is_indexable = true;
	   }
	   
	   return is_indexable;
	   
   }

   private void indexFile(File file) throws Exception {
      System.out.println("Indexing "+file.getCanonicalPath());
      if (this.isFileIndexable(file)) {
	      Document document = getDocument(file);
	      writer.addDocument(document);
      }
   }

   public int createIndex(String dataDirPath, FileFilter filter) 
      throws Exception {
      //get all files in the data directory
      File[] files = new File(dataDirPath).listFiles();

      for (File file : files) {
         if(!file.isDirectory()
            && !file.isHidden()
            && file.exists()
            && file.canRead()
            && filter.accept(file)
         ){
            indexFile(file);
         }
      }
      return writer.numDocs();
   }
}