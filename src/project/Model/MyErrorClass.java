package project.Model;

import javafx.scene.control.Alert;

/**
 * Класс для вывода сообщений об ошибках на экран.
 * За основу выбран класс Alert.
 */
public class MyErrorClass {
    public enum MessageType{MESSAGE, WARNING, ERROR}
    public static void showMesssage(String title, String header, String message, MessageType type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        switch (type){
            case MESSAGE: alert.setAlertType(Alert.AlertType.INFORMATION);break;
            case WARNING: alert.setAlertType(Alert.AlertType.WARNING);break;
            case ERROR: alert.setAlertType(Alert.AlertType.ERROR);break;
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
