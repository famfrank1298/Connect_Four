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
import javafx.collections.ObservableList;

import java.util.*;

public class JavaFXTemplate extends Application {

	Scene welcome_scene, game_scene, win_scene, dbz_load_scene, marvel_load_scene, beach_load_scene;
	static int move_counter = 0;
	Text move;
	static int theme = 0;
	VBox game_screen_vbox;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Connect Four!");

		// Welcome Scene :
		Text welcome_title_screen = new Text(10, 20, "!Welcome to Connect 4!");
		welcome_title_screen.setFill(Color.BLUE);
		welcome_title_screen.setFont(Font.font(null, FontWeight.BOLD, 32));

		InnerShadow inner = new InnerShadow();
		inner.setOffsetX(5.0);
		inner.setOffsetY(5.0);
		welcome_title_screen.setEffect(inner);
		welcome_title_screen.setTextAlignment(TextAlignment.CENTER);

		// Beach Image
		Image welcome_bimage = new Image("beach_pic.jpg", 650, 650, false, false);
		Image connect4_image = new Image("connect-4.jpg");
		ImageView connect4_imgView = new ImageView();
		connect4_imgView.setImage(new Image("connect-4.jpg", 300, 300, false, false));
		
		BackgroundImage welcome_background_image = new BackgroundImage(welcome_bimage, BackgroundRepeat.NO_REPEAT,
		BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
		Background welcome_background = new Background(welcome_background_image);
		
		Button welcome_start_button = new Button("Start");
		welcome_start_button.setPrefWidth(60);
		welcome_start_button.setPrefHeight(40);
		welcome_start_button.setOnAction(e -> {
			primaryStage.setScene(game_scene);
			primaryStage.show();
		});
		
		Button welcome_about_button = new Button("About");
		welcome_about_button.setPrefWidth(60);
		welcome_about_button.setPrefHeight(40);
		
		Popup about_popup = new Popup();
		Label about_text = new Label("Frank Mensah and David Serrano...Enjoy lol");
		about_text.setStyle(" -fx-background-color: white");
		about_popup.getContent().add(about_text);
		about_popup.setAutoHide(true);
		welcome_about_button.setOnAction(e -> {
			if(!about_popup.isShowing()) {
				about_popup.show(primaryStage);
			} else {
				about_popup.hide();
			}
		});
		
		BorderPane welcome_scene_layout = new BorderPane();
		welcome_scene_layout.setAlignment(welcome_title_screen, Pos.TOP_CENTER);
		welcome_scene_layout.setTop(welcome_title_screen);
		welcome_scene_layout.setAlignment(welcome_about_button, Pos.BOTTOM_LEFT);
		welcome_scene_layout.setLeft(welcome_about_button);
		welcome_scene_layout.setAlignment(welcome_start_button, Pos.BOTTOM_RIGHT);
		welcome_scene_layout.setRight(welcome_start_button);
		welcome_scene_layout.setAlignment(connect4_imgView, Pos.CENTER);
		welcome_scene_layout.setCenter(connect4_imgView);
		
		welcome_scene_layout.setPrefSize(650, 650);
		
		VBox welcome_screen_vbox = new VBox(welcome_scene_layout);
		
		welcome_scene_layout.setPadding(new Insets(10, 20, 10, 20));
		welcome_screen_vbox.setAlignment(Pos.CENTER_LEFT);
		welcome_screen_vbox.setBackground(welcome_background);
		
		welcome_scene = new Scene(welcome_screen_vbox, 650, 650);
		primaryStage.setScene(welcome_scene);
		primaryStage.show();

		// Game Scene;
		GameScene(primaryStage);

	}

