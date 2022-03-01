package ru.digitalleague.monopoly;

/**
 * Класс игрового кубика.
 */
public class GameCube {

    private final int number;

    public GameCube(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCube gameCube = (GameCube) o;
        return number == gameCube.number;
    }
}
