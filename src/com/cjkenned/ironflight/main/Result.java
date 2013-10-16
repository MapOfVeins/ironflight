package com.cjkenned.ironflight.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores the results of an evaluation to be displayed and returned.
 * 
 * @author Cam Kennedy
 *
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 2L;
	private HashMap<Integer, HashMap<MuscleGroup, String>> allDifs;
	private HashMap<Integer, HashMap<MuscleGroup, String>> allVols;
	
	private String[] dayDifs;
	private String[] dayVols;
	
	private String overallDif;
	private String overallVol;
	
	private ArrayList<String> needMore;
	private ArrayList<String> needLess;
	
	public Result() {
		
	}
	
	public Result(HashMap<Integer, HashMap<MuscleGroup, String>> difficulty, HashMap<Integer, HashMap<MuscleGroup, String>> volume,
			String[] dayDifs, String[] dayVols, String overallDif, String overallVol, ArrayList<String> m, ArrayList<String> l) {
		this.allDifs = difficulty;
		this.allVols = volume;
		this.dayDifs = dayDifs;
		this.dayVols = dayVols;
		this.overallDif = overallDif;
		this.overallVol = overallVol;
		this.needMore = m;
		this.needLess = l;
	}
	
	public String[] getAllDaysDifficulty() {
		return dayDifs;
	}
	
	public String[] getAllDaysVolume() {
		return dayVols;
	}
	
	public String getDifficulty() {
		return overallDif;
	}
	
	public String getVolume() {
		return overallVol;
	}
	
	public HashMap<MuscleGroup, String> getDifficultyMap(int day) {
		return allDifs.get(day);
	}
	
	public HashMap<MuscleGroup, String> getVolumeMap(int day) {
		return allVols.get(day);
	}
	
	public ArrayList<String> getMoreList() {
		return needMore;
	}
	
	public ArrayList<String> getLessList() {
		return needLess;
	}

}
