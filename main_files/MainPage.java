package main_files;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage extends Application{

	
	private Pane root = new Pane(); 
	private static Button Resume_btn;
	public static Stage var;
	public static Settings color = new Settings();
	public static int Undo_button = 0;
	public static boolean Winner = false;
	public static Button getButton()
	{
		return Resume_btn;
	}
	private Parent CreateContent(Stage stage)
	{
		root.setPrefSize(400, 600);
		
		var = stage;
		
		root.getStyleClass().add("Back");
		
//		root.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, CornerRadii.EMPTY, Insets.EMPTY)));
		
		Text Heading = new Text();
		Heading.setText("Chain Reaction");
		Heading.setX(70);
		Heading.setY(50);
		Heading.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		root.getChildren().add(Heading);
		
		Text PlayerNumber = new Text();
		PlayerNumber.setText("Number of Players ");
		PlayerNumber.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
//		PlayerNumber.setFill(Color.WHITE);
		PlayerNumber.setX(120);
		PlayerNumber.setY(130);
		root.getChildren().add(PlayerNumber);
		
		ToggleGroup group = new ToggleGroup();
		
		ToggleButton P2,P3,P4,P5,P6,P7,P8;
		
		P2 = new ToggleButton("2");
		P2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		P2.setLayoutX(20);
		P2.setLayoutY(150);
		P2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		P2.getStyleClass().add("Toggle");
		P2.setToggleGroup(group);

		P3 = new ToggleButton("3");
		P3.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		P3.setLayoutX(75);
		P3.setLayoutY(150);
		P3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		P3.getStyleClass().add("Toggle");
		P3.setToggleGroup(group);
		
		P4 = new ToggleButton("4");
		P4.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		P4.setLayoutX(130);
		P4.setLayoutY(150);
		P4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		P4.getStyleClass().add("Toggle");
		P4.setToggleGroup(group);

		P5 = new ToggleButton("5");
		P5.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		P5.setLayoutX(185);
		P5.setLayoutY(150);
		P5.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		P5.getStyleClass().add("Toggle");
		P5.setToggleGroup(group);

		P6 = new ToggleButton("6");
		P6.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		P6.setLayoutX(240);
		P6.setLayoutY(150);
		P6.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		P6.getStyleClass().add("Toggle");
		P6.setToggleGroup(group);

		P7 = new ToggleButton("7");
		P7.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		P7.setLayoutX(295);
		P7.setLayoutY(150);
		P7.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		P7.getStyleClass().add("Toggle");
		P7.setToggleGroup(group);

		P8 = new ToggleButton("8");
		P8.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		P8.setLayoutX(350);
		P8.setLayoutY(150);
		P8.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		P8.getStyleClass().add("Toggle");
		P8.setToggleGroup(group);

		
		
		root.getChildren().addAll(P2,P3,P4,P5,P6,P7,P8);
		
		Text Size = new Text();
		Size.setText("Choose Grid Size ");
		Size.setX(125);
		Size.setY(220);
		Size.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
//		Size.setFill(Color.WHITE);
		root.getChildren().add(Size);
		
		ToggleGroup G = new ToggleGroup();
		ToggleButton G1,G2;
		
		G1 = new ToggleButton("9 X 6");
		G1.setLayoutX(120);
		G1.setLayoutY(240);
		G1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		G1.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		G1.getStyleClass().add("Toggle");
		G1.setToggleGroup(G);
		
		G2 = new ToggleButton("15 X 10");
		G2.setLayoutX(210);
		G2.setLayoutY(240);
		G2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		G2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		G2.getStyleClass().add("Toggle");
		G2.setToggleGroup(G);
		
		root.getChildren().addAll(G1,G2);
		
		group.selectToggle(P2);
		G.selectToggle(G1);
		
		Button Start_btn = new Button();
		Start_btn.setText("START GAME");
		Start_btn.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Start_btn.setLayoutX(100);
		Start_btn.setLayoutY(300);
		Start_btn.setPrefSize(200, 50);
		Start_btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//		Start_btn.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		Start_btn.getStyleClass().add("Start_btn");
		
		Start_btn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event) 
			{
				String s = group.getSelectedToggle().toString();
				String s1 = G.getSelectedToggle().toString();
				int a = Character.getNumericValue(s.charAt(s.length()-2));
				int b = Character.getNumericValue(s1.charAt(s1.length()-2));
				int x,y;
				if(b==0)
				 {
					 x = 15;
					 y = 10;
				 }
				 else
				 {
					 x = 9;
					 y = 6;
				 }
				 Platform.runLater(new Runnable() {
				       public void run() {             
				           try {
							GamePlayUI gameStart = new GamePlayUI(a,x,y);
							gameStart.start(stage);
						} catch (Exception e) {
							e.getMessage();
							e.printStackTrace();
							System.out.println("dsfgsdfg");
						}
				       }
				    });
			}
		});
		root.getChildren().add(Start_btn);
		
		Button Setting_btn = new Button();
		Setting_btn.setText("SETTINGS");
		Setting_btn.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Setting_btn.setLayoutX(100);
		Setting_btn.setLayoutY(400);
		Setting_btn.setPrefSize(200, 50);
		Setting_btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Setting_btn.getStyleClass().add("Start_btn");
		Setting_btn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event) 
			{
				 Platform.runLater(new Runnable() {
				       public void run() {             
				           try {
							RenderGUISettings Settings = new RenderGUISettings();
							color = new Settings();
							color = Settings.render(stage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.getMessage();
							e.printStackTrace();
						}
				       }
				    });
				
			}
		});
		root.getChildren().add(Setting_btn);
		
		Resume_btn = new Button();
		Resume_btn.setText("RESUME GAME");
		Resume_btn.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		Resume_btn.setLayoutX(100);
		Resume_btn.setLayoutY(500);
		Resume_btn.setPrefSize(200, 50);
		Resume_btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Resume_btn.getStyleClass().add("Start_btn");
		GamePlayUI gameStart = null;
		try {
			 gameStart = GamePlayUI.deserialise("in");
		} catch (ClassNotFoundException | IOException e1) {
			Resume_btn.setVisible(false);
		}
		if(gameStart!=null && gameStart.winnerFound)
		{
			Resume_btn.setVisible(false);
		}
		
		
		
		Resume_btn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event) 
			{
				 Platform.runLater(new Runnable() {
				       public void run() {             
				           try {
					        	GamePlayUI gameStart;
				        	if(Undo_button == 0)
				   				gameStart = GamePlayUI.deserialise("in");
				   			else
				   			{
				   				gameStart = GamePlayUI.deserialise("in2");
				   				System.out.println("babu mushao kaisse ho");
				   			}
							gameStart.start(var);
						} catch (Exception e) {
							e.getMessage();
							e.printStackTrace();
						}
				       }
				    });
				// TODO Auto-generated method stub
				
			}
		});
		root.getStylesheets().add("style/Settings.css");
		root.getChildren().add(Resume_btn);
		return root;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CHAIN REACTION");
		primaryStage.setScene(new Scene(CreateContent(primaryStage)));
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}