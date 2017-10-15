package main_files;
import java.lang.*;
import java.io.*;

public class Player
{
	private String name;
	private final int playerID;
	private String orbColor;
	public Player(String name, int playerID, String orbColor)
	{
		this.name = name;
		this.playerID = playerID;
		this.orbColor = orbColor;
	}
	public String getName()
	{
		return this.name;
	}
	public int getPlayerID()
	{
		return this.playerID;
	}
	public String getOrbColor()
	{
		return this.orbColor;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setOrbColor(String orbColor)
	{
		this.orbColor = orbColor;
	}
}