package com.cjkenned.ironflight.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjkenned.ironflight.R;

public class SuggFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View rootView = inflater.inflate(R.layout.suggestion_fragment, container, false);
		
		ArrayList<String> needMoreValues = getArguments().getStringArrayList("needmore");
		ArrayList<String> needLessValues = getArguments().getStringArrayList("needless");
		
		ListView lvMore = (ListView) rootView.findViewById(R.id.lv_more);
		ListView lvLess = (ListView) rootView.findViewById(R.id.lv_less);
		
	    ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_list_item_1, needMoreValues);
	    
	    ArrayAdapter<String> lAdapter = new ArrayAdapter<String>(getActivity(),
	            android.R.layout.simple_list_item_1, needLessValues);
	    
	    lvMore.setAdapter(mAdapter);
	    lvLess.setAdapter(lAdapter);

		return rootView;
	}
}
