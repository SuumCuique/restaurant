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
 * Класс для работы с xml-файлом пользователей.
 * В этом классе реализованы опрерации по добавлению/удалению/изменению данных о пользователе;
 * Поиска пользователя в списке;
 * Создание нового пользователя;
 * Валидация на логин и пароль.
 */
public class User {
    private final String FILENAME = "D:/project/xml/users.xml";
    private final String SCHEMA = "D:/project/xsd/users.xsd";
    private String login;
    private String password;
    private String position;
    private String FIO;

    public User() {
        login = "";
        password = "";
        position = "";
        FIO="";
    }

    public User (String login, String password) {
        this.login = login;
        this.password = password;
        this.position = "";
        this.FIO = "";
    }

    public User (String login, String password, String position, String FIO) {
        this.login = login;
        this.password = password;
        this.position = position;
        this.FIO = FIO;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFIO() { return FIO; }

    public void setFIO(String FIO) { this.FIO = FIO; }

    /**
     * Загружает информацию о пользователях из файла и возвращает её в виде объекта NodeList.
     * @return
     */
    private NodeList open() {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        Document doc = null;

        try{
            //Валидация по xsd
            dbf = DocumentBuilderFactory.newInstance();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema s = sf.newSchema(new File(SCHEMA));
            dbf.setValidating(false);
            dbf.setSchema(s);
            db= dbf.newDocumentBuilder();
            db.setErrorHandler(new MyErrorHandler());

            doc = db.parse(new File(FILENAME));

            Element root = doc.getDocumentElement();
            if(root.getTagName().equals("Users")){
               return root.getElementsByTagName("User");

            }
        }
        catch (Exception e){System.out.print(e.getMessage());}
        return null;
    }

    /**
     * Метод для поиска пользователя в файле.
     * @return
     */
    public boolean isFound()
    {
        NodeList listUsers = null;
        listUsers = open();
        if(listUsers!=null) {
            for (int i=0; i < listUsers.getLength(); i++) {
                Element user = (Element) listUsers.item(i);
                if (user.getAttribute("login").equals(login) && user.getAttribute("password").equals(password)) {
                    position = user.getAttribute("position");
                    FIO = user.getAttribute("FIO");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод для поиска всех инженеров в файле пользователей.
     * @return
     */
    public ObservableList<String> getCookers()
    {
        NodeList listUsers = null;
        ObservableList<String> users = FXCollections.observableArrayList();
        listUsers = open();
        if(listUsers!=null){
            for(int i=0;i<listUsers.getLength();i++){
                Element cook_user = (Element) listUsers.item(i);
                if(cook_user.getAttribute("position").equals("cook")){
                    users.add(cook_user.getAttribute("FIO"));
                }
            }
        }
        return users;
    }


    /**
     * Метод для добавления нового пользователя.
     * @return
     */
    public boolean add() {
        NodeList listUsers = null;
        listUsers = open();
        if(listUsers!=null) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();

                doc = db.parse(new File(FILENAME));
                Element root = doc.getDocumentElement();
                Element newUser = doc.createElement("User");
                newUser.setAttribute("login", login);
                newUser.setAttribute("password", password);
                newUser.setAttribute("position", position);
                newUser.setAttribute("FIO", FIO);
                root.appendChild(newUser);

                Source domSource = new DOMSource(doc);
                Result fileResult = new StreamResult(new File(FILENAME));
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(domSource, fileResult);
            }
            catch (Exception e){};
        }
        return false;
    }

    /**
     * Метод для удаления данных о пользователе в файле.
     * @return
     */
    public boolean remove() {
        NodeList listUsers = null;
        listUsers = open();
        if(listUsers!=null) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();

                doc = db.parse(new File(FILENAME));
                Element root = doc.createElement("Users");
                for(int i=0;i<listUsers.getLength();i++) {
                    Element user = (Element) listUsers.item(i);
                    if(!user.getAttribute("login").equals(login)) {
                        root.appendChild(user);
                    }
                }

                Source domSource = new DOMSource(doc);
                Result fileResult = new StreamResult(new File(FILENAME));
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(domSource, fileResult);
            }
            catch (Exception e){System.out.print(e.getMessage());}
        }
        return false;
    }
}
