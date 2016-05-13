import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class creates an XML file using Java Object Model.
 * 
 * @author Sahithi Karre
 *
 */

public class CreateXML {
	/**
	 * This function creates the XML  document using DOM parser functions.
	 * 
	 * @param listOfSentences takes list of sentences
	 * @throws Exception gives exception 
	 */
	public void createXml(ArrayList<Sentence> listOfSentences) throws Exception {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = docBuilderFactory
				.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("Document");
		document.appendChild(element);
		for (Sentence s : listOfSentences) {
			Element sentence = document.createElement("Sentence");
			element.appendChild(sentence);
			Iterator<String> it = s.tokenMap.keySet().iterator();
			while (it.hasNext()) {
				String token = (String) it.next();
				Element tokens = document.createElement("Token");
				tokens.appendChild(document.createTextNode(token));
				sentence.appendChild(tokens);
			}
		}
		TransformerFactory transformer = TransformerFactory.newInstance();
		Transformer trans = transformer.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File("Result.xml"));
		trans.transform(source, result);
	}
}
