package com.piemon.tictoe;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TicToeConnector implements GameConnector{
    private Game game;
    private List<Player> players = new ArrayList<>();
    private String messageToClient;

    @Override
    public boolean addPlayer(Player player) {
        game = Game.getGameInstance();
        boolean isPlayerAdded = game.addPlayer(player);
        if(!isPlayerAdded){
            messageToClient = GameEventMessage.MESSAGE_GAME_IS_FULL.getMessage();
            getMessage();
            return false;
        }
        else{
            players.add(player);
            messageToClient = player.getName() + GameEventMessage.MESSAGE_PLAYER_ADDED.getMessage() + player.getSign();
            if(game.getGameStatus().equals(GameStatusChecker.GameStatusEnum.STARTED)){
                messageToClient += "\n" + GameEventMessage.MESSAGE_GAME_STARTED.getMessage()
                        + "\n" + GameEventMessage.MESSAGE_WHO.getMessage()
                        +" " + game.getPlayers().get(0).getName()
                        + drawBoard();
                getMessage();
            }
        }

        return true;
    }

    @Override
    public String drawBoard() {
        char[][] board = game.getBoard().getBoard();
        String boardDrawing = "\nBoard\n";
        for (char[] boardRow : board) {
            System.out.print('|');
            boardDrawing+=("|");
            for (char anABoard : boardRow) {
                if(anABoard == 0){
                    System.out.print(" " + "|");
                    boardDrawing+=(" " + "|");
                }
                else {
                    System.out.print(String.valueOf(anABoard) + "|");
                    boardDrawing+=(String.valueOf(anABoard) + "|");
                }
            }
            boardDrawing+=("\n");
            System.out.println();
        }
        return boardDrawing;
    }

    @Override
    public String getMessage() {
        System.out.println(messageToClient);
        return messageToClient;
    }

    @Override
    public boolean move(int x, int y, Player player) {
        Player playerServ = player;
        for (Player playerServer : players) if(playerServer.getName().equals(player.getName())) playerServ = playerServer;
        if(isPlayerTurn(playerServ.getNumber(), GameStatusChecker.howManySignsOnBoard(game.getBoard().getBoard()))){
            return makeTheMove(x,y,playerServ);
        } else{
            messageToClient = GameEventMessage.MESSAGE_NOT_MOVE.getMessage();
            getMessage();
            return false;
        }

    }

    @Override
    public boolean isGameStared() {
        return game.getGameStatus().equals(GameStatusChecker.GameStatusEnum.STARTED) || game.getGameStatus().equals(GameStatusChecker.GameStatusEnum.IN_PROGRESS);
    }

    @Override
    public int howManySignsOnBoard() throws RemoteException {
        return GameStatusChecker.howManySignsOnBoard(game.getBoard().getBoard());
    }

    private boolean makeTheMove(int x, int y, Player player){
        char[][] board = game.getBoard().getBoard();
        if(!isMovePossible(board,x,y)){
            messageToClient = GameEventMessage.MESSAGE_FIELD_LOCKED.getMessage();
            getMessage();
            return false;
        }
        Sign playerSign = player.getSign();
        board[x][y] = playerSign.name().charAt(0);
        GameStatusChecker.GameStatusEnum gameStatus = GameStatusChecker.getGameStatus(playerSign ,board);
        game.setGameStatus(gameStatus);
        switch (gameStatus){
            case IN_PROGRESS:
                int opponentNumber = player.getNumber() == 1 ? 1 : 0;
                messageToClient = GameEventMessage.MESSAGE_WHO.getMessage()
                        + game.getPlayers().get(opponentNumber).getName();
                String boardDrawing = drawBoard();
                messageToClient+=boardDrawing;
                getMessage();
                break;
            case WIN_X:
                messageToClient = GameEventMessage.MESSAGE_GAME_FINISHED.getMessage() + player.getName();
                getMessage();
                break;
            case WIN_Y:
                messageToClient = GameEventMessage.MESSAGE_GAME_FINISHED.getMessage() + player.getName();
                getMessage();
                break;
            case DRAW:
                messageToClient = GameEventMessage.MESSAGE_DRAW.getMessage();
                getMessage();
                break;
            default:
                messageToClient = GameEventMessage.MESSAGE_WAIT.getMessage();
                getMessage();
        }

        return true;
    }

    private boolean isPlayerTurn(int number, int i) {
        return (i % 2 == 0 && number == 1) || (i % 2 == 1 && number == 2);
    }

    private boolean isMovePossible(char[][] board, int x, int y) {
        return x <= 2 && y <= 2 && board[x][y] == 0;
    }

}
