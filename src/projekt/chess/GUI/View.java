package projekt.chess.GUI;

import projekt.chess.grids.Board;
import projekt.chess.grids.Grid;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.*;

import projekt.chess.Global.*;
import projekt.chess.Pieces.*;
import projekt.chess.Players.PlayerListener;

/**
 * The View class manages the graphical user interface using Swing.
 */
public class View extends JFrame implements PlayerListener, AbstractView {

	/* Attributs */

	/* MVC objects */
	private AbstractModel abstractModel;	

	/* Cases maps */
	private Map<CoordGraph, JPanelImage> mapMainGridCases = new TreeMap<CoordGraph, JPanelImage>();
	private Map<CoordGraph, JPanelImage> mapSmallGridCases = new TreeMap<CoordGraph, JPanelImage>();

	/* Containers */
	private JPanel container = new JPanel(new BorderLayout(0,0));
	private JPanel mainGridPanel = new JPanel(new GridBagLayout());

	private JPanel rightPanel = new JPanel(new BorderLayout(0,0));
	private JPanel infosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	private JPanel smallGridPanel = new JPanel(new GridBagLayout());

	/* Menu objects */	
	private JMenuBar jMenuBar = new JMenuBar();
	private JMenu aboutMenu = new JMenu("About");
	private JMenu gameMenu = new JMenu("Game");

	private JMenuItem newGameMenuItem = new JMenuItem("New Game");

	private JMenuItem rulesMenuItem = new JMenuItem("Rules");

	/* InfosPanel objects */	
	private JPanel panelPlayer1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
	private JPanel panelPlayer2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

	private JPanel labelPanelPlayer1 = new JPanel(new BorderLayout(0,0));
	private JPanel labelPanelPlayer2 = new JPanel(new BorderLayout(0,0));

	private JPanel piecesPanelPlayer1 = new JPanel(new GridLayout(2,0,6,0));
	private JPanel piecesPanelPlayer2 = new JPanel(new GridLayout(2,0,6,0));    


	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();

	/* Dead Pieces arrayList */
	private List<JPanelImage> deadPiecesCasesPlayer1 = new ArrayList<JPanelImage>();
	private List<JPanelImage> deadPiecesCasesPlayer2 = new ArrayList<JPanelImage>();

	/* Picture loading */
	private ImageIcon blackBishopIcon = new ImageIcon(this.getClass().getResource("Images/Black/bishop.png"));
	private ImageIcon blackKingIcon = new ImageIcon(this.getClass().getResource("Images/Black/king.png"));
	private ImageIcon blackKnightIcon = new ImageIcon(this.getClass().getResource("Images/Black/knight.png"));
	private ImageIcon blackPawnIcon = new ImageIcon(this.getClass().getResource("Images/Black/pawn.png"));
	private ImageIcon blackQueenIcon = new ImageIcon(this.getClass().getResource("Images/Black/queen.png"));
	private ImageIcon blackRookIcon = new ImageIcon(this.getClass().getResource("Images/Black/rook.png"));

	private ImageIcon whiteBishopIcon = new ImageIcon(this.getClass().getResource("Images/White/bishop.png"));
	private ImageIcon whiteKingIcon = new ImageIcon(this.getClass().getResource("Images/White/king.png"));
	private ImageIcon whiteKnightIcon = new ImageIcon(this.getClass().getResource("Images/White/knight.png"));
	private ImageIcon whitePawnIcon = new ImageIcon(this.getClass().getResource("Images/White/pawn.png"));
	private ImageIcon whiteQueenIcon = new ImageIcon(this.getClass().getResource("Images/White/queen.png"));
	private ImageIcon whiteRookIcon = new ImageIcon(this.getClass().getResource("Images/White/rook.png"));

	/* Constructor(s) */
	public View(AbstractModel abstractModel) {

		this.abstractModel = abstractModel;

		/* Frame/Panels initialization */
		this.windowInitialization("3D Chess", 625, 700);		
		this.menuInitialization();

		this.containersInitialization(625, 700);
		this.infosPanelInitialization();

		/* Creation of the cases */	
		this.mainGridInitialization();
		this.smallGridInitialization();

		this.mainBoardPlacement(abstractModel.getBoard());
		this.smallBoardPlacement(abstractModel.getBoard());

		this.piecesPlacement();

		/* Initialization of the deadPieces cases */
		this.deadPiecesInitialization();

		/* Display the window */
		this.pack();
		this.revalidate();
		this.setVisible(true);

		/* Center the frame */
		this.setLocationRelativeTo(null);
	}

