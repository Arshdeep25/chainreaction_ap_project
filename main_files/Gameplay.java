package main_files;
import java.io.*;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.RotateTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.ParallelTransition;
import java.util.concurrent.*;
import javafx.util.Duration;

public class GamePlay implements Serializable
{
	
	private Cell[][] Grid;
	private Player[] players;
	private int playerCount;
	private int gridX, gridY;
	private int movesPlayed;
	
	public Cell[][] getGrid()
	{
		return this.Grid;
	}
	public void setGrid(Cell[][] Grid)
	{
		this.Grid = Grid;
	}
	
	GamePlay(int gridX, int gridY, int playerCount)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		this.setPlayerCount(playerCount);
		this.movesPlayed = 0;
		setPlayers(new Player[playerCount]);
		for(int i=0; i<playerCount; i++)
		{
			getPlayers()[i] = new Player("Player "+(i+1), i+1, "color"+i);
		}
		Grid = new Cell[gridX][gridY];
		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if((i==0&&j==0)||(i==0&&j==gridY-1)||(i==gridX-1&&j==0)||(i==gridX-1&&j==gridY-1))
				{
					Grid[i][j] = new Cell(0, -1, 2);
				}
				else if(i==0||i==gridX-1||j==0||j==gridY-1)
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

	private void stabilizeCell2(int x, int y, int PlayerID)
	{
		if(x==0)
		{
			if(y==0)
			{
				Grid[x][y].setOrbCount(0);
				Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
				Grid[x+1][y].setOwner(PlayerID);
				Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
				Grid[x][y+1].setOwner(PlayerID);

				Sphere Shape1 = new Sphere(10);
				PhongMaterial material = new PhongMaterial();  
				material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
				Shape1.setMaterial(material);
				Sphere Shape2 = new Sphere(10);
				Shape2.setMaterial(material);
				Group orbGroup = new Group();
				orbGroup.getChildren().add(Shape1);
				orbGroup.getChildren().add(Shape2);
				GamePlayUI.root.getChildren().add(orbGroup);
				TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
				tt1.setFromX(25f);
				tt1.setToX(75f);
				tt1.setFromY(25f);
				tt1.setToY(25f);
				TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
				tt2.setFromX(25f);
				tt2.setToX(25f);
				tt2.setFromY(25f);
				tt2.setToY(75f);
				tt1.play();
				tt2.play();
		        checkStabilityAndStabilize(x+1, y, PlayerID);
				checkStabilityAndStabilize(x, y+1, PlayerID);
				return ;
				
			}
			else
			{
				Grid[x][y].setOrbCount(0);
				Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
				Grid[x+1][y].setOwner(PlayerID);
				Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
				Grid[x][y-1].setOwner(PlayerID);

				Sphere Shape1 = new Sphere(10);
				PhongMaterial material = new PhongMaterial();  
				material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
				Shape1.setMaterial(material);
				Sphere Shape2 = new Sphere(10);
				Shape2.setMaterial(material);
				Group orbGroup = new Group();
				orbGroup.getChildren().add(Shape1);
				orbGroup.getChildren().add(Shape2);
				GamePlayUI.root.getChildren().add(orbGroup);
				System.out.println(x+" "+y);
				TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
				tt1.setFromX((float)y*50+25f);
				tt1.setToX((float)(y-1)*50+25f);
				tt1.setFromY(25f);
				tt1.setToY(25f);
				TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
				tt2.setFromX((float)y*50+25f);
				tt2.setToX((float)y*50+25f);
				tt2.setFromY(25f);
				tt2.setToY(75f);
				tt1.play();
				tt2.play();

				checkStabilityAndStabilize(x+1, y, PlayerID);
				checkStabilityAndStabilize(x, y-1, PlayerID);
				return ;
			}
		}
		else
		{
			if(y==0)
			{
				Grid[x][y].setOrbCount(0);
				Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
				Grid[x-1][y].setOwner(PlayerID);
				Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
				Grid[x][y+1].setOwner(PlayerID);

				Sphere Shape1 = new Sphere(10);
				PhongMaterial material = new PhongMaterial();  
				material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
				Shape1.setMaterial(material);
				Sphere Shape2 = new Sphere(10);
				Shape2.setMaterial(material);
				Group orbGroup = new Group();
				orbGroup.getChildren().add(Shape1);
				orbGroup.getChildren().add(Shape2);
				GamePlayUI.root.getChildren().add(orbGroup);
				System.out.println(x+" "+y);
				TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
				tt1.setFromX(25f);
				tt1.setToX(75f);
				tt1.setFromY((float)x*50+25f);
				tt1.setToY((float)x*50+25f);
				TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
				tt2.setFromX((float)y*50+25f);
				tt2.setToX((float)y*50+25f);
				tt2.setFromY((float)x*50+25f);
				tt2.setToY((float)(x-1)*50+25f);
				tt1.play();
				tt2.play();

				checkStabilityAndStabilize(x-1, y, PlayerID);
				checkStabilityAndStabilize(x, y+1, PlayerID);
				return ;
			}
			else
			{
				Grid[x][y].setOrbCount(0);
				Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
				Grid[x-1][y].setOwner(PlayerID);
				Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
				Grid[x][y-1].setOwner(PlayerID);

				Sphere Shape1 = new Sphere(10);
				PhongMaterial material = new PhongMaterial();  
				material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
				Shape1.setMaterial(material);
				Sphere Shape2 = new Sphere(10);
				Shape2.setMaterial(material);
				Group orbGroup = new Group();
				orbGroup.getChildren().add(Shape1);
				orbGroup.getChildren().add(Shape2);
				GamePlayUI.root.getChildren().add(orbGroup);
				System.out.println(x+" "+y);
				TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
				tt1.setFromX((float)y*50+25f);
				tt1.setToX((float)(y-1)*50+25f);
				tt1.setFromY((float)x*50+25f);
				tt1.setToY((float)x*50+25f);
				TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
				tt2.setFromX((float)y*50+25f);
				tt2.setToX((float)y*50+25f);
				tt2.setFromY((float)x*50+25f);
				tt2.setToY((float)(x-1)*50+25f);
				tt1.play();
				tt2.play();

				checkStabilityAndStabilize(x-1, y, PlayerID);
				checkStabilityAndStabilize(x, y-1, PlayerID);
				return ;
			}
		}
	}
	private void stabilizeCell3(int x, int y, int PlayerID)
	{
		if(y==0)
		{
			Grid[x][y].setOrbCount(0);
			Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
			Grid[x+1][y].setOwner(PlayerID);
			Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
			Grid[x-1][y].setOwner(PlayerID);
			Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
			Grid[x][y+1].setOwner(PlayerID);

			Sphere Shape1 = new Sphere(10);
			PhongMaterial material = new PhongMaterial();  
			material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
			Shape1.setMaterial(material);
			Sphere Shape2 = new Sphere(10);
			Shape2.setMaterial(material);
			Sphere Shape3 = new Sphere(10);
			Shape3.setMaterial(material);
			Group orbGroup = new Group();
			orbGroup.getChildren().add(Shape1);
			orbGroup.getChildren().add(Shape2);
			orbGroup.getChildren().add(Shape3);
			GamePlayUI.root.getChildren().add(orbGroup);
			System.out.println(x+" "+y);
			TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
			tt1.setFromX((float)y*50+25f);
			tt1.setToX((float)y*50+25f);
			tt1.setFromY((float)x*50+25f);
			tt1.setToY((float)(x+1)*50+25f);
			TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
			tt2.setFromX((float)y*50+25f);
			tt2.setToX((float)y*50+25f);
			tt2.setFromY((float)x*50+25f);
			tt2.setToY((float)(x-1)*50+25f);
			TranslateTransition tt3 = new TranslateTransition(Duration.millis(2000), Shape3);
			tt3.setFromX((float)y*50+25f);
			tt3.setToX((float)(y+1)*50+25f);
			tt3.setFromY((float)x*50+25f);
			tt3.setToY((float)x*50+25f);

			tt1.play();
			tt2.play();
			tt3.play();

			checkStabilityAndStabilize(x+1, y, PlayerID);
			checkStabilityAndStabilize(x-1, y, PlayerID);
			checkStabilityAndStabilize(x, y+1, PlayerID);
			return ;
		}
		else if(x==0)
		{
			Grid[x][y].setOrbCount(0);
			Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
			Grid[x+1][y].setOwner(PlayerID);
			Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
			Grid[x][y+1].setOwner(PlayerID);
			Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
			Grid[x][y-1].setOwner(PlayerID);

			Sphere Shape1 = new Sphere(10);
			PhongMaterial material = new PhongMaterial();  
			material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
			Shape1.setMaterial(material);
			Sphere Shape2 = new Sphere(10);
			Shape2.setMaterial(material);
			Sphere Shape3 = new Sphere(10);
			Shape3.setMaterial(material);
			Group orbGroup = new Group();
			orbGroup.getChildren().add(Shape1);
			orbGroup.getChildren().add(Shape2);
			orbGroup.getChildren().add(Shape3);
			GamePlayUI.root.getChildren().add(orbGroup);
			System.out.println(x+" "+y);
			TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
			tt1.setFromX((float)y*50+25f);
			tt1.setToX((float)y*50+25f);
			tt1.setFromY((float)x*50+25f);
			tt1.setToY((float)(x+1)*50+25f);
			TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
			tt2.setFromX((float)y*50+25f);
			tt2.setToX((float)(y+1)*50+25f);
			tt2.setFromY((float)x*50+25f);
			tt2.setToY((float)x*50+25f);
			TranslateTransition tt3 = new TranslateTransition(Duration.millis(2000), Shape3);
			tt3.setFromX((float)y*50+25f);
			tt3.setToX((float)(y-1)*50+25f);
			tt3.setFromY((float)x*50+25f);
			tt3.setToY((float)x*50+25f);

			tt1.play();
			tt2.play();
			tt3.play();

			checkStabilityAndStabilize(x+1, y, PlayerID);
			checkStabilityAndStabilize(x, y+1, PlayerID);
			checkStabilityAndStabilize(x, y-1, PlayerID);
			return ;
		}
		else if(y==gridY-1)
		{
			Grid[x][y].setOrbCount(0);
			Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
			Grid[x+1][y].setOwner(PlayerID);
			Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
			Grid[x-1][y].setOwner(PlayerID);
			Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
			Grid[x][y-1].setOwner(PlayerID);

			Sphere Shape1 = new Sphere(10);
			PhongMaterial material = new PhongMaterial();  
			material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
			Shape1.setMaterial(material);
			Sphere Shape2 = new Sphere(10);
			Shape2.setMaterial(material);
			Sphere Shape3 = new Sphere(10);
			Shape3.setMaterial(material);
			Group orbGroup = new Group();
			orbGroup.getChildren().add(Shape1);
			orbGroup.getChildren().add(Shape2);
			orbGroup.getChildren().add(Shape3);
			GamePlayUI.root.getChildren().add(orbGroup);
			System.out.println(x+" "+y);
			TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
			tt1.setFromX((float)y*50+25f);
			tt1.setToX((float)y*50+25f);
			tt1.setFromY((float)x*50+25f);
			tt1.setToY((float)(x+1)*50+25f);
			TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
			tt2.setFromX((float)y*50+25f);
			tt2.setToX((float)y*50+25f);
			tt2.setFromY((float)x*50+25f);
			tt2.setToY((float)(x-1)*50+25f);
			TranslateTransition tt3 = new TranslateTransition(Duration.millis(2000), Shape3);
			tt3.setFromX((float)y*50+25f);
			tt3.setToX((float)(y-1)*50+25f);
			tt3.setFromY((float)x*50+25f);
			tt3.setToY((float)x*50+25f);

			tt1.play();
			tt2.play();
			tt3.play();

			checkStabilityAndStabilize(x+1, y, PlayerID);
			checkStabilityAndStabilize(x-1, y, PlayerID);
			checkStabilityAndStabilize(x, y-1, PlayerID);
			return ;
		}
		else if(x==gridX-1)
		{
			Grid[x][y].setOrbCount(0);
			Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
			Grid[x-1][y].setOwner(PlayerID);
			Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
			Grid[x][y+1].setOwner(PlayerID);
			Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
			Grid[x][y-1].setOwner(PlayerID);

			Sphere Shape1 = new Sphere(10);
			PhongMaterial material = new PhongMaterial();  
			material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
			Shape1.setMaterial(material);
			Sphere Shape2 = new Sphere(10);
			Shape2.setMaterial(material);
			Sphere Shape3 = new Sphere(10);
			Shape3.setMaterial(material);
			Group orbGroup = new Group();
			orbGroup.getChildren().add(Shape1);
			orbGroup.getChildren().add(Shape2);
			orbGroup.getChildren().add(Shape3);
			GamePlayUI.root.getChildren().add(orbGroup);
			System.out.println(x+" "+y);
			TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
			tt1.setFromX((float)y*50+25f);
			tt1.setToX((float)(y+1)*50+25f);
			tt1.setFromY((float)x*50+25f);
			tt1.setToY((float)x*50+25f);
			TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
			tt2.setFromX((float)y*50+25f);
			tt2.setToX((float)y*50+25f);
			tt2.setFromY((float)x*50+25f);
			tt2.setToY((float)(x-1)*50+25f);
			TranslateTransition tt3 = new TranslateTransition(Duration.millis(2000), Shape3);
			tt3.setFromX((float)y*50+25f);
			tt3.setToX((float)(y-1)*50+25f);
			tt3.setFromY((float)x*50+25f);
			tt3.setToY((float)x*50+25f);

			tt1.play();
			tt2.play();
			tt3.play();

			checkStabilityAndStabilize(x-1, y, PlayerID);
			checkStabilityAndStabilize(x, y+1, PlayerID);
			checkStabilityAndStabilize(x, y-1, PlayerID);
			return ;
		}
	}
	private void stabilizeCell4(int x, int y, int PlayerID)
	{
		Grid[x][y].setOrbCount(0);
		Grid[x+1][y].setOrbCount(Grid[x+1][y].getOrbCount()+1);
		Grid[x+1][y].setOwner(PlayerID);
		Grid[x-1][y].setOrbCount(Grid[x-1][y].getOrbCount()+1);
		Grid[x-1][y].setOwner(PlayerID);
		Grid[x][y+1].setOrbCount(Grid[x][y+1].getOrbCount()+1);
		Grid[x][y+1].setOwner(PlayerID);
		Grid[x][y-1].setOrbCount(Grid[x][y-1].getOrbCount()+1);
		Grid[x][y-1].setOwner(PlayerID);

		Sphere Shape1 = new Sphere(10);
		PhongMaterial material = new PhongMaterial();  
		material.setDiffuseColor(MainPage.color.getAllColors()[GamePlayUI.Board[x][y].Owner]); 
		Shape1.setMaterial(material);
		Sphere Shape2 = new Sphere(10);
		Shape2.setMaterial(material);
		Sphere Shape3 = new Sphere(10);
		Shape3.setMaterial(material);
		Sphere Shape4 = new Sphere(10);
		Shape4.setMaterial(material);
		Group orbGroup = new Group();
		orbGroup.getChildren().add(Shape1);
		orbGroup.getChildren().add(Shape2);
		orbGroup.getChildren().add(Shape3);
		orbGroup.getChildren().add(Shape4);
		GamePlayUI.root.getChildren().add(orbGroup);
		System.out.println(x+" "+y);
		TranslateTransition tt1 = new TranslateTransition(Duration.millis(2000), Shape1);
		tt1.setFromX((float)y*50+25f);
		tt1.setToX((float)(y+1)*50+25f);
		tt1.setFromY((float)x*50+25f);
		tt1.setToY((float)x*50+25f);
		TranslateTransition tt2 = new TranslateTransition(Duration.millis(2000), Shape2);
		tt2.setFromX((float)y*50+25f);
		tt2.setToX((float)y*50+25f);
		tt2.setFromY((float)x*50+25f);
		tt2.setToY((float)(x-1)*50+25f);
		TranslateTransition tt3 = new TranslateTransition(Duration.millis(2000), Shape3);
		tt3.setFromX((float)y*50+25f);
		tt3.setToX((float)(y-1)*50+25f);
		tt3.setFromY((float)x*50+25f);
		tt3.setToY((float)x*50+25f);
		TranslateTransition tt4 = new TranslateTransition(Duration.millis(2000), Shape4);
		tt4.setFromX((float)y*50+25f);
		tt4.setToX((float)(x+1)*50+25f);
		tt4.setFromY((float)x*50+25f);
		tt4.setToY((float)x*50+25f);

		tt1.play();
		tt2.play();
		tt3.play();
		tt4.play();


		checkStabilityAndStabilize(x+1, y, PlayerID);
		checkStabilityAndStabilize(x-1, y, PlayerID);
		checkStabilityAndStabilize(x, y+1, PlayerID);
		checkStabilityAndStabilize(x, y-1, PlayerID);
		return ;
	}

