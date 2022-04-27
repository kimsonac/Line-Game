import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;




public class LineGameIn4 extends JFrame implements KeyListener {
	
	GameHandler handler;
	JTextArea textArea;
	
	
	LineGameIn4()
		{
			setTitle("Let's play 4 In A Line");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(300, 250);
			setVisible(true);
			setLocationRelativeTo(null);
			textArea = new JTextArea();
			textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
			textArea.addKeyListener(this); // this = LineGame
			add(textArea);
			textArea.setEditable(false);
			
			handler = new GameHandler(textArea);
			new Thread(new GameThread()).start();
			
		
		}
	
	public void restart()
	{	
		handler.initData();
		new Thread(new GameThread()).start();
	}
	
	// 게임실행
	class GameThread implements Runnable
	{
		@Override
		public void run()
		{
			
			while(!handler.isGameOver())
			{
				handler.gameTiming();
				handler.drawAll(); // render
				handler.gameLogic();
				
			}
			
			handler.drawGameOver();
			
			
		}
	}

	public static void main(String[] args) {
		LineGameIn4 mf = new LineGameIn4();

	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT:
			handler.moveRightBall();
			break;
		case KeyEvent.VK_LEFT:
			handler.moveLeftBall();
			break;
		case KeyEvent.VK_DOWN:
			handler.putTheBall();
			break;
		case KeyEvent.VK_Y:
			if(handler.isGameOver())
				restart();
			break;
		case KeyEvent.VK_N:
			if(handler.isGameOver())
			{	
				handler.saveGame();
				System.exit(0);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}

}
	

class GameHandler
{
	private final int FLOOR = 6;
	private final int ROOM = 13;
	private final int SCREEN_WIDTH = 54;
	private final int SCREEN_HEIGHT = 60;
	private final int HEIGHT_PADDING = 3;
	private final int WIDTH_PADDING = 1;
	private final int BLOCK_ROOM = 15;
	private final int BALL_READY = 6;
	final static int INITIAL_VALUE = 0;

	private int turn = INITIAL_VALUE;
	private JTextArea text;
	private boolean isGameOver;
	private int win = 0;
	private static String black = "●";
	private static String white = "○";
	private char[][] screen;
	private int board[][];
	private int winner[][];
	private int location[]; // location = 현재 준비중인 돌의 x 좌표
	private int score[];
	private String previous[];
	
	
	public GameHandler(JTextArea txt)
	{
		text = txt;

		
		screen = new char[SCREEN_HEIGHT][SCREEN_WIDTH];
		board = new int[FLOOR][ROOM];
		winner = new int[FLOOR][ROOM];
		location = new int[1];
		score = new int[2];
		previous = new String [1];
		score[0] = 0;
		score[1] = 0;
		
		initData();
		
	}
	
	public void initData()
	{
		int i, j;
		
		
		// board ready
		for(i=0; i<FLOOR; i++) //6
		{
			if(i == 0) // top
			{
				for(j=0; j<ROOM; j++) //13
				{	
					int dummy = j % 2;
					
					if(j == 0) board[i][j] = 2;
					else if(j == 12) board[i][j] = 4;
					else if(dummy == 0) board[i][j] = 3;
					else if(dummy == 1) board[i][j] = 1;
				}
			}
			
			else if(i == 5) //bot
			{
				for(j=0; j<ROOM; j++)
				{
					int dummy = j % 2;
					
					if(j == 0) board[i][j] = 8;
					else if(j == 12) board[i][j] = 10;
					else if(dummy == 0) board[i][j] = 9;
					else if(dummy == 1) board[i][j] = 1;
				}
			}
			
			else // mid
			{
				for(j=0; j<ROOM; j++)
				{
					int dummy = j % 2;
					
					if(j == 0) board[i][j] = 5; 
					else if(j == 12) board[i][j] = 7;
					else if(dummy == 0) board[i][j] = 6;
					else if(dummy == 1) board[i][j] = 1;
				}
				
			}
			
		}
		
		
		// winner 초기화
		for(i=0; i<FLOOR; i++)
		{
			for(j=0; j<ROOM; j++)
			{
				winner[i][j] = 0;
			}
		}
		
		
		isGameOver = false;
		location[0] = BALL_READY;
		
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("previous.txt"));
			previous[0] = in.readLine();
			in.close();
		}
		
