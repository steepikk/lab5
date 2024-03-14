package ru.itmo.lab5.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.lab5.data.Dragon;
import ru.itmo.lab5.input.Console;
import ru.itmo.lab5.utils.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Класс для работы с файлом
 *
 * @author steepikk
 */
public class Parser {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    private final String fileName;
    private final Console console;

    public Parser(String fileName, Console console) {
        if (!(new File(fileName).exists())) {
            fileName = "../" + fileName;
        }
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Записывает коллекцию в файл.
     *
     * @param collection коллекция
     */
    public void writeCollection(Collection<Dragon> collection) {
        try (BufferedWriter collectionBufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            collectionBufferedWriter.write(gson.toJson(collection));
            console.println("Коллекция успешно сохранена в файл!");
        } catch (IOException exception) {
            console.printError("Этот файл не может быть открыт!");
        }
    }

    /**
     * Считывает коллекцию из файла.
     *
     * @return Считанная коллекция
     */
    public Collection<Dragon> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<HashSet<Dragon>>() {
                }.getType();
                var reader = new BufferedReader(fileReader);

                var jsonString = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.length() == 0) {
                    jsonString = new StringBuilder("[]");
                }

                HashSet<Dragon> collection = gson.fromJson(jsonString.toString(), collectionType);

                console.println("Коллекция успешна загружена!");
                return collection;

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException | IOException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new HashSet<>();
    }

    public Console getConsole() {
        return console;
    }
}
