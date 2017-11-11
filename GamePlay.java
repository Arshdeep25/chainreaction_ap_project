package main_files;
import java.io.*;

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

	public void takeTurn(int PlayerID, int x, int y)
	{
		if(Grid[x][y].getOwner()!=-1 && Grid[x][y].getOwner()!=PlayerID)
		{
			System.out.println("Please Enter Again");
		}
		else
		{
			Grid[x][y].setOwner(PlayerID);
			Grid[x][y].setOrbCount(Grid[x][y].getOrbCount()+1);
			checkStabilityAndStabilize(x, y, PlayerID);
		}
		this.movesPlayed++;
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