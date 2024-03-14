package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.input.Console;

/**
 * Команда для вывода всех элементов коллекции
 *
 * @author steepikk
 */
public class ShowCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public ShowCommand(Console console, CollectionController collectionController) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        console.println(collectionController);
        return true;
    }
}
