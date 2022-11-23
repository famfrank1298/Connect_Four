import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
public class MyTest extends TestButton {
	// recurseThru:
	// * * x * *
	// recurses thru 4 pieces of the same team (in total)
	// function is called twice, once each at opposite sides of x, so x-1 and x+1
	// also same for both diagonals
	// so basically, the start piece, (piece just placed), is x. function gets called for x-1 and x+1, and all of them
	// are added together. B)
	static public int recurseThru(ArrayList<ArrayList<TestButton>> buttons, int i, int j, int hor, int vert,
	boolean origTeam, ArrayList<TestButton> allFour) {
		if (i < 0 || i > 6 || j < 0 || j >= 6 || !buttons.get(i).get(j).isPiece() || buttons.get(i).get(j).getTeam() != origTeam) {
			return 0;
		}
		allFour.add(buttons.get(i).get(j));
		return recurseThru(buttons, i + hor, j + vert, hor, vert, origTeam, allFour) + 1;
	}

	// checkDown:
	// regularly recurses thru 4 pieces of the same team
	static public int checkDown(ArrayList<ArrayList<TestButton>> buttons, int i, int j, int down, boolean origTeam,
			ArrayList<TestButton> allFour) {
		if (j < 0 || j > 5 || !buttons.get(i).get(j).isPiece() || buttons.get(i).get(j).getTeam() != origTeam) {
			return 0;
		}
		allFour.add(buttons.get(i).get(j));
		return checkDown(buttons, i, j + down, down, origTeam, allFour) + 1;
	}

	// resetFour :
	// resets the winning connect 4 ArrayList
	static public void resetFour(ArrayList<TestButton> allFour) {
		while (!allFour.isEmpty()) {
			allFour.remove(allFour.size()-1);
		}
	}	

	// fourConnected:
	// checks if four of the same team are connected
	static public boolean fourConnected(ArrayList<ArrayList<TestButton>> buttons, int[] coords, ArrayList<TestButton> allFour) {
		resetFour(allFour);
		int i = coords[0];
		int j = coords[1];
		boolean origTeam = buttons.get(i).get(j).getTeam();

		// up and down (technically only down)
		int countDown = checkDown(buttons, i, j, 1, origTeam, allFour); // down
		if (countDown >= 4) {
			return true;
		} else {
			resetFour(allFour); // resets allFour ArrayList
		}

		// left and right
		int countLR = 1; allFour.add(buttons.get(i).get(j));	// adding the recently placed piece to the allFour
		countLR += recurseThru(buttons, i + 1, j, 1, 0, origTeam, allFour);
		countLR += recurseThru(buttons, i - 1, j, -1, 0, origTeam, allFour);
		if (countLR >= 4) {
			return true;
		} else {
			resetFour(allFour);
		}

		// backslash diagonal
		int BS = 1; allFour.add(buttons.get(i).get(j));
		BS += recurseThru(buttons, i + 1, j + 1, 1, 1, origTeam, allFour);
		BS += recurseThru(buttons, i - 1, j - 1, -1, -1, origTeam, allFour);
		if (BS >= 4) {
			return true;
		} else {
			resetFour(allFour);
		}
		// forward slash diagonal
		int FS = 1;  allFour.add(buttons.get(i).get(j));
		FS += recurseThru(buttons, i - 1, j + 1, -1, 1, origTeam, allFour);
		FS += recurseThru(buttons, i + 1, j - 1, 1, -1, origTeam, allFour);
		if (FS >= 4) {
			return true;
		}
		resetFour(allFour);
		
		return false;
	}
	
	static ArrayList<ArrayList<TestButton>> buttons;
	static ArrayList<ArrayList<TestButton>> buttons1;
	static ArrayList<ArrayList<TestButton>> buttons2;
	static ArrayList<ArrayList<TestButton>> buttons3;
	static ArrayList<ArrayList<TestButton>> buttons4;

