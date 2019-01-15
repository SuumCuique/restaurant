package project.Control;

import project.Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import project.Model.Steward;
import project.Model.Dish;

/**
 * Класс-контроллер для взаимодействия с рабочей областью администратора.
 */

public class admin {
    @FXML
    TableView tableSteward;
    @FXML
    TableView tableProducts;
    @FXML
    TableView tableDish;
    @FXML
    TextField textFIO;
    @FXML
    ChoiceBox choiceCook;
    @FXML
    TextField textProductsName;
    @FXML
    TextField textProductsInfo;
    @FXML
    TextField textProductsCount;
    @FXML
    TextField textDishName;
    @FXML
    TextField textDishSize;
    @FXML
    TextField textDishInfo;
    @FXML
    TextField textDishCount;

    private StewardList stewards;
    private ProductsList products;
    private DishList dishes;


    /**
     * Инициализация компонентов графического интерфейса.
     * Создание и заполнение таблиц рабочих, деталей и изделий.
     */
    public void initialize() {
        stewards = new StewardList("D:/project/xml/stewards.xml");
        dishes = new DishList("D:/project/xml/dishes.xml");
        products = new ProductsList("D:/project/xml/products.xml");
        User a = new User();
        ObservableList<String> namesOfCookers = a.getCookers();
        choiceCook.setItems(namesOfCookers);
        //---------------------РАБОЧИЕ---------------------
        TableColumn<Steward, String> id_stewards = new TableColumn<>("id");
        TableColumn<Steward, String> FIO_stewards = new TableColumn<>("ФИО");
        TableColumn<Steward, String> cook_stewards = new TableColumn<>("Повар");
        id_stewards.setCellValueFactory(new PropertyValueFactory<>("id"));
        FIO_stewards .setCellValueFactory(new PropertyValueFactory<>("FIO"));
        cook_stewards.setCellValueFactory(new PropertyValueFactory<>("cook"));
        id_stewards.setSortable(false);
        FIO_stewards.setSortable(false);
        cook_stewards.setSortable(false);
        tableSteward.getColumns().addAll(id_stewards,FIO_stewards,cook_stewards);
        tableSteward.setItems(stewards.getList());

        //---------------------ДЕТАЛИ---------------------
        TableColumn<Products, String> id_products= new TableColumn<>("id");
        TableColumn<Products, String> name_products= new TableColumn<>("Название");
        TableColumn<Products, String> info_products= new TableColumn<>("Информация");
        TableColumn<Products, String> count_products= new TableColumn<>("Количество");
        id_products.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_products.setCellValueFactory(new PropertyValueFactory<>("name"));
        info_products.setCellValueFactory(new PropertyValueFactory<>("info"));
        count_products.setCellValueFactory(new PropertyValueFactory<>("count"));
        id_products.setSortable(false);
        name_products.setSortable(false);
        info_products.setSortable(false);
        count_products.setSortable(false);
        tableProducts.getColumns().addAll(id_products,name_products,info_products,count_products);
        tableProducts.setItems(products.getList());

        //---------------------ИЗДЕЛИЯ---------------------
        TableColumn<Dish, String> id_dish = new TableColumn<>("id");
        TableColumn<Dish, String> name_dish = new TableColumn<>("Название");
        TableColumn<Dish, String> size_dish = new TableColumn<>("Размеры");
        TableColumn<Dish, String> count_dish = new TableColumn<>("Количество");
        TableColumn<Dish, String> info_dish = new TableColumn<>("Информация");
        id_dish.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_dish.setCellValueFactory(new PropertyValueFactory<>("name"));
        size_dish.setCellValueFactory(new PropertyValueFactory<>("size"));
        count_dish.setCellValueFactory(new PropertyValueFactory<>("count"));
        info_dish.setCellValueFactory(new PropertyValueFactory<>("info"));
        id_dish.setSortable(false);
        name_dish.setSortable(false);
        size_dish.setSortable(false);
        count_dish.setSortable(false);
        info_dish.setSortable(false);
        tableDish.getColumns().addAll(id_dish, name_dish, size_dish, count_dish, info_dish);
        tableDish.setItems(products.getList());
    }

    //---------------------РАБОЧИЕ---------------------
    /**
     * Метод производит добавление рабочего в список.
     * @param actionEvent
     */
    public void onAddSteward(ActionEvent actionEvent) {
        try {
            int id = ((Steward)stewards.getList().get(stewards.length()-1)).getId();
            stewards.add(new Steward(++id, textFIO.getText()));
            textFIO.setText("");
            stewards.saveToFile("D:/project/xml/stewards.xml");
        }
        catch (Exception e){}

    }

    /**
     * Метод производит удаление рабочего из списка.
     * @param actionEvent
     */
    public void onDelSteward(ActionEvent actionEvent) { try{
        tableSteward.getItems().remove(tableSteward.getSelectionModel().getSelectedIndex());
        stewards.saveToFile("/project/xml/stewards.xml");
    } catch(Exception e) {}}

    //---------------------ДЕТАЛИ---------------------
    /**
     * Метод производит добавление детали в список.
     * @param actionEvent
     */
    public void onAddProducts(ActionEvent actionEvent) {
        try {
            int id = ((Products)products.getList().get(products.length()-1)).getId();
            products.add(new Products(++id,textProductsName.getText(), Integer.parseInt(textProductsCount.getText()),textProductsInfo.getText()));
            textProductsName.setText("");
            textProductsInfo.setText("");
            textProductsCount.setText("");
            products.saveToFile("D:/project/xml/products.xml");
        }
        catch (Exception e){}
    }

    /**
     * Метод производит удаление детали из списка.
     * @param actionEvent
     */
    public void onDelProducts(ActionEvent actionEvent) { try{
        tableProducts.getItems().remove(tableProducts.getSelectionModel().getSelectedIndex());
        products.saveToFile("D:/project/xml/products.xml");
    } catch (Exception e) {}}

    //---------------------ИЗДЕЛИЯ---------------------
    /**
     * Метод производит добавление изделия в список.
     * @param actionEvent
     */
    public void onAddProduct(ActionEvent actionEvent) {
        try {
            Dish D = new Dish();
            int id = ((Dish)dishes.getList().get(dishes.length()-1)).getId();
            D.setId(++id);
            D.setName(textDishName.getText());
            D.setSize(textDishSize.getText());
            D.setCount(Integer.parseInt(textDishCount.getText()));
            D.setInfo(textDishInfo.getText());
            dishes.add(D);
            textDishName.setText("");
            textDishSize.setText("");
            textDishCount.setText("");
            textDishInfo.setText("");
            dishes.saveToFile("D:/project/xml/dishes.xml");
        }
        catch (Exception e) {}
    }

    /**
     * Метод производит удаление изделия из списка.
     * @param actionEvent
     */
    public void onDelDish(ActionEvent actionEvent) { try{
        tableDish.getItems().remove(tableDish.getSelectionModel().getSelectedIndex());
        dishes.saveToFile("D:/project/xml/dishes.xml");
    } catch (Exception e) {}}

}
