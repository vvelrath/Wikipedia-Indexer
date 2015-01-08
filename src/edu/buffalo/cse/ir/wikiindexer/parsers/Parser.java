/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

/**
 * @author nikhillo
 *
 */
public class Parser {
	/* */
	private final Properties props;
	
	/**
	 * 
	 * @param idxConfig
	 * @param parser
	 */
	public Parser(Properties idxProps) {
		props = idxProps;
	}
	
	/* TODO: Implement this method */
	/**
	 * 
	 * @param filename
	 * @param docs
	 */
	public void parse(String filename, Collection<WikipediaDocument> docs) {
		new XmlParser(filename,docs);
	}
	
	/**
	 * Method to add the given document to the collection.
	 * PLEASE USE THIS METHOD TO POPULATE THE COLLECTION AS YOU PARSE DOCUMENTS
	 * For better performance, add the document to the collection only after
	 * you have completely populated it, i.e., parsing is complete for that document.
	 * @param doc: The WikipediaDocument to be added
	 * @param documents: The collection of WikipediaDocuments to be added to
	 */
	private synchronized void add(WikipediaDocument doc, Collection<WikipediaDocument> documents) {
		documents.add(doc);
	}
}

class XmlParser extends DefaultHandler
{
	WikipediaDocument mydoc;
	int id;
	String author="";
	String ModiDate="";
	String title="";
	String tmpValue="";
	String filename="";
	int flag=0;
	int flag_text=2;
	String text="";
	Collection<WikipediaDocument> docs;

	public XmlParser(String filename,Collection<WikipediaDocument> documents)
	{
		this.filename=filename;
		this.docs=documents;
		parseDocument();
	}
	
	private void parseDocument()
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			 SAXParser saxparser = factory.newSAXParser();
			 XMLReader xmlReader = saxparser.getXMLReader();
			 xmlReader.setContentHandler(handler);
			//saxparser.parse("files/five_entries.xml", handler);
			saxparser.parse(filename, handler);
		}
		catch (ParserConfigurationException e) 
		{
		    System.out.println("ParserConfig error");
        } 
		catch (SAXException e) 
		{
            System.out.println("SAXException : xml not well formed");
        } 
		catch (IOException e) 
		{
            System.out.println("IO error");
        }
		catch(Exception e)
		{
			
		}
	}
	
	DefaultHandler handler = new DefaultHandler(){
	
	@Override
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException
	{
		if(elementName.equalsIgnoreCase("text"))
		{
			flag_text=0;
		}
	}
	
	@Override
	public void endElement(String s, String s1, String element) throws SAXException 
	{
		//System.out.println(element);
		
		if(element.equalsIgnoreCase("text"))
		{
			flag=0;
			flag_text=1;
			
			//System.out.println(text);
			try 
			{
				mydoc=new WikipediaDocument(id,ModiDate,author,title);
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			mydoc=WikipediaParser.Initalizer(text,mydoc);
			
			
			/*System.out.println("Title:"+mydoc.getTitle());
			
			System.out.println("Author:"+mydoc.getAuthor());
			
			System.out.println("Date:"+mydoc.getPublishDate());
			
			System.out.println("Categories:"+mydoc.getCategories());
			
			System.out.println("Links:"+mydoc.getLinks());
			
			for(int i=0;i<mydoc.getSections().size();i++)
			{
				System.out.println("SECTITLE:"+mydoc.getSections().get(i).getTitle());
				System.out.println("");
				System.out.println("SECTEXT:");
				System.out.println(mydoc.getSections().get(i).getText());
				System.out.println("");
			}*/
			
	
			
			
			
			
			
			docs.add(mydoc);
			//System.out.println("Size:"+docs.size());
			
			text="";
		}
		if((element.equalsIgnoreCase("id")) && (flag==0))
		{
			id=Integer.parseInt(tmpValue);
			flag=1;
			//System.out.println("ID:"+id);
		}
		if(element.equalsIgnoreCase("timestamp"))
		{
			ModiDate=tmpValue;			
		}
		if((element.equalsIgnoreCase("ip")) || (element.equalsIgnoreCase("username")))
		{
			author=tmpValue;
		}
		if(element.equalsIgnoreCase("title"))
		{
			title=tmpValue;
		}
	}
	
	@Override
	public void characters(char[] ac, int i, int j) throws SAXException 
	{
	     tmpValue = new String(ac, i, j);
	     String text_tmp=null;
	     if (flag_text==0)
	     {
	    	 //text=text+tmpValue;
	    	 /*text=text.concat(tmpValue);*/
	    	 text_tmp=text.concat(tmpValue);
	    	 text=text_tmp;
	     }
	}

	};
}

