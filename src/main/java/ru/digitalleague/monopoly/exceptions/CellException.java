package ru.digitalleague.monopoly.exceptions;

/**
 * Базовый класс ошибок при работе с ячейками игрового поля.
 */
public abstract class CellException extends Exception {

    public CellException(String message) {
        super(message);
    }

    /**
     * Пытаемся купить ячейку, которая не подлежит продаже.
     */
    public static class CellNotBuyException extends CellException {

        public CellNotBuyException(String message) {
            super(message);
        }
    }
}
