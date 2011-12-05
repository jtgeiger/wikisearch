/*========================================================================
| PrefActivity Class
|=========================================================================
| Copyright (C) 2011 Joel Geiger.
| All rights reserved.
|=========================================================================
| Change notice:
| 12/04/11  JTG  Original version.
|=======================================================================*/

package com.alltooeasy.wikisearch.ui;

import com.alltooeasy.wikisearch.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

//==================================================

public class PrefActivity extends PreferenceActivity
{

    final static private String TAG = PrefActivity.class.getSimpleName();
    
//==================================================

    public PrefActivity(){}     //No-op.

//==================================================

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        Log.i( TAG, getClass().getName() + ".onCreate." );
        
        super.onCreate( savedInstanceState );
        
        addPreferencesFromResource( R.xml.settings );
    }

//==================================================
}
