package project.Control;

import project.Model.MyErrorClass;
import javafx.event.ActionEvent;
import project.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс-контроллер для окна ввода логина и пароля.
 */

public class login {
    @FXML
    TextField loginField;
    @FXML
    PasswordField passwordField;

    /**
     * Метод производит валидацию логина и пароля пользователя, а также определяет, к какому классу относится пользователь.
     * @param actionEvent
     * @throws IOException
     */
    public void onEnter(ActionEvent actionEvent) throws IOException {
        User user = new User(loginField.getText(),passwordField.getText());
        if(user.isFound()){
            Stage stage = new Stage();
            Parent root;
            switch (user.getPosition())
            {
                case "admin": {
                    root = FXMLLoader.load(getClass().getResource("/project/View/admin.fxml"));/////
                    stage.setTitle("Admin");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                    Node source = (Node) actionEvent.getSource();
                    Stage mystage = (Stage) source.getScene().getWindow();
                    mystage.close();
                    break;
                }
                case "steward": {
                    root = FXMLLoader.load(getClass().getResource("/project/View/employee.fxml"));
                    stage.setTitle("Steward");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                    Node source = (Node) actionEvent.getSource();
                    Stage mystage = (Stage) source.getScene().getWindow();
                    mystage.close();
                    break;
                }
                case "cook": {
                    root = FXMLLoader.load(getClass().getResource("/project/View/engineer.fxml"));
                    stage.setTitle("Cook");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                    Node source = (Node) actionEvent.getSource();
                    Stage mystage = (Stage) source.getScene().getWindow();
                    mystage.close();
                    break;
                }
                default:{
                    MyErrorClass.showMesssage("Ошибка", "Ошибка при чтении данных из файла","", MyErrorClass.MessageType.ERROR);
                    break;
                }
            }
        }
        else
        {
           MyErrorClass.showMesssage("Ошибка", "Неправильный логин или пароль","", MyErrorClass.MessageType.ERROR);
        }
        passwordField.setText("");
    }

    /**
     * Метод вызывается при нажатии на кнопку регистрации и открывает форму для регистрации нового пользователя.
     * @param actionEvent
     */
    public void onRegistr(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/project/View/add_user.fxml"));
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, 380, 280));
            stage.setResizable(false);
            stage.show();
            Node source = (Node) actionEvent.getSource();
            Stage mystage = (Stage) source.getScene().getWindow();
            mystage.close();
        }
        catch (IOException e) { MyErrorClass.showMesssage("Ошибка", "Не удаётся открыть окно", e.getMessage(), MyErrorClass.MessageType.ERROR);}
    }
}
