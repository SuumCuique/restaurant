package project.Model;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Класс для выявления и вывода ошибок при парсинге xml-файла.
 */

class MyErrorHandler implements ErrorHandler {
    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Строка " +e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }
    public void error(SAXParseException e) throws SAXException {
        System.out.println("Строка " +e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }
    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Строка " +e.getLineNumber() + ":");
        System.out.println(e.getMessage());
    }
}
