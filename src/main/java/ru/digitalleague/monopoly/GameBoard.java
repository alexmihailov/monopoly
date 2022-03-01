package ru.digitalleague.monopoly;

import ru.digitalleague.monopoly.exceptions.CellException;
import ru.digitalleague.monopoly.exceptions.PlayerException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс игровой доски.
 */
public class GameBoard {

    private final Random random = new Random();
    protected final List<Cell> cells;
    protected final List<Player> players;
    protected final List<String> history;

    public GameBoard(int countCells, int countPlayers, int initialBalance) {
        this.cells = initCells(countCells);
        this.players = initPlayers(countPlayers, initialBalance, cells.get(0));
        this.history = new ArrayList<>();
    }

    /**
     * Инициализация клеток игрового поля.
     *
     * @param countCells количество клеток
     * @return список с клетками игрового поля
     */
    private List<Cell> initCells(int countCells) {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(0, CellType.START, 0));
        for (int i = 1; i < countCells; ++i) {
            Cell cell;
            if (i % 3 == 0) {
                cell = new Cell(i, CellType.TAX, random.nextInt(100));
            } else if (random.nextInt(20) % 2 == 0) {
                cell = new Cell(i, CellType.FREE, 0);
            } else {
                cell = new Cell(i, CellType.PAID, 50);
            }
            cells.add(cell);
        }
        return cells;
    }

    /**
     * Инициализация игроков.
     *
     * @param countPlayers количество игроков
     * @param initialBalance начальный баланс каждого игрока
     * @param startCell начальна клетка игрока
     * @return список с игроками
     */
    private static List<Player> initPlayers(int countPlayers, int initialBalance, Cell startCell) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= countPlayers; ++i){
            players.add(new Player("Player" + i, initialBalance, startCell));
        }
        return players;
    }

    /**
     * Логирование игры.
     *
     * @param step шаг
     * @param start true, если начало шага
     */
    private void log(int step, boolean start) {
        StringBuilder builder = new StringBuilder();
        if (start) {
            builder.append("Start game step: ");
        } else {
            builder.append("End game step: ");
        }
        builder.append(step).append("\n");

        for (Player player : this.players) {
            builder.append(player).append("\n");
        }
        this.history.add(builder.toString());
    }

    /**
     * Бросок игрового кубика.
     * В результате броска нужно вернуть кубик со случайным числом от 1 до 6.
     *
     * @return случайно брошенный игровой кубик
     */
    protected GameCube diceRoll() {
        // случайное число в диапазоне от 1 до 6
        int number = random.nextInt(6) + 1;
        return new GameCube(number);
    }

    /**
     * Совершить переход игрока на количество клеток брошенных двумя кубиками.
     * Возможен круговой обход.
     *
     * @param current текущий игрок
     * @return игровая клетка, на которую попал игрок
     */
    protected Cell move(Player current) {
        GameCube first = diceRoll();
        GameCube second = diceRoll();
        int count = first.getNumber() + second.getNumber() + current.getCurrentCell().getNumber() + 1;
        if (count > cells.size()) {
            return cells.get(count % cells.size());
        } else {
            return cells.get(count - 1);
        }
    }

    /**
     * Метод эмуляции игры.
     *
     * @return победитель
     */
    public Player play() throws CellException {
        int step = 1;
        while (this.players.size() > 1) {
            log(step, true);
            for (Player player : this.players) {
                Cell cell = move(player);
                switch (cell.getType()) {
                    case PAID:
                        cell.buy(player);
                        break;
                    case TAX:
                        try {
                            player.pay(cell.getPrice());
                        } catch (PlayerException.NotEnoughMoneyException e) {
                            player.bankruptcy();
                        }
                        break;
                }
                player.setCurrentCell(cell);
            }
            log(step, false);
            this.players.removeIf(it -> it.getCurrentBalance() <= 0 && this.players.size() > 1);
            step++;
        }
        return players.get(0);
    }

    /**
     * Получить историю игры.
     *
     * @return история игры
     */
    public List<String> getHistory() {
        return history;
    }
}
