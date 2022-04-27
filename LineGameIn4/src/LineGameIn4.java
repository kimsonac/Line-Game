import java.util.Scanner;

public class LineGameIn4 {
	
	final static int roop = 7;
	final static int roop2 = 5;
	final static int roop3 = 8;
	

	public static void main(String[] args) 
	{
		while(true)
		{
			String black = "●";
			String white = "○";
			String replay;
			boolean gameStart = true;
			int turn, turn3;
			int turn2 = 1;
			int location [][]; 
			int loc = 0;
			
			location = makeArray();
			
			while(gameStart)
			{
				
				turn = drawBoard(black, white, turn2, location);
				
				System.out.println();
				
				
				
				if(loc == 0)
				
				{
					turn3 = whoseTurn(black, white, turn); // 플레이어 위치
					turn2 = fillArray(turn3, selectNum(location), location);
					loc = whoWin(black, white, loc, location);
									
				}
				
				
				else break;
			
			}
			
			exWinner(black, white, loc);
			replay = getAgainMessage();
			
			if(replay.compareTo("n") == 0)
				{
					gameStart = false;
					System.out.println();
					System.out.print("게임을 종료합니다.");
					System.exit(0);
				}
			
			else continue;
		}
	
			
	}
	
	public static int[][] makeArray()
	{
		int location [][] = new int[6][7]; // index = 0~4, 0~6
		
		for(int i=0; i<roop2; i++) 
			for (int j=0; j<roop2+1; j++)
				
				location[i][j] = 0;
				
		return location;
	}
	
	public static void drawMessage()
	{
		int i;
		
		System.out.print("┌");
		
		for(i=0; i<24; i++)
		{
			System.out.print("─");
		}

		System.out.println("┐");
		
		System.out.print("│ ");
		
		printMessage();
		
		System.out.println(" │");
		
		
		System.out.print("└");

		for(i=0; i<24; i++)
		{
			System.out.print("─");
		}
		
		System.out.print("┘");
	}
	
	public static void printMessage()
	{
		System.out.print("Let's Play 4 In A Line"); 
	}
	
	public static void makeMiddle()
	{
		int i;
		for(i=0; i<6; i++)
		{
			System.out.print(" ");
			
		}
	}
	
	public static int makeTurn(int player)
	{
		if(player == 0) player = 1; //흑이면 백으로
		else player = 0;
		
		return player;
	}
	
	public static int drawBoard(String black, String white, int player, int[][] location)
	{
		drawMessage();
		
		System.out.println();
		System.out.println();		
		
		makeMiddle();
		System.out.println("1 2 3 4 5 6 7"); 
		makeMiddle();
		
		drawBoardTop(black, white, player, location);

		
		for(int i=0; i<roop2; i++) // 0~4 다섯번 
		{
			drawBoardMid(black, white, player, i, location);
		}
		
		makeMiddle(); 
		drawBoardBot(black, white, player, location); 
		
		return makeTurn(player);
		
	}

	public static void drawBall(int ballcolor, String black, String white) //그 값에 맞게 다시
	{
		
		if(ballcolor == 1)
			System.out.print(black);
		else
			System.out.print(white);
	}
	
