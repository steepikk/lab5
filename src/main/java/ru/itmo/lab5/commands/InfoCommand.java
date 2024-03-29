package ru.itmo.lab5.commands;

import ru.itmo.lab5.controllers.CollectionController;
import ru.itmo.lab5.input.Console;

import java.time.LocalDateTime;

/**
 * Команда для вывода информации о коллекции
 *
 * @author steepikk
 */
public class InfoCommand extends Command {
    private final Console console;
    private final CollectionController collectionController;

    public InfoCommand(Console console, CollectionController collectionController) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.collectionController = collectionController;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Пожалуйста введите команду в правильном формате");
            return false;
        }
        LocalDateTime lastInitTime = collectionController.getLastInitTime();
        String lastInitTimeStr = (lastInitTime == null) ? "Инициализации в данной сессии еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
        LocalDateTime lastSaveTime = collectionController.getLastSaveTime();
        String lastSaveTimeStr = (lastSaveTime == null) ? "Сохранения в данной сессии еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();
        console.println("Информация о коллекции:");
        console.println("Тип коллекции: " + collectionController.getType());
        console.println("Размер коллекции: " + collectionController.getSize());
        console.println("Дата и время последней инициализации: " + lastInitTimeStr);
        console.println("Дата и время последнего сохранения: " + lastSaveTimeStr);
        return true;
    }
}
