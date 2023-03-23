package startrekwarpwars;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class Display {

	static int enemy_number = -1;
	static Enemy[] enemy = new Enemy[50];
	static Player player = new Player(0, 0, 50, 0, 5);
	static CircularQueue input_queue = new CircularQueue(15);
	static Stack Backpack = new Stack(8);
	static int computer_score = 0;

	public static enigma.console.Console cn = Enigma.getConsole("Lorem Ipsum dolor sit amet", 96, 32, 15);

	public KeyListener klis;

	// ------ Standard variables for keyboard ------
	public boolean keypr; // key pressed?
	public int rkey; // key (for press/release)

	public Display(String text) throws InterruptedException {
		cn.setTitle(text);

		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (!keypr) {
					keypr = true;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);

		drawTitle();
		drawStars(50);
		drawOptions();

		setCursorPosition(19, 6);
		System.out.print(">");
		setCursorPosition(19, 6);

		int selection = 0;
		boolean levelLoaded = false;
		char[][] GameArea = null;

		while (true) {
			if (keypr) {
				if (rkey == KeyEvent.VK_DOWN) {
					if (selection < 2)
						selection++;
					else
						selection = 0;

					System.out.print(" ");
					setCursorPosition(19, 6 + selection);
					System.out.print(">");
					setCursorPosition(19, 6 + selection);
				} else if (rkey == KeyEvent.VK_UP) {
					if (selection > 0)
						selection--;
					else
						selection = 2;

					System.out.print(" ");
					setCursorPosition(19, 6 + selection);
					System.out.print(">");
					setCursorPosition(19, 6 + selection);
				} else if (rkey == KeyEvent.VK_ENTER) {
					switch (selection) {
					case (0):
						int Rows = 23;
						int Columns = 55;
						GameArea = new char[Rows][Columns];
						File GameAreaFile = new File("map.txt");
						Scanner scanner;

						setBackgroundColor(0, 0, 0);
						setForegroundColor(.5, .5, 1);
						setCursorPosition(11, 11);

						try {
							scanner = new Scanner(GameAreaFile);

							for (int row = 0; scanner.hasNextLine() && row < Rows; row++) {
								char[] ch = scanner.nextLine().toCharArray();
								for (int i = 0; i < Columns && i < ch.length; i++) {
									GameArea[row][i] = ch[i];
								}
							}
							levelLoaded = true;
							System.out.print(" Maze successfully loaded. ");
						} catch (FileNotFoundException e1) {
							System.out.print("   \"maze.txt\" not found.   ");
						}

						setBackgroundColor(1, .75, 0);
						setCursorPosition(19, 6 + selection);
						break;

					case (1):
						if (levelLoaded) {
							// START
							Field field = new Field(GameArea);

							Clear();

							setBackgroundColor(0, 0, 0);
							setForegroundColor(0, .5, 1);

							input_queue();

							printgamemenu();
							printinputqueue();

							setCursorPosition(0, 0);
							Field.printgamearea();
							startGame();

							Clear();
							drawTitle();
							drawStars(50);
							drawOptions();

							setBackgroundColor(0, 0, 0);
							setForegroundColor(.5, .5, 1);
							setCursorPosition(13, 11);
							System.out.print("Game over. Score: " + (Player.getPlayerScore() - Enemy.getEnemyScore()));
							setBackgroundColor(1, .75, 0);
							setForegroundColor(0, 0, 0);
							setCursorPosition(19, 6 + selection);

						} else {
							setBackgroundColor(0, 0, 0);
							setForegroundColor(.5, .5, 1);
							setCursorPosition(11, 11);
							System.out.print("Please load the maze first.");
							setBackgroundColor(1, .75, 0);
							setForegroundColor(0, 0, 0);
							setCursorPosition(19, 6 + selection);
						}
						break;
					case (2):
						setBackgroundColor(0, 0, 0);
						setForegroundColor(.5, .5, 1);
						setCursorPosition(11, 11);
						System.out.print("        Exiting...         ");
						Thread.sleep(600);
						System.exit(1);
						break;
					}
				}

				keypr = false;
			}
			Thread.sleep(50);
		}
	}

	public void Clear() {
		for (int i = 0; i < 150; i++) {
			System.out.println("");
		}
		cn.getTextWindow().setCursorPosition(0, 0);
	}

//sets color of text
	// each value is a float (0.0 to 1.0) representing R G B values.
	static public void setForegroundColor(double R, double G, double B) {
		float red = (float) R;
		float green = (float) G;
		float blue = (float) B;
		if (red < 0)
			red = 0;
		else if (red > 1)
			red = 1;
		if (green < 0)
			green = 0;
		else if (green > 1)
			green = 1;
		if (blue < 0)
			blue = 0;
		else if (blue > 1)
			blue = 1;

		Color foregroundColor = new Color(red, green, blue);
		Color backgroundColor = cn.getTextAttributes().getBackground();
		cn.setTextAttributes(new TextAttributes(foregroundColor, backgroundColor));
	}

	// sets color of background
	// each value is a float (0.0 to 1.0) representing R G B values.
	static public void setBackgroundColor(double R, double G, double B) {
		float red = (float) R;
		float green = (float) G;
		float blue = (float) B;
		if (red < 0)
			red = 0;
		else if (red > 1)
			red = 1;
		if (green < 0)
			green = 0;
		else if (green > 1)
			green = 1;
		if (blue < 0)
			blue = 0;
		else if (blue > 1)
			blue = 1;

		Color backgroundColor = new Color(red, green, blue);
		Color foregroundColor = cn.getTextAttributes().getForeground();
		cn.setTextAttributes(new TextAttributes(foregroundColor, backgroundColor));
	}

	public static void setCursorPosition(int x, int y) {
		cn.getTextWindow().setCursorPosition(x, y);
	}

	public void drawTitle() {
		for (int i = 0; i < 3; i++) {
			setCursorPosition(5, i);
			for (int j = 0; j < 10; j++) {
				setBackgroundColor(.1 + (j * .1), .1 + (j * .1), 0);
				System.out.print(" ");
			}
		}

		setCursorPosition(15, 0);
		System.out.print("                   ");
		setCursorPosition(15, 2);
		System.out.print("                   ");

		for (int i = 0; i < 3; i++) {
			setCursorPosition(34, i);
			for (int j = 10; j > 0; j--) {
				setBackgroundColor(j * .1, j * .1, 0);
				System.out.print(" ");
			}
		}

		setCursorPosition(15, 1);
		setForegroundColor(1, 1, 0);
		System.out.print("STAR TREK WARP WARS");
	}

	public void drawOptions() {

		setBackgroundColor(.5, .5, 0);
		setForegroundColor(0, 0, 0);

		setCursorPosition(16, 5);
		System.out.print("*               *");
		setCursorPosition(16, 6);
		System.out.print("|               |");
		setCursorPosition(16, 7);
		System.out.print("|               |");
		setCursorPosition(16, 8);
		System.out.print("|               |");
		setCursorPosition(16, 9);
		System.out.print("*               *");

		setBackgroundColor(1, 1, 0);
		setForegroundColor(0, 0, 0);

		setCursorPosition(17, 5);
		System.out.print("+-------------+");
		setCursorPosition(17, 6);
		System.out.print("|             |");
		setCursorPosition(17, 7);
		System.out.print("|             |");
		setCursorPosition(17, 8);
		System.out.print("|             |");
		setCursorPosition(17, 9);
		System.out.print("+-------------+");

		setBackgroundColor(1, .75, 0);
		setForegroundColor(0, 0, 0);

		setCursorPosition(18, 6);
		System.out.print("  LOAD MAZE  ");
		setCursorPosition(18, 7);
		System.out.print("  PLAY       ");
		setCursorPosition(18, 8);
		System.out.print("  EXIT       ");
	}

	public void drawStars(int starCount) {
		setBackgroundColor(0, 0, 0);
		setForegroundColor(1, 1, 1);

		for (int i = 0; i < starCount; i++) {
			Random random = new Random();
			setCursorPosition(random.nextInt(50), random.nextInt(20) + 3);
			int charNo = random.nextInt(3);
			if (charNo == 0)
				System.out.print(".");
			if (charNo == 1)
				System.out.print("*");
			if (charNo == 2)
				System.out.print("@");
		}

	}

	public static char random_trasure() {
		char treasures = ' ';
		int random_treasures = (int) (Math.random() * 40 + 1);

		if (random_treasures <= 12) {
			treasures = '1';
		} else if (random_treasures > 12 && random_treasures <= 20) {
			treasures = '2';
		} else if (random_treasures > 20 && random_treasures <= 26) {
			treasures = '3';
		} else if (random_treasures > 26 && random_treasures <= 31) {
			treasures = '4';
		} else if (random_treasures > 31 && random_treasures <= 35) {
			treasures = '5';
		} else if (random_treasures > 35 && random_treasures <= 37) {
			treasures = '=';
		} else if (random_treasures > 37 && random_treasures <= 38) {
			treasures = '*';
		} else if (random_treasures > 38 && random_treasures <= 40) {
			treasures = 'C';
		}
		return treasures;
	}

	public static void get_input() {

		char treasures = random_trasure();
		for (int i = 0; i < 1; i++) {
			int random_rows = (int) (Math.random() * 22 + 1);
			int random_columns = (int) (Math.random() * 54 + 1);

			if (Field.level[random_rows][random_columns] == ' ') {

				Field.level[random_rows][random_columns] = (char) input_queue.dequeue();
				input_queue.enqueue(treasures);
				if (Field.level[random_rows][random_columns] == 'C') {
					enemy_number++;
					enemy[enemy_number] = new Enemy(random_rows, random_columns, false, false, 0, 0, 0, 0, false);

				}
			} else {
				i--;
			}
		}
	}

	public void input_queue() {

		for (int i = 1; i <= 15; i++) {
			char treasures = random_trasure();

			input_queue.enqueue(treasures);

		}
	}

	public static void printgamemenu() {

		setCursorPosition(60, 0);
		System.out.print("Input");
		setCursorPosition(60, 1);
		System.out.print("<<<<<<<<<<<<<<<");
		setCursorPosition(60, 3);
		System.out.print("<<<<<<<<<<<<<<<");

		for (int i = 5; i <= 12; i++) {

			setCursorPosition(65, i);
			System.out.print("|   |");
		}

		setCursorPosition(65, 13);
		System.out.print("+---+");
		setCursorPosition(65, 14);
		System.out.print("P.Backpack");

		setCursorPosition(60, 16);
		System.out.print("P.Energy: ");
		setCursorPosition(60, 17);
		System.out.print("P.Score: ");
		setCursorPosition(60, 18);
		System.out.print("P.Life: ");
		setCursorPosition(60, 19);
		System.out.print("C.Score: ");
	}

	public static void printmenuvariables() {// energy-player score-player life-computer score
		// player energy
		Display.setCursorPosition(70, 16);
		setBackgroundColor(0, 0, 0);
		System.out.print("     ");
		setBackgroundColor(0, 0, .2);
		Display.setCursorPosition(70, 16);
		System.out.print(player.getPlayerEnergy());

		// player score
		Display.setCursorPosition(70, 17);
		System.out.print(player.getPlayerScore());

		// player life
		Display.setCursorPosition(70, 18);
		System.out.print(player.getPlayerLife());

		// computer score
		Display.setCursorPosition(70, 19);
		System.out.print(Enemy.getEnemyScore());
	}

	public static void printinputqueue() {

		setCursorPosition(60, 2);

		for (int i = 0; i < input_queue.size(); i++) {

			switch ((char) input_queue.peek()) {
			case 'C':
				Display.setForegroundColor(0, 0, 0);
				Display.setBackgroundColor(1, 0, 0);
				break;
			case '1':
				Display.setForegroundColor(1, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '2':
				Display.setForegroundColor(.75, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '3':
				Display.setForegroundColor(.5, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '4':
				Display.setForegroundColor(.25, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '5':
				Display.setForegroundColor(0, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '=':
				Display.setForegroundColor(0, 0, 0);
				Display.setBackgroundColor(1, 0, 1);
				break;
			case '*':
				Display.setForegroundColor(0, 0, 0);
				Display.setBackgroundColor(0, 1, 1);
				break;
			}
			System.out.print(input_queue.peek());
			input_queue.enqueue(input_queue.dequeue());
		}
		Display.setForegroundColor(0, .5, 1);
		Display.setBackgroundColor(0, 0, .2);
	}

	public static void clearbackpack() {
		for (int i = 12; i >= 5; i--) {
			setCursorPosition(67, i);

			System.out.print(" ");

		}
	}

	public static void checkbackpack() {
		if (Display.Backpack.size() > 1) {

			if ((char) Backpack.peek() == '2' || (char) Backpack.peek() == '3' || (char) Backpack.peek() == '4'
					|| (char) Backpack.peek() == '5') {

				char TopElement = (char) Backpack.pop();
				char SecondElement = (char) Backpack.peek();
				Backpack.push(TopElement);

				if (TopElement == SecondElement && TopElement == '2') {
					player.setPlayerEnergy(player.getPlayerEnergy() + 30);
					Display.Backpack.pop();
					Display.Backpack.pop();
					clearbackpack();
				} else if (TopElement == SecondElement && TopElement == '3') {
					Display.Backpack.pop();
					Display.Backpack.pop();
					Backpack.push('=');
					clearbackpack();
				} else if (TopElement == SecondElement && TopElement == '4') {
					player.setPlayerEnergy(player.getPlayerEnergy() + 240);
					Display.Backpack.pop();
					Display.Backpack.pop();
					clearbackpack();
				} else if (TopElement == SecondElement && TopElement == '5') {
					Display.Backpack.pop();
					Display.Backpack.pop();
					Backpack.push('*');
					clearbackpack();
				} else if (TopElement != SecondElement && (SecondElement != '*' && SecondElement != '=')) {
					Display.Backpack.pop();
					Display.Backpack.pop();
					clearbackpack();
				}
			}
		}
	}

	public static void printbackpack() {

		int cursor = 12;

		Stack TempStack = new Stack(8);

		clearbackpack();

		while (!Backpack.isEmpty()) {

			TempStack.push(Backpack.pop());
		}

		while (!TempStack.isEmpty()) {

			setCursorPosition(67, cursor);

			switch ((char) TempStack.peek()) {
			case '2':
				Display.setForegroundColor(.75, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '3':
				Display.setForegroundColor(.5, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '4':
				Display.setForegroundColor(.25, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '5':
				Display.setForegroundColor(0, 1, 0);
				Display.setBackgroundColor(0, 0, .2);
				break;
			case '=':
				Display.setForegroundColor(0, 0, 0);
				Display.setBackgroundColor(1, 0, 1);
				break;
			case '*':
				Display.setForegroundColor(0, 0, 0);
				Display.setBackgroundColor(0, 1, 1);
				break;
			}

			System.out.print(TempStack.peek());
			cursor--;

			Backpack.push(TempStack.pop());
		}

	}

	public void startGame() throws InterruptedException {
		Enemy.setEnemyScore(0);
		Player.setPlayerScore(0);

		setBackgroundColor(0, 0, 0);
		setForegroundColor(0, .5, 1);
		for (int i = 1; i <= 20; i++) {

			int random_rows = (int) (Math.random() * 22 + 1);
			int random_columns = (int) (Math.random() * 54 + 1);

			char treasures = random_trasure();

			if (Field.level[random_rows][random_columns] == ' ') {

				Field.level[random_rows][random_columns] = treasures;

				if (treasures == '=' || treasures == '*')
					Field.addDevice(new Device(random_columns, random_rows, 1000));
				else if (treasures == 'C') {
					enemy_number++;
					enemy[enemy_number] = new Enemy(random_rows, random_columns, false, false, 0, 0, 0, 0, false);
				}

			} else {
				i--;
			}

		}

		char playerSign = 'P';

		Random rnd = new Random();

		while (true) {

			int px = rnd.nextInt(54); // daha kullanışlı hale gelmesi lazım
			int py = rnd.nextInt(22);

			if (Field.level[py][px] != '#' && px != 0 && py != 0) {

				player.setPy(py);
				player.setPx(px);
				Field.level[py][px] = playerSign;
				setCursorPosition(0, 0);
				Field.printgamearea();
				break;

			}

		}

		int count = 0;
		int time = 0;
		setCursorPosition(60, 21);
		cn.getTextWindow().output("Time: " + time);
		setCursorPosition(0, 0);

		klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (!keypr) {
					keypr = true;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		};
		cn.getTextWindow().addKeyListener(klis);

		int moveCooldown = 10;

		while (true) {

			if (moveCooldown == 0) {
				if (keypr) {
					if (rkey == KeyEvent.VK_DOWN) {
						player.playerDown();
						checkbackpack();
						printbackpack();
					} else if (rkey == KeyEvent.VK_UP) {

						player.playerUp();
						checkbackpack();
						printbackpack();
					} else if (rkey == KeyEvent.VK_LEFT) {
						player.playerLeft();
						checkbackpack();
						printbackpack();
					} else if (rkey == KeyEvent.VK_RIGHT) {
						player.playerRight();
						checkbackpack();
						printbackpack();
					} else if (rkey == KeyEvent.VK_W) {
						player.using_W();
						printbackpack();
					} else if (rkey == KeyEvent.VK_S) {
						player.using_S();
						printbackpack();
					} else if (rkey == KeyEvent.VK_A) {
						player.using_A();
						printbackpack();
					} else if (rkey == KeyEvent.VK_D) {
						player.using_D();
						printbackpack();
					}

					keypr = false;
				}
				if (player.getPlayerEnergy() == 0)
					moveCooldown = 20;
				else
					moveCooldown = 10;
			}

			Thread.sleep(25);
			if (moveCooldown > 0)
				moveCooldown--;

			setCursorPosition(0, 0);
			Field.printgamearea();
			printmenuvariables();

			if (count % 40 == 0) {
				if (player.getPlayerLife() < 1)
					break;

				player.energyDown();
				time++;
				setCursorPosition(60, 21);
				cn.getTextWindow().output("Time: " + time);
				setCursorPosition(0, 0);

			}
			if (count % 120 == 0) {
				get_input();
				printinputqueue();
			}
			if (count % 20 == 0) {
				for (int i = 0; i < enemy_number + 1; i++) {
					ComputerMovement(i);

				}
			}

			// device control
			int deviceNo = 0;
			while (true) {
				Device device = Field.getDevice(deviceNo);
				if (device == null)
					break;

				if (device.getTime() <= 0) {
					Field.level[device.getY()][device.getX()] = ' ';
					Field.removeDevice(deviceNo);
				} else {
					device.decreaseTime();
					deviceNo++;
				}
			}

			if (count % 20 == 0) {

				// computer steal
				backpacksteal();

				// look for every movable treasure
				int[][] treasure = new int[256][3];
				int treasureCount = 0;

				char[][] level = Field.getLevel();
				int x = 0;
				int y = 0;

				for (char[] rows : level) {
					x = 0;
					for (char character : rows) {
						if ((character == '4') || (character == '5')) {
							treasure[treasureCount][0] = x;
							treasure[treasureCount][1] = y;
							treasure[treasureCount][2] = character;
							treasureCount++;
						}
						x++;
					}
					y++;
				}

				Random random = new Random();

				for (int i = 0; i < treasureCount; i++) {

					boolean canMoveTreasure = true;

					for (int k = -1; k < 2; k++)
						for (int l = -1; l < 2; l++) {
							if (Field.level[treasure[i][1] + k][treasure[i][0] + l] == '*') {
								if (treasure[i][2] == '4') {
									player.addScore(50);
									Field.level[treasure[i][1]][treasure[i][0]] = ' ';
									canMoveTreasure = false;
								} else if (treasure[i][2] == '5') {
									player.addScore(150);
									Field.level[treasure[i][1]][treasure[i][0]] = ' ';
									canMoveTreasure = false;
								} else if (treasure[i][2] == '3') {
									player.addScore(15);
									Field.level[treasure[i][1]][treasure[i][0]] = ' ';
									canMoveTreasure = false;
								} else if (treasure[i][2] == '1') {
									player.addScore(1);
									Field.level[treasure[i][1]][treasure[i][0]] = ' ';
									canMoveTreasure = false;
								} else if (treasure[i][2] == '2') {
									player.addScore(5);
									Field.level[treasure[i][1]][treasure[i][0]] = ' ';
									canMoveTreasure = false;
								}
							} else if (Field.level[treasure[i][1] + k][treasure[i][0] + l] == '=') {
								canMoveTreasure = false;
							}
						}

					if (canMoveTreasure) {

						boolean treasureMoved = false;
						int tries = 0;
						while (!treasureMoved) {
							switch (random.nextInt(4)) {
							case 0:
								try {
									if (level[treasure[i][1] + 1][treasure[i][0]] == ' ') {
										level[treasure[i][1] + 1][treasure[i][0]] = (char) treasure[i][2];
										treasureMoved = true;
									}
								} catch (Exception e) {

								}
								break;
							case 1:
								try {
									if (level[treasure[i][1] - 1][treasure[i][0]] == ' ') {
										level[treasure[i][1] - 1][treasure[i][0]] = (char) treasure[i][2];
										treasureMoved = true;
									}
								} catch (Exception e) {

								}
								break;
							case 2:
								try {
									if (level[treasure[i][1]][treasure[i][0] + 1] == ' ') {
										level[treasure[i][1]][treasure[i][0] + 1] = (char) treasure[i][2];
										treasureMoved = true;
									}
								} catch (Exception e) {

								}
								break;
							case 3:
								try {
									if (level[treasure[i][1]][treasure[i][0] - 1] == ' ') {
										level[treasure[i][1]][treasure[i][0] - 1] = (char) treasure[i][2];
										treasureMoved = true;
									}
								} catch (Exception e) {

								}
								break;
							}
							tries++;
							if (tries == 4)
								break;
						}

						if (treasureMoved)
							level[treasure[i][1]][treasure[i][0]] = ' ';
					}
				}

			}

			count++;
		}

	}

	static public void backpacksteal() {
		for (int i = 0; i < enemy_number + 1; i++) {
			for (int j = enemy[i].getX() - 1; j <= enemy[i].getX() + 1; j++) {
				for (int k = enemy[i].getY() - 1; k <= enemy[i].getY() + 1; k++) {
					try {
						if (Field.level[j][k] == 'P') {
							for (int l = 0; l < 2; l++) {
								Object item = Display.Backpack.pop();
								if (item != null)
									ComputerScore(i, (char) item);
								else
									break;
							}
						}
					} catch (Exception e) {

					}
				}
			}
		}
		Display.printbackpack();
	}

	static public void closesttarget(int no) {

		int a = 1;
		while (!enemy[no].isIs_trapped() && !enemy[no].isIs_warpped() && !enemy[no].isTargetchoosed()) {
			for (int j = enemy[no].getX() - a; j <= enemy[no].getX() + a; j++) {
				for (int k = enemy[no].getY() - a; k <= enemy[no].getY() + a; k++) {
					if (j >= 0 && k >= 0 && j <= 22 && k <= 54) {
						if (Field.level[j][k] == '=') {
							enemy[no].setIs_trapped(true);
							break;
						} else if (Field.level[j][k] == '*') {
							enemy[no].setIs_warpped(true);
							break;
						} else if (Field.level[j][k] == 'P' || Field.level[j][k] == 'C' || Field.level[j][k] == '#'
								|| Field.level[j][k] == ' ') {
						}
						// continue;
						else {
							enemy[no].setTarget_value((int) Field.level[j][k]);
							enemy[no].setTargetx(j);
							enemy[no].setTargety(k);
							enemy[no].setTargetchoosed(true);
						}
					}

				}
				if (enemy[no].isIs_trapped())
					break;
				if (enemy[no].isIs_warpped())
					break;

			}
			if (enemy[no].getTarget_value() == 0 && !enemy[no].isIs_warpped() && !enemy[no].isIs_trapped())
				a++;
		}

	}

	public static boolean roadcheck(int no, int x_diff, int y_diff) {
		if (Field.level[enemy[no].getX() - x_diff][enemy[no].getY() - y_diff] != '*'
				&& Field.level[enemy[no].getX() - x_diff][enemy[no].getY() - y_diff] != '#'
				&& Field.level[enemy[no].getX() - x_diff][enemy[no].getY() - y_diff] != 'P'
				&& Field.level[enemy[no].getX() - x_diff][enemy[no].getY() - y_diff] != '='
				&& Field.level[enemy[no].getX() - x_diff][enemy[no].getY() - y_diff] != 'C')
			return true;
		else
			return false;

	}

	static public boolean isTrapped(int no) {
		boolean flag = false;
		for (int i = enemy[no].getX() - 1; i <= enemy[no].getX() + 1; i++) {
			for (int j = enemy[no].getY() - 1; j <= enemy[no].getY() + 1; j++) {
				if ((i >= 0 && j >= 0 && i <= 22 && j <= 54)) {
					if (Field.level[i][j] == '=') {
						enemy[no].setIs_trapped(true);
						flag = true;
						break;
					}
				}
				if (flag)
					break;
			}
		}

		if (!flag)
			enemy[no].setIs_trapped(false);
		return flag;
	}

	static public boolean isWarpped(int no) {
		boolean flag = false;
		for (int i = enemy[no].getX() - 1; i <= enemy[no].getX() + 1; i++) {
			for (int j = enemy[no].getY() - 1; j <= enemy[no].getY() + 1; j++) {
				if ((i >= 0 && j >= 0 && i <= 22 && j <= 54)) {
					if (Field.level[i][j] == '*') {
						enemy[no].setIs_warpped(true);
						Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
						flag = true;
						break;
					}
				}
				if (flag)
					break;
			}
		}
		return flag;
	}

	static public void ComputerMovement(int no) {
		char temp = 0;
		if (!enemy[no].isIs_warpped()) {
			isTrapped(no);
			closesttarget(no);
			Random random = new Random();

			if (!enemy[no].isIs_trapped() && !enemy[no].isIs_warpped()) {
				int x_difference = enemy[no].getX() - enemy[no].getTargetx();
				int y_difference = enemy[no].getX() - enemy[no].getTargety();
				if (x_difference >= 0 && roadcheck(no, 1, 0)) {
					temp = Field.level[enemy[no].getX() - 1][enemy[no].getY()];// holds the char at
																				// the place
					Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
					Field.level[enemy[no].getX() - 1][enemy[no].getY()] = 'C';
					enemy[no].setX(enemy[no].getX() - 1);
				} else if (x_difference <= 0 && roadcheck(no, -1, 0)) {
					temp = Field.level[enemy[no].getX() + 1][enemy[no].getY()];// holds the char at
																				// the place
					Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
					Field.level[enemy[no].getX() + 1][enemy[no].getY()] = 'C';
					enemy[no].setX(enemy[no].getX() + 1);
				} else if (y_difference >= 0 && roadcheck(no, 0, 1)) {
					temp = Field.level[enemy[no].getX()][enemy[no].getY() - 1];// holds the char at
																				// the place
					Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
					Field.level[enemy[no].getX()][enemy[no].getY() - 1] = 'C';
					enemy[no].setY(enemy[no].getY() - 1);
				} else if (y_difference <= 0 && roadcheck(no, 0, -1)) {
					temp = Field.level[enemy[no].getX()][enemy[no].getY() + 1];// holds the char at
																				// the place
					Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
					Field.level[enemy[no].getX()][enemy[no].getY() + 1] = 'C';
					enemy[no].setY(enemy[no].getY() + 1);
				} else {
					boolean moved = false;
					int tries = 0;
					while (!moved) {
						switch (random.nextInt(4)) {
						case 0: {
							if (Field.level[enemy[no].getX() - 1][enemy[no].getY()] == '1'
									|| Field.level[enemy[no].getX() - 1][enemy[no].getY()] == '2'
									|| Field.level[enemy[no].getX() - 1][enemy[no].getY()] == '3'
									|| Field.level[enemy[no].getX() - 1][enemy[no].getY()] == '4'
									|| Field.level[enemy[no].getX() - 1][enemy[no].getY()] == '5'
									|| Field.level[enemy[no].getX() - 1][enemy[no].getY()] == 'P'
									|| Field.level[enemy[no].getX() - 1][enemy[no].getY()] == ' ') {
								Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
								temp = Field.level[enemy[no].getX() - 1][enemy[no].getY()];
								Field.level[enemy[no].getX() - 1][enemy[no].getY()] = 'C';
								moved = true;
								enemy[no].setX(enemy[no].getX() - 1);
							}
							tries++;
							break;
						}
						case 1: {
							if (Field.level[enemy[no].getX()][enemy[no].getY() - 1] == '1'
									|| Field.level[enemy[no].getX()][enemy[no].getY() - 1] == '2'
									|| Field.level[enemy[no].getX()][enemy[no].getY() - 1] == '3'
									|| Field.level[enemy[no].getX()][enemy[no].getY() - 1] == '4'
									|| Field.level[enemy[no].getX()][enemy[no].getY() - 1] == '5'
									|| Field.level[enemy[no].getX()][enemy[no].getY() - 1] == 'P'
									|| Field.level[enemy[no].getX()][enemy[no].getY() - 1] == ' ') {
								Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
								temp = Field.level[enemy[no].getX()][enemy[no].getY() - 1];
								Field.level[enemy[no].getX()][enemy[no].getY() - 1] = 'C';
								moved = true;
								enemy[no].setY(enemy[no].getY() - 1);

							}
							tries++;
							break;
						}
						case 2: {
							if (Field.level[enemy[no].getX() + 1][enemy[no].getY()] == '1'
									|| Field.level[enemy[no].getX() + 1][enemy[no].getY()] == '2'
									|| Field.level[enemy[no].getX() + 1][enemy[no].getY()] == '3'
									|| Field.level[enemy[no].getX() + 1][enemy[no].getY()] == '4'
									|| Field.level[enemy[no].getX() + 1][enemy[no].getY()] == '5'
									|| Field.level[enemy[no].getX() + 1][enemy[no].getY()] == 'P'
									|| Field.level[enemy[no].getX() + 1][enemy[no].getY()] == ' ') {
								Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
								temp = Field.level[enemy[no].getX() + 1][enemy[no].getY()];
								Field.level[enemy[no].getX() + 1][enemy[no].getY()] = 'C';
								moved = true;
								enemy[no].setX(enemy[no].getX() + 1);
							}
							tries++;
							break;
						}
						case 3: {
							if (Field.level[enemy[no].getX()][enemy[no].getY() + 1] == '1'
									|| Field.level[enemy[no].getX()][enemy[no].getY() + 1] == '2'
									|| Field.level[enemy[no].getX()][enemy[no].getY() + 1] == '3'
									|| Field.level[enemy[no].getX()][enemy[no].getY() + 1] == '4'
									|| Field.level[enemy[no].getX()][enemy[no].getY() + 1] == '5'
									|| Field.level[enemy[no].getX()][enemy[no].getY() + 1] == 'P'
									|| Field.level[enemy[no].getX()][enemy[no].getY() + 1] == ' ') {
								Field.level[enemy[no].getX()][enemy[no].getY()] = ' ';
								temp = Field.level[enemy[no].getX()][enemy[no].getY() + 1];
								Field.level[enemy[no].getX()][enemy[no].getY() + 1] = 'C';
								enemy[no].setY(enemy[no].getY() + 1);
								moved = true;
							}
							tries++;
							break;
						}
						}
						if (tries == 4) {
							break;
						}
						tries++;
						break;
					}
				}

			}
			if (temp != ' ')
				ComputerScore(no, temp);
			if (enemy[no].getX() == enemy[no].getTargetx() && enemy[no].getY() == enemy[no].getTargety())
				enemy[no].setTargetchoosed(false);
			isWarpped(no);
		}
		

		if ((enemy[no].getX() == player.getPy()) && (enemy[no].getY() == player.getPx()))
			player.takeDamage();
		else {
			Field.level[player.getPy()][player.getPx()] = 'P';
		}

	}

	public static void ComputerScore(int no, char element) {
		if (element == '1')
			Enemy.addScore(2);
		else if (element == '2')
			Enemy.addScore(10);
		else if (element == '3')
			Enemy.addScore(30);
		else if (element == '4')
			Enemy.addScore(100);
		else if (element == '5')
			Enemy.addScore(300);
		else if (element == '=')
			Enemy.addScore(300);
		else if (element == '*')
			Enemy.addScore(300);

	}
}
