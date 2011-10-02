package com.alltooeasy.wikisearch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class WikiSearchActivity extends Activity {
    
    final static private String TAG = "WikiSearchActivity";

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1110011602;

    @Override
    protected void onActivityResult( int requestCode, int resultCode,
            Intent data )
    {
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

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button speakButton = (Button)findViewById( R.id.btnSpeak );
        
     // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
          new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0)
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
          speakButton.setEnabled(false);
          speakButton.setText("Recognizer not present");
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
                    String url = "http://en.wikipedia.org/wiki/Special:Search?search=" + topic;
                    launch( url );
                }
            } );
    }
    
    private void launch( String uri )
    {
        Intent intent = new Intent( Intent.ACTION_VIEW );
        intent.setData( Uri.parse( uri ) );
        startActivity( intent );
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
//        final EditText fldTopic = (EditText)findViewById( R.id.editText1 );
//        fldTopic.setText( "" );
    }
    
    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

}