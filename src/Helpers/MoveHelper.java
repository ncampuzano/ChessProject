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
		//Izquierda
		for(int i = Col-1; i >=  0;  i--){
			if(Chess.isThereAPieceInPosition(Row, i)){
				break;
			}
			Pieces[Row][i].setEnabled(true);
		}
		//Derecha
		for(int i = Col+1; i < 8;  i++){
			if(Chess.isThereAPieceInPosition(Row, i)){
				break;
			}
			Pieces[Row][i].setEnabled(true);
		}
		//Arriba
		for(int i = Row-1; i >= 0;  i--){
			if(Chess.isThereAPieceInPosition(i, Col)){
				break;
			}
			Pieces[i][Col].setEnabled(true);
		}
		//Abajo
		for(int i = Row+1; i < 8;  i++){
			if(Chess.isThereAPieceInPosition(i, Col) ){
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
		//NW
		for(int i = 1; Col-i>=0 && Row-i >= 0;  i++){
			if(Chess.isThereAPieceInPosition(Row-i, Col-i)){
				break;
			}
			Pieces[Row-i][Col-i].setEnabled(true);
		}
		//NE
		for(int i = 1; Col+i<=7 && Row-i >= 0;  i++){
			if(Chess.isThereAPieceInPosition(Row-i, Col+i)){
				break;
			}
			Pieces[Row-i][Col+i].setEnabled(true);
		}
		//SW
		for(int i = 1; Col-i>=0 && Row+i <= 7;  i++){
			if(Chess.isThereAPieceInPosition(Row+i, Col-i)){
				break;
			}
			Pieces[Row+i][Col-i].setEnabled(true);
		}
		//SE
		for(int i = 1; Col+i<=7 && Row+i <= 7;  i++){
			if(Chess.isThereAPieceInPosition(Row+i, Col+i)){
				break;
			}
			Pieces[Row+i][Col+i].setEnabled(true);
		}
	}
	
}
