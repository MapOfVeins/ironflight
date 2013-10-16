package com.cjkenned.ironflight.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cjkenned.ironflight.R;
import com.cjkenned.ironflight.main.Evaluator;
import com.cjkenned.ironflight.main.MuscleGroup;
import com.cjkenned.ironflight.main.Result;
import com.cjkenned.ironflight.main.Routine;

/**
 * Sets up the appropriate structure and then calls the evaluator to
 * evaluate a given routine.  After evaluation is complete, displays the
 * results of the evaluator to the user and then allows them to make
 * changes or start a new routine.
 * 
 * @author Cam Kennedy
 *
 */
public class EvaluateRoutine extends ActionBarActivity {
	
	private Result result;
	private Routine routine;
	private Evaluator eval;
	
	// Arrays of textviews to display results for each muscle
	final TextView[] muscleText = new TextView[14];
	final TextView[] muscleRes = new TextView[14];
	final TableRow[] muscleRow = new TableRow[14];
	
	private final MuscleGroup[] muscles = { MuscleGroup.CHEST, MuscleGroup.BICEPS,
			MuscleGroup.TRICEPS, MuscleGroup.FOREARMS, MuscleGroup.SHOULDERS,
			MuscleGroup.TRAPS, MuscleGroup.UPPER_BACK, MuscleGroup.LOWER_BACK,
			MuscleGroup.LATS, MuscleGroup.ABS, MuscleGroup.GLUTES,
			MuscleGroup.HAMSTRINGS, MuscleGroup.QUADS, MuscleGroup.CALVES };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the goal and experience level from previous selection
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {
				
			} else {
				routine = (Routine) extras.getSerializable("routine");
				routine.createRoutine();
			}
		}
		
		setContentView(R.layout.eval_progress);
		
		// Don't want to show the action bar while we evaluate
		ActionBar ab = getSupportActionBar();
		ab.setHomeButtonEnabled(false);
		ab.hide();
				
		// Empty the lists that we have set as rest days
		/*ArrayList<String> rest = BuildRoutine.getRestDays();
		
		for (String day : rest) {
			ArrayList<Exercise> dayToClear = BuildRoutine.getDayList(day);
			dayToClear.clear();
		}*/
		
		// Initialize the routine mapping and then fill it with the selected exercises.
		// It's easier to fill with a for loop but I don't want to confuse myself
		// by switching the names of the days from strings to integers. 
		routine.addDay("Monday", BuildRoutine.getDayList("Monday"));
		routine.addDay("Tuesday", BuildRoutine.getDayList("Tuesday"));
		routine.addDay("Wednesday", BuildRoutine.getDayList("Wednesday"));
		routine.addDay("Thursday", BuildRoutine.getDayList("Thursday"));
		routine.addDay("Friday", BuildRoutine.getDayList("Friday"));
		routine.addDay("Saturday", BuildRoutine.getDayList("Saturday"));
		routine.addDay("Sunday", BuildRoutine.getDayList("Sunday"));

		// Start the actual routine critique.  This may need to be threaded
		// in the future if the critique gets more complex
		eval = new Evaluator(routine);
		result = eval.evaluate();

		// Extract some values from the results
		String[] volumes = result.getAllDaysVolume();
		String[] diffs = result.getAllDaysDifficulty();
		String overallVol = result.getVolume();
		String overallDif = result.getDifficulty();
		
		// Now we bring back the action bar and set the new view
		setContentView(R.layout.results_table);
		ab.show();
		
		// Set the values and the color for the overall results
		TextView overallVolText = (TextView) findViewById(R.id.ovr_vol_col);
		TextView overallDifText = (TextView) findViewById(R.id.ovr_dif_col);
		
		setResultsTextColor(overallVolText, overallDifText, overallVol, overallDif);
		overallVolText.setText(overallVol);
		overallDifText.setText(overallDif);
		
		// For each day, set the text and the appropriate text color
		for (int i = 0; i < 7; i++) {
			setResultsText(i, volumes, diffs);
		}
		
		// Time to set the values for the need more and less 
		// recommendations
		TextView needMoreText = (TextView) findViewById(R.id.need_more_results);
		TextView needLessText = (TextView) findViewById(R.id.need_less_results);
		
		ArrayList<String> needMoreValues = result.getMoreList();
		ArrayList<String> needLessValues = result.getLessList();
		String mRes = "";
		String lRes = "";
		
		// do some string and list processing here
		for (String s : needMoreValues) {
			if (s.equals("UPPER_BACK")) {
				s = "BACK (UPPER)";
			}
			if (s.equals("LOWER_BACK")) {
				s = "BACK (LOWER)";
			}
			s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
			mRes += s + ", ";
		}

		for (String s : needLessValues) {
			if (s.equals("UPPER_BACK")) {
				s = "BACK (UPPER)";
			}
			if (s.equals("LOWER_BACK")) {
				s = "BACK (LOWER)";
			}
			s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
			lRes += s + ", ";
		}
		
		if (!mRes.isEmpty()) {
			mRes = mRes.substring(0, mRes.length()-2) + ".";
		}
		
		if (!lRes.isEmpty()) {
			lRes = lRes.substring(0, lRes.length()-2) + ".";
		}
		
		needMoreText.setText(mRes);
		needLessText.setText(lRes);
		
	}

	/**
	 * Sets the appropriate results text for each textview in the 
	 * results table.
	 * 
	 * @param i			The table row we are in
	 * @param volumes	The volume results for each day	
	 * @param diffs		The difficulty results for each day
	 */
	private void setResultsText(int i, String[] volumes, String[] diffs) {
		switch(i) {
			case 0:
				TextView monVol = (TextView) findViewById(R.id.mon_vol_col);
				TextView monDif = (TextView) findViewById(R.id.mon_dif_col);
				setResultsTextColor(monVol, monDif, volumes[i], diffs[i]);
				monVol.setText(volumes[i]);
				monDif.setText(diffs[i]);
				monVol.setOnClickListener(dayVolResultClick);
				monDif.setOnClickListener(dayDifResultClick);
			case 1:
				TextView tueVol = (TextView) findViewById(R.id.tue_vol_col);
				TextView tueDif = (TextView) findViewById(R.id.tue_dif_col);
				setResultsTextColor(tueVol, tueDif, volumes[i], diffs[i]);
				tueVol.setText(volumes[i]);
				tueDif.setText(diffs[i]);
				tueVol.setOnClickListener(dayVolResultClick);
				tueDif.setOnClickListener(dayDifResultClick);
			case 2:
				TextView wedVol = (TextView) findViewById(R.id.wed_vol_col);
				TextView wedDif = (TextView) findViewById(R.id.wed_dif_col);
				setResultsTextColor(wedVol, wedDif, volumes[i], diffs[i]);
				wedVol.setText(volumes[i]);
				wedDif.setText(diffs[i]);
				wedVol.setOnClickListener(dayVolResultClick);
				wedDif.setOnClickListener(dayDifResultClick);
			case 3:
				TextView thuVol = (TextView) findViewById(R.id.thu_vol_col);
				TextView thuDif = (TextView) findViewById(R.id.thu_dif_col);
				setResultsTextColor(thuVol, thuDif, volumes[i], diffs[i]);
				thuVol.setText(volumes[i]);
				thuDif.setText(diffs[i]);
				thuVol.setOnClickListener(dayVolResultClick);
				thuDif.setOnClickListener(dayDifResultClick);
			case 4:
				TextView friVol = (TextView) findViewById(R.id.fri_vol_col);
				TextView friDif = (TextView) findViewById(R.id.fri_dif_col);
				setResultsTextColor(friVol, friDif, volumes[i], diffs[i]);
				friVol.setText(volumes[i]);
				friDif.setText(diffs[i]);
				friVol.setOnClickListener(dayVolResultClick);
				friDif.setOnClickListener(dayDifResultClick);
			case 5:
				TextView satVol = (TextView) findViewById(R.id.sat_vol_col);
				TextView satDif = (TextView) findViewById(R.id.sat_dif_col);
				setResultsTextColor(satVol, satDif, volumes[i], diffs[i]);
				satVol.setText(volumes[i]);
				satDif.setText(diffs[i]);
				satVol.setOnClickListener(dayVolResultClick);
				satDif.setOnClickListener(dayDifResultClick);
			case 6:
				TextView sunVol = (TextView) findViewById(R.id.sun_vol_col);
				TextView sunDif = (TextView) findViewById(R.id.sun_dif_col);
				setResultsTextColor(sunVol, sunDif, volumes[i], diffs[i]);
				sunVol.setText(volumes[i]);
				sunDif.setText(diffs[i]);
				sunVol.setOnClickListener(dayVolResultClick);
				sunDif.setOnClickListener(dayDifResultClick);
		}
	}

	/**
	 * Sets the appropriate color for the text in the results.
	 * 
	 * @param dayVol	The volume textview for the day
	 * @param dayDif	The difficulty textview for the day
	 * @param volText	The volume result
	 * @param difText	The difficulty result
	 */
	private void setResultsTextColor(TextView dayVol, TextView dayDif, String volText, String difText) {
		if (volText.equals("Easy")) {
			dayVol.setTextColor(Color.parseColor("#FFA500"));
		} else if (volText.equals("OK")) {
			dayVol.setTextColor(Color.GREEN);
		} else if (volText.equals("Hard")) {
			dayVol.setTextColor(Color.RED);
		} else {
			dayVol.setTextColor(Color.BLACK);
		}
		
		if (difText.equals("Easy")) {
			dayDif.setTextColor(Color.parseColor("#FFA500"));
		} else if (difText.equals("OK")) {
			dayDif.setTextColor(Color.GREEN);
		} else if (difText.equals("Hard")) {
			dayDif.setTextColor(Color.RED);
		} else {
			dayDif.setTextColor(Color.BLACK);
		}
	}
	
	/**
	 * Listener to show the volume work for each muscle
	 * on a given day.
	 */
	OnClickListener dayVolResultClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			HashMap<MuscleGroup, String> dayVol;
			switch(v.getId()) {
				case R.id.mon_vol_col:
					dayVol = result.getVolumeMap(0);
					showDayWork(dayVol, "Monday");
					break;
				case R.id.tue_vol_col:
					dayVol = result.getVolumeMap(1);
					showDayWork(dayVol, "Tuesday");
					break;
				case R.id.wed_vol_col:
					dayVol = result.getVolumeMap(2);
					showDayWork(dayVol, "Wednesday");
					break;
				case R.id.thu_vol_col:
					dayVol = result.getVolumeMap(3);
					showDayWork(dayVol, "Thursday");
					break;
				case R.id.fri_vol_col:
					dayVol = result.getVolumeMap(4);
					showDayWork(dayVol, "Friday");
					break;
				case R.id.sat_vol_col:
					dayVol = result.getVolumeMap(5);
					showDayWork(dayVol, "Saturday");
					break;
				case R.id.sun_vol_col:
					dayVol = result.getVolumeMap(6);
					showDayWork(dayVol, "Sunday");
					break;
			}
		}
	};
	
	/**
	 * Listener to show the difficulty work for each muscle
	 * on a given day.
	 */
	OnClickListener dayDifResultClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			HashMap<MuscleGroup, String> dayDif;
			switch(v.getId()) {
				case R.id.mon_dif_col:
					dayDif = result.getDifficultyMap(0);
					showDayWork(dayDif, "Monday");
					break;
				case R.id.tue_dif_col:
					dayDif = result.getDifficultyMap(1);
					showDayWork(dayDif, "Tuesday");
					break;
				case R.id.wed_dif_col:
					dayDif = result.getDifficultyMap(2);
					showDayWork(dayDif, "Wednesday");
					break;
				case R.id.thu_dif_col:
					dayDif = result.getDifficultyMap(3);
					showDayWork(dayDif, "Thursday");
					break;
				case R.id.fri_dif_col:
					dayDif = result.getDifficultyMap(4);
					showDayWork(dayDif, "Friday");
					break;
				case R.id.sat_dif_col:
					dayDif = result.getDifficultyMap(5);
					showDayWork(dayDif, "Saturday");
					break;
				case R.id.sun_dif_col:
					dayDif = result.getDifficultyMap(6);
					showDayWork(dayDif, "Sunday");
					break;
			}		
		}
	};
	
	/**
	 * Initializes a new Dialog that will display the results for each
	 * muscle, per day.  Also generates the TextViews and the right
	 * layout for the view.
	 *  
	 * @param dayMap	The results of a day
	 * @param day		The name of the day
	 */
	private void showDayWork(HashMap<MuscleGroup, String> dayMap, String day) {
		final Dialog resultsDialog = new Dialog(EvaluateRoutine.this);
		
		resultsDialog.setTitle(day);
		resultsDialog.setCancelable(true);
		resultsDialog.setContentView(R.layout.day_result_dialog);
		
		resultsDialog.show();

		TableLayout layout = (TableLayout) resultsDialog.findViewById(R.id.muscle_table);
		for (int i = 0; i < 14; i++) {
			muscleText[i] = new TextView(this);
			muscleText[i].setText(muscles[i].toString());
			muscleText[i].setPadding(4, 4, 4, 0);
			muscleText[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
			
			muscleRes[i] = new TextView(EvaluateRoutine.this);
			muscleRes[i].setText(dayMap.get(muscles[i]));
			muscleRes[i].setPadding(4, 4, 4, 0);
			muscleRes[i].setTypeface(null, Typeface.BOLD);
			muscleRes[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
			
			muscleRow[i] = new TableRow(this);
			muscleRow[i].setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
			muscleRow[i].addView(muscleText[i]);
			muscleRow[i].addView(muscleRes[i]);
			
			layout.addView(muscleRow[i], new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.results_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.restart_app:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Start new Routine?");
                builder.setMessage("Do you want to start a new routine?  You can still go " +
                		"back and make changes to the current routine and then re-evaluate!");
                builder.setCancelable(false);
                builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                	
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
        				Intent i = new Intent(getBaseContext(), NewRoutine.class);
        				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        				startActivity(i);
                	}
                });
                
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
                		dialog.cancel();
                    }
                });
                
                AlertDialog alertDialog = builder.create();
                alertDialog.show();   
                return true;
			case R.id.eval_routine_help:
                AlertDialog.Builder hBuilder = new AlertDialog.Builder(this);
                hBuilder.setTitle("Evaluation Results");
                hBuilder.setMessage("This shows a summary of your routine by estimated " +
                		"volume and difficulty of chosen exercises.  Clicking on each " +
                		"daily result will show a breakdown by muscle group.  If the " +
                		"result is easy, this isn't necessarily bad, but you may " +
                		"be able to do more work.");
                hBuilder.setCancelable(false);
                hBuilder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                	
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
        				dialog.cancel();
                	}
                });
                
                AlertDialog dialog = hBuilder.create();
                dialog.show();
				return true;
			default:
				return true;
		}
	}
}
