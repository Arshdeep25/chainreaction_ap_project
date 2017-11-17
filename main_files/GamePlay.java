package main_files;
import java.io.*;


/**
* <h2>Back End of the game</h2>
* The following class implements the backend of the game.
*
*
* @author Anubhav Jaiswal and Arshdeep Singh Chugh
* @version 1.0
* @since 17 November 2017
*/

public class GamePlay implements Serializable
{
	/**
	* 2-d rray of Cell class for the backend functioning of Grid.
	*/
	private Cell[][] Back_Grid;
	
	/**
	 * Array for the Players
	 */
//	private Player[] players;
	
	/**
	 * It specifies the chance of the Player by keeping track of PlayerId
	 */
	private int playerCount;
	
	/**
	 * Grid size : x coordinate
	 */
	private int gridX;
	
	/**
	 * Grid size : y coordinate
	 */
	private int gridY;
	
	/**
	 * It is used to check if everyone has had atleast a single turn
	 */
	private int movesPlayed;
	private int lastPlayed;
	
	/**
	 * 
	 * @return the refernce of the 2-D Cell type Grid
	 */
	public Cell[][] getBack_Grid()
	{
		return this.Back_Grid;
	}
	
	
	/**
	 * 
	 * Constructor used to initialise Back_Grid, grid size and player count
	 * @param gridX : x coordinate
	 * @param gridY : y coordinate
	 * @param playerCount : Number of Players
	 */
	GamePlay(int gridX, int gridY, int playerCount)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		this.setPlayerCount(playerCount);
		this.movesPlayed = 0;
		this.lastPlayed = 0;
		Back_Grid = new Cell[gridX][gridY];
		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if((i==0&&j==0)||(i==0&&j==gridY-1)||(i==gridX-1&&j==0)||(i==gridX-1&&j==gridY-1))
				{
					Back_Grid[i][j] = new Cell(0, -1, 2);
				}
				else if(i==0||i==gridX-1||j==0||j==gridY-1)
				{
					Back_Grid[i][j] = new Cell(0, -1, 3);
				}
				else
				{
					Back_Grid[i][j] = new Cell(0, -1, 4);
				}
			}
		}
	}
	
	/**
	 * This method is used to stabilize the cells which have critical mass 2
	 * @param x : Grid Coordinate
	 * @param y : Grid Coordinate
	 * @param PlayerID : The Id of the player whose turn is currently going on
	 */
	private void stabilizeCell2(int x, int y, int PlayerID)
	{
		if(x==0)
		{
			if(y==0)
			{
				Back_Grid[x][y].setOrbCount(0);
				Back_Grid[x+1][y].setOrbCount(Back_Grid[x+1][y].getOrbCount()+1);
				Back_Grid[x+1][y].setOwner(PlayerID);
				Back_Grid[x][y+1].setOrbCount(Back_Grid[x][y+1].getOrbCount()+1);
				Back_Grid[x][y+1].setOwner(PlayerID);
				checkStabilityAndStabilize(x+1, y, PlayerID);
				checkStabilityAndStabilize(x, y+1, PlayerID);
				return ;
			}
			else
			{
				Back_Grid[x][y].setOrbCount(0);
				Back_Grid[x+1][y].setOrbCount(Back_Grid[x+1][y].getOrbCount()+1);
				Back_Grid[x+1][y].setOwner(PlayerID);
				Back_Grid[x][y-1].setOrbCount(Back_Grid[x][y-1].getOrbCount()+1);
				Back_Grid[x][y-1].setOwner(PlayerID);
				checkStabilityAndStabilize(x+1, y, PlayerID);
				checkStabilityAndStabilize(x, y-1, PlayerID);
				return ;
			}
		}
		else
		{
			if(y==0)
			{
				Back_Grid[x][y].setOrbCount(0);
				Back_Grid[x-1][y].setOrbCount(Back_Grid[x-1][y].getOrbCount()+1);
				Back_Grid[x-1][y].setOwner(PlayerID);
				Back_Grid[x][y+1].setOrbCount(Back_Grid[x][y+1].getOrbCount()+1);
				Back_Grid[x][y+1].setOwner(PlayerID);
				checkStabilityAndStabilize(x-1, y, PlayerID);
				checkStabilityAndStabilize(x, y+1, PlayerID);
				return ;
			}
			else
			{
				Back_Grid[x][y].setOrbCount(0);
				Back_Grid[x-1][y].setOrbCount(Back_Grid[x-1][y].getOrbCount()+1);
				Back_Grid[x-1][y].setOwner(PlayerID);
				Back_Grid[x][y-1].setOrbCount(Back_Grid[x][y-1].getOrbCount()+1);
				Back_Grid[x][y-1].setOwner(PlayerID);
				checkStabilityAndStabilize(x-1, y, PlayerID);
				checkStabilityAndStabilize(x, y-1, PlayerID);
				return ;
			}
		}
	}
	
	/**
	 * This method is used to stabilize the cells which have critical mass 3
	 * @param x : Grid Coordinate
	 * @param y : Grid Coordinate
	 * @param PlayerID : The Id of the player whose turn is currently going on
	 */
	private void stabilizeCell3(int x, int y, int PlayerID)
	{
		if(y==0)
		{
			Back_Grid[x][y].setOrbCount(0);
			Back_Grid[x+1][y].setOrbCount(Back_Grid[x+1][y].getOrbCount()+1);
			Back_Grid[x+1][y].setOwner(PlayerID);
			Back_Grid[x-1][y].setOrbCount(Back_Grid[x-1][y].getOrbCount()+1);
			Back_Grid[x-1][y].setOwner(PlayerID);
			Back_Grid[x][y+1].setOrbCount(Back_Grid[x][y+1].getOrbCount()+1);
			Back_Grid[x][y+1].setOwner(PlayerID);
			checkStabilityAndStabilize(x+1, y, PlayerID);
			checkStabilityAndStabilize(x-1, y, PlayerID);
			checkStabilityAndStabilize(x, y+1, PlayerID);
			return ;
		}
		else if(x==0)
		{
			Back_Grid[x][y].setOrbCount(0);
			Back_Grid[x+1][y].setOrbCount(Back_Grid[x+1][y].getOrbCount()+1);
			Back_Grid[x+1][y].setOwner(PlayerID);
			Back_Grid[x][y+1].setOrbCount(Back_Grid[x][y+1].getOrbCount()+1);
			Back_Grid[x][y+1].setOwner(PlayerID);
			Back_Grid[x][y-1].setOrbCount(Back_Grid[x][y-1].getOrbCount()+1);
			Back_Grid[x][y-1].setOwner(PlayerID);
			checkStabilityAndStabilize(x+1, y, PlayerID);
			checkStabilityAndStabilize(x, y+1, PlayerID);
			checkStabilityAndStabilize(x, y-1, PlayerID);
			return ;
		}
		else if(y==gridY-1)
		{
			Back_Grid[x][y].setOrbCount(0);
			Back_Grid[x+1][y].setOrbCount(Back_Grid[x+1][y].getOrbCount()+1);
			Back_Grid[x+1][y].setOwner(PlayerID);
			Back_Grid[x-1][y].setOrbCount(Back_Grid[x-1][y].getOrbCount()+1);
			Back_Grid[x-1][y].setOwner(PlayerID);
			Back_Grid[x][y-1].setOrbCount(Back_Grid[x][y-1].getOrbCount()+1);
			Back_Grid[x][y-1].setOwner(PlayerID);
			checkStabilityAndStabilize(x+1, y, PlayerID);
			checkStabilityAndStabilize(x-1, y, PlayerID);
			checkStabilityAndStabilize(x, y-1, PlayerID);
			return ;
		}
		else if(x==gridX-1)
		{
			Back_Grid[x][y].setOrbCount(0);
			Back_Grid[x-1][y].setOrbCount(Back_Grid[x-1][y].getOrbCount()+1);
			Back_Grid[x-1][y].setOwner(PlayerID);
			Back_Grid[x][y+1].setOrbCount(Back_Grid[x][y+1].getOrbCount()+1);
			Back_Grid[x][y+1].setOwner(PlayerID);
			Back_Grid[x][y-1].setOrbCount(Back_Grid[x][y-1].getOrbCount()+1);
			Back_Grid[x][y-1].setOwner(PlayerID);
			checkStabilityAndStabilize(x-1, y, PlayerID);
			checkStabilityAndStabilize(x, y+1, PlayerID);
			checkStabilityAndStabilize(x, y-1, PlayerID);
			return ;
		}
	}
	
	/**
	 * This method is used to stabilize the cells which have critical mass = 4
	 * @param x : Grid Coordinate
	 * @param y : Grid Coordinate
	 * @param PlayerID : The Id of the player whose turn is currently going on
	 */
	private void stabilizeCell4(int x, int y, int PlayerID)
	{
		Back_Grid[x][y].setOrbCount(0);
		Back_Grid[x+1][y].setOrbCount(Back_Grid[x+1][y].getOrbCount()+1);
		Back_Grid[x+1][y].setOwner(PlayerID);
		Back_Grid[x-1][y].setOrbCount(Back_Grid[x-1][y].getOrbCount()+1);
		Back_Grid[x-1][y].setOwner(PlayerID);
		Back_Grid[x][y+1].setOrbCount(Back_Grid[x][y+1].getOrbCount()+1);
		Back_Grid[x][y+1].setOwner(PlayerID);
		Back_Grid[x][y-1].setOrbCount(Back_Grid[x][y-1].getOrbCount()+1);
		Back_Grid[x][y-1].setOwner(PlayerID);
		checkStabilityAndStabilize(x+1, y, PlayerID);
		checkStabilityAndStabilize(x-1, y, PlayerID);
		checkStabilityAndStabilize(x, y+1, PlayerID);
		checkStabilityAndStabilize(x, y-1, PlayerID);
		return ;
	}

	/**
	 * This method is used to check whether there is a need to stabilize the cell
	 * @param x : Grid Coordinate
	 * @param y : Grid Coordinate
	 * @param PlayerID : The Id of the player whose turn is currently going on
	 */
	private void checkStabilityAndStabilize(int x, int y, int PlayerID)
	{
		if(Back_Grid[x][y].isStable())
		{
			return ;
		}
		Back_Grid[x][y].setOwner(-1);
		if(Back_Grid[x][y].getCriticalMass()==2)
		{
			stabilizeCell2(x,y, PlayerID);
		}
		if(Back_Grid[x][y].getCriticalMass()==3)
		{
			stabilizeCell3(x,y, PlayerID);
		}
		if(Back_Grid[x][y].getCriticalMass()==4)
		{
			stabilizeCell4(x,y, PlayerID);
		}
	}

	/**
	 * This method is used to check the validity of the move and call for checkStabilityAndStabilize
	 * method for that particular cell
	 * @param PlayerID : Id of player to be checked
	 * @param x : x coordinate
	 * @param y : y coordinate
	 */
	public void takeTurn(int PlayerID, int x, int y)
	{
		if(Back_Grid[x][y].getOwner()!=-1 && Back_Grid[x][y].getOwner()!=PlayerID)
		{
			System.out.println("Please Enter Again");
		}
		else
		{
			this.lastPlayed = PlayerID;
			Back_Grid[x][y].setOwner(PlayerID);
			Back_Grid[x][y].setOrbCount(Back_Grid[x][y].getOrbCount()+1);
			checkStabilityAndStabilize(x, y, PlayerID);
		}
		this.movesPlayed++;
	}
	
	/***
	 * 
	 * It Checks whether the next player is currently in the game. it not it moves to the next player and so on.
	 * @param PlayerID : The player to be checked
	 * @return : returns the playerId of a valid player 
	 */
	public int nextTurnPlayer(int PlayerID)
	{
		int playerTurn = PlayerID;
		while(!isInGame(playerTurn))
		{
			playerTurn = (playerTurn+1)%playerCount;
		}
		return playerTurn;
	}
	
	/**
	 * It is used to check whether the current player is in game or not
	 * @param PlayerID : The player to be checked
	 * @return  : true if player is in game otherwise false
	 */
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
	
	/**
	 * This method verifies if each player has moved atleast once
	 * @return : true if they have, false otherwise
	 */
	public boolean eachPlayerMovedOnce()
	{
		if(this.movesPlayed>=this.playerCount)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param PlayerID : The player to be checked
	 * @return Returns the number of orbs associated with a particular PlayerID
	 */
	public int orbCountPlayer(int PlayerID)
	{
		int count = 0;
		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if(Back_Grid[i][j].getOwner()==PlayerID)
				{
					count += Back_Grid[i][j].getOrbCount();
				}
			}
		}
		return count;
	}

	/**
	 * 
	 * Checks if there is a winner or not
	 * @return : Return true of there is a winner ; otherwise false
	 */
	public boolean isWinner()
	{
		int prevOwner = -1, isAssigned=0;

		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if(Back_Grid[i][j].getOwner()!=-1)
				{
					if(isAssigned==0)
					{
						prevOwner = Back_Grid[i][j].getOwner();
						isAssigned = 1;
					}
					if(prevOwner!=Back_Grid[i][j].getOwner())
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @return The id of the player who is the winner
	 */
	public int nameWinner()
	{
		for(int i=0; i<gridX; i++)
		{
			for(int j=0; j<gridY; j++)
			{
				if(Back_Grid[i][j].getOwner()!=-1)
				{
					return Back_Grid[i][j].getOwner();
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
}