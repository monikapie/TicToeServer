package com.piemon.tictoe;


import java.util.ArrayList;
import java.util.List;

public class TicToeConnector implements GameConnector{
    private Game game;
    private List<Player> players = new ArrayList<>();

    @Override
    public String addPlayer(Player player) {
        game = Game.getGameInstance();
        boolean isPlayerAdded = game.addPlayer(player);
        String messagePlayerAdded;
        if(!isPlayerAdded){
            sendPlayersNotification(GameEventMessage.MESSAGE_GAME_IS_FULL.getMessage());
            messagePlayerAdded = GameEventMessage.MESSAGE_GAME_IS_FULL.getMessage();
        }
        else{
            players.add(player);
            sendPlayersNotification(player.getName() + GameEventMessage.MESSAGE_PLAYER_ADDED.getMessage() + player.getSign());
            messagePlayerAdded = player.getName() + GameEventMessage.MESSAGE_PLAYER_ADDED.getMessage() + player.getSign();
            if(game.getGameStatus().equals(GameStatusChecker.GameStatusEnum.STARTED)){
                String boardDrawing = drawBoard();
                String successMessage = "\n" + GameEventMessage.MESSAGE_GAME_STARTED.getMessage()
                        + "\n" + GameEventMessage.MESSAGE_WHO.getMessage()
                        +" " + game.getPlayers().get(0).getName();
                sendPlayersNotification(successMessage);
                messagePlayerAdded = messagePlayerAdded + "\n" + boardDrawing + successMessage;
            }
        }

        return messagePlayerAdded;
    }

    private String drawBoard() {
        char[][] board = game.getBoard().getBoard();
        String boardDrawing = "Board\n";
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
    
    private void sendPlayersNotification(String message) {
        System.out.println(message);
    }

    @Override
    public String move(int x, int y, Player player) {
        Player playerServ = player;
        for (Player playerServer : players) if(playerServer.getName().equals(player.getName())) playerServ = playerServer;
        if(isPlayerTurn(playerServ.getNumber(), GameStatusChecker.howManySignsOnBoard(game.getBoard().getBoard()))){
            String messageMove = makeTheMove(x,y,playerServ);
            return messageMove;
        } else{
            sendPlayersNotification(GameEventMessage.MESSAGE_NOT_MOVE.getMessage());
            return GameEventMessage.MESSAGE_NOT_MOVE.getMessage();
        }

    }

    private String makeTheMove(int x, int y, Player player){
        char[][] board = game.getBoard().getBoard();
        if(!isMovePossible(board,x,y)){
            sendPlayersNotification(GameEventMessage.MESSAGE_FIELD_LOCKED.getMessage());
            return GameEventMessage.MESSAGE_FIELD_LOCKED.getMessage();
        }
        Sign playerSign = player.getSign();
        board[x][y] = playerSign.name().charAt(0);
        GameStatusChecker.GameStatusEnum gameStatus = GameStatusChecker.getGameStatus(playerSign ,board);
        game.setGameStatus(gameStatus);
        String messageMove;
        switch (gameStatus){
            case IN_PROGRESS:
                int opponentNumber = player.getNumber() == 1 ? 1 : 0;
                messageMove = GameEventMessage.MESSAGE_WHO.getMessage()
                        + game.getPlayers().get(opponentNumber).getName();
                sendPlayersNotification(messageMove);
                String boardDrawing = drawBoard();
                messageMove+=boardDrawing;
                break;
            case WIN_X:
                messageMove = GameEventMessage.MESSAGE_GAME_FINISHED.name() + player.getName();
                sendPlayersNotification(messageMove);
                break;
            case WIN_Y:
                messageMove = GameEventMessage.MESSAGE_GAME_FINISHED.name() + player.getName();
                sendPlayersNotification(messageMove);
                break;
            case DRAW:
                messageMove = GameEventMessage.MESSAGE_DRAW.getMessage();
                sendPlayersNotification(messageMove);
                break;
            default:
                messageMove = GameEventMessage.MESSAGE_WAIT.getMessage();
                sendPlayersNotification(messageMove);
        }

        return messageMove;
    }

    private boolean isPlayerTurn(int number, int i) {
        return (i % 2 == 0 && number == 1) || (i % 2 == 1 && number == 2);
    }

    private boolean isMovePossible(char[][] board, int x, int y) {
        return board[x][y] == 0 || x > 2 || y > 2;
    }

}
