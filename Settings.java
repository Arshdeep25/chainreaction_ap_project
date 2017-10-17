package main_files;
import javafx.scene.Node;
import main_files.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.effect.*;
import javafx.geometry.*;



public class Settings extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Text mainHeading = new Text("SETTINGS");
		mainHeading.setX(20); 
		mainHeading.setY(40);
		mainHeading.getStyleClass().add("text");
		root.getChildren().add(mainHeading);
		Text subHeading = new Text("Please select color of the respective players.");
		subHeading.setX(20); 
		subHeading.setY(70);
		subHeading.getStyleClass().add("subText");
		root.getChildren().add(subHeading);

		Color[] orbColors = new Color[8];
		orbColors[0] = Color.rgb(216, 221, 115);
		orbColors[1] = Color.rgb(32, 86, 173);
		orbColors[2] = Color.rgb(31, 130, 42);
		orbColors[3] = Color.rgb(186, 26, 14);
		orbColors[4] = Color.rgb(150, 121, 16);
		orbColors[5] = Color.rgb(16, 129, 150);
		orbColors[6] = Color.rgb(96, 16, 150);
		orbColors[7] = Color.rgb(150, 16, 94);
		
		GridPane orbPane = new GridPane();
		orbPane.setMinSize(400, 400);
		orbPane.setPadding(new Insets(20));
		orbPane.setHgap(20);
		orbPane.setVgap(20);
		orbPane.setAlignment(Pos.CENTER);
		orbPane.setTranslateX(50);
		orbPane.setTranslateY(150);

		for(int i=0; i<8; i++)
		{
			Text playerName = new Text("Player "+(i+1));
			playerName.getStyleClass().add("playername");
			orbPane.add(playerName, 0, i);
			for(int j=0; j<8; j++)
			{
				Sphere orb = new Sphere();
				orb.setRadius(10);
				PhongMaterial material = new PhongMaterial();  
				material.setDiffuseColor(orbColors[j]); 
				orb.setMaterial(material);
				EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
					@Override 
						public void handle(MouseEvent e) { 
						System.out.println("Selected");
						for(Node orbs: orbPane.getChildren())
						{
							if(orbs.getClass().getName().equals("javafx.scene.shape.Sphere") && orbPane.getColumnIndex(orbs)==orbPane.getColumnIndex(orb))
							{
								Sphere orbs_s = (Sphere)orbs;
								PhongMaterial x = (PhongMaterial)orbs_s.getMaterial();
								if(x.getDiffuseColor().equals(Color.rgb(180, 180, 180)))
								{
									System.out.print("nahi ho paega");
									return;
								}
								
							}
						}
						for(Node orbs: orbPane.getChildren())
						{
							if(orbs.getClass().getName().equals("javafx.scene.shape.Sphere") && orbPane.getRowIndex(orbs)==orbPane.getRowIndex(orb))
							{
								Sphere orbs_s = (Sphere)orbs;
								PhongMaterial setOrigMat = new PhongMaterial();
								setOrigMat.setDiffuseColor(orbColors[orbPane.getColumnIndex(orbs_s)-2]); 
								orbs_s.setMaterial(setOrigMat);
								System.out.println(orbs_s.getClass().getName().equals("javafx.scene.text.Text"));
							}
						}
						PhongMaterial newMaterial = new PhongMaterial();
						material.setDiffuseColor(Color.rgb(180, 180, 180));
						orb.setMaterial(material);
					} 
				};
				//Registering the event filter 
				orb.setOnMousePressed(eventHandler);
				orbPane.add(orb, 2+j, i);
			}
			Text colorText = new Text("N/A");
			colorText.getStyleClass().add("playername");
			orbPane.add(colorText, 10, i);

		}
		 
		root.getChildren().add(orbPane);
		Scene scene = new Scene(root,600, 600);
		scene.getStylesheets().add("style/Settings.css");
		scene.setFill(Color.rgb(18, 55, 114));
		primaryStage.setTitle("Settings");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
}