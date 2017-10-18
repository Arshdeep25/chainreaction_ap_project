package main_files;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.animation.Interpolator;


public class GamePlayUI extends Application{


	private Tile[][] Board;
	private int PlayerID = 0;
	private GamePlay obj;
	private Pane root = new Pane();
	private int cnt = 1;
	private int TotalPlayers;
	private int GridX,GridY;
	
	public GamePlayUI(int Player,int x,int y)
	{
		this.TotalPlayers = Player;
		this.GridX = x;
		this.GridY = y;
		this.Board = new Tile[x][y];
	}
	private Parent createContent() {
        root.setPrefSize(GridY*50, GridX*50);
        obj = new GamePlay(GridX,GridY,TotalPlayers);
        for (int i = 0; i < GridX; i++) 
        {
            for (int j = 0; j < GridY; j++) 
            {
                Tile tile = new Tile();
                tile.setTranslateX(j * 50);
                tile.setTranslateY(i * 50);
                tile.x = i;
                tile.y = j;
                root.getChildren().add(tile);

                
                Board[i][j] = tile;
            }
        }
        return root;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        //System.out.println("mvncmnncvdf");
		 primaryStage.setScene(new Scene(createContent()));
	     primaryStage.show();
	    
	}
	public static void main(String[] args)
	{
        //System.out.println("wetert");
		launch(args);
	}
	private class Tile extends StackPane
	{
		public int Owner=-1,NumberOfOrbs=0,x,y;
		public Tile()
		{
			Rectangle border = new Rectangle(50, 50);
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
                	Cell[][] Temp = obj.getGrid();
                	for(int p = 0 ; p<GridX ; p++)
                	{
                		//System.out.println("asdfadsfsd");
                        for(int q = 0;q<GridY;q++)
                		{
                            if(Board[p][q].NumberOfOrbs!=Temp[p][q].getOrbCount()||Board[p][q].Owner!=Temp[p][q].getOwner())
                            {
                    			Board[p][q].NumberOfOrbs = Temp[p][q].getOrbCount();
                    			Board[p][q].Owner = Temp[p][q].getOwner();
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
                		Platform.exit();
                	}
                    PlayerID = (PlayerID+1)%TotalPlayers;
            	}
            });
		}
	}
}

