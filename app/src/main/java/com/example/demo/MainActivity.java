package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private TextView textView;
    private Group group;
    private Sensor mLight;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        group = findViewById(R.id.group);
        group.setVisibility(View.GONE);

        textView = findViewById(R.id.textView8);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        System.out.println("===> Tổng: "+ deviceSensors.size());
        for(int i = 0; i<deviceSensors.size();i++)
        {
            System.out.println("===> "+ deviceSensors.get(i).getName());
        }
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float[] lux = event.values;
        textView.setText("Length: " + lux.length+ " x: " + lux[0]);

        if(lux[0] < 0)
        {
            Toast.makeText(this, "Điện thoại bị ngược", Toast.LENGTH_SHORT).show();
        }
        if(lux[0] > 0.5)
        {
            Toast.makeText(this, "Điện thoại dựng đứng nè", Toast.LENGTH_SHORT).show();
        }
        // Do something with this sensor value.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
