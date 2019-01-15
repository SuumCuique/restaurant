package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
* Главный класс для запуска приложения и открытия окна ввода логина и пароля.
*/
public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage){
        try {
            stage = primaryStage;

            Parent root = FXMLLoader.load(getClass().getResource("/project/View/login.fxml"));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(new Scene(root, 380, 225));
            stage.show();
        }
        catch (Exception e) {System.out.println(e.getMessage());}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
