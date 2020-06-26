package simulator;

import simulator.controllers.WeatherTower;

public interface Flyable {
	public void updateConditions();
	public void registerTower(WeatherTower weatherTower);
}
