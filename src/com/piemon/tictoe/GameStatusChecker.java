package com.piemon.tictoe;

public class GameStatusChecker{

    public enum GameStatusEnum{
        WAITING, STARTED, DRAW, WIN_X, WIN_Y, IN_PROGRESS;
    }

    public static GameStatusEnum getGameStatus(Sign playerMark, char[][] boardInstance){
        char playerSymbol = playerMark.name().charAt(0);
        return isThreeInRow(playerSymbol,boardInstance)
                || isThreeInCol(playerSymbol,boardInstance)
                || isThreeOnCrossLines(playerSymbol,boardInstance)
                ? (playerMark.equals(Sign.X) ? GameStatusEnum.WIN_X : GameStatusEnum.WIN_Y)
                : (isDraw(boardInstance) ? GameStatusEnum.DRAW : GameStatusEnum.IN_PROGRESS);
    }

    private static boolean isThreeInRow(char playerMark, char[][] boardInstance){
        boolean isThreeInRow;
        for(int rowNum = 0; rowNum < 3; rowNum ++){
            isThreeInRow =  (boardInstance[rowNum][0] == playerMark && boardInstance[rowNum][1] == playerMark && boardInstance[rowNum][2] == playerMark);
            if(isThreeInRow) return true;
        }
        return false;
    }

    private static boolean isThreeInCol(char playerMark, char[][] boardInstance){
        boolean isThreeInCol;
        for(int colNum = 0; colNum < 3; colNum ++){
            isThreeInCol =  (boardInstance[0][colNum] == playerMark && boardInstance[1][colNum] == playerMark && boardInstance[2][colNum] == playerMark);
            if(isThreeInCol) return true;
        }
        return false;
    }

    private static boolean isThreeOnCrossLines(char playerMark, char[][] boardInstance){
        boolean isThreeInDiagLeft =  (boardInstance[0][0] == playerMark && boardInstance[1][1] == playerMark && boardInstance[2][2] == playerMark);
        boolean isThreeInDiagRight =  (boardInstance[2][0] == playerMark && boardInstance[1][1] == playerMark && boardInstance[0][2] == playerMark);
        return isThreeInDiagLeft || isThreeInDiagRight;
    }

    private static boolean isDraw(char[][] boardInstance){
        for(int rowNum = 0; rowNum < 3; rowNum ++){
            for (int colNum = 0; colNum < 3; colNum++){
                if(boardInstance[rowNum][colNum] == 0) return false;
            }
        }
        return true;
    }

    public static int howManySignsOnBoard(char[][] boardInstance){
        int count = 0;
        for(int rowNum = 0; rowNum < 3; rowNum ++){
            for (int colNum = 0; colNum < 3; colNum++){
                if(boardInstance[rowNum][colNum] != 0) count++;
            }
        }
        return count;
    }
}
