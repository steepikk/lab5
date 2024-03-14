package ru.itmo.lab5.commands;

import ru.itmo.lab5.input.Console;

/**
 * Команда для исполнения скрипта
 *
 * @author steepikk
 */
public class ExecuteScriptCommand extends Command {
    String strMaxDepth;
    Integer maxDepth = 3;
    private final Console console;

    public ExecuteScriptCommand(Console console) {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }


    @Override
    public boolean execute(String[] args) {
        if (args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        console.println("Выполнение скрипта '" + args[1] + "':");
        return true;
    }

}
