package ru.netology;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
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

    List<Employee> list = new ArrayList<>();
    private static final String JSONDFILENAME = "/Users/dmitry.pirumov/IdeaProjects/Netology/Netology_HW/parser/XMLtoJSON/new_json.json";
    private static final String XMLFILENAME = "/Users/dmitry.pirumov/IdeaProjects/Netology/Netology_HW/parser/XMLtoJSON/data.xml";


    public static void main(String[] args) {
        Main main = new Main();
        main.parseXML();
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

    private void readXML(Node node){
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node2 = nodeList.item(i);
            if (Node.ELEMENT_NODE == node2.getNodeType()) {
                Element element = (Element) node2;
                long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());;
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();;
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();;
                String country = element.getElementsByTagName("country").item(0).getTextContent();;
                int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                list.add(new Employee(id, firstName,lastName, country, age));

//                NamedNodeMap map = element.getAttributes();
//                for (int j = 0; j < map.getLength(); j++) {
//                    System.out.println(map.item(j).getNodeName());
//                    System.out.println(map.item(j).getNodeValue());
//                        long id = Long.parseLong(map.item(0).getNodeValue());;
//                        String firstName = map.item(1).getNodeValue();
//                        String lastName = map.item(2).getNodeValue();
//                        String country = map.item(3).getNodeValue();
//                        int age = Integer.parseInt(map.item(4).getNodeValue());
//                        list.add(new Employee(id, firstName,lastName, country, age));
//                }
//                readXML(node2);
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