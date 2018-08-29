/**
 * 
 */
package controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.*;

/**
 * @author sopr026
 *
 */
public class RestrictionControllerTest {
	
	
	private RestrictionController restrictionController;
	private WalkingDinnerController wdC = new WalkingDinnerController();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//restrictionController = new RestrictionController(wdC);
		wdC = TestDataFactory.createTestWalkingDinnerController();
		restrictionController = wdC.getRestrictionController();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests, whether after calling this method the amount of Restrictions has increased
	 * Test method for {@link controller.RestrictionController#addNewRestriction(java.lang.String)}.
	 */
	@Test
	public void testAddNewRestriction() {
		int oldAmountOfRestrictions = wdC.getWalkingDinner().getCurrentEvent().getRestriction().size();
		restrictionController.addNewRestriction("testRestriction");
		int newAmountOfRestrictions = wdC.getWalkingDinner().getCurrentEvent().getRestriction().size();
		assertTrue("The amount of Restrictions has to increase.", oldAmountOfRestrictions < newAmountOfRestrictions);
	}

	/**
	 * The size of the returned List should be >= 3 and has to contain
	 * vegan, vegetarian and noAlcohol
	 * Test method for {@link controller.RestrictionController#getEventRestrictions()}.
	 */
	@Test
	public void testGetEventRestrictions() {
		List<Restriction> restrictions = restrictionController.getEventRestrictions();
		assertTrue("There should be 3 or more Restrictions.",restrictions.size() >= 3);
		int countPermanents = 0;
		for(Restriction restriction:restrictions){
			if(restriction.getName().equals("Vegan")){
				countPermanents++;
			}
			else if(restriction.getName().equals("Vegetarian")){
				countPermanents++;
			}
			else if(restriction.getName().equals("noAlcohol")){
				countPermanents++;
			}
		}
		assertEquals("There should be 3 permanent Restrictions", 3, countPermanents);
	}

	/**
	 * After calling the method Participant.getRestrictions should be equal
	 * to the List given to the method as a parameter.
	 * Also the Participant has to be in each Restriction.getParticipants List
	 * for each restriction in the parameter List, but in no Restriction else.
	 * Test method for {@link controller.RestrictionController#setParticipantRestrictions(java.util.List)}.
	 */
	@Test
	public void testSetParticipantRestrictions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.RestrictionController#getParticipantRestrictions()}.
	 */
	@Test
	public void testGetParticipantRestrictions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.RestrictionController#renameRestriction(model.Restriction, java.lang.String)}.
	 */
	@Test
	public void testRenameRestriction() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.RestrictionController#getWalkingDinnerController()}.
	 */
	@Test
	public void testGetWalkingDinnerController() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link controller.RestrictionController#setWalkingDinnerController(controller.WalkingDinnerController)}.
	 */
	@Test
	public void testSetWalkingDinnerController() {
		fail("Not yet implemented");
	}

}
