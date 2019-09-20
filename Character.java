/*
 * Monica Tam
 * Mr. Slattery
 * Java Capstone p. 4
 * 3/12/17
 */
package seniorProj;

import java.io.File;
import java.util.ArrayList;

public class Character {
	
	private String name; 
	private String gender; 
	private ArrayList<String> hearing = new ArrayList<>(); //String of things the character is hearing, for talking to npcs 
	private boolean isHearing = false; //used to check whether to display textbox
	private boolean isSleeping= false; //used for the clock in LifeSimRunner
	
	/*
	 * used for populating from save file
	 */
	private String currentEnv; //name of the environment char is in
	private int charX; //their x coordinate
	private int charY; //their y coordinate
	private String posture; //whether they are facing right, left, back, or front
	
	private int day; //the day the char is in last
	private int hour; //the last hour the char is played
	private int minute; //last minute the char is played
	private String amOrPm; //am or pm of the time
	private int saveNum; //the num identifier for the save file the character is stored in

	/*
	 * char's needs
	 */
	private int hunger;
	private int bladder;
	private int hygiene;
	private int sleep;
	private int social;
	
	/*
	 * the constant rate at which the needs decrement
	 */
	private int hungerDec = -4;
	private int bladderDec =-5;
	private int hygieneDec = -2;
	private int sleepDec = -1;
	private int socialDec = -3;
	
	private boolean stinky; //if stinky, then npcs will not talk to char
	 
	/*
	 * Used so that the notification that a need is full does not spam at the start of the game
	 */
	private boolean hungerUsed = false;
	private boolean bladderUsed = false;
	private boolean hygieneUsed = false;
	private boolean socialUsed = false;
	private boolean sleepUsed = false;
	
	/*
	 * Boolean identifiers that structures a sequence of if-statements for notifications on the status
	 * of the char's needs, making it so that a notification will not repeat itself
	 */
	public static boolean hunger1 = true;
	public static boolean hunger2 = true;
	public static boolean hunger3 = true;
	public static boolean hunger4 = true;
	public static boolean hunger5 = true;

	public static boolean bladder1 = true;
	public static boolean bladder2 = true;
	public static boolean bladder3 = true;
	public static boolean bladder4 = true;
	public static boolean bladder5 = true;

	public static boolean hygiene1 = true;
	public static boolean hygiene2 = true;
	public static boolean hygiene3 = true;
	public static boolean hygiene4 = true;
	public static boolean hygiene5 = true;
	public static boolean hygiene6 = true;

	public static boolean social1 = true;
	public static boolean social2 = true;
	public static boolean social3 = true;
	public static boolean social4 = true;
	public static boolean social5 = true;

	public static boolean sleep1 = true;
	public static boolean sleep2 = true;
	public static boolean sleep3 = true;
	public static boolean sleep4 = true;
	public static boolean sleep5 = true;
	
	public static boolean exitedHouse = false; //for placing the character at a certain point if exiting the house
	public static boolean tutorialFinished = false; //to identify that the tutorial part is finished
	public static boolean exitedHouseScene = false; //to identify the scene where the old man leaves is able to start
	
	public Character(String name, String gender){
		this.name = name;
		this.gender = gender;
	}
	
