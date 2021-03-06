package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable, IrvingMatchable {

	public final int TEAM_MAX_SIZE = 2;
	
	private Participant host;

	private List<Participant> members;
	
	
	public Team() {
		this.host = null;
		this.members = new ArrayList<Participant>();
	}
	
	public Team(Participant host, Participant member) {//'member the good times
		this.host = host;
		this.members = new ArrayList<Participant>();
		this.members.add(member);
	}

	public List<Participant> getParticipants() {
		List<Participant> list = new ArrayList<>();
		if(host!=null)
		{
			list.add(host);
		}	
		for(Participant p:members)
		{
			list.add(p);
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
		else if(members.size() == TEAM_MAX_SIZE){
			ArrayList<Restriction> subSet = Participant.getRestrictionUnionForParticipants(members.get(0), members.get(1));
			restrictions.addAll(Restriction.getUnionForRestrictions(subSet, host.getRestrictions()));
		}
		else if(host != null){
			restrictions.addAll(host.getRestrictions());
		}
		return restrictions;
	}


}
