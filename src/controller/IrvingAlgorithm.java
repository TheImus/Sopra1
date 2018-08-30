package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.IrvingMatchable;

public class IrvingAlgorithm {

	/**
	 * Irving-Algorithm for matching Participants into teams
	 * @param participants
	 * @return HashMap - which describes teams
	 */
	public static HashMap<IrvingMatchable, IrvingMatchable> irvingAlgorithmus(List<IrvingMatchable> entities){
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
	 * Computes an Irving-Array from a list of participants, where each row is the sorted list of matching participants for a team
	 * @param participants
	 * @return irvingArray
	 */
	private static Integer[][] generateIrvingArray(List<IrvingMatchable> entities){
		//Generates an Array for Irving-Algorithm
		if(!entities.isEmpty()){
			int numberOfEntities = entities.size();
			//The Irving-Algorithm needs an even amount of participants
			if(numberOfEntities % 2 == 1){
				numberOfEntities++;
			}
			Integer[][] dynArray = new Integer[numberOfEntities][numberOfEntities];
			//Rows and Columns are indexed over participants and the entries are the SymmetricDifferences of Restrictions 
			//for these participants
			for(IrvingMatchable entity1:entities){
				for(IrvingMatchable entity2:entities){
					int i = entities.indexOf(entity1);
					int j = entities.indexOf(entity2);
					if(i < numberOfEntities && j < numberOfEntities && i>=0 && j >= 0){
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
			if(numberOfEntities > entities.size()){
				for(int i = 0; i < numberOfEntities; i++){
					assert(dynArray[i][numberOfEntities-1] == null);
					assert(dynArray[numberOfEntities-1][i] == null);
					dynArray[i][numberOfEntities-1] = Integer.MAX_VALUE;
					dynArray[numberOfEntities-1][i] = Integer.MAX_VALUE;				
				}
			}
			Integer[][] irvingArray = new Integer[numberOfEntities][numberOfEntities];
			//The IrvingArray is the array above, but each row contains the indexes of participants sorted by their value
			for(int i = 0; i < numberOfEntities; i++){
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
	private static ArrayList<ArrayList<Integer>> irvingArrayToLists(Integer[][] irvingArray){
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
	 * Phase2 of Irving-Algorithm, where non matching pairs are rotated until they match
	 * @param irvingLists
	 * @return
	 */
	private static boolean irvingPhase2(ArrayList<ArrayList<Integer>> irvingLists){
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
	/**
	 * Removes the value from the key-indexed list and key from value-indexed list
	 * @param irvingLists
	 * @param rotationPairs
	 * @return true, if a rotation has been removed
	 */
	private static boolean removeRotationsFromLists(ArrayList<ArrayList<Integer>> irvingLists,
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

}
