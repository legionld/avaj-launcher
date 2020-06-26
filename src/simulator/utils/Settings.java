package simulator.utils;


public class Settings {

	/**
	 * Setting world data
	 */
	public static final int MAX_LON = 100;
	public static final int MAX_LAT = 100;
	public static final int MAX_HEIGHT = 100;
	public static final int SCALEBLE = 10;

	public enum weather {
		RAIN,
		FOG,
		SUN,
		SNOW
	};

	/**
	 * Settings program parameters
	 */
	public static final String md5Directory = "resource/md5/";
	public static final String outPutFile = "simulation.txt";
}
