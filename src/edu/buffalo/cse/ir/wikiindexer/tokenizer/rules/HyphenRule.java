package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.HYPHEN)
public class HyphenRule implements TokenizerRule
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{
			String token=null;
			
			stream.reset();
			while(stream.hasNext())
			{
				String newtoken1=null;
				String newtoken2=null;
				
				token=stream.next();
				stream.previous();
				
				//if(token.replaceAll("-","").trim().length()==0)
				//if(token.trim().equals("–")||token.trim().equals("-"))// – hyphen problem –-–- see the difference
				if(token.replaceAll("-","").trim().equals("")||token.replaceAll("–","").trim().equals(""))
				{	
					stream.remove();	
				}
				else if(token.startsWith("-")||token.endsWith("-")||token.startsWith("–")||token.endsWith("–"))
				{
					newtoken1=token.replaceAll("-","");
					newtoken2=newtoken1.replaceAll("–","").trim();
					stream.set(newtoken2);
					stream.next();
				}
				else if((token.contains("-")) && token.replaceAll("-","").matches("[A-Za-z \\s \\.]*"))
				{
					newtoken1=token.replaceAll("-"," ");
					stream.set(newtoken1);
					stream.next();
				}
				else if((token.contains("–")) && token.replaceAll("–","").matches("[A-Za-z \\s \\.]*"))
				{
					newtoken1=token.replaceAll("–"," ");
					stream.set(newtoken1);
					stream.next();
				}
				else if((token.contains("—")) && token.replaceAll("—","").matches("[A-Za-z \\s \\.]*"))
				{
					newtoken1=token.replaceAll("—"," ");
					stream.set(newtoken1);
					stream.next();
				}
				else
					stream.next();
			}					
		stream.reset();
		}
	}
}