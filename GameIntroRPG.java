/*
 * Monica Tam
 * Mr. Slattery
 * Java Capstone p. 4
 * 3/12/17
 */
package seniorProj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameIntroRPG implements ActionListener, KeyListener{
	
	private JFrame frame;
	private JButton startButton;
	private JButton creditsButton;
	private JButton exitButton;
	
	//SPACES
	private JLabel titleSpace;
	private JLabel spaceBtwnLoadandSettings;
	private JLabel spaceBtwnSettingsandExit;

	
	public GameIntroRPG(){ //simply to set up jframe
		
		// Create a new JFrame container
		frame = new JFrame("The Lazuli Trials");
		
		// Give the frame an initial size
		frame.setSize(1200,800); // width,height
		
		frame.setLocationRelativeTo(null); //causes window to center upon open

		frame.setResizable(false);

		// Terminate the program when the user closes the application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		menuScreen();
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	/*
	 * sets up the buttons and labels for the menuscreen
	 */
	private void menuScreen(){
		
		/*
		 * sets the background of the menu
		 */
		JLabel background;
		background = new JLabel(new ImageIcon("res\\backgrounds\\rpg menu bg.PNG"));
		frame.setContentPane(background);
		frame.getContentPane().setLayout(new FlowLayout()); 
		
		//spacing labels
		titleSpace = new JLabel();
		titleSpace.setPreferredSize(new Dimension(1198,247));
				
		spaceBtwnLoadandSettings = new JLabel();
		spaceBtwnLoadandSettings.setPreferredSize(new Dimension(1198,20));
				
		spaceBtwnSettingsandExit = new JLabel();
		spaceBtwnSettingsandExit.setPreferredSize(new Dimension(1198,20));
				
		//startButton
		ImageIcon startGameLS = new ImageIcon("res\\backgrounds\\buttons\\startButtonRPG.png");
		startButton = new JButton(startGameLS);
		startButton.setVisible(true);
		startButton.setPreferredSize(new Dimension(370,130));
		startButton.setEnabled(true);
		startButton.addActionListener(this); //added actionlistener
		startButton.setActionCommand("start game");
				
		//creditsButton
		ImageIcon SettingsLS = new ImageIcon("res\\backgrounds\\buttons\\creditsRPG.png");
		creditsButton = new JButton(SettingsLS);
		creditsButton.setVisible(true);
		creditsButton.setPreferredSize(new Dimension(370,130));
		creditsButton.setEnabled(true);
		creditsButton.addActionListener(this); //added actionlistener
		creditsButton.setActionCommand("credits");
						
		//exitButton
		ImageIcon ExitLS = new ImageIcon("res\\backgrounds\\buttons\\ExitButtonsRPG.png");
		exitButton = new JButton(ExitLS);
		exitButton.addActionListener(this);
		exitButton.setActionCommand("quit");
		exitButton.setVisible(true);
		exitButton.setPreferredSize(new Dimension(370,130));
		exitButton.setEnabled(true);
				
		frame.getContentPane().add(titleSpace);
		frame.getContentPane().add(startButton);
		frame.getContentPane().add(spaceBtwnLoadandSettings);
		frame.getContentPane().add(creditsButton);
		frame.getContentPane().add(spaceBtwnSettingsandExit);
		frame.getContentPane().add(exitButton);
		
		frame.getContentPane().revalidate();  //refreshes frame and checks for removals from the frame
		frame.getContentPane().repaint();
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		//click start game in game menu --------------------------------Start Game-----------------------------------------------------------------------------------------------------------------------------------------------
		if(ae.getActionCommand()=="start game"){
			proceedToGame();
		}
		
		//sends user back to menu
		if(ae.getActionCommand()=="back"){
			frame.getContentPane().removeAll();
			menuScreen();
		}
		
		/*
		 * gives credits to where outside resources were found and contains a button to go back to menu
		 */
		if(ae.getActionCommand()=="credits"){
			JLabel creditsPage = new JLabel(new ImageIcon("res\\backgrounds\\creditsPage.png"));
			frame.setContentPane(creditsPage);
			frame.setLayout(new FlowLayout());
			JButton backButton = new JButton(new ImageIcon("res\\backgrounds\\buttons\\backButtonRPG.png")); 
			backButton.setPreferredSize(new Dimension(48,40)); //backbutton icon
			backButton.addActionListener(this);
			backButton.setActionCommand("back");
			backButton.setVisible(true);
			
			/*
			 * spacing labels
			 */
			JLabel space4 = new JLabel();
			space4.setPreferredSize(new Dimension(1198,30));
			JLabel space3 = new JLabel();
			space3.setPreferredSize(new Dimension(1070,30));

			frame.getContentPane().add(space4);
			frame.getContentPane().add(backButton);
			frame.getContentPane().add(space3);
			
			frame.getContentPane().revalidate();  //refreshes frame and checks for removals from the frame
			frame.getContentPane().repaint();

		}
		
		//exits the system
		if(ae.getActionCommand()=="quit"){
			System.exit(0);
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * starts the game
	 */
	private void proceedToGame(){
		JPanel black = new JPanel();
		black.setBackground(Color.BLACK);
		frame.getContentPane().removeAll();
		frame.setContentPane(black);
		frame.getContentPane().revalidate();  //refreshes frame and checks for removals from the frame
		frame.getContentPane().repaint();
		
		//make user object
		User user = new User();
		
		//then execute the RPGRunner for starting game
		frame.dispose();
		RpgRunner.runGame(user);
	}

}
