/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vvelrath
 * This class implements Wikipedia markup processing.
 * Wikipedia markup details are presented here: http://en.wikipedia.org/wiki/Help:Wiki_markup
 * It is expected that all methods marked "todo" will be implemented by students.
 * All methods are static as the class is not expected to maintain any state.
 */
public class WikipediaParser {
	
	
	
static String textFull;
	
	public static WikipediaDocument Initalizer(String text,WikipediaDocument mydoc)
	{
		
		textFull=text;
		String[] textandlink = null;
		Set<String> linksindoc = new HashSet<String>();
		
		String[] lines=textFull.split("\n");
		Pattern ListPat=Pattern.compile("(^\\*.*)|(^#.*)|(^\\:.*)|(^\\;.*)");
		
		textFull="";
		String textFull_tmp=null;
		
		for(int i=0;i<lines.length;i++)
		{
			Matcher matcher=ListPat.matcher(lines[i]);
			if(matcher.matches())
			{
				lines[i]=WikipediaParser.parseListItem(lines[i].trim());
			}
			textFull_tmp=null;
			textFull_tmp=textFull.concat(lines[i]).concat("\n");
			textFull=textFull_tmp;
		}

		textFull=WikipediaParser.parseTemplates(textFull);
		
		textFull=WikipediaParser.parseTagFormatting(textFull);
		
		textFull=WikipediaParser.parseTextFormatting(textFull);
		

		while(textFull.contains("[[Image:"))
		{
			int index11= textFull.indexOf("[[Image:");
			int braces=1;
			int index14=index11;
			int index15=index11;
			int index12=0,index13=0;
			do
			{
			index12= textFull.indexOf("[[",index14+1);
			index13= textFull.indexOf("]]",index15+1);
			if ((index12>0) && (index12<index13))
				braces++;
			braces--;
			index14=index12;
			index15=index13;
			
			}while(braces!=0);
	
			textFull_tmp=null;
			textFull_tmp=textFull.substring(0,index11).concat(textFull.substring(index13+2));
			textFull=textFull_tmp;
		}
		
		while(textFull.contains("[[image:"))
		{
			int index11= textFull.indexOf("[[image:");
			int braces=1;
			int index14=index11;
			int index15=index11;
			int index12=0,index13=0;
			do
			{
			index12= textFull.indexOf("[[",index14+1);
			index13= textFull.indexOf("]]",index15+1);
			if ((index12>0) && (index12<index13))
				braces++;
			braces--;
			index14=index12;
			index15=index13;
			
			}while(braces!=0);
	
			textFull_tmp=null;
			textFull_tmp=textFull.substring(0,index11).concat(textFull.substring(index13+2));
			textFull=textFull_tmp;
		}
		
		
		while(textFull.contains("[[File:"))
		{
			int index11= textFull.indexOf("[[File:");
			int braces=1;
			int index14=index11;
			int index15=index11;
			int index12=0,index13=0;
			do
			{
			index12= textFull.indexOf("[[",index14+1);
			index13= textFull.indexOf("]]",index15+1);
			if ((index12>0) && (index12<index13))
				braces++;
			braces--;
			index14=index12;
			index15=index13;
			
			}while(braces!=0);
	
			textFull_tmp=null;
			textFull_tmp=textFull.substring(0,index11).concat(textFull.substring(index13+2));
			textFull=textFull_tmp;			
		}

		
		while(textFull.contains("[[file:"))
		{
			int index11= textFull.indexOf("[[file:");
			int braces=1;
			int index14=index11;
			int index15=index11;
			int index12=0,index13=0;
			do
			{
			index12= textFull.indexOf("[[",index14+1);
			index13= textFull.indexOf("]]",index15+1);
			if ((index12>0) && (index12<index13))
				braces++;
			braces--;
			index14=index12;
			index15=index13;
			
			}while(braces!=0);
	
			textFull_tmp=null;
			textFull_tmp=textFull.substring(0,index11).concat(textFull.substring(index13+2));
			textFull=textFull_tmp;
		}

		while(textFull.contains("[[")&&textFull.contains("]]"))
		{
			int index1=textFull.indexOf("[[");
			int index2=textFull.indexOf("]]",index1);
			String linktoParse = textFull.substring(index1,index2+2);
			textandlink=WikipediaParser.parseLinks(linktoParse);

			textFull_tmp=null;
			textFull_tmp=textFull.substring(0,index1).concat(textandlink[0]).concat(textFull.substring(index2+2));
			textFull=textFull_tmp;
			
			if(linktoParse.contains("[[Category:")) mydoc.addCategory(textandlink[0]);
			else {if(textandlink[1].trim().length()>0) linksindoc.add(textandlink[1]);}
		}
		
		mydoc.addLInks(linksindoc);
		
		String linktoParse=null;
		
		while(textFull.contains("[http"))
		{
			int index1=textFull.indexOf("[http");
			int index2=textFull.indexOf("]",index1);
			int index3=textFull.indexOf("[http",index1+1);
			
			if((index2<index3)||index3==-1) linktoParse = textFull.substring(index1,index2+1);
			else linktoParse = textFull.substring(index1,index3);
			
			textandlink=WikipediaParser.parseLinks(linktoParse);
			
			if((index2<index3)||index3==-1) 
			{
				textFull_tmp=null;
				textFull_tmp=textFull.substring(0,index1).concat(textandlink[0]).concat(textFull.substring(index2+2));
				textFull=textFull_tmp;
			}	
			else
			{
				textFull_tmp=null;
				textFull_tmp=textFull.substring(0,index1).concat(textandlink[0]).concat(textFull.substring(index3));
				textFull=textFull_tmp;
			}	
		}
		
		if(textFull.contains("[")&&textFull.contains("]"))
		{
			String temp_text=null;
			temp_text=textFull.replaceAll("[\\[\\]]","");
			textFull=temp_text;
		}
		
		Pattern tag3=Pattern.compile("[&][^&;]*[;]");	
		
		Matcher m3=tag3.matcher(textFull);
		
		while(m3.find())
		{
			textFull=m3.replaceAll(" ");
		}
		
		WikipediaParser.sectionSpliter(textFull,mydoc);
		
		return mydoc;
	}
	
	
	private static  void sectionSpliter(String fullText,WikipediaDocument mydoc)
	{
		String[] lines=fullText.split("\n");
		String temp="";
		Pattern myPattern=Pattern.compile("=+(.*)(?<!=)=+",Pattern.DOTALL);
		String sectionName="Default";
		for(int i=0;i<lines.length;i++)
		{
			Matcher matcher=myPattern.matcher(lines[i]);
			if(matcher.matches())
			{ 
				mydoc.addSection(sectionName, temp);
				sectionName=WikipediaParser.parseSectionTitle(lines[i]);
				temp="";
				i=i+1;		
			}
			temp=temp.concat(lines[i]).concat("\n");
		}
		mydoc.addSection(sectionName, temp);
	}
	
	
	/* TODO */
	/**
	 * Method to parse section titles or headings.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * @param titleStr: The string to be parsed
	 * @return The parsed string with the markup removed
	 */
	public static String parseSectionTitle(String titleStr) {
		if(titleStr == null)
			return null;
		else
		{	
		String secTitle=null;
		secTitle=titleStr.replaceAll("=", "").trim();
		return secTitle;
		}
	}
	
	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * @param itemText: The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		if(itemText==null)
			return null;
		else
		{
		String itemtext_tmp=null;
		
		while(itemText.startsWith("*")) { itemtext_tmp=itemText.replace("*",""); itemText=itemtext_tmp;} 
		while(itemText.startsWith("#")) { itemtext_tmp=itemText.replace("#",""); itemText=itemtext_tmp;}
		while(itemText.startsWith(":")) { itemtext_tmp=itemText.replace(":",""); itemText=itemtext_tmp;}
		while(itemText.startsWith(";")) { itemtext_tmp=itemText.replace(";",""); itemText=itemtext_tmp;}
		
		/*while(itemText.startsWith("*")) itemText=itemText.replace("*","");
		while(itemText.startsWith("#")) itemText=itemText.replace("#","");
		while(itemText.startsWith(":")) itemText=itemText.replace(":","");
		while(itemText.startsWith(";")) itemText=itemText.replace(";","");*/
		return itemText.trim();
		}
	}
	
	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String text) {
		if (text==null)
			return null;
		else
		{
			String text_temp=null;
			
			text_temp=text.replaceAll("'''''","");
			text=text_temp;
			
			text_temp=text.replaceAll("''''","");
			text=text_temp;
			
			text_temp=text.replaceAll("'''","");
			text=text_temp;
			
			text_temp=text.replaceAll("''","");
			text=text_temp;
			
			return text;
		}
	}
	
	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz>
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String text) {
		if(text==null)
			return null;
		else
		{		
			Pattern tag7=Pattern.compile("[<][b][r][\\s]*[\\/]*[>]");
			
			Matcher m7;
			
			m7=tag7.matcher(text);
			
			while(m7.find())
			{
				text=m7.replaceAll("");
			}
			
			Pattern tag6=Pattern.compile("[<][r][e][f][^<>]*[>][^<>]*[<][\\/][r][e][f][>]");
			
			Matcher m6;
			
			m6=tag6.matcher(text);
			
			while(m6.find())
			{
				text=m6.replaceAll("");
			}
				
			Pattern tag5=Pattern.compile("[\\<][^\\<\\>]*[\\>]");
			
			Matcher m;
			
			m=tag5.matcher(text);
			
			while(m.find())
			{
				text=m.replaceAll("");
			}
			
			
			Pattern tag4=Pattern.compile("[\\&][l][t][\\;][a-zA-z 0-9 \\s \\= \\' \\/ \\\" \\& \\- \\! \\: \\] \\[ \\*]*[g][t][\\;]");	
				
			m=tag4.matcher(text);
			
			while(m.find())
			{
				text=m.replaceAll("");
			}
			
			
			
			return text.replaceAll("  "," ").trim();
		}
	}
	
	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags
	 * For most cases, simply removing the tags should work.
	 * @param text: The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String text) {		
		
			Pattern TempPat1=Pattern.compile("[\\{]{2}[^\\{\\}]+[\\{][^\\{\\}]*[\\}][^\\{\\}]+[\\}]{2}");
			Matcher m1;

			m1=TempPat1.matcher(text);
			while(m1.find())
			{
				text=m1.replaceAll("");
			}
			
			Pattern TempPat2=Pattern.compile("[\\{]{2}[^\\{\\}]+[\\{][^\\{\\}]+[\\}]{2}");
			Matcher m2;

			m2=TempPat2.matcher(text);
			while(m2.find())
			{
				text=m2.replaceAll("");
			}
			
			Pattern TempPat3=Pattern.compile("[\\{]{2}[^\\{\\}]+[\\}][^\\{\\}]+[\\}]{2}");
			Matcher m3;

			m3=TempPat3.matcher(text);
			while(m3.find())
			{
				text=m3.replaceAll("");
			}
			
			
			Pattern TempPat=Pattern.compile("[\\{]{2}[^\\{\\}]*[\\}]{2}");
			Matcher m;

			while(text.contains("{{")&&text.contains("}}"))
			{
				m=TempPat.matcher(text);
				while(m.find())
				{
					text=m.replaceAll("");				
				}
			}
			
			Pattern TempPat5=Pattern.compile("[\\{][^\\{\\}]*[\\}]");
			Matcher m5;

			while(text.contains("{")&&text.contains("}"))
			{
				m5=TempPat5.matcher(text);
				while(m5.find())
				{
					text=m5.replaceAll("");				
				}
			}
			
			return text;
	}
	
	
	/* TODO */
	/**
	 * Method to parse links and URLs.
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * @param text: The text to be parsed
	 * @return An array containing two elements as follows - 
	 *  The 0th element is the parsed text as visible to the user on the page
	 *  The 1st element is the link url
	 */
	public static String[] parseLinks(String text) {
		String par_txt="",link="";
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		int tex_len=0;
		
		if ((text==null) || (text==""))
		{
			return new String[]{"",""};
		}
				
		index1=text.indexOf("[[");
		index2=text.indexOf("|",index1);
		index3=text.indexOf("]]",index1);
		tex_len=text.length();		

		
		Pattern p7=Pattern.compile("[\\[]{2}[:]*[C][a][t][e][g][o][r][y][:]");	
		Matcher m7=p7.matcher(text);
		Pattern p4=Pattern.compile("[\\[]{2}[W][i][k][i][p][e][d][i][a][:]");	
		Matcher m4=p4.matcher(text);
		Pattern p5=Pattern.compile("[\\[]{2}[W][i][k][t][i][o][n][a][r][y][:]");	
		Matcher m5=p5.matcher(text);
		Pattern p6=Pattern.compile("[\\[]{2}[a-z]{2}[:]");	
		Matcher m6=p6.matcher(text);
		Pattern p2=Pattern.compile("[\\[]{2}[^\\(\\)|#,]+[\\]]{2}");	
		Matcher m2=p2.matcher(text);
		Pattern p3=Pattern.compile("[\\[]{2}(.*)[\\(,\\)](.*)[\\]]{2}");	
		Matcher m3=p3.matcher(text);
		
		
		if(text.contains("[[media:") ||text.contains("[[File:"))
		{			
			int index4=text.lastIndexOf("|");
			
			if(index1>0)
			{
				par_txt=text.substring(0,index1);
			}
			
			if(index4>0)
			{
				par_txt=par_txt.concat(text.substring(index4+1, index3));
			}
			
			if((index3<tex_len-1)&&index3>0)
			{
				par_txt=par_txt.concat(text.substring(index3+2,tex_len));
			}
		}
		
		else if(text.contains("[http"))
		{
			index1=0;
			index2=0;
			index3=0;
			index1=text.indexOf("[");
			index2=text.indexOf("|",index1);
			index3=text.indexOf("]",index1);
			tex_len=text.length();
			
			int index4=text.indexOf(" ",index1);
			
			if(index1>0)
			{
				par_txt=text.substring(0,text.indexOf("["));
			}
			
			if(index4>0)
			{
			if(index3>0)  par_txt=par_txt.concat(text.substring(index4+1, index3));
			else par_txt=par_txt.concat(text.substring(index4+1));
			}
			
			if((index3<tex_len-1)&&index3>0)
			{
			    par_txt=par_txt.concat(text.substring(index3+1,tex_len));
			}
		}
		
		else if(m7.find())
		{
			int index4=0;
			int index5=0;
			int index6=0;
	
			index4=text.indexOf(":",index1);
			index5=text.indexOf("|",index1);
			index6=text.lastIndexOf(":",index1);
			
			if(index1>0)
			{
				par_txt=text.substring(0,index1);
			}
			
			if(text.contains("|]]"))
				par_txt=par_txt.concat(text.substring(index6+1,index5));
			else if(index5>0)
				par_txt=par_txt.concat(text.substring(index5+1,index3));
			else
				par_txt=par_txt.concat(text.substring(index4+1,index3));
			
			if((index3<tex_len-1)&&index3>0)
			{
				par_txt=par_txt.concat(text.substring(index3+2,tex_len));
			}
			
		}
		
		else if(m4.find())
		{
			int index4=text.indexOf(":",index1);
			int index5=text.indexOf("(",index1);
			int index6=text.indexOf("#",index1);
			
			if(index1>0)
			{
				par_txt=text.substring(0,text.indexOf("[["));
			}
			
			
			if(text.contains("|")&&!text.contains("|]]"))
				par_txt=par_txt.concat(text.substring(index2+1,index3));
			else if(index6>0)
			{	
				if(index2>0)  par_txt=par_txt.concat(text.substring(index1+2,index2));
				else par_txt=par_txt.concat(text.substring(index1+2,index3));
			}
			else if(index5>0)
				par_txt=par_txt.concat(text.substring(index4+1,index5));
			else if(text.contains("|]]"))
				par_txt=par_txt.concat(text.substring(index4+1,index2));
			else
				par_txt=par_txt.concat(text.substring(index1+2,index3));
			
				
			if((index3<tex_len-1)&&index3>0)
			{
				par_txt=par_txt.concat(text.substring(index3+2,tex_len));
			}	
		}
		
		else if(m5.find())
		{
			if(index1>0)
			{
				par_txt=text.substring(0,text.indexOf("[["));
			}
			
			if(text.contains("|]]"))
				par_txt=par_txt.concat(text.substring(text.indexOf(":")+1,index2));
			else if(text.contains("|"))
				par_txt=par_txt.concat(text.substring(index2+1,index3));
			else
				par_txt=par_txt.concat(text.substring(index1+2,index3));
		
			if((index3<tex_len-1)&&index3>0)
			{
				par_txt=par_txt.concat(text.substring(index3+2,tex_len));
			}
		}
		
		else if(m6.find()||text.contains("simple:"))
		{
			String partxt_temp=null;
			
			partxt_temp=text.replaceAll("[\\[]{2}","");
			par_txt=partxt_temp;
			
			partxt_temp=par_txt.replaceAll("[\\]]{2}","");
			par_txt=partxt_temp;
			
			partxt_temp=par_txt.replaceAll("\\|","");
			par_txt=partxt_temp;			
		}
		
		else if(m2.find())
		{
			String partxt_temp=null;
			
			partxt_temp = text.replaceAll("[\\[]{2}","");
			par_txt=partxt_temp;
			
			partxt_temp = par_txt.replaceAll("[\\]]{2}","");
			par_txt=partxt_temp;
			link=text.substring(index1+2, index3);
		}
		
		else if(text.contains("|")&&!text.contains("|]]"))
		{
			if(index1>0)
			{
				par_txt=text.substring(0,index1);
			}

			String partxt_temp=null;
			partxt_temp=par_txt.concat(text.substring(index2+1, index3));
			par_txt=partxt_temp;
			
			link=text.substring(index1+2, index2);
			
			if((index3<tex_len-1)&&index3>0)
			{
				partxt_temp=null;
				partxt_temp=par_txt.concat(text.substring(index3+2,tex_len));
				par_txt=partxt_temp;
			}
		}
		
		else if(m3.find())
		{
			int index4=text.indexOf("(",index1);
			int index5=text.indexOf(",",index1);
			
			if(index1>0)
			{
				par_txt=text.substring(0,index1);
			}
			
			String partxt_temp=null;
			
			if(index4>0)
			{	
				partxt_temp=null;
				partxt_temp=par_txt.concat(text.substring(index1+2,index4));
				par_txt=partxt_temp;
			}
			else if(index5>0)
			{
				partxt_temp=null;
				partxt_temp=par_txt.concat(text.substring(index1+2,index5));
				par_txt=partxt_temp;
			}
			
			if((index3<tex_len-1)&&index3>0)
			{
				partxt_temp=null;
				partxt_temp=par_txt.concat(text.substring(index3+2,tex_len));
				par_txt=partxt_temp;
			}			
			
			if(index2>0) link=text.substring(index1+2,index2);
			else link=text.substring(index1+2,index3);
		}

		else
		{
			par_txt="";
			link="";
		}	
		
		
		
		if(link.length()>0)
		{	
			String link_temp=null;
			
			link_temp=Character.toUpperCase(link.charAt(0)) + link.substring(1);
			link=link_temp;
			
			link_temp=link.replaceAll(" ", "_");
			link=link_temp;
		}
		
		
		if(par_txt.length()>0)
		{
			String partxt_temp=null;
			partxt_temp = par_txt.replaceAll("<[A-Z a-z \\/]+>","");
			par_txt=partxt_temp;
		}	
		
		return new String[]{par_txt.trim(),link.trim()};
	}	
}
