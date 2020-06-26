package simulator.controllers;

import simulator.Coordinates;
import simulator.weather.WeatherProvider;

public class WeatherTower extends Tower{
	public String getWeather(Coordinates coordinates) {
		return WeatherProvider.getProvider().getCurrentWeather(coordinates);
	}

	void changeWeather() {
		WeatherProvider.getProvider();
		super.conditionChange();
	}
}
