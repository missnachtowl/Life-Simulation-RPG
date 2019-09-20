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

 class GraphicRunRPG extends JLabel{
	 
	 private Timer time;
	 
	 /*
	  * locations of character
	  */
	 private int charX = 0;
	 private int charY = 0;
	 
	 //image of monster
	 private Image monster = new ImageIcon("res\\characters\\monster\\creepy floating.gif").getImage();
	 
	 /*
	  * locations of monster
	  */
	 private int monX = 635;
	 private int monY = 295;
	 
	 //the current environment image location
	 private Image background;
	 
	 //the image location of the character displayed
	 private String posture;
	 
	//the current Environment
	 private Environment currentEnv;
	 
	//the current character
	 private Character gameChar;
	 
	//the rpgRunner running
	 private RpgRunner rpgRunner;
	 
	 /*
	  * the Fighter objects for when the battle begins
	  */
	 private Fighter hero;
	 private Fighter villain;
	 
	//booleans for when to open the textbox
	 private boolean hearing = false;
	 
	 //the things to show that will pop up for a short period of time
	 private ArrayList<String> notifications = new ArrayList<>();
	 
	 //boolean for when the battle portion begins
	 private static boolean inFight = false;
	 
	 //boolean so that battle UI is not constantly recreated
	 private static boolean madeUI = false;
	 
	 //arraylist for strings shown during battle
	 private ArrayList<String> fightString = new ArrayList<String>();
	 
	 //character, posture, background, and environment are set
	 //fightString is given its default string
	 //inFight and madeUI starts as false
	 public GraphicRunRPG(Character ch, Environment env){
		 this.gameChar = ch;
		 this.posture = "res\\characters\\"+ch.getGender()+"\\"+ch.getPosture()+" stand.png";
		 this.background = new ImageIcon(env.getImageLocation()).getImage();
		 this.charX = ch.getCharX();
		 this.charY = ch.getCharY();
		 setCurrentEnv(env);
		 fightString.add("What will you do?");
		 inFight = false;
		 madeUI = false;
	 }
	 
	 public GraphicRunRPG(){
		 
	 }
	
	 private void doDrawing(Graphics g) {
		 /*
		  * character is set to a certain image and that and background are drawn
		  */
		 Graphics2D g2d = (Graphics2D) g;
		 Image charImg;
		 charImg = new ImageIcon(this.posture).getImage();

		 g2d.drawImage(background, 0, 0,null);		   
		 g2d.drawImage(charImg, charX, charY, null);
		 
		 //in battle mode
		 if(inFight){
			 setEnvBackground("res\\battle\\fight " +gameChar.getGender()+ ".png");
			 setPosture("none"); //char is not displayed
			 setMonsterLocation(695,45);
			 //villain bars
			 g2d.setColor(new Color(0,0, 0)); //bar color color
			 g2d.fillRect(237, 61, villain.getBarHealth(), 30); // health bar
			 g2d.fillRect(237, 101, villain.getBarStrength(), 9); // strength bar
			 g2d.fillRect(237, 120, villain.getBarDefense(), 9); // defense bar

			 //hero bars
			 g2d.setColor(new Color(13,255, 249)); //bar color color
			 g2d.fillRect(809, 419, hero.getBarHealth(), 29); // health bar
			 g2d.fillRect(809, 460, hero.getBarStrength(), 8); // strength bar
			 g2d.fillRect(809, 478, hero.getBarDefense(), 7); // defense bar
			 
			 /*
			  * displays fightString arraylist, increments the height
			  */
			 g2d.setFont(new Font("Tandysoft", Font.BOLD,18));
			 int height = 575;
			 for(int i =0; i<fightString.size();i++){
				 g2d.drawString(fightString.get(i),75,height);
				 height+=22;
			 }	
			 
			 //makes UI once and set monster to fighting gif
			 if(!madeUI){
				 monster = new ImageIcon("res\\characters\\monster\\fighting.gif").getImage();
				 RpgRunner.fightUI();
				 madeUI = true;
			 }
		 }

		 /*
		  * draws the monster
		  */
		 if(gameChar.getCurrentEnv().equals("clearing")){
			 g2d.drawImage(monster, monX, monY, null);
		 }
		 
		 /*
		  * does not display character and stops user interaction
		  */
		 if(gameChar.getCurrentEnv().equals("end")){
			 rpgRunner.removeKeyAdapter();
			 posture = "none";
		 }
		 
		 /*
		  * displays when npcs talk in the game from the Character hearing arraylist
		  * increments the height to display
		  */
		 if(gameChar.isHearing()){
			 g2d.setFont(new Font("Tandysoft", Font.BOLD,18));
			 g2d.setColor(new Color(13,255, 229)); //text color
			 Image textbox = new ImageIcon("res\\dialogue\\textboxRPG2.png").getImage();
			 g2d.drawImage(textbox, 230, 600,null);
			 int height = 640;
			 for(int i =0; i<gameChar.getHearing().size();i++){
				 g2d.drawString(gameChar.getHearing().get(i),275,height);
				 height+=25;
			 }	
		 }
		 repaint();
		 revalidate();
	 }

	@Override
	  public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	public Fighter getHero() {
		return hero;
	}

	public void setHero(Fighter hero) {
		this.hero = hero;
	}

	public Fighter getVillain() {
		return villain;
	}

	public void setVillain(Fighter villain) {
		this.villain = villain;
	}

	public Environment getEnv(){
		return currentEnv;
	}
	
	public ArrayList<String> getNotif(){
		return notifications;
	}

	public ArrayList<String> getFightString() {
		return fightString;
	}

	public Image getMonster() {
		return monster;
	}

	public void setMonster(Image monster) {
		this.monster = monster;
	}

	public Environment getCurrentEnv() {
		return currentEnv;
	}

	/*
	 * sets the current environment of the game, also checks though for when scenes will begin by the name
	 * of the new environments, also checks how to position character
	 */
	public void setCurrentEnv(Environment currentEnv) {
		this.currentEnv = currentEnv;
		setEnvBackground(currentEnv.getImageLocation());
		if(Character.exitedHouse){
			this.charX = 365;
			this.charY = 490;
			this.posture = "res\\characters\\"+gameChar.getGender()+"\\front stand.png";
			Character.exitedHouse = false;
		}else{
			if(gameChar.getCurrentEnv().equals("clearing")&&this.currentEnv.getName().equals("forest")){
				this.charX = 1120;
				this.charY = 630;
			}else{
				if(gameChar.getCurrentEnv().equals("forest")&&this.currentEnv.getName().equals("outsideHouseRPG")){
					this.charX = 1120;
					this.charY = 630;
				}else{
					if(currentEnv.getName().equals("forestEnd")){
						//stay
					}else{
						this.charX = currentEnv.getCharStartX();
						this.charY = currentEnv.getCharStartY();
//						}
					}
				}
			}
		}
		gameChar.setCurrentEnv(currentEnv.getName()); //sets environment name to be saved later
		
		/*
		 * checks to begin the scene for meeting the old lady
		 * removes key adapter and disables the minimenu button so that user cannot interact with 
		 * game, then adds them back once the scene is done
		 * 
		 * sets notifying to true to start displaying notifications, which is clears first and then adds to the arraylist,
		 * using timer to let the user read the dialogue
		 * 
		 * sets to different backgrounds for theatrics
		 */
		if(this.currentEnv.getName().equals("forestBeg")){
			posture = "res\\characters\\"+gameChar.getGender()+"\\rightside stand.png";
			time = new Timer(1000, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					time.stop();
					getRPGRunner().removeKeyAdapter();
					RpgRunner.butt.setEnabled(false);
					RpgRunner.butt.setVisible(false);
					posture = "";
					setEnvBackground("res\\environments\\forestBeg\\blackedOut.png");
					repaint();
					time = new Timer(2500, new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							time.stop();
							RpgRunner.butt.setVisible(true);
							posture = "res\\characters\\"+gameChar.getGender()+"\\rightside stand.png";
							setEnvBackground("res\\environments\\forestBeg\\appears.png");
							repaint();
							time = new Timer(1500, new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									time.stop();
									gameChar.setIsHearing(true);
									gameChar.getHearing().clear();
									gameChar.getHearing().add("Dear child...I heard from a birdie that you're after");
									gameChar.getHearing().add("someone in these woods.");
									repaint();
									time = new Timer(4000, new ActionListener(){
										public void actionPerformed(ActionEvent evt){
											time.stop();
											gameChar.setIsHearing(true);
											gameChar.getHearing().clear();
											gameChar.getHearing().add("If you could, would you mind delivering something");
											gameChar.getHearing().add("for me? Just a poor child's snack.");
											repaint();
											time = new Timer(4000, new ActionListener(){
												public void actionPerformed(ActionEvent evt){
													time.stop();
													gameChar.setIsHearing(false);
													repaint();
													time = new Timer(1000, new ActionListener(){
														public void actionPerformed(ActionEvent evt){
															time.stop();
															gameChar.setIsHearing(true);
															gameChar.getHearing().clear();
															gameChar.getHearing().add("Great, just walk on over to me and take it, I'm tired");
															gameChar.getHearing().add("of walking.");
															getRPGRunner().addKeyAdapter();
															repaint();
															time = new Timer(4000, new ActionListener(){
																public void actionPerformed(ActionEvent evt){
																	time.stop();
																	gameChar.setIsHearing(false);
																	repaint();
																	time = new Timer(1000, new ActionListener(){
																		public void actionPerformed(ActionEvent evt){
																			time.stop();
																			gameChar.setIsHearing(true);
																			gameChar.getHearing().clear();
																			gameChar.getHearing().add("Don't worry, I won't bite.");
																			repaint();
																			time = new Timer(1000, new ActionListener(){
																				public void actionPerformed(ActionEvent evt){
																					time.stop();
																					gameChar.setIsHearing(false);
																					repaint();
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
		 * sets for when player first enters the clearing, where they are eventually stopped
		 * so depending on where they were facing the game sets them in a stationary image and 
		 * removes user interaction, then sets inFight to true to begin fight mode
		 */
		if(this.currentEnv.getName().equals("clearing")){
			time = new Timer(1300, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					time.stop();
					monster = new ImageIcon("res\\characters\\monster\\leftside stand.png").getImage();
					if(posture.equals("res\\characters\\"+gameChar.getGender()+"\\rightside walking.gif")){
						posture = "res\\characters\\"+gameChar.getGender()+"\\rightside stand.png";				
					}
					if(posture.equals("res\\characters\\"+gameChar.getGender()+"\\leftside walking.gif")){
						posture = "res\\characters\\"+gameChar.getGender()+"\\leftside stand.png";				
					}
					if(posture.equals("res\\characters\\"+gameChar.getGender()+"\\back walking.gif")){
						posture = "res\\characters\\"+gameChar.getGender()+"\\back stand.png";				
					}
					if(posture.equals("res\\characters\\"+gameChar.getGender()+"\\front walking.gif")){
						posture = "res\\characters\\"+gameChar.getGender()+"\\front stand.png";				
					}
					repaint();
					getRPGRunner().removeKeyAdapter();
					RpgRunner.butt.setEnabled(false);
					time = new Timer(2800, new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							time.stop();
							RpgRunner.butt.setEnabled(true);
							inFight = true;
							repaint();
						}
					});

					time.start();
				}
			   });
			  time.start();
		}
		
		/*
		 * checks to begin the scene for ending game
		 * removes key adapter and disables the minimenu button so that user cannot interact with 
		 * game
		 * 
		 * sets notifying to true to start displaying notifications, which is clears first and then adds to the arraylist,
		 * using timer to let the user read the dialogue
		 * 
		 * deletes the rpg save file and disposes of the frame before restarting with the life sim main menu
		 */
		if(this.currentEnv.getName().equals("end")){
			setEnvBackground("res\\environments\\end\\end.gif");
			time = new Timer(2800, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					time.stop();
					gameChar.setIsHearing(true);
					gameChar.getHearing().clear();
					gameChar.getHearing().add("Congratulations, you've reached the end of the demo.");
					repaint();
					time = new Timer(3000, new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							time.stop();
							gameChar.setIsHearing(true);
							gameChar.getHearing().clear();
							gameChar.getHearing().add("Will the old man ever be saved? Who knows.");
							repaint();
							time = new Timer(3000, new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									time.stop();
									gameChar.setIsHearing(true);
									gameChar.getHearing().clear();
									gameChar.getHearing().add("That's to be continued...");
									repaint();
									time = new Timer(2000, new ActionListener(){
										public void actionPerformed(ActionEvent evt){
											time.stop();
											gameChar.setIsHearing(false);
											currentEnv.setName("ended");
											repaint();
											User.deleteRPG();
											rpgRunner.disposeFrame();
											new GameIntro();
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

	}
	
	public Character getChar(){
		return gameChar;
	}
	
	public void setChar(Character gameChar){
		this.gameChar = gameChar;
	}
	
	//sets location of the character
	public void setCharLocation(int x, int y){
		this.charX = x;
		this.charY = y;	
	}
	
	//sets location of the monster
	public void setMonsterLocation(int x, int y){
		this.monX = x;
		this.monY = y;
	}
	
	public RpgRunner getRPGRunner() {
		return rpgRunner;
	}

	public void setRPGRunner(RpgRunner rpgRunner) {
		this.rpgRunner = rpgRunner;
	}
	
	public boolean isHearing() {
		return hearing;
	}

	public void setHearing(boolean hearing) {
		this.hearing = hearing;
	}
	
	public static boolean isFighting(){
		return inFight;
	}
	
	public static void setFighting(boolean fight){
		inFight = fight;
	}
	
	public static boolean isMadeUI(){
		return madeUI;
	}
	
	public static void setMadeUI(boolean UI){
		madeUI = UI;
	}
	  
	public String getPosture(){
		return posture;
	}
	
	public void setPosture(String posture){
		this.posture = posture;
	}
	
	public Image getEnvBackground(){
		return background;
	}
	
	public void setEnvBackground(String background){
		this.background = new ImageIcon(background).getImage();
		repaint();
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
 
 public class RpgRunner implements KeyListener, ActionListener{
	
	private static JFrame frame = new JFrame();
	
	private static GraphicRunRPG draw;
	
	private static User player;

	//buttons for the mini menu of the game
	private static JButton returnMenu;
	private static JButton inventory;
	private static JButton save;
	private static JButton saveExit;
	private static JButton cancel;
	public static JButton butt = new JButton(new ImageIcon("res\\backgrounds\\buttons\\gameDotRPG.png"));
	private static boolean buttonMade = false; //so that the button is not recreated
	private static boolean inInventory = false; //to check whether to display use or give button
	
	//battle UI buttons
	private static JButton attack = new JButton(new ImageIcon("res\\battle\\attack.png"));
	private static JButton talk = new JButton(new ImageIcon("res\\battle\\talk.png"));
	private static JButton give = new JButton(new ImageIcon("res\\battle\\give.png"));
	private static JButton use = new JButton(new ImageIcon("res\\battle\\use.png"));
	
	//backbutton to go back to minimenu
	private static JButton backButton = new JButton(new ImageIcon("res\\backgrounds\\buttons\\backButtonRPG.png"));
	
	/*
	 * the fighters of the battle
	 */
	private static Fighter mainChar;
	private static Fighter monster;
	/*
	 * whether to display give or use button in inventory
	 */
	private static boolean giving = false;
	private static boolean using = false;
	
	/*
	 * so that the actionlisteners are not recreated
	 */
	private static boolean inventoryMade = false;
	private static boolean selectionMade = false;
	private static boolean fightMade = false;
	
	/*
	 * inventory item buttons
	 */
	private static JButton sword = new JButton(new ImageIcon("res\\inventory\\sword.png"));
	private static JButton shield = new JButton(new ImageIcon("res\\inventory\\shield.png"));
	private static JButton potion1 = new JButton(new ImageIcon("res\\inventory\\potion.png"));
	private static JButton potion2 = new JButton(new ImageIcon("res\\inventory\\potion.png"));
	private static JButton potion3 = new JButton(new ImageIcon("res\\inventory\\potion.png"));
	private static JButton snickers = new JButton(new ImageIcon("res\\inventory\\snickers.png"));
	
	//identifiers
	public static boolean gifted = false; //that potions, weapons, and snickers have been given to player
	private static boolean eatenBread= false; //whether bread was eaten
	private static boolean swordEquipped = false; //whether sword was equipped
	private static boolean shieldEquipped = false; //whether shield was equipped
	private static boolean potionSelected1 = false; //whether potion1 selected
	private static boolean potionSelected2 = false; //whether potion2 selected
	private static boolean potionSelected3 = false; //whether potion3 selected
	private static boolean swordSelected= false; //whether sword selected
	private static boolean shieldSelected= false; //whether shield selected
	private static boolean swordGave= false; //whether sword gave away
	private static boolean shieldGave= false; //whether shield gave away
	private static boolean drank1 = false; //whether potion1 drank
	private static boolean drank2 = false;//whether potion2 drank
	private static boolean drank3 = false; //whether potion3 drank
	private static boolean selectedBread = false; //whether bread selected
	
	private static Timer time;
	
	public RpgRunner(){
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
        draw = new GraphicRunRPG(player.getChar(), new Environment(new File("res\\environments\\"+player.getChar().getCurrentEnv()+"\\envInfo.txt"), new File("res\\environments\\"+player.getChar().getCurrentEnv()+"\\objects.txt")));
		draw.setRPGRunner(this);
		
		/*
		 * creates and sets up the two fighters
		 */
		mainChar = new Fighter(100,10,10, draw, false);
		monster = new Fighter(200,20,25, draw, true); 
		
		mainChar.setOpponent(monster);
		monster.setOpponent(mainChar);
		
		draw.setHero(mainChar);
		draw.setVillain(monster);
		
		//in case game is starting again immediately after player dies in battle
		reset();
        
    }
	
	public void removeKeyAdapter(){
		frame.removeKeyListener(keyAdapter);
	}
	
	public void disposeFrame(){
		frame.removeKeyListener(keyAdapter);
		frame.dispose();
	}
	
	public void addKeyAdapter(){
		frame.addKeyListener(keyAdapter);
	}
	
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

		if(!buttonMade){
			//makes the minimenu button
			makeButton();
			JLabel space1 = new JLabel();
			space1.setPreferredSize(new Dimension(1130,36));
			frame.getContentPane().add(butt);
			buttonMade = true;
			frame.getContentPane().add(space1);
		}

		frame.getContentPane().revalidate();  //refreshes frame and checks for removed objects
		frame.getContentPane().repaint();	
		
	}
	
	public static void runGame(User player1){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				
				//sets the user
				player = player1;

				//calls the constructor for the LifeSimRunner class, which sets some things up
				RpgRunner rr = new RpgRunner();
				
				//sets things up for lifesimrunner to run
				backToGame();
				
			}
		});	
	}
	
	/*
	 * creates and displays the fightUI
	 */
	public static void fightUI(){
		frame.getContentPane().removeAll();
		frame.setContentPane(draw);
		frame.getContentPane().setLayout(new FlowLayout());
        
        attack.setPreferredSize(new Dimension(250,70));
        talk.setPreferredSize(new Dimension(250,70));
        give.setPreferredSize(new Dimension(250,70));
        use.setPreferredSize(new Dimension(250,70));
        
        attack.setDisabledIcon(new ImageIcon("res\\battle\\attack.png"));
		talk.setDisabledIcon(new ImageIcon("res\\battle\\talk.png"));
		use.setDisabledIcon(new ImageIcon("res\\battle\\use.png"));
		give.setDisabledIcon(new ImageIcon("res\\battle\\give.png"));
        
      if(!fightMade){
    	fightMade = true;
    	
        attack.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		disableFightButtons();
        		mainChar.attack(20);
        	}
        });
        talk.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		disableFightButtons();
        		mainChar.talk();
        	}
        });
        give.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		disableFightButtons();
        		giving = true;
        		inventory();
        	}
        });
        use.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		disableFightButtons();
        		using = true;
        		inventory();
        	}
        });
	}

        	JLabel space1 = new JLabel();
	        space1.setPreferredSize(new Dimension(1200,546));
	        JLabel space2 = new JLabel();
	        space2.setPreferredSize(new Dimension(535,70));
	        JLabel space3 = new JLabel();
	        space3.setPreferredSize(new Dimension(535,70));

	        frame.getContentPane().add(space1);
	        frame.getContentPane().add(space2);
	        frame.getContentPane().add(attack);
	        frame.getContentPane().add(talk);
	        frame.getContentPane().add(space3);
	        frame.getContentPane().add(give);
	        frame.getContentPane().add(use);
        
		frame.getContentPane().revalidate();  //refreshes frame and checks for removed objects
		frame.getContentPane().repaint();	
	}
	
	/*
	 * creates and displays inventory, certain items are only added if identifiers allow it
	 */
	public static void inventory(){
		frame.getContentPane().removeAll();
		frame.setContentPane(new JLabel(new ImageIcon("res\\backgrounds\\rpgBg.png")));
        frame.setLayout(new FlowLayout());
        
        int width = 500;
        int height = 80;
        
        backButton.setEnabled(true);
		sword.setEnabled(true);
		shield.setEnabled(true);
		potion1.setEnabled(true);
		potion2.setEnabled(true);
		potion3.setEnabled(true);
		snickers.setEnabled(true);
		
		if(!inventoryMade){
			inventoryMade = true;
			backButton.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\backButtonRPG.png"));
	        backButton.setPreferredSize(new Dimension(48,40));
	        backButton.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		if(GraphicRunRPG.isFighting()){
	        			giving = false;
	        			using = false;
	        			fightUI();
	        			attack.setEnabled(true);
	        			give.setEnabled(true);
	        			use.setEnabled(true);
	        			talk.setEnabled(true);
	        		}else{
	            		menu();
	        		}
	        	}
	        });
	        
	        sword.setDisabledIcon(new ImageIcon("res\\inventory\\sword.png"));
	        sword.setPreferredSize(new Dimension(width,height));
	        sword.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		swordSelected = true;
	        		itemSelected();
	        	}
	        });
	        
	        shield.setDisabledIcon(new ImageIcon("res\\inventory\\shield.png"));
	        shield.setPreferredSize(new Dimension(width,height));
	        shield.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		shieldSelected = true;
	        		itemSelected();
	        	}
	        });
	        
	        potion1.setDisabledIcon(new ImageIcon("res\\inventory\\potion.png"));
	        potion1.setPreferredSize(new Dimension(width,height));
	        potion1.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		drank1 = true;
	        		potionSelected1 = true;
	        		itemSelected();
	        	}
	        });
	        
	        potion2.setDisabledIcon(new ImageIcon("res\\inventory\\potion.png"));
	        potion2.setPreferredSize(new Dimension(width,height));
	        potion2.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		drank2 = true;
	        		potionSelected2 = true;
	        		itemSelected();
	        	}
	        });
	        
	        potion3.setDisabledIcon(new ImageIcon("res\\inventory\\potion.png"));
	        potion3.setPreferredSize(new Dimension(width,height));
	        potion3.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		drank3 = true;
	        		potionSelected3 = true;
	        		itemSelected();
	        	}
	        });
	        
	        snickers.setDisabledIcon(new ImageIcon("res\\inventory\\snickers.png"));
	        snickers.setPreferredSize(new Dimension(width,height));
	        snickers.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		selectedBread = true;
	        		itemSelected();
	        	}
	        });
		}
		
		/*
		 * spacing labels
		 */
        JLabel space1 = new JLabel();
        space1.setPreferredSize(new Dimension(1198,30));
        JLabel space2 = new JLabel();
        space2.setPreferredSize(new Dimension(1000,40));
        JLabel space3 = new JLabel();
        space3.setPreferredSize(new Dimension(1200,25));
        JLabel space4 = new JLabel();
        space4.setPreferredSize(new Dimension(width,height));
        
        frame.getContentPane().add(space1);
        frame.getContentPane().add(backButton);
        
        frame.getContentPane().add(space2);
        frame.getContentPane().add(space3);
        
        if(gifted&&!swordEquipped&&!swordGave){
        	frame.getContentPane().add(sword);
        }
        if(gifted&&!drank1){
            frame.getContentPane().add(potion1);
        }
        if(gifted&&!shieldEquipped&&!shieldGave){
            frame.getContentPane().add(shield);
    	}
        if(gifted&&!drank2){
            frame.getContentPane().add(potion2);
        }
        if(gifted&&!drank3){
            frame.getContentPane().add(potion3);
        }
        if(gifted&&!eatenBread){
            frame.getContentPane().add(snickers);
        }
        frame.getContentPane().add(space4);

        frame.getContentPane().revalidate();  //refreshes frame and checks for removed objects
		frame.getContentPane().repaint();
	}
	
	/*
	 * the confirmation or cancellation buttons when the user selects an item
	 */
	private static void itemSelected(){
		JButton cancelInventory = new JButton(new ImageIcon("res\\inventory\\cancel.png"));
		JButton giveInventory = new JButton(new ImageIcon("res\\inventory\\give.png"));
		JButton useInventory = new JButton(new ImageIcon("res\\inventory\\use.png"));

		giveInventory.setPreferredSize(new Dimension(134,66));
		useInventory.setPreferredSize(new Dimension(134,66));
		cancelInventory.setPreferredSize(new Dimension(166,66));
		
		//can't select another item when this opens
		backButton.setEnabled(false);
		sword.setEnabled(false);
		shield.setEnabled(false);
		potion1.setEnabled(false);
		potion2.setEnabled(false);
		potion3.setEnabled(false);
		snickers.setEnabled(false);
		
		/*
		 * this is where giving or using certain items will affect the character or the monster
		 */
		if(!selectionMade){
			giveInventory.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		giving = false;
	        		if(potionSelected1||potionSelected2||potionSelected3){
	        			fightUI();
	        			draw.getFightString().clear();
	        			draw.getFightString().add("Uh, your opponent doesn't complain. Their health increases.");
	        			potionSelected1 = false;
	        			potionSelected2 = false;
	        			potionSelected3 = false;
	        			mainChar.getOpponent().affect(30, 0, 0);
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	        					mainChar.getOpponent().attack(0);
	        				}
	        			});
	        			
	        			time.start();
	        		}
	        		if(swordSelected){
	        			fightUI();
	        			draw.getFightString().clear();
	        			draw.getFightString().add("Great thinking, your opponent's strength increases.");
	        			swordSelected = false;
	        			swordGave = true;
	        			mainChar.getOpponent().affect(0, 0, 10);
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	        					mainChar.getOpponent().attack(0);
	        				}
	        			});
	        			
	        			time.start();
	        		}
	        		if(shieldSelected){
	        			fightUI();
	        			draw.getFightString().clear();
	        			draw.getFightString().add("Great thinking, your opponent's defense increases.");
	        			shieldSelected = false;
	        			shieldGave = true;
	        			mainChar.getOpponent().affect(0, 10, 0);
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	        					mainChar.getOpponent().attack(0);
	        				}
	        			});
	        			time.start();
	        		}
	        		if(selectedBread){
	        			fightUI();
	        			draw.getFightString().clear();
	        			draw.getFightString().add("Your opponent stops for a second.");
	        			draw.setMonster(new ImageIcon("res\\characters\\monster\\stopped.png").getImage());
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	                			draw.getFightString().clear();
	                			draw.getFightString().add("Your opponent starts to attack, but stops...");
	    	        			draw.setMonster(new ImageIcon("res\\characters\\monster\\fighting.gif").getImage());
	    	        			time = new Timer(1000, new ActionListener(){
                    				public void actionPerformed(ActionEvent evt){
                    					time.stop();
        	    	        			draw.setMonster(new ImageIcon("res\\characters\\monster\\stopped.png").getImage());
        	    	        			time = new Timer(3000, new ActionListener(){
        	                				public void actionPerformed(ActionEvent evt){
        	                					time.stop();
        	                        			draw.getFightString().clear();
                	    	        			draw.setMonster(new ImageIcon("res\\characters\\monster\\twitch.gif").getImage());
        	                        			draw.getFightString().add("It starts to writhe and shriek.");
        	                        			time = new Timer(3000, new ActionListener(){
        	                        				public void actionPerformed(ActionEvent evt){
        	                        					time.stop();
        	                                			mainChar.getOpponent().affect(-500, -500, -500);
        	                                			frame.getContentPane().removeAll();
        	                                			backToGame();
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
	        	}
	        });
			
			useInventory.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){        		
	        		using = false;
	        		
	        		if(potionSelected1||potionSelected2||potionSelected3){
	        			mainChar.affect(30, 0, 0);
	        			fightUI();
	        			draw.getFightString().clear();
	        			draw.getFightString().add("Your health increased.");
	        			potionSelected1 = false;
	        			potionSelected2 = false;
	        			potionSelected3 = false;
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	        					mainChar.getOpponent().attack(0);     				
	        				}
	        			});
	        			
	        			time.start();
	        		}
	        		if(swordSelected){
	        			mainChar.affect(0, 0, 10);
	        			fightUI();
	        			draw.getFightString().clear();
	        			swordEquipped = true;
	        			draw.getFightString().add("Weapon equipped, your strength increased!");
	        			swordSelected = false;
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	        					mainChar.getOpponent().attack(0);
	        				}
	        			});
	        			
	        			time.start();
	        		}
	        		if(shieldSelected){
	        			mainChar.affect(0, 10, 0);
	        			fightUI();
	        			draw.getFightString().clear();
	        			shieldEquipped = true;
	        			draw.getFightString().add("Armor equipped, your defense increased!");
	        			shieldSelected = false;
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	        					mainChar.getOpponent().attack(0);
	        				}
	        			});
	        			
	        			time.start();
	        		}
	        		if(selectedBread){
	        			eatenBread = true;
	        			mainChar.affect(20, 0, 0);
	        			fightUI();
	        			draw.getFightString().clear();
	        			draw.getFightString().add("The snickers's tasty, but it feels like");
	        			draw.getFightString().add("you weren't supposed to eat that.");
	        			selectedBread = false;
	        			time = new Timer(3000, new ActionListener(){
	        				public void actionPerformed(ActionEvent evt){
	        					time.stop();
	        					mainChar.getOpponent().attack(0);
	        				}
	        			});
	        			
	        			time.start();
	        		}
	        	}
	        });
			
			//cancelling resets some identifiers and goes back to inventory
			cancelInventory.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent ae){
	        		
	        		if(swordSelected){
	        			swordSelected = false;
	        		}
	        		if(shieldSelected){
	        			shieldSelected = false;
	        		}
	        		if(potionSelected1){
	        			potionSelected1 = false;
	        			drank1 = false;
	        		}
	        		if(potionSelected2){
	        			potionSelected2 = false;
	        			drank2 = false;
	        		}
	        		if(potionSelected3){
	        			potionSelected3 = false;
	        			drank3 = false;
	        		}
	        		if(selectedBread){
	        			selectedBread = false;
	        		}
	        		inventory();
	        	}
	        });
		}
		
		/*
		 * spacing labels
		 */
		JLabel space1 = new JLabel();
		JLabel space2 = new JLabel();
		space1.setPreferredSize(new Dimension(1198,150));
		space2.setPreferredSize(new Dimension(10,50)); //space btwn buttons\
		
		frame.getContentPane().add(space1);
		if(giving){
			frame.getContentPane().add(giveInventory);
		}
		if(using||inInventory){
			frame.getContentPane().add(useInventory);
		}
		frame.getContentPane().add(space2);
		frame.getContentPane().add(cancelInventory);
		
		frame.getContentPane().revalidate();  //refreshes frame and checks for removed objects
		frame.getContentPane().repaint();
	}
	
	/*
	 * for when user has just made a move and is waiting for their turn again
	 */
	public static void disableFightButtons(){
		attack.setEnabled(false);
		talk.setEnabled(false);
		use.setEnabled(false);
		give.setEnabled(false);
	}
	
	/*
	 * for when user gets back their turn
	 */
	public static void enableFightButtons(){
		attack.setEnabled(true);
		talk.setEnabled(true);
		use.setEnabled(true);
		give.setEnabled(true);
	}
	
	/*
	 * for if user replays right after losing battle
	 */
	public static void reset(){
		eatenBread= false;
		swordEquipped = false;
		shieldEquipped = false;
		potionSelected1 = false;
		potionSelected2 = false;
		potionSelected3 = false;
		swordSelected= false;
		shieldSelected= false;
		swordGave= false;
		shieldGave= false;
		drank1 = false;
		drank2 = false;
		drank3 = false;
		selectedBread = false;
	}
	
	//makes minimenu button
	public static void makeButton(){
		butt.setDisabledIcon(new ImageIcon("res\\backgrounds\\buttons\\gameDotRPG.png"));
		butt.setPreferredSize(new Dimension(30,27));
		butt.setVisible(true);
		butt.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent ae){
		    	menu();
		    }
		});

		}
		
	/*
	 * creates and displays all things within the minimenu
	 */
	public static void menu(){
		frame.setContentPane(new JLabel(new ImageIcon("res\\backgrounds\\rpgBg.png")));
        frame.setLayout(new FlowLayout());
        
        returnMenu = new JButton(new ImageIcon("res\\backgrounds\\buttons\\mainMenuRPG.png"));
        inventory = new JButton(new ImageIcon("res\\backgrounds\\buttons\\inventory.png"));
        save= new JButton(new ImageIcon("res\\backgrounds\\buttons\\saveButtRPG.png"));
        saveExit = new JButton(new ImageIcon("res\\backgrounds\\buttons\\saveAndExitRPG.png"));
        cancel = new JButton(new ImageIcon("res\\backgrounds\\buttons\\cancelButtRPG.png"));
        
        returnMenu.setPreferredSize(new Dimension(300,80));
        save.setPreferredSize(new Dimension(300,80));
        saveExit.setPreferredSize(new Dimension(300,80));
        cancel.setPreferredSize(new Dimension(300,80));
        inventory.setPreferredSize(new Dimension(300,80));
        
        returnMenu.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		frame.removeKeyListener(keyAdapter);
        		frame.dispose();
        		new GameIntroRPG();
        		buttonMade = false;
        	}
        });
        inventory.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		inInventory = true;
        		inventory();
        		buttonMade = false;
        	}
        });
        save.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		player.saveRPG();
		    	JOptionPane.showMessageDialog(frame, "Saved.");
		    	buttonMade = true;
        	}
        });
        saveExit.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		player.saveRPG();
        		System.exit(0);
        		buttonMade = false;
        	}
        });
        cancel.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		buttonMade = true;
        		inInventory = false;
        		backToGame();
        	}
        });
        
        /*
         * spacing labels
         */
        JLabel space1 = new JLabel();
        space1.setPreferredSize(new Dimension(1200,100));
        JLabel space2 = new JLabel();
        space2.setPreferredSize(new Dimension(1200,25));
        JLabel space3 = new JLabel();
        space3.setPreferredSize(new Dimension(1200,25));
        JLabel space4 = new JLabel();
        space4.setPreferredSize(new Dimension(1200,25));
        JLabel space5 = new JLabel();
        space5.setPreferredSize(new Dimension(1200,25));
        
        frame.getContentPane().add(space1);
        frame.getContentPane().add(returnMenu);
        frame.getContentPane().add(space2);
        frame.getContentPane().add(inventory);
        frame.getContentPane().add(space3);
        frame.getContentPane().add(save);
        frame.getContentPane().add(space4);
        frame.getContentPane().add(saveExit);
        frame.getContentPane().add(space5);
        frame.getContentPane().add(cancel);
        
        frame.getContentPane().revalidate();  //refreshes frame and checks for removed objects
		frame.getContentPane().repaint();
        
	}
	
}
