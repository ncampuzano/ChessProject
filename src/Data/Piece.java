package Data;

public class Piece {
	public String PieceImage;
	public boolean IsWhite;
	public String Name;
	public int Type;	
	public String getPieceImage() {
		return PieceImage;
	}
	public void setPieceImage(String pieceImage) {
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
	
}
