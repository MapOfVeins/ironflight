package com.cjkenned.ironflight.services;

import java.util.ArrayList;
import java.util.Collections;

import com.cjkenned.ironflight.main.Exercise;
import com.cjkenned.ironflight.main.MuscleGroup;

public class ExerciseService {

	private static ArrayList<Exercise> exerciseList;
	

	public static ArrayList<Exercise> getExerciseList() {
		exerciseList = new ArrayList<Exercise>();

		addChest();
		addArms();
		addBack();
		addTraps();
		addLowBack();
		addLowBody();
		addShoulders();
		addAbs();

		Collections.sort(exerciseList);
		return exerciseList;
	}

	private static void addAbs() {
		Exercise situp = new Exercise("Sit-Up, Unweighted", MuscleGroup.ABS,
				null, null, 3);
		exerciseList.add(situp);

		Exercise wSitup = new Exercise("Sit-Up, Weighted", MuscleGroup.ABS,
				null, null, 4);
		exerciseList.add(wSitup);

		Exercise wCrunch = new Exercise("Crunch, Weighted", MuscleGroup.ABS,
				null, null, 4);
		exerciseList.add(wCrunch);

		Exercise crunch = new Exercise("Crunch, unWeighted", MuscleGroup.ABS,
				null, null, 3);
		exerciseList.add(crunch);

		Exercise hlRaise = new Exercise("Hanging Leg Raise", MuscleGroup.ABS,
				null, MuscleGroup.SHOULDERS, 6);
		exerciseList.add(hlRaise);

		Exercise lRaise = new Exercise("Lying Leg Raise", MuscleGroup.ABS,
				null, null, 6);
		exerciseList.add(lRaise);

		Exercise rTwist = new Exercise("Russian Twist", MuscleGroup.ABS, null,
				null, 4);
		exerciseList.add(rTwist);

		Exercise sideRaise = new Exercise("Side Raise", MuscleGroup.ABS, null,
				null, 4);
		exerciseList.add(sideRaise);

	}

	private static void addShoulders() {
		Exercise militaryPress = new Exercise("Military Press",
				MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS, MuscleGroup.TRAPS,
				9);
		exerciseList.add(militaryPress);

		Exercise shoulderPress = new Exercise("Shoulder Press, Standing",
				MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS, MuscleGroup.TRAPS,
				9);
		exerciseList.add(shoulderPress);

		Exercise sShoulderPress = new Exercise("Shoulder Press, Seated",
				MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS, MuscleGroup.TRAPS,
				9);
		exerciseList.add(sShoulderPress);

		Exercise latRaise = new Exercise("Lateral Raise",
				MuscleGroup.SHOULDERS, null, MuscleGroup.FOREARMS, 4);
		exerciseList.add(latRaise);

		Exercise frontRaise = new Exercise("Front Raise",
				MuscleGroup.SHOULDERS, null, MuscleGroup.FOREARMS, 4);
		exerciseList.add(frontRaise);

		Exercise arnoldPress = new Exercise("Arnold Press",
				MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS, MuscleGroup.BICEPS,
				7);
		exerciseList.add(arnoldPress);
	}

