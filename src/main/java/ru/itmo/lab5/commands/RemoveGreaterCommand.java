package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.exceptions.*;
import ru.itmo.lab5.input.Console;
import ru.itmo.lab5.input.DragonInput;

/**
 * Команда для удаления из коллекции всех элементов больше заданного
 *
 * @author steepikk
 */
public class RemoveGreaterCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public RemoveGreaterCommand(Console console, CollectionController collectionController) {
        super("remove_greater {element}", "удалить из коллекции все элементы больше заданного");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new InvalidAmountException();
            var dragon = ((new DragonInput(console)).make());
            collectionController.removeGreater(dragon);
            console.println("Драконы успешно удалены!");
            return true;
        } catch (InvalidAmountException exception) {
            console.printError("Неправильное количество аргументов!");
        } catch (InvalidFormException | InvalidValueException e) {
            console.printError("Поля дракона не валидны! Дракон не создан!");
        } catch (IncorrectScriptException ignored) {
        }
        return false;
    }
}