	/* Initialization functions */
	private void windowInitialization(String title, int width, int height) {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	  
		this.setResizable(false);
		this.setTitle(title);	      

		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height)); 

		this.getContentPane().setPreferredSize(new Dimension(width, height));	
	}

	private void menuInitialization() {

		/* Add actions */
		newGameMenuItem.addActionListener(new MenuListener(this, abstractModel, "newGame"));

		rulesMenuItem.addActionListener(new MenuListener("rules"));

		/* Binding keys settings */
		newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

		rulesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

		/* Creation of the menu */
		this.setJMenuBar(jMenuBar);

		jMenuBar.setPreferredSize(new Dimension(0,25)); /* give a height to the bar, so that we can redefine the coordCase method */

		jMenuBar.add(gameMenu);
		jMenuBar.add(aboutMenu);	

		gameMenu.add(newGameMenuItem);
		gameMenu.addSeparator();  

		aboutMenu.add(rulesMenuItem);
	}

	private void containersInitialization(int width, int height) {

		/* Panel container */				
		container.setBackground(new Color(255,255,255));		
		container.setPreferredSize(this.getContentPane().getPreferredSize());		
		this.getContentPane().add(container);
	
		mainGridPanel.setBackground(container.getBackground());		
		mainGridPanel.setPreferredSize(new Dimension(375, height));					
		mainGridPanel.getLayout().preferredLayoutSize(mainGridPanel);

		rightPanel.setBackground(container.getBackground());		
		rightPanel.setPreferredSize(new Dimension(width-375, height));	
	
		infosPanel.setPreferredSize(new Dimension(250, 300));	

		smallGridPanel.setBackground(new Color(255,255,150));	
		smallGridPanel.setPreferredSize(new Dimension(250, height-300));	
		
		container.add(mainGridPanel, BorderLayout.WEST);	
		container.add(rightPanel);	

		rightPanel.add(infosPanel, BorderLayout.NORTH);		
		rightPanel.add(smallGridPanel);		
	}

	private void infosPanelInitialization() {

		Dimension infosPanelDimension = infosPanel.getPreferredSize();

		int width = (int) infosPanelDimension.getWidth();
		int height = (int) infosPanelDimension.getHeight()/2;

		int heighLabelPanel = (int) infosPanelDimension.getHeight()/6;
		int heightPiecesPanel = (int) infosPanelDimension.getHeight()/3;

		Dimension panelPlayerDimension = new Dimension(width, height);				
		Dimension labelPanelDimension = new Dimension(width, heighLabelPanel);
		Dimension piecesPanelDimension = new Dimension(width, heightPiecesPanel);		

		String textLabelPlayer1;
		String textLabelPlayer2;

		/* Initialization of the panels */
		panelPlayer1.setPreferredSize(panelPlayerDimension);
		panelPlayer2.setPreferredSize(panelPlayerDimension);

		/* Initialization of the labels */
		/*AbstractPlayer players1 = abstractModel.getListPlayers().get(0);
		AbstractPlayer players2 = abstractModel.getListPlayers().get(1);

		if(players1.getColor().equals(Color.BLACK))
		{
			textLabelPlayer1 = "" + players1.getName() + " : Black";
			textLabelPlayer2 = "" + players2.getName() + " : White";
		}
		else
		{*/
		textLabelPlayer1 = "Player 1 : White";
		textLabelPlayer2 = "Player 2 : Black";	
		/*}*/

		Color labelPanelColor = new Color(255,220,255);
		Color piecesPanelColor = new Color(255,240,210);

		labelPanelPlayer1.setBackground(labelPanelColor);
		labelPanelPlayer2.setBackground(labelPanelColor);

		labelPanelPlayer1.setPreferredSize(labelPanelDimension);
		labelPanelPlayer2.setPreferredSize(labelPanelDimension);

		piecesPanelPlayer1.setBackground(piecesPanelColor);	
		piecesPanelPlayer2.setBackground(piecesPanelColor);

		piecesPanelPlayer1.setPreferredSize(piecesPanelDimension);		
		piecesPanelPlayer2.setPreferredSize(piecesPanelDimension);

		/* Creation of the infosPanel */
		infosPanel.add(panelPlayer1);
		infosPanel.add(panelPlayer2);		

		panelPlayer1.add(labelPanelPlayer1);
		panelPlayer1.add(piecesPanelPlayer1);

		panelPlayer2.add(labelPanelPlayer2);
		panelPlayer2.add(piecesPanelPlayer2);

		jLabel1.setText(textLabelPlayer1);
		jLabel2.setText(textLabelPlayer2);

		Font f = jLabel1.getFont();
		jLabel1.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
		jLabel2.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));

		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

		labelPanelPlayer1.add(jLabel1, BorderLayout.CENTER);
		labelPanelPlayer2.add(jLabel2, BorderLayout.CENTER);
	}

	private void mainGridInitialization() {		

		int i, j;

		CoordGraph tmpCoordGraph;
		JPanelImage tmpJPanelImage;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		Color defaultColor = mainGridPanel.getBackground();

		int marginBot = 0;
		int marginRight = 0;		

		for(i=0; i<18; i++) {

			for(j=0; j<8; j++) {	

				tmpJPanelImage = new JPanelImage();
				tmpJPanelImage.setBackground(defaultColor);
				tmpJPanelImage.setPreferredSize(new Dimension(35,35));

				/* Position in the grid */
				gridBagConstraints.gridy = 18-i;
				gridBagConstraints.gridx = j;				

				/* Computation and affectation of the margin */
				if(j==1 || j==5)
					marginRight = 20;
				else
					marginRight = 0;

				if(i==6 || i==12)
					marginBot = 5;
				else
					marginBot = 0;

				gridBagConstraints.insets = new Insets(0, 0, marginBot, marginRight);
				gridBagConstraints.ipadx = 0;
				gridBagConstraints.ipady = 0;

				/* Add the case to the map ... */
				tmpCoordGraph = new CoordGraph(i,j);
				mapMainGridCases.put(tmpCoordGraph, tmpJPanelImage);				

				/* ... and to the main Panel */
				mainGridPanel.add(tmpJPanelImage, gridBagConstraints);
			}
		}
	}

	private void smallGridInitialization() {

		int i, j;

		CoordGraph tmpCoordGraph;
		JPanelImage tmpJPanelImage;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		Color defaultColor = smallGridPanel.getBackground();

		int marginBot = 0;
		int marginRight = 0;		

		for(i=0; i<18; i++) {

			for(j=0; j<8; j++) {

				tmpJPanelImage = new JPanelImage();
				tmpJPanelImage.setBackground(defaultColor);
				tmpJPanelImage.setPreferredSize(new Dimension(18,18));

				/* Position in the grid */
				gridBagConstraints.gridy = 18-i;
				gridBagConstraints.gridx = j;		

				/* Computation and affectation of the margin */
				if(j==1 || j==5)
					marginRight = 5;
				else
					marginRight = 0;

				if(i==6 || i==12)
					marginBot = 5;
				else
					marginBot = 0;

				gridBagConstraints.insets = new Insets(0, 0, marginBot, marginRight);
				gridBagConstraints.ipadx = 0;
				gridBagConstraints.ipady = 0;

				/* Add the case to the map ... */
				tmpCoordGraph = new CoordGraph(i,j);
				mapSmallGridCases.put(tmpCoordGraph, tmpJPanelImage);	

				/* ... and to the small Grid */
				smallGridPanel.add(tmpJPanelImage, gridBagConstraints);
			}
		}
	}

	private void deadPiecesInitialization() {

		int i;

		piecesPanelPlayer1.setLayout(new GridLayout(2,0,4,0));
		piecesPanelPlayer2.setLayout(new GridLayout(2,0,4,0));
		JPanelImage tmpCase;			
		Color defaultColor1 = piecesPanelPlayer1.getBackground();
		Color defaultColor2 = piecesPanelPlayer2.getBackground();

		for(i=0; i<16; i++) {

			tmpCase = new JPanelImage();			
			tmpCase.setBackground(defaultColor1);
			tmpCase.setPreferredSize(new Dimension(25,25));			
			deadPiecesCasesPlayer1.add(tmpCase);
			piecesPanelPlayer1.add(tmpCase);

			tmpCase = new JPanelImage();			
			tmpCase.setBackground(defaultColor2);
			tmpCase.setPreferredSize(new Dimension(25,25));			
			deadPiecesCasesPlayer2.add(tmpCase);
			piecesPanelPlayer2.add(tmpCase);
		}
		this.revalidate();
	}

	/* Placement functions */
	@Override
	public void attackBoardPlacement() {

		mainBoardPlacement(abstractModel.getBoard().getAttackBoards());
		smallBoardPlacement(abstractModel.getBoard().getAttackBoards());
	}

	@Override
	public void mainBoardPlacement(Board boardList)	{	

		int i, j;
		CoordGraph minCoord, maxCoord;

		JPanelImage tmpCase;

		Color blackColor = new Color(255,147,40);
		Color whiteColor = new Color(255,206,158);		
		Color tmpColor = blackColor;

		Border caseBorder = new javax.swing.border.LineBorder(new Color(8,4,16));

		for(Grid tmpBoard : boardList) {	

			minCoord = tmpBoard.getMinCoord().toCoordGraph();
			maxCoord = tmpBoard.getMaxCoord().toCoordGraph();

			for(i=minCoord.getX(); i<=maxCoord.getX(); i++) {

				for(j=minCoord.getY(); j<=maxCoord.getY(); j++) {

					tmpCase = getCaseFromCoords(new CoordGraph(i, j));
					tmpCase.setBackground(tmpColor);
					tmpCase.setBorder(caseBorder);

					if(tmpColor == blackColor)
						tmpColor = whiteColor;
					else
						tmpColor = blackColor;					
				}	

				if(tmpColor == blackColor)
					tmpColor = whiteColor;
				else
					tmpColor = blackColor;
			}			
		}
	}

	@Override
	public void smallBoardPlacement(Board boardList) {

		int i, j;
		CoordGraph minCoord, maxCoord;

		JPanelImage tmpCase;

		Color blackColor = new Color(255,147,40);
		Color whiteColor = new Color(255,206,158);		
		Color tmpColor = blackColor;

		Border caseBorder = new javax.swing.border.LineBorder(new Color(8,4,16));

		for(Grid tmpBoard : boardList) {

			minCoord = tmpBoard.getMinCoord().toCoordGraph();
			maxCoord = tmpBoard.getMaxCoord().toCoordGraph();

			for(i=minCoord.getX(); i<=maxCoord.getX(); i++) {

				for(j=minCoord.getY(); j<=maxCoord.getY(); j++) {

					tmpCase = getSmallCaseFromCoords(new CoordGraph(i, j));
					tmpCase.setBackground(tmpColor);
					tmpCase.setBorder(caseBorder);

					if(tmpColor == blackColor)
						tmpColor = whiteColor;
					else
						tmpColor = blackColor;					
				}	

				if(tmpColor == blackColor)
					tmpColor = whiteColor;
				else
					tmpColor = blackColor;
			}			
		}
	}

	@Override
	public void piecesPlacement() {

		JPanelImage tmpCase;

		ImageIcon tmpImageIcon;
		ImageIcon tmpImageIcon2;

		for(Piece tmpPiece : abstractModel.getPieces().getLivingPieces()) {

			tmpCase = getCaseFrom3DCoords(tmpPiece.getCoordinates());

			if(tmpPiece instanceof Pawn) {

				tmpImageIcon = blackPawnIcon;
				tmpImageIcon2 = whitePawnIcon;

			}else if(tmpPiece instanceof Rook) {

				tmpImageIcon = blackRookIcon;
				tmpImageIcon2 = whiteRookIcon;

			}else if(tmpPiece instanceof Knight) {

				tmpImageIcon = blackKnightIcon;
				tmpImageIcon2 = whiteKnightIcon;

			}else if(tmpPiece instanceof Bishop) {

				tmpImageIcon = blackBishopIcon;
				tmpImageIcon2 = whiteBishopIcon;

			}else if(tmpPiece instanceof Queen) {

				tmpImageIcon = blackQueenIcon;
				tmpImageIcon2 = whiteQueenIcon;

			}else if(tmpPiece instanceof King) {

				tmpImageIcon = blackKingIcon;
				tmpImageIcon2 = whiteKingIcon;

			}else {

				tmpImageIcon = null;
				tmpImageIcon2 = null;
			}

			if(tmpImageIcon != null ) {

				if(tmpPiece.getColor().equals(Color.BLACK))
					tmpCase.setImageIcon(tmpImageIcon);
				else
					tmpCase.setImageIcon(tmpImageIcon2);

				tmpCase.repaint();					
			}
		}
	}

	@Override
	public void deadPiecesPlacement() {	

		int i = 0;
		int j = 0;

		ListPieces deadPiecesSet = abstractModel.getPieces().getDeadPieces();

		JPanelImage tmpCase;			
		ImageIcon tmpImageIcon;
		ImageIcon tmpImageIcon2;

		for(Piece tmpPiece : deadPiecesSet) {	

			if(tmpPiece instanceof Pawn) {

				tmpImageIcon = blackPawnIcon;
				tmpImageIcon2 = whitePawnIcon;

			}else if(tmpPiece instanceof Rook) {

				tmpImageIcon = blackRookIcon;
				tmpImageIcon2 = whiteRookIcon;

			}else if(tmpPiece instanceof Knight) {

				tmpImageIcon = blackKnightIcon;
				tmpImageIcon2 = whiteKnightIcon;

			}else if(tmpPiece instanceof Bishop) {

				tmpImageIcon = blackBishopIcon;
				tmpImageIcon2 = whiteBishopIcon;

			}else if(tmpPiece instanceof Queen) {

				tmpImageIcon = blackQueenIcon;
				tmpImageIcon2 = whiteQueenIcon;

			}else if(tmpPiece instanceof King) {

				tmpImageIcon = blackKingIcon;
				tmpImageIcon2 = whiteKingIcon;

			}else {

				tmpImageIcon = null;
				tmpImageIcon2 = null;
			}

			if(tmpImageIcon != null) {

				if(tmpPiece.getColor().equals(Color.BLACK)) {

					tmpCase = deadPiecesCasesPlayer2.get(i);
					tmpCase.setImageIcon(tmpImageIcon);
					tmpCase.repaint();

					i++;

				}else {

					tmpCase = deadPiecesCasesPlayer1.get(j);
					tmpCase.setImageIcon(tmpImageIcon2);
					tmpCase.repaint();

					j++;
				}		
			}
		}
	}		

	/* Cleaning function */
	@Override
	public void cleanAttackBoards() {

		boardCleaning(abstractModel.getBoard().getAttackBoards());
	}
	
	@Override
	public void boardCleaning(Board boardList) {

		int i, j;
		CoordGraph minCoord, maxCoord;

		JPanelImage tmpCase;	
		Color mainGridColor = container.getBackground();
		Color smallGridColor = smallGridPanel.getBackground();

		for(Grid tmpBoard : boardList) {

			minCoord = tmpBoard.getMinCoord().toCoordGraph();
			maxCoord = tmpBoard.getMaxCoord().toCoordGraph();

			for(i=minCoord.getX(); i<=maxCoord.getX(); i++) {

				for(j=minCoord.getY(); j<=maxCoord.getY(); j++) {

					tmpCase = getCaseFromCoords(new CoordGraph(i, j));
					tmpCase.setBackground(mainGridColor);
					tmpCase.setBorder(null);		

					tmpCase = getSmallCaseFromCoords(new CoordGraph(i, j));
					tmpCase.setBackground(smallGridColor);
					tmpCase.setBorder(null);	
				}	
			}			
		}
	}

	@Override
	public void piecesCleaning() {

		JPanelImage tmpCase;

		for(Piece tmpPiece : abstractModel.getPieces()) {

			tmpCase = getCaseFrom3DCoords(tmpPiece.getCoordinates());

			tmpCase.setImageIcon(null);
			tmpCase.repaint();			
		}
	}

	@Override
	public void deadPiecesCleaning() {

		int i;
		JPanelImage tmpCase;

		for(i=0; i<16; i++) {

			tmpCase = deadPiecesCasesPlayer1.get(i);

			if(tmpCase.getImageIcon() != null) {

				tmpCase.setImageIcon(null);
				tmpCase.repaint();
			}

			tmpCase = deadPiecesCasesPlayer2.get(i);

			if(tmpCase.getImageIcon() != null) {

				tmpCase.setImageIcon(null);
				tmpCase.repaint();
			}
		}
	}

	@Override
	public JPanelImage getCaseFrom3DCoords(Coord coord) {	

		return getCaseFromCoords(coord.toCoordGraph());				
	}

	@Override
	public CoordGraph coordCaseAtPointer(int x, int y) {

		int posX, posY, width, heigth;
		boolean boolCaseExist = false;		

		CoordGraph tmpCoordGraph = null;
		JPanelImage tmpJPanelImage;

		x = x - this.getInsets().left;
		y = y - this.getInsets().top - jMenuBar.getHeight();

		Iterator<Map.Entry<CoordGraph, JPanelImage>> it = mapMainGridCases.entrySet().iterator();

		while(boolCaseExist == false && it.hasNext()) {

			Map.Entry<CoordGraph, JPanelImage> tmpCase = it.next();

			tmpCoordGraph = tmpCase.getKey();
			tmpJPanelImage = tmpCase.getValue();

			posX = tmpJPanelImage.getX();
			posY = tmpJPanelImage.getY();
			width = tmpJPanelImage.getWidth();
			heigth = tmpJPanelImage.getHeight();

			if(x >= posX && x <= posX+width && y >= posY && y <= posY+heigth) {

				if(!tmpJPanelImage.getBackground().equals(mainGridPanel.getBackground()))
					boolCaseExist = true;
			}
		}

		if(boolCaseExist)
			return tmpCoordGraph;
		else
			return null;
	}

	@Override
	public CoordGraph coordSmallCaseAtPointer(int x, int y) {

		int posX, posY, width, heigth;
		boolean boolCaseExist = false;		

		CoordGraph tmpCoordGraph = null;
		JPanelImage tmpJPanelImage;

		x = x - rightPanel.getX() - smallGridPanel.getX() - this.getInsets().left;
		y = y - rightPanel.getY() - smallGridPanel.getY() - this.getInsets().top - jMenuBar.getHeight();

		Iterator<Map.Entry<CoordGraph, JPanelImage>> it = mapSmallGridCases.entrySet().iterator();	

		while(boolCaseExist == false && it.hasNext()) {

			Map.Entry<CoordGraph, JPanelImage> tmpCase = it.next();

			tmpCoordGraph = tmpCase.getKey();
			tmpJPanelImage = tmpCase.getValue();

			posX = tmpJPanelImage.getX();
			posY = tmpJPanelImage.getY();
			width = tmpJPanelImage.getWidth();
			heigth = tmpJPanelImage.getHeight();

			if(x>=posX && x<=posX+width && y>=posY && y<=posY+heigth) {

				boolCaseExist = true;
			}
		}

		if(boolCaseExist)
			return tmpCoordGraph;
		else
			return null;
	}	


	@Override
	public JPanelImage getCaseFromCoords(CoordGraph coordGraph) {

		return mapMainGridCases.get(coordGraph);
	}

	@Override
	public JPanelImage getSmallCaseFromCoords(CoordGraph coordGraph) {

		return mapSmallGridCases.get(coordGraph);
	}

	@Override
	public void refreshDeadPieces() {

		this.deadPiecesCleaning();
		this.deadPiecesPlacement();
	}

	@Override
	public void refreshCurrentPlayer() {

		Font f = jLabel1.getFont();

		if(abstractModel.getCurrentPlayer().equals(abstractModel.getListPlayers().get(0))) {

			jLabel1.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			jLabel2.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));

		}else {

			jLabel2.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			jLabel1.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
		}
	}

	@Override
	public void hasPlayed() {
		refreshCurrentPlayer();

		if(abstractModel.isCheck(abstractModel.getCurrentPlayer()))
			JOptionPane.showMessageDialog(this, abstractModel.getCurrentPlayer().getName() + " is in check !", "You are in check", JOptionPane.WARNING_MESSAGE);
	}	

	@Override
	public int popupPlayer(String playerName) {

		Object[] options = {"Yes, he's a human player", "No, it's a bot"};
		return JOptionPane.showOptionDialog(this, "Is " + playerName + " human ?", "Human or bot", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	@Override
	public void setLocationRelativeTo(Component object) {
		super.setLocationRelativeTo(object);
	}
}