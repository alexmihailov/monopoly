package ru.digitalleague.monopoly;

import ru.digitalleague.monopoly.exceptions.CellException;
import ru.digitalleague.monopoly.exceptions.PlayerException;

/**
 * Класс игровой ячейки (клетки).
 */
public class Cell {

    private final int number;
    private final CellType type;
    private final int price;

    private Player owner;

    public Cell(int number, CellType type, int price) {
        this.number = number;
        this.type = type;
        this.price = price;
    }

    /**
     * Получить порядковый номер ячейки.
     *
     * @return порядковый номер ячейки
     */
    public int getNumber() {
        return number;
    }

    /**
     * Получить тип ячейки.
     *
     * @return тип ячейки
     */
    public CellType getType() {
        return type;
    }

    /**
     * Получить стоимость ячейки.
     *
     * @return стоимость ячейки
     */
    public int getPrice() {
        return price;
    }

    /**
     * Проверить, что ячейка доступна к покупке.
     * К покупке доступна только платная ячейка
     *
     * @return true, если ячейка доступна к покупке
     */
    public boolean isBuy() {
        return this.type == CellType.PAID;
    }

    /**
     * Проверить, что у ячейки есть владелец.
     *
     * @return true, если ячейка уже куплена
     */
    public boolean isOwned() {
        return this.owner != null;
    }

    /**
     * Купить ячейку.
     *
     * @param player игрок покупающий ячейку
     * @throws CellException ошибка ячейки во время покупки
     */
    public void buy(Player player) throws CellException {
        if (!isBuy()) throw new CellException.CellNotBuyException("");
        if (player.equals(owner)) return;
        try {
            player.pay(price);
            if (!isOwned()) {
                owner = player;
            } else {
                owner.replenishBalance(price);
            }
        } catch (PlayerException.NotEnoughMoneyException e) {
            if (isOwned()) {
                owner.replenishBalance(player.getCurrentBalance());
                player.bankruptcy();
            }
        }
    }

    @Override
    public String toString() {
        return "Cell{" +
                "number=" + number +
                ", type=" + type +
                ", price=" + price +
                ", owner=" + (isOwned() ? owner.getName() : null) +
                '}';
    }
}