		catch(FileNotFoundException e)
		{
			previous[0] = "●:0   ○:0";
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		clearScreen(); // 얘가 먼저 실행되어서 초기화 시키고
		drawToScreen(HEIGHT_PADDING-1, location[0], black); // 2, 7에 얘 넣어 줌 (대기열)
		
	}
	
	public void clearScreen()
	{
		for(int i=0; i<SCREEN_HEIGHT; i++)
		{
			for(int j=0; j<SCREEN_WIDTH; j++)
				screen[i][j] = ' ';
			
		}
	}
	
	public void gameTiming()
	{
		try {
			Thread.sleep(50);
		}
		
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void drawToScreen(int y, int x, String c) 	
	{
	
		for(int i=0; i<c.length(); i++) 
			screen[y][i+x+WIDTH_PADDING] = c.charAt(i); // y = 6, x = 12 -> [6+3+12][13] c 문자열의 i 초기값부터 길이까지
		 // "┌───CURRENT───┐".charAt(0~문자열 길이)
	}
	
	private void drawToScreen(int y, int x, char c)
	{
		screen[y][x+WIDTH_PADDING] = c; // X + 14
	}
	
	public void drawBlock() // 채워주는 역할
	{	
			for(int j=INITIAL_VALUE; j<BLOCK_ROOM; j++ )
			{
			
				drawToScreen(HEIGHT_PADDING - 1, BLOCK_ROOM, "┌───CURRENT───┐"); // 2, 15 
				drawToScreen(HEIGHT_PADDING, BLOCK_ROOM, "│  "+black+":"+score[0]+"   "+white+":"+score[1]+"  │");
				drawToScreen(HEIGHT_PADDING + 1, BLOCK_ROOM, "└─────────────┘"); // 4, 15
				drawToScreen(HEIGHT_PADDING + 3, BLOCK_ROOM, "┌───PREVIOUS──┐"); // 7, 15
				drawToScreen(HEIGHT_PADDING + 4, BLOCK_ROOM, "│  " + previous[0] +"  │");
				drawToScreen(HEIGHT_PADDING + 5, BLOCK_ROOM, "└─────────────┘"); // 9, 15
			}
			
	}
		
	
	

	private void render()
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i<SCREEN_HEIGHT; i++)
		{
			for(int j = 0; j<SCREEN_WIDTH; j++)
				{
					sb.append(screen[i][j]);
				}
			sb.append("\n");
		}
		
