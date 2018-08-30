package model;

import java.util.List;

public interface IrvingMatchable {
	static List<Restriction> getRestrictionSymmetricDifference(IrvingMatchable entity1, IrvingMatchable entity2){
		return Restriction.getSymmetricDifferenceForRestrictions(entity1.getRestrictions(), entity2.getRestrictions());
	}	
	List<Restriction> getRestrictions();
}
