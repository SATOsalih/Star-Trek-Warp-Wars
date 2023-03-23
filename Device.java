package startrekwarpwars;

public class Device {

	private int x;
	private int y;
	private int time;

	public Device(int x, int y, int time) {
		this.x = x;
		this.y = y;
		this.time = time;
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

	public int getTime() {
		return time;
	}
	
	public void decreaseTime() {
		this.time -= 1;
	}

}