	@BeforeAll
	static void setup() {

		
		buttons = new ArrayList<ArrayList<TestButton>>();
		for (int i = 0; i < 7; i++) {
			ArrayList<TestButton> buttonRow = new ArrayList<TestButton>();
			buttons.add(buttonRow);
			
			for (int j = 0; j < 6; j++) {
				TestButton BD = new TestButton(i, j);
				buttons.get(i).add(BD);
			}
		}
		
		
		
		buttons1 = new ArrayList<ArrayList<TestButton>>();
		for (int i = 0; i < 7; i++) {
			ArrayList<TestButton> buttonRow = new ArrayList<TestButton>();
			buttons1.add(buttonRow);
			
			for (int j = 0; j < 6; j++) {
				TestButton BD = new TestButton(i, j);
				buttons1.get(i).add(BD);
			}
		}
		
		
		buttons2 = new ArrayList<ArrayList<TestButton>>();
		for (int i = 0; i < 7; i++) {
			ArrayList<TestButton> buttonRow = new ArrayList<TestButton>();
			buttons2.add(buttonRow);
			
			for (int j = 0; j < 6; j++) {
				TestButton BD = new TestButton(i, j);
				buttons2.get(i).add(BD);
			}
		}
		
		
		buttons3 = new ArrayList<ArrayList<TestButton>>();
		for (int i = 0; i < 7; i++) {
			ArrayList<TestButton> buttonRow = new ArrayList<TestButton>();
			buttons3.add(buttonRow);
			
			for (int j = 0; j < 6; j++) {
				TestButton BD = new TestButton(i, j);
				buttons3.get(i).add(BD);
			}
		}
		
		
		buttons4 = new ArrayList<ArrayList<TestButton>>();
		for (int i = 0; i < 7; i++) {
			ArrayList<TestButton> buttonRow = new ArrayList<TestButton>();
			buttons4.add(buttonRow);
			
			for (int j = 0; j < 6; j++) {
				TestButton BD = new TestButton(i, j);
				buttons4.get(i).add(BD);
			}
		}
}
	
