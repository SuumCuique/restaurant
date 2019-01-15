package project.Model;

/**
 * Класс для описания поваров.
 *
 * Поля:
 * id - ID;
 * FIO - Фамилия, имя и отчество сотрудника;
 */


public class Cook extends AbstractSubject{
    private int id;
    private String FIO;
	
    public Cook() {}
    
    public Cook(int id, String fIO) {
		super();
		this.id = id;
		FIO = fIO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFIO() {
		return FIO;
	}

	public void setFIO(String fIO) {
		FIO = fIO;
	}

	@Override
	public String toString() {
		return "Cook [id=" + id + ", FIO=" + FIO + "]";
	}
	
}
