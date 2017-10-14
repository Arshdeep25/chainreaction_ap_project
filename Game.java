import java.util.Scanner;

public class Game {
	static Cell[][] Grid;
	static int n,m;
	static int[] arr;
	public static void func(int pos1,int pos2,int owner)
	{
		int cm = Grid[pos1][pos2].criticalMass;
		if(cm==2)
		{
			if(pos1==1&&pos2==1)
			{
				Grid[pos1+1][pos2].orbs++;
				Grid[pos1+1][pos2].Owner = owner;
				Grid[pos1][pos2+1].orbs++;
				Grid[pos1][pos2+1].Owner = owner;
				arr[owner]+=2;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1+1][pos2].orbs==Grid[pos1+1][pos2].criticalMass)
				{
					Grid[pos1+1][pos2].orbs = 0;
					Grid[pos1+1][pos2].Owner = 0;
					func(pos1+1,pos2,owner);
				}
				if(Grid[pos1][pos2+1].orbs==Grid[pos1][pos2+1].criticalMass)
				{
					Grid[pos1][pos2+1].orbs = 0;
					Grid[pos1][pos2+1].Owner = 0;
					func(pos1,pos2+1,owner);
				}
			}
			else if(pos1==m&&pos2==1)
			{
				Grid[pos1-1][pos2].orbs++;
				Grid[pos1-1][pos2].Owner = owner;
				Grid[pos1][pos2+1].orbs++;
				Grid[pos1][pos2+1].Owner = owner;
				arr[owner]+=2;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1-1][pos2].orbs==Grid[pos1-1][pos2].criticalMass)
				{
					func(pos1-1,pos2,owner);
					Grid[pos1-1][pos2].orbs = 0;
					Grid[pos1-1][pos2].Owner = 0;
				}
				if(Grid[pos1][pos2+1].orbs==Grid[pos1][pos2+1].criticalMass)
				{
					Grid[pos1][pos2+1].orbs = 0;
					Grid[pos1][pos2+1].Owner = 0;
					func(pos1,pos2+1,owner);
				}
			}
			else if(pos1==1&&pos2==n)
			{
				Grid[pos1+1][pos2].orbs++;
				Grid[pos1+1][pos2].Owner = owner;
				Grid[pos1][pos2-1].orbs++;
				Grid[pos1][pos2-1].Owner = owner;
				arr[owner]+=2;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1+1][pos2].orbs==Grid[pos1+1][pos2].criticalMass)
				{
					Grid[pos1+1][pos2].orbs = 0;
					Grid[pos1+1][pos2].Owner = 0;
					func(pos1+1,pos2,owner);
					
				}
				if(Grid[pos1][pos2-1].orbs==Grid[pos1][pos2-1].criticalMass)
				{
					Grid[pos1][pos2-1].orbs = 0;
					Grid[pos1][pos2-1].Owner = 0;
					func(pos1,pos2-1,owner);
				}
			}
			else
			{
				Grid[pos1][pos2-1].orbs++;
				Grid[pos1][pos2-1].Owner = owner;
				Grid[pos1-1][pos2].orbs++;
				Grid[pos1-1][pos2].Owner = owner;
				arr[owner]+=2;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1][pos2-1].orbs==Grid[pos1][pos2-1].criticalMass)
				{
					Grid[pos1][pos2-1].orbs = 0;
					Grid[pos1][pos2-1].Owner = 0;
					func(pos1,pos2-1,owner);
				}
				if(Grid[pos1-1][pos2].orbs==Grid[pos1-1][pos2].criticalMass)
				{
					Grid[pos1-1][pos2].orbs = 0;
					Grid[pos1-1][pos2].Owner = 0;
					func(pos1-1,pos2,owner);
				}
			}
		}
		else if(cm==3)
		{
			if(pos1==1)
			{
				Grid[pos1][pos2-1].orbs++;
				Grid[pos1][pos2-1].Owner = owner;
				Grid[pos1][pos2+1].orbs++;
				Grid[pos1][pos2+1].Owner = owner;
				Grid[pos1+1][pos2].orbs++;
				Grid[pos1+1][pos2].Owner = owner;
				arr[owner]+=3;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1][pos2-1].orbs==Grid[pos1][pos2-1].criticalMass)
				{
					Grid[pos1][pos2-1].orbs = 0;
					Grid[pos1][pos2-1].Owner = 0;
					func(pos1,pos2-1,owner);
				}
				if(Grid[pos1][pos2+1].orbs==Grid[pos1][pos2+1].criticalMass)
				{
					Grid[pos1][pos2+1].orbs = 0;
					Grid[pos1][pos2+1].Owner = 0;
					func(pos1,pos2+1,owner);
				}
				if(Grid[pos1+1][pos2].orbs==Grid[pos1+1][pos2].criticalMass)
				{
					Grid[pos1+1][pos2].orbs = 0;
					Grid[pos1+1][pos2].Owner = 0;
					func(pos1+1,pos2,owner);
				}
			}
			else if(pos1==n)
			{
				Grid[pos1][pos2-1].orbs++;
				Grid[pos1][pos2-1].Owner = owner;
				Grid[pos1][pos2+1].orbs++;
				Grid[pos1][pos2+1].Owner = owner;
				Grid[pos1-1][pos2].orbs++;
				Grid[pos1-1][pos2].Owner = owner;
				arr[owner]+=3;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1][pos2-1].orbs==Grid[pos1][pos2-1].criticalMass)
				{
					Grid[pos1][pos2-1].orbs = 0;
					Grid[pos1][pos2-1].Owner = 0;
					func(pos1,pos2-1,owner);
				}
				if(Grid[pos1][pos2+1].orbs==Grid[pos1][pos2+1].criticalMass)
				{
					Grid[pos1][pos2+1].orbs = 0;
					Grid[pos1][pos2+1].Owner = 0;
					func(pos1,pos2+1,owner);
				}
				if(Grid[pos1-1][pos2].orbs==Grid[pos1-1][pos2].criticalMass)
				{
					Grid[pos1-1][pos2].orbs = 0;
					Grid[pos1-1][pos2].Owner = 0;
					func(pos1-1,pos2,owner);
				}
			}
			else if(pos2==1)
			{
				Grid[pos1-1][pos2].orbs++;
				Grid[pos1-1][pos2].Owner = owner;
				Grid[pos1+1][pos2].orbs++;
				Grid[pos1+1][pos2].Owner = owner;
				Grid[pos1][pos2+1].orbs++;
				Grid[pos1][pos2+1].Owner = owner;
				arr[owner]+=3;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1-1][pos2].orbs==Grid[pos1-1][pos2].criticalMass)
				{
					Grid[pos1-1][pos2].orbs = 0;
					Grid[pos1-1][pos2].Owner = 0;
					func(pos1-1,pos2,owner);
				}
				if(Grid[pos1+1][pos2].orbs==Grid[pos1+1][pos2].criticalMass)
				{
					Grid[pos1+1][pos2].orbs = 0;
					Grid[pos1+1][pos2].Owner = 0;
					func(pos1+1,pos2,owner);
				}
				if(Grid[pos1][pos2+1].orbs==Grid[pos1][pos2+1].criticalMass)
				{
					Grid[pos1][pos2+1].orbs = 0;
					Grid[pos1][pos2+1].Owner = 0;
					func(pos1,pos2+1,owner);
				}
			}
			else if(pos2==m)
			{
				Grid[pos1-1][pos2].orbs++;
				Grid[pos1-1][pos2].Owner = owner;
				Grid[pos1+1][pos2].orbs++;
				Grid[pos1+1][pos2].Owner = owner;
				Grid[pos1][pos2-1].orbs++;
				Grid[pos1][pos2-1].Owner = owner;
				arr[owner]+=3;
				if(chkWinner()==0)
				{
					return;
				}
				if(Grid[pos1-1][pos2].orbs==Grid[pos1-1][pos2].criticalMass)
				{
					Grid[pos1-1][pos2].orbs = 0;
					Grid[pos1-1][pos2].Owner = 0;
					func(pos1-1,pos2,owner);
				}
				if(Grid[pos1+1][pos2].orbs==Grid[pos1+1][pos2].criticalMass)
				{
					Grid[pos1+1][pos2].orbs = 0;
					Grid[pos1+1][pos2].Owner = 0;
					func(pos1+1,pos2,owner);
				}
				if(Grid[pos1][pos2-1].orbs==Grid[pos1][pos2-1].criticalMass)
				{
					Grid[pos1][pos2-1].orbs = 0;
					Grid[pos1][pos2-1].Owner = 0;
					func(pos1,pos2-1,owner);
				}
			}
		}
		else
		{
			Grid[pos1][pos2+1].orbs++;
			Grid[pos1][pos2+1].Owner = owner;
			Grid[pos1][pos2-1].orbs++;
			Grid[pos1][pos2-1].Owner = owner;
			Grid[pos1+1][pos2].orbs++;
			Grid[pos1+1][pos2].Owner = owner;
			Grid[pos1-1][pos2].orbs++;
			Grid[pos1-1][pos2].Owner = owner;
			arr[owner]+=4;
			if(chkWinner()==0)
			{
				return;
			}
			if(Grid[pos1][pos2+1].orbs==Grid[pos1][pos2+1].criticalMass)
			{
				Grid[pos1][pos2+1].orbs = 0;
				Grid[pos1][pos2+1].Owner = 0;
				func(pos1,pos2+1,owner);
			}
			if(Grid[pos1][pos2-1].orbs==Grid[pos1][pos2-1].criticalMass)
			{
				Grid[pos1][pos2-1].orbs = 0;
				Grid[pos1][pos2-1].Owner = 0;
				func(pos1,pos2-1,owner);
			}
			if(Grid[pos1+1][pos2].orbs==Grid[pos1+1][pos2].criticalMass)
			{
				Grid[pos1+1][pos2].orbs = 0;
				Grid[pos1+1][pos2].Owner = 0;
				func(pos1+1,pos2,owner);
			}
			if(Grid[pos1-1][pos2].orbs==Grid[pos1-1][pos2].criticalMass)
			{
				Grid[pos1-1][pos2].orbs = 0;
				Grid[pos1-1][pos2].Owner = 0;
				func(pos1-1,pos2,owner);
			}
		}
		
	}
	public static void taketurn(int Player)
	{
		Scanner sc = new Scanner(System.in);
		int pos1,pos2;
		do
		{
			System.out.println("Enter the Position of the orb of player  :"+Player);
			pos1 = sc.nextInt();
			pos2 = sc.nextInt();
		}while(Grid[pos1][pos2].Owner!=0&&Grid[pos1][pos2].Owner!=Player);
		System.out.println("Position is valid");
		if(Grid[pos1][pos2].Owner==0)
		{
			Grid[pos1][pos2].orbs++;
			arr[Player]++;
			Grid[pos1][pos2].Owner = Player;
		}
		else
		{
			Grid[pos1][pos2].orbs++;
			arr[Player]++;
			if(Grid[pos1][pos2].orbs==Grid[pos1][pos2].criticalMass)
			{
				Grid[pos1][pos2].orbs = 0;
				Grid[pos1][pos2].Owner = 0;
				func(pos1,pos2,Player);
			}
		}
	}
	public static int chkWinner()
	{
		int cnt1=0,cnt2=0;
		int flag=0;
		for(int i=1;i<=m;i++)
		{
			for(int j=1;j<=n;j++)
			{
				if(Grid[i][j].Owner==1)
					cnt1++;
				if(Grid[i][j].Owner==2)
					cnt2++;
				if(cnt1>0&&cnt2>0)
					flag=1;
			}
		}
		if(flag==0)
			System.out.println("We have a winner");
		else
			System.out.println("Let the Game continue");
		return flag;
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		arr = new int[3];
		arr[1]=0;
		arr[2]=0;
		System.out.println("Enter the grid dimanesions");
		m = sc.nextInt();
		n = sc.nextInt();
		Grid = new Cell[m+1][n+1];
		
		/*Initializing each cell with critical mass and orbs and owner*/
		for(int i=1;i<=m;i++)
		{
			for(int j=1;j<=n;j++)
			{
				Grid[i][j] = new Cell();
				if((i==1&&j==1)||(i==1&&j==n)||(i==m&&j==1)||(i==m&&j==n))
				{
					Grid[i][j].criticalMass = 2;
					Grid[i][j].orbs = 0;
					Grid[i][j].Owner = 0;
				}
				else if(i==1 || i==n || j==1 || j==m)
				{
					Grid[i][j].criticalMass = 3;
					Grid[i][j].orbs = 0;
					Grid[i][j].Owner = 0;
				}
				else
				{
					Grid[i][j].criticalMass = 4;
					Grid[i][j].orbs = 0;
					Grid[i][j].Owner = 0;
				}		
			}
		}
		int P = 1;
		taketurn(1);
		taketurn(2);
		do
		{
			taketurn(P);
			if(P==1)
				P=2;
			else 
				P=1;
		}while(chkWinner()==1);
		
		
		
	}
}
class Cell
{
	int orbs,criticalMass,Owner;
	
}