package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.CAPITALIZATION)
public class CapitalizationRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		//int x=0;
		boolean start=true;
		boolean PreviousCap=false;
		boolean CurrentCap=false;
		//boolean NextCap=false;
		
		if(stream!=null)
		{
			String token=null;
			stream.reset();
			while(stream.hasNext())
			{
				if(start==true)
				{
				token=stream.next();
				stream.previous();
				
				if((token.endsWith(".")&&token.length()>2)||token.endsWith("!")||token.endsWith("?"))
					start=true;
				else	
					start=false;
				
				token=Character.toLowerCase(token.charAt(0))+token.substring(1);
				stream.set(token);
				stream.next();
				}
				else 
				{
					if(stream.index<stream.getAllTokens().size()-2)
					{
					String previous=stream.previous();
					stream.next();
					String current=stream.next();
					stream.previous();
						
					if((current.endsWith(".")&&current.length()>2)||current.endsWith("!")||current.endsWith("?"))
						start=true;
					
					
					//for(int i=0;i<previous.length();i++)
					//{
						if(Character.isUpperCase(previous.charAt(0))&&(!previous.endsWith(",")))
						{
							PreviousCap=true;
							//break;
						}
					//}
					
					if(PreviousCap)
					{
						//for(int i=0;i<current.length();i++)
						//{
							if(Character.isUpperCase(current.charAt(0)))
							{
								CurrentCap=true;
								//break;
							}
						//}
					}
					
					if(PreviousCap&&CurrentCap)
					{
						stream.mergeWithPrevious();	
					}
					//x++;
					PreviousCap=false;
					CurrentCap=false;
					stream.next();
					
					}
					else
					{
						break;
					}
				}
			}
			stream.reset();
		}
	}
}