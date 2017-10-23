package main_files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.animation.Interpolator;


public class GamePlayUI extends Application implements Serializable{


	public Tile[][] Board;
	public int PlayerID;
	public GamePlay obj;
	private int cnt = 0;
	private int TotalPlayers;
	private int GridX,GridY;
	
	public GamePlayUI(int Player,int x,int y)
	{
		this.TotalPlayers = Player;
		this.GridX = x;
		this.GridY = y;
		obj = new GamePlay(GridX,GridY,TotalPlayers);
		if(Board==null)
			this.Board = new Tile[x][y];
		PlayerID = 0;
	}
	private Parent createContent(Stage primaryStage) {
		Pane root = new Pane();
        root.setPrefSize(GridY*50, GridX*60);
        for (int p = 0; p < GridX; p++) 
        {
            for (int q = 0; q < GridY; q++) 
            {
            		
    			Tile tile = new Tile(p,q);
                tile.setTranslateX(q * 50);
                tile.setTranslateY(p * 50);
                root.getChildren().add(tile);
                Board[p][q] = tile; 
            }     
     
        }
        Cell[][] T = obj.getGrid();
    	for(int p = 0 ; p<GridX ; p++)
    	{
            for(int q = 0;q<GridY;q++)
    		{
                if(Board[p][q].NumberOfOrbs!=T[p][q].getOrbCount()||Board[p][q].Owner!=T[p][q].getOwner())
                {
        			Board[p][q].NumberOfOrbs = T[p][q].getOrbCount();
        			Board[p][q].Owner = T[p][q].getOwner();
        			if(Board[p][q].getChildren().size()>1)
        				Board[p][q].getChildren().remove(1, Board[p][q].getChildren().size());
                    Group orbGroup = new Group();
        			for(int i=0;i<Board[p][q].NumberOfOrbs;i++)
                	{
                		Sphere Shape = new Sphere(10);
                		if(i==1)
                			Shape.setTranslateX(10);
                		if(i==2)
                			Shape.setTranslateY(10);
                		if(Board[p][q].Owner == 0)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.GREEN); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}
                		else if(Board[p][q].Owner==1)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.RED); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}
                		else if(Board[p][q].Owner==2)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.YELLOW); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}
                		else if(Board[p][q].Owner==3)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.BLUE); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}
                		else if(Board[p][q].Owner==4)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.ALICEBLUE); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}
                		else if(Board[p][q].Owner==5)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.BLACK); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}
                		else if(Board[p][q].Owner==6)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.BROWN); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}
                		else if(Board[p][q].Owner==7)
                		{
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(Color.BEIGE); 
            				Shape.setMaterial(material);
            				orbGroup.getChildren().add(Shape);
                		}                        		
                	}
                    RotateTransition rt = new RotateTransition(Duration.millis(5000), orbGroup);
                    rt.setAutoReverse(false);
                    rt.setCycleCount(Timeline.INDEFINITE);
                    rt.setByAngle(360);
                    rt.setInterpolator(Interpolator.LINEAR);
                    rt.play();
                    Board[p][q].getChildren().add(orbGroup);
                }
    		}
    	}
    	Button Undo = new Button();
    	if(GridX==9)
    	{
    		Undo.setLayoutX(GridX*5);
        	Undo.setLayoutY(GridY*80);
    	}
    	else
    	{
    		Undo.setLayoutX(GridX*20);
        	Undo.setLayoutY(GridY*35);
    	}
    	Undo.setText("Undo");
    	root.getChildren().add(Undo);
    	MenuButton menubutton = new MenuButton("Options");
    	Button Back = new Button("Exit");
    	Button StartAgain = new Button("StartAgain");
    	MenuItem Exit = new MenuItem("",Back);
    	MenuItem Again = new MenuItem("",StartAgain);
    	Exit.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				primaryStage.close();
			}
		});
    	menubutton.getItems().addAll(Exit,Again);
    	if(GridX==9)
    	{
        	menubutton.setLayoutX(GridX*20);
        	menubutton.setLayoutY(GridY*80);
    	}
    	else
    	{
    		menubutton.setLayoutX(GridX*45);
        	menubutton.setLayoutY(GridY*35);
    	}
    	root.getChildren().add(menubutton);
        return root;
}
	public int a = 0;
	@Override
	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setScene(new Scene(createContent(primaryStage)));
	     primaryStage.show();
	     if(a==1)
	    	 primaryStage.close();
	    
	}
	public static void main(String[] args)
	{
		launch(args);
	}
	public class Tile extends StackPane implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int Owner=-1,NumberOfOrbs=0,x,y;
		public Tile(int x,int y)
		{
			Rectangle border = new Rectangle(50, 50);
			this.x = x;
			this.y = y;
            border.setFill(null);
            border.setStroke(Color.BLACK);
            this.getChildren().add(border);
            this.setOnMouseClicked(event -> {
                while(!obj.isInGame(PlayerID))
                {
                    PlayerID = (PlayerID+1)%TotalPlayers;
                }
            	if(Board[x][y].Owner==-1||PlayerID==Board[x][y].Owner)
            	{
            		obj.takeTurn(PlayerID, x, y);
                	Cell[][] T = obj.getGrid();
                	for(int p = 0 ; p<GridX ; p++)
                	{
                        for(int q = 0;q<GridY;q++)
                		{
                            if(Board[p][q].NumberOfOrbs!=T[p][q].getOrbCount()||Board[p][q].Owner!=T[p][q].getOwner())
                            {
                    			Board[p][q].NumberOfOrbs = T[p][q].getOrbCount();
                    			Board[p][q].Owner = T[p][q].getOwner();
                    			if(Board[p][q].getChildren().size()>1)
                    				Board[p][q].getChildren().remove(1, Board[p][q].getChildren().size());
                                Group orbGroup = new Group();
                    			for(int i=0;i<Board[p][q].NumberOfOrbs;i++)
                            	{
                            		Sphere Shape = new Sphere(10);
                            		if(i==1)
                            			Shape.setTranslateX(10);
                            		if(i==2)
                            			Shape.setTranslateY(10);
                            		if(Board[p][q].Owner == 0)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.GREEN); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}
                            		else if(Board[p][q].Owner==1)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.RED); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}
                            		else if(Board[p][q].Owner==2)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.YELLOW); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}
                            		else if(Board[p][q].Owner==3)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.BLUE); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}
                            		else if(Board[p][q].Owner==4)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.ALICEBLUE); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}
                            		else if(Board[p][q].Owner==5)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.BLACK); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}
                            		else if(Board[p][q].Owner==6)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.BROWN); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}
                            		else if(Board[p][q].Owner==7)
                            		{
                            			PhongMaterial material = new PhongMaterial();  
                        				material.setDiffuseColor(Color.BEIGE); 
                        				Shape.setMaterial(material);
                        				orbGroup.getChildren().add(Shape);
                            		}                        		
                            	}
                                RotateTransition rt = new RotateTransition(Duration.millis(5000), orbGroup);
                                rt.setAutoReverse(false);
                                rt.setCycleCount(Timeline.INDEFINITE);
                                rt.setByAngle(360);
                                rt.setInterpolator(Interpolator.LINEAR);
                                rt.play();
                                Board[p][q].getChildren().add(orbGroup);
                            }
                		}
                	}
                	if(obj.isWinner()&&obj.eachPlayerMovedOnce())
                	{
                		System.out.println("winner");
                		cnt = 1;
                		a=1;
                		
                	}
                	else
                	{
                		MainPage.getButton().setVisible(true);
                	}
                	PlayerID = (PlayerID+1)%TotalPlayers;
                	try {
						serialise();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                   
            	}
            });
		}
	}
	public void serialise() throws IOException
	{
		ObjectOutputStream out = null;
		try
		{
			out = new ObjectOutputStream(new FileOutputStream("./in.txt"));
			out.writeObject(this);
		}
		finally
		{
			out.close();
		}
	}
	public static GamePlayUI deserialise() throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(new FileInputStream("./in.txt"));
			return (GamePlayUI) in.readObject();
	
		}
		finally
		{
			in.close();
		}
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}