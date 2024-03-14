package ru.itmo.lab5.commands;

/**
 * Абстрактная команда с именем и описанием.
 *
 * @author steepikk
 */
public abstract class Command {
    private String name, description;

    /**
     * Базовый класс команды
     *
     * @author hh
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Базовый вызов команды
     */
    public abstract boolean execute(String[] args);
}
