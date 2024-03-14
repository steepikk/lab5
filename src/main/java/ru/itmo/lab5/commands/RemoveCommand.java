package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.exceptions.*;
import ru.itmo.lab5.input.Console;

/**
 * Команда для удаления элемента из коллекции по айди
 *
 * @author steepikk
 */
public class RemoveCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public RemoveCommand(Console console, CollectionController collectionController) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new InvalidAmountException();
            var id = Integer.parseInt(args[1]);
            var dragon = collectionController.getById(id);
            if (dragon == null) throw new MustBeNotEmptyException();

            collectionController.removeFromCollection(dragon);
            console.println("Дракон успешно удален!");
            return true;
        } catch (InvalidAmountException exception) {
            console.printError("Неправильное количество аргументов!");
        } catch (MustBeNotEmptyException exception) {
            console.printError("Дракона с таким ID в коллекции нет!");
        }
        return false;
    }

}
