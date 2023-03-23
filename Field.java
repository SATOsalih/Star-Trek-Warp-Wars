package startrekwarpwars;

public class Field {

	public Player player;
	private Enemy[] enemies;
	public static Device[] devices;
	private static int deviceCount;
	public static char[][] level;

	public Field(char[][] chararray) {
		this.deviceCount = 0;
		level = chararray;
		devices = new Device[255];
	}

	public static void printgamearea() {

		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				switch (level[i][j]) {
				case 'C':
					Display.setForegroundColor(0, 0, 0);
					Display.setBackgroundColor(1, 0, 0);
					break;
				case '#':
					Display.setForegroundColor(0, .5, 1);
					Display.setBackgroundColor(0, 0, .2);
					break;
				case ' ':
					Display.setForegroundColor(0, .5, 1);
					Display.setBackgroundColor(0, 0, .2);
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
				case 'P':
					Display.setForegroundColor(0, 0, 0);
					Display.setBackgroundColor(.75, .75, .75);
					break;
				}

				System.out.print(level[i][j]);
			}
			System.out.println();
		}

	}

	public static char[][] getLevel() {
		return level;
	}

	public static void setLevel(char[][] level) {
		Field.level = level;
	}

	public static Device getDevice(int i) {
		return devices[i];
	}

	public static void addDevice(Device device) {
		devices[deviceCount] = device;
		deviceCount++;
	}

	public static void removeDevice(int index) {
		devices[index] = null;

		for (int i = index; i < deviceCount; i++) {
			devices[i] = devices[i + 1];
		}
		deviceCount--;
	}
}
