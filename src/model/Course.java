package model;
import java.io.Serializable;

import controller.ParticipantAction;
public enum Course implements Serializable {
	STARTER,
	MAIN,
	DESSERT;
	public String toString(){
		if 		(this.equals(STARTER)){
			return "Vorspeise";
		}else if(this.equals(MAIN)){
			return "Haupspeise";
		}else if(this.equals(DESSERT)){
			return "Nachspeise";
		}
		return "Unbekannter Gang";
	}
}
