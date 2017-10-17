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

public class GamePlayUI extends Application{


	private Tile[][] Board = new Tile[9][6];
	private int PlayerID = 0;
	private GamePlay obj;
	private Pane root = new Pane();
	private int cnt = 1;
	
	private Parent createContent() {
        root.setPrefSize(600, 600);
        obj = new GamePlay(9,6,2);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
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
		 primaryStage.setScene(new Scene(createContent()));
	     primaryStage.show();
	    
	}
	public static void main(String[] args)
	{
		launch(args);
	}
	private class Tile extends StackPane
	{
		private int Owner=-1,NumberOfOrbs=0,x,y;
		public Tile()
		{
			Rectangle border = new Rectangle(50, 50);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            getChildren().add(border);
            setOnMouseClicked(event -> {
            	if(Board[x][y].Owner==-1||PlayerID==Board[x][y].Owner)
            	{
            		obj.takeTurn(PlayerID, x, y);
                	Cell[][] Temp = obj.getGrid();
                	for(int p = 0 ; p<9 ; p++)
                	{
                		System.out.println();
                		for(int q = 0;q<6;q++)
                		{
                			Board[p][q].NumberOfOrbs = Temp[p][q].getOrbCount();
                			Board[p][q].Owner = Temp[p][q].getOwner();
                			if(Board[p][q].getChildren().size()>1)
                				Board[p][q].getChildren().remove(1, Board[p][q].getChildren().size());
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
                    				Board[p][q].getChildren().add(Shape);
                        		}
                        		else if(Board[p][q].Owner==1)
                        		{
                        			PhongMaterial material = new PhongMaterial();  
                    				material.setDiffuseColor(Color.RED); 
                    				Shape.setMaterial(material);
                    				Board[p][q].getChildren().add(Shape);
                        		}
                        		
                        	}
                		}
                	}
                	if(obj.isWinner()&&cnt>=2)
                	{
                		System.out.println("winner");
                		Platform.exit();
                	}
                	cnt++;
                	System.out.println();
                	System.out.println();
                	PlayerID = (PlayerID+1)%2;
            	}
            });
		}
	}
}

