package com.example.ashleythemagnificant.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class DisplayMessageActivity extends Activity {
    Intent intent = getIntent();
    private double weight = 160;
    private double age = 21;
//    target heart rate
    private double heart_rate = 99.0;
    private ArrayAdapter<String> arrayAdapter;
//    Key Converts form time to rep number
    private HashMap<String, Double> converter = new HashMap<>();
    private HashMap<String, String> reps_or_min = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        converter.put("Jogging", 1.0);
        reps_or_min.put("Jogging", "Min");

        converter.put("Jumping Jacks", 0.8);
        reps_or_min.put("Jumping Jacks", "Min");

        converter.put("Push Ups", 35.0);
        reps_or_min.put("Push Ups", "Reps");

        converter.put("Sit Ups", 29.0);
        reps_or_min.put("Sit Ups", "Reps");

        while (intent == null) {
            intent = getIntent();
        }
        displayExerciseCalculations();
    }


    public void displayExerciseCalculations() {
        String number = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        double calculation_number = 0;
        try {
            System.out.println("int set");

            calculation_number = Double.parseDouble(number);
            System.out.print("Calc num is = "); System.out.println(calculation_number);

        } catch(NumberFormatException e) {
            System.out.println(e.toString() + "Is not a number");
        }

        String exercise = intent.getStringExtra(MainActivity.EXERCISE);
        String reps_min = intent.getStringExtra(MainActivity.REPS_MIN);
        String message = "You did " + exercise + " for " + number + " " + reps_min;

        TextView textView = (TextView) findViewById(R.id.display_message);
        textView.setText(message);


        this.heart_rate = 220 - this.age;
        System.out.print("Before calculation method call = "); System.out.println(calculation_number);
        double calories_burned = caloriesBurnedForExercise(exercise, calculation_number);
        String calories_burned_message = "You burned " + calories_burned + " calories!";
        TextView calories_burnedTextView = (TextView) findViewById(R.id.display_results);
//        calories_burnedTextView.setTextSize(13);
        String equivalent_exercises = "These exercises will burn the same amount of calories:";
        calories_burnedTextView.setText(calories_burned_message);

        TextView simple_TextView = (TextView) findViewById(R.id.simple_message);
        simple_TextView.setText(equivalent_exercises);

//     Equivalent Exercises
        ListView listView = (ListView) findViewById(R.id.display_list_view);
        ArrayList<String> opposite_exercises = equivalentExercise(calories_burned, exercise);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_view, R.id.txt_item, opposite_exercises);
        listView.setAdapter(arrayAdapter);
        for(int i = 0; i< opposite_exercises.size(); i++) {
            String cur = opposite_exercises.get(i);
            System.out.println(cur);
        }

    }

    public double caloriesBurnedForExercise(String exercise, double time) {
        double answer = 0;
        // Gaol heart rate
        switch(exercise){
            case "Push Ups":
                // to find push ups per minute 35 per minute
                time = time*(1.0/35.0);
                answer = equationCalculation(time);
                break;
            case "Sit Ups":
//                29 sit ups per minute average
                time = time*(1.0/29.0);
                answer = equationCalculation(time);
                break;
            case "Jogging":
                answer = equationCalculation(time*.9);
                break;
            case "Jumping Jacks":
                answer = equationCalculation(time);
                break;

        }
        return (double) Math.round(answer * 100.0) / 100.0;
    }
    public double equationCalculation(double time) {
        // time in minutes
        double calories_burned = (((this.age*0.074) - (this.weight*0.05741) + (this.heart_rate*0.4472) - 20.4022 )*time)/4.184;
        return calories_burned;
    }

    public double oppositeEquationCalculation(double calorie, double metric) {
        double time = (calorie*4.184)/((this.age*0.074) - (this.weight*0.05741) + (this.heart_rate*0.4472) - 20.4022);
        return (double) Math.round(time * metric * 100.0) / 100.0;
    }

    public ArrayList<String> equivalentExercise(double calorie, String cur_exercise) {
//        iterate through the keys of the hashmap
        ArrayList<String> equivalent_exercises = new ArrayList<String>();
        Object[] keys = converter.keySet().toArray();
        for(int i = 0; i < keys.length; i++){
            String current_key = (String) keys[i];
            double time = 0.0;
            if (!current_key.equals(cur_exercise)) {
                double exercise = converter.get(current_key);
                time = oppositeEquationCalculation(calorie, exercise);
                equivalent_exercises.add(current_key + " :  " + Double.toString(time) + " " + reps_or_min.get(current_key));
            }
        }
        return equivalent_exercises;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.2
        getMenuInflater().inflate(R.menu.menu_display_message, menu);
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

}
