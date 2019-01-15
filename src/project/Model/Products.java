package project.Model;

/**
 * Класс для описания продуктов используемых для блюда.
 *
 * Поля:
 * id - ID;
 * name - Название продукта;
 * count - Количество описываемых продуктов, которые имеются в наличии;
 * info - Любая дополнительная информация о продукте, которая не была описана выше.
 */

public class Products extends AbstractSubject {
    private int id;
    private String name;
    private int count;
    private String info;

    public Products() {
        this.id = 0;
        this.name = "";
        this.count = 0;
        this.info = "";
    }
    
    public Products(int id, String name,int count, String info) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.info = info;
    }

    public Products(Products P)
    {
        this.id = P.getId();
        this.name = P.getName();
        this.count = P.getCount();
        this.info = P.getInfo();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", name=" + name + ", count=" + count + ", info=" + info + "]";
	}
    
    
}
