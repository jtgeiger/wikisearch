/*========================================================================
| EditUrlActivity Class
|=========================================================================
| Copyright (C) 2011 Joel Geiger.
| All rights reserved.
|=========================================================================
| Change notice:
| 11/29/11  JTG  Original version.
|=======================================================================*/

package com.alltooeasy.wikisearch.ui;

import com.alltooeasy.wikisearch.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

//==================================================

public class EditUrlActivity extends Activity
{

    final static private String TAG = EditUrlActivity.class.getSimpleName();
    
//==================================================

    public EditUrlActivity(){}      //No-op.

//==================================================
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        Log.i( TAG, getClass().getName() + ".onCreate." );
        
        super.onCreate( savedInstanceState );
        
        setContentView( R.layout.edit_url );
    }

//==================================================
}
