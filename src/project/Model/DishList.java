package project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * Класс для описания списка изделий.
 * Список представлен классом observableArrayList.
 */

public class DishList extends AbstractList{

    public DishList() {
        list = FXCollections.observableArrayList();
    }

    /**
     * Создание нового списка изделий с загрузкой данных из файла.
     * @param fileName
     */
    public DishList(String fileName){
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
        Schema s = sf.newSchema(new File("D:/project/xsd/dishes.xsd"));
        dbf.setValidating(false);
        dbf.setSchema(s);
        db= dbf.newDocumentBuilder();
        db.setErrorHandler(new MyErrorHandler());

        doc = db.parse(new File(fileName));

        Element root = doc.getDocumentElement();
        if(root.getTagName().equals("Dishes")){
            NodeList listDishes = root.getElementsByTagName("Dish");
            for(int i=0; i < listDishes.getLength(); i++) {
                Element Dish = (Element) listDishes.item(i);

                project.Model.Dish D = new Dish();
                D.setId(Integer.parseInt(Dish.getAttribute("id")));
                D.setName(Dish.getAttribute("name"));
                D.setSize(Dish.getAttribute("size"));
                D.setAssembly(Dish.getAttribute("assembly"));
                D.setCount(Integer.parseInt(Dish.getAttribute("count")));
                D.setInfo(Dish.getAttribute("info"));

                ObservableList<Products> listProducts = FXCollections.observableArrayList();
                NodeList listDishProducts = Dish.getElementsByTagName("Products");
                for(int j=0; j<listDishProducts.getLength(); j++)
                {
                    Element Products = (Element) listDishProducts.item(j);

                    Products P = new Products();
                    P.setId(Integer.parseInt(Products.getAttribute("id")));
                    P.setName(Products.getAttribute("name"));
                    P.setInfo(Products.getAttribute("info"));
                    P.setCount(Integer.parseInt(Products.getAttribute("count")));

                    listProducts.add(P);
                }

                D.setProducts(listProducts);
                list.add(D);
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


        Element root = doc.createElement("Dishes");
        doc.appendChild(root);

        for(AbstractSubject i : list){
            Element Dish = doc.createElement("Dish");
            Dish.setAttribute("id", String.valueOf(((Dish)i).getId()));
            Dish.setAttribute("name", ((Dish)i).getName());
            Dish.setAttribute("size", ((Dish)i).getSize());
            Dish.setAttribute("assembly", ((Dish)i).getAssembly());
            Dish.setAttribute("count", String.valueOf(((Dish)i).getCount()));
            Dish.setAttribute("info", ((Dish)i).getInfo());
            for(AbstractSubject j : ((Dish) i).getProducts()){
                Element Products = doc.createElement("Products");
                Products.setAttribute("id", String.valueOf(((Products)j).getId()));
                Products.setAttribute("name", ((Products)j).getName());
                Products.setAttribute("count", String.valueOf(((Products)j).getCount()));
                Products.setAttribute("info", ((Products)j).getInfo());
                Dish.appendChild(Products);
            }
            root.appendChild(Dish);
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
