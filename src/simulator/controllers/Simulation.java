package simulator.controllers;

import simulator.AircraftFactory;
import simulator.Flyable;
import simulator.utils.ErrorsProvider;
import simulator.utils.MD5Hashing;
import simulator.utils.Settings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
	public static void main(String[] args) throws NoSuchAlgorithmException {

		List<Flyable> aircrafts 		= new ArrayList<>();
		String[] lines					= null;
		int	numsOfIterations			= 0;
		byte[] b = "".getBytes();

		if (args.length != 1) {
			ErrorsProvider.printError(1);
			return;
		}

		try {
			Files.write(Paths.get(Settings.outPutFile), b);
		} catch (IOException e) {
			ErrorsProvider.printError(22);
		}

		if ((lines = MD5Hashing.firsValidate(args[0])) == null)
			return;
		for (int i = 1; i < lines.length; ++i) {
			Flyable aircraft = dataReading(lines[i]);
			if (aircraft == null)
				return;
			aircrafts.add(aircraft);
			System.out.println(lines[i]);
		}

		numsOfIterations = Integer.parseInt(lines[0]);
		if (numsOfIterations == 0) {
			ErrorsProvider.printError(31);
			return;
		}

		WeatherTower weatherTower = new WeatherTower();

		for (Flyable aircraft : aircrafts) {
			aircraft.registerTower(weatherTower);
		}

		while (numsOfIterations > 0) {
			weatherTower.changeWeather();
			--numsOfIterations;
		}
	}

	private static Flyable dataReading(String line) {

		String[] str = line.split(" ");

		String type = str[0];
		String name = str[1];
		int longitude;
		int latitude;
		int height;

		try {
			longitude = scale(Integer.parseInt(str[2]), Settings.MAX_LON);
			latitude = scale(Integer.parseInt(str[3]), Settings.MAX_LAT);
			height = scale(Integer.parseInt(str[4]), Settings.MAX_HEIGHT);
		} catch (NumberFormatException e) {
			ErrorsProvider.printError(33);
			System.out.println(e);
			return null;
		}

		if (!type.equals("Baloon") && !type.equals("JetPlane") && !type.equals("Helicopter")) {
			ErrorsProvider.printError(34);
			return null;
		}

		Flyable aircraft = AircraftFactory.newAircraft(type, name, longitude, latitude, height);

		return aircraft;
	}

	private static int scale(int num, int max) {
		if (num < max)
			return num;
		return scale(num / Settings.SCALEBLE, max);
	}
}
