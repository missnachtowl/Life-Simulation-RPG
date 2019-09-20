/*
 * Monica Tam
 * Mr. Slattery
 * Java Capstone p. 4
 * 3/12/17
 */
package seniorProj;

/*
 * class that contains the name and power of the moves
 * and returns them
 */
public class Move {
	
	private int power;
	private String name;
	
	Move(int power, String name){
		this.power = power;
		this.name = name;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
