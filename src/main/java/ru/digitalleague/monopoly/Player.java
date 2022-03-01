package ru.digitalleague.monopoly;

import ru.digitalleague.monopoly.exceptions.PlayerException;

import java.util.Objects;

public class Player {

    private final String name;
    private int balance;
    private Cell cell;
    private boolean active;

    public Player(String name, int balance, Cell cell) {
        this.name = name;
        this.balance = balance;
        this.cell = cell;
    }

    /**
     * Вернуть клетку, на которой в данный момент находится игрок.
     *
     * @return игровая клетка
     */
    public Cell getCurrentCell() {
        return this.cell;
    }

    /**
     * Установить текущую клетку игрока.
     *
     * @param cell клетка
     */
    public void setCurrentCell(Cell cell) {
        this.cell = cell;
    }

    /**
     * Вернуть текущий баланс игрока.
     *
     * @return текущий баланс
     */
    public int getCurrentBalance() {
        return this.balance;
    }

    /**
     * Вернуть имя игрока.
     *
     * @return имя игрока
     */
    public String getName() {
        return this.name;
    }

    /**
     * Осуществить покупку.
     *
     * @param money стоимость покупки
     * @throws PlayerException.NotEnoughMoneyException у игрока недостаточно средств
     */
    public void pay(int money) throws PlayerException.NotEnoughMoneyException {
        int result = this.balance - money;
        if (result < 0) throw new PlayerException.NotEnoughMoneyException("");
        this.balance = result;
    }

    /**
     * Пополнить баланс игрока.
     *
     * @param money количество денег для пополнения баланса
     */
    public void replenishBalance(int money) {
        this.balance += money;
    }

    /**
     * Объявить игрока банкротом.
     */
    void bankruptcy() {
        this.balance = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", cell=" + cell +
                '}';
    }
}
