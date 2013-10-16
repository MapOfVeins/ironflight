package com.cjkenned.ironflight.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores all the exercises performed each day.
 * 
 * @author Cam Kennedy
 *
 */
public class Routine implements Serializable {

	private static final long serialVersionUID = 1L;
	private String goal;
	private String exp;
	private HashMap<String, ArrayList<Exercise>> exerciseMap;
	
	/**
	 * Build a new routine from another one.
	 * 
	 * @param r	Routine to clone
	 */
	public Routine(Routine r) {
		this.goal = r.getGoal();
		this.exp = r.getExp();
		this.exerciseMap = r.getExerciseMap();
	}
	
	public Routine(String g, String e, HashMap<String, ArrayList<Exercise>> r) {
		this.goal = g;
		this.exp = e;
		this.exerciseMap = r;
	}
	
	public Routine() {
		this.goal = "not set";
		this.exp  = "not set";
	}
	
	public void setGoal(String g) {
		goal = g;
	}
	
	public void setExp(String e) {
		exp = e;
	}
	
	public String getGoal() {
		return goal;
	}
	
	public String getExp() {
		return exp;
	}
	
	public HashMap<String, ArrayList<Exercise>> getExerciseMap() {
		return exerciseMap;
	}
	
	public void createRoutine() {
		exerciseMap = new HashMap<String, ArrayList<Exercise>>(7);
	}
	
	public void addDay(String s, ArrayList<Exercise> d) {
		exerciseMap.put(s, d);
	}
	
	public void delDay(String s) {
		exerciseMap.remove(s);
	}
}
