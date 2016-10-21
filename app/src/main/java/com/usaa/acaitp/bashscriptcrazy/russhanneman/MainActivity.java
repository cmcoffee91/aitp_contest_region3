package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View v = (View)findViewById(R.id.fragment_container);
        v.setBackgroundColor(Color.GREEN);



        FragmentManager fragmentManager = getFragmentManager();
        Fragment start_frag = fragmentManager.findFragmentById(R.id.fragment_container);
        if(start_frag == null) {

                start_frag = new Start_frag();


            fragmentManager.beginTransaction().add(R.id.fragment_container, start_frag).commit();
        }



    }


    @Override
    protected void onResume() {
        super.onResume();

        boolean hasGooglePlayServices = checkPlayServices();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }

            return false;
        }

        return true;
    }
}