	private static void addLowBody() {
		// Lower body exercises
		Exercise lbSquat = new Exercise("Squat, Low-bar", MuscleGroup.QUADS,
				MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, 10);
		exerciseList.add(lbSquat);

		Exercise hbSquat = new Exercise("Squat, High-bar", MuscleGroup.QUADS,
				MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, 10);
		exerciseList.add(hbSquat);

		Exercise bSquat = new Exercise("Box Squat", MuscleGroup.QUADS,
				MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, 10);
		exerciseList.add(bSquat);

		Exercise fSquat = new Exercise("Front Squat", MuscleGroup.QUADS,
				MuscleGroup.GLUTES, MuscleGroup.UPPER_BACK, 10);
		exerciseList.add(fSquat);

		Exercise splitSquat = new Exercise("Split squat", MuscleGroup.QUADS,
				MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, 7);
		exerciseList.add(splitSquat);

		Exercise hSquat = new Exercise("Hack squat", MuscleGroup.QUADS,
				MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS, 6);
		exerciseList.add(hSquat);

		Exercise rLunge = new Exercise("Lunges, Rear, Barbell",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				6);
		exerciseList.add(rLunge);

		Exercise dbrLunge = new Exercise("Lunges, Rear, Dumbbell",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				6);
		exerciseList.add(dbrLunge);

		Exercise crLunge = new Exercise("Lunges, Rear, Cable",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				5);
		exerciseList.add(crLunge);

		Exercise wLunge = new Exercise("Lunges, Walking, Barbell",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				6);
		exerciseList.add(wLunge);

		Exercise dbwLunge = new Exercise("Lunges, Walking, Dumbbell",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				6);
		exerciseList.add(dbwLunge);

		Exercise stepups = new Exercise("Step-ups, Unweighted",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				3);
		exerciseList.add(stepups);

		Exercise wStepups = new Exercise("Step-ups, Weighted",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				5);
		exerciseList.add(wStepups);

		Exercise hipThrust = new Exercise("Hip Thrusts", MuscleGroup.GLUTES,
				MuscleGroup.LOWER_BACK, null, 5);
		exerciseList.add(hipThrust);

		Exercise lLegCurl = new Exercise("Leg Curl, Lying",
				MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES,
				6);
		exerciseList.add(lLegCurl);

		Exercise sLegCurl = new Exercise("Leg Curl, Standing",
				MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, MuscleGroup.CALVES,
				5);
		exerciseList.add(sLegCurl);

		Exercise seatedLegCurl = new Exercise("Leg Curl, Seated",
				MuscleGroup.HAMSTRINGS, null, MuscleGroup.CALVES, 4);
		exerciseList.add(seatedLegCurl);

		Exercise legExt = new Exercise("Leg Extensions", MuscleGroup.QUADS,
				null, MuscleGroup.CALVES, 4);
		exerciseList.add(legExt);

		Exercise legPress = new Exercise("Leg Press, 45 degree",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				6);
		exerciseList.add(legPress);

		Exercise lLegPress = new Exercise("Leg Press, Lying",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				6);
		exerciseList.add(lLegPress);

		Exercise sLegPress = new Exercise("Leg Press, Seated",
				MuscleGroup.QUADS, MuscleGroup.GLUTES, MuscleGroup.HAMSTRINGS,
				4);
		exerciseList.add(sLegPress);

		Exercise calfRaise = new Exercise("Calf Raise, Standing, Barbell",
				MuscleGroup.CALVES, null, null, 4);
		exerciseList.add(calfRaise);

		Exercise dCalfRaise = new Exercise("Calf Raise, Donkey",
				MuscleGroup.CALVES, null, null, 5);
		exerciseList.add(dCalfRaise);

		Exercise mCalfRaise = new Exercise("Calf Raise, Standing, Machine",
				MuscleGroup.CALVES, null, null, 3);
		exerciseList.add(mCalfRaise);

		Exercise dbCalfRaise = new Exercise("Calf Raise, Standing, Dumbbell",
				MuscleGroup.CALVES, null, null, 4);
		exerciseList.add(dbCalfRaise);

		Exercise sCalfRaise = new Exercise("Calf Raise, Seated",
				MuscleGroup.CALVES, null, null, 4);
		exerciseList.add(sCalfRaise);

		Exercise calfPress = new Exercise("Calf Press, 45 degree",
				MuscleGroup.CALVES, null, null, 5);
		exerciseList.add(calfPress);

		Exercise lCalfPress = new Exercise("Calf Press, Lying",
				MuscleGroup.CALVES, null, null, 4);
		exerciseList.add(lCalfPress);

		Exercise sCalfPress = new Exercise("Calf Press, Seated",
				MuscleGroup.CALVES, null, null, 4);
		exerciseList.add(sCalfPress);

	}

