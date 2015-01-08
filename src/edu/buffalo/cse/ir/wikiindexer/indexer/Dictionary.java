/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author nikhillo
 * An abstract class that represents a dictionary object for a given index
 */
public abstract class Dictionary implements Writeable {
	
	TreeMap<String,Integer> Diction=new TreeMap<String,Integer>();
	//INDEXFEILD dictfeild;
	//public sint generatedvalue;
	public Dictionary (Properties props, INDEXFIELD field) {
		//TODO Implement this methodd
		//dictf
		
	}
	
	public Dictionary()
	{
		
	}
	
	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		// TODO Implement this method
		
        FileOutputStream fos;
		try {
			fos = new FileOutputStream("dictionary.ser");
			ObjectOutputStream oos;
			try {
				//System.out.println("DocDict size:"+Diction.size());
				oos = new ObjectOutputStream(fos);
				oos.writeObject(Diction);
				 oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		
		// TODO Implement this method
		/*try
	      {
	         FileInputStream fileIn = new FileInputStream("dictionary.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         TreeMap<String,Integer> Dicttree = (TreeMap<String,Integer>) in.readObject();
	         //System.out.println(Dicttree);
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
*/
	}
	
	/**
	 * Method to check if the given value exists in the dictionary or not
	 * Unlike the subclassed lookup methods, it only checks if the value exists
	 * and does not change the underlying data structure
	 * @param value: The value to be looked up
	 * @return true if found, false otherwise
	 */
	public boolean exists(String value) {
		//TODO Implement this method
		if(Diction.containsKey(value))
		{
			return true;
		}
		else
		{
		return false;
		}
	}
	
	/**
	 * MEthod to lookup a given string from the dictionary.
	 * The query string can be an exact match or have wild cards (* and ?)
	 * Must be implemented ONLY AS A BONUS
	 * @param queryStr: The query string to be searched
	 * @return A collection of ordered strings enumerating all matches if found
	 * null if no match is found
	 */
	
	
	public Collection<String> query(String queryStr) {
		//TODO: Implement this method (FOR A BONUS)
		Collection<String> query_result=new ArrayList<String>();
		
		if(queryStr.startsWith("*"))
		{
			String queryTerm=queryStr.replaceAll("[\\*]", "");
			for (String docname : Diction.keySet()) 
			{
				if(docname.endsWith(queryTerm))
					query_result.add(docname);
			}
		}
		else if(queryStr.endsWith("*"))
		{
			String queryTerm=queryStr.replaceAll("[\\*]", "");
			for (String docname : Diction.keySet()) 
			{
				if(docname.startsWith(queryTerm))
					query_result.add(docname);
			}
		}
		else if(queryStr.contains("*"))
		{
			//String queryTerm=queryStr.replaceAll("[\\*]", "");
			int asterisk=queryStr.indexOf("*");
			String queryTerm1=queryStr.substring(0,asterisk);
			String queryTerm2=queryStr.substring(asterisk+1);
			for (String docname : Diction.keySet()) 
			{
				if(docname.startsWith(queryTerm1)&&docname.endsWith(queryTerm2))
					query_result.add(docname);
			}
		}
		else if(queryStr.startsWith("?"))
		{
			String queryTerm=queryStr.replaceAll("[\\?]", "");
			for (String docname : Diction.keySet()) 
			{
				if(docname.endsWith(queryTerm)&&(docname.length()==queryStr.length()))
					query_result.add(docname);
			}
		}
		else if(queryStr.endsWith("?"))
		{
			String queryTerm=queryStr.replaceAll("[\\?]", "");
			for (String docname : Diction.keySet()) 
			{
				if(docname.startsWith(queryTerm)&&(docname.length()==queryStr.length()))
					query_result.add(docname);
			}
		}
		else if(queryStr.contains("?"))
		{
			//String queryTerm=queryStr.replaceAll("[\\*]", "");
			int quesmark=queryStr.indexOf("?");
			String queryTerm1=queryStr.substring(0,quesmark);
			String queryTerm2=queryStr.substring(quesmark+1);
			for (String docname : Diction.keySet()) 
			{
				if(docname.startsWith(queryTerm1)&&docname.endsWith(queryTerm2)&&(docname.length()==queryStr.length()))
					query_result.add(docname);
			}
		}
		else if(!queryStr.contains("*")&&!queryStr.contains("?"))
		{
			if(Diction.containsKey(queryStr))
				query_result.add(queryStr);
			else query_result=null;
		}
		else
			query_result=null;
		
		return query_result;
	}
	
	/**
	 * Method to get the total number of terms in the dictionary
	 * @return The size of the dictionary
	 */
	public int getTotalTerms() {
		//TODO: Implement this method
		return Diction.size();
	}
}
