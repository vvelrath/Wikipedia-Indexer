package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;



@RuleClass(className = RULENAMES.PUNCTUATION)
public class PunctuationRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{	
		while(stream.hasNext())
		{
			String token=stream.next();
			stream.previous();
			String newtoken=null;
						
			if(token.contains(". ")) newtoken=token.replaceAll("(\\.\\s)"," ");
			else if(token.contains("? ")) newtoken=token.replaceAll("(\\?\\s)"," ");
			else if(token.contains("! ")) newtoken=token.replaceAll("! "," ");
			else newtoken=token;
			
			stream.set(newtoken);
			
			token=stream.next();
			stream.previous();
			
			Pattern p1 = Pattern.compile("[\\?!\\.]+$");
			Matcher m1 = p1.matcher(token);
			
			if(m1.find()) token=m1.replaceAll("");
			
			stream.set(token);
			stream.next();
			
		}
		stream.reset();
		}
	}
}