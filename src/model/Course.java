package model;
import java.io.Serializable;

import controller.ParticipantAction;
public enum Course implements Serializable {
	STARTER,
	MAIN,
	DESSERT;
	public String toString(){
		if 		(this.equals(STARTER)){
			return "Starter";
		}else if(this.equals(MAIN)){
			return "Main";
		}else if(this.equals(DESSERT)){
			return "Dessert";
		}
		return "Unknown Course";
	}
}
