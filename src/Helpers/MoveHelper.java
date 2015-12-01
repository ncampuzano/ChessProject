package Helpers;

import javax.swing.JButton;

import Data.ChessGUI;
import Data.Piece;

public class MoveHelper {
	private ChessGUI Chess;
	
	public MoveHelper(ChessGUI chess){
		Chess = chess;		
	}
	
	public void EnableMovements(int row, int col, int type){
		
		for(JButton[] pieceRows : Chess.getChessBoardButtons())
		{
			for(JButton piece : pieceRows){
				piece.setEnabled(false);
			}
		}
		switch (type) {
        case Piece.TYPE_BISHOP:
        	EnableBishopMovements();
            break;
        case Piece.TYPE_KING:
        	EnableKingMovements();
            break;
        case Piece.TYPE_KNIGHT:
        	EnableKnightMovements();
            break;
        case Piece.TYPE_PAWN:
        	EnablePawnMovements();
            break;
        case Piece.TYPE_QUEEN:
        	EnableQueenMovements();
            break;
        case Piece.TYPE_ROOK:
        	EnableRookMovements();
            break;
		}
		
	}
	public void EnableRookMovements(){
		
	}
	public void EnableQueenMovements(){
		
	}
	public void EnablePawnMovements(){
		
	}
	public void EnableKnightMovements(){
		
	}
	public void EnableKingMovements(){
		
	}
	public void EnableBishopMovements(){
		
	}
	
}
