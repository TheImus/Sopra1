package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restriction implements Serializable{

	private String name;

	private boolean permanent;

	private List<Participant> participant;
	
	
	public Restriction() {
		this.name = "";
		this.permanent = false;
		this.participant = new ArrayList<Participant>();
	}

	public void removeParticipant(Participant oldParticipant) {
		participant.remove(oldParticipant);
	}

	public void addParticipant(Participant newParticipant) {
		participant.add(newParticipant);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public List<Participant> getParticipant() {
		return participant;
	}

	public void setParticipant(List<Participant> participant) {
		this.participant = participant;
	}
	
	public static ArrayList<Restriction> getIntersectionForRestrictions(List<Restriction> restrictionList1, List<Restriction> restrictionList2){
		ArrayList<Restriction> intersectionList = new ArrayList<Restriction>();
		for(Restriction restriction:restrictionList1){
			if(restrictionList2.contains(restriction)){
				intersectionList.add(restriction);
			}
		}
		return intersectionList;
	}
	
	public static ArrayList<Restriction> getSymmetricDifferenceForRestrictions(List<Restriction> restrictionList1, 
			List<Restriction> restrictionList2){
		ArrayList<Restriction> intersectionList = getIntersectionForRestrictions(restrictionList1, restrictionList2);
		ArrayList<Restriction> symDiffList = new ArrayList<Restriction>();
		for(Restriction restriction:restrictionList1){
			if(!intersectionList.contains(restriction)){
				symDiffList.add(restriction);
			}
		}
		for(Restriction restriction:restrictionList2){
			if(!symDiffList.contains(restriction) && !intersectionList.contains(restriction)){
				symDiffList.add(restriction);
			}
		}
		return symDiffList;
	}
	
	public static ArrayList<Restriction> getUnionForRestrictions(List<Restriction> restrictionList1, 
			List<Restriction> restrictionList2){
		ArrayList<Restriction> unionList = new ArrayList<Restriction>();
		unionList.addAll(restrictionList1);
		for(Restriction restriction:restrictionList2){
			if(!unionList.contains(restriction)){
				unionList.add(restriction);
			}
		}
		return unionList;
	}

}
