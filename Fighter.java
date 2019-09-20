/*
 * Monica Tam
 * Mr. Slattery
 * Java Capstone p. 4
 * 3/12/17
 */
package seniorProj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Fighter {
	
	/*
	 * current stats
	 */
	private int health;
	private int defense;
	private int strength;
	
	/*
	 * the maximum the stats can go
	 */
	private int fullHealth;
	private int fullDefense;
	private int fullStrength;
	
	/*
	 * the stats that are first given
	 */
	private int startHealth;
	private int startDefense;
	private int startStrength;
	
	private GraphicRunRPG grRPG;
	
	//so that whatever the main fighter does can affect their opponent
	private Fighter opponent;
	
	//so the same methods can be used but diff parts
	private boolean isMonster;
	
	//the moves of the monster
	private ArrayList<Move> moves = new ArrayList<>();
	
	//so to display the damage taken
	private int damageTaken;
	
	private Timer time;
	
	public Fighter(int h, int d, int s, GraphicRunRPG grRPG, boolean isMonster){
		this.startHealth = h;
		this.startDefense=d;
		this.startStrength = s;
		this.fullHealth = h;
		this.fullDefense=d;
		this.fullStrength = s;
		this.health = h;
		this.defense =d;
		this.strength = s;
		this.grRPG = grRPG;
		this.isMonster = isMonster;
	}
	
	public int getStartHealth() {
		return startHealth;
	}
	
	public void setStartHealth(int startHealth) {
		this.startHealth = startHealth;
	}

	public int getStartDefense() {
		return startDefense;
	}

	public void setStartDefense(int startDefense) {
		this.startDefense = startDefense;
	}

	public int getStartStrength() {
		return startStrength;
	}

	public void setStartStrength(int startStrength) {
		this.startStrength = startStrength;
	}

	/*
	 * sets everything back to the beginning, in case user replays game immediately after losing
	 */
	public void reset(){
		health = startHealth;
		defense = startDefense;
		strength = startStrength;
		opponent.setHealth(opponent.getStartHealth());
		opponent.setStrength(opponent.getStartStrength());
		opponent.setDefense(opponent.getStartDefense());
		RpgRunner.reset();
	}
	
	/*
	 * clears string to display current words
	 * decreases opponent's strength and defense by 10
	 * waits 3 seconds then has the opponent counter
	 */
	public void talk(){
		grRPG.getFightString().clear();
		grRPG.getFightString().add("You talk to it, it seems to listen.");
		opponent.affect(0, -10, -10);
		time = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				time.stop();
				opponent.attack(0);
			}
		});
		
		time.start();
	}
	
	public void attack(int power){
		if(!isMonster){
			/*
			 * if not monster, attacks the monster, and after 3 seconds monster counters
			 */
			grRPG.getFightString().clear();
			grRPG.getFightString().add("You attacked! The opponent's health");
			grRPG.getFightString().add("dropped by " + ((power+strength)-opponent.getDefense())+ ".");
			System.out.println("affect monster:"+ -((power+strength)-opponent.getDefense()));
			opponent.affect(-((power+strength)-opponent.getDefense()), 0, 0);
			time = new Timer(3000, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					time.stop();
					opponent.attack(0);
				}
			});
			
			time.start();
		}else{
			/*
			 * sets monster's moves, randomizes which they choose, then 'raffles' whether the attack will fail or not
			 * with the higher the power of the move the higher the chance of it failing
			 */
			
			int randomMove = 0;
			int raffle = 0;
			
			moves.clear();
			moves.add(new Move(10, "Throw Shade"));
			moves.add(new Move(30, "Black Shock"));
			moves.add(new Move(50, "Hell's Fire"));
			moves.add(new Move(100, "Devil's Farewell"));
			Random r = new Random();
			randomMove = r.nextInt(4);	
			power = moves.get(randomMove).getPower();
			boolean failed = false;
			
			if(power==10){
				raffle = r.nextInt(100);
				if(raffle<=90){
					opponent.affect(-((power+strength)-opponent.getDefense()), 0, 0);
				}else{
					failed = true;
				}
			}
			if(power>10&&power<=35){
				raffle = r.nextInt(100);
				if(raffle<=70){
					opponent.affect(-((power+strength)-opponent.getDefense()), 0, 0);
				}else{
					failed = true;
				}
			}
			if(power>35&&power<=50){
				raffle = r.nextInt(100);
				if(raffle<=50){
					opponent.affect(-((power+strength)-opponent.getDefense()), 0, 0);
				}else{
					failed = true;
				}
			}
			if(power>50&&power<=100){
				raffle = r.nextInt(100);
				if(raffle<=5){
					opponent.affect(-((power+strength)-opponent.getDefense()), 0, 0);
				}else{
					failed = true;
				}
			}
			grRPG.getFightString().clear();
			grRPG.getFightString().add("Your opponent is mad, it attacks with ");
			grRPG.getFightString().add(moves.get(randomMove).getName() + ".");
			damageTaken = ((moves.get(randomMove).getPower()+strength)-opponent.getDefense());
			
			/*
			 * if it fails or doesnt, displays different messages
			 * then in both enables the fight buttons which were disabled while the monster
			 * was attacking
			 */
			if(!failed){
				time = new Timer(1000, new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						time.stop();
						grRPG.getFightString().clear();
						grRPG.getFightString().add("Your health drops by "+damageTaken+ ".");
						time = new Timer(1500, new ActionListener(){
							public void actionPerformed(ActionEvent evt){
								time.stop();
								grRPG.getFightString().clear();
								grRPG.getFightString().add("What will you do now?");
								System.out.println("affectplayer:"+ damageTaken);
								RpgRunner.enableFightButtons();
							}
						});
						
						time.start();
					}
				});
				
				time.start();
			}else{
				if(failed){
					time = new Timer(1000, new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							time.stop();
							grRPG.getFightString().clear();
							grRPG.getFightString().add("The move fails.");
							time = new Timer(1500, new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									time.stop();
									grRPG.getFightString().clear();
									grRPG.getFightString().add("What will you do now?");
									RpgRunner.enableFightButtons();
								}
							});
							
							time.start();
						}
					});
					
					time.start();
				}
			}
		}
	}
	
	/*
	 * if stats go below min, they're set back to the min
	 * 
	 * for health, if it goes past health, it gets set to max value
	 * 
	 * for defense and strength, if it is increased, the max value is increased
	 */
	public void affect(int healthA, int defenseA, int strengthA){
		health +=healthA;
		if(health<0){
			health=0;
		}
		if(health>fullHealth){
			health=fullHealth;
		}
		defense +=defenseA;
		if(defense<0){
			defense=0;
		}
		if(defense>fullDefense){
			fullDefense = defense;
		}
		strength +=strengthA;
		if(strength<0){
			strength =0;
		}
		if(strength>fullStrength){
			fullStrength =strength;
		}
		
		/*
		 * if monster is defeated and user isn't
		 * timers are to give the dialogue enough time to be read
		 */
		if(isMonster&&health==0&&opponent.getHealth()!=0){
			/*
			 * env is set back to the clearing scene, character is drawn to frame again, inFight variable is set to false
			 * monster is not drawn to the frame, character is set to the right location, frame is repainted, stalls 
			 * for a 1.2 seconds
			 */
			grRPG.setEnvBackground(grRPG.getCurrentEnv().getImageLocation()); 
			grRPG.setPosture("res\\characters\\"+grRPG.getChar().getGender()+"\\rightside stand");
			grRPG.setFighting(false);
			grRPG.setMonster(new ImageIcon("").getImage());
			grRPG.setCurrentEnv(new Environment(new File("res\\environments\\clearingEnd\\envInfo.txt"),new File("res\\environments\\clearingEnd\\objects.txt")));
			grRPG.setPosture("res\\characters\\"+grRPG.getChar().getGender()+"\\rightside stand.png");
			grRPG.setCharLocation(590, 275);
			grRPG.getChar().setPosture("rightside");
			grRPG.repaint();
			time = new Timer(1200, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					time.stop();
					grRPG.getChar().setIsHearing(true); //makes the textbox appear in GraphicRunRPG class
					grRPG.getChar().getHearing().clear();
					grRPG.getChar().getHearing().add("...Uugha, what happened? ...Oh...");
					time = new Timer(5000, new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							time.stop();
							grRPG.getChar().getHearing().clear();
							grRPG.getChar().getHearing().add("Um...thanks for saving me...I...uh...");
							time = new Timer(3000, new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									time.stop();
									grRPG.getChar().getHearing().clear();
									grRPG.getChar().getHearing().add("...I'm not me when I'm hungry. You're a good kid.");
									time = new Timer(3000, new ActionListener(){
										public void actionPerformed(ActionEvent evt){
											time.stop();
											grRPG.getChar().getHearing().clear();
											grRPG.getChar().getHearing().add("If you're looking for the old man, I think he got");
											grRPG.getChar().getHearing().add("dragged up in there.");
											grRPG.setEnvBackground("res\\environments\\clearingEnd\\clearingEnd2.png");
											grRPG.setPosture("res\\characters\\"+grRPG.getChar().getGender()+"\\back stand.png");
											grRPG.repaint();
											time = new Timer(4500, new ActionListener(){
												public void actionPerformed(ActionEvent evt){
													time.stop();
													grRPG.getChar().getHearing().clear();
													grRPG.getChar().setIsHearing(false);
													grRPG.getRPGRunner().addKeyAdapter(); //ends scene, enables user to move
													RpgRunner.butt.setEnabled(true);
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
		 * timers are for dialogue to be read
		 * if user dies, a black screen is given, dialogue is given
		 * then frame is disposed and rpg intro is initialized to bring back to menu
		 */
		if(!isMonster&&health==0){
			time = new Timer(3000, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					time.stop();
					grRPG.removeAll();
					GraphicRunRPG.setFighting(false);
					grRPG.setMonster(new ImageIcon("").getImage());
					grRPG.setEnvBackground("res\\battle\\died.png");
					time = new Timer(1500, new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							time.stop();
							grRPG.getChar().setIsHearing(true);
							grRPG.getChar().getHearing().clear();
							grRPG.getChar().getHearing().add("Oops, you died...");
							grRPG.repaint();
							time = new Timer(2000, new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									time.stop();
									grRPG.getChar().getHearing().clear();
									grRPG.getChar().getHearing().add("Try harder next time!");
									time = new Timer(2500, new ActionListener(){
										public void actionPerformed(ActionEvent evt){
											time.stop();
											grRPG.getChar().setIsHearing(false);
											grRPG.getChar().getHearing().clear();
											grRPG.getRPGRunner().disposeFrame();
									    	GameIntroRPG intro = new GameIntroRPG();
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
	public int getHealth() {
		return health;
	}
	
	//displays the stats graphically, since the length of the bars are 303 pixels long
	public int getBarHealth() {
		return ((health*303)/fullHealth);
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getDefense() {
		return defense;
	}

	//displays the stats graphically, since the length of the bars are 303 pixels long
	public int getBarDefense() {
		return ((defense*303)/fullDefense);
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	//displays the stats graphically, since the length of the bars are 303 pixels long
	public int getBarStrength() {
		return ((strength*303)/fullStrength);
	}

	public int getStrength() {
		return strength;
	}
	
	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getFullHealth() {
		return fullHealth;
	}

	public void setFullHealth(int fullHealth) {
		this.fullHealth = fullHealth;
	}

	public int getFullDefense() {
		return fullDefense;
	}

	public void setFullDefense(int fullDefense) {
		this.fullDefense = fullDefense;
	}

	public int getFullStrength() {
		return fullStrength;
	}

	public void setFullStrength(int fullStrength) {
		this.fullStrength = fullStrength;
	}

	public Fighter getOpponent() {
		return opponent;
	}

	public void setOpponent(Fighter opponent) {
		this.opponent = opponent;
	}

	public boolean isMonster() {
		return isMonster;
	}

	public void setMonster(boolean isMonster) {
		this.isMonster = isMonster;
	}

}
