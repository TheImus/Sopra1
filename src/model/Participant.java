package model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class Participant implements Serializable, IrvingMatchable{

	private String specialNeeds;

	private boolean changedSinceExport;

	private List<Restriction> restriction;

	private Person person;

	private Address address;

	private Course courseWish;

	
	public Participant() {
		this.specialNeeds = "";
		this.changedSinceExport = true;
		this.restriction = new ArrayList<Restriction>();
		this.person = new Person();
		this.address = new Address();
		this.courseWish = null;
	}
	
	public Participant(Person person, Address address, Course courseWish, List<Restriction> restrictions, String specialNeeds, String eMail){
		this.changedSinceExport = true;
		this.person = person;
		this.address = address;
		this.courseWish = courseWish;
		this.restriction = restrictions;
		this.specialNeeds = specialNeeds;
		person.setMailAddress(eMail);
	}

	/* 
	public static void setChanged(List<Participant> participants, boolean value) {

	}
	*/

	/**
	 *  
	 */
	public void removeRestriction(Restriction oldRestriction) {
		changedSinceExport = true;
		restriction.remove(oldRestriction);
	}

	public void addRestriction(Restriction newRestriction) {
		changedSinceExport = true;
		restriction.add(newRestriction);
	}
	
	/**
	 * 
	 * @param participant
	 * @return
	 */
	public Participant createNewParticipant() {
		Participant participant = new Participant();
		participant.address = this.address.clone();
		participant.changedSinceExport = true;
		participant.person = this.getPerson();
		participant.restriction = new ArrayList<Restriction>();
		participant.specialNeeds = "";
		return participant;
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

	@Override
	public List<Restriction> getRestrictions() {
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
	
	public static ArrayList<Restriction> getRestrictionIntersectionForParticipants(Participant participant1, Participant participant2){
		ArrayList<Restriction> restrictionIntersection = Restriction.getIntersectionForRestrictions(participant1.getRestrictions(), participant2.getRestrictions());
		return restrictionIntersection;
	}
	
	public static ArrayList<Restriction> getRestrictionSymmetricDifferenceForParticipants(Participant participant1,
			Participant participant2){
		ArrayList<Restriction> symDiff = Restriction.getSymmetricDifferenceForRestrictions(participant1.getRestrictions(), participant2.getRestrictions());
		return symDiff;
	}
	
	public static ArrayList<Restriction> getRestrictionUnionForParticipants(Participant participant1,
			Participant participant2){
		ArrayList<Restriction> union = Restriction.getUnionForRestrictions(participant1.getRestrictions(), participant2.getRestrictions());
		return union;
	}

}
