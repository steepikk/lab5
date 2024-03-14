package ru.itmo.lab5.commands;


import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.exceptions.*;
import ru.itmo.lab5.input.Console;
import ru.itmo.lab5.input.DragonInput;

/**
 * Команда для добавления элемента в коллекцию, если его возраст превышает максимальный возраст этой коллекции
 *
 * @author steepikk
 */
public class AddIfMaxCommand extends Command {

    private final Console console;
    private final CollectionController collectionController;

    public AddIfMaxCommand(Console console, CollectionController collectionController) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его цена превышает максимальный возраст этой коллекции");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new InvalidAmountException();
            var product = ((new DragonInput(console)).make());

            if (collectionController.greaterThanAll(product)) {
                collectionController.addToCollection(product);
                console.println("Дракон успешно добавлен!");
            } else
                console.println("Дракон не добавлен, возраст не максимальный");
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
