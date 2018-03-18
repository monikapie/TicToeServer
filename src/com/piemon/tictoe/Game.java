package com.piemon.tictoe;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Board board;
    private List<Player> players;
    private GameStatusChecker.GameStatusEnum gameStatus;
    private static Game game;

    private Game() {
        this.board = Board.getBoardInstance();
        this.gameStatus = GameStatusChecker.GameStatusEnum.WAITING;
        this.players = new ArrayList<>();
    }

    public static Game getGameInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public boolean addPlayer(Player player) {
        int playersCount = players.size();
        if (playersCount < 2) {
            int playerNumber = playersCount + 1;
            player.setNumber(playerNumber);
            players.add(player);
            playersCount++;
            changeGameStatusIfRequired(playersCount);
            return true;
        } else return false;
    }

    private void changeGameStatusIfRequired(int playersCount) {
        if(playersCount == 2) setGameStatus(GameStatusChecker.GameStatusEnum.STARTED);
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameStatusChecker.GameStatusEnum getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatusChecker.GameStatusEnum gameStatus) {
        this.gameStatus = gameStatus;
    }
}
