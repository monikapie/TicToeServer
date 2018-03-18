package com.piemon.tictoe;

public enum GameEventMessage {
    MESSAGE_GAME_IS_FULL("I am sorry, game is full."),
    MESSAGE_WHO(" It is turn of "),
    MESSAGE_PLAYER_ADDED(" successfully added to game. Your symbol is: "),
    MESSAGE_GAME_FINISHED("Game is finished. The winner is: "),
    MESSAGE_DRAW("Game is finished. The winner is no winner. "),
    MESSAGE_GAME_STARTED("Welcome! Game is started."),
    MESSAGE_FIELD_LOCKED("This field is occupied. Choose another x and y."),
    MESSAGE_WAIT("There is not enough players. Please wait..."),
    MESSAGE_NOT_MOVE("Please wait is not your move.");


    private String message;

    GameEventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
