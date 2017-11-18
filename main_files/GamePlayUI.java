package main_files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.RotateTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import java.util.EventListener;
import javafx.scene.text.*;
import javafx.scene.image.*;

/**
* <h2>Chain Reaction Game</h2>
* The following class implements the total gameplay happening after a new game starts.
* <b> The class is the total implementation of all the processes in the game. </b>
*
*
* @author Anubhav Jaiswal and Arshdeep Singh Chugh
* @version 1.0
* @since 17 November 2017
*/

public class GamePlayUI extends Application implements Serializable{

	/**
	* 2-d array of tiles for the grid.
	*/
	public Tile[][] Board;
	/**
	* 2-d rray of Cell class for the backend functioning of Grid.
	*/
	public volatile Cell[][] Grid;
	/**
	* Keeping the Player Id of the player in move.
	*/
	public int PlayerID;
	private int cnt = 0;
	/**
	* Total number of players.
	*/
	public int TotalPlayers;
	/**
	* The X length of the Grid
	*/
	public int GridX;
	/**
	* The Y length of the Grid. 
	*/
	public int GridY;
	/**
	* The array of Players playing. 
	*/
//	public Player[] players;
	private int movesPlayed;
	protected int undo_var;
	private volatile int animationRunningCounter;
	/**
	* To keep current state of grid. Used in saving the game. 
	*/
	public GamePlay resumeGrid;
	private transient Stage GameplayStage;
	/**
	* To notify that a winne is found. 
	*/
	public boolean winnerFound;
	/**
	* For stopping the code after several recursions. 
	*/
	public int winnerFoundAndOneRecursion = 0;
	
	/**
	* This is the constructor of the class. It accepts 3 parameters:
	*
	* @param Player : The total player of playing the game.
	* @param x : The X axis length of grid
	* @param y : The Y axis length of grid
	*/

