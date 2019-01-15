package project.Model;

import javafx.collections.FXCollections;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Класс для описания списка деталей.
 * Сам список представлен классом observableArrayList.
 */

public class ProductsList extends AbstractList{
	public ProductsList() { list = FXCollections.observableArrayList(); }

    /**
     * Создание нового списка деталей с загрузкой данных из файла.
     * @param fileName
     */
    public ProductsList(String fileName) {
        list = FXCollections.observableArrayList();
        try {
            loadFromFile(fileName);
        }
        catch (Exception e){ MyErrorClass.showMesssage("Ошибка", "Не удалось загрузить файл", e.getMessage(), MyErrorClass.MessageType.ERROR);}
    }

    @Override
    public boolean loadFromFile(String fileName) throws Exception{
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;


        //Валидация по xsd
        dbf = DocumentBuilderFactory.newInstance();
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema s = sf.newSchema(new File("D:/project/xsd/products.xsd"));
        dbf.setValidating(false);
        dbf.setSchema(s);
        db= dbf.newDocumentBuilder();
        db.setErrorHandler(new MyErrorHandler());

        doc = db.parse(new File(fileName));

        Element root = doc.getDocumentElement();
        if(root.getTagName().equals("Products")){		//many
            NodeList listDishes = root.getElementsByTagName("Products");//1
            for(int i=0; i < listDishes.getLength(); i++) {
                Element Products = (Element) listDishes.item(i);

                Products P = new Products();
                P.setId(Integer.parseInt(Products.getAttribute("id")));
                P.setName(Products.getAttribute("name"));
                P.setCount(Integer.parseInt(Products.getAttribute("count")));
                P.setInfo(Products.getAttribute("info"));

                list.add(P);
            }
        }
        return true;
    }

    @Override
    public boolean saveToFile(String fileName) throws Exception{
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;

        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.newDocument();

        Element root = doc.createElement("Products");//many
        doc.appendChild(root);

        for(AbstractSubject i : list)
        {
            Element Products = doc.createElement("Products");//1
            Products.setAttribute("id", String.valueOf(((Products)i).getId()));
            Products.setAttribute("name", ((Products)i).getName());
            Products.setAttribute("count", String.valueOf(((Products)i).getCount()));
            Products.setAttribute("info", ((Products)i).getInfo());
            root.appendChild(Products);
        }

        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(fileName));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(domSource, fileResult);

        return true;
    }

}
