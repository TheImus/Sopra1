package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Course;
import model.Event;
import model.Group;
import model.Participant;
import model.Person;
import model.Restriction;
import model.Team;
import model.WalkingDinner;


public class ConsistencyController {
	
	private final int zero = 0;
	private final int one = 1;
	private final int two = 2;
	private final int three = 3;

	private WalkingDinnerController walkingDinnerController;
	
	/**
	 * Create a new consistency controller with a reference to the
	 * central walking dinner controller 
	 * 
	 * @param walkingDinnerController the central walking dinner controller
	 */
	public ConsistencyController(WalkingDinnerController walkingDinnerController) {
		this.walkingDinnerController = walkingDinnerController;
	}

	/**
	 * the method gets the walkingDinnerController
	 * @return walkingDinnerController object
	 */
	public WalkingDinnerController getWalkingDinnerController() {
		return walkingDinnerController;
	}

	public List<String> getWarnings(){
		List<String> warnings = new ArrayList<String>();
		int size = walkingDinnerController.getGroupController().getAllGroups().size();
		
		if(size == 0) warnings.add("Keine Gruppen im Event");
		if(size %3 != 0)	warnings.add("Die Anzahl der Gruppen ist kein Vielfaches von 3");
		
		return warnings;
	}
	
	/**
	 * The method creates warning messages for each team
	 * @param team name of a team object
	 * @return returns a list of all warnings for the team
	 */
	public List<String> getWarnings(Team team) {
		
	    List<String> warnings = new ArrayList<String>();
	    List<Participant> members = team.getParticipants();												// get all members of the team and save them in the list, they are participants currently
		
	    warnings.addAll(teamSizeWarnings(team));														//add all warnings with size issues
		warnings.addAll(multipleMemberAndKnowingWarnings(team));											//adds warning if same if there is a knowing relation or if same participant multiple times in team
		
		for(int i = 0; i < members.size()-1; i++){
			if(!members.get(i).getCourseWish().equals(members.get(i+1).getCourseWish())){				//check if team members have same course wish
				warnings.add(members.get(i).getPerson().getName() + "hat anderen Wunschgang als " + members.get(i+1).getPerson().getName());
			}
		}
		if(team.getHost() == null) warnings.add("kein Host vorhanden/gesetzt");							//check if there is a host
		List<Restriction> differentR = getDifferentRestrictionsFor(team.getParticipants());
		if(differentR.size() >0 ) {																		// check if there are any restrictions that don't match
			String newWarning = "";
			newWarning+= "folgende Restriktionen könnten Problematisch sein:" + 
					differentR.toString() + 
					"bitte einmal überprüfen für Team mit folgenden Mitgliedern: \n" ;
			for(Participant p : team.getParticipants())
			{
				newWarning += p.getPerson().getName() + "\n";
			}
			warnings.add(newWarning);
		}	
		return warnings;
	}

	/**
	 * the method creates 2 warnings: if there are any members in a team that know each other or if
	 * the same member is multiple times in one team
	 * @param team for which the warnings are created
	 */
	private List<String> multipleMemberAndKnowingWarnings(Team team) {
		
		List<String> warnings = new ArrayList<String>();
		ScheduleController schedule = walkingDinnerController.getScheduleController();
		Event event = walkingDinnerController.getWalkingDinner().getCurrentEvent();
		Map<Person,List<Person>> knownPersons = schedule.generateKnowingRelations();
		List<Team> allTeams = event.getAllTeams();
		List<Participant> members = team.getParticipants();
		List<Person> membersAsPerson = new ArrayList<Person>();	
		
		for(int i = 0; i < team.getParticipants().size(); i++){												// make all members to Persons and save them in a list
			membersAsPerson.add(members.get(i).getPerson());
		}
		for(int i = 0; i<membersAsPerson.size()-1;i++) {																				
			for(int j = i+1; j<membersAsPerson.size();j++) {														
				if(membersAsPerson.get(i).equals(membersAsPerson.get(j))){								//check if a person is multiple times in the same team
					warnings.add(membersAsPerson.get(i).getName() + "kommt mehrmals im Team vor");	
				}
			}		
		}
		for(int i = 0; i< allTeams.size(); i++){														// check if a member is in multiple (other) teams
			if(!allTeams.get(i).equals(team)){
				for(int j = 0; j < members.size(); j++){
					if(allTeams.get(i).getParticipants().contains(members.get(j))){
						String newWarning = "";
						newWarning+= members.get(j).getPerson().getName() + "kommt in mehreren Teams vor, das andere Team besteht aus: \n" ;
						for(Participant p : allTeams.get(i).getParticipants()){
							newWarning += p.getPerson().getName() + "\n";
						}
						warnings.add(newWarning);
					}	
				}
			}
		}
		try{
			if(!knownPersons.isEmpty()) warnings.addAll(knowingRelation(membersAsPerson));				//check knowing Relation
			}catch(NullPointerException nullPointer){}
		
		return warnings;
	}
	
