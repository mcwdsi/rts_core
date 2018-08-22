package edu.uams.dbmi.rts.tuple.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.uams.dbmi.rts.ParticularReference;

/**
 * represents the particular(s) that the templates reference.  
 * 
 * Should only be one particular in all cases except PtoP templates, 
 * 	where there should be at least two particulars.
 * 
 * @author Josh Hanna
 *
 */
public class ParticularComponent<T extends ParticularReference> {
	ArrayList<T> particulars; 
	
	public ParticularComponent() {
		particulars = new ArrayList<T>();
	}


	public T getParticular() {
		return particulars.get(0);
	}


	public  T getParticular(int index) {
		return particulars.get(index);
	}


	@SuppressWarnings("unchecked")
	public List<T> getParticulars() {
		return (List<T>) particulars.clone();
	}
	
	public void setParticular(int index, T particular){
		this.particulars.set(index, particular);
	}

	/**
	 * adds a particular at the end of the particular list
	 * @param particular
	 */
	public void addParticular(T particular){
		this.particulars.add(particular);
	}

	/**
	 * removes the particular at index from the component
	 * @param index
	 */
	public void removeParticular(int index){
		this.particulars.remove(index);
	}

	/**
	 * clears the particulars from this component
	 */
	public void clearParticulars(){
		this.particulars.clear();
	}

	/**
	 * adds all particulars in the container to the component
	 * @param particulars
	 */
	public void addAllParticulars(Collection<T> particulars){
		/* A bit concerned about this: if the container has 
		 *  no ordering (like a bag) then the order could be
		 *  random, whereas the tuple of particulars is 
		 *  ordered.  Could result in asserting nonsense.
		 */
		this.particulars.addAll(particulars);
	}


	public boolean isEmpty() {
		return particulars.isEmpty();
	}

}
