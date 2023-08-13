package ru.netology;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {

    private List<Employee> list = new ArrayList<>();
    private static final String JSONDFILENAME = "/Users/dmitry.pirumov/IdeaProjects/Netology/Netology_HW/parser/XMLtoJSON/new_json.json";
    private static final String XMLFILENAME = "/Users/dmitry.pirumov/IdeaProjects/Netology/Netology_HW/parser/XMLtoJSON/data.xml";


    public static void main(String[] args) {
        Main main = new Main();
        main.parseXML();
        main.listToJSON();
    }

    public void parseXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(XMLFILENAME);
            Node node = doc.getDocumentElement();
            readXML(node);
            list.stream().map(Employee::toString).forEach(System.out::println);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readXML(Node node) {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node2 = nodeList.item(i);
            if (Node.ELEMENT_NODE == node2.getNodeType()) {
                Element element = (Element) node2;
                long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                String country = element.getElementsByTagName("country").item(0).getTextContent();
                int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                list.add(new Employee(id, firstName, lastName, country, age));
            }
        }

    }

    public void listToJSON() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();

        try (FileWriter fw = new FileWriter(JSONDFILENAME)) {
            String json = gson.toJson(list, listType);
            fw.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}