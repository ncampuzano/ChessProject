package Data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ChessGUI {

	private List<Piece> pieces = new ArrayList<>();
	private JButton[][] chessBoardButtons = new JButton [8][8];
	private JPanel ChessBoard = null;
	private JPanel GamePanel = null;
	private boolean isHoldingAPiece = false;
	private Piece heldPiece = null;
	private int gameState = GAME_STATE_WHITE;
    static final int GAME_STATE_WHITE = 1;
    static final int GAME_STATE_BLACK = 0;
    public JLabel lblGameState;
    private Helpers.MoveHelper MoveHelper ;
	
	public ChessGUI(JFrame frame){
		//Generate the Game Panel, which includes the Board and the extra game information
		GamePanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(GamePanel, BorderLayout.CENTER);
		MoveHelper = new Helpers.MoveHelper(this);
		//TODO - Display Captured pieces
		//TODO - Show available promotions when a Pawn reaches the end row
		//TODO - Other Info (Game notation?)
		String labelText = this.getGameStateAsText();
		lblGameState = new JLabel(labelText);
		
		JLabel lblCapturasPromocionesEtc = new JLabel("Capturas, Promociones, etc");
		GamePanel.add(lblCapturasPromocionesEtc, BorderLayout.WEST);
		
		//Instance the basic ChessBoard
		chessBoardButtons = setBasicChessBoardButtons();
		ChessBoard = setChessBoard(chessBoardButtons);
		GamePanel.add(ChessBoard, BorderLayout.CENTER);
		initializePieces();
		paintPieces();
		
		ChessBoard = setChessBoard(chessBoardButtons);
		GamePanel.add(ChessBoard, BorderLayout.CENTER);
	}
	
	/**
	 * Generates the basic background-colored array of buttons
	 * @return an 8x8 array of buttons with just colored backgrounds
	 */
	private String getGameStateAsText() {
        return (this.gameState == GAME_STATE_WHITE ? "Juegan blancas" : "Juegan negras");
    }
 
    /**
     * switches between the different game states 
     */
    public void changeGameState() {
        switch (this.gameState) {
            case GAME_STATE_WHITE:
                this.gameState = GAME_STATE_BLACK;
                break;
            case GAME_STATE_BLACK:
                this.gameState = GAME_STATE_WHITE;
                break;
            default:
                throw new IllegalStateException("unknown game state:" + this.gameState);
        }
        lblGameState.setText(this.getGameStateAsText());
    }
 
    /**
     * @return current game state
     */
    public int getGameState() {
        return this.gameState;
    }
    
	public JButton[][] setBasicChessBoardButtons (){
		//Generates an 8x8 Array of buttons and saves them 
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				chessBoardButtons[i][j] = new JButton();
				
				//Color each button the appropiate color
				if((i+j)%2 == 0){
					chessBoardButtons[i][j].setBackground(Color.WHITE);
				}
				else{
					chessBoardButtons[i][j].setBackground(Color.GRAY);
				}
				
				//Fucked up attempt at a solution
				//The variables in the actionPerformed() function must be final
				//MUST be fixed
				final int temp = i;
				final int temp2 = j;

				chessBoardButtons[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonPressed(8-temp, temp2+1);
						
					}
				});
			}
		}
		return chessBoardButtons;
	}
	
	
	public JPanel setChessBoard(JButton[][] chessBoardButtons){
		//Generate a 9x9 JPanel (First row and column for notation (l to r :ABCDEFGH) (bot to top: 12345678)
		JPanel ChessBoard = new JPanel();
		
		ChessBoard.setLayout(new GridLayout(0,9));
		
		ChessBoard.add(new JLabel(""));
		for (int i = (int)'A'; i < (int)'A'+8; i++)
			ChessBoard.add( new JLabel( Character.toString( (char)i ) ,
                    SwingConstants.CENTER ) );
		
		
		//Chooses between adding the leftmost numbers or introducing the 8x8 array of buttons to the main ChessBoard
				for (int i = 0; i < 8; i++){
					for (int j = 0; j < 8; j++){
						switch (j){
						case(0):
							ChessBoard.add(new JLabel ("" + (8-i) ,
		                            SwingConstants.CENTER ) );
						default:
							ChessBoard.add(chessBoardButtons[i][j]);
						}
					}
				}
		return ChessBoard;
	}
	
	
	public void buttonPressed (int row, int column){
		if(isHoldingAPiece){
			MoveHelper.EnableMovements(row, column, pieceInAPosition(row,column).getType(), gameState);
			pieces.remove(heldPiece);
			heldPiece.setColumn(column);
			heldPiece.setRow(row);
			pieces.add(heldPiece);
			repaintPieces();
			
			//Reset held piece
			heldPiece = null;
			isHoldingAPiece = false;
		}
		else{
			if(pieceInAPosition(row,column) != null && canPieceMove(pieceInAPosition(row,column))){
				heldPiece = pieceInAPosition (row,column);
				isHoldingAPiece = true;
			}
		}
	}
	
	
	public void initializePieces(){
		//White pieces
		pieces.add(new Piece (true,Piece.TYPE_ROOK  ,1,1));
		pieces.add(new Piece (true,Piece.TYPE_KNIGHT,1,2));
		pieces.add(new Piece (true,Piece.TYPE_BISHOP,1,3));
		pieces.add(new Piece (true,Piece.TYPE_QUEEN ,1,4));
		pieces.add(new Piece (true,Piece.TYPE_KING  ,1,5));
		pieces.add(new Piece (true,Piece.TYPE_BISHOP,1,6));
		pieces.add(new Piece (true,Piece.TYPE_KNIGHT,1,7));
		pieces.add(new Piece (true,Piece.TYPE_ROOK  ,1,8));
		//White Pawns
		for (int i = 1; i < 9; i++){
			pieces.add(new Piece (true,Piece.TYPE_PAWN,2,i));
		}
		
		//Black pieces
		pieces.add(new Piece (false,Piece.TYPE_ROOK  ,8,1));
		pieces.add(new Piece (false,Piece.TYPE_KNIGHT,8,2));
		pieces.add(new Piece (false,Piece.TYPE_BISHOP,8,3));
		pieces.add(new Piece (false,Piece.TYPE_KING  ,8,4));
		pieces.add(new Piece (false,Piece.TYPE_QUEEN ,8,5));
		pieces.add(new Piece (false,Piece.TYPE_BISHOP,8,6));
		pieces.add(new Piece (false,Piece.TYPE_KNIGHT,8,7));
		pieces.add(new Piece (false,Piece.TYPE_ROOK  ,8,8));
		//Black Pawns
		for (int i = 1; i < 9; i++){
			pieces.add(new Piece (false,Piece.TYPE_PAWN,7,i));
		}
	}
	
	
	public Piece pieceInAPosition (int row, int column){
		for (Piece piece : pieces){
			if (piece.getRow() == row && piece.getColumn() == column)
				return piece;
		}
		return null;
	}
	
	
	public void clearBoard (){
		for(int i = 0; i < 8 ; i++){
			for(int j = 0 ; j < 8; j++){
				chessBoardButtons[i][j].setIcon(null);
			}
			
		}
	}
	
	
	public void paintPieces(){
		for(Piece piece : pieces){
			chessBoardButtons[8-(piece.getRow())][piece.getColumn()-1].setIcon(new ImageIcon(piece.getPieceImage())); 
		}
	}
	
	
	public void repaintPieces(){
		clearBoard();
		paintPieces();
	}
	
	public boolean canPieceMove(Piece piece){
		if((piece.isIsWhite() ? 1 : 0) == gameState ){
			return true;
		}
		return false;
	}
	
	
	
	public List<Piece> getPieces() {
		return pieces;
	}
	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}
	public JButton[][] getChessBoardButtons() {
		return chessBoardButtons;
	}
	public void setChessBoardButtons(JButton[][] chessBoardButtons) {
		this.chessBoardButtons = chessBoardButtons;
	}
	public JPanel getChessBoard() {
		return ChessBoard;
	}
	public void setChessBoard(JPanel chessBoard) {
		ChessBoard = chessBoard;
	}
	public JPanel getGamePanel() {
		return GamePanel;
	}
	public void setGamePanel(JPanel gamePanel) {
		GamePanel = gamePanel;
	}
	public boolean isHoldingAPiece() {
		return isHoldingAPiece;
	}
	public void setHoldingAPiece(boolean isHoldingAPiece) {
		this.isHoldingAPiece = isHoldingAPiece;
	}
	public Piece getHeldPiece() {
		return heldPiece;
	}
	public void setHeldPiece(Piece heldPiece) {
		this.heldPiece = heldPiece;
	}
	
}