	private static void addLowBack() {
		Exercise deadlift = new Exercise("Deadlift", MuscleGroup.LOWER_BACK,
				MuscleGroup.QUADS, MuscleGroup.GLUTES, 11);
		exerciseList.add(deadlift);

		Exercise backExtension = new Exercise("Back Raise, Unweighted",
				MuscleGroup.LOWER_BACK, MuscleGroup.HAMSTRINGS, null, 3);
		exerciseList.add(backExtension);

		Exercise wBackExtension = new Exercise("Back Raise, Weighted",
				MuscleGroup.LOWER_BACK, MuscleGroup.HAMSTRINGS, null, 4);
		exerciseList.add(wBackExtension);

		Exercise rExtension = new Exercise(
				"Reverse Hyper-extension, Unweighted", MuscleGroup.LOWER_BACK,
				MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, 4);
		exerciseList.add(rExtension);

		Exercise rwExtension = new Exercise(
				"Reverse Hyper-extension, Weighted", MuscleGroup.LOWER_BACK,
				MuscleGroup.HAMSTRINGS, MuscleGroup.GLUTES, 5);
		exerciseList.add(rwExtension);

		Exercise gluteHam = new Exercise("Glute-Ham Raise, Unweighted",
				MuscleGroup.LOWER_BACK, MuscleGroup.HAMSTRINGS,
				MuscleGroup.GLUTES, 6);
		exerciseList.add(gluteHam);

		Exercise wGluteHam = new Exercise("Glute-Ham Raise, Weighted",
				MuscleGroup.LOWER_BACK, MuscleGroup.HAMSTRINGS,
				MuscleGroup.GLUTES, 7);
		exerciseList.add(wGluteHam);

		Exercise slDead = new Exercise("Straight Legged Deadlift",
				MuscleGroup.LOWER_BACK, MuscleGroup.HAMSTRINGS,
				MuscleGroup.GLUTES, 8);
		exerciseList.add(slDead);

		Exercise rDead = new Exercise("Romanian Deadlift",
				MuscleGroup.LOWER_BACK, MuscleGroup.HAMSTRINGS,
				MuscleGroup.GLUTES, 8);
		exerciseList.add(rDead);

	}

	private static void addTraps() {
		Exercise bbShrug = new Exercise("Shrugs, Barbell", MuscleGroup.TRAPS,
				MuscleGroup.UPPER_BACK, MuscleGroup.FOREARMS, 5);
		exerciseList.add(bbShrug);

		Exercise dbShrug = new Exercise("Shrugs, Dumbbell", MuscleGroup.TRAPS,
				MuscleGroup.UPPER_BACK, MuscleGroup.FOREARMS, 4);
		exerciseList.add(dbShrug);

		Exercise cShrug = new Exercise("Shrugs, Cable", MuscleGroup.TRAPS,
				MuscleGroup.UPPER_BACK, null, 4);
		exerciseList.add(cShrug);

		Exercise pShrug = new Exercise("Power Shrugs", MuscleGroup.TRAPS,
				MuscleGroup.UPPER_BACK, MuscleGroup.FOREARMS, 5);
		exerciseList.add(pShrug);

	}

