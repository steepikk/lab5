package ru.itmo.lab5.utils;

import ru.itmo.lab5.controllers.CommandController;
import ru.itmo.lab5.exceptions.*;
import ru.itmo.lab5.input.Console;
import ru.itmo.lab5.input.InputSteamer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Класс для выполнения программы
 *
 * @author steepikk
 */
public class Executor {
    private final Console console;
    private final CommandController commandController;
    private final Set<String> scriptStack = new HashSet<>();

    public Executor(Console console, CommandController commandController) {
        this.console = console;
        this.commandController = commandController;
    }

    boolean flag = false;
    Integer depth = 0;


    /**
     * Интерактивный режим
     */
    /*public void fromConsole() {
        var userScanner = InputSteamer.getScanner();
        try {
            ExitCode exitCode = ExitCode.OK;
            String[] inputCommand = {"", ""};

            while (exitCode != ExitCode.EXIT) {
                console.ps1();
                try {
                    inputCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                    inputCommand[1] = inputCommand[1].trim();
                } catch (NoSuchElementException exception) {
                    console.println("Ввод завершён. Сохранение даннных в файл...");
                    commandController.getCommands().get("save").execute(new String[2]);
                    break;
                }

                commandController.addToHistory(inputCommand[0]);
                exitCode = apply(inputCommand);
            }
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }*/
    public void fromConsole() {
        var userScanner = InputSteamer.getScanner();
        try {
            ExitCode exitCode = ExitCode.OK;
            String[] inputCommand = {"", ""};

            while (exitCode != ExitCode.EXIT) {
                console.ps1();
                try {
                    inputCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                    inputCommand[1] = inputCommand[1].trim();
                } catch (NoSuchElementException e) {
                    console.println("Ввод принудительно завершен. Сохранение данных в файл...");
                    String[] errorCommand = new String[2];
                    errorCommand[0] = "save";
                    errorCommand[1] = "";
                    commandController.getCommands().get("save").execute(errorCommand);
                    break;
                }

                commandController.addToHistory(inputCommand[0]);
                exitCode = apply(inputCommand);
            }
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }


    /**
     * Режим для запуска скрипта.
     *
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    public ExitCode fromScript(String argument) {


        Integer maxDepth = 3;
        String[] inputCommand = {"", ""};
        ExitCode exitCode;
        scriptStack.add(argument);


        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }

        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = InputSteamer.getScanner();
            InputSteamer.setScanner(scriptScanner);
            InputSteamer.setFileMode(true);


            do {

                inputCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                inputCommand[1] = inputCommand[1].trim();

                while (scriptScanner.hasNextLine() && inputCommand[0].isEmpty()) {
                    inputCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    inputCommand[1] = inputCommand[1].trim();
                }

                console.println(console.getPS1() + String.join(" ", inputCommand));
                if (inputCommand[0].equals("execute_script")) {
                    if (!scriptStack.add(inputCommand[1])) {
                        if (flag == false) {
                            InputSteamer.setScanner(tmpScanner);
                            InputSteamer.setFileMode(false);
                            maxDepth = setMaxDepth();
                            InputSteamer.setScanner(scriptScanner);
                            InputSteamer.setFileMode(true);
                            flag = true;
                        }
                        depth++;
                    }
                    if (depth > maxDepth) throw new ScriptRecursionException();
                }
                exitCode = apply(inputCommand);
            } while (exitCode == ExitCode.OK && scriptScanner.hasNextLine());

            InputSteamer.setScanner(tmpScanner);
            InputSteamer.setFileMode(false);
            flag = false;
            depth = 0;

            if (exitCode == ExitCode.ERROR && !(inputCommand[0].equals("execute_script") && !inputCommand[1].isEmpty())) {
                console.println("Проверьте скрипт на корректность введенных данных!");
            }

            return exitCode;
        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            console.printError("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            console.printError("Максимальная глубина рекурсии превышена!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(argument);
        }
        return ExitCode.ERROR;
    }

    /**
     * Выполняет команду
     *
     * @param inputCommand Команда для запуска
     * @return Код завершения.
     */
    private ExitCode apply(String[] inputCommand) {
        if (inputCommand[0].equals("")) return ExitCode.OK;
        var command = commandController.getCommands().get(inputCommand[0]);

        if (command == null) {
            console.printError("Команда '" + inputCommand[0] + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }

        if (inputCommand[0].equals("exit")) {
            if (command.execute(inputCommand)) return ExitCode.EXIT;
            else return ExitCode.ERROR;
        } else if (inputCommand[0].equals("execute_script")) {
            if (command.execute(inputCommand)) return fromScript(inputCommand[1]);
            else return ExitCode.ERROR;
        } else if (!command.execute(inputCommand))
            return ExitCode.ERROR;

        return ExitCode.OK;
    }


    public Integer setMaxDepth() {
        console.println("Введите максимальную глубину рекурсии");
        var strMaxDepth = InputSteamer.getScanner().nextLine().trim();
        return Integer.parseInt(strMaxDepth);
    }

    public enum ExitCode {
        OK,
        ERROR,
        EXIT;
    }
}
