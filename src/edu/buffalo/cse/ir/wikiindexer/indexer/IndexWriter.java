/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author vvelrath
 * This class is used to write an index to the disk
 * 
 */
public class IndexWriter implements Writeable,Serializable {
	
	/**
	 * Constructor that assumes the underlying index is inverted
	 * Every index (inverted or forward), has a key field and the value field
	 * The key field is the field on which the postings are aggregated
	 * The value field is the field whose postings we are accumulating
	 * For term index for example:
	 * 	Key: Term (or term id) - referenced by TERM INDEXFIELD
	 * 	Value: Document (or document id) - referenced by LINK INDEXFIELD
	 * @param props: The Properties file
	 * @param keyField: The index field that is the key for this index
	 * @param valueField: The index field that is the value for this index
	 * 
	 * 
	 */
	
	LinkedList<PostingsList> Termpostings =new LinkedList<PostingsList>();
	TreeMap<String,LinkedList> TermIndexmap=new TreeMap<String,LinkedList>();
	LinkedList<PostingsList> Authorpostings=new LinkedList<PostingsList>();
	TreeMap<String,LinkedList> AuthorIndexmap=new TreeMap<String,LinkedList>();
	LinkedList<PostingsList> Categorypostings=new LinkedList<PostingsList>();
	TreeMap<String,LinkedList> CategoryIndexmap=new TreeMap<String,LinkedList>();
	LinkedList<PostingsList> Linkpostings=new LinkedList<PostingsList>();
	TreeMap<Integer,LinkedList> LinkIndexmap=new TreeMap<Integer,LinkedList>();
	INDEXFIELD keyIndex;
	INDEXFIELD valueIndex;
	LocalDictionary Dict ;
	SharedDictionary docDict;
	int Pnum;
	
	
	
	
	public IndexWriter(Properties props, INDEXFIELD keyField, INDEXFIELD valueField) {
		this(props, keyField, valueField, false);
	}
	
	/**
	 * Overloaded constructor that allows specifying the index type as
	 * inverted or forward
	 * Every index (inverted or forward), has a key field and the value field
	 * The key field is the field on which the postings are aggregated
	 * The value field is the field whose postings we are accumulating
	 * For term index for example:
	 * 	Key: Term (or term id) - referenced by TERM INDEXFIELD
	 * 	Value: Document (or document id) - referenced by LINK INDEXFIELD
	 * @param props: The Properties file
	 * @param keyField: The index field that is the key for this index
	 * @param valueField: The index field that is the value for this index
	 * @param isForward: true if the index is a forward index, false if inverted
	 */
	static int count;
	public IndexWriter(Properties props, INDEXFIELD keyField, INDEXFIELD valueField, boolean isForward) {
		//TODO: Implement this method
		keyIndex=keyField;
		valueIndex=valueField;
		Dict = new LocalDictionary(props,keyField);
	}
	
