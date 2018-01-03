package com.agh.tramsim.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class XmlParser {

    private static Logger logger = LoggerFactory.getLogger( XmlParser.class );

    public void readFile() throws IOException, SAXException, ParserConfigurationException, TransformerException {

        List<String> routeIds = new LinkedList<>();
        File fXmlFile = new File("src/main/resources/node.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("member");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getAttribute("role").equalsIgnoreCase("")) {
                    routeIds.add(eElement.getAttribute("ref"));
                }
            }

        }

        Writer writer = new OutputStreamWriter(new FileOutputStream("output.json") , "UTF-8");
            Gson gson = new GsonBuilder().create();
            gson.toJson(routeIds,writer);
            writer.close();

        logger.info(gson.toJson(routeIds));
        logger.info(String.valueOf(routeIds.size()));
    }
}