	public static void drawBoardTop(String black, String white, int player, int[][] location)
	{
		int ballcolor;
		
		if(location[5][0] == 0)
			System.out.print("┌");
		else
			{
				ballcolor = location[5][0];
				drawBall(ballcolor, black, white);
			}
			
		
		for(int i=0; i<roop2; i++)
		{
				ballcolor = location[5][i+1]; 
				
				if(ballcolor == 0)
					System.out.print("─┬");
				else 	
				{
					System.out.print("─");
					drawBall(ballcolor, black, white);
				}
			
					
		}
			
			if(location[5][6] == 0)
				System.out.println("─┐");
			else 
			{
				ballcolor = location[5][6];
				System.out.print("─");
				drawBall(ballcolor, black, white);
				System.out.println();
			}
		
			
			
	}
	
	
	public static void drawBoardMid(String black, String white, int player, int i, int[][] location)
	{
		
		int ballcolor;
		
		if(i != 0)
		{
			makeMiddle();
			if(location[i][0] == 0) // 1~5 하지만 4까지만 해야 함
				System.out.print("├");
			else
			{	ballcolor = location[i][0];
				drawBall(ballcolor, black, white);
			}
			
			 for(int j=1; j<roop2+1; j++) // 5번
				{
				 	ballcolor = location[i][j];
				 	
					if(ballcolor == 0)
						System.out.print("─┼");			
					else 
						{
							System.out.print("─");
							drawBall(ballcolor, black, white);
						}
						
				}
			
				
			if(i != 0 && location[i][6] == 0)
				System.out.println("─┤");
			else
				{
					ballcolor = location[i][6];
					System.out.print("─");
					drawBall(ballcolor, black, white);
					System.out.println();
					
				}
		}

		
	}
	
	public static void drawBoardBot(String black, String white, int player, int[][] location)
	{
			int ballcolor;
			
			if(location[0][0] == 0)
				System.out.print("└");
			else
			{	ballcolor = location[0][0];
				drawBall(ballcolor, black, white);
				
			}
		
		
			for(int i=1; i<roop2+1; i++)
			{
				ballcolor = location[0][i];
				
				if(ballcolor == 0)
					System.out.print("─┴");
				else 
					{
						System.out.print("─");
						drawBall(ballcolor, black, white);
					}

						
			}		
			
			if(location[0][6] == 0)
				System.out.println("─┘");
			else 	
			{
				ballcolor = location[0][6];
				System.out.print("─");
				drawBall(ballcolor, black, white);
				System.out.println();
			}

			
	}
	

	
	public static int whoseTurn(String black, String white, int player)
	{
		for(int i=0; i<roop3; i++)
		{
			System.out.print("─");
		}
		
		if(player == 0)
			{
				System.out.print(" " + black + "'s Turn ");
			}
		
		else 
			{
				System.out.print(" " + white + "'s Turn ");
			}
		
		for(int i=0; i<roop3; i++)
		{
			System.out.print("─");
		}
		
		System.out.println();
		
		return player;

	}
	

	
	public static int selectNum(int[][] location) // 변수 하나 만들어 놓고 true면 selectnum false면 win 해야 함 fillarray 메인도 수정해야 함
	{
		Scanner inputNum = new Scanner(System.in);
		makeMiddle();
		System.out.print("Select (1-7) : ");
		int num = 0;
		num = inputNum.nextInt();
		System.out.println();
		
		return num;

	}
	

	
	public static int fillArray(int player, int num, int location[][])
	{
		
		if(num != 0)
		{
			
			int cnt = 0;
			
			for(int i=6; i>0; i--) // 6 5 4 3 2 1
			
			{
								
				
				if(cnt == 0)
				{
					if(location[cnt][num-1] == 1 || location[cnt][num-1] == 2)
					{
						cnt++;
						continue;
					}
					else if(location[cnt][num-1] == 0)
					{
						if(player == 0)
							{
								location[cnt][num-1] = 1;
								break;
							}
						
						else 
							{
								location[cnt][num-1] = 2;								
								break;
							}
					}
				}
				
				else if(i != 5 && cnt !=1)
				{
					if(location[i][num-1] == 1 || location[i][num-1] == 2)
					{
						continue;
					}
					
					else if(location[i][num-1] == 0)
					{
						if(player == 0) 
							{
								location[i][num-1] = 1;
								break;
							}
						else 
							{
								location[i][num-1] = 2;								
								break;
							}
					}
				}
				
				else if(location[1][num-1] == 1 || location[1][num-1] == 2)
				{
					if(location[5][num-1] == 1 || location[5][num-1] == 2)
						{	
							System.out.println("더 이상 돌을 둘 수 없습니다.");
							System.out.print("게임을 처음부터 다시 시작합니다.");
							System.exit(0);
							
						}
					else if(location[5][num-1] == 0)
					{
						if(player == 0) 
							location[5][num-1] = 1;
						else 
							{
								location[5][num-1] = 2;								
								break;
							}
					}
				}
				
				else if(cnt == 1)
				{
					cnt++;
					continue;
				}
			

	
			}
		}
		

		return player;
		

		
	}
	
