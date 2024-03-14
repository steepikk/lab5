package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.exceptions.InvalidAmountException;
import ru.itmo.lab5.input.Console;

/**
 * Команда для вывода количества элементов, значение поля age которых меньше заданного
 *
 * @author steepikk
 */
public class CountLessThanAgeCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public CountLessThanAgeCommand(Console console, CollectionController collectionController) {
        super("count_less_than_age <age>", "вывести количество элементов, значение поля age которых меньше заданного");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new InvalidAmountException();

            Integer countDragon = collectionController.countLessThenAge(args[1]);
            if (countDragon == 0)
                console.println("Драконов, у которых поле age меньше заданной подстроки не обнаружено!");
            else
                console.println(countDragon);

        } catch (InvalidAmountException e) {
            console.printError("Неправильное количество аргументов!");
        } catch (NumberFormatException e) {
            console.printError("Подстрока должна быть представлена числом!");
        }
        return false;
    }
}
