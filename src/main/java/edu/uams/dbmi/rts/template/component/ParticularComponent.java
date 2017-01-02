package edu.uams.dbmi.rts.template.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.uams.dbmi.rts.iui.Iui;

/**
 * represents the particular(s) that the templates reference
 * 
 * @author Josh Hanna
 *
 */
public class ParticularComponent {
	List<Iui> particulars = new ArrayList<Iui>();

	/**
	 * create a ParticularComponent with only one particular
	 * @param particular
	 */
	public ParticularComponent(Iui particular){
		this.particulars.add(particular);
	}

	/**
	 * create a ParticularComponent with an ordered list of particulars
	 * @param particulars
	 */
	public ParticularComponent(List<Iui> particulars){
		this.particulars = particulars;
	}

	public ParticularComponent(){

	}

	/**
	 * get the first (or only) particular in this component
	 * @return
	 */
	public Iui getParticular(){
		if(particulars.size() >= 1){
			return particulars.get(0);
		} else {
			return null;
		}
	}

	/**
	 * get the particular in this component at index
	 * @param index
	 * @return
	 */
	public Iui getParticular(int index){
		return particulars.get(index);
	}

	/**
	 * get all the full list of the particulars in the component
	 * @return
	 */
	public List<Iui> getParticulars(){
		return particulars;
	}
	
	public void setParticulars(List<Iui> particulars){
		this.particulars = particulars;
	}

	/**
	 * sets the first particular
	 * @param particular
	 */
	public void setParticular(Iui particular){
		if(this.particulars.size() < 1){
			this.particulars.add(particular);
		} else {
			this.particulars.set(0, particular);
		}
	}

	/**
	 * sets the particular at index
	 * @param particular
	 * @param index
	 */
	public void setParticular(int index, Iui particular){
		this.particulars.set(index, particular);
	}

	/**
	 * adds a particular at the end of the particular list
	 * @param particular
	 */
	public void addParticular(Iui particular){
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
	public void addAllParticulars(Collection<Iui> particulars){
		this.particulars.addAll(particulars);
	}

}
