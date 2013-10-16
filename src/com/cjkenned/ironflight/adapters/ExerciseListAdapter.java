package com.cjkenned.ironflight.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjkenned.ironflight.R;
import com.cjkenned.ironflight.activities.BuildRoutine;
import com.cjkenned.ironflight.main.Exercise;
import com.cjkenned.ironflight.main.Routine;

public class ExerciseListAdapter extends BaseAdapter {

	private static final String TAG = ExerciseListAdapter.class.getName();
	private Activity activity;
	private ArrayList<Exercise> items;
	private String day;
	private Routine routine;

	public ExerciseListAdapter(Activity activity, ArrayList<Exercise> items) {
		Log.i(TAG, TAG);
		this.activity = activity;
		this.items = items;
		this.day = activity.getIntent().getExtras().getString("day");
		this.routine = (Routine) activity.getIntent().getExtras().getSerializable("routine");
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {

			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.listrow_exercise, null);
			holder = new ViewHolder();

			holder.name = (TextView) convertView.findViewById(R.id.nameTV);
			holder.headingLL = (LinearLayout) convertView
					.findViewById(R.id.headingLL);
			holder.headingTV = (TextView) convertView
					.findViewById(R.id.headingTV);
			holder.nameLL = (LinearLayout) convertView
					.findViewById(R.id.nameLL);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position < items.size()) {
			/* get the exercise object that was selected */
			final Exercise exercise = items.get(position);
			
			if (exercise != null && (exercise.getName().length() == 1)) {
				holder.nameLL.setVisibility(View.GONE);
				holder.headingLL.setVisibility(View.VISIBLE);
				holder.headingTV.setText(exercise.getName());
			} else {
				holder.nameLL.setVisibility(View.VISIBLE);
				holder.headingLL.setVisibility(View.GONE);
				holder.name.setText(exercise.getName());

				View ll = (LinearLayout) holder.name.getParent();
				ll.setFocusable(true);
				ll.setSelected(true);
				
				ll.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						/* exercise name, used as the title of the dialog */
						final String selectedExercise = exercise.getName();
						
						/* base activity.  The context is needed to launch the builder activity after the
						set and rep selection is made */
						final Activity mContext = (Activity) v.getContext();
						
						/* here we create the dialog to set the reps and the sets */
						AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
						ad.setTitle(selectedExercise);
						ad.setMessage("Select number of sets and reps");
						
						/* create and set the dialog view, inflate it. Will need it later for the editables */
						LayoutInflater inflater = ((Activity) v.getContext()).getLayoutInflater();
						final View layoutV = inflater.inflate(R.layout.set_rep_select, null);
						ad.setView(layoutV);
						
						/* get the text for the sets and reps numbers */
						final EditText setsText = (EditText) layoutV.findViewById(R.id.edttext_sets);
						final EditText repsText = (EditText) layoutV.findViewById(R.id.edttext_reps);
						
						/* set the OK button actions */
						ad.setPositiveButton(R.string.ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										
										/* now we can extract the text from the edittext boxes */
										String setNum = setsText.getText().toString();
										String repNum = repsText.getText().toString();
										
										/* save the exercise and the reps to the proper structure */
										saveExercise(day, exercise);
										saveTotalReps(setNum, repNum, exercise);
										
										// Start the builder activity again, passing along the information selected
										Intent i = new Intent(mContext, BuildRoutine.class);
										i.putExtra("routine", routine);
										
										// Move the activity to the top of the stack for better navigation
										i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
										activity.finish();
									}
								});
						
						ad.setNegativeButton(R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
									}
								});
						ad.show();
					}
				});
			}
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView name, headingTV;
		LinearLayout nameLL, headingLL;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public static void saveExercise(String d, Exercise e) {
		BuildRoutine.getDayList(d).add(e);
	}
	
	public void saveTotalReps(String s, String r, Exercise e) {
		int sets = Integer.parseInt(s);
		int reps = Integer.parseInt(r);

		e.setSets(sets);
		e.setReps(reps);
	}
}
