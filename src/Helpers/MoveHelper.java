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
	public boolean isEnemyPiece(Piece movingPiece,int row, int column){
		if(Chess.pieceInAPosition(row,column).isWhite() != movingPiece.isWhite() ){
			return true;
		}
		return false;
	}
	public void EnableRookMovements(){
		Piece rook = Chess.pieceInAPosition(Row, Col);
		//Izquierda
		for(int i = 1; Col-i >=  0;  i++){
			if(Chess.isThereAPieceInPosition(Row,Col-i)){
				if( isEnemyPiece(rook, Row, Col-i) ){
					Pieces[Row][Col-i].setEnabled(true);
				}
				break;
			}
			Pieces[Row][Col-i].setEnabled(true);
		}
		//Derecha
		for(int i = 1; Col+i < 8;  i++){
			if(Chess.isThereAPieceInPosition(Row, Col+i)){
				if( isEnemyPiece(rook, Row, Col+i) ){
					Pieces[Row][Col+i].setEnabled(true);
				}
				break;
			}
			Pieces[Row][Col+i].setEnabled(true);
		}
		//Arriba
		for(int i = 1; Row-i >= 0;  i++){
			if(Chess.isThereAPieceInPosition(Row-i, Col)){
				if( isEnemyPiece(rook, Row-i, Col) ){
					Pieces[Row-i][Col].setEnabled(true);
				}
				break;
			}
			Pieces[Row-i][Col].setEnabled(true);
		}
		//Abajo
		for(int i = 1; Row+i < 8;  i++){
			if(Chess.isThereAPieceInPosition(Row+i, Col) ){
				if( isEnemyPiece(rook, Row+i, Col) ){
					Pieces[Row+i][Col].setEnabled(true);
				}
				break;
			}
			Pieces[Row+i][Col].setEnabled(true);
		}
	}
	public void EnableQueenMovements(){
		EnableRookMovements();
		EnableBishopMovements();
	}
	public void EnablePawnMovements(){
		Piece pawn = Chess.pieceInAPosition(Row,Col);
		if(pawn.isWhite()){
			//Check both attacking squares
			if(Chess.isThereAPieceInPosition(Row+1, Col+1) && isEnemyPiece(pawn, Row+1, Col+1)){
				Pieces[Row+1][Col+1].setEnabled(true);
			}
			if(Chess.isThereAPieceInPosition(Row+1, Col-1) && isEnemyPiece(pawn, Row+1, Col-1)){
				Pieces[Row+1][Col-1].setEnabled(true);
			}
			//Check normal movement
			if(!Chess.isThereAPieceInPosition(Row+1, Col)){
				Pieces[Row+1][Col].setEnabled(true);
				if(pawn.getRow() == 1){
					if(!Chess.isThereAPieceInPosition(Row+2, Col)){
						Pieces[Row+2][Col].setEnabled(true);
					}
				}
			}
			
		}
		else{
			
			if(Chess.isThereAPieceInPosition(Row-1, Col+1) && isEnemyPiece(pawn, Row-1, Col+1)){
				Pieces[Row-1][Col+1].setEnabled(true);
			}
			if(Chess.isThereAPieceInPosition(Row-1, Col-1) && isEnemyPiece(pawn, Row-1, Col-1)){
				Pieces[Row-1][Col-1].setEnabled(true);
			}
			
			if(!Chess.isThereAPieceInPosition(Row-1, Col)){
				Pieces[Row-1][Col].setEnabled(true);
				if(pawn.getRow() == 6){
					if(!Chess.isThereAPieceInPosition(Row-2, Col)){
						Pieces[Row-2][Col].setEnabled(true);
					}
				}
			}	
		}
	}
	public void EnableKnightMovements(){
		//North
		if(Row-2 >= 0){
			if(!Chess.isThereAPieceInPosition(Row-2, Col+1) && (Col+1) <= 7)
				Pieces[Row-2][Col+1].setEnabled(true);
			if(!Chess.isThereAPieceInPosition(Row-2, Col-1) && (Col-1) >= 0)
				Pieces[Row-2][Col-1].setEnabled(true);
		}
		//South
		if(Row+2 <= 7){
			if(!Chess.isThereAPieceInPosition(Row+2, Col+1) && (Col+1) <= 7)
				Pieces[Row+2][Col+1].setEnabled(true);
			if(!Chess.isThereAPieceInPosition(Row+2, Col-1) && (Col-1) >= 0)
				Pieces[Row+2][Col-1].setEnabled(true);
		}
		//East
		if(Col+2 <= 7){
			if(!Chess.isThereAPieceInPosition(Row-1, Col+2) && (Row-1) >= 0)
				Pieces[Row-1][Col+2].setEnabled(true);
			if(!Chess.isThereAPieceInPosition(Row+1, Col+2) && (Row+1) <= 7)
				Pieces[Row+1][Col+2].setEnabled(true);
		}
		//West
		if(Col-2 >= 0){
			if(!Chess.isThereAPieceInPosition(Row-1, Col-2) && (Row-1) >= 0)
				Pieces[Row-1][Col-2].setEnabled(true);
			if(!Chess.isThereAPieceInPosition(Row+1, Col-2) && (Row+1) <= 7)
				Pieces[Row+1][Col-2].setEnabled(true);
		}
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