	public GamePlayUI(int Player,int x,int y)
	{
		winnerFound = false;
		//this.animationRunningCounter = 0;
		this.TotalPlayers = Player;
		this.GridX = x;
		this.GridY = y;
		if(Board==null)
			this.Board = new Tile[x][y];
		PlayerID = 0;
//		players = new Player[Player];
//		for(int i=0; i<Player; i++)
//		{
//			players[i] = new Player("Player "+(i+1), i+1, "color"+i);
//		}
		resumeGrid = new GamePlay(GridX,GridY,TotalPlayers);
		Grid = new Cell[GridX][GridY];
		for(int i=0; i<GridX; i++)
		{
			for(int j=0; j<GridY; j++)
			{
				if((i==0&&j==0)||(i==0&&j==GridY-1)||(i==GridX-1&&j==0)||(i==GridX-1&&j==GridY-1))
				{
					Grid[i][j] = new Cell(0, -1, 2);
				}
				else if(i==0||i==GridX-1||j==0||j==GridY-1)
				{
					Grid[i][j] = new Cell(0, -1, 3);
				}
				else
				{
					Grid[i][j] = new Cell(0, -1, 4);
				}
			}
		}
	}
	/**
	* The methods creates the whole Game page including the Grid and its contents, 
	* Undo button and the dropdown with <i>Start Again</i> and <i>Back</i> buttons.
	*
	*
	* @param primaryStage : this the refrence to the stage variable of javafx class
	* @return return the Parent variable <i>root</i>, contaning everything displayed in the Game.
	*/
	private Parent createContent(Stage primaryStage) 
	{
		this.animationRunningCounter = 0;
		this.GameplayStage = primaryStage;
		Pane root = new Pane();
		root.getStyleClass().add("UI");
		root.getStylesheets().add("style/Settings.css");
        root.setPrefSize(GridY*50+10, GridX*60+10);
        for (int p = 1; p <= GridX; p++) 
        {
            for (int q = 0; q < GridY; q++) 
            {
            		
    			Tile tile = new Tile(p-1,q);
                tile.setTranslateX(q * 50);
                tile.setTranslateY(p * 50);
                root.getChildren().add(tile);
                Board[p-1][q] = tile; 
            }     
     
        }
    	Cell[][] T = resumeGrid.getBack_Grid();
    	for(int p = 0 ; p<GridX ; p++)
    	{
            for(int q = 0;q<GridY;q++)
    		{
                if(Board[p][q].NumberOfOrbs!=T[p][q].getOrbCount()||Board[p][q].Owner!=T[p][q].getOwner())
                {
        			Board[p][q].NumberOfOrbs = T[p][q].getOrbCount();
        			Board[p][q].Owner = T[p][q].getOwner();
        			if(Board[p][q].getChildren().size()>1)
        				Board[p][q].getChildren().remove(6, Board[p][q].getChildren().size());
                    Group orbGroup = new Group();
        			for(int i=0;i<Board[p][q].NumberOfOrbs;i++)
                	{
                		Sphere Shape = new Sphere(10);
                		if(i==1)
                			Shape.setTranslateX(10);
                		if(i==2)
                			Shape.setTranslateY(10);
                		
            			PhongMaterial material = new PhongMaterial();  
        				material.setDiffuseColor(MainPage.color.getAllColors()[Board[p][q].Owner]); 
        				Shape.setMaterial(material);
        				orbGroup.getChildren().add(Shape);
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
//    	if(GridX==9)
//    	{
    		Undo.setLayoutX(GridX*5);
        	Undo.setLayoutY(GridY*1.2);
//    	}
//    	else
//    	{
//    		Undo.setLayoutX(GridX*20);
//        	Undo.setLayoutY(GridY*35);
//    	}
    	Undo.setText("Undo");
    	Undo.getStyleClass().add("MenuButton");
    	Undo.setOnAction(new EventHandler<ActionEvent>()
		{
    		
			@Override
			public void handle(ActionEvent event) {
				
				if(!winnerFound)
				{
					GamePlayUI des = null;
					try {
						des = deserialise("Undo");
					} catch (Exception e) {}
					MainPage.Undo_button = 1;
					undo_var = 1;
					PlayerID = des.PlayerID;
					movesPlayed = des.movesPlayed;
					resumeGrid = des.resumeGrid;
					for(int i=0;i<GridX;i++)
					{
						for(int j=0;j<GridY;j++)
						{
							Grid[i][j].setOrbCount(resumeGrid.getBack_Grid()[i][j].getOrbCount());
							Grid[i][j].setOwner(resumeGrid.getBack_Grid()[i][j].getOwner());
						}
					}
                	int futureCorrectTurn = resumeGrid.nextTurnPlayer(PlayerID);
                	for(int i=0;i<GridX;i++)
					{
						for(int j=0;j<GridY;j++)
						{
							Rectangle Border = (Rectangle) Board[i][j].getChildren().get(0);
							Border.setStroke(MainPage.color.getAllColors()[futureCorrectTurn]);
							Board[i][j].getChildren().remove(0);
							Board[i][j].getChildren().add(0, Border);
							Border = (Rectangle) Board[i][j].getChildren().get(1);
							Border.setStroke(MainPage.color.getAllColors()[futureCorrectTurn]);
							Board[i][j].getChildren().remove(1);
							Board[i][j].getChildren().add(1, Border);
						}
					}
					for(int p = 0 ; p<GridX ; p++)
			    	{
			            for(int q = 0;q<GridY;q++)
			    		{
//			                if(Board[p][q].NumberOfOrbs!=Grid[p][q].getOrbCount()||Board[p][q].Owner!=Grid[p][q].getOwner())
//			                {
			        			Board[p][q].NumberOfOrbs = Grid[p][q].getOrbCount();
			        			Board[p][q].Owner = Grid[p][q].getOwner();
			        			
			        			Board[p][q].getChildren().remove(6, Board[p][q].getChildren().size());
			                    Group orbGroup = new Group();
			        			for(int i=0;i<Board[p][q].NumberOfOrbs;i++)
			                	{
			                		Sphere Shape = new Sphere(10);
			                		if(i==1)
			                			Shape.setTranslateX(10);
			                		if(i==2)
			                			Shape.setTranslateY(10);
			                		
			            			PhongMaterial material = new PhongMaterial();  
			        				material.setDiffuseColor(MainPage.color.getAllColors()[Board[p][q].Owner]); 
			        				Shape.setMaterial(material);
			        				orbGroup.getChildren().add(Shape);
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
				}
				
	
		});
    	root.getChildren().add(Undo);
    	MenuButton menubutton = new MenuButton("Options");
    	menubutton.getStyleClass().add("MenuButton");
    	MenuItem Exit = new MenuItem("Back");
    	Exit.getStyleClass().add("MenuButton");
    	MenuItem Again = new MenuItem("StartAgain");
    	Exit.getStyleClass().add("MenuButton");
    	Exit.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent event)
			{
				MainPage obj = new MainPage();
				try {
					obj.start(MainPage.var);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
    	Again.setOnAction(new EventHandler<ActionEvent>()
    	{

			@Override
			public void handle(ActionEvent event) {
				try {
					GamePlayUI obj = new GamePlayUI(TotalPlayers,GridX,GridY);
					serialise("Resume",obj);
					serialise("Undo",obj);
					obj.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		
   		});
    	menubutton.getItems().addAll(Exit,Again);
//    	if(GridX==9)
//    	{
        	menubutton.setLayoutX(GridX*20);
        	menubutton.setLayoutY(GridY*1.2);
//    	}
//    	else
//    	{
//    		menubutton.setLayoutX(GridX*45);
//        	menubutton.setLayoutY(GridY*35);
//    	}
    	root.getChildren().add(menubutton);
        return root;
	}
	public int a = 0;
	/**
	* This the function to call for the creation of the whole page.
	*
	* @param primaryStage : The stage of the game.
	* @exception Exception is put to cater all the exceptions.
	*/
	@Override
	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setScene(new Scene(createContent(primaryStage)));
	     primaryStage.show();
	    
	}

	/**
	* <b>The Tile Class</b>
	* The class creates and implents every function happening inside a single tile of the grid.
	* The class only has constructor that creates a tiel and all its borders. It also imoplements
	* the method to be followed when a player clicks a specific tile in the grid.<br>
	* It has 4 basic feilds.<br>
	* 1. The Owner of the tile. (-1 if none)
	* 2. The Number of Orbs in the tile.
	* 3. The x position of tile in the grid
	* 4. The y position of tile in the grid
	*
	*
	*
	*
	* @author Anubhav Jaiswal and Arshdeep Singh Chugh
	* @version 1.0
	* @since 17 November 2017
	*/

	public class Tile extends StackPane implements Serializable
	{
		/**
		* The owber of the tile
		*/
		public int Owner = -1;
		/**
		* Number of orbs in the tile.
		*/
		public int NumberOfOrbs = 0;
		/**
		* the x and y coordinates of the tile.
		*/
		public int x, y;
		/**
		* The animation time of an orb.
		*/
		public int transistionTime = 400;

		/**
		* This is the constructor of the class. It accepts 3 parameters:
		*
		* @param x : The X axis postion of the tile in the grid
		* @param y : The Y axis postion of the tile in the grid
		*/

		public Tile(int x,int y)
		{
			Rectangle border = new Rectangle(50, 50);
			this.x = x;
			this.y = y;
            border.setFill(null);
            this.getChildren().add(border);
            Rectangle border1 = new Rectangle(50, 50);
            border.setStroke(MainPage.color.getAllColors()[0]);
			border1.setStroke(MainPage.color.getAllColors()[0]);
            border1.setFill(null);
            border1.setTranslateX(5);
            border1.setTranslateY(5);
            this.getChildren().add(border1);
            Line line1 = new Line(0, 0, 5, 5);
            Line line2 = new Line(0, 0, 5, 5);
            Line line3 = new Line(0, 0, 5, 5);
            Line line4 = new Line(0, 0, 5, 5);
            line1.setTranslateX(-22);
            line1.setTranslateY(-22);
            line2.setTranslateX(27);
            line2.setTranslateY(-22);
            line3.setTranslateX(-22);
            line3.setTranslateY(27);
            line4.setTranslateX(27);
            line4.setTranslateY(27);
            this.getChildren().add(line1);
            this.getChildren().add(line2);
            this.getChildren().add(line3);
            this.getChildren().add(line4);
            
           
            
        	this.setOnMouseClicked(new EventHandler<MouseEvent>() {
        		/**
        		* The function checks if the player is still in the game or not.
        		*
        		* @param PlayerID : the id of the player to be checked.
        		* @return boolean value true if the player is in the game or vide-versa.
        		*/
        		public boolean isInGame(int PlayerID)
				{
					if(eachPlayerMovedOnce())
					{
						if(orbCountPlayer(PlayerID)==0)
						{
							return false;
						}

					}
					return true;
				}
				/**
        		* The function checks if each player has atleast moved once.
        		*
        		* 
        		* @return boolean value true if the each player has moved once or vide-versa.
        		*/
				public boolean eachPlayerMovedOnce()
				{
					if(movesPlayed>=TotalPlayers)
					{
						return true;
					}
					return false;
				}

				/**
        		* The function counts the number of the player.
        		*
        		* @param PlayerID : the id of the player for which the number of orbs are needed.
        		* @return boolean value true if the player is in the game or vide-versa.
        		*/

				public int orbCountPlayer(int PlayerID)
				{
					int count = 0;
					for(int i=0; i<GridX; i++)
					{
						for(int j=0; j<GridY; j++)
						{
							if(Grid[i][j].getOwner()==PlayerID)
							{
								count += Grid[i][j].getOrbCount();
							}
						}
					}
					return count;
				}
				/**
        		* The function makes the cell with the Number of orbs its has and with the color of its owner.
        		* Making a cell means it places a group of Spheres equal to the number of orbs it contains with
        		* the color of the owner of the orb
        		*
        		* @param x : The x coordinate of the tile.
        		* @param y : The y coordinate of the tile.
        		* @param PlayerID : the id of the player to be checked.
        		* @return Nothing.
        		*/
				public void makeBoardCell(int x, int y, int PlayerID)
				{
					Board[x][y].NumberOfOrbs = Grid[x][y].getOrbCount();
        			Board[x][y].Owner = Grid[x][y].getOwner();
        			if(Board[x][y].getChildren().size()>1)
        			{
        				//Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
        			}
        			for(int i=6; i<Board[x][y].getChildren().size();i++)
        			{
        				if(Board[x][y].getChildren().get(i).getClass().toString().equals("class javafx.scene.Group"))
        				{
        					Board[x][y].getChildren().remove(i);
        				}
        			}
                    Group orbGroup = new Group();
        			for(int i=0;i<Grid[x][y].getOrbCount();i++)
                	{
                		Sphere Shape = new Sphere(10);
                		if(i==1)
                			Shape.setTranslateX(10);
                		if(i==2)
                			Shape.setTranslateY(10);
            			PhongMaterial material = new PhongMaterial();  
        				material.setDiffuseColor(MainPage.color.getAllColors()[Board[x][y].Owner]); 
        				Shape.setMaterial(material);
        				orbGroup.getChildren().add(Shape);
                	}
                    RotateTransition rt = new RotateTransition(Duration.millis(5000), orbGroup);
                    rt.setAutoReverse(false);
                    rt.setCycleCount(Timeline.INDEFINITE);
                    rt.setByAngle(360);
                    rt.setInterpolator(Interpolator.LINEAR);
                    rt.play();
                    Board[x][y].getChildren().add(orbGroup);
				}
				/**
        		* This the main handle function of the ActionEvent class for handling of the mouseclick.
        		*
        		* @param e1 : The mouse event
        		* @return Nothing.
        		*/
				@Override 
				public void handle(MouseEvent e1) 
				{
					
					if(animationRunningCounter==0)
					{
						
		                while(!isInGame(PlayerID))
		                {
		                    PlayerID = (PlayerID+1)%TotalPlayers;
		                }
		            	if(Board[x][y].Owner==-1||PlayerID==Board[x][y].Owner)
		            	{
		            		try{
		            			resumeGrid.takeTurn(PlayerID, x, y);
		            		}
		            		catch(StackOverflowError e)
		            		{
		            			System.out.println(e.getMessage());
		            		}
		            		
		            		movesPlayed++;
		            		Grid[x][y].setOwner(PlayerID);
		            		
							Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()+1);
							Board[x][y].Owner = PlayerID;
							makeBoardCell(x, y, PlayerID);
							if(!Grid[x][y].isStable())
		            			stabilizeCell(x, y, PlayerID);
							
		                	PlayerID = (PlayerID+1)%TotalPlayers;
		                	int futureCorrectTurn = resumeGrid.nextTurnPlayer(PlayerID);
		                	for(int i=0;i<GridX;i++)
							{
								for(int j=0;j<GridY;j++)
								{
									Rectangle Border = (Rectangle) Board[i][j].getChildren().get(0);
									Border.setStroke(MainPage.color.getAllColors()[futureCorrectTurn]);
									Board[i][j].getChildren().remove(0);
									Board[i][j].getChildren().add(0, Border);
									Border = (Rectangle) Board[i][j].getChildren().get(1);
									Border.setStroke(MainPage.color.getAllColors()[futureCorrectTurn]);
									Board[i][j].getChildren().remove(1);
									Board[i][j].getChildren().add(1, Border);
								}
							}
		                	
		                	try {
		                		try {
		                			if(undo_var==0)
		                			{
										GamePlayUI obj1 = deserialise("Resume");
										serialise("Undo",obj1);
		                			}
		                			undo_var = 0;
		                			MainPage.Undo_button = 0;
								} catch (Exception e) {
								}
		                		
		                		serialise("Resume",null);
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	}
		            }
		            else
		            {
		            	System.out.println("");
		            }
	            	
	            }
	            /**
        		* Checks if there is a winner in the game or not.
        		* If a winner is found a new stage is opened and a banner is displayed.
        		*
        		* @return Nothing.
        		*/
	            public void isWinner()
				{
					int prevOwner = -1, isAssigned=0;

					
	            	
					for(int i=0; i<GridX; i++)
					{
						for(int j=0; j<GridY; j++)
						{
							if(Grid[i][j].getOwner()!=-1)
							{
								if(isAssigned==0)
								{
									prevOwner = Grid[i][j].getOwner();
									
									isAssigned = 1;
								}
								else if(prevOwner!=Grid[i][j].getOwner())
								{
									return ;
								}
								
							}
						}
					}
					winnerFound = true;
					for(int i=0;i<GridX;i++)
					{
						for(int j=0;j<GridY;j++)
						{
							Board[i][j].setOnMouseClicked(null);
							
						}
					}
					try {
						serialise("Resume",null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Stage WinnerStage = new Stage();
					Pane winnerPane = new Pane();
					winnerPane.setPrefSize(300, 200);

					Image winnerImage = new Image("winnerDeclared.jpg");
					ImageView winnerImageView = new ImageView();
					winnerImageView.setTranslateX(50);
					winnerImageView.setImage(winnerImage);
					winnerImageView.setFitWidth(200);
					winnerImageView.setPreserveRatio(true);
					winnerImageView.setSmooth(true);
					winnerImageView.setCache(true);
					winnerPane.getChildren().add(winnerImageView);

					Text winnerDeclaration = new Text();
					winnerDeclaration.setTranslateY(150);
					winnerDeclaration.setFont(Font.font ("Verdana, cursive", 20));
					winnerDeclaration.setWrappingWidth(300);
					winnerDeclaration.setTextAlignment(TextAlignment.CENTER);
					winnerDeclaration.setText("Congratulations! Player "+(prevOwner+1)+" won the game.");
					winnerPane.getChildren().add(winnerDeclaration);

					Scene winnerScene = new Scene(winnerPane);
					WinnerStage.setScene(winnerScene);
					WinnerStage.show();
					
				}
				/**
        		* The function stabilizes a cell. It checks if a cell/tile contains more orbs than its critical mass.
        		* If it does, it bursts it in its respective way and launches an animation for transition of orbs 
        		* from the cell to side ones. On the end of the animation it checks for a winner makes the cell and then
        		* launches a recursive action for the side cells.
        		*
        		* @param x : The x coordinate of the tile.
        		* @param y : The y coordinate of the tile.
        		* @param PlayerID : the id of the player to be checked.
        		* @return Nothing.
        		*/
	            public void stabilizeCell(int x, int y, int PlayerID)
	            {
	            	
	            	
	            	if(Grid[x][y].isStable())
            		{
            			makeBoardCell(x, y, PlayerID);
            			
            			if(!winnerFound)
	            			isWinner();
            			return;
            		}
            		else if(Grid[x][y].getCriticalMass()==2)
            		{
            			
            			//makeBoardCell(x, y, PlayerID);
            			if(x==0)
            			{
            				if(y==0)
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());

								ObservableList<Node> children = Board[x][y].getChildren();
								Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
								Sphere Shape1 = new Sphere(10);
                    			PhongMaterial material = new PhongMaterial();  
                				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
                				Shape1.setMaterial(material);
                				Sphere Shape2 = new Sphere(10);
                				Shape2.setMaterial(material);
                				Board[x][y].getChildren().addAll(Shape1, Shape2);
                				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
                				tt1.setByX(40f);
                				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
                				tt2.setByY(40f);
                				ParallelTransition transition = new ParallelTransition(tt1, tt2);
                				transition.play();
                				animationRunningCounter += 1;
                				transition.setOnFinished(event->{
                					animationRunningCounter -= 1;
									Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
									Grid[x+1][y].setOwner(PlayerID);
									Board[x+1][y].Owner = PlayerID;
									Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
									Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
									Grid[x][y+1].setOwner(PlayerID);
									Board[x][y+1].Owner = PlayerID;
									Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();
                					Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
                					makeBoardCell(x, y, PlayerID);

	            					
                					if(!winnerFound)
	            						isWinner(); 
	            					else if(winnerFoundAndOneRecursion>64)
	            						return;
	            					else
	            					{
	            						winnerFoundAndOneRecursion +=1;
	            					}
                					stabilizeCell(x+1, y, PlayerID);
                					stabilizeCell(x, y+1, PlayerID);

                				});


            				}
            				else
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());

								ObservableList<Node> children = Board[x][y].getChildren();
								Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
								Sphere Shape1 = new Sphere(10);
                    			PhongMaterial material = new PhongMaterial();  
                				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
                				Shape1.setMaterial(material);
                				Sphere Shape2 = new Sphere(10);
                				Shape2.setMaterial(material);
                				Board[x][y].getChildren().addAll(Shape1, Shape2);
                				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
                				tt1.setByX(-40f);
                				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
                				tt2.setByY(40f);
                				ParallelTransition transition = new ParallelTransition(tt1, tt2);
                				transition.play();
                				animationRunningCounter += 1;
                				transition.setOnFinished(event->{
                					
                					animationRunningCounter -= 1;
									Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
									Grid[x+1][y].setOwner(PlayerID);
									Board[x+1][y].Owner = PlayerID;
									Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
									Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
									Grid[x][y-1].setOwner(PlayerID);
									Board[x][y-1].Owner = PlayerID;
									Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();
                					Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
                					makeBoardCell(x, y, PlayerID);
	            				
	                				if(!winnerFound)
	            						isWinner();
	            					else if(winnerFoundAndOneRecursion>64)
	            						return;
	            					else
	            					{
	            						winnerFoundAndOneRecursion +=1;
	            					}
	                				stabilizeCell(x+1, y, PlayerID);
	                				stabilizeCell(x, y-1, PlayerID);


                				});
            				}
            			}
            			else
            			{
            				if(y==0)
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{	
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());

								ObservableList<Node> children = Board[x][y].getChildren();
								Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
								Sphere Shape1 = new Sphere(10);
                    			PhongMaterial material = new PhongMaterial(); 
                				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
                				Shape1.setMaterial(material);
                				Sphere Shape2 = new Sphere(10);
                				Shape2.setMaterial(material);
                				Board[x][y].getChildren().addAll(Shape1, Shape2);
                				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
                				tt1.setByX(40f);
                				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
                				tt2.setByY(-40f);
                				ParallelTransition transition = new ParallelTransition(tt1, tt2);
                				transition.play();
                				animationRunningCounter += 1;
                				transition.setOnFinished(event->{
                					
                					animationRunningCounter -= 1;
									Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
									Grid[x-1][y].setOwner(PlayerID);
									Board[x-1][y].Owner = PlayerID;
									Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
									Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
									Grid[x][y+1].setOwner(PlayerID);
									Board[x][y+1].Owner = PlayerID;
									Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();
                					Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
                					makeBoardCell(x, y, PlayerID);
	            					
	                				if(!winnerFound)
	            						isWinner();
	            					else if(winnerFoundAndOneRecursion>64)
	            						return;
	            					else
	            					{
	            						winnerFoundAndOneRecursion +=1;
	            					}
	                				stabilizeCell(x-1, y, PlayerID);
	                				stabilizeCell(x, y+1, PlayerID);

                				});
            				}
            				else
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{	
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
								/*Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
								Grid[x-1][y].setOwner(PlayerID);
								Board[x-1][y].Owner = PlayerID;
								Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
								Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
								Grid[x][y-1].setOwner(PlayerID);
								Board[x][y-1].Owner = PlayerID;
								Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();*/

								ObservableList<Node> children = Board[x][y].getChildren();
								Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
								Sphere Shape1 = new Sphere(10);
                    			PhongMaterial material = new PhongMaterial();  
                				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
                				Shape1.setMaterial(material);
                				Sphere Shape2 = new Sphere(10);
                				Shape2.setMaterial(material);
                				Board[x][y].getChildren().addAll(Shape1, Shape2);
                				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
                				tt1.setByX(-40f);
                				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
                				tt2.setByY(-40f);
                				ParallelTransition transition = new ParallelTransition(tt1, tt2);
                				transition.play();
                				animationRunningCounter += 1;
                				transition.setOnFinished(event->{
                					
                					animationRunningCounter -= 1;
									Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
									Grid[x-1][y].setOwner(PlayerID);
									Board[x-1][y].Owner = PlayerID;
									Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
									Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
									Grid[x][y-1].setOwner(PlayerID);
									Board[x][y-1].Owner = PlayerID;
									Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();
                					Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
                					makeBoardCell(x, y, PlayerID);
	            				
	                				if(!winnerFound)
	            						isWinner();
	            					else if(winnerFoundAndOneRecursion>64)
	            						return;
	            					else
	            					{
	            						winnerFoundAndOneRecursion +=1;
	            					}
	                				stabilizeCell(x-1, y, PlayerID);
	                				stabilizeCell(x, y-1, PlayerID);


                				});	
            				}
            			}

            		}
            		else if(Grid[x][y].getCriticalMass()==3)
            		{
            			//makeBoardCell(x, y, PlayerID);
            			if(x==0)
            			{
        					int playerIndex = Board[x][y].Owner;
        					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
        					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
        					{	
        						Grid[x][y].setOwner(-1);
    							Board[x][y].Owner = -1;
    						}
        					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());

							ObservableList<Node> children = Board[x][y].getChildren();
							Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
							Sphere Shape1 = new Sphere(10);
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
            				Shape1.setMaterial(material);
            				Sphere Shape2 = new Sphere(10);
            				Shape2.setMaterial(material);
            				Sphere Shape3 = new Sphere(10);
            				Shape3.setMaterial(material);
            				Board[x][y].getChildren().addAll(Shape1, Shape2, Shape3);
            				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
            				tt1.setByX(-40f);
            				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
            				tt2.setByX(40f);
            				TranslateTransition tt3 = new TranslateTransition(Duration.millis(transistionTime), Shape3);
            				tt3.setByY(40f);
            				ParallelTransition transition = new ParallelTransition(tt1, tt2, tt3);
            				transition.play();
            				animationRunningCounter += 1;
            				transition.setOnFinished(event->{
            					
            					animationRunningCounter -= 1;
								Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
								Grid[x+1][y].setOwner(PlayerID);
								Board[x+1][y].Owner = PlayerID;
								Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
								Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
								Grid[x][y+1].setOwner(PlayerID);
								Board[x][y+1].Owner = PlayerID;
								Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();
								Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
								Grid[x][y-1].setOwner(PlayerID);
								Board[x][y-1].Owner = PlayerID;
								Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();
            					Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
            					makeBoardCell(x, y, PlayerID);
	            			
                				if(!winnerFound)
	            					isWinner();
	            				else if(winnerFoundAndOneRecursion>64)
	            					return;
	            				else
	            				{
	            					winnerFoundAndOneRecursion +=1;
	            				}
                				stabilizeCell(x+1, y, PlayerID);
                				stabilizeCell(x, y+1, PlayerID);
                				stabilizeCell(x, y-1, PlayerID);

            				});

            			}
            			else if(y==0)
            			{
            				int playerIndex = Board[x][y].Owner;
            				int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            				
            				if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            				{	
            					Grid[x][y].setOwner(-1);
            					Board[x][y].Owner = -1;
            				}
            				Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
						

							ObservableList<Node> children = Board[x][y].getChildren();
							Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
							Sphere Shape1 = new Sphere(10);
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
            				Shape1.setMaterial(material);
            				Sphere Shape2 = new Sphere(10);
            				Shape2.setMaterial(material);
            				Sphere Shape3 = new Sphere(10);
            				Shape3.setMaterial(material);
            				Board[x][y].getChildren().addAll(Shape1, Shape2, Shape3);
            				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
            				tt1.setByX(40f);
            				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
            				tt2.setByY(-40f);
            				TranslateTransition tt3 = new TranslateTransition(Duration.millis(transistionTime), Shape3);
            				tt3.setByY(40f);
            				ParallelTransition transition = new ParallelTransition(tt1, tt2, tt3);
            				transition.play();
            				animationRunningCounter += 1;
            				transition.setOnFinished(event->{
            					
            					animationRunningCounter -= 1;
								Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
								Grid[x+1][y].setOwner(PlayerID);
								Board[x+1][y].Owner = PlayerID;
								Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
								Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
								Grid[x-1][y].setOwner(PlayerID);
								Board[x-1][y].Owner = PlayerID;
								Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
								Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
								Grid[x][y+1].setOwner(PlayerID);
								Board[x][y+1].Owner = PlayerID;
								Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();
            					Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
            					makeBoardCell(x, y, PlayerID);
	            			
                				if(!winnerFound)
	            					isWinner();
	            				else if(winnerFoundAndOneRecursion>64)
	            					return;
	            				else
	            				{
	            					winnerFoundAndOneRecursion +=1;
	            				}
                				stabilizeCell(x+1, y, PlayerID);
                				stabilizeCell(x, y+1, PlayerID);
                				stabilizeCell(x-1, y, PlayerID);

            				});
            			}
            			else if(x==GridX-1)
            			{
        					int playerIndex = Board[x][y].Owner;
        					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
        					
        					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            				{	
            					Grid[x][y].setOwner(-1);
        						Board[x][y].Owner = -1;
            				}
            				Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
					

							ObservableList<Node> children = Board[x][y].getChildren();
							Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
							Sphere Shape1 = new Sphere(10);
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
            				Shape1.setMaterial(material);
            				Sphere Shape2 = new Sphere(10);
            				Shape2.setMaterial(material);
            				Sphere Shape3 = new Sphere(10);
            				Shape3.setMaterial(material);
            				Board[x][y].getChildren().addAll(Shape1, Shape2, Shape3);
            				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
            				tt1.setByX(-40f);
            				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
            				tt2.setByX(40f);
            				TranslateTransition tt3 = new TranslateTransition(Duration.millis(transistionTime), Shape3);
            				tt3.setByY(-40f);
            				ParallelTransition transition = new ParallelTransition(tt1, tt2, tt3);
            				transition.play();
            				animationRunningCounter += 1;
            				transition.setOnFinished(event->{
            					
            					animationRunningCounter -= 1;
								Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
								Grid[x-1][y].setOwner(PlayerID);
								Board[x-1][y].Owner = PlayerID;
								Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
								Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
								Grid[x][y+1].setOwner(PlayerID);
								Board[x][y+1].Owner = PlayerID;
								Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();
								Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
								Grid[x][y-1].setOwner(PlayerID);
								Board[x][y-1].Owner = PlayerID;
								Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();
            					Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
            					makeBoardCell(x, y, PlayerID);
	            			
                				if(!winnerFound)
	            					isWinner();
	            				else if(winnerFoundAndOneRecursion>64)
	            					return;
	            				else
	            				{
	            					winnerFoundAndOneRecursion +=1;
	            				}
                				stabilizeCell(x-1, y, PlayerID);
                				stabilizeCell(x, y+1, PlayerID);
                				stabilizeCell(x, y-1, PlayerID);

            				});
            			}
            			else if(y==GridY-1)
            			{
            				int playerIndex = Board[x][y].Owner;
            				int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            				if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            				{
            					Grid[x][y].setOwner(-1);
            					Board[x][y].Owner = -1;
            				}
            				Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
						

							ObservableList<Node> children = Board[x][y].getChildren();
							Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
							Sphere Shape1 = new Sphere(10);
                			PhongMaterial material = new PhongMaterial();  
            				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
            				Shape1.setMaterial(material);
            				Sphere Shape2 = new Sphere(10);
            				Shape2.setMaterial(material);
            				Sphere Shape3 = new Sphere(10);
            				Shape3.setMaterial(material);
            				Board[x][y].getChildren().addAll(Shape1, Shape2, Shape3);
            				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
            				tt1.setByX(-40f);
            				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
            				tt2.setByY(-40f);
            				TranslateTransition tt3 = new TranslateTransition(Duration.millis(transistionTime), Shape3);
            				tt3.setByY(40f);
            				ParallelTransition transition = new ParallelTransition(tt1, tt2, tt3);
            				transition.play();
            				animationRunningCounter += 1;
            				transition.setOnFinished(event->{
            					
            					animationRunningCounter -= 1;
								Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
								Grid[x+1][y].setOwner(PlayerID);
								Board[x+1][y].Owner = PlayerID;
								Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
								Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
								Grid[x-1][y].setOwner(PlayerID);
								Board[x-1][y].Owner = PlayerID;
								Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
								Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
								Grid[x][y-1].setOwner(PlayerID);
								Board[x][y-1].Owner = PlayerID;
								Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();
            					Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
            					makeBoardCell(x, y, PlayerID);
	            			
                				if(!winnerFound)
	            					isWinner();
	            				else if(winnerFoundAndOneRecursion>64)
	            					return;
	            				else
	            				{
	            					winnerFoundAndOneRecursion +=1;
	            				}
                				stabilizeCell(x+1, y, PlayerID);
                				stabilizeCell(x, y-1, PlayerID);
                				stabilizeCell(x-1, y, PlayerID);

            				});
            			}
            		}
            		else if(Grid[x][y].getCriticalMass()==4)
            		{
            			//makeBoardCell(x, y, PlayerID);
        				int playerIndex = Board[x][y].Owner;
        				int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
        				if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            			{	
            				Grid[x][y].setOwner(-1);
        					Board[x][y].Owner = -1;
        				}
            			Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());

						ObservableList<Node> children = Board[x][y].getChildren();
						Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
						Sphere Shape1 = new Sphere(10);
            			PhongMaterial material = new PhongMaterial();  
        				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
        				Shape1.setMaterial(material);
        				Sphere Shape2 = new Sphere(10);
        				Shape2.setMaterial(material);
        				Sphere Shape3 = new Sphere(10);
        				Shape3.setMaterial(material);
        				Sphere Shape4 = new Sphere(10);
        				Shape4.setMaterial(material);
        				Board[x][y].getChildren().addAll(Shape1, Shape2, Shape3, Shape4);
        				TranslateTransition tt1 = new TranslateTransition(Duration.millis(transistionTime), Shape1);
        				tt1.setByX(-40f);
        				TranslateTransition tt2 = new TranslateTransition(Duration.millis(transistionTime), Shape2);
        				tt2.setByY(-40f);
        				TranslateTransition tt3 = new TranslateTransition(Duration.millis(transistionTime), Shape3);
        				tt3.setByY(40f);
        				TranslateTransition tt4 = new TranslateTransition(Duration.millis(transistionTime), Shape4);
        				tt4.setByX(40f);
        				ParallelTransition transition = new ParallelTransition(tt1, tt2, tt3, tt4);
        				transition.play();
        				animationRunningCounter += 1;
        				transition.setOnFinished(event -> {
        					
        					animationRunningCounter -= 1;
        					Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
							Grid[x+1][y].setOwner(PlayerID);
							Board[x+1][y].Owner = PlayerID;
							Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
							Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
							Grid[x-1][y].setOwner(PlayerID);
							Board[x-1][y].Owner = PlayerID;
							Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
							Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
							Grid[x][y-1].setOwner(PlayerID);
							Board[x][y-1].Owner = PlayerID;
							Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();
							Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
							Grid[x][y+1].setOwner(PlayerID);
							Board[x][y+1].Owner = PlayerID;
							Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();
        					Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
        					makeBoardCell(x, y, PlayerID);
	            		
            				if(!winnerFound)
	            				isWinner();
	            			else if(winnerFoundAndOneRecursion>64)
	            				return;
	            			else
	            			{
	            				winnerFoundAndOneRecursion +=1;
	            			}
            				stabilizeCell(x+1, y, PlayerID);
            				stabilizeCell(x, y+1, PlayerID);
            				stabilizeCell(x-1, y, PlayerID);
            				stabilizeCell(x, y-1, PlayerID);

        				});
            		}
	            }
            });
            //this.setOnMouseClicked(clickEvent);
		}


	}
	
	public void serialise(String File,GamePlayUI obj) throws IOException
	{
		ObjectOutputStream out = null;
		try
		{
			out = new ObjectOutputStream(new FileOutputStream("./"+File+".txt"));
			if(obj!=null)
				out.writeObject(obj);
			else
				out.writeObject(this);
		}
		finally
		{
			out.close();
		}
	}
	public static GamePlayUI deserialise(String File) throws FileNotFoundException, IOException, ClassNotFoundException 
	{
		ObjectInputStream in = null;
		try
		{
			in = new ObjectInputStream(new FileInputStream("./"+File+".txt"));
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