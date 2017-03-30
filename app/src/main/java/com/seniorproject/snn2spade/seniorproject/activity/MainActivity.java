package com.seniorproject.snn2spade.seniorproject.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seniorproject.snn2spade.seniorproject.R;
import com.seniorproject.snn2spade.seniorproject.fragment.DashboardFragment;

public class MainActivity extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.dashboard_fragment_container, DashboardFragment.newInstance(""))
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
