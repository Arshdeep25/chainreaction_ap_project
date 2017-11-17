package main_files;
import java.lang.*;
import java.io.*;


/**
* <h2The Cell of each Grid</h2>
* The following class takes into account the owner, orbcount and critical mass of a cell
*
*
* @author Anubhav Jaiswal and Arshdeep Singh Chugh
* @version 1.0
* @since 17 November 2017
*/
public class Cell implements Serializable
{
	/**
	 * The number of orbs in the cell
	 */
	private int orbCount;
	
	/**
	 * The owner of the cell
	 */
	private int owner;
	
	/**
	 * The critical mass of the cell
	 */
	private final int criticalMass;

	/**
	 * The constructor is used to initialize the Parameters of the cell
	 * @param orbCount
	 * @param owner
	 * @param criticalMass
	 */
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
	
	/**
	 * It is used to check whether the cell is stable by comparing the orbcount and critcal mass
	 * @return : return true if the cell is stable, false otherwise
	 */
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