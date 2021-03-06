package controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Sort indexes of an array by their value
 * @author sopr026
 *
 * @param <T>
 */
public class IndexSorter<T extends Comparable<T>> implements Comparator<Integer>{
	private final T[] values;
	private final Integer[] indexes;
	/**
	 * Constructs a new IndexSorter based upon the parameter array.
	 * @param d
	 */
	public IndexSorter(T[] array){
		this.values = array;
		indexes = new Integer[this.values.length];
		for ( int i = 0; i < indexes.length; i++ ){
			indexes[i] = i;
		}
	}
	/**
	 * Constructs a new IndexSorter based upon the parameter List.
	 * @param d
	 */
	public IndexSorter(List<T> currentList){
		this.values = (T[])currentList.toArray();
		for ( int i = 0; i < values.length; i++ ){
			values[i] = currentList.get(i);
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
		T firstToCompare = values[arg0];
		T secondToCompare = values[arg1];
		return firstToCompare.compareTo(secondToCompare);
	}
}
