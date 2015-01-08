/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.Map;
import java.util.concurrent.Callable;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;

/**
 * A Callable document transformer that converts the given WikipediaDocument object
 * into an IndexableDocument object using the given Tokenizer
 * @author vvelrath
 *
 */
public class DocumentTransformer implements Callable<IndexableDocument> {
	
	private Map<INDEXFIELD,Tokenizer> tmap;
	private WikipediaDocument document;
	Tokenizer myTokenizer;
	private IndexableDocument indexableDoc=new IndexableDocument();
	
	/**
	 * Default constructor, DO NOT change
	 * @param tknizerMap: A map mapping a fully initialized tokenizer to a given field type
	 * @param doc: The WikipediaDocument to be processed
	 */
	public DocumentTransformer(Map<INDEXFIELD, Tokenizer> tknizerMap, WikipediaDocument doc) {
		//TODO: Implement this method
		tmap=tknizerMap;
		document=doc;

	
	}
	
	/**
	 * Method to trigger the transformation
	 * @throws TokenizerException Inc ase any tokenization error occurs
	 */
	public IndexableDocument call() throws TokenizerException {
		// TODO Implement this method
		String DocID=document.getTitle();
		
		indexableDoc.setDocumentIdentifier(DocID.toString());
		TokenStream stream = null;
		for(INDEXFIELD field:INDEXFIELD.values())
		{
			
			myTokenizer=tmap.get(field);
			switch(field)
			{
			case TERM:
			{
			    stream=new TokenStream(document.getSectionText());
			    
			    myTokenizer.tokenize(stream);
			    indexableDoc.addField(field, stream);
				break;
			}
			case AUTHOR:
			{
				stream=new TokenStream(document.getAuthor());
				
				myTokenizer.tokenize(stream);
				indexableDoc.addField(field, stream);
				break;
			}
			case CATEGORY:
			{
				stream=new TokenStream("");
				for(String category:document.getCategories())
					stream.append(category);
				
				myTokenizer.tokenize(stream);
				indexableDoc.addField(field, stream);
				break;
			}
			case LINK:
			{
				stream=new TokenStream("");
				for(String links:document.getLinks())
					stream.append(links);
				
				myTokenizer.tokenize(stream);
				indexableDoc.addField(field, stream);
				break;
			}
			}
			
		}
		
		
		return indexableDoc;

	}
	
}
