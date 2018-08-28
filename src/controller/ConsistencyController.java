package controller;

import java.util.ArrayList;
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

	
	/**
	 * The method creates warning messages for each team
	 * @param team name of a team object
	 * @return returns a list of all warnings for the team
	 */
	public List<String> getWarnings(Team team) {
		
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();					
		Event event = wd.getCurrentEvent();
		List<Team> allTeams = event.getAllTeams();
	    List<String> warnings = new ArrayList<String>();
	    List<Participant> members = team.getMembers();									// get all members of the team and save them in the list, they are participants currently
		List<Person> membersAsPerson = new ArrayList<Person>();
		
	    warnings.addAll(TeamSizeWarnings(team));										//add all warnings with size issues
		
		for(int i = 0; i < team.getMembers().size(); i++){								// make all members to Persons and save them in a list
			membersAsPerson.add(members.get(i).getPerson());
		}
		
		for(int i = 0; i<membersAsPerson.size()-1;i++) {																				
			for(int j = 1; j<membersAsPerson.size();j++) {														
				if(membersAsPerson.get(i).equals(membersAsPerson.get(j))){									//check if a person is multiple times in the same team
					warnings.add(membersAsPerson.get(i) + "kommt mehrmals im Team vor");	
				}
			}		
		}
		
		for(int i = 0; i < members.size()-1; i++){
			if(!members.get(i).getCourseWish().equals(members.get(i+1).getCourseWish())){					//check if team members have same course wish
				warnings.add(members.get(i) + "hat anderen Wunschgang als " + members.get(i+1));
			}
		}
		
		warnings.addAll(knowingRelation(membersAsPerson));													//check knowing Relation
		
		if(getDifferentRestrictionsFor(team.getMembers()) != null) {			// check if there are any restrictions that don't match
			System.out.println(getDifferentRestrictionsFor(team.getMembers()).toString());
			warnings.add("folgende Restriktionen könnten Problematisch sein:" + 
					getDifferentRestrictionsFor(team.getMembers()).toString() + 
					"bitte einmal überprüfen für folgendes Team:" + team.getMembers().toString());
		}	
		
		for(int i = 0; i< allTeams.size(); i++){															// check if a member is in multiple (other) teams
			if(!allTeams.get(i).equals(team)){
				for(int j = 0; j < members.size(); j++){
					if(allTeams.get(i).getMembers().contains(members.get(j))){
						warnings.add(members.get(j) + "kommt in mehreren Teams vor, das andere Team besteht aus: " + allTeams.get(i).getMembers());	
					}	
				}	
			}
		}
		return warnings;
	}
	
	/**
	 * The method checks for all teams if you have a team with a warning message
	 * @return Returns a list of all teams that have a list of warnings
	 */
	public List<Team> getInconsistentTeams() {
		
		WalkingDinner wd = walkingDinnerController.getWalkingDinner();					
		Event event = wd.getCurrentEvent();
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
	 * 1. restrictions not compatible 2. course not compatible etc..
	 * @param group name of a group object
	 * @return Returns a list with all warnings for the group
	 */
	public List<String> getWarnings(Group group) {
		
		List<String> warnings = new ArrayList<String>();								//return List with all warnings
		List<Participant> allParticipantsInGroup = new ArrayList<Participant>();		//List with all participants in the group
		List<Person> allPersonsInGroup = new ArrayList<Person>();						//List with all Persons in the group
		
		
		for(int i = 0; i<group.getTeams().size(); i++) {								//saves all participants in the group
			allParticipantsInGroup.addAll(group.getTeams().get(i).getMembers());
		}
		
		for(int i = 0; i<allParticipantsInGroup.size(); i++) {							//saves all participants in the group as persons
			allPersonsInGroup.add(allParticipantsInGroup.get(i).getPerson());
		}
		warnings.addAll(GroupSizeWarnings(group));
	    warnings.addAll(knowingRelation(allPersonsInGroup));							//checks if any persons in the group know each other
	    
	    if(getDifferentRestrictionsFor(allParticipantsInGroup) != null) {					// check if there are any restrictions that don't match
			warnings.add("folgende Restriktionen könnten Problematisch sein:" + 
					getDifferentRestrictionsFor(allParticipantsInGroup).toString() + 
					"bitte einmal überprüfen für folgende Gruppe:" + allParticipantsInGroup.toString());
		}	
	    
	    warnings.addAll(checkGroupCoursesStarter(group));								//checks if any team in the group has another group with the same course (Starter) already
	    warnings.addAll(checkGroupCoursesMain(group));									//checks if any team in the group has another group with the same course (Main) already
	    warnings.addAll(checkGroupCoursesDessert(group));								//checks if any team in the group has another group with the same course (Dessert) already
		
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
		
		for(Group group : allGroups) {										//check for warnings for each team in event 
																						
			if(!getWarnings(group).isEmpty()) {												//if there are warnings add the group to the list
			
				allGroupsWithWarnings.add(group);
			}
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
			List<Restriction> rest = part.getRestriction();										//get all restrictions for the actual participant
			
			for(Participant others : participants) {											//iterate again for comparison
				List<Restriction> restOfOthers = others.getRestriction();						//get all restrictions for the other participant
				
				if(!part.equals(others)) {														//make sure the participants are not the same
					for(Restriction compareRestriction : restOfOthers) {						//check if any restrictions of the participant and other participant match
						if(!rest.contains(compareRestriction)) {								//if they don't match add them to the list
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
		ScheduleController schedule = walkingDinnerController.getScheduleController();
		Map<Person,List<Person>> knownPersons = schedule.generateKnowingRelations();			//get map with all knowing relations for each person
		List<Person> knowingList = new ArrayList<Person>();
		List<String> warnings = new ArrayList<String>();						
		
		for(int i = 0; i<person.size()-1;i++) {		
			System.out.println(knownPersons);
			knowingList = knownPersons.get(person.get(i));							//get the knowing relations for a person
			for(int j = 1; j<person.size();j++) {														
				if(knowingList.contains(person.get(j))){										//check if another person is in the knowing relations list
					warnings.add(person.get(i) + " und" + person.get(j) + " kennen sich");	    //save warning if so
				}
			}		
		}

		return warnings;
	}
	
	private List<String> checkGroupCoursesStarter(Group group)
	{
		List<String> warnings = new ArrayList<String>();
		GroupController gc = walkingDinnerController.getGroupController();
		gc.setCourse(Course.STARTER);
	
		for(int i = 0; i< gc.getGroups().size(); i++){
			if(!gc.getGroups().get(i).equals(group)){
				for(int j = 0; j < group.getTeams().size();j++){
					if(gc.getGroups().get(i).getTeams().contains(group.getTeams().get(j))){
						warnings.add(group.getTeams().get(j) + "kommt in mehreren STARTER Gruppen vor, die andere Gruppe besteht aus: " + gc.getGroups().get(i).getTeams());	
					}
				}
			}
			
		}			
		return warnings;
	}
	
	private List<String> checkGroupCoursesMain(Group group)
	{
		List<String> warnings = new ArrayList<String>();
		GroupController gc = walkingDinnerController.getGroupController();
		gc.setCourse(Course.MAIN);
		
		for(int i = 0; i< gc.getGroups().size(); i++){
			if(!gc.getGroups().get(i).equals(group)){
				for(int j = 0; j < group.getTeams().size();j++){
					if(gc.getGroups().get(i).getTeams().contains(group.getTeams().get(j))){
						warnings.add(group.getTeams().get(j) + "kommt in mehreren MAIN Gruppen vor, die andere Gruppe besteht aus: " + gc.getGroups().get(i).getTeams());	
					}
				}
			}
			
		}
		return warnings;
	}
	
	private List<String> checkGroupCoursesDessert(Group group)
	{
		List<String> warnings = new ArrayList<String>();
		GroupController gc = walkingDinnerController.getGroupController();
		gc.setCourse(Course.DESSERT);

		for(int i = 0; i< gc.getGroups().size(); i++){
			if(!gc.getGroups().get(i).equals(group)){
				for(int j = 0; j < group.getTeams().size();j++){
					if(gc.getGroups().get(i).getTeams().contains(group.getTeams().get(j))){
						warnings.add(group.getTeams().get(j) + "kommt in mehreren DESSERT Gruppen vor, die andere Gruppe besteht aus: " + gc.getGroups().get(i).getTeams());	
					}
				}
			}	
		}
		return warnings;
	}
	
	private List<String> GroupSizeWarnings(Group group)
	{
		List<String> warnings = new ArrayList<String>();
		
		if(group.getTeams().size() < 3){												//checks if group size is below 3
			warnings.add("Gruppe zu klein");
		}
		
		if(group.getTeams().size() > 3){												//checks if group size is more than 3
			warnings.add("Gruppe zu groß");
		}
		
		if(group.getHostTeam() == null) {											//checks if there is a host team
			warnings.add("kein Hostteam festgelegt");
		}
			
		if(group.getGuest().isEmpty()) {												//checks if there are any guest teams in group
			warnings.add("keine Gastteams vorhanden");
		}
		
		if(group.getGuest().size() != 2)												//checks if there are the correct amount of guest teams
		{
			warnings.add("Die Anzahl der Gastteams stimmt nicht");
		}
		
		return warnings;
	}
	
	private List<String> TeamSizeWarnings(Team team)
	{
		List<String> warnings = new ArrayList<String>();
		
		if(team.getMembers().size() == 0) {												// check teamsize and save warning in list if the size is not correct
			warnings.add("Das Team ist leer und kann gelöscht werden");
		}
		
		if(team.getMembers().size() == 1) {												
			warnings.add("In dem Team befindet sich nur eine Person");
		}
		
		if(team.getMembers().size() > 3) {
			warnings.add("Teamgröße ist größer als 3");
		}
		
		return warnings;
	}

}
