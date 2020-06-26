package simulator;

import simulator.utils.ErrorsProvider;
import simulator.weather.WeatherProvider;
import simulator.controllers.WeatherTower;
import simulator.utils.Settings;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;

public class Helicopter extends Aircraft implements Flyable{

	private WeatherTower weatherTower;

	Helicopter(String name, Coordinates coordinates) {
		super(name, coordinates);
	}

	@Override
	public void updateConditions() {
		String bts = "Helicopter#" + this.name + "(" + this.id + ") The weather has changed. ";
		String say = null;
		String weather = WeatherProvider.getCurrentWeather(this.coordinates);

		switch (weather) {
			case "RAIN":
				changeCoordinates(5,0,0);
				say = " This rain. Rider on the Storm\n";
				break;
			case "FOG":
				changeCoordinates(1,0,0);
				say = " This fog. I can't see anything, I decrease speed\n";
				break;
			case "SUN":
				changeCoordinates(10,0,2);
				say = " This sun. I increase speed, and increase height\n";
				break;
			case "SNOW":
				changeCoordinates(0,0,-12);
				say = "This snow? I'm going down\n";
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
		String bts = "Tower say: Helicopter#" + super.name + "(" + super.id + ")" + " registered to weather tower\n";
		try {
			Files.write(Paths.get(Settings.outPutFile), bts.getBytes(), APPEND);
		} catch (IOException e) {
			ErrorsProvider.printError(22);
		}
	}

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
