package startrekwarpwars;

public class Enemy {
	private static int enemyScore;
	private int x;
	private int y;
	private boolean is_warpped;
	private boolean is_trapped;
	private int target_value;
	private int targetx;
	private int targety;
	private boolean targetchoosed;
	

	public Enemy(int x, int y, boolean is_warpped, boolean is_trapped, int trapped_time, int target_value,
			int targetx, int targety, boolean targetchoosed) {
		this.x = x;
		this.y = y;
		this.is_warpped = is_warpped;
		this.is_trapped = is_trapped;
		this.target_value = target_value;
		this.targetx = targetx;
		this.targety = targety;
		this.targetchoosed = targetchoosed;
	}
	
	public static void addScore(int x) {
		enemyScore+=x;
	}
	
	public static int getScore() {
		return enemyScore;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isIs_warpped() {
		return is_warpped;
	}

	public void setIs_warpped(boolean is_warpped) {
		this.is_warpped = is_warpped;
	}

	public boolean isIs_trapped() {
		return is_trapped;
	}

	public void setIs_trapped(boolean is_trapped) {
		this.is_trapped = is_trapped;
	}

	public int getTarget_value() {
		return target_value;
	}

	public void setTarget_value(int target_value) {
		this.target_value = target_value;
	}

	public int getTargetx() {
		return targetx;
	}

	public void setTargetx(int targetx) {
		this.targetx = targetx;
	}

	public int getTargety() {
		return targety;
	}

	public void setTargety(int targety) {
		this.targety = targety;
	}

	public boolean isTargetchoosed() {
		return targetchoosed;
	}

	public void setTargetchoosed(boolean targetchoosed) {
		this.targetchoosed = targetchoosed;
	}

	/**
	 * @return the enemyScore
	 */
	public static int getEnemyScore() {
		return enemyScore;
	}

	/**
	 * @param enemyScore the enemyScore to set
	 */
	public static void setEnemyScore(int enemyScore) {
		Enemy.enemyScore = enemyScore;
	}
	
}