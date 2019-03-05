package com.tcs.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author ASIT KUMAR PANDA
 * 
 *
 *         <webServiceRequestEnvelope> 
 *         <versionedIdentifier>
	 *         <value>BT5X87P3-80-78_1</value> 
	 *         <source>Aib</source>
	 *         <version>2</version> 
 *         </versionedIdentifier>
 *         </webServiceRequestEnvelope>
 *
 * 
 */
public class XMLModifier {

	public void xmlModifierUtil() throws IOException {

		TextFileReader tfr = new TextFileReader();
		for (int i = 0; i < tfr.getOrderId().length; i++) {
			String orderid = tfr.getOrderId()[i];

			String filePath = "demo.xml";
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;

			try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(xmlFile);
				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				
				NodeList nodeList = doc.getElementsByTagName("versionedIdentifier");

				if (nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodeList.item(0);
					setTagValue("value", element, orderid);
				}

				
				NodeList nodeList2= doc.getElementsByTagNameNS("stan", "serviceAddressing");
				
				if(nodeList2.item(4).getNodeType()== Node.ELEMENT_NODE) 
				{ 	Element e2= (Element)nodeList2.item(4); 
					setTagValue("stan:messageId", e2, orderid);
				}
				 

				// The following logic is to write into new xmlfile
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(
						new File("C:\\Users\\611329859\\Documents\\output" + orderid + "-80-78_1.xml"));
				transformer.transform(source, result);

				System.out.println("Done");

			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (SAXException sae) {
				sae.printStackTrace();
			}

		}

	}

	public static void main(String[] args) throws IOException {

		XMLModifier oXMLModifier = new XMLModifier();
		oXMLModifier.xmlModifierUtil();
	}

	private static void setTagValue(String tag, Element element, String value) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		String temp = node.getNodeValue().substring(node.getNodeValue().indexOf("-"), node.getNodeValue().length());
		node.setTextContent(value + temp);
	}

	
	/*
	 * private static void setTagValue2(String tag, Element element,String value) {
	 * NodeList nodeList =element.getElementsByTagName(tag).item(0).getChildNodes();
	 * Node node = (Node)nodeList.item(0); String
	 * temp=node.getNodeValue().substring(node.getNodeValue().indexOf("-"),node.
	 * getNodeValue().length()); node.setTextContent(value+temp); }
	 */

}
