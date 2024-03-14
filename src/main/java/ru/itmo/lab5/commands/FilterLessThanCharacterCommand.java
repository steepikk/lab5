package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.exceptions.InvalidAmountException;
import ru.itmo.lab5.input.Console;

/**
 * Команда для вывода элементов, значение поля character которых меньше заданного
 *
 * @author steepikk
 */
public class FilterLessThanCharacterCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public FilterLessThanCharacterCommand(Console console, CollectionController collectionController) {
        super("filter_less_then_character <character>", "вывести элементы, значение поля character которых меньше заданного");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new InvalidAmountException();

            var list = collectionController.listLessThanCharacter(args[1]);
            if(list.isEmpty())
                console.println("Драконов, у которых поле character меньше заданной подстроки не обнаружено!");
            else
                list.forEach(console::println);
        } catch (InvalidAmountException e) {
            console.printError("Неправильное количество аргументов!");
        } catch (IllegalArgumentException e) {
            System.out.println("Значение подстроки не соответствует ни одному значению enum");
        }
        return false;
    }
}
