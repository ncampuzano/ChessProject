package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IAPlayer {
	
	private ChessGUI chessGame;
	
    public IAPlayer(ChessGUI chessGame) {
    	this.chessGame = chessGame;

    }
    
	public Move getBestMove() {
		 
        List<Move> validMoves = generateMoves();
        int bestResult = Integer.MIN_VALUE;
        Move bestMove = null;
        Random rnd = new Random();
        for (Move move : validMoves) {
        	
        	//Make movement
        	move = MakeTestMovement(move);
            
            //Evaluate
            int evaluationResult = this.evaluateState();
            
            //Undo
            UndoTestMovement(move);
            
            if( evaluationResult > bestResult){
                bestResult = evaluationResult;
                bestMove = move;
            }if(evaluationResult == bestResult && rnd.nextInt(10) > 5){
            	bestResult = evaluationResult;
                bestMove = move;
            }
        }
        System.out.println("done thinking! best move is: "+bestMove);
        return bestMove;
    }
	private Move MakeTestMovement (Move move){
		Piece pieceToMove = chessGame.pieceInAPosition(move.sourceRow, move.sourceColumn);
		chessGame.pieces.remove(pieceToMove);
		if(chessGame.pieceInAPosition(move.targetRow, move.targetColumn) != null){
			move.capturedPiece = chessGame.pieceInAPosition(move.targetRow, move.targetColumn);
			chessGame.pieces.remove(move.capturedPiece);
		}
		pieceToMove.setRow(move.targetRow);
		pieceToMove.setColumn(move.targetColumn);
		chessGame.pieces.add(pieceToMove);
		return move;
	}
	private void UndoTestMovement (Move move){
		Piece pieceToMove = chessGame.pieceInAPosition(move.targetRow, move.targetColumn);
		chessGame.pieces.remove(pieceToMove);
		if(move.capturedPiece != null){
			chessGame.pieces.add(move.capturedPiece);
		}
		pieceToMove.setRow(move.sourceRow);
		pieceToMove.setColumn(move.sourceColumn);
		chessGame.pieces.add(pieceToMove);
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
		 chessGame.MoveHelper.disableAll(chessGame.getChessBoardButtons());
		 chessGame.MoveHelper.EnableMovements(chessGame.pieceInAPosition(move.sourceRow,move.sourceColumn), chessGame.getChessBoardButtons());
		 if(chessGame.getChessBoardButtons()[move.targetRow][move.targetColumn].isEnabled()){
			 chessGame.MoveHelper.reEnableAll(chessGame.getChessBoardButtons());
			 return true;
		 }else{
			 chessGame.MoveHelper.reEnableAll(chessGame.getChessBoardButtons());
			 return false;
		 }			 
	 }
	 private int evaluateState() {
	        // add up score
	        //
	        int scoreWhite = 0;
	        int scoreBlack = 0;
	        for (Piece piece : this.chessGame.getPieces()) {
	            if(!piece.isWhite()){
	                scoreBlack +=
	                    getScoreForPieceType(piece.getType());
	 
	            }else if( piece.isWhite()){
	                scoreWhite +=
	                    getScoreForPieceType(piece.getType());
	 
	            }else{
	                throw new IllegalStateException(
	                        "unknown piece color found: "+piece.isWhite());
	            }
	        }
	         
	        // return evaluation result depending on who's turn it is
	        int gameState = this.chessGame.getGameState();
	         
	        if( gameState == chessGame.GAME_STATE_BLACK){
	            return scoreBlack - scoreWhite;
	         
	        }else if(gameState == chessGame.GAME_STATE_WHITE){
	            return scoreWhite - scoreBlack;
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
