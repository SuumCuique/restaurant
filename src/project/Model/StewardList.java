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
 * Класс для описания списка рабочих.
 * Список представлен классом observableArrayList.
 */

public class StewardList extends AbstractList{
	public StewardList() {
        list = FXCollections.observableArrayList();
    }
    /**
     * Создание нового списка рабочих с загрузкой данных из файла.
     * @param fileName
     */
    public StewardList(String fileName) {
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
        Schema s = sf.newSchema(new File("D:/project/xsd/stewards.xsd"));
        dbf.setValidating(false);
        dbf.setSchema(s);
        db= dbf.newDocumentBuilder();
        db.setErrorHandler(new MyErrorHandler());

        doc = db.parse(new File(fileName));

        Element root = doc.getDocumentElement();
        if(root.getTagName().equals("Stewards")){
            NodeList listProducts = root.getElementsByTagName("Steward");
            for(int i=0; i < listProducts.getLength(); i++) {
                Element Steward = (Element) listProducts.item(i);

                project.Model.Steward S = new Steward();
                S.setId(Integer.parseInt(Steward.getAttribute("id")));
                S.setFIO(Steward.getAttribute("FIO"));

                list.add(S);
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


        Element root = doc.createElement("Stewards");
        doc.appendChild(root);

        for(AbstractSubject i : list)
        {
            Element Steward = doc.createElement("Steward");
            Steward.setAttribute("id", String.valueOf(((Steward)i).getId()));
            Steward.setAttribute("FIO", ((Steward)i).getFIO());
            root.appendChild(Steward);
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
