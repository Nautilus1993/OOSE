package com.team16.oose_project.core;

import android.icu.text.CollationKey;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.team16.oose_project.R;
import com.team16.oose_project.Subcategory.Balcony;

/**
 * Created by lxx on 12/11/16.
 */

// not working, needs refactoring
public class MyBottomBar extends AppCompatActivity {
    public final BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);;


    public void setBottomBar(){
        bottomBar.getRootView().findViewById(R.id.tab_none).setVisibility(View.GONE);
    }
}
