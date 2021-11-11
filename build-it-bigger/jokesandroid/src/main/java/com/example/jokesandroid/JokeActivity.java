package com.example.jokesandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private static final String GCE_RESULT = "gce_result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_lib);

        try{
            String mJoke = getIntent().getStringExtra(GCE_RESULT);
            TextView mTextView = findViewById(R.id.tv_jokes);
            mTextView.setText(mJoke);
        } catch (Exception e){
            Toast.makeText(this, "Error in request", Toast.LENGTH_SHORT).show();
            Log.e(JokeActivity.class.getSimpleName(), e.toString());
        }
    }

    public JokeActivity(){

    }
}
