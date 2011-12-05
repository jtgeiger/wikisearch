/*========================================================================
| WikiSearchActivity Class
|=========================================================================
| Copyright (C) 2011 Joel Geiger.
| All rights reserved.
|=========================================================================
| Change notice:
| 09/26/11  JTG  Original version.
|=======================================================================*/

package com.alltooeasy.wikisearch;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.alltooeasy.wikisearch.ui.PrefActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//==================================================

public class WikiSearchActivity extends Activity
{
    
    final static private String TAG = WikiSearchActivity.class.getSimpleName();;

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1110011602;
    
    final static private int ID_SEARCH_URL = 1111291939;
    
    final static private String SEARCH_VAR = "%s";

//==================================================

    public WikiSearchActivity(){}   //No-op.
    
//==================================================
    
    private void launch( String uri )
    {
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setData( Uri.parse( uri ) );
        startActivity( intent );
    }
    
//==================================================
    
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        Log.i( TAG, getClass().getName() + ".onActivityResult." );
        
        switch ( requestCode )
        {
            case VOICE_RECOGNITION_REQUEST_CODE:
                if ( resultCode == RESULT_OK )
                {
                    ArrayList<String> matches = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
                    Log.i( TAG, "Got result count=" + matches.size() + ": " + matches );
                    EditText fldTopic = (EditText)findViewById( R.id.editText1 );
                    fldTopic.setText( matches.get( 0 ) );
                }
                break;
        }
    }

//==================================================

    /** Called when the activity is first created. */
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        Log.i( TAG, getClass().getName() + ".onCreate." );
        
        super.onCreate( savedInstanceState );
        
        setContentView( R.layout.main );
        
//        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );
//        Editor prefEditor = defaultSharedPreferences.edit();
//        prefEditor.clear();
//        prefEditor.commit();
//        PreferenceManager.setDefaultValues( this, R.xml.settings, false );
        
        ImageButton speakButton = (ImageButton)findViewById( R.id.btnSpeak );
        
     // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
          new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if ( activities.size() != 0 )
        {
            speakButton.setOnClickListener( new OnClickListener()
            {
                
                @Override
                public void onClick( View v )
                {
                    startVoiceRecognitionActivity();
                }
            });
        }
        else
        {
            Log.i( TAG, "Speech Recognizer not present." );
            speakButton.setEnabled(false);
        }
        
        //launch( "http://www.alltooeasy.com" );
        Button btnSearch = (Button)findViewById( R.id.button1 );
        btnSearch.setOnClickListener( new OnClickListener()
            {
                
                @Override
                public void onClick( View v )
                {
                    EditText fldTopic = (EditText)findViewById( R.id.editText1 );
                    String topic = fldTopic.getText().toString();
                    //SharedPreferences prefs = getPreferences( MODE_PRIVATE );
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( WikiSearchActivity.this );
                    Map<String, ?> allPrefs = prefs.getAll();
                    Log.i( TAG, "All prefs: \n" + allPrefs );
                    String pref_url = getString( R.string.pref_searchurl );
                    String gotUrl = prefs.getString( pref_url, null );
                    Log.i( TAG, "Url pref \"" + pref_url + "\"=\"" + gotUrl + "\"" );
                    if ( gotUrl == null || gotUrl.trim().length() == 0 )
                    {
                        gotUrl = getString( R.string.defaultSearchUrl );
                        Log.i( TAG, "Using default URL." );
                    }
                    Log.i( TAG, "Target url=\"" + gotUrl + "\"" );
                    String url = gotUrl.replace( SEARCH_VAR, topic );
                    Log.i( TAG, "Final url=\"" + url + "\"" );
                    launch( url );
                }
            } );
    }

//==================================================
    
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        Log.i( TAG, getClass().getName() + ".onCreateOptionsMenu." );
        
        super.onCreateOptionsMenu( menu );
        
        menu.add( Menu.NONE, ID_SEARCH_URL, Menu.NONE, R.string.preferences );
        
        return true;
    }

//==================================================
    
    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        final int itemId = item.getItemId();
        
        Log.i( TAG, getClass().getName() + ".onOptionsItemSelected( " + itemId + " )." );
        
        switch ( itemId )
        {
            case ID_SEARCH_URL:
                startActivity( new Intent( this, PrefActivity.class ) );
                return true;
            
            default:
                throw new UnsupportedOperationException( "Unexpected menu item=" + itemId );
        }
    }
    
//==================================================

    @Override
    protected void onResume()
    {
        Log.i( TAG, getClass().getName() + ".onResume." );
        
        super.onResume();
//        final EditText fldTopic = (EditText)findViewById( R.id.editText1 );
//        fldTopic.setText( "" );
    }
    
//==================================================
    
    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.app_name);
        startActivityForResult( intent, VOICE_RECOGNITION_REQUEST_CODE );
    }

//==================================================
}
