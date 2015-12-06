package Helpers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import Data.ChessGUI;
import Data.Piece;

public class MoveHelper {
	private ChessGUI Chess;
	private int Col;
	private int Row;
	private JButton[][] buttonPiecesArray; 
	
	public MoveHelper(ChessGUI chess){
		Chess = chess;
		buttonPiecesArray = Chess.getChessBoardButtons();
	}
	
	public void disableAll(JButton[][] array){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				array[i][j].setEnabled(false);
			}
		}
	}
	
	public void EnableMovements(Piece piece, JButton[][] Pieces){
		Col = piece.getColumn();
		Row = piece.getRow();
		
		switch (piece.getType()) {
        case Piece.TYPE_BISHOP:
        	EnableBishopMovements(Pieces);
            break;
        case Piece.TYPE_KING:
        	EnableKingMovements(Pieces);
            break;
        case Piece.TYPE_KNIGHT:
        	EnableKnightMovements(Pieces);
            break;
        case Piece.TYPE_PAWN:
        	EnablePawnMovements(Pieces);
            break;
        case Piece.TYPE_QUEEN:
        	EnableQueenMovements(Pieces);
            break;
        case Piece.TYPE_ROOK:
        	EnableRookMovements(Pieces);
            break;
		}	
	}
	public void EnableAttackedSquares(Piece piece, JButton[][] Pieces){
		Col = piece.getColumn();
		Row = piece.getRow();
		
		switch (piece.getType()) {
        case Piece.TYPE_BISHOP:
        	EnableBishopMovements(Pieces);
            break;
        case Piece.TYPE_KING:
        	EnableKingAttacks(Pieces);
            break;
        case Piece.TYPE_KNIGHT:
        	EnableKnightMovements(Pieces);
            break;
        case Piece.TYPE_PAWN:
        	EnablePawnAttacks(Pieces);
            break;
        case Piece.TYPE_QUEEN:
        	EnableQueenMovements(Pieces);
            break;
        case Piece.TYPE_ROOK:
        	EnableRookMovements(Pieces);
            break;
		}	
	}
	private void EnablePawnAttacks(JButton[][] Pieces) {
		Piece pawn = Chess.pieceInAPosition(Row,Col);
		if(pawn.isWhite()){
			//Check both attacking squares
//			if(Chess.isThereAPieceInPosition(Row+1, Col+1) && isEnemyPiece(pawn, Row+1, Col+1)){
			if((Col+1) <= 7)
				Pieces[Row+1][Col+1].setEnabled(true);
//			}
//			if(Chess.isThereAPieceInPosition(Row+1, Col-1) && isEnemyPiece(pawn, Row+1, Col-1)){
			if((Col-1) >= 0)
				Pieces[Row+1][Col-1].setEnabled(true);
//			}
		}
		else{
//			if(Chess.isThereAPieceInPosition(Row-1, Col+1) && isEnemyPiece(pawn, Row-1, Col+1)){
			if((Col+1) <= 7)
				Pieces[Row-1][Col+1].setEnabled(true);
//			}
//			if(Chess.isThereAPieceInPosition(Row-1, Col-1) && isEnemyPiece(pawn, Row-1, Col-1)){
			if((Col-1) >= 0)
				Pieces[Row-1][Col-1].setEnabled(true);
//			}
		}
		
	}

	private void EnableKingAttacks(JButton[][] Pieces) {
		Piece king = Chess.pieceInAPosition(Row, Col);
		if( (!Chess.isThereAPieceInPosition(Row+1, Col+1) || isEnemyPiece(king, Row+1, Col+1) ) && (Row+1) <= 7  && (Col+1) <= 7){
			Pieces[Row+1][Col+1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row+1, Col) || isEnemyPiece(king, Row+1, Col) ) && (Row+1) <= 7){
			Pieces[Row+1][Col].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row+1, Col-1) || isEnemyPiece(king, Row+1, Col-1) ) && (Row+1) <= 7 && (Col-1) >= 0){
			Pieces[Row+1][Col-1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row-1, Col+1) || isEnemyPiece(king, Row-1, Col+1) ) && (Row-1) >= 0 && (Col+1) <= 7){
			Pieces[Row-1][Col+1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row-1, Col) || isEnemyPiece(king, Row-1, Col) ) && (Row-1) >= 0 ){
			Pieces[Row-1][Col].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row-1, Col-1) || isEnemyPiece(king, Row-1, Col-1) ) && (Row-1) >= 0 && (Col-1) >= 0){
			Pieces[Row-1][Col-1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row, Col+1) || isEnemyPiece(king, Row, Col+1) ) && (Col+1) <= 7){
			Pieces[Row][Col+1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row, Col-1) || isEnemyPiece(king, Row, Col-1) ) && (Col-1) >= 0){
			Pieces[Row][Col-1].setEnabled(true);
		}
	}

	public void reEnableAll(){
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				buttonPiecesArray[i][j].setEnabled(true);
			}
		}
	}
	
	public boolean isEnemyPiece(Piece movingPiece,int row, int column){
		if(Chess.pieceInAPosition(row,column).isWhite() != movingPiece.isWhite() ){
			return true;
		}
		return false;
	}
	
	public void EnableRookMovements(JButton[][] Pieces){
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
	public void EnableQueenMovements(JButton[][] Pieces){
		EnableRookMovements(Pieces);
		EnableBishopMovements(Pieces);
	}
	
	public void EnablePawnMovements(JButton[][] Pieces){
		Piece pawn = Chess.pieceInAPosition(Row,Col);
    	EnablePawnAttacks(Pieces);
		if(pawn.isWhite()){
			if(!Chess.isThereAPieceInPosition(Row+1, Col+1) || !isEnemyPiece(pawn, Row+1, Col+1)){
	    		Pieces[Row+1][Col+1].setEnabled(false);
	    	}
	    	if(!Chess.isThereAPieceInPosition(Row+1, Col-1) || !isEnemyPiece(pawn, Row+1, Col-1)){
	    		Pieces[Row+1][Col-1].setEnabled(false);
	    	}
			//Check normal movement
			if(!Chess.isThereAPieceInPosition(Row+1, Col)){
				Pieces[Row+1][Col].setEnabled(true);
				if(pawn.getRow() == 1 && !Chess.isThereAPieceInPosition(Row+2, Col)){
					Pieces[Row+2][Col].setEnabled(true);
				}
			}
		}
		else{
			if(!Chess.isThereAPieceInPosition(Row-1, Col+1) || !isEnemyPiece(pawn, Row-1, Col+1)){
				Pieces[Row-1][Col+1].setEnabled(false);
			}
			if(!Chess.isThereAPieceInPosition(Row-1, Col-1) || !isEnemyPiece(pawn, Row-1, Col-1)){
				Pieces[Row-1][Col-1].setEnabled(false);
			}
			if(!Chess.isThereAPieceInPosition(Row-1, Col)){
				Pieces[Row-1][Col].setEnabled(true);
				if(pawn.getRow() == 6 && !Chess.isThereAPieceInPosition(Row-2, Col)){
					Pieces[Row-2][Col].setEnabled(true);
				}
			}	
		}
	}
	public void EnableKnightMovements(JButton[][] Pieces){
		Piece knight = Chess.pieceInAPosition(Row, Col);
		//North
		if(Row-2 >= 0){
			if( (!Chess.isThereAPieceInPosition(Row-2, Col+1) || isEnemyPiece(knight, Row-2, Col+1) ) && (Col+1) <= 7)
				Pieces[Row-2][Col+1].setEnabled(true);
			if( (!Chess.isThereAPieceInPosition(Row-2, Col-1) || isEnemyPiece(knight, Row-2, Col-1) ) && (Col-1) >= 0)
				Pieces[Row-2][Col-1].setEnabled(true);
		}
		//South
		if(Row+2 <= 7){
			if( (!Chess.isThereAPieceInPosition(Row+2, Col+1) || isEnemyPiece(knight, Row+2, Col+1) ) && (Col+1) <= 7)
				Pieces[Row+2][Col+1].setEnabled(true);
			if( (!Chess.isThereAPieceInPosition(Row+2, Col-1) || isEnemyPiece(knight, Row+2, Col-1) ) && (Col-1) >= 0)
				Pieces[Row+2][Col-1].setEnabled(true);
		}
		//East
		if(Col+2 <= 7){
			if( (!Chess.isThereAPieceInPosition(Row-1, Col+2) || isEnemyPiece(knight, Row-1, Col+2) ) && (Row-1) >= 0)
				Pieces[Row-1][Col+2].setEnabled(true);
			if( (!Chess.isThereAPieceInPosition(Row+1, Col+2) || isEnemyPiece(knight, Row+1, Col+2) ) && (Row+1) <= 7)
				Pieces[Row+1][Col+2].setEnabled(true);
		}
		//West
		if(Col-2 >= 0){
			if( (!Chess.isThereAPieceInPosition(Row-1, Col-2) || isEnemyPiece(knight, Row-1, Col-2) ) && (Row-1) >= 0)
				Pieces[Row-1][Col-2].setEnabled(true);
			if( (!Chess.isThereAPieceInPosition(Row+1, Col-2) || isEnemyPiece(knight, Row+1, Col-2) )&& (Row+1) <= 7)
				Pieces[Row+1][Col-2].setEnabled(true);
		}
	}
	public void EnableKingMovements(JButton[][] Pieces){
		Piece king = Chess.pieceInAPosition(Row, Col);
		if( (!Chess.isThereAPieceInPosition(Row+1, Col+1) || isEnemyPiece(king, Row+1, Col+1) ) && (Row+1) <= 7  && (Col+1) <= 7){
			Pieces[Row+1][Col+1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row+1, Col) || isEnemyPiece(king, Row+1, Col) ) && (Row+1) <= 7){
			Pieces[Row+1][Col].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row+1, Col-1) || isEnemyPiece(king, Row+1, Col-1) ) && (Row+1) <= 7 && (Col-1) >= 0){
			Pieces[Row+1][Col-1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row-1, Col+1) || isEnemyPiece(king, Row-1, Col+1) ) && (Row-1) >= 0 && (Col+1) <= 7){
			Pieces[Row-1][Col+1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row-1, Col) || isEnemyPiece(king, Row-1, Col) ) && (Row-1) >= 0 ){
			Pieces[Row-1][Col].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row-1, Col-1) || isEnemyPiece(king, Row-1, Col-1) ) && (Row-1) >= 0 && (Col-1) >= 0){
			Pieces[Row-1][Col-1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row, Col+1) || isEnemyPiece(king, Row, Col+1) ) && (Col+1) <= 7){
			Pieces[Row][Col+1].setEnabled(true);
		}
		if( (!Chess.isThereAPieceInPosition(Row, Col-1) || isEnemyPiece(king, Row, Col-1) ) && (Col-1) >= 0){
			Pieces[Row][Col-1].setEnabled(true);
		}
		JButton[][] illegalMoves = squaresInDanger( !king.isWhite() );
		for(int i = 0; i <= 7; i++){
			for(int j = 0; j <= 7; j++){
				if( illegalMoves[i][j].isEnabled() ){
					Pieces[i][j].setEnabled(false);
				}
			}
		}
		//BUG - Kings can capture enemy pieces that are protected by other enemy pieces
		//This is due to the behavior on the Enable___Movement that dictates that the
		//spot occupied by an ally piece is not a valid movement, and as such, it is not
		//protected 
	}
	public void EnableBishopMovements(JButton[][] Pieces){
		//NW
		Piece bishop = Chess.pieceInAPosition(Row, Col);
		for(int i = 1; Col-i>=0 && Row-i >= 0;  i++){
			if(Chess.isThereAPieceInPosition(Row-i, Col-i)){
				if( isEnemyPiece(bishop, Row-i, Col-i) ){
					Pieces[Row-i][Col-i].setEnabled(true);
				}
				break;
			}
			Pieces[Row-i][Col-i].setEnabled(true);
		}
		//NE
		for(int i = 1; Col+i<=7 && Row-i >= 0;  i++){
			if(Chess.isThereAPieceInPosition(Row-i, Col+i)){
				if( isEnemyPiece(bishop, Row-i, Col+i) ){
					Pieces[Row-i][Col+i].setEnabled(true);
				}
				break;
			}
			Pieces[Row-i][Col+i].setEnabled(true);
		}
		//SW
		for(int i = 1; Col-i>=0 && Row+i <= 7;  i++){
			if(Chess.isThereAPieceInPosition(Row+i, Col-i)){
				if( isEnemyPiece(bishop, Row+i, Col-i) ){
					Pieces[Row+i][Col-i].setEnabled(true);
				}
				break;
			}
			Pieces[Row+i][Col-i].setEnabled(true);
		}
		//SE
		for(int i = 1; Col+i<=7 && Row+i <= 7;  i++){
			if(Chess.isThereAPieceInPosition(Row+i, Col+i)){
				if( isEnemyPiece(bishop, Row+i, Col+i) ){
					Pieces[Row+i][Col+i].setEnabled(true);
				}
				break;
			}
			Pieces[Row+i][Col+i].setEnabled(true);
		}
	}
	public JButton[][] squaresInDanger (boolean isWhite){
		JButton[][] dangerBoard = new JButton [8][8];
		for(int i = 0; i <= 7; i++){
			for (int j = 0; j <= 7; j++){
				dangerBoard[i][j] = new JButton ();
			}
		}
		disableAll(dangerBoard);
		for(Piece piece : Chess.getPieces()){
			if(piece.isWhite() == isWhite){
				EnableAttackedSquares(piece, dangerBoard);
			}
		}
		return dangerBoard;
	}
}
