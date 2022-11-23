import java.util.ArrayList;

// button class, WITHOUT THE BUTTON
class TestButton {
		private int i, j;
		private boolean team;
		private boolean clicked;
		
		TestButton() {
		
		}
	
		TestButton(int i, int j) {
			this.i = i;
			this.j = j;
			clicked = false;
		}
		
		void setAll(int i, int j) {
			this.i = i;
			this.j = j;
		}
		
		int getI() {
			return i;
		}
		
		int getJ() {
			return j;
		}
		
		boolean getTeam() {
			return team;
		}
		
		
		void setPiece(boolean blueTeam) {
			team = blueTeam;
			clicked = true;
		}
		
		boolean isPiece() {
			return clicked; 
		}

	
}
