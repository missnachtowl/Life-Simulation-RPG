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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//contains the two diff thiss

public class GameIntro implements ActionListener, KeyListener{
	
	private JFrame frame;
	
	private JButton startButton;
	private JButton creditsButton;
	private JButton exitButton;
	
	//SPACES
	private JLabel titleSpace;
	private JLabel spaceBtwnLoadandSettings;
	private JLabel spaceBtwnSettingsandExit;
	
	//User Variables
	private String userName;
	private String gender;
	
	//Load Screen
	private JButton load1;
	private JButton load2;
	private JButton load3;
	private JButton load4;
	private int loadNum = 0;
		//loaded
	JLabel space1 = new JLabel();		
	JLabel space2 = new JLabel();
	JLabel space3 = new JLabel();
	JButton deleteFile = new JButton(new ImageIcon("res\\backgrounds\\buttons\\delete.png"));
	JButton runFile = new JButton(new ImageIcon("res\\backgrounds\\buttons\\run.png"));
	JButton cancelFile = new JButton(new ImageIcon("res\\backgrounds\\buttons\\cancel.png"));
	
	//User Info
	private JTextField nameField;
	private JButton male;
	private JButton female;
	
	public GameIntro(){ //simply to set up jframe
		
		// Create a new JFrame container
		frame = new JFrame("Peaceful Living");
		
		// Give the frame an initial size
		frame.setSize(1200,800); // width,height
		
		frame.setLocationRelativeTo(null); //causes window to center upon open

		frame.setResizable(false);

		// Terminate the program when the user closes the application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		menuScreen(); //where all the buttons and etc are set
	}
	
