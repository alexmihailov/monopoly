package ru.digitalleague.monopoly;

import ru.digitalleague.monopoly.exceptions.CellException;

public class Main {

    public static void main(String[] args) throws CellException {
        GameBoard gameBoard = new GameBoard(200, 5, 10000);
        Player winner = gameBoard.play();
        System.out.println(String.join("\n", gameBoard.getHistory()));
        System.out.println("Winner: " + winner);
    }
}
