package com.piemon.tictoe;

public class Board {
    private static final int ROW_COUNT = 3;
    private static final int COLUMN_COUNT = 3;
    private static Board boardInstance;
    private char[][] board = new char[ROW_COUNT][COLUMN_COUNT];

    private Board() {
    }

    public static Board getBoardInstance() {
        if (boardInstance == null) {
            boardInstance = new Board();
        }
        return boardInstance;
    }

    public char[][] getBoard() {
        return board;
    }
}
