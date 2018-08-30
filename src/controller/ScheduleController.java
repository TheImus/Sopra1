package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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
		//Get all restrictions
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		ArrayList<IrvingMatchable> teams = new ArrayList<IrvingMatchable>();
		teams.addAll(currentEvent.getAllTeams());
		assert(teams.size()%3 == 0);	
		Map<Person, List<Person>> knowingMap = generateKnowingRelations();
		List<Restriction> restrictionsWithoutKnowing = currentEvent.getRestriction();
		List<Restriction> restrictions = generateRestrictionsFromMap(knowingMap);
		restrictions.addAll(restrictionsWithoutKnowing);
		currentEvent.setRestriction(restrictions);
		
		HashMap<IrvingMatchable, IrvingMatchable> groupMap = irvingAlgorithmus(teams);
		ArrayList<Group> groups = generateGroupsFromMap(groupMap, teams);
		/**
		ArrayList<Group> groups = greedyGroups(teams);
		currentEvent.setRestriction(restrictionsWithoutKnowing);
		*/
		System.out.println("There are "+teams.size()+" teams.");
		System.out.println("There are "+groups.size()+" groups.");
		return groups;
	}
	
	private ArrayList<Group> greedyGroups(ArrayList<IrvingMatchable> teams){
		ArrayList<Group> groups = new ArrayList<Group>();
		assert(teams.size()%3==0);
		ArrayList<IrvingMatchable> alreadyScheduled = new ArrayList<IrvingMatchable>();
		ArrayList<IrvingMatchable> toSchedule = new ArrayList<IrvingMatchable>();
		toSchedule.addAll(teams);
		for(IrvingMatchable team:teams){
			if(!alreadyScheduled.contains(team)){
				toSchedule.remove(team);
				alreadyScheduled.add(team);
				IrvingMatchable guest = findMatchingTeamIn(toSchedule,team);
				Group group = new Group();
				group.setHostTeam((Team)team);
				ArrayList<Team> guests = new ArrayList<Team>();
				guests.add((Team)guest);
				toSchedule.remove(guest);
				alreadyScheduled.add(guest);
				guest = findMatchingTeamIn(toSchedule, team);
				guests.add((Team)guest);
				toSchedule.remove(guest);
				alreadyScheduled.add(guest);
				group.setGuest(guests);
				groups.add(group);
			}
		}
		return groups;
	}

	private ArrayList<Restriction> generateRestrictionsFromMap(Map<Person, List<Person>> knowingMap) {
		Event currentEvent = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		List<Participant> participants = currentEvent.getParticipants();
		int restrictionCounter = 0;
		ArrayList<Restriction> restrictions = new ArrayList<Restriction>();
		for(Participant participant:participants){
			Person participantAsPerson = participant.getPerson();
			List<Person> knownPersons = knowingMap.get(participantAsPerson);
			ArrayList<Participant> knownParticipants = new ArrayList<>();
			if(knownPersons != null){
				for(Person person:knownPersons){
					knownParticipants.add(currentEvent.getParticipantForPerson(person));
				}
			}
			Restriction restriction = new Restriction();
			restriction.setName("knowingRestriction"+restrictionCounter);
			restriction.addParticipant(participant);
			for(Participant notKnownParticipant:participants){
				if(!knownParticipants.contains(notKnownParticipant)){
					restriction.addParticipant(notKnownParticipant);
				}
				
			}
			restrictions.add(restriction);
			restrictionCounter++;
		}
		return restrictions;
	}

	private ArrayList<Group> generateGroupsFromMap(HashMap<IrvingMatchable, IrvingMatchable> groupMap,
			ArrayList<IrvingMatchable> teams) {
		ArrayList<Group> groups = new ArrayList<Group>();
		if(groupMap.isEmpty()){
			return greedyGroups(teams);
		}
		ArrayList<IrvingMatchable> alreadyScheduled = new ArrayList<IrvingMatchable>();
		IrvingMatchable toSchedule = null;
		for(IrvingMatchable team:teams){
			if(!alreadyScheduled.contains(team)){
				IrvingMatchable guest = groupMap.get(team);
				Group group = new Group();
				group.setHostTeam((Team)team);
				ArrayList<Team> guests = new ArrayList<Team>();
				guests.add((Team)guest);
				alreadyScheduled.add(guest);
				if(toSchedule != null){
					guest = toSchedule;
					toSchedule = null;
				}
				else{
					guest = findMatchingTeamIn(teams, team);
					toSchedule = groupMap.get(guest);
				}
				guests.add((Team)guest);
				alreadyScheduled.add(guest);
				alreadyScheduled.add(team);
				group.setGuest(guests);
			}
		}
		return groups;
	}

	private IrvingMatchable findMatchingTeamIn(ArrayList<IrvingMatchable> teams, IrvingMatchable team) {
		int indexOfFittingTeam = 0;
		int minimum = Integer.MAX_VALUE;
		//minimum search for team with least SymmetricDifference of Restrictions
		for(IrvingMatchable testTeam:teams){
			int amountOfSymDiff = Restriction.getSymmetricDifferenceForRestrictions(testTeam.getRestrictions(), team.getRestrictions()).size();
			if(amountOfSymDiff < minimum){
				minimum = amountOfSymDiff;
				indexOfFittingTeam = teams.indexOf(testTeam);
			}
		}
		//get the fitting team
		IrvingMatchable fittingTeam = teams.get(indexOfFittingTeam);
		return fittingTeam;
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
		ArrayList<IrvingMatchable> vegans = new ArrayList<IrvingMatchable>();
		ArrayList<IrvingMatchable> vegetarians = new ArrayList<IrvingMatchable>();
		ArrayList<IrvingMatchable> others = new ArrayList<IrvingMatchable>();
		others.addAll(currentEvent.getParticipants());
		for(Restriction restriction:restrictions){
			if(restriction.getName().equals("Vegan")){
				vegans.addAll(restriction.getParticipant());
				if(!vegans.isEmpty()){
					for(IrvingMatchable vegan:vegans){
						others.remove(vegan);
					}
				}
			}
			if(restriction.getName().equals("Vegetarian")){
				vegetarians.addAll(restriction.getParticipant());
				if(!vegetarians.isEmpty()){
					for(IrvingMatchable vegetarian:vegetarians){
						others.remove(vegetarian);
					}
				}
			}
		}
		//Compute Teams with Irving-Algorithm
		HashMap<IrvingMatchable, IrvingMatchable> veganMap = irvingAlgorithmus(vegans);
		ArrayList<Team> veganTeams = generateTeamsFromMap(veganMap, vegans);
		//If |participants| is not even, then there will be one left, which will be returned as a Team with himself as Host and member
		Participant leftVegan = getLeftParticipantFromTeams(veganTeams);
		//add leftVegan to vegetarians
		if(leftVegan != null){
			vegetarians.add(leftVegan);
		}		
		//The same as above with vegetariansgreedyTeams(entities);
		HashMap<IrvingMatchable, IrvingMatchable> vegetarianMap = irvingAlgorithmus(vegetarians);
		ArrayList<Team> vegetarianTeams = generateTeamsFromMap(vegetarianMap, vegetarians);
		Participant leftVegetarian = getLeftParticipantFromTeams(vegetarianTeams);
		if(leftVegetarian != null){
			others.add(leftVegetarian);
		}		
		//and the others
		HashMap<IrvingMatchable, IrvingMatchable> otherMap = irvingAlgorithmus(others);
		ArrayList<Team> otherTeams = generateTeamsFromMap(otherMap, others);
		Participant leftOther = getLeftParticipantFromTeams(otherTeams);
		//add all teams to one list
		ArrayList<Team> teams = new ArrayList<Team>();
		teams.addAll(veganTeams);
		teams.addAll(vegetarianTeams);
		teams.addAll(otherTeams);
		//there may be one left participant, which has to be scheduled, too
		ArrayList<Participant> leftParticipants = new ArrayList<Participant>();
		if(leftOther != null){
			leftParticipants.add(leftOther);
		}
		//Case, that there is one team too much
		if(teams.size()%3==1){
			//Take a team from the "biggest" ordered Restriction available
			//and add the participants to the left ones
			if(otherTeams.size()>=1){
			Team toResize = otherTeams.get(0);
			leftParticipants.addAll(toResize.getParticipants());
			teams.remove(toResize);
			}
			else if(vegetarianTeams.size()>=1){
				Team toResize = vegetarianTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
			}
			else{
				assert(veganTeams.size()>1);
				Team toResize = veganTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
			}
		}
		//Case, that there are two team too much
		if(teams.size()%3==2){
			//Take a team from the "biggest" ordered Restriction available
			//and add the participants to the left ones
			if(otherTeams.size()>=2){
				Team toResize = otherTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
				toResize = otherTeams.get(1);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
				}
				else if(vegetarianTeams.size()>=2){
					Team toResize = vegetarianTeams.get(0);
					leftParticipants.addAll(toResize.getParticipants());
					teams.remove(toResize);
					toResize = vegetarianTeams.get(1);
					leftParticipants.addAll(toResize.getParticipants());
					teams.remove(toResize);
				}
				else{
					assert(veganTeams.size()>2);
					Team toResize = veganTeams.get(0);
					leftParticipants.addAll(toResize.getParticipants());
					teams.remove(toResize);
					toResize = veganTeams.get(1);
					leftParticipants.addAll(toResize.getParticipants());
					teams.remove(toResize);
				}
		}
		//Resize all teams with the left participants
		teams = resizeTeams(teams, leftParticipants);
		currentEvent.setAllTeams(teams);		
		return teams;
	}
	
	private HashMap<IrvingMatchable, IrvingMatchable> greedyTeams(List<IrvingMatchable> participants){
		HashMap<IrvingMatchable, IrvingMatchable> teams = new HashMap<IrvingMatchable, IrvingMatchable>();
		if(participants.isEmpty()){
			return teams;
		}
		ArrayList<IrvingMatchable> alreadyScheduled = new ArrayList<IrvingMatchable>();
		ArrayList<IrvingMatchable> toSchedule = new ArrayList<IrvingMatchable>();
		toSchedule.addAll(participants);
		for(IrvingMatchable participant:participants){
			if(!alreadyScheduled.contains(participant)){
				toSchedule.remove(participant);
				alreadyScheduled.add(participant);
				IrvingMatchable member;
				if(toSchedule.isEmpty()){
					member= participant;
				}
				else{
					member = findMatchingParticipantIn(toSchedule,participant);
					toSchedule.remove(member);
				}
				alreadyScheduled.add(member);
				teams.put(participant, member);
			}
		}
		return teams;
	}

	private IrvingMatchable findMatchingParticipantIn(ArrayList<IrvingMatchable> participants,
			IrvingMatchable participant) {
		int indexOfFittingParticipant = 0;
		int minimum = Integer.MAX_VALUE;
		//minimum search for team with least SymmetricDifference of Restrictions
		for(IrvingMatchable testParticipant:participants){
			int amountOfSymDiff = Restriction.getSymmetricDifferenceForRestrictions(testParticipant.getRestrictions(), participant.getRestrictions()).size();
			if(amountOfSymDiff < minimum){
				minimum = amountOfSymDiff;
				indexOfFittingParticipant = participants.indexOf(testParticipant);
			}
		}
		//get the fitting team
		IrvingMatchable fittingParticipant = participants.get(indexOfFittingParticipant);
		return fittingParticipant;
	}

	/**
	 * Creates a Map for each Participant and the Persons
	 * he has met in WalkingDinners.
	 * @return Returns a Map which relates each Participant (as Person) to all Persons he has met in WalkingDinners
	 */
	public Map<Person, List<Person>> generateKnowingRelations() {
		HashMap<Person, List<Person>> knowingMap = new HashMap<Person, List<Person>>();
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		List<Person> personList = walkingDinner.getPersons();
		List<Event> eventList = walkingDinner.getEvents();
		for(Person person:personList){
			ArrayList<Person> knowingList = new ArrayList<Person>();
			for(Event event:eventList){
				Participant participant = event.getParticipantForPerson(person);
				if(participant != null){
					event.addNewKnowingPersons(knowingList, participant);
				}
			}
			knowingMap.put(person, knowingList);
		}
		return knowingMap;
		
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
	
	/**
	 * Resizes the teams of 2 into teams of 3 with the left participants
	 * @param teams - all teams that have to be resized
	 * @param leftParticipants - participants to resize
	 * @return resized teams
	 */
	private ArrayList<Team> resizeTeams(ArrayList<Team> teams, ArrayList<Participant> leftParticipants){
		if(leftParticipants.isEmpty()){
			return teams;
		}
		for(Participant participant:leftParticipants){
			teams = insertParticipantInFittingTeam(teams, participant);
		}
		assert(teams.size()%3==0);
		return teams;
	}
	
	/**
	 * Searching through all teams of two and adding the participant
	 * to the one, were the Restrictions fits best
	 * @param teams
	 * @param participant
	 * @return teams, where participant is added to the best fitting team of two
	 */
	private ArrayList<Team> insertParticipantInFittingTeam(ArrayList<Team> teams, Participant participant){
		int indexOfFittingTeam = 0;
		int minimum = Integer.MAX_VALUE;
		//minimum search for team with least SymmetricDifference of Restrictions
		for(Team team:teams){
			if(team.getSize()==2){
				int amountOfSymDiff = Restriction.getSymmetricDifferenceForRestrictions(team.getRestrictions(), participant.getRestrictions()).size();
				if(amountOfSymDiff < minimum){
					minimum = amountOfSymDiff;
					indexOfFittingTeam = teams.indexOf(team);
				}
			}
		}
		//get the fitting team and add the participant
		Team fittingTeam = teams.get(indexOfFittingTeam);
		List<Participant> members = fittingTeam.getMembers();
		members.add(participant);
		fittingTeam.setMembers(members);
		teams.set(indexOfFittingTeam, fittingTeam);
		return teams;
	}
	
	/**
	 * The algorithm returns left participants (there are left ones, if |participants| is odd)
	 * as a team, where the participant is the host and the member
	 * @param teams
	 * @return participant
	 */
	private Participant getLeftParticipantFromTeams(ArrayList<Team> teams){
		for(Team team:teams){
			if(team.getMembers().contains(team.getHost())){
				teams.remove(team);
				return team.getHost();
			}
		}
		return null;
	}
	
	/**
	 * Takes a Map of Participants and computes the teams from it.
	 * @param participantMap - relation of team-members
	 * @param participants - all participants related in the Map
	 * @return teams of participants computed from the map
	 */
	private ArrayList<Team> generateTeamsFromMap(HashMap<IrvingMatchable, IrvingMatchable> participantMap, ArrayList<IrvingMatchable> participants){
		if(participantMap.isEmpty()){
			participantMap = greedyTeams(participants);
		}
		ArrayList<Team> teams = new ArrayList<Team>();
		ArrayList<Participant> alreadyScheduled = new ArrayList<Participant>();
		for(IrvingMatchable participant:participants){
			if(!alreadyScheduled.contains(participant)){
				IrvingMatchable partner = participantMap.get(participant);
				Team team = new Team();
				team.setHost((Participant)participant);
				ArrayList<Participant> members = new ArrayList<Participant>();
				members.add((Participant)partner);
				team.setMembers(members);
				teams.add(team);
				alreadyScheduled.add((Participant)partner);
			}
		}
		return teams;
	}
	
	/**
	 * Computes an Irving-Array from a list of participants, where each row is the sorted list of matching participants for a team
	 * @param participants
	 * @return irvingArray
	 */
	private Integer[][] generateIrvingArray(List<IrvingMatchable> entities){
		//Generates an Array for Irving-Algorithm
		if(!entities.isEmpty()){
			int n = entities.size();
			//The Irving-Algorithm needs an even amount of participants
			if(n%2 == 1){
				n++;
			}
			Integer[][] dynArray = new Integer[n][n];
			//Rows and Columns are indexed over participants and the entries are the SymmetricDifferences of Restrictions 
			//for these participants
			for(IrvingMatchable entity1:entities){
				for(IrvingMatchable entity2:entities){
					int i = entities.indexOf(entity1);
					int j = entities.indexOf(entity2);
					if(i < n && j < n && i>=0 && j >= 0){
						if(i != j){
							dynArray[i][j] = new Integer(IrvingMatchable.getRestrictionSymmetricDifference(entity1, entity2).size());
						}
						else{
							dynArray[i][j] = Integer.MAX_VALUE;
						}
					}
				}
			}
			//If we have an odd amount of participants, we have to add one with worst values, just for the algorithm
			if(n > entities.size()){
				for(int i = 0; i < n; i++){
					assert(dynArray[i][n-1] == null);
					assert(dynArray[n-1][i] == null);
					dynArray[i][n-1] = Integer.MAX_VALUE;
					dynArray[n-1][i] = Integer.MAX_VALUE;				
				}
			}
			Integer[][] irvingArray = new Integer[n][n];
			//The IrvingArray is the array above, but each row contains the indexes of participants sorted by their value
			for(int i = 0; i < n; i++){
				IndexSorter<Integer> indexSorter = new IndexSorter<Integer>(dynArray[i]);
				indexSorter.sort();
				irvingArray[i] = indexSorter.getIndexes();
			}
			return irvingArray;
		}
		//If |participants| == 0, we return an empty array
		return new Integer[0][0];
	}
	
	/**
	 * Compiles the irvingArray into a list of irvingLists
	 * (an irvingList is a list with indexes of other participants sorted how good they match for a team
	 * @param irvingArray
	 * @return irvingLists - list of the rows of the irvingArray as lists
	 */
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
	
	/**
	 * Irving-Algorithm for matching Participants into teams
	 * @param participants
	 * @return HashMap - which describes teams
	 */
	private HashMap<IrvingMatchable, IrvingMatchable> irvingAlgorithmus(List<IrvingMatchable> entities){
		Integer[][] irvingArray = generateIrvingArray(entities);
		ArrayList<ArrayList<Integer>> entityLists = irvingArrayToLists(irvingArray);
		//Phase1 of Irving-Algorithm
		for(ArrayList<Integer> entityList:entityLists){
			assert(!entityList.isEmpty());
			Integer proposalToIndex = entityList.get(0);
			ArrayList<Integer> proposalList = entityLists.get((int)proposalToIndex);
			Integer removeFromHere = proposalList.indexOf(entityLists.indexOf(entityList));
			List<Integer> toRemove = proposalList.subList(removeFromHere+1, proposalList.size());
			for(Integer removed:toRemove){
				boolean rightMethod = entityLists.get(removed).remove((Object)proposalToIndex);
				assert(rightMethod);
			}
			proposalList.removeAll(toRemove);
			
		}
		boolean stable = true;
		//The Phase1-Lists are a stable matching, if all list have size 1
		for(ArrayList<Integer> entityList:entityLists){
			assert(!entityList.isEmpty());
			stable = stable && entityList.size() == 1;
		}
		//Otherwise go on with Phase2 of Irving-Algorithm
		while(!stable){
			if(!irvingPhase2(entityLists)){
				return new HashMap<IrvingMatchable, IrvingMatchable>();
			}
			for(ArrayList<Integer> entityList:entityLists){
				assert(!entityList.isEmpty());
				stable = stable && entityList.size() == 1;	
			}
		}	
		//create a Map from participantLists
		HashMap<IrvingMatchable, IrvingMatchable> teamMemberIndexMap = new HashMap<IrvingMatchable, IrvingMatchable>();
		for(ArrayList<Integer> entityList:entityLists){
			Integer entity1 = entityLists.indexOf(entityList);
			assert(entityList.size() == 1);
			Integer entity2 = entityList.get(0);
			if(entities.size() <= entity2){
				teamMemberIndexMap.put(entities.get((int)entity1), entities.get((int)entity1));
			}
			else if(entities.size() <= entity1){
				teamMemberIndexMap.put(entities.get((int)entity2), entities.get((int)entity2));
			}
			else{
				teamMemberIndexMap.put(entities.get((int)entity1), entities.get((int)entity2));
			}
		}
		return teamMemberIndexMap;
	}
	
	/**
	 * Phase2 of Irving-Algorithm, where non matching pairs are rotated until they match
	 * @param irvingLists
	 * @return
	 */
	private boolean irvingPhase2(ArrayList<ArrayList<Integer>> irvingLists){
		/**
		ArrayList<ArrayList<Integer>> phase2Lists = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> irvingList:irvingLists){
			if(irvingList.size()>1){
				phase2Lists.add(irvingList);
			}
		}
		assert(!phase2Lists.isEmpty());
		*/
		HashMap<Integer, Integer> rotationPairs = new HashMap<Integer, Integer>();
		Integer startKey = null;
		ArrayList<Integer> list = irvingLists.get(0);
		startKey = irvingLists.indexOf(list); //phase2Lists.indexOf(list);
		Integer key = null;
		int listCounter = 0;
		while(key != startKey && listCounter < irvingLists.size()){
			if(list.size()>1){
				Integer value = list.get(1);
				ArrayList<Integer> valueList = irvingLists.get((int)value);
				assert(valueList.size()>1);
				key = valueList.get(valueList.size()-1);
				list = irvingLists.get(key);
				rotationPairs.put(key, value);
			}
			listCounter++;
			/**
			Integer value = list.get(1);
			ArrayList<Integer> valueList = phase2Lists.get((int)value);
			assert(valueList.size()>1);
			key = valueList.get(valueList.size()-1);
			list = phase2Lists.get(key);
			rotationPairs.put(key, value);
			*/
		}
		return removeRotationsFromLists(irvingLists, rotationPairs);
	}
	
	private boolean removeRotationsFromLists(ArrayList<ArrayList<Integer>> irvingLists,
			HashMap<Integer, Integer> rotationPairs) {
		boolean returnValue = false;
		if(rotationPairs.isEmpty()){
			return returnValue;
		}
		for(ArrayList<Integer> list:irvingLists){
			Integer key = irvingLists.indexOf(list);
			if(rotationPairs.containsKey(key)){
				assert(list.size()>1);
				Integer value = rotationPairs.get(key);
				list.remove(value);
				ArrayList<Integer> valueList = irvingLists.get(value);
				valueList.remove(key);
				returnValue = true;
			}
		}
		return returnValue;
	}

	/**
	 * Sort indexes of an array by their value
	 * @author sopr026
	 *
	 * @param <T>
	 */
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
