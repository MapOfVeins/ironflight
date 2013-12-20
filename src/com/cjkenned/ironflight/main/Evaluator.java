package com.cjkenned.ironflight.main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Evaluates a given routine.  The evaluation is done in two main parts,
 * exercise difficulty and daily volume.  There is a set of maps to hold each
 * of these, done per day, and another map thats holds all of these maps for each
 * portion (difficulty, volume).  A recommendation is made based on these values,
 * also done for each day and for the overall routine.  The daily recommendations
 * are averaged to get the overall grade.
 * 
 * @author Cam Kennedy
 * @version 1.0
 *
 */
public class Evaluator {

	private Routine routine;
	private int difficulty;
	
	private final double SECONDARY_WEIGHT = 0.5;
	private final double TERTIARY_WEIGHT = 0.25;
	private final int NUM_GROUPS = 14;
	private final int NUM_DAYS = 7;
	
	private final int OVR_DIF_EASY_BEG = 80;
	private final int OVR_DIF_OK_BEG = 122;
	
	private final int OVR_DIF_EASY_INT = 55;
	private final int OVR_DIF_OK_INT = 95;
	
	private final int OVR_DIF_EASY_ADV = 30;
	private final int OVR_DIF_OK_ADV = 54;
	
	private final int OVR_VOL_EASY = 10;
	private final int OVR_VOL_OK = 17;
	
	private final double DAY_DIF_EASY = 40.0;
	private final double DAY_DIF_OK = 55.0;
	
	private final double DAY_VOL_EASY_STR = 50.0;
	private final double DAY_VOL_OK_STR = 123.0;
	
	private final double DAY_VOL_EASY_SIZ = 196.0;
	private final double DAY_VOL_OK_SIZ = 295.0;
	
	private final double MG_DIF_EASY = 20.0;
	private final double MG_DIF_OK = 30.0;
	
	private final double MG_VOL_EASY_STR = 10.0;
	private final double MG_VOL_OK_STR = 30.0;
	
	private final double MG_VOL_EASY_SIZ = 40.0;
	private final double MG_VOL_OK_SIZ = 60.0;

	// Maps for the perceived difficulties of the exercises performed on each day
	private HashMap<MuscleGroup, String> monDif = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> tueDif = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> wedDif = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> thuDif = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> friDif = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> satDif = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> sunDif = new HashMap<MuscleGroup, String>(NUM_GROUPS);

	// Map to store all the other difficulty maps
	private HashMap<Integer, HashMap<MuscleGroup, String>> allDifs = 
			new HashMap<Integer, HashMap<MuscleGroup, String>>(NUM_DAYS);
	
	// Map to store the hard numbers for estimated difficulty
	private HashMap<MuscleGroup, Double> exerDif =
			new HashMap<MuscleGroup, Double>(NUM_GROUPS);
	
	// Maps for the volume of each muscle group on each day
	private HashMap<MuscleGroup, String> monVol = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> tueVol = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> wedVol = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> thuVol = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> friVol = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> satVol = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	private HashMap<MuscleGroup, String> sunVol = new HashMap<MuscleGroup, String>(NUM_GROUPS);
	
	// Map to store all other volume maps
	private HashMap<Integer, HashMap<MuscleGroup, String>> allVols =
			new HashMap<Integer, HashMap<MuscleGroup, String>>(NUM_DAYS);
	
	// Map to store all the hard numbers for volumes
	private HashMap<MuscleGroup, Double> exerVol = 
			new HashMap<MuscleGroup, Double>(NUM_GROUPS);
	
	// String array to hold the daily recommendations 
	private String[] dayDifRecs = new String[NUM_DAYS];
	private String[] dayVolRecs = new String[NUM_DAYS];
	
	// To store total routine volumes
	private HashMap<MuscleGroup, Double> overallVol =
			new HashMap<MuscleGroup, Double>(NUM_GROUPS);
	
	// Lists to hold more/less recommendations
	private ArrayList<String> needMoreList =
			new ArrayList<String>();
	private ArrayList<String> needLessList =
			new ArrayList<String>();

	private final String[] dayArray = { "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday", "Sunday" };

