import java.util.HashSet;
import java.util.Set;

public class Algebra {

    public String name;
    public HashSet<Integer> elements;
    public HashSet<String> operations;
    public HashSet<Integer> neutralElements;

    public Algebra(String nameIn, HashSet<Integer> element, HashSet<String> operation, HashSet<Integer> neutralElement) { // конструктор -- создание экземпляра класса
        this.name = nameIn;
        this.elements = element;
        this.operations = operation;
        this.neutralElements = neutralElement;
    }

    // Метод для добавления элемента в множество
    public void addElement(int element) {
        elements.add(element);
    }

    // Метод для удаления элемента из множества
    public void removeElement(int element) {
        elements.remove(element);
    }

    // Метод для добавления операции в множество операций
    public void addOperation(String operation) {
        operations.add(operation);
    }

    // Метод для удаления операции из множества операций
    public void removeOperation(String operation) {
        operations.remove(operation);
    }

    // Метод для добавления нейтрального элемента
    public void addNeutralElement(int neutralElement) {
        neutralElements.add(neutralElement);
    }

    // Метод для удаления нейтрального элемента
    public void removeNeutralElement(int neutralElement) {
        neutralElements.remove(neutralElement);
    }

    // show
    public void showElement() {
        System.out.println(this.name + ": " + this.elements + " " + this.operations + ((this.neutralElements == null) ? "" : " " + this.neutralElements));
    }

    // Геттер для получения множества элементов
    public Set<Integer> getElements() {
        return elements;
    }

    // Геттер для получения множества операций
    public Set<String> getOperations() {
        return operations;
    }

    // Геттер для получения множества нейтральных элементов
    public Set<Integer> getNeutralElements() {
        return neutralElements;
    }
}
