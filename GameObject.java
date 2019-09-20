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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Timer;

public class GameObject {
	//name of object
	private String name;
	
	//bounds of the object
	private int xBoundsR;
	private int xBoundsL;
	private int yBoundsTop;
	private int yBoundsBottom;
	
	//any affects the object has
	private int hungerAffect;
	private int bladderAffect;
	private int hygieneAffect;
	private int sleepAffect;
	private int socialAffect;
	
	//a timer for if it contains animations
	private Timer time;
	
	//dialogue for when npcs are spoken to, they generate random responses from files
	private ArrayList<String> dialogue = new ArrayList<>();
	
	public GameObject(String name, int x1, int x2, int y1, int y2, int hunger, int bladder, int hygiene, int sleep, int social){
		
		this.name = name;
		this.xBoundsR = x1;
		this.xBoundsL = x2;
		this.yBoundsTop = y1;
		this.yBoundsBottom = y2;
		
		this.hungerAffect = hunger;
		this.bladderAffect = bladder;
		this.hygieneAffect = hygiene;
		this.sleepAffect = sleep;
		this.socialAffect = social;
		
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getHungerAffect() {
		return hungerAffect;
	}

	public void setHungerAffect(int hungerAffect) {
		this.hungerAffect = hungerAffect;
	}

	public int getBladderAffect() {
		return bladderAffect;
	}

	public void setBladderAffect(int bladderAffect) {
		this.bladderAffect = bladderAffect;
	}

	public int getHygieneAffect() {
		return hygieneAffect;
	}

	public void setHygieneAffect(int hygieneAffect) {
		this.hygieneAffect = hygieneAffect;
	}

	public int getSleepAffect() {
		return sleepAffect;
	}

	public void setSleepAffect(int sleepAffect) {
		this.sleepAffect = sleepAffect;
	}

	public int getSocialAffect() {
		return socialAffect;
	}

	public void setSocialAffect(int socialAffect) {
		this.socialAffect = socialAffect;
	}
	
	public void affect(Character ch,GraphicRun g){		

		/*if object has social effects, and user is not stinky or isn't already listening to someone else
		 * then uses name of the object to find the txt file for it and randomizes the different responses
		 * separated by a 0
		 */
		if(socialAffect!=0){
			if(!g.isHearing()&&!ch.isStinky()){
				try {
					File lines = new File("res\\dialogue\\"+name+".txt");
					Scanner fileScan = new Scanner(lines);
					
					while(fileScan.hasNextLine()){
						dialogue.add(fileScan.nextLine());
					}
					
					int range = -1;
					for(int i =0;i<dialogue.size();i++){
						if(dialogue.get(i).equals("0")){
							range++;
						}
					}
					
					Random rand = new Random(); 
					int rVal = rand.nextInt(range);
					
					int count = -1;
					for(int i =0;i<dialogue.size();i++){
						if(dialogue.get(i).equals("0")){
							count++;

						}else{
							if(count==rVal){
								ch.getHearing().add(dialogue.get(i));
							}
						}
					}
					
					/*
					 * set isHearing to display textbox and dialoge is cleared
					 */
					ch.setIsHearing(true);
					g.setHearing(true);
					dialogue.clear();

					ch.setSocial(socialAffect);
					//shows user didn't just start the game
					ch.setSocialUsed(true);
					Character.social1=true; 
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		//displays what the sign says
		if(name.equals("sign")){
			if(!g.isHearing()){
				try {
					File lines = new File("res\\dialogue\\sign.txt");
					Scanner fileScan = new Scanner(lines);
					
					while(fileScan.hasNextLine()){
						dialogue.add(fileScan.nextLine());
					}
					
					ch.setIsHearing(true);
					for(int i =0;i<dialogue.size();i++){
						if(!dialogue.get(i).equals("0")){
							ch.getHearing().add(dialogue.get(i));
						}
					}
					g.setHearing(true);
					dialogue.clear();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		/*
		 * sets an animation and uses timer to keep the user from interacting with the game during the animation
		 */
		if(name.equals("fridge")){
			g.setEnvBackground("res\\environments\\insideHouse\\eattime"+g.getGameChar().getGender()+".png");
			g.setPosture("none");
			LifeSimRunner.butt.setEnabled(false);
			g.getLSRunner().removeKeyAdapter();
			time = new Timer(2000, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   g.setEnvBackground(g.getEnv().getImageLocation());
					   g.setPosture("res\\characters\\"+g.getGameChar().getGender()+"\\front stand.png");
					   g.getLSRunner().addKeyAdapter();
					   ch.setHungerUsed(true);
					   ch.setHunger(hungerAffect);
					   //shows user didn't just start the game
					   Character.hunger1=true;
					   LifeSimRunner.butt.setEnabled(true);
				   }
			   });

			   time.start();			
		}
		/*
		 * sets an animation and uses timer to keep the user from interacting with the game during the animation
		 */
		if(name.equals("shower")){
			g.setEnvBackground("res\\environments\\insideHouse\\showertime.png");
			g.setPosture("none");
			LifeSimRunner.butt.setEnabled(false);
			g.getLSRunner().removeKeyAdapter();
			time = new Timer(5000, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   g.setEnvBackground(g.getEnv().getImageLocation());
					   g.setPosture("res\\characters\\"+g.getGameChar().getGender()+"\\front stand.png");
					   g.getLSRunner().addKeyAdapter();
					   ch.setHygieneUsed(true);
					   ch.setHygiene(hygieneAffect);
					   Character.hygiene1=true;
					   LifeSimRunner.butt.setEnabled(true);
				   }
			   });

			   time.start();			
		}
		/*
		 * sets an animation and uses timer to keep the user from interacting with the game during the animation
		 */
		if(name.equals("bed")){
			g.setEnvBackground("res\\environments\\insideHouse\\sleeptime"+g.getGameChar().getGender()+".png");
			g.setPosture("none");
			LifeSimRunner.butt.setEnabled(false);
			g.getLSRunner().removeKeyAdapter();
			//time is paused
			int minutes = Integer.parseInt(ch.getMinute());
			ch.setIsSleeping(true);
			time = new Timer(10000, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   //time is increased by 8 hours
					   ch.setHour(Integer.parseInt(ch.getHour())+8);
					   ch.setMinute(minutes);
					   ch.setIsSleeping(false);
					   g.setEnvBackground(g.getEnv().getImageLocation());
					   g.setPosture("res\\characters\\"+g.getGameChar().getGender()+"\\rightside stand.png");
					   g.getLSRunner().addKeyAdapter();
					   ch.setSleepUsed(true);
					   ch.setSleep(sleepAffect);
					   Character.sleep1=true;
					   LifeSimRunner.butt.setEnabled(true);
				   }
			   });
			   time.start();
		}
		/*
		 * sets an animation and uses timer to keep the user from interacting with the game during the animation
		 */
		if(name.equals("toiletfront")){
			g.setEnvBackground("res\\environments\\insideHouse\\peetime"+g.getGameChar().getGender()+".png");
			g.setPosture("none");
			LifeSimRunner.butt.setEnabled(false);
			g.getLSRunner().removeKeyAdapter();
			time = new Timer(3000, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   g.setEnvBackground(g.getEnv().getImageLocation());
					   g.setPosture("res\\characters\\"+g.getGameChar().getGender()+"\\rightside stand.png");
					   g.getLSRunner().addKeyAdapter();
					   ch.setBladderUsed(true);
					   ch.setBladder(bladderAffect);
					   //hygiene takes an extra loss of 10 pts to encourage washing hands with sink
					   ch.setHygiene(-10);
					   Character.bladder1=true;
					   LifeSimRunner.butt.setEnabled(true);
				   }
			   });
			   time.start();
		}
		/*
		 * sets an animation and uses timer to keep the user from interacting with the game during the animation
		 */
		if(name.equals("sink")){
			g.setEnvBackground("res\\environments\\insideHouse\\washinghands"+g.getGameChar().getGender()+".png");
			g.setPosture("none");
			LifeSimRunner.butt.setEnabled(false);
			g.getLSRunner().removeKeyAdapter();
			time = new Timer(3000, new ActionListener(){
				   public void actionPerformed(ActionEvent evt){
					   time.stop();
					   g.setEnvBackground(g.getEnv().getImageLocation());
					   g.setPosture("res\\characters\\"+g.getGameChar().getGender()+"\\front stand.png");
					   g.getLSRunner().addKeyAdapter();
					   ch.setHygiene(hygieneAffect);
					   LifeSimRunner.butt.setEnabled(true);
				   }
			   });
			   time.start();
		}
		//----------
	}
	
	/*
	 * checks that the user is not at the boundary of the object or will go through the item for any move they make
	 */
	public boolean isCloseRight(GraphicRun g){
		boolean noGo = false; 
		if((g.getCharX()==xBoundsR||((g.getCharX()+5)>xBoundsR&&(g.getCharX()+5)<xBoundsL))&&(g.getCharY()>yBoundsTop&&g.getCharY()<yBoundsBottom)){
			noGo = true;
		}
		return noGo;
	}
	
	public boolean isCloseLeft(GraphicRun g){
		
		boolean noGo = false; 
		if((g.getCharX()==xBoundsL||((g.getCharX()-5)<xBoundsL&&(g.getCharX()-5)>xBoundsR))&&(g.getCharY()>yBoundsTop&&g.getCharY()<yBoundsBottom)){
			noGo = true;
		}
		return noGo;
	}
	
	public boolean isCloseUp(GraphicRun g){
		boolean noGo = false; 
		if((g.getCharY()==yBoundsBottom||((g.getCharY()-5)<yBoundsBottom&&(g.getCharY()-5)>yBoundsTop))&&(g.getCharX()>xBoundsR&&g.getCharX()<xBoundsL)){
			noGo = true;
		}
		return noGo;
	}
	
	public boolean isCloseDown(GraphicRun g){
		boolean noGo = false; 
		if((g.getCharY()==yBoundsTop||((g.getCharY()+5)>yBoundsTop&&(g.getCharY()+5)<yBoundsBottom))&&(g.getCharX()>xBoundsR&&g.getCharX()<xBoundsL)){
			noGo = true;
		}
		return noGo;
	}
	
	//-------------------------------------the methods for the RPG part of the game
	
	public void affect(Character ch,GraphicRunRPG g){		
		//will have a text file for each character
		if(socialAffect!=0){
			if(!g.isHearing()){
				try {
					File lines = new File("res\\dialogue\\"+name+".txt");
					Scanner fileScan = new Scanner(lines);
					
					while(fileScan.hasNextLine()){
						dialogue.add(fileScan.nextLine());
					}
					
					int range = -1;
					for(int i =0;i<dialogue.size();i++){
						if(dialogue.get(i).equals("0")){
							range++;
						}
					}
					System.out.println("range "+ range);
					Random rand = new Random(); 
					int rVal = rand.nextInt(range);
					
					System.out.println("rVal "+ rVal);
					int count = -1;
					for(int i =0;i<dialogue.size();i++){
						if(dialogue.get(i).equals("0")){
							count++;
							System.out.println("count "+ count);

						}else{
							if(count==rVal){
								ch.getHearing().add(dialogue.get(i));
							}
						}
					}
					
					ch.setIsHearing(true);
					g.setHearing(true);
					dialogue.clear();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		/*
		 * when user interacts with the old lady, displays the scene and sets the gifted boolean to true so that the user
		 * now has potions, weapons, and a snickers in their inventory
		 */
		if(name.equals("old lady")){
			g.getRPGRunner().removeKeyAdapter();
			g.setPosture("res\\characters\\"+ch.getGender()+"\\rightside stand.png");
			ch.setIsHearing(true);
			ch.getHearing().clear();
			ch.getHearing().add("Hm, what a good smelling kid you are. Here's some");
			ch.getHearing().add("extra treats. You'll find them in your inventory.");
			time = new Timer(5000, new ActionListener(){
				public void actionPerformed(ActionEvent evt){
					time.stop();
					g.setEnvBackground("res\\environments\\forestBeg\\going.png");
					ch.getHearing().clear();
					ch.getHearing().add("See ya, kid, I've done my job. Good luck.");
					time = new Timer(3000, new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							time.stop();
							g.setCurrentEnv(new Environment(new File("res\\environments\\forestEnd\\envInfo.txt"), new File("res\\environments\\forestEnd\\objects.txt")));
							g.repaint();
							ch.getHearing().clear();
							ch.getHearing().add("...It's nice to give out quests.");
							time = new Timer(2500, new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									time.stop();
									ch.getHearing().clear();
									ch.getHearing().add("When am I going to get to eat someone again though...");
									time = new Timer(3000, new ActionListener(){
										public void actionPerformed(ActionEvent evt){
											time.stop();
											ch.setIsHearing(false);
											g.getRPGRunner().addKeyAdapter();
											RpgRunner.butt.setEnabled(true);
											RpgRunner.gifted = true;
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
		if(name.equals("sign")){
			if(!g.isHearing()){
				try {
					File lines = new File("res\\dialogue\\sign.txt");
					Scanner fileScan = new Scanner(lines);
					
					while(fileScan.hasNextLine()){
						dialogue.add(fileScan.nextLine());
					}
					
					ch.setIsHearing(true);
					for(int i =0;i<dialogue.size();i++){
						if(!dialogue.get(i).equals("0")){
							ch.getHearing().add(dialogue.get(i));
						}
					}
					g.setHearing(true);
					dialogue.clear();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isCloseRight(GraphicRunRPG g){
		boolean noGo = false; 
		if((g.getCharX()==xBoundsR||((g.getCharX()+5)>xBoundsR&&(g.getCharX()+5)<xBoundsL))&&(g.getCharY()>yBoundsTop&&g.getCharY()<yBoundsBottom)){
			noGo = true;
		}
		return noGo;
	}
	
	public boolean isCloseLeft(GraphicRunRPG g){
		
		boolean noGo = false; 
		if((g.getCharX()==xBoundsL||((g.getCharX()-5)<xBoundsL&&(g.getCharX()-5)>xBoundsR))&&(g.getCharY()>yBoundsTop&&g.getCharY()<yBoundsBottom)){
			noGo = true;
		}
		return noGo;
	}
	
	public boolean isCloseUp(GraphicRunRPG g){
		boolean noGo = false; 
		if((g.getCharY()==yBoundsBottom||((g.getCharY()-5)<yBoundsBottom&&(g.getCharY()-5)>yBoundsTop))&&(g.getCharX()>xBoundsR&&g.getCharX()<xBoundsL)){
			noGo = true;
		}
		return noGo;
	}
	
	public boolean isCloseDown(GraphicRunRPG g){
		boolean noGo = false; 
		if((g.getCharY()==yBoundsTop||((g.getCharY()+5)>yBoundsTop&&(g.getCharY()+5)<yBoundsBottom))&&(g.getCharX()>xBoundsR&&g.getCharX()<xBoundsL)){
			noGo = true;
		}
		return noGo;
	}

}
