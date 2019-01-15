package project.Control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import project.Model.Products;
import project.Model.ProductsList;
import project.Model.DishList;
import project.Model.MyErrorClass;
import project.Model.Dish;

/**
 * Класс-контроллер для взаимодействия с рабочей областью инженера.
 */

public class cook {
    @FXML
    TableView tableProductsChoose;
    @FXML
    TextField textId;
    @FXML
    TextField textName;
    @FXML
    TextField textSize;
    @FXML
    TableView tableDishRedact;
    @FXML
    TextArea textAssembly;
    @FXML
    TableView tableProductsInDish;
    @FXML
    TableView tableDishView;

    private ProductsList products;
    private DishList accept;
    private DishList reject;
    private DishList dishesReady;
    private DishList dishesRedact;
    private Dish D;
    /**
     * Инициализация компонентов графического интерфейса.
     * Создание таблиц для хранения деталей и изделий.
     */
    public void initialize() {
    	products = new ProductsList("D:/project/xml/products.xml");
        accept = new DishList("D:/project/xml/accepted_dishes.xml");
        reject = new DishList("D:/project/xml/rejected_dishes.xml");
        dishesReady = new DishList("D:/project/xml/check_dishes.xml");
        dishesRedact = new DishList("D:/project/xml/dishes.xml");

        //---------------------ИЗДЕЛИЯ_ПРИЁМ_ГОТОВЫХ_ИЗДЕЛИЙ---------------------
        TableColumn<Dish, String> id_dish = new TableColumn<>("id");
        TableColumn<Dish, String> name_dish = new TableColumn<>("Название");
        TableColumn<Dish, String> size_dish = new TableColumn<>("Размеры");
        TableColumn<Dish, String> info_dish = new TableColumn<>("Информация");
        id_dish.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_dish.setCellValueFactory(new PropertyValueFactory<>("name"));
        size_dish.setCellValueFactory(new PropertyValueFactory<>("size"));
        info_dish.setCellValueFactory(new PropertyValueFactory<>("info"));
        tableDishView.getColumns().addAll(id_dish, name_dish, size_dish, info_dish);
        tableDishView.setItems(dishesReady.getList());

        //---------------------ИЗДЕЛИЯ_РЕДАКТИРОВАНИЕ_ИЗДЕЛИЯ---------------------
        TableColumn<Dish, String> id_dish1 = new TableColumn<>("id");
        TableColumn<Dish, String> name_dish1 = new TableColumn<>("Название");
        TableColumn<Dish, String> size_dish1 = new TableColumn<>("Размеры");
        TableColumn<Dish, String> info_dish1 = new TableColumn<>("Информация");
        id_dish1.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_dish1.setCellValueFactory(new PropertyValueFactory<>("name"));
        size_dish1.setCellValueFactory(new PropertyValueFactory<>("size"));
        info_dish1.setCellValueFactory(new PropertyValueFactory<>("info"));
        tableDishRedact.getColumns().addAll(id_dish1, name_dish1, size_dish1, info_dish1);
        tableDishRedact.setItems(dishesRedact.getList());

        //---------------------ДЕТАЛИ_В_ИЗДЕЛИИ_РЕДАКТИРОВАНИЕ_ИЗДЕЛИЯ---------------------
        TableColumn<Products, String> id_products = new TableColumn<>("id");
        TableColumn<Products, String> name_products = new TableColumn<>("Название");
        TableColumn<Products, String> info_products = new TableColumn<>("Информация");
        TableColumn<Products, String> count_products = new TableColumn<>("Количество");
        id_products.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_products.setCellValueFactory(new PropertyValueFactory<>("name"));
        info_products.setCellValueFactory(new PropertyValueFactory<>("info"));
        count_products.setCellValueFactory(new PropertyValueFactory<>("count"));

        tableProductsInDish.getColumns().addAll(id_products,name_products,info_products,count_products);

        //---------------------ДЕТАЛИ_ДЛЯ_ВЫБОРА_РЕДАКТИРОВАНИЕ_ИЗДЕЛИЯ---------------------
        TableColumn<Products, String> id_products_choose = new TableColumn<>("id");
        TableColumn<Products, String> name_products_choose = new TableColumn<>("Название");
        TableColumn<Products, String> info_products_choose = new TableColumn<>("Информация");
        TableColumn<Products, String> count_products_choose = new TableColumn<>("Количество");
        id_products_choose.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_products_choose.setCellValueFactory(new PropertyValueFactory<>("name"));
        info_products_choose.setCellValueFactory(new PropertyValueFactory<>("info"));
        count_products_choose.setCellValueFactory(new PropertyValueFactory<>("count"));

        tableProductsChoose.getColumns().addAll(id_products_choose,name_products_choose,info_products_choose,count_products_choose);
        tableProductsChoose.setItems(products.getList());
    }

