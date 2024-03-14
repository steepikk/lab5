package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.input.Console;

/**
 * Команда для очистки коллекции
 *
 * @author steepikk
 */
public class ClearCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public ClearCommand(Console console, CollectionController collectionController) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        collectionController.clearCollection();
        console.println("Коллекция драконов очищена!");
        return true;
    }
}
