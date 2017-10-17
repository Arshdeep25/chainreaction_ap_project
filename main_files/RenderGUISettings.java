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
import javafx.stage.*;;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.effect.*;
import javafx.geometry.*;



public class RenderGUISettings extends Application{

	private Color[] playerColors = new Color[8];
	//private Color[] orbColors;

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
				orb.setOnMousePressed(new orbColorChangeEvent(orb, orbPane, orbColors));
				orbPane.add(orb, 2+j, i);
			}
			Text colorText = new Text("N/A");
			colorText.getStyleClass().add("playername");
			orbPane.add(colorText, 10, i);

		}
		root.getChildren().add(orbPane);
		Button doneButton = new Button("DONE");
		doneButton.getStyleClass().add("donebtn");
		doneButton.setTranslateX(400);
		doneButton.setTranslateY(600);
		doneButton.setOnAction(new doneButtonEvent(orbPane, orbColors, primaryStage));
		root.getChildren().add(doneButton);
		Scene scene = new Scene(root, 700, 700);
		scene.getStylesheets().add("style/Settings.css");
		scene.setFill(Color.rgb(18, 55, 114, 0.99));
		primaryStage.setTitle("Settings");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void render()
	{
		launch();
	}
	
}

class doneButtonEvent implements EventHandler<ActionEvent>
{
	GridPane orbPane;
	Color[] selectedColors;
	Color[] orbColors;
	Stage stage;
	public doneButtonEvent(GridPane orbPane, Color[] orbColors, Stage stage)
	{
		this.orbPane = orbPane;
		this.selectedColors = new Color[8];
		this.orbColors = orbColors;
		this.stage = stage;
	}
	@Override
	public void handle(ActionEvent event)
	{
	    int cntr = 0;
	    boolean[] selected = new boolean[8];
	    int j=0, i=0;
	    for(Node orbs: orbPane.getChildren())
	    {
	    	if(orbs.getClass().getName().equals("javafx.scene.shape.Sphere"))
	    	{
    			Sphere orbs_s = (Sphere)orbs;
    			PhongMaterial x = (PhongMaterial)orbs_s.getMaterial();
				if(x.getDiffuseColor().equals(Color.rgb(180, 180, 180)))
				{
					selected[i] = true;
					selectedColors[j] = x.getDiffuseColor();
				}
	    		i++;
	    		if(i==8)
	    		{
	    			j++;
	    		}
	    		i%=8;
	    	}
	    	else
	    	{
	    		if(orbs.getClass().getName().equals("javafx.scene.text.Text"))
	    		{

	    		}
	    	}
	    }
	    for(i=0; i<8; i++)
	    {
	    	if(selectedColors[i]==null)
	    	{
	    		for(j=0; j<8; j++)
	    		{
	    			if(selected[j]!=true)
	    			{
	    				selectedColors[i] = orbColors[j];
	    				selected[j] = true;
	    			}
	    		}
	    	}
	    }
	    this.writeColorToFile(selectedColors);
	    this.stage.close();
	}

	private void writeColorToFile(Color[] selectedColors)
	{
		try
		{
			//PrintWriter w = new PrintWriter("./data_files/PlayerColors.txt", "UTF-8");
		    for(int i=0; i<8; i++)
		    {
		    	//w.println("Player "+(i+1)+" "+(selectedColors[i].getRed()*255)+" "+(selectedColors[i].getGreen()*255)+" "+(selectedColors[i].getBlue()*255));
		    	System.out.println("qwe");
		    	System.out.println(selectedColors[i+1].getRed());
		    	System.out.println("asd");
		    }
		    System.out.println("asdffsdfgsd");
		    //w.close();
		}
		catch(Exception e)
		{

		}
	}
}

class orbColorChangeEvent implements EventHandler<MouseEvent>
{
	private Sphere orb;
	private GridPane orbPane;
	private Color[] orbColors;
	public orbColorChangeEvent(Sphere orb, GridPane orbPane, Color[] orbColors)
	{
		this.orb = orb;
		this.orbPane = orbPane;
		this.orbColors = orbColors;
	}

	@Override 
	public void handle(MouseEvent e) 
	{ 
		//System.out.println("Selected");
		for(Node orbs: orbPane.getChildren())
		{
			if(orbs.getClass().getName().equals("javafx.scene.shape.Sphere") && orbPane.getColumnIndex(orbs)==orbPane.getColumnIndex(orb))
			{
				Sphere orbs_s = (Sphere)orbs;
				PhongMaterial x = (PhongMaterial)orbs_s.getMaterial();
				if(x.getDiffuseColor().equals(Color.rgb(180, 180, 180)))
				{
					System.out.println("nahi ho paega");
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
					//System.out.println(orbs_s.getClass().getName().equals("javafx.scene.text.Text"));
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
	} 
}