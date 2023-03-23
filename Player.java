package startrekwarpwars;

public class Player {

	private int px;
	private int py;
	private static int playerEnergy;
	private static int playerScore;
	private int playerLife;

	public Player(int px, int py, int playerEnergy, int playerScore, int playerLife) {

		this.px = px;
		this.py = py;
		this.playerEnergy = playerEnergy;
		Player.playerScore = playerScore;
		this.playerLife = playerLife;

	}

	public void energyDown() {
		if (playerEnergy != 0)
			playerEnergy--;
	}

	public void addScore(int score) {
		playerScore += score;
	}

	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public int getPy() {
		return py;
	}

	public void setPy(int py) {
		this.py = py;
	}

	public int getPlayerEnergy() {
		return playerEnergy;
	}

	public void setPlayerEnergy(int playerEnergy) {
		this.playerEnergy = playerEnergy;
	}

	public static int getPlayerScore() {
		return playerScore;
	}

	public static void setPlayerScore(int x) {
		playerScore = x;
	}

	public int getPlayerLife() {
		return playerLife;
	}

	public void takeDamage() {
		playerLife--;
	}

	public void setPlayerLife(int playerLife) {
		this.playerLife = playerLife;
	}

	public void playerLeft() {

		if ((Field.level[py][px - 1] != '#') && (Field.level[this.getPy()][this.getPx() - 1] == ' ')) {

			this.setPx(this.getPx() - 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy()][this.getPx() + 1] = ' ';

		} else if ((Field.level[this.getPy()][this.getPx() - 1] != '#')
				&& (Field.level[this.getPy()][this.getPx() - 1] != ' ')
				&& (Field.level[this.getPy()][this.getPx() - 1] != '=')
				&& (Field.level[this.getPy()][this.getPx() - 1] != '*')) {

			char treasures = Field.level[this.getPy()][this.getPx() - 1];

			if (treasures == '1') {
				this.addScore(1);
			} else if (treasures == '2') {
				this.addScore(5);
			} else if (treasures == '3') {
				this.addScore(15);
			} else if (treasures == '4') {
				this.addScore(50);
			} else if (treasures == '5') {
				this.addScore(150);
			}

			char temp_treasure = ' ';
			if (Display.Backpack.size() != 0) {
				temp_treasure = (char) Display.Backpack.peek();
			}

			this.setPx(this.getPx() - 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy()][this.getPx() + 1] = ' ';

			if (treasures != '*' && treasures != '=' && treasures != 'C' && treasures != '1') {

				Display.Backpack.push((char) treasures);

			}

		}

	}

	public void playerRight() {

		if ((Field.level[this.getPy()][this.getPx() + 1] != '#')
				&& (Field.level[this.getPy()][this.getPx() + 1] == ' ')) {

			this.setPx(this.getPx() + 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy()][this.getPx() - 1] = ' ';

		}

		else if ((Field.level[this.getPy()][this.getPx() + 1] != '#')
				&& (Field.level[this.getPy()][this.getPx() + 1] != ' ')
				&& (Field.level[this.getPy()][this.getPx() + 1] != '=')
				&& (Field.level[this.getPy()][this.getPx() + 1] != '*')) {

			char treasures = Field.level[this.getPy()][this.getPx() + 1];

			if (treasures == '1') {
				this.addScore(1);
			} else if (treasures == '2') {
				this.addScore(5);
			} else if (treasures == '3') {
				this.addScore(15);
			} else if (treasures == '4') {
				this.addScore(50);
			} else if (treasures == '5') {
				this.addScore(150);
			}

			char temp_treasure = ' ';
			if (Display.Backpack.size() != 0) {
				temp_treasure = (char) Display.Backpack.peek();
			}

			this.setPx(this.getPx() + 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy()][this.getPx() - 1] = ' ';

			if (treasures != '*' && treasures != '=' && treasures != 'C' && treasures != '1') {

				Display.Backpack.push((char) treasures);

			}

		}
	}

