/*
 * Monica Tam
 * Mr. Slattery
 * Java Capstone p. 4
 * 3/12/17
 */
package seniorProj;

import javax.swing.SwingUtilities;

public class MainGameRunner{
	
	public MainGameRunner(){
		/*
		 * checks which gameIntro to run
		 */
		if(User.isInRPG()){
			GameIntroRPG intro = new GameIntroRPG();
		}else{
			GameIntro intro = new GameIntro();
		}
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new MainGameRunner();
			}
		});

	}

}
