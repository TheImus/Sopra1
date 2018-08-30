package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Course;
import model.Event;
import model.Group;
import model.IrvingMatchable;
import model.Participant;
import model.Person;
import model.Restriction;
import model.Schedule;
import model.Team;
import model.WalkingDinner;

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
		HashMap<IrvingMatchable, IrvingMatchable> groupMap = IrvingAlgorithm.irvingAlgorithmus(teams);
		ArrayList<Group> groups = generateGroupsFromMap(groupMap, teams);
		if(groups != null){
			System.out.println(groups.toString());
		}
		currentEvent.setRestriction(restrictionsWithoutKnowing);
		Schedule schedule = currentEvent.getSchedule();
		Course currentCourse = schedule.getCurrentCourse();
		schedule.setGroup(currentCourse, groups);
		return groups;
	}
	/**
	 * Returns a GroupScheduling with a greedy algorithm
	 * @param teams
	 * @return groupSchedule
	 */
	private ArrayList<Group> greedyGroups(ArrayList<IrvingMatchable> teams){
		ArrayList<Group> groups = new ArrayList<Group>();
		assert(teams.size()%3==0);
		ArrayList<IrvingMatchable> alreadyScheduled = new ArrayList<IrvingMatchable>();
		ArrayList<IrvingMatchable> toSchedule = new ArrayList<IrvingMatchable>();
		toSchedule.addAll(teams);
		ArrayList<IrvingMatchable> shuffledTeams = new ArrayList<IrvingMatchable>();
		shuffledTeams.addAll(teams);
		Random random = new Random();
		Collections.rotate(shuffledTeams, random.nextInt(shuffledTeams.size()));
		for(IrvingMatchable team:shuffledTeams){
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
		System.out.println("greedy "+groups.toString());
		return groups;
	}
	/**
	 * Generates KnowingRelations from the knowingMap
	 * @param knowingMap
	 * @return A list of knowingRestrictions generated from the knowingMap
	 */
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
	/**
	 * Schedules the teams into groups referring to groupMap
	 * @param groupMap
	 * @param teams
	 * @return Schedule of Groups
	 */
	private ArrayList<Group> generateGroupsFromMap(HashMap<IrvingMatchable, IrvingMatchable> groupMap,
			ArrayList<IrvingMatchable> teams) {
		//ArrayList<Group> groups = new ArrayList<Group>();
		//if(groupMap.isEmpty()){
			return greedyGroups(teams);
		/*}
		System.out.println("nicht greedy");
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
				groups.add(group);
			}
		}
		return groups;*/

	}
	/**
	 * returns best-matching team
	 * @param teams
	 * @param team
	 * @return best-matching-team for team-parameter
	 */
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
		HashMap<IrvingMatchable, IrvingMatchable> veganMap = IrvingAlgorithm.irvingAlgorithmus(vegans);
		ArrayList<Team> veganTeams = generateTeamsFromMap(veganMap, vegans);
		//If |participants| is not even, then there will be one left, which will be returned as a Team with himself as Host and member
		Participant leftVegan = getLeftParticipantFromTeams(veganTeams);
		//add leftVegan to vegetarians
		if(leftVegan != null){
			vegetarians.add(leftVegan);
		}		
		//The same as above with vegetariansgreedyTeams(entities);
		HashMap<IrvingMatchable, IrvingMatchable> vegetarianMap = IrvingAlgorithm.irvingAlgorithmus(vegetarians);
		ArrayList<Team> vegetarianTeams = generateTeamsFromMap(vegetarianMap, vegetarians);
		Participant leftVegetarian = getLeftParticipantFromTeams(vegetarianTeams);
		if(leftVegetarian != null){
			others.add(leftVegetarian);
		}		
		//and the others
		HashMap<IrvingMatchable, IrvingMatchable> otherMap = IrvingAlgorithm.irvingAlgorithmus(others);
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
		int three = 3;
		int one = 1;
		int two = 2;
		if(teams.size()%three==one){
			//Take a team from the "biggest" ordered Restriction available
			//and add the participants to the left ones
			if(otherTeams.size()>=one){
			Team toResize = otherTeams.get(0);
			leftParticipants.addAll(toResize.getParticipants());
			teams.remove(toResize);
			}else if(vegetarianTeams.size()>=one){
				Team toResize = vegetarianTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
			}else{
				assert(veganTeams.size()>one);
				Team toResize = veganTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
			}
		}
		//Case, that there are two team too much
		if(teams.size()%three==two){
			//Take a team from the "biggest" ordered Restriction available
			//and add the participants to the left ones
			if(otherTeams.size()>=two){
				Team toResize = otherTeams.get(0);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
				toResize = otherTeams.get(1);
				leftParticipants.addAll(toResize.getParticipants());
				teams.remove(toResize);
				}
				else if(vegetarianTeams.size()>=two){
					Team toResize = vegetarianTeams.get(0);
					leftParticipants.addAll(toResize.getParticipants());
					teams.remove(toResize);
					toResize = vegetarianTeams.get(1);
					leftParticipants.addAll(toResize.getParticipants());
					teams.remove(toResize);
				}
				else{
					assert(veganTeams.size()>two);
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
	/**
	 * Alternative greedy-algorithm for Team-Scheduling
	 * @param participants
	 * @return ParticipantMap of Teams
	 */
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
				}else{
					member = findMatchingParticipantIn(toSchedule,participant);
					toSchedule.remove(member);
				}
				alreadyScheduled.add(member);
				teams.put(participant, member);
			}
		}
		return teams;
	}
	/**
	 * Returns best-matching participant
	 * @param participants
	 * @param participant
	 * @return best-matching-participant for participant-parameter
	 */
	private IrvingMatchable findMatchingParticipantIn(ArrayList<IrvingMatchable> participants, IrvingMatchable participant) {
		int indexOfFittingParticipant = 0;
		int minimum = Integer.MAX_VALUE;
		//minimum search for team with least SymmetricDifference of Restrictions
		for(IrvingMatchable testParticipant:participants){
			int amountOfSymDiff = Restriction.getSymmetricDifferenceForRestrictions(testParticipant.getRestrictions(), participant.getRestrictions()).size();
			if(amountOfSymDiff < minimum){
				minimum = amountOfSymDiff;
				indexOfFittingParticipant = participants.indexOf(testParticipant);
			}
		}//get the fitting team
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
		int zero = 0;
		int three = 3;
		assert(teams.size()%three==zero);
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
			int two = 2;
			if(team.getSize()==two){
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
}