	private static void addBack() {
		Exercise bentRow = new Exercise("Bent row, Barbell",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.LOWER_BACK, 9);
		exerciseList.add(bentRow);

		Exercise oneArmRow = new Exercise("One arm Bent row",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.LOWER_BACK, 9);
		exerciseList.add(oneArmRow);

		Exercise oneArmSeatedCableRow = new Exercise(
				"One arm Cable Row, Seated", MuscleGroup.UPPER_BACK,
				MuscleGroup.BICEPS, null, 5);
		exerciseList.add(oneArmSeatedCableRow);

		Exercise cPullover = new Exercise("Pullover, Cable",
				MuscleGroup.UPPER_BACK, MuscleGroup.TRICEPS,
				MuscleGroup.SHOULDERS, 4);
		exerciseList.add(cPullover);

		Exercise bbPullover = new Exercise("Pullover, Barbell",
				MuscleGroup.UPPER_BACK, MuscleGroup.TRICEPS,
				MuscleGroup.SHOULDERS, 5);
		exerciseList.add(bbPullover);

		Exercise penRow = new Exercise("Pendlay Row", MuscleGroup.UPPER_BACK,
				MuscleGroup.BICEPS, MuscleGroup.LOWER_BACK, 9);
		exerciseList.add(penRow);

		Exercise uRow = new Exercise("Upright Row", MuscleGroup.UPPER_BACK,
				MuscleGroup.BICEPS, null, 5);
		exerciseList.add(uRow);

		Exercise seatedCableRow = new Exercise("Cable Row, Seated",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS, null, 6);
		exerciseList.add(seatedCableRow);

		Exercise mTbarRow = new Exercise("T-Bar Row, Machine",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.LOWER_BACK, 6);
		exerciseList.add(mTbarRow);

		Exercise lTbarRow = new Exercise("T-Bar Row, Lying",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.LOWER_BACK, 7);
		exerciseList.add(lTbarRow);

		Exercise tbarRow = new Exercise("T-Bar Row, Barbell",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.LOWER_BACK, 8);
		exerciseList.add(tbarRow);

		Exercise wChin = new Exercise("Chinups, Weighted",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.SHOULDERS, 10);
		exerciseList.add(wChin);

		Exercise uwChin = new Exercise("Chinups, Unweighted",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.SHOULDERS, 9);
		exerciseList.add(uwChin);

		Exercise wPull = new Exercise("Pullups, Weighted",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.SHOULDERS, 10);
		exerciseList.add(wPull);

		Exercise uwPull = new Exercise("Pullups, Unweighted",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.SHOULDERS, 9);
		exerciseList.add(uwPull);

		Exercise latPull = new Exercise("Lat Pulldowns",
				MuscleGroup.UPPER_BACK, MuscleGroup.BICEPS,
				MuscleGroup.SHOULDERS, 7);
		exerciseList.add(latPull);

		Exercise rPecDec = new Exercise("Reverse Pec-Dec",
				MuscleGroup.UPPER_BACK, null, null, 3);
		exerciseList.add(rPecDec);

	}

	private static void addArms() {
		Exercise curl = new Exercise("Curl, Barbell", MuscleGroup.BICEPS, null,
				null, 4);
		exerciseList.add(curl);

		Exercise dbCurl = new Exercise("Curl, Dumbbell", MuscleGroup.BICEPS,
				null, null, 4);
		exerciseList.add(dbCurl);

		Exercise preacherCurl = new Exercise("Preacher Curl",
				MuscleGroup.BICEPS, MuscleGroup.FOREARMS, null, 6);
		exerciseList.add(preacherCurl);

		Exercise spiderCurl = new Exercise("Spider Curl", MuscleGroup.BICEPS,
				MuscleGroup.FOREARMS, null, 6);
		exerciseList.add(spiderCurl);

		Exercise concCurl = new Exercise("Concentration Curl",
				MuscleGroup.BICEPS, MuscleGroup.FOREARMS, null, 4);
		exerciseList.add(concCurl);

		Exercise hammerCurl = new Exercise("Hammer Curl", MuscleGroup.BICEPS,
				MuscleGroup.FOREARMS, null, 6);
		exerciseList.add(hammerCurl);

		Exercise pressdown = new Exercise("Cable Pressdown",
				MuscleGroup.TRICEPS, MuscleGroup.FOREARMS, null, 4);
		exerciseList.add(pressdown);

		Exercise skullcrusher = new Exercise("Skullcrusher",
				MuscleGroup.TRICEPS, null, null, 4);
		exerciseList.add(skullcrusher);

		Exercise triExt = new Exercise("Triceps Extension",
				MuscleGroup.TRICEPS, null, null, 3);
		exerciseList.add(triExt);

		Exercise triKick = new Exercise("Triceps Kickback",
				MuscleGroup.TRICEPS, null, null, 3);
		exerciseList.add(triKick);

		Exercise wristCurl = new Exercise("Wrist Curl", MuscleGroup.FOREARMS,
				MuscleGroup.BICEPS, null, 4);
		exerciseList.add(wristCurl);

		Exercise uwDips = new Exercise("Dips, unweighted", MuscleGroup.TRICEPS,
				MuscleGroup.SHOULDERS, MuscleGroup.CHEST, 7);
		exerciseList.add(uwDips);

		Exercise wDips = new Exercise("Dips, weighted", MuscleGroup.TRICEPS,
				MuscleGroup.SHOULDERS, MuscleGroup.CHEST, 9);
		exerciseList.add(wDips);

	}

