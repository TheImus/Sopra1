package model;

import java.util.Collection;
import java.util.List;

public class Participant {

	private String specialNeeds;

	private boolean changedSinceExport;

	private Collection<Restriction> restriction;

	private Person person;

	private Address address;

	private Course courseWish;

	public static void setChanged(List participants, boolean value) {

	}

	/**
	 *  
	 */
	public void removeRestriction(Restriction restriction) {

	}

	public void addRestriction(Restriction restriction) {

	}

}
