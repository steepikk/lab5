package ru.itmo.lab5.controllers;

import ru.itmo.lab5.data.Dragon;
import ru.itmo.lab5.data.DragonCharacter;
import ru.itmo.lab5.input.Console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Класс для контроля коллекции
 *
 * @author steepikk
 */

public class CollectionController {
    private HashSet<Dragon> collection = new HashSet<Dragon>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final Parser parser;


    public CollectionController(Parser parser) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.parser = parser;

        loadCollection();
    }

    /**
     * Валидирует всех значений дракона
     *
     * @param console
     */
    public void validateAll(Console console) {
        (new ArrayList<>(this.getCollection())).forEach(dragon -> {
            if (!dragon.validate()) {
                console.println("Дракон с id = " + dragon.getId() + " имеет невалидные поля.");
            }
        });
        console.println("! Загруженные драконы валидны.");
    }

    /**
     * @return Коллекия
     */
    public HashSet<Dragon> getCollection() {
        return collection;
    }

    /**
     * @return Дата и время последней инициализации
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Дата и время последнего сохранения
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Тип коллекции
     */
    public String getType() {
        return collection.getClass().getName();
    }

    /**
     * @return Размер коллекции
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * @return Последний элемент коллекции
     */
    public Dragon getLast() {
        if (collection.isEmpty()) return null;
        return collection.stream().reduce((one, two) -> two).get();
    }

    /**
     * Получает элемент коллекции по его айди
     *
     * @param id
     * @return элемент
     */
    public Dragon getById(int id) {
        for (Dragon element : collection) {
            if (element.getId() == id) return element;
        }
        return null;
    }

    /**
     * Проверяет, что данный элемент самый большой в коллекции
     *
     * @param dragon элемент
     */
    public boolean greaterThanAll(Dragon dragon) {
        for (Dragon element : collection) {
            if (dragon.compareTo(element) < 0) return false;
        }
        return true;
    }

    /**
     * Удаляет из коллекции все элементы больше заданного
     *
     * @param dragon
     */
    public void removeGreater(Dragon dragon) {
        collection = collection.stream().filter(element -> dragon.compareTo(element) > 0).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     *  @return Коллекцию по возрастанию
     */
    public HashSet<Dragon> printAscending() {
        TreeSet<Dragon> ascendingTreeSet = new TreeSet<>(collection);
        HashSet<Dragon> ascendingHashSet = new HashSet<>(ascendingTreeSet);
        return ascendingHashSet;
    }

    /**
     * @param character Характер дракона
     * @return Отфильтрованную коллекцию, где характер дракона меньше, чем указанный в подстроке
     */
    public List<Dragon> listLessThanCharacter(String character) {
        List<Dragon> list = new ArrayList<>();
        DragonCharacter dragonCharacter = DragonCharacter.valueOf(character);
        for (Dragon element : collection) {
            if (element.getCharacter().compareTo(dragonCharacter) < 0) {
                list.add(element);
            }
        }
        return list;
    }

    /**
     * @param age Характер дракона
     * @return Количество драконов возраст, которых меньше заданного в подстроке
     */
    public Integer countLessThenAge(String age) {
        Integer countAge = 0;
        Integer constantAge = Integer.parseInt(age);
        for (Dragon element : collection) {
            if (element.getAge() < constantAge) {
                countAge += 1;
            }
        }
        return countAge;
    }

    /**
     * Добавляет элемент в коллекцию
     *
     * @param element Элемент для добавления
     */
    public void addToCollection(Dragon element) {
        collection.add(element);
    }

    /**
     * Удаляет элемент коллекции
     *
     * @param element элемент
     */
    public void removeFromCollection(Dragon element) {
        collection.remove(element);
    }

    /**
     * Очищает коллекцию
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        parser.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Загружает коллекцию из файла.
     */
    private void loadCollection() {
        collection = (HashSet<Dragon>) parser.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        var last = getLast();

        StringBuilder info = new StringBuilder();
        for (Dragon dragon : collection) {
            info.append(dragon);
            if (dragon != last) info.append("\n\n");
        }
        return info.toString();
    }
}
