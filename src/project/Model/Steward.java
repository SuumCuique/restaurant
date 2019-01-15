package project.Model;

/**
 * Класс для описания сотрудников завода.
 *
 * Поля:
 * id - ID;
 * FIO - Фамилия, имя и отчество сотрудника;
 * engineer - Фамилия, имя и отчество инженера, который отвечает за сотрудника.
 */

public class Steward extends AbstractSubject{
    private int id;
    private String FIO;
    
    public Steward() {}

	public Steward(int id, String fIO) {
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
		return "Steward [id=" + id + ", FIO=" + FIO + "]";
	}
	
}
