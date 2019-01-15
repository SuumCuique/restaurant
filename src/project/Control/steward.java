package project.Control;

import javafx.scene.control.cell.CheckBoxTableCell;
import project.Model.Products;
import project.Model.MyErrorClass;
import project.Model.Dish;
import project.Model.DishList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

/**
 * Класс-контроллер для взаимодействия с рабочей областью официанта.
 */

public class steward {
	  	@FXML
	    Label lbLogin;
	    @FXML
	    TableView tableDishProducts;
	    @FXML
	    Button btnNext;
	    @FXML
	    TextArea textAssembly;
	    @FXML
	    TextField textId;
	    @FXML
	    TextField textName;
	    @FXML
	    TextField textSize;

	    private DishList dishes;
	    private DishList toSendDishes;
	    private int index;
	    private boolean regim;
	    private boolean next;
	    private int number;

	    /**
	     * Инициализация компонентов графического интерфейса.
	     * Создание таблицы для хранения заказов.
	     */
	    public void initialize() {
	        dishes = new DishList("D:/project/xml/dishes.xml");
	        toSendDishes = new DishList("D:/project/xml/check_dishes.xml");
	        index=0;
	        number=0;
	        regim = true;
	        next = true;
	        //---------------------ДЕТАЛИ---------------------
	        TableColumn<Products, Boolean> check_products = new TableColumn<>("-");
	        TableColumn<Products, String> id_products = new TableColumn<>("id");
	        TableColumn<Products, String> name_products = new TableColumn<>("Название");
	        TableColumn<Products, String> info_products = new TableColumn<>("Информация");
	        TableColumn<Products, String> count_products = new TableColumn<>("Количество");
	        check_products.setCellFactory(param -> new CheckBoxTableCell<>());
	        id_products.setCellValueFactory(new PropertyValueFactory<>("id"));
	        name_products.setCellValueFactory(new PropertyValueFactory<>("name"));
	        info_products.setCellValueFactory(new PropertyValueFactory<>("info"));
	        count_products.setCellValueFactory(new PropertyValueFactory<>("count"));
	        tableDishProducts.getColumns().addAll(check_products, id_products,name_products,info_products,count_products); //table product поменять
	    }

	    /**
	     * При первом нажатии на кнопку происходит отображение нового заказа на сборку изделия.
	     * Заполняются необходимые текстовые поля для отображения информации об изделии, а также в таблицу записывается информаци о деталях.
	     *
	     * При втором нажатии на кнопку происходит помещение выполненого заказа в файл для последующей проверки инженером.
	     * Также производится очистка полей для того, чтобы рабочий мог взять новый заказ.
	     * @param actionEvent
	     */
	    public void onNext(ActionEvent actionEvent) {
	        try {
	            if(index < dishes.length())
	                if(regim) {
	                    Dish D = (Dish) dishes.getList().get(index);
	                    textId.setText(String.valueOf(D.getId()));
	                    textName.setText(D.getName());
	                    textSize.setText(D.getSize());
	                    textAssembly.setText(D.getAssembly());
	                    tableDishProducts.setItems(D.getProducts());
	                    if(D.getCount()>=1){
	                        D.setCount(D.getCount()-1);
	                        next=false;
	                    }
	                    dishes.saveToFile("D:/project/xml/dishes.xml");
	                    regim=!regim;
	                    btnNext.setText("Отправить заказ");
	                    lbLogin.setText("Заказ: "+String.valueOf(++number));
	                }
	                else
	                {
	                	Dish Q = (Dish)dishes.getList().get(index);
	                	Dish D = new Dish(Q.getId(),Q.getName(),Q.getSize(),Q.getAssembly(),Q.getCount(),Q.getInfo(),Q.getProducts());
	                    D.setCount(1);
	                    toSendDishes.add(D);
	                    toSendDishes.saveToFile("D:/project/xml/check_dishes.xml");
	                    textId.clear();
	                    textName.clear();
	                    textSize.clear();
	                    textAssembly.clear();
	                    regim=!regim;
	                    btnNext.setText("Принять заказ");
	                    if(next) index++;
	                    next = true;
	                }
	        }
	        catch (Exception e) {
	            MyErrorClass.showMesssage("Ошибка", "Ошибка", e.getMessage(), MyErrorClass.MessageType.ERROR);
	        }
	    }
}
