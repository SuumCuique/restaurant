package project.Model;


import javafx.collections.ObservableList;
import project.Model.MyList;

/**
 * Класс для описания абстрактного списка.
 * Здесь представлены основные методы, через которые будет осуществляться обращение к спискам
 */
abstract class AbstractList implements MyList {
    protected ObservableList<AbstractSubject> list;

    /**
     * Возвращает список объектов AbstractSubject в виде списка ObservableList.
     * @return
     */
    public ObservableList<AbstractSubject> getList() {
        return list;
    }

    /**
     * Устанавливает список объектов.
     * @param list
     */
    public void setList(ObservableList<AbstractSubject> list) {
        this.list = list;
    }

    /**
     * Добавляет новый объект в список.
     * @param element
     * @return
     */
    public boolean add(AbstractSubject element){
        return list.add(element);
    }

    /**
     * Удаляет объект из списка.
     * @param element
     * @return
     */
    public boolean remove(AbstractSubject element){
        return list.remove(element);
    }

    /**
     * Очищает список.
     */
    public void clear(){
        list.clear();
    }

    /**
     * Возвращает длинну списка.
     * @return
     */
    public int length(){
        return list.size();
    }
}
