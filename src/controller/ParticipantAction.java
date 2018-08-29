package controller;

import com.sun.xml.internal.ws.util.StringUtils;

public enum ParticipantAction {
	REGISTER, 
	UNREGISTER, 
	REGISTER_NEW_PERSON, 
	UPDATE_PARTICIPANT;
	
	public String getText(){
		String action = this.name();
		String result = action.substring(0,1).toUpperCase() + action.substring(1).toLowerCase();
		
		return result;
		
	}
}
