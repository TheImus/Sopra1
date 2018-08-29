package model;

import java.util.ArrayList;
import java.util.List;

public interface IrvingMatchable {
	ArrayList<Restriction> getRestrictionSymmetricDifference(IrvingMatchable entity1, IrvingMatchable entity2);	
	List<Restriction> getRestrictions();
}
