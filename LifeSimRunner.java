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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


/*
 * responsible for decrementing need stats and increasing the clock
 */
class ThreadDemo extends Thread {
	   private Thread t;
	   private String threadName = "timer";
	   private Timer time;
	   private Character gameChar;
	   private GraphicRun gr;
	   private boolean stop = false;
	   
	   ThreadDemo( Character gameChar, GraphicRun gr) {
	      this.gameChar = gameChar;
	      this.gr = gr;
	   }
	   
	   public void setStop(boolean b){
		   this.stop = b;
	   }
	   
	   public void run() {
		   /*
		    * needs decrement every 30 seconds
		    */
		   time = new Timer(30000, new ActionListener(){
			   public void actionPerformed(ActionEvent evt){
				   time.stop();
				   gameChar.setHunger(gameChar.getHungerDec());
				   gameChar.setBladder(gameChar.getBladderDec());
				   gameChar.setHygiene(gameChar.getHygieneDec());
				   gameChar.setSleep(gameChar.getSleepDec());
				   gameChar.setSocial(gameChar.getSocialDec());
				   time.restart();
			   }
		   });

		   time.start();
		   
		   /*
		    * a minute is added every 3 seconds
		    */
		   time = new Timer(3000, new ActionListener(){
			   public void actionPerformed(ActionEvent evt){
				   time.stop();
				   if(!gameChar.isSleeping()){
					   gameChar.setMinute(Integer.parseInt(gameChar.getMinute())+1);
				   }
				   time.restart();
			   }
		   });

		   time.start();
		   
	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start();
	      }
	   }
	}

 class GraphicRun extends JLabel{
	 
	 /*
	  * locations of character
	  */
	 private int charX = 0;
	 private int charY = 0;
	 
	 //the current environment image location
	 private String background;
	 
	 //the image location of the character displayed
	 private String posture;
	 
	 //the current Environment
	 private Environment currentEnv;
	 
	 //the current character
	 private Character gameChar;
	 
	 //the LifeSimRunner running 
	 private LifeSimRunner LSRunner;
	 
	 //booleans for when to open the textbox
	 private boolean hearing = false;
	 private boolean notifying = false;
	 
	 //the things to show that will pop up for a short period of time
	 private ArrayList<String> notifications = new ArrayList<>();
	 
	 //for displaying the protester
	 private boolean protesting = false;
	 private Image protester = new ImageIcon("res\\characters\\weird dude\\front stand.png").getImage();
	 
	 //for when the user is not allowed to interact with the game
	 private boolean stayStill = false;
	 
	 //so that the store char location is used
	 private boolean restarted = false;
	 
	 private Timer time;
	 
	 //character, posture, background, and environment are set
	 public GraphicRun(Character ch, Environment env, LifeSimRunner ls){
		 this.gameChar = ch;
		 this.posture = "res\\characters\\"+ch.getGender()+"\\"+ch.getPosture()+" stand.png";
		 this.background = env.getImageLocation();
		 this.charX = ch.getCharX();
		 this.charY = ch.getCharY();
		 LSRunner = ls;
		 restarted = true;
		 setCurrentEnv(env);
	 }
	 
	 public GraphicRun(){
		 
	 }
	
	 private void doDrawing(Graphics g) {
		 /*
		  * the character and background are set to certain images
		  */
		 Image charImg;
		 charImg = new ImageIcon(this.posture).getImage();
		 Image background;
		 background = new ImageIcon(this.background).getImage();
		 
		 //the need bars are given background images
		 Image hunger = new ImageIcon("res\\UI\\LifeSim\\needBar.png").getImage();
		 Image bladder = new ImageIcon("res\\UI\\LifeSim\\needBar.png").getImage();
		 Image hygiene = new ImageIcon("res\\UI\\LifeSim\\needBar.png").getImage();
		 Image social = new ImageIcon("res\\UI\\LifeSim\\needBar.png").getImage();
		 Image sleep = new ImageIcon("res\\UI\\LifeSim\\needBar.png").getImage();

		 //the background and character image are drawn
		 Graphics2D g2d = (Graphics2D) g;
		 g2d.drawImage(background, 0, 0,null);		   
		 g2d.drawImage(charImg, charX, charY, null);

		 /*
		  * if at forestBeg environment, the LifeSim part will have ended, so all UI is not displayed 
		  */
		 if(currentEnv.getName().equals("forestBeg")){
			 posture = "";
		 }else{
			 /*
			  * this sets and draws the image of the protester
			  */
			 if(currentEnv.getName().equals("townsquareTutorial")){
				 if(protesting){
					 protester = new ImageIcon("res\\characters\\weird dude\\marching.gif").getImage();
					 protesting = false;
				 }
				 if(protester.equals(new ImageIcon("res\\characters\\weird dude\\fading.gif").getImage())){
					 g2d.drawImage(protester, 105, 310,null);		   
				 }else{
					 g2d.drawImage(protester, 165, 370,null);		   
				 }
			 }

			 /*
			  * sets the image of the character if a scene is going where the user must be stilled
			  */
			 if(stayStill){
				 if(currentEnv.getName().equals("insideHouseTutorial")||currentEnv.getName().equals("townsquareTutorial")){
					 posture = "res\\characters\\"+gameChar.getGender()+"\\leftside stand.png";
				 }
				 if(currentEnv.getName().equals("outsideHouseOpened")){
					 posture = "res\\characters\\"+gameChar.getGender()+"\\front stand.png";
				 }
			 }

			 /*
			  * checks that the it has been at least 3 days later, the user has finished the tutorial, and 
			  * the character has just exited the house before starting the old man leaving scene
			  */
			 if(gameChar.getDay()>=3&&Character.tutorialFinished&&Character.exitedHouseScene){
				 setCurrentEnv(new Environment(new File("res\\environments\\outsideHouseOpened\\envInfo.txt"), new File("res\\environments\\outsideHouseOpened\\objects.txt")));
			 }

			 /*
			  * displaying clock
			  */
			 g2d.setColor(new Color(255,255,255)); //color for the text of the clock
			 g2d.setFont(new Font("Orator Std", Font.BOLD,50)); //font of the clock
			 g2d.drawString("Day "+ gameChar.getDay(),977,42);
			 g2d.setFont(new Font("Orator Std", Font.BOLD,74)); //bigger font for the time
			 g2d.drawString(gameChar.getHour()+":"+gameChar.getMinute() + ""+ gameChar.getAmOrPm().toLowerCase(),890,100);

			 /*
			  * the notification list for the status of the hunger need
			  */

			 /*if hunger is full and it was from the user interacting with the game
			  * hunger1 makes it so this only happens once instead of repeating, so then you equal it to false inside
			  */
			 if(gameChar.getHunger()==107 && Character.hunger1&&gameChar.isHungerUsed()){
				 Character.hunger1 = false;
				 JOptionPane.showMessageDialog(this, "Yum, I'm never completely full but that was pretty satisfying.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getHunger()<36&&gameChar.getHunger()>24 && Character.hunger2){
				 Character.hunger2 = false;
				 Character.hunger1 = true;
				 JOptionPane.showMessageDialog(this, "I'm getting hungry.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getHunger()<24&&gameChar.getHunger()>12&&Character.hunger3){
				 Character.hunger3 = false;
				 Character.hunger2 = true;
				 JOptionPane.showMessageDialog(this, "I'm getting REALLY hungry.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getHunger()<12&&gameChar.getHunger()>0 && Character.hunger4){
				 Character.hunger4 = false;
				 Character.hunger3 = true;
				 JOptionPane.showMessageDialog(this, "Literally starving.");
			 }

			 //when hunger is 0, user save is deleted, frame is disposed, intro comes back, and a notif that you died appears
			 if(gameChar.getHunger()==0&&Character.hunger5){
				 Character.hunger5 = false;
				 User.deleteSave(gameChar.getSaveNum());
				 LSRunner.disposeFrame();
				 new GameIntro();
				 JOptionPane.showMessageDialog(this, "Game Over, You Died. How even? You had a fridge.");
			 }

			 /*if bladder is full and it was from the user interacting with the game
			  * bladder1 makes it so this only happens once instead of repeating, so then you equal it to false inside
			  */
			 if(gameChar.getBladder()==107 && Character.bladder1&&gameChar.isBladderUsed()){
				 Character.bladder1 = false;
				 JOptionPane.showMessageDialog(this, "Ahhh, I feel better now.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getBladder()<45&&gameChar.getBladder()>30 && Character.bladder2){
				 Character.bladder2 = false;
				 Character.bladder1 = true;
				 JOptionPane.showMessageDialog(this, "Uh...gonna need that bathroom soon.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getBladder()<30&&gameChar.getBladder()>15 && Character.bladder3){
				 Character.bladder3 = false;
				 Character.bladder2 = true;
				 JOptionPane.showMessageDialog(this, "REALLY need that bathroom soon.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getBladder()<15&&gameChar.getBladder()>0 && Character.bladder4){
				 Character.bladder4 = false;
				 Character.bladder3 = true;
				 JOptionPane.showMessageDialog(this, "Dude, I'm gonna pee my pants.");
			 }

			 /*
			  * once bladder is 0, the character pees their pants so their hygiene goes down to 0
			  * but their bladder goes to max
			  */
			 if(gameChar.getBladder()==0 && Character.bladder5){
				 Character.bladder5 = false;
				 Character.bladder4 = true;
				 gameChar.setBladder(107);
				 gameChar.setHygiene(-107);
			 }

			 /*if hygiene is full and it was from the user interacting with the game
			  * hygiene1 makes it so this only happens once instead of repeating, so then you equal it to false inside
			  */
			 if(gameChar.getHygiene()==107 && Character.hygiene1&&gameChar.isHygieneUsed()){
				 Character.hygiene1 = false;
				 JOptionPane.showMessageDialog(this, "It's nice to be clean and fresh.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getHygiene()<18&&gameChar.getHygiene()>12 && Character.hygiene2){
				 Character.hygiene2 = false;
				 Character.hygiene1 = true;
				 JOptionPane.showMessageDialog(this, "Feels like I'll need a shower real soon.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getHygiene()<12&&gameChar.getHygiene()>6 && Character.hygiene3){
				 Character.hygiene3 = false;
				 Character.hygiene2 = true;
				 JOptionPane.showMessageDialog(this, "I am STINKY.");
				 gameChar.setStinky(false);
			 }

			 //sets to stinky, which keeps people from speaking to the character
			 if(gameChar.getHygiene()<6&&gameChar.getHygiene()>0 && Character.hygiene4){
				 Character.hygiene4 = false;
				 Character.hygiene3 = true;
				 JOptionPane.showMessageDialog(this, "Literally no one wants to talk to me right now.");
				 gameChar.setStinky(true);
			 }


			 /*if social is full and it was from the user interacting with the game
			  * social1 makes it so this only happens once instead of repeating, so then you equal it to false inside
			  */

			 if(gameChar.getSocial()==107 && Character.social1&&gameChar.isSocialUsed()){
				 Character.social1= false;
				 JOptionPane.showMessageDialog(this, "I have a life!");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getSocial()<21&&gameChar.getSocial()>15 && Character.social2){
				 Character.social2= false;
				 Character.social1 = true;
				 JOptionPane.showMessageDialog(this, "I feel a bit lonely...");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getSocial()<15&&gameChar.getSocial()>9 && Character.social3){
				 Character.social3= false;
				 Character.social2 = true;
				 JOptionPane.showMessageDialog(this, "I'm a bit sad right now...");
			 }

			 //once social gets to this point, hunger starts decreasing faster
			 if(gameChar.getSocial()<9 && Character.social4){
				 Character.social4= false;
				 Character.social3 = true;
				 JOptionPane.showMessageDialog(this, "I feel depressed.");
				 gameChar.setHungerDec(-6);
			 }

			 //when social increases again, hunger decreases normally
			 if(gameChar.getSocial()>9&& Character.social5){
				 Character.social5= false;
				 Character.social4 = true;
				 gameChar.setHungerDec(-4);
			 }

			 /*if sleep is full and it was from the user interacting with the game
			  * sleep1 makes it so this only happens once instead of repeating, so then you equal it to false inside
			  */

			 if(gameChar.getSleep()==107 && Character.sleep1&&gameChar.isSleepUsed()){
				 Character.sleep1 = false;
				 JOptionPane.showMessageDialog(this, "Mm, that was a good night's sleep.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getSleep()<9&&gameChar.getSleep()>6&& Character.sleep2){
				 Character.sleep2 = false;
				 Character.sleep1 = true;
				 JOptionPane.showMessageDialog(this, "I'm getting pretty tired.");
			 }

			 /*
			  * same things, identifiers are used to keep from repeating 
			  */
			 if(gameChar.getSleep()<6&&gameChar.getSleep()>3&& Character.sleep3){
				 Character.sleep3 = false;
				 Character.sleep2 = true;
				 JOptionPane.showMessageDialog(this, "I really need to sleep.");
			 }

			 /*
			  * once sleep gets to this point, social starts decreasing faster
			  */
			 if(gameChar.getSleep()<3&& Character.sleep4){
				 Character.sleep4 = false;
				 Character.sleep3 = true;
				 JOptionPane.showMessageDialog(this, "I'm literally going to drop pretty soon.");
				 gameChar.setSocialDec(-5);
			 }

			 //once sleep goes back up, social decreases normally
			 if(gameChar.getSleep()>3&& Character.sleep5){
				 Character.sleep5 = false;
				 Character.sleep4 = true;
				 gameChar.setSocialDec(-3);
			 }

			 /*
			  * Drawing the needs bars -------------------------------------
			  */
			 
			 //Hunger
			 g2d.drawImage(hunger, 280, 714, null); //the background of the needs bar
			 g2d.setColor(new Color(255,195,5, 100)); //bar color color
			 g2d.fillRect(286, 720, gameChar.getHunger(), 20); //filling in the what the need bar is at

			 g2d.setColor(new Color(255,255,255)); //outline color
			 g2d.drawRect(286, 720, 107, 20); //outline of the inner bar

			 g2d.setFont(new Font("Adobe", Font.BOLD,18)); //font of the needs name
			 g2d.setColor(new Color(169,209,142));//text color
			 g2d.drawString("Hunger", 309, 736); //displaying the name

			 //Bladder
			 g2d.drawImage(bladder, 409, 714, null);//the background of the needs bar
			 g2d.setColor(new Color(255,195,5, 100)); //bar color color
			 g2d.fillRect(415, 720, gameChar.getBladder(), 20); //filling in the what the need bar is at

			 g2d.setColor(new Color(255,255,255)); //outline color
			 g2d.drawRect(415, 720, 107, 20);//outline of the inner bar

			 g2d.setFont(new Font("Adobe", Font.BOLD,18));//font of the needs name
			 g2d.setColor(new Color(169,209,142));//text color
			 g2d.drawString("Bladder", 438, 736);//displaying the name

			 //Hygiene
			 g2d.drawImage(hygiene, 538, 714, null);//the background of the needs bar
			 g2d.setColor(new Color(255,195,5, 100)); //bar color color
			 g2d.fillRect(544, 720, gameChar.getHygiene(), 20); //filling in the what the need bar is at

			 g2d.setColor(new Color(255,255,255)); //outline color
			 g2d.drawRect(544, 720, 107, 20);//outline of the inner bar

			 g2d.setFont(new Font("Adobe", Font.BOLD,18));//font of the needs name
			 g2d.setColor(new Color(169,209,142));//text color
			 g2d.drawString("Hygiene", 561, 736);//displaying the name

			 //Social
			 g2d.drawImage(social, 667, 714, null);//the background of the needs bar
			 g2d.setColor(new Color(255,195,5, 100)); //bar color color
			 g2d.fillRect(673, 720, gameChar.getSocial(), 20); //filling in the what the need bar is at

			 g2d.setColor(new Color(255,255,255)); //outline color
			 g2d.drawRect(673, 720, 107, 20);//outline of the inner bar

			 g2d.setFont(new Font("Adobe", Font.BOLD,18));//font of the needs name
			 g2d.setColor(new Color(169,209,142));//text color
			 g2d.drawString("Social", 690, 736);//displaying the name

			 //Sleep
			 g2d.drawImage(sleep, 796, 714, null);//the background of the needs bar
			 g2d.setColor(new Color(255,195,5, 100)); //bar color color
			 g2d.fillRect(802, 720, gameChar.getSleep(), 20); //filling in the what the need bar is at

			 g2d.setColor(new Color(255,255,255)); //outline color
			 g2d.drawRect(802, 720, 107, 20);//outline of the inner bar

			 g2d.setFont(new Font("Adobe", Font.BOLD,18));//font of the needs name
			 g2d.setColor(new Color(169,209,142));//text color
			 g2d.drawString("Sleep", 819, 736);//displaying the name
			 
			 /*
			  * Drawing the needs bars -------------------------------------
			  */
		 }

		 /*
		  * prints out what npcs say, the textbox is closeable by the space bar
		  */
		 if(gameChar.isHearing()){
			 g2d.setFont(new Font("Orator Std", Font.BOLD,18));
			 g2d.setColor(new Color(169,209,142));
			 Image textbox = new ImageIcon("res\\dialogue\\textbox.png").getImage();
			 g2d.drawImage(textbox, 250, 623,null);
			 int height = 650;
			 /*
			  * prints from an arraylist and draws it in the textbox by incrementing the height
			  */
			 for(int i =0; i<gameChar.getHearing().size();i++){
				 g2d.drawString(gameChar.getHearing().get(i),270,height);
				 height+=22;
			 }	
		 }

		 /*
		  * for when uncloseable text is displayed
		  */
		 if(notifying){
			 Image textbox;
			 int height = 650;
			 int width = 270;
			 /*
			  * displays the text at the end of the life sim part of the game
			  */
			 if(currentEnv.getName().equals("forestBeg")){
				 posture = "";
				 textbox = new ImageIcon("res\\dialogue\\textboxRPG2.png").getImage();
				 g2d.setFont(new Font("Tandysoft", Font.BOLD,18));
				 g2d.setColor(new Color(13,255, 229)); //text color
				 height = 650;
				 width = 280;
				 g2d.drawImage(textbox, 250, 600,null);
			 }else{
				 /*
				  * displays the text during the life sim part of the game
				  */
				 textbox = new ImageIcon("res\\dialogue\\textbox2.png").getImage();
				 g2d.setFont(new Font("Orator Std", Font.BOLD,18));
				 g2d.setColor(new Color(169,209,142));
				 g2d.drawImage(textbox, 250, 623,null);
			 }
			 /*
			  * prints from an arraylist and draws it in the textbox by incrementing the height
			  */
			 for(int i =0; i<notifications.size();i++){
				 g2d.drawString(notifications.get(i),width,height);
				 height+=22;
			 }	
		 }

		 repaint();      
	 }

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	public Environment getEnv(){
		return currentEnv;
	}
	
	//returns the notifications arraylist
	public ArrayList<String> getNotif(){
		return notifications;
	}

	/*
	 * sets the current environment of the game, also checks though for when scenes will begin by the name
	 * of the new environments
	 */
	public void setCurrentEnv(Environment currentEnv) {
		this.currentEnv = currentEnv;
		this.background = currentEnv.getImageLocation();
		//checks if character is exiting house to place her appropriately
		if(Character.exitedHouse||(Character.exitedHouseScene&&currentEnv.getName().equals("outsideHouseOpened"))){ 
			this.charX = 365;
			this.charY = 490;
			this.posture = "res\\characters\\"+gameChar.getGender()+"\\front stand.png";
			Character.exitedHouse = false;
		}else{
			//places character by her saved location if game has just started
			if(restarted){
				restarted = false;
				this.charX = gameChar.getCharX();
				this.charY = gameChar.getCharY();
			}else{
				//else it places the char by where the environment says the starting point should be
				this.charX = currentEnv.getCharStartX();
				this.charY = currentEnv.getCharStartY();
			}
		}
		//sets the name of the current environment to the Character object so it can be saved later
		gameChar.setCurrentEnv(currentEnv.getName());
		
		/*
		 * checks to begin the scene for the tutorial at the start of the game
		 * removes key adapter and disables the minimenu button so that user cannot interact with 
		 * game, then adds them back once the scene is done
		 * 
		 * sets notifying to true to start displaying notifications, which is clears first and then adds to the arraylist,
		 * using timer to let the user read the dialogue
		 */
		if(currentEnv.getName().equals("outsideHouseTutorial")){
			LSRunner.removeKeyAdapter();
			LifeSimRunner.butt.setEnabled(false);
			time = new Timer(1500, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   notifying = true;
					   notifications.clear();
					   notifications.add("Welcome, new player. Enter the house to begin.");
					   time = new Timer(3000, new ActionListener(){
						   public void actionPerformed(ActionEvent evt){
							   time.stop();
							   notifying = false;
							   notifications.clear();
							   LSRunner.addKeyAdapter();
							   LifeSimRunner.butt.setEnabled(true);
						   }
					   });

					   time.start();
				   }
			   });

			   time.start();
			
		}
		
		/*
		 * checks to begin the scene for inside the house
		 * removes key adapter and disables the minimenu button so that user cannot interact with 
		 * game, then adds them back once the scene is done
		 * 
		 * sets notifying to true to start displaying notifications, which is clears first and then adds to the arraylist,
		 * using timer to let the user read the dialogue
		 */
		if(currentEnv.getName().equals("insideHouseTutorial")){
			setEnvBackground("res\\environments\\insideHouseTutorial\\tutorial.png");
			LSRunner.removeKeyAdapter();
			stayStill = true;
			LifeSimRunner.butt.setEnabled(false);
			time = new Timer(1500, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   notifying = true;
					   notifications.clear();
					   notifications.add("You might be wondering how I got here before you,");
					   notifications.add("but don't worry about that. Instead, what do you");
					   notifications.add("think about your new crib?");
					   time = new Timer(5000, new ActionListener(){
						   public void actionPerformed(ActionEvent evt){
							   time.stop();
							   notifications.clear();
							   notifications.add("That's what the kids call it these days right?");
							   time = new Timer(4000, new ActionListener(){
								   public void actionPerformed(ActionEvent evt){
									   time.stop();
									   notifications.clear();
									   notifications.add("...Nevermind, I'm too old for this.");
									   time = new Timer(2000, new ActionListener(){
										   public void actionPerformed(ActionEvent evt){
											   time.stop();
											   notifications.clear();
											   notifications.add("As you can see, there are several meters at the bottom of");
											   notifications.add("your screen. At full capacity, you'll be a perfectly");
											   notifications.add("functioning human being. But closer to zero, not so much.");
											   time = new Timer(6500, new ActionListener(){
												   public void actionPerformed(ActionEvent evt){
													   time.stop();
													   notifications.clear();
													   notifications.add("You can interact with objects by standing next");
													   notifications.add("to them and pressing x.");
													   time = new Timer(3000, new ActionListener(){
														   public void actionPerformed(ActionEvent evt){
															   time.stop();
															   notifications.clear();
															   notifications.add("The hunger meter can be raised by the fridge, the");
															   notifications.add("bladder by the toilet, hygiene by the shower and sink,");
															   notifications.add("sleep by the bed, and social by people.");
															   time = new Timer(6500, new ActionListener(){
																   public void actionPerformed(ActionEvent evt){
																	   time.stop();
																	   notifications.clear();
																	   notifications.add("Be careful none of your meters are ever too low.");
																	   notifications.add("There are possible consequences.");
																	   time = new Timer(4000, new ActionListener(){
																		   public void actionPerformed(ActionEvent evt){
																			   time.stop();
																			   notifications.clear();
																			   notifications.add("Anyhow, feel free to try out your surroundings.");
																			   notifications.add("Meet me in townsquare when you're ready, it's the");
																			   notifications.add("exit to the left of the house.");
																			   time = new Timer(6000, new ActionListener(){
																				   public void actionPerformed(ActionEvent evt){
																					   time.stop();
																					   notifications.clear();
																					   notifications.add("Oh, and don't worry about me teleporting, I'm simply too old");
																					   notifications.add("to walk.");
																					   time = new Timer(3000, new ActionListener(){
																						   public void actionPerformed(ActionEvent evt){
																							   time.stop();
																							   notifying = false;
																							   notifications.clear();
																							   setEnvBackground("res\\environments\\insideHouseTutorial\\insideHouseTutorial.png");
																							   LSRunner.addKeyAdapter();
																							   stayStill = false;
																							   LifeSimRunner.butt.setEnabled(true);
																						   }
																					   });

																					   time.start();
																				   }
																			   });

																			   time.start();
																		   }
																	   });

																	   time.start();
																   }
															   });

															   time.start();
														   }
													   });

													   time.start();
												   }
											   });

											   time.start();
										   }
									   });

									   time.start();
								   }
							   });

							   time.start();

						   }
					   });

					   time.start();
				   }
			   });

			   time.start();

		}
		
		/*
		 * checks to begin the scene for the tutorial at the townsquare
		 * removes key adapter and disables the minimenu button so that user cannot interact with 
		 * game, then adds them back once the scene is done
		 * 
		 * sets notifying to true to start displaying notifications, which is clears first and then adds to the arraylist,
		 * using timer to let the user read the dialogue
		 */
		if(currentEnv.getName().equals("townsquareTutorial")){
			LSRunner.removeKeyAdapter();
			stayStill = true;
			LifeSimRunner.butt.setEnabled(false);
			protesting = true; //to show the protesting gif
			time = new Timer(1500, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   notifying = true;
					   notifications.clear();
					   notifications.add("RPG, RPG, RPG, RPG.");
					   time = new Timer(2000, new ActionListener(){
						   public void actionPerformed(ActionEvent evt){
							   time.stop();
							   notifications.clear();
							   notifications.add("Give me back my old life! Give it back! Give it back!");
							   time = new Timer(3000, new ActionListener(){
								   public void actionPerformed(ActionEvent evt){
									   time.stop();
									   notifications.clear();
									   notifications.add("Boy! Stop this nonsense this instant. You know the admins");
									   notifications.add("won't stand for this. We'll all be in trouble!");
									   time = new Timer(5000, new ActionListener(){
										   public void actionPerformed(ActionEvent evt){
											   time.stop();
											   //changes the image of the protester
											   protester = new ImageIcon("res\\characters\\weird dude\\front stand.png").getImage();
											   notifications.clear();
											   notifications.add("You're just a coward, old man. You don't even remember who");
											   notifications.add("you used to be anymore. You just continue to live peacefully");
											   notifications.add("with these fakes, it's pathetic.");
											   time = new Timer(5500, new ActionListener(){
												   public void actionPerformed(ActionEvent evt){
													   time.stop();
													   notifications.clear();
													   notifications.add("Why you little--");
													   time = new Timer(3000, new ActionListener(){
														   public void actionPerformed(ActionEvent evt){
															   time.stop();
															   notifications.clear();
															   //changes protester image
															   protester = new ImageIcon("res\\characters\\weird dude\\rightside stand.png").getImage();
															   notifications.add("Oh, look who's here. Guess it's a bit late to censor");
															   //changes environment image
															   setEnvBackground("res\\environments\\townsquareTutorial\\trouble seen.png");
															   notifications.add("me now, huh--what--");
															   time = new Timer(5000, new ActionListener(){
																   public void actionPerformed(ActionEvent evt){
																	   time.stop();
																	   //changes protester image
																	   protester = new ImageIcon("res\\characters\\weird dude\\fading.gif").getImage();
																	   notifying = false;
																	 //changes environment image
																	   setEnvBackground("res\\environments\\townsquareTutorial\\townsquareTutorial.png");
																	   time = new Timer(5000, new ActionListener(){
																		   public void actionPerformed(ActionEvent evt){
																			   time.stop();
																			   //takes away image of protester
																			   protester = new ImageIcon("").getImage();
																			   notifying = true;
																			   notifications.clear();
																			   notifications.add("No...now look what you've done...");							
																			   time = new Timer(3500, new ActionListener(){
																				   public void actionPerformed(ActionEvent evt){
																					   time.stop();
																					   notifications.clear();
																					 //changes environment image
																					   setEnvBackground("res\\environments\\townsquareTutorial\\trouble seen.png");
																					   notifications.add("This is quite awkward, but I'm afraid you'll have to aquaint");
																					   notifications.add("yourself with the others alone.");	
																					   time = new Timer(5000, new ActionListener(){
																						   public void actionPerformed(ActionEvent evt){
																							   time.stop();
																							   notifying = false;
																							   //sets to a new environment
																							   setCurrentEnv(new Environment(new File("res\\environments\\townsquare\\envInfo.txt"), new File("res\\environments\\townsquare\\objects.txt")));
																							   LSRunner.addKeyAdapter();
																							   stayStill = false;
																							   //identifies that tutorials have been finished
																							   Character.tutorialFinished = true;
																							   LifeSimRunner.butt.setEnabled(true);
																						   }
																					   });

																					   time.start();
																				   }
																			   });

																			   time.start();
																		   }
																	   });

																	   time.start();																   
																   }
															   });

															   time.start();
														   }
													   });

													   time.start();
												   }
											   });

											   time.start();
										   }
									   });

									   time.start();
								   }
							   });

							   time.start();
						   }
					   });

					   time.start();
				   }
			   });

			   time.start();
		}
		
		/*
		 * checks to begin the scene for when the old man leaves
		 * removes key adapter and disables the minimenu button so that user cannot interact with 
		 * game, then adds them back once the scene is done
		 * 
		 * sets notifying to true to start displaying notifications, which is clears first and then adds to the arraylist,
		 * using timer to let the user read the dialogue
		 * 
		 * environment background is changed several times to act as animation
		 */
		if(currentEnv.getName().equals("outsideHouseOpened")&&Character.exitedHouseScene){
			Character.exitedHouseScene = false;
			LSRunner.removeKeyAdapter();
			LifeSimRunner.butt.setEnabled(false);
			setEnvBackground("res\\environments\\outsideHouseOpened\\leaving.png");
			time = new Timer(1500, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   notifying = true;
					   notifications.clear();
					   notifications.add("That stupid boy, he must be in trouble now.");
					   time = new Timer(2500, new ActionListener(){
						   public void actionPerformed(ActionEvent evt){
							   time.stop();
							   notifications.clear();
							   notifications.add("I could save him...but that would mean disobeying the");
							   notifications.add("admins...");
							   time = new Timer(3000, new ActionListener(){
								   public void actionPerformed(ActionEvent evt){
									   time.stop();
									   notifications.clear();
									   notifications.add("Who knows what would happen afterwards...");
									   time = new Timer(3000, new ActionListener(){
										   public void actionPerformed(ActionEvent evt){
											   time.stop();
											   notifications.clear();
											   notifications.add("But I guess that's the thrill...");
											   time = new Timer(3000, new ActionListener(){
												   public void actionPerformed(ActionEvent evt){
													   time.stop();
													   setEnvBackground("res\\environments\\outsideHouseOpened\\seesChar.png");
													   notifications.clear();
													   notifications.add("Wouldn't you agree, "+gameChar.getName()+"?");
													   time = new Timer(2500, new ActionListener(){
														   public void actionPerformed(ActionEvent evt){
															   time.stop();
															   notifications.clear();
															   notifications.add("If you wish to follow me, I won't stop you, but you");
															   notifications.add("should be aware of the dangers that follow, and that");
															   notifications.add("all others will be gone once you enter the old world.");
															   time = new Timer(6000, new ActionListener(){
																   public void actionPerformed(ActionEvent evt){
																	   time.stop();
																	   notifications.clear();
																	   notifications.add("To walk off the paved road is a risk of your own life,");
																	   notifications.add("even I may never come back...");
																	   time = new Timer(4500, new ActionListener(){
																		   public void actionPerformed(ActionEvent evt){
																			   time.stop();
																			   setEnvBackground("res\\environments\\outsideHouseOpened\\opensRoad.png");
																			   notifications.clear();
																			   notifications.add("But then again, I'm old. What do I have to lose?");
																			   notifications.add("See you on the flip side, kid.");
																			   time = new Timer(4000, new ActionListener(){
																				   public void actionPerformed(ActionEvent evt){
																					   time.stop();
																					   notifying = false;
																					   setEnvBackground("res\\environments\\outsideHouseOpened\\outsideHouseOpened.png");
																					   LSRunner.addKeyAdapter();
																					   LifeSimRunner.butt.setEnabled(true);
																				   }
																			   });

																			   time.start();
																		   }
																	   });

																	   time.start();
																   }
															   });

															   time.start();
														   }
													   });

													   time.start();
												   }
											   });

											   time.start();
										   }
									   });

									   time.start();
								   }
							   });

							   time.start();
						   }
					   });

					   time.start();
				   }
			   });

			   time.start();
		}
		
		/*
		 * checks to begin the scene for the outro of the life sim part of the game 
		 * everything is disabled bc user is not able to interact
		 * 
		 * sets notifying to true to start displaying notifications, which is clears first and then adds to the arraylist,
		 * using timer to let the user read the dialogue
		 * 
		 * posture is set for the character for when the rpg part begins, a new rpg save file is made,
		 * all other save files are deleted, and the system closes
		 */
		if(currentEnv.getName().equals("forestBeg")){
			LSRunner.removeKeyAdapter();
			removeAll();
			setEnvBackground("res\\battle\\died.png");
			time = new Timer(1500, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   notifying=true;
					   notifications.clear();
					   notifications.add("...So you've made your decision.");
					   time = new Timer(2500, new ActionListener(){
						   public void actionPerformed(ActionEvent evt){
							   time.stop();
							   notifying=true;
							   notifications.clear();
							   notifications.add("Well, see you soon, bucko.");
							   time = new Timer(2500, new ActionListener(){
								   public void actionPerformed(ActionEvent evt){
									   time.stop();
									   gameChar.setPosture("rightside");
									   LSRunner.getUser().saveRPG();
									   User.deleteAll();
									   System.exit(0);
								   }
							   });

							   time.start();
						   }
					   });

					   time.start();
				   }
			   });

			   time.start();
		}

	}
	
	public Character getChar(){
		return gameChar;
	}
	
	public LifeSimRunner getLSRunner() {
		return LSRunner;
	}

	public void setLSRunner(LifeSimRunner lSRunner) {
		LSRunner = lSRunner;
	}
	
	public boolean isNotifying(){
		return notifying;
	}

	public void setNotifying(boolean notifying){
		this.notifying = notifying;
	}
	
	public boolean isHearing() {
		return hearing;
	}

	public void setHearing(boolean hearing) {
		this.hearing = hearing;
	}
	  
	public String getPosture(){
		return posture;
	}
	
	public void setPosture(String posture){
		this.posture = posture;
	}
	
	public Character getGameChar(){
		return gameChar;
	}
	
	public void setGameChar(Character gameChar){
		this.gameChar = gameChar;
	}
	
	public String getEnvBackground(){
		return background;
	}
	
	public void setEnvBackground(String background){
		this.background = background;
	}
	
	public int getCharX(){
		return charX;
	}
	
	public void incrementCharX(int x){
		charX +=x;
	}
	
	public int getCharY(){
		return charY;
	}
	
	public void incrementCharY(int y){
		charY += y;
	}
	  
	public void moveRight(){
		//checks boundaries
		currentEnv.checkPositionRight(this);
		
		//gives the walking animation
		posture = "res\\characters\\"+ gameChar.getGender() +"\\rightside walking.gif";
		
		//sets coordinate to save later
		gameChar.setCharX(this.charX);
		
		repaint();
	}
	public void moveLeft(){
		
		//checks boundaries
		currentEnv.checkPositionLeft(this);
		
		//gives the walking animation
		posture = "res\\characters\\"+ gameChar.getGender() +"\\leftside walking.gif";

		//sets coordinate to save later
		gameChar.setCharX(this.charX);
		
		repaint();
	}
	public void moveUp(){
		
		//checks boundaries
		currentEnv.checkPositionUp(this);

		//gives the walking animation
		posture = "res\\characters\\"+ gameChar.getGender() +"\\back walking.gif";
		
		//sets coordinate to save later
		gameChar.setCharY(this.charY);
		
		repaint();
	}

	public void moveDown(){
		//checks boundaries
		currentEnv.checkPositionDown(this);

		//gives the walking animation
		posture = "res\\characters\\"+ gameChar.getGender() +"\\front walking.gif";
		
		//sets coordinate to save later
		gameChar.setCharY(this.charY);
		
		repaint();
	}
 }
 
 public class LifeSimRunner implements KeyListener, ActionListener{
	
	private static JFrame frame = new JFrame();
	
	private static GraphicRun draw;
	
	private static User player;

	//buttons for the mini menu of the game
	private static JButton returnMenu;
	private static JButton save;
	private static JButton saveExit;
	private static JButton cancel;
	public static JButton butt =new JButton(new ImageIcon("res\\backgrounds\\buttons\\gameDotButton.png"));
	
	//checks so that the minimenu button is not remade constantly
	private static boolean buttonMade = false;
	
	public LifeSimRunner(){
		frame.setSize(1200,800); // width,height
		
		frame.setLocationRelativeTo(null); //causes window to center upon open

		frame.setResizable(false);

		// Terminate the program when the user closes the applicompletionGifion
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		frame.setVisible(true);
		
		//sets the keyadapter
        frame.addKeyListener(keyAdapter);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        
        //initializes the GraphicRun object, sets the game in motion
        draw = new GraphicRun(player.getChar(), new Environment(new File("res\\environments\\"+player.getChar().getCurrentEnv()+"\\envInfo.txt"), new File("res\\environments\\"+player.getChar().getCurrentEnv()+"\\objects.txt")),this);
        
    }
	
	//removes keyAdapter
	public void removeKeyAdapter(){
		frame.removeKeyListener(keyAdapter);
	}
	
	//disposes the frame
	public void disposeFrame(){
		frame.removeKeyListener(keyAdapter);
		frame.dispose();
	}
	
	//adds adapter back
	public void addKeyAdapter(){
		frame.addKeyListener(keyAdapter);
	}
	
	//returns the user 
	public User getUser(){
		return player;
	}
	
	private static KeyAdapter keyAdapter = new KeyAdapter(){
		public void keyPressed(KeyEvent ke) { 
			
			/*
			 * for moving the character
			 */
			if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
				draw.moveRight();
			}
	
			if(ke.getKeyCode()==KeyEvent.VK_LEFT){
				draw.moveLeft();
			}
			
			if(ke.getKeyCode()==KeyEvent.VK_UP){
				draw.moveUp();
			}
			
			if(ke.getKeyCode()==KeyEvent.VK_DOWN){
				draw.moveDown();
			}
			
			/*
			 * for interacting with objects
			 */
			if(ke.getKeyCode()==KeyEvent.VK_X){
				draw.getEnv().checkObjects(draw);
				frame.repaint();
			}
			
			/*
			 * for closing the textbox
			 */
			if(ke.getKeyCode()==KeyEvent.VK_SPACE){
				if(player.getChar().isHearing()){
					draw.setHearing(false);
					player.getChar().setIsHearing(false);
					player.getChar().getHearing().clear();
					frame.repaint();
				}
			}
		}
		
		public void keyReleased(KeyEvent ke) {
			/*
			 * these make it so that when the user stops pressing on the keys, the character exits the walking animation
			 */
			if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
				draw.setPosture("res\\characters\\"+ player.getChar().getGender() +"\\rightside stand.png");
				player.getChar().setPosture("rightside");
				frame.repaint();
			}
	
			if(ke.getKeyCode()==KeyEvent.VK_LEFT){
				draw.setPosture("res\\characters\\"+ player.getChar().getGender() +"\\leftside stand.png");
				player.getChar().setPosture("leftside");
				frame.repaint();
			}
			
			if(ke.getKeyCode()==KeyEvent.VK_UP){
				draw.setPosture("res\\characters\\"+ player.getChar().getGender() +"\\back stand.png");
				player.getChar().setPosture("back");
				frame.repaint();
			}
			
			if(ke.getKeyCode()==KeyEvent.VK_DOWN){
				draw.setPosture("res\\characters\\"+ player.getChar().getGender() +"\\front stand.png");
				player.getChar().setPosture("front");
				frame.repaint();
			}
		}
	};

	@Override
	public void keyReleased(KeyEvent ke) {
		
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		
	}
	
	/*
	 * sets contentpane of the frame to the GraphicRun
	 */
	public static void backToGame(){
		frame.setContentPane(draw);
		frame.setLayout(new FlowLayout());
		
		
		JLabel space1 = new JLabel(); //for spacing
		space1.setPreferredSize(new Dimension(1130,36));
		
		//checks to make sure the button is not readded to frame
		if(!buttonMade){
			//makes the minimenu button
			makeButton();
			frame.getContentPane().add(butt);
		}
		frame.getContentPane().add(space1);
		
		frame.getContentPane().revalidate();  //refreshes frame and checks for removed objects
		frame.getContentPane().repaint();
	}
	
	public static void runGame(User player1){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				
				//sets the user
				player = player1;
				
				//calls the constructor for the LifeSimRunner class, which sets some things up
				LifeSimRunner ls = new LifeSimRunner();
				
				//sets things up for lifesimrunner to run
				backToGame();
				
				//starts thread for clock and needs
				ThreadDemo td = new ThreadDemo(player.getChar(), draw);
				td.start();
				
			}
		});	
	}
	
	/*
	 * creates the minimenu button
	 */
	public static void makeButton(){
		butt.setPreferredSize(new Dimension(30,27));
		butt.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\gameDotButton.png"));
		butt.setVisible(true);
		butt.setFocusable(false);
		butt.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent ae)
		    {
		    	
		        frame.setContentPane(new JLabel(new ImageIcon("res\\backgrounds\\loadScreenBg.png")));
		        frame.setLayout(new FlowLayout());
		        
		        returnMenu = new JButton(new ImageIcon("res\\backgrounds\\buttons\\mainMenu.png"));
		        save= new JButton(new ImageIcon("res\\backgrounds\\buttons\\saveButt.png"));
		        saveExit = new JButton(new ImageIcon("res\\backgrounds\\buttons\\saveAndExit.png"));
		        cancel = new JButton(new ImageIcon("res\\backgrounds\\buttons\\cancelButt.png"));
		        
		        returnMenu.setPreferredSize(new Dimension(300,80));
		        save.setPreferredSize(new Dimension(300,80));
		        saveExit.setPreferredSize(new Dimension(300,80));
		        cancel.setPreferredSize(new Dimension(300,80));
		        
		        /*
		         * returns to main menu without saving
		         */
		        returnMenu.addActionListener(new ActionListener(){
		        	public void actionPerformed(ActionEvent ae){
		        		frame.removeKeyListener(keyAdapter);
		        		frame.dispose();
		        		new GameIntro();
		        		buttonMade = false;
		        	}
		        });
		        
		        /*
		         * saves and then shows confirmation message
		         */
		        save.addActionListener(new ActionListener(){
		        	public void actionPerformed(ActionEvent ae){
		        		player.save();
				    	JOptionPane.showMessageDialog(frame, "Saved.");
		        	}
		        });
		        
		        /*
		         * saves and exits
		         */
		        saveExit.addActionListener(new ActionListener(){
		        	public void actionPerformed(ActionEvent ae){
		        		player.save();
		        		System.exit(0);
		        		buttonMade = false;
		        	}
		        });
		        
		        /*
		         * cancels and goes back to the game
		         */
		        cancel.addActionListener(new ActionListener(){
		        	public void actionPerformed(ActionEvent ae){
		        		buttonMade = true;
		        		backToGame();
		        	}
		        });
		        
		        /*
		         * spacing labels
		         */
		        JLabel space1 = new JLabel();
		        space1.setPreferredSize(new Dimension(1200,150));
		        JLabel space2 = new JLabel();
		        space2.setPreferredSize(new Dimension(1200,25));
		        JLabel space3 = new JLabel();
		        space3.setPreferredSize(new Dimension(1200,25));
		        JLabel space4 = new JLabel();
		        space4.setPreferredSize(new Dimension(1200,25));
		        
		        frame.getContentPane().add(space1);
		        frame.getContentPane().add(returnMenu);
		        frame.getContentPane().add(space2);
		        frame.getContentPane().add(save);
		        frame.getContentPane().add(space3);
		        frame.getContentPane().add(saveExit);
		        frame.getContentPane().add(space4);
		        frame.getContentPane().add(cancel);
		        
		        frame.getContentPane().revalidate();  //refreshes frame and checks for removed objects
				frame.getContentPane().repaint();
		        
		    }
		});

		}
	
}
