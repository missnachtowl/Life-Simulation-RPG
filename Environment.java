/*
 * Monica Tam
 * Mr. Slattery
 * Java Capstone p. 4
 * 3/12/17
 */
package seniorProj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Timer;

public class Environment {
	
	private String name; //name of the environment
	
	/*
	 * the outer bounds of the environment
	 */
	private int boundLeftX;
	private int boundRightX;
	private int boundUpY;
	private int boundDownY;
	
	/*
	 * the bounds of the right exit
	 */
	private int rExitX1;
	private int rExitX2;
	private int rExitY1;
	private int rExitY2;
	
	/*
	 * the bounds of the left exit
	 */
	private int lExitX1;
	private int lExitX2;
	private int lExitY1;
	private int lExitY2;
	
	/*
	 * the bounds of the upper exit
	 */
	private int uExitX1;
	private int uExitX2;
	private int uExitY1;
	private int uExitY2;
	
	/*
	 * the starting coordinates of the user when they enter the environment
	 */
	private int charStartX;
	private int charStartY;
	
	/*
	 * whether or not there is a right, left, or upper exit
	 */
	private boolean right;
	private boolean left;
	private boolean up;
	
	/*
	 * where each exit will go
	 */
	private String rightName;
	private String leftName;
	private String upName;
	
	/*
	 * the location of the image for the environment
	 */
	private String imageLocation;
	
	/*
	 * list of the objects within the environment
	 */
	private ArrayList<GameObject> furniture = new ArrayList<>();
	
