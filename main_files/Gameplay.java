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
				if((i==0&&j==0)||(i==0&&j==gridY-1)||(i==gridX-1&&j==0)||(i==gridX-1&&j==gridY-1))
				{
					Grid[i][j] = new Cell(0, -1, 2);
				}
				else if(i==0||i==gridX-1||j==0||j==gridX-1)
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
		checkStabilityAndStabilize(x+1, y, PlayerID);
		checkStabilityAndStabilize(x-1, y, PlayerID);
		checkStabilityAndStabilize(x, y+1, PlayerID);
		checkStabilityAndStabilize(x, y-1, PlayerID);
		return ;
	}

	private void checkStabilityAndStabilize(int x, int y, int PlayerID)
	{
		if(Grid[x][y].isStable())
		{
			return ;
		}
		Grid[x][y].setOwner(-1);
		if(Grid[x][y].getCriticalMass()==2)
		{
			stabilizeCell2(x,y, PlayerID);
		}
		if(Grid[x][y].getCriticalMass()==3)
		{
			stabilizeCell3(x,y, PlayerID);
		}
		if(Grid[x][y].getCriticalMass()==4)
		{
			stabilizeCell4(x,y, PlayerID);
		}
	}

	public boolean takeTurn(int PlayerID, int x, int y)
	{
		if(Grid[x][y].getOwner()!=-1 && Grid[x][y].getOwner()!=PlayerID)
		{
			return false;
		}
		Grid[x][y].setOwner(PlayerID);
		Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()+1);
		checkStabilityAndStabilize(x, y, PlayerID);
		return true;
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

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		prt("Grid Size:\n");
		int x = in.nextInt();
		int y = in.nextInt();
		prt("Enter Player count\n");
		int pc = in.nextInt();
		Gameplay game = new Gameplay(x, y, pc);

		for(int i=0; i<game.gridX; i++)
		{
			for(int j=0; j<game.gridY; j++)
			{
				prt(game.Grid[i][j].getCriticalMass()+" ");
			}
			prt("\n");
		}

		for(int i=0; i<game.playerCount; i++)
		{
			prt("enter coordinates:\n");
			while(!game.takeTurn(game.players[i].getPlayerID(), in.nextInt()-1, in.nextInt()-1))
			{

			}
		}
		for(int i=0; i<game.gridX; i++)
		{
			for(int j=0; j<game.gridY; j++)
			{
				prt(game.Grid[i][j].getOrbCount()+" ");
			}
			prt("\n");
		}
		for(int i=0; !game.isWinner(); )
		{
			prt("enter coordinates:\n");
			boolean shouldPlay = true;
			while(shouldPlay)
			{
				shouldPlay = !game.takeTurn(game.players[i].getPlayerID(), in.nextInt()-1, in.nextInt()-1);
				for(int k=0; k<game.gridX; k++)
				{
					for(int j=0; j<game.gridY; j++)
					{
						prt(game.Grid[k][j].getOrbCount()+" ");
					}
					prt("\n");
				}
			}
			i++;
			i=i%game.playerCount;
		}
		prt("we have a winner with playerID :"+game.nameWinner()+"\n");
	}
	public static void prt(String x)
	{
		System.out.print(x);
	}
}


