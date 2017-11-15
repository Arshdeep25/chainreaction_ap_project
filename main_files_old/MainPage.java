package main_files;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage extends Application{

	
	private Pane root = new Pane(); 
	private static Button Resume_btn;
	public static Stage var;
	public static Settings color;
	public static int Undo_button = 0;
	
	
	public static Button getButton()
	{
		return Resume_btn;
	}
	
	private Parent CreateContent()
	{
		root.setPrefSize(600, 600);
		
		root.setBackground(new Background(new BackgroundFill(Color.LIGHTCORAL, CornerRadii.EMPTY, Insets.EMPTY)));
		
		Text Heading = new Text();
		Heading.setText("Welcome to the Game");
		Heading.setX(100);
		Heading.setY(50);
		Heading.setFont(Font.font(36));
		root.getChildren().add(Heading);
		
		Text PlayerNumber = new Text();
		PlayerNumber.setText("Select the number of players ");
		PlayerNumber.setX(10);
		PlayerNumber.setY(200);
		root.getChildren().add(PlayerNumber);
		
		final ToggleGroup group = new ToggleGroup();
		ToggleButton P1,P2,P3,P4,P5,P6,P7,P8;
		
		P1 = new ToggleButton("1");
		P1.setLayoutX(200);
		P1.setLayoutY(187);
		P1.setToggleGroup(group);
		
		P2 = new ToggleButton("2");
		P2.setLayoutX(250);
		P2.setLayoutY(187);
		P2.setToggleGroup(group);
		
		P3 = new ToggleButton("3");
		P3.setLayoutX(300);
		P3.setLayoutY(187);
		P3.setToggleGroup(group);
		
		P4 = new ToggleButton("4");
		P4.setLayoutX(350);
		P4.setLayoutY(187);
		P4.setToggleGroup(group);

		P5 = new ToggleButton("5");
		P5.setLayoutX(400);
		P5.setLayoutY(187);
		P5.setToggleGroup(group);

		P6 = new ToggleButton("6");
		P6.setLayoutX(450);
		P6.setLayoutY(187);
		P6.setToggleGroup(group);

		P7 = new ToggleButton("7");
		P7.setLayoutX(500);
		P7.setLayoutY(187);
		P7.setToggleGroup(group);

		P8 = new ToggleButton("8");
		P8.setLayoutX(550);
		P8.setLayoutY(187);
		P8.setToggleGroup(group);

		root.getChildren().addAll(P1,P2,P3,P4,P5,P6,P7,P8);
		
		Text Size = new Text();
		Size.setText("Choose Grid Size ");
		Size.setX(10);
		Size.setY(250);
		root.getChildren().add(Size);
		
		ToggleGroup G = new ToggleGroup();
		ToggleButton G1,G2;
		
		G1 = new ToggleButton("9 X 6");
		G1.setLayoutX(200);
		G1.setLayoutY(240);
		G1.setToggleGroup(G);
		
		G2 = new ToggleButton("15 X 10");
		G2.setLayoutX(300);
		G2.setLayoutY(240);
		G2.setToggleGroup(G);
		
		root.getChildren().addAll(G1,G2);
		
		group.selectToggle(P2);
		G.selectToggle(G1);
		
		Button Start_btn = new Button();
		Start_btn.setText("START GAME");
		Start_btn.setLayoutX(220);
		Start_btn.setLayoutY(300);
		Start_btn.setPrefSize(200, 50);
		Start_btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Start_btn.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
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
					 x = 10;
					 y = 15;
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
							var = new Stage();
							gameStart.start(var);
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
		Setting_btn.setLayoutX(220);
		Setting_btn.setLayoutY(400);
		Setting_btn.setPrefSize(200, 50);
		Setting_btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Setting_btn.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
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
							color = Settings.render(new Stage());
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
		Resume_btn.setLayoutX(220);
		Resume_btn.setLayoutY(500);
		Resume_btn.setPrefSize(200, 50);
		Resume_btn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		Resume_btn.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		GamePlayUI gameStart;
		try {
			if(Undo_button == 0)
				gameStart = GamePlayUI.deserialise("in");
			else
				gameStart = GamePlayUI.deserialise("in2");
			if(gameStart.getCnt()==1)
			{
				Resume_btn.setVisible(false);
			}
		} catch (Exception e1) {
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
				   				gameStart = GamePlayUI.deserialise("in2");
				        	var = new Stage();
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
		root.getChildren().add(Resume_btn);
		
		
		return root;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CHAIN REACTION");
		primaryStage.setScene(new Scene(CreateContent()));
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
}