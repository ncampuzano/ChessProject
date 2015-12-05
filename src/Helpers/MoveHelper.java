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
		
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				Pieces[i][j].setEnabled(false);
			}
		}
		Pieces[Row][Col].setEnabled(true);
		
		System.out.println("Disabled buttons");
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
	public void reEnableAll(){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				Pieces[i][j].setEnabled(true);
			}
		}
	}
	public void EnableRookMovements(){
		System.out.println("finding piece");
		Piece rook = Chess.pieceInAPosition(Row, Col);
		System.out.println("rook choosen");
		//Izquierda
		for(int i = Col-1; i >=  0;  i--){
			if(Chess.isThereAPieceInPosition(Row, i)){
				System.out.println("Pieza a la izquierda");
//				i=0;
				break;
			}
			Pieces[Row][i].setEnabled(true);
		}
		//Derecha
		for(int i = Col+1; i < 8;  i++){
			if(Chess.isThereAPieceInPosition(Row, i)){
				System.out.println("Pieza a la derecha");
//				i=0;
				break;
			}
			Pieces[Row][i].setEnabled(true);
		}
		//Arriba
		for(int i = Row-1; i >= 0;  i--){
			if(Chess.isThereAPieceInPosition(i, Col)){
				System.out.println("Pieza arriba");
//				i=0;
				break;
			}
			Pieces[i][Col].setEnabled(true);
		}
		//Abajo
		for(int i = Row+1; i < 8;  i++){
			if( Chess.isThereAPieceInPosition(i, Col) ){
				System.out.println("Pieza abajo");
//				i=0;
				break;
			}
			Pieces[i][Col].setEnabled(true);
		}
	}
	public void EnableQueenMovements(){
		
	}
	public void EnablePawnMovements(){
		Piece pawn = Chess.pieceInAPosition(Row,Col);
		if(pawn.isWhite()){
			Pieces[Row+1][Col].setEnabled(true);
			if(pawn.getRow() == 1)
				Pieces[Row+2][Col].setEnabled(true);
		}
		else{
			Pieces[Row-1][Col].setEnabled(true);
			if(pawn.getRow() == 6)
				Pieces[Row-2][Col].setEnabled(true);
		}
	}
	public void EnableKnightMovements(){
		
	}
	public void EnableKingMovements(){
		
	}
	public void EnableBishopMovements(){
		
	}
	
}
