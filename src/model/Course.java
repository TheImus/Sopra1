package model;
import java.io.Serializable;
public enum Course implements Serializable {
	STARTER,
	MAIN,
	DESSERT;
	
	public String getText(Course course){
		if(course.equals(STARTER)){
			return "Vorspeise";
		}
		if(course.equals(MAIN)){
			return "Hautpgang";
		}
		else{
			return "Nachtisch";
		}
	}
}
