package ru.digitalleague.monopoly.exceptions;

/**
 * Базовый класс ошибок для игроков.
 */
public abstract class PlayerException extends Exception {

    public PlayerException(String message) {
        super(message);
    }

    /**
     * У игрока недостаточно средств для оплаты.
     */
    public static class NotEnoughMoneyException extends PlayerException {

        public NotEnoughMoneyException(String message) {
            super(message);
        }
    }
}
