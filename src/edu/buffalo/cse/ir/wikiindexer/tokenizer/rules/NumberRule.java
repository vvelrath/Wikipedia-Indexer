package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;


import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.NUMBERS)
public class NumberRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{	
			String token=null;
			stream.reset();
			String newtoken=null;
			
			while(stream.hasNext())
			{
				token=stream.next().trim();
				stream.previous();
				newtoken=null;
		
				if(!token.matches("[0-9]{8}")&&!token.matches("[0-9]{9}")&&!token.matches("[0-9]{8}[\\s][0-9]{2}[:][0-9]{2}[:][0-9]{2}"))
				{	
					if(token.matches("[0-9]+[.][0-9]+[%]")||token.matches("[0-9]+[/][0-9]+"))
					{
						newtoken=token.replaceAll("[0-9]+[.]*[0-9]+","");
						stream.set(newtoken);
						stream.next();
					}
					else if(token.matches("[0-9]+[,]*[0-9]+"))
					{
						stream.remove();		
					}
					else if(token.matches("[0-9]+[.][0-9]+"))
					{
						stream.remove();		
					}
					else if(token.matches("[0-9]{1}")&&token.length()==1)
					{
						stream.remove();		
					}
					else
						stream.next();
				}
				else
					stream.next();
				
			}
			stream.reset();
			
		}
	}
}