	public void playerUp() {

		if ((Field.level[this.getPy() - 1][this.getPx()] != '#')
				&& (Field.level[this.getPy() - 1][this.getPx()] == ' ')) {

			this.setPy(this.getPy() - 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy() + 1][this.getPx()] = ' ';

		} else if ((Field.level[this.getPy() - 1][this.getPx()] != '#')
				&& (Field.level[this.getPy() - 1][this.getPx()] != ' ')
				&& (Field.level[this.getPy() - 1][this.getPx()] != '=')
				&& (Field.level[this.getPy() - 1][this.getPx()] != '*')) {

			char treasures = Field.level[this.getPy() - 1][this.getPx()];

			if (treasures == '1') {
				this.addScore(1);
			} else if (treasures == '2') {
				this.addScore(5);
			} else if (treasures == '3') {
				this.addScore(15);
			} else if (treasures == '4') {
				this.addScore(50);
			} else if (treasures == '5') {
				this.addScore(150);
			}

			char temp_treasure = ' ';
			if (Display.Backpack.size() != 0) {
				temp_treasure = (char) Display.Backpack.peek();
			}

			this.setPy(this.getPy() - 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy() + 1][this.getPx()] = ' ';

			if (treasures != '*' && treasures != '=' && treasures != 'C' && treasures != '1') {

				Display.Backpack.push((char) treasures);

			}
		}
	}

	public void playerDown() {

		if ((Field.level[this.getPy() + 1][this.getPx()] != '#')
				&& (Field.level[this.getPy() + 1][this.getPx()] == ' ')) {

			this.setPy(this.getPy() + 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy() - 1][this.getPx()] = ' ';

		} else if ((Field.level[this.getPy() + 1][this.getPx()] != '#')
				&& (Field.level[this.getPy() + 1][this.getPx()] != ' ')
				&& (Field.level[this.getPy() + 1][this.getPx()] != '=')
				&& (Field.level[this.getPy() + 1][this.getPx()] != '*')) {

			char treasures = Field.level[this.getPy() + 1][this.getPx()];

			if (treasures == '1') {
				this.addScore(1);
			} else if (treasures == '2') {
				this.addScore(5);
			} else if (treasures == '3') {
				this.addScore(15);
			} else if (treasures == '4') {
				this.addScore(50);
			} else if (treasures == '5') {
				this.addScore(150);
			}

			char temp_treasure = ' ';
			if (Display.Backpack.size() != 0) {
				temp_treasure = (char) Display.Backpack.peek();
			}

			this.setPy(this.getPy() + 1);
			Field.level[this.getPy()][this.getPx()] = 'P';
			Field.level[this.getPy() - 1][this.getPx()] = ' ';

			if (treasures != '*' && treasures != '=' && treasures != 'C' && treasures != '1') {

				Display.Backpack.push((char) treasures);

			}

		}
	}

	public void using_W() {

		if (Display.Backpack.size() != 0 && Field.level[this.getPy() - 1][this.getPx()] == ' ') {
			if ((char) Display.Backpack.peek() == '*' || (char) Display.Backpack.peek() == '=') {
				Field.addDevice(new Device(this.getPx(), this.getPy() - 1, 1000));
				Field.level[this.getPy() - 1][this.getPx()] = (char) Display.Backpack.pop();
				Display.clearbackpack();
			} else {
				Display.Backpack.pop();
				Display.clearbackpack();
			}
		}

	}

	public void using_S() {

		if (Display.Backpack.size() != 0 && Field.level[this.getPy() + 1][this.getPx()] == ' ') {
			if ((char) Display.Backpack.peek() == '*' || (char) Display.Backpack.peek() == '=') {
				Field.addDevice(new Device(this.getPx(), this.getPy() + 1, 1000));
				Field.level[this.getPy() + 1][this.getPx()] = (char) Display.Backpack.pop();
				Display.clearbackpack();
			} else {
				Display.Backpack.pop();
				Display.clearbackpack();
			}
		}

	}

	public void using_A() {

		if (Display.Backpack.size() != 0 && Field.level[this.getPy()][this.getPx() - 1] == ' ') {
			if ((char) Display.Backpack.peek() == '*' || (char) Display.Backpack.peek() == '=') {
				Field.addDevice(new Device(this.getPx() - 1, this.getPy(), 1000));
				Field.level[this.getPy()][this.getPx() - 1] = (char) Display.Backpack.pop();
				Display.clearbackpack();
			} else {
				Display.Backpack.pop();
				Display.clearbackpack();
			}
		}

	}

	public void using_D() {

		if (Display.Backpack.size() != 0 && Field.level[this.getPy()][this.getPx() + 1] == ' ') {
			if ((char) Display.Backpack.peek() == '*' || (char) Display.Backpack.peek() == '=') {
				Field.addDevice(new Device(this.getPx() + 1, this.getPy(), 1000));
				Field.level[this.getPy()][this.getPx() + 1] = (char) Display.Backpack.pop();
				Display.clearbackpack();
			} else {
				Display.Backpack.pop();
				Display.clearbackpack();
			}
		}

	}

}
