package ru.itmo.lab5.data;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.utils.Validateable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Класс дракона
 *
 * @author steepikk
 */

public class Dragon implements Validateable, Comparable<Dragon> {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long age; //Значение поля должно быть больше 0
    private String description; //Поле не может быть null
    private Color color; //Поле может быть null
    private DragonCharacter character; //Поле не может быть null
    private Person killer; //Поле может быть null
    public static int nextId = 1;

    public Dragon(String name, Coordinates coordinates, long age, String description, Color color, DragonCharacter character, Person killer) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.age = age;
        this.description = description;
        this.color = color;
        this.character = character;
        this.killer = killer;
        this.id = nextId++;
    }

    @Override
    public boolean validate() {
        if (id == null || id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (age <= 0) return false;
        if (color == null) return false;
        if (character == null) return false;
        return true;
    }

    /**
     * Обновляет дракон
     *
     * @param dragon
     */

    public void update(Dragon dragon) {
        this.name = dragon.name;
        this.coordinates = dragon.coordinates;
        this.creationDate = dragon.creationDate;
        this.age = dragon.age;
        this.description = dragon.description;
        this.color = dragon.color;
        this.character = dragon.character;
        this.killer = dragon.killer;
    }

    public Integer getId() {
        return id;
    }

    public long getAge() {
        return age;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public static void touchNextId(CollectionController collectionController) {
        var maxId = collectionController
                .getCollection()
                .stream().filter(Objects::nonNull)
                .map(Dragon::getId)
                .mapToInt(Integer::intValue).max().orElse(0);
        nextId = maxId + 1;
    }

    @Override
    public int compareTo(Dragon dragon) {
        return (int) (this.id - dragon.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return age == dragon.age && Objects.equals(id, dragon.id) && Objects.equals(name, dragon.name) && Objects.equals(coordinates, dragon.coordinates) && Objects.equals(creationDate, dragon.creationDate) && Objects.equals(description, dragon.description) && color == dragon.color && character == dragon.character && Objects.equals(killer, dragon.killer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, description, color, character, killer);
    }

    // Переделать под условия
    @Override
    public String toString() {
        return "d{\"id\": " + (id == null ? "null" : "\"" + id.toString() + "\"") + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"creationDate\" = \"" + creationDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\", " +
                "\"age\": " + age + ", " +
                "\"description\": \"" + description + "\", " +
                "\"color\" = \"" + color + "\", " +
                "\"character\": " + (character == null ? "null" : "\"" + character + "\"") + ", " +
                "\"killer\": " + (killer == null ? "null" : "\"" + killer + "\"") + "}";
    }
}
