package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.exceptions.*;
import ru.itmo.lab5.input.Console;
import ru.itmo.lab5.input.DragonInput;

/**
 * Команда для добавления элемента в коллекцию
 *
 * @author steepikk
 */
public class AddCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public AddCommand(Console console, CollectionController collectionController) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new InvalidAmountException();
            collectionController.addToCollection((new DragonInput(console)).make());
            console.println("Дракон успешно добавлен!");
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
