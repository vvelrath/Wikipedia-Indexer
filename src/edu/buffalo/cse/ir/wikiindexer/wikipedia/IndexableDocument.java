/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.Map;
import java.util.HashMap;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;

/**
 * A simple map based token view of the transformed document
 * @author vvelrath
 *
 */
public class IndexableDocument {
	/**
	 * Default constructor
	 */
	Map<INDEXFIELD,TokenStream> mymap;
	String documentIdentifier;
	//static int count=0;
	
	public void setDocumentIdentifier(String documentID) {
		this.documentIdentifier = documentID;
	}

	
	public IndexableDocument() {
		mymap=new HashMap<INDEXFIELD,TokenStream>();
		//TODO: Init state as needed
	}
	
	/**
	 * MEthod to add a field and stream to the map
	 * If the field already exists in the map, the streams should be merged
	 * @param field: The field to be added
	 * @param stream: The stream to be added.
	 */
	public void addField(INDEXFIELD field, TokenStream stream) {
		mymap.put(field, stream);
		//TODO: Implement this method
	}
	
	/**
	 * Method to return the stream for a given field
	 * @param key: The field for which the stream is requested
	 * @return The underlying stream if the key exists, null otherwise
	 */
	public TokenStream getStream(INDEXFIELD key) {
		//TODO: Implement this method
		return mymap.get(key);
	}
	
	/**
	 * Method to return a unique identifier for the given document.
	 * It is left to the student to identify what this must be
	 * But also look at how it is referenced in the indexing process
	 * @return A unique identifier for the given document
	 */
	public String getDocumentIdentifier() {
		//TODO: Implement this method
		return documentIdentifier;
	}
	
}
