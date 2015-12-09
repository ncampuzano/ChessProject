package Logic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Data.IAPlayer;
import Data.Move;
import Data.Piece;



public class ChessGUI {

	public List<Piece> pieces = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	private JButton[][] chessBoardButtons = new JButton [8][8];
	private JPanel ChessBoard = null;
	private JPanel GamePanel = null;
	JPanel panelCapturasPromocionesEtc = null;
	JPanel panelMovements = null;
	private boolean isHoldingAPiece = false;
	public boolean isComputer;
	
	private Piece heldPiece = null;
	private int gameState = GAME_STATE_WHITE;

	static final int GAME_STATE_WHITE = 1;
    static final int GAME_STATE_BLACK = 0;
    static final int GAME_STATE_END = 2;
    public JLabel lblGameState;
    public Helpers.MoveHelper MoveHelper ;
    public IAPlayer cpuPlayer;
    public String movements = "";
    JTextArea textMovement;
	
    
    public ChessGUI(JFrame frame, Boolean computer){
		
		//Generate the Game Panel, which includes the Board and the extra game information
		GamePanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(GamePanel, BorderLayout.CENTER);
		MoveHelper = new Helpers.MoveHelper(this);
		//TODO - Display Captured pieces
		//TODO - Show available promotions when a Pawn reaches the end row
		//TODO - Other Info (Game notation?)
		String labelText = this.getGameStateAsText();
		lblGameState = new JLabel(labelText);
		
		panelCapturasPromocionesEtc = new JPanel() ;
		panelCapturasPromocionesEtc.setLayout(new GridLayout(1,1));
		GamePanel.add(panelCapturasPromocionesEtc, BorderLayout.SOUTH);
	
		
		panelMovements = new JPanel() ;
		panelMovements.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0; 
		constraints.gridy = 0; 
		constraints.gridwidth = 1; 
		constraints.gridheight = 1; 

		panelMovements.add(new JLabel("   Movimientos"),constraints);
		constraints.gridx = 0; 
		constraints.gridy = 1; 
		constraints.gridwidth = 2; 
		constraints.gridheight = 2; 

		textMovement = new JTextArea();
		textMovement.setEditable(false);
		textMovement.setPreferredSize(new Dimension(100,400));
		JScrollPane scroll = new JScrollPane (textMovement, 
		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panelMovements.add(scroll,constraints);

		
		GamePanel.add(panelMovements, BorderLayout.WEST);
		
		

		//Instance the basic ChessBoard
		chessBoardButtons = setBasicChessBoardButtons();
		ChessBoard = setChessBoard(chessBoardButtons);
		GamePanel.add(ChessBoard, BorderLayout.CENTER);
		isComputer = computer;
		initializePieces();
		paintPieces();
		
		
		
	}
	public void paintCapturedPieces(){
		panelCapturasPromocionesEtc.removeAll();
		for(Piece piece : capturedPieces){
			panelCapturasPromocionesEtc.add(new JLabel(new ImageIcon(piece.getPieceImage())));
		}
	}
	public void DestroyChessBoard(JFrame frame){
		frame.getContentPane().remove(GamePanel);
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
    	if(endGameConditionReached()){
    		if(this.gameState == GAME_STATE_WHITE){
    			lblGameState.setText("GAME OVER. WHITE WON!");
    		}
    		else{
    			lblGameState.setText("GAME OVER. BLACK WON!");
    		}
    		this.gameState = GAME_STATE_END;
    	}
    	switch (this.gameState) {
	        case GAME_STATE_WHITE:
	            this.gameState = GAME_STATE_BLACK;
	            break;
	        case GAME_STATE_BLACK:
	            this.gameState = GAME_STATE_WHITE;
	            break;
	        case GAME_STATE_END:
	        	JOptionPane.showMessageDialog(null,"Fin Del Juego!" ,"",JOptionPane.INFORMATION_MESSAGE);
	        	break;
	        default:
	            throw new IllegalStateException("unknown game state:" + this.gameState);
    	}
    	if(isComputer && this.gameState == GAME_STATE_BLACK )
    		lblGameState.setText("Pensando...");
    	else
    		lblGameState.setText(this.getGameStateAsText());
    }
    
    public boolean endGameConditionReached(){
    	for(Piece piece : capturedPieces){
    		if(piece.getType() == Piece.TYPE_KING){
    			return true;
    		}
    	}
    	return false;
    }
    public void IAPlay(){
    	Move bestMovement  = cpuPlayer.getBestMove();
    	buttonPressed(bestMovement.sourceRow, bestMovement.sourceColumn);
    	buttonPressed(bestMovement.targetRow, bestMovement.targetColumn);
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
						buttonPressed(temp, temp2);
						
					}
				});
			}
		}
		return chessBoardButtons;
	}
	public void setBasicChessBackgrounds(){
		for(int i = 0; i<= 7; i++){
			for(int j = 0; j<= 7; j++){
				//Color each button the appropiate color
				if((i+j)%2 == 0){
					chessBoardButtons[i][j].setBackground(Color.WHITE);
				}
				else{
					chessBoardButtons[i][j].setBackground(Color.GRAY);
				}
			}
		}
	}
	public void colorAvailableMoves(){
		for(int i = 0; i<= 7; i++){
			for(int j = 0; j<= 7; j++){
				if(chessBoardButtons[i][j].isEnabled()){
					chessBoardButtons[i][j].setBackground(Color.YELLOW);
				}
			}
		}
	}
	
	public JPanel setChessBoard(JButton[][] chessBoardButtons){
		//Generate a 9x9 JPanel (First row and column for notation (l to r :ABCDEFGH) (bot to top: 12345678)
		JPanel ChessBoard = new JPanel();
		
		ChessBoard.setLayout(new GridLayout(0,9));
		
		ChessBoard.add(new JLabel(""));
		for (int i = (int)'H'; i > (int)'H'-8; i--)
			ChessBoard.add( new JLabel( Character.toString( (char)(i) ) ,
                    SwingConstants.CENTER ) );
		
		
		//Chooses between adding the leftmost numbers or introducing the 8x8 array of buttons to the main ChessBoard
				for (int i = 0; i < 8; i++){
					for (int j = 0; j < 8; j++){
						switch (j){
						case(0):
							ChessBoard.add(new JLabel ("" + (1+i) ,
		                            SwingConstants.CENTER ) );
						default:
							ChessBoard.add(chessBoardButtons[i][j]);
						}
					}
				}
		return ChessBoard;
	}
	
	public char convertToLetter(int number){
		switch(number){
			case 0: return 'H'; 
			case 1: return 'G';
			case 2: return 'F';
			case 3: return 'E';
			case 4: return 'D';
			case 5: return 'C';
			case 6: return 'B';
			case 7: return 'A';
			default: return 'A';
		}
	}
	
	public void buttonPressed (int row, int column){
		if(isHoldingAPiece){
			pieces.remove(heldPiece);
			if(isThereAPieceInPosition(row, column)){
				pieceInAPosition(row, column).setCapture(true);
				capturedPieces.add(pieceInAPosition(row,column));
				pieces.remove(pieceInAPosition(row,column));
				paintCapturedPieces();
			}
			if(heldPiece.getColumn() != column || heldPiece.getRow() != row){
					changeGameState();
					movements += "   " + convertToLetter(heldPiece.getColumn()) +""+ (heldPiece.getRow()+1) + " x " + convertToLetter(column) + (row +1) + "\n";
					textMovement.setText(movements);
			}
			heldPiece.setColumn(column);
			heldPiece.setRow(row);
			if( heldPiece.getType() == Piece.TYPE_PAWN && MoveHelper.isPromoted(heldPiece) ){
				heldPiece = new Piece(heldPiece.isWhite(),Piece.TYPE_QUEEN,heldPiece.getRow(),heldPiece.getColumn());
			}
			pieces.add(heldPiece);
			if(MoveHelper.isCheck(heldPiece.isWhite(), chessBoardButtons) ){
				lblGameState.setText("En Jaque Rey " +( heldPiece.isWhite() ? "Negro" : "Blanco") );
			}
			repaintPieces();
			MoveHelper.reEnableAll(chessBoardButtons);
			setBasicChessBackgrounds();
			//Reset held piece
			heldPiece = null;
			isHoldingAPiece = false;
			
			if(isComputer && this.gameState == GAME_STATE_BLACK )
	        	IAPlay();
			System.out.println(movements);
		}
		else{
			if(pieceInAPosition(row,column) != null && canPieceMove(pieceInAPosition(row,column))){
				MoveHelper.disableAll(chessBoardButtons);
				chessBoardButtons[row][column].setEnabled(true);
				colorAvailableMoves();
				MoveHelper.EnableMovements(pieceInAPosition(row,column), chessBoardButtons);
				colorAvailableMoves();
				heldPiece = pieceInAPosition (row,column);
				isHoldingAPiece = true;
			}
		}
	}
	
	
	public void initializePieces(){
		//White pieces
		pieces.add(new Piece (true,Piece.TYPE_ROOK  ,0,0));
		pieces.add(new Piece (true,Piece.TYPE_KNIGHT,0,1));
		pieces.add(new Piece (true,Piece.TYPE_BISHOP,0,2));
		pieces.add(new Piece (true,Piece.TYPE_QUEEN ,0,4));
		pieces.add(new Piece (true,Piece.TYPE_KING  ,0,3));
		pieces.add(new Piece (true,Piece.TYPE_BISHOP,0,5));
		pieces.add(new Piece (true,Piece.TYPE_KNIGHT,0,6));
		pieces.add(new Piece (true,Piece.TYPE_ROOK  ,0,7));
		//White Pawns
		for (int i = 0; i < 8; i++){
			pieces.add(new Piece (true,Piece.TYPE_PAWN,1,i));
		}
		
		//Black pieces
		pieces.add(new Piece (false,Piece.TYPE_ROOK  ,7,0));
		pieces.add(new Piece (false,Piece.TYPE_KNIGHT,7,1));
		pieces.add(new Piece (false,Piece.TYPE_BISHOP,7,2));
		pieces.add(new Piece (false,Piece.TYPE_KING  ,7,3));
		pieces.add(new Piece (false,Piece.TYPE_QUEEN ,7,4));
		pieces.add(new Piece (false,Piece.TYPE_BISHOP,7,5));
		pieces.add(new Piece (false,Piece.TYPE_KNIGHT,7,6));
		pieces.add(new Piece (false,Piece.TYPE_ROOK  ,7,7));
		//Black Pawns
		for (int i = 0; i < 8; i++){
			pieces.add(new Piece (false,Piece.TYPE_PAWN,6,i));
		}
		if(isComputer)
			cpuPlayer = new IAPlayer(this);
		
		
	}
	public boolean isThereAPieceInPosition(int row, int column){
		for(Piece piece : pieces){
			if(piece.getRow() == row && piece.getColumn() == column){
				return true;
			}
		}
		return false;
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
			chessBoardButtons[piece.getRow()][piece.getColumn()].setIcon(new ImageIcon(piece.getPieceImage())); 
		}
	}
	
	
	public void repaintPieces(){
		clearBoard();
		paintPieces();
	}
	
	public boolean canPieceMove(Piece piece){
		if((piece.isWhite() ? 1 : 0) == gameState ){
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
	public boolean isComputer() {
		return isComputer;
	}
	public void setComputer(boolean isComputer) {
		this.isComputer = isComputer;
	}
    public void setGameState(int gameState) {
		this.gameState = gameState;
	}
}
