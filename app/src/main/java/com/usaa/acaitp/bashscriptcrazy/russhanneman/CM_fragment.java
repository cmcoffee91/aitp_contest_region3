package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by christophercoffee on 10/20/16.
 */

public class CM_fragment {

    //only used to replace already existing fragment
    public void launchFragWithName(Activity activity, String fragClassName, Bundle bundleArgs)
    {

        FragmentActivity mycontext = (FragmentActivity)activity;
        FragmentManager fragmentManager = mycontext.getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
      //  ft.setCustomAnimations(R.animator.curlin,R.animator.curlin);

        Fragment fragToLaunch = null;

        try
        {
            fragToLaunch = (Fragment) Class.forName("com.usaa.acaitp.bashscriptcrazy.russhanneman." + fragClassName).newInstance();
            if(!(bundleArgs == null))
            {
                fragToLaunch.setArguments(bundleArgs);
            }
            //for animation replace fragmentmanager with ft and remove begintransaction
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragToLaunch, fragClassName)
                    .addToBackStack(fragClassName).commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void popFragByName(Activity activity, String fragClassName)
    {
        FragmentActivity mycontext = (FragmentActivity)activity;
        FragmentManager fragmentManager = mycontext.getFragmentManager();

        fragmentManager.popBackStack (fragClassName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
