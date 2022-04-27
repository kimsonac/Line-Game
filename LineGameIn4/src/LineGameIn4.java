import java.util.Scanner;

public class LineGameIn4 {

	public static void main(String[] args) 
	{
		int location [];
		location = makeArray();
		

			
			drawBoard(location);
			
			System.out.println();	
			
			whoseTurn();
			
			selectNum(location);

		
		
		
	}
	
	public static int[] makeArray()
	{
		int location[] = new int[7];
		for(int i=0; i<7; i++)
			location[i] = 0;
		return location;
	}
	
	public static void drawMessage()
	{
		int i;
		
		System.out.print("忙");
		
		for(i=0; i<24; i++)
		{
			System.out.print("式");
		}

		System.out.println("忖");
		
		System.out.print("弛 ");
		
		printMessage();
		
		System.out.println(" 弛");
		
		
		System.out.print("戌");

		for(i=0; i<24; i++)
		{
			System.out.print("式");
		}
		
		System.out.print("戎");
	}
	
	public static void printMessage()
	{
		System.out.print("Let's Play 4 In A Line"); // 釭醞縑 case one
	}
	
	public static void makeMiddle()
	{
		int i;
		for(i=0; i<6; i++)
		{
			System.out.print(" ");
			
		}
	}
	
	public static void drawBoard(int[] location)
	{
		drawMessage();
		
		System.out.println();
		System.out.println();		
		
		makeMiddle();
		System.out.println("1 2 3 4 5 6 7"); 
		makeMiddle();
		drawBoardTop();

		for(int i=0; i<4; i++)
		{
			drawBoardMid();
		}
		
		makeMiddle(); 
		drawBoardBot(location); 
		
	}

	public static void drawBoardTop()
	{
		
		
		System.out.print("忙");
		
		int i;
		
		for(i=0; i<5; i++)
		{
			System.out.print("式成");
		}
		
		System.out.println("式忖");
	}
	
	public static void drawBoardMid()
	{
		
		makeMiddle();
		System.out.print("戍");
		for(int j=0; j<5; j++) 
			System.out.print("式托");
		System.out.println("式扣");
		
	}
	
	public static void drawBoardBot(int[] location)
	{
		if(location[0] == 0)
			System.out.print("戌");
		else
			System.out.print("≒");
		
		
		for(int i=0; i<5; i++)
		{
			if(location[i+1] == 0)
				System.out.print("式扛");
			else
				System.out.print("式≒");
			
		}
		
		if(location[6] == 0)
			System.out.println("式戎");
		else
			System.out.print("式≒");
		
	}
	
	public static void whoseTurn()
	{
		for(int i=0; i<8; i++)
		{
			System.out.print("式");
		}
		
		System.out.print(" ≒'s Turn ");
		
		for(int i=0; i<8; i++)
		{
			System.out.print("式");
		}
		
		System.out.println();
		

	}
	
	public static void selectNum(int[] location)
	{
		Scanner inputNum = new Scanner(System.in);
		makeMiddle();
		System.out.print("Select (1-7) : ");
		int num = inputNum.nextInt();
		
		inputNum.close();
		
		isDataInput(num, location);
		drawBoard(location);
		winMessage();
	}
	
	
	public static int[] isDataInput(int num, int[] location)
	{
		
		
		switch(num)
		{
		case 1:
			location[0]=1;
			break;
		case 2:
			location[1]=1;
			break;
		case 3:
			location[2]=1;
			break;
		case 4:
			location[3]=1;
			break;
		case 5:
			location[4]=1;
			break;
		case 6:
			location[5]=1;
			break;
		case 7:
			location[6]=1;
			break;
		
		}
		
		return location;
	}
	
	public static void winMessage()
	{
		for(int i=0; i<8; i++)
		{
			System.out.print("㏑");
		}
		
		System.out.print(" ≒ WINS! ");
		
		for(int i=0; i<8; i++)
		{
			System.out.print("㏑");
		}
		
		System.out.println();
	}
}