	private final MuscleGroup[] muscles = { MuscleGroup.CHEST, MuscleGroup.BICEPS,
			MuscleGroup.TRICEPS, MuscleGroup.FOREARMS, MuscleGroup.SHOULDERS,
			MuscleGroup.TRAPS, MuscleGroup.UPPER_BACK, MuscleGroup.LOWER_BACK,
			MuscleGroup.LATS, MuscleGroup.ABS, MuscleGroup.GLUTES,
			MuscleGroup.HAMSTRINGS, MuscleGroup.QUADS, MuscleGroup.CALVES };

	public Evaluator(Routine routine) {
		this.routine = routine;
		initMaps();
	}

	/**
	 * Initializes all maps and adds the starting values to each of them.
	 */
	private void initMaps() {

		// Put all the day hashes into the main map 
		allDifs.put(0, monDif);
		allDifs.put(1, tueDif);
		allDifs.put(2, wedDif);
		allDifs.put(3, thuDif);
		allDifs.put(4, friDif);
		allDifs.put(5, satDif);
		allDifs.put(6, sunDif);
		
		// Put all the volume hashes into the main map
		allVols.put(0, monVol);
		allVols.put(1, tueVol);
		allVols.put(2, wedVol);
		allVols.put(3, thuVol);
		allVols.put(4, friVol);
		allVols.put(5, satVol);
		allVols.put(6, sunVol);
		
		// Fill the difficulty and volume maps with doubles, to store the raw values
		for (MuscleGroup m : muscles) {
			exerDif.put(m, 0.0);
			exerVol.put(m, 0.0);
			overallVol.put(m, 0.0);
		}
		
		 // Loop through each map, and then add each musclegroup to it. Set a
		 // default value of zero.
		for (int i = 0; i < NUM_DAYS; i++) {
			for (int j = 0; j < NUM_GROUPS; j++) {
				allDifs.get(i).put(muscles[j], "");
				allVols.get(i).put(muscles[j], "");
			}
		}
	}

	/**
	 * Main evaluator function.  Evaluates the work performed
	 * on each day and makes a recommendation.
	 * 
	 * @return	Result object containing the recommendations
	 * 			of the evaluator.
	 */
	public Result evaluate() {
		
		 // Get the overall routine difficulty coefficient from the experience value
		setDifficulty();

		// Evaluate each day individually and add the totals to each muscle
		// group value for the total routine. We use this to see if there are
		// any days in the routine that are particularly hard. Experience factor
		// does not apply to days.
		for (int i = 0; i < NUM_DAYS; i++) {
			ArrayList<Exercise> workDay = routine.getExerciseMap().get(dayArray[i]);
			HashMap<MuscleGroup, String> workDayDif = allDifs.get(i);
			HashMap<MuscleGroup, String> workDayVol = allVols.get(i);
			if (workDay.isEmpty()) {
				dayDifRecs[i] = "Rest";
				dayVolRecs[i] = "Rest";
			} else {
				evalWorkDay(i, workDay, workDayDif, workDayVol);
			}
		}
		
		String finalDifRec = getOverallDif();
		String finalVolRec = getOverallVol();
		
		calcGroupRecs();
		
		Result result = new Result(allDifs, allVols, dayDifRecs, dayVolRecs, finalDifRec, finalVolRec, needMoreList, needLessList);
		return result;
	}

	private void calcGroupRecs() {
		for (MuscleGroup m : muscles) {
			Double totalVol = overallVol.get(m);

			if (m.equals(MuscleGroup.FOREARMS) || m.equals(MuscleGroup.ABS) ||
					m.equals(MuscleGroup.CALVES)) {
				if (totalVol < 15.0) {
					needMoreList.add(m.toString().toLowerCase());
				} else if (totalVol > 50.0) {
					needLessList.add(m.toString().toLowerCase());
				}
			} else {
				if (totalVol < 30.0) {
					needMoreList.add(m.toString().toLowerCase());
				} else if (totalVol > 145.0) {
					needLessList.add(m.toString().toLowerCase());
				}
			}
		}	
	}

	/**
	 * Calculates an overall volume recommendation based on the 
	 * results given for each day. This is called after the daily
	 * volumes are evaluated.
	 * 
	 * @return	The overall volume recommendation
	 */
	private String getOverallVol() {
		int hardCount = 0;
		int okCount = 0;
		int easyCount = 0;
		int overallVal = 0;
		String overallVol = "";
		
		for (int i = 0; i < NUM_DAYS; i++) {
			String volVal = dayVolRecs[i];
			if (volVal.equals("Easy")) {
				easyCount++;
			} else if (volVal.equals("OK")) {
				okCount += 2;
			} else if (volVal.equals("Hard")){
				hardCount += 3;
			}
		}
		
		overallVal = (easyCount + okCount + hardCount);
		
		if (overallVal < OVR_VOL_EASY) {
			overallVol = "Easy";
		} else if (overallVal <= OVR_VOL_OK) {
			overallVol = "OK";
		} else {
			overallVol = "Hard";
		}
		return overallVol;
	}

