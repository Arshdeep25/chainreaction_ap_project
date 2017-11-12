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

public class GamePlayUI extends Application implements Serializable{


	public Tile[][] Board;
	public Cell[][] Grid;
	public int PlayerID;
	private int cnt = 0;
	public int TotalPlayers;
	public int GridX,GridY;
	public Player[] players;
	private int movesPlayed;
	protected int undo_var;
	private volatile int animationRunningCounter;
	public GamePlay resumeGrid;
	private transient Stage GameplayStage;
	public boolean winnerFound;
	
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
		players = new Player[Player];
		for(int i=0; i<Player; i++)
		{
			players[i] = new Player("Player "+(i+1), i+1, "color"+i);
		}
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
					System.out.println("animation wala bakwaas"+ animationRunningCounter);
					GamePlayUI des = null;
					try {
						des = deserialise("in2");
					} catch (Exception e) {}
					MainPage.Undo_button = 1;
					undo_var = 1;
					PlayerID = des.PlayerID;
					resumeGrid = des.resumeGrid;
					for(int i=0;i<GridX;i++)
					{
						for(int j=0;j<GridY;j++)
						{
							Grid[i][j].setOrbCount(resumeGrid.getBack_Grid()[i][j].getOrbCount());
							Grid[i][j].setOwner(resumeGrid.getBack_Grid()[i][j].getOwner());
						}
					}
					System.out.println("for one last time");
					for(int i=0;i<GridX;i++)
					{
						for(int j=0;j<GridY;j++)
						{
							System.out.print(Grid[i][j].getOrbCount() +" ");
						}
						System.out.println();
					}
					for(int p = 0 ; p<GridX ; p++)
			    	{
			            for(int q = 0;q<GridY;q++)
			    		{
//			                if(Board[p][q].NumberOfOrbs!=Grid[p][q].getOrbCount()||Board[p][q].Owner!=Grid[p][q].getOwner())
//			                {
			        			Board[p][q].NumberOfOrbs = Grid[p][q].getOrbCount();
			        			Board[p][q].Owner = Grid[p][q].getOwner();
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
					System.out.println("bloodychaljaa");
					System.out.println("for one second time");
					for(int i=0;i<GridX;i++)
					{
						for(int j=0;j<GridY;j++)
						{
							System.out.print(Grid[i][j].getOrbCount() +" ");
						}
						System.out.println();
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
				System.out.println("yoyo"+winnerFound);
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
	@Override
	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setScene(new Scene(createContent(primaryStage)));
	     primaryStage.show();
	    
	}
	public static void main(String[] args)
	{
		launch(args);
	}
	public class Tile extends StackPane implements Serializable
	{
		public int Owner=-1, NumberOfOrbs=0, x, y;
		public int transistionTime = 400;
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
        		public boolean isInGame(int PlayerID)
				{
					if(eachPlayerMovedOnce())
					{
						if(orbCountPlayer(PlayerID)==0 && undo_var==0)
						{
							return false;
						}

					}
					return true;
				}
				public boolean eachPlayerMovedOnce()
				{
					if(movesPlayed>=TotalPlayers)
					{
						return true;
					}
					return false;
				}
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
				public void makeBoardCell(int x, int y, int PlayerID)
				{
					Board[x][y].NumberOfOrbs = Grid[x][y].getOrbCount();
        			Board[x][y].Owner = Grid[x][y].getOwner();
        			if(Board[x][y].getChildren().size()>1)
        			{
        				//Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
        			}
        			System.out.println(Board[x][y].getChildren().size());
        			for(int i=6; i<Board[x][y].getChildren().size();i++)
        			{
        				if(Board[x][y].getChildren().get(i).getClass().toString().equals("class javafx.scene.Group"))
        				{
        					System.out.println(Board[x][y].getChildren().get(i).getClass());
        					Board[x][y].getChildren().remove(i);
        				}
        			}
        			for(int i=6; i<Board[x][y].getChildren().size();i++)
        			{
        				System.out.println(Board[x][y].getChildren().get(i).getClass().toString()+" sdf");
        			}
                    Group orbGroup = new Group();
                    System.out.println("Grid ki aisi ki taisi"+ Grid[x][y].getOrbCount() + " " + x + " "+ y);
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
				@Override 
				public void handle(MouseEvent e1) 
				{
					
					if(animationRunningCounter==0)
					{
						
		                System.out.println(PlayerID);
		                while(!isInGame(PlayerID))
		                {
		                    PlayerID = (PlayerID+1)%TotalPlayers;
		                }
		            	if(Board[x][y].Owner==-1||PlayerID==Board[x][y].Owner)
		            	{
		            		resumeGrid.takeTurn(PlayerID, x, y);
		            		for(int i=0;i<GridX;i++)
							{
								for(int j=0;j<GridY;j++)
								{
									System.out.print(resumeGrid.getBack_Grid()[i][j].getOrbCount()+" ");
								}
								System.out.println();
							}
							System.out.println("children.size()");
		            		movesPlayed++;
		            		Grid[x][y].setOwner(PlayerID);
		            		System.out.println("for one last time");
		    				for(int i=0;i<GridX;i++)
		    				{
		    					for(int j=0;j<GridY;j++)
		    					{
		    						System.out.print(Grid[i][j].getOrbCount() +" ");
		    					}
		    					System.out.println();
		    				}
		            		System.out.println("chudil");
		            		System.out.println(Grid[x][y].getOrbCount() + " " + x + " "+y);
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
										GamePlayUI obj1 = deserialise("in");
										serialise("in2",obj1);
		                			}
		                			undo_var = 0;
		                			MainPage.Undo_button = 0;
								} catch (Exception e) {
								}
		                		System.out.println("hallejuab" + Grid[0][0].getOrbCount() + " " +Grid[0][1].getOrbCount() + " "+ Grid[1][0].getOrbCount() );
								System.out.println(animationRunningCounter);
		                		serialise("in",null);
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	}
		            }
		            else
		            {
		            	System.out.println("ee hai locha!!");
		            }
	            	/*if(Board[x][y].Owner==-1||PlayerID==Board[x][y].Owner)
	            	{
	            		this.takeTurn(PlayerID, x, y);
	            	}*/
	            }

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
								if(prevOwner!=Grid[i][j].getOwner())
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
						serialise("in",null);
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

	            public void stabilizeCell(int x, int y, int PlayerID)
	            {
	            	if(Grid[x][y].isStable())
            		{
            			makeBoardCell(x, y, PlayerID);
            			System.out.println("Stable "+x+" "+y+" - "+PlayerID);
            			if(!winnerFound)
	            			isWinner();
            			return;
            		}
            		else if(Grid[x][y].getCriticalMass()==2)
            		{
            			System.out.println("Unstable "+x+" "+y+" - "+PlayerID);
            			//makeBoardCell(x, y, PlayerID);
            			if(x==0)
            			{
            				if(y==0)
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					System.out.println("orbsleft "+orbsleft);
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					System.out.println("Player2 - "+playerIndex);
            					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
								/*Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
								Grid[x+1][y].setOwner(PlayerID);
								Board[x+1][y].Owner = PlayerID;
								Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
								Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
								Grid[x][y+1].setOwner(PlayerID);
								Board[x][y+1].Owner = PlayerID;
								Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();*/

								ObservableList<Node> children = Board[x][y].getChildren();
								Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
								Sphere Shape1 = new Sphere(10);
                    			PhongMaterial material = new PhongMaterial();  
                    			System.out.println("sdfsdf "+playerIndex);
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
                					if(!Grid[x+1][y].isStable())
                						stabilizeCell(x+1, y, PlayerID);
                					else
                						makeBoardCell(x+1, y, PlayerID);
                					if(!Grid[x][y+1].isStable())
                						stabilizeCell(x, y+1, PlayerID);
                					else
                						makeBoardCell(x, y+1, PlayerID);
                					if(!winnerFound)
	            						isWinner();

                				});


            				}
            				else
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					System.out.println("orbsleft "+orbsleft);
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					System.out.println("Player2 - "+playerIndex);
            					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
								/*Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
								Grid[x+1][y].setOwner(PlayerID);
								Board[x+1][y].Owner = PlayerID;
								Board[x+1][y].NumberOfOrbs = Grid[x+1][y].getOrbCount();
								Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
								Grid[x][y-1].setOwner(PlayerID);
								Board[x][y-1].Owner = PlayerID;
								Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();*/

								ObservableList<Node> children = Board[x][y].getChildren();
								Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
								Sphere Shape1 = new Sphere(10);
                    			PhongMaterial material = new PhongMaterial();  
                    			System.out.println("sdfsdf "+playerIndex);
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
	                				stabilizeCell(x+1, y, PlayerID);
	                				stabilizeCell(x, y-1, PlayerID);
	                				if(!winnerFound)
	            						isWinner();

                				});
            				}
            			}
            			else
            			{
            				if(y==0)
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					System.out.println("orbsleft "+orbsleft);
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{	
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					System.out.println("Player2 - "+playerIndex);
            					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
								/*Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
								Grid[x-1][y].setOwner(PlayerID);
								Board[x-1][y].Owner = PlayerID;
								Board[x-1][y].NumberOfOrbs = Grid[x-1][y].getOrbCount();
								Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
								Grid[x][y+1].setOwner(PlayerID);
								Board[x][y+1].Owner = PlayerID;
								Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();*/

								ObservableList<Node> children = Board[x][y].getChildren();
								Board[x][y].getChildren().remove(6, Board[x][y].getChildren().size());
								Sphere Shape1 = new Sphere(10);
                    			PhongMaterial material = new PhongMaterial();  
                    			System.out.println("sdfsdf "+playerIndex);
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
	                				stabilizeCell(x-1, y, PlayerID);
	                				stabilizeCell(x, y+1, PlayerID);
	                				if(!winnerFound)
	            						isWinner();
                				});
            				}
            				else
            				{
            					int playerIndex = Board[x][y].Owner;
            					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            					System.out.println("orbsleft "+orbsleft);
            					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            					{	
            						Grid[x][y].setOwner(-1);
            						Board[x][y].Owner = -1;
            					}
            					System.out.println("Player2 - "+playerIndex);
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
                    			System.out.println("sdfsdf "+playerIndex);
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
	                				stabilizeCell(x-1, y, PlayerID);
	                				stabilizeCell(x, y-1, PlayerID);
	                				if(!winnerFound)
	            						isWinner();

                				});	
            				}
            			}

            		}
            		else if(Grid[x][y].getCriticalMass()==3)
            		{
            			System.out.println("Unstable "+x+" "+y+" - "+PlayerID);
            			//makeBoardCell(x, y, PlayerID);
            			if(x==0)
            			{
        					int playerIndex = Board[x][y].Owner;
        					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
        					System.out.println("orbsleft "+orbsleft);
        					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
        					{	
        						Grid[x][y].setOwner(-1);
    							Board[x][y].Owner = -1;
    						}
    						System.out.println("Player3 - "+playerIndex);
        					Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
							/*Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
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
							Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();*/

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
                				stabilizeCell(x+1, y, PlayerID);
                				stabilizeCell(x, y+1, PlayerID);
                				stabilizeCell(x, y-1, PlayerID);
                				if(!winnerFound)
	            					isWinner();
            				});

            			}
            			else if(y==0)
            			{
            				int playerIndex = Board[x][y].Owner;
            				int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            				System.out.println("orbsleft "+orbsleft);
            				if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            				{	
            					Grid[x][y].setOwner(-1);
            					Board[x][y].Owner = -1;
            				}
            				System.out.println("Player3 - "+playerIndex);
            				Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
							/*Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
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
							Board[x][y+1].NumberOfOrbs = Grid[x][y+1].getOrbCount();*/

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
                				stabilizeCell(x+1, y, PlayerID);
                				stabilizeCell(x, y+1, PlayerID);
                				stabilizeCell(x-1, y, PlayerID);
                				if(!winnerFound)
	            					isWinner();
            				});
            			}
            			else if(x==GridX-1)
            			{
        					int playerIndex = Board[x][y].Owner;
        					int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
        					System.out.println("orbsleft "+orbsleft);
        					if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            				{	
            					Grid[x][y].setOwner(-1);
        						Board[x][y].Owner = -1;
            				}
            				System.out.println("Player3 - "+playerIndex);
            				Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
							/*Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
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
							Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();*/

            				System.out.println("vbvbvnbn "+playerIndex+" "+x);
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
                				stabilizeCell(x-1, y, PlayerID);
                				stabilizeCell(x, y+1, PlayerID);
                				stabilizeCell(x, y-1, PlayerID);
                				if(!winnerFound)
	            					isWinner();
            				});
            			}
            			else if(y==GridY-1)
            			{
            				int playerIndex = Board[x][y].Owner;
            				int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
            				System.out.println("orbsleft "+orbsleft);
            				if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            				{
            					Grid[x][y].setOwner(-1);
            					Board[x][y].Owner = -1;
            				}
            				System.out.println("Player3 - "+playerIndex);
            				Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());
							/*Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
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
							Board[x][y-1].NumberOfOrbs = Grid[x][y-1].getOrbCount();*/

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
                				stabilizeCell(x+1, y, PlayerID);
                				stabilizeCell(x, y-1, PlayerID);
                				stabilizeCell(x-1, y, PlayerID);
                				if(!winnerFound)
	            					isWinner();
            				});
            			}
            		}
            		else if(Grid[x][y].getCriticalMass()==4)
            		{
            			System.out.println("Unstable "+x+" "+y+" - "+PlayerID);
            			//makeBoardCell(x, y, PlayerID);
        				int playerIndex = Board[x][y].Owner;
        				System.out.println("owner4-  "+playerIndex);
        				int orbsleft = Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass();
        				System.out.println("orbsleft "+orbsleft);
        				if(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass()==0)
            			{	
            				Grid[x][y].setOwner(-1);
        					Board[x][y].Owner = -1;
        					System.out.println("kjhasdfiouy  "+playerIndex);
        				}
            			Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()-Grid[x][y].getCriticalMass());

						ObservableList<Node> children = Board[x][y].getChildren();
						Board[x][y].getChildren().remove(6+orbsleft, Board[x][y].getChildren().size());
						Sphere Shape1 = new Sphere(10);
            			PhongMaterial material = new PhongMaterial();  
        				material.setDiffuseColor(MainPage.color.getAllColors()[PlayerID]); 
        				System.out.println("Player4 - "+playerIndex);
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
            				stabilizeCell(x+1, y, PlayerID);
            				stabilizeCell(x, y+1, PlayerID);
            				stabilizeCell(x-1, y, PlayerID);
            				stabilizeCell(x, y-1, PlayerID);
            				if(!winnerFound)
	            				isWinner();
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