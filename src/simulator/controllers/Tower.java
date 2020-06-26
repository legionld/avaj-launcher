package simulator.controllers;

import simulator.Flyable;

import java.util.ArrayList;

public class Tower {
	private ArrayList<Flyable> observers = new ArrayList<>();
	private ArrayList<Flyable> removeList = new ArrayList<>();

	public void register(Flyable flyable) {
		observers.add(flyable);
	}

	public void unregister(Flyable flyable) {
		removeList.add(flyable);
	}

	protected void conditionChange() {
		for(Flyable flyable : removeList) {
			observers.remove(flyable);
		}
		for (Flyable flyable : observers) {
			flyable.updateConditions();
		}
	}

}
