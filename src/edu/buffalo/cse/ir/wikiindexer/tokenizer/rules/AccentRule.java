package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;


@RuleClass(className = RULENAMES.ACCENTS)
public class AccentRule implements TokenizerRule 
{
	public void apply(TokenStream stream) throws TokenizerException
	{
		if(stream!=null)
		{	
			String token=null;
			stream.reset();
			char[] char_tokens;
			HashMap<String,String> char_map = new HashMap<String,String>();
			
			char_map.put("À","A");
			char_map.put("Á","A");
			char_map.put("Â","A");
			char_map.put("Ã","A");
			char_map.put("Ä","A"); 
			char_map.put("Å","A");
			char_map.put("Æ","AE");
			char_map.put("Ç","C");
			char_map.put("È","E");
			char_map.put("É","E");
			char_map.put("Ê","E");
			char_map.put("Ë","E");
			
			char_map.put("Ì","I");
			char_map.put("Í","I");
			char_map.put("Î","I");
			char_map.put("Ï","I");
			
			char_map.put("Ð","D");
			char_map.put("Ñ","N");
			char_map.put("Ò","O");
			char_map.put("Ó","O");
			char_map.put("Ô","O");
			char_map.put("Õ","O");
			char_map.put("Ö","O");
			char_map.put("Ø","O");
			char_map.put("Ù","U");
			char_map.put("Ú","U");
			char_map.put("Û","U");
			char_map.put("Ü","U");
			char_map.put("Ý","Y");
			
			char_map.put("ß","s"); 
			char_map.put("à","a"); 
			char_map.put("á","a"); 
			char_map.put("â","a"); 
			char_map.put("ã","a"); 
			char_map.put("ä","a"); 
			char_map.put("å","a"); 
			char_map.put("æ","ae"); 
			char_map.put("ç","c"); 
			char_map.put("è","e"); 
			char_map.put("é","e"); 
			char_map.put("ê","e"); 
			char_map.put("ë","e"); 
			char_map.put("ì","i"); 
			char_map.put("í","i"); 
			char_map.put("î","i"); 
			char_map.put("ï","i");
			
			char_map.put("ñ","n"); 
			char_map.put("ò","o"); 
			char_map.put("ó","o"); 
			char_map.put("ô","o"); 
			char_map.put("õ","o"); 
			char_map.put("ö","o"); 
			char_map.put("ø","o"); 
			char_map.put("ù","u"); 
			char_map.put("ú","u"); 
			char_map.put("û","u"); 
			char_map.put("ü","u"); 
			char_map.put("ý","y"); 
			char_map.put("ÿ","y"); 
			char_map.put("Ā","A"); 
			char_map.put("ā","a"); 
			char_map.put("Ă","A"); 
			char_map.put("ă","a"); 
			char_map.put("Ą","A"); 
			char_map.put("ą","a");
			
			char_map.put("Ć","C"); 
			char_map.put("ć","c"); 
			char_map.put("Ĉ","C"); 
			char_map.put("ĉ","c"); 
			char_map.put("Ċ","C"); 
			char_map.put("ċ","c"); 
			char_map.put("Č","C"); 
			char_map.put("č","c"); 
			char_map.put("Ď","D"); 
			char_map.put("ď","d"); 
			char_map.put("Đ","D"); 
			char_map.put("đ","d"); 
			char_map.put("Ē","E"); 
			char_map.put("ē","e"); 
			char_map.put("Ĕ","E"); 
			char_map.put("ĕ","e"); 
			char_map.put("Ė","E"); 
			char_map.put("ė","e"); 
			char_map.put("Ę","E"); 
			char_map.put("ę","e"); 
			char_map.put("Ě","E"); 
			char_map.put("ě","e");
			
			char_map.put("Ĝ","G");
			char_map.put("ĝ","g"); 
			char_map.put("Ğ","G"); 
			char_map.put("ğ","g"); 
			char_map.put("Ġ","G"); 
			char_map.put("ġ","g"); 
			char_map.put("Ģ","G"); 
			char_map.put("ģ","g"); 
			char_map.put("Ĥ","H"); 
			char_map.put("ĥ","h"); 
			char_map.put("Ħ","H"); 
			char_map.put("ħ","h"); 
			char_map.put("Ĩ","I"); 
			char_map.put("ĩ","i"); 
			char_map.put("Ī","I"); 
			char_map.put("ī","i"); 
			char_map.put("Ĭ","I"); 
			char_map.put("ĭ","i"); 
			char_map.put("Į","I"); 
			char_map.put("į","i"); 
			char_map.put("İ","I"); 
			char_map.put("ı","i");
					
			char_map.put("Ĳ","IJ"); 
			char_map.put("ĳ","ij"); 
			char_map.put("Ĵ","J"); 
			char_map.put("ĵ","j"); 
			char_map.put("Ķ","K"); 
			char_map.put("ķ","k"); 
			char_map.put("Ĺ","L"); 
			char_map.put("ĺ","l"); 
			char_map.put("Ļ","L"); 
			char_map.put("ļ","l"); 
			char_map.put("Ľ","L"); 
			char_map.put("ľ","l"); 
			char_map.put("Ŀ","L"); 
			char_map.put("ŀ","l"); 
			char_map.put("Ł","l"); 
			char_map.put("ł","l"); 
			char_map.put("Ń","N"); 
			char_map.put("ń","n"); 
			char_map.put("Ņ","N"); 
			char_map.put("ņ","n"); 
			char_map.put("Ň","N"); 
			char_map.put("ň","n"); 
			char_map.put("ŉ","n");
			
			char_map.put("Ō","O"); 
			char_map.put("ō","o"); 
			char_map.put("Ŏ","O"); 
			char_map.put("ŏ","o"); 
			char_map.put("Ő","O"); 
			char_map.put("ő","o"); 
			char_map.put("Œ","OE"); 
			char_map.put("œ","oe"); 
			char_map.put("Ŕ","R"); 
			char_map.put("ŕ","r"); 
			char_map.put("Ŗ","R"); 
			char_map.put("ŗ","r"); 
			char_map.put("Ř","R"); 
			char_map.put("ř","r"); 
			char_map.put("Ś","S"); 
			char_map.put("ś","s"); 
			char_map.put("Ŝ","S"); 
			char_map.put("ŝ","s"); 
			char_map.put("Ş","S"); 
			char_map.put("ş","s"); 
			char_map.put("Š","S"); 
			char_map.put("š","s");
			
			char_map.put("Ţ","T"); 
			char_map.put("ţ","t"); 
			char_map.put("Ť","T"); 
			char_map.put("ť","t"); 
			char_map.put("Ŧ","T"); 
			char_map.put("ŧ","t"); 
			char_map.put("Ũ","U"); 
			char_map.put("ũ","u"); 
			char_map.put("Ū","U"); 
			char_map.put("ū","u"); 
			char_map.put("Ŭ","U"); 
			char_map.put("ŭ","u"); 
			char_map.put("Ů","U"); 
			char_map.put("ů","u"); 
			char_map.put("Ű","U"); 
			char_map.put("ű","u"); 
			char_map.put("Ų","U"); 
			char_map.put("ų","u"); 
			
			char_map.put("Ŵ","W"); 
			char_map.put("ŵ","w"); 
			char_map.put("Ŷ","Y"); 
			char_map.put("ŷ","y"); 
			char_map.put("Ÿ","Y"); 
			char_map.put("Ź","Z"); 
			char_map.put("ź","z"); 
			char_map.put("Ż","Z"); 
			char_map.put("ż","z"); 
			char_map.put("Ž","Z"); 
			char_map.put("ž","z"); 
			char_map.put("ſ","s"); 
			char_map.put("ƒ","f"); 
			char_map.put("Ơ","O"); 
			char_map.put("ơ","o"); 
			char_map.put("Ư","U"); 
			char_map.put("ư","u"); 
			char_map.put("Ǎ","A"); 
			char_map.put("ǎ","a"); 
			char_map.put("Ǐ","I"); 
			char_map.put("ǐ","i"); 
			char_map.put("Ǒ","O"); 
			char_map.put("ǒ","o"); 
			char_map.put("Ǔ","U"); 
			char_map.put("ǔ","u"); 
			char_map.put("Ǖ","U"); 
			char_map.put("ǖ","u"); 
			char_map.put("Ǘ","U"); 
			char_map.put("ǘ","u"); 
			char_map.put("Ǚ","U"); 
			char_map.put("ǚ","u"); 
			char_map.put("Ǜ","U"); 
			char_map.put("ǜ","u"); 
			char_map.put("Ǻ","A"); 
			char_map.put("ǻ","a"); 
			char_map.put("Ǽ","AE"); 
			char_map.put("ǽ","ae"); 
			char_map.put("Ǿ","O"); 
			char_map.put("ǿ","o");
			
			char_map.put("а̀","a");
			char_map.put("Þ","TH");
			char_map.put("п", "n");
			
				
			while(stream.hasNext())
			{
				token=stream.next();
				stream.previous();
				StringBuilder newtoken = new StringBuilder();
				token=token.replaceAll("а̀","a");
				token=token.replaceAll("п","n");
				char_tokens=token.toCharArray();
				
				for(int i=0;i<char_tokens.length;i++)
				{
					String stringchar=Character.toString(char_tokens[i]);
					if(char_map.containsKey(stringchar))
					{
						newtoken.append(char_map.get(stringchar));
						//System.out.println("1:"+char_tokens[i]+" "+char_map.get(stringchar));
					}
					else
					{
						newtoken.append(char_tokens[i]);
						//System.out.println("2:"+char_tokens[i]);
					}	
				}
								
				//token = Normalizer.normalize(token, Normalizer.Form.NFD);
				//System.out.println(token);
				token=newtoken.toString().replace("а̀","a");
				stream.set(token);
				stream.next();
			}
			
			stream.reset();
		}
	}
}