	public static int whoWin(String black, String white, int loc, int location[][])
	{
		int i, j;
		
		
		for(i=0; i<6; i++) //가로 
		{
			for(j=0; j<4; j++)
				if(location[i][j] != 0 && location[i][j] == location[i][j+1] && location[i][j] == location[i][j+2] && location[i][j] == location[i][j+3])
					loc = location[i][j];
			
			for(j=0; j<7; j++) //세로
			{	
				if(i == 5)
				{
					if(location[i][j] != 0 && location[i][j] == location[i-4][j] && location[i][j] == location[i-3][j] && location[i][j] == location[i-2][j])
						loc = location[i][j];
				}
				
				if(i == 4)
				{
					if(location[i][j] != 0 && location[i][j] == location[i-3][j] && location[i][j] == location[i-2][j] && location[i][j] == location[i-1][j])
						loc = location[i][j];
				}
				
				if(i == 0)
				{
					if(location[i][j] != 0 && location[i][j] == location[i+2][j] && location[i][j] == location[i+3][j] && location[i][j] == location[i+4][j])
						loc = location[i][j];
				}
			
			}
		}
		
	
		
		for(i=0; i<5; i++) // 대각선 조건 다시 보기 가로세로는 되는데 이게 안 됨 이것만 고치면 끝
		{
			for(j=3; j<7; j++) // ↘
			{
				if(i == 0)
				{
					if(location[i][j] != 0 && location[i][j] == location[i+4][j-1] && location[i][j] == location[i+3][j-2] && location[i][j] == location[i+2][j-3])
							loc = location[i][j];
				}
					
				if(i == 4 || i == 3)
				{
					if(location[i][j] != 0 && location[i][j] == location[i-1][j-1] && location[i][j] == location[i-2][j-2] && location[i][j] == location[i-3][j-3])
							loc = location[i][j];
				}
					
					
			}
					
			
			for(j=0; j<4; j++) // ↙
			{
				
				if(i == 0)
				{
					if(location[i][j] !=0 && location[i][j] == location[i+4][j+1] && location[i][j] == location[i+3][j+2] && location[i][j] == location[i+2][j+3])
						loc = location[i][j];
				}
				
				if(i == 4)
				{
					if(location[i][j] !=0 && location[i][j] == location[i-1][j+1] && location[i][j] == location[i-2][j+2] && location[i][j] == location[i-3][j+3])
						loc = location[i][j];
				}
				
				if(i == 3)
				{
					if(location[i][j] !=0 && location[i][j] == location[i-1][j+1] && location[i][j] == location[i-2][j+2] && location[i][j] == location[i+2][j+3])
						loc = location[i][j];
				}
				
			}
		}
		
		
		return loc;

		
	}
	
	public static void exWinner(String black, String white, int loc)
	{

		
		if(loc == 1)
			winMessage(black, white, 1);
			
		else if(loc == 2)
			winMessage(black, white, 2);
		


	}

	public static void winMessage(String black, String white, int winner)
	{
		for(int i=0; i<roop; i++)
		{
			System.out.print("〓");
		}
		
		if(winner == 1)
			System.out.print(" " + black + " WINS! ");
		else
			System.out.print(" " + white + " WINS! ");
		
		for(int i=0; i<roop; i++)
		{
			System.out.print("〓");
		}
		
		System.out.println("\n");
	}
	
	public static String getAgainMessage()
	{
		Scanner question = new Scanner(System.in);
		makeMiddle();
		System.out.print("Play Again? (y/n) ");
		String reply;
		reply = question.next();
		
		return reply;
		
		
	}
	
}



