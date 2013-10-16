package com.cjkenned.ironflight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cjkenned.ironflight.R;
import com.cjkenned.ironflight.main.Routine;

/**
 * The new routine activity allows for users to input some
 * preliminary information before actually building the routine.
 * This info is used for changing and setting some numbers
 * during the actual evaluation.
 * 
 * @author Cam Kennedy
 *
 */
public class NewRoutine extends ActionBarActivity implements OnItemSelectedListener {
	
	private Routine routine;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		routine = new Routine();
		setContentView(R.layout.new_routine2);
		
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(false);
		
		// Get all our spinners and create their adapters.
		// The adapters are generic Array Adapters populated
		// by string array resources.
		Spinner genSpinner = (Spinner) findViewById(R.id.spinner_gender);
		ArrayAdapter<CharSequence> genAdapter = ArrayAdapter.createFromResource(this, 
				R.array.gender_array, android.R.layout.simple_spinner_item);
		genAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genSpinner.setAdapter(genAdapter);
		genSpinner.setOnItemSelectedListener(this);
		
		Spinner goalSpinner = (Spinner) findViewById(R.id.spinner_goal);
		ArrayAdapter<CharSequence> goalAdapter = ArrayAdapter.createFromResource(this, 
				R.array.goal_array, android.R.layout.simple_spinner_item);
		goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		goalSpinner.setAdapter(goalAdapter);
		goalSpinner.setOnItemSelectedListener(this);

		Spinner expSpinner = (Spinner) findViewById(R.id.spinner_exp);
		ArrayAdapter<CharSequence> expAdapter = ArrayAdapter.createFromResource(this, 
				R.array.exp_array, android.R.layout.simple_spinner_item);
		expAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		expSpinner.setAdapter(expAdapter);
		expSpinner.setOnItemSelectedListener(this);
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, 
			int pos, long id) {
		// Switch to set the values in our routine
		switch (parent.getId()) {
			case R.id.spinner_gender:
				// TODO add gender data
				break;
			case R.id.spinner_goal:
				routine.setGoal(parent.getItemAtPosition(pos).toString());
				break;
			case R.id.spinner_exp:
				routine.setExp(parent.getItemAtPosition(pos).toString());
				break;
		}	
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.build_routine_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.build_routine_arrow:
				// Ready to continue, move to next activity
				Intent buildRoutineIntent = new Intent(NewRoutine.this, BuildRoutine.class);
				buildRoutineIntent.putExtra("routine", routine);
				startActivity(buildRoutineIntent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
