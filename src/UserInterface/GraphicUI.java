package UserInterface;
import Data.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

public class GraphicUI {

	private JFrame frame;
	private JButton[][] chessBoardButtons = new JButton [8][8];
	public List<Piece> pieces = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicUI window = new GraphicUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Generate Main Panel, which contains the Tool Bar and the Game Panel
		JPanel MainPanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(MainPanel, BorderLayout.NORTH);
		
		//Generate the Tool bar and its 5 components (A label and 4 buttons)
		//TODO - Add functionality
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		MainPanel.add(toolBar, BorderLayout.PAGE_START);
		
		JButton btnNewGame = new JButton("Nuevo Juego");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Generate the Game Panel, which includes the Board and the extra game information
				JPanel GamePanel = new JPanel(new BorderLayout());
				frame.getContentPane().add(GamePanel, BorderLayout.CENTER);
				
				//TODO - Display Captured pieces
				//TODO - Show available promotions when a Pawn reaches the end row
				//TODO - Other Info (Game notation?)
				JLabel lblCapturasPromocionesEtc = new JLabel("Capturas, Promociones, etc");
				GamePanel.add(lblCapturasPromocionesEtc, BorderLayout.WEST);
				

				//Instance the basic ChessBoard
				chessBoardButtons = setBasicChessBoardButtons();
				JPanel ChessBoard = setChessBoard(chessBoardButtons);
				GamePanel.add(ChessBoard, BorderLayout.CENTER);
				
				initializeBoard();
				
				chessBoardButtons = paintPieces(pieces,chessBoardButtons);
				ChessBoard = setChessBoard(chessBoardButtons);
				GamePanel.add(ChessBoard, BorderLayout.CENTER);
			}
		});
		toolBar.add(btnNewGame);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(btnSave);
		
		JButton btnLoad = new JButton("Cargar");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(btnLoad);
		
		JButton btnResign = new JButton("Rendirse");
		btnResign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(btnResign);
		
		JLabel lblPlaceholdertext = new JLabel("PlaceHolderText");
		toolBar.add(lblPlaceholdertext);
		
		
		
	}
	
	/**
	 *Generates the starting pieces, saving them into the ArrayList pieces
	 *Completely unmanageable code, fix later
	 */
	public void initializeBoard(){
		//White pieces
		pieces.add(new Piece (true,1,1,1));
		pieces.add(new Piece (true,2,1,2));
		pieces.add(new Piece (true,3,1,3));
		pieces.add(new Piece (true,4,1,4));
		pieces.add(new Piece (true,5,1,5));
		pieces.add(new Piece (true,3,1,6));
		pieces.add(new Piece (true,2,1,7));
		pieces.add(new Piece (true,1,1,8));
		//White Pawns
		for (int i = 1; i < 9; i++){
			pieces.add(new Piece (true,6,2,i));
		}
		
		//Black pieces
		pieces.add(new Piece (false,1,8,1));
		pieces.add(new Piece (false,2,8,2));
		pieces.add(new Piece (false,3,8,3));
		pieces.add(new Piece (false,5,8,4));
		pieces.add(new Piece (false,4,8,5));
		pieces.add(new Piece (false,3,8,6));
		pieces.add(new Piece (false,2,8,7));
		pieces.add(new Piece (false,1,8,8));
		//Black Pawns
		for (int i = 1; i < 9; i++){
			pieces.add(new Piece (false,6,7,i));
		}
	}
	
	/**
	 * 
	 * @param pieces The list of pieces that must be painted
	 * @param chessBoardButtons An already existing array of buttons to be modified
	 * @return chessBoardButtons An array of buttons that has the painted pieces
	 */
	public JButton[][] paintPieces(List<Piece> pieces, JButton[][] chessBoardButtons){
		for(Piece piece : pieces){
			chessBoardButtons[8-(piece.getRow())][piece.getColumn()-1].setIcon(new ImageIcon(piece.getPieceImage())); 
		}
		return chessBoardButtons;
		
	}
	
	/**
	 * Generates the basic background-colored array of buttons
	 * @return an 8x8 array of buttons with just colored backgrounds
	 */
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
	/**
	 * Generates an entire ChessBoard , including a first row and first column of notation, and an 8x8 array of buttons
	 * @param chessBoardButtons The buttons that must be shown
	 * @return a ChessBoard with notation and the buttons
	 */
	public JPanel setChessBoard(JButton[][] chessBoardButtons){
		//Generate a 9x9 JPanel (First row and column for notation (l to r :ABCDEFGH) (bot to top: 12345678)
		JPanel ChessBoard = new JPanel();

		ChessBoard.setLayout(new GridLayout(0, 9));
		
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
	/**
	 * What should happen when you press a button?
	 * @param row the row of the button
	 * @param column the column of the button
	 */
	public void buttonPressed (int row, int column){
		Piece pieceInPosition = pieceInAPosition (row,column);
		System.out.println( Boolean.toString(pieceInPosition.isIsWhite()) + Integer.toString(pieceInPosition.getType()) );
		
	}
	/**
	 * Finds the piece that is ocuppying a especific row and column
	 * @param row the row integer
	 * @param column the column integer
	 * @return the piece object ocuppying that row and column. If empty, returns null
	 */
	public Piece pieceInAPosition (int row, int column){
		for (Piece piece : pieces){
			if (piece.getRow() == row && piece.getColumn() == column)
				return piece;
		}
		return null;
	}

	
}
