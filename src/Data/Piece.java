package Data;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Piece {
	private Image PieceImage;
	private boolean White;
	private String Name;
	private int Type;
	private int Row;
	private int Column;
	private boolean isCapture = false;

	//Constants used to identify the type of piece
	public static final int TYPE_ROOK = 1;
    public static final int TYPE_KNIGHT = 2;
    public static final int TYPE_BISHOP = 3;
    public static final int TYPE_QUEEN = 4;
    public static final int TYPE_KING = 5;
    public static final int TYPE_PAWN = 6;
    
    public Piece(boolean isWhite, int type, int row, int column){
    	this.Type = type;
    	this.White = isWhite;
    	this.Row = row;
    	this.Column = column;
    	this.PieceImage = getImageForPiece(White,Type);
    }
	
	public boolean isCapture() {
		return isCapture;
	}

	public void setCapture(boolean isCapture) {
		this.isCapture = isCapture;
	}

	public int getColumn() {
		return Column;
	}

	public void setColumn(int column) {
		Column = column;
	}

	public int getRow() {
		return Row;
	}

	public void setRow(int row) {
		Row = row;
	}

	public Image getPieceImage() {
		return PieceImage;
	}
	public void setPieceImage(Image pieceImage) {
		PieceImage = pieceImage;
	}
	public boolean isWhite() {
		return White;
	}
	public void setWhite(boolean isWhite) {
		White = isWhite;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}

	//Searches in the project folder for the right image to use on each type and color of piece
	private Image getImageForPiece(boolean isWhite, int type) {
		 
	    String filename = "";
	 
	    filename += (isWhite ? "w" : "b");
	    switch (type) {
	        case TYPE_BISHOP:
	            filename += "b";
	            break;
	        case TYPE_KING:
	            filename += "k";
	            break;
	        case TYPE_KNIGHT:
	            filename += "n";
	            break;
	        case TYPE_PAWN:
	            filename += "p";
	            break;
	        case TYPE_QUEEN:
	            filename += "q";
	            break;
	        case TYPE_ROOK:
	            filename += "r";
	            break;
	    }
	    filename += ".png";
	    filename = "assets/" + filename;
	    return new ImageIcon(filename).getImage();
	}

}