	// recurseThru:
	// * * x * *
	// recurses thru 4 pieces of the same team (in total)
	// function is called twice, once each at opposite sides of x, so x-1 and x+1
	// also same for both diagonals
	// so basically, the start piece, (piece just placed), is x. function gets called for x-1 and x+1, and all of them
	// are added together. B)
	public int recurseThru(ArrayList<ArrayList<GameButton>> buttons, int i, int j, int hor, int vert,
	boolean origTeam, ArrayList<GameButton> allFour) {
		if (i < 0 || i > 6 || j < 0 || j >= 6 || !buttons.get(i).get(j).isPiece() || buttons.get(i).get(j).getTeam() != origTeam) {
			return 0;
		}
		allFour.add(buttons.get(i).get(j));
		return recurseThru(buttons, i + hor, j + vert, hor, vert, origTeam, allFour) + 1;
	}
	
	// checkDown:
	// regularly recurses thru 4 pieces of the same team
	public int checkDown(ArrayList<ArrayList<GameButton>> buttons, int i, int j, int down, boolean origTeam,
			ArrayList<GameButton> allFour) {
		if (j < 0 || j > 5 || !buttons.get(i).get(j).isPiece() || buttons.get(i).get(j).getTeam() != origTeam) {
			return 0;
		}
		allFour.add(buttons.get(i).get(j));
		return checkDown(buttons, i, j + down, down, origTeam, allFour) + 1;
	}
	
	// resetFour :
	// resets the winning connect 4 ArrayList
	public void resetFour(ArrayList<GameButton> allFour) {
		while (!allFour.isEmpty()) {
			allFour.remove(allFour.size()-1);
		}
	}	

