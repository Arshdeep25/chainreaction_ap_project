package main_files;
import javafx.scene.Node;
import main_files.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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


/**
* This class implents a GUI interface for the Settings page. It gives the 
* user to select the color of their orbs and ensures no clash in the colors.
*
*
* @author Anubhav Jaiswal and Arshdeep Singh Chugh
* @version 1.0
* @since 17 November 2017
*/

public class RenderGUISettings extends Application{

	private Color[] playerColors = new Color[8];
	private Settings setting = new Settings();
	private Color[] orbColors = new Color[8];
	private boolean[] selectedPlayers = new boolean[8];
	public int a,x;
	/**
	* This is the main start function called to initiate the GUI making process.
	* @param primaryStage: the stage on which the game is running.
	* @exception Exception is put to cater all the exceptions.
	*/

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();
		Text subHeading = new Text("Please select color of the respective players");
		subHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		subHeading.setX(13); 
		subHeading.setY(70);
		root.getChildren().add(subHeading);

		GridPane orbPane = new GridPane();
		orbPane.setMinSize(400, 400);
		orbPane.setPadding(new Insets(20));
		orbPane.setHgap(12);
		orbPane.setVgap(20);
		orbPane.setAlignment(Pos.CENTER);
		orbPane.setTranslateX(0);
		orbPane.setTranslateY(150);

		orbColors[0] = Color.rgb(255, 153, 51);
		orbColors[1] = Color.rgb(32, 86, 173);
		orbColors[2] = Color.rgb(31, 130, 42);
		orbColors[3] = Color.rgb(186, 26, 14);
		orbColors[4] = Color.rgb(150, 121, 16);
		orbColors[5] = Color.rgb(16, 129, 150);
		orbColors[6] = Color.rgb(96, 16, 150);
		orbColors[7] = Color.rgb(150, 16, 94);