	/**
	 * The method checks for all teams if you have a team with a warning message
	 * @return Returns a list of all teams that have a list of warnings
	 */
	public List<Team> getInconsistentTeams() {
		
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();					
		Event event = walkingDinner.getCurrentEvent();
		List<Team> allTeamsWithWarnings = new ArrayList<Team>();
		
		for(Team team : event.getAllTeams()) {											//check for warnings for each team in event 
																						
			if(!getWarnings(team).isEmpty()) {											//if there are warnings add the team to the list
				allTeamsWithWarnings.add(team);
			}
		}
		return allTeamsWithWarnings;
	}

	/**
	 * The method creates warning messages for each group, possible warnings:
	 * 1. "Gruppe zu klein"
	 * 2. "Gruppe zu groß"
	 * 3. "kein Hostteam festgelegt"
	 * 4. "keine Gastteams vorhanden"
	 * 5. "Die Anzahl der Gastteams stimmt nicht"
	 * 6. person.get(i) + " und" + person.get(j) + " kennen sich"
	 * 7. "folgende Restriktionen könnten Problematisch sein:" + Restriction +  "bitte einmal überprüfen für folgende Gruppe:" + Gruppe
	 * 8. TeamX "kommt in mehreren STARTER Gruppen vor, die andere Gruppe besteht aus: " TeamsXYZ
	 * 9. TeamX "kommt in mehreren MAIN Gruppen vor, die andere Gruppe besteht aus: "
	 * 10. TeamX "kommt in mehreren DESSERT Gruppen vor, die andere Gruppe besteht aus: " TeamsXYZ
	 *
	 * @param group name of a group object
	 * @return Returns a list with all warnings for the group
	 */
	public List<String> getWarnings(Group group) {
		
		List<String> warnings = new ArrayList<String>();								//return List with all warnings
		List<Participant> allParticipantsInGroup = new ArrayList<Participant>();		//List with all participants in the group
		List<Person> allPersonsInGroup = new ArrayList<Person>();						//List with all Persons in the group
		GroupController groupController = walkingDinnerController.getGroupController();
		groupController.setCourse(Course.STARTER);
		
		for(int i = 0; i<group.getTeams().size(); i++) {								//saves all participants in the group
			allParticipantsInGroup.addAll(group.getTeams().get(i).getParticipants());
		}
		
		for(int i = 0; i<allParticipantsInGroup.size(); i++) {							//saves all participants in the group as persons
			allPersonsInGroup.add(allParticipantsInGroup.get(i).getPerson());
		}
		
		warnings.addAll(groupSizeWarnings(group));
		
		try{
		if(!knowingRelation(allPersonsInGroup).isEmpty()){
			warnings.addAll(knowingRelation(allPersonsInGroup)); 							//checks if any persons in the group know each other
		}
		}catch(NullPointerException nullPointer){}
		
		 warnings.addAll(checkGroupCourses(group));								//checks if any team in the group has another group with the same course (Starter) already
		 groupController.setCourse(Course.MAIN);
		 warnings.addAll(checkGroupCourses(group));									//checks if any team in the group has another group with the same course (Main) already
		 groupController.setCourse(Course.DESSERT);
		 warnings.addAll(checkGroupCourses(group));	
		    
	    List<Restriction> differentR = getDifferentRestrictionsFor(allParticipantsInGroup);
	    if(differentR != null) {														// check if there are any restrictions that don't match
			warnings.add("folgende Restriktionen könnten Problematisch sein:" + 
					differentR.toString() + 
					"bitte einmal überprüfen für folgende Gruppe:" + allParticipantsInGroup.toString());
		}	
		
		return warnings;
	}

	/**
	 * The method checks for all groups if you have a group with a warning message
	 * @return Returns a list of all groups that have a list of warnings
	 */
	public List<Group> getInconsistentGroups() {
		
		GroupController groupController = walkingDinnerController.getGroupController();					
		List<Group> allGroupsWithWarnings = new ArrayList<Group>();
		List<Group> allGroups = groupController.getGroups();
		
		for(Group group : allGroups) {																			//check for warnings for each group in event 																				
			if(!getWarnings(group).isEmpty()) allGroupsWithWarnings.add(group); 								//if there are warnings add the group to the list
		}
		return allGroupsWithWarnings;
	}
	

