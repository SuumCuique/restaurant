package project.Control;

import project.Model.MyErrorClass;
import project.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс-контроллер для взаимодействия с формой создания учётной записи нового пользователя.
 */
public class add_user {
    @FXML
    TextField textFIO;
    @FXML
    ChoiceBox choosePosition;
    @FXML
    TextField textLogin;
    @FXML
    PasswordField textPassword;
    @FXML
    PasswordField textRePassword;

    /**
     * Инициализация компонентов графического интерфейса.
     */
    public void initialize() {
        choosePosition.getItems().addAll("Администратор", "Повар", "Официант");
    }

    /**
     * При нажатии на кнопку регистрации происходит валидация всех введённых значений,
     * и в случае успешной валидации происходит добавление нового пользователя.
     * @param actionEvent
     */
    public void onRegistr(ActionEvent actionEvent) {
        try {
            if(!(textFIO.getText().equals("")&&textLogin.getText().equals("")&&textPassword.getText().equals("")&&textRePassword.getText().equals(""))) {
                if(!textPassword.getText().equals(textRePassword.getText())) throw new Exception("Введённые пароли не совпадают");
                String Position = new String();
                switch(choosePosition.getSelectionModel().getSelectedIndex())
                {
                    case 0: Position="admin";break;
                    case 1: Position="cook";break;
                    case 2: Position="steward";break;
                    default: throw new Exception("Проверьте, все ли данные введены верно");
                }
                User user = new User(textLogin.getText(), textPassword.getText(), Position, textFIO.getText());
                user.add();

                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("\\..\\View/login.fxml"));
                stage.setTitle("Login");
                stage.setResizable(false);
                stage.setScene(new Scene(root, 380, 225));
                stage.show();
                Node source = (Node) actionEvent.getSource();
                Stage mystage = (Stage) source.getScene().getWindow();
                mystage.close();
            }
            else throw new Exception("Проверьте, все ли данные введены верно");
        }
        catch (Exception e) {MyErrorClass.showMesssage("", "Ошибка при регистрации",e.getMessage(), MyErrorClass.MessageType.MESSAGE);}
    }

    /**
     * При нажатии на кнопку закрытия программа возварщает нас в окно ввода логина и пароля.
     * @param actionEvent
     */
    public void onCancel(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/project/View/login.fxml"));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 380, 225));
            stage.show();
            Node source = (Node) actionEvent.getSource();
            Stage mystage = (Stage) source.getScene().getWindow();
            mystage.close();
        }
        catch (IOException e) {MyErrorClass.showMesssage("Ошибка", "Ошибка",e.getMessage(), MyErrorClass.MessageType.ERROR);}
    }
}
