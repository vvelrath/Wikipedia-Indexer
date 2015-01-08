package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.DELIM)
public class DelimiterRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{
			String token=null;
			String newtoken=null;
			String[] newtokens = null;
			stream.reset();
			while(stream.hasNext())
			{
				token=stream.next();
				stream.previous();
				newtoken=token.replaceAll("[,;:]+"," ").trim();
				newtokens = newtoken.split(" ");
				stream.set(newtokens);
				stream.next();
			}					
			stream.reset();
		}
	}
}