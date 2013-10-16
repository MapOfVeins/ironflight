package com.cjkenned.ironflight.activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.cjkenned.ironflight.R;
import com.cjkenned.ironflight.adapters.ExpandableListAdapter;
import com.cjkenned.ironflight.main.Exercise;
import com.cjkenned.ironflight.main.Routine;

/**
 * Gives the user options to select exercises to add to current workout.
 * This is the meat of the program, where the routine is built and set before
 * being evaluated.
 * 
 * @author Cam Kennedy
 *
 */
public class BuildRoutine extends ActionBarActivity {

	private List<String> groupList;
	private List<Exercise> childList;
	private Map<String, List<Exercise>> exerciseCollection;		
	private Routine routine;
	
	// Arraylists to hold the exercises for each day.  These are created
	// as static in order to access them from the evaluator and in the 
	// exercise adapter. 
	private static ArrayList<Exercise> monday = new ArrayList<Exercise>(5);
	private static ArrayList<Exercise> tuesday = new ArrayList<Exercise>(5);
	private static ArrayList<Exercise> wednesday = new ArrayList<Exercise>(5);
	private static ArrayList<Exercise> thursday = new ArrayList<Exercise>(5);
	private static ArrayList<Exercise> friday = new ArrayList<Exercise>(5);
	private static ArrayList<Exercise> saturday = new ArrayList<Exercise>(5);
	private static ArrayList<Exercise> sunday = new ArrayList<Exercise>(5);
	 
	// private static ArrayList<String> restDays = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the goal and exp level from previous selection
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras == null) {

			} else {
				routine = (Routine) extras.getSerializable("routine");
			}
		}
		
		// Set logo navigation
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.routine_parent);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// Create group list will add all the days to our "parent" 
		// portion of the list view.
		createGroupList();

		// The collection includes the exercises selected by the user
		createCollection();

		// Find the expandable list and set the adapter
		ExpandableListView expListView = (ExpandableListView) findViewById(R.id.day_list);
		
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
				this, groupList, exerciseCollection);
		expListView.setAdapter(expListAdapter);
		int c = expListAdapter.getGroupCount();
		
		// Set the groups to be expanded by default, so we can
		// see what exercises are in each day
		for (int pos = 1; pos <= c; pos++) {
			expListView.expandGroup(pos-1);
		}
	}

	/**
	 * Adds the days to the group list.  So, we have seven total
	 * groups for the expandable list, one for each day.
	 */
	private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("Monday");
        groupList.add("Tuesday");
        groupList.add("Wednesday");
        groupList.add("Thursday");
        groupList.add("Friday");
        groupList.add("Saturday");
        groupList.add("Sunday");
    }
    
	/**
	 * Initializes the children for each group in the expandable
	 * list.  The children are lists of exercises that belong in
	 * each day.  The loadChild method is called to add each
	 * exercise to the child.
	 */
    private void createCollection() {
    	
        exerciseCollection = new LinkedHashMap<String, List<Exercise>>();
 
        for (String day : groupList) {
            if (day.equals("Monday")) {
                loadChild(monday);
            } else if (day.equals("Tuesday"))
                loadChild(tuesday);
            else if (day.equals("Wednesday"))
                loadChild(wednesday);
            else if (day.equals("Thursday"))
                loadChild(thursday);
            else if (day.equals("Friday"))
                loadChild(friday);
            else if (day.equals("Saturday"))
                loadChild(saturday);
            else
            	loadChild(sunday);
 
            exerciseCollection.put(day, childList);
        }
    }
    
    /**
     * Adds the exercises in a current exercise list to the
     * child list of exercises, so that it can be added to the
     * entire collection.
     * 
     * @param exerciseDay List containing the exercises in a 
     * 		certain day of the week.
     */
    private void loadChild(ArrayList<Exercise> exerciseDay) {
        childList = new ArrayList<Exercise>();
        for (Exercise exercise : exerciseDay)
            childList.add(exercise);
    }
    
    /**
     * Returns a list containing the exercises for a
     * particular day.
     * 
     * @param day The name of the day to load, as a string
     * @return A list containing the exercises for that day
     */
    public static ArrayList<Exercise> getDayList(String day) {
    	if (day.equals("Monday")) {
    		return monday;
    	} else if (day.equals("Tuesday")) {
    		return tuesday;
    	} else if (day.equals("Wednesday")) {
    		return wednesday;
    	} else if (day.equals("Thursday")) {
    		return thursday;
    	} else if (day.equals("Friday")) {
    		return friday;
    	} else if (day.equals("Saturday")) {
    		return saturday;
    	} else {
    		return sunday;
    	}
    }
    
    /*
    public static void addRestDay(String day) {
    	restDays.add(day);
    }
    
    public static void delRestDay(String day) {
    	restDays.remove(day);
    }
    
    public static ArrayList<String> getRestDays() {
    	return restDays;
    }*/
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.eval_routine_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.eval_routine_arrow:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Confirm Routine");
                builder.setMessage("Are you sure you are ready to evaluate this routine? \n\n" +
                		"If not, you can go back and review your exercises and your rest days (any days " +
                		"without exercises will be counted as rest days).\n\n" +
                		"Your desired goal is: " + routine.getGoal() + "\n\n" +
                		"Your experience level is: " + routine.getExp() + "\n\n");
                builder.setCancelable(false);
                
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
        				Intent i = new Intent(BuildRoutine.this, EvaluateRoutine.class);
        				i.putExtra("routine", routine);
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
			case R.id.build_routine_help:
				AlertDialog.Builder hBuilder = new AlertDialog.Builder(this);
				hBuilder.setTitle("Build Your Routine");
                hBuilder.setMessage("Use the icons to add exercises to each day (you will also be " +
                		"able to choose sets and reps). \n\nTo set a rest day, simply do not add any " +
                		"exercises to that day!");
                hBuilder.setCancelable(false);
                
                hBuilder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
        				dialog.cancel();
                    }
                });

                AlertDialog alertHDialog = hBuilder.create();
                alertHDialog.show();
				return true;
			case R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
    public Routine getRoutine() {
    	return routine;
    }
}
