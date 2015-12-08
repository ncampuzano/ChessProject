package Data;

import java.util.ArrayList;
import java.util.List;

import UserInterface.GraphicUI;

public class IAPlayer {
	
	private ChessGUI chessGame;
	private	GraphicUI graphicUI;
	
    public IAPlayer(ChessGUI chessGame,GraphicUI graphic ) {
        this.chessGame = chessGame;
        this.graphicUI = graphic;
    }
    
	private Move getBestMove() {
 
        List<Move> validMoves = generateMoves();
        int bestResult = Integer.MIN_VALUE;
        Move bestMove = null;
         
        for (Move move : validMoves) {
            executeMove(move);
 
            int evaluationResult = this.evaluateState();
 
            chessGame.undoMove(move);
            if( evaluationResult > bestResult){
                bestResult = evaluationResult;
                bestMove = move;
            }
        }
        System.out.println("done thinking! best move is: "+bestMove);
        return bestMove;
    }
	 private List<Move> generateMoves() {
		 
	        List<Piece> pieces = this.chessGame.getPieces();
	        List<Move> validMoves = new ArrayList<Move>();
	        Move testMove = new Move(0,0,0,0);
	 
	        int pieceColor = this.chessGame.getGameState();
	 
	        // iterate over all non-captured pieces
	        for (Piece piece : pieces) {
	 
	            // only look at pieces of current players color
	            if (convertToInt(pieceColor) == piece.isWhite()) {
	                // start generating move
	                testMove.sourceRow = piece.getRow();
	                testMove.sourceColumn = piece.getColumn();
	 
	                // iterate over all board rows and columns
	                for (int targetRow = 0; targetRow <= 7; targetRow++) {
	                    for (int targetColumn = 0; targetColumn <= 7; targetColumn++) {
	 
	                        // finish generating move
	                        testMove.targetRow = targetRow;
	                        testMove.targetColumn = targetColumn;
	 
	                        // check if generated move is valid
	                        if (isMoveValid(testMove)) {
	                            // valid move
	                            validMoves.add(testMove.clone());
	                        } else {
	                            // generated move is invalid, so we skip it
	                        }
	                    }
	                }
	 
	            }
	        }
	        return validMoves;
	    }
	 private boolean convertToInt(int i){
		 return (i == 1) ? true: false;
	 }
	 private boolean isMoveValid(Move move){
		 chessGame.MoveHelper.EnableMovements(chessGame.pieceInAPosition(move.sourceRow,move.sourceColumn), chessGame.getChessBoardButtons());
		 if(chessGame.getChessBoardButtons()[move.targetRow][move.targetColumn].isEnabled()){
			 chessGame.MoveHelper.reEnableAll(chessGame.getChessBoardButtons());
			 return true;
		 }else{
			 
			 return false;
		 }
			 
	 }
	 private int evaluateState() {
	        // add up score
	        //
	        int scoreWhite = 0;
	        int scoreBlack = 0;
	        for (Piece piece : this.chessGame.getPieces()) {
	            if(piece.getColor() == Piece.COLOR_BLACK){
	                scoreBlack +=
	                    getScoreForPieceType(piece.getType());
	 
	            }else if( piece.getColor() == Piece.COLOR_WHITE){
	                scoreWhite +=
	                    getScoreForPieceType(piece.getType());
	 
	            }else{
	                throw new IllegalStateException(
	                        "unknown piece color found: "+piece.getColor());
	            }
	        }
	         
	        // return evaluation result depending on who's turn it is
	        int gameState = this.chessGame.getGameState();
	         
	        if( gameState == ChessGame.GAME_STATE_BLACK){
	            return scoreBlack - scoreWhite;
	         
	        }else if(gameState == ChessGame.GAME_STATE_WHITE){
	            return scoreWhite - scoreBlack;
	         
	        }else if(gameState == ChessGame.GAME_STATE_END_WHITE_WON
	                || gameState == ChessGame.GAME_STATE_END_BLACK_WON){
	            return Integer.MIN_VALUE + 1;
	         
	        }else{
	            throw new IllegalStateException("unknown game state: "+gameState);
	        }
	    }
	 
	    /**
	     * get the evaluation score for the specified piece type
	     * @param type - one of Piece.TYPE_..
	     * @return integer score
	     */
	    private int getScoreForPieceType(int type){
	        switch (type) {
	            case Piece.TYPE_BISHOP: return 30;
	            case Piece.TYPE_KING: return 99999;
	            case Piece.TYPE_KNIGHT: return 30;
	            case Piece.TYPE_PAWN: return 10;
	            case Piece.TYPE_QUEEN: return 90;
	            case Piece.TYPE_ROOK: return 50;
	            default: throw new IllegalArgumentException("unknown piece type: "+type);
	        }
	    } 
}
