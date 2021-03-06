package controller;

public enum ParticipantAction {
	REGISTER, 
	UNREGISTER, 
	REGISTER_NEW_PERSON, 
	UPDATE_PARTICIPANT;
	
	public String toString(){
		String action = this.name();
		String result = action.substring(0,1).toUpperCase() + action.substring(1).toLowerCase();
		
		//return result;
		switch(this){
		case REGISTER: result = "Anmelden"; break;
		case UNREGISTER: result = "Abmelden"; break;
		case REGISTER_NEW_PERSON: result = "Neue Person anmelden"; break;
		case UPDATE_PARTICIPANT: result = "Teilnehmer aktualisieren"; break;
		}
		return result;
	}
}