	/**
	 * Calculates an overall difficulty recommendation based on the 
	 * results given for each day. This is called after the daily
	 * volumes are evaluated.
	 * 
	 * @return	The overall difficulty recommendation
	 */
	private String getOverallDif() {
		int hardCount = 0;
		int okCount = 0;
		int easyCount = 0;
		int overallVal = 0;
		String overallDif = "";
		
		for (int i = 0; i < NUM_DAYS; i++) {
			String difVal = dayDifRecs[i];
			if (difVal.equals("Easy")) {
				easyCount++;
			} else if (difVal.equals("OK")) {
				okCount += 2;
			} else if (difVal.equals("Hard")) {
				hardCount += 3;
			}
		}
		
		// Here, we factor in the difficulty coefficient
		overallVal = (easyCount + okCount + hardCount) * difficulty;
		
		// We also need to account for the routine experience level
		if (routine.getExp().equals("Beginner")) {
			if (overallVal < OVR_DIF_EASY_BEG) {
				overallDif = "Easy";
			} else if (overallVal <= OVR_DIF_OK_BEG) {
				overallDif = "OK";
			} else {
				overallDif = "Hard";
			}
		} else if (routine.getExp().equals("Intermediate")) { 
			if (overallVal < OVR_DIF_EASY_INT) {
				overallDif = "Easy";
			} else if (overallVal <= OVR_DIF_OK_INT) {
				overallDif = "OK";
			} else {
				overallDif = "Hard";
			}
		} else {
			if (overallVal < OVR_DIF_EASY_ADV) {
				overallDif = "Easy";
			} else if (overallVal <= OVR_DIF_OK_ADV) {
				overallDif = "OK";
			} else {
				overallDif = "Hard";
			}
		}
		return overallDif;
	}

	/**
	 * Finds the difficulty sum of an exercise map.
	 * 
	 * @param workDay	Day to calculate
	 * @return			Sum of the given map
	 */
	private double getMapSum(HashMap<MuscleGroup, Double> workDay) {
		double totalDifVal = 0.0;
		for (MuscleGroup m : muscles) {
			totalDifVal += workDay.get(m);
		}
		return totalDifVal;
	}
	
	/**
	 * Evaluates the volume and difficulty for each day in a routine.  Generates a string
	 * recommendation based on the input values.  Also gives an overall recommendation
	 * for each muscle group used during the day.
	 * 
	 * @param day			The day to evaluate
	 * @param workDay		List of exercises for that day
	 * @param workDayDif	Map of difficulty recommendations for each muscle
	 * @param workDayVol	Map of volume recommendations for each muscle
	 */
	private void evalWorkDay(int day, ArrayList<Exercise> workDay, HashMap<MuscleGroup, String> workDayDif, HashMap<MuscleGroup, String> workDayVol) {
		for (Exercise e : workDay) {
		    // Get each coefficient from the exercise.  We need primary, secondary
			// and tertiary values.  Secondary and tertiary may not apply if the muscle
			// group is null.  In this case, they are not used.
			double perf = e.getReps() * e.getSets();
			
			double primaryVal = e.getWeight();
			double secondaryVal = (e.getWeight() * SECONDARY_WEIGHT);
			double tertiaryVal = (e.getWeight() * TERTIARY_WEIGHT);
					
			// Update the values in the map.
			double curPVal = exerDif.get(e.getPrimary());
			curPVal += primaryVal;
			exerDif.put(e.getPrimary(), curPVal);
			
			double curPVol = exerVol.get(e.getPrimary());
			curPVol += perf;
			exerVol.put(e.getPrimary(), curPVol);
					
			// To put in the secondary and tertiary values, we have to make sure the
			// muscle group is not null first
			if (e.getSecondary() != null) {
				double curSVal = exerDif.get(e.getSecondary());
				curSVal += secondaryVal;
				exerDif.put(e.getSecondary(), curSVal);
				
				double curSVol = exerVol.get(e.getSecondary());
				curSVol += perf;
				exerVol.put(e.getSecondary(), curSVol);
			}		
			if (e.getTertiary() != null) {
				double curTVal = exerDif.get(e.getTertiary());
				curTVal += tertiaryVal;
				exerDif.put(e.getTertiary(), curTVal);
				
				double curTVol = exerVol.get(e.getTertiary());
				curTVol += perf;
				exerVol.put(e.getSecondary(), curTVol);
			}
		}
		
		// Now, get a daily recommendation (used for the overall)
		// and a recommendation for each muscle group
		String goal = routine.getGoal();
		calcDailyRecs(day, goal);
		calcMuscleRecs(goal, workDayDif, workDayVol);			
	}

