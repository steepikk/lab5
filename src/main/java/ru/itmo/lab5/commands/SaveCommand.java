package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.input.Console;

/**
 * Команда для сохранения коллекции в файл
 *
 * @author steepikk
 */
public class SaveCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public SaveCommand(Console console, CollectionController collectionController) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        collectionController.saveCollection();
        return true;
    }
}
