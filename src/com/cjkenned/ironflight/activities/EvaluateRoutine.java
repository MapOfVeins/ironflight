package com.cjkenned.ironflight.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableRow;
import android.widget.TextView;

import com.cjkenned.ironflight.R;
import com.cjkenned.ironflight.main.Evaluator;
import com.cjkenned.ironflight.main.Result;
import com.cjkenned.ironflight.main.Routine;

public class EvaluateRoutine extends ActionBarActivity {

	private ActionBar mActionBar;
	private Result result;
	private Routine routine;
	private Evaluator eval;

	// Arrays of textviews to display results for each muscle
	final TextView[] muscleText = new TextView[14];
	final TextView[] muscleRes = new TextView[14];
	final TableRow[] muscleRow = new TableRow[14];

	
	private String[] tabs = { "Volume", "Difficulty", "Suggestions" };

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
		
		ArrayList<String> needMoreValues = result.getMoreList();
		ArrayList<String> needLessValues = result.getLessList();
		
		// do some string and list processing here
		for (int i = 0; i < needMoreValues.size(); i++) {
			String s = needMoreValues.get(i);
			if (s.equals("upper_back")) {
				s = "back (upper)";
			}
			if (s.equals("lower_back")) {
				s = "back (lower)";
			}
			s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
			needMoreValues.set(i, s);
		}

		for (int i = 0; i < needLessValues.size(); i++) {
			String s = needLessValues.get(i);
			if (s.equals("upper_back")) {
				s = "back (upper)";
			}
			if (s.equals("lower_back")) {
				s = "back (lower)";
			}
			s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
			needLessValues.set(i, s);
		}

		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.setDisplayShowTitleEnabled(false);
		
		Bundle volArgs = new Bundle();
		volArgs.putStringArray("volumes", volumes);
		volArgs.putString("overallVolume", overallVol);

		Tab tab = mActionBar
				.newTab()
				.setText(tabs[0])
				.setTabListener(
						new TabListener<VolumeFragment>(this, "volume",
								VolumeFragment.class));
		tab.setTag(volArgs);
		mActionBar.addTab(tab);
		
		Bundle difArgs = new Bundle();
		difArgs.putStringArray("diffs", diffs);
		difArgs.putString("overallDiff", overallDif);

		tab = mActionBar
				.newTab()
				.setText(tabs[1])
				.setTabListener(
						new TabListener<DifficultyFragment>(this, "difficulty",
								DifficultyFragment.class));
		tab.setTag(difArgs);
		mActionBar.addTab(tab);
		
		Bundle suggArgs = new Bundle();
		suggArgs.putStringArrayList("needmore", needMoreValues);
		suggArgs.putStringArrayList("needless", needLessValues);

		tab = mActionBar
				.newTab()
				.setText(tabs[2])
				.setTabListener(
						new TabListener<SuggFragment>(this, "suggestions",
								SuggFragment.class));
		tab.setTag(suggArgs);
		mActionBar.addTab(tab);

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
			builder.setMessage("Do you want to start a new routine?  You can still go "
					+ "back and make changes to the current routine and then re-evaluate!");
			builder.setCancelable(false);
			builder.setPositiveButton("Restart",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							Intent i = new Intent(getBaseContext(),
									NewRoutine.class);
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
						}
					});

			builder.setNegativeButton(R.string.cancel,
					new DialogInterface.OnClickListener() {
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
			hBuilder.setMessage("Volume - The amount of work you are doing per muscle group." +
					" If volume is too high, you may feel extra sore.  Too low, and you might" +
					" feel not sore enough!\n" +
					"Difficulty - Some exercises are harder than others.  For example, it is " +
					" much harder to perform many deadlifts than bicep curls.  Too many difficult" +
					" exercises may be hard to perform on one day.\n" +
					"Suggestions - Consider adding or removing exercises in the listed muscle groups" +
					" to achieve more balance in your routine.");
			hBuilder.setCancelable(false);
			hBuilder.setPositiveButton("Got it",
					new DialogInterface.OnClickListener() {

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
	
	public static class TabListener<T extends Fragment> implements
			ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/**
		 * Constructor used each time a new tab is created.
		 * 
		 * @param activity
		 *            The host Activity, used to instantiate the fragment
		 * @param tag
		 *            The identifier tag for the fragment
		 * @param clz
		 *            The fragment's Class, used to instantiate the fragment
		 */
		public TabListener(Activity activity, String tag, Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		/* The following are each of the ActionBar.TabListener callbacks */

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName(), (Bundle) tab.getTag());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
		}
	}

}
