package luceneExample;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class Lucene1 {

	public static void main(String[] args) throws IOException, ParseException {

		// Create a new index
		StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);

		// Create a writer
		IndexWriter writer = new IndexWriter(directory, config);
		Document document = new Document();

		// Setting up demo content for example
		document.add(new TextField("content", "Hello People", Store.YES));
		writer.addDocument(document);
		document.add(new TextField("content", "Hello there", Store.YES));
		writer.addDocument(document);

		writer.close();

		// Search for hello
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser("content", standardAnalyzer);
		Query query = parser.parse("Hello");

		TopDocs results = searcher.search(query, 5);
		System.out.println("Hits for Hello -->" + results.totalHits);

		// case insensitive search
		query = parser.parse("hello");
		results = searcher.search(query, 5);
		System.out.println("Hits for hello -->" + results.totalHits);

		// search for a value not indexed
		query = parser.parse("Hi there");
		results = searcher.search(query, 5);
		System.out.println("Hits for Hi there -->" + results.totalHits);

	}

}
