package main_files;
import main_files.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import javafx.scene.paint.Color;

/**
* This implements the backend storage for the color selection
* of orbs for the players in the game.
*
*
* @author Anubhav Jaiswal and Arshdeep Singh Chugh
* @version 1.0
* @since 17 November 2017
*/


public class Settings
{
	private Color[] playerColors;
	private Color[] playerColorsTemp;

	/**
	* The constructor of the class. It has no parameters and intializes variables to its deafult values.
	*
	*
	*/

	Settings()
	{
		this.playerColors = new Color[8];
		playerColors[0] = Color.rgb(255, 153, 51);
		playerColors[1] = Color.rgb(32, 86, 173);
		playerColors[2] = Color.rgb(31, 130, 42);
		playerColors[3] = Color.rgb(186, 26, 14);
		playerColors[4] = Color.rgb(150, 121, 16);
		playerColors[5] = Color.rgb(16, 129, 150);
		playerColors[6] = Color.rgb(96, 16, 150);
		playerColors[7] = Color.rgb(150, 16, 94);
		this.playerColorsTemp = this.playerColors;
	}

	/**
	* This function sets the value of the color of a player.
	*
	*
	* @param i : index of player.
	* @param x : Color to be set.
	*/

	public void setPlayerColor(int i, Color x)
	{
		this.playerColors[i] = x;
	}

	/**
	* This function gets the value of the color of a player.
	*
	*
	* @param i : index of player.
	* @return color of the player.
	*/
	
	public Color getPlayerColor(int i)
	{
		return this.playerColors[i];
	}

	/**
	* This function gets the value of the colors of the players.
	*
	*
	* @return the color array of the colors of the players.
	*/
	
	public Color[] getAllColors()
	{
		return this.playerColors;
	}

	/**
	* This function sets the value of colors for the players whose colors weren't selected.
	*
	*
	* @param orbColors : Color array of the original default colors.
	* @param selected : boolean array fo the players whose colors have been selected.
	* @return Nothing
	*/
	
	public Settings setOthers(Color[] orbColors, boolean[] selected)
	{
		boolean[] sel = new boolean[8];
		Color[] getColor = new Color[8];
		for(int i=0; i<8; i++)
		{
			if(selected[i])
			{
				for(int j=0; j<8; j++)
				{
					if(playerColors[i].equals(orbColors[j]))
					{
						sel[j] = true;
						break;
					}
				}
			}
		}
		for(int i=0; i<8; i++)
		{
			if(!selected[i])
			{
				for(int j=0; j<8; j++)
				{
					if(!sel[j])
					{
						playerColors[i] = orbColors[j];
						sel[j] = true;
						break;
					}
				}
			}
		}
		
		return this;
	}
}