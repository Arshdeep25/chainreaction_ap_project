package main_files;
import main_files.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import javafx.scene.paint.Color;

public class Settings
{
	private Color[] playerColors;
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
	}
	public void setPlayerColor(int i, Color x)
	{
		this.playerColors[i] = x;
	}
	public Color getPlayerColor(int i)
	{
		return this.playerColors[i];
	}
	public Color[] getAllColors()
	{
		return this.playerColors;
	}
	public void setOthers(Color[] orbColors)
	{
		boolean[] sel = new boolean[8];
		for(int i=0; i<8; i++)
		{
			if(playerColors[i]!=null)
			{
				for(int j=0; j<8; j++)
				{
					if(playerColors[i].equals(orbColors[j]))
					{
						sel[j] = true;
					}
				}
			}
			else
			{
//				System.out.println(i);
			}
		}
		for(int i=0; i<8; i++)
		{
			if(playerColors[i]==null)
			{
				for(int j=0; j<8; j++)
				{
					if(sel[j]!=true)
					{
						playerColors[i] = orbColors[j];
//						System.out.println("j "+j);
						sel[j] = true;
						break;
					}
				}
			}
			else
			{
//				System.out.println(i);
			}
		}
	}
}