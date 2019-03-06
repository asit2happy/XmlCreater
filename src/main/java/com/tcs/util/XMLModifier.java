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
 *This utility helps 
 * 
 */
public class XMLModifier {

	public void xmlModifierUtil() throws IOException {

		TextFileReader tfr = new TextFileReader();
		String[] orderidArray = (String[]) tfr.getOrderId().get(0);//value
		String[] iccidArray = (String[]) tfr.getOrderId().get(1);//value2


		for (int i = 0; i < orderidArray.length; i++) {
			String orderid=orderidArray[i];
			System.out.println("###########"+orderid);
			
			String iccid=iccidArray[i];
			System.out.println("###########"+iccid);

			

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
				
				NodeList nodeList2 = doc.getElementsByTagName("request");
				Element element2 = (Element) nodeList2.item(0);
				//NodeList nodeList3 = element2.getElementsByTagName("upd:UpdateSimProfileStatusRequest").item(0).getChildNodes();
				//Element e=(Element)nodeList3.item(0).getChildNodes().item(2);
				/*
				 * NodeList nodeList4 =
				 * element2.getElementsByTagName("stan:messageId").item(0).getChildNodes();
				 * 
				 * Node node = (Node) nodeList4.item(0);
				 * System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@"+node.getNodeName()+" "+node.
				 * getNodeValue());
				 */		
				setTagValue("stan:messageId", element2, orderid);


//upd:UpdateSimProfileStatusRequest

				/*
				 * NodeList nodeList2= doc.getElementsByTagNameNS("stan", "serviceAddressing");
				 * 
				 * if(nodeList2.item(4).getNodeType()== Node.ELEMENT_NODE) { Element e2=
				 * (Element)nodeList2.item(4); setTagValue("stan:messageId", e2, orderid); }
				 
				
				NodeList nodeList3 = doc.getElementsByTagNameNS("v1", "updateSimProfileStatus");

				if (nodeList.item(5).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nodeList.item(5);
					setTagValue("value", element, orderid);
				}
					*/
				// The following logic is to write into new xmlfile
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(
						new File("E:\\output\\" + orderid + "-80-78_1.xml"));
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
		String nodeValue=node.getNodeValue();
		String temp = nodeValue.substring(nodeValue.indexOf("-"), nodeValue.length());
		String finalValue=nodeValue.contains("Aib") ? "Aib/"+value+temp: value+temp;
		node.setTextContent(finalValue);
	}

	
	/*
	 * private static void setTagValue2(String tag, Element element,String value) {
	 * NodeList nodeList =element.getElementsByTagName(tag).item(0).getChildNodes();
	 * Node node = (Node)nodeList.item(0); String
	 * temp=node.getNodeValue().substring(node.getNodeValue().indexOf("-"),node.
	 * getNodeValue().length()); node.setTextContent(value+temp); }
	 */
	private static void setTagValue3(String tag, Element element, String value) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		String temp = node.getNodeValue().substring(node.getNodeValue().indexOf("-"), node.getNodeValue().length());
		node.setTextContent(value + temp);
	}

}
