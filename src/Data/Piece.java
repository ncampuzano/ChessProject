package Data;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Piece {
	public Image PieceImage;
	public boolean IsWhite;
	public String Name;
	public int Type;	
	public String Position;
	
	//Constants used to identify the type of piece
	public static final int TYPE_ROOK = 1;
    public static final int TYPE_KNIGHT = 2;
    public static final int TYPE_BISHOP = 3;
    public static final int TYPE_QUEEN = 4;
    public static final int TYPE_KING = 5;
    public static final int TYPE_PAWN = 6;
	
	public Image getPieceImage() {
		return PieceImage;
	}
	public void setPieceImage(Image pieceImage) {
		PieceImage = pieceImage;
	}
	public boolean isIsWhite() {
		return IsWhite;
	}
	public void setIsWhite(boolean isWhite) {
		IsWhite = isWhite;
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
	private Image getImageForPiece(int color, int type) {
		 
	    String filename = "";
	 
	    filename += (this.isIsWhite() ? "w" : "b");
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
