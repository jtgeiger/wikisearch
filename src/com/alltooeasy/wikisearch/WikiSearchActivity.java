package com.alltooeasy.wikisearch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WikiSearchActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
        final EditText fldTopic = (EditText)findViewById( R.id.editText1 );
        fldTopic.setText( "" );
    }
}