	private static void addChest() {
		Exercise flatBench = new Exercise("Bench Press, Flat",
				MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS,
				10);
		exerciseList.add(flatBench);

		Exercise declineBench = new Exercise("Bench Press, Decline",
				MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS,
				8);
		exerciseList.add(declineBench);

		Exercise inclineBench = new Exercise("Bench Press, Incline",
				MuscleGroup.CHEST, MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS,
				8);
		exerciseList.add(inclineBench);

		Exercise dbFlatBench = new Exercise("Bench Press, Dumbbell, Flat",
				MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS,
				9);
		exerciseList.add(dbFlatBench);

		Exercise dbDeclineBench = new Exercise(
				"Bench Press, Dumbbell, Decline", MuscleGroup.CHEST,
				MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS, 7);
		exerciseList.add(dbDeclineBench);

		Exercise dbInclineBench = new Exercise(
				"Bench Press, Dumbbell, Incline", MuscleGroup.CHEST,
				MuscleGroup.SHOULDERS, MuscleGroup.TRICEPS, 7);
		exerciseList.add(dbInclineBench);

		Exercise closegripBench = new Exercise("Close-grip Bench Press",
				MuscleGroup.TRICEPS, MuscleGroup.CHEST, null, 7);
		exerciseList.add(closegripBench);

		Exercise flatFlyes = new Exercise("Flye, Dumbbell, Flat",
				MuscleGroup.CHEST, null, MuscleGroup.SHOULDERS, 4);
		exerciseList.add(flatFlyes);

		Exercise inclineFlyes = new Exercise("Flye, Dumbbell, Incline",
				MuscleGroup.CHEST, MuscleGroup.SHOULDERS, null, 5);
		exerciseList.add(inclineFlyes);

		Exercise cableFlyes = new Exercise("Flye, Cable", MuscleGroup.CHEST,
				null, MuscleGroup.SHOULDERS, 4);
		exerciseList.add(cableFlyes);

		Exercise pecDeck = new Exercise("Flye, Pec-Dec", MuscleGroup.CHEST,
				null, null, 3);
		exerciseList.add(pecDeck);

		Exercise chestPress = new Exercise("Cable Chest Press",
				MuscleGroup.CHEST, null, MuscleGroup.SHOULDERS, 3);
		exerciseList.add(chestPress);

		Exercise machinePress = new Exercise("Bench Press, Machine",
				MuscleGroup.CHEST, MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS,
				5);
		exerciseList.add(machinePress);

		Exercise pushup = new Exercise("Pushup, Weighted", MuscleGroup.CHEST,
				MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS, 3);
		exerciseList.add(pushup);

		Exercise wPushup = new Exercise("Pushup, Weighted", MuscleGroup.CHEST,
				MuscleGroup.TRICEPS, MuscleGroup.SHOULDERS, 4);
		exerciseList.add(wPushup);
	}

}
