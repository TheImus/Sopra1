package model;

import java.util.List;

public class Participant {

	private String specialNeeds;

	private boolean changedSinceExport;

	private List<Restriction> restriction;

	private Person person;

	private Address address;

	private Course courseWish;

	public static void setChanged(List<Participant> participants, boolean value) {

	}

	/**
	 *  
	 */
	public void removeRestriction(Restriction restriction) {

	}

	public void addRestriction(Restriction restriction) {

	}

}
