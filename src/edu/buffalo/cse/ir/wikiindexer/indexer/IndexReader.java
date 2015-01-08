/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author vvelrath This class is used to introspect a given index The
 *         expectation is the class should be able to read the index and all
 *         associated dictionaries.
 */

public class IndexReader {

	INDEXFIELD Ifeild;
	static int termkeysize;

	/**
	 * Constructor to create an instance
	 * 
	 * @param props
	 *            : The properties file
	 * @param field
	 *            : The index field whose index is to be read
	 */
	public IndexReader(Properties props, INDEXFIELD field) {
		// TODO: Implement this method
		Ifeild = field;

	}

	/**
	 * Method to get the total number of terms in the key dictionary
	 * 
	 * @return The total number of terms as above
	 */
	public int getTotalKeyTerms() {
		// TODO: Implement this method
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		TreeMap<String, LinkedList> termIndex = null;
		TreeMap<String, LinkedList> autIndex = null;
		TreeMap<String, LinkedList> catIndex = null;
		TreeMap<Integer, LinkedList> linIndex = null;
		int p;
		try {
			if (Ifeild == INDEXFIELD.TERM) {
				for (p = 1; p <= 4; p++) {
					fileIn = new FileInputStream("myIndex" + p + ".ser");
					in = new ObjectInputStream(fileIn);

					termIndex = (TreeMap<String, LinkedList>) in.readObject();
					termkeysize = termkeysize + termIndex.size();
				}
				return termkeysize;
			} else if (Ifeild == INDEXFIELD.AUTHOR) {

				fileIn = new FileInputStream("AuthorIndex.ser");
				in = new ObjectInputStream(fileIn);
				autIndex = (TreeMap<String, LinkedList>) in.readObject();
				in.close();
				fileIn.close();
				return autIndex.size();
			}

			else if (Ifeild == INDEXFIELD.CATEGORY) {

				fileIn = new FileInputStream("CategoryIndex.ser");
				in = new ObjectInputStream(fileIn);

				catIndex = (TreeMap<String, LinkedList>) in.readObject();

				in.close();
				fileIn.close();
				return catIndex.size();
			} else if (Ifeild == INDEXFIELD.LINK) {

				fileIn = new FileInputStream("LinkIndex.ser");
				in = new ObjectInputStream(fileIn);

				linIndex = (TreeMap<Integer, LinkedList>) in.readObject();

				in.close();
				fileIn.close();
				return linIndex.size();

			}

		} catch (IOException i) {
			i.printStackTrace();
			return 0;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return 0;
		}
		return 0;

	}