	public Environment(File envFile, File objectsFile){
		
		Scanner fileScan;
		Scanner objectScan;
		try {
			fileScan = new Scanner(envFile);
			
			/*
			 * while the file has another line
			 * stores the value of that line, but substrings since part of it
			 * is not data but labeling what the data is
			 */
			while(fileScan.hasNextLine()){
				name = fileScan.nextLine().substring(6);
				boundLeftX = Integer.parseInt(fileScan.nextLine().substring(12));
				boundRightX = Integer.parseInt(fileScan.nextLine().substring(13));
				boundUpY = Integer.parseInt(fileScan.nextLine().substring(10));
				boundDownY = Integer.parseInt(fileScan.nextLine().substring(12));
				right = Boolean.parseBoolean(fileScan.nextLine().substring(12)); 
				rightName = fileScan.nextLine().substring(12);
				rExitX1 = Integer.parseInt(fileScan.nextLine().substring(9));
				rExitX2 = Integer.parseInt(fileScan.nextLine().substring(9));
				rExitY1 = Integer.parseInt(fileScan.nextLine().substring(9));
				rExitY2 = Integer.parseInt(fileScan.nextLine().substring(9));
				left = Boolean.parseBoolean(fileScan.nextLine().substring(11)); 
				leftName = fileScan.nextLine().substring(11);
				lExitX1 = Integer.parseInt(fileScan.nextLine().substring(9));
				lExitX2 = Integer.parseInt(fileScan.nextLine().substring(9));
				lExitY1 = Integer.parseInt(fileScan.nextLine().substring(9));
				lExitY2 = Integer.parseInt(fileScan.nextLine().substring(9));
				up = Boolean.parseBoolean(fileScan.nextLine().substring(9)); 
				upName = fileScan.nextLine().substring(9);
				uExitX1 = Integer.parseInt(fileScan.nextLine().substring(9));
				uExitX2 = Integer.parseInt(fileScan.nextLine().substring(9));
				uExitY1 = Integer.parseInt(fileScan.nextLine().substring(9));
				uExitY2 = Integer.parseInt(fileScan.nextLine().substring(9));
				charStartX = Integer.parseInt(fileScan.nextLine().substring(13));
				charStartY = Integer.parseInt(fileScan.nextLine().substring(13));
			}
			
			/*
			 * a 0 is stored if there are no objects in the environment
			 * so if there is no zero in the first line, read all data from file
			 */
			fileScan = new Scanner(objectsFile);
			objectScan = new Scanner(objectsFile);

			String firstLine = fileScan.nextLine();
			
			if(!firstLine.equals("0")){
				while(objectScan.hasNextLine()){
					furniture.add(new GameObject(objectScan.nextLine().substring(6), //name
							Integer.parseInt(objectScan.nextLine().substring(4)), //xBounds1
							Integer.parseInt(objectScan.nextLine().substring(4)), //xBounds2
							Integer.parseInt(objectScan.nextLine().substring(4)), //yBounds1
							Integer.parseInt(objectScan.nextLine().substring(4)), //yBounds2
							Integer.parseInt(objectScan.nextLine().substring(8)), //hunger
							Integer.parseInt(objectScan.nextLine().substring(9)), //bladder
							Integer.parseInt(objectScan.nextLine().substring(9)), //hygiene
							Integer.parseInt(objectScan.nextLine().substring(7)), //sleep
							Integer.parseInt(objectScan.nextLine().substring(8)))); //social
				}
			}else{
				//no objects
			}
				
			this.imageLocation = "res\\environments\\"+ this.name +"\\"+this.name+".png";
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Environment file issue");
		}
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getImageLocation(){
		return this.imageLocation;
	}
	
	public int getCharStartX(){
		return this.charStartX;
	}
	
	public int getCharStartY(){
		return this.charStartY;
	}
	
	/*for the Life Simulation part of the game
	 * checks for if user collides with objects, 
	 * and then if the objects are able to affect the user's character,
	 * the character is affected
	 * 
	 * also makes it so that the user must be facing that object to be affected by it
	 */
	public void checkObjects(GraphicRun g){
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseRight(g)){
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\rightside stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}
			
			if(furniture.get(i).isCloseLeft(g)){
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\leftside stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}

			if(furniture.get(i).isCloseUp(g)){
				System.out.println(g.getPosture());
				System.out.println(g.getChar().getGender());
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\back stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}
	
			if(furniture.get(i).isCloseDown(g)){
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\front stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}
		}
	}
	
	/*
	 * first checks whether when the user goes right, if they are going into the right exit or
	 * if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move,
	 * if the user goes past the boundaries of the exit, the environment of the user will be changed
	 * to where the right exit of the current environment leads
	 */
	public void checkPositionRight(GraphicRun g){
		
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(right){
			if((g.getCharX()==boundRightX||(g.getCharX()+5)>boundRightX)&&g.getCharY()<rExitY1){
				willCollide = true;
			}
		}else{
			if((g.getCharX()==boundRightX||(g.getCharX()+5)>boundRightX)){
				willCollide = true;
			}
		}
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseRight(g)){
				willCollide = true;
			}
		}

		if(!willCollide){
			if(right){
				if(g.getCharX()>=rExitX2&&g.getCharY()>rExitY1){
					/*
					 * if the user exits the house, the user is ready to enter the old man leaving scene
					 */
					if(rightName.equals("outsideHouse")&&g.getEnv().getName().equals("insideHouse")){
						Character.exitedHouseScene = true;
					}
					g.setCurrentEnv(new Environment(new File("res\\environments\\"+rightName+"\\envInfo.txt"),new File("res\\environments\\"+rightName+"\\objects.txt")));
					g.getChar().setCurrentEnv(rightName);
				}else{
					g.incrementCharX(5);
				}
			}else{
				g.incrementCharX(5);
			}
	
		}else{
			//stay still
		}
		
	}
	
	/*
	 * first checks whether when the user goes left, if they are going into the left exit or
	 * if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move,
	 * if the user goes past the boundaries of the exit, the environment of the user will be changed
	 * to where the left exit of the current environment leads
	 */
	public void checkPositionLeft(GraphicRun g){
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(left){
			if((g.getCharX()==boundLeftX||(g.getCharX()-5)<boundLeftX)&&g.getCharY()<lExitY1){
				willCollide = true;
			}
		}else{
			if((g.getCharX()==boundLeftX||(g.getCharX()-5)<boundLeftX)){
				willCollide = true;
			}
		}
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseLeft(g)){
				willCollide = true;
			}
		}

		if(!willCollide){
			if(left){
				if(g.getCharX()<=lExitX2&&g.getCharY()>lExitY1){ //change for leftside
					g.setCurrentEnv(new Environment(new File("res\\environments\\"+leftName+"\\envInfo.txt"),new File("res\\environments\\"+leftName+"\\objects.txt")));			  
					g.getChar().setCurrentEnv(leftName);
				}else{
					g.incrementCharX(-5);
				}
			}else{
				g.incrementCharX(-5);
			}
		}else{
			//stay still
		}
		
	}

	/*
	 * first checks whether when the user goes up, if they are going into the upper exit or
	 * if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move,
	 * if the user goes past the boundaries of the exit, the environment of the user will be changed
	 * to where the upper exit of the current environment leads
	 */
	public void checkPositionUp(GraphicRun g){
		
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(right){
			if((g.getCharY()==boundUpY||(g.getCharY()-5)<boundUpY)&&g.getCharX()<rExitX1){
				willCollide = true;
			}
			
			if((g.getCharY()==rExitY1||(g.getCharY()-5)<rExitY1)&&g.getCharX()>=rExitX1+1){ 
				willCollide = true;
			}
		}
		
		if(left){
			if((g.getCharY()==boundUpY||(g.getCharY()-5)<boundUpY)&&g.getCharX()>lExitX2){
				willCollide = true;
			}
			
			if((g.getCharY()==lExitY1||(g.getCharY()-5)<lExitY1)&&g.getCharX()<=lExitX2+1){ 
				willCollide = true;
			}
		}		
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseUp(g)){
				willCollide = true;
			}
		}
		
		if(!willCollide){
			if(up){
				if((g.getCharX()>=uExitX1&&g.getCharX()<=uExitX2)&&g.getCharY()<=uExitY1){ //Y1 is the bottom dimension
					if(upName.equals("end")){
						//does nothing, the game is at an end
					}else{
						g.setCurrentEnv(new Environment(new File("res\\environments\\"+upName+"\\envInfo.txt"),new File("res\\environments\\"+upName+"\\objects.txt")));			  
						g.getChar().setCurrentEnv(upName);
						/*
						 * exitedHouse is enabled true to prepare for positioning the character when they leave the house
						 * exitedHouseScene is set to false so that the scene only begins when the user exits the house,
						 * not when the user is still within the house
						 */
						if(upName.equals("insideHouse")||upName.equals("insideHouseOpened")||upName.equals("insideHouseTutorial")){
							g.setPosture("res\\characters\\"+g.getChar().getGender()+"\\leftside stand.png");
							Character.exitedHouse = true;
							Character.exitedHouseScene = false;
						}
					}
				}else{
					g.incrementCharY(-5);
				}
			}else{
				g.incrementCharY(-5);
			}
		}else{
			//stay still
		}
		
	}

	/*
	 * first checks whether when the user goes down, if they are going down in any of the exit boundaries,
	 * then if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move
	 */
	public void checkPositionDown(GraphicRun g){
		
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(right){
			if((g.getCharY()==boundDownY||(g.getCharY()+5)>boundDownY)&&g.getCharX()<rExitX1){
				willCollide = true;
			}
			
			if((g.getCharY()==rExitY2||(g.getCharY()+5)>rExitY2)&&g.getCharX()>=rExitX1+1){ 
				willCollide = true;
			}
		}
		
		if(left){
			if((g.getCharY()==boundDownY||(g.getCharY()+5)>boundDownY)&&g.getCharX()>lExitX2){
				willCollide = true;
			}
			
			if((g.getCharY()==lExitY2||(g.getCharY()+5)>lExitY2)&&g.getCharX()<=lExitX2+1){ 
				willCollide = true;
			}
		}
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseDown(g)){
				willCollide = true;
			}
		}
			
		if(!willCollide){
			g.incrementCharY(5);
		}else{
			//stay still
		}
		
	}
	
	//---------------------------------methods for RPG part of Game
	
	/*for the rpg part of the game
	 * checks for if user collides with objects, 
	 * and then if the objects are able to affect the user's character,
	 * the character is affected
	 * 
	 * also makes it so that the user must be facing that object to be affected by it
	 */
	public void checkObjects(GraphicRunRPG g){
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseRight(g)){
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\rightside stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}
			
			if(furniture.get(i).isCloseLeft(g)){
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\leftside stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}

			if(furniture.get(i).isCloseUp(g)){
				System.out.println(g.getPosture());
				System.out.println(g.getChar().getGender());
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\back stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}
	
			if(furniture.get(i).isCloseDown(g)){
				if(g.getPosture().equals("res\\characters\\"+ g.getChar().getGender()+"\\front stand.png")){
					furniture.get(i).affect(g.getChar(),g);
				}
			}
	
		}
		
	}
	
	/*
	 * first checks whether when the user goes right, if they are going into the right exit or
	 * if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move,
	 * if the user goes past the boundaries of the exit, the environment of the user will be changed
	 * to where the right exit of the current environment leads
	 */
	public void checkPositionRight(GraphicRunRPG g){
		
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(right){
			if((g.getCharX()==boundRightX||(g.getCharX()+5)>boundRightX)&&g.getCharY()<rExitY1){
				willCollide = true;
			}
		}else{
			if((g.getCharX()==boundRightX||(g.getCharX()+5)>boundRightX)){
				willCollide = true;
			}
		}
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseRight(g)){
				willCollide = true;
			}
		}

		if(!willCollide){
			if(right){
				if(g.getCharX()>=rExitX2&&g.getCharY()>rExitY1){
					g.setCurrentEnv(new Environment(new File("res\\environments\\"+rightName+"\\envInfo.txt"),new File("res\\environments\\"+rightName+"\\objects.txt")));
					g.getChar().setCurrentEnv(rightName);
					System.out.println(g.getCurrentEnv().getName());
				}else{
					g.incrementCharX(5);
				}
			}else{
				g.incrementCharX(5);
			}
	
		}else{
			//stay still
		}
		
	}
	
	/*
	 * first checks whether when the user goes left, if they are going into the left exit or
	 * if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move,
	 * if the user goes past the boundaries of the exit, the environment of the user will be changed
	 * to where the left exit of the current environment leads
	 */
	public void checkPositionLeft(GraphicRunRPG g){
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(left){
			if((g.getCharX()==boundLeftX||(g.getCharX()-5)<boundLeftX)&&g.getCharY()<lExitY1){
				willCollide = true;
			}
		}else{
			if((g.getCharX()==boundLeftX||(g.getCharX()-5)<boundLeftX)){
				willCollide = true;
			}
		}
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseLeft(g)){
				willCollide = true;
			}
		}

		if(!willCollide){
			if(left){
				if(g.getCharX()<=lExitX2&&g.getCharY()>lExitY1){ 
					g.setCurrentEnv(new Environment(new File("res\\environments\\"+leftName+"\\envInfo.txt"),new File("res\\environments\\"+leftName+"\\objects.txt")));			  
					g.getChar().setCurrentEnv(leftName);
				}else{
					g.incrementCharX(-5);
				}
			}else{
				g.incrementCharX(-5);
			}
		}else{
			//stay still
		}
		
	}

	/*
	 * first checks whether when the user goes up, if they are going into the upper exit or
	 * if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move,
	 * if the user goes past the boundaries of the exit, the environment of the user will be changed
	 * to where the upper exit of the current environment leads
	 */
	public void checkPositionUp(GraphicRunRPG g){
		
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(right){
			if((g.getCharY()==boundUpY||(g.getCharY()-5)<boundUpY)&&g.getCharX()<rExitX1){
				willCollide = true;
			}
			
			if((g.getCharY()==rExitY1||(g.getCharY()-5)<rExitY1)&&g.getCharX()>=rExitX1+1){ 
				willCollide = true;
			}
		}
		
		if(left){
			if((g.getCharY()==boundUpY||(g.getCharY()-5)<boundUpY)&&g.getCharX()>lExitX2){
				willCollide = true;
			}
			
			if((g.getCharY()==lExitY1||(g.getCharY()-5)<lExitY1)&&g.getCharX()<=lExitX2+1){ 
				willCollide = true;
			}
		}	
		
		if(!left&&!right){
			if(g.getCharY()==boundUpY||(g.getCharY()-5)<boundUpY){
				willCollide = true;
			}
		}	
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseUp(g)){
				willCollide = true;
			}
		}
		
		if(!willCollide){
			if(up){
				if((g.getCharX()>=uExitX1&&g.getCharX()<=uExitX2)&&g.getCharY()<=uExitY1){ //Y1 is the bottom dimension
					g.setCurrentEnv(new Environment(new File("res\\environments\\"+upName+"\\envInfo.txt"),new File("res\\environments\\"+upName+"\\objects.txt")));			 
					g.getChar().setCurrentEnv(upName);
					/*
					 * so that the user exits house in correct position
					 */
					if(upName.equals("insideHouseRPG")){
						g.setPosture("res\\characters\\"+g.getChar().getGender()+"\\leftside stand.png");
						Character.exitedHouse = true;
					}
				}else{
					g.incrementCharY(-5);
				}
			}else{
				g.incrementCharY(-5);
			}
		}else{
			//stay still
		}
		
	}

	/*
	 * first checks whether when the user goes down, if they are going down in any of the exit boundaries,
	 * then if they will collide with the environment's boundaries
	 * then checks if they collide with any furniture
	 * 
	 * if the boolean variable is turned true, then the character's coordinates will not move
	 */
	public void checkPositionDown(GraphicRunRPG g){
		
		boolean willCollide = false;
		
		/*
		 * no go if user's coordinate equals the boundary or user's next step will go past the boundary
		 */
		if(right){
			if((g.getCharY()==boundDownY||(g.getCharY()+5)>boundDownY)&&g.getCharX()<rExitX1){
				willCollide = true;
			}
			
			if((g.getCharY()==rExitY2||(g.getCharY()+5)>rExitY2)&&g.getCharX()>=rExitX1+1){ 
				willCollide = true;
			}
		}
		
		if(left){
			if((g.getCharY()==boundDownY||(g.getCharY()+5)>boundDownY)&&g.getCharX()>lExitX2){
				willCollide = true;
			}
			
			if((g.getCharY()==lExitY2||(g.getCharY()+5)>lExitY2)&&g.getCharX()<=lExitX2+1){ 
				willCollide = true;
			}
		}
		
		if(!left&&!right){
			if(g.getCharY()==boundDownY||(g.getCharY()+5)>boundDownY){
				willCollide = true;
			}
		}	
			
		for(int i =0; i<furniture.size();i++){
			if(furniture.get(i).isCloseDown(g)){
				willCollide = true;
			}
		}
			
		if(!willCollide){
			g.incrementCharY(5);
		}else{
			//stay still
		}
		
	}
	
}