	private void menuScreen(){
		
		//sets background of the menu
		JLabel background;
		background = new JLabel(new ImageIcon("res\\backgrounds\\LifeSim menu bg.PNG")); 
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
		ImageIcon startGameLS = new ImageIcon("res\\backgrounds\\buttons\\startButtonLS.png");
		startButton = new JButton(startGameLS);
		startButton.setVisible(true);
		startButton.setPreferredSize(new Dimension(370,130));
		startButton.setEnabled(true);
		startButton.addActionListener(this); //added actionlistener
		startButton.setActionCommand("start game");
				
		//creditsButton
		ImageIcon SettingsLS = new ImageIcon("res\\backgrounds\\buttons\\creditsLS.png");
		creditsButton = new JButton(SettingsLS);
		creditsButton.setVisible(true);
		creditsButton.setPreferredSize(new Dimension(370,130));
		creditsButton.addActionListener(this); //added actionlistener
		creditsButton.setActionCommand("credits");
		creditsButton.setEnabled(true);
						
		//exitButton
		ImageIcon ExitLS = new ImageIcon("res\\backgrounds\\buttons\\ExitButtonsLS.png");
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
		
		frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
		frame.getContentPane().repaint();
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		//click start game in game menu --------------------------------Start Game-----------------------------------------------------------------------------------------------------------------------------------------------
		if(ae.getActionCommand()=="start game"){
			
			/*
			 * button for going back to menuscreen
			 */
			JButton backButton = new JButton(new ImageIcon("res\\backgrounds\\buttons\\backButton.png")); 
			backButton.setActionCommand("back");
			backButton.setVisible(true);
			
			/*
			 * Labels for spacing buttons
			 */
			JLabel space1 = new JLabel();
			JLabel space2 = new JLabel();
			JLabel space3 = new JLabel();
			JLabel space4 = new JLabel();
			JLabel space5 = new JLabel();
			JLabel space6 = new JLabel();
			JLabel space7 = new JLabel();
			
			/*goes into saves made file to check which save files have been used (4 in total)
			 * since each file has been given its own button
			 */
			User.populateSaves(); 
			
			//if file is not used, a new save icon is given to the button, else it is a load# image
			//a disabled icon image is used for when a button is selected
			if(User.isSaved(1)==false){
				load1 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
				load1.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}else{
				load1 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\load1.png"));
				load1.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load1.png"));
			}
			load1.setVisible(true);
			load1.setEnabled(true);
			
			//if file is not used, a new save icon is given to the button, else it is a load# image
			//a disabled icon image is used for when a button is selected
			if(User.isSaved(2)==false){
				load2 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
				load2.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}else{
				load2 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\load2.png"));
				load2.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load2.png"));
			}
			load2.setVisible(true);
			load2.setEnabled(true);
			
			//if file is not used, a new save icon is given to the button, else it is a load# image
			//a disabled icon image is used for when a button is selected
			if(User.isSaved(3)==false){
				load3 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
				load3.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}else{
				load3 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\load3.png"));
				load3.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load3.png"));
			}
			load3.setVisible(true);
			load3.setEnabled(true);
			
			//if file is not used, a new save icon is given to the button, else it is a load# image
			//a disabled icon image is used for when a button is selected
			if(User.isSaved(4)==false){
				load4 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
				load4.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}else{
				load4 = new JButton(new ImageIcon("res\\backgrounds\\buttons\\load4.png"));
				load4.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load4.png"));
			}
			load4.setVisible(true);
			load4.setEnabled(true);
			
			//set action commands to load buttons
			load1.setActionCommand("load 1");
			load2.setActionCommand("load 2");
			load3.setActionCommand("load 3");
			load4.setActionCommand("load 4");

			
			//set Sizes
			backButton.setPreferredSize(new Dimension(48,40)); //backbutton icon
			space1.setPreferredSize(new Dimension(1198,150));
			space2.setPreferredSize(new Dimension(5,30));
			space3.setPreferredSize(new Dimension(1070,30));
			space4.setPreferredSize(new Dimension(1198,30));
			space5.setPreferredSize(new Dimension(15,200));
			space6.setPreferredSize(new Dimension(15,200));
			space7.setPreferredSize(new Dimension(1198,15));
			load1.setPreferredSize(new Dimension(492,200));
			load2.setPreferredSize(new Dimension(492,200));
			load3.setPreferredSize(new Dimension(492,200));
			load4.setPreferredSize(new Dimension(492,200));
			
			//add listeners
			backButton.addActionListener(this);
			load1.addActionListener(this);
			load2.addActionListener(this);
			load3.addActionListener(this);
			load4.addActionListener(this);

			//setting load screen bg
			JLabel loadBg;
			loadBg = new JLabel(new ImageIcon("res\\backgrounds\\selectASave.png")); 
			frame.setContentPane(loadBg);
			frame.getContentPane().setVisible(true);
			frame.getContentPane().setLayout(new FlowLayout());
			
			frame.getContentPane().removeAll();
			
			/*
			 * adds all buttons and spaces
			 */
			frame.getContentPane().add(space4);
//			frame.getContentPane().add(space2);
			frame.getContentPane().add(backButton);
			frame.getContentPane().add(space3);
			frame.getContentPane().add(space1);
			frame.getContentPane().add(load1);
			frame.getContentPane().add(space5);
			frame.getContentPane().add(load2);
			frame.getContentPane().add(space7);
			frame.getContentPane().add(load3);
			frame.getContentPane().add(space6);
			frame.getContentPane().add(load4);
			frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
			frame.getContentPane().repaint();
			
		}
		
		/*
		 * removes the buttons added on to confirm selection of a load
		 * reenables all load buttons
		 */
		if(ae.getActionCommand()=="cancelFile"){ 
			
			frame.getContentPane().remove(space1);
			frame.getContentPane().remove(deleteFile);
			frame.getContentPane().remove(space2);
			frame.getContentPane().remove(cancelFile);
			frame.getContentPane().remove(space3);
			frame.getContentPane().remove(runFile);
			
			if(User.isSaved(1)){
				load1.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load1.png"));
			}else{
				load1.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}
			if(User.isSaved(2)){
				load2.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load2.png"));
			}else{
				load2.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}
			if(User.isSaved(3)){
				load3.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load3.png"));
			}else{
				load3.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}
			if(User.isSaved(4)){
				load4.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load4.png"));
			}else{
				load4.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\newSave.png"));
			}
			
			load1.setEnabled(true);
			load2.setEnabled(true);
			load3.setEnabled(true);
			load4.setEnabled(true);
			
			frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
			frame.getContentPane().repaint();
		}
		
		//stores the name entered and executes the screen after entering name
		if(ae.getActionCommand()=="Done"){
			userName = nameField.getText();
			System.out.println("username: " + userName);
			afterNameEntered();
		}
		 
		//sets the gender selected and executes method that confirms
		if(ae.getActionCommand()=="male"){
			//set user char object to male
			male.setEnabled(false);
			male.setDisabledIcon(new ImageIcon("res\\backgrounds\\create char stuff\\boyOpButtonglow.png"));
			female.setEnabled(false);
			gender = "boy";
			System.out.println("gender: " + gender);
			afterGenderEntered();
		}
		
		//sets the gender selected and executes method that confirms
		if(ae.getActionCommand()=="female"){
			//set user char object to male
			female.setEnabled(false);
			female.setDisabledIcon(new ImageIcon("res\\backgrounds\\create char stuff\\girlOpButtonglow.png"));
			male.setEnabled(false);
			gender = "girl";
			System.out.println("gender: " + gender);
			afterGenderEntered();
		}
		
		//goes into the game
		if(ae.getActionCommand()=="go"){
			proceedToGame();
		}
		
		//resets the gender selection
		if(ae.getActionCommand()=="cancel"){
			frame.getContentPane().removeAll();
			afterNameEntered();
		}
		
		//--------------------------------------------------------------------------------End of Start Game Chunk------------------------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------*******----------------------------------------------------------------------------------------------------------------------

		
		//--------------------------------------------------------------------------------Load Save File Chunk------------------------------------------------------------------------------------------------------------------

		//load 1
		/*
		 * if load selected has a stored file, it asks for confirmation to proceed to game
		 * else it starts a new game by entering the create a character stage
		 */
		if(ae.getActionCommand()=="load 1"&&User.isSaved(1)==true){
			loadNum = 1;
			load1.setEnabled(false);
			load2.setEnabled(false);
			load3.setEnabled(false);
			load4.setEnabled(false);
			
			load1.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load1glow.png"));
			loaded();
		}else{
			if(ae.getActionCommand()=="load 1"&&User.isSaved(1)==false){
				System.out.println("is saved false");
				loadNum = 1;
				newSave();
			}
		}
		
		//load 2
		/*
		 * if load selected has a stored file, it asks for confirmation to proceed to game
		 * else it starts a new game by entering the create a character stage
		 */
		if(ae.getActionCommand()=="load 2"&&User.isSaved(2)==true){
			loadNum = 2;
			load1.setEnabled(false);
			load2.setEnabled(false);
			load3.setEnabled(false);
			load4.setEnabled(false);
			
			load2.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load2glow.png"));
			loaded();
		}else{
			if(ae.getActionCommand()=="load 2"&&User.isSaved(2)==false){
				loadNum = 2;
				newSave();
			}		
		}
		
		//load 3
		/*
		 * if load selected has a stored file, it asks for confirmation to proceed to game
		 * else it starts a new game by entering the create a character stage
		 */
		if(ae.getActionCommand()=="load 3"&&User.isSaved(3)==true){
			loadNum = 3;
			load1.setEnabled(false);
			load2.setEnabled(false);
			load3.setEnabled(false);
			load4.setEnabled(false);
			
			load3.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load3glow.png"));
			loaded();
		}else{
			if(ae.getActionCommand()=="load 3"&&User.isSaved(3)==false){
				loadNum = 3;
				newSave();
			}	
		}
		
		//load 4
		/*
		 * if load selected has a stored file, it asks for confirmation to proceed to game
		 * else it starts a new game by entering the create a character stage
		 */
		if(ae.getActionCommand()=="load 4"&&User.isSaved(4)==true){
			loadNum = 4;
			load1.setEnabled(false);
			load2.setEnabled(false);
			load3.setEnabled(false);
			load4.setEnabled(false);
			
			load4.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\load4glow.png"));
			loaded();
		}else{
			if(ae.getActionCommand()=="load 4"&&User.isSaved(4)==false){
				loadNum = 4;
				newSave();
			}	
		}
		
		/*
		 * Confirms whether to delete the file or not, 
		 * if yes, frame goes back to menu and a notification is shown to show it has been deleted
		 * else it stays the same
		 */
		if(ae.getActionCommand()=="deleteFile"){
			int confirm = JOptionPane.showConfirmDialog(frame,"Are you sure you want to delete this save file?", "Wait", JOptionPane.YES_NO_OPTION);
			if(confirm == JOptionPane.YES_OPTION){
				User.deleteSave(loadNum);
				menuScreen();		
				deleteFile.removeActionListener(this); //keeps the option pane from repeating
				JOptionPane.showMessageDialog(frame, "Save Deleted.");
			}else{
				//do nothing
			}
		}
		
		/*
		 * proceeds to the game
		 */
		if(ae.getActionCommand()=="runFile"){
			proceedToGame();
		}
		
		/*
		 * goes back to menu 
		 */
		if(ae.getActionCommand()=="back"){
			frame.getContentPane().removeAll();
			menuScreen();
		}
		
		//--------------------------------------------------------------------------------End of Load Save File Chunk------------------------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------*******----------------------------------------------------------------------------------------------------------------------
		
		/*
		 * shows the credits of where outside resources were gathered and contains a back button to return to menu
		 */
		if(ae.getActionCommand()=="credits"){
			JLabel creditsPage = new JLabel(new ImageIcon("res\\backgrounds\\creditsPage.png"));
			frame.setContentPane(creditsPage);
			frame.setLayout(new FlowLayout());
			JButton backButton = new JButton(new ImageIcon("res\\backgrounds\\buttons\\backButton.png")); 
			backButton.setPreferredSize(new Dimension(48,40)); //backbutton icon
			backButton.addActionListener(this);
			backButton.setActionCommand("back");
			backButton.setVisible(true);
			
			JLabel space4 = new JLabel();
			space4.setPreferredSize(new Dimension(1198,30));
			JLabel space3 = new JLabel();
			space3.setPreferredSize(new Dimension(1070,30));

			
			frame.getContentPane().add(space4);
			frame.getContentPane().add(backButton);
			frame.getContentPane().add(space3);
			
			frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
			frame.getContentPane().repaint();

		}
		
		/*
		 * closes system entirely
		 */
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
	 * the confirmation buttons after a load button has been selected,
	 * gives options to delete the file, run the file and proceed to game, or cancel the file selection
	 */
	private void loaded(){
		space1.setPreferredSize(new Dimension(1198,7));
		space2.setPreferredSize(new Dimension(10,20)); //space btwn buttons
		space3.setPreferredSize(new Dimension(10,20)); //space btwn buttons
		
		deleteFile.setPreferredSize(new Dimension(50,50));
		runFile.setPreferredSize(new Dimension(50,50));
		cancelFile.setPreferredSize(new Dimension(70,50));
		deleteFile.setActionCommand("deleteFile");
		runFile.setActionCommand("runFile");
		cancelFile.setActionCommand("cancelFile");
		deleteFile.addActionListener(this);
		runFile.addActionListener(this);
		cancelFile.addActionListener(this);
		
		frame.getContentPane().add(space1);
		frame.getContentPane().add(deleteFile);
		frame.getContentPane().add(space2);
		frame.getContentPane().add(cancelFile);
		frame.getContentPane().add(space3);
		frame.getContentPane().add(runFile);
		
		frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
		frame.getContentPane().repaint();
	}
	
	/*
	 * gives confirmation of whether to run the game as a girl or boy or to cancel
	 */
	private void afterGenderEntered(){
		space2.setPreferredSize(new Dimension(10,20)); //space btwn buttons
		space3.setPreferredSize(new Dimension(25,20)); //space btwn buttons
		
		JButton go = new JButton(new ImageIcon("res\\backgrounds\\buttons\\run.png"));
		go.setPreferredSize(new Dimension(50,50));
		JButton cancel = new JButton(new ImageIcon("res\\backgrounds\\buttons\\cancel.png"));
		cancel.setPreferredSize(new Dimension(70,50));
		go.setActionCommand("go");
		cancel.setActionCommand("cancel");
		go.addActionListener(this);
		cancel.addActionListener(this);
		
		frame.getContentPane().add(space2);
		frame.getContentPane().add(go);
		frame.getContentPane().add(space3);
		frame.getContentPane().add(cancel);
		
		frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
		frame.getContentPane().repaint();
	}
	
	/*
	 * starts the create a character stage
	 */
	private void newSave(){
		frame.getContentPane().removeAll();
		
		JLabel newSaveBg = new JLabel(new ImageIcon("res\\backgrounds\\loadScreenBg.png")); 
		frame.setContentPane(newSaveBg);
		frame.getContentPane().setVisible(true);
		frame.getContentPane().setLayout(new FlowLayout());
		
		JLabel space1 = new JLabel();
		space1.setPreferredSize(new Dimension(1200,45));
		JLabel nameQuestion = new JLabel(new ImageIcon("res\\backgrounds\\create char stuff\\nameCharLabel.PNG"));
		nameQuestion.setPreferredSize(new Dimension(1000,150));

		JLabel space2 = new JLabel();
		space2.setPreferredSize(new Dimension(1200,50));
		
		//might want to edit fonts and text color
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(300,50));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		Color borderColor = new Color(170,209,143);
		nameField.setBorder(BorderFactory.createLineBorder(borderColor, 5));
		Color textColor = new Color(170,209,143);
		nameField.setForeground(textColor); //sets text color
		Font font = new Font("Orator Std", Font.PLAIN,30);
		nameField.setFont(font);
		
		JButton nameEntered = new JButton("Done");
		nameEntered.addActionListener(this);
		
		frame.getContentPane().add(space1);
		frame.getContentPane().add(nameQuestion);
		frame.getContentPane().add(space2);
		frame.getContentPane().add(nameField);
		frame.getContentPane().add(nameEntered);
		frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
		frame.getContentPane().repaint();
		
		//allows for enter button to be pressed to activate listener for the nameEntered button
		frame.getRootPane().setDefaultButton(nameEntered); 

	}
	
