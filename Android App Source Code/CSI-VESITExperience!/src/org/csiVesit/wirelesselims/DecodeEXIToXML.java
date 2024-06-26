package org.csiVesit.wirelesselims;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.openexi.proc.common.AlignmentType;
import org.openexi.proc.common.EXIOptionsException;
import org.openexi.proc.common.GrammarOptions;
import org.openexi.proc.grammars.GrammarCache;
import org.openexi.sax.EXIReader;
import org.openexi.schema.EXISchema;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DecodeEXIToXML
{
    public DecodeEXIToXML()
	{
        super();
    }

    static public String decodeEXIToXML(String sourceFile)
	{
        FileInputStream in = null;
        StringWriter stringWriter = new StringWriter();
        String reconstitutedString = null;

        GrammarCache grammarCache;
        try
		{
            SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setNamespaceAware(true);
            TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();

            EXIReader reader = new EXIReader();		//recontruct the XML File Struct
			reader.setAlignmentType(AlignmentType.compress);
            
            File inputFile = new File(sourceFile);
            in = new FileInputStream(inputFile);

            grammarCache = new GrammarCache((EXISchema)null, GrammarOptions.DEFAULT_OPTIONS);
            reader.setGrammarCache(grammarCache);

            transformerHandler.setResult(new StreamResult(stringWriter));		//Prepare to send the results from the transformer to a StringWriter object.
            
            byte fileContent[] = new byte[(int)inputFile.length()];
            in.read(fileContent);
            
            reader.setContentHandler(transformerHandler);						//Assign transformer handler to interpret XML content
            reader.parse(new InputSource(new ByteArrayInputStream(fileContent)));

// Get the resulting string, write it to the output file, and flush the buffer contents.
            reconstitutedString = stringWriter.getBuffer().toString();
			
			in.close();
			stringWriter.close();
        }catch(FileNotFoundException fnfe)
		{
        	
		}catch(IOException ioe)
		{
		
		}catch(SAXException saxe)
		{
			
		}catch(TransformerException transformerExcep)
		{
			
		}catch(EXIOptionsException EXIOptionsexception)
		{
			
		}
        return reconstitutedString;
    }
}
