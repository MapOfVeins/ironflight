package com.cjkenned.ironflight.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjkenned.ironflight.R;

public class VolumeFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.volume_fragment, container, false);
		
		String[] volumes = getArguments().getStringArray("volumes");
		String overallVolume = getArguments().getString("overallVolume");
		for (int i = 0; i < 7; i++) {
			setVolumeResults(i, rootView, volumes);
		}
		
		TextView volumeTitle = (TextView) rootView.findViewById(R.id.vol_result_col);
		volumeTitle.setText("Est. Volume");
		
		TextView ovrVol = (TextView) rootView.findViewById(R.id.ovr_vol_col);
		ovrVol.setText(overallVolume);
		
		return rootView;
	}

	private void setVolumeResults(int i, View rootView, String[] volumes) {
		switch(i) {
		case 0:
			TextView monVol = (TextView) rootView.findViewById(R.id.mon_vol_col);
			monVol.setText(volumes[i]);

		case 1:
			TextView tueVol = (TextView) rootView.findViewById(R.id.tue_vol_col);
			tueVol.setText(volumes[i]);
		case 2:
			TextView wedVol = (TextView) rootView.findViewById(R.id.wed_vol_col);
			wedVol.setText(volumes[i]);
		case 3:
			TextView thuVol = (TextView) rootView.findViewById(R.id.thu_vol_col);
			thuVol.setText(volumes[i]);
		case 4:
			TextView friVol = (TextView) rootView.findViewById(R.id.fri_vol_col);
			friVol.setText(volumes[i]);
		case 5:
			TextView satVol = (TextView) rootView.findViewById(R.id.sat_vol_col);
			satVol.setText(volumes[i]);
		case 6:
			TextView sunVol = (TextView) rootView.findViewById(R.id.sun_vol_col);
			sunVol.setText(volumes[i]);
		}
	}
}
