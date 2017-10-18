package main_files;
import java.lang.*;
import java.io.*;

public class Cell implements Serializable
{
	private int orbCount;
	private int owner;
	private final int criticalMass;

	public Cell(int orbCount, int owner, int criticalMass)
	{
		this.orbCount = orbCount;	
		this.owner = owner;			
		this.criticalMass = criticalMass;					
	}

	public int getOrbCount()
	{
		return this.orbCount;
	}
	public int getOwner()
	{
		return this.owner;
	}
	public int getCriticalMass()
	{
		return this.criticalMass;
	}
	public void setOrbCount(int orbCount)
	{
		this.orbCount = orbCount;
	}
	public void setOwner(int owner)
	{
		this.owner = owner;
	}

	public boolean isStable()
	{
		if(this.orbCount>=this.criticalMass)
		{
			return false;
		}
		return true;
	}

	public void getinfo(int i, int j)
	{
		System.out.println(i+" "+j+" "+this.orbCount+" "+this.owner+" "+this.criticalMass);
	} 

}