	/**
	 * the method compares all of the restrictions of all participants given as parameter and checks if
	 * there are any differences between the participants
	 * @param participants name of list of participants
	 * @return Returns a list of all restrictions that don't appear more than once
	 */	
	public List<Restriction> getDifferentRestrictionsFor(List<Participant> participants) {
	
		List<Restriction> notMatchingRestrictions = new ArrayList<Restriction>();				//create list to save all restrictions that don't match
	   
		for(Participant part : participants) {													//iterate through the list for every participant
			List<Restriction> rest = part.getRestrictions();										//get all restrictions for the actual participant
			
			for(Participant others : participants) {											//iterate again for comparison
				List<Restriction> restOfOthers = others.getRestrictions();						//get all restrictions for the other participant
				
				if(!part.equals(others)) {														//make sure the participants are not the same
					for(Restriction compareRestriction : restOfOthers) {						//check if any restrictions of the participant and other participant match
						if(!rest.contains(compareRestriction) && !notMatchingRestrictions.contains(compareRestriction)) {								//if they don't match add them to the list
							notMatchingRestrictions.add(compareRestriction);
						}
					}
				}
			}
		}
		return notMatchingRestrictions;															//return all restrictions that don't match
	}
	
	/**
	 * Help method to get the warnings for persons that know each other
	 * @param person
	 * @return warnings list with all persons that know each other
	 */
	private List<String> knowingRelation(List<Person> person)
	{
		Map<Person,List<Person>> knownPersons = generateKnowingRelationsWithoutCurrentEvent();			//get map with all knowing relations for each person
		List<Person> knowingList = new ArrayList<Person>();
		List<String> warnings = new ArrayList<String>();						
		
		for(int i = 0; i<person.size()-1;i++) {		
			
			knowingList = knownPersons.get(person.get(i));							//get the knowing relations for a person
			for(int j = i+1; j<person.size();j++) {														
				if(knowingList.contains(person.get(j))){										//check if another person is in the knowing relations list
					warnings.add(person.get(i).getName() + " und" + person.get(j).getName() + " kennen sich");	    //save warning if so
				}
			}		
		}
		return warnings;
	}
	
	/**
	 * help method to get the warnings of a group in a course
	 * @param group
	 * @return warnings list of warnings
	 */
	public List<String> checkGroupCourses(Group group)
	{
		List<String> warnings = new ArrayList<String>();
		GroupController groupController = walkingDinnerController.getGroupController();
		
		for(int i = 0; i< groupController.getGroups().size(); i++){
			if(!groupController.getGroups().get(i).equals(group)){
				for(int j = 0; j < group.getTeams().size();j++){
					if(groupController.getGroups().get(i).getTeams().contains(group.getTeams().get(j))){
						warnings.add(group.getTeams().get(j)+"kommt in mehreren "+groupController.getCourse()+" Gruppen vor, die andere Gruppe besteht aus Gruppe mit Gastgeber: "+groupController.getGroups().get(i).getHostTeam().getHost());	
					} 
				}
			}
			
		}			
		return warnings;
	}
	
	/**
	 * help method
	 * @param group
	 * @return
	 */
	private List<String> groupSizeWarnings(Group group)
	{
		int groupSize = group.getTeams().size();
		List<String> warnings = new ArrayList<String>();
		
		if(groupSize < three){												//checks if group size is below 3
			warnings.add("Gruppe zu klein");
		}
		
		if(groupSize > three){												//checks if group size is more than 3
			warnings.add("Gruppe zu groß");
		}
		
		if(group.getHostTeam() == null) {											//checks if there is a host team
			warnings.add("kein Hostteam festgelegt");
		}
			
		if(group.getGuest().isEmpty()) {												//checks if there are any guest teams in group
			warnings.add("keine Gastteams vorhanden");
		}
		
		if(group.getGuest().size() != two)												//checks if there are the correct amount of guest teams
		{
			warnings.add("Die Anzahl der Gastteams stimmt nicht");
		}
		return warnings;
	}
	
	/**
	 * help method
	 * @param team
	 * @return
	 */
	private List<String> teamSizeWarnings(Team team)
	{
		List<String> warnings = new ArrayList<String>();
		
		if(team.getParticipants().size() == zero) {												// check teamsize and save warning in list if the size is not correct
			warnings.add("Das Team ist leer und kann gelöscht werden");
		}
		
		if(team.getParticipants().size() == one) {												
			warnings.add("In dem Team befindet sich nur eine Person");
		}
		
		if(team.getParticipants().size() > three) {
			warnings.add("Teamgröße ist größer als 3");
		}
		return warnings;
	}
	
	public Map<Person, List<Person>> generateKnowingRelationsWithoutCurrentEvent() {
		HashMap<Person, List<Person>> knowingMap = new HashMap<Person, List<Person>>();
		WalkingDinner walkingDinner = walkingDinnerController.getWalkingDinner();
		List<Person> personList = walkingDinner.getPersons();
		List<Event> eventList = walkingDinner.getEvents();
		for(Person person:personList){
			ArrayList<Person> knowingList = new ArrayList<Person>();
			for(Event event:eventList){
				if(!event.equals(walkingDinnerController.getWalkingDinner().getCurrentEvent())){
					Participant participant = event.getParticipantForPerson(person);
					if(participant != null){
						event.addNewKnowingPersons(knowingList, participant);
					}
				}
			}
			knowingMap.put(person, knowingList);
		}
		return knowingMap;
		
	}

}
