package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable, IrvingMatchable{

	private Participant host;

	private List<Participant> members;
	
	
	public Team() {
		this.host = null;
		this.members = new ArrayList<Participant>();
	}

	public List<Participant> getParticipants() {
		List<Participant> list = new ArrayList<>();
		for(Participant p:members)
		{
			list.add(p);
		}
		if(host!=null)
		{
			list.add(host);
		}		
		return list;
	}

	public Participant getHost() {
		return host;
	}

	public void setHost(Participant host) {
		this.host = host;
	}

	public List<Participant> getMembers() {
		return members;
	}

	public void setMembers(List<Participant> members) {
		this.members = members;
	}
	
	public int getSize(){
		if(host != null){
			return members.size() + 1;
		}
		return members.size();
	}
	
	@Override
	public ArrayList<Restriction> getRestrictions(){
		ArrayList<Restriction> restrictions = new ArrayList<Restriction>();
		if(!members.isEmpty()){
			restrictions.addAll(Participant.getRestrictionUnionForParticipants(host, members.get(0)));
		}
		else if(members.size()==2){
			ArrayList<Restriction> subSet = Participant.getRestrictionUnionForParticipants(members.get(0), members.get(1));
			restrictions.addAll(Restriction.getUnionForRestrictions(subSet, host.getRestrictions()));
		}
		else if(host != null){
			restrictions.addAll(host.getRestrictions());
		}
		return restrictions;
	}


}
