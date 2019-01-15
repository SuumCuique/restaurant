package project.Model;

/**
 * Интерфейс, описывающий загрузку и сохранение данных из списка в файл.
 */
interface MyList {
    /**
     * Метод для загрузки данных из файла.
     * @param fileName
     * @return
     * @throws Exception
     */
    boolean loadFromFile(String fileName) throws Exception;

    /**
     * Метод для сохранения данных в файл.
     * @param fileName
     * @return
     * @throws Exception
     */
    boolean saveToFile(String fileName) throws Exception;
}
