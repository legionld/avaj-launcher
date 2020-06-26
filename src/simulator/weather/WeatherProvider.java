package simulator.weather;

import simulator.Coordinates;
import simulator.utils.Settings;

public class WeatherProvider {
	private static WeatherProvider weatherProvider 			= null;
	private static final int size							= Settings.MAX_LON * Settings.MAX_LAT * Settings.MAX_HEIGHT;
	private static String[] weather							= new String[size];

	private WeatherProvider() {
		changeWeather();
	}

	public static WeatherProvider getProvider() {
		if (weatherProvider == null)
			return new WeatherProvider();
		changeWeather();
		return weatherProvider;
	}

	public static String getCurrentWeather(Coordinates coordinates) {
//		System.out.println(coordinates.getLongitude() + " " + coordinates.getLatitude() + " " + coordinates.getHeight());
		return weather[coordinates.getLongitude() - 1
				+ coordinates.getLatitude() * (Settings.MAX_LON - 1)
				+ coordinates.getHeight() * (Settings.MAX_LON) * (Settings.MAX_LAT - 1)];
	}

	private static void changeWeather() {
		int i = size - 1;

		while (i >= 0) {
			int rand = (int)(Math.random() * 4);
			weather[i] = Settings.weather.values()[rand].name();
			--i;
		}
	}
}
