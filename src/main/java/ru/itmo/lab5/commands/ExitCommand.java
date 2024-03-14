package ru.itmo.lab5.commands;

import ru.itmo.lab5.input.Console;

/**
 * Команда для завершения работы программы
 *
 * @author steepikk
 */
public class ExitCommand extends Command {
    private final Console console;

    public ExitCommand(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        console.println("Завершение выполнения");
        return true;
    }
}
