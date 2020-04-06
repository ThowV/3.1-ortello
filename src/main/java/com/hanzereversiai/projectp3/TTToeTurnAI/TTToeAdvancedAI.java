package com.hanzereversiai.projectp3.TTToeTurnAI;

import com.thowv.javafxgridgameboard.AbstractGameInstance;
import com.thowv.javafxgridgameboard.GameBoard;
import com.thowv.javafxgridgameboard.GameBoardTile;
import com.thowv.javafxgridgameboard.GameBoardTileType;
import com.thowv.javafxgridgameboard.AbstractTurnEntity;
import com.thowv.javafxgridgameboard.premades.tictactoe.TTToeAlgorithms;
import java.util.ArrayList;

public class TTToeAdvancedAI extends AbstractTurnEntity {
    public TTToeAdvancedAI() {
        super(EntityType.AI);
    }

    @Override
    public void takeTurn(AbstractGameInstance gameInstance) {
        ArrayList<GameBoardTile> possibleGameBoardTiles = gameInstance.getGameBoard().getTilesByType(
                GameBoardTileType.HIDDEN);

        gameInstance.getGameBoard().setTileTypes(possibleGameBoardTiles,
                GameBoardTileType.VISIBLE);
        GameBoardTile best = findBestMove(gameInstance);
        System.out.println(best.getXCord());
        System.out.println(best.getYCord());
        gameInstance.doTurn(best.getXCord(),best.getYCord());

    }


    // This function returns true if there are moves remaining on the board. It returns false if there are no moves left to play.
    static Boolean isMovesLeft(AbstractGameInstance gameInstance)
    {
        if(gameInstance.getGameBoard().getTilesByType(GameBoardTileType.HIDDEN).size() > 0){
            return true;
        }
        return false;
    }

    // This is an evaluation function
    static int evaluate(GameBoard gameBoard) {
        if (TTToeAlgorithms.checkThreeInRow(gameBoard) == GameBoardTileType.PLAYER_1) {
            return +10;
        }
        else if (TTToeAlgorithms.checkThreeInRow(gameBoard) == GameBoardTileType.PLAYER_2) {
            return -10;
        }
        else return 0;
    }

    //minMax func
    static int minMax(AbstractGameInstance gameInstance, int depth, Boolean isMax)
    {
        int score = evaluate(gameInstance.getGameBoard());
        //predict max wins
        if (score == 10)
            return score;
        //predict min wins
        if (score == -10)
            return score;
        // predicts no winner = a tie
        if (isMovesLeft(gameInstance) == false)
            return 0;

        // If this maximizer's move
        if (isMax) {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (gameInstance.getGameBoard().getTile(i,j).getGameBoardTileType() != GameBoardTileType.PLAYER_1 && gameInstance.getGameBoard().getTile(i,j).getGameBoardTileType() != GameBoardTileType.PLAYER_2){
                        // Make the move
                        gameInstance.getGameBoard().setTileType(i,j,GameBoardTileType.PLAYER_1);
                        // Call minmax recursively and choose the maximum value
                        best = Math.max(best, minMax(gameInstance,depth + 1, !isMax));
                        // Undo the move
                        gameInstance.getGameBoard().setTileType(i,j,GameBoardTileType.HIDDEN);
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else {
            int best = 1000;
            // Traverse all cells
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    // Check if cell is empty
                    if (gameInstance.getGameBoard().getTile(i,j).getGameBoardTileType() != GameBoardTileType.PLAYER_1 && gameInstance.getGameBoard().getTile(i,j).getGameBoardTileType() != GameBoardTileType.PLAYER_2){

                        // Make the move
                        gameInstance.getGameBoard().setTileType(i,j,GameBoardTileType.PLAYER_2);

                        // Call minmax recursively and choose the minimum value
                        best = Math.min(best, minMax(gameInstance, depth + 1, !isMax));

                        // Undo the move
                        gameInstance.getGameBoard().setTileType(i,j,GameBoardTileType.HIDDEN);                    }
                }
            }
            return best;
        }
    }

    // This will return the best possible move for the AI
    static GameBoardTile findBestMove(AbstractGameInstance gameInstance)
    {
        int bestVal = -1000;
        int bestMoveX = -1;
        int bestMoveY = -1;

        // Traverse all cells, evaluate minmax function for all empty cells. And return the cell with optimal value.
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                // Check if cell is empty
                if (gameInstance.getGameBoard().getTile(i,j).getGameBoardTileType() != GameBoardTileType.PLAYER_1 && gameInstance.getGameBoard().getTile(i,j).getGameBoardTileType() != GameBoardTileType.PLAYER_2){

                    // Make the move
                    gameInstance.getGameBoard().setTileType(i,j,GameBoardTileType.PLAYER_1);

                    // compute evaluation function for this move.
                    int moveVal = minMax(gameInstance, 0, false);

                    // Undo the move
                    gameInstance.getGameBoard().setTileType(i,j,GameBoardTileType.HIDDEN);

                    // If the value of the current move is more than the best value, then update best
                    if (moveVal > bestVal) {
                        bestMoveX = i;
                        bestMoveY = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        GameBoardTile bestMove = gameInstance.getGameBoard().getTile(bestMoveX,bestMoveY);
        return (bestMove);
    }
}
