package ru.itmo.lab5;

import ru.itmo.lab5.commands.*;
import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.controllers.CommandController;
import ru.itmo.lab5.controllers.Parser;
import ru.itmo.lab5.data.Dragon;
import ru.itmo.lab5.input.Console;
import ru.itmo.lab5.input.InputSteamer;
import ru.itmo.lab5.utils.Executor;

import java.util.Scanner;

/**
 * Главный класс приложения.
 *
 * @author steepikk
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        InputSteamer.setScanner(new Scanner(System.in));
        var console = new Console();

        if (args.length == 0) {
            console.printError("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        var parser = new Parser(args[0], console);
        var collectionController = new CollectionController(parser);

        Dragon.touchNextId(collectionController);
        collectionController.validateAll(console);

        var commandController = new CommandController() {
            {
                commandAdd("help", new HelpCommand(console, this));
                commandAdd("info", new InfoCommand(console, collectionController));
                commandAdd("show", new ShowCommand(console, collectionController));
                commandAdd("add", new AddCommand(console, collectionController));
                commandAdd("update", new UpdateCommand(console, collectionController));
                commandAdd("remove", new RemoveCommand(console, collectionController));
                commandAdd("clear", new ClearCommand(console, collectionController));
                commandAdd("save", new SaveCommand(console, collectionController));
                commandAdd("execute_script", new ExecuteScriptCommand(console));
                commandAdd("exit", new ExitCommand(console));
                commandAdd("history", new HistoryCommand(console, this));
                commandAdd("add_if_max", new AddIfMaxCommand(console, collectionController));
                commandAdd("remove_greater", new RemoveGreaterCommand(console, collectionController));
                commandAdd("filter_less_then_character", new FilterLessThanCharacterCommand(console, collectionController));
                commandAdd("count_less_than_age", new CountLessThanAgeCommand(console, collectionController));
                commandAdd("print_ascending", new PrintAscendingCommand(console, collectionController));
            }
        };

        new Executor(console, commandController).fromConsole();
    }
}
