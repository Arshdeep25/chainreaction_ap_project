package main_files;
import main_files.*;
import java.lang.*;
import java.io.*;
import java.util.*;

public class Gameplay
{
	private Cell[][] Grid;
	private Player[] players;
	private int playerCount;
	private int gridX, gridY;

	Gameplay(int gridX, int gridY, int playerCount)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		this.playerCount = playerCount;
		players = new Player[playerCount];
		for(int i=0; i<playerCount; i++)
		{
			players[i] = new Player("Player "+(i+1), i+1, "color"+i);
		}
		Grid = new Cell[gridX][gridY];
		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if((i==0&&j==0)||(i==0&&j==gridY)||(i==gridX&&j==0)||(i==gridX&&j==gridY))
				{
					Grid[i][j] = new Cell(0, 0, 2);
				}
				else if(i==0||i==gridX||j==0||j==gridX)
				{
					Grid[i][j] = new Cell(0, 0, 3);
				}
				else
				{
					Grid[i][j] = new Cell(0, 0, 4);
				}
			}
		}
	}

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		prt("Grid Size:");
		int x = in.nextInt();
		int y = in.nextInt();
		prt("Enter Player count");
		int pc = in.nextInt();
		Gameplay game = new Gameplay(x, y, pc);
	}
	public static void prt(String x)
	{
		System.out.println(x);
	}
}