	// fourConnected:
	// checks if four of the same team are connected
	public boolean fourConnected(ArrayList<ArrayList<GameButton>> buttons, int[] coords, ArrayList<GameButton> allFour) {
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
	
	// establish:
	// establishes GameButton data (sets team, sets piece)
	// sets coords of button
	public void establish(ArrayList<ArrayList<GameButton>> buttons, int i, int j, boolean[] blueTurn, int[] coords,
	ArrayList<Integer> mi, ArrayList<Integer> mj) {
		buttons.get(i).get(j).setPiece(blueTurn[0]);
		coords[0] = i;
		coords[1] = j;
		mi.add(i);
		mj.add(j);
	}
	
	// validPlacement:
	// uses DD ArrayList of GameButtons to determine if pressed button is at a valid placement
	// return true if it is
	public boolean validPlacement(ArrayList<ArrayList<GameButton>> buttons, Button button, boolean[] blueTurn,
	int[] coords, ArrayList<Integer> mi, ArrayList<Integer> mj) {
			for (int i = 0; i < buttons.size(); ++i) {
				for (int j = 0; j < buttons.get(i).size(); ++j) {
					if (buttons.get(i).get(j).getButton() == button) { // find instance of button in array
						// check if valid placement
						if (j == 5) { // bottom
							establish(buttons, i, j, blueTurn, coords, mi, mj);
							return true;
						}
						if (buttons.get(i).get(j + 1).isPiece()) { // once bottom gets placed,
							establish(buttons, i, j, blueTurn, coords, mi, mj); // just check if there is a piece under
							return true;
						}
		
					}
				}
			}
			return false;
	}
	
	// incAmount:
	// increments piecesAmount
	// piecesAmount is tracked up until 6 pieces because you can't have a connect for unless theres >=7 pieces on the board
	public void incAmount(int[] piecesAmount) {
		piecesAmount[0]++;
	}
	
	// setTurn:
	// changes turn to other player
	// there's no pass by reference in java, so I put the bool in an array because they're
	// pass by reference
	public void setTurn(boolean[] blueTurn) {
		blueTurn[0] = !blueTurn[0];
	}
	
	public void undoMove(ArrayList<Integer> mi, ArrayList<Integer> mj, ArrayList<ArrayList<GameButton>> buttons,
		boolean[] blueTurn, int[] piecesAmount, ArrayList<Text> game_action_text) {
		if (mi.size() > 0) {
			buttons.get(mi.get(mi.size()-1)).get(mj.get(mj.size()-1)).reEnable();
			mi.remove(mi.size()-1);
			mj.remove(mj.size()-1);
			blueTurn[0] = !blueTurn[0];
			piecesAmount[0]--;
			game_action_text.remove(move_counter);
			move_counter--;

		}
	}
	
	// resetGame:
	// resets the game board
	public void resetGame(ArrayList<Integer> mi, ArrayList<Integer> mj, ArrayList<ArrayList<GameButton>> buttons,
	boolean[] blueTurn, int[] piecesAmount, ArrayList<Text> game_action_text, ArrayList<GameButton> allFour) {
		while (mi.size() > 0) {
			undoMove(mi, mj, buttons, blueTurn, piecesAmount, game_action_text);
		}
	}
	
	// winningPieces:
	// changes the winning pieces' color to black
	public void winningPieces(ArrayList<GameButton> pieces) {
		if (pieces.size() == 0) {	// other case
			return;
		}

			for (int i = 0; i < pieces.size(); ++i) {
				pieces.get(i).getButton().setStyle("-fx-background-color: #000000");
		}
	}
	
	// disable:
	// disables all buttons
	public void disable(ArrayList<ArrayList<GameButton>> buttons) {
		for (int i = 0; i < buttons.size(); ++i) {
			for (int j = 0; j < buttons.get(i).size(); ++j) {
				buttons.get(i).get(j).getButton().setDisable(true);
			}
		}
	}
	
	// reEnableAll:
	// reEnables all buttons and sets player turn to One
	public void reEnableAll(TextField display_player_turn, ArrayList<ArrayList<GameButton>> buttons) {
		display_player_turn.setText("Player One's Turn");
		
		for (int i = 0; i < buttons.size(); ++i) {
			for (int j = 0; j < buttons.get(i).size(); ++j) {
				buttons.get(i).get(j).getButton().setDisable(false);
				buttons.get(i).get(j).getButton().setStyle("-fx-base: #");
			}
		}
	}

	// setRandFont:
	// sets random font to param using Random class
	public void setRandFont(Text text) {
		Random rand = new Random();
		ArrayList<String> fonts = new ArrayList<String>();
		fonts.add("Algerian");
		fonts.add("Arial");
		fonts.add("Blackadder ITC");
		fonts.add("Bodoni MT");
		fonts.add("Cooper");
		fonts.add("Elephant");
		fonts.add("Gill Sans");
		fonts.add("Harlow Solid");
		
		Font font = new Font(fonts.get(rand.nextInt(fonts.size())), 30);
		text.setFont(font);
	}
	
	// setRandFont:
	// same as above with different parameter
	public void setRandFont(TextField text) {
		Random rand = new Random();
		ArrayList<String> fonts = new ArrayList<String>();
		fonts.add("Algerian");
		fonts.add("Arial");
		fonts.add("Blackadder ITC");
		fonts.add("Bodoni MT");
		fonts.add("Cooper");
		fonts.add("Elephant");
		fonts.add("Gill Sans");
		fonts.add("Harlow Solid");
		
		Font font = new Font(fonts.get(rand.nextInt(fonts.size())), 16);
		text.setFont(font);
	}
	
	// changeDropTheme:
	// changes color of menuItems
	public void changeDropTheme(Menu u) {
		Random rand = new Random();
		ArrayList<String> fonts = new ArrayList<String>();
		fonts.add("Algerian");
		fonts.add("Arial");
		fonts.add("Blackadder ITC");
		fonts.add("Bodoni MT");
		fonts.add("Cooper");
		fonts.add("Elephant");
		fonts.add("Gill Sans");
		fonts.add("Harlow Solid");
		
		
		
		ObservableList<MenuItem> menuItems = u.getItems();
		for (int i = 0; i < menuItems.size(); ++i) {
			if (theme == 0) {
				menuItems.get(i).setStyle("-fx-background-color: orange");
				menuItems.get(i).setStyle("-fx-font: 20 " + fonts.get(rand.nextInt(fonts.size()))+ ";");
			} else if (theme == 1) {
				menuItems.get(i).setStyle("-fx-background-color: pink");
				menuItems.get(i).setStyle("-fx-font: 20 " + fonts.get(rand.nextInt(fonts.size()))+ ";");
			} else if (theme == 2) {
				menuItems.get(i).setStyle("-fx-background-color: red");
				menuItems.get(i).setStyle("-fx-font: 20 " + fonts.get(rand.nextInt(fonts.size()))+ ";");
			}
		}
		
	}
	
	// changeMenuTheme:
	// changes MenuBar theme
	public void changeMenuTheme(MenuBar u) {
			if (theme == 0) {
				u.setStyle("-fx-background-color: blue");
			} else if (theme == 1) {
				u.setStyle("-fx-background-color: cyan");
			} else if (theme == 2) {
				u.setStyle("-fx-background-color: green");
			}
			ObservableList<Menu> menus = u.getMenus();
			
			for (int j = 0; j < menus.size(); ++j) {
			ObservableList<MenuItem> menuItems = menus.get(j).getItems();
				for (int i = 0; i < menuItems.size(); ++i) {
					if (theme == 0) {
						menuItems.get(i).setStyle("-fx-background-color: orange");
					} else if (theme == 1) {
						menuItems.get(i).setStyle("-fx-background-color: pink");
					} else if (theme == 2) {
						menuItems.get(i).setStyle("-fx-background-color: red");
					}
				}
			
			}	
	}
	
	// changeColor:
	// changes pressed button to theme color
	public void changeColor(Button button, boolean playerOne) {
		if (playerOne) {
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
	
	// changeAllColors:
	// changes All pressed buttons to theme color
	// needed when you change them and you've already played a bit
	public void changeAllColors(ArrayList<ArrayList<GameButton>> buttons) {
		for (int i = 0; i < buttons.size(); ++i) {
			for (int j = 0; j < buttons.get(i).size(); ++j) {
				buttons.get(i).get(j).changeToTheme(theme);
			}
		}
	}
	
	// didPieceWin:
	// executes code for if piece won
	// if someone won:
	//		disable buttons, highlight winningPieces, pause game, reEnable buttons when pause is finished, move to winScreen
	// else
	// pause game, enable 'winScreen' but with TIE
	public void didPieceWin(ArrayList<ArrayList<GameButton>> buttons, int[] coords, ArrayList<GameButton> allFour, ArrayList<Integer> mi,
			ArrayList<Integer> mj, boolean[] blueTurn, int[] piecesAmount,ArrayList<Text> game_action_text, boolean blueTeam, Stage primaryStage,
			GameButton BD, TextField display_player_turn) {
		// piecesAmount tries to optimize amount of calls to fourConnected
		if (piecesAmount[0] > 6 && fourConnected(buttons, coords, allFour)) {
			disable(buttons);
			winningPieces(allFour);
			PauseTransition pause = new PauseTransition(Duration.seconds(4));
			pause.setOnFinished(e -> {reEnableAll(display_player_turn, buttons);
				winScreen(primaryStage, BD.getTeam(), false, mi, mj, buttons, blueTurn, piecesAmount, game_action_text, allFour);});
			pause.play();
		} else if(piecesAmount[0] == 42) { // tie game
			PauseTransition pause = new PauseTransition(Duration.seconds(1));
			pause.setOnFinished(e -> {winScreen(primaryStage, BD.getTeam(), true, mi, mj, buttons, blueTurn, piecesAmount, game_action_text, allFour);});
			pause.play();
		}
	}
	
	// changeButtonAfterPlacement:
	// disables button, changes button's color based on who's turn it is, then displays movement text
	public void changeButtonAfterPlacement(Button temp, boolean[] blueTurn, int[] coords, TextField display_player_turn) {
		temp.setDisable(true);
		if (blueTurn[0]) { // player one's turn
			changeColor(temp, true);
			//temp.setOpacity(1.0);
			display_player_turn.setText("Player Two's Turn");
			move = new Text("Player One moved to " + "(" + coords[0] + "," + coords[1] + "). This is a valid move.");
		} else { // Player two's turn
			changeColor(temp, false);
			//temp.setOpacity(1.0);
			display_player_turn.setText("Player One's Turn");
			move = new Text("Player Two moved to " + "(" + coords[0] + "," + coords[1] + "). This is a valid move.");
		}
	}
	
	// setGameTheme:
	// sets current game theme
	public void setGameTheme(int type, MenuItem currentTheme, ArrayList<ArrayList<GameButton>> buttons, MenuBar game_menubar, Stage primaryStage,
			TextField display_player_turn, BorderPane game_bpane) {
			currentTheme.setOnAction(e -> {
			if (type == 0) {
				theme = 0;
				beach_Loading(primaryStage);
			} else if (type == 1) {
				theme = 1;
				dbz_Loading(primaryStage);
			} else if (type == 2) {
				theme = 2;
				marvel_Loading(primaryStage);
			}
				
			changeAllColors(buttons); changeMenuTheme(game_menubar);
			if (type == 1) {
				Image game_bimage = new Image("goku_bg.jpg", 650, 650, false, false);
				display_player_turn.setStyle("-fx-text-inner-color: darkOrange");
	
				BackgroundImage game_background_image_t1 = new BackgroundImage(game_bimage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
				
				Background game_background_t1 = new Background(game_background_image_t1);
				game_bpane.setBackground(game_background_t1);
			
				game_bpane.setPadding(new Insets(10, 20, 10, 20));
				game_bpane.setPrefSize(650, 650);
			
			} else if (type == 2) {
				Image game_bimage = new Image("marveDC.jpg", 650, 650, false, false);
				display_player_turn.setStyle("-fx-text-inner-color: darkRed");
	
				BackgroundImage game_background_image_t1 = new BackgroundImage(game_bimage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
				
				Background game_background_t1 = new Background(game_background_image_t1);
				game_bpane.setBackground(game_background_t1);
			
				game_bpane.setPadding(new Insets(10, 20, 10, 20));
				game_bpane.setPrefSize(650, 650);
			}
			
			});
			

	}
	// GameScene:
	// controls everything about the GameScene
	public void GameScene(Stage primaryStage) {
		
		ArrayList<Text> game_action_text = new ArrayList<Text>();
		Text temp = new Text("Waiting for First Move...");
		temp.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		temp.setFill(Color.BLUE);
		game_action_text.add(temp);
		
		// these are initialized in arrays because they are able to be passed by reference
		boolean[] blueTurn = new boolean[1];
		int[] coords = new int[2];
		int[] piecesAmount = new int[1];
		boolean[] winner = new boolean[1];
		winner[0] = false;
		piecesAmount[0] = 0;
		blueTurn[0] = true;
		
		// ArrayLists used to manipulate the game 
		ArrayList<ArrayList<GameButton>> buttons = new ArrayList<ArrayList<GameButton>>();
		ArrayList<Integer> mi = new ArrayList<Integer>();
		ArrayList<Integer> mj = new ArrayList<Integer>();
		ArrayList<GameButton> allFour = new ArrayList<GameButton>();
		
		// This is the GUI from now on
		Menu game_themes = new Menu("Themes");
		MenuItem game_themes0 = new MenuItem("Original Theme");
		MenuItem game_themes1 = new MenuItem("Theme One");
		MenuItem game_themes2 = new MenuItem("Theme Two");
		
		game_themes.getItems().add(game_themes0);
		game_themes.getItems().add(game_themes1);
		game_themes.getItems().add(game_themes2);
		
		Menu game_options = new Menu("Options");
		
		MenuItem game_options_htp = new MenuItem("How to Play");
		Popup htp_popup = new Popup();
		Label htp_text = new Label("YOU KNOW HOW TO PLAY CONNECT 4... CMON LOL");
		htp_text.setStyle(" -fx-background-color: white");
		htp_popup.getContent().add(htp_text);
		htp_popup.setAutoHide(true);
		game_options_htp.setOnAction(e -> {
			if(!htp_popup.isShowing()) {
				htp_popup.show(primaryStage);
			} else {
				htp_popup.hide();
			}
		});
		
		Menu game_bar = new Menu("Game Play");
		MenuItem game_bar_reverse = new MenuItem("Reverse Move");
		game_bar.getItems().add(game_bar_reverse);
		game_bar_reverse.setOnAction(e -> undoMove(mi, mj, buttons, blueTurn, piecesAmount, game_action_text));
		
		MenuItem game_options_ng = new MenuItem("New Game");
		game_options_ng.setOnAction(e-> resetGame(mi, mj, buttons, blueTurn, piecesAmount, game_action_text, allFour));
		
		MenuItem game_options_exit = new MenuItem("Exit");
		game_options_exit.setOnAction(e -> Platform.exit());
		
		game_options.getItems().add(game_options_htp);
		game_options.getItems().add(game_options_ng);
		game_options.getItems().add(game_options_exit);
		
		MenuBar game_menubar = new MenuBar();
		game_menubar.getMenus().addAll(game_bar, game_themes, game_options);
		
		GridPane game_gridpane = new GridPane();
		
		Text game_title = new Text("Connect Four");
		game_title.setX(10.0);
		game_title.setY(50.0);
		game_title.setFill(Color.AQUA);
		game_title.setFont(Font.font(null, FontWeight.BOLD, 32));
		
		TextField display_player_turn = new TextField();
		display_player_turn.setDisable(true);
		display_player_turn.setMaxWidth(200);
		display_player_turn.setOpacity(1);
		display_player_turn.setText("Player One's Turn");
		display_player_turn.setStyle("-fx-text-inner-color: darkBlue");
		setRandFont(display_player_turn);
		
		BorderPane game_bpane = new BorderPane();
		
		// adding all GameButtons to the borderpane, (and arrayList)
		// as well as implementing the handle event for them
		for (int i = 0; i < 7; i++) {
			ArrayList<GameButton> buttonRow = new ArrayList<GameButton>();
			buttons.add(buttonRow);
	
			for (int j = 0; j < 6; j++) {
				GameButton BD = new GameButton(i, j);
				buttons.get(i).add(BD);
		
				
				buttons.get(i).get(j).getButton().setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Button temp = (Button) event.getSource();
						// check if placement is valid
						Image img;
						if (validPlacement(buttons, temp, blueTurn, coords, mi, mj)) { // add piece to board
							incAmount(piecesAmount);
							changeButtonAfterPlacement(temp, blueTurn, coords, display_player_turn);
			
							didPieceWin(buttons, coords, allFour, mi, mj, blueTurn, piecesAmount, game_action_text, BD.getTeam(), primaryStage, BD, display_player_turn);	
							setTurn(blueTurn);
						} else {	// invalid move
							if(blueTurn[0]) {
								move = new Text("Player One tried to move. This is a Not valid move. Please pick again");
							} else {
								move = new Text("Player Two tried to move. This is a Not valid move. Please pick again");
							}
						}
						move.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
						if (theme == 1) {
							game_title.setFill(Color.SLATEBLUE);
							move.setStyle("-fx-text-inner-color: brown");
						} else {
							move.setStyle("-fx-text-inner-color: white");
							move.setFill(Color.WHITE);
						}
						game_action_text.add(move);
						move_counter++;
						game_bpane.setBottom(game_action_text.get(move_counter));			
					}
				});
				game_gridpane.add(buttons.get(i).get(j).getButton(), i, j);
			}
		}
		
		setGameTheme(0, game_themes0, buttons, game_menubar, primaryStage, display_player_turn, game_bpane);
		setGameTheme(1, game_themes1, buttons, game_menubar, primaryStage, display_player_turn, game_bpane);
		setGameTheme(2, game_themes2, buttons, game_menubar, primaryStage, display_player_turn, game_bpane);
	
		game_scene = new Scene(game_theme_style(game_bpane, game_title, game_gridpane, game_menubar, display_player_turn, game_action_text), 650, 650);
	}
	
	public VBox game_theme_style(BorderPane game_bpane, Text game_title, GridPane game_gridpane, MenuBar game_menubar, TextField display_player_turn, ArrayList<Text> game_action_text) {
		
		theme = 0;
		
		Image game_bimage = new Image("beach_table.jpg", 650, 650, false, false);
		BackgroundImage game_background_image = new BackgroundImage(game_bimage, BackgroundRepeat.NO_REPEAT,
		BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
		Background game_background = new Background(game_background_image);
		
		game_bpane.setAlignment(game_title, Pos.TOP_CENTER);
		game_bpane.setTop(game_title);
		game_bpane.setAlignment(game_gridpane, Pos.CENTER);
		game_gridpane.setAlignment(Pos.CENTER);
		game_bpane.setCenter(game_gridpane);
		game_bpane.setAlignment(game_menubar, Pos.TOP_RIGHT);
		game_bpane.setRight(game_menubar);
		game_bpane.setAlignment(display_player_turn, Pos.TOP_LEFT);
		game_bpane.setLeft(display_player_turn);
		game_bpane.setBottom(game_action_text.get(move_counter));
		game_bpane.setBackground(game_background);
		
		game_bpane.setPadding(new Insets(10, 20, 10, 20));
		game_bpane.setPrefSize(650, 650);
		game_screen_vbox = new VBox(game_bpane);

		return game_screen_vbox;
		
	}
	
	// Loading Scene for when Theme One is selected
		public Scene dbz_Loading(Stage primaryStage) {
			
			theme = 1;
			
			Image dbz_bimage = new Image("goku_spiritbomb.jpg", 650, 650, false, false);
			BackgroundImage dbz_background_image = new BackgroundImage(dbz_bimage,
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
			
			Background dbz_background = new Background(dbz_background_image);
			
			Text dbz_load_txt = new Text("GIVE ME YOUR ENERGY!");
			setRandFont(dbz_load_txt);
			dbz_load_txt.setFill(Color.BLUE);
			dbz_load_txt.setStyle("-fx-text-inner-color: white");
			
			InnerShadow inner = new InnerShadow();
			inner.setOffsetX(5.0);
			inner.setOffsetY(5.0);
			dbz_load_txt.setEffect(inner);
			dbz_load_txt.setTextAlignment(TextAlignment.CENTER);
			
			BorderPane dbz_bpane = new BorderPane();
			dbz_bpane.setAlignment(dbz_load_txt, Pos.BOTTOM_CENTER);
			dbz_bpane.setCenter(dbz_load_txt);
			
			VBox dbz_load_vbox = new VBox(dbz_bpane);
			dbz_load_vbox.setBackground(dbz_background);
			
			
			dbz_load_scene = new Scene(dbz_load_vbox, 650, 650);
			primaryStage.setScene(dbz_load_scene);
			primaryStage.show();
			
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(event -> {primaryStage.setScene(game_scene);});
			pause.play();
			
			return dbz_load_scene;
		}
		
		// Loading Scene for when Theme Two is selected
		public Scene marvel_Loading(Stage primaryStage) {
			
			theme = 2;
			
			Image marvel_bimage = new Image("marvel_dc.jpg", 650, 650, false, false);
			BackgroundImage marvel_background_image = new BackgroundImage(marvel_bimage,
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
			
			Background marvel_background = new Background(marvel_background_image);
			
			Text marvel_load_txt = new Text("I DONT EVEN KNOW WHO YOU ARE..");
			setRandFont(marvel_load_txt);
			marvel_load_txt.setFill(Color.RED);
			InnerShadow inner = new InnerShadow();
			inner.setOffsetX(10.0);
			inner.setOffsetY(10.0);
			marvel_load_txt.setEffect(inner);
			marvel_load_txt.setTextAlignment(TextAlignment.CENTER);
			
			BorderPane marvel_bpane = new BorderPane();
			marvel_bpane.setAlignment(marvel_load_txt, Pos.BOTTOM_CENTER);
			marvel_bpane.setCenter(marvel_load_txt);
			
			VBox marvel_load_vbox = new VBox(marvel_bpane);
			marvel_load_vbox.setBackground(marvel_background);
			
			
			marvel_load_scene = new Scene(marvel_load_vbox, 650, 650);
			primaryStage.setScene(marvel_load_scene);
			primaryStage.show();
			
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(event -> {primaryStage.setScene(game_scene);});
			pause.play();
			
			return marvel_load_scene;
		}

		// Loading Scene for when the Original Theme is selected
		public Scene beach_Loading(Stage primaryStage) {
		
			theme = 0;
			
			Image beach_bimage = new Image("beach_load.jpg", 650, 650, false, false);
			BackgroundImage beach_background_image = new BackgroundImage(beach_bimage,
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
			
			Background beach_background = new Background(beach_background_image);
			
			Text beach_load_txt = new Text(10, 20, "BACK TO THE GOOD OLD PLACE.... ");
			setRandFont(beach_load_txt);
			beach_load_txt.setFill(Color.WHITE);
			InnerShadow inner = new InnerShadow();
			inner.setOffsetX(5.0);
			inner.setOffsetY(5.0);
			beach_load_txt.setEffect(inner);
			beach_load_txt.setTextAlignment(TextAlignment.CENTER);
				
			BorderPane beach_bpane = new BorderPane();
			beach_bpane.setAlignment(beach_load_txt, Pos.BOTTOM_CENTER);
			beach_bpane.setCenter(beach_load_txt);
			
			VBox beach_load_vbox = new VBox(beach_bpane);
			beach_load_vbox.setBackground(beach_background);
			
			
			beach_load_scene = new Scene(beach_load_vbox, 650, 650);
			primaryStage.setScene(beach_load_scene);
			primaryStage.show();
			
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(event -> {primaryStage.setScene(game_scene);});
			pause.play();
			
			return beach_load_scene;
		}

		// Win_Scene when a player has won
		public void winScreen(Stage primaryStage, boolean blueTeam, boolean tie, ArrayList<Integer> mi, ArrayList<Integer> mj, ArrayList<ArrayList<GameButton>> buttons,
				boolean[] blueTurn, int[] piecesAmount, ArrayList<Text> game_action_text, ArrayList<GameButton> allFour) {
		
			Label win_text;
			if(!blueTeam && !tie) {
				win_text = new Label("CONGRAGULATIONS PLAYER TWO!");
			} else if(blueTeam && !tie){
				win_text = new Label("CONGRAULATIONS PLAYER ONE!");
			} else {
				win_text = new Label("IT WAS A TIE, NOBODY WINS!");
			}
			win_text.setStyle(" -fx-background-color: white");
			win_text.setAlignment(Pos.CENTER);
			
			Image win_bimage;
			
			if(!tie) {
				win_bimage = new Image("win_scene_bg.jpg", 650, 650, false, false);
			} else {
				win_bimage = new Image("win_tie_bg.jpg", 650, 650, false, false);
			}
			
			BackgroundImage win_background_image = new BackgroundImage(win_bimage,
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
			
			Background win_background = new Background(win_background_image);
			
			Button win_scene_exit = new Button("Exit");
			win_scene_exit.setOnAction(e -> Platform.exit());
			Button win_scene_play = new Button("Play Again");
			win_scene_play.setOnAction(e -> {resetGame(mi, mj, buttons, blueTurn, piecesAmount, game_action_text, allFour);
			primaryStage.setScene(game_scene); 
			});

			
			BorderPane win_scene_bp = new BorderPane();
			win_scene_bp.setCenter(win_text);
			win_scene_bp.setAlignment(win_text, Pos.BOTTOM_CENTER);
			win_scene_bp.setLeft(win_scene_exit);
			win_scene_bp.setAlignment(win_scene_exit, Pos.BOTTOM_LEFT);
			win_scene_bp.setRight(win_scene_play);
			win_scene_bp.setAlignment(win_scene_exit, Pos.BOTTOM_RIGHT);
			
			VBox win_scene_vbox = new VBox(win_scene_bp);
			win_scene_vbox.setBackground(win_background);

			win_scene = new Scene(win_scene_vbox, 650, 650);
			primaryStage.setScene(win_scene);
			primaryStage.show();
			
		}
}
// how do I access grid_pane within the win screen, if I cant, I'll just make a copy of the gridPane
