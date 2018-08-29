package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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
		//Get all restrictions
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		List<Restriction> restrictions = currentEvent.getRestriction();
		//Order Participants to vegan, vegetarian and others
		ArrayList<Participant> vegans = new ArrayList<Participant>();
		ArrayList<Participant> vegetarians = new ArrayList<Participant>();
		ArrayList<Participant> others = new ArrayList<Participant>();
		others.addAll(currentEvent.getParticipants());
		for(Restriction restriction:restrictions){
			if(restriction.getName().equals("Vegan")){
				vegans.addAll(restriction.getParticipant());
				if(!vegans.isEmpty()){
					for(Participant vegan:vegans){
						others.remove(vegan);
					}
				}
			}
			if(restriction.getName().equals("Vegetarian")){
				vegetarians.addAll(restriction.getParticipant());
				if(!vegetarians.isEmpty()){
					for(Participant vegetarian:vegetarians){
						others.remove(vegetarian);
					}
				}
			}
		}
		//Compute Teams with Irving-Algorithm
		HashMap<Participant, Participant> veganMap = irvingAlgorithmusForParticipants(vegans);
		ArrayList<Team> veganTeams = generateTeamsFromMap(veganMap, vegans);
		//If |participants| is not even, then there will be one left, which will be returned as a Team with himself as Host and member
		Participant leftVegan = getLeftParticipantFromTeams(veganTeams);
		//add leftVegan to vegetarians
		if(leftVegan != null){
			vegetarians.add(leftVegan);
		}		
		//The same as above with vegetarians
		HashMap<Participant, Participant> vegetarianMap = irvingAlgorithmusForParticipants(vegetarians);
		ArrayList<Team> vegetarianTeams = generateTeamsFromMap(vegetarianMap, vegetarians);
		Participant leftVegetarian = getLeftParticipantFromTeams(vegetarianTeams);
		if(leftVegetarian != null){
			others.add(leftVegetarian);
		}		
		//and the others
		HashMap<Participant, Participant> otherMap = irvingAlgorithmusForParticipants(others);
		ArrayList<Team> otherTeams = generateTeamsFromMap(otherMap, others);
		Participant leftOther = getLeftParticipantFromTeams(otherTeams);
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.addAll(veganTeams);
		teams.addAll(vegetarianTeams);
		teams.addAll(otherTeams);
		ArrayList<Participant> leftParticipants = new ArrayList<Participant>();
		if(leftOther != null){
			leftParticipants.add(leftOther);
		}
		if(teams.size()%3==1){
			if(otherTeams.size()>=1){
			Team toResize = otherTeams.get(0);
			leftParticipants.addAll(toResize.getParticipants());
			}
			else if(vegetarianTeams.size()>=1){
				Team toResize = vegetarianTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
			}
			else{
				assert(veganTeams.size()>1);
				Team toResize = veganTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
			}
		}
		if(teams.size()%3==2){
			if(otherTeams.size()>=2){
				Team toResize = otherTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
				toResize = otherTeams.get(1);
				leftParticipants.addAll(toResize.getParticipants());
				}
				else if(vegetarianTeams.size()>=2){
					Team toResize = vegetarianTeams.get(0);
					leftParticipants.addAll(toResize.getParticipants());
					toResize = vegetarianTeams.get(1);
					leftParticipants.addAll(toResize.getParticipants());
				}
				else{
					assert(veganTeams.size()>2);
					Team toResize = veganTeams.get(0);
					leftParticipants.addAll(toResize.getParticipants());
					toResize = veganTeams.get(1);
					leftParticipants.addAll(toResize.getParticipants());
				}
		}
		teams = resizeTeams(teams, leftParticipants);
		return teams;
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
	
	private ArrayList<Team> resizeTeams(ArrayList<Team> teams, ArrayList<Participant> leftParticipants){
		if(leftParticipants.isEmpty()){
			return teams;
		}
		for(Participant participant:leftParticipants){
			teams = insertParticipantInFittingTeam(teams, participant);
		}
		return teams;
		
	}
	
	private ArrayList<Team> insertParticipantInFittingTeam(ArrayList<Team> teams, Participant participant){
		int indexOfFittingTeam = 0;
		int minimum = Integer.MAX_VALUE;
		for(Team team:teams){
			if(team.getSize()==2){
				int amountOfSymDiff = Restriction.getSymmetricDifferenceForRestrictions(team.getRestrictions(), participant.getRestriction()).size();
				if(amountOfSymDiff < minimum){
					minimum = amountOfSymDiff;
					indexOfFittingTeam = teams.indexOf(team);
				}
			}
		}
		Team fittingTeam = teams.get(indexOfFittingTeam);
		List<Participant> members = fittingTeam.getMembers();
		members.add(participant);
		fittingTeam.setMembers(members);
		teams.set(indexOfFittingTeam, fittingTeam);
		return teams;
	}
	private Participant getLeftParticipantFromTeams(ArrayList<Team> teams){
		for(Team team:teams){
			if(team.getMembers().contains(team.getHost())){
				return team.getHost();
			}
		}
		return null;
	}
	
	private ArrayList<Team> generateTeamsFromMap(HashMap<Participant, Participant> participantMap, ArrayList<Participant> participants){
		ArrayList<Team> teams = new ArrayList<Team>();
		for(Participant participant:participants){
			Participant partner = participantMap.get(participant);
			Team team = new Team();
			team.setHost(participant);
			ArrayList<Participant> members = new ArrayList<Participant>();
			members.add(partner);
			team.setMembers(members);
			teams.add(team);
		}
		return teams;
	}
	
	private Integer[][] generateIrvingArrayForParticipants(ArrayList<Participant> participants){
		//Generates an Array for Irving-Algorithm
		if(!participants.isEmpty()){
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
							//assert(i <= j);
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
					assert(dynArray[i][n-1] == null);
					assert(dynArray[n-1][i] == null);
					dynArray[i][n-1] = Integer.MAX_VALUE;
					dynArray[n-1][i] = Integer.MAX_VALUE;				
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
		return new Integer[0][0];
	}
	
	private ArrayList<ArrayList<Integer>> irvingArrayToLists(Integer[][] irvingArray){
		ArrayList<ArrayList<Integer>> irvingLists = new ArrayList<ArrayList<Integer>>();
		for(Integer i = 0; i < irvingArray.length; i++){
			List<Integer> list = Arrays.asList(irvingArray[i]);
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			arrayList.addAll(list);
			boolean rightMethod = arrayList.remove((Object)i);
			assert(rightMethod);
			irvingLists.add(arrayList);
		}
		return irvingLists;
	}
	
	private HashMap<Participant, Participant> irvingAlgorithmusForParticipants(ArrayList<Participant> participants){
		Integer[][] irvingArray = generateIrvingArrayForParticipants(participants);
		ArrayList<ArrayList<Integer>> participantLists = irvingArrayToLists(irvingArray);
		
		for(ArrayList<Integer> participantList:participantLists){
			assert(!participantList.isEmpty());
			Integer proposalToIndex = participantList.get(0);
			ArrayList<Integer> proposalList = participantLists.get((int)proposalToIndex);
			Integer removeFromHere = proposalList.indexOf(participantLists.indexOf(participantList));
			List<Integer> toRemove = proposalList.subList(removeFromHere+1, proposalList.size());
			for(Integer removed:toRemove){
				boolean rightMethod = participantLists.get(removed).remove((Object)proposalToIndex);
				assert(rightMethod);
			}
			proposalList.removeAll(toRemove);
			
		}
		boolean stable = true;
		for(ArrayList<Integer> participantList:participantLists){
			stable = participantList.size() == 1;
		}
		if(!stable){
			participantLists = irvingPhase2(participantLists);
			for(ArrayList<Integer> participantList:participantLists){
				stable = participantList.size() == 1;
			}
		}				
		HashMap<Participant, Participant> teamMemberIndexMap = new HashMap<Participant, Participant>();
		for(ArrayList<Integer> participantList:participantLists){
			Integer participant1 = participantLists.indexOf(participantList);
			assert(participantList.size() == 1);
			Integer participant2 = participantList.get(0);
			if(participants.size() < participant2){
				teamMemberIndexMap.put(participants.get((int)participant1), participants.get((int)participant1));
			}
			if(participants.size() < participant1){
				teamMemberIndexMap.put(participants.get((int)participant2), participants.get((int)participant2));
			}
			teamMemberIndexMap.put(participants.get((int)participant1), participants.get((int)participant2));
		}
		return teamMemberIndexMap;
	}
	
	private ArrayList<ArrayList<Integer>> irvingPhase2(ArrayList<ArrayList<Integer>> irvingLists){
		//TODO
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
