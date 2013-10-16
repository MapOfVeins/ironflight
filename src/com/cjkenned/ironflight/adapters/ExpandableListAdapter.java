package com.cjkenned.ironflight.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjkenned.ironflight.R;
import com.cjkenned.ironflight.activities.BuildRoutine;
import com.cjkenned.ironflight.activities.ExerciseSelect;
import com.cjkenned.ironflight.main.Exercise;
import com.cjkenned.ironflight.main.Routine;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	// http://theopentutorials.com/tutorials/android/listview/android-expandable-list-view-example/

	private BuildRoutine context;
	private Map<String, List<Exercise>> workDays;
	private List<String> days;
	private Routine routine;

	public ExpandableListAdapter(Activity context, List<String> days, Map<String, List<Exercise>> workDays) {
		this.context = (BuildRoutine) context;
		this.workDays = workDays;
		this.days = days;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return workDays.get(days.get(groupPosition)).get(childPosition).getName();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		/* need the exercise name and day name */
		final String exercise = (String) getChild(groupPosition, childPosition);
		final String dayName = (String) getGroup(groupPosition);

		LayoutInflater inflater = context.getLayoutInflater();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.routine_exercise_row, null);
		}

		TextView item = (TextView) convertView.findViewById(R.id.exercise);
		
		ImageView delExercise = (ImageView) convertView.findViewById(R.id.imgv_del_exercise);
		delExercise.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove Exercise?");
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
                		/* if the user chooses to remove, we take the exercise out
                		 * of the view and out of the actual list itself
                		 */
                		List<Exercise> child = workDays.get(days.get(groupPosition));
                        child.remove(childPosition);
                        ArrayList<Exercise> e = BuildRoutine.getDayList(dayName);
                        e.remove(childPosition);
                        notifyDataSetChanged();
                    }
                });
                
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
                		dialog.cancel();
                    }
                });
                
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
		});
		item.setText(exercise + ", " + 
				workDays.get(days.get(groupPosition)).get(childPosition).getSets() +
				"x" +
				workDays.get(days.get(groupPosition)).get(childPosition).getReps());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return workDays.get(days.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return days.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return days.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final String dayName = (String) getGroup(groupPosition);
		final int pos = groupPosition;
		
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.routine_day_row, null);
		}
		
		/*
		 * Checkboxes removed until I can figure out how to save state in an expandable list
		 * properly...
		final CheckBox restBox = (CheckBox) convertView.findViewById(R.id.chkbox_rest_day);
		
		restBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					BuildRoutine.addRestDay(dayName);;
				} else {
					BuildRoutine.delRestDay(dayName);
				}	
			}
		});*/
	
		final ImageView addExercise = (ImageView) convertView.findViewById(R.id.imgv_add_exercise);
		addExercise.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				routine = context.getRoutine();
				Intent selectIntent = new Intent(context, ExerciseSelect.class);
				selectIntent.putExtra("day", dayName);
				selectIntent.putExtra("routine", routine);
				context.startActivity(selectIntent);
			}
		});
		
		final ImageView delAll = (ImageView) convertView.findViewById(R.id.imgv_del_all);
		delAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Remove Exercises");
                builder.setMessage("Do you want to remove all exercises for " + dayName + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
                		List<Exercise> child = workDays.get(days.get(pos));
                		ArrayList<Exercise> e = BuildRoutine.getDayList(dayName);
                		child.clear();
                		e.clear();
                		notifyDataSetChanged();
                    }
                });
                
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                	@Override
                	public void onClick(DialogInterface dialog, int id) {
                		dialog.cancel();
                    }
                });
                
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }		
		});
		
		TextView item = (TextView) convertView.findViewById(R.id.exercise);	
		item.setText(dayName);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
