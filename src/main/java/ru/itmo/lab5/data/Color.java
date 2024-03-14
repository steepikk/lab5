package ru.itmo.lab5.data;

/**
 * Перечисление содержащее все цвета
 *
 * @author steepikk
 */

public enum Color {
    GREEN,
    BLACK,
    BLUE,
    ORANGE,
    BROWN;

    /**
     * Названия всех цветов через запятую
     *
     * @return строка со всеми цветами
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var colorType : values()) {
            nameList.append(colorType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