		text.setText(sb.toString());
		}
	
	
	public void drawAll()
	{
		
		
		// draw board
		for(int i=0; i<FLOOR; i++) // 0~6
		{
			for(int j=0; j<ROOM; j++) // 0~12
				drawToScreen(i+HEIGHT_PADDING, j, " ─┌┬┐├┼┤└┴┘●○".charAt(board[i][j])); // b 11, w 12
		}
		
		// draw current & previous
		drawBlock();
		// 준비열 돌
		
		
		
		drawToScreen(9, 20, " By Y.Kim");
		render();
	}
	
	
	public void drawBall(int move) // 방향키 누를 때
	{
		
		
			if(turn == INITIAL_VALUE)
				drawToScreen(HEIGHT_PADDING-1, move, black); // 준비열에 그려줌
			else drawToScreen(HEIGHT_PADDING-1, move, white);
		
		
	}
	

	
	
	public void moveRightBall()
	{
		
		if(location[0] < 12) 
		{
			drawToScreen(HEIGHT_PADDING-1, location[0], " ");
			location[0] = location[0] + 2;
			
			
		}
		
		int move = location[0];
		drawBall(move);
	}
	
	public void moveLeftBall()
	{
		
		if(location[0] > 0) 
		{
			drawToScreen(HEIGHT_PADDING-1, location[0], " ");
			location[0] = location[0] - 2;
		}
		
		int move = location[0];
		drawBall(move);
		
		
	}
	

	public void putTheBall() // 보드에 그리기 -> init으로 초기화해 준 값을 바꾸는 함수
	{
		
		drawToScreen(HEIGHT_PADDING-1, location[0], " ");
		
		for(int i=5; i>-1; i--)
		{
			if(board[i][location[0]] == 11 || board[i][location[0]] == 12)
			{
				if(i == 0)
					System.exit(0);
				continue;
			}
			
			
			
			else 
			{
				if(turn == INITIAL_VALUE)
				{
					board[i][location[0]] = 11;
					winner[i][location[0]] = 1;
					break;
				}
				else 
				{
					board[i][location[0]] = 12;
					winner[i][location[0]] = 2;
					break;
				}
			}
			
		}
		
		location[0] = BALL_READY;
		
		if(turn == INITIAL_VALUE)
		{
			turn = INITIAL_VALUE + 1;
			drawToScreen(HEIGHT_PADDING-1, location[0], white);
		}
		else 
		{
			turn = INITIAL_VALUE;
			drawToScreen(HEIGHT_PADDING-1, location[0], black);
		}
		
		 
		
			
	}

	public boolean isGameOver()
	{
		return isGameOver;
	}
	

	
	public void gameLogic()
	{
		for(int i = 0; i<FLOOR; i++)
		{
			for(int j = 0; j<ROOM; j++)
			{
				if(j % 2 == 0)
				{
					if(winner[i][j] != 0)
					{
						//가로
						if(j < 7 && winner[i][j] == winner[i][j+2] && winner[i][j] == winner[i][j+4] && winner[i][j] == winner[i][j+6])
						{
							if(winner[i][j] == 1)
							{
								win = 0;
								score[0] = score[0] + 1;
							}
							else
							{
								win = 1;
								score[1] = score[1] + 1;
							}
							
							
							isGameOver = true;
							
						}
						
						//세로
						if(i > 2 && winner[i][j] == winner[i-1][j] && winner[i][j] == winner[i-2][j] && winner[i][j] == winner[i-3][j])
						{
							if(winner[i][j] == 1)
							{
								win = 0;
								score[0] = score[0] + 1;
							}
							else 
							{
								win = 1;
								score[1] = score[1] + 1;
							}
							
							isGameOver = true;
							
						}
						
						// 대각선 ↗
						if(i > 2 && j < 7 && winner[i][j] == winner[i-1][j+2] && winner[i][j] == winner[i-2][j+4] && winner[i][j] == winner[i-3][j+6])
						{
							if(winner[i][j] == 1)
							{
								win = 0;
								score[0] = score[0] + 1;
							}
							else 
							{
								win = 1;
								score[1] = score[1] + 1;
							}
							
							isGameOver = true;
						}
						
						// 대각선 ↘
						if(i < 3 && j < 7 && winner[i][j] == winner[i+1][j+2] && winner[i][j] == winner[i+2][j+4] && winner[i][j] == winner[i+3][j+6])
						{
							if(winner[i][j] == 1)
							{
								win = 0;
								score[0] = score[0] + 1;
							}
							else 
							{
								win = 1;
								score[1] = score[1] + 1;
							}
							
							isGameOver = true;
						}
					}
					
				}
				
				else continue;
			}
			
		}
	}
	
	public void drawGameOver()
	{
		String player = white;
		
		if(win == 0)
			player = black;
		
		drawToScreen(HEIGHT_PADDING+2, BLOCK_ROOM, "┏━━━━━━━━━━━━━━┓");
		drawToScreen(HEIGHT_PADDING+3, BLOCK_ROOM, "┃   "+player+" WINS!    ┃");
		drawToScreen(HEIGHT_PADDING+4, BLOCK_ROOM, "┃              ┃");
		drawToScreen(HEIGHT_PADDING+5, BLOCK_ROOM, "┃ AGAIN? (Y/N) ┃");
		drawToScreen(HEIGHT_PADDING+6, BLOCK_ROOM, "┗━━━━━━━━━━━━━━┛");
		
		for(int j=INITIAL_VALUE; j<BLOCK_ROOM; j++)
		{
		
			drawToScreen(HEIGHT_PADDING - 1, BLOCK_ROOM, "┌───CURRENT───┐"); // 2, 15 
			drawToScreen(HEIGHT_PADDING, BLOCK_ROOM, "│  "+black+":"+score[0]+"   "+white+":"+score[1]+"  │");
			drawToScreen(HEIGHT_PADDING + 1, BLOCK_ROOM, "└─────────────┘"); // 4, 15
		}
		// 종료 전 현재 스코어 파일에 저장
		
		
		
		render();
	}
	
	public void saveGame()
	{
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter("previous.txt"));
			out.write(black + ":" + score[0] + "   "+ white + ":" + score[1]);
			out.close();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
