package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.GridLayout;

public class GraphicUI {

	private JFrame frame;
	private JButton[][] chessBoardButtons = new JButton [8][8];

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
		
		JPanel MainPanel = new JPanel(new BorderLayout());
		
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
		
		frame.getContentPane().add(MainPanel, BorderLayout.NORTH);
		
		JPanel GamePanel = new JPanel(new BorderLayout());
		frame.getContentPane().add(GamePanel, BorderLayout.CENTER);
		
		JLabel lblCapturasPromocionesEtc = new JLabel("Capturas, Promociones, etc");
		GamePanel.add(lblCapturasPromocionesEtc, BorderLayout.WEST);
		
		JPanel ChessBoard = new JPanel();
		GamePanel.add(ChessBoard, BorderLayout.CENTER);
		ChessBoard.setLayout(new GridLayout(9, 9));
		
		

	}

}
