package UserInterface;
import Data.*;
import Logic.ChessGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

public class GraphicUI {

	private JFrame frame;
	public JButton[][] chessBoardButtons = new JButton [8][8];
	public List<Piece> pieces = new ArrayList<>();
	public boolean isHoldingAPiece = false;
	public Piece heldPiece = null;
	public JPanel ChessBoard = null;
	public ChessGUI Chess = null;
	FileOutputStream fileStream = null;
	FileInputStream fileInStream = null;
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
	private void keepGame(){
		try {
			fileStream = new FileOutputStream("Game.obj");
			ObjectOutputStream os = new ObjectOutputStream(fileStream);
			if(Chess.isComputer()){
				os.writeObject("1\n" + Chess.movements);
			}else{
				os.writeObject("0\n" + Chess.movements);		
			}
			os.close();
        	JOptionPane.showMessageDialog(null,"Guardado correctamente" ,"",JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e1) {
        	JOptionPane.showMessageDialog(null,"No se pudo guardar" ,"",JOptionPane.INFORMATION_MESSAGE);
			
		}	
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Generate Main Panel, which contains the Tool Bar and the Game Panel
		JPanel MainPanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(MainPanel, BorderLayout.NORTH);
		
		//Generate the Tool bar and its 5 components (A label and 4 buttons)
		//TODO - Add functionality
		JMenuBar toolBar = new JMenuBar();
		toolBar.setEnabled(false);
		MainPanel.add(toolBar, BorderLayout.PAGE_START);
		
		JMenu btnNewGame = new JMenu("Nuevo Juego");
		toolBar.add(btnNewGame);
		JMenuItem btnPlayerGame = new JMenuItem("1 vs 1");
		JMenuItem btnComputerGame = new JMenuItem("1 vs CPU");
		
		btnNewGame.add(btnPlayerGame);
		btnNewGame.add(btnComputerGame);
		
		btnPlayerGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Generate the Game Panel, which includes the Board and the extra game information
				if(Chess != null){
					toolBar.remove(Chess.lblGameState);
					Chess.DestroyChessBoard(frame);
				}
				Chess =  new ChessGUI(frame,false,null);
				toolBar.add(Chess.lblGameState);
			}
		});
		btnComputerGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Generate the Game Panel, which includes the Board and the extra game information
				if(Chess != null){
					toolBar.remove(Chess.lblGameState);
					Chess.DestroyChessBoard(frame);
				}
				Chess =  new ChessGUI(frame,true,null);
				toolBar.add(Chess.lblGameState);
			}
		});
		JMenu btnActions = new JMenu("Acciones");
		toolBar.add(btnActions);
		JMenuItem btnSave = new JMenuItem("Guardar");
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Chess != null)
					keepGame();	
				else
		        	JOptionPane.showMessageDialog(null,"No hay ningún juego para guardar" ,"",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JMenuItem btnLoad = new JMenuItem("Cargar");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fileInStream = new FileInputStream("Game.obj");
					ObjectInputStream os = new ObjectInputStream(fileInStream);
					String movements = (String)os.readObject();
					if(Chess != null){
						toolBar.remove(Chess.lblGameState);
						Chess.DestroyChessBoard(frame);
					}
					if(movements.charAt(0) == '1'){
						Chess =  new ChessGUI(frame,true,movements);
						toolBar.add(Chess.lblGameState);
					}else{
						Chess =  new ChessGUI(frame,false,movements);
						toolBar.add(Chess.lblGameState);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}
			}
		});
		
		JMenuItem btnResign = new JMenuItem("Rendirse");
		btnResign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Chess != null){
					if(Chess.getGameState() == 0){
		    			Chess.lblGameState.setText("GAME OVER. WHITE WON!");
		    		}
		    		else{
		    			Chess.lblGameState.setText("GAME OVER. BLACK WON!");
		    		}
					Chess.setGameState(2);
					Chess.changeGameState();
				}
			}
		});
		btnActions.add(btnSave);
		btnActions.add(btnLoad);
		btnActions.add(btnResign);
		JMenu btnSeparator = new JMenu("           ");
		btnResign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(btnSeparator);
	}
}