	public int checkStabilityAndStabilize(int x, int y, int PlayerID)
	{
		if(Grid[x][y].isStable())
		{
			return 0;
		}
		Grid[x][y].setOwner(-1);
		GamePlayUI.Board[x][y].getChildren().remove(6, GamePlayUI.Board[x][y].getChildren().size());
		if(Grid[x][y].getCriticalMass()==2)
		{
			stabilizeCell2(x,y, PlayerID);
			return 2;
		}
		if(Grid[x][y].getCriticalMass()==3)
		{
			stabilizeCell3(x,y, PlayerID);
			return 3;
		}
		if(Grid[x][y].getCriticalMass()==4)
		{
			stabilizeCell4(x,y, PlayerID);
			return 4;
		}
		return 0;
	}

	public int takeTurn(int PlayerID, int x, int y)
	{
		int cellType = 0;
		if(Grid[x][y].getOwner()!=-1 && Grid[x][y].getOwner()!=PlayerID)
		{
			System.out.println("Please Enter Again");
		}
		else
		{
			Grid[x][y].setOwner(PlayerID);
			Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()+1);
			cellType = checkStabilityAndStabilize(x, y, PlayerID);
		}
		this.movesPlayed++;
		return cellType;
	}
	public boolean isInGame(int PlayerID)
	{
		if(this.eachPlayerMovedOnce())
		{
			if(this.orbCountPlayer(PlayerID)==0)
			{
				return false;
			}

		}
		return true;
	}
	public boolean eachPlayerMovedOnce()
	{
		if(this.movesPlayed>=this.playerCount)
		{
			return true;
		}
		return false;
	}
	public int orbCountPlayer(int PlayerID)
	{
		int count = 0;
		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if(Grid[i][j].getOwner()==PlayerID)
				{
					count += Grid[i][j].getOrbCount();
				}
			}
		}
		return count;
	}

	public boolean isWinner()
	{
		int prevOwner = -1, isAssigned=0;

		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
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
						return false;
					}
				}
			}
		}
		return true;
	}
	public int nameWinner()
	{
		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if(Grid[i][j].getOwner()!=-1)
				{
					return Grid[i][j].getOwner();
				}
			}
		}
		return -1;
	}
	public static void prt(String x)
	{
		System.out.print(x);
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}
}