	public Character(){ 
		//for rpg, since we won't be making any new player characters
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCharX() {
		return charX;
	}

	public void setCharX(int charX) {
		this.charX = charX;
	}
	
	public int getSaveNum(){
		return saveNum;
	}
	
	public void setSaveNum(int saveNum){
		this.saveNum = saveNum;
	}
	
	public String getPosture() {
		return posture;
	}

	public void setPosture(String posture) {
		this.posture = posture;
	}

	public String getCurrentEnv(){
		return currentEnv;
	}
	
	public void setCurrentEnv(String env){
		currentEnv = env;
	}

	public int getCharY() {
		return charY;
	}

	public void setCharY(int charY) {
		this.charY = charY;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	/*
	 * turns the hour into a String
	 */
	public String getHour() {
		return ""+hour;
	}

	/*
	 * calculates whether the set hour will change the day from am or pm
	 * and calculates, if the hour is more than 12, whether it has become am or pm
	 * and what it is equivalent to on a 12 hour system
	 */
	public void setHour(int hour) {
		this.hour = hour;

		if(hour==12&&amOrPm.equals("PM")){
			amOrPm="AM";
			day++;
		}else{
			if(hour==12&&amOrPm.equals("AM")){
				amOrPm = "PM";
			}
		}
		if(hour>12&&amOrPm.equals("AM")){
			amOrPm = "PM";
			day++;
			setHour(hour-12);
		}else{
			if(hour>12&&amOrPm.equals("PM")){
				amOrPm = "AM";
				day++;
				setHour(hour-12);
			}
		}
	}
	
	/*
	 * if the minute is 1 digit, adds a 0 to the string, otherwise simply converts to string
	 */
	public String getMinute() {
		if(minute<10){
			return "0"+minute;
		}
		return ""+minute;
	}

	//if minute is set to more than 59, increments the hour and sets minute back to 0
	public void setMinute(int min) {
		this.minute = min;
		if(minute==60){
			  setHour(hour+1);
			  minute = 0;
		   }
	}


	public String getAmOrPm() {
		return amOrPm;
	}

	public void setAmOrPm(String amOrPm) {
		this.amOrPm = amOrPm;
	}
	
	public boolean isSleeping() {
		return isSleeping;
	}
	
	public void setIsSleeping(boolean isSleeping) {
		this.isSleeping = isSleeping;
	}

	public boolean isHearing() {
		return isHearing;
	}

	public void setIsHearing(boolean isHearing) {
		this.isHearing = isHearing;
	}
	
	public ArrayList<String> getHearing() {
		return hearing;
	}

	public int getHunger() {
		return hunger;
	}

	/*
	 * if more than 107, which is the max, then gets set back to max
	 * if less than min, gets set to min, which is 0
	 */
	public void setHunger(int hunger) {
		this.hunger += hunger;
		if(this.hunger>107){
			this.hunger = 107;
		}
		if(this.hunger<0){
			this.hunger = 0;
		}
	}

	public int getBladder() {
		return bladder;
	}
	
	public boolean isStinky(){
		return stinky;
	}
	
	public void setStinky(boolean stinky){
		this.stinky = stinky;
	}

	/*
	 * if more than 107, which is the max, then gets set back to max
	 * if less than min, gets set to min, which is 0
	 */
	public void setBladder(int bladder) {
		this.bladder += bladder;
		if(this.bladder>107){
			this.bladder = 107;
		}
		if(this.bladder<0){
			this.bladder = 0;
		}
	}

	public int getHygiene() {
		return hygiene;
	}

	/*
	 * if more than 107, which is the max, then gets set back to max
	 * if less than min, gets set to min, which is 0
	 */
	public void setHygiene(int hygiene) {
		this.hygiene += hygiene;
		if(this.hygiene>107){
			this.hygiene = 107;
		}
		if(this.hygiene<0){
			this.hygiene = 0;
		}
	}

	public int getSleep() {
		return sleep;
	}

	/*
	 * if more than 107, which is the max, then gets set back to max
	 * if less than min, gets set to min, which is 0
	 */
	public void setSleep(int sleep) {
		this.sleep += sleep;
		if(this.sleep>107){
			this.sleep = 107;
		}
		if(this.sleep<0){
			this.sleep = 0;
		}
	}

	public int getSocial() {
		return social;
	}

	/*
	 * if more than 107, which is the max, then gets set back to max
	 * if less than min, gets set to min, which is 0
	 */
	public void setSocial(int social) {
		this.social += social;
		if(this.social>107){
			this.social = 107;
		}
		if(this.social<0){
			this.social = 0;
		}
	}

	public int getHungerDec() {
		return hungerDec;
	}

	public void setHungerDec(int hungerDec) {
		this.hungerDec = hungerDec;
	}

	public int getBladderDec() {
		return bladderDec;
	}

	public void setBladderDec(int bladderDec) {
		this.bladderDec = bladderDec;
	}

	public int getHygieneDec() {
		return hygieneDec;
	}

	public void setHygieneDec(int hygieneDec) {
		this.hygieneDec = hygieneDec;
	}

	public int getSleepDec() {
		return sleepDec;
	}

	public void setSleepDec(int sleepDec) {
		this.sleepDec = sleepDec;
	}

	public int getSocialDec() {
		return socialDec;
	}

	public void setSocialDec(int socialDec) {
		this.socialDec = socialDec;
	}
	
	public boolean isHungerUsed() {
		return hungerUsed;
	}

	public void setHungerUsed(boolean hungerUsed) {
		this.hungerUsed = hungerUsed;
	}

	public boolean isBladderUsed() {
		return bladderUsed;
	}

	public void setBladderUsed(boolean bladderUsed) {
		this.bladderUsed = bladderUsed;
	}

	public boolean isHygieneUsed() {
		return hygieneUsed;
	}

	public void setHygieneUsed(boolean hygieneUsed) {
		this.hygieneUsed = hygieneUsed;
	}

	public boolean isSocialUsed() {
		return socialUsed;
	}

	public void setSocialUsed(boolean socialUsed) {
		this.socialUsed = socialUsed;
	}

	public boolean isSleepUsed() {
		return sleepUsed;
	}

	public void setSleepUsed(boolean sleepUsed) {
		this.sleepUsed = sleepUsed;
	}
}
