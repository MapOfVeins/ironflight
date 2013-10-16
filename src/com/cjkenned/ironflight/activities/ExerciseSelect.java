package com.cjkenned.ironflight.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cjkenned.ironflight.R;
import com.cjkenned.ironflight.adapters.ExerciseListAdapter;
import com.cjkenned.ironflight.main.AlphabetListIndex;
import com.cjkenned.ironflight.main.Exercise;
import com.cjkenned.ironflight.services.ExerciseService;

/**
 * Code is taken from
 * http://vardhan-justlikethat.blogspot.in/2012/06/android-android-listview.html
 * Some slight alterations made but it's basically the same...
 * 
 */
public class ExerciseSelect extends AlphabetListIndex {

    private ListView exerciseLV;
    private ExerciseListAdapter exerciseListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);		
        setContentView(R.layout.exercise_list);
        
        exerciseLV = (ListView)findViewById(R.id.exerciseLV);
        
        exerciseList = ExerciseService.getExerciseList();
        
        ArrayList<Exercise> subsidiesList = getIndexedExercises(exerciseList);
        totalListSize = subsidiesList.size();
        
        exerciseListAdapter = new ExerciseListAdapter(this, subsidiesList);
        exerciseLV.setAdapter(exerciseListAdapter);
        
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndex.setOnClickListener(onClicked);
        sideIndexHeight = sideIndex.getHeight();
        mGestureDetector = new GestureDetector(this, new ListIndexGestureListener());
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getDisplayListOnChange();
    }
    
    
    private ArrayList<Exercise> getIndexedExercises(ArrayList<Exercise> eList) {
        
        // Retrieve it from DB in shorting order 
        ArrayList<Exercise> e = new ArrayList<Exercise>();
        
        // Add default item
        String idx1 = null;
        String idx2 = null;
        for (int i = 0; i < eList.size(); i++) {
            Exercise temp = eList.get(i);
            //Insert the alphabets
            idx1 = (temp.getName().substring(0, 1)).toLowerCase();
            if(!idx1.equalsIgnoreCase(idx2))
            {
                e.add(new Exercise(idx1.toUpperCase(), null, null, null, 0));
                idx2 = idx1;
                dealList.add(i);
            }
            e.add(temp);
        }
        
        return e;
    }
    
    /**
     * ListIndexGestureListener method gets the list on scroll.
     */
    private class ListIndexGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            /**
             * we know already coordinates of first touch we know as well a
             * scroll distance
             */
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            /**
             * when the user scrolls within our side index, we can show for
             * every position in it a proper item in the list
             */
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
