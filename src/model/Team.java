package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable{

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

}