    /**
     * При нажатии на кнопку "Принять изделие" выбранное в таблице изделий записывается в файл принятых изделий.
     * @param actionEvent
     */
    public void onAccept(ActionEvent actionEvent) {
        try {
            accept.add(dishesReady.getList().get(tableDishView.getSelectionModel().getSelectedIndex()));
            tableDishView.getItems().remove(tableDishView.getSelectionModel().getSelectedIndex());
            accept.saveToFile("D:/project/xml/accepted_dishes.xml");
        }
        catch (Exception e){}
    }

    /**
     * При нажатии на кнопку "Отвергнуть изделие" выбранное в таблице изделие записывается в файл бракованных изделий.
     * @param actionEvent
     */
    public void onReject(ActionEvent actionEvent) {
        try{
            reject.add(dishesReady.getList().get(tableDishView.getSelectionModel().getSelectedIndex()));
            tableDishView.getItems().remove(tableDishView.getSelectionModel().getSelectedIndex());
            reject.saveToFile("/project/xml/rejected_dishes.xml");
        }
        catch (Exception e){}
    }

    /**
     * При нажатии на кнопку "Принять изделие" порядок сборки изделия и список деталей записываются в файл, где храняться изделия.
     * Происходит очистка полей для отображения информации об изделии.
     * @param actionEvent
     */
    public void onReady(ActionEvent actionEvent) {
        try{
        	Dish NP = new Dish(D);
            NP.setAssembly(textAssembly.getText());
            ObservableList<Products> list = FXCollections.observableArrayList();
            for(Object P : tableProductsInDish.getItems())
                list.add((Products)P);
            NP.setProducts(list);
            dishesRedact.getList().set(dishesRedact.getList().indexOf(D), NP);
            tableProductsInDish.getItems().clear();
            textId.clear();
            textName.clear();
            textSize.clear();
            textAssembly.clear();
            dishesRedact.saveToFile("D:/project/xml/dishes.xml");
        }
        catch (Exception E) {
            MyErrorClass.showMesssage("Ошибка", "Ошибка при вводе данных", "Не выбрано изделие", MyErrorClass.MessageType.ERROR);}
    }

    /**
     * При нажатии на кнопку "Выбрать изделие" информация об изделии переносится из таблицы в соответствующие поля.
     * @param actionEvent
     */
    public void onChoose(ActionEvent actionEvent) {
        try {
            D = (Dish) dishesRedact.getList().get(tableDishRedact.getSelectionModel().getSelectedIndex());
            textId.setText(String.valueOf(D.getId()));
            textName.setText(D.getName());
            textSize.setText(D.getSize());
            textAssembly.setText(D.getAssembly());
            tableProductsInDish.setItems(D.getProducts());
        }
        catch (Exception e){}
    }

    /**
     * Метод добавляет деталь из общего списка деталей в список деталей конкретного изделия.
     * @param actionEvent
     */
    public void onAdd(ActionEvent actionEvent) {
        int index = tableProductsChoose.getSelectionModel().getSelectedIndex();
        tableProductsInDish.getItems().add((Products)products.getList().get(index));
    }

    /**
     * Метод удаляет деталь из списка деталей конкретного изделия.
     * @param actionEvent
     */
    public void onDel(ActionEvent actionEvent) {
        int index = tableProductsInDish.getSelectionModel().getSelectedIndex();
        tableProductsInDish.getItems().remove(index);
    }

}
