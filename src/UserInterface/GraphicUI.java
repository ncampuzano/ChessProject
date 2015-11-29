package UserInterface;
import Data.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
		frame.setBounds(100, 100, 500, 500);
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
		
		//Generate the Game Panel, which includes the Board and the extra game information
		JPanel GamePanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(GamePanel, BorderLayout.CENTER);
		
		//TODO - Display Captured pieces
		//TODO - Show available promotions when a Pawn reaches the end row
		//TODO - Other Info (Game notation?)
		JLabel lblCapturasPromocionesEtc = new JLabel("Capturas, Promociones, etc");
		GamePanel.add(lblCapturasPromocionesEtc, BorderLayout.WEST);
		
		//Generate a 9x9 JPanel (First row and column for notation (l to r :ABCDEFGH) (top to bot: 12345678)
		JPanel ChessBoard = new JPanel();
		GamePanel.add(ChessBoard, BorderLayout.CENTER);
		ChessBoard.setLayout(new GridLayout(0, 9));
		
		//Adds the first top row, first an empty label, then, 8 Lettered labels
		ChessBoard.add(new JLabel(""));
		for (int i = (int)'A'; i < (int)'A'+8; i++)
			ChessBoard.add( new JLabel( Character.toString( (char)i ) ,
                    SwingConstants.CENTER ) );
		
		//Generates an 8x8 Array of buttons and saves them 
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				JButton b = new JButton();
				//Color each button the appropiate color
				if((i+j)%2 == 0){
					b.setBackground(Color.WHITE);
				}
				else{
					b.setBackground(Color.BLACK);
				}
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				chessBoardButtons[i][j] = b;
			}
		}
		
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
		initializeBoard();
		
	}
	
	//Instances all starting pieces and adds them to a list
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
		pieces.add(new Piece (false,4,8,4));
		pieces.add(new Piece (false,5,8,5));
		pieces.add(new Piece (false,3,8,6));
		pieces.add(new Piece (false,2,8,7));
		pieces.add(new Piece (false,1,8,8));
		//Black Pawns
		for (int i = 1; i < 9; i++){
			pieces.add(new Piece (false,6,7,i));
		}
	}

	
}