	@Test
	void test1() {
			
		boolean blueTurn = true;
		ArrayList<TestButton> allFour = new ArrayList<TestButton>();
		int[] coords = new int[2];
		
		// can only test for connect fours, as checking if validPlacement() requires you get a button from the event();
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		
		// test 1
		x.add(0); y.add(5);
		x.add(1); y.add(5);
		x.add(0); y.add(4);
		x.add(1); y.add(4);
		x.add(0); y.add(3);
		x.add(1); y.add(3);
		x.add(0); y.add(2);
		System.out.println("down connect 4 for player one (blue)");
		while (!x.isEmpty()) {
				buttons.get(x.get(0)).get(y.get(0)).setPiece(blueTurn);
				x.remove(0); y.remove(0);
				blueTurn = !blueTurn;
		}
		coords[0] = 0;
		coords[1] = 2;
		if (fourConnected(buttons, coords, allFour)) {
			System.out.println("Test 1 works");
		}
	
		// test 2
		x.add(0); y.add(5);
		x.add(1); y.add(5);
		x.add(2); y.add(5);
		x.add(2); y.add(4);
		x.add(3); y.add(5);
		x.add(3); y.add(4);
		x.add(4); y.add(5);	
		x.add(3); y.add(3);
		x.add(1); y.add(4);
		x.add(4); y.add(4);
		x.add(2); y.add(3);
		x.add(0); y.add(4);
		x.add(3); y.add(2);
		System.out.println("FS diagonal connect 4 for player one (blue)");
		while (!x.isEmpty()) {
				buttons1.get(x.get(0)).get(y.get(0)).setPiece(blueTurn);
				x.remove(0); y.remove(0);
				blueTurn = !blueTurn;
		}
		coords[0] = 3;
		coords[1] = 2;
		if (fourConnected(buttons1, coords, allFour)) {
			System.out.println("Test 2 works");
		}

		
		// test 3
		x.add(0); y.add(5);
		x.add(0); y.add(4);
		x.add(1); y.add(5);
		x.add(0); y.add(3);
		x.add(2); y.add(5);
		x.add(0); y.add(2);
		x.add(1); y.add(4);
		x.add(3); y.add(5);
		x.add(2); y.add(4);
		x.add(4); y.add(5);
		x.add(4); y.add(4);
		x.add(5); y.add(5);
		x.add(5); y.add(4);
		x.add(6); y.add(5);
		System.out.println("LR connect 4 for player two (red)");
		while (!x.isEmpty()) {
				buttons1.get(x.get(0)).get(y.get(0)).setPiece(blueTurn);
				x.remove(0); y.remove(0);
				blueTurn = !blueTurn;
		}
		coords[0] = 6;
		coords[1] = 5;
		if (fourConnected(buttons1, coords, allFour)) {
			System.out.println("Test 3 works");
		}
		
		
		// test 4
		x.add(5); y.add(5);
		x.add(4); y.add(5);
		x.add(3); y.add(5);
		x.add(3); y.add(4);
		x.add(2); y.add(5);
		x.add(2); y.add(4);
		x.add(2); y.add(3);
		x.add(1); y.add(5);
		x.add(1); y.add(4);
		x.add(1); y.add(3);
		x.add(5); y.add(4);
		x.add(4); y.add(4);
		x.add(1); y.add(2);
		x.add(3); y.add(3);
		x.add(5); y.add(3);
		x.add(2); y.add(2);
		x.add(4); y.add(3);
		x.add(1); y.add(1);
		System.out.println("BS connect 4 for player two (red)");
		while (!x.isEmpty()) {
			buttons2.get(x.get(0)).get(y.get(0)).setPiece(blueTurn);
			x.remove(0); y.remove(0);
			blueTurn = !blueTurn;
	}

		coords[0] = 1;
		coords[1] = 1;
		if (fourConnected(buttons2, coords, allFour)) {
			System.out.println("Test 4 works");
		} else {
			System.out.println("False");
		}
		
		// test 5: test 4 without 1, 1 from red. so it should NOT work
		x.add(5); y.add(5);
		x.add(4); y.add(5);
		x.add(3); y.add(5);
		x.add(3); y.add(4);
		x.add(2); y.add(5);
		x.add(2); y.add(4);
		x.add(2); y.add(3);
		x.add(1); y.add(5);
		x.add(1); y.add(4);
		x.add(1); y.add(3);
		x.add(5); y.add(4);
		x.add(4); y.add(4);
		x.add(1); y.add(2);
		x.add(3); y.add(3);
		x.add(5); y.add(3);
		x.add(2); y.add(2);
		x.add(4); y.add(3);
		//x.add(1); y.add(1);
		System.out.println("BS connect 4 for player two (red)");
		while (!x.isEmpty()) {
				buttons3.get(x.get(0)).get(y.get(0)).setPiece(blueTurn);
				x.remove(0); y.remove(0);
				blueTurn = !blueTurn;
		}
		coords[0] = 4;
		coords[1] = 3;
		if (fourConnected(buttons3, coords, allFour)) {
			System.out.println("Test 5 does not work");
		} else {
			System.out.println("Test 5 works");
		}
	
	
	}
	
}



/*
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.application.Platform;
import java.util.*;





class MyTest extends TestButton {

	static ArrayList<ArrayList<TestButton>> buttons;

	@BeforeAll
	void setUp() {
		
		buttons = new ArrayList<ArrayList<TestButton>>();
		for (int i = 0; i < 7; i++) {
			ArrayList<TestButton> buttonRow = new ArrayList<TestButton>();
			buttons.add(buttonRow);
			
			for (int j = 0; j < 6; j++) {
				TestButton BD = new TestButton(i, j);
				buttons.get(i).add(BD);
			}
		}
	}

	@Test
	public void test() {
		
	}
	
	

}
*/