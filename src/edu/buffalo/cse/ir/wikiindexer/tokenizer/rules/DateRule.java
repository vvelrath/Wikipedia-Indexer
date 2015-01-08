package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.DATES)
public class DateRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{
			String token=null;
			String token1=null;
			String token2=null;
			HashMap<String, String> month = new HashMap<String,String>();
			
			month.put("January","01");
			month.put("February","02");
			month.put("March","03");
			month.put("April","04");
			month.put("May","05");
			month.put("June","06");
			month.put("July","07");
			month.put("August","08");
			month.put("September","09");
			month.put("October","10");
			month.put("November","11");
			month.put("December","12");
			
			HashSet<String> day = new HashSet<String>();
			
			day.add("sunday");
			day.add("Sunday");
			day.add("monday");
			day.add("Monday");
			day.add("tuesday");
			day.add("Tuesday");
			day.add("wednesday");
			day.add("Wednesday");
			day.add("thursday");
			day.add("Thursday");
			day.add("friday");
			day.add("Friday");
			day.add("saturday");
			day.add("Saturday");
			
			//System.out.println("Hai1 "+stream.getAllTokens());
			stream.reset();
			
			
			while(stream.hasNext())
			{
				token=stream.next().trim();
				stream.previous();
				
				Boolean YearFlag_pat1=false;
				Boolean YearFlag_pat2=false;
				Boolean DayFlag_pat1=false;
				Boolean DayFlag_pat2=false;
				Boolean comma=false;
				Boolean dot=false;
				
				//System.out.println("Hai2 "+stream.getAllTokens());
				//System.out.println("TOken:"+token);
				if(month.containsKey(token))
				{
					//System.out.println("asadsdsds");
					//System.out.println("TOken333:"+token);
					
					if(stream.hasPrevious())
					{
						token1=stream.previous().trim();
						//System.out.println("TOken334:"+token);
					if(token1.matches("[\\d]{1,2}")&&(!token1.matches("[\\d]{3}"))&&token1.length()<=3)
					{
						//System.out.println("hai how are you"+token1);
						DayFlag_pat1=true;
						//if(token1.startsWith("0"))
							//token1=token1.trim().substring(0,1);
						//else
							//token1='0'+token1.trim().substring(0);
					}	
				
					stream.next();
					}
					stream.next();
					token2=stream.next().trim();
					
					if(token2.matches("[\\d]{1,2}[,]*")&&(!token2.matches("[\\d]{3}"))&&token2.length()<=3)
					{
						DayFlag_pat2=true;
					}
					//System.out.println("Dot:"+token2);
					if(token2.matches("[\\d]{4}[,\\.]*")&&token2.length()>=4&&token2.length()<=5)
					{
						//System.out.println("Dot:"+token2);
						
						YearFlag_pat1=true;
						if(token2.endsWith(",")) comma=true;
						
						if(token2.endsWith(".")) dot=true;
						token2=token2.substring(0,4);
						//System.out.println("Dot1:"+token2);
					}
					
					if(DayFlag_pat2)
					{
						token1=stream.next().trim();
					
						if(token1.matches("[\\d]{4}[,]*")&&token1.length()>=4&&token1.length()<=5)
						{
							YearFlag_pat2=true;
							if(token1.endsWith(",")) comma=true;
							if(token1.endsWith(".")) dot=true;
							token1=token1.substring(0,4);
							stream.previous();
							stream.mergeWithPrevious();
						}
						else
						{
							stream.previous();
							stream.previous();
							token1="1900";
						}	
					}
					
					if(DayFlag_pat1)
					{
						//System.out.println("tokentesting:"+token1);
						
						if(token1.startsWith("0")||token1.matches("[\\d]{2}[,]*"))
							token1=token1.substring(0,2);
						else
							token1="0".concat(token1.substring(0,1));
						
						if(comma) token1=token1.concat(",");
						//System.out.println("Dot "+dot );
						if(dot) token1=token1.concat(".");
					}
					
					if(DayFlag_pat2)
					{
						if(token2.startsWith("0")||token2.matches("[\\d]{2}[,]*")) 
							token2=token2.trim().substring(0,2);
						else
							token2="0".concat(token2.trim().substring(0,1));						
					
						if(comma) token2=token2.concat(",");
						if(dot) token2=token2.concat(".");
					}
					
					//System.out.println("111 "+stream.getAllTokens());
					if(DayFlag_pat1) stream.previous();
					else if(YearFlag_pat1&&!DayFlag_pat1) stream.previous();
					stream.mergeWithPrevious();
					if(DayFlag_pat1) stream.mergeWithPrevious();
					
					
					//System.out.println("222 "+stream.getAllTokens()+DayFlag_pat1+token1);
					
					if(DayFlag_pat1)	
						stream.set(token2.concat(month.get(token)).concat(token1));
					if(DayFlag_pat2)	
						stream.set(token1.concat(month.get(token)).concat(token2));
					if(YearFlag_pat1&&!DayFlag_pat1)
					{	
						token1="01";
						if(comma) token1="01.";
						if(dot) token1="01,";
						stream.set(token2.concat(month.get(token)).concat(token1));
					}
					//System.out.println("333 "+stream.getAllTokens()+DayFlag_pat1);
				
					stream.next();
					
					stream.previous();
					Boolean flag_tmp=false;
						
					Pattern p1=Pattern.compile("[\\d]{2}[:][\\d]{2}[:][\\d]{2}");
					Matcher m1;
					int i=0;
					//System.out.println("flag "+flag_tmp);				
					while(flag_tmp==false)
					{
						
						if(stream.hasPrevious()) token1=stream.previous().trim(); else break;
						//System.out.println("TOKEN1 "+token1);
						i++;
						
						m1=p1.matcher(token1);
						if(m1.find()) flag_tmp=true;
						else if(token1.equals("UTC")||token1.equals("on")||day.contains("Sunday")) flag_tmp=false;
						else break;
						//System.out.println("TOKEN1 "+token1);
					}
					//System.out.println("flag "+flag_tmp);
					if(flag_tmp==true)
					{	
						while(i>0)
						{
						stream.remove();
						i--;
						}
						token=stream.next().trim();
						
						if(token.endsWith(".")) token2=token.substring(0,token.indexOf("."));
						else if(token.endsWith(",")) token2=token.substring(0,token.indexOf(","));
						else if(token.endsWith(";")) token2=token.substring(0,token.indexOf(";"));
						else token2=token;
						
						stream.previous();
						
						if(token.endsWith(".")) stream.set(token2.concat(" ").concat(token1).concat("."));
						else if(token.endsWith(",")) stream.set(token2.concat(" ").concat(token1).concat(","));
						else if(token.endsWith(";")) stream.set(token2.concat(" ").concat(token1).concat(";"));
						else stream.set(token2.concat(" ").concat(token1));

					}
					else
					{
						while(i>0)
						{
						stream.next();
						i--;
						}
					}	
					stream.next();
					
				}
				else if(token.equals("BC")||token.equals("BC.")||token.equals("AD")||token.equals("AD."))
				{
					//System.out.println("asadsdsds");
					token1=stream.previous().trim();
					if(token1.matches("[\\d]+")) stream.remove();
					if(token1.length()==1)
						token1="000".concat(token1).concat("0101");
					else if(token1.length()==2)
						token1="00".concat(token1).concat("0101");
					else if(token1.length()==3)
						token1="0".concat(token1).concat("0101");
					else if(token1.length()==4)
						token1=token1.concat("0101");
					else if(token1.length()==8)
						token1=token1;
				
					if(token1.matches("[\\d]+"))
					{	
					if(token.equals("BC")||token.equals("BC."))
						token1="-".concat(token1);
					if(token.equals("BC.")||token.equals("AD."))
						token1=token1.concat(".");
					}
					else stream.next();
					
					if(token1.matches("[-]*[\\d]+[\\.]*")) stream.set(token1);
					else stream.set(token);
					
					stream.next();
				}
				else if((token.matches("[\\d]{4}[,\\.]{0,1}"))&&(!token.matches("[\\d]{5}[,\\.]{0,1}"))&&(token.length()<=5))
					{
					//System.out.println("asadsdsds");
					//System.out.println(token);
					if(token.contains("."))
						token=token.substring(0,token.indexOf(".")).concat("0101.");
					else if(token.contains(","))
						token=token.substring(0,token.indexOf(",")).concat("0101,");
					else if(token.contains(";"))
						token=token.substring(0,token.indexOf(";")).concat("0101;");
					else
						token=token.concat("0101");
					
					stream.set(token);
					stream.next();
					}	
				else if(token.matches("[\\d]{1,2}[:][\\d]{1,2}[\\s]*[apAP][Mm][.]*")||token.equalsIgnoreCase("AM")||token.equalsIgnoreCase("AM.")||token.equalsIgnoreCase("PM")||token.equalsIgnoreCase("PM."))
				{
					//System.out.println("asadsdsds");
					if(token.equalsIgnoreCase("AM")||token.equalsIgnoreCase("AM.")||token.equalsIgnoreCase("PM")||token.equalsIgnoreCase("PM."))
					{
						stream.mergeWithPrevious();
					}
					
					token=stream.next().trim();
					stream.previous();
					
					if(token.matches("[\\d]{1,2}[:][\\d]{1,2}[\\s]*[apAP][Mm][.]*"))
					{
						Pattern p1=Pattern.compile("[\\d]{1,2}[:][\\d]{1,2}");
						Matcher m1=p1.matcher(token);
						if(m1.find())
							token1=m1.group().concat(":00");
						
						if(token1.length()==7)
							token1="0".concat(token1);
						
						Pattern p2=Pattern.compile("[pP][Mm][.]*");
						Matcher m2=p2.matcher(token);
						
						
						int time=0;
						if(m2.find())
						{	
							time=Integer.parseInt(token1.substring(0,2));
							time=time+12;
							token1=time+token1.substring(2);
						}
						
						if(token.endsWith("."))
							token1=token1.concat(".");
						
						stream.set(token1);
						stream.next();
					}
						
				}
				else if(token.contains("�")&&(token.indexOf("�")==4)&&token.substring(0,4).matches("[\\d]{4}"))
				{
					//System.out.println("asadsdsds");
					token1=token.substring(0,token.indexOf("�"));
					if(token.endsWith(".")) token2=token.substring(token.indexOf("�")+1,token.indexOf("."));
					else if(token.endsWith(",")) token2=token.substring(token.indexOf("�")+1,token.indexOf(","));
					else if(token.endsWith(";")) token2=token.substring(token.indexOf("�")+1,token.indexOf(";"));
					else token2=token.substring(token.indexOf("�")+1);
					
					if(token2.length()==2)
						token2=token1.substring(0,2).concat(token2);
					
					if(token.endsWith(".")) stream.set(token1.concat("0101�").concat(token2).concat("0101."));
					else if(token.endsWith(",")) stream.set(token1.concat("0101�").concat(token2).concat("0101,"));
					else if(token.endsWith(";")) stream.set(token1.concat("0101�").concat(token2).concat("0101;"));
					else stream.set(token1.concat("0101�").concat(token2).concat("0101"));
				}	
				else
					stream.next();
				
			}
			stream.reset();
		}
	}

	
}