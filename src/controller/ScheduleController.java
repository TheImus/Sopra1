package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import model.*;

/**
 * @author sopr026
 */

public class ScheduleController {

	private WalkingDinnerController walkingDinnerController;
	
	/**
	 * Create a new ScheduleController with a reference to
	 * the central WalkingDinnerController.
	 * @param walkingDinnerController Central WalkingDinnerController
	 * @return Returns a ScheduleController-entity with walkingDinnerController-attribute set to param
	 */
	public ScheduleController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**
	 * This method implements the scheduling for teams into valid groups for one course and is
	 * referring to the knowing-Relation and therefore other already scheduled courses.
	 * @return Returns a valid Group-Schedule for a course with reference to the knowing-Relation and therefore other courses
	 */
	public ArrayList<Group> generateGroups() {
		return null;
	}

	/**
	 * This method implements the first step of the scheduling-algorithm and
	 * generates a List of valid Teams of 2-3 members from all participants
	 * @return Returns a valid Team-Schedule for all Participants
	 */
	public ArrayList<Team> generateTeams() {
		//Begin of Team-Scheduling
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		List<Restriction> restrictions = currentEvent.getRestriction();
		List<Participant> vegans = null;
		List<Participant> vegetarians = null;
		List<Participant> others = currentEvent.getParticipants();
		for(Restriction restriction:restrictions){
			if(restriction.getName().equals("Vegan")){
				vegans = restriction.getParticipant();
				if(vegans != null){
					for(Participant vegan:vegans){
						others.remove(vegan);
					}
				}
			}
			if(restriction.getName().equals("Vegetarian")){
				vegetarians = restriction.getParticipant();
				if(vegetarians != null){
					for(Participant vegetarian:vegetarians){
						others.remove(vegetarian);
					}
				}
			}
		}
		//TODO Irving-Algorithm on vegans, etc.
		return null;
	}

	/**
	 * Creates a Map for each Participant and the Persons
	 * he has met in WalkingDinners.
	 * @return Returns a Map which relates each Participant (as Person) to all Persons he has met in WalkingDinners
	 */
	public Map<Person, List<Person>> generateKnowingRelations() {
		return null;
	}

	/**
	 * @return Returns the actually set WalkingDinnerController
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	/**
	 * Set the walkingDinnerController-attribute to the given WalkingDinnerController-Entity.
	 * You should use the parametered constructor to set this attribute when constructing the object.
	 * @param walkingDinnerController Set the walkingDinnerController-attribute to the given one
	 */
	public void setWalkingDinnerController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}
	
	private Integer[][] generateIrvingArrayForParticipants(ArrayList<Participant> participants){
		if(participants!=null){
			int n = participants.size();
			if(n%2 == 1){
				n++;
			}
			Integer[][] dynArray = new Integer[n][n];
			for(Participant participant1:participants){
				for(Participant participant2:participants){
					int i = participants.indexOf(participant1);
					int j = participants.indexOf(participant2);
					if(i < n && j < n && i>=0 && j >= 0){
						if(i != j){
							dynArray[i][j] = new Integer(Participant.getRestrictionSymmetricDifferenceForParticipants(participant1, participant2).size());
						}
						else{
							dynArray[i][j] = Integer.MAX_VALUE;
						}
					}
				}
			}
			if(n > participants.size()){
				for(int i = 0; i < n; i++){
					assert(dynArray[i][n] == 0);
					assert(dynArray[n][i] == 0);
					dynArray[i][n] = Integer.MAX_VALUE;
					dynArray[n][i] = Integer.MAX_VALUE;				
				}
			}
			Integer[][] irvingArray = new Integer[n][n];
			for(int i = 0; i < n; i++){
				IndexSorter<Integer> indexSorter = new IndexSorter<Integer>(dynArray[i]);
				indexSorter.sort();
				irvingArray[i] = indexSorter.getIndexes();
			}
			return irvingArray;
		}
		return null;
	}
	
	private class IndexSorter<T extends Comparable<T>> implements Comparator<Integer>{

		

		private final T[] values;

		

		private final Integer[] indexes;

		

		/**

		 * Constructs a new IndexSorter based upon the parameter array.

		 * @param d

		 */

		public IndexSorter(T[] d){

			this.values = d;

			indexes = new Integer[this.values.length];

			for ( int i = 0; i < indexes.length; i++ ){

				indexes[i] = i;

			}

		}

		

		/**

		 * Constructs a new IndexSorter based upon the parameter List.

		 * @param d

		 */

		public IndexSorter(List<T> d){

			this.values = (T[])d.toArray();

			for ( int i = 0; i < values.length; i++ ){

				values[i] = d.get(i);

			}

			indexes = new Integer[this.values.length];

			for ( int i = 0; i < indexes.length; i++ ){

				indexes[i] = i;

			}

		}



		/**

		 * Sorts the underlying index array based upon the values provided in the constructor. The underlying value array is not sorted. 

		 */

		public void sort(){

			Arrays.sort(indexes, this);

		}	

		

		/**

		 * Retrieves the indexes of the array. The returned array is sorted if this object has been sorted.

		 * @return The array of indexes.

		 */

		public Integer[] getIndexes(){

			return indexes;

		}

		

		/**

		 * Compares the two values at index arg0 and arg0

		 * @param arg0 The first index

		 * @param arg1 The second index

		 * @return The result of calling compareTo on T objects at position arg0 and arg1

		 */

		@Override

		public int compare(Integer arg0, Integer arg1) {

			T d1 = values[arg0];

			T d2 = values[arg1];

			return d1.compareTo(d2);

		}



	}


}
