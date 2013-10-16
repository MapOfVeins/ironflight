package com.cjkenned.ironflight.main;

/**
 * 
 * @author cam
 *
 */
public class Exercise implements Comparable<Exercise> {
	
	private String name;
	private MuscleGroup primaryMuscle;
	private MuscleGroup secondaryMuscle;
	private MuscleGroup tertiaryMuscle;
	private int weight;
	private int sets;
	private int reps;
	
	public Exercise() {
	}
	
	/**
	 * Create a new exercise, setting all properties.
	 * 
	 * @param n		Exercise name
	 * @param m1	Primary muscle used
	 * @param m2	Secondary muscle used
	 * @param m3	Tertiary muscle used
	 * @param w		Difficulty weighting
	 */
	public Exercise(String n, MuscleGroup m1, MuscleGroup m2, MuscleGroup m3, int w) {
		super();
		this.name = n;
		this.primaryMuscle = m1;
		this.secondaryMuscle = m2;
		this.tertiaryMuscle = m3;
		this.weight = w;
	}
	
	@Override
	public int compareTo(Exercise another) {
		return this.getName().compareTo(another.getName());
	}

	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPrimary(MuscleGroup g) {
		primaryMuscle = g;
	}
	
	public MuscleGroup getPrimary() {
		return primaryMuscle;
	}
	
	public MuscleGroup getSecondary() {
		return secondaryMuscle;
	}
	
	public MuscleGroup getTertiary() {
		return tertiaryMuscle;
	}

	public void setWeight(int v) {
		weight = v;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setSets(int s) {
		sets = s;
	}
	
	public void setReps(int r) {
		reps = r;
	}
	
	public int getSets() {
		return sets;
	}
	
	public int getReps() {
		return reps;
	}
	
}
