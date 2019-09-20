/*
 * Monica Tam
 * Mr. Slattery
 * Java Capstone p. 4
 * 3/12/17
 */
package seniorProj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class User {
	
	private Character userChar;
	private Date dateSaved;
	private static int saveNum;
	private FileWriter fileWriter;
	private static File saveFile;
	private static File rpgSaveFile = new File("res\\saved files\\rpgSave.txt");
	private static File saveFiles = new File("res\\saved files\\saves made.txt");
	private static ArrayList<Integer> saves = new ArrayList<>();
	private static boolean inRPG = false;
	
	public User(String name, String gender, int saveNum){ //checks savesMade file to decide which num to give
		userChar = new Character(name, gender);
		this.saveNum = saveNum;
		userChar.setSaveNum(this.saveNum);
		saveFile = new File("res\\saved files\\save" + saveNum + ".txt");
		int dupeCounter = 0;
		if(saves.size()>0){
			for(int i =0; i<saves.size();i++){
				if(saves.get(i).equals(saveNum)){
					dupeCounter++;
				}
			}
			if(dupeCounter==0){
				saves.add(saveNum);
			}
		}else{
			saves.add(saveNum);
			System.out.println("added");
		}
		populateCharacter();
	}
	
	public User(){ //for RPG
		userChar = new Character();
		popCharRPG();
	}

	public void popCharRPG(){
		try {
			Scanner charScan1 = new Scanner(rpgSaveFile);
			
			if(charScan1.hasNextLine()){
				while(charScan1.hasNextLine()){
					charScan1.nextLine();
					userChar.setName(charScan1.nextLine().substring(6));
					userChar.setGender(charScan1.nextLine().substring(8));
					userChar.setCurrentEnv(charScan1.nextLine().substring(13));
					userChar.setCharX(Integer.parseInt(charScan1.nextLine().substring(13)));
					userChar.setCharY(Integer.parseInt(charScan1.nextLine().substring(13)));
					userChar.setPosture(charScan1.nextLine().substring(9));
					Character.exitedHouse=Boolean.parseBoolean(charScan1.nextLine().substring(13));
					RpgRunner.gifted = Boolean.parseBoolean(charScan1.nextLine().substring(8));
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void populateCharacter(){
		try {
			Scanner charScan1 = new Scanner(saveFile);
			
			if(charScan1.hasNextLine()){
				while(charScan1.hasNextLine()){
					charScan1.nextLine();
					userChar.setName(charScan1.nextLine().substring(6));
					userChar.setGender(charScan1.nextLine().substring(8));
					userChar.setCurrentEnv(charScan1.nextLine().substring(13));
					userChar.setCharX(Integer.parseInt(charScan1.nextLine().substring(13)));
					userChar.setCharY(Integer.parseInt(charScan1.nextLine().substring(13)));
					userChar.setPosture(charScan1.nextLine().substring(9));
					userChar.setHunger(Integer.parseInt(charScan1.nextLine().substring(8)));
					userChar.setBladder(Integer.parseInt(charScan1.nextLine().substring(9)));
					userChar.setHygiene(Integer.parseInt(charScan1.nextLine().substring(9)));
					userChar.setSleep(Integer.parseInt(charScan1.nextLine().substring(7)));
					userChar.setSocial(Integer.parseInt(charScan1.nextLine().substring(8)));
					userChar.setDay(Integer.parseInt(charScan1.nextLine().substring(5)));
					userChar.setHour(Integer.parseInt(charScan1.nextLine().substring(6)));
					userChar.setMinute(Integer.parseInt(charScan1.nextLine().substring(8)));
					userChar.setAmOrPm(charScan1.nextLine().substring(10));
					Character.exitedHouse = Boolean.parseBoolean(charScan1.nextLine().substring(13));
					Character.exitedHouseScene = Boolean.parseBoolean(charScan1.nextLine().substring(18));
					Character.tutorialFinished = Boolean.parseBoolean(charScan1.nextLine().substring(18));
					Character.hunger1 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.hunger2 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.hunger3 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.hunger4 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.hunger5 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.bladder1 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.bladder2 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.bladder3 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.bladder4 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.bladder5 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.hygiene1 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.hygiene2 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.hygiene3 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.hygiene4 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.hygiene5 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.hygiene6 = Boolean.parseBoolean(charScan1.nextLine().substring(10));
					Character.social1 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.social2 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.social3 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.social4 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.social5 = Boolean.parseBoolean(charScan1.nextLine().substring(9));
					Character.sleep1 = Boolean.parseBoolean(charScan1.nextLine().substring(8));
					Character.sleep2 = Boolean.parseBoolean(charScan1.nextLine().substring(8));
					Character.sleep3 = Boolean.parseBoolean(charScan1.nextLine().substring(8));
					Character.sleep4 = Boolean.parseBoolean(charScan1.nextLine().substring(8));
					Character.sleep5 = Boolean.parseBoolean(charScan1.nextLine().substring(8));
						
				}
			}else{
				userChar.setHunger(107);
				userChar.setBladder(107);
				userChar.setHygiene(107);
				userChar.setSocial(107);
				userChar.setSleep(107);
				
				userChar.setCurrentEnv("outsideHouseTutorial");
				userChar.setCharX(370);
				userChar.setCharY(530);
				userChar.setPosture("front");
				
				userChar.setDay(1);
				userChar.setHour(1);
				userChar.setMinute(0);
				userChar.setAmOrPm("PM");
				
				Character.exitedHouse = false;
				Character.exitedHouseScene = false;
				Character.tutorialFinished = false;
				
				Character.hunger1 = true;
				Character.hunger2 = true;
				Character.hunger3 = true;
				Character.hunger4 = true;
				Character.hunger5 = true;

				Character.bladder1 = true;
				Character.bladder2 = true;
				Character.bladder3 = true;
				Character.bladder4 = true;
				Character.bladder5 = true;

				Character.hygiene1 = true;
				Character.hygiene2 = true;
				Character.hygiene3 = true;
				Character.hygiene4 = true;
				Character.hygiene5 = true;
				Character.hygiene6 = true;

				Character.social1 = true;
				Character.social2 = true;
				Character.social3 = true;
				Character.social4 = true;
				Character.social5 = true;

				Character.sleep1 = true;
				Character.sleep2 = true;
				Character.sleep3 = true;
				Character.sleep4 = true;
				Character.sleep5 = true;
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isInRPG() {
		Scanner fileScan;
		try {
			fileScan = new Scanner(rpgSaveFile);
			if(fileScan.hasNextLine()){
				inRPG = true;
			}else{
				inRPG = false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return inRPG;
	}
	
	public static void populateSaves(){
		Scanner fileScanner;
		try {
			saves.clear();
			fileScanner = new Scanner(saveFiles);
			while(fileScanner.hasNextLine()){
				saves.add(Integer.parseInt(fileScanner.nextLine()));
				for(int i =0; i<saves.size();i++){
					System.out.println("After populating: " + saves.size());
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isSaved(int num){
		boolean saved = false;
		for(int i =0; i<saves.size();i++){
			if(saves.get(i).equals(num)){
				saved = true;
				System.out.println("here");
				}		
			}
		return saved;
	}
	
	public static void deleteRPG(){
		try {
			//empyting save file
			FileWriter fileWriter = new FileWriter(rpgSaveFile);
			fileWriter.write("");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteSave(int num){
		for(int i =0; i<saves.size();i++){
			if(saves.get(i).equals(num)){
				saves.remove(i);
				System.out.println("at first for loop");
			}
		}
		try {
			//rewriting saves made file
			FileWriter fileWriter = new FileWriter(saveFiles);
			for(int i =0; i<saves.size();i++){
				fileWriter.write(saves.get(i)+"\r\n");
			}
			fileWriter.close();
			
			//empyting save file
			saveFile = new File("res\\saved files\\save" + num + ".txt");
			fileWriter = new FileWriter(saveFile);
			fileWriter.write("");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteAll(){
		saves.clear();
		try {
			//rewriting saves made file
			FileWriter fileWriter = new FileWriter(saveFiles);
			fileWriter.write("");
			fileWriter.close();
			
			//empyting save file
			saveFile = new File("res\\saved files\\save1.txt");
			fileWriter = new FileWriter(saveFile);
			fileWriter.write("");
			fileWriter.close();
			saveFile = new File("res\\saved files\\save2.txt");
			fileWriter = new FileWriter(saveFile);
			fileWriter.write("");
			fileWriter.close();
			saveFile = new File("res\\saved files\\save3.txt");
			fileWriter = new FileWriter(saveFile);
			fileWriter.write("");
			fileWriter.close();
			saveFile = new File("res\\saved files\\save4.txt");
			fileWriter = new FileWriter(saveFile);
			fileWriter.write("");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getSaveNum(){
		return saveNum;
	}
	
	public Character getChar(){
		return userChar;
	}
	
	public String getSavedTime(){
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 return dateFormat.format(dateSaved);
	}
	
	private void setSavedTime(){
		dateSaved = new Date();
	}
	
	public void saveRPG(){
		setSavedTime();
		try {
			//writing into a save File
			fileWriter = new FileWriter(rpgSaveFile);
			fileWriter.write("TimeSaved: " + getSavedTime());
			fileWriter.write("\r\nName: " + userChar.getName());
			fileWriter.write("\r\nGender: " + userChar.getGender());
			fileWriter.write("\r\nCurrent Env: " + userChar.getCurrentEnv());
			fileWriter.write("\r\nxCoordinate: " + userChar.getCharX());
			fileWriter.write("\r\nyCoordinate: " + userChar.getCharY());
			fileWriter.write("\r\nposture: " + userChar.getPosture());
			fileWriter.write("\r\nexitedHouse: " + Character.exitedHouse);
			fileWriter.write("\r\ngifted: " + RpgRunner.gifted);

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save(){ //input booleans for lifesim
		setSavedTime();
		try {
			for(int i = 0; i<saves.size();i++){
				System.out.println("saves"+saves.get(i));
			}
			//writing into save Files
			System.out.println("we're in");
			fileWriter = new FileWriter(saveFiles);
			for(int j =0; j<saves.size();j++){
				if(j!=saves.size()-1){
					fileWriter.write(saves.get(j)+"\r\n");
				}else{
					if(j==saves.size()-1){
						fileWriter.write(""+saves.get(j));
						System.out.println("it added" + saves.get(j));
					}
				}
			}
			fileWriter.close();
			
			//writing into a save File
			fileWriter = new FileWriter(saveFile);
			fileWriter.write("TimeSaved: " + getSavedTime());
			fileWriter.write("\r\nName: " + userChar.getName());
			fileWriter.write("\r\nGender: " + userChar.getGender());
			fileWriter.write("\r\nCurrent Env: " + userChar.getCurrentEnv());
			fileWriter.write("\r\nxCoordinate: " + userChar.getCharX());
			fileWriter.write("\r\nyCoordinate: " + userChar.getCharY());
			fileWriter.write("\r\nposture: " + userChar.getPosture());
			fileWriter.write("\r\nHunger: " + userChar.getHunger());
			fileWriter.write("\r\nBladder: " + userChar.getBladder());
			fileWriter.write("\r\nHygiene: " + userChar.getHygiene());
			fileWriter.write("\r\nSleep: " + userChar.getSleep());
			fileWriter.write("\r\nSocial: " + userChar.getSocial());
			fileWriter.write("\r\nDay: " + userChar.getDay());
			fileWriter.write("\r\nHour: " + userChar.getHour());
			fileWriter.write("\r\nMinute: " + userChar.getMinute());
			fileWriter.write("\r\nam or pm: " + userChar.getAmOrPm());
			fileWriter.write("\r\nexitedHouse: " + Character.exitedHouse);
			fileWriter.write("\r\nexitedHouseScene: " + Character.exitedHouseScene);
			fileWriter.write("\r\ntutorialFinished: " + Character.tutorialFinished);
			fileWriter.write("\r\nhunger1: " + Character.hunger1);
			fileWriter.write("\r\nhunger2: " + Character.hunger2);
			fileWriter.write("\r\nhunger3: " + Character.hunger3);
			fileWriter.write("\r\nhunger4: " + Character.hunger4);
			fileWriter.write("\r\nhunger5: " + Character.hunger5);
			fileWriter.write("\r\nbladder1: " + Character.bladder1);
			fileWriter.write("\r\nbladder2: " + Character.bladder2);
			fileWriter.write("\r\nbladder3: " + Character.bladder3);
			fileWriter.write("\r\nbladder4: " + Character.bladder4);
			fileWriter.write("\r\nbladder5: " + Character.bladder5);
			fileWriter.write("\r\nhygiene1: " + Character.hygiene1);
			fileWriter.write("\r\nhygiene2: " + Character.hygiene2);
			fileWriter.write("\r\nhygiene3: " + Character.hygiene3);
			fileWriter.write("\r\nhygiene4: " + Character.hygiene4);
			fileWriter.write("\r\nhygiene5: " + Character.hygiene5);
			fileWriter.write("\r\nhygiene6: " + Character.hygiene6);
			fileWriter.write("\r\nsocial1: " + Character.social1);
			fileWriter.write("\r\nsocial2: " + Character.social2);
			fileWriter.write("\r\nsocial3: " + Character.social3);
			fileWriter.write("\r\nsocial4: " + Character.social4);
			fileWriter.write("\r\nsocial5: " + Character.social5);
			fileWriter.write("\r\nsleep1: " + Character.sleep1);
			fileWriter.write("\r\nsleep2: " + Character.sleep2);
			fileWriter.write("\r\nsleep3: " + Character.sleep3);
			fileWriter.write("\r\nsleep4: " + Character.sleep4);
			fileWriter.write("\r\nsleep5: " + Character.sleep5);

			
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