	/**
	 * Calculates the recommendations for each muscle group used during the day
	 * 
	 * @param goal			User's input goal
	 * @param workDayDif	Map holding muscle difficulty recommendations
	 * @param workDayVol	Map holding muscle volume recommendations
	 */
	private void calcMuscleRecs(String goal, HashMap<MuscleGroup, String> workDayDif, HashMap<MuscleGroup, String> workDayVol ) {
		// At this point, the exerDif map is filled with doubles that contain the difficulties
		// of each muscle group for the day (specified in the parameter).  Now we can take those
		// numbers and translate them to a recommendation, and fill the map for that day.
		for (MuscleGroup m : muscles) {
			double dif = exerDif.get(m);
			double vol = exerVol.get(m);
			String difRec = "";
			String volRec = "";
			
			// Convert the double to a String recommendation. Then we can add that String to
			// the proper map.
			if (dif < MG_DIF_EASY) {
				difRec = "Easy";
			} else if (dif <= MG_DIF_OK) {
				difRec = "OK";
			} else {
				difRec = "Hard";
			}
			workDayDif.put(m, difRec);
			
			// Reset the value of the muscle group back to zero, so it can be used again in 
			// the next iteration of the function.
			exerDif.put(m, 0.0);
			
			// Now repeat this process, but for the volume maps
			if (goal.equals("Strength")) {
				if (vol < MG_VOL_EASY_STR) {
					volRec = "Easy";
				} else if (vol < MG_VOL_OK_STR) {
					volRec = "OK";
				} else {
					volRec = "Hard";
				}
			} else if (goal.equals("Size")) {
				if (vol < MG_VOL_EASY_SIZ) {
					volRec = "Easy";
				} else if (vol < MG_VOL_OK_SIZ) {
					volRec = "OK";
				} else {
					volRec = "Hard";
				}
			}
			workDayVol.put(m, volRec);
			double curVol = overallVol.get(m);
			curVol += vol;
			overallVol.put(m, curVol);
			exerVol.put(m, 0.0);
		}	
	}
	
	/**
	 * Calculates the daily difficulty and volume recommendations.  This is
	 * an overall view, considering all muscle groups.
	 * 
	 * @param day	Day to calculate on
	 * @param goal	User's input goal
	 */
	private void calcDailyRecs(int day, String goal) {
		double difSum = getMapSum(exerDif);
		double volSum = getMapSum(exerVol);
		String dayDifRec = "";
		String dayVolRec = "";
		
		if (difSum < DAY_DIF_EASY) {
			dayDifRec = "Easy";
		} else if (difSum < DAY_DIF_OK) {
			dayDifRec = "OK";
		} else {
			dayDifRec = "Hard";
		}
		
		// Must take into account the goal here
		if (goal.equals("Strength")) {
			if (volSum < DAY_VOL_EASY_STR) {
				dayVolRec = "Easy";
			} else if (volSum < DAY_VOL_OK_STR) {
				dayVolRec = "OK";
			} else {
				dayVolRec = "Hard";
			}
		} else if (goal.equals("Size")) {
			if (volSum < DAY_VOL_EASY_SIZ) {
				dayVolRec = "Easy";
			} else if (volSum < DAY_VOL_OK_SIZ) {
				dayVolRec = "OK";
			} else {
				dayVolRec = "Hard";
			}
		}
		
		dayDifRecs[day] = dayDifRec;
		dayVolRecs[day] = dayVolRec;	
	}

	/**
	 * Set the difficulty coefficient based on the user's
	 * supplied experience level.
	 */
	private void setDifficulty() {
		String exp = routine.getExp();
		if (exp.equals("Beginner")) {
			difficulty = 6;
		} else if (exp.equals("Intermediate")) {
			difficulty = 4;
		} else {
			difficulty = 2;
		}
	}
}
