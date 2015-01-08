package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;


import java.util.HashMap;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.APOSTROPHE)
public class ApostropheRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{
			String token=null;
			
			HashMap<String, String[]> contr = new HashMap<String, String[]>();
			
			contr.put("isn't", new String[]{"is","not"});
			contr.put("aren't", new String[]{"are","not"});
			contr.put("wasn't", new String[]{"was","not"});
			contr.put("weren't", new String[]{"were","not"});
			
			contr.put("haven't", new String[]{"are","not"});
			contr.put("hasn't", new String[]{"was","not"});
			contr.put("hadn't", new String[]{"were","not"});
			
			contr.put("won't", new String[]{"will","not"});
			contr.put("wouldn't", new String[]{"would","not"});
			
			contr.put("can't",new String[]{"cannot"});
			contr.put("couldn't",new String[]{"could","not"});
			
			contr.put("don't",new String[]{"do","not"});
			contr.put("doesn't",new String[]{"does","not"});
			contr.put("didn't",new String[]{"did","not"});
			contr.put("won't",new String[]{"will","not"});
			contr.put("shan't",new String[]{"shall","not"});
			contr.put("let's",new String[]{"let","us"});
			contr.put("I'm",new String[]{"I","am"});
			contr.put("we're",new String[]{"we","are"});
			contr.put("they're",new String[]{"they","are"});
			contr.put("I've",new String[]{"I","have"});
			contr.put("Should've",new String[]{"Should","have"});
			contr.put("Should'nt",new String[]{"Should","not"});
			contr.put("Mustn't",new String[]{"Must","not"});
			contr.put("They'd",new String[]{"They","would"});
			contr.put("She'll",new String[]{"She","will"});
			contr.put("'em",new String[]{"them"});
			
			contr.put("I'll",new String[]{"I","will"});
			contr.put("I'd",new String[]{"I","would"});
			contr.put("you're",new String[]{"you","are"});
			contr.put("you'll",new String[]{"you","will"});
			contr.put("you've",new String[]{"you","have"});
			contr.put("he's",new String[]{"he","is"});
			contr.put("she's",new String[]{"she","is"});
			contr.put("he'll",new String[]{"he","will"});
			contr.put("it's",new String[]{"it","is"});
			contr.put("we're",new String[]{"we","are"});
			contr.put("we'll",new String[]{"we","will"});
			contr.put("we've",new String[]{"we","have"});
			contr.put("they're",new String[]{"they","are"});
			contr.put("they'll",new String[]{"they","will"});
			contr.put("they've",new String[]{"they","have"});
			contr.put("That's",new String[]{"That","is"});
			contr.put("who'll",new String[]{"who","will"});
			contr.put("who'd",new String[]{"who","would"});
			
			

			
			
			stream.reset();
			
			while(stream.hasNext())
			{
				token=stream.next();
				stream.previous();
				
				String newtoken1=null;
				String newtoken2=null;
				
				if(token.contains("'")||token.contains("’"))
				{	
					//System.out.println(token);
					if(contr.containsKey(token))
					{
						stream.set(contr.get(token));
					}
					else if(token.contains("'s")||token.contains("’s"))
					{
						//System.out.println(token);
						newtoken1=token.replaceAll("'s","");
						newtoken2=newtoken1.replaceAll("’s","");
						stream.set(newtoken2);
					}	
					else
					{
						newtoken1=token.replaceAll("'","");
						newtoken2=newtoken1.replaceAll("’","");
						stream.set(newtoken1);
					}
					stream.next();
				}
				else
					stream.next();
			}
			stream.reset();
		}
	}
}