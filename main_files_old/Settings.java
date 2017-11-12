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