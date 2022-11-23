import javafx.scene.control.Button;
import javafx.application.Application;

class GameButton extends Button {
		private Button button;
		private int i, j;
		private boolean team;
		private boolean clicked;
		private boolean test;
		GameButton() {
		
		}
		void changeToTheme(int theme) {
			if (clicked) {
				if (team) {
					if (theme == 0) {
						button.setStyle("-fx-background-color: blue");
					} else if (theme == 1) {
						button.setStyle("-fx-background-color: cyan");
					} else if (theme == 2) {
						button.setStyle("-fx-background-color: green");
					}
				} else {
					if (theme == 0) {
						button.setStyle("-fx-background-color: red");
					} else if (theme == 1) {
						button.setStyle("-fx-background-color: orange");
					} else if (theme == 2) {
						button.setStyle("-fx-background-color: pink");
					}
				}
			}
		}
		GameButton(int i, int j) {
			button = new Button();
			this.i = i;
			this.j = j;
			clicked = false;
			button.setPrefWidth(50);
			button.setPrefHeight(50);
			test = false;
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
		
		Button getButton() {
			return button;
		}
		
		boolean getTeam() {
			return team;
		}
		
		void setWin() {
			button.setStyle("-fx-background-color: yellow");
		}
		
		void setPiece(boolean blueTeam) {
			team = blueTeam;
			clicked = true;
		}
		
		boolean isPiece() {
			return clicked; 
		}
		void printCoords() {
			System.out.println("(" + i + ", " + j + ")");
		}
		void reEnable() {
			clicked = false;
			//button.setPrefWidth(50);
			//button.setPrefHeight(50);
			button.setStyle("-fx-base: # f4f162");
			//button.setStyle("-fx-base");
			button.setDisable(false);
		}
	}
