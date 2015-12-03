package Helpers;

import javax.swing.JButton;

import Data.ChessGUI;
import Data.Piece;

public class MoveHelper {
	private ChessGUI Chess;
	private int Col;
	private int Row;
	private JButton[][] Pieces; 
	private int GameState; 
	public MoveHelper(ChessGUI chess){
		Chess = chess;
		Pieces = Chess.getChessBoardButtons();
	}
	
	public void EnableMovements(int row, int col, int type, int gameState){
		Col = col;
		Row = row;
		GameState = gameState;
		
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
        	for(JButton[] pieceRows :Pieces)
    		{
    			for(JButton piece : pieceRows){
    				piece.setEnabled(false);
    			}
    		}
        	EnableRookMovements();
            break;
		}
		
	}
	public void EnableRookMovements(){
		Piece rook = Chess.pieceInAPosition(Row, Col);
		for(int i = Col; i >=  0;  i--){
			if(rook.isIsWhite() == Chess.pieceInAPosition(Row, i).isIsWhite())
				break;
			Pieces[Row][i].setEnabled(true);
		}
		for(int i = Col; i <  9;  i++){
			if(rook.isIsWhite() == Chess.pieceInAPosition(Row, i).isIsWhite())
				break;
			Pieces[Row][i].setEnabled(true);
		}
		for(int i = Row; i >= 0;  i--){
			if(rook.isIsWhite() == Chess.pieceInAPosition(i, Col).isIsWhite())
				break;
			Pieces[i][Col].setEnabled(true);
		}
		for(int i = Row; i < 9;  i++){
			if(rook.isIsWhite() == Chess.pieceInAPosition(i, Col).isIsWhite())
				break;
			Pieces[i][Col].setEnabled(true);
		}
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
