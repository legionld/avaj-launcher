package simulator;

import simulator.utils.ErrorsProvider;
import simulator.weather.WeatherProvider;
import simulator.controllers.WeatherTower;
import simulator.utils.Settings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;

public class Baloon extends Aircraft implements Flyable {

	private WeatherTower weatherTower;

	Baloon (String name, Coordinates coordinates) {
		super(name, coordinates);
	}

	@Override
	public void updateConditions() {

		String bts = "Balloon#" + this.name + "(" + this.id + ") The weather has changed. ";
		String say = null;

		String weather = WeatherProvider.getCurrentWeather(this.coordinates);

		switch (weather) {
			case "RAIN":
				changeCoordinates(0, 0, -5);
				say = "Rain... This summer is very rainy.\n";
				break;
			case "FOG":
				changeCoordinates(0, 0, -3);
				say = "The fog made it look like heaven here.\n";
				break;
			case "SUN":
				changeCoordinates(2, 0, 4);
				say = "Wow sun! This is unexpected.\n";
				break;
			case "SNOW":
				changeCoordinates(0, 0, -15);
				say = "Snow? Who made the weather forecast?\n";
				break;
		}

		try {
			Files.write(Paths.get(Settings.outPutFile), (bts + say).getBytes(), APPEND);
		} catch (IOException e) {
			ErrorsProvider.printError(22);
		}
	}

	@Override
	public void registerTower(WeatherTower weatherTower) {
		this.weatherTower = weatherTower;
		this.weatherTower.register(this);

		String bts = "Tower say: Balloon#" + this.name + "(" + this.id + ")" + " registered to weather tower\n";
		try {
			Files.write(Paths.get(Settings.outPutFile), bts.getBytes(), APPEND);
		} catch (IOException e) {
			ErrorsProvider.printError(22);
		}
	}

	/**
	 * Function for change coordinates Balloon
	 * @param longitude value to change this longitude
	 * @param latitude value to change this latitude
	 * @param height value to change this height
	 */
	private void changeCoordinates(int longitude, int latitude, int height) {

		if (this.coordinates.getLongitude() + longitude < Settings.MAX_LON)
			longitude += this.coordinates.getLongitude();
		else {
			longitude = Settings.MAX_LON;
			weatherTower.unregister(this);
		}

		if (this.coordinates.getLatitude() + latitude < Settings.MAX_LAT)
			latitude += this.coordinates.getLatitude();
		else {
			latitude = Settings.MAX_LAT;
			weatherTower.unregister(this);
		}

		if (this.coordinates.getHeight() + height < Settings.MAX_HEIGHT && this.coordinates.getHeight() + height > 0)
			height += this.coordinates.getHeight();
		else if (this.coordinates.getHeight() + height > 0 ){
			height = Settings.MAX_HEIGHT;
		}
		else {
			height = 0;
			weatherTower.unregister(this);
		}

		this.coordinates = new Coordinates(longitude, latitude, height);
	}
}
