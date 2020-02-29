
package calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
 * <p>
 * Title: Calculator Class.
 * </p>
 * 
 * <p>
 * Description: A JavaFX demonstration application and baseline for a sequence
 * of projects
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter � 2016
 * </p>
 *
 * @author Aakash Sharma
 * 
 * @version 4.00 2018-01-29 Added the sub, mpy and div method which perform the
 *          required computations.
 * @version 5.00 2019-02-19 Aakash Sharma tested the methods so that it properly
 *          works. Aakash Sharma added the log method to calculate the log base 2
 * 
 * @version 6.00 2019-03-20 Aakash Sharma added the FSM code Aakash Sharma added the
 *          functioning of FSM and declared all the variables needed to run the
 *          FSM.
 *
 * @version 7.00 2019-03-25 Aakash Sharma work on the UI of the Calculator. Aman
 *          Verma add the code in the Error term operands The code of the FSM
 *          has been taken from the W8D3 Study Hall Activity.
 * 
 * @version 7.10 2019-03-25 Aakash Sharma work on the UI of the Calculator and
 *          FSM. Add the code in the Error term operands
 *
 * @version 8.00 2019-10-23 Aakash Sharma work on the UI of the Calculator and
 *          the UNumber in this previous version . Add the code for using the
 *          UNumber code in calculator.
 * @version 9.00 2019-10-23 Aakash Sharma work on the UI of the Calculator and
 *          the UNumber in this previous version . Add the code for using the
 *          UNumber code in calculator.
 * 
 * @version 10.00 2019-11-24 Aakash Sharma work on the Science and Engineering
 *          Calculator with Units
 */

public class Calculator extends Application {

	public final static double WINDOW_WIDTH = 1200;
	public final static double WINDOW_HEIGHT = 400;

	public UserInterface theGUI;

	/**********
	 * This is the start method that is called once the application has been loaded
	 * into memory and is ready to get to work.
	 * 
	 * In designing this application I have elected to IGNORE all opportunities for
	 * automatic layout support and instead have elected to manually position each
	 * GUI element and its properties in order to exercise complete control over the
	 * user interface look and feel.
	 * 
	 */
	@Override
	public void start(Stage theStage) throws Exception {

		theStage.setTitle("Aakash Sharma"); // Label the stage (a window)

		Pane theRoot = new Pane(); // Create a pane within the window

		theGUI = new UserInterface(theRoot); // Create the Graphical User Interface

		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT); // Create the scene

		theStage.setScene(theScene); // Set the scene on the stage

		theStage.show(); // Show the stage to the user

		// When the stage is shown to the user, the pane within the window is visible.
		// This means that the
		// labels, fields, and buttons of the Graphical User Interface (GUI) are visible
		// and it is now
		// possible for the user to select input fields and enter values into them,
		// click on buttons, and
		// read the labels, the results, and the error messages.
	}

	/*******************************************************************************************************/

	/*******************************************************************************************************
	 * This is the method that launches the JavaFX application
	 * 
	 */
	public static void main(String[] args) { // This method may not be required
		launch(args); // for all JavaFX applications using
	} // other IDEs.
}
