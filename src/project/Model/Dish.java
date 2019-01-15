package project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Класс для описания приготовляемых блюд
 *
 * Поля:
 * id - ID;
 * name - Название изделия;
 * size - Размеры изделия;
 * assembly - Порядок сборки изделия;
 * count - Количество описываемых изделий, которые имеются в наличии;
 * info - Любая дополнительная информация о детали, которая не была описана выше;
 * picture - Поле для хранения адреса изображения изделия;
 * details - Список деталей, из которых собирается изделие.
 */

public class Dish extends AbstractSubject {
    private int id;
    private String name;
    private String size;
    private String assembly;
    private int count;
    private String info;
    private ObservableList<Products> products;

    public Dish()
    {
        this.id = 0;
        this.name = "";
        this.size = "";
        this.assembly = "";
        this.count = 0;
        this.info = "";
        this.products = FXCollections.observableArrayList();
    }

    public Dish(int id, String name, String size, String assembly, int count, String info, ObservableList<Products> products) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.assembly = assembly;
        this.count = count;
        this.info = info;
        this.products = products;
    }

    public Dish(Dish D)
    {
        this.id = D.id;
        this.name = D.name;
        this.size = D.size;
        this.assembly = D.assembly;
        this.count = D.count;
        this.info = D.info;
        this.products = D.products;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getAssembly() {
        return assembly;
    }

    public int getCount() {
        return count;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setAssembly(String assembly) {
        this.assembly = assembly;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ObservableList<Products> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Products> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", assembly='" + assembly + '\'' +
                ", count=" + count +
                ", info='" + info + '\'' +
                ", products=" + products +
                '}';
    }
}
