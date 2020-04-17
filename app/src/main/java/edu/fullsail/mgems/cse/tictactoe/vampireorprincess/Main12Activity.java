package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class Main12Activity extends AppCompatActivity {

    TextView ProximitySensor, data;
    SensorManager mySensorManager;
    Sensor myProximitySensor;
public Main11Activity scoree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main12);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
       ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        ProximitySensor = (TextView) findViewById(R.id.proximitySensor);
        data = (TextView) findViewById(R.id.data);
        mySensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);
        if (myProximitySensor == null) {
            ProximitySensor.setText("No Proximity Sensor!");
            AlertDialog.Builder dialog = new AlertDialog.Builder(Main12Activity.this);
            dialog.setTitle("You've Lost Points");
            dialog.setMessage("Lost Game Points For Not Having a Sensor\n"  + scoree.score--);
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
    SensorEventListener proximitySensorEventListener
            = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    data.setText("Near");
                    ProximitySensor.setText("You Win Points"+ scoree.score++);
                    scoree.textView3.setText("Near Object");
                } else {
                    data.setText("Away");

                }
            }
        }
    };
}