		for(int i=0; i<8; i++)
		{
			Text playerName = new Text("Player "+(i+1));
			orbPane.add(playerName, 0, i);
			for(int j=0; j<8; j++)
			{
				Sphere orb = new Sphere();
				orb.setRadius(9);
				PhongMaterial material = new PhongMaterial();  
				material.setDiffuseColor(orbColors[j]); 
				orb.setMaterial(material);
				orb.setOnMousePressed(new orbColorChangeEvent(orb, orbPane, orbColors, setting, selectedPlayers));
				orbPane.add(orb, 2+j, i);
			}
			Text colorText = new Text("N/A");
			orbPane.add(colorText, 10, i);

		}
		root.getChildren().add(orbPane);
		Button doneButton = new Button("DONE");
		doneButton.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		doneButton.getStyleClass().add("Start_btn");
		doneButton.setTranslateX(175);
		doneButton.setTranslateY(550);
		doneButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		doneButton.setOnAction(new doneButtonEvent(orbPane, orbColors, primaryStage, setting, selectedPlayers,a,x));
		root.getChildren().add(doneButton);
		Scene scene = new Scene(root, 400, 600);
		scene.getStylesheets().add("style/Settings.css");
		scene.setFill(Color.rgb(208, 211, 212, 0.99));
		primaryStage.setTitle("Settings");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	* This function is used to intiate the varibles and class the start funciton 
	* to initiate the GUI making process. 
	*
	*
	* @param stage : the stage on which the game is running.
	* @param a : The Number of Players Selected from the MainPage.
	* @param x : The Grid Size selected from the MainPage.
	*/
	public void render(Stage stage,int a,int x)
	{
		this.a = a;
		this.x = x;
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

/**
* This class is an event handler for the click on The Done button
*
*
* @author Anubhav Jaiswal and Arshdeep Singh Chugh
* @version 1.0
* @since 17 November 2017
*/

class doneButtonEvent implements EventHandler<ActionEvent>
{
	private GridPane orbPane;
	private Color[] selectedColors;
	private Color[] orbColors;
	private Stage stage;
	private Settings setting;
	private boolean[] selectedPlayers;
	int a,x;

	/**
	* The constructor of the class
	*
	*
	* @param orbPane : The Pane with all the orbs and the text.
	* @param orbColors : The original and default colors.
	* @param stage: the stage on which the game is running.
	* @param setting : The Settings class variable
	* @param selectedPlayers: boolean array of which players colors have been selected.
	* @param a and x : The Players Selected and the grid size respectively from the MainPage.
	*/

	public doneButtonEvent(GridPane orbPane, Color[] orbColors, Stage stage, Settings setting, boolean[] selectedPlayers,int a,int x)
	{
		this.orbPane = orbPane;
		this.selectedColors = new Color[8];
		this.orbColors = orbColors;
		this.stage = stage;
		this.setting = setting;
		this.selectedPlayers = selectedPlayers;
		this.a = a;
		this.x = x;
	}

	/**
	* This the main handle function of the ActionEvent class for handling of the mouseclick.
	*
	* @param event : The mouse event
	*/

	@Override
	public void handle(ActionEvent event)
	{
	    MainPage obj = new MainPage();
	    try {
	    	obj.color = setting.setOthers(orbColors, selectedPlayers);
	    	obj.PlayerSelected = a;
	    	obj.SizeSelected = x;
			obj.start(MainPage.var);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**
* This class handles th click of a specific color of orbs.
*
*
* @author Anubhav Jaiswal and Arshdeep Singh Chugh
* @version 1.0
* @since 17 November 2017
*/

class orbColorChangeEvent implements EventHandler<MouseEvent>
{
	private Sphere orb;
	private GridPane orbPane;
	private Color[] orbColors;
	private Settings setting;
	private boolean[] selectedPlayers;

	/**
	* The constructor of the class
	*
	*
	* @param orb : the refrence if the orb clicked.
	* @param orbPane : The Pane with all the orbs and the text.
	* @param orbColors : The original and default colors.
	* @param setting : The Settings class variable
	* @param selectedPlayers: boolean array of which players colors have been selected.
	*/

	public orbColorChangeEvent(Sphere orb, GridPane orbPane, Color[] orbColors, Settings setting, boolean[] selectedPlayers)
	{
		this.orb = orb;
		this.orbPane = orbPane;
		this.orbColors = orbColors;
		this.setting = setting;
		this.selectedPlayers = selectedPlayers;
	}

	/**
	* This the main handle function of the ActionEvent class for handling of the mouseclick.
	* It checks if the color is slected for any other player. If not, then it selects the color for the player and sets the color of the orb to grey.
	*
	* @param event : The mouse event
	*/

	@Override 
	public void handle(MouseEvent e) 
	{ 
		for(Node orbs: orbPane.getChildren())
		{
			if(orbs.getClass().getName().equals("javafx.scene.shape.Sphere") && orbPane.getColumnIndex(orbs)==orbPane.getColumnIndex(orb))
			{
				Sphere orbs_s = (Sphere)orbs;
				PhongMaterial x = (PhongMaterial)orbs_s.getMaterial();
				if(x.getDiffuseColor().equals(Color.rgb(180, 180, 180)))
				{
					return;
				}
				
			}
		}
		for(Node orbs: orbPane.getChildren())
		{
			if(orbPane.getRowIndex(orbs)==orbPane.getRowIndex(orb))
			{
				if(orbs.getClass().getName().equals("javafx.scene.shape.Sphere"))
				{
					Sphere orbs_s = (Sphere)orbs;
					PhongMaterial setOrigMat = new PhongMaterial();
					setOrigMat.setDiffuseColor(orbColors[orbPane.getColumnIndex(orbs_s)-2]); 
					orbs_s.setMaterial(setOrigMat);
				}
				if(orbs.getClass().getName().equals("javafx.scene.text.Text")&&orbPane.getColumnIndex(orbs)>1)
				{
					Text text = (Text)orbs;
					text.setText("SELECTED");
					PhongMaterial x = (PhongMaterial)orb.getMaterial();
					text.setFill(x.getDiffuseColor());
				}
			}
		}
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.rgb(180, 180, 180));
		orb.setMaterial(material);
		selectedPlayers[orbPane.getRowIndex(orb)] = true;
		setting.setPlayerColor(orbPane.getRowIndex(orb), orbColors[orbPane.getColumnIndex(orb)-2]);
	} 
}
