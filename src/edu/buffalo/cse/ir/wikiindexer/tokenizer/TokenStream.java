/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents a stream of tokens as the name suggests.
 * It wraps the token stream and provides utility methods to manipulate it
 * @author vvelrath
 *
 */
public class TokenStream implements Iterator<String>{
	
	List<String> TokenList;
	Map<String,Integer> tokenmap;
	public int index=-1;

	
	/**
	 * Default constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(StringBuilder bldr) {
		//TODO: Implement this method
	}
	
	/**
	 * Overloaded constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(String string) {
		//TODO: Implement this method
		TokenList=new LinkedList<String>();
		
		if(string!=null&&string!="")
		{
		TokenList.add(string);
		}
	}
	
	/**
	 * Method to append tokens to the stream
	 * @param tokens: The tokens to be appended
	 */
	public void append(String... tokens) {
		//TODO: Implement this method
		if(tokens!=null)
		{
		for(int i=0;i<tokens.length;i++)
		{
			if((tokens[i]!=null)&&(tokens[i]!=""))
				TokenList.add(tokens[i]);
		}
		}
	}
	
	/**
	 * Method to retrieve a map of token to count mapping
	 * This map should contain the unique set of tokens as keys
	 * The values should be the number of occurrences of the token in the given stream
	 * @return The map as described above, no restrictions on ordering applicable
	 */
	public Map<String, Integer> getTokenMap() {
		//TODO: Implement this method
		tokenmap=new TreeMap<String,Integer>();
		
		if(TokenList.isEmpty())
		{
			return null;
		}
		else
		{
		for(String myString:TokenList)
		{
			if(!tokenmap.containsKey(myString))
			{
				tokenmap.put(myString, 1);
			}
			else{
				Integer count=tokenmap.get(myString);
				count=count+1;
				tokenmap.put(myString,count);
			}
			
		}
		
		return tokenmap;
		}
	}
	
	/**
	 * Method to get the underlying token stream as a collection of tokens
	 * @return A collection containing the ordered tokens as wrapped by this stream
	 * Each token must be a separate element within the collection.
	 * Operations on the returned collection should NOT affect the token stream
	 */
	public Collection<String> getAllTokens() {
		//TODO: Implement this method
		if((TokenList==null)||(TokenList.isEmpty()))
		{
			return null;
		}
		else
		return TokenList;
	}
	
	/**
	 * Method to query for the given token within the stream
	 * @param token: The token to be queried
	 * @return: THe number of times it occurs within the stream, 0 if not found
	 */
	public int query(String token) {
		//TODO: Implement this method
		tokenmap=new TreeMap<String,Integer>();
		
		if(TokenList.isEmpty())
		{
			return 0;
		}
		else
		{
		for(String myString:TokenList)
		{
			if(!tokenmap.containsKey(myString))
			{
				tokenmap.put(myString, 1);
			}
			else{
				Integer count=tokenmap.get(myString);
				count=count+1;
				tokenmap.put(myString,count);
			}
			
		}
		if(tokenmap.containsKey(token))
		{
			return tokenmap.get(token);
		}
		else
		{
			return 0;
		}
		
		}
	}
	
	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasNext() {
		// TODO: Implement this method
		if((index<TokenList.size()-1)&&(TokenList.size()!=0))
			return true;
			else
			return false;
	}
	
	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasPrevious() {
		//TODO: Implement this method
		if((index>=0)&&(TokenList.size()!=0)&&(index<TokenList.size()))
			return true;
			//TODO: Implement this method
		else
			return false;
	}
	
	/**
	 * Iterator method: Method to get the next token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String next() {
		// TODO: Implement this method
		if((TokenList.size()==0)||(index>=TokenList.size()-1))
			return null;
		else 
			{
				index=index+1;
				
				return TokenList.get(index);
			}
	}
	
	/**
	 * Iterator method: Method to get the previous token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String previous() {
		//TODO: Implement this method
		String token=null;
		if(TokenList.isEmpty()||index==-1)
			return token;
		else if(index==TokenList.size()-1)
		{
			index=index-1;
			token=TokenList.get(index+1);
		}	
		else
		{
			token=TokenList.get(index);
			index=index-1;
		}
		return token;
	}
	
	/**
	 * Iterator method: Method to remove the current token from the stream
	 */
	public void remove() {
		// TODO: Implement this method
		if((TokenList.size()!=0)&&(index!=TokenList.size()-1))
		{
			//System.out.println("index:"+index);
			TokenList.remove(index+1);
		}
	}
	
	/**
	 * Method to merge the current token with the previous token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the previous one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithPrevious() {
		//TODO: Implement this method
		String temp=null;
		if(TokenList.size()==0||TokenList.size()==1)
		{
			return false;
		}
		else if(!hasPrevious())
			return false;
		else
		{
			String newtemp=null;
			temp=next();
			previous();
			//temp=previous()+" "+temp;
			newtemp=previous().concat(" ").concat(temp);
			remove();
			remove();
			//TokenList.add(index+1, temp);
			TokenList.add(index+1, newtemp);
			//append(temp);
			
			return true;
		}
	}
	
	/**
	 * Method to merge the current token with the next token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the current one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithNext() {
		//TODO: Implement this method
		String temp=null;
		if(TokenList.size()==0||TokenList.size()==1)
		{
			return false;
		}
		else if(!hasNext())
		{
			return false;
		}
		else
		{
			String newtemp=null;
			temp=next();
			next();
			/*temp=temp+" "+previous();*/
			newtemp=temp.concat(" ").concat(previous());
			previous();
			remove();
			remove();
			TokenList.add(index+1, newtemp);
		return true;
		}
	}
	
	/**
	 * Method to replace the current token with the given tokens
	 * The stream should be manipulated accordingly based upon the number of tokens set
	 * It is expected that remove will be called to delete a token instead of passing
	 * null or an empty string here.
	 * The iterator should point to the last set token, i.e, last token in the passed array.
	 * @param newValue: The array of new values with every new token as a separate element within the array
	 */
	public void set(String... newValue) {
		//TODO: Implement this method
		if((TokenList.size()!=0)&&(newValue[0]!=null)&&(newValue[0]!="")&&(hasNext()))
		{
			for(int i=0;i<newValue.length;i++)
			{
				if(i==0)
				{
					TokenList.set(index+1,newValue[i]);
				}
				else
				{
					next();
					TokenList.add(index+1,newValue[i]);	
				}
			}
		}
	}
	
	/**
	 * Iterator method: Method to reset the iterator to the start of the stream
	 * next must be called to get a token
	 */
	public void reset() {
		//TODO: Implement this method
		index=-1;
	}
	
	/**
	 * Iterator method: Method to set the iterator to beyond the last token in the stream
	 * previous must be called to get a token
	 */
	public void seekEnd() {
		index=TokenList.size()-1;
	}
	
	/**
	 * Method to merge this stream with another stream
	 * @param other: The stream to be merged
	 */
	public void merge(TokenStream other) {
		//TODO: Implement this method
		if((other!=null)&&(other.TokenList.size()!=0))
		{
		for(int i=0;i<other.getAllTokens().size();i++)
		{
			TokenList.add(other.next());
		}
		}
	}
}
