package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.exceptions.InvalidAmountException;
import ru.itmo.lab5.input.Console;

/**
 * Команда для вывода коллекциии в порядке возрастания
 *
 * @author steepikk
 */
public class PrintAscendingCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public PrintAscendingCommand(Console console, CollectionController collectionController) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new InvalidAmountException();

            var set = collectionController.printAscending();
            if (set.isEmpty())
                console.println("Коллекция пуста");
            else
                set.forEach(console::println);
        } catch (InvalidAmountException e) {
            console.printError("Неправильное количество аргументов!");
        }
        return false;
    }
}
