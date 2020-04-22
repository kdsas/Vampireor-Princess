package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class Main12Activity extends AppCompatActivity implements SensorEventListener {

    TextView ProximitySensor, data;
    SensorManager mySensorManager;
    Sensor myProximitySensor;
    public int gold = 0;
    private TextView textView;
    private TextView textView4;
    private Button btnInv;


    private ImageView image;
    private float currentDegree = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        data = (TextView) findViewById(R.id.data);
        textView4 = (TextView) findViewById(R.id.textView4);
        image = (ImageView) findViewById(R.id.main_iv);
        Button inve = findViewById(R.id.button5);
        inve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main12Activity.this);
                dialog.setTitle("Inventory items: " + String.valueOf(ItemView.getInventory().size()));
                if(ItemView.getInventory().size()==28) message = "You've collected all of the items\n"+ "You've Won 100 Points\n";
                if(ItemView.getInventory().size()==0) message = "inventory is empty";
                else for(int i=0; i < ItemView.getInventory().size(); i++) {
                    message += ItemView.getInventory().get(i).name + "\n";
                }
                dialog.setMessage(message);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });


        mySensorManager = (SensorManager) getSystemService
                (Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            ProximitySensor.setText("No Proximity Sensor!");
            AlertDialog.Builder dialog = new AlertDialog.Builder(Main12Activity.this);
            dialog.setTitle("You've Lost 10 Points");
            dialog.setMessage("Lost Game Points For Not Having a Sensor\n");
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener( ) {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss( );
                }
            });
            dialog.show( );
        } else {
            mySensorManager.registerListener(proximitySensorEventListener,
                    myProximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
       mySensorManager.registerListener(this, mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
       mySensorManager.unregisterListener(this);
    }
    SensorEventListener proximitySensorEventListener
            = new SensorEventListener( ) {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }


        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            float degree = Math.round(event.values[0]);

            textView4.setText("Heading: " + Float.toString(degree) + " degrees");

            // create a rotation animation (reverse turn degree degrees)
            RotateAnimation ra = new RotateAnimation(
                    currentDegree,
                    -degree,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f);

            // how long the animation will take place
            ra.setDuration(210);

            // set the animation after the end of the reservation status
            ra.setFillAfter(true);

            // Start the animation
            image.startAnimation(ra);
            currentDegree = -degree;



            if (event.sensor.getType( ) == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    data.setText("Near");
                    ProximitySensor.setText("You Win 10 Points");
                } else {
                    data.setText("Away");

                }

            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


















