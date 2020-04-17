package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);








    };


    public void onButtonClick(View v) {
        Intent myIntent = new Intent(getBaseContext(),   MainActivity.class);
        startActivity(myIntent);

    }

    public void onButtonClicko(View v) {
        Intent myIntent = new Intent(getBaseContext(),   Main6Activity.class);
        startActivity(myIntent);

    }
    public void onButtonClickoo(View v) {
        Intent myIntent = new Intent(getBaseContext(),  Main12Activity.class);
        startActivity(myIntent);

    }



    @Override
    public void onClick(View v) {

    }
}
