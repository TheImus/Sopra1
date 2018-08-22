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

	public String getSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public boolean isChangedSinceExport() {
		return changedSinceExport;
	}

	public void setChangedSinceExport(boolean changedSinceExport) {
		this.changedSinceExport = changedSinceExport;
	}

	public List<Restriction> getRestriction() {
		return restriction;
	}

	public void setRestriction(List<Restriction> restriction) {
		this.restriction = restriction;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Course getCourseWish() {
		return courseWish;
	}

	public void setCourseWish(Course courseWish) {
		this.courseWish = courseWish;
	}

}
