package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CommandController;
import ru.itmo.lab5.input.Console;

/**
 * Команда для вывода справки о доступных командах
 *
 * @author steepikk
 */
public class HelpCommand extends Command {
    private final Console console;
    private final CommandController commandController;

    public HelpCommand(Console console, CommandController commandController) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandController = commandController;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        commandController.getCommands().values()
                .forEach(command -> console.printTable(command.getName(), command.getDescription()));
        return true;
    }
}
