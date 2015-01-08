package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.SPECIALCHARS)
public class SpecialCharRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{
			String token=null;
			/*ArrayList<String> split_tokens;
			String[] split_tok = null;*/
			stream.reset();
			while(stream.hasNext())
			{
				token=stream.next();
				stream.previous();
				
				/*while(token.startsWith("\"")&&token.indexOf("\"",1)==-1)
				{	
					stream.mergeWithNext();
					token=stream.next();
					stream.previous();
				}*/
				
				Pattern p=Pattern.compile("[~\\(\\)#@\\$%^=\\*&\\+:;<>\\|\\/\\\\_,\\?\\[\\]]"); 
				Matcher m = p.matcher(token);
				
				String newtoken = null;
				String[] newtokens = null;
				
				if(m.find())
				{
					newtoken = m.replaceAll(":");
				}
				
				
				if(newtoken!=null)
				{	
				Pattern p1=Pattern.compile("^[:]*"); 
				Matcher m1 = p1.matcher(newtoken);
				
				if(m1.find())
					newtoken=m1.replaceAll("");
				
				Pattern p2=Pattern.compile("[:]*$"); 
				Matcher m2 = p2.matcher(newtoken);
				
				if(m2.find())
					newtoken=m2.replaceAll("");
				
				}
				
				
				if(newtoken==null)
				{
					stream.next();
				}
				else if(newtoken.equals(""))
				{
					stream.remove();
				}	
				else if(newtoken.contains(":"))
				{
					newtokens = newtoken.split(":");
					stream.set(newtokens);
					stream.next();
				}
				else if(newtoken!=null)
				{	
					stream.set(newtoken);
					stream.next();
				}
					
				
				token=stream.previous();
				
				if(token.contains(".\""))
				{
					stream.set(token.replaceAll("\\.\"",""));
				}
				
				stream.next();
				
				
				token=stream.previous();
				
				if(token.contains("\""))
				{	
				stream.set(token.replaceAll("\"",""));
				}
				
				stream.next();
			
				
				
				/*//Pattern p1=Pattern.compile("^[~#$=&<>|\\-\\+\\(\\)_\\/]+[A-Z a-z 0-9]*");
				Pattern p1=Pattern.compile("^[~#$=&<>|\\+\\(\\)_\\/]+[A-Z a-z 0-9]*");
				Matcher m1=p1.matcher(token);
				
				Pattern p2=Pattern.compile("[A-Z a-z 0-9]+[;:,%\\(\\)]+"); //removed dot from this pattern
				Matcher m2=p2.matcher(token);
				
				//Pattern p3=Pattern.compile("[A-Z a-z \\.]+[\\+\\-\\*\\^@][A-Z a-z 0-9\\.]+");
				Pattern p3=Pattern.compile("[A-Z a-z \\.]+[\\+\\*\\^@][A-Z a-z 0-9\\.]+");
				Matcher m3=p3.matcher(token);
				
				//Pattern p7=Pattern.compile("[A-Z a-z 0-9 \\.]+[\\+\\-\\*\\^@][A-Z a-z \\.]+");
				Pattern p7=Pattern.compile("[A-Z a-z 0-9 \\.]+[\\+\\*\\^@][A-Z a-z \\.]+");
				Matcher m7=p7.matcher(token);
				
				Pattern p4=Pattern.compile("[A-Z a-z 0-9 \\.]+");
				Matcher m4=p4.matcher(token);
				
				Boolean pat1 = false,pat2 = false;
				
				if((pat1=m1.find()) | (pat2=m2.find()))
				{
					Pattern p5=Pattern.compile("^[~#\\$=&<>|\\+\\(\\)_\\/\\\\]+");
					Matcher m5=p5.matcher(token);
					
					if(pat1) token=m5.replaceAll("").trim();
					
					Pattern p6=Pattern.compile("[;:,%\\(\\)\\.]+$");
					Matcher m6=p6.matcher(token);
					
					
					if(pat2) token=m6.replaceAll("").trim();
					
					if(token.length()==0)
					{
						stream.remove();
					}	
					else
					{
						stream.set(token);
						stream.next();
					}	
				}
				else if(m3.find())
				{
					
					split_tokens=new ArrayList<String>();
					
					while(m4.find())
					{
						split_tokens.add(m4.group());
					}
					split_tok=split_tokens.toArray(new String[split_tokens.size()]);
					stream.set(split_tok);
					stream.next();
				}
				else if(m7.find())
				{
					
					split_tokens=new ArrayList<String>();
					
					while(m4.find())
					{
						split_tokens.add(m4.group());
					}
					split_tok=split_tokens.toArray(new String[split_tokens.size()]);
					stream.set(split_tok);
					stream.next();
				}
				else
				{
					stream.next();
				}*/
				
				/*if(token.contains("\""))
				{
					token=stream.previous();
					stream.set(token.replaceAll("\\.\"",""));
					stream.next();
					token=stream.previous();
					stream.set(token.replaceAll("\"",""));
					stream.next();
				}*/
				/*
				if(token.endsWith(","))
				{
					token=stream.previous();
					stream.set(token.replaceAll(",",""));
					stream.next();
				}
				
								
				token=stream.previous();
				
				Pattern p6=Pattern.compile("[A-Z a-z \\s][\\/][A-Z a-z \\s]");
				Matcher m6=p6.matcher(token);
				
				while(m6.find())
				{
					token=m6.replaceAll(" ");
				}	
				stream.set(token);
				
				stream.next();
				
				
				
				token=stream.previous();
				
				Pattern p5=Pattern.compile("[~#$=&<>|\\+\\(\\)_;:,%\\(\\)\\/]");
				Matcher m5=p5.matcher(token);
				
				while(m5.find())
				{
					token=m5.replaceAll("");
				}	
				stream.set(token);
				
				stream.next();
				
				
				split_tok=null;
				split_tokens=null;*/
			}
			stream.reset();
		}
	}
}	