	/**
	 * Method to make the writer self aware of the current partition it is handling
	 * Applicable only for distributed indexes.
	 * @param pnum: The partition number
	 */
	public void setPartitionNumber(int pnum) {
		
		Pnum=pnum;
		
		//TODO: Optionally implement this method
	}
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param keyId: The id for the key field, pre-converted
	 * @param valueId: The id for the value field, pre-converted
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, int valueId, int numOccurances) throws IndexerException {
		//TODO: Implement this method
		PostingsList post=new PostingsList(valueId,numOccurances);
		
		if(keyIndex==INDEXFIELD.LINK)
		{	
			if(!(LinkIndexmap.containsKey(keyId)))
		    {   
		    	Linkpostings=new LinkedList<PostingsList>();
		    	Linkpostings.add(post);
		    	LinkIndexmap.put(keyId,Linkpostings);
		    }
		    else
		    {
		    	Linkpostings=LinkIndexmap.get(keyId);
		    	Linkpostings.add(post);
		    	LinkIndexmap.put(keyId, Linkpostings);
		    }
		}
		
	}
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param keyId: The id for the key field, pre-converted
	 * @param value: The value for the value field
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, String value, int numOccurances) throws IndexerException {
		//TODO: Implement this method
	}
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param key: The key for the key field
	 * @param valueId: The id for the value field, pre-converted
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(String key, int valueId, int numOccurances) throws IndexerException {
		//TODO: Implement this method
		PostingsList post=new PostingsList(valueId,numOccurances);
		if(keyIndex==INDEXFIELD.TERM)
		{
		int id = Dict.lookup(key);
	    if(!(TermIndexmap.containsKey(key)))
	    {   
	    	Termpostings=new LinkedList<PostingsList>();
	    	Termpostings.add(post);
	    	TermIndexmap.put(key,Termpostings);
	    }
	    else
	    {
	    	Termpostings=TermIndexmap.get(key);
	    	Termpostings.add(post);
	    	TermIndexmap.put(key, Termpostings);
	    }
		}
		else if(keyIndex==INDEXFIELD.AUTHOR)
		{
			int id = Dict.lookup(key);
			if(!(AuthorIndexmap.containsKey(key)))
		    {
		    	Authorpostings=new LinkedList<PostingsList>();
		    	Authorpostings.add(post);
		    	AuthorIndexmap.put(key,Authorpostings);
		    }
		    else
		    {		    	
		    	Authorpostings=AuthorIndexmap.get(key);
		    	Authorpostings.add(post);
		    	AuthorIndexmap.put(key, Authorpostings);
		    }
			
		}
		else if(keyIndex==INDEXFIELD.CATEGORY)
		{
			int id = Dict.lookup(key);
			if(!(CategoryIndexmap.containsKey(key)))
		    {
		    	Categorypostings=new LinkedList<PostingsList>();
		    	Categorypostings.add(post);
		    	CategoryIndexmap.put(key,Categorypostings);
		    	
		    }
		    else
		    {
		    	Categorypostings=CategoryIndexmap.get(key);
		    	Categorypostings.add(post);
		    	CategoryIndexmap.put(key, Categorypostings);
		    	
		    }
		}
	    }
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param key: The key for the key field
	 * @param value: The value for the value field
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(String key, String value, int numOccurances) throws IndexerException {
		//TODO: Implement this method
		
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		// TODO Implement this method
		//System.out.println(TermIndexmap.keySet());
		
		try{

		 ObjectOutputStream oos=null;
		 
		 if(TermIndexmap.size()>0)
		 {
			 FileOutputStream out=new FileOutputStream("myIndex"+Pnum+".ser");
			 oos=new ObjectOutputStream(out);
			 oos.writeObject(TermIndexmap);
			 oos.flush();
			 out.close();
		 }
		 if(oos!=null)
			 oos.close();
		 if(AuthorIndexmap.size()>0)
		 {
			 FileOutputStream out=new FileOutputStream("AuthorIndex.ser");
			 oos=new ObjectOutputStream(out);
			 oos.writeObject(AuthorIndexmap);
			 oos.flush();
			 out.close();
		 }
		 if(oos!=null)
			 oos.close();
		 if(CategoryIndexmap.size()>0)
		 {
			 FileOutputStream out=new FileOutputStream("CategoryIndex.ser");
			 oos=new ObjectOutputStream(out);
			 oos.writeObject(CategoryIndexmap);
			 oos.flush();
			 out.close();
		 }
		 if(oos!=null)
			 oos.close();
		 if(LinkIndexmap.size()>0)
		 {
			 FileOutputStream out=new FileOutputStream("LinkIndex.ser");
			 oos=new ObjectOutputStream(out);
			 oos.writeObject(LinkIndexmap);
			 oos.flush();
			 out.close();
		 }
		 if(oos!=null)
			 oos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	 
		        
	}
	

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		// TODO Implement this method
		/*{
		 FileInputStream fileIn=null;
		 ObjectInputStream in=null;
		try {
			fileIn = new FileInputStream("AuthorIndex.ser");
			
				in = new ObjectInputStream(fileIn);
	        TreeMap<String, LinkedList> termIndex;
				termIndex = (TreeMap<String,LinkedList>)in.readObject();
			
	        
	      System.out.println(termIndex.keySet());
	       in.close();
	       fileIn.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
}

 class PostingsList implements Serializable
{
	int docid,frequency;
	PostingsList(int docID,int noofocc)
	{
		docid=docID;
		frequency=noofocc;
		
	}
	public int getFrequency()
	{
		return frequency;
	}
	public int getID()
	{
		return docid;
	}
	
}