	/*
	 * sets up the gender option page for creating a character
	 */
	private void afterNameEntered(){
		if(nameField.getText().equals("")){
			//do nothing
		}else{
			userName = nameField.getText();
			
			frame.getContentPane().removeAll();
			
			JLabel space1 = new JLabel();
			space1.setPreferredSize(new Dimension(1200,45));
			JLabel space2 = new JLabel();
			space2.setPreferredSize(new Dimension(1200,32));
			JLabel space3 = new JLabel();
			space3.setPreferredSize(new Dimension(100,353));
			
			JLabel genderQuestion = new JLabel(new ImageIcon("res\\backgrounds\\create char stuff\\selectCharacterLabel.png"));
			genderQuestion.setPreferredSize(new Dimension(1000,150));
			JLabel space4 = new JLabel();
			space4.setPreferredSize(new Dimension(1200,35));
			
			male = new JButton(new ImageIcon("res\\backgrounds\\create char stuff\\boyOpButton.png"));
			female = new JButton(new ImageIcon("res\\backgrounds\\create char stuff\\girlOpButton.png"));
	
			male.setDisabledIcon(new ImageIcon("res\\backgrounds\\create char stuff\\boyOpButton.png"));
			female.setDisabledIcon(new ImageIcon("res\\backgrounds\\create char stuff\\girlOpButton.png"));
			
			male.setPreferredSize(new Dimension(259,353));
			female.setPreferredSize(new Dimension(259,353));
			male.setActionCommand("male");
			male.addActionListener(this);
			female.setActionCommand("female");
			female.addActionListener(this);
			
			frame.getContentPane().add(space1);
			frame.getContentPane().add(genderQuestion);
			frame.getContentPane().add(space2);
			frame.getContentPane().add(female);
			frame.getContentPane().add(space3);
			frame.getContentPane().add(male);
			frame.getContentPane().add(space4);
			frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
			frame.getContentPane().repaint();
		}
	}
	
	//method for after user finishes entering info
	private void proceedToGame(){
		JPanel black = new JPanel();
		black.setBackground(Color.BLACK);
		
		frame.getContentPane().removeAll();
		frame.setContentPane(black);
		frame.getContentPane().revalidate();  //refreshes the frame and checks for anything removed
		frame.getContentPane().repaint();
		
		//make user object
		User user = new User(userName, gender, loadNum);
		user.save(); //saves to create or update save file
		frame.dispose();
		
		//executes the game
		LifeSimRunner.runGame(user);
	}

}
