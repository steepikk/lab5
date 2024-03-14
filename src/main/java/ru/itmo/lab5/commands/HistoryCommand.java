package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CommandController;
import ru.itmo.lab5.input.Console;

/**
 * Команда для вывода последних 8 команд
 *
 * @author steepikk
 */
public class HistoryCommand extends Command {
    private final Console console;
    private final CommandController commandController;

    public HistoryCommand(Console console, CommandController commandController) {
        super("history", "вывести последние 8 команд");
        this.console = console;
        this.commandController = commandController;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        console.println(String.join("\n", commandController.get8CommandHistory()));
        return true;
    }
}