	/**
	 * Method to get the total number of terms in the value dictionary
	 * 
	 * @return The total number of terms as above
	 */
	public int getTotalValueTerms() {
		// TODO: Implement this method
		TreeMap<String, Integer> dictmap = new TreeMap<String, Integer>();
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		TreeMap<Integer, LinkedList> linIndex = null;

		if (Ifeild == INDEXFIELD.LINK) {
			try {
				fileIn = new FileInputStream("LinkIndex.ser");
				in = new ObjectInputStream(fileIn);

				linIndex = (TreeMap<Integer, LinkedList>) in.readObject();

				in.close();
				fileIn.close();

			} catch (IOException i) {
				i.printStackTrace();
				return 0;
			} catch (ClassNotFoundException c) {
				System.out.println("Employee class not found");
				c.printStackTrace();
				return 0;
			}
			return linIndex.size();
		} else {
			try {
				fileIn = new FileInputStream("dictionary.ser");

				in = new ObjectInputStream(fileIn);

				dictmap = (TreeMap<String, Integer>) in.readObject();

				in.close();
				fileIn.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dictmap.size();
		}
	}

	/**
	 * Method to retrieve the postings list for a given dictionary term
	 * 
	 * @param key
	 *            : The dictionary term to be queried
	 * @return The postings list with the value term as the key and the number
	 *         of occurrences as value. An ordering is not expected on the map
	 */
	public Map<String, Integer> getPostings(String key) {
		// TODO: Implement this method
		LinkedList<PostingsList> myList;
		Map<String, Integer> postings;
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		TreeMap<String, LinkedList> termIndex = null;
		TreeMap<String, LinkedList> autIndex = null;
		TreeMap<String, LinkedList> catIndex = null;
		TreeMap<Integer, LinkedList> linIndex = null;

		int p;
		try {
			fileIn = new FileInputStream("dictionary.ser");
			in = new ObjectInputStream(fileIn);
			TreeMap<String, Integer> Dicttree = (TreeMap<String, Integer>) in
					.readObject();
			in.close();
			fileIn.close();

			if (Ifeild == INDEXFIELD.TERM) {
				myList = new LinkedList<PostingsList>();
				postings = new TreeMap<String, Integer>();
				for (p = 1; p <= 4; p++) {
					fileIn = new FileInputStream("myIndex" + p + ".ser");
					in = new ObjectInputStream(fileIn);

					termIndex = (TreeMap<String, LinkedList>) in.readObject();
					if (termIndex.containsKey(key)) {
						myList = termIndex.get(key);
						for (int i = 0; i < myList.size(); i++) {
							Integer myInt = myList.get(i).getID();
							String myString = myInt.toString();
							for (Entry<String, Integer> entry : Dicttree
									.entrySet()) {
								if (myInt.equals(entry.getValue())) {
									postings.put(entry.getKey(), myList.get(i)
											.getFrequency());
								}
							}
						}

						return postings;
					}
				}
				return null;
			} else if (Ifeild == INDEXFIELD.AUTHOR) {
				myList = new LinkedList<PostingsList>();
				postings = new TreeMap<String, Integer>();
				fileIn = new FileInputStream("AuthorIndex.ser");
				in = new ObjectInputStream(fileIn);
				autIndex = (TreeMap<String, LinkedList>) in.readObject();

				if (autIndex.containsKey(key)) {
					myList = autIndex.get(key);
					for (int i = 0; i < myList.size(); i++) {

						Integer myInt = myList.get(i).getID();
						String myString = myInt.toString();
						for (Entry<String, Integer> entry : Dicttree.entrySet()) {
							if (myInt.equals(entry.getValue())) {
								postings.put(entry.getKey(), myList.get(i)
										.getFrequency());
							}
						}
						return postings;
					}
				}
				in.close();
				fileIn.close();
				return null;
			}

			else if (Ifeild == INDEXFIELD.CATEGORY) {
				myList = new LinkedList<PostingsList>();
				postings = new TreeMap<String, Integer>();

				fileIn = new FileInputStream("CategoryIndex.ser");
				in = new ObjectInputStream(fileIn);

				catIndex = (TreeMap<String, LinkedList>) in.readObject();

				if (catIndex.containsKey(key)) {
					myList = catIndex.get(key);
					for (int i = 0; i < myList.size(); i++) {

						Integer myInt = myList.get(i).getID();
						String myString = myInt.toString();
						for (Entry<String, Integer> entry : Dicttree.entrySet()) {
							if (myInt.equals(entry.getValue())) {
								postings.put(entry.getKey(), myList.get(i)
										.getFrequency());
							}
						}
						return postings;
					}
				}
				in.close();
				fileIn.close();
				return null;
			} else if (Ifeild == INDEXFIELD.LINK) {
				myList = new LinkedList<PostingsList>();
				postings = new TreeMap<String, Integer>();
				int id = Dicttree.get(key);
				fileIn = new FileInputStream("LinkIndex.ser");
				in = new ObjectInputStream(fileIn);

				linIndex = (TreeMap<Integer, LinkedList>) in.readObject();
				if (linIndex.containsKey(id)) {
					myList = linIndex.get(id);
					for (int i = 0; i < myList.size(); i++) {

						Integer myInt = myList.get(i).getID();
						String myString = myInt.toString();
						for (Entry<String, Integer> entry : Dicttree.entrySet()) {
							if (myInt.equals(entry.getValue())) {
								postings.put(entry.getKey(), myList.get(i)
										.getFrequency());
							}
						}

					}
					return postings;
				}
			}

		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Method to get the top k key terms from the given index The top here
	 * refers to the largest size of postings.
	 * 
	 * @param k
	 *            : The number of postings list requested
	 * @return An ordered collection of dictionary terms that satisfy the
	 *         requirement If k is more than the total size of the index, return
	 *         the full index and don't pad the collection. Return null in case
	 *         of an error or invalid inputs
	 * 
	 * 
	 */

	static class ValueComparator implements Comparator<String> {

		Map<String, Integer> base;

		ValueComparator(Map<String, Integer> base) {
			this.base = base;
		}

		@Override
		public int compare(String a, String b) {
			Integer x = base.get(a);
			Integer y = base.get(b);
			if (x == y) {
				return a.compareTo(b);
			}
			return y.compareTo(x);
		}
	}

	@SuppressWarnings("rawtypes")
	public Collection<String> getTopK(int k) {
		// TODO: Implement this method

		ValueComparator vc = null;
		TreeMap<String, Integer> sorted = null;

		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		TreeMap<String, LinkedList> termIndex = null;
		TreeMap<String, LinkedList> autIndex = null;
		TreeMap<String, LinkedList> catIndex = null;
		TreeMap<Integer,LinkedList> linIndex=null;
		Map<String, Integer> docount;
		Map<Integer,Integer> lincount;
		int p;
		try {
			fileIn = new FileInputStream("dictionary.ser");
			in = new ObjectInputStream(fileIn);
			TreeMap<String, Integer> Dicttree = (TreeMap<String, Integer>) in
					.readObject();
			in.close();
			fileIn.close();
			
			
			if (Ifeild == INDEXFIELD.TERM) {
				docount = new TreeMap<String, Integer>();
				for (p = 1; p <= 4; p++) {
					fileIn = new FileInputStream("myIndex1.ser");
					in = new ObjectInputStream(fileIn);
					System.out.println("Starting de-serialization...");
					Long startTime = System.currentTimeMillis();
					termIndex = (TreeMap<String, LinkedList>) in.readObject();

					in.close();
					fileIn.close();
					System.out.println("De-serialization completed in : "
							+ (System.currentTimeMillis() - startTime) / 1000);

					for (Map.Entry<String, LinkedList> entry : termIndex
							.entrySet()) {
						Integer temp = entry.getValue().size();
						String tempS = entry.getKey();
						docount.put(tempS, temp);
					}
				}

				vc = new ValueComparator(docount);
				sorted = new TreeMap<String, Integer>(vc);
				sorted.putAll(docount);

				ArrayList<String> myList = new ArrayList<String>();
				int x = k;

				for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
					if (x > 0) {
						myList.add(entry.getKey());
					} else {
						break;
					}
					x--;
				}
				return myList;

			} else if (Ifeild == INDEXFIELD.AUTHOR) {
				docount = new TreeMap<String, Integer>();
				fileIn = new FileInputStream("AuthorIndex.ser");
				in = new ObjectInputStream(fileIn);
				autIndex = (TreeMap<String, LinkedList>) in.readObject();

				for (Map.Entry<String, LinkedList> entry : autIndex.entrySet()) {
					docount.put(entry.getKey(), entry.getValue().size());
				}

				in.close();
				fileIn.close();
				vc = new ValueComparator(docount);
				sorted = new TreeMap<String, Integer>(vc);
				sorted.putAll(docount);

				ArrayList<String> myList = new ArrayList<String>();
				int x = k;

				for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
					if (x > 0) {
						myList.add(entry.getKey());
					} else {
						break;
					}
					x--;
				}
				return myList;
			}

			else if (Ifeild == INDEXFIELD.CATEGORY) {

				fileIn = new FileInputStream("CategoryIndex.ser");
				in = new ObjectInputStream(fileIn);
				docount = new TreeMap<String, Integer>();
				catIndex = (TreeMap<String, LinkedList>) in.readObject();

				for (Map.Entry<String, LinkedList> entry : catIndex.entrySet()) {
					docount.put(entry.getKey(), entry.getValue().size());
				}

				in.close();
				fileIn.close();

				vc = new ValueComparator(docount);
				sorted = new TreeMap<String, Integer>(vc);
				sorted.putAll(docount);
				ArrayList<String> myList = new ArrayList<String>();
				int x = k;

				for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
					if (x > 0) {
						myList.add(entry.getKey());
					} else {
						break;
					}
					x--;
				}
				return myList;
			}
			else if(Ifeild==INDEXFIELD.LINK)
			{
				fileIn = new FileInputStream("LinkIndex.ser");
				in = new ObjectInputStream(fileIn);
				docount = new TreeMap<String, Integer>();
				linIndex = (TreeMap<Integer, LinkedList>) in.readObject();

				for (Map.Entry<Integer, LinkedList> entry : linIndex.entrySet()) {
					for (Entry<String, Integer> entry1: Dicttree.entrySet()) {
						if(entry.getKey().equals(entry1.getValue()))
						{
							docount.put(entry1.getKey(), entry.getValue().size());
						}
					}
				}

				in.close();
				fileIn.close();

				vc = new ValueComparator(docount);
				sorted = new TreeMap<String, Integer>(vc);
				sorted.putAll(docount);
				ArrayList<String> myList = new ArrayList<String>();
				int x = k;

				for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
					if (x > 0) {
						myList.add(entry.getKey());
					} else {
						break;
					}
					x--;
				}
				return myList;
			}

		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * Method to execute a boolean AND query on the index
	 * 
	 * @param terms
	 *            The terms to be queried on
	 * @return An ordered map containing the results of the query The key is the
	 *         value field of the dictionary and the value is the sum of
	 *         occurrences across the different postings. The value with the
	 *         highest cumulative count should be the first entry in the map.
	 */
	
	public Map<String, Integer> query(String... terms) {
		//TODO: Implement this method (FOR A BONUS)
		Map<String,Integer> postinglist1 = new TreeMap<String,Integer>();
		Map<String,Integer> postinglist2 = new TreeMap<String,Integer>();
		Map<String,Integer> intersect_posting = new TreeMap<String,Integer>();
		Map<String,Integer> intersect_posting_sorted = new TreeMap<String,Integer>();
		
		if(terms.length==0)
		{	
			intersect_posting_sorted=null;
		}
		else if(terms.length>=1)
		{	
			postinglist1=getPostings(terms[0]);
			intersect_posting_sorted=postinglist1;
		}
		
		if(terms.length>1)
		{	
			for(int i=1;i<terms.length;i++)
			{	
				postinglist2=getPostings(terms[i]);
				
				if(postinglist1==null||postinglist2==null)
				{
					intersect_posting_sorted=null;
					return intersect_posting_sorted;
				}
				else
				{	
					for(Map.Entry<String, Integer> docfreq: postinglist1.entrySet())
					{
						String key=docfreq.getKey();
						
						if(postinglist2.containsKey(key))
						{
							int value=postinglist1.get(key)+postinglist2.get(key);
							intersect_posting.put(key,value);
						}	
					}
					ValueComparator vc = new ValueComparator(intersect_posting);
					intersect_posting_sorted = new TreeMap<String, Integer>(vc);
					intersect_posting_sorted.putAll(intersect_posting);
					postinglist1=intersect_posting_sorted;
				}
			}
		}
		return intersect_posting_sorted;
	}	
}
