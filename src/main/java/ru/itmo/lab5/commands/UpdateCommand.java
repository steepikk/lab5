package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.data.Dragon;
import ru.itmo.lab5.exceptions.*;
import ru.itmo.lab5.input.Console;
import ru.itmo.lab5.input.DragonInput;

import javax.swing.*;

/**
 * Команда для обновления значения элемента коллекции по айди
 *
 * @author steepikk
 */
public class UpdateCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public UpdateCommand(Console console, CollectionController collectionController) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new InvalidAmountException();
            var id = Integer.parseInt(args[1]);
            var product = collectionController.getById(id);
            if (product == null) throw new MustBeNotEmptyException();

            console.println("Введите новые данные дракона:");
            console.ps2();

            Dragon newDragon = (new DragonInput(console)).make();
            product.update(newDragon);

            console.println("Дракон успешно обновлен!");
            return true;
        } catch (InvalidAmountException exception) {
            console.printError("Неправильное количество аргументов!");
        } catch (InvalidFormException | InvalidValueException e) {
            console.printError("Поля дракона не валидны! Дракон не обновлен!");
        } catch (MustBeNotEmptyException exception) {
            console.printError("Дракона с таким ID в коллекции нет!");
        } catch (IncorrectScriptException ignored) {
        }
        return false;
    }
}
