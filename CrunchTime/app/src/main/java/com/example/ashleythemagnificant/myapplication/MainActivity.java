package com.example.ashleythemagnificant.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnItemSelectedListener {
    public final static String EXTRA_MESSAGE = "com.example.ashleythemagnificant.myapplication.EXTRA_MESSAGE";
    public final static String EXERCISE = "com.example.ashleythemagnificant.myapplication.EXERCISE";
    public final static String REPS_MIN = "com.example.ashleythemagnificant.myapplication.REP" +
            "S_MIN" ;
    private String exercise_choice = "No Selection";
    private String reps_choice = "No Selection";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Make the Spinner
        Spinner spinner_exercises = (Spinner) findViewById(R.id.spinner_exercises);
        Spinner spinner_reps = (Spinner) findViewById(R.id.spinner_reps);
        spinner_exercises.setOnItemSelectedListener(this);
        spinner_reps.setOnItemSelectedListener(this);

        List<String> exercise = new ArrayList<String>();
        exercise.add("Choose and Exercise");
        exercise.add("Push Ups");
        exercise.add("Sit Ups");
//        exercise.add("Lunges");
//        exercise.add("Squats");
        exercise.add("Jumping Jacks");
        exercise.add("Jogging");

        List<String> rep_min = new ArrayList<String>();
        rep_min.add("Type ?");
        rep_min.add("Reps");
        rep_min.add("Min");

        ArrayAdapter exerc_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exercise);
        exerc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter reps_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rep_min);
        reps_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_exercises.setAdapter(exerc_adapter);
        spinner_reps.setAdapter(reps_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /* Called when the user clicks the Send Button. */
    /* This will take the Exercise, number and whether
    Reps/minutes and Display them on another page. */
    public void sendMessage(View view) {
        // The intent is used to read the text field in the button and
        // initiate another activity
        Intent intent  = new Intent(this, DisplayMessageActivity.class);
//         spinner_exercise, spinner_reps, Edit_text
        EditText editText = (EditText) findViewById(R.id.edit_reps);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message.toString());
        intent.putExtra(EXERCISE, exercise_choice.toString());
        intent.putExtra(REPS_MIN, reps_choice.toString());
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();

        switch(parent.getId()){
            case R.id.spinner_exercises:
                exercise_choice = selection;
                break;
            case R.id.spinner_reps:
                reps_choice = selection;
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }
}

