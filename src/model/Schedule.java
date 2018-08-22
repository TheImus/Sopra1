package model;

import java.util.List;

public class Schedule {
	
	// TODO: Hier wollten wir das doch irgendwie anders implementieren

	private List<Group> main;

	private Group group;

	private List<Group> dessert;

	private List<Group> starter;

	public List<Group> getMain() {
		return main;
	}

	public void setMain(List<Group> main) {
		this.main = main;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Group> getDessert() {
		return dessert;
	}

	public void setDessert(List<Group> dessert) {
		this.dessert = dessert;
	}

	public List<Group> getStarter() {
		return starter;
	}

	public void setStarter(List<Group> starter) {
		this.starter = starter;
	}

}
