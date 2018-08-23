/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * @author sopr024
 *
 */
public class ConsistencyControllerTest {

	/**
	 * Test method for {@link controller.ConsistencyController#getWalkingDinnerController()}.
	 * check if return is null
	 */
	@Test
	public void testGetWalkingDinnerController() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	@Test
	public void testSetWalkingDinnerController() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getWarnings(model.Team)}.
	 * check if list is null, check if test gives the correct warnings if the object has warning cases
	 * how to check: generate/give a team which throws a warning on purpose and check if the method gives the correct warning
	 */
	@Test
	public void testGetWarningsTeam() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentTeams()}.
	 * check if the list is correct and not null (contains all teams with warnings)
	 */
	@Test
	public void testGetInconsistentTeams() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getWarnings(model.Group)}.
	 * check if the list is not empty/null and check if the warnings are correct
	 */
	@Test
	public void testGetWarningsGroup() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getInconsistentGroups()}.
	 * check if the list is correct and not null (contains all groups with warnings)
	 * how to check: generate/give a group which throws a warning on purpose and check if the method gives the correct warning
	 */
	@Test
	public void testGetInconsistentGroups() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.ConsistencyController#getDifferentRestrictionsFor(java.util.List)}.
	 * check if the returned list is not null and check if you get the correct different restrictions if you put 2 participants with different restrictions
	 */
	@Test
	public void testGetDifferentRestrictionsFor() {
		fail("Not yet implemented");
	}

}
