package ru.itmo.lab5.data;

import ru.itmo.lab5.utils.Validateable;

import java.util.Objects;

/**
 * Класс координат
 *
 * @author steepikk
 */

public class Coordinates implements Validateable {
    private double x; //Максимальное значение поля: 244
    private Integer y; //Максимальное значение поля: 826, Поле не может быть null

    public Coordinates(double x, Integer y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean validate() {
        if (y == null) return false;
        return x <= 244 && y <= 826;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(x, that.x) == 0 && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + ";" + y;
    }
}