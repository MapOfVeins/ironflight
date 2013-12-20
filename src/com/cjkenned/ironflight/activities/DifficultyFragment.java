package com.cjkenned.ironflight.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjkenned.ironflight.R;

public class DifficultyFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.difficulty_fragment, container, false);
		
		String[] diffs = getArguments().getStringArray("diffs");
		String overallDiff = getArguments().getString("overallDiff");
		
		for (int i = 0; i < 7; i++) {
			setDiffResults(i, rootView, diffs);
		}
		
		TextView diffTitle = (TextView) rootView.findViewById(R.id.dif_result_col);
		diffTitle.setText("Est. Difficulty");
		
		TextView ovrDiff = (TextView) rootView.findViewById(R.id.ovr_dif_col);
		ovrDiff.setText(overallDiff);
		
		return rootView;
	}

	private void setDiffResults(int i, View rootView, String[] diffs) {
		switch(i) {
		case 0:
			TextView monDif = (TextView) rootView.findViewById(R.id.mon_dif_col);
			monDif.setText(diffs[i]);
		case 1:
			TextView tueDif = (TextView) rootView.findViewById(R.id.tue_dif_col);
			tueDif.setText(diffs[i]);
		case 2:
			TextView wedDif = (TextView) rootView.findViewById(R.id.wed_dif_col);
			wedDif.setText(diffs[i]);
		case 3:
			TextView thuDif = (TextView) rootView.findViewById(R.id.thu_dif_col);
			thuDif.setText(diffs[i]);
		case 4:
			TextView friDif = (TextView) rootView.findViewById(R.id.fri_dif_col);
			friDif.setText(diffs[i]);
		case 5:
			TextView satDif = (TextView) rootView.findViewById(R.id.sat_dif_col);
			satDif.setText(diffs[i]);
		case 6:
			TextView sunDif = (TextView) rootView.findViewById(R.id.sun_dif_col);
			sunDif.setText(diffs[i]);
		}	